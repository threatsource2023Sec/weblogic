package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomFilterListenerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.FilterListenersType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class FilterListenersTypeImpl extends XmlComplexContentImpl implements FilterListenersType {
   private static final long serialVersionUID = 1L;
   private static final QName CUSTOMFILTERLISTENER$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-filter-listener");

   public FilterListenersTypeImpl(SchemaType sType) {
      super(sType);
   }

   public CustomFilterListenerType[] getCustomFilterListenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CUSTOMFILTERLISTENER$0, targetList);
         CustomFilterListenerType[] result = new CustomFilterListenerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CustomFilterListenerType getCustomFilterListenerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomFilterListenerType target = null;
         target = (CustomFilterListenerType)this.get_store().find_element_user(CUSTOMFILTERLISTENER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilCustomFilterListenerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomFilterListenerType target = null;
         target = (CustomFilterListenerType)this.get_store().find_element_user(CUSTOMFILTERLISTENER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfCustomFilterListenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMFILTERLISTENER$0);
      }
   }

   public void setCustomFilterListenerArray(CustomFilterListenerType[] customFilterListenerArray) {
      this.check_orphaned();
      this.arraySetterHelper(customFilterListenerArray, CUSTOMFILTERLISTENER$0);
   }

   public void setCustomFilterListenerArray(int i, CustomFilterListenerType customFilterListener) {
      this.generatedSetterHelperImpl(customFilterListener, CUSTOMFILTERLISTENER$0, i, (short)2);
   }

   public void setNilCustomFilterListenerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomFilterListenerType target = null;
         target = (CustomFilterListenerType)this.get_store().find_element_user(CUSTOMFILTERLISTENER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public CustomFilterListenerType insertNewCustomFilterListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomFilterListenerType target = null;
         target = (CustomFilterListenerType)this.get_store().insert_element_user(CUSTOMFILTERLISTENER$0, i);
         return target;
      }
   }

   public CustomFilterListenerType addNewCustomFilterListener() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomFilterListenerType target = null;
         target = (CustomFilterListenerType)this.get_store().add_element_user(CUSTOMFILTERLISTENER$0);
         return target;
      }
   }

   public void removeCustomFilterListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMFILTERLISTENER$0, i);
      }
   }
}
