/*
 * Copyright 2017 the original author or authors.
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
package org2.beryx.textio.web;

/**
 * The interface implementetd by a web server that allows clients to access the {@link DataApi}.
 */
public interface DataServer {
    public DataServer withPathForInitData(String pathForInitData);
    public DataServer withPathForGetData(String pathForGetData);
    public DataServer withPathForPostInput(String pathForPostInput);

    public DataServer withPort(int portNumber);
    public int getPort();

    public void init();
}
