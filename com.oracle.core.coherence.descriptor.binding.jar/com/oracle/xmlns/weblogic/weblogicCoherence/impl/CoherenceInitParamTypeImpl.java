package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlNMTOKEN;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceInitParamType;
import javax.xml.namespace.QName;

public class CoherenceInitParamTypeImpl extends XmlComplexContentImpl implements CoherenceInitParamType {
   private static final long serialVersionUID = 1L;
   private static final QName NAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "name");
   private static final QName PARAMTYPE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "param-type");
   private static final QName PARAMVALUE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "param-value");
   private static final QName ID$6 = new QName("", "id");

   public CoherenceInitParamTypeImpl(SchemaType sType) {
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

   public String getParamType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARAMTYPE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetParamType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARAMTYPE$2, 0);
         return target;
      }
   }

   public boolean isSetParamType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PARAMTYPE$2) != 0;
      }
   }

   public void setParamType(String paramType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARAMTYPE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PARAMTYPE$2);
         }

         target.setStringValue(paramType);
      }
   }

   public void xsetParamType(XmlString paramType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARAMTYPE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PARAMTYPE$2);
         }

         target.set(paramType);
      }
   }

   public void unsetParamType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PARAMTYPE$2, 0);
      }
   }

   public String getParamValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARAMVALUE$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetParamValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARAMVALUE$4, 0);
         return target;
      }
   }

   public boolean isSetParamValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PARAMVALUE$4) != 0;
      }
   }

   public void setParamValue(String paramValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PARAMVALUE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PARAMVALUE$4);
         }

         target.setStringValue(paramValue);
      }
   }

   public void xsetParamValue(XmlString paramValue) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PARAMVALUE$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PARAMVALUE$4);
         }

         target.set(paramValue);
      }
   }

   public void unsetParamValue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PARAMVALUE$4, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlNMTOKEN xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNMTOKEN target = null;
         target = (XmlNMTOKEN)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$6);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlNMTOKEN id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlNMTOKEN target = null;
         target = (XmlNMTOKEN)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlNMTOKEN)this.get_store().add_attribute_user(ID$6);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$6);
      }
   }
}
