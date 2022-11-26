package org.apache.xmlbeans.xml.stream;

public interface ChangePrefixMapping extends XMLEvent {
   String getOldNamespaceUri();

   String getNewNamespaceUri();

   String getPrefix();
}
