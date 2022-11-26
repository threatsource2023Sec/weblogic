package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.PartitionPlan;
import org.jcp.xmlns.xml.ns.javaee.Properties;

public class PartitionPlanImpl extends XmlComplexContentImpl implements PartitionPlan {
   private static final long serialVersionUID = 1L;
   private static final QName PROPERTIES$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "properties");
   private static final QName PARTITIONS$2 = new QName("", "partitions");
   private static final QName THREADS$4 = new QName("", "threads");

   public PartitionPlanImpl(SchemaType sType) {
      super(sType);
   }

   public Properties[] getPropertiesArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PROPERTIES$0, targetList);
         Properties[] result = new Properties[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Properties getPropertiesArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Properties target = null;
         target = (Properties)this.get_store().find_element_user(PROPERTIES$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfPropertiesArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROPERTIES$0);
      }
   }

   public void setPropertiesArray(Properties[] propertiesArray) {
      this.check_orphaned();
      this.arraySetterHelper(propertiesArray, PROPERTIES$0);
   }

   public void setPropertiesArray(int i, Properties properties) {
      this.generatedSetterHelperImpl(properties, PROPERTIES$0, i, (short)2);
   }

   public Properties insertNewProperties(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Properties target = null;
         target = (Properties)this.get_store().insert_element_user(PROPERTIES$0, i);
         return target;
      }
   }

   public Properties addNewProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Properties target = null;
         target = (Properties)this.get_store().add_element_user(PROPERTIES$0);
         return target;
      }
   }

   public void removeProperties(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTIES$0, i);
      }
   }

   public String getPartitions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(PARTITIONS$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPartitions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(PARTITIONS$2);
         return target;
      }
   }

   public boolean isSetPartitions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(PARTITIONS$2) != null;
      }
   }

   public void setPartitions(String partitions) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(PARTITIONS$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(PARTITIONS$2);
         }

         target.setStringValue(partitions);
      }
   }

   public void xsetPartitions(XmlString partitions) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(PARTITIONS$2);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(PARTITIONS$2);
         }

         target.set(partitions);
      }
   }

   public void unsetPartitions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(PARTITIONS$2);
      }
   }

   public String getThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(THREADS$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(THREADS$4);
         return target;
      }
   }

   public boolean isSetThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(THREADS$4) != null;
      }
   }

   public void setThreads(String threads) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(THREADS$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(THREADS$4);
         }

         target.setStringValue(threads);
      }
   }

   public void xsetThreads(XmlString threads) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(THREADS$4);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(THREADS$4);
         }

         target.set(threads);
      }
   }

   public void unsetThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(THREADS$4);
      }
   }
}
