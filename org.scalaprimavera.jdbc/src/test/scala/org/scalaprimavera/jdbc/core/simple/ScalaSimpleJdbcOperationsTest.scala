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

package org.scalaprimavera.jdbc.core.simple

import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests
import org.scalatest.testng.TestNGSuite
import org.scalatest.matchers.ShouldMatchers
import org.springframework.test.context.ContextConfiguration
import org.testng.annotations.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import collection.immutable.Map
import org.scalaprimavera.jdbc.TestBean
import java.sql.ResultSet
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 19/10/11
 * Time: 23:30
 */
@ContextConfiguration
class ScalaSimpleJdbcOperationsTest extends AbstractTransactionalTestNGSpringContextTests with TestNGSuite with ShouldMatchers {

  @Autowired
  var template: ScalaSimpleJdbcOperations = _

  val selectIdWithNamedParams = "select id from test_bean where description = :description"
  val selectId = "select id from test_bean where description = ?"
  val selectDescriptionWithNamedParams = "select description from test_bean where id = :id"
  val selectDescription = "select description from test_bean where id = ?"
  val select = "select * from test_bean"
  val selectLessThan = select + " where id < :id"
  val whereNamedId = " where id = :id"
  val selectWithNamedParams = select + whereNamedId
  val whereId = " where id = ?"
  val selectObject = select + whereId
  val insertWithNamedParams = "insert into test_bean(description) values (:description)"
  val insert = "insert into test_bean(description) values (?)"
  val selectCount = "select count(id) from test_bean"
  val delete = "delete from test_bean "
  val deleteWithNamedParams = delete + whereNamedId
  val deleteObject = delete + whereId


  val mapperFunction = (rs: ResultSet, i: Int) => {
    import rs._
    val bean = new TestBean
    import bean._
    id = getInt("id")
    description = getString("description")
    createDate = getDate("create_date")
    bean
  }

  @Test
  def testGetOperations() {
    template.getSimpleJdbcOperations should not be null
  }

  @Test
  def testQueryForInt() {

    //Map
    template.queryForInt(selectIdWithNamedParams, Map("description" -> "python")) should be(1)
    intercept[EmptyResultDataAccessException] {
      template.queryForInt(selectIdWithNamedParams, Map("description" -> "cobol"))
    }

    //Source
    template.queryForInt(selectIdWithNamedParams, new MapSqlParameterSource("description", "python")) should be(1)
    intercept[EmptyResultDataAccessException] {
      template.queryForInt(selectIdWithNamedParams, new MapSqlParameterSource("description", "cobol"))
    }

    //with params
    template.queryForInt(selectId, "python") should be(1)
    //without params
    template.queryForInt("select id from test_bean where description = 'python'") should be(1)
    intercept[EmptyResultDataAccessException] {
      template.queryForInt(selectId, "cobol")
    }
  }

  @Test
  def testQueryForOptionInt() {
    //Map
    template.queryForOptionInt(selectIdWithNamedParams, Map("description" -> "python")).get should be(1)
    template.queryForOptionInt(selectIdWithNamedParams, Map("description" -> "cobol")) should be(None)
    //Source
    template.queryForOptionInt(selectIdWithNamedParams, new MapSqlParameterSource("description", "python")).get should be(1)
    template.queryForOptionInt(selectIdWithNamedParams, new MapSqlParameterSource("description", "cobol")) should be(None)
    //Any*
    template.queryForOptionInt(selectId, "python").get should be(1)
    template.queryForOptionInt(selectId, "cobol") should be(None)
  }

  @Test
  def testQueryForLong() {
    //map
    template.queryForLong(selectIdWithNamedParams, Map("description" -> "python")) should be(1)
    intercept[EmptyResultDataAccessException] {
      template.queryForLong(selectIdWithNamedParams, Map("description" -> "cobol"))
    }
    //source
    template.queryForLong(selectIdWithNamedParams, new MapSqlParameterSource("description", "python")) should be(1)
    intercept[EmptyResultDataAccessException] {
      template.queryForLong(selectIdWithNamedParams, new MapSqlParameterSource("description", "cobol"))
    }
    //with params
    template.queryForLong(selectId, "python") should be(1)
    //without params
    template.queryForLong("select id from test_bean where description = 'python'") should be(1)
    intercept[EmptyResultDataAccessException] {
      template.queryForLong(selectId, "cobol")
    }
  }

  @Test
  def testQueryForOptionLong() {
    //Map
    template.queryForOptionLong(selectIdWithNamedParams, Map("description" -> "python")).get should be(1)
    template.queryForOptionLong(selectIdWithNamedParams, Map("description" -> "cobol")) should be(None)
    //Source
    template.queryForOptionLong(selectIdWithNamedParams, new MapSqlParameterSource("description", "python")).get should be(1)
    template.queryForOptionLong(selectIdWithNamedParams, new MapSqlParameterSource("description", "cobol")) should be(None)
    //Any*
    template.queryForOptionLong(selectId, "python").get should be(1)
    template.queryForOptionLong(selectId, "cobol") should be(None)
  }


  @Test
  def testQueryForObject() {
    //Map
    template.queryForObject(selectDescriptionWithNamedParams, classOf[String], Map("id" -> 1)) should be("python")

    template.queryForObject(selectWithNamedParams, mapperFunction, Map("id" -> 1)) should have(
      'id(1),
      'description("python")
    )

    intercept[EmptyResultDataAccessException] {
      template.queryForObject(selectDescriptionWithNamedParams, classOf[String], Map("id" -> -1))
    }

    intercept[EmptyResultDataAccessException] {
      template.queryForObject(selectDescriptionWithNamedParams, mapperFunction, Map("id" -> -1))
    }
    //Source
    template.queryForObject(selectDescriptionWithNamedParams, classOf[String], new MapSqlParameterSource("id", 1)) should be("python")

    template.queryForObject(selectWithNamedParams, mapperFunction, new MapSqlParameterSource("id", 1)) should have(
      'id(1),
      'description("python")
    )

    intercept[EmptyResultDataAccessException] {
      template.queryForObject(selectDescriptionWithNamedParams, classOf[String], new MapSqlParameterSource("id", -1))
    }

    intercept[EmptyResultDataAccessException] {
      template.queryForObject(selectDescriptionWithNamedParams, mapperFunction, new MapSqlParameterSource("id", -1))
    }
    //Any*
    template.queryForObject(selectDescription, classOf[String], 1) should be("python")

    template.queryForObject(selectObject, mapperFunction, 1) should have(
      'id(1),
      'description("python")
    )

    intercept[EmptyResultDataAccessException] {
      template.queryForObject(selectDescription, classOf[String], -1)
    }

    intercept[EmptyResultDataAccessException] {
      template.queryForObject(selectObject, mapperFunction, -1)
    }
  }

  @Test
  def testQueryForOption() {
    //Map
    template.queryForOption(selectDescriptionWithNamedParams, classOf[String], Map("id" -> 1)).get should be("python")

    template.queryForOption(selectWithNamedParams, mapperFunction, Map("id" -> 1)).get should have(
      'id(1),
      'description("python")
    )

    template.queryForOption(selectDescriptionWithNamedParams, classOf[String], Map("id" -> -1)) should be(None)

    template.queryForOption(selectDescriptionWithNamedParams, mapperFunction, Map("id" -> -1)) should be(None)
    //Source
    template.queryForOption(selectDescriptionWithNamedParams, classOf[String], new MapSqlParameterSource("id", 1)).get should be("python")

    template.queryForOption(selectWithNamedParams, mapperFunction, new MapSqlParameterSource("id", 1)).get should have(
      'id(1),
      'description("python")
    )

    template.queryForOption(selectDescriptionWithNamedParams, classOf[String], new MapSqlParameterSource("id", -1)) should be(None)

    template.queryForOption(selectWithNamedParams, mapperFunction, new MapSqlParameterSource("id", -1)) should be(None)
    //Any*
    template.queryForOption(selectDescription, classOf[String], 1).get should be("python")

    template.queryForOption(selectObject, mapperFunction, 1).get should have(
      'id(1),
      'description("python")
    )

    template.queryForOption(selectDescription, classOf[String], -1) should be(None)

    template.queryForOption(selectObject, mapperFunction, -1) should be(None)
  }

  @Test
  def testQuery() {
    template.query(selectLessThan, mapperFunction, Map("id" -> 4)) should have size 3

    template.query(selectLessThan, mapperFunction, new MapSqlParameterSource("id", 4)) should have size 3

    template.query(select, mapperFunction) should have size 5
  }

  @Test
  def testQueryForMap() {
    //Map
    template.queryForMap(selectWithNamedParams, Map("id" -> 1))("DESCRIPTION") should be("python")

    intercept[EmptyResultDataAccessException] {
      template.queryForMap(selectWithNamedParams, Map("id" -> -1))
    }

    //Source
    template.queryForMap(selectWithNamedParams, new MapSqlParameterSource("id", 1))("DESCRIPTION") should be("python")

    intercept[EmptyResultDataAccessException] {
      template.queryForMap(selectWithNamedParams, new MapSqlParameterSource("id", -1))
    }

    //Any*
    template.queryForMap(selectObject, 1)("DESCRIPTION") should be("python")

    intercept[EmptyResultDataAccessException] {
      template.queryForMap(selectObject, -1)
    }
  }

  @Test
  def testQueryForOptionMap() {
    //Map
    template.queryForOptionMap(selectWithNamedParams, Map("id" -> 1)).get("DESCRIPTION") should be("python")

    template.queryForOptionMap(selectWithNamedParams, Map("id" -> -1)) should be(None)
    //Source
    template.queryForOptionMap(selectWithNamedParams, new MapSqlParameterSource("id", 1)).get("DESCRIPTION") should be("python")

    template.queryForOptionMap(selectWithNamedParams, new MapSqlParameterSource("id", -1)) should be(None)
    //Any*
    template.queryForOptionMap(selectObject, 1).get("DESCRIPTION") should be("python")

    template.queryForOptionMap(selectObject, -1) should be(None)
  }

  @Test
  def testQueryForList() {
    template.queryForList(selectLessThan, Map("id" -> 4)) should have size 3

    template.queryForList(selectLessThan, new MapSqlParameterSource("id", 4)) should have size 3

    template.queryForList(select) should have size 5
  }

  def checkCount(count: Int) {
    template.queryForInt(selectCount) should be(count)
  }

  @Test
  def testUpdate() {
    template.update(insertWithNamedParams, Map("description" -> "haskell")) should be(1)
    template.update(insertWithNamedParams, new MapSqlParameterSource("description", "clojure")) should be(1)
    template.update(insert, "erlang") should be(1)
    checkCount(8)
  }

  @Test
  def testBatchUpdate() {
    template.batchUpdate(deleteWithNamedParams, Seq(new MapSqlParameterSource("id", 1), new MapSqlParameterSource("id", 2)))(0) should be(1)
    template.batchUpdate(deleteObject, Seq(Seq(3), Seq(4)), Seq.empty)
    checkCount(1)
  }
}