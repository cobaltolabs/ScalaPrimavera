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

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate
import collection.JavaConversions._
import java.{util => ju}
import org.springframework.dao.{EmptyResultDataAccessException, DataAccessException}
import org.springframework.jdbc.core.namedparam.SqlParameterSource
import java.sql.ResultSet
import org.scalaprimavera.jdbc.core.FunctionalRowMapper
import collection.mutable.WrappedArray

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 19/10/11
 * Time: 23:21
 */

sealed class ScalaSimpleJdbcTemplate(val template: SimpleJdbcTemplate) extends ScalaSimpleJdbcOperations {

  private implicit def toList[T](l: ju.List[T]) = asScalaBuffer(l).toList

  private implicit def toMap[String, Any](m: ju.Map[String, Any]) = mapAsScalaMap(m).toMap


  private def toArray(args: Seq[Any]): Array[Any] = {
    args.asInstanceOf[WrappedArray[Any]].toArray
  }

  private def toOption[T](body: => T): Option[T] = {
    try {
      Option(body)
    }
    catch {
      case e: EmptyResultDataAccessException => None
    }
  }

  def getSimpleJdbcOperations = template

  @throws(classOf[DataAccessException])
  def queryForInt(sql: String, args: Map[String, Any]): Int = template.queryForInt(sql, mapAsJavaMap(args))

  @throws(classOf[DataAccessException])
  def queryForOptionInt(sql: String, args: Map[String, Any]): Option[Int] = toOption(queryForInt(sql, args))


  @throws(classOf[DataAccessException])
  def queryForInt(sql: String, args: SqlParameterSource): Int = template.queryForInt(sql, args)

  @throws(classOf[DataAccessException])
  def queryForOptionInt(sql: String, args: SqlParameterSource): Option[Int] = toOption(queryForInt(sql, args))


  @throws(classOf[DataAccessException])
  def queryForInt(sql: String, args: Any*): Int = template.queryForInt(sql, toArray(args))


  @throws(classOf[DataAccessException])
  def queryForOptionInt(sql: String, args: Any*): Option[Int] = toOption(template.queryForInt(sql, toArray(args)))


  @throws(classOf[DataAccessException])
  def queryForLong(sql: String, args: Map[String, Any]): Long = template.queryForLong(sql, mapAsJavaMap(args))

  @throws(classOf[DataAccessException])
  def queryForOptionLong(sql: String, args: Map[String, Any]): Option[Long] = toOption(queryForLong(sql, args))

  @throws(classOf[DataAccessException])
  def queryForLong(sql: String, args: SqlParameterSource): Long = template.queryForLong(sql, args)

  @throws(classOf[DataAccessException])
  def queryForOptionLong(sql: String, args: SqlParameterSource): Option[Long] = toOption(queryForLong(sql, args))

  @throws(classOf[DataAccessException])
  def queryForLong(sql: String, args: Any*): Long = template.queryForLong(sql, toArray(args))

  @throws(classOf[DataAccessException])
  def queryForOptionLong(sql: String, args: Any*): Option[Long] = toOption(template.queryForLong(sql, toArray(args)))

  @throws(classOf[DataAccessException])
  def queryForObject[T](sql: String, requiredType: Class[T], args: Map[String, Any]): T = template.queryForObject(sql, requiredType, mapAsJavaMap(args))

  @throws(classOf[DataAccessException])
  def queryForOption[T](sql: String, requiredType: Class[T], args: Map[String, Any]): Option[T] = toOption(queryForObject(sql, requiredType, args))

  @throws(classOf[DataAccessException])
  def queryForObject[T](sql: String, requiredType: Class[T], args: SqlParameterSource): T = template.queryForObject(sql, requiredType, args)

  @throws(classOf[DataAccessException])
  def queryForOption[T](sql: String, requiredType: Class[T], args: SqlParameterSource): Option[T] = toOption(queryForObject(sql, requiredType, args))

  @throws(classOf[DataAccessException])
  def queryForObject[T](sql: String, requiredType: Class[T], args: Any*): T = template.queryForObject(sql, requiredType, toArray(args))

  @throws(classOf[DataAccessException])
  def queryForOption[T](sql: String, requiredType: Class[T], args: Any*): Option[T] = toOption(template.queryForObject(sql, requiredType, toArray(args)))

  @throws(classOf[DataAccessException])
  def queryForObject[T](sql: String, mapper: (ResultSet, Int) => T, args: Map[String, Any]): T = template.queryForObject(sql, new FunctionalRowMapper[T](mapper), mapAsJavaMap(args))

  @throws(classOf[DataAccessException])
  def queryForOption[T](sql: String, mapper: (ResultSet, Int) => T, args: Map[String, Any]): Option[T] = toOption(queryForObject(sql, mapper, args))

  @throws(classOf[DataAccessException])
  def queryForObject[T](sql: String, mapper: (ResultSet, Int) => T, args: SqlParameterSource): T = template.queryForObject(sql, new FunctionalRowMapper[T](mapper), args)

  @throws(classOf[DataAccessException])
  def queryForOption[T](sql: String, mapper: (ResultSet, Int) => T, args: SqlParameterSource): Option[T] = toOption(queryForObject(sql, mapper, args))

  @throws(classOf[DataAccessException])
  def queryForObject[T](sql: String, mapper: (ResultSet, Int) => T, args: Any*): T = template.queryForObject(sql, new FunctionalRowMapper[T](mapper), toArray(args))

  @throws(classOf[DataAccessException])
  def queryForOption[T](sql: String, mapper: (ResultSet, Int) => T, args: Any*): Option[T] = toOption(template.queryForObject(sql, new FunctionalRowMapper[T](mapper), toArray(args)))

  @throws(classOf[DataAccessException])
  def query[T](sql: String, mapper: (ResultSet, Int) => T, args: Map[String, Any]): List[T] = template.query(sql, new FunctionalRowMapper[T](mapper), mapAsJavaMap(args))

  @throws(classOf[DataAccessException])
  def query[T](sql: String, mapper: (ResultSet, Int) => T, args: SqlParameterSource): List[T] = template.query(sql, new FunctionalRowMapper[T](mapper), args)

  @throws(classOf[DataAccessException])
  def query[T](sql: String, mapper: (ResultSet, Int) => T, args: Any*): List[T] = template.query(sql, new FunctionalRowMapper[T](mapper), toArray(args))

  @throws(classOf[DataAccessException])
  def queryForMap(sql: String, args: Map[String, Any]): Map[String, Any] = template.queryForMap(sql, mapAsJavaMap(args))

  @throws(classOf[DataAccessException])
  def queryForOptionMap(sql: String, args: Map[String, Any]): Option[Map[String, Any]] = toOption(queryForMap(sql, args))

  @throws(classOf[DataAccessException])
  def queryForMap(sql: String, args: SqlParameterSource): Map[String, Any] = template.queryForMap(sql, args)

  @throws(classOf[DataAccessException])
  def queryForOptionMap(sql: String, args: SqlParameterSource): Option[Map[String, Any]] = toOption(queryForMap(sql, args))

  @throws(classOf[DataAccessException])
  def queryForMap(sql: String, args: Any*): Map[String, Any] = template.queryForMap(sql, toArray(args))

  @throws(classOf[DataAccessException])
  def queryForOptionMap(sql: String, args: Any*): Option[Map[String, Any]] = toOption(template.queryForMap(sql, toArray(args)))

  @throws(classOf[DataAccessException])
  def queryForList(sql: String, args: Map[String, Any]): List[Map[String, Any]] = toList(template.queryForList(sql, mapAsJavaMap(args))).map(mapAsScalaMap(_).toMap)

  @throws(classOf[DataAccessException])
  def queryForList(sql: String, args: SqlParameterSource): List[Map[String, Any]] = toList(template.queryForList(sql, args)).map(mapAsScalaMap(_).toMap)

  @throws(classOf[DataAccessException])
  def queryForList(sql: String, args: Any*): List[Map[String, Any]] = toList(template.queryForList(sql, toArray(args))).map(mapAsScalaMap(_).toMap)

  @throws(classOf[DataAccessException])
  def update(sql: String, args: Map[String, Any]): Int = template.update(sql, mapAsJavaMap(args))

  @throws(classOf[DataAccessException])
  def update(sql: String, args: SqlParameterSource): Int = template.update(sql, args)

  @throws(classOf[DataAccessException])
  def update(sql: String, args: Any*): Int = template.update(sql, toArray(args))

  @throws(classOf[DataAccessException])
  def batchUpdate(sql: String, args: Seq[_ <: SqlParameterSource]): Seq[Int] = template.batchUpdate(sql, args.map(_.asInstanceOf[SqlParameterSource]).toArray)

  @throws(classOf[DataAccessException])
  def batchUpdate(sql: String, args: Seq[Seq[Any]], types: Seq[Int]): Seq[Int] = template.batchUpdate(sql,seqAsJavaList(args.map(_.toArray.map(_.asInstanceOf[Object]))),types.toArray)
}