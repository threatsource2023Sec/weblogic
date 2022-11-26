package com.bea.wls.jms.message.impl;

import com.bea.wls.jms.message.DestinationType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class DestinationTypeImpl extends XmlComplexContentImpl implements DestinationType {
   private static final long serialVersionUID = 1L;
   private static final QName DESTINATION$0 = new QName("http://www.bea.com/WLS/JMS/Message", "Destination");

   public DestinationTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DestinationType.Destination getDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestinationType.Destination target = null;
         target = (DestinationType.Destination)this.get_store().find_element_user(DESTINATION$0, 0);
         return target == null ? null : target;
      }
   }

   public void setDestination(DestinationType.Destination destination) {
      this.generatedSetterHelperImpl(destination, DESTINATION$0, 0, (short)1);
   }

   public DestinationType.Destination addNewDestination() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestinationType.Destination target = null;
         target = (DestinationType.Destination)this.get_store().add_element_user(DESTINATION$0);
         return target;
      }
   }

   public static class DestinationImpl extends XmlComplexContentImpl implements DestinationType.Destination {
      private static final long serialVersionUID = 1L;
      private static final QName NAME$0 = new QName("", "name");
      private static final QName SERVERNAME$2 = new QName("", "serverName");
      private static final QName SERVERURL$4 = new QName("", "serverURL");
      private static final QName APPLICATIONNAME$6 = new QName("", "applicationName");
      private static final QName MODULENAME$8 = new QName("", "moduleName");

      public DestinationImpl(SchemaType sType) {
         super(sType);
      }

      public String getName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(NAME$0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(NAME$0);
            return target;
         }
      }

      public void setName(String name) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(NAME$0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(NAME$0);
            }

            target.setStringValue(name);
         }
      }

      public void xsetName(XmlString name) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(NAME$0);
            if (target == null) {
               target = (XmlString)this.get_store().add_attribute_user(NAME$0);
            }

            target.set(name);
         }
      }

      public String getServerName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SERVERNAME$2);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetServerName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(SERVERNAME$2);
            return target;
         }
      }

      public void setServerName(String serverName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SERVERNAME$2);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(SERVERNAME$2);
            }

            target.setStringValue(serverName);
         }
      }

      public void xsetServerName(XmlString serverName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(SERVERNAME$2);
            if (target == null) {
               target = (XmlString)this.get_store().add_attribute_user(SERVERNAME$2);
            }

            target.set(serverName);
         }
      }

      public String getServerURL() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SERVERURL$4);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetServerURL() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(SERVERURL$4);
            return target;
         }
      }

      public boolean isSetServerURL() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(SERVERURL$4) != null;
         }
      }

      public void setServerURL(String serverURL) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SERVERURL$4);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(SERVERURL$4);
            }

            target.setStringValue(serverURL);
         }
      }

      public void xsetServerURL(XmlString serverURL) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(SERVERURL$4);
            if (target == null) {
               target = (XmlString)this.get_store().add_attribute_user(SERVERURL$4);
            }

            target.set(serverURL);
         }
      }

      public void unsetServerURL() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(SERVERURL$4);
         }
      }

      public String getApplicationName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(APPLICATIONNAME$6);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetApplicationName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(APPLICATIONNAME$6);
            return target;
         }
      }

      public boolean isSetApplicationName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(APPLICATIONNAME$6) != null;
         }
      }

      public void setApplicationName(String applicationName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(APPLICATIONNAME$6);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(APPLICATIONNAME$6);
            }

            target.setStringValue(applicationName);
         }
      }

      public void xsetApplicationName(XmlString applicationName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(APPLICATIONNAME$6);
            if (target == null) {
               target = (XmlString)this.get_store().add_attribute_user(APPLICATIONNAME$6);
            }

            target.set(applicationName);
         }
      }

      public void unsetApplicationName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(APPLICATIONNAME$6);
         }
      }

      public String getModuleName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(MODULENAME$8);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetModuleName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(MODULENAME$8);
            return target;
         }
      }

      public boolean isSetModuleName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(MODULENAME$8) != null;
         }
      }

      public void setModuleName(String moduleName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(MODULENAME$8);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(MODULENAME$8);
            }

            target.setStringValue(moduleName);
         }
      }

      public void xsetModuleName(XmlString moduleName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_attribute_user(MODULENAME$8);
            if (target == null) {
               target = (XmlString)this.get_store().add_attribute_user(MODULENAME$8);
            }

            target.set(moduleName);
         }
      }

      public void unsetModuleName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(MODULENAME$8);
         }
      }
   }
}
