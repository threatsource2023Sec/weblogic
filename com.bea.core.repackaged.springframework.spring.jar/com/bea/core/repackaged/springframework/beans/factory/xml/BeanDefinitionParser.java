package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.lang.Nullable;
import org.w3c.dom.Element;

public interface BeanDefinitionParser {
   @Nullable
   BeanDefinition parse(Element var1, ParserContext var2);
}
