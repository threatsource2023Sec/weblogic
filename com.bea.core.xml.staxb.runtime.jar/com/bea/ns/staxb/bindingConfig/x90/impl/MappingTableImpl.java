package com.bea.ns.staxb.bindingConfig.x90.impl;

import com.bea.ns.staxb.bindingConfig.x90.Mapping;
import com.bea.ns.staxb.bindingConfig.x90.MappingTable;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class MappingTableImpl extends XmlComplexContentImpl implements MappingTable {
   private static final long serialVersionUID = 1L;
   private static final QName MAPPING$0 = new QName("http://www.bea.com/ns/staxb/binding-config/90", "mapping");

   public MappingTableImpl(SchemaType sType) {
      super(sType);
   }

   public Mapping[] getMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MAPPING$0, targetList);
         Mapping[] result = new Mapping[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Mapping getMappingArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Mapping target = null;
         target = (Mapping)this.get_store().find_element_user(MAPPING$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfMappingArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAPPING$0);
      }
   }

   public void setMappingArray(Mapping[] mappingArray) {
      this.check_orphaned();
      this.arraySetterHelper(mappingArray, MAPPING$0);
   }

   public void setMappingArray(int i, Mapping mapping) {
      this.generatedSetterHelperImpl(mapping, MAPPING$0, i, (short)2);
   }

   public Mapping insertNewMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Mapping target = null;
         target = (Mapping)this.get_store().insert_element_user(MAPPING$0, i);
         return target;
      }
   }

   public Mapping addNewMapping() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Mapping target = null;
         target = (Mapping)this.get_store().add_element_user(MAPPING$0);
         return target;
      }
   }

   public void removeMapping(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAPPING$0, i);
      }
   }
}
