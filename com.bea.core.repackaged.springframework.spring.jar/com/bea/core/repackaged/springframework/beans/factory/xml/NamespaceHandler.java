package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinitionHolder;
import com.bea.core.repackaged.springframework.lang.Nullable;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public interface NamespaceHandler {
   void init();

   @Nullable
   BeanDefinition parse(Element var1, ParserContext var2);

   @Nullable
   BeanDefinitionHolder decorate(Node var1, BeanDefinitionHolder var2, ParserContext var3);
}
