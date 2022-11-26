package com.oracle.xmlns.weblogic.webservicePolicyRef.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.oracle.xmlns.weblogic.webservicePolicyRef.StatusType;
import com.oracle.xmlns.weblogic.webservicePolicyRef.WsPolicyType;
import com.sun.java.xml.ns.j2Ee.String;
import javax.xml.namespace.QName;

public class WsPolicyTypeImpl extends XmlComplexContentImpl implements WsPolicyType {
   private static final long serialVersionUID = 1L;
   private static final QName URI$0 = new QName("http://xmlns.oracle.com/weblogic/webservice-policy-ref", "uri");
   private static final QName DIRECTION$2 = new QName("http://xmlns.oracle.com/weblogic/webservice-policy-ref", "direction");
   private static final QName STATUS$4 = new QName("http://xmlns.oracle.com/weblogic/webservice-policy-ref", "status");

   public WsPolicyTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(URI$0, 0);
         return target == null ? null : target;
      }
   }

   public void setUri(String uri) {
      this.generatedSetterHelperImpl(uri, URI$0, 0, (short)1);
   }

   public String addNewUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(URI$0);
         return target;
      }
   }

   public String getDirection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(DIRECTION$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDirection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DIRECTION$2) != 0;
      }
   }

   public void setDirection(String direction) {
      this.generatedSetterHelperImpl(direction, DIRECTION$2, 0, (short)1);
   }

   public String addNewDirection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(DIRECTION$2);
         return target;
      }
   }

   public void unsetDirection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DIRECTION$2, 0);
      }
   }

   public StatusType.Enum getStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATUS$4, 0);
         return target == null ? null : (StatusType.Enum)target.getEnumValue();
      }
   }

   public StatusType xgetStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatusType target = null;
         target = (StatusType)this.get_store().find_element_user(STATUS$4, 0);
         return target;
      }
   }

   public boolean isSetStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STATUS$4) != 0;
      }
   }

   public void setStatus(StatusType.Enum status) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATUS$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STATUS$4);
         }

         target.setEnumValue(status);
      }
   }

   public void xsetStatus(StatusType status) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatusType target = null;
         target = (StatusType)this.get_store().find_element_user(STATUS$4, 0);
         if (target == null) {
            target = (StatusType)this.get_store().add_element_user(STATUS$4);
         }

         target.set(status);
      }
   }

   public void unsetStatus() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STATUS$4, 0);
      }
   }
}
