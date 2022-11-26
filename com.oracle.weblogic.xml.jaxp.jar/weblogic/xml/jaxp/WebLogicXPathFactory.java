package weblogic.xml.jaxp;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;
import weblogic.utils.Debug;

public class WebLogicXPathFactory extends XPathFactory {
   private static final boolean debug = Boolean.getBoolean("weblogic.xml.debug");
   private XPathFactory delegate;
   String[] factories = new String[]{"javax.xml.xpath.XPathFactory"};

   public WebLogicXPathFactory() {
      this.delegate = (XPathFactory)Utils.getDelegate(this.factories);
      if (debug) {
         Debug.say("WebLogicXPathFactory is delegating to " + this.delegate.getClass());
      }

   }

   public boolean isObjectModelSupported(String objectModel) {
      return this.delegate.isObjectModelSupported(objectModel);
   }

   public void setFeature(String name, boolean value) throws XPathFactoryConfigurationException {
      this.delegate.setFeature(name, value);
   }

   public boolean getFeature(String name) throws XPathFactoryConfigurationException {
      return this.delegate.getFeature(name);
   }

   public void setXPathVariableResolver(XPathVariableResolver resolver) {
      this.delegate.setXPathVariableResolver(resolver);
   }

   public void setXPathFunctionResolver(XPathFunctionResolver resolver) {
      this.delegate.setXPathFunctionResolver(resolver);
   }

   public XPath newXPath() {
      return this.delegate.newXPath();
   }
}
