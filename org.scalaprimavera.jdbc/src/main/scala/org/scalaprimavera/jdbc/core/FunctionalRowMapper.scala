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

package org.scalaprimavera.jdbc.core

import java.sql.ResultSet
import org.springframework.jdbc.core.RowMapper

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 19/10/11
 * Time: 23:17
 */

class FunctionalRowMapper[T](function: (ResultSet, Int) => T) extends RowMapper[T] {
  def mapRow(rs: ResultSet, rowNum: Int): T = function(rs, rowNum)
}