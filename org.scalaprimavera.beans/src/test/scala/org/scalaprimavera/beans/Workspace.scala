/*
 * Copyright 2009 - 2011 Cobalto Labs SAS
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.scalaprimavera.beans

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.springframework.test.context.ContextConfiguration
import java.{util => ju}
import org.testng.annotations.Test
import org.testng.Assert
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}


/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 10/10/11
 * Time: 0:44
 */

@ContextConfiguration
class Workspace extends AbstractTestNGSpringContextTests {

  @Autowired
  var user: org.scalaprimavera.beans.User = _



  @Test
  def test() {
    Assert.assertEquals(2, user.getAges().size())
  }
}