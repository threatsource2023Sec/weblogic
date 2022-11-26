package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.InboundGroupPrincipalMappingType;
import com.sun.java.xml.ns.javaee.String;
import javax.xml.namespace.QName;

public class InboundGroupPrincipalMappingTypeImpl extends XmlComplexContentImpl implements InboundGroupPrincipalMappingType {
   private static final long serialVersionUID = 1L;
   private static final QName EISGROUPPRINCIPAL$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "eis-group-principal");
   private static final QName MAPPEDGROUPPRINCIPAL$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "mapped-group-principal");
   private static final QName ID$4 = new QName("", "id");

   public InboundGroupPrincipalMappingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getEisGroupPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(EISGROUPPRINCIPAL$0, 0);
         return target == null ? null : target;
      }
   }

   public void setEisGroupPrincipal(String eisGroupPrincipal) {
      this.generatedSetterHelperImpl(eisGroupPrincipal, EISGROUPPRINCIPAL$0, 0, (short)1);
   }

   public String addNewEisGroupPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(EISGROUPPRINCIPAL$0);
         return target;
      }
   }

   public String getMappedGroupPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(MAPPEDGROUPPRINCIPAL$2, 0);
         return target == null ? null : target;
      }
   }

   public void setMappedGroupPrincipal(String mappedGroupPrincipal) {
      this.generatedSetterHelperImpl(mappedGroupPrincipal, MAPPEDGROUPPRINCIPAL$2, 0, (short)1);
   }

   public String addNewMappedGroupPrincipal() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(MAPPEDGROUPPRINCIPAL$2);
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
