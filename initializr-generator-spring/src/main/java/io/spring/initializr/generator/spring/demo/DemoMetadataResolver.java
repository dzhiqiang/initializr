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

package io.spring.initializr.generator.spring.demo;

import io.spring.initializr.metadata.Demo;
import io.spring.initializr.metadata.InitializrMetadata;
import io.spring.initializr.metadata.Template;

public class DemoMetadataResolver {

	private InitializrMetadata metadata;

	public DemoMetadataResolver(InitializrMetadata metadata) {
		this.metadata = metadata;
	}

	public Demo resolveDemo(String id) {
		return this.metadata.getDemos().get(id);
	}

	public Template resolveTemplate(String name) {
		return this.metadata.getConfiguration().getEnv().getTemplates().get(name);
	}

}
