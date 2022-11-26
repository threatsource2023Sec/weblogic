package org.apache.xmlbeans.xml.stream;

public interface StartPrefixMapping extends XMLEvent {
   String getNamespaceUri();

   String getPrefix();
}
