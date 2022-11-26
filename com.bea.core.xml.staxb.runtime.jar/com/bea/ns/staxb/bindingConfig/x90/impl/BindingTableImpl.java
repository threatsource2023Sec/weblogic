package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.BindingTable;
import com.bea.ns.staxb.bindingConfig.x90.BindingType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class BindingTableImpl extends XmlComplexContentImpl implements BindingTable {
   private static final long serialVersionUID = 1L;
   private static final QName BINDINGTYPE$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "binding-type");

   public BindingTableImpl(SchemaType sType) {
      super(sType);
   }

   public BindingType[] getBindingTypeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(BINDINGTYPE$0, targetList);
         BindingType[] result = new BindingType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public BindingType getBindingTypeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BindingType target = null;
         target = (BindingType)this.get_store().find_element_user(BINDINGTYPE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfBindingTypeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BINDINGTYPE$0);
      }
   }

   public void setBindingTypeArray(BindingType[] bindingTypeArray) {
      this.check_orphaned();
      this.arraySetterHelper(bindingTypeArray, BINDINGTYPE$0);
   }

   public void setBindingTypeArray(int i, BindingType bindingType) {
      this.generatedSetterHelperImpl(bindingType, BINDINGTYPE$0, i, (short)2);
   }

   public BindingType insertNewBindingType(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BindingType target = null;
         target = (BindingType)this.get_store().insert_element_user(BINDINGTYPE$0, i);
         return target;
      }
   }

   public BindingType addNewBindingType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         BindingType target = null;
         target = (BindingType)this.get_store().add_element_user(BINDINGTYPE$0);
         return target;
      }
   }

   public void removeBindingType(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BINDINGTYPE$0, i);
      }
   }
}
