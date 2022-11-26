package com.bea.ns.weblogic.x90.impl;

import com.bea.ns.weblogic.x90.ResourceDescriptionType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.JndiNameType;
import com.sun.java.xml.ns.j2Ee.String;
import javax.xml.namespace.QName;

public class ResourceDescriptionTypeImpl extends XmlComplexContentImpl implements ResourceDescriptionType {
   private static final long serialVersionUID = 1L;
   private static final QName RESREFNAME$0 = new QName("http://www.bea.com/ns/weblogic/90", "res-ref-name");
   private static final QName JNDINAME$2 = new QName("http://www.bea.com/ns/weblogic/90", "jndi-name");
   private static final QName RESOURCELINK$4 = new QName("http://www.bea.com/ns/weblogic/90", "resource-link");
   private static final QName ID$6 = new QName("", "id");

   public ResourceDescriptionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public JndiNameType getResRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().find_element_user(RESREFNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setResRefName(JndiNameType resRefName) {
      this.generatedSetterHelperImpl(resRefName, RESREFNAME$0, 0, (short)1);
   }

   public JndiNameType addNewResRefName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().add_element_user(RESREFNAME$0);
         return target;
      }
   }

   public String getJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(JNDINAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JNDINAME$2) != 0;
      }
   }

   public void setJndiName(String jndiName) {
      this.generatedSetterHelperImpl(jndiName, JNDINAME$2, 0, (short)1);
   }

   public String addNewJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(JNDINAME$2);
         return target;
      }
   }

   public void unsetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JNDINAME$2, 0);
      }
   }

   public String getResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(RESOURCELINK$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESOURCELINK$4) != 0;
      }
   }

   public void setResourceLink(String resourceLink) {
      this.generatedSetterHelperImpl(resourceLink, RESOURCELINK$4, 0, (short)1);
   }

   public String addNewResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(RESOURCELINK$4);
         return target;
      }
   }

   public void unsetResourceLink() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESOURCELINK$4, 0);
      }
   }

   public java.lang.String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(java.lang.String id) {
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

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$6);
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
