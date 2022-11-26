package com.bea.xbean.common;

import weblogic.xml.stream.XMLName;

public class XmlNameImpl implements XMLName {
   private String namespaceUri = null;
   private String localName = null;
   private String prefix = null;
   private int hash = 0;

   public XmlNameImpl() {
   }

   public XmlNameImpl(String localName) {
      this.localName = localName;
   }

   public XmlNameImpl(String namespaceUri, String localName) {
      this.setNamespaceUri(namespaceUri);
      this.localName = localName;
   }

   public XmlNameImpl(String namespaceUri, String localName, String prefix) {
      this.setNamespaceUri(namespaceUri);
      this.localName = localName;
      this.prefix = prefix;
   }

   public String getNamespaceUri() {
      return this.namespaceUri;
   }

   public String getLocalName() {
      return this.localName;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public void setNamespaceUri(String namespaceUri) {
      this.hash = 0;
      if (namespaceUri == null || !namespaceUri.equals("")) {
         this.namespaceUri = namespaceUri;
      }
   }

   public void setLocalName(String localName) {
      this.localName = localName;
      this.hash = 0;
   }

   public void setPrefix(String prefix) {
      this.prefix = prefix;
   }

   public String getQualifiedName() {
      return this.prefix != null && this.prefix.length() > 0 ? this.prefix + ":" + this.localName : this.localName;
   }

   public String toString() {
      return this.getNamespaceUri() != null ? "['" + this.getNamespaceUri() + "']:" + this.getQualifiedName() : this.getQualifiedName();
   }

   public final int hashCode() {
      int tmp_hash = this.hash;
      if (tmp_hash == 0) {
         tmp_hash = 17;
         if (this.namespaceUri != null) {
            tmp_hash = 37 * tmp_hash + this.namespaceUri.hashCode();
         }

         if (this.localName != null) {
            tmp_hash = 37 * tmp_hash + this.localName.hashCode();
         }

         this.hash = tmp_hash;
      }

      return tmp_hash;
   }

   public final boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof XMLName)) {
         return false;
      } else {
         XMLName name;
         label28: {
            name = (XMLName)obj;
            String lname = this.localName;
            if (lname == null) {
               if (name.getLocalName() == null) {
                  break label28;
               }
            } else if (lname.equals(name.getLocalName())) {
               break label28;
            }

            return false;
         }

         String uri = this.namespaceUri;
         return uri == null ? name.getNamespaceUri() == null : uri.equals(name.getNamespaceUri());
      }
   }
}
