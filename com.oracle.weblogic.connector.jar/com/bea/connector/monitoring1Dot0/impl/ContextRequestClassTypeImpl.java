package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ContextCaseType;
import com.bea.connector.monitoring1Dot0.ContextRequestClassType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ContextRequestClassTypeImpl extends XmlComplexContentImpl implements ContextRequestClassType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "name");
   private static final QName CONTEXTCASE$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "context-case");

   public ContextRequestClassTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAME$0);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAME$0);
         }

         target.set(name);
      }
   }

   public ContextCaseType[] getContextCaseArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONTEXTCASE$2, targetList);
         ContextCaseType[] result = new ContextCaseType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ContextCaseType getContextCaseArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContextCaseType target = null;
         target = (ContextCaseType)this.get_store().find_element_user(CONTEXTCASE$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfContextCaseArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONTEXTCASE$2);
      }
   }

   public void setContextCaseArray(ContextCaseType[] contextCaseArray) {
      this.check_orphaned();
      this.arraySetterHelper(contextCaseArray, CONTEXTCASE$2);
   }

   public void setContextCaseArray(int i, ContextCaseType contextCase) {
      this.generatedSetterHelperImpl(contextCase, CONTEXTCASE$2, i, (short)2);
   }

   public ContextCaseType insertNewContextCase(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContextCaseType target = null;
         target = (ContextCaseType)this.get_store().insert_element_user(CONTEXTCASE$2, i);
         return target;
      }
   }

   public ContextCaseType addNewContextCase() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ContextCaseType target = null;
         target = (ContextCaseType)this.get_store().add_element_user(CONTEXTCASE$2);
         return target;
      }
   }

   public void removeContextCase(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONTEXTCASE$2, i);
      }
   }
}
