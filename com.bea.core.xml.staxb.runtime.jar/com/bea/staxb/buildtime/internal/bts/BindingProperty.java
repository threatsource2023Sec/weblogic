package com.bea.staxb.buildtime.internal.bts;

import java.io.Serializable;

public abstract class BindingProperty implements Serializable {
   private BindingTypeName typeName;
   private MethodName getter;
   private MethodName setter;
   private MethodName issetter;
   private String field;
   private int ctorParamIndex = -1;
   private JavaTypeName collection;
   private JavaInstanceFactory javaInstanceFactory;
   private static final long serialVersionUID = 1L;

   protected BindingProperty() {
   }

   public boolean isField() {
      return this.getField() != null;
   }

   public BindingTypeName getTypeName() {
      return this.typeName;
   }

   public void setBindingType(BindingType bType) {
      this.setTypeName(bType.getName());
   }

   public MethodName getGetterName() {
      return this.isField() ? null : this.getGetter();
   }

   public void setGetterName(MethodName mn) {
      this.setGetter(mn);
   }

   public boolean hasSetter() {
      return !this.isField() && this.getSetter() != null;
   }

   public MethodName getSetterName() {
      return this.isField() ? null : this.getSetter();
   }

   public void setSetterName(MethodName mn) {
      this.setSetter(mn);
   }

   public boolean hasIssetter() {
      return !this.isField() && this.getIssetter() != null;
   }

   public MethodName getIssetterName() {
      return this.isField() ? null : this.getIssetter();
   }

   public void setIssetterName(MethodName mn) {
      this.setIssetter(mn);
   }

   public String getFieldName() {
      return this.getField();
   }

   public void setFieldName(String field) {
      this.setField(field);
   }

   public JavaTypeName getCollectionClass() {
      return this.getCollection();
   }

   public void setCollectionClass(JavaTypeName jName) {
      this.setCollection(jName);
   }

   public String toString() {
      return this.getClass().getName() + " [" + (this.isField() ? this.getFieldName() : this.getGetterName().getSimpleName()) + "]";
   }

   public JavaInstanceFactory getJavaInstanceFactory() {
      return this.javaInstanceFactory;
   }

   public void setJavaInstanceFactory(JavaInstanceFactory javaInstanceFactory) {
      this.javaInstanceFactory = javaInstanceFactory;
   }

   public void setTypeName(BindingTypeName typeName) {
      this.typeName = typeName;
   }

   public MethodName getGetter() {
      return this.getter;
   }

   public void setGetter(MethodName getter) {
      this.getter = getter;
   }

   public MethodName getSetter() {
      return this.setter;
   }

   public void setSetter(MethodName setter) {
      this.setter = setter;
   }

   public MethodName getIssetter() {
      return this.issetter;
   }

   public void setIssetter(MethodName issetter) {
      this.issetter = issetter;
   }

   public String getField() {
      return this.field;
   }

   public void setField(String field) {
      this.field = field != null ? field.intern() : null;
   }

   public JavaTypeName getCollection() {
      return this.collection;
   }

   public void setCollection(JavaTypeName collection) {
      this.collection = collection;
   }

   public int getCtorParamIndex() {
      return this.ctorParamIndex;
   }

   public void setCtorParamIndex(int ctorParamIndex) {
      this.ctorParamIndex = ctorParamIndex;
   }
}
