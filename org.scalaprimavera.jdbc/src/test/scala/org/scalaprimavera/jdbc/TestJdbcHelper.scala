package org.scalaprimavera.jdbc

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

import java.sql.ResultSet
import org.scalaprimavera.jdbc.TestBean

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 7/11/11
 * Time: 22:41
 */

object TestJdbcHelper {
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
}