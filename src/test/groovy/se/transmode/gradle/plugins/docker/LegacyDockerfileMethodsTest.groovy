/**
 * Copyright 2014 Transmode AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.transmode.gradle.plugins.docker
import org.junit.Before;
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import se.transmode.gradle.plugins.docker.image.Dockerfile

import static org.hamcrest.Matchers.*
import static org.junit.Assert.assertThat

class LegacyDockerfileMethodsTest {

  @Rule
  public TemporaryFolder testFolder = new TemporaryFolder()
  LegacyDockerfileMethods methods

  @Before
  public void setup() {
      methods = new LegacyDockerfileMethods()
      methods.dockerfile = new Dockerfile(new File("contextDir"))
  }

  @Test
  public void testHealthCheck() {
      methods.healthCheck("curl localhost || exit 1")
      assertThat methods.dockerfile.instructions[0], equalTo('HEALTHCHECK CMD curl localhost || exit 1')
  }

  @Test
  public void testHealthCheckWithTimeoutOptions() {
      methods.healthCheck(30, 30, 3, "curl localhost || exit 1")
      assertThat methods.dockerfile.instructions[0], equalTo('HEALTHCHECK --interval=30s --timeout=30s --retries=3 CMD curl localhost || exit 1')
  }

  @Test
  public void testDisableHealthCheck() {
      methods.disableHealthCheck()
      assertThat methods.dockerfile.instructions[0], equalTo('HEALTHCHECK NONE')
  }
}
