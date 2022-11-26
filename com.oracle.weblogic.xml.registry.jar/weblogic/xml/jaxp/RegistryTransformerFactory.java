package weblogic.xml.jaxp;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TemplatesHandler;
import javax.xml.transform.sax.TransformerHandler;
import org.xml.sax.XMLFilter;
import weblogic.xml.XMLLogger;
import weblogic.xml.registry.RegistryEntityResolver;
import weblogic.xml.registry.XMLRegistryException;
import weblogic.xml.util.XMLConstants;

public class RegistryTransformerFactory extends SAXTransformerFactory implements XMLConstants {
   private static final boolean debug = Boolean.getBoolean("weblogic.xml.debug");
   private SAXTransformerFactory delegate;

   public RegistryTransformerFactory() {
      try {
         RegistryEntityResolver registry = new RegistryEntityResolver();
         this.delegate = (SAXTransformerFactory)registry.getTransformerFactory();
      } catch (XMLRegistryException var2) {
         XMLLogger.logXMLRegistryException(var2.getMessage());
      }

      if (this.delegate == null) {
         this.delegate = new WebLogicTransformerFactory();
      }

   }

   public Source getAssociatedStylesheet(Source source, String media, String title, String charset) throws TransformerConfigurationException {
      return this.delegate.getAssociatedStylesheet(source, media, title, charset);
   }

   public Object getAttribute(String name) throws IllegalArgumentException {
      return this.delegate.getAttribute(name);
   }

   public ErrorListener getErrorListener() {
      return this.delegate.getErrorListener();
   }

   public boolean getFeature(String name) {
      return this.delegate.getFeature(name);
   }

   public URIResolver getURIResolver() {
      return this.delegate.getURIResolver();
   }

   public Templates newTemplates(Source source) throws TransformerConfigurationException {
      return this.delegate.newTemplates(source);
   }

   public Transformer newTransformer() throws TransformerConfigurationException {
      return this.delegate.newTransformer();
   }

   public Transformer newTransformer(Source source) throws TransformerConfigurationException {
      return this.delegate.newTransformer(source);
   }

   public void setAttribute(String name, Object value) throws IllegalArgumentException {
      this.delegate.setAttribute(name, value);
   }

   public void setErrorListener(ErrorListener listener) throws IllegalArgumentException {
      this.delegate.setErrorListener(listener);
   }

   public void setURIResolver(URIResolver resolver) {
      this.delegate.setURIResolver(resolver);
   }

   public TemplatesHandler newTemplatesHandler() throws TransformerConfigurationException {
      return this.delegate.newTemplatesHandler();
   }

   public TransformerHandler newTransformerHandler() throws TransformerConfigurationException {
      return this.delegate.newTransformerHandler();
   }

   public TransformerHandler newTransformerHandler(Source src) throws TransformerConfigurationException {
      return this.delegate.newTransformerHandler(src);
   }

   public TransformerHandler newTransformerHandler(Templates templates) throws TransformerConfigurationException {
      return this.delegate.newTransformerHandler(templates);
   }

   public XMLFilter newXMLFilter(Source src) throws TransformerConfigurationException {
      return this.delegate.newXMLFilter(src);
   }

   public XMLFilter newXMLFilter(Templates templates) throws TransformerConfigurationException {
      return this.delegate.newXMLFilter(templates);
   }

   public void setFeature(String s, boolean b) {
      try {
         this.delegate.setFeature(s, b);
      } catch (TransformerConfigurationException var4) {
         throw new UnsupportedOperationException(var4);
      }
   }
}
