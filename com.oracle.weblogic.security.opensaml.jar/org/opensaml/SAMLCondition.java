package org.opensaml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public abstract class SAMLCondition extends SAMLObject implements Cloneable {
   protected static Hashtable conditionTypeMap = new Hashtable();

   public static void regFactory(QName var0, String var1) {
      conditionTypeMap.put(var0, var1);
   }

   public static void unregFactory(QName var0) {
      conditionTypeMap.remove(var0);
   }

   public static SAMLCondition getInstance(Element var0) throws SAMLException {
      if (var0 == null) {
         throw new MalformedException(SAMLException.RESPONDER, "SAMLCondition.getInstance() given an empty DOM");
      } else {
         try {
            String var1;
            Class[] var3;
            Object[] var4;
            Constructor var5;
            Class var11;
            if (XML.isElementNamed(var0, "urn:oasis:names:tc:SAML:1.0:assertion", "Condition")) {
               var1 = (String)conditionTypeMap.get(QName.getQNameAttribute(var0, "http://www.w3.org/2001/XMLSchema-instance", "type"));
               if (var1 == null) {
                  throw new UnsupportedExtensionException(SAMLException.RESPONDER, "SAMLCondition.getInstance() unable to locate an implementation of specified condition type");
               } else {
                  var11 = Class.forName(var1);
                  var3 = new Class[]{Class.forName("org.w3c.dom.Element")};
                  var4 = new Object[]{var0};
                  var5 = var11.getDeclaredConstructor(var3);
                  return (SAMLCondition)var5.newInstance(var4);
               }
            } else {
               var1 = (String)conditionTypeMap.get(new QName(var0.getNamespaceURI(), var0.getLocalName()));
               if (var1 == null) {
                  throw new UnsupportedExtensionException(SAMLException.RESPONDER, "SAMLCondition.getInstance() unable to locate an implementation of specified condition type");
               } else {
                  var11 = Class.forName(var1);
                  var3 = new Class[]{Class.forName("org.w3c.dom.Element")};
                  var4 = new Object[]{var0};
                  var5 = var11.getDeclaredConstructor(var3);
                  return (SAMLCondition)var5.newInstance(var4);
               }
            }
         } catch (ClassNotFoundException var6) {
            throw new SAMLException(SAMLException.REQUESTER, "SAMLCondition.getInstance() unable to locate implementation class for condition", var6);
         } catch (NoSuchMethodException var7) {
            throw new SAMLException(SAMLException.REQUESTER, "SAMLCondition.getInstance() unable to bind to constructor for condition", var7);
         } catch (InstantiationException var8) {
            throw new SAMLException(SAMLException.REQUESTER, "SAMLCondition.getInstance() unable to build implementation object for condition", var8);
         } catch (IllegalAccessException var9) {
            throw new SAMLException(SAMLException.REQUESTER, "SAMLCondition.getInstance() unable to access implementation of condition", var9);
         } catch (InvocationTargetException var10) {
            var10.printStackTrace();
            Throwable var2 = var10.getTargetException();
            if (var2 instanceof SAMLException) {
               throw (SAMLException)var2;
            } else {
               throw new SAMLException(SAMLException.REQUESTER, "SAMLCondition.getInstance() caught unknown exception while building condition object: " + var2.getMessage());
            }
         }
      }
   }

   public static SAMLCondition getInstance(InputStream var0) throws SAMLException {
      try {
         Document var1 = XML.parserPool.parse(var0);
         return getInstance(var1.getDocumentElement());
      } catch (SAXException var2) {
         logDebug("SAMLCondition.getInstance() caught an exception while parsing a stream:\n" + var2.getMessage());
         throw new MalformedException("SAMLCondition.getInstance() caught exception while parsing a stream", var2);
      } catch (IOException var3) {
         logDebug("SAMLCondition.getInstance() caught an exception while parsing a stream:\n" + var3.getMessage());
         throw new MalformedException("SAMLCondition.getInstance() caught exception while parsing a stream", var3);
      }
   }
}
