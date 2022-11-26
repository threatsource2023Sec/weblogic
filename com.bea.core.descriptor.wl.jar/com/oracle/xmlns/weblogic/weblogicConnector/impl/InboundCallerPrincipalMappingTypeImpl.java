package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.AnonPrincipalType;
import com.oracle.xmlns.weblogic.weblogicConnector.InboundCallerPrincipalMappingType;
import com.sun.java.xml.ns.javaee.String;
import javax.xml.namespace.QName;

public class InboundCallerPrincipalMappingTypeImpl extends XmlComplexContentImpl implements InboundCallerPrincipalMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName EISCALLERPRINCIPAL$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "eis-caller-principal");
   private static final QName MAPPEDCALLERPRINCIPAL$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "mapped-caller-principal");
   private static final QName ID$4 = new QName("", "id");

   public InboundCallerPrincipalMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getEisCallerPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(EISCALLERPRINCIPAL$0, 0);
         return target == null ? null : target;
      }
   }

   public void setEisCallerPrincipal(String eisCallerPrincipal) {
      this.generatedSetterHelperImpl(eisCallerPrincipal, EISCALLERPRINCIPAL$0, 0, (short)1);
   }

   public String addNewEisCallerPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(EISCALLERPRINCIPAL$0);
         return target;
      }
   }

   public AnonPrincipalType getMappedCallerPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnonPrincipalType target = null;
         target = (AnonPrincipalType)this.get_store().find_element_user(MAPPEDCALLERPRINCIPAL$2, 0);
         return target == null ? null : target;
      }
   }

   public void setMappedCallerPrincipal(AnonPrincipalType mappedCallerPrincipal) {
      this.generatedSetterHelperImpl(mappedCallerPrincipal, MAPPEDCALLERPRINCIPAL$2, 0, (short)1);
   }

   public AnonPrincipalType addNewMappedCallerPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnonPrincipalType target = null;
         target = (AnonPrincipalType)this.get_store().add_element_user(MAPPEDCALLERPRINCIPAL$2);
         return target;
      }
   }

   public java.lang.String getId() {
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

   public void setId(java.lang.String id) {
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
