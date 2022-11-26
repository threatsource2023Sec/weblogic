package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.AnonPrincipalCallerType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class AnonPrincipalCallerTypeImpl extends XmlComplexContentImpl implements AnonPrincipalCallerType {
   private static final long serialVersionUID = 1L;
   private static final QName USEANONYMOUSIDENTITY$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "use-anonymous-identity");
   private static final QName PRINCIPALNAME$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "principal-name");
   private static final QName USECALLERIDENTITY$4 = new QName("http://www.bea.com/connector/monitoring1dot0", "use-caller-identity");

   public AnonPrincipalCallerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getUseAnonymousIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USEANONYMOUSIDENTITY$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseAnonymousIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USEANONYMOUSIDENTITY$0, 0);
         return target;
      }
   }

   public boolean isSetUseAnonymousIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USEANONYMOUSIDENTITY$0) != 0;
      }
   }

   public void setUseAnonymousIdentity(boolean useAnonymousIdentity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USEANONYMOUSIDENTITY$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USEANONYMOUSIDENTITY$0);
         }

         target.setBooleanValue(useAnonymousIdentity);
      }
   }

   public void xsetUseAnonymousIdentity(XmlBoolean useAnonymousIdentity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USEANONYMOUSIDENTITY$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USEANONYMOUSIDENTITY$0);
         }

         target.set(useAnonymousIdentity);
      }
   }

   public void unsetUseAnonymousIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USEANONYMOUSIDENTITY$0, 0);
      }
   }

   public String getPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRINCIPALNAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRINCIPALNAME$2, 0);
         return target;
      }
   }

   public boolean isSetPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRINCIPALNAME$2) != 0;
      }
   }

   public void setPrincipalName(String principalName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRINCIPALNAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PRINCIPALNAME$2);
         }

         target.setStringValue(principalName);
      }
   }

   public void xsetPrincipalName(XmlString principalName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(PRINCIPALNAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(PRINCIPALNAME$2);
         }

         target.set(principalName);
      }
   }

   public void unsetPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRINCIPALNAME$2, 0);
      }
   }

   public boolean getUseCallerIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USECALLERIDENTITY$4, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseCallerIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USECALLERIDENTITY$4, 0);
         return target;
      }
   }

   public boolean isSetUseCallerIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USECALLERIDENTITY$4) != 0;
      }
   }

   public void setUseCallerIdentity(boolean useCallerIdentity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USECALLERIDENTITY$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USECALLERIDENTITY$4);
         }

         target.setBooleanValue(useCallerIdentity);
      }
   }

   public void xsetUseCallerIdentity(XmlBoolean useCallerIdentity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USECALLERIDENTITY$4, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USECALLERIDENTITY$4);
         }

         target.set(useCallerIdentity);
      }
   }

   public void unsetUseCallerIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USECALLERIDENTITY$4, 0);
      }
   }
}
