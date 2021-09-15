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

public class ArchitectureGroupCapability extends ServiceCapability<List<ArchitectureGroup>> {

	final List<ArchitectureGroup> content = new ArrayList<>();

	@JsonIgnore
	private final Map<String, ArchitectureGroup> contentMap = new HashMap<>();

	public Module getModule(String id, String type) {
		ArchitectureGroup group = getGroup(id);
		return group.getModuleByType(type);
	}

	public ArchitectureGroup getGroup(String id) {
		ArchitectureGroup group = this.contentMap.get(id);
		if (group == null) {
			group = this.contentMap.get("none");
		}
		return group;
	}

	public ArchitectureGroupCapability() {
		super("architectureGroup", ServiceCapabilityType.SINGLE_SELECT, "Project Architecture", "architecture");
	}

	@Override
	public List<ArchitectureGroup> getContent() {
		return this.content;
	}

	@Override
	public void merge(List<ArchitectureGroup> otherContent) {
		otherContent.forEach((group) -> {
			if (this.content.stream().noneMatch((it) -> group.getId() != null && group.getId().equals(it.getId()))) {
				this.content.add(group);
			}
		});
		index();
	}

	private void index() {
		this.contentMap.clear();
		this.content.stream().forEach((group) -> {
			group.clear();
			group.index();
			indexedArchitecture(group.getId(), group);
		});
	}

	private void indexedArchitecture(String id, ArchitectureGroup group) {
		ArchitectureGroup existing = this.contentMap.get(id);
		if (existing != null) {
			throw new IllegalArgumentException(
					"Could not register " + group + " another group " + "has also the '" + id + "' id " + existing);
		}
		this.contentMap.put(id, group);
	}

	public void validate() {
		this.content.forEach((group) -> group.validate());
	}

}
