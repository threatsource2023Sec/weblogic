package com.oracle.xmlns.weblogic.multiVersionState.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.multiVersionState.InferredIdType;
import com.oracle.xmlns.weblogic.multiVersionState.StateType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class InferredIdTypeImpl extends XmlComplexContentImpl implements InferredIdType {
   private static final long serialVersionUID = 1L;
   private static final QName STATE$0 = new QName("http://xmlns.oracle.com/weblogic/multi-version-state", "state");
   private static final QName ID$2 = new QName("", "id");

   public InferredIdTypeImpl(SchemaType sType) {
      super(sType);
   }

   public StateType[] getStateArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(STATE$0, targetList);
         StateType[] result = new StateType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public StateType getStateArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StateType target = null;
         target = (StateType)this.get_store().find_element_user(STATE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfStateArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STATE$0);
      }
   }

   public void setStateArray(StateType[] stateArray) {
      this.check_orphaned();
      this.arraySetterHelper(stateArray, STATE$0);
   }

   public void setStateArray(int i, StateType state) {
      this.generatedSetterHelperImpl(state, STATE$0, i, (short)2);
   }

   public StateType insertNewState(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StateType target = null;
         target = (StateType)this.get_store().insert_element_user(STATE$0, i);
         return target;
      }
   }

   public StateType addNewState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StateType target = null;
         target = (StateType)this.get_store().add_element_user(STATE$0);
         return target;
      }
   }

   public void removeState(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STATE$0, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(ID$2);
         return target;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$2);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlString id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(ID$2);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(ID$2);
         }

         target.set(id);
      }
   }
}
