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

package io.spring.initializr.metadata;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArchitectureGroupTests {

	@Test
	void invalidNameNull() {
		ArchitectureGroup group = ArchitectureGroup.withId("test", null);
		Assertions.assertThatExceptionOfType(InvalidInitializrMetadataException.class)
				.isThrownBy(() -> group.validate()).withMessageContaining("ArchitectureGroup requires name");
	}

	@Test
	void invalidNameEmpty() {
		ArchitectureGroup group = ArchitectureGroup.withId("test", "");
		Assertions.assertThatExceptionOfType(InvalidInitializrMetadataException.class)
				.isThrownBy(() -> group.validate()).withMessageContaining("ArchitectureGroup requires name");
	}

	@Test
	void invalidIdNull() {
		ArchitectureGroup group = ArchitectureGroup.withId(null, "test");
		Assertions.assertThatExceptionOfType(InvalidInitializrMetadataException.class)
				.isThrownBy(() -> group.validate()).withMessageContaining("ArchitectureGroup requires id");
	}

	@Test
	void invalidIdEmpty() {
		ArchitectureGroup group = ArchitectureGroup.withId("", "test");
		Assertions.assertThatExceptionOfType(InvalidInitializrMetadataException.class)
				.isThrownBy(() -> group.validate()).withMessageContaining("ArchitectureGroup requires id");
	}

}
