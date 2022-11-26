package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.InboundGroupPrincipalMappingType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class InboundGroupPrincipalMappingTypeImpl extends XmlComplexContentImpl implements InboundGroupPrincipalMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName EISGROUPPRINCIPAL$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "eis-group-principal");
   private static final QName MAPPEDGROUPPRINCIPAL$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "mapped-group-principal");

   public InboundGroupPrincipalMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getEisGroupPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EISGROUPPRINCIPAL$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetEisGroupPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EISGROUPPRINCIPAL$0, 0);
         return target;
      }
   }

   public void setEisGroupPrincipal(String eisGroupPrincipal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EISGROUPPRINCIPAL$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(EISGROUPPRINCIPAL$0);
         }

         target.setStringValue(eisGroupPrincipal);
      }
   }

   public void xsetEisGroupPrincipal(XmlString eisGroupPrincipal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EISGROUPPRINCIPAL$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(EISGROUPPRINCIPAL$0);
         }

         target.set(eisGroupPrincipal);
      }
   }

   public String getMappedGroupPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAPPEDGROUPPRINCIPAL$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMappedGroupPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAPPEDGROUPPRINCIPAL$2, 0);
         return target;
      }
   }

   public void setMappedGroupPrincipal(String mappedGroupPrincipal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAPPEDGROUPPRINCIPAL$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAPPEDGROUPPRINCIPAL$2);
         }

         target.setStringValue(mappedGroupPrincipal);
      }
   }

   public void xsetMappedGroupPrincipal(XmlString mappedGroupPrincipal) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MAPPEDGROUPPRINCIPAL$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MAPPEDGROUPPRINCIPAL$2);
         }

         target.set(mappedGroupPrincipal);
      }
   }
}
