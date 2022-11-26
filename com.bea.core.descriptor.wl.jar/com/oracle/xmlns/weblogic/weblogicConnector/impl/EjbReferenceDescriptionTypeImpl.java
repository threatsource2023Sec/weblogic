package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.EjbReferenceDescriptionType;
import com.sun.java.xml.ns.javaee.EjbRefNameType;
import com.sun.java.xml.ns.javaee.JndiNameType;
import javax.xml.namespace.QName;

public class EjbReferenceDescriptionTypeImpl extends XmlComplexContentImpl implements EjbReferenceDescriptionType {
   private static final long serialVersionUID = 1L;
   private static final QName EJBREFNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "ejb-ref-name");
   private static final QName JNDINAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "jndi-name");
   private static final QName ID$4 = new QName("", "id");

   public EjbReferenceDescriptionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public EjbRefNameType getEjbRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefNameType target = null;
         target = (EjbRefNameType)this.get_store().find_element_user(EJBREFNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbRefName(EjbRefNameType ejbRefName) {
      this.generatedSetterHelperImpl(ejbRefName, EJBREFNAME$0, 0, (short)1);
   }

   public EjbRefNameType addNewEjbRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbRefNameType target = null;
         target = (EjbRefNameType)this.get_store().add_element_user(EJBREFNAME$0);
         return target;
      }
   }

   public JndiNameType getJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().find_element_user(JNDINAME$2, 0);
         return target == null ? null : target;
      }
   }

   public void setJndiName(JndiNameType jndiName) {
      this.generatedSetterHelperImpl(jndiName, JNDINAME$2, 0, (short)1);
   }

   public JndiNameType addNewJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().add_element_user(JNDINAME$2);
         return target;
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
