package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.ListArray;
import com.bea.ns.staxb.bindingConfig.x90.Mapping;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class ListArrayImpl extends BindingTypeImpl implements ListArray {
   private static final long serialVersionUID = 1L;
   private static final QName ITEMTYPE$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "item-type");

   public ListArrayImpl(SchemaType sType) {
      super(sType);
   }

   public Mapping getItemType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Mapping target = null;
         target = (Mapping)this.get_store().find_element_user(ITEMTYPE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setItemType(Mapping itemType) {
      this.generatedSetterHelperImpl(itemType, ITEMTYPE$0, 0, (short)1);
   }

   public Mapping addNewItemType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Mapping target = null;
         target = (Mapping)this.get_store().add_element_user(ITEMTYPE$0);
         return target;
      }
   }
}
