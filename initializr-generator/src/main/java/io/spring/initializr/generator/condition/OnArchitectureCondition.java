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

package io.spring.initializr.generator.condition;

import io.spring.initializr.generator.architecture.Architecture;
import io.spring.initializr.generator.project.ProjectDescription;

import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * {@link ProjectGenerationCondition Condition} implementation for
 * {@link ConditionalOnArchitecture}.
 *
 * @author Duan Zhiqiang
 */
class OnArchitectureCondition extends ProjectGenerationCondition {

	@Override
	protected boolean matches(ProjectDescription description, ConditionContext context,
			AnnotatedTypeMetadata metadata) {
		if (description.getArchitecture() == null) {
			return false;
		}
		String architectureId = (String) metadata.getAllAnnotationAttributes(ConditionalOnArchitecture.class.getName())
				.getFirst("value");
		Architecture architecture = Architecture.forId(architectureId);
		return description.getArchitecture().id().equals(architecture.id());
	}

}
