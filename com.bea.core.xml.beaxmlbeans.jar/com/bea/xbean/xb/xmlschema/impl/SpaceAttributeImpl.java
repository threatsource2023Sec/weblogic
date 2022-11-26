package com.bea.xbean.xb.xmlschema.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xmlschema.SpaceAttribute;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import javax.xml.namespace.QName;

public class SpaceAttributeImpl extends XmlComplexContentImpl implements SpaceAttribute {
   private static final QName SPACE$0 = new QName("http://www.w3.org/XML/1998/namespace", "space");

   public SpaceAttributeImpl(SchemaType sType) {
      super(sType);
   }

   public SpaceAttribute.Space.Enum getSpace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(SPACE$0);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(SPACE$0);
         }

         return target == null ? null : (SpaceAttribute.Space.Enum)target.getEnumValue();
      }
   }

   public SpaceAttribute.Space xgetSpace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SpaceAttribute.Space target = null;
         target = (SpaceAttribute.Space)this.get_store().find_attribute_user(SPACE$0);
         if (target == null) {
            target = (SpaceAttribute.Space)this.get_default_attribute_value(SPACE$0);
         }

         return target;
      }
   }

   public boolean isSetSpace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(SPACE$0) != null;
      }
   }

   public void setSpace(SpaceAttribute.Space.Enum space) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(SPACE$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(SPACE$0);
         }

         target.setEnumValue(space);
      }
   }

   public void xsetSpace(SpaceAttribute.Space space) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SpaceAttribute.Space target = null;
         target = (SpaceAttribute.Space)this.get_store().find_attribute_user(SPACE$0);
         if (target == null) {
            target = (SpaceAttribute.Space)this.get_store().add_attribute_user(SPACE$0);
         }

         target.set(space);
      }
   }

   public void unsetSpace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(SPACE$0);
      }
   }

   public static class SpaceImpl extends JavaStringEnumerationHolderEx implements SpaceAttribute.Space {
      public SpaceImpl(SchemaType sType) {
         super(sType, false);
      }

      protected SpaceImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
