package com.bea.connector.diagnostic.impl;

import com.bea.connector.diagnostic.AdapterType;
import com.bea.connector.diagnostic.HealthType;
import com.bea.connector.diagnostic.InboundAdapterType;
import com.bea.connector.diagnostic.OutboundAdapterType;
import com.bea.connector.diagnostic.WorkManagerType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class AdapterTypeImpl extends XmlComplexContentImpl implements AdapterType {
   private static final long serialVersionUID = 1L;
   private static final QName PARTITIONNAME$0 = new QName("http://www.bea.com/connector/diagnostic", "partitionName");
   private static final QName JNDINAME$2 = new QName("http://www.bea.com/connector/diagnostic", "jndiName");
   private static final QName STATE$4 = new QName("http://www.bea.com/connector/diagnostic", "state");
   private static final QName HEALTH$6 = new QName("http://www.bea.com/connector/diagnostic", "health");
   private static final QName OUTBOUNDADAPTER$8 = new QName("http://www.bea.com/connector/diagnostic", "outboundAdapter");
   private static final QName INBOUNDADAPTER$10 = new QName("http://www.bea.com/connector/diagnostic", "inboundAdapter");
   private static final QName WORKMANAGER$12 = new QName("http://www.bea.com/connector/diagnostic", "workManager");

   public AdapterTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getPartitionName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARTITIONNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPartitionName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARTITIONNAME$0, 0);
         return target;
      }
   }

   public void setPartitionName(String partitionName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARTITIONNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PARTITIONNAME$0);
         }

         target.setStringValue(partitionName);
      }
   }

   public void xsetPartitionName(XmlString partitionName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARTITIONNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PARTITIONNAME$0);
         }

         target.set(partitionName);
      }
   }

   public String getJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDINAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$2, 0);
         return target;
      }
   }

   public void setJndiName(String jndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDINAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JNDINAME$2);
         }

         target.setStringValue(jndiName);
      }
   }

   public void xsetJndiName(XmlString jndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JNDINAME$2);
         }

         target.set(jndiName);
      }
   }

   public String getState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATE$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STATE$4, 0);
         return target;
      }
   }

   public void setState(String state) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STATE$4);
         }

         target.setStringValue(state);
      }
   }

   public void xsetState(XmlString state) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STATE$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(STATE$4);
         }

         target.set(state);
      }
   }

   public HealthType getHealth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HealthType target = null;
         target = (HealthType)this.get_store().find_element_user(HEALTH$6, 0);
         return target == null ? null : target;
      }
   }

   public void setHealth(HealthType health) {
      this.generatedSetterHelperImpl(health, HEALTH$6, 0, (short)1);
   }

   public HealthType addNewHealth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HealthType target = null;
         target = (HealthType)this.get_store().add_element_user(HEALTH$6);
         return target;
      }
   }

   public OutboundAdapterType[] getOutboundAdapterArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(OUTBOUNDADAPTER$8, targetList);
         OutboundAdapterType[] result = new OutboundAdapterType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public OutboundAdapterType getOutboundAdapterArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OutboundAdapterType target = null;
         target = (OutboundAdapterType)this.get_store().find_element_user(OUTBOUNDADAPTER$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfOutboundAdapterArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OUTBOUNDADAPTER$8);
      }
   }

   public void setOutboundAdapterArray(OutboundAdapterType[] outboundAdapterArray) {
      this.check_orphaned();
      this.arraySetterHelper(outboundAdapterArray, OUTBOUNDADAPTER$8);
   }

   public void setOutboundAdapterArray(int i, OutboundAdapterType outboundAdapter) {
      this.generatedSetterHelperImpl(outboundAdapter, OUTBOUNDADAPTER$8, i, (short)2);
   }

   public OutboundAdapterType insertNewOutboundAdapter(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OutboundAdapterType target = null;
         target = (OutboundAdapterType)this.get_store().insert_element_user(OUTBOUNDADAPTER$8, i);
         return target;
      }
   }

   public OutboundAdapterType addNewOutboundAdapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OutboundAdapterType target = null;
         target = (OutboundAdapterType)this.get_store().add_element_user(OUTBOUNDADAPTER$8);
         return target;
      }
   }

   public void removeOutboundAdapter(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OUTBOUNDADAPTER$8, i);
      }
   }

   public InboundAdapterType[] getInboundAdapterArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INBOUNDADAPTER$10, targetList);
         InboundAdapterType[] result = new InboundAdapterType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public InboundAdapterType getInboundAdapterArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InboundAdapterType target = null;
         target = (InboundAdapterType)this.get_store().find_element_user(INBOUNDADAPTER$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfInboundAdapterArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INBOUNDADAPTER$10);
      }
   }

   public void setInboundAdapterArray(InboundAdapterType[] inboundAdapterArray) {
      this.check_orphaned();
      this.arraySetterHelper(inboundAdapterArray, INBOUNDADAPTER$10);
   }

   public void setInboundAdapterArray(int i, InboundAdapterType inboundAdapter) {
      this.generatedSetterHelperImpl(inboundAdapter, INBOUNDADAPTER$10, i, (short)2);
   }

   public InboundAdapterType insertNewInboundAdapter(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InboundAdapterType target = null;
         target = (InboundAdapterType)this.get_store().insert_element_user(INBOUNDADAPTER$10, i);
         return target;
      }
   }

   public InboundAdapterType addNewInboundAdapter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InboundAdapterType target = null;
         target = (InboundAdapterType)this.get_store().add_element_user(INBOUNDADAPTER$10);
         return target;
      }
   }

   public void removeInboundAdapter(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INBOUNDADAPTER$10, i);
      }
   }

   public WorkManagerType getWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerType target = null;
         target = (WorkManagerType)this.get_store().find_element_user(WORKMANAGER$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WORKMANAGER$12) != 0;
      }
   }

   public void setWorkManager(WorkManagerType workManager) {
      this.generatedSetterHelperImpl(workManager, WORKMANAGER$12, 0, (short)1);
   }

   public WorkManagerType addNewWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WorkManagerType target = null;
         target = (WorkManagerType)this.get_store().add_element_user(WORKMANAGER$12);
         return target;
      }
   }

   public void unsetWorkManager() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WORKMANAGER$12, 0);
      }
   }
}
