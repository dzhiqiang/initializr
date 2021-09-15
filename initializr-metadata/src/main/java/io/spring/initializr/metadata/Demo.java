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

package io.spring.initializr.metadata;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * 示例类.
 *
 * @author Duan zhiqiang
 */
public class Demo {

	private String name;

	private String id;

	private String description;

	private List<String> templates = new ArrayList<>();

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getTemplates() {
		return this.templates;
	}

	public void setTemplates(List<String> templates) {
		this.templates = templates;
	}

	@Override
	public String toString() {
		return "Demo{" + "name='" + this.name + '\'' + ", id='" + this.id + '\'' + ", description='" + this.description
				+ '\'' + '}';
	}

	public static Demo withId(String id, String name, String description) {
		Demo demo = new Demo();
		demo.setId(id);
		demo.setName(name);
		demo.setDescription(description);
		return demo;
	}

	public void validate() {

		if (!StringUtils.hasText(this.name)) {
			throw new InvalidInitializrMetadataException("Demo requires name");
		}

		if (!StringUtils.hasText(this.id)) {
			throw new InvalidInitializrMetadataException("Demo requires id");
		}

	}

}
