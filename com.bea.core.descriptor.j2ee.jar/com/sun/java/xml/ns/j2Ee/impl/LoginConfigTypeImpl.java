package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.AuthMethodType;
import com.sun.java.xml.ns.j2Ee.FormLoginConfigType;
import com.sun.java.xml.ns.j2Ee.LoginConfigType;
import com.sun.java.xml.ns.j2Ee.String;
import javax.xml.namespace.QName;

public class LoginConfigTypeImpl extends XmlComplexContentImpl implements LoginConfigType {
   private static final long serialVersionUID = 1L;
   private static final QName AUTHMETHOD$0 = new QName("http://java.sun.com/xml/ns/j2ee", "auth-method");
   private static final QName REALMNAME$2 = new QName("http://java.sun.com/xml/ns/j2ee", "realm-name");
   private static final QName FORMLOGINCONFIG$4 = new QName("http://java.sun.com/xml/ns/j2ee", "form-login-config");
   private static final QName ID$6 = new QName("", "id");

   public LoginConfigTypeImpl(SchemaType sType) {
      super(sType);
   }

   public AuthMethodType getAuthMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthMethodType target = null;
         target = (AuthMethodType)this.get_store().find_element_user(AUTHMETHOD$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAuthMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTHMETHOD$0) != 0;
      }
   }

   public void setAuthMethod(AuthMethodType authMethod) {
      this.generatedSetterHelperImpl(authMethod, AUTHMETHOD$0, 0, (short)1);
   }

   public AuthMethodType addNewAuthMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AuthMethodType target = null;
         target = (AuthMethodType)this.get_store().add_element_user(AUTHMETHOD$0);
         return target;
      }
   }

   public void unsetAuthMethod() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTHMETHOD$0, 0);
      }
   }

   public String getRealmName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(REALMNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRealmName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REALMNAME$2) != 0;
      }
   }

   public void setRealmName(String realmName) {
      this.generatedSetterHelperImpl(realmName, REALMNAME$2, 0, (short)1);
   }

   public String addNewRealmName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(REALMNAME$2);
         return target;
      }
   }

   public void unsetRealmName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REALMNAME$2, 0);
      }
   }

   public FormLoginConfigType getFormLoginConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FormLoginConfigType target = null;
         target = (FormLoginConfigType)this.get_store().find_element_user(FORMLOGINCONFIG$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFormLoginConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FORMLOGINCONFIG$4) != 0;
      }
   }

   public void setFormLoginConfig(FormLoginConfigType formLoginConfig) {
      this.generatedSetterHelperImpl(formLoginConfig, FORMLOGINCONFIG$4, 0, (short)1);
   }

   public FormLoginConfigType addNewFormLoginConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FormLoginConfigType target = null;
         target = (FormLoginConfigType)this.get_store().add_element_user(FORMLOGINCONFIG$4);
         return target;
      }
   }

   public void unsetFormLoginConfig() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FORMLOGINCONFIG$4, 0);
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
