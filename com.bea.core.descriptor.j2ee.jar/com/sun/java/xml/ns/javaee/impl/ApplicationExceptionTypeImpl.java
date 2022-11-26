package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.ApplicationExceptionType;
import com.sun.java.xml.ns.javaee.FullyQualifiedClassType;
import com.sun.java.xml.ns.javaee.TrueFalseType;
import javax.xml.namespace.QName;

public class ApplicationExceptionTypeImpl extends XmlComplexContentImpl implements ApplicationExceptionType {
   private static final long serialVersionUID = 1L;
   private static final QName EXCEPTIONCLASS$0 = new QName("http://java.sun.com/xml/ns/javaee", "exception-class");
   private static final QName ROLLBACK$2 = new QName("http://java.sun.com/xml/ns/javaee", "rollback");
   private static final QName INHERITED$4 = new QName("http://java.sun.com/xml/ns/javaee", "inherited");
   private static final QName ID$6 = new QName("", "id");

   public ApplicationExceptionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType getExceptionClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(EXCEPTIONCLASS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setExceptionClass(FullyQualifiedClassType exceptionClass) {
      this.generatedSetterHelperImpl(exceptionClass, EXCEPTIONCLASS$0, 0, (short)1);
   }

   public FullyQualifiedClassType addNewExceptionClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(EXCEPTIONCLASS$0);
         return target;
      }
   }

   public TrueFalseType getRollback() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ROLLBACK$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRollback() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ROLLBACK$2) != 0;
      }
   }

   public void setRollback(TrueFalseType rollback) {
      this.generatedSetterHelperImpl(rollback, ROLLBACK$2, 0, (short)1);
   }

   public TrueFalseType addNewRollback() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ROLLBACK$2);
         return target;
      }
   }

   public void unsetRollback() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ROLLBACK$2, 0);
      }
   }

   public TrueFalseType getInherited() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(INHERITED$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInherited() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INHERITED$4) != 0;
      }
   }

   public void setInherited(TrueFalseType inherited) {
      this.generatedSetterHelperImpl(inherited, INHERITED$4, 0, (short)1);
   }

   public TrueFalseType addNewInherited() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(INHERITED$4);
         return target;
      }
   }

   public void unsetInherited() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INHERITED$4, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$6);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$6);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$6);
      }
   }
}
