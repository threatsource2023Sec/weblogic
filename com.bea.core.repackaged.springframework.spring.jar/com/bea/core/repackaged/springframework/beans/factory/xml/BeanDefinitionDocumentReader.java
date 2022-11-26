package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import org.w3c.dom.Document;

public interface BeanDefinitionDocumentReader {
   void registerBeanDefinitions(Document var1, XmlReaderContext var2) throws BeanDefinitionStoreException;
}
