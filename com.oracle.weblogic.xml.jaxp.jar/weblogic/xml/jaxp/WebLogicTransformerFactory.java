package weblogic.xml.jaxp;

import java.util.Locale;
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
import weblogic.utils.Debug;
import weblogic.xml.util.XMLConstants;

public class WebLogicTransformerFactory extends SAXTransformerFactory implements XMLConstants {
   private static final boolean debug = Boolean.getBoolean("weblogic.xml.debug");
   private static boolean IBM;
   private SAXTransformerFactory delegate;
   String[] factories = new String[]{"javax.xml.transform.TransformerFactory"};

   public WebLogicTransformerFactory() {
      this.delegate = (SAXTransformerFactory)Utils.getDelegate(this.factories);
      if (debug) {
         Debug.say("WebLogicTransformerFactory is delegating to " + this.delegate.getClass());
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

   static {
      IBM = System.getProperty("java.vendor", "unknown").toLowerCase(Locale.ENGLISH).indexOf("ibm") >= 0;
   }
}
