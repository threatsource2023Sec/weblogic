package com.bea.security.saml2.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SAML2AttributeStatementInfo {
   private Collection attributes;

   public SAML2AttributeStatementInfo() {
   }

   public SAML2AttributeStatementInfo(Collection attrs) {
      this.addAttributeInfo(attrs);
   }

   public void addAttributeInfo(SAML2AttributeInfo attr) {
      if (this.isValid(attr)) {
         if (this.attributes == null) {
            this.attributes = new ArrayList();
         }

         this.attributes.add(attr);
      }

   }

   public void addAttributeInfo(Collection attrs) {
      if (attrs != null && attrs.size() > 0) {
         if (this.attributes == null) {
            this.attributes = new ArrayList();
         }

         Iterator var2 = attrs.iterator();

         while(var2.hasNext()) {
            SAML2AttributeInfo attr = (SAML2AttributeInfo)var2.next();
            if (this.isValid(attr)) {
               this.attributes.add(attr);
            }
         }
      }

   }

   public Collection getAttributeInfo() {
      return this.attributes;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      int size = this.attributes == null ? 0 : this.attributes.size();
      builder.append("SAML2AttributeStatement - NumOfAttrs: ").append(size).append("\n");
      if (size < 1) {
         return builder.toString();
      } else {
         Iterator var3 = this.attributes.iterator();

         while(var3.hasNext()) {
            SAML2AttributeInfo attr = (SAML2AttributeInfo)var3.next();
            builder.append(attr).append("\n");
         }

         return builder.toString();
      }
   }

   private boolean isValid(SAML2AttributeInfo attr) {
      String attrName = attr == null ? null : attr.getAttributeName();
      return attrName != null && attrName.trim().length() > 0;
   }
}
