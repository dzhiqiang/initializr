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

public class Demo {

	private String id;

	private String name;

	private String description;

	private Map<String, Template> template = new HashMap<>();

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

	public Map<String, Template> getTemplate() {
		return this.template;
	}

	public void setTemplate(Map<String, Template> template) {
		this.template = template;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static Demo create(String id, String name, String description) {
		Demo demo = new Demo();
		demo.setDescription(description);
		demo.setId(id);
		demo.setName(name);
		return demo;
	}

	public void addTemplate(Template template) {
		this.template.put(template.getId(), template);
	}

	public static class DemoBuilder {

		private String id;

		private String name;

		private String description;

		private Map<String, Template> template = new HashMap<>();

		public DemoBuilder(String id, String name, String description) {
			this.id = id;
			this.name = name;
			this.description = description;
		}

		public DemoBuilder templates(Map<String, Template> template) {
			this.template = template;
			return this;
		}

		public DemoBuilder addTemplate(Template template) {
			this.template.put(template.getId(), template);
			return this;
		}

		public Demo build() {
			Demo demo = new Demo();
			demo.setId(this.id);
			demo.setName(this.name);
			demo.setDescription(this.description);
			demo.setTemplate(this.template);
			return demo;
		}

	}

}
