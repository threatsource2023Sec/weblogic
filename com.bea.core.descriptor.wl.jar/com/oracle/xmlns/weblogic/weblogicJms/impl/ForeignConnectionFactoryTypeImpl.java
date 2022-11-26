package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.EnabledDisabledType;
import com.oracle.xmlns.weblogic.weblogicJms.ForeignConnectionFactoryType;
import javax.xml.namespace.QName;

public class ForeignConnectionFactoryTypeImpl extends ForeignJndiObjectTypeImpl implements ForeignConnectionFactoryType {
   private static final long serialVersionUID = 1L;
   private static final QName USERNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "username");
   private static final QName PASSWORDENCRYPTED$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "password-encrypted");
   private static final QName CONNECTIONHEALTHCHECKING$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "connection-health-checking");

   public ForeignConnectionFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getUsername() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USERNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetUsername() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(USERNAME$0, 0);
         return target;
      }
   }

   public boolean isNilUsername() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(USERNAME$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetUsername() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USERNAME$0) != 0;
      }
   }

   public void setUsername(String username) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USERNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USERNAME$0);
         }

         target.setStringValue(username);
      }
   }

   public void xsetUsername(XmlString username) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(USERNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(USERNAME$0);
         }

         target.set(username);
      }
   }

   public void setNilUsername() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(USERNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(USERNAME$0);
         }

         target.setNil();
      }
   }

   public void unsetUsername() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USERNAME$0, 0);
      }
   }

   public String getPasswordEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PASSWORDENCRYPTED$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPasswordEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PASSWORDENCRYPTED$2, 0);
         return target;
      }
   }

   public boolean isNilPasswordEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PASSWORDENCRYPTED$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetPasswordEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PASSWORDENCRYPTED$2) != 0;
      }
   }

   public void setPasswordEncrypted(String passwordEncrypted) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PASSWORDENCRYPTED$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PASSWORDENCRYPTED$2);
         }

         target.setStringValue(passwordEncrypted);
      }
   }

   public void xsetPasswordEncrypted(XmlString passwordEncrypted) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PASSWORDENCRYPTED$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PASSWORDENCRYPTED$2);
         }

         target.set(passwordEncrypted);
      }
   }

   public void setNilPasswordEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PASSWORDENCRYPTED$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PASSWORDENCRYPTED$2);
         }

         target.setNil();
      }
   }

   public void unsetPasswordEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PASSWORDENCRYPTED$2, 0);
      }
   }

   public EnabledDisabledType.Enum getConnectionHealthChecking() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONHEALTHCHECKING$4, 0);
         return target == null ? null : (EnabledDisabledType.Enum)target.getEnumValue();
      }
   }

   public EnabledDisabledType xgetConnectionHealthChecking() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnabledDisabledType target = null;
         target = (EnabledDisabledType)this.get_store().find_element_user(CONNECTIONHEALTHCHECKING$4, 0);
         return target;
      }
   }

   public boolean isSetConnectionHealthChecking() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONHEALTHCHECKING$4) != 0;
      }
   }

   public void setConnectionHealthChecking(EnabledDisabledType.Enum connectionHealthChecking) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONHEALTHCHECKING$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONHEALTHCHECKING$4);
         }

         target.setEnumValue(connectionHealthChecking);
      }
   }

   public void xsetConnectionHealthChecking(EnabledDisabledType connectionHealthChecking) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EnabledDisabledType target = null;
         target = (EnabledDisabledType)this.get_store().find_element_user(CONNECTIONHEALTHCHECKING$4, 0);
         if (target == null) {
            target = (EnabledDisabledType)this.get_store().add_element_user(CONNECTIONHEALTHCHECKING$4);
         }

         target.set(connectionHealthChecking);
      }
   }

   public void unsetConnectionHealthChecking() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONHEALTHCHECKING$4, 0);
      }
   }
}
