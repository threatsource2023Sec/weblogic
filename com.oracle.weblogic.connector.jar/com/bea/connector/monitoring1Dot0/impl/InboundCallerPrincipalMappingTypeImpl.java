package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.AnonPrincipalType;
import com.bea.connector.monitoring1Dot0.InboundCallerPrincipalMappingType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class InboundCallerPrincipalMappingTypeImpl extends XmlComplexContentImpl implements InboundCallerPrincipalMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName EISCALLERPRINCIPAL$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "eis-caller-principal");
   private static final QName MAPPEDCALLERPRINCIPAL$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "mapped-caller-principal");

   public InboundCallerPrincipalMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getEisCallerPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EISCALLERPRINCIPAL$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetEisCallerPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EISCALLERPRINCIPAL$0, 0);
         return target;
      }
   }

   public void setEisCallerPrincipal(String eisCallerPrincipal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EISCALLERPRINCIPAL$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(EISCALLERPRINCIPAL$0);
         }

         target.setStringValue(eisCallerPrincipal);
      }
   }

   public void xsetEisCallerPrincipal(XmlString eisCallerPrincipal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EISCALLERPRINCIPAL$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(EISCALLERPRINCIPAL$0);
         }

         target.set(eisCallerPrincipal);
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
}
