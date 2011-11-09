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

import org.scalaprimavera.jdbc.core.simple.ScalaSimpleJdbcOperations
import org.springframework.dao.DataAccessException

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 5/11/11
 * Time: 21:47
 */

trait DSLJdbcOperations {

  def noDSL: ScalaSimpleJdbcOperations

  @throws(classOf[DataAccessException])
  def queryForInt(sql: String): ParametersToken[Int]

  @throws(classOf[DataAccessException])
  def queryForOptionInt(sql: String): ParametersToken[Option[Int]]

  @throws(classOf[DataAccessException])
  def queryForLong(sql: String): ParametersToken[Long]

  @throws(classOf[DataAccessException])
  def queryForOptionLong(sql: String): ParametersToken[Option[Long]]

  @throws(classOf[DataAccessException])
  def queryForObject[T](sql: String): ParametersToken[MappedWithToken[T]]

  @throws(classOf[DataAccessException])
  def queryForOption[T](sql: String): ParametersToken[OptionMappedWithToken[T]]

  @throws(classOf[DataAccessException])
  def query[T](sql: String): ParametersToken[ListMappedWithToken[T]]

  @throws(classOf[DataAccessException])
  def queryForMap(sql: String): ParametersToken[Map[String, Any]]

  @throws(classOf[DataAccessException])
  def queryForOptionMap(sql: String): ParametersToken[Option[Map[String, Any]]]

  @throws(classOf[DataAccessException])
  def queryForList(sql: String): ParametersToken[List[Map[String, Any]]]

  @throws(classOf[DataAccessException])
  def update(sql: String): WithThisToken
}