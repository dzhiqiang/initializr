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

package io.spring.initializr.generator.architecture;

import java.util.ArrayList;
import java.util.List;

public class Module {

	private String name;

	private List<String> dependencies = new ArrayList<>();

	public Module(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getDependencies() {
		return this.dependencies;
	}

	public void setDependencies(List<String> dependencies) {
		this.dependencies = dependencies;
	}

	public void addDependency(String name) {
		this.dependencies.add(name);
	}

	public static class ModuleBuild {

		private String name;

		private List<String> dependencies;

		public ModuleBuild(String name) {
			this.name = name;
		}

		public ModuleBuild name(String name) {
			this.name = name;
			return this;
		}

		public ModuleBuild dependency(List<String> dependencies) {
			this.dependencies = dependencies;
			return this;
		}

		public Module build() {
			Module module = new Module(this.name);
			module.setDependencies(this.dependencies);
			return module;
		}

	}

}
