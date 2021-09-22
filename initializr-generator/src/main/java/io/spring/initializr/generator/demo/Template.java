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

package io.spring.initializr.generator.demo;

import java.util.HashMap;
import java.util.Map;

import io.spring.initializr.generator.buildsystem.Dependency;

public class Template {

	private String id;

	private String name;

	private String templateType;

	private String module;

	private Map<String, Dependency> dependencies;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplateType() {
		return this.templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public Map<String, Dependency> getDependencies() {
		return this.dependencies;
	}

	public void setDependencies(Map<String, Dependency> dependencies) {
		this.dependencies = dependencies;
	}

	public static Template create(String id, String name, String templateType, String module) {
		Template template = new Template();
		template.setId(id);
		template.setModule(module);
		template.setName(name);
		template.setTemplateType(templateType);
		return template;
	}

	public static class TemplateBuilder {

		private String id;

		private String name;

		private String templateType;

		private String module;

		private Map<String, Dependency> dependencies = new HashMap<>();

		public TemplateBuilder(String id, String name, String templateType, String module) {
			this.id = id;
			this.name = name;
			this.templateType = templateType;
			this.module = module;
		}

		public TemplateBuilder dependencies(Map<String, Dependency> dependencies) {
			this.dependencies = dependencies;
			return this;
		}

		public TemplateBuilder addDependency(String id, Dependency dependency) {
			this.dependencies.put(id, dependency);
			return this;
		}

		public Template build() {
			Template template = new Template();
			template.setId(this.id);
			template.setName(this.name);
			template.setModule(this.module);
			template.setTemplateType(this.templateType);
			template.setDependencies(this.dependencies);
			return template;
		}

	}

}
