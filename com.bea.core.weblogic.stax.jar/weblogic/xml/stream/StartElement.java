package weblogic.xml.stream;

import java.util.Map;

/** @deprecated */
@Deprecated
public interface StartElement extends XMLEvent {
   AttributeIterator getAttributes();

   AttributeIterator getNamespaces();

   AttributeIterator getAttributesAndNamespaces();

   Attribute getAttributeByName(XMLName var1);

   String getNamespaceUri(String var1);

   Map getNamespaceMap();
}
