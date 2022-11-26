package org.apache.xml.security.stax.ext.stax;

import java.util.List;
import javax.xml.stream.events.XMLEvent;

public interface XMLSecEvent extends XMLEvent {
   void setParentXMLSecStartElement(XMLSecStartElement var1);

   XMLSecStartElement getParentXMLSecStartElement();

   int getDocumentLevel();

   void getElementPath(List var1);

   List getElementPath();

   XMLSecStartElement getStartElementAtLevel(int var1);

   XMLSecStartElement asStartElement();

   XMLSecEndElement asEndElement();

   XMLSecCharacters asCharacters();
}
