package com.oracle.xmlns.weblogic.weblogicExtension.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicExtension.ConfigurationSupportType;
import javax.xml.namespace.QName;

public class ConfigurationSupportTypeImpl extends XmlComplexContentImpl implements ConfigurationSupportType {
   private static final long serialVersionUID = 1L;
   private static final QName BASEROOTELEMENT$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "base-root-element");
   private static final QName CONFIGROOTELEMENT$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "config-root-element");
   private static final QName BASENAMESPACE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "base-namespace");
   private static final QName CONFIGNAMESPACE$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "config-namespace");
   private static final QName BASEURI$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "base-uri");
   private static final QName CONFIGURI$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "config-uri");
   private static final QName BASEPACKAGENAME$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "base-package-name");
   private static final QName CONFIGPACKAGENAME$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-extension", "config-package-name");

   public ConfigurationSupportTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getBaseRootElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASEROOTELEMENT$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetBaseRootElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BASEROOTELEMENT$0, 0);
         return target;
      }
   }

   public void setBaseRootElement(String baseRootElement) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASEROOTELEMENT$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BASEROOTELEMENT$0);
         }

         target.setStringValue(baseRootElement);
      }
   }

   public void xsetBaseRootElement(XmlString baseRootElement) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BASEROOTELEMENT$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BASEROOTELEMENT$0);
         }

         target.set(baseRootElement);
      }
   }

   public String getConfigRootElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONFIGROOTELEMENT$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConfigRootElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONFIGROOTELEMENT$2, 0);
         return target;
      }
   }

   public boolean isSetConfigRootElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONFIGROOTELEMENT$2) != 0;
      }
   }

   public void setConfigRootElement(String configRootElement) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONFIGROOTELEMENT$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONFIGROOTELEMENT$2);
         }

         target.setStringValue(configRootElement);
      }
   }

   public void xsetConfigRootElement(XmlString configRootElement) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONFIGROOTELEMENT$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONFIGROOTELEMENT$2);
         }

         target.set(configRootElement);
      }
   }

   public void unsetConfigRootElement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGROOTELEMENT$2, 0);
      }
   }

   public String getBaseNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASENAMESPACE$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetBaseNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BASENAMESPACE$4, 0);
         return target;
      }
   }

   public void setBaseNamespace(String baseNamespace) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASENAMESPACE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BASENAMESPACE$4);
         }

         target.setStringValue(baseNamespace);
      }
   }

   public void xsetBaseNamespace(XmlString baseNamespace) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BASENAMESPACE$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BASENAMESPACE$4);
         }

         target.set(baseNamespace);
      }
   }

   public String getConfigNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONFIGNAMESPACE$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConfigNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONFIGNAMESPACE$6, 0);
         return target;
      }
   }

   public boolean isSetConfigNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONFIGNAMESPACE$6) != 0;
      }
   }

   public void setConfigNamespace(String configNamespace) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONFIGNAMESPACE$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONFIGNAMESPACE$6);
         }

         target.setStringValue(configNamespace);
      }
   }

   public void xsetConfigNamespace(XmlString configNamespace) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONFIGNAMESPACE$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONFIGNAMESPACE$6);
         }

         target.set(configNamespace);
      }
   }

   public void unsetConfigNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGNAMESPACE$6, 0);
      }
   }

   public String getBaseUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASEURI$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetBaseUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BASEURI$8, 0);
         return target;
      }
   }

   public void setBaseUri(String baseUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASEURI$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BASEURI$8);
         }

         target.setStringValue(baseUri);
      }
   }

   public void xsetBaseUri(XmlString baseUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BASEURI$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BASEURI$8);
         }

         target.set(baseUri);
      }
   }

   public String getConfigUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONFIGURI$10, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConfigUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONFIGURI$10, 0);
         return target;
      }
   }

   public boolean isSetConfigUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONFIGURI$10) != 0;
      }
   }

   public void setConfigUri(String configUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONFIGURI$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONFIGURI$10);
         }

         target.setStringValue(configUri);
      }
   }

   public void xsetConfigUri(XmlString configUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONFIGURI$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONFIGURI$10);
         }

         target.set(configUri);
      }
   }

   public void unsetConfigUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGURI$10, 0);
      }
   }

   public String getBasePackageName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASEPACKAGENAME$12, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetBasePackageName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BASEPACKAGENAME$12, 0);
         return target;
      }
   }

   public void setBasePackageName(String basePackageName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASEPACKAGENAME$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BASEPACKAGENAME$12);
         }

         target.setStringValue(basePackageName);
      }
   }

   public void xsetBasePackageName(XmlString basePackageName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BASEPACKAGENAME$12, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BASEPACKAGENAME$12);
         }

         target.set(basePackageName);
      }
   }

   public String getConfigPackageName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONFIGPACKAGENAME$14, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConfigPackageName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONFIGPACKAGENAME$14, 0);
         return target;
      }
   }

   public boolean isSetConfigPackageName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONFIGPACKAGENAME$14) != 0;
      }
   }

   public void setConfigPackageName(String configPackageName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONFIGPACKAGENAME$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONFIGPACKAGENAME$14);
         }

         target.setStringValue(configPackageName);
      }
   }

   public void xsetConfigPackageName(XmlString configPackageName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONFIGPACKAGENAME$14, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONFIGPACKAGENAME$14);
         }

         target.set(configPackageName);
      }
   }

   public void unsetConfigPackageName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGPACKAGENAME$14, 0);
      }
   }
}
