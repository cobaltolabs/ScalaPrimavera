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

package org.scalaprimavera.jdbc.core.simple.dsl

import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests
import org.scalatest.testng.TestNGSuite
import org.scalatest.matchers.ShouldMatchers
import org.springframework.beans.factory.annotation.Autowired
import org.testng.annotations.Test
import org.springframework.dao.EmptyResultDataAccessException
import org.scalaprimavera.jdbc.TestJdbcHelper._
import org.scalaprimavera.jdbc.TestBean

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 7/11/11
 * Time: 19:53
 */

@ContextConfiguration
class DSLJdbcTemplateTest extends AbstractTransactionalTestNGSpringContextTests with TestNGSuite with ShouldMatchers {

  @Autowired
  var template: DSLJdbcOperations = _

  private def checkCount(count: Int) {
    template.noDSL.queryForInt(selectCount) should be(count)
  }


  @Test
  def testQueryForInt() {
    template queryForInt selectIdWithNamedParams parameters "description" -> "python" should be(1)

    intercept[EmptyResultDataAccessException] {
      template queryForInt selectIdWithNamedParams parameters "description" -> "cobol"
    }
  }

  @Test
  def testQueryForOptionInt() {
    template.queryForOptionInt(selectIdWithNamedParams).parameters("description" -> "python").get should be(1)
    template.queryForOptionInt(selectIdWithNamedParams).parameters("description" -> "cobol") should be(None)
  }

  @Test
  def testQueryForLong() {

    template queryForLong selectIdWithNamedParams parameters "description" -> "python" should be(1)
    intercept[EmptyResultDataAccessException] {
      template queryForLong selectIdWithNamedParams parameters "description" -> "cobol"
    }
  }

  @Test
  def testQueryForOptionLong() {
    template.queryForOptionLong(selectIdWithNamedParams).parameters("description" -> "python").get should be(1)
    template.queryForOptionLong(selectIdWithNamedParams).parameters("description" -> "cobol") should be(None)
  }

  @Test
  def testQueryForObject() {

    template queryForObject selectDescriptionWithNamedParams parameters "id" -> 1 as classOf[String] should be("python")

    template queryForObject selectWithNamedParams parameters "id" -> 1 mappedWith mapperFunction should have(
      'id(1),
      'description("python")
    )

    intercept[EmptyResultDataAccessException] {
      template queryForObject selectDescriptionWithNamedParams parameters "id" -> -1 as classOf[String]
    }

    intercept[EmptyResultDataAccessException] {
      template queryForObject selectDescriptionWithNamedParams parameters "id" -> -1 mappedWith mapperFunction
    }
  }

  @Test
  def testQueryForOption() {
    template.queryForOption(selectDescriptionWithNamedParams)
      .parameters("id" -> 1)
      .as(classOf[String])
      .get should be("python")

    template.queryForOption(selectWithNamedParams)
      .parameters("id" -> 1)
      .mappedWith(mapperFunction)
      .get should have(
      'id(1),
      'description("python")
    )

    template.queryForOption(selectDescriptionWithNamedParams)
      .parameters("id" -> -1)
      .as(classOf[String]) should be(None)

    template.queryForOption(selectWithNamedParams)
      .parameters("id" -> -1)
      .mappedWith(mapperFunction) should be(None)
  }

  @Test
  def testQuery() {
    template query selectLessThan parameters "id" -> 4 mappedWith mapperFunction should have size 3
  }

  @Test
  def testQueryForMap() {
    //Map
    template.queryForMap(selectWithNamedParams)
      .parameters("id" -> 1)("DESCRIPTION") should be("python")

    intercept[EmptyResultDataAccessException] {
      template queryForMap selectWithNamedParams parameters "id" -> -1
    }
  }

  @Test
  def testQueryForOptionMap() {
    //Map
    template.queryForOptionMap(selectWithNamedParams).parameters("id" -> 1).get("DESCRIPTION") should be("python")

    template.queryForOptionMap(selectWithNamedParams).parameters("id" -> -1) should be(None)
  }

  @Test
  def testQueryForList() {
    template queryForList selectLessThan parameters "id" -> 4 should have size 3
  }

  @Test
  def testUpdate() {
    template update insertWithNamedParams parameters "description" -> "haskell" should be(1)

    val bean = new TestBean
    bean.description = "clojure"
    template update insertWithNamedParams withThis bean should be(1)

    checkCount(7)
  }

}