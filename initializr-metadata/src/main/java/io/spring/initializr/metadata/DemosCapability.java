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

public class DemosCapability extends ServiceCapability<List<Demo>> {

	private List<Demo> content = new ArrayList<>();

	@JsonIgnore
	private Map<String, Demo> indexedDemos = new HashMap<>();

	public DemosCapability() {
		super("demo", ServiceCapabilityType.HIERARCHICAL_MULTI_SELECT, "Demo", "project demo");
	}

	public Demo get(String id) {
		return this.indexedDemos.get(id);
	}

	@Override
	public List<Demo> getContent() {
		return this.content;
	}

	@Override
	public void merge(List<Demo> otherContent) {
		otherContent.forEach((demo) -> {
			if (get(demo.getId()) == null) {
				this.content.add(demo);
			}
		});
		index();
	}

	private void index() {
		this.indexedDemos.clear();
		this.content.forEach((demo) -> indexedDemos(demo.getId(), demo));
	}

	private void indexedDemos(String id, Demo demo) {
		Demo existing = this.indexedDemos.get(id);
		if (existing != null) {
			throw new IllegalArgumentException(
					"Could not register " + demo + " another demo " + "has also the '" + id + "' id " + existing);
		}
		this.indexedDemos.put(id, demo);
	}

	public void validate() {
		this.content.forEach((demo) -> demo.validate());
	}

}
