package com.oracle.xmlns.weblogic.weblogicWebApp.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicWebApp.JaspicProviderType;
import com.oracle.xmlns.weblogic.weblogicWebApp.TrueFalseType;
import javax.xml.namespace.QName;

public class JaspicProviderTypeImpl extends XmlComplexContentImpl implements JaspicProviderType {
   private static final long serialVersionUID = 1L;
   private static final QName ENABLED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "enabled");
   private static final QName AUTHCONFIGPROVIDERNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "auth-config-provider-name");

   public JaspicProviderTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TrueFalseType getEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ENABLED$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ENABLED$0) != 0;
      }
   }

   public void setEnabled(TrueFalseType enabled) {
      this.generatedSetterHelperImpl(enabled, ENABLED$0, 0, (short)1);
   }

   public TrueFalseType addNewEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ENABLED$0);
         return target;
      }
   }

   public void unsetEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ENABLED$0, 0);
      }
   }

   public String getAuthConfigProviderName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTHCONFIGPROVIDERNAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAuthConfigProviderName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTHCONFIGPROVIDERNAME$2, 0);
         return target;
      }
   }

   public boolean isSetAuthConfigProviderName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTHCONFIGPROVIDERNAME$2) != 0;
      }
   }

   public void setAuthConfigProviderName(String authConfigProviderName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTHCONFIGPROVIDERNAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(AUTHCONFIGPROVIDERNAME$2);
         }

         target.setStringValue(authConfigProviderName);
      }
   }

   public void xsetAuthConfigProviderName(XmlString authConfigProviderName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(AUTHCONFIGPROVIDERNAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(AUTHCONFIGPROVIDERNAME$2);
         }

         target.set(authConfigProviderName);
      }
   }

   public void unsetAuthConfigProviderName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTHCONFIGPROVIDERNAME$2, 0);
      }
   }
}
