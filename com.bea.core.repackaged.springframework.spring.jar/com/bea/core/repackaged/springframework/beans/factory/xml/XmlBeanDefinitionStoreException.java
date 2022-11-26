package com.bea.core.repackaged.springframework.beans.factory.xml;

import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlBeanDefinitionStoreException extends BeanDefinitionStoreException {
   public XmlBeanDefinitionStoreException(String resourceDescription, String msg, SAXException cause) {
      super(resourceDescription, msg, (Throwable)cause);
   }

   public int getLineNumber() {
      Throwable cause = this.getCause();
      return cause instanceof SAXParseException ? ((SAXParseException)cause).getLineNumber() : -1;
   }
}
