package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.AnonPrincipalCallerType;
import com.oracle.xmlns.weblogic.weblogicConnector.AnonPrincipalType;
import com.oracle.xmlns.weblogic.weblogicConnector.ResourceAdapterSecurityType;
import com.oracle.xmlns.weblogic.weblogicConnector.SecurityWorkContextType;
import javax.xml.namespace.QName;

public class ResourceAdapterSecurityTypeImpl extends XmlComplexContentImpl implements ResourceAdapterSecurityType {
   private static final long serialVersionUID = 1L;
   private static final QName DEFAULTPRINCIPALNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "default-principal-name");
   private static final QName MANAGEASPRINCIPALNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "manage-as-principal-name");
   private static final QName RUNASPRINCIPALNAME$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "run-as-principal-name");
   private static final QName RUNWORKASPRINCIPALNAME$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "run-work-as-principal-name");
   private static final QName SECURITYWORKCONTEXT$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "security-work-context");
   private static final QName ID$10 = new QName("", "id");

   public ResourceAdapterSecurityTypeImpl(SchemaType sType) {
      super(sType);
   }

   public AnonPrincipalType getDefaultPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnonPrincipalType target = null;
         target = (AnonPrincipalType)this.get_store().find_element_user(DEFAULTPRINCIPALNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDefaultPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTPRINCIPALNAME$0) != 0;
      }
   }

   public void setDefaultPrincipalName(AnonPrincipalType defaultPrincipalName) {
      this.generatedSetterHelperImpl(defaultPrincipalName, DEFAULTPRINCIPALNAME$0, 0, (short)1);
   }

   public AnonPrincipalType addNewDefaultPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnonPrincipalType target = null;
         target = (AnonPrincipalType)this.get_store().add_element_user(DEFAULTPRINCIPALNAME$0);
         return target;
      }
   }

   public void unsetDefaultPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTPRINCIPALNAME$0, 0);
      }
   }

   public AnonPrincipalType getManageAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnonPrincipalType target = null;
         target = (AnonPrincipalType)this.get_store().find_element_user(MANAGEASPRINCIPALNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetManageAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MANAGEASPRINCIPALNAME$2) != 0;
      }
   }

   public void setManageAsPrincipalName(AnonPrincipalType manageAsPrincipalName) {
      this.generatedSetterHelperImpl(manageAsPrincipalName, MANAGEASPRINCIPALNAME$2, 0, (short)1);
   }

   public AnonPrincipalType addNewManageAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnonPrincipalType target = null;
         target = (AnonPrincipalType)this.get_store().add_element_user(MANAGEASPRINCIPALNAME$2);
         return target;
      }
   }

   public void unsetManageAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MANAGEASPRINCIPALNAME$2, 0);
      }
   }

   public AnonPrincipalCallerType getRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnonPrincipalCallerType target = null;
         target = (AnonPrincipalCallerType)this.get_store().find_element_user(RUNASPRINCIPALNAME$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RUNASPRINCIPALNAME$4) != 0;
      }
   }

   public void setRunAsPrincipalName(AnonPrincipalCallerType runAsPrincipalName) {
      this.generatedSetterHelperImpl(runAsPrincipalName, RUNASPRINCIPALNAME$4, 0, (short)1);
   }

   public AnonPrincipalCallerType addNewRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnonPrincipalCallerType target = null;
         target = (AnonPrincipalCallerType)this.get_store().add_element_user(RUNASPRINCIPALNAME$4);
         return target;
      }
   }

   public void unsetRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RUNASPRINCIPALNAME$4, 0);
      }
   }

   public AnonPrincipalCallerType getRunWorkAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnonPrincipalCallerType target = null;
         target = (AnonPrincipalCallerType)this.get_store().find_element_user(RUNWORKASPRINCIPALNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRunWorkAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RUNWORKASPRINCIPALNAME$6) != 0;
      }
   }

   public void setRunWorkAsPrincipalName(AnonPrincipalCallerType runWorkAsPrincipalName) {
      this.generatedSetterHelperImpl(runWorkAsPrincipalName, RUNWORKASPRINCIPALNAME$6, 0, (short)1);
   }

   public AnonPrincipalCallerType addNewRunWorkAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AnonPrincipalCallerType target = null;
         target = (AnonPrincipalCallerType)this.get_store().add_element_user(RUNWORKASPRINCIPALNAME$6);
         return target;
      }
   }

   public void unsetRunWorkAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RUNWORKASPRINCIPALNAME$6, 0);
      }
   }

   public SecurityWorkContextType getSecurityWorkContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityWorkContextType target = null;
         target = (SecurityWorkContextType)this.get_store().find_element_user(SECURITYWORKCONTEXT$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSecurityWorkContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYWORKCONTEXT$8) != 0;
      }
   }

   public void setSecurityWorkContext(SecurityWorkContextType securityWorkContext) {
      this.generatedSetterHelperImpl(securityWorkContext, SECURITYWORKCONTEXT$8, 0, (short)1);
   }

   public SecurityWorkContextType addNewSecurityWorkContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityWorkContextType target = null;
         target = (SecurityWorkContextType)this.get_store().add_element_user(SECURITYWORKCONTEXT$8);
         return target;
      }
   }

   public void unsetSecurityWorkContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYWORKCONTEXT$8, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$10) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$10);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$10);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$10);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$10);
      }
   }
}
