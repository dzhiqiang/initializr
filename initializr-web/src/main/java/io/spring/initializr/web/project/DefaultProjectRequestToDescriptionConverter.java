/*
 * Copyright 2012-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.initializr.web.project;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.spring.initializr.generator.architecture.Architecture;
import io.spring.initializr.generator.buildsystem.BuildSystem;
import io.spring.initializr.generator.demo.Template;
import io.spring.initializr.generator.language.Language;
import io.spring.initializr.generator.packaging.Packaging;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.project.ProjectDescription;
import io.spring.initializr.generator.version.Version;
import io.spring.initializr.metadata.ArchitectureGroup;
import io.spring.initializr.metadata.DefaultMetadataElement;
import io.spring.initializr.metadata.Demo;
import io.spring.initializr.metadata.Dependency;
import io.spring.initializr.metadata.InitializrConfiguration.Platform;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.Module;
import io.spring.initializr.metadata.Type;
import io.spring.initializr.metadata.support.MetadataBuildItemMapper;
import io.spring.initializr.metadata.support.MetadataDemoMapper;
import io.spring.initializr.metadata.support.MetadataTemplateMapper;

import org.springframework.util.Assert;

/**
 * A default {@link ProjectRequestToDescriptionConverter} implementation that uses the
 * {@link InitializrMetadata metadata} to set default values for missing attributes if
 * necessary. Transparently transform the platform version if necessary using a
 * {@link ProjectRequestPlatformVersionTransformer}.
 *
 * @author Madhura Bhave
 * @author HaiTao Zhang
 * @author Stephane Nicoll
 */
public class DefaultProjectRequestToDescriptionConverter
		implements ProjectRequestToDescriptionConverter<ProjectRequest> {

	private final ProjectRequestPlatformVersionTransformer platformVersionTransformer;

	public DefaultProjectRequestToDescriptionConverter() {
		this((version, metadata) -> version);
	}

	public DefaultProjectRequestToDescriptionConverter(
			ProjectRequestPlatformVersionTransformer platformVersionTransformer) {
		Assert.notNull(platformVersionTransformer, "PlatformVersionTransformer must not be null");
		this.platformVersionTransformer = platformVersionTransformer;
	}

	@Override
	public ProjectDescription convert(ProjectRequest request, InitializrMetadata metadata) {
		MutableProjectDescription description = new MutableProjectDescription();
		convert(request, description, metadata);
		return description;
	}

	/**
	 * Validate the specified {@link ProjectRequest request} and initialize the specified
	 * {@link ProjectDescription description}. Override any attribute of the description
	 * that are managed by this instance.
	 * @param request the request to validate
	 * @param description the description to initialize
	 * @param metadata the metadata instance to use to apply defaults if necessary
	 */
	public void convert(ProjectRequest request, MutableProjectDescription description, InitializrMetadata metadata) {
		validate(request, metadata);
		Version platformVersion = getPlatformVersion(request, metadata);
		List<Dependency> resolvedDependencies = getResolvedDependencies(request, platformVersion, metadata);
		List<Demo> resolvedDemos = getResolvedDemos(request, metadata);
		validateDependencyRange(platformVersion, resolvedDependencies);

		description.setApplicationName(request.getApplicationName());
		description.setArtifactId(request.getArtifactId());
		description.setBaseDirectory(request.getBaseDir());
		description.setBuildSystem(getBuildSystem(request, metadata));
		description.setDescription(request.getDescription());
		description.setGroupId(request.getGroupId());
		description.setLanguage(Language.forId(request.getLanguage(), request.getJavaVersion()));
		description.setName(request.getName());
		description.setPackageName(request.getPackageName());
		description.setPackaging(Packaging.forId(request.getPackaging()));
		description.setPlatformVersion(platformVersion);
		description.setVersion(request.getVersion());
		description.setArchitecture(Architecture.forId(request.getArchitecture()));
		resolvedDependencies.forEach((dependency) -> description.addDependency(dependency.getId(),
				MetadataBuildItemMapper.toDependency(dependency)));
		resolvedDemos.forEach((demo) -> description.addDemo(demo.getId(),
				MetadataDemoMapper.getDemo(demo, getResolvedTemplate(request, platformVersion, metadata))));
	}

	private Function<String, Template> getResolvedTemplate(ProjectRequest request, Version platformVersion,
			InitializrMetadata metadata) {
		return (template) -> MetadataTemplateMapper.getTemplate(template,
				metadata.getConfiguration().getEnv().getTemplates().get(template), (denpendency) -> {
					Dependency dependency = metadata.getDependencies().get(denpendency);
					return MetadataBuildItemMapper.toDependency(dependency.resolve(platformVersion));
				}, (type) -> {
					Module module = metadata.getArchitectures().getModule(request.getArchitecture(), type);
					return (module != null) ? request.getName() + "-" + module.getName() : null;
				});
	}

	private void validate(ProjectRequest request, InitializrMetadata metadata) {
		validatePlatformVersion(request, metadata);
		validateType(request.getType(), metadata);
		validateLanguage(request.getLanguage(), metadata);
		validatePackaging(request.getPackaging(), metadata);
		validateArchitecture(request.getArchitecture(), metadata);
		validateDependencies(request, metadata);
	}

	private void validatePlatformVersion(ProjectRequest request, InitializrMetadata metadata) {
		Version platformVersion = Version.safeParse(request.getBootVersion());
		Platform platform = metadata.getConfiguration().getEnv().getPlatform();
		if (platformVersion != null && !platform.isCompatibleVersion(platformVersion)) {
			throw new InvalidProjectRequestException("Invalid Spring Boot version '" + platformVersion
					+ "', Spring Boot compatibility range is " + platform.determineCompatibilityRangeRequirement());
		}
	}

	private void validateType(String type, InitializrMetadata metadata) {
		if (type != null) {
			Type typeFromMetadata = metadata.getTypes().get(type);
			if (typeFromMetadata == null) {
				throw new InvalidProjectRequestException("Unknown type '" + type + "' check project metadata");
			}
			if (!typeFromMetadata.getTags().containsKey("build")) {
				throw new InvalidProjectRequestException(
						"Invalid type '" + type + "' (missing build tag) check project metadata");
			}
		}
	}

	private void validateLanguage(String language, InitializrMetadata metadata) {
		if (language != null) {
			DefaultMetadataElement languageFromMetadata = metadata.getLanguages().get(language);
			if (languageFromMetadata == null) {
				throw new InvalidProjectRequestException("Unknown language '" + language + "' check project metadata");
			}
		}
	}

	private void validatePackaging(String packaging, InitializrMetadata metadata) {
		if (packaging != null) {
			DefaultMetadataElement packagingFromMetadata = metadata.getPackagings().get(packaging);
			if (packagingFromMetadata == null) {
				throw new InvalidProjectRequestException(
						"Unknown packaging '" + packaging + "' check project metadata");
			}
		}
	}

	private void validateDependencies(ProjectRequest request, InitializrMetadata metadata) {
		List<String> dependencies = request.getDependencies();
		dependencies.forEach((dep) -> {
			Dependency dependency = metadata.getDependencies().get(dep);
			if (dependency == null) {
				throw new InvalidProjectRequestException("Unknown dependency '" + dep + "' check project metadata");
			}
		});
	}

	private void validateArchitecture(String architecture, InitializrMetadata metadata) {
		ArchitectureGroup architectureGroup = metadata.getArchitectures().getGroup(architecture);
		if (architectureGroup == null) {
			throw new InvalidProjectRequestException(
					"Unknown architecture '" + architecture + "' check project metadata");
		}
	}

	private void validateDependencyRange(Version platformVersion, List<Dependency> resolvedDependencies) {
		resolvedDependencies.forEach((dep) -> {
			if (!dep.match(platformVersion)) {
				throw new InvalidProjectRequestException(
						"Dependency '" + dep.getId() + "' is not compatible " + "with Spring Boot " + platformVersion);
			}
		});
	}

	private BuildSystem getBuildSystem(ProjectRequest request, InitializrMetadata metadata) {
		Type typeFromMetadata = metadata.getTypes().get(request.getType());
		return BuildSystem.forId(typeFromMetadata.getTags().get("build"));
	}

	private Version getPlatformVersion(ProjectRequest request, InitializrMetadata metadata) {
		String versionText = (request.getBootVersion() != null) ? request.getBootVersion()
				: metadata.getBootVersions().getDefault().getId();
		Version version = Version.parse(versionText);
		return this.platformVersionTransformer.transform(version, metadata);
	}

	private List<Dependency> getResolvedDependencies(ProjectRequest request, Version platformVersion,
			InitializrMetadata metadata) {
		List<String> depIds = request.getDependencies();
		return depIds.stream().map((it) -> {
			Dependency dependency = metadata.getDependencies().get(it);
			return dependency.resolve(platformVersion);
		}).collect(Collectors.toList());
	}

	private List<Demo> getResolvedDemos(ProjectRequest request, InitializrMetadata metadata) {
		return request.getDemos().stream().map((demo) -> metadata.getDemos().get(demo)).collect(Collectors.toList());
	}

}
