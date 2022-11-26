package org.hibernate.validator.internal.xml.mapping;

import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.hibernate.validator.internal.xml.AbstractStaxBuilder;

abstract class AbstractMultiValuedElementStaxBuilder extends AbstractStaxBuilder {
   private static final String VALUE_QNAME_LOCAL_PART = "value";
   private static final Class[] EMPTY_CLASSES_ARRAY = new Class[0];
   private final ClassLoadingHelper classLoadingHelper;
   private final DefaultPackageStaxBuilder defaultPackageStaxBuilder;
   private final List values;

   protected AbstractMultiValuedElementStaxBuilder(ClassLoadingHelper classLoadingHelper, DefaultPackageStaxBuilder defaultPackageStaxBuilder) {
      this.classLoadingHelper = classLoadingHelper;
      this.defaultPackageStaxBuilder = defaultPackageStaxBuilder;
      this.values = new ArrayList();
   }

   protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
      while(!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals(this.getAcceptableQName())) {
         xmlEvent = xmlEventReader.nextEvent();
         if (xmlEvent.isStartElement() && xmlEvent.asStartElement().getName().getLocalPart().equals("value")) {
            this.values.add(this.readSingleElement(xmlEventReader));
         }
      }

   }

   public Class[] build() {
      String defaultPackage = (String)this.defaultPackageStaxBuilder.build().orElse("");
      return this.values.isEmpty() ? EMPTY_CLASSES_ARRAY : (Class[])this.values.stream().map((valueClass) -> {
         return this.classLoadingHelper.loadClass(valueClass, defaultPackage);
      }).peek(this::verifyClass).toArray((x$0) -> {
         return new Class[x$0];
      });
   }

   public abstract void verifyClass(Class var1);
}
