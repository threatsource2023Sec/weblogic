package com.oracle.xmlns.weblogic.weblogicApplicationClient.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.oracle.xmlns.weblogic.weblogicApplicationClient.CdiDescriptorType;
import com.sun.java.xml.ns.javaee.String;
import com.sun.java.xml.ns.javaee.XsdBooleanType;
import javax.xml.namespace.QName;

public class CdiDescriptorTypeImpl extends XmlComplexContentImpl implements CdiDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName POJOANNOTATIONENABLED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "pojo-annotation-enabled");
   private static final QName IMPLICITBEANDISCOVERYENABLED$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "implicit-bean-discovery-enabled");
   private static final QName POLICY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application-client", "policy");

   public CdiDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdBooleanType getPojoAnnotationEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdBooleanType target = null;
         target = (XsdBooleanType)this.get_store().find_element_user(POJOANNOTATIONENABLED$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPojoAnnotationEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POJOANNOTATIONENABLED$0) != 0;
      }
   }

   public void setPojoAnnotationEnabled(XsdBooleanType pojoAnnotationEnabled) {
      this.generatedSetterHelperImpl(pojoAnnotationEnabled, POJOANNOTATIONENABLED$0, 0, (short)1);
   }

   public XsdBooleanType addNewPojoAnnotationEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdBooleanType target = null;
         target = (XsdBooleanType)this.get_store().add_element_user(POJOANNOTATIONENABLED$0);
         return target;
      }
   }

   public void unsetPojoAnnotationEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POJOANNOTATIONENABLED$0, 0);
      }
   }

   public XsdBooleanType getImplicitBeanDiscoveryEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdBooleanType target = null;
         target = (XsdBooleanType)this.get_store().find_element_user(IMPLICITBEANDISCOVERYENABLED$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetImplicitBeanDiscoveryEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IMPLICITBEANDISCOVERYENABLED$2) != 0;
      }
   }

   public void setImplicitBeanDiscoveryEnabled(XsdBooleanType implicitBeanDiscoveryEnabled) {
      this.generatedSetterHelperImpl(implicitBeanDiscoveryEnabled, IMPLICITBEANDISCOVERYENABLED$2, 0, (short)1);
   }

   public XsdBooleanType addNewImplicitBeanDiscoveryEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdBooleanType target = null;
         target = (XsdBooleanType)this.get_store().add_element_user(IMPLICITBEANDISCOVERYENABLED$2);
         return target;
      }
   }

   public void unsetImplicitBeanDiscoveryEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IMPLICITBEANDISCOVERYENABLED$2, 0);
      }
   }

   public String getPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(POLICY$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POLICY$4) != 0;
      }
   }

   public void setPolicy(String policy) {
      this.generatedSetterHelperImpl(policy, POLICY$4, 0, (short)1);
   }

   public String addNewPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(POLICY$4);
         return target;
      }
   }

   public void unsetPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POLICY$4, 0);
      }
   }
}
