package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.EjbLinkType;
import com.sun.java.xml.ns.j2Ee.ServiceImplBeanType;
import com.sun.java.xml.ns.j2Ee.ServletLinkType;
import javax.xml.namespace.QName;

public class ServiceImplBeanTypeImpl extends XmlComplexContentImpl implements ServiceImplBeanType {
   private static final long serialVersionUID = 1L;
   private static final QName EJBLINK$0 = new QName("http://java.sun.com/xml/ns/j2ee", "ejb-link");
   private static final QName SERVLETLINK$2 = new QName("http://java.sun.com/xml/ns/j2ee", "servlet-link");
   private static final QName ID$4 = new QName("", "id");

   public ServiceImplBeanTypeImpl(SchemaType sType) {
      super(sType);
   }

   public EjbLinkType getEjbLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLinkType target = null;
         target = (EjbLinkType)this.get_store().find_element_user(EJBLINK$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEjbLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EJBLINK$0) != 0;
      }
   }

   public void setEjbLink(EjbLinkType ejbLink) {
      this.generatedSetterHelperImpl(ejbLink, EJBLINK$0, 0, (short)1);
   }

   public EjbLinkType addNewEjbLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbLinkType target = null;
         target = (EjbLinkType)this.get_store().add_element_user(EJBLINK$0);
         return target;
      }
   }

   public void unsetEjbLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EJBLINK$0, 0);
      }
   }

   public ServletLinkType getServletLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletLinkType target = null;
         target = (ServletLinkType)this.get_store().find_element_user(SERVLETLINK$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetServletLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVLETLINK$2) != 0;
      }
   }

   public void setServletLink(ServletLinkType servletLink) {
      this.generatedSetterHelperImpl(servletLink, SERVLETLINK$2, 0, (short)1);
   }

   public ServletLinkType addNewServletLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletLinkType target = null;
         target = (ServletLinkType)this.get_store().add_element_user(SERVLETLINK$2);
         return target;
      }
   }

   public void unsetServletLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVLETLINK$2, 0);
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
