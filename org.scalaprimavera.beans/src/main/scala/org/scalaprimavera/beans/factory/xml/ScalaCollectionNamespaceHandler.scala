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

package org.scalaprimavera.beans.factory.xml

import org.w3c.dom.Element
import org.springframework.beans.factory.xml.{ParserContext, AbstractSingleBeanDefinitionParser, NamespaceHandlerSupport}
import org.springframework.beans.factory.support.BeanDefinitionBuilder
import org.springframework.util.StringUtils
import org.scalaprimavera.beans.factory.config.{ScalaMapFactoryBean, ScalaListFactoryBean}


/**
 * Created by IntelliJ IDEA.
 * User: mario
 * Date: 10/10/11
 * Time: 0:12
 */

class ScalaCollectionNamespaceHandler extends NamespaceHandlerSupport {
  def init() {
    registerBeanDefinitionParser(ScalaCollectionNamespaceHandler.list, new ScalaListBeanDefinitionParser)
    registerBeanDefinitionParser(ScalaCollectionNamespaceHandler.map, new ScalaMapBeanDefinitionParser)
  }
}

private[xml] class ScalaListBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
  override def getBeanClass(element: Element): Class[ScalaListFactoryBean] = classOf[ScalaListFactoryBean]


  override def doParse(element: Element, parserContext: ParserContext, builder: BeanDefinitionBuilder) {
    val parsedList = parserContext.getDelegate.parseListElement(element, builder.getRawBeanDefinition);
    builder.addPropertyValue(ScalaCollectionNamespaceHandler.sourceList, parsedList)
    ScalaCollectionNamespaceHandler.setScope(element, builder)
  }
}

private[xml] class ScalaMapBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
  override def getBeanClass(element: Element): Class[ScalaMapFactoryBean] = classOf[ScalaMapFactoryBean]

  override def doParse(element: Element, parserContext: ParserContext, builder: BeanDefinitionBuilder) {
    val parsedMap = parserContext.getDelegate.parseMapElement(element, builder.getRawBeanDefinition);
    builder.addPropertyValue(ScalaCollectionNamespaceHandler.sourceMap, parsedMap)
    ScalaCollectionNamespaceHandler.setScope(element, builder)
  }
}

object ScalaCollectionNamespaceHandler {
  final val scopeAttribute = "scope";
  final val list = "list"
  final val map = "map"
  final val sourceList = "sourceList"
  final val sourceMap = "sourceMap"

  private[xml] def setScope(element: Element, builder: BeanDefinitionBuilder) {
    val scope = element.getAttribute(ScalaCollectionNamespaceHandler.scopeAttribute)
    if (StringUtils.hasLength(scope)) {
      builder.setScope(scope)
    }
  }
}