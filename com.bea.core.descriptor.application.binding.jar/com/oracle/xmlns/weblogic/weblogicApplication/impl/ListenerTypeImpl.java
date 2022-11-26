package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.ListenerType;
import javax.xml.namespace.QName;

public class ListenerTypeImpl extends XmlComplexContentImpl implements ListenerType {
   private static final long serialVersionUID = 1L;
   private static final QName LISTENERCLASS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "listener-class");
   private static final QName LISTENERURI$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "listener-uri");
   private static final QName RUNASPRINCIPALNAME$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "run-as-principal-name");

   public ListenerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getListenerClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LISTENERCLASS$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetListenerClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LISTENERCLASS$0, 0);
         return target;
      }
   }

   public void setListenerClass(String listenerClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LISTENERCLASS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LISTENERCLASS$0);
         }

         target.setStringValue(listenerClass);
      }
   }

   public void xsetListenerClass(XmlString listenerClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LISTENERCLASS$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LISTENERCLASS$0);
         }

         target.set(listenerClass);
      }
   }

   public String getListenerUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LISTENERURI$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetListenerUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LISTENERURI$2, 0);
         return target;
      }
   }

   public boolean isSetListenerUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LISTENERURI$2) != 0;
      }
   }

   public void setListenerUri(String listenerUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LISTENERURI$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LISTENERURI$2);
         }

         target.setStringValue(listenerUri);
      }
   }

   public void xsetListenerUri(XmlString listenerUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LISTENERURI$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LISTENERURI$2);
         }

         target.set(listenerUri);
      }
   }

   public void unsetListenerUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LISTENERURI$2, 0);
      }
   }

   public String getRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RUNASPRINCIPALNAME$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RUNASPRINCIPALNAME$4, 0);
         return target;
      }
   }

   public boolean isSetRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RUNASPRINCIPALNAME$4) != 0;
      }
   }

   public void setRunAsPrincipalName(String runAsPrincipalName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RUNASPRINCIPALNAME$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RUNASPRINCIPALNAME$4);
         }

         target.setStringValue(runAsPrincipalName);
      }
   }

   public void xsetRunAsPrincipalName(XmlString runAsPrincipalName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RUNASPRINCIPALNAME$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(RUNASPRINCIPALNAME$4);
         }

         target.set(runAsPrincipalName);
      }
   }

   public void unsetRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RUNASPRINCIPALNAME$4, 0);
      }
   }
}
