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

package org.scalaprimavera.beans.factory.config

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.springframework.test.context.ContextConfiguration
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import org.testng.annotations.Test
import org.scalatest.testng.TestNGSuite
import org.scalatest.matchers.ShouldMatchers
import org.scalaprimavera.beans.User

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 17/10/11
 * Time: 11:22
 */

@ContextConfiguration
class ScalaListFactoryBeanTest extends AbstractTestNGSpringContextTests with TestNGSuite with ShouldMatchers {

  @Autowired
  @Qualifier("scalaStringsList")
  var scalaStringsList: List[String] = _

  @Autowired
  @Qualifier("scalaIntList")
  var scalaIntList: List[Int] = _

  @Autowired
  @Qualifier("scalaUserList")
  var scalaUserList: List[User] = _

  @Test
  def testScalaStringList() {
    scalaStringsList should have size(2)
    scalaStringsList(0) should be("foo")
  }

  @Test
  def testScalaIntList() {
    scalaIntList should have size(2)
    scalaIntList(0) should be(123)
  }

  @Test
  def testScalaUserList(){
    scalaUserList(0) should have(
      'name("John")
    )
  }

}