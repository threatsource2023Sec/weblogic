package com.bea.staxb.buildtime.internal.bts;

import javax.xml.namespace.QName;

public class QNameProperty extends BindingProperty {
   private QName theName;
   private boolean isAttribute;
   private boolean isMultiple;
   private boolean isOptional;
   private boolean isNillable;
   private String defaultValue;
   private static final long serialVersionUID = 1L;

   public QName getQName() {
      return this.theName;
   }

   public void setQName(QName theName) {
      this.theName = theName;
   }

   public boolean isAttribute() {
      return this.isAttribute;
   }

   public void setAttribute(boolean attribute) {
      this.isAttribute = attribute;
   }

   public boolean isMultiple() {
      return this.isMultiple;
   }

   public void setMultiple(boolean multiple) {
      this.isMultiple = multiple;
   }

   public boolean isOptional() {
      return this.isOptional;
   }

   public void setOptional(boolean optional) {
      this.isOptional = optional;
   }

   public boolean isNillable() {
      return this.isNillable;
   }

   public void setNillable(boolean nillable) {
      this.isNillable = nillable;
   }

   public String getDefault() {
      return this.defaultValue;
   }

   public void setDefault(String default_value) {
      this.defaultValue = default_value != null ? default_value.intern() : null;
   }
}
