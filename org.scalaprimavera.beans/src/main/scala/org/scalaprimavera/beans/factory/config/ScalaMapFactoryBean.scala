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

import org.springframework.beans.factory.config.AbstractFactoryBean
import collection.JavaConversions

/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 17/10/11
 * Time: 16:00
 */

class ScalaMapFactoryBean extends AbstractFactoryBean[Map[_, _]] with SourceMapAware {
  def getObjectType: Class[Map[_, _]] = classOf[Map[_, _]]

  def createInstance(): Map[_, _] = {
    Option(sourceMap) match {
      case Some(x) => JavaConversions.mapAsScalaMap(sourceMap).toMap
      case None => throw new IllegalArgumentException("'sourceMap' is required")
    }
  }
}