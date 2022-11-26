package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.LibraryContextRootOverrideType;
import javax.xml.namespace.QName;

public class LibraryContextRootOverrideTypeImpl extends XmlComplexContentImpl implements LibraryContextRootOverrideType {
   private static final long serialVersionUID = 1L;
   private static final QName CONTEXTROOT$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "context-root");
   private static final QName OVERRIDEVALUE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "override-value");

   public LibraryContextRootOverrideTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getContextRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONTEXTROOT$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetContextRoot() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONTEXTROOT$0, 0);
         return target;
      }
   }

   public void setContextRoot(String contextRoot) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONTEXTROOT$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONTEXTROOT$0);
         }

         target.setStringValue(contextRoot);
      }
   }

   public void xsetContextRoot(XmlString contextRoot) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONTEXTROOT$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONTEXTROOT$0);
         }

         target.set(contextRoot);
      }
   }

   public String getOverrideValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OVERRIDEVALUE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetOverrideValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(OVERRIDEVALUE$2, 0);
         return target;
      }
   }

   public void setOverrideValue(String overrideValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OVERRIDEVALUE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(OVERRIDEVALUE$2);
         }

         target.setStringValue(overrideValue);
      }
   }

   public void xsetOverrideValue(XmlString overrideValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(OVERRIDEVALUE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(OVERRIDEVALUE$2);
         }

         target.set(overrideValue);
      }
   }
}
