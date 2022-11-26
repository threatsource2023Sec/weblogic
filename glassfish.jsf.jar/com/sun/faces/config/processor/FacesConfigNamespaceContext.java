package com.sun.faces.config.processor;

import java.util.Collections;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;

class FacesConfigNamespaceContext implements NamespaceContext {
   public String getNamespaceURI(String prefix) {
      return "http://xmlns.jcp.org/xml/ns/javaee";
   }

   public String getPrefix(String namespaceURI) {
      return "ns1";
   }

   public Iterator getPrefixes(String namespaceURI) {
      return Collections.emptyList().iterator();
   }
}
