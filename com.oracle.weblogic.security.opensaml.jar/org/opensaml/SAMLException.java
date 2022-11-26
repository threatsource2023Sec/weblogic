package org.opensaml;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SAMLException extends Exception implements Cloneable {
   public static final QName SUCCESS = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Success");
   public static final QName REQUESTER = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Requester");
   public static final QName RESPONDER = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Responder");
   public static final QName VERSION = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "VersionMismatch");
   protected String msg;
   protected Exception e;
   protected ArrayList codes;
   protected Node root;
   private static final DebugLogger LOGGER = SAMLServicesHelper.getDebugLogger();
   protected SAMLConfig config;

   private static final void logDebug(String var0) {
      if (LOGGER != null && LOGGER.isDebugEnabled()) {
         LOGGER.debug(var0);
      }

   }

   public static SAMLException getInstance(Element var0) throws SAMLException {
      if (var0 == null) {
         throw new MalformedException(RESPONDER, "SAMLException.getInstance() given an empty DOM");
      } else {
         try {
            Element var1 = XML.getLastChildElement(var0, "urn:oasis:names:tc:SAML:1.0:protocol", "StatusDetail");
            if (var1 != null) {
               Element var2 = XML.getFirstChildElement(var1, "http://www.opensaml.org", "ExceptionClass");
               if (var2 != null && var2.getFirstChild() != null && var2.getFirstChild().getNodeType() == 3) {
                  String var3 = var2.getFirstChild().getNodeValue();
                  if (var3 != null && var3.length() > 0) {
                     Class var4 = Class.forName(var3);
                     Class[] var5 = new Class[]{Class.forName("org.w3c.dom.Element")};
                     Object[] var6 = new Object[]{var0};
                     Constructor var7 = var4.getDeclaredConstructor(var5);
                     return (SAMLException)var7.newInstance(var6);
                  }
               }
            }
         } catch (ClassNotFoundException var8) {
            logDebug("SAMLException unable to locate implementation class for exception: " + var8.getMessage());
         } catch (NoSuchMethodException var9) {
            logDebug("SAMLException unable to bind to constructor for exception: " + var9.getMessage());
         } catch (InstantiationException var10) {
            logDebug("SAMLException unable to build implementation object for exception: " + var10.getMessage());
         } catch (IllegalAccessException var11) {
            logDebug("SAMLException unable to access implementation of exception: " + var11.getMessage());
         } catch (InvocationTargetException var12) {
            var12.printStackTrace();
            logDebug("SAMLException caught unknown exception while building exception object: " + var12.getTargetException().getMessage());
         }

         return new SAMLException(var0);
      }
   }

   public static SAMLException getInstance(InputStream var0) throws SAMLException {
      try {
         Document var1 = XML.parserPool.parse(var0);
         return getInstance(var1.getDocumentElement());
      } catch (SAXException var2) {
         logDebug("SAMLException.getInstance() caught an exception while parsing a stream:\n" + var2.getMessage());
         throw new MalformedException("SAMLException.getInstance() caught exception while parsing a stream", var2);
      } catch (IOException var3) {
         logDebug("SAMLException.getInstance() caught an exception while parsing a stream:\n" + var3.getMessage());
         throw new MalformedException("SAMLException.getInstance() caught exception while parsing a stream", var3);
      }
   }

   protected SAMLException(Element var1) throws SAMLException {
      this.msg = null;
      this.e = null;
      this.codes = new ArrayList();
      this.root = null;
      this.config = SAMLConfig.instance();
      this.fromDOM(var1);
   }

   public SAMLException(String var1) {
      super(var1);
      this.msg = null;
      this.e = null;
      this.codes = new ArrayList();
      this.root = null;
      this.config = SAMLConfig.instance();
      this.msg = var1;
   }

   public SAMLException(String var1, Exception var2) {
      super(var1);
      this.msg = null;
      this.e = null;
      this.codes = new ArrayList();
      this.root = null;
      this.config = SAMLConfig.instance();
      this.msg = var1;
      this.e = var2;
   }

   public SAMLException(Collection var1) {
      this.msg = null;
      this.e = null;
      this.codes = new ArrayList();
      this.root = null;
      this.config = SAMLConfig.instance();
      if (var1 != null) {
         this.codes.addAll(var1);
      }

   }

   public SAMLException(Collection var1, String var2) {
      super(var2);
      this.msg = null;
      this.e = null;
      this.codes = new ArrayList();
      this.root = null;
      this.config = SAMLConfig.instance();
      this.msg = var2;
      if (var1 != null) {
         this.codes.addAll(var1);
      }

   }

   public SAMLException(Collection var1, Exception var2) {
      this(var1, (String)null);
      this.e = var2;
   }

   public SAMLException(Collection var1, String var2, Exception var3) {
      this(var1, var2);
      this.e = var3;
   }

   public SAMLException(QName var1) {
      this.msg = null;
      this.e = null;
      this.codes = new ArrayList();
      this.root = null;
      this.config = SAMLConfig.instance();
      if (var1 != null) {
         this.codes.add(var1);
      }

   }

   public SAMLException(QName var1, String var2) {
      this(var2);
      if (var1 != null) {
         this.codes.add(var1);
      }

   }

   public SAMLException(QName var1, Exception var2) {
      this(var1, (String)null);
      this.e = var2;
   }

   public SAMLException(QName var1, String var2, Exception var3) {
      this(var1, var2);
      this.e = var3;
   }

   public void fromDOM(Element var1) throws SAMLException {
      if (var1 == null) {
         throw new MalformedException("SAMLException.fromDOM() given an empty DOM");
      } else {
         this.root = var1;
         if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:protocol", "Status")) {
            throw new MalformedException(RESPONDER, "SAMLException.fromDOM() requires samlp:Status at root");
         } else {
            Element var2 = XML.getFirstChildElement(var1, "urn:oasis:names:tc:SAML:1.0:protocol", "StatusMessage");
            if (var2 != null && var2.getFirstChild() != null) {
               this.msg = var2.getFirstChild().getNodeValue();
            }

            NodeList var3 = var1.getElementsByTagNameNS("urn:oasis:names:tc:SAML:1.0:protocol", "StatusCode");

            for(int var4 = 0; var3 != null && var4 < var3.getLength(); ++var4) {
               QName var5 = QName.getQNameAttribute((Element)var3.item(var4), (String)null, "Value");
               if (var5 == null) {
                  throw new MalformedException(RESPONDER, "SAMLException.fromDOM() unable to evaluate QName Value");
               }

               this.codes.add(var5);
            }

         }
      }
   }

   public void toStream(OutputStream var1) throws IOException, SAMLException {
      XML.outputNode(this.toDOM(), var1);
   }

   public Node toDOM(Document var1, boolean var2) throws SAMLException {
      if (this.root != null) {
         if (this.root.getOwnerDocument() != var1) {
            this.root = var1.adoptNode(this.root);
         }
      } else {
         Element var3 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:protocol", "Status");
         var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:samlp", "urn:oasis:names:tc:SAML:1.0:protocol");
         Element var4;
         if (this.codes != null && !this.codes.isEmpty()) {
            Object var9 = var3;

            Element var7;
            for(Iterator var5 = this.codes.iterator(); var5.hasNext(); var9 = ((Node)var9).appendChild(var7)) {
               QName var6 = (QName)var5.next();
               var7 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:protocol", "StatusCode");
               String var8 = var6.getNamespaceURI();
               if (!var8.equals("urn:oasis:names:tc:SAML:1.0:protocol")) {
                  var7.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:code", var8);
                  var8 = "code:";
               } else {
                  var8 = "samlp:";
               }

               var7.setAttributeNS((String)null, "Value", var8 + var6.getLocalName());
            }
         } else {
            var4 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:protocol", "StatusCode");
            var4.setAttributeNS((String)null, "Value", RESPONDER.getLocalName());
            var3.appendChild(var4);
         }

         if (!XML.isEmpty(this.getMessage())) {
            var4 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:protocol", "StatusMessage");
            var4.appendChild(var1.createTextNode(this.getMessage()));
            var3.appendChild(var4);
         }

         if (!(this instanceof SAMLException)) {
            var4 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:protocol", "StatusDetail");
            var4.appendChild(var1.createElementNS("http://www.opensaml.org", "ExceptionClass")).appendChild(var1.createTextNode(this.getClass().getName()));
         }

         this.root = var3;
      }

      if (var2) {
         ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:protocol");
      }

      return this.root;
   }

   public Node toDOM(boolean var1) throws SAMLException {
      return this.root != null ? this.root : this.toDOM(XML.parserPool.newDocument(), var1);
   }

   public Node toDOM(Document var1) throws SAMLException {
      return this.toDOM(var1, true);
   }

   public Node toDOM() throws SAMLException {
      return this.toDOM(true);
   }

   public Iterator getCodes() {
      return this.codes.iterator();
   }

   public String getMessage() {
      if (this.msg != null && this.e != null) {
         return this.msg + " (wrapped: " + this.e.getMessage() + ')';
      } else {
         return this.e != null ? "(wrapped: " + this.e.getMessage() + ")" : this.msg;
      }
   }

   public Exception getException() {
      return this.e;
   }

   public String toString() {
      return this.e != null ? this.e.toString() : super.toString();
   }

   public Object clone() throws CloneNotSupportedException {
      SAMLException var1 = (SAMLException)super.clone();
      var1.root = null;
      var1.codes = (ArrayList)this.codes.clone();
      return var1;
   }
}
