package com.oracle.xmlns.weblogic.deploymentPlan.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.deploymentPlan.ModuleDescriptorType;
import com.oracle.xmlns.weblogic.deploymentPlan.VariableAssignmentType;
import com.sun.java.xml.ns.j2Ee.PathType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ModuleDescriptorTypeImpl extends XmlComplexContentImpl implements ModuleDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName ROOTELEMENT$0 = new QName("http://xmlns.oracle.com/weblogic/deployment-plan", "root-element");
   private static final QName URI$2 = new QName("http://xmlns.oracle.com/weblogic/deployment-plan", "uri");
   private static final QName VARIABLEASSIGNMENT$4 = new QName("http://xmlns.oracle.com/weblogic/deployment-plan", "variable-assignment");
   private static final QName HASHCODE$6 = new QName("http://xmlns.oracle.com/weblogic/deployment-plan", "hash-code");
   private static final QName EXTERNAL$8 = new QName("", "external");

   public ModuleDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getRootElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ROOTELEMENT$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRootElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ROOTELEMENT$0, 0);
         return target;
      }
   }

   public void setRootElement(String rootElement) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ROOTELEMENT$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ROOTELEMENT$0);
         }

         target.setStringValue(rootElement);
      }
   }

   public void xsetRootElement(XmlString rootElement) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ROOTELEMENT$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ROOTELEMENT$0);
         }

         target.set(rootElement);
      }
   }

   public PathType getUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().find_element_user(URI$2, 0);
         return target == null ? null : target;
      }
   }

   public void setUri(PathType uri) {
      this.generatedSetterHelperImpl(uri, URI$2, 0, (short)1);
   }

   public PathType addNewUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PathType target = null;
         target = (PathType)this.get_store().add_element_user(URI$2);
         return target;
      }
   }

   public VariableAssignmentType[] getVariableAssignmentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(VARIABLEASSIGNMENT$4, targetList);
         VariableAssignmentType[] result = new VariableAssignmentType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public VariableAssignmentType getVariableAssignmentArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableAssignmentType target = null;
         target = (VariableAssignmentType)this.get_store().find_element_user(VARIABLEASSIGNMENT$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfVariableAssignmentArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(VARIABLEASSIGNMENT$4);
      }
   }

   public void setVariableAssignmentArray(VariableAssignmentType[] variableAssignmentArray) {
      this.check_orphaned();
      this.arraySetterHelper(variableAssignmentArray, VARIABLEASSIGNMENT$4);
   }

   public void setVariableAssignmentArray(int i, VariableAssignmentType variableAssignment) {
      this.generatedSetterHelperImpl(variableAssignment, VARIABLEASSIGNMENT$4, i, (short)2);
   }

   public VariableAssignmentType insertNewVariableAssignment(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableAssignmentType target = null;
         target = (VariableAssignmentType)this.get_store().insert_element_user(VARIABLEASSIGNMENT$4, i);
         return target;
      }
   }

   public VariableAssignmentType addNewVariableAssignment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         VariableAssignmentType target = null;
         target = (VariableAssignmentType)this.get_store().add_element_user(VARIABLEASSIGNMENT$4);
         return target;
      }
   }

   public void removeVariableAssignment(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(VARIABLEASSIGNMENT$4, i);
      }
   }

   public String getHashCode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HASHCODE$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetHashCode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(HASHCODE$6, 0);
         return target;
      }
   }

   public boolean isSetHashCode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HASHCODE$6) != 0;
      }
   }

   public void setHashCode(String hashCode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(HASHCODE$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(HASHCODE$6);
         }

         target.setStringValue(hashCode);
      }
   }

   public void xsetHashCode(XmlString hashCode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(HASHCODE$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(HASHCODE$6);
         }

         target.set(hashCode);
      }
   }

   public void unsetHashCode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HASHCODE$6, 0);
      }
   }

   public boolean getExternal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(EXTERNAL$8);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(EXTERNAL$8);
         }

         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetExternal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(EXTERNAL$8);
         if (target == null) {
            target = (XmlBoolean)this.get_default_attribute_value(EXTERNAL$8);
         }

         return target;
      }
   }

   public boolean isSetExternal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(EXTERNAL$8) != null;
      }
   }

   public void setExternal(boolean external) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(EXTERNAL$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(EXTERNAL$8);
         }

         target.setBooleanValue(external);
      }
   }

   public void xsetExternal(XmlBoolean external) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_attribute_user(EXTERNAL$8);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_attribute_user(EXTERNAL$8);
         }

         target.set(external);
      }
   }

   public void unsetExternal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(EXTERNAL$8);
      }
   }
}
