package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceInitParamType;
import com.oracle.xmlns.weblogic.weblogicCoherence.IdentityAsserterType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class IdentityAsserterTypeImpl extends XmlComplexContentImpl implements IdentityAsserterType {
   private static final long serialVersionUID = 1L;
   private static final QName CLASSNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "class-name");
   private static final QName COHERENCEINITPARAM$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-init-param");

   public IdentityAsserterTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLASSNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASSNAME$0, 0);
         return target;
      }
   }

   public boolean isNilClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASSNAME$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLASSNAME$0) != 0;
      }
   }

   public void setClassName(String className) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLASSNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CLASSNAME$0);
         }

         target.setStringValue(className);
      }
   }

   public void xsetClassName(XmlString className) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASSNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CLASSNAME$0);
         }

         target.set(className);
      }
   }

   public void setNilClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASSNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CLASSNAME$0);
         }

         target.setNil();
      }
   }

   public void unsetClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLASSNAME$0, 0);
      }
   }

   public CoherenceInitParamType[] getCoherenceInitParamArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(COHERENCEINITPARAM$2, targetList);
         CoherenceInitParamType[] result = new CoherenceInitParamType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CoherenceInitParamType getCoherenceInitParamArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceInitParamType target = null;
         target = (CoherenceInitParamType)this.get_store().find_element_user(COHERENCEINITPARAM$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCoherenceInitParamArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCEINITPARAM$2);
      }
   }

   public void setCoherenceInitParamArray(CoherenceInitParamType[] coherenceInitParamArray) {
      this.check_orphaned();
      this.arraySetterHelper(coherenceInitParamArray, COHERENCEINITPARAM$2);
   }

   public void setCoherenceInitParamArray(int i, CoherenceInitParamType coherenceInitParam) {
      this.generatedSetterHelperImpl(coherenceInitParam, COHERENCEINITPARAM$2, i, (short)2);
   }

   public CoherenceInitParamType insertNewCoherenceInitParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceInitParamType target = null;
         target = (CoherenceInitParamType)this.get_store().insert_element_user(COHERENCEINITPARAM$2, i);
         return target;
      }
   }

   public CoherenceInitParamType addNewCoherenceInitParam() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceInitParamType target = null;
         target = (CoherenceInitParamType)this.get_store().add_element_user(COHERENCEINITPARAM$2);
         return target;
      }
   }

   public void removeCoherenceInitParam(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCEINITPARAM$2, i);
      }
   }
}
