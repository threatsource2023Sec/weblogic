package org.apache.xmlbeans.xml.stream;

import java.util.Map;

public interface StartElement extends XMLEvent {
   AttributeIterator getAttributes();

   AttributeIterator getNamespaces();

   AttributeIterator getAttributesAndNamespaces();

   Attribute getAttributeByName(XMLName var1);

   String getNamespaceUri(String var1);

   Map getNamespaceMap();
}
