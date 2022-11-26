package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicApplication.SecurityPermissionType;
import com.sun.java.xml.ns.javaee.DescriptionType;
import com.sun.java.xml.ns.javaee.String;
import javax.xml.namespace.QName;

public class SecurityPermissionTypeImpl extends XmlComplexContentImpl implements SecurityPermissionType {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "description");
   private static final QName SECURITYPERMISSIONSPEC$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "security-permission-spec");
   private static final QName ID$4 = new QName("", "id");

   public SecurityPermissionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DescriptionType getDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESCRIPTION$0) != 0;
      }
   }

   public void setDescription(DescriptionType description) {
      this.generatedSetterHelperImpl(description, DESCRIPTION$0, 0, (short)1);
   }

   public DescriptionType addNewDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DescriptionType target = null;
         target = (DescriptionType)this.get_store().add_element_user(DESCRIPTION$0);
         return target;
      }
   }

   public void unsetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESCRIPTION$0, 0);
      }
   }

   public String getSecurityPermissionSpec() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(SECURITYPERMISSIONSPEC$2, 0);
         return target == null ? null : target;
      }
   }

   public void setSecurityPermissionSpec(String securityPermissionSpec) {
      this.generatedSetterHelperImpl(securityPermissionSpec, SECURITYPERMISSIONSPEC$2, 0, (short)1);
   }

   public String addNewSecurityPermissionSpec() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(SECURITYPERMISSIONSPEC$2);
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
