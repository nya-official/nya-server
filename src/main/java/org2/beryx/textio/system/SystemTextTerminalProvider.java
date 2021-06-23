/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org2.beryx.textio.system;

import org2.beryx.textio.TextTerminalProvider;

/**
 * It provides a {@link SystemTextTerminal}.
 */
public class SystemTextTerminalProvider implements TextTerminalProvider {
    public SystemTextTerminal getTextTerminal() {
        return new SystemTextTerminal();
    }

    @Override
    public String toString() {
        return "System terminal";
    }
}
