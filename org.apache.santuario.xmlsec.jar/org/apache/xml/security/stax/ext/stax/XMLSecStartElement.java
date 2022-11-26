package org.apache.xml.security.stax.ext.stax;

import java.util.List;
import javax.xml.stream.events.StartElement;

public interface XMLSecStartElement extends XMLSecEvent, StartElement {
   XMLSecNamespace getElementNamespace();

   void getNamespacesFromCurrentScope(List var1);

   List getOnElementDeclaredNamespaces();

   void addNamespace(XMLSecNamespace var1);

   void getAttributesFromCurrentScope(List var1);

   List getOnElementDeclaredAttributes();

   void addAttribute(XMLSecAttribute var1);

   XMLSecStartElement asStartElement();
}
