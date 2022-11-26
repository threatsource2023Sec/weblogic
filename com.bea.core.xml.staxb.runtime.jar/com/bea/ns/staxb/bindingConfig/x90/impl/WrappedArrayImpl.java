package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.Mapping;
import com.bea.ns.staxb.bindingConfig.x90.WrappedArray;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlQName;
import javax.xml.namespace.QName;

public class WrappedArrayImpl extends BindingTypeImpl implements WrappedArray {
   private static final long serialVersionUID = 1L;
   private static final QName ITEMNAME$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "item-name");
   private static final QName ITEMTYPE$2 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "item-type");
   private static final QName ITEMNILLABLE$4 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "item-nillable");

   public WrappedArrayImpl(SchemaType sType) {
      super(sType);
   }

   public QName getItemName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ITEMNAME$0, 0);
         return target == null ? null : target.getQNameValue();
      }
   }

   public XmlQName xgetItemName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_element_user(ITEMNAME$0, 0);
         return target;
      }
   }

   public void setItemName(QName itemName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ITEMNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ITEMNAME$0);
         }

         target.setQNameValue(itemName);
      }
   }

   public void xsetItemName(XmlQName itemName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlQName target = null;
         target = (XmlQName)this.get_store().find_element_user(ITEMNAME$0, 0);
         if (target == null) {
            target = (XmlQName)this.get_store().add_element_user(ITEMNAME$0);
         }

         target.set(itemName);
      }
   }

   public Mapping getItemType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Mapping target = null;
         target = (Mapping)this.get_store().find_element_user(ITEMTYPE$2, 0);
         return target == null ? null : target;
      }
   }

   public void setItemType(Mapping itemType) {
      this.generatedSetterHelperImpl(itemType, ITEMTYPE$2, 0, (short)1);
   }

   public Mapping addNewItemType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Mapping target = null;
         target = (Mapping)this.get_store().add_element_user(ITEMTYPE$2);
         return target;
      }
   }

   public boolean getItemNillable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ITEMNILLABLE$4, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetItemNillable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ITEMNILLABLE$4, 0);
         return target;
      }
   }

   public boolean isSetItemNillable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ITEMNILLABLE$4) != 0;
      }
   }

   public void setItemNillable(boolean itemNillable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ITEMNILLABLE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ITEMNILLABLE$4);
         }

         target.setBooleanValue(itemNillable);
      }
   }

   public void xsetItemNillable(XmlBoolean itemNillable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ITEMNILLABLE$4, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ITEMNILLABLE$4);
         }

         target.set(itemNillable);
      }
   }

   public void unsetItemNillable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ITEMNILLABLE$4, 0);
      }
   }
}
