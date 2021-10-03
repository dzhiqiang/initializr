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
import java.util.Arrays;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * 子模块.
 *
 * @author Duan Zhiqiang
 */
public class Module {

	private String name;

	private String type;

	private List<String> dependencies = new ArrayList<>();

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getDependencies() {
		return this.dependencies;
	}

	public void setDependencies(List<String> dependencies) {
		this.dependencies = dependencies;
	}

	@Override
	public String toString() {
		return "Module{" + "name='" + this.name + '\'' + ", type='" + this.type + '\'' + ", dependencies="
				+ this.dependencies + '}';
	}

	public static Module create(String name, String type) {
		Module module = new Module();
		module.setName(name);
		module.setType(type);
		return module;
	}

	public static Module createWithDependencies(String name, String type, String... dependencies) {
		Module module = new Module();
		module.setName(name);
		module.setType(type);
		module.getDependencies().addAll(Arrays.asList(dependencies));
		return module;
	}

	public void validate() {

		if (!StringUtils.hasText(this.name)) {
			throw new InvalidInitializrMetadataException("Module requires name");
		}

	}

}
