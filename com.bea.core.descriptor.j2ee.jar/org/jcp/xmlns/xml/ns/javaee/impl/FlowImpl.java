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
import org.jcp.xmlns.xml.ns.javaee.End;
import org.jcp.xmlns.xml.ns.javaee.Fail;
import org.jcp.xmlns.xml.ns.javaee.Flow;
import org.jcp.xmlns.xml.ns.javaee.Next;
import org.jcp.xmlns.xml.ns.javaee.Split;
import org.jcp.xmlns.xml.ns.javaee.Step;
import org.jcp.xmlns.xml.ns.javaee.Stop;

public class FlowImpl extends XmlComplexContentImpl implements Flow {
   private static final long serialVersionUID = 1L;
   private static final QName DECISION$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "decision");
   private static final QName FLOW$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "flow");
   private static final QName SPLIT$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "split");
   private static final QName STEP$6 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "step");
   private static final QName END$8 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "end");
   private static final QName FAIL$10 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "fail");
   private static final QName NEXT$12 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "next");
   private static final QName STOP$14 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "stop");
   private static final QName ID$16 = new QName("", "id");
   private static final QName NEXT2$18 = new QName("", "next");

   public FlowImpl(SchemaType sType) {
      super(sType);
   }

   public Decision[] getDecisionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DECISION$0, targetList);
         Decision[] result = new Decision[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Decision getDecisionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Decision target = null;
         target = (Decision)this.get_store().find_element_user(DECISION$0, i);
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
         return this.get_store().count_elements(DECISION$0);
      }
   }

   public void setDecisionArray(Decision[] decisionArray) {
      this.check_orphaned();
      this.arraySetterHelper(decisionArray, DECISION$0);
   }

   public void setDecisionArray(int i, Decision decision) {
      this.generatedSetterHelperImpl(decision, DECISION$0, i, (short)2);
   }

   public Decision insertNewDecision(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Decision target = null;
         target = (Decision)this.get_store().insert_element_user(DECISION$0, i);
         return target;
      }
   }

   public Decision addNewDecision() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Decision target = null;
         target = (Decision)this.get_store().add_element_user(DECISION$0);
         return target;
      }
   }

   public void removeDecision(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DECISION$0, i);
      }
   }

   public Flow[] getFlowArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FLOW$2, targetList);
         Flow[] result = new Flow[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Flow getFlowArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Flow target = null;
         target = (Flow)this.get_store().find_element_user(FLOW$2, i);
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
         return this.get_store().count_elements(FLOW$2);
      }
   }

   public void setFlowArray(Flow[] flowArray) {
      this.check_orphaned();
      this.arraySetterHelper(flowArray, FLOW$2);
   }

   public void setFlowArray(int i, Flow flow) {
      this.generatedSetterHelperImpl(flow, FLOW$2, i, (short)2);
   }

   public Flow insertNewFlow(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Flow target = null;
         target = (Flow)this.get_store().insert_element_user(FLOW$2, i);
         return target;
      }
   }

   public Flow addNewFlow() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Flow target = null;
         target = (Flow)this.get_store().add_element_user(FLOW$2);
         return target;
      }
   }

   public void removeFlow(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FLOW$2, i);
      }
   }

   public Split[] getSplitArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SPLIT$4, targetList);
         Split[] result = new Split[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Split getSplitArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Split target = null;
         target = (Split)this.get_store().find_element_user(SPLIT$4, i);
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
         return this.get_store().count_elements(SPLIT$4);
      }
   }

   public void setSplitArray(Split[] splitArray) {
      this.check_orphaned();
      this.arraySetterHelper(splitArray, SPLIT$4);
   }

   public void setSplitArray(int i, Split split) {
      this.generatedSetterHelperImpl(split, SPLIT$4, i, (short)2);
   }

   public Split insertNewSplit(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Split target = null;
         target = (Split)this.get_store().insert_element_user(SPLIT$4, i);
         return target;
      }
   }

   public Split addNewSplit() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Split target = null;
         target = (Split)this.get_store().add_element_user(SPLIT$4);
         return target;
      }
   }

   public void removeSplit(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SPLIT$4, i);
      }
   }

   public Step[] getStepArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(STEP$6, targetList);
         Step[] result = new Step[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Step getStepArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Step target = null;
         target = (Step)this.get_store().find_element_user(STEP$6, i);
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
         return this.get_store().count_elements(STEP$6);
      }
   }

   public void setStepArray(Step[] stepArray) {
      this.check_orphaned();
      this.arraySetterHelper(stepArray, STEP$6);
   }

   public void setStepArray(int i, Step step) {
      this.generatedSetterHelperImpl(step, STEP$6, i, (short)2);
   }

   public Step insertNewStep(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Step target = null;
         target = (Step)this.get_store().insert_element_user(STEP$6, i);
         return target;
      }
   }

   public Step addNewStep() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Step target = null;
         target = (Step)this.get_store().add_element_user(STEP$6);
         return target;
      }
   }

   public void removeStep(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STEP$6, i);
      }
   }

   public End[] getEndArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(END$8, targetList);
         End[] result = new End[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public End getEndArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         End target = null;
         target = (End)this.get_store().find_element_user(END$8, i);
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
         return this.get_store().count_elements(END$8);
      }
   }

   public void setEndArray(End[] endArray) {
      this.check_orphaned();
      this.arraySetterHelper(endArray, END$8);
   }

   public void setEndArray(int i, End end) {
      this.generatedSetterHelperImpl(end, END$8, i, (short)2);
   }

   public End insertNewEnd(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         End target = null;
         target = (End)this.get_store().insert_element_user(END$8, i);
         return target;
      }
   }

   public End addNewEnd() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         End target = null;
         target = (End)this.get_store().add_element_user(END$8);
         return target;
      }
   }

   public void removeEnd(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(END$8, i);
      }
   }

   public Fail[] getFailArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(FAIL$10, targetList);
         Fail[] result = new Fail[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Fail getFailArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Fail target = null;
         target = (Fail)this.get_store().find_element_user(FAIL$10, i);
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
         return this.get_store().count_elements(FAIL$10);
      }
   }

   public void setFailArray(Fail[] failArray) {
      this.check_orphaned();
      this.arraySetterHelper(failArray, FAIL$10);
   }

   public void setFailArray(int i, Fail fail) {
      this.generatedSetterHelperImpl(fail, FAIL$10, i, (short)2);
   }

   public Fail insertNewFail(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Fail target = null;
         target = (Fail)this.get_store().insert_element_user(FAIL$10, i);
         return target;
      }
   }

   public Fail addNewFail() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Fail target = null;
         target = (Fail)this.get_store().add_element_user(FAIL$10);
         return target;
      }
   }

   public void removeFail(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FAIL$10, i);
      }
   }

   public Next[] getNextArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(NEXT$12, targetList);
         Next[] result = new Next[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Next getNextArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Next target = null;
         target = (Next)this.get_store().find_element_user(NEXT$12, i);
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
         return this.get_store().count_elements(NEXT$12);
      }
   }

   public void setNextArray(Next[] nextArray) {
      this.check_orphaned();
      this.arraySetterHelper(nextArray, NEXT$12);
   }

   public void setNextArray(int i, Next next) {
      this.generatedSetterHelperImpl(next, NEXT$12, i, (short)2);
   }

   public Next insertNewNext(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Next target = null;
         target = (Next)this.get_store().insert_element_user(NEXT$12, i);
         return target;
      }
   }

   public Next addNewNext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Next target = null;
         target = (Next)this.get_store().add_element_user(NEXT$12);
         return target;
      }
   }

   public void removeNext(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NEXT$12, i);
      }
   }

   public Stop[] getStopArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(STOP$14, targetList);
         Stop[] result = new Stop[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public Stop getStopArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Stop target = null;
         target = (Stop)this.get_store().find_element_user(STOP$14, i);
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
         return this.get_store().count_elements(STOP$14);
      }
   }

   public void setStopArray(Stop[] stopArray) {
      this.check_orphaned();
      this.arraySetterHelper(stopArray, STOP$14);
   }

   public void setStopArray(int i, Stop stop) {
      this.generatedSetterHelperImpl(stop, STOP$14, i, (short)2);
   }

   public Stop insertNewStop(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Stop target = null;
         target = (Stop)this.get_store().insert_element_user(STOP$14, i);
         return target;
      }
   }

   public Stop addNewStop() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Stop target = null;
         target = (Stop)this.get_store().add_element_user(STOP$14);
         return target;
      }
   }

   public void removeStop(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STOP$14, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$16);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$16);
         return target;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$16);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$16);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$16);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$16);
         }

         target.set(id);
      }
   }

   public String getNext2() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NEXT2$18);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNext2() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NEXT2$18);
         return target;
      }
   }

   public boolean isSetNext2() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(NEXT2$18) != null;
      }
   }

   public void setNext2(String next2) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NEXT2$18);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NEXT2$18);
         }

         target.setStringValue(next2);
      }
   }

   public void xsetNext2(XmlString next2) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(NEXT2$18);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(NEXT2$18);
         }

         target.set(next2);
      }
   }

   public void unsetNext2() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(NEXT2$18);
      }
   }
}
