/*
 * Copyright 2012-2019 the original author or authors.
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

package io.spring.initializr.generator.spring.build.maven;

import io.spring.initializr.generator.architecture.Architecture;
import io.spring.initializr.generator.architecture.layered.LayeredArchitecture;
import io.spring.initializr.generator.buildsystem.maven.MavenBuild;
import io.spring.initializr.generator.buildsystem.maven.MavenModule;
import io.spring.initializr.generator.project.MutableProjectDescription;
import io.spring.initializr.generator.spring.architecture.ArchitectureMetadataResolver;
import io.spring.initializr.generator.test.InitializrMetadataTestBuilder;
import io.spring.initializr.metadata.InitializrMetadata;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArchitectureMavenBuildCustomizerTests {

	@Test
	void testModulePomFile() {
		InitializrMetadata metadata = InitializrMetadataTestBuilder.withDefaults().build();
		MutableProjectDescription description = initializeDescription();
		MavenBuild build = customizeBuild(metadata, description);
		assertThat(build.modules().getId()).isEqualTo(LayeredArchitecture.ID);
		assertThat(build.modules().getModules()).hasSize(3);

		MavenModule startMavenModule = build.modules().getMavenModule("start");
		assertThat(startMavenModule).isNotNull();
		assertThatMavenModule(startMavenModule, "start");
		assertThatParent(startMavenModule, "start");
		assertThat(startMavenModule.getModuleMavenBuild().dependencies().items()).hasSize(2);
		assertThatDependencies(startMavenModule, "api", "service");

		MavenModule apiMavenModule = build.modules().getMavenModule("api");
		assertThat(apiMavenModule).isNotNull();
		assertThatMavenModule(apiMavenModule, "api");
		assertThatParent(apiMavenModule, "api");
		assertThat(apiMavenModule.getModuleMavenBuild().dependencies().items()).isEmpty();

		MavenModule serviceMavenModule = build.modules().getMavenModule("service");
		assertThat(serviceMavenModule).isNotNull();
		assertThatMavenModule(serviceMavenModule, "service");
		assertThatParent(serviceMavenModule, "service");
		assertThat(serviceMavenModule.getModuleMavenBuild().dependencies().items()).hasSize(1);
		assertThatDependencies(serviceMavenModule, "api");
	}

	private void assertThatDependencies(MavenModule mavenModule, String... dependencies) {
		for (String dependency : dependencies) {
			assertThat(mavenModule.getModuleMavenBuild().dependencies().get("test-" + dependency)).isNotNull();
			assertThat(mavenModule.getModuleMavenBuild().dependencies().get("test-" + dependency).getGroupId())
					.isEqualTo("com.dzq");
			assertThat(mavenModule.getModuleMavenBuild().dependencies().get("test-" + dependency).getArtifactId())
					.isEqualTo("test-" + dependency);
			assertThat(
					mavenModule.getModuleMavenBuild().dependencies().get("test-" + dependency).getVersion().getValue())
							.isEqualTo("${project.version}");
		}
	}

	private void assertThatParent(MavenModule mavenModule, String name) {
		assertThat(mavenModule.getModuleMavenBuild().getSettings().getParent().getArtifactId()).isEqualTo("test");
		assertThat(mavenModule.getModuleMavenBuild().getSettings().getParent().getGroupId()).isEqualTo("com.dzq");
		assertThat(mavenModule.getModuleMavenBuild().getSettings().getParent().getVersion())
				.isEqualTo("0.0.1-SNAPSHOT");
	}

	private void assertThatMavenModule(MavenModule mavenModule, String name) {
		assertThat(mavenModule.getArtifactId()).isEqualTo("test-" + name);
		assertThat(mavenModule.getModuleName()).isEqualTo("demo-" + name);
		assertThat(mavenModule.getModuleMavenBuild().getSettings().getGroup()).isEqualTo("com.dzq");
		assertThat(mavenModule.getModuleMavenBuild().getSettings().getArtifact()).isEqualTo("test-" + name);
	}

	private MavenBuild customizeBuild(InitializrMetadata metadata, MutableProjectDescription description) {
		MavenBuild mavenBuild = new MavenBuild();
		mavenBuild.settings().coordinates(description.getGroupId(), description.getArtifactId())
				.version(description.getVersion()).name(description.getName());
		ArchitectureMetadataResolver resolver = new ArchitectureMetadataResolver(metadata);
		ArchitectureMavenBuildCustomizer customizer = new ArchitectureMavenBuildCustomizer(description, resolver);
		customizer.customize(mavenBuild);
		return mavenBuild;
	}

	private MutableProjectDescription initializeDescription() {
		MutableProjectDescription description = new MutableProjectDescription();
		description.setArchitecture(Architecture.forId(LayeredArchitecture.ID));
		description.setName("demo");
		description.setPackageName("com.dzq");
		description.setGroupId("com.dzq");
		description.setArtifactId("test");
		description.setVersion("0.0.1-SNAPSHOT");
		return description;
	}

}
