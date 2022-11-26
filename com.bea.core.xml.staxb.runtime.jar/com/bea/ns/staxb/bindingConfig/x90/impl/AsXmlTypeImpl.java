package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.AsXmlType;
import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.JavaStringHolderEx;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import javax.xml.namespace.QName;

public class AsXmlTypeImpl extends JavaStringHolderEx implements AsXmlType {
   private static final long serialVersionUID = 1L;
   private static final QName WHITESPACE$0 = new QName("", "whitespace");

   public AsXmlTypeImpl(SchemaType sType) {
      super(sType, true);
   }

   protected AsXmlTypeImpl(SchemaType sType, boolean b) {
      super(sType, b);
   }

   public AsXmlType.Whitespace.Enum getWhitespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(WHITESPACE$0);
         return target == null ? null : (AsXmlType.Whitespace.Enum)target.getEnumValue();
      }
   }

   public AsXmlType.Whitespace xgetWhitespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AsXmlType.Whitespace target = null;
         target = (AsXmlType.Whitespace)this.get_store().find_attribute_user(WHITESPACE$0);
         return target;
      }
   }

   public boolean isSetWhitespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(WHITESPACE$0) != null;
      }
   }

   public void setWhitespace(AsXmlType.Whitespace.Enum whitespace) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(WHITESPACE$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(WHITESPACE$0);
         }

         target.setEnumValue(whitespace);
      }
   }

   public void xsetWhitespace(AsXmlType.Whitespace whitespace) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AsXmlType.Whitespace target = null;
         target = (AsXmlType.Whitespace)this.get_store().find_attribute_user(WHITESPACE$0);
         if (target == null) {
            target = (AsXmlType.Whitespace)this.get_store().add_attribute_user(WHITESPACE$0);
         }

         target.set(whitespace);
      }
   }

   public void unsetWhitespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(WHITESPACE$0);
      }
   }

   public static class WhitespaceImpl extends JavaStringEnumerationHolderEx implements AsXmlType.Whitespace {
      private static final long serialVersionUID = 1L;

      public WhitespaceImpl(SchemaType sType) {
         super(sType, false);
      }

      protected WhitespaceImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
