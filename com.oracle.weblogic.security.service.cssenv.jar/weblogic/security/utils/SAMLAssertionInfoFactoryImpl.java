package weblogic.security.utils;

import com.bea.common.security.ApiLogger;
import com.bea.common.security.utils.CSSPlatformProxy;
import com.bea.common.security.utils.SAML2ClassLoader;
import com.bea.common.security.utils.ThreadClassLoaderContextInvocationHandler;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import weblogic.utils.XXEUtils;

class SAMLAssertionInfoFactoryImpl extends SAMLAssertionInfoFactory {
   private static ClassLoader saml2ClassLoader = null;

   public SAMLAssertionInfo getSAMLAssertionInfo(String assertion) throws Exception {
      if (assertion != null && assertion.length() >= 1) {
         String version = getMajorVersion(assertion);
         Object assertionInfo;
         if (this.isSAML1(version)) {
            assertionInfo = new SAMLAssertionInfoImpl(assertion);
         } else {
            if (!this.isSAML2(version)) {
               throw new IllegalArgumentException(ApiLogger.getFailedToGetSAMLAssertionInfo("assertion version not supported"));
            }

            assertionInfo = SAMLAssertionInfoFactoryImpl.SAML2AssertionInfoFactory.newInstance(getSAML2ClassLoader(), assertion, String.class);
         }

         return (SAMLAssertionInfo)assertionInfo;
      } else {
         throw new IllegalArgumentException(ApiLogger.getIllegalArgumentSpecified("getSAMLAssertionInfo", "assertion", "null or empty"));
      }
   }

   public SAMLAssertionInfo getSAMLAssertionInfo(Element assertion) throws Exception {
      if (assertion == null) {
         throw new IllegalArgumentException(ApiLogger.getIllegalArgumentSpecified("getSAMLAssertionInfo", "assertion", "null"));
      } else {
         String version = getMajorVersion(assertion);
         Object assertionInfo;
         if (this.isSAML1(version)) {
            assertionInfo = new SAMLAssertionInfoImpl(assertion);
         } else {
            if (!this.isSAML2(version)) {
               throw new IllegalArgumentException(ApiLogger.getFailedToGetSAMLAssertionInfo("assertion version not supported"));
            }

            assertionInfo = SAMLAssertionInfoFactoryImpl.SAML2AssertionInfoFactory.newInstance(getSAML2ClassLoader(), assertion, Element.class);
         }

         return (SAMLAssertionInfo)assertionInfo;
      }
   }

   private static String getMajorVersion(String assertion) throws IllegalArgumentException {
      Element element;
      try {
         Document doc = parse(new InputSource(new StringReader(assertion)));
         element = doc.getDocumentElement();
      } catch (Exception var3) {
         throw new IllegalArgumentException(ApiLogger.getFailedToGetSAMLAssertionInfo("malformed assertion document"));
      }

      return getMajorVersion(element);
   }

   private static String getMajorVersion(Element assertion) throws IllegalArgumentException {
      String version = assertion.getAttribute("MajorVersion");
      if (isEmpty(version)) {
         version = assertion.getAttribute("Version");
         if (isEmpty(version)) {
            throw new IllegalArgumentException(ApiLogger.getFailedToGetSAMLAssertionInfo("version attribute not found"));
         }
      }

      return version;
   }

   private static boolean isEmpty(String string) {
      boolean empty = true;
      if (string != null) {
         string = string.trim();
         if (string.length() > 0) {
            empty = false;
         }
      }

      return empty;
   }

   private boolean isSAML1(String version) {
      return version.startsWith("1");
   }

   private boolean isSAML2(String version) {
      return version.startsWith("2");
   }

   private static Document parse(InputSource in) throws ParserConfigurationException, SAXException, IOException {
      DocumentBuilderFactory dbf = XXEUtils.createDocumentBuilderFactoryInstance();
      dbf.setIgnoringComments(true);
      DocumentBuilder db = dbf.newDocumentBuilder();
      db.setErrorHandler(new DefaultErrorHandler());
      return db.parse(in);
   }

   private static ClassLoader getSAML2ClassLoader() {
      ClassLoader parent = SAMLAssertionInfoFactoryImpl.class.getClassLoader();
      Class var1 = SAMLAssertionInfoFactoryImpl.class;
      synchronized(SAMLAssertionInfoFactoryImpl.class) {
         if (saml2ClassLoader != null) {
            return saml2ClassLoader;
         } else if (!CSSPlatformProxy.getInstance().isOnWLS()) {
            saml2ClassLoader = parent;
            return saml2ClassLoader;
         } else {
            try {
               saml2ClassLoader = new SAML2ClassLoader(parent, false);
            } catch (Exception var4) {
               throw new RuntimeException(var4);
            }

            return saml2ClassLoader;
         }
      }
   }

   private static class SAML2AssertionInfoFactory {
      private static final String SAML2_AINFO_NAME = "com.bea.common.security.saml2.utils.SAML2AssertionInfoImpl";

      private static SAMLAssertionInfo newInstance(ClassLoader cl, Object assertion, Class parameterType) throws SAXException {
         try {
            Class aInfoClass = Class.forName("com.bea.common.security.saml2.utils.SAML2AssertionInfoImpl", true, cl);
            Constructor ctor = aInfoClass.getConstructor(parameterType);
            Object aInfo = ctor.newInstance(assertion);
            return (SAMLAssertionInfo)Proxy.newProxyInstance(cl, aInfoClass.getInterfaces(), new ThreadClassLoaderContextInvocationHandler(cl, aInfo));
         } catch (InvocationTargetException var6) {
            Throwable t = var6.getCause();
            if (t != null && t instanceof SAXException) {
               throw (SAXException)t;
            } else {
               throw new SAXException(ApiLogger.getFailedToGetSAMLAssertionInfo("InvocationTargetException - " + var6.getMessage()), var6);
            }
         } catch (Exception var7) {
            throw new SAXException(ApiLogger.getFailedToGetSAMLAssertionInfo(var7.getMessage()), var7);
         }
      }
   }

   private static class DefaultErrorHandler implements ErrorHandler {
      private DefaultErrorHandler() {
      }

      public void fatalError(SAXParseException spe) throws SAXException {
         throw spe;
      }

      public void error(SAXParseException spe) throws SAXException {
         throw spe;
      }

      public void warning(SAXParseException spe) throws SAXException {
      }

      // $FF: synthetic method
      DefaultErrorHandler(Object x0) {
         this();
      }
   }
}
