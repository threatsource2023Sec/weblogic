package com.bea.security.saml2.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SAML2AttributeInfo {
   public static final String ATTR_NAME_FORMAT_BASIC = "urn:oasis:names:tc:SAML:2.0:attrname-format:basic";
   private String attributeName;
   private String attributeNameFormat = "urn:oasis:names:tc:SAML:2.0:attrname-format:basic";
   private String attributeFriendlyName;
   private Collection attributeValues;

   public SAML2AttributeInfo() {
   }

   public SAML2AttributeInfo(String attributeName, Collection attributeValues) {
      this.attributeName = attributeName;
      this.addAttributeValues(attributeValues);
   }

   public SAML2AttributeInfo(String attributeName, String attributeValue) {
      this.attributeName = attributeName;
      this.addAttributeValue(attributeValue);
   }

   public SAML2AttributeInfo(String attributeName, String attributeNameFormat, String attributeFriendlyName, Collection attributeValues) {
      this.attributeName = attributeName;
      this.attributeFriendlyName = attributeFriendlyName;
      this.attributeNameFormat = attributeNameFormat;
      this.addAttributeValues(attributeValues);
   }

   public String getAttributeName() {
      return this.attributeName;
   }

   public void setAttributeName(String attributeName) {
      this.attributeName = attributeName;
   }

   public String getAttributeNameFormat() {
      return this.attributeNameFormat;
   }

   public void setAttributeNameFormat(String attributeNameFormat) {
      this.attributeNameFormat = attributeNameFormat;
   }

   public String getAttributeFriendlyName() {
      return this.attributeFriendlyName;
   }

   public void setAttributeFriendlyName(String attributeFriendlyName) {
      this.attributeFriendlyName = attributeFriendlyName;
   }

   public void addAttributeValue(String attributeValue) {
      if (this.attributeValues == null) {
         this.attributeValues = new ArrayList();
      }

      this.attributeValues.add(attributeValue);
   }

   public void addAttributeValues(Collection attributeValues) {
      if (attributeValues != null && attributeValues.size() > 0) {
         if (this.attributeValues == null) {
            this.attributeValues = new ArrayList();
         }

         Iterator var2 = attributeValues.iterator();

         while(var2.hasNext()) {
            String s = (String)var2.next();
            this.attributeValues.add(s);
         }
      }

   }

   public Collection getAttributeValues() {
      return this.attributeValues;
   }

   public String toString() {
      StringBuilder builder = new StringBuilder();
      int attrValueSize = this.attributeValues == null ? 0 : this.attributeValues.size();
      builder.append("    attrName=").append(this.attributeName).append(", attrNameFormat=").append(this.attributeNameFormat).append(", attrFridentlyName=").append(this.attributeFriendlyName).append(", NumOfAttrValues=").append(attrValueSize).append("\n");
      if (attrValueSize < 1) {
         return builder.toString();
      } else {
         Iterator var3 = this.attributeValues.iterator();

         while(var3.hasNext()) {
            String value = (String)var3.next();
            builder.append("      value=").append(value).append("\n");
         }

         return builder.toString();
      }
   }
}
