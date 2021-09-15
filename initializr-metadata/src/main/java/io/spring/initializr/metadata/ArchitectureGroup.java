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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.util.StringUtils;

/**
 * 架构.
 *
 * @author Duan Zhiqiang
 */
public class ArchitectureGroup {

	private String name;

	private String id;

	private List<Module> content = new ArrayList<>();

	private List<String> demos = new ArrayList<>();

	@JsonIgnore
	private Map<String, Module> typeModule = new HashMap<>();

	@JsonIgnore
	private Map<String, Module> nameModule = new HashMap<>();

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

	public List<Module> getContent() {
		return this.content;
	}

	public void setContent(List<Module> content) {
		this.content = content;
	}

	public List<String> getDemos() {
		return this.demos;
	}

	public void setDemos(List<String> demos) {
		this.demos = demos;
	}

	public Module getModuleByType(String type) {
		return this.typeModule.get(type);
	}

	public Module getModuleByName(String name) {
		return this.nameModule.get(name);
	}

	public void clear() {
		this.typeModule.clear();
		this.nameModule.clear();
	}

	public void index() {
		for (Module module : this.content) {
			indexedTypeModule(module.getType(), module);
			indexedNameModule(module.getName(), module);
		}
	}

	private void indexedTypeModule(String type, Module module) {
		if (StringUtils.hasText(type)) {
			Module existing = this.typeModule.get(type);
			if (existing != null) {
				throw new IllegalArgumentException("Could not register " + module + " another module "
						+ "has also the '" + type + "' type " + existing);
			}
			this.typeModule.put(type, module);
		}
	}

	private void indexedNameModule(String name, Module module) {
		Module existing = this.nameModule.get(name);
		if (existing != null) {
			throw new IllegalArgumentException("Could not register " + module + " another module " + "has also the '"
					+ name + "' name " + existing);
		}
		this.nameModule.put(name, module);
	}

	@Override
	public String toString() {
		return "ArchitectureGroup{" + "name='" + this.name + '\'' + ", id='" + this.id + '\'' + '}';
	}

	public static ArchitectureGroup withId(String id, String name) {
		ArchitectureGroup architectureGroup = new ArchitectureGroup();
		architectureGroup.setId(id);
		architectureGroup.setName(name);
		return architectureGroup;
	}

	public void validate() {

		if (!StringUtils.hasText(this.name)) {
			throw new InvalidInitializrMetadataException("ArchitectureGroup requires name");
		}
		if (!StringUtils.hasText(this.id)) {
			throw new InvalidInitializrMetadataException("ArchitectureGroup requires id");
		}

		this.content.forEach((module) -> module.validate());

	}

}
