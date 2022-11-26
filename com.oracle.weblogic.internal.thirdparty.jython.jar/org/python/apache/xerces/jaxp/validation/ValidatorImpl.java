package org.python.apache.xerces.jaxp.validation;

import java.io.IOException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Validator;
import org.python.apache.xerces.util.SAXMessageFormatter;
import org.python.apache.xerces.xni.parser.XMLConfigurationException;
import org.python.apache.xerces.xs.AttributePSVI;
import org.python.apache.xerces.xs.ElementPSVI;
import org.python.apache.xerces.xs.PSVIProvider;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

final class ValidatorImpl extends Validator implements PSVIProvider {
   private static final String JAXP_SOURCE_RESULT_FEATURE_PREFIX = "http://javax.xml.transform";
   private static final String CURRENT_ELEMENT_NODE = "http://apache.org/xml/properties/dom/current-element-node";
   private final XMLSchemaValidatorComponentManager fComponentManager;
   private ValidatorHandlerImpl fSAXValidatorHelper;
   private DOMValidatorHelper fDOMValidatorHelper;
   private StAXValidatorHelper fStAXValidatorHelper;
   private StreamValidatorHelper fStreamValidatorHelper;
   private boolean fConfigurationChanged = false;
   private boolean fErrorHandlerChanged = false;
   private boolean fResourceResolverChanged = false;

   public ValidatorImpl(XSGrammarPoolContainer var1) {
      this.fComponentManager = new XMLSchemaValidatorComponentManager(var1);
      this.setErrorHandler((ErrorHandler)null);
      this.setResourceResolver((LSResourceResolver)null);
   }

   public void validate(Source var1, Result var2) throws SAXException, IOException {
      if (var1 instanceof SAXSource) {
         if (this.fSAXValidatorHelper == null) {
            this.fSAXValidatorHelper = new ValidatorHandlerImpl(this.fComponentManager);
         }

         this.fSAXValidatorHelper.validate(var1, var2);
      } else if (var1 instanceof DOMSource) {
         if (this.fDOMValidatorHelper == null) {
            this.fDOMValidatorHelper = new DOMValidatorHelper(this.fComponentManager);
         }

         this.fDOMValidatorHelper.validate(var1, var2);
      } else if (var1 instanceof StAXSource) {
         if (this.fStAXValidatorHelper == null) {
            this.fStAXValidatorHelper = new StAXValidatorHelper(this.fComponentManager);
         }

         this.fStAXValidatorHelper.validate(var1, var2);
      } else {
         if (!(var1 instanceof StreamSource)) {
            if (var1 == null) {
               throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceParameterNull", (Object[])null));
            }

            throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "SourceNotAccepted", new Object[]{var1.getClass().getName()}));
         }

         if (this.fStreamValidatorHelper == null) {
            this.fStreamValidatorHelper = new StreamValidatorHelper(this.fComponentManager);
         }

         this.fStreamValidatorHelper.validate(var1, var2);
      }

   }

   public void setErrorHandler(ErrorHandler var1) {
      this.fErrorHandlerChanged = var1 != null;
      this.fComponentManager.setErrorHandler(var1);
   }

   public ErrorHandler getErrorHandler() {
      return this.fComponentManager.getErrorHandler();
   }

   public void setResourceResolver(LSResourceResolver var1) {
      this.fResourceResolverChanged = var1 != null;
      this.fComponentManager.setResourceResolver(var1);
   }

   public LSResourceResolver getResourceResolver() {
      return this.fComponentManager.getResourceResolver();
   }

   public boolean getFeature(String var1) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (var1 == null) {
         throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "FeatureNameNull", (Object[])null));
      } else if (var1.startsWith("http://javax.xml.transform") && (var1.equals("http://javax.xml.transform.stream.StreamSource/feature") || var1.equals("http://javax.xml.transform.sax.SAXSource/feature") || var1.equals("http://javax.xml.transform.dom.DOMSource/feature") || var1.equals("http://javax.xml.transform.stax.StAXSource/feature") || var1.equals("http://javax.xml.transform.stream.StreamResult/feature") || var1.equals("http://javax.xml.transform.sax.SAXResult/feature") || var1.equals("http://javax.xml.transform.dom.DOMResult/feature") || var1.equals("http://javax.xml.transform.stax.StAXResult/feature"))) {
         return true;
      } else {
         try {
            return this.fComponentManager.getFeature(var1);
         } catch (XMLConfigurationException var4) {
            String var3 = var4.getIdentifier();
            if (var4.getType() == 0) {
               throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-recognized", new Object[]{var3}));
            } else {
               throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-supported", new Object[]{var3}));
            }
         }
      }
   }

   public void setFeature(String var1, boolean var2) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (var1 == null) {
         throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "FeatureNameNull", (Object[])null));
      } else if (!var1.startsWith("http://javax.xml.transform") || !var1.equals("http://javax.xml.transform.stream.StreamSource/feature") && !var1.equals("http://javax.xml.transform.sax.SAXSource/feature") && !var1.equals("http://javax.xml.transform.dom.DOMSource/feature") && !var1.equals("http://javax.xml.transform.stax.StAXSource/feature") && !var1.equals("http://javax.xml.transform.stream.StreamResult/feature") && !var1.equals("http://javax.xml.transform.sax.SAXResult/feature") && !var1.equals("http://javax.xml.transform.dom.DOMResult/feature") && !var1.equals("http://javax.xml.transform.stax.StAXResult/feature")) {
         try {
            this.fComponentManager.setFeature(var1, var2);
         } catch (XMLConfigurationException var5) {
            String var4 = var5.getIdentifier();
            if (var5.getType() == 0) {
               throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-recognized", new Object[]{var4}));
            }

            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-not-supported", new Object[]{var4}));
         }

         this.fConfigurationChanged = true;
      } else {
         throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "feature-read-only", new Object[]{var1}));
      }
   }

   public Object getProperty(String var1) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (var1 == null) {
         throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "ProperyNameNull", (Object[])null));
      } else if ("http://apache.org/xml/properties/dom/current-element-node".equals(var1)) {
         return this.fDOMValidatorHelper != null ? this.fDOMValidatorHelper.getCurrentElement() : null;
      } else {
         try {
            return this.fComponentManager.getProperty(var1);
         } catch (XMLConfigurationException var4) {
            String var3 = var4.getIdentifier();
            if (var4.getType() == 0) {
               throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-recognized", new Object[]{var3}));
            } else {
               throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-supported", new Object[]{var3}));
            }
         }
      }
   }

   public void setProperty(String var1, Object var2) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (var1 == null) {
         throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "ProperyNameNull", (Object[])null));
      } else if ("http://apache.org/xml/properties/dom/current-element-node".equals(var1)) {
         throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-read-only", new Object[]{var1}));
      } else {
         try {
            this.fComponentManager.setProperty(var1, var2);
         } catch (XMLConfigurationException var5) {
            String var4 = var5.getIdentifier();
            if (var5.getType() == 0) {
               throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-recognized", new Object[]{var4}));
            }

            throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fComponentManager.getLocale(), "property-not-supported", new Object[]{var4}));
         }

         this.fConfigurationChanged = true;
      }
   }

   public void reset() {
      if (this.fConfigurationChanged) {
         this.fComponentManager.restoreInitialState();
         this.setErrorHandler((ErrorHandler)null);
         this.setResourceResolver((LSResourceResolver)null);
         this.fConfigurationChanged = false;
         this.fErrorHandlerChanged = false;
         this.fResourceResolverChanged = false;
      } else {
         if (this.fErrorHandlerChanged) {
            this.setErrorHandler((ErrorHandler)null);
            this.fErrorHandlerChanged = false;
         }

         if (this.fResourceResolverChanged) {
            this.setResourceResolver((LSResourceResolver)null);
            this.fResourceResolverChanged = false;
         }
      }

   }

   public ElementPSVI getElementPSVI() {
      return this.fSAXValidatorHelper != null ? this.fSAXValidatorHelper.getElementPSVI() : null;
   }

   public AttributePSVI getAttributePSVI(int var1) {
      return this.fSAXValidatorHelper != null ? this.fSAXValidatorHelper.getAttributePSVI(var1) : null;
   }

   public AttributePSVI getAttributePSVIByName(String var1, String var2) {
      return this.fSAXValidatorHelper != null ? this.fSAXValidatorHelper.getAttributePSVIByName(var1, var2) : null;
   }
}
