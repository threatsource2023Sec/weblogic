package weblogic.xml.jaxp;

import java.io.OutputStream;
import java.io.Writer;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import weblogic.utils.Debug;

public class WebLogicXMLOutputFactory extends XMLOutputFactory {
   private static final boolean debug = Boolean.getBoolean("weblogic.xml.debug");
   private XMLOutputFactory delegate;

   public WebLogicXMLOutputFactory() {
      String[] outputFactories = new String[]{"com.ctc.wstx.stax.WstxOutputFactory", "org.codehaus.stax2.XMLOutputFactory2", "weblogic.xml.stax.XMLStreamOutputFactory", "javax.xml.stream.XMLOutputFactory"};
      this.delegate = (XMLOutputFactory)Utils.getDelegate(outputFactories);
      if (debug) {
         Debug.say("WebLogicXMLOutputFactory is delegating to " + this.delegate.getClass());
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
