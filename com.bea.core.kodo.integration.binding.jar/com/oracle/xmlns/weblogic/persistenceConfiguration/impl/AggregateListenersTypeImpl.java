package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.AggregateListenersType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomAggregateListenerType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AggregateListenersTypeImpl extends XmlComplexContentImpl implements AggregateListenersType {
   private static final long serialVersionUID = 1L;
   private static final QName CUSTOMAGGREGATELISTENER$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "custom-aggregate-listener");

   public AggregateListenersTypeImpl(SchemaType sType) {
      super(sType);
   }

   public CustomAggregateListenerType[] getCustomAggregateListenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CUSTOMAGGREGATELISTENER$0, targetList);
         CustomAggregateListenerType[] result = new CustomAggregateListenerType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CustomAggregateListenerType getCustomAggregateListenerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomAggregateListenerType target = null;
         target = (CustomAggregateListenerType)this.get_store().find_element_user(CUSTOMAGGREGATELISTENER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilCustomAggregateListenerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomAggregateListenerType target = null;
         target = (CustomAggregateListenerType)this.get_store().find_element_user(CUSTOMAGGREGATELISTENER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfCustomAggregateListenerArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CUSTOMAGGREGATELISTENER$0);
      }
   }

   public void setCustomAggregateListenerArray(CustomAggregateListenerType[] customAggregateListenerArray) {
      this.check_orphaned();
      this.arraySetterHelper(customAggregateListenerArray, CUSTOMAGGREGATELISTENER$0);
   }

   public void setCustomAggregateListenerArray(int i, CustomAggregateListenerType customAggregateListener) {
      this.generatedSetterHelperImpl(customAggregateListener, CUSTOMAGGREGATELISTENER$0, i, (short)2);
   }

   public void setNilCustomAggregateListenerArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomAggregateListenerType target = null;
         target = (CustomAggregateListenerType)this.get_store().find_element_user(CUSTOMAGGREGATELISTENER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public CustomAggregateListenerType insertNewCustomAggregateListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomAggregateListenerType target = null;
         target = (CustomAggregateListenerType)this.get_store().insert_element_user(CUSTOMAGGREGATELISTENER$0, i);
         return target;
      }
   }

   public CustomAggregateListenerType addNewCustomAggregateListener() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CustomAggregateListenerType target = null;
         target = (CustomAggregateListenerType)this.get_store().add_element_user(CUSTOMAGGREGATELISTENER$0);
         return target;
      }
   }

   public void removeCustomAggregateListener(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CUSTOMAGGREGATELISTENER$0, i);
      }
   }
}
