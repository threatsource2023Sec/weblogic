package org.hibernate.validator.internal.xml;

import java.util.Optional;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public abstract class AbstractStaxBuilder {
   protected abstract String getAcceptableQName();

   protected boolean accept(XMLEvent xmlEvent) {
      return xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals(this.getAcceptableQName());
   }

   public boolean process(XMLEventReader xmlEventReader, XMLEvent xmlEvent) {
      if (this.accept(xmlEvent)) {
         try {
            this.add(xmlEventReader, xmlEvent);
            return true;
         } catch (XMLStreamException var4) {
            throw new IllegalStateException(var4);
         }
      } else {
         return false;
      }
   }

   protected abstract void add(XMLEventReader var1, XMLEvent var2) throws XMLStreamException;

   protected String readSingleElement(XMLEventReader xmlEventReader) throws XMLStreamException {
      XMLEvent xmlEvent = xmlEventReader.nextEvent();
      StringBuilder stringBuilder = new StringBuilder(xmlEvent.asCharacters().getData());

      while(xmlEventReader.peek().isCharacters()) {
         xmlEvent = xmlEventReader.nextEvent();
         stringBuilder.append(xmlEvent.asCharacters().getData());
      }

      return stringBuilder.toString().trim();
   }

   protected Optional readAttribute(StartElement startElement, QName qName) {
      Attribute attribute = startElement.getAttributeByName(qName);
      return Optional.ofNullable(attribute).map(Attribute::getValue);
   }
}
