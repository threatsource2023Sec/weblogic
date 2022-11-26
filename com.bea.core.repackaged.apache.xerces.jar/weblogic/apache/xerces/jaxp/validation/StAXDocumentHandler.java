package weblogic.apache.xerces.jaxp.validation;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.stream.events.StartDocument;
import javax.xml.transform.stax.StAXResult;
import weblogic.apache.xerces.xni.XMLDocumentHandler;

interface StAXDocumentHandler extends XMLDocumentHandler {
   void setStAXResult(StAXResult var1);

   void startDocument(XMLStreamReader var1) throws XMLStreamException;

   void endDocument(XMLStreamReader var1) throws XMLStreamException;

   void comment(XMLStreamReader var1) throws XMLStreamException;

   void processingInstruction(XMLStreamReader var1) throws XMLStreamException;

   void entityReference(XMLStreamReader var1) throws XMLStreamException;

   void startDocument(StartDocument var1) throws XMLStreamException;

   void endDocument(EndDocument var1) throws XMLStreamException;

   void doctypeDecl(DTD var1) throws XMLStreamException;

   void characters(Characters var1) throws XMLStreamException;

   void cdata(Characters var1) throws XMLStreamException;

   void comment(Comment var1) throws XMLStreamException;

   void processingInstruction(ProcessingInstruction var1) throws XMLStreamException;

   void entityReference(EntityReference var1) throws XMLStreamException;

   void setIgnoringCharacters(boolean var1);
}
