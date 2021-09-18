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

package io.spring.initializr.generator.spring.architecture;

import java.util.List;

import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.Module;

public class ArchitectureMetadataResolver {

	private final InitializrMetadata metadata;

	public ArchitectureMetadataResolver(InitializrMetadata metadata) {
		this.metadata = metadata;
	}

	public List<Module> mudules(String id) {
		return this.metadata.getArchitectures().getGroup(id).getContent();
	}

	public Module mudule(String id, String type) {
		return this.metadata.getArchitectures().getModule(id, type);
	}

}
