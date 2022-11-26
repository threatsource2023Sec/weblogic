package com.oracle.xmlns.weblogic.weblogicRdbmsJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.ContextCaseType;
import com.oracle.xmlns.weblogic.weblogicRdbmsJar.ContextRequestClassType;
import com.sun.java.xml.ns.javaee.XsdStringType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ContextRequestClassTypeImpl extends XmlComplexContentImpl implements ContextRequestClassType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "name");
   private static final QName CONTEXTCASE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar", "context-case");
   private static final QName ID$4 = new QName("", "id");

   public ContextRequestClassTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdStringType getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setName(XsdStringType name) {
      this.generatedSetterHelperImpl(name, NAME$0, 0, (short)1);
   }

   public XsdStringType addNewName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdStringType target = null;
         target = (XsdStringType)this.get_store().add_element_user(NAME$0);
         return target;
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

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
