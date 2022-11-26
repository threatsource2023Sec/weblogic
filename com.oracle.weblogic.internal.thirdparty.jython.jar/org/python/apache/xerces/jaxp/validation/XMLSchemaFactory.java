package org.python.apache.xerces.jaxp.validation;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import javax.xml.stream.XMLEventReader;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.python.apache.xerces.impl.xs.XMLSchemaLoader;
import org.python.apache.xerces.util.DOMEntityResolverWrapper;
import org.python.apache.xerces.util.DOMInputSource;
import org.python.apache.xerces.util.ErrorHandlerWrapper;
import org.python.apache.xerces.util.SAXInputSource;
import org.python.apache.xerces.util.SAXMessageFormatter;
import org.python.apache.xerces.util.SecurityManager;
import org.python.apache.xerces.util.StAXInputSource;
import org.python.apache.xerces.util.XMLGrammarPoolImpl;
import org.python.apache.xerces.xni.XNIException;
import org.python.apache.xerces.xni.grammars.Grammar;
import org.python.apache.xerces.xni.grammars.XMLGrammarDescription;
import org.python.apache.xerces.xni.grammars.XMLGrammarPool;
import org.python.apache.xerces.xni.parser.XMLConfigurationException;
import org.python.apache.xerces.xni.parser.XMLInputSource;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;

public final class XMLSchemaFactory extends SchemaFactory {
   private static final String JAXP_SOURCE_FEATURE_PREFIX = "http://javax.xml.transform";
   private static final String SCHEMA_FULL_CHECKING = "http://apache.org/xml/features/validation/schema-full-checking";
   private static final String USE_GRAMMAR_POOL_ONLY = "http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only";
   private static final String XMLGRAMMAR_POOL = "http://apache.org/xml/properties/internal/grammar-pool";
   private static final String SECURITY_MANAGER = "http://apache.org/xml/properties/security-manager";
   private final XMLSchemaLoader fXMLSchemaLoader = new XMLSchemaLoader();
   private ErrorHandler fErrorHandler;
   private LSResourceResolver fLSResourceResolver;
   private final DOMEntityResolverWrapper fDOMEntityResolverWrapper = new DOMEntityResolverWrapper();
   private final ErrorHandlerWrapper fErrorHandlerWrapper = new ErrorHandlerWrapper(DraconianErrorHandler.getInstance());
   private SecurityManager fSecurityManager;
   private final XMLGrammarPoolWrapper fXMLGrammarPoolWrapper = new XMLGrammarPoolWrapper();
   private boolean fUseGrammarPoolOnly;

   public XMLSchemaFactory() {
      this.fXMLSchemaLoader.setFeature("http://apache.org/xml/features/validation/schema-full-checking", true);
      this.fXMLSchemaLoader.setProperty("http://apache.org/xml/properties/internal/grammar-pool", this.fXMLGrammarPoolWrapper);
      this.fXMLSchemaLoader.setEntityResolver(this.fDOMEntityResolverWrapper);
      this.fXMLSchemaLoader.setErrorHandler(this.fErrorHandlerWrapper);
      this.fUseGrammarPoolOnly = true;
   }

   public boolean isSchemaLanguageSupported(String var1) {
      if (var1 == null) {
         throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "SchemaLanguageNull", (Object[])null));
      } else if (var1.length() == 0) {
         throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "SchemaLanguageLengthZero", (Object[])null));
      } else {
         return var1.equals("http://www.w3.org/2001/XMLSchema");
      }
   }

   public LSResourceResolver getResourceResolver() {
      return this.fLSResourceResolver;
   }

   public void setResourceResolver(LSResourceResolver var1) {
      this.fLSResourceResolver = var1;
      this.fDOMEntityResolverWrapper.setEntityResolver(var1);
      this.fXMLSchemaLoader.setEntityResolver(this.fDOMEntityResolverWrapper);
   }

   public ErrorHandler getErrorHandler() {
      return this.fErrorHandler;
   }

   public void setErrorHandler(ErrorHandler var1) {
      this.fErrorHandler = var1;
      this.fErrorHandlerWrapper.setErrorHandler((ErrorHandler)(var1 != null ? var1 : DraconianErrorHandler.getInstance()));
      this.fXMLSchemaLoader.setErrorHandler(this.fErrorHandlerWrapper);
   }

   public Schema newSchema(Source[] var1) throws SAXException {
      XMLGrammarPoolImplExtension var2 = new XMLGrammarPoolImplExtension();
      this.fXMLGrammarPoolWrapper.setGrammarPool(var2);
      XMLInputSource[] var3 = new XMLInputSource[var1.length];

      StreamSource var6;
      for(int var4 = 0; var4 < var1.length; ++var4) {
         Source var5 = var1[var4];
         String var8;
         if (var5 instanceof StreamSource) {
            var6 = (StreamSource)var5;
            String var7 = var6.getPublicId();
            var8 = var6.getSystemId();
            InputStream var9 = var6.getInputStream();
            Reader var10 = var6.getReader();
            XMLInputSource var11 = new XMLInputSource(var7, var8, (String)null);
            var11.setByteStream(var9);
            var11.setCharacterStream(var10);
            var3[var4] = var11;
         } else if (var5 instanceof SAXSource) {
            SAXSource var15 = (SAXSource)var5;
            InputSource var18 = var15.getInputSource();
            if (var18 == null) {
               throw new SAXException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "SAXSourceNullInputSource", (Object[])null));
            }

            var3[var4] = new SAXInputSource(var15.getXMLReader(), var18);
         } else if (var5 instanceof DOMSource) {
            DOMSource var16 = (DOMSource)var5;
            Node var20 = var16.getNode();
            var8 = var16.getSystemId();
            var3[var4] = new DOMInputSource(var20, var8);
         } else {
            if (!(var5 instanceof StAXSource)) {
               if (var5 == null) {
                  throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "SchemaSourceArrayMemberNull", (Object[])null));
               }

               throw new IllegalArgumentException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "SchemaFactorySourceUnrecognized", new Object[]{var5.getClass().getName()}));
            }

            StAXSource var17 = (StAXSource)var5;
            XMLEventReader var21 = var17.getXMLEventReader();
            if (var21 != null) {
               var3[var4] = new StAXInputSource(var21);
            } else {
               var3[var4] = new StAXInputSource(var17.getXMLStreamReader());
            }
         }
      }

      try {
         this.fXMLSchemaLoader.loadGrammar(var3);
      } catch (XNIException var12) {
         throw Util.toSAXException(var12);
      } catch (IOException var13) {
         SAXParseException var22 = new SAXParseException(var13.getMessage(), (Locator)null, var13);
         if (this.fErrorHandler != null) {
            this.fErrorHandler.error(var22);
         }

         throw var22;
      }

      this.fXMLGrammarPoolWrapper.setGrammarPool((XMLGrammarPool)null);
      int var14 = var2.getGrammarCount();
      var6 = null;
      Object var19;
      if (this.fUseGrammarPoolOnly) {
         if (var14 > 1) {
            var19 = new XMLSchema(new ReadOnlyGrammarPool(var2));
         } else if (var14 == 1) {
            Grammar[] var23 = var2.retrieveInitialGrammarSet("http://www.w3.org/2001/XMLSchema");
            var19 = new SimpleXMLSchema(var23[0]);
         } else {
            var19 = new EmptyXMLSchema();
         }
      } else {
         var19 = new XMLSchema(new ReadOnlyGrammarPool(var2), false);
      }

      this.propagateFeatures((AbstractXMLSchema)var19);
      return (Schema)var19;
   }

   public Schema newSchema() throws SAXException {
      WeakReferenceXMLSchema var1 = new WeakReferenceXMLSchema();
      this.propagateFeatures(var1);
      return var1;
   }

   public Schema newSchema(XMLGrammarPool var1) throws SAXException {
      XMLSchema var2 = this.fUseGrammarPoolOnly ? new XMLSchema(new ReadOnlyGrammarPool(var1)) : new XMLSchema(var1, false);
      this.propagateFeatures(var2);
      return var2;
   }

   public boolean getFeature(String var1) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (var1 == null) {
         throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "FeatureNameNull", (Object[])null));
      } else if (var1.startsWith("http://javax.xml.transform") && (var1.equals("http://javax.xml.transform.stream.StreamSource/feature") || var1.equals("http://javax.xml.transform.sax.SAXSource/feature") || var1.equals("http://javax.xml.transform.dom.DOMSource/feature") || var1.equals("http://javax.xml.transform.stax.StAXSource/feature"))) {
         return true;
      } else if (var1.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
         return this.fSecurityManager != null;
      } else if (var1.equals("http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only")) {
         return this.fUseGrammarPoolOnly;
      } else {
         try {
            return this.fXMLSchemaLoader.getFeature(var1);
         } catch (XMLConfigurationException var4) {
            String var3 = var4.getIdentifier();
            if (var4.getType() == 0) {
               throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "feature-not-recognized", new Object[]{var3}));
            } else {
               throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "feature-not-supported", new Object[]{var3}));
            }
         }
      }
   }

   public Object getProperty(String var1) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (var1 == null) {
         throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "ProperyNameNull", (Object[])null));
      } else if (var1.equals("http://apache.org/xml/properties/security-manager")) {
         return this.fSecurityManager;
      } else if (var1.equals("http://apache.org/xml/properties/internal/grammar-pool")) {
         throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-supported", new Object[]{var1}));
      } else {
         try {
            return this.fXMLSchemaLoader.getProperty(var1);
         } catch (XMLConfigurationException var4) {
            String var3 = var4.getIdentifier();
            if (var4.getType() == 0) {
               throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-recognized", new Object[]{var3}));
            } else {
               throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-supported", new Object[]{var3}));
            }
         }
      }
   }

   public void setFeature(String var1, boolean var2) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (var1 == null) {
         throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "FeatureNameNull", (Object[])null));
      } else if (var1.startsWith("http://javax.xml.transform") && (var1.equals("http://javax.xml.transform.stream.StreamSource/feature") || var1.equals("http://javax.xml.transform.sax.SAXSource/feature") || var1.equals("http://javax.xml.transform.dom.DOMSource/feature") || var1.equals("http://javax.xml.transform.stax.StAXSource/feature"))) {
         throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "feature-read-only", new Object[]{var1}));
      } else if (var1.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
         this.fSecurityManager = var2 ? new SecurityManager() : null;
         this.fXMLSchemaLoader.setProperty("http://apache.org/xml/properties/security-manager", this.fSecurityManager);
      } else if (var1.equals("http://apache.org/xml/features/internal/validation/schema/use-grammar-pool-only")) {
         this.fUseGrammarPoolOnly = var2;
      } else {
         try {
            this.fXMLSchemaLoader.setFeature(var1, var2);
         } catch (XMLConfigurationException var5) {
            String var4 = var5.getIdentifier();
            if (var5.getType() == 0) {
               throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "feature-not-recognized", new Object[]{var4}));
            } else {
               throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "feature-not-supported", new Object[]{var4}));
            }
         }
      }
   }

   public void setProperty(String var1, Object var2) throws SAXNotRecognizedException, SAXNotSupportedException {
      if (var1 == null) {
         throw new NullPointerException(JAXPValidationMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "ProperyNameNull", (Object[])null));
      } else if (var1.equals("http://apache.org/xml/properties/security-manager")) {
         this.fSecurityManager = (SecurityManager)var2;
         this.fXMLSchemaLoader.setProperty("http://apache.org/xml/properties/security-manager", this.fSecurityManager);
      } else if (var1.equals("http://apache.org/xml/properties/internal/grammar-pool")) {
         throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-supported", new Object[]{var1}));
      } else {
         try {
            this.fXMLSchemaLoader.setProperty(var1, var2);
         } catch (XMLConfigurationException var5) {
            String var4 = var5.getIdentifier();
            if (var5.getType() == 0) {
               throw new SAXNotRecognizedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-recognized", new Object[]{var4}));
            } else {
               throw new SAXNotSupportedException(SAXMessageFormatter.formatMessage(this.fXMLSchemaLoader.getLocale(), "property-not-supported", new Object[]{var4}));
            }
         }
      }
   }

   private void propagateFeatures(AbstractXMLSchema var1) {
      var1.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", this.fSecurityManager != null);
      String[] var2 = this.fXMLSchemaLoader.getRecognizedFeatures();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         boolean var4 = this.fXMLSchemaLoader.getFeature(var2[var3]);
         var1.setFeature(var2[var3], var4);
      }

   }

   static class XMLGrammarPoolWrapper implements XMLGrammarPool {
      private XMLGrammarPool fGrammarPool;

      public Grammar[] retrieveInitialGrammarSet(String var1) {
         return this.fGrammarPool.retrieveInitialGrammarSet(var1);
      }

      public void cacheGrammars(String var1, Grammar[] var2) {
         this.fGrammarPool.cacheGrammars(var1, var2);
      }

      public Grammar retrieveGrammar(XMLGrammarDescription var1) {
         return this.fGrammarPool.retrieveGrammar(var1);
      }

      public void lockPool() {
         this.fGrammarPool.lockPool();
      }

      public void unlockPool() {
         this.fGrammarPool.unlockPool();
      }

      public void clear() {
         this.fGrammarPool.clear();
      }

      void setGrammarPool(XMLGrammarPool var1) {
         this.fGrammarPool = var1;
      }

      XMLGrammarPool getGrammarPool() {
         return this.fGrammarPool;
      }
   }

   static class XMLGrammarPoolImplExtension extends XMLGrammarPoolImpl {
      public XMLGrammarPoolImplExtension() {
      }

      public XMLGrammarPoolImplExtension(int var1) {
         super(var1);
      }

      int getGrammarCount() {
         return this.fGrammarCount;
      }
   }
}
