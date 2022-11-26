package org.hibernate.validator.internal.xml.mapping;

import java.util.Optional;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.hibernate.validator.internal.xml.AbstractStaxBuilder;

abstract class AbstractOneLineStringStaxBuilder extends AbstractStaxBuilder {
   private String value;

   protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
      this.value = this.readSingleElement(xmlEventReader);
   }

   public Optional build() {
      return Optional.ofNullable(this.value);
   }
}
