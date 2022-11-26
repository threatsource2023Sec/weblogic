package org.glassfish.hk2.xml.internal;

public class XmlElementData {
   private final String namespace;
   private final String name;
   private final String alias;
   private final String defaultValue;
   private final Format format;
   private final String type;
   private final boolean isTypeInterface;
   private final String xmlWrapperTag;
   private final boolean required;
   private final String originalMethodName;

   XmlElementData(String namespace, String name, String alias, String defaultValue, Format format, String type, boolean isTypeInterface, String xmlWrapperTag, boolean required, String originalMethodName) {
      this.namespace = namespace;
      this.name = name;
      this.alias = alias;
      this.defaultValue = defaultValue;
      this.format = format;
      this.type = type;
      this.isTypeInterface = isTypeInterface;
      this.xmlWrapperTag = xmlWrapperTag;
      this.required = required;
      this.originalMethodName = originalMethodName;
   }

   public String getNamespace() {
      return this.namespace;
   }

   public String getName() {
      return this.name;
   }

   public String getAlias() {
      return this.alias;
   }

   public String getDefaultValue() {
      return this.defaultValue;
   }

   public Format getFormat() {
      return this.format;
   }

   public String getType() {
      return this.type;
   }

   public boolean isTypeInterface() {
      return this.isTypeInterface;
   }

   public String getXmlWrapperTag() {
      return this.xmlWrapperTag;
   }

   public boolean isRequired() {
      return this.required;
   }

   public String getOriginalMethodName() {
      return this.originalMethodName;
   }

   public String toString() {
      return "XmlElementData(" + this.namespace + "," + this.name + "," + this.alias + "," + Utilities.safeString(this.defaultValue) + "," + this.format + "," + this.type + "," + this.isTypeInterface + "," + this.xmlWrapperTag + "," + this.required + "," + this.originalMethodName + "," + System.identityHashCode(this) + ")";
   }
}
