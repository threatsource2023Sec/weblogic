package com.bea.connector.diagnostic.impl;

import com.bea.connector.diagnostic.EndpointsType;
import com.bea.connector.diagnostic.InboundAdapterType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class InboundAdapterTypeImpl extends XmlComplexContentImpl implements InboundAdapterType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://www.bea.com/connector/diagnostic", "name");
   private static final QName STATE$2 = new QName("http://www.bea.com/connector/diagnostic", "state");
   private static final QName ENDPOINTS$4 = new QName("http://www.bea.com/connector/diagnostic", "endpoints");

   public InboundAdapterTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         return target;
      }
   }

   public void setName(String name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NAME$0);
         }

         target.setStringValue(name);
      }
   }

   public void xsetName(XmlString name) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NAME$0);
         }

         target.set(name);
      }
   }

   public String getState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STATE$2, 0);
         return target;
      }
   }

   public void setState(String state) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STATE$2);
         }

         target.setStringValue(state);
      }
   }

   public void xsetState(XmlString state) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STATE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(STATE$2);
         }

         target.set(state);
      }
   }

   public EndpointsType getEndpoints() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EndpointsType target = null;
         target = (EndpointsType)this.get_store().find_element_user(ENDPOINTS$4, 0);
         return target == null ? null : target;
      }
   }

   public void setEndpoints(EndpointsType endpoints) {
      this.generatedSetterHelperImpl(endpoints, ENDPOINTS$4, 0, (short)1);
   }

   public EndpointsType addNewEndpoints() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EndpointsType target = null;
         target = (EndpointsType)this.get_store().add_element_user(ENDPOINTS$4);
         return target;
      }
   }
}
