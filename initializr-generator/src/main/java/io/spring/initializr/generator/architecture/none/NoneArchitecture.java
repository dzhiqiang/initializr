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

package io.spring.initializr.generator.architecture.none;

import io.spring.initializr.generator.architecture.Architecture;

/**
 * None {@link Architecture}.
 *
 * @author Duan ZhiQiang
 */
public class NoneArchitecture implements Architecture {

	/**
	 * None {@link Architecture} identifier.
	 */
	public static final String ID = "none";

	/**
	 * Not Multi_Module.
	 */
	public static final boolean MULTI_MODULE = false;

	@Override
	public String id() {
		return ID;
	}

	@Override
	public boolean isMultiModule() {
		return MULTI_MODULE;
	}

	@Override
	public String toString() {
		return id();
	}

}
