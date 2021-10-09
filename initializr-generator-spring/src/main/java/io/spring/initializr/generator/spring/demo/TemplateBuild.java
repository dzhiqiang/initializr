/*
 * Copyright 2012-2021 the original author or authors.
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

package io.spring.initializr.generator.spring.demo;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import io.spring.initializr.generator.demo.Template;
import io.spring.initializr.generator.io.template.MustacheTemplateRenderer;
import io.spring.initializr.generator.project.ProjectDescription;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

public class TemplateBuild {

	private final String resoureRoot;

	private final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

	private final MustacheTemplateRenderer templateRenderer;

	private final Supplier<Map<String, Object>> modelFactory;

	List<TemplateCode> templateCodes = new ArrayList<>();

	public List<TemplateCode> getTemplateCodes() {
		return this.templateCodes;
	}

	public TemplateBuild(String resoureRoot, Supplier<Map<String, Object>> modelFactory) {
		this.resoureRoot = resoureRoot;
		this.templateRenderer = new MustacheTemplateRenderer(resoureRoot);
		this.modelFactory = modelFactory;
	}

	public void setTemplateCodes(List<TemplateCode> templateCodes) {
		this.templateCodes = templateCodes;
	}

	public void write(Path projectRoot) throws IOException {
		for (TemplateCode templateCode : this.getTemplateCodes()) {
			templateCode.write(projectRoot);
		}
	}

	public void addTemplateCode(ProjectDescription description, Template template) {
		try {
			Resource root = this.resolver.getResource(this.resoureRoot);
			Resource[] resources = this.resolver.getResources(this.resoureRoot + "/" + template.getName() + "/**");
			for (Resource resource : resources) {
				if (resource.isReadable()) {
					String[] filename = { extractFileName(root.getURI(), resource.getURI()) };
					this.templateCodes.add(new TemplateCode(((projectRoot) -> {
						String moduleRoot = template.getModule();
						if (StringUtils.hasText(moduleRoot)) {
							projectRoot = projectRoot.resolve(moduleRoot);
						}
						projectRoot = projectRoot.resolve("src/main");
						if (io.spring.initializr.metadata.Template.TEMPLATE_TYPE_CODE
								.equals(template.getTemplateType())) {
							projectRoot = projectRoot.resolve(description.getLanguage().id());
							projectRoot = projectRoot.resolve(description.getPackageName().replaceAll("\\.", "/"));
						}
						projectRoot = projectRoot.resolve(filename[0]);
						return projectRoot;
					}), filename[0], this.templateRenderer, this.modelFactory));
				}
			}
		}
		catch (IOException ex) {
			throw new IllegalStateException("Cannot load template " + this.resoureRoot + template.getName(), ex);
		}
	}

	private String extractFileName(URI root, URI resource) {
		String candidate = resource.toString().substring(root.toString().length(),
				resource.toString().indexOf(".mustache"));
		return StringUtils.trimLeadingCharacter(candidate, '/');
	}

}
