package com.oracle.xmlns.weblogic.weblogicWseeStandaloneclient.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicWseeStandaloneclient.WeblogicWseeStandaloneclientType;
import com.sun.java.xml.ns.j2Ee.ServiceRefType;
import javax.xml.namespace.QName;

public class WeblogicWseeStandaloneclientTypeImpl extends XmlComplexContentImpl implements WeblogicWseeStandaloneclientType {
   private static final long serialVersionUID = 1L;
   private static final QName SERVICEREF$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-wsee-standaloneclient", "service-ref");
   private static final QName VERSION$2 = new QName("", "version");

   public WeblogicWseeStandaloneclientTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ServiceRefType getServiceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().find_element_user(SERVICEREF$0, 0);
         return target == null ? null : target;
      }
   }

   public void setServiceRef(ServiceRefType serviceRef) {
      this.generatedSetterHelperImpl(serviceRef, SERVICEREF$0, 0, (short)1);
   }

   public ServiceRefType addNewServiceRef() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServiceRefType target = null;
         target = (ServiceRefType)this.get_store().add_element_user(SERVICEREF$0);
         return target;
      }
   }

   public String getVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$2);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$2);
         return target;
      }
   }

   public boolean isSetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(VERSION$2) != null;
      }
   }

   public void setVersion(String version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(VERSION$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(VERSION$2);
         }

         target.setStringValue(version);
      }
   }

   public void xsetVersion(XmlString version) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_attribute_user(VERSION$2);
         if (target == null) {
            target = (XmlString)this.get_store().add_attribute_user(VERSION$2);
         }

         target.set(version);
      }
   }

   public void unsetVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(VERSION$2);
      }
   }
}
