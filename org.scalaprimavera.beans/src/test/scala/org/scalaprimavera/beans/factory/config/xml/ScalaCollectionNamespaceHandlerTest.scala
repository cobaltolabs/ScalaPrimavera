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

package org.scalaprimavera.beans.factory.config.xml

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.scalatest.testng.TestNGSuite
import org.scalatest.matchers.ShouldMatchers
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import org.testng.annotations.Test
import org.springframework.test.context.ContextConfiguration

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 17/10/11
 * Time: 15:03
 */
@ContextConfiguration
class ScalaCollectionNamespaceHandlerTest extends AbstractTestNGSpringContextTests with TestNGSuite with ShouldMatchers {
  @Autowired
  @Qualifier("scalaStringsList")
  var scalaStringsList: List[String] = _

  @Autowired
  @Qualifier("scalaIntList")
  var scalaIntList: List[Int] = _

  @Autowired
  @Qualifier("stringStringMap")
  var stringStringMap: Map[String, String] = _

  @Autowired
  @Qualifier("stringIntMap")
  var stringIntMap: Map[String, Int] = _

  @Test
  def stringStringMapTest() {
    stringStringMap should have size (1)
    stringStringMap("foo") should be("bar")
  }

  @Test
  def stringIntMapTest() {
    stringIntMap should have size (2)
    stringIntMap("foo") should be(123)
    stringIntMap("bar") should be(456)
  }

  @Test
  def testScalaStringList() {
    scalaStringsList should have size (2)
    scalaStringsList(0) should be("foo")
  }

  @Test
  def testScalaIntList() {
    scalaIntList should have size (2)
    scalaIntList(0) should be(123)
  }
}