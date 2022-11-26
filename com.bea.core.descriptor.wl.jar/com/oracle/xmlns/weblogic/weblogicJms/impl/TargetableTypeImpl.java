package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.TargetableType;
import javax.xml.namespace.QName;

public class TargetableTypeImpl extends NamedEntityTypeImpl implements TargetableType {
   private static final long serialVersionUID = 1L;
   private static final QName SUBDEPLOYMENTNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "sub-deployment-name");
   private static final QName DEFAULTTARGETINGENABLED$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "default-targeting-enabled");

   public TargetableTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getSubDeploymentName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUBDEPLOYMENTNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSubDeploymentName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SUBDEPLOYMENTNAME$0, 0);
         return target;
      }
   }

   public boolean isSetSubDeploymentName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUBDEPLOYMENTNAME$0) != 0;
      }
   }

   public void setSubDeploymentName(String subDeploymentName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUBDEPLOYMENTNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUBDEPLOYMENTNAME$0);
         }

         target.setStringValue(subDeploymentName);
      }
   }

   public void xsetSubDeploymentName(XmlString subDeploymentName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SUBDEPLOYMENTNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SUBDEPLOYMENTNAME$0);
         }

         target.set(subDeploymentName);
      }
   }

   public void unsetSubDeploymentName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUBDEPLOYMENTNAME$0, 0);
      }
   }

   public boolean getDefaultTargetingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTTARGETINGENABLED$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetDefaultTargetingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DEFAULTTARGETINGENABLED$2, 0);
         return target;
      }
   }

   public boolean isSetDefaultTargetingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTTARGETINGENABLED$2) != 0;
      }
   }

   public void setDefaultTargetingEnabled(boolean defaultTargetingEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTTARGETINGENABLED$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTTARGETINGENABLED$2);
         }

         target.setBooleanValue(defaultTargetingEnabled);
      }
   }

   public void xsetDefaultTargetingEnabled(XmlBoolean defaultTargetingEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DEFAULTTARGETINGENABLED$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(DEFAULTTARGETINGENABLED$2);
         }

         target.set(defaultTargetingEnabled);
      }
   }

   public void unsetDefaultTargetingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTTARGETINGENABLED$2, 0);
      }
   }
}
