package org.glassfish.hk2.xml.internal;

import javax.xml.namespace.QName;
import org.glassfish.hk2.xml.internal.alt.AdapterInformation;
import org.glassfish.hk2.xml.internal.alt.AltClass;
import org.glassfish.hk2.xml.internal.alt.AltMethod;
import org.glassfish.hk2.xml.internal.alt.MethodInformationI;

public class MethodInformation implements MethodInformationI {
   private final AltMethod originalMethod;
   private final MethodType methodType;
   private final AltClass getterSetterType;
   private final String decapitalizedMethodProperty;
   private final QName representedProperty;
   private final String defaultValue;
   private final AltClass baseChildType;
   private final boolean key;
   private final boolean isList;
   private final boolean isArray;
   private final boolean isReference;
   private final Format format;
   private final AltClass listParameterizedType;
   private final String xmlWrapperTag;
   private final AdapterInformation adapterInfo;
   private final boolean required;
   private final String originalMethodName;

   public MethodInformation(AltMethod originalMethod, MethodType methodType, String decapitalizedMethodProperty, QName representedProperty, String defaultValue, AltClass baseChildType, AltClass gsType, boolean key, boolean isList, boolean isArray, boolean isReference, Format format, AltClass listParameterizedType, String xmlWrapperTag, AdapterInformation adapterInfo, boolean required, String originalMethodName) {
      this.originalMethod = originalMethod;
      this.methodType = methodType;
      this.decapitalizedMethodProperty = decapitalizedMethodProperty;
      this.representedProperty = representedProperty;
      this.defaultValue = defaultValue;
      this.baseChildType = baseChildType;
      this.getterSetterType = gsType;
      this.key = key;
      this.isList = isList;
      this.isArray = isArray;
      this.isReference = isReference;
      this.format = format;
      this.listParameterizedType = listParameterizedType;
      this.xmlWrapperTag = xmlWrapperTag;
      this.adapterInfo = adapterInfo;
      this.required = required;
      this.originalMethodName = originalMethodName;
   }

   public AltMethod getOriginalMethod() {
      return this.originalMethod;
   }

   public MethodType getMethodType() {
      return this.methodType;
   }

   public AltClass getGetterSetterType() {
      return this.getterSetterType;
   }

   public QName getRepresentedProperty() {
      return this.representedProperty;
   }

   public String getDefaultValue() {
      return this.defaultValue;
   }

   public AltClass getBaseChildType() {
      return this.baseChildType;
   }

   public boolean isKey() {
      return this.key;
   }

   public boolean isList() {
      return this.isList;
   }

   public boolean isArray() {
      return this.isArray;
   }

   public boolean isReference() {
      return this.isReference;
   }

   public boolean isRequired() {
      return this.required;
   }

   public String getDecapitalizedMethodProperty() {
      return this.decapitalizedMethodProperty;
   }

   public Format getFormat() {
      return this.format;
   }

   public AltClass getListParameterizedType() {
      return this.listParameterizedType;
   }

   public String getWrapperTag() {
      return this.xmlWrapperTag;
   }

   public AdapterInformation getAdapterInformation() {
      return this.adapterInfo;
   }

   public String getOriginalMethodName() {
      return this.originalMethodName;
   }

   public String toString() {
      return "MethodInformation(name=" + this.originalMethod.getName() + ",type=" + this.methodType + ",getterType=" + this.getterSetterType + ",decapitalizedMethodProperty=" + this.decapitalizedMethodProperty + ",representedProperty=" + this.representedProperty + ",defaultValue=" + ("\u0000".equals(this.defaultValue) ? "" : this.defaultValue) + ",baseChildType=" + this.baseChildType + ",key=" + this.key + ",isList=" + this.isList + ",isArray=" + this.isArray + ",isReference=" + this.isReference + ",format=" + this.format + ",listParameterizedType=" + this.listParameterizedType + ",xmlWrapperTag=" + this.xmlWrapperTag + ",adapterInfo=" + this.adapterInfo + ",required=" + this.required + ",originalMethodName=" + this.originalMethodName + "," + System.identityHashCode(this) + ")";
   }
}
