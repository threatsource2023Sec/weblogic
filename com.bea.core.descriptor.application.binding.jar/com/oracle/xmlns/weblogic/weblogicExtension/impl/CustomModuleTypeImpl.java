package com.oracle.xmlns.weblogic.weblogicExtension.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicExtension.ConfigurationSupportType;
import com.oracle.xmlns.weblogic.weblogicExtension.CustomModuleType;
import javax.xml.namespace.QName;

public class CustomModuleTypeImpl extends XmlComplexContentImpl implements CustomModuleType {
   private static final long serialVersionUID = 1L;
   private static final QName URI$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "uri");
   private static final QName PROVIDERNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "provider-name");
   private static final QName CONFIGURATIONSUPPORT$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "configuration-support");

   public CustomModuleTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(URI$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(URI$0, 0);
         return target;
      }
   }

   public void setUri(String uri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(URI$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(URI$0);
         }

         target.setStringValue(uri);
      }
   }

   public void xsetUri(XmlString uri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(URI$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(URI$0);
         }

         target.set(uri);
      }
   }

   public String getProviderName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PROVIDERNAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetProviderName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PROVIDERNAME$2, 0);
         return target;
      }
   }

   public void setProviderName(String providerName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PROVIDERNAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PROVIDERNAME$2);
         }

         target.setStringValue(providerName);
      }
   }

   public void xsetProviderName(XmlString providerName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PROVIDERNAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PROVIDERNAME$2);
         }

         target.set(providerName);
      }
   }

   public ConfigurationSupportType getConfigurationSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigurationSupportType target = null;
         target = (ConfigurationSupportType)this.get_store().find_element_user(CONFIGURATIONSUPPORT$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetConfigurationSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONFIGURATIONSUPPORT$4) != 0;
      }
   }

   public void setConfigurationSupport(ConfigurationSupportType configurationSupport) {
      this.generatedSetterHelperImpl(configurationSupport, CONFIGURATIONSUPPORT$4, 0, (short)1);
   }

   public ConfigurationSupportType addNewConfigurationSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigurationSupportType target = null;
         target = (ConfigurationSupportType)this.get_store().add_element_user(CONFIGURATIONSUPPORT$4);
         return target;
      }
   }

   public void unsetConfigurationSupport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGURATIONSUPPORT$4, 0);
      }
   }
}
