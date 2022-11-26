package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.Decision;
import org.jcp.xmlns.xml.ns.javaee.Flow;
import org.jcp.xmlns.xml.ns.javaee.Job;
import org.jcp.xmlns.xml.ns.javaee.Listeners;
import org.jcp.xmlns.xml.ns.javaee.Properties;
import org.jcp.xmlns.xml.ns.javaee.Split;
import org.jcp.xmlns.xml.ns.javaee.Step;

public class JobImpl extends XmlComplexContentImpl implements Job {
   private static final long serialVersionUID = 1L;
   private static final QName PROPERTIES$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "properties");
   private static final QName LISTENERS$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "listeners");
   private static final QName DECISION$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "decision");
   private static final QName FLOW$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "flow");
   private static final QName SPLIT$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "split");
   private static final QName STEP$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "step");
   private static final QName VERSION$12 = new QName("", "version");
   private static final QName ID$14 = new QName("", "id");
   private static final QName RESTARTABLE$16 = new QName("", "restartable");

   public JobImpl(SchemaType sType) {
      super(sType);
   }

   public Properties getProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Properties target = null;
         target = (Properties)this.get_store().find_element_user(PROPERTIES$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROPERTIES$0) != 0;
      }
   }

   public void setProperties(Properties properties) {
      this.generatedSetterHelperImpl(properties, PROPERTIES$0, 0, (short)1);
   }

   public Properties addNewProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Properties target = null;
         target = (Properties)this.get_store().add_element_user(PROPERTIES$0);
         return target;
      }
   }

   public void unsetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTIES$0, 0);
      }
   }

   public Listeners getListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Listeners target = null;
         target = (Listeners)this.get_store().find_element_user(LISTENERS$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LISTENERS$2) != 0;
      }
   }

   public void setListeners(Listeners listeners) {
      this.generatedSetterHelperImpl(listeners, LISTENERS$2, 0, (short)1);
   }

   public Listeners addNewListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Listeners target = null;
         target = (Listeners)this.get_store().add_element_user(LISTENERS$2);
         return target;
      }
   }

   public void unsetListeners() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LISTENERS$2, 0);
      }
   }

   public Decision[] getDecisionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DECISION$4, targetList);
         Decision[] result = new Decision[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Decision getDecisionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Decision target = null;
         target = (Decision)this.get_store().find_element_user(DECISION$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDecisionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DECISION$4);
      }
   }

   public void setDecisionArray(Decision[] decisionArray) {
      this.check_orphaned();
      this.arraySetterHelper(decisionArray, DECISION$4);
   }

   public void setDecisionArray(int i, Decision decision) {
      this.generatedSetterHelperImpl(decision, DECISION$4, i, (short)2);
   }

   public Decision insertNewDecision(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Decision target = null;
         target = (Decision)this.get_store().insert_element_user(DECISION$4, i);
         return target;
      }
   }

   public Decision addNewDecision() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Decision target = null;
         target = (Decision)this.get_store().add_element_user(DECISION$4);
         return target;
      }
   }

   public void removeDecision(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DECISION$4, i);
      }
   }

   public Flow[] getFlowArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FLOW$6, targetList);
         Flow[] result = new Flow[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Flow getFlowArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Flow target = null;
         target = (Flow)this.get_store().find_element_user(FLOW$6, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFlowArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FLOW$6);
      }
   }

   public void setFlowArray(Flow[] flowArray) {
      this.check_orphaned();
      this.arraySetterHelper(flowArray, FLOW$6);
   }

   public void setFlowArray(int i, Flow flow) {
      this.generatedSetterHelperImpl(flow, FLOW$6, i, (short)2);
   }

   public Flow insertNewFlow(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Flow target = null;
         target = (Flow)this.get_store().insert_element_user(FLOW$6, i);
         return target;
      }
   }

   public Flow addNewFlow() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Flow target = null;
         target = (Flow)this.get_store().add_element_user(FLOW$6);
         return target;
      }
   }

   public void removeFlow(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FLOW$6, i);
      }
   }

   public Split[] getSplitArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SPLIT$8, targetList);
         Split[] result = new Split[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Split getSplitArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Split target = null;
         target = (Split)this.get_store().find_element_user(SPLIT$8, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSplitArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SPLIT$8);
      }
   }

   public void setSplitArray(Split[] splitArray) {
      this.check_orphaned();
      this.arraySetterHelper(splitArray, SPLIT$8);
   }

   public void setSplitArray(int i, Split split) {
      this.generatedSetterHelperImpl(split, SPLIT$8, i, (short)2);
   }

   public Split insertNewSplit(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Split target = null;
         target = (Split)this.get_store().insert_element_user(SPLIT$8, i);
         return target;
      }
   }

   public Split addNewSplit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Split target = null;
         target = (Split)this.get_store().add_element_user(SPLIT$8);
         return target;
      }
   }

   public void removeSplit(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SPLIT$8, i);
      }
   }

   public Step[] getStepArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(STEP$10, targetList);
         Step[] result = new Step[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Step getStepArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Step target = null;
         target = (Step)this.get_store().find_element_user(STEP$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfStepArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STEP$10);
      }
   }

   public void setStepArray(Step[] stepArray) {
      this.check_orphaned();
      this.arraySetterHelper(stepArray, STEP$10);
   }

   public void setStepArray(int i, Step step) {
      this.generatedSetterHelperImpl(step, STEP$10, i, (short)2);
   }

   public Step insertNewStep(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Step target = null;
         target = (Step)this.get_store().insert_element_user(STEP$10, i);
         return target;
      }
   }

   public Step addNewStep() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Step target = null;
         target = (Step)this.get_store().add_element_user(STEP$10);
         return target;
      }
   }

   public void removeStep(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STEP$10, i);
      }
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$12);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(VERSION$12);
         }

         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$12);
         if (target == null) {
            target = (XmlString)this.get_default_attribute_value(VERSION$12);
         }

         return target;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$12);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$12);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$12);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VERSION$12);
         }

         target.set(version);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         return target;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$14);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$14);
         }

         target.set(id);
      }
   }

   public String getRestartable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(RESTARTABLE$16);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRestartable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(RESTARTABLE$16);
         return target;
      }
   }

   public boolean isSetRestartable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(RESTARTABLE$16) != null;
      }
   }

   public void setRestartable(String restartable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(RESTARTABLE$16);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(RESTARTABLE$16);
         }

         target.setStringValue(restartable);
      }
   }

   public void xsetRestartable(XmlString restartable) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(RESTARTABLE$16);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(RESTARTABLE$16);
         }

         target.set(restartable);
      }
   }

   public void unsetRestartable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(RESTARTABLE$16);
      }
   }
}
