package com.oracle.xmlns.weblogic.weblogicWebApp.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicWebApp.DestroyAsPrincipalNameType;
import com.oracle.xmlns.weblogic.weblogicWebApp.DispatchPolicyType;
import com.oracle.xmlns.weblogic.weblogicWebApp.InitAsPrincipalNameType;
import com.oracle.xmlns.weblogic.weblogicWebApp.RunAsPrincipalNameType;
import com.oracle.xmlns.weblogic.weblogicWebApp.ServletDescriptorType;
import com.sun.java.xml.ns.javaee.ServletNameType;
import javax.xml.namespace.QName;

public class ServletDescriptorTypeImpl extends XmlComplexContentImpl implements ServletDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName SERVLETNAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "servlet-name");
   private static final QName RUNASPRINCIPALNAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "run-as-principal-name");
   private static final QName INITASPRINCIPALNAME$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "init-as-principal-name");
   private static final QName DESTROYASPRINCIPALNAME$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "destroy-as-principal-name");
   private static final QName DISPATCHPOLICY$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-web-app", "dispatch-policy");
   private static final QName ID$10 = new QName("", "id");

   public ServletDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ServletNameType getServletName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletNameType target = null;
         target = (ServletNameType)this.get_store().find_element_user(SERVLETNAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setServletName(ServletNameType servletName) {
      this.generatedSetterHelperImpl(servletName, SERVLETNAME$0, 0, (short)1);
   }

   public ServletNameType addNewServletName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ServletNameType target = null;
         target = (ServletNameType)this.get_store().add_element_user(SERVLETNAME$0);
         return target;
      }
   }

   public RunAsPrincipalNameType getRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RunAsPrincipalNameType target = null;
         target = (RunAsPrincipalNameType)this.get_store().find_element_user(RUNASPRINCIPALNAME$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RUNASPRINCIPALNAME$2) != 0;
      }
   }

   public void setRunAsPrincipalName(RunAsPrincipalNameType runAsPrincipalName) {
      this.generatedSetterHelperImpl(runAsPrincipalName, RUNASPRINCIPALNAME$2, 0, (short)1);
   }

   public RunAsPrincipalNameType addNewRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RunAsPrincipalNameType target = null;
         target = (RunAsPrincipalNameType)this.get_store().add_element_user(RUNASPRINCIPALNAME$2);
         return target;
      }
   }

   public void unsetRunAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RUNASPRINCIPALNAME$2, 0);
      }
   }

   public InitAsPrincipalNameType getInitAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InitAsPrincipalNameType target = null;
         target = (InitAsPrincipalNameType)this.get_store().find_element_user(INITASPRINCIPALNAME$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetInitAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INITASPRINCIPALNAME$4) != 0;
      }
   }

   public void setInitAsPrincipalName(InitAsPrincipalNameType initAsPrincipalName) {
      this.generatedSetterHelperImpl(initAsPrincipalName, INITASPRINCIPALNAME$4, 0, (short)1);
   }

   public InitAsPrincipalNameType addNewInitAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         InitAsPrincipalNameType target = null;
         target = (InitAsPrincipalNameType)this.get_store().add_element_user(INITASPRINCIPALNAME$4);
         return target;
      }
   }

   public void unsetInitAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INITASPRINCIPALNAME$4, 0);
      }
   }

   public DestroyAsPrincipalNameType getDestroyAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestroyAsPrincipalNameType target = null;
         target = (DestroyAsPrincipalNameType)this.get_store().find_element_user(DESTROYASPRINCIPALNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDestroyAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESTROYASPRINCIPALNAME$6) != 0;
      }
   }

   public void setDestroyAsPrincipalName(DestroyAsPrincipalNameType destroyAsPrincipalName) {
      this.generatedSetterHelperImpl(destroyAsPrincipalName, DESTROYASPRINCIPALNAME$6, 0, (short)1);
   }

   public DestroyAsPrincipalNameType addNewDestroyAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestroyAsPrincipalNameType target = null;
         target = (DestroyAsPrincipalNameType)this.get_store().add_element_user(DESTROYASPRINCIPALNAME$6);
         return target;
      }
   }

   public void unsetDestroyAsPrincipalName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESTROYASPRINCIPALNAME$6, 0);
      }
   }

   public DispatchPolicyType getDispatchPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DispatchPolicyType target = null;
         target = (DispatchPolicyType)this.get_store().find_element_user(DISPATCHPOLICY$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDispatchPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DISPATCHPOLICY$8) != 0;
      }
   }

   public void setDispatchPolicy(DispatchPolicyType dispatchPolicy) {
      this.generatedSetterHelperImpl(dispatchPolicy, DISPATCHPOLICY$8, 0, (short)1);
   }

   public DispatchPolicyType addNewDispatchPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DispatchPolicyType target = null;
         target = (DispatchPolicyType)this.get_store().add_element_user(DISPATCHPOLICY$8);
         return target;
      }
   }

   public void unsetDispatchPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DISPATCHPOLICY$8, 0);
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
