package weblogic.xml.jaxp;

import java.io.OutputStream;
import java.io.Writer;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import weblogic.xml.XMLLogger;
import weblogic.xml.registry.RegistryEntityResolver;
import weblogic.xml.registry.XMLRegistryException;

public class RegistryXMLOutputFactory extends XMLOutputFactory {
   private XMLOutputFactory delegate = null;

   public RegistryXMLOutputFactory() {
      try {
         RegistryEntityResolver registry = new RegistryEntityResolver();
         this.delegate = registry.getXMLOutputFactory();
      } catch (XMLRegistryException var2) {
         XMLLogger.logXMLRegistryException(var2.getMessage());
      }

      if (this.delegate == null) {
         this.delegate = new WebLogicXMLOutputFactory();
      }

   }

   public XMLStreamWriter createXMLStreamWriter(Writer stream) throws XMLStreamException {
      return this.delegate.createXMLStreamWriter(stream);
   }

   public XMLStreamWriter createXMLStreamWriter(OutputStream stream) throws XMLStreamException {
      return this.delegate.createXMLStreamWriter(stream);
   }

   public XMLStreamWriter createXMLStreamWriter(OutputStream stream, String encoding) throws XMLStreamException {
      return this.delegate.createXMLStreamWriter(stream, encoding);
   }

   public XMLStreamWriter createXMLStreamWriter(Result result) throws XMLStreamException {
      return this.delegate.createXMLStreamWriter(result);
   }

   public XMLEventWriter createXMLEventWriter(Result result) throws XMLStreamException {
      return this.delegate.createXMLEventWriter(result);
   }

   public XMLEventWriter createXMLEventWriter(OutputStream stream) throws XMLStreamException {
      return this.delegate.createXMLEventWriter(stream);
   }

   public XMLEventWriter createXMLEventWriter(OutputStream stream, String encoding) throws XMLStreamException {
      return this.delegate.createXMLEventWriter(stream, encoding);
   }

   public XMLEventWriter createXMLEventWriter(Writer stream) throws XMLStreamException {
      return this.delegate.createXMLEventWriter(stream);
   }

   public void setProperty(String name, Object value) throws IllegalArgumentException {
      this.delegate.setProperty(name, value);
   }

   public Object getProperty(String name) throws IllegalArgumentException {
      return this.delegate.getProperty(name);
   }

   public boolean isPropertySupported(String name) {
      return this.delegate.isPropertySupported(name);
   }
}
