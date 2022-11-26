package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.oracle.xmlns.weblogic.persistenceConfiguration.StackExecutionContextNameProviderType;
import javax.xml.namespace.QName;

public class StackExecutionContextNameProviderTypeImpl extends XmlComplexContentImpl implements StackExecutionContextNameProviderType {
   private static final long serialVersionUID = 1L;
   private static final QName STYLE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "style");

   public StackExecutionContextNameProviderTypeImpl(SchemaType sType) {
      super(sType);
   }

   public StackExecutionContextNameProviderType.Style.Enum getStyle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STYLE$0, 0);
         return target == null ? null : (StackExecutionContextNameProviderType.Style.Enum)target.getEnumValue();
      }
   }

   public StackExecutionContextNameProviderType.Style xgetStyle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StackExecutionContextNameProviderType.Style target = null;
         target = (StackExecutionContextNameProviderType.Style)this.get_store().find_element_user(STYLE$0, 0);
         return target;
      }
   }

   public boolean isNilStyle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StackExecutionContextNameProviderType.Style target = null;
         target = (StackExecutionContextNameProviderType.Style)this.get_store().find_element_user(STYLE$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetStyle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STYLE$0) != 0;
      }
   }

   public void setStyle(StackExecutionContextNameProviderType.Style.Enum style) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STYLE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STYLE$0);
         }

         target.setEnumValue(style);
      }
   }

   public void xsetStyle(StackExecutionContextNameProviderType.Style style) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StackExecutionContextNameProviderType.Style target = null;
         target = (StackExecutionContextNameProviderType.Style)this.get_store().find_element_user(STYLE$0, 0);
         if (target == null) {
            target = (StackExecutionContextNameProviderType.Style)this.get_store().add_element_user(STYLE$0);
         }

         target.set(style);
      }
   }

   public void setNilStyle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StackExecutionContextNameProviderType.Style target = null;
         target = (StackExecutionContextNameProviderType.Style)this.get_store().find_element_user(STYLE$0, 0);
         if (target == null) {
            target = (StackExecutionContextNameProviderType.Style)this.get_store().add_element_user(STYLE$0);
         }

         target.setNil();
      }
   }

   public void unsetStyle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STYLE$0, 0);
      }
   }

   public static class StyleImpl extends JavaStringEnumerationHolderEx implements StackExecutionContextNameProviderType.Style {
      private static final long serialVersionUID = 1L;

      public StyleImpl(SchemaType sType) {
         super(sType, false);
      }

      protected StyleImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
