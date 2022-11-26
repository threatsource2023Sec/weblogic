package com.oracle.xmlns.weblogic.multiVersionState.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.multiVersionState.ConfiguredIdType;
import com.oracle.xmlns.weblogic.multiVersionState.MultiVersionStateType;
import com.oracle.xmlns.weblogic.multiVersionState.UnresponsiveType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class MultiVersionStateTypeImpl extends XmlComplexContentImpl implements MultiVersionStateType {
   private static final long serialVersionUID = 1L;
   private static final QName UNRESPONSIVE$0 = new QName("http://xmlns.oracle.com/weblogic/multi-version-state", "unresponsive");
   private static final QName CONFIGUREDID$2 = new QName("http://xmlns.oracle.com/weblogic/multi-version-state", "configured-id");

   public MultiVersionStateTypeImpl(SchemaType sType) {
      super(sType);
   }

   public UnresponsiveType getUnresponsive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnresponsiveType target = null;
         target = (UnresponsiveType)this.get_store().find_element_user(UNRESPONSIVE$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUnresponsive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNRESPONSIVE$0) != 0;
      }
   }

   public void setUnresponsive(UnresponsiveType unresponsive) {
      this.generatedSetterHelperImpl(unresponsive, UNRESPONSIVE$0, 0, (short)1);
   }

   public UnresponsiveType addNewUnresponsive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnresponsiveType target = null;
         target = (UnresponsiveType)this.get_store().add_element_user(UNRESPONSIVE$0);
         return target;
      }
   }

   public void unsetUnresponsive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UNRESPONSIVE$0, 0);
      }
   }

   public ConfiguredIdType[] getConfiguredIdArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONFIGUREDID$2, targetList);
         ConfiguredIdType[] result = new ConfiguredIdType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConfiguredIdType getConfiguredIdArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfiguredIdType target = null;
         target = (ConfiguredIdType)this.get_store().find_element_user(CONFIGUREDID$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfConfiguredIdArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONFIGUREDID$2);
      }
   }

   public void setConfiguredIdArray(ConfiguredIdType[] configuredIdArray) {
      this.check_orphaned();
      this.arraySetterHelper(configuredIdArray, CONFIGUREDID$2);
   }

   public void setConfiguredIdArray(int i, ConfiguredIdType configuredId) {
      this.generatedSetterHelperImpl(configuredId, CONFIGUREDID$2, i, (short)2);
   }

   public ConfiguredIdType insertNewConfiguredId(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfiguredIdType target = null;
         target = (ConfiguredIdType)this.get_store().insert_element_user(CONFIGUREDID$2, i);
         return target;
      }
   }

   public ConfiguredIdType addNewConfiguredId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfiguredIdType target = null;
         target = (ConfiguredIdType)this.get_store().add_element_user(CONFIGUREDID$2);
         return target;
      }
   }

   public void removeConfiguredId(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGUREDID$2, i);
      }
   }
}
