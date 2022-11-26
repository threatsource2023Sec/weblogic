package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.SafLoginContextType;
import javax.xml.namespace.QName;

public class SafLoginContextTypeImpl extends XmlComplexContentImpl implements SafLoginContextType {
   private static final long serialVersionUID = 1L;
   private static final QName LOGINURL$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "loginURL");
   private static final QName USERNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "username");
   private static final QName PASSWORDENCRYPTED$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "password-encrypted");

   public SafLoginContextTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getLoginURL() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGINURL$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLoginURL() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOGINURL$0, 0);
         return target;
      }
   }

   public void setLoginURL(String loginURL) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOGINURL$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOGINURL$0);
         }

         target.setStringValue(loginURL);
      }
   }

   public void xsetLoginURL(XmlString loginURL) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOGINURL$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOGINURL$0);
         }

         target.set(loginURL);
      }
   }

   public String getUsername() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USERNAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetUsername() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(USERNAME$2, 0);
         return target;
      }
   }

   public boolean isSetUsername() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USERNAME$2) != 0;
      }
   }

   public void setUsername(String username) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USERNAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USERNAME$2);
         }

         target.setStringValue(username);
      }
   }

   public void xsetUsername(XmlString username) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(USERNAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(USERNAME$2);
         }

         target.set(username);
      }
   }

   public void unsetUsername() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USERNAME$2, 0);
      }
   }

   public String getPasswordEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PASSWORDENCRYPTED$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPasswordEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PASSWORDENCRYPTED$4, 0);
         return target;
      }
   }

   public boolean isSetPasswordEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PASSWORDENCRYPTED$4) != 0;
      }
   }

   public void setPasswordEncrypted(String passwordEncrypted) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PASSWORDENCRYPTED$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PASSWORDENCRYPTED$4);
         }

         target.setStringValue(passwordEncrypted);
      }
   }

   public void xsetPasswordEncrypted(XmlString passwordEncrypted) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PASSWORDENCRYPTED$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PASSWORDENCRYPTED$4);
         }

         target.set(passwordEncrypted);
      }
   }

   public void unsetPasswordEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PASSWORDENCRYPTED$4, 0);
      }
   }
}
