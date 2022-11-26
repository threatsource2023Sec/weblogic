package weblogic.xml.jaxp;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import javax.xml.xpath.XPathFunctionResolver;
import javax.xml.xpath.XPathVariableResolver;
import weblogic.xml.XMLLogger;
import weblogic.xml.registry.RegistryEntityResolver;
import weblogic.xml.registry.XMLRegistryException;

public class RegistryXPathFactory extends XPathFactory {
   private XPathFactory delegate = null;

   public RegistryXPathFactory() {
      try {
         RegistryEntityResolver registry = new RegistryEntityResolver();
         this.delegate = registry.getXPathFactory();
      } catch (XMLRegistryException var2) {
         XMLLogger.logXMLRegistryException(var2.getMessage());
      }

      if (this.delegate == null) {
         this.delegate = new WebLogicXPathFactory();
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
