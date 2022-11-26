package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.weblogicJms.SecurityParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.SecurityPolicyType;
import javax.xml.namespace.QName;

public class SecurityParamsTypeImpl extends XmlComplexContentImpl implements SecurityParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName ATTACHJMSXUSERID$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "attach-jmsx-user-id");
   private static final QName SECURITYPOLICY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "security-policy");

   public SecurityParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getAttachJmsxUserId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ATTACHJMSXUSERID$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetAttachJmsxUserId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ATTACHJMSXUSERID$0, 0);
         return target;
      }
   }

   public boolean isSetAttachJmsxUserId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ATTACHJMSXUSERID$0) != 0;
      }
   }

   public void setAttachJmsxUserId(boolean attachJmsxUserId) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ATTACHJMSXUSERID$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ATTACHJMSXUSERID$0);
         }

         target.setBooleanValue(attachJmsxUserId);
      }
   }

   public void xsetAttachJmsxUserId(XmlBoolean attachJmsxUserId) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ATTACHJMSXUSERID$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ATTACHJMSXUSERID$0);
         }

         target.set(attachJmsxUserId);
      }
   }

   public void unsetAttachJmsxUserId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ATTACHJMSXUSERID$0, 0);
      }
   }

   public SecurityPolicyType.Enum getSecurityPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SECURITYPOLICY$2, 0);
         return target == null ? null : (SecurityPolicyType.Enum)target.getEnumValue();
      }
   }

   public SecurityPolicyType xgetSecurityPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityPolicyType target = null;
         target = (SecurityPolicyType)this.get_store().find_element_user(SECURITYPOLICY$2, 0);
         return target;
      }
   }

   public boolean isSetSecurityPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYPOLICY$2) != 0;
      }
   }

   public void setSecurityPolicy(SecurityPolicyType.Enum securityPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SECURITYPOLICY$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SECURITYPOLICY$2);
         }

         target.setEnumValue(securityPolicy);
      }
   }

   public void xsetSecurityPolicy(SecurityPolicyType securityPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityPolicyType target = null;
         target = (SecurityPolicyType)this.get_store().find_element_user(SECURITYPOLICY$2, 0);
         if (target == null) {
            target = (SecurityPolicyType)this.get_store().add_element_user(SECURITYPOLICY$2);
         }

         target.set(securityPolicy);
      }
   }

   public void unsetSecurityPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYPOLICY$2, 0);
      }
   }
}
