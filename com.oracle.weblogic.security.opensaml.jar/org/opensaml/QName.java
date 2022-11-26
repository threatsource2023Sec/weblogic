package org.opensaml;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class QName {
   private String namespace = null;
   private String localName = null;

   public QName(String var1, String var2) {
      this.namespace = var1;
      this.localName = var2;
   }

   public static QName getQNameAttribute(Element var0, String var1, String var2) {
      String var3 = var0.getAttributeNS(var1, var2);
      return var3 == null ? null : new QName(getNamespaceForQName(var3, var0), var3.substring(var3.indexOf(58) + 1));
   }

   public static QName getQNameTextNode(Text var0) {
      String var1 = var0.getNodeValue();
      Node var2 = var0.getParentNode();
      return var1 != null && var2 != null && var2.getNodeType() == 1 ? new QName(getNamespaceForQName(var1, (Element)var2), var1.substring(var1.indexOf(58) + 1)) : null;
   }

   public static String getNamespaceForQName(String var0, Element var1) {
      String var2 = "";
      if (var0 != null && var0.indexOf(58) >= 0) {
         var2 = var0.substring(0, var0.indexOf(58));
      }

      return getNamespaceForPrefix(var2, var1);
   }

   public static String getNamespaceForPrefix(String var0, Element var1) {
      Object var2 = var1;
      String var3 = null;
      if (var0 != null) {
         if (var0.equals("xml")) {
            return "http://www.w3.org/XML/1998/namespace";
         }

         if (var0.equals("xmlns")) {
            return "http://www.w3.org/2000/xmlns/";
         }
      }

      while((var3 == null || var3.length() == 0) && var2 != null && ((Node)var2).getNodeType() == 1) {
         var3 = ((Element)var2).getAttributeNS("http://www.w3.org/2000/xmlns/", var0 != null ? var0 : "xmlns");
         var2 = ((Node)var2).getParentNode();
      }

      return var3;
   }

   public String getNamespaceURI() {
      return this.namespace;
   }

   public String getLocalName() {
      return this.localName;
   }

   public boolean equals(Object var1) {
      return var1 instanceof QName && XML.safeCompare(this.namespace, ((QName)var1).getNamespaceURI()) && XML.safeCompare(this.localName, ((QName)var1).getLocalName());
   }

   public int hashCode() {
      return (this.namespace == null ? 0 : this.namespace.hashCode()) + (this.localName == null ? 0 : this.localName.hashCode());
   }

   public String toString() {
      return '{' + (this.namespace == null ? "none" : this.namespace) + "}:" + this.localName;
   }
}
