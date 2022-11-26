package org.hibernate.validator.internal.xml;

import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.hibernate.validator.internal.util.Contracts;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.util.logging.Messages;
import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
import org.hibernate.validator.internal.util.privilegedactions.GetResource;
import org.hibernate.validator.internal.util.privilegedactions.NewSchema;

public class XmlParserHelper {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private static final int NUMBER_OF_SCHEMAS = 4;
   private static final String DEFAULT_VERSION = "1.0";
   private static final QName VERSION_QNAME = new QName("version");
   private final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
   private static final ConcurrentMap schemaCache = new ConcurrentHashMap(4);

   public String getSchemaVersion(String resourceName, XMLEventReader xmlEventReader) {
      Contracts.assertNotNull(xmlEventReader, Messages.MESSAGES.parameterMustNotBeNull("xmlEventReader"));

      try {
         StartElement rootElement = this.getRootElement(xmlEventReader);
         return this.getVersionValue(rootElement);
      } catch (XMLStreamException var4) {
         throw LOG.getUnableToDetermineSchemaVersionException(resourceName, var4);
      }
   }

   public synchronized XMLEventReader createXmlEventReader(String resourceName, InputStream xmlStream) {
      try {
         return this.xmlInputFactory.createXMLEventReader(xmlStream);
      } catch (Exception var4) {
         throw LOG.getUnableToCreateXMLEventReader(resourceName, var4);
      }
   }

   private String getVersionValue(StartElement startElement) {
      if (startElement == null) {
         return null;
      } else {
         Attribute versionAttribute = startElement.getAttributeByName(VERSION_QNAME);
         return versionAttribute != null ? versionAttribute.getValue() : "1.0";
      }
   }

   private StartElement getRootElement(XMLEventReader xmlEventReader) throws XMLStreamException {
      XMLEvent event;
      for(event = xmlEventReader.peek(); event != null && !event.isStartElement(); event = xmlEventReader.peek()) {
         xmlEventReader.nextEvent();
      }

      return event == null ? null : event.asStartElement();
   }

   public Schema getSchema(String schemaResource) {
      Schema schema = (Schema)schemaCache.get(schemaResource);
      if (schema != null) {
         return schema;
      } else {
         schema = this.loadSchema(schemaResource);
         if (schema != null) {
            Schema previous = (Schema)schemaCache.putIfAbsent(schemaResource, schema);
            return previous != null ? previous : schema;
         } else {
            return null;
         }
      }
   }

   private Schema loadSchema(String schemaResource) {
      ClassLoader loader = (ClassLoader)this.run((PrivilegedAction)GetClassLoader.fromClass(XmlParserHelper.class));
      URL schemaUrl = (URL)this.run((PrivilegedAction)GetResource.action(loader, schemaResource));
      SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
      Schema schema = null;

      try {
         schema = (Schema)this.run((PrivilegedExceptionAction)NewSchema.action(sf, schemaUrl));
      } catch (Exception var7) {
         LOG.unableToCreateSchema(schemaResource, var7.getMessage());
      }

      return schema;
   }

   private Object run(PrivilegedAction action) {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }

   private Object run(PrivilegedExceptionAction action) throws Exception {
      return System.getSecurityManager() != null ? AccessController.doPrivileged(action) : action.run();
   }
}
