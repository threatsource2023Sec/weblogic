package org.opensaml;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class SAMLAttribute extends SAMLObject implements Cloneable {
   private static Hashtable attributeMap = new Hashtable();
   protected String name = null;
   protected String namespace = null;
   protected QName type = null;
   protected long lifetime = 0L;
   protected ArrayList values = new ArrayList();

   public static void regFactory(String var0, String var1, String var2) {
      attributeMap.put(var1 + "!!" + var0, var2);
   }

   public static void unregFactory(String var0, String var1) {
      attributeMap.remove(var1 + "!!" + var0);
   }

   public static SAMLAttribute getInstance(Element var0) throws SAMLException {
      if (var0 == null) {
         throw new MalformedException(SAMLException.RESPONDER, "SAMLAttribute.getInstance() given an empty DOM");
      } else {
         String var1 = var0.getAttributeNS((String)null, "AttributeName");
         String var2 = var0.getAttributeNS((String)null, "AttributeNamespace");
         if (var1 != null && var2 != null) {
            String var3 = (String)attributeMap.get(var2 + "!!" + var1);
            if (var3 == null) {
               return new SAMLAttribute(var0);
            } else {
               try {
                  Class var4 = Class.forName(var3);
                  Class[] var13 = new Class[]{Class.forName("org.w3c.dom.Element")};
                  Object[] var6 = new Object[]{var0};
                  Constructor var7 = var4.getDeclaredConstructor(var13);
                  return (SAMLAttribute)var7.newInstance(var6);
               } catch (ClassNotFoundException var8) {
                  throw new SAMLException(SAMLException.REQUESTER, "SAMLAttribute.getInstance() unable to locate implementation class for attribute", var8);
               } catch (NoSuchMethodException var9) {
                  throw new SAMLException(SAMLException.REQUESTER, "SAMLAttribute.getInstance() unable to bind to constructor for attribute", var9);
               } catch (InstantiationException var10) {
                  throw new SAMLException(SAMLException.REQUESTER, "SAMLAttribute.getInstance() unable to build implementation object for attribute", var10);
               } catch (IllegalAccessException var11) {
                  throw new SAMLException(SAMLException.REQUESTER, "SAMLAttribute.getInstance() unable to access implementation of attribute", var11);
               } catch (InvocationTargetException var12) {
                  var12.printStackTrace();
                  Throwable var5 = var12.getTargetException();
                  if (var5 instanceof SAMLException) {
                     throw (SAMLException)var5;
                  } else {
                     throw new SAMLException(SAMLException.REQUESTER, "SAMLAttribute.getInstance() caught unknown exception while building attribute object: " + var5.getMessage());
                  }
               }
            }
         } else {
            throw new MalformedException(SAMLException.RESPONDER, "SAMLAttribute.getInstance() can't find AttributeName or Namespace on root element");
         }
      }
   }

   public static SAMLAttribute getInstance(InputStream var0) throws SAMLException {
      try {
         Document var1 = XML.parserPool.parse(var0);
         return getInstance(var1.getDocumentElement());
      } catch (SAXException var2) {
         logDebug("SAMLAttribute.getInstance() caught an exception while parsing a stream:\n" + var2.getMessage());
         throw new MalformedException("SAMLAttribute.getInstance() caught exception while parsing a stream", var2);
      } catch (IOException var3) {
         logDebug("SAMLAttribute.getInstance() caught an exception while parsing a stream:\n" + var3.getMessage());
         throw new MalformedException("SAMLAttribute.getInstance() caught exception while parsing a stream", var3);
      }
   }

   protected String computeTypeDecl(Element var1) {
      String var2 = null;
      var1.removeAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:typens");
      if (this.type != null) {
         String var3;
         if ("http://www.w3.org/2001/XMLSchema".equals(this.type.getNamespaceURI())) {
            var3 = "xsd";
         } else {
            var1.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:typens", this.type.getNamespaceURI());
            var3 = "typens";
         }

         var2 = var3 + ":" + this.type.getLocalName();
      }

      return var2;
   }

   protected void valueToDOM(Object var1, Element var2) throws SAMLException {
      var2.appendChild(var2.getOwnerDocument().createTextNode(var1.toString()));
   }

   public SAMLAttribute() {
   }

   public SAMLAttribute(String var1, String var2, QName var3, long var4, Collection var6) throws SAMLException {
      this.name = var1;
      this.namespace = var2;
      this.type = var3;
      this.lifetime = var4;
      if (var6 != null) {
         this.values.addAll(var6);
      }

   }

   public SAMLAttribute(Element var1) throws SAMLException {
      this.fromDOM(var1);
   }

   public SAMLAttribute(InputStream var1) throws SAMLException {
      this.fromDOM(fromStream(var1));
   }

   public void fromDOM(Element var1) throws SAMLException {
      super.fromDOM(var1);
      if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "Attribute")) {
         throw new MalformedException("SAMLAttribute.fromDOM() requires saml:Attribute at root");
      } else {
         this.name = var1.getAttributeNS((String)null, "AttributeName");
         this.namespace = var1.getAttributeNS((String)null, "AttributeNamespace");

         for(Element var2 = XML.getFirstChildElement(var1, "urn:oasis:names:tc:SAML:1.0:assertion", "AttributeValue"); var2 != null; var2 = XML.getNextSiblingElement(var2)) {
            if (this.type == null) {
               this.type = QName.getQNameAttribute(var2, "http://www.w3.org/2001/XMLSchema-instance", "type");
            }

            if (this.accept(var2)) {
               this.addValue(var2);
            }
         }

         this.checkValidity();
      }
   }

   public String getName() {
      return this.name;
   }

   public void setName(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("name cannot be null");
      } else {
         this.name = var1;
         if (this.root != null) {
            ((Element)this.root).getAttributeNodeNS((String)null, "AttributeName").setNodeValue(var1);
         }

      }
   }

   public String getNamespace() {
      return this.namespace;
   }

   public void setNamespace(String var1) {
      if (XML.isEmpty(var1)) {
         throw new IllegalArgumentException("namespace cannot be null");
      } else {
         this.namespace = var1;
         if (this.root != null) {
            ((Element)this.root).getAttributeNodeNS((String)null, "AttributeNamespace").setNodeValue(var1);
         }

      }
   }

   public QName getType() {
      return this.type;
   }

   public void setType(QName var1) {
      this.type = var1;
      if (this.root != null) {
         String var2 = this.computeTypeDecl((Element)this.root);

         for(Element var3 = XML.getFirstChildElement(this.root); var3 != null; var3 = XML.getNextSiblingElement(var3)) {
            var3.removeAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:type");
            if (var2 != null) {
               var3.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:type", var2);
            }
         }
      }

   }

   public long getLifetime() {
      return this.lifetime;
   }

   public void setLifetime(long var1) {
      this.lifetime = var1;
   }

   public Iterator getValues() {
      return this.values.iterator();
   }

   public boolean accept(Element var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("e cannot be null");
      } else {
         return true;
      }
   }

   public boolean addValue(Element var1) {
      Node var2 = var1.getFirstChild();
      if (this.accept(var1)) {
         if (var2 != null && var2.getNodeType() == 3) {
            this.values.add(var2.getNodeValue());
            return true;
         }

         if (var2 == null) {
            this.values.add("");
            return true;
         }

         logDebug("rejecting an AttributeValue without a simple text node");
      }

      return false;
   }

   public void setValues(Collection var1) throws SAMLException {
      while(this.values.size() > 0) {
         this.removeValue(0);
      }

      if (var1 != null) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.addValue(var2.next());
         }
      }

   }

   public void addValue(Object var1) throws SAMLException {
      if (var1 != null) {
         if (this.root != null) {
            String var2 = this.computeTypeDecl((Element)this.root);
            Element var3 = this.root.getOwnerDocument().createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeValue");
            if (var2 != null) {
               var3.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:type", var2);
            }

            this.valueToDOM(var1, var3);
            this.root.appendChild(var3);
         }

         this.values.add(var1);
      } else {
         this.values.add("");
      }

   }

   public void removeValue(int var1) throws IndexOutOfBoundsException {
      this.values.remove(var1);
      if (this.root != null) {
         Element var2;
         for(var2 = XML.getFirstChildElement(this.root); var2 != null && var1 > 0; --var1) {
            var2 = XML.getNextSiblingElement(var2);
         }

         if (var2 == null) {
            throw new IndexOutOfBoundsException();
         }

         this.root.removeChild(var2);
      }

   }

   public Node toDOM(Document var1, boolean var2) throws SAMLException {
      if ((this.root = super.toDOM(var1, var2)) != null) {
         if (var2) {
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            ((Element)this.root).setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
         }

         return this.root;
      } else {
         Element var3 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "Attribute");
         if (var2) {
            var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "urn:oasis:names:tc:SAML:1.0:assertion");
         }

         var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
         var3.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
         var3.setAttributeNS((String)null, "AttributeName", this.name);
         var3.setAttributeNS((String)null, "AttributeNamespace", this.namespace);
         String var4 = this.computeTypeDecl(var3);
         Iterator var5 = this.values.iterator();

         while(var5.hasNext()) {
            Element var6 = var1.createElementNS("urn:oasis:names:tc:SAML:1.0:assertion", "AttributeValue");
            if (var4 != null) {
               var6.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:type", var4);
            }

            this.valueToDOM(var5.next(), var6);
            var3.appendChild(var6);
         }

         return this.root = var3;
      }
   }

   public void checkValidity() throws SAMLException {
      if (XML.isEmpty(this.name) || XML.isEmpty(this.namespace) || this.values.size() == 0) {
         throw new MalformedException(SAMLException.RESPONDER, "Attribute invalid, requires name and namespace, and at least one value");
      }
   }

   public Object clone() throws CloneNotSupportedException {
      SAMLAttribute var1 = (SAMLAttribute)super.clone();
      var1.values = (ArrayList)this.values.clone();
      return var1;
   }
}
