package com.oracle.xmlns.weblogic.multiVersionState.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.multiVersionState.ConfiguredIdType;
import com.oracle.xmlns.weblogic.multiVersionState.InferredIdType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ConfiguredIdTypeImpl extends XmlComplexContentImpl implements ConfiguredIdType {
   private static final long serialVersionUID = 1L;
   private static final QName INFERREDID$0 = new QName("http://xmlns.oracle.com/weblogic/multi-version-state", "inferred-id");
   private static final QName ID$2 = new QName("", "id");

   public ConfiguredIdTypeImpl(SchemaType sType) {
      super(sType);
   }

   public InferredIdType[] getInferredIdArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(INFERREDID$0, targetList);
         InferredIdType[] result = new InferredIdType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public InferredIdType getInferredIdArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InferredIdType target = null;
         target = (InferredIdType)this.get_store().find_element_user(INFERREDID$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfInferredIdArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INFERREDID$0);
      }
   }

   public void setInferredIdArray(InferredIdType[] inferredIdArray) {
      this.check_orphaned();
      this.arraySetterHelper(inferredIdArray, INFERREDID$0);
   }

   public void setInferredIdArray(int i, InferredIdType inferredId) {
      this.generatedSetterHelperImpl(inferredId, INFERREDID$0, i, (short)2);
   }

   public InferredIdType insertNewInferredId(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InferredIdType target = null;
         target = (InferredIdType)this.get_store().insert_element_user(INFERREDID$0, i);
         return target;
      }
   }

   public InferredIdType addNewInferredId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InferredIdType target = null;
         target = (InferredIdType)this.get_store().add_element_user(INFERREDID$0);
         return target;
      }
   }

   public void removeInferredId(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INFERREDID$0, i);
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
