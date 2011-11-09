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

import org.springframework.jdbc.core.simple.SimpleJdbcOperations
import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import java.sql.ResultSet

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 19/10/11
 * Time: 23:19
 */

trait ScalaSimpleJdbcOperations {
  def getSimpleJdbcOperations: SimpleJdbcOperations

  @throws(classOf[DataAccessException])
  def queryForInt(sql: String, args: Map[String, Any]): Int

  @throws(classOf[DataAccessException])
  def queryForOptionInt(sql: String, args: Map[String, Any]): Option[Int]

  @throws(classOf[DataAccessException])
  def queryForInt(sql: String, args: SqlParameterSource): Int

  @throws(classOf[DataAccessException])
  def queryForOptionInt(sql: String, args: SqlParameterSource): Option[Int]

  @throws(classOf[DataAccessException])
  def queryForInt(sql: String, args: Any*): Int

  @throws(classOf[DataAccessException])
  def queryForOptionInt(sql: String, args: Any*): Option[Int]

  @throws(classOf[DataAccessException])
  def queryForLong(sql: String, args: Map[String, Any]): Long

  @throws(classOf[DataAccessException])
  def queryForOptionLong(sql: String, args: Map[String, Any]): Option[Long]

  @throws(classOf[DataAccessException])
  def queryForLong(sql: String, args: SqlParameterSource): Long

  @throws(classOf[DataAccessException])
  def queryForOptionLong(sql: String, args: SqlParameterSource): Option[Long]

  @throws(classOf[DataAccessException])
  def queryForLong(sql: String, args: Any*): Long

  @throws(classOf[DataAccessException])
  def queryForOptionLong(sql: String, args: Any*): Option[Long]

  @throws(classOf[DataAccessException])
  def queryForObject[T](sql: String, requiredType: Class[T], args: Map[String, Any]): T

  @throws(classOf[DataAccessException])
  def queryForOption[T](sql: String, requiredType: Class[T], args: Map[String, Any]): Option[T]

  @throws(classOf[DataAccessException])
  def queryForObject[T](sql: String, requiredType: Class[T], args: SqlParameterSource): T

  @throws(classOf[DataAccessException])
  def queryForOption[T](sql: String, requiredType: Class[T], args: SqlParameterSource): Option[T]

  @throws(classOf[DataAccessException])
  def queryForObject[T](sql: String, requiredType: Class[T], args: Any*): T

  @throws(classOf[DataAccessException])
  def queryForOption[T](sql: String, requiredType: Class[T], args: Any*): Option[T]

  @throws(classOf[DataAccessException])
  def queryForObject[T](sql: String, mapper: (ResultSet, Int) => T, args: Map[String, Any]): T

  @throws(classOf[DataAccessException])
  def queryForOption[T](sql: String, mapper: (ResultSet, Int) => T, args: Map[String, Any]): Option[T]

  @throws(classOf[DataAccessException])
  def queryForObject[T](sql: String, mapper: (ResultSet, Int) => T, args: SqlParameterSource): T

  @throws(classOf[DataAccessException])
  def queryForOption[T](sql: String, mapper: (ResultSet, Int) => T, args: SqlParameterSource): Option[T]

  @throws(classOf[DataAccessException])
  def queryForObject[T](sql: String, mapper: (ResultSet, Int) => T, args: Any*): T

  @throws(classOf[DataAccessException])
  def queryForOption[T](sql: String, mapper: (ResultSet, Int) => T, args: Any*): Option[T]

  @throws(classOf[DataAccessException])
  def query[T](sql: String, mapper: (ResultSet, Int) => T, args: Map[String, Any]): List[T]

  @throws(classOf[DataAccessException])
  def query[T](sql: String, mapper: (ResultSet, Int) => T, args: SqlParameterSource): List[T]

  @throws(classOf[DataAccessException])
  def query[T](sql: String, mapper: (ResultSet, Int) => T, args: Any*): List[T]

  @throws(classOf[DataAccessException])
  def queryForMap(sql: String, args: Map[String, Any]): Map[String, Any]

  @throws(classOf[DataAccessException])
  def queryForOptionMap(sql: String, args: Map[String, Any]): Option[Map[String, Any]]

  @throws(classOf[DataAccessException])
  def queryForMap(sql: String, args: SqlParameterSource): Map[String, Any]

  @throws(classOf[DataAccessException])
  def queryForOptionMap(sql: String, args: SqlParameterSource): Option[Map[String, Any]]

  @throws(classOf[DataAccessException])
  def queryForMap(sql: String, args: Any*): Map[String, Any]

  @throws(classOf[DataAccessException])
  def queryForOptionMap(sql: String, args: Any*): Option[Map[String, Any]]

  @throws(classOf[DataAccessException])
  def queryForList(sql: String, args: Map[String, Any]): List[Map[String, Any]]

  @throws(classOf[DataAccessException])
  def queryForList(sql: String, args: SqlParameterSource): List[Map[String, Any]]

  @throws(classOf[DataAccessException])
  def queryForList(sql: String, args: Any*): List[Map[String, Any]]

  @throws(classOf[DataAccessException])
  def update(sql: String, args: Map[String, Any]): Int

  @throws(classOf[DataAccessException])
  def update(sql: String, args: SqlParameterSource): Int

  @throws(classOf[DataAccessException])
  def update(sql: String, args: Any*): Int

  @throws(classOf[DataAccessException])
  def batchUpdate(sql: String, args: Seq[_ <: SqlParameterSource]): Seq[Int]

  @throws(classOf[DataAccessException])
  def batchUpdate(sql: String, args: Seq[Seq[Any]], types: Seq[Int]): Seq[Int]


}