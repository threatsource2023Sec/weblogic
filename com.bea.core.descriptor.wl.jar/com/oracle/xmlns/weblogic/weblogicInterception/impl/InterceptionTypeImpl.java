package com.oracle.xmlns.weblogic.weblogicInterception.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicInterception.AssociationType;
import com.oracle.xmlns.weblogic.weblogicInterception.InterceptionType;
import com.oracle.xmlns.weblogic.weblogicInterception.ProcessorType;
import com.oracle.xmlns.weblogic.weblogicInterception.ProcessorTypeType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class InterceptionTypeImpl extends XmlComplexContentImpl implements InterceptionType {
   private static final long serialVersionUID = 1L;
   private static final QName ASSOCIATION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-interception", "association");
   private static final QName PROCESSOR$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-interception", "processor");
   private static final QName PROCESSORTYPE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-interception", "processor-type");
   private static final QName VERSION$6 = new QName("", "version");

   public InterceptionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public AssociationType[] getAssociationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(ASSOCIATION$0, targetList);
         AssociationType[] result = new AssociationType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public AssociationType getAssociationArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AssociationType target = null;
         target = (AssociationType)this.get_store().find_element_user(ASSOCIATION$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfAssociationArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ASSOCIATION$0);
      }
   }

   public void setAssociationArray(AssociationType[] associationArray) {
      this.check_orphaned();
      this.arraySetterHelper(associationArray, ASSOCIATION$0);
   }

   public void setAssociationArray(int i, AssociationType association) {
      this.generatedSetterHelperImpl(association, ASSOCIATION$0, i, (short)2);
   }

   public AssociationType insertNewAssociation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AssociationType target = null;
         target = (AssociationType)this.get_store().insert_element_user(ASSOCIATION$0, i);
         return target;
      }
   }

   public AssociationType addNewAssociation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AssociationType target = null;
         target = (AssociationType)this.get_store().add_element_user(ASSOCIATION$0);
         return target;
      }
   }

   public void removeAssociation(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ASSOCIATION$0, i);
      }
   }

   public ProcessorType[] getProcessorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PROCESSOR$2, targetList);
         ProcessorType[] result = new ProcessorType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ProcessorType getProcessorArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProcessorType target = null;
         target = (ProcessorType)this.get_store().find_element_user(PROCESSOR$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfProcessorArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROCESSOR$2);
      }
   }

   public void setProcessorArray(ProcessorType[] processorArray) {
      this.check_orphaned();
      this.arraySetterHelper(processorArray, PROCESSOR$2);
   }

   public void setProcessorArray(int i, ProcessorType processor) {
      this.generatedSetterHelperImpl(processor, PROCESSOR$2, i, (short)2);
   }

   public ProcessorType insertNewProcessor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProcessorType target = null;
         target = (ProcessorType)this.get_store().insert_element_user(PROCESSOR$2, i);
         return target;
      }
   }

   public ProcessorType addNewProcessor() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProcessorType target = null;
         target = (ProcessorType)this.get_store().add_element_user(PROCESSOR$2);
         return target;
      }
   }

   public void removeProcessor(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROCESSOR$2, i);
      }
   }

   public ProcessorTypeType[] getProcessorTypeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(PROCESSORTYPE$4, targetList);
         ProcessorTypeType[] result = new ProcessorTypeType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ProcessorTypeType getProcessorTypeArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProcessorTypeType target = null;
         target = (ProcessorTypeType)this.get_store().find_element_user(PROCESSORTYPE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfProcessorTypeArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROCESSORTYPE$4);
      }
   }

   public void setProcessorTypeArray(ProcessorTypeType[] processorTypeArray) {
      this.check_orphaned();
      this.arraySetterHelper(processorTypeArray, PROCESSORTYPE$4);
   }

   public void setProcessorTypeArray(int i, ProcessorTypeType processorType) {
      this.generatedSetterHelperImpl(processorType, PROCESSORTYPE$4, i, (short)2);
   }

   public ProcessorTypeType insertNewProcessorType(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProcessorTypeType target = null;
         target = (ProcessorTypeType)this.get_store().insert_element_user(PROCESSORTYPE$4, i);
         return target;
      }
   }

   public ProcessorTypeType addNewProcessorType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProcessorTypeType target = null;
         target = (ProcessorTypeType)this.get_store().add_element_user(PROCESSORTYPE$4);
         return target;
      }
   }

   public void removeProcessorType(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROCESSORTYPE$4, i);
      }
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$6);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(VERSION$6) != null;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$6);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$6);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VERSION$6);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(VERSION$6);
      }
   }
}
