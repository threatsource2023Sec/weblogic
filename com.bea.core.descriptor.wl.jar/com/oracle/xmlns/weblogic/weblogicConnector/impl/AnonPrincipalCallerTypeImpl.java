package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.AnonPrincipalCallerType;
import com.oracle.xmlns.weblogic.weblogicConnector.TrueFalseType;
import com.sun.java.xml.ns.javaee.String;
import javax.xml.namespace.QName;

public class AnonPrincipalCallerTypeImpl extends XmlComplexContentImpl implements AnonPrincipalCallerType {
   private static final long serialVersionUID = 1L;
   private static final QName USEANONYMOUSIDENTITY$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "use-anonymous-identity");
   private static final QName PRINCIPALNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "principal-name");
   private static final QName USECALLERIDENTITY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "use-caller-identity");
   private static final QName ID$6 = new QName("", "id");

   public AnonPrincipalCallerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TrueFalseType getUseAnonymousIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(USEANONYMOUSIDENTITY$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUseAnonymousIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USEANONYMOUSIDENTITY$0) != 0;
      }
   }

   public void setUseAnonymousIdentity(TrueFalseType useAnonymousIdentity) {
      this.generatedSetterHelperImpl(useAnonymousIdentity, USEANONYMOUSIDENTITY$0, 0, (short)1);
   }

   public TrueFalseType addNewUseAnonymousIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(USEANONYMOUSIDENTITY$0);
         return target;
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
         String target = null;
         target = (String)this.get_store().find_element_user(PRINCIPALNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRINCIPALNAME$2) != 0;
      }
   }

   public void setPrincipalName(String principalName) {
      this.generatedSetterHelperImpl(principalName, PRINCIPALNAME$2, 0, (short)1);
   }

   public String addNewPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(PRINCIPALNAME$2);
         return target;
      }
   }

   public void unsetPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRINCIPALNAME$2, 0);
      }
   }

   public TrueFalseType getUseCallerIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(USECALLERIDENTITY$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUseCallerIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USECALLERIDENTITY$4) != 0;
      }
   }

   public void setUseCallerIdentity(TrueFalseType useCallerIdentity) {
      this.generatedSetterHelperImpl(useCallerIdentity, USECALLERIDENTITY$4, 0, (short)1);
   }

   public TrueFalseType addNewUseCallerIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(USECALLERIDENTITY$4);
         return target;
      }
   }

   public void unsetUseCallerIdentity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USECALLERIDENTITY$4, 0);
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
