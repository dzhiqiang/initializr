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

package io.spring.initializr.generator.buildsystem.maven;

import java.util.ArrayList;
import java.util.List;

public class MavenModuleContainer {

	private String id;

	private MavenBuild parent;

	private List<MavenModule> modules = new ArrayList<>();

	public MavenModuleContainer(MavenBuild parent) {
		this.parent = parent;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MavenBuild getParent() {
		return this.parent;
	}

	public void setParent(MavenBuild parent) {
		this.parent = parent;
	}

	public List<MavenModule> getModules() {
		return this.modules;
	}

	public void setModules(List<MavenModule> modules) {
		this.modules = modules;
	}

	public void addModuleMavenBuild(MavenModule mavenBuild) {
		this.modules.add(mavenBuild);
	}

	public boolean isEmpty() {
		return this.modules.isEmpty();
	}

	public MavenModule getMavenModule(String name) {
		return this.modules.stream().filter((module) -> module.getName().equals(name)).findFirst().orElse(null);
	}

}
