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

import org.springframework.dao.DataAccessException
import org.scalaprimavera.jdbc.core.simple.ScalaSimpleJdbcTemplate
import java.sql.ResultSet
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource


/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 5/11/11
 * Time: 21:52
 */

class DSLJdbcTemplate(val template: ScalaSimpleJdbcTemplate) extends DSLJdbcOperations {


  def noDSL = template

  @throws(classOf[DataAccessException])
  def queryForInt(sql: String) = new ParametersToken[Int] {
    def parameters(params: (String, Any)*) = template.queryForInt(sql, Map(params: _*))
  }

  @throws(classOf[DataAccessException])
  def queryForOptionInt(sql: String) = new ParametersToken[Option[Int]] {
    def parameters(params: (String, Any)*) = template.queryForOptionInt(sql, Map(params: _*))
  }

  @throws(classOf[DataAccessException])
  def queryForLong(sql: String) = new ParametersToken[Long] {
    def parameters(params: (String, Any)*) = template.queryForLong(sql, Map(params: _*))
  }

  @throws(classOf[DataAccessException])
  def queryForOptionLong(sql: String) = new ParametersToken[Option[Long]] {
    def parameters(params: (String, Any)*) = template.queryForOptionLong(sql, Map(params: _*))
  }

  @throws(classOf[DataAccessException])
  def queryForObject[T](sql: String): ParametersToken[MappedWithToken[T]] = new ParametersToken[MappedWithToken[T]] {
    def parameters(params: (String, Any)*): MappedWithToken[T] = new MappedWithToken[T] {
      def as(requiredType: Class[T]): T = template.queryForObject(sql, requiredType, Map(params: _*))

      def mappedWith(mapper: (ResultSet, Int) => T): T = template.queryForObject(sql, mapper, Map(params: _*))
    }
  }

  @throws(classOf[DataAccessException])
  def queryForOption[T](sql: String): ParametersToken[OptionMappedWithToken[T]] = new ParametersToken[OptionMappedWithToken[T]] {
    def parameters(params: (String, Any)*): OptionMappedWithToken[T] = new OptionMappedWithToken[T] {
      def as(requiredType: Class[T]): Option[T] = template.queryForOption(sql, requiredType, Map(params: _*))

      def mappedWith(mapper: (ResultSet, Int) => T): Option[T] = template.queryForOption(sql, mapper, Map(params: _*))
    }
  }

  @throws(classOf[DataAccessException])
  def query[T](sql: String): ParametersToken[ListMappedWithToken[T]] = new ParametersToken[ListMappedWithToken[T]] {
    def parameters(params: (String, Any)*): ListMappedWithToken[T] = new ListMappedWithToken[T] {
      def mappedWith(mapper: (ResultSet, Int) => T): List[T] = template.query(sql, mapper, Map(params: _*))
    }
  }

  @throws(classOf[DataAccessException])
  def queryForMap(sql: String): ParametersToken[Map[String, Any]] = new ParametersToken[Map[String, Any]] {
    def parameters(params: (String, Any)*): Map[String, Any] = template.queryForMap(sql, Map(params: _*))
  }

  @throws(classOf[DataAccessException])
  def queryForOptionMap(sql: String): ParametersToken[Option[Map[String, Any]]] = new ParametersToken[Option[Map[String, Any]]] {
    def parameters(params: (String, Any)*): Option[Map[String, Any]] = template.queryForOptionMap(sql, Map(params: _*))
  }

  @throws(classOf[DataAccessException])
  def queryForList(sql: String): ParametersToken[List[Map[String, Any]]] = new ParametersToken[List[Map[String, Any]]] {
    def parameters(params: (String, Any)*): List[Map[String, Any]] = template.queryForList(sql, Map(params: _*))
  }

  @throws(classOf[DataAccessException])
  def update(sql: String): WithThisToken = new WithThisToken {
    def withThis(bean: Any): Int = template.update(sql, new BeanPropertySqlParameterSource(bean))

    def parameters(params: (String, Any)*): Int = template.update(sql, Map(params: _*))
  }
}