package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import org.w3c.dom.Node;

public interface BeanDefinitionDecorator {
   BeanDefinitionHolder decorate(Node var1, BeanDefinitionHolder var2, ParserContext var3);
}
