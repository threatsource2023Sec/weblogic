package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.javaee.ErrorCodeType;
import com.sun.java.xml.ns.javaee.ErrorPageType;
import com.sun.java.xml.ns.javaee.FullyQualifiedClassType;
import com.sun.java.xml.ns.javaee.WarPathType;
import javax.xml.namespace.QName;

public class ErrorPageTypeImpl extends XmlComplexContentImpl implements ErrorPageType {
   private static final long serialVersionUID = 1L;
   private static final QName ERRORCODE$0 = new QName("http://java.sun.com/xml/ns/javaee", "error-code");
   private static final QName EXCEPTIONTYPE$2 = new QName("http://java.sun.com/xml/ns/javaee", "exception-type");
   private static final QName LOCATION$4 = new QName("http://java.sun.com/xml/ns/javaee", "location");
   private static final QName ID$6 = new QName("", "id");

   public ErrorPageTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ErrorCodeType getErrorCode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ErrorCodeType target = null;
         target = (ErrorCodeType)this.get_store().find_element_user(ERRORCODE$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetErrorCode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ERRORCODE$0) != 0;
      }
   }

   public void setErrorCode(ErrorCodeType errorCode) {
      this.generatedSetterHelperImpl(errorCode, ERRORCODE$0, 0, (short)1);
   }

   public ErrorCodeType addNewErrorCode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ErrorCodeType target = null;
         target = (ErrorCodeType)this.get_store().add_element_user(ERRORCODE$0);
         return target;
      }
   }

   public void unsetErrorCode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ERRORCODE$0, 0);
      }
   }

   public FullyQualifiedClassType getExceptionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(EXCEPTIONTYPE$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetExceptionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXCEPTIONTYPE$2) != 0;
      }
   }

   public void setExceptionType(FullyQualifiedClassType exceptionType) {
      this.generatedSetterHelperImpl(exceptionType, EXCEPTIONTYPE$2, 0, (short)1);
   }

   public FullyQualifiedClassType addNewExceptionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(EXCEPTIONTYPE$2);
         return target;
      }
   }

   public void unsetExceptionType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXCEPTIONTYPE$2, 0);
      }
   }

   public WarPathType getLocation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WarPathType target = null;
         target = (WarPathType)this.get_store().find_element_user(LOCATION$4, 0);
         return target == null ? null : target;
      }
   }

   public void setLocation(WarPathType location) {
      this.generatedSetterHelperImpl(location, LOCATION$4, 0, (short)1);
   }

   public WarPathType addNewLocation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WarPathType target = null;
         target = (WarPathType)this.get_store().add_element_user(LOCATION$4);
         return target;
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
