package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.Batchlet;
import org.jcp.xmlns.xml.ns.javaee.Chunk;
import org.jcp.xmlns.xml.ns.javaee.End;
import org.jcp.xmlns.xml.ns.javaee.Fail;
import org.jcp.xmlns.xml.ns.javaee.Listeners;
import org.jcp.xmlns.xml.ns.javaee.Next;
import org.jcp.xmlns.xml.ns.javaee.Partition;
import org.jcp.xmlns.xml.ns.javaee.Properties;
import org.jcp.xmlns.xml.ns.javaee.Step;
import org.jcp.xmlns.xml.ns.javaee.Stop;

public class StepImpl extends XmlComplexContentImpl implements Step {
   private static final long serialVersionUID = 1L;
   private static final QName PROPERTIES$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "properties");
   private static final QName LISTENERS$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "listeners");
   private static final QName BATCHLET$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "batchlet");
   private static final QName CHUNK$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "chunk");
   private static final QName PARTITION$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "partition");
   private static final QName END$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "end");
   private static final QName FAIL$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "fail");
   private static final QName NEXT$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "next");
   private static final QName STOP$16 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "stop");
   private static final QName ID$18 = new QName("", "id");
   private static final QName STARTLIMIT$20 = new QName("", "start-limit");
   private static final QName ALLOWSTARTIFCOMPLETE$22 = new QName("", "allow-start-if-complete");
   private static final QName NEXT2$24 = new QName("", "next");

   public StepImpl(SchemaType sType) {
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

   public Batchlet getBatchlet() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Batchlet target = null;
         target = (Batchlet)this.get_store().find_element_user(BATCHLET$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetBatchlet() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BATCHLET$4) != 0;
      }
   }

   public void setBatchlet(Batchlet batchlet) {
      this.generatedSetterHelperImpl(batchlet, BATCHLET$4, 0, (short)1);
   }

   public Batchlet addNewBatchlet() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Batchlet target = null;
         target = (Batchlet)this.get_store().add_element_user(BATCHLET$4);
         return target;
      }
   }

   public void unsetBatchlet() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BATCHLET$4, 0);
      }
   }

   public Chunk getChunk() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Chunk target = null;
         target = (Chunk)this.get_store().find_element_user(CHUNK$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetChunk() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHUNK$6) != 0;
      }
   }

   public void setChunk(Chunk chunk) {
      this.generatedSetterHelperImpl(chunk, CHUNK$6, 0, (short)1);
   }

   public Chunk addNewChunk() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Chunk target = null;
         target = (Chunk)this.get_store().add_element_user(CHUNK$6);
         return target;
      }
   }

   public void unsetChunk() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CHUNK$6, 0);
      }
   }

   public Partition getPartition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Partition target = null;
         target = (Partition)this.get_store().find_element_user(PARTITION$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPartition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PARTITION$8) != 0;
      }
   }

   public void setPartition(Partition partition) {
      this.generatedSetterHelperImpl(partition, PARTITION$8, 0, (short)1);
   }

   public Partition addNewPartition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Partition target = null;
         target = (Partition)this.get_store().add_element_user(PARTITION$8);
         return target;
      }
   }

   public void unsetPartition() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PARTITION$8, 0);
      }
   }

   public End[] getEndArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(END$10, targetList);
         End[] result = new End[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public End getEndArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         End target = null;
         target = (End)this.get_store().find_element_user(END$10, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfEndArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(END$10);
      }
   }

   public void setEndArray(End[] endArray) {
      this.check_orphaned();
      this.arraySetterHelper(endArray, END$10);
   }

   public void setEndArray(int i, End end) {
      this.generatedSetterHelperImpl(end, END$10, i, (short)2);
   }

   public End insertNewEnd(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         End target = null;
         target = (End)this.get_store().insert_element_user(END$10, i);
         return target;
      }
   }

   public End addNewEnd() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         End target = null;
         target = (End)this.get_store().add_element_user(END$10);
         return target;
      }
   }

   public void removeEnd(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(END$10, i);
      }
   }

   public Fail[] getFailArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FAIL$12, targetList);
         Fail[] result = new Fail[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Fail getFailArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Fail target = null;
         target = (Fail)this.get_store().find_element_user(FAIL$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfFailArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FAIL$12);
      }
   }

   public void setFailArray(Fail[] failArray) {
      this.check_orphaned();
      this.arraySetterHelper(failArray, FAIL$12);
   }

   public void setFailArray(int i, Fail fail) {
      this.generatedSetterHelperImpl(fail, FAIL$12, i, (short)2);
   }

   public Fail insertNewFail(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Fail target = null;
         target = (Fail)this.get_store().insert_element_user(FAIL$12, i);
         return target;
      }
   }

   public Fail addNewFail() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Fail target = null;
         target = (Fail)this.get_store().add_element_user(FAIL$12);
         return target;
      }
   }

   public void removeFail(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FAIL$12, i);
      }
   }

   public Next[] getNextArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(NEXT$14, targetList);
         Next[] result = new Next[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Next getNextArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Next target = null;
         target = (Next)this.get_store().find_element_user(NEXT$14, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfNextArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NEXT$14);
      }
   }

   public void setNextArray(Next[] nextArray) {
      this.check_orphaned();
      this.arraySetterHelper(nextArray, NEXT$14);
   }

   public void setNextArray(int i, Next next) {
      this.generatedSetterHelperImpl(next, NEXT$14, i, (short)2);
   }

   public Next insertNewNext(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Next target = null;
         target = (Next)this.get_store().insert_element_user(NEXT$14, i);
         return target;
      }
   }

   public Next addNewNext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Next target = null;
         target = (Next)this.get_store().add_element_user(NEXT$14);
         return target;
      }
   }

   public void removeNext(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NEXT$14, i);
      }
   }

   public Stop[] getStopArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(STOP$16, targetList);
         Stop[] result = new Stop[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Stop getStopArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Stop target = null;
         target = (Stop)this.get_store().find_element_user(STOP$16, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfStopArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STOP$16);
      }
   }

   public void setStopArray(Stop[] stopArray) {
      this.check_orphaned();
      this.arraySetterHelper(stopArray, STOP$16);
   }

   public void setStopArray(int i, Stop stop) {
      this.generatedSetterHelperImpl(stop, STOP$16, i, (short)2);
   }

   public Stop insertNewStop(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Stop target = null;
         target = (Stop)this.get_store().insert_element_user(STOP$16, i);
         return target;
      }
   }

   public Stop addNewStop() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Stop target = null;
         target = (Stop)this.get_store().add_element_user(STOP$16);
         return target;
      }
   }

   public void removeStop(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STOP$16, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$18);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$18);
         return target;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$18);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$18);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$18);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$18);
         }

         target.set(id);
      }
   }

   public String getStartLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(STARTLIMIT$20);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetStartLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(STARTLIMIT$20);
         return target;
      }
   }

   public boolean isSetStartLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(STARTLIMIT$20) != null;
      }
   }

   public void setStartLimit(String startLimit) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(STARTLIMIT$20);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(STARTLIMIT$20);
         }

         target.setStringValue(startLimit);
      }
   }

   public void xsetStartLimit(XmlString startLimit) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(STARTLIMIT$20);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(STARTLIMIT$20);
         }

         target.set(startLimit);
      }
   }

   public void unsetStartLimit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(STARTLIMIT$20);
      }
   }

   public String getAllowStartIfComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ALLOWSTARTIFCOMPLETE$22);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAllowStartIfComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(ALLOWSTARTIFCOMPLETE$22);
         return target;
      }
   }

   public boolean isSetAllowStartIfComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ALLOWSTARTIFCOMPLETE$22) != null;
      }
   }

   public void setAllowStartIfComplete(String allowStartIfComplete) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ALLOWSTARTIFCOMPLETE$22);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ALLOWSTARTIFCOMPLETE$22);
         }

         target.setStringValue(allowStartIfComplete);
      }
   }

   public void xsetAllowStartIfComplete(XmlString allowStartIfComplete) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(ALLOWSTARTIFCOMPLETE$22);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(ALLOWSTARTIFCOMPLETE$22);
         }

         target.set(allowStartIfComplete);
      }
   }

   public void unsetAllowStartIfComplete() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ALLOWSTARTIFCOMPLETE$22);
      }
   }

   public String getNext2() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NEXT2$24);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNext2() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NEXT2$24);
         return target;
      }
   }

   public boolean isSetNext2() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(NEXT2$24) != null;
      }
   }

   public void setNext2(String next2) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NEXT2$24);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NEXT2$24);
         }

         target.setStringValue(next2);
      }
   }

   public void xsetNext2(XmlString next2) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NEXT2$24);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(NEXT2$24);
         }

         target.set(next2);
      }
   }

   public void unsetNext2() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(NEXT2$24);
      }
   }
}
