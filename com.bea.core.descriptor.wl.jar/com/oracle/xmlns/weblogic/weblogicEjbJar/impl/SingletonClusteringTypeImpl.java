package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.CallRouterClassNameType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.LoadAlgorithmType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.SingletonClusteringType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TrueFalseType;
import javax.xml.namespace.QName;

public class SingletonClusteringTypeImpl extends XmlComplexContentImpl implements SingletonClusteringType {
   private static final long serialVersionUID = 1L;
   private static final QName USESERVERSIDESTUBS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "use-serverside-stubs");
   private static final QName SINGLETONBEANISCLUSTERABLE$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "singleton-bean-is-clusterable");
   private static final QName SINGLETONBEANLOADALGORITHM$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "singleton-bean-load-algorithm");
   private static final QName SINGLETONBEANCALLROUTERCLASSNAME$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "singleton-bean-call-router-class-name");
   private static final QName ID$8 = new QName("", "id");

   public SingletonClusteringTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TrueFalseType getUseServersideStubs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(USESERVERSIDESTUBS$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUseServersideStubs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USESERVERSIDESTUBS$0) != 0;
      }
   }

   public void setUseServersideStubs(TrueFalseType useServersideStubs) {
      this.generatedSetterHelperImpl(useServersideStubs, USESERVERSIDESTUBS$0, 0, (short)1);
   }

   public TrueFalseType addNewUseServersideStubs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(USESERVERSIDESTUBS$0);
         return target;
      }
   }

   public void unsetUseServersideStubs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USESERVERSIDESTUBS$0, 0);
      }
   }

   public TrueFalseType getSingletonBeanIsClusterable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(SINGLETONBEANISCLUSTERABLE$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSingletonBeanIsClusterable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SINGLETONBEANISCLUSTERABLE$2) != 0;
      }
   }

   public void setSingletonBeanIsClusterable(TrueFalseType singletonBeanIsClusterable) {
      this.generatedSetterHelperImpl(singletonBeanIsClusterable, SINGLETONBEANISCLUSTERABLE$2, 0, (short)1);
   }

   public TrueFalseType addNewSingletonBeanIsClusterable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(SINGLETONBEANISCLUSTERABLE$2);
         return target;
      }
   }

   public void unsetSingletonBeanIsClusterable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SINGLETONBEANISCLUSTERABLE$2, 0);
      }
   }

   public LoadAlgorithmType getSingletonBeanLoadAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoadAlgorithmType target = null;
         target = (LoadAlgorithmType)this.get_store().find_element_user(SINGLETONBEANLOADALGORITHM$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSingletonBeanLoadAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SINGLETONBEANLOADALGORITHM$4) != 0;
      }
   }

   public void setSingletonBeanLoadAlgorithm(LoadAlgorithmType singletonBeanLoadAlgorithm) {
      this.generatedSetterHelperImpl(singletonBeanLoadAlgorithm, SINGLETONBEANLOADALGORITHM$4, 0, (short)1);
   }

   public LoadAlgorithmType addNewSingletonBeanLoadAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoadAlgorithmType target = null;
         target = (LoadAlgorithmType)this.get_store().add_element_user(SINGLETONBEANLOADALGORITHM$4);
         return target;
      }
   }

   public void unsetSingletonBeanLoadAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SINGLETONBEANLOADALGORITHM$4, 0);
      }
   }

   public CallRouterClassNameType getSingletonBeanCallRouterClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CallRouterClassNameType target = null;
         target = (CallRouterClassNameType)this.get_store().find_element_user(SINGLETONBEANCALLROUTERCLASSNAME$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSingletonBeanCallRouterClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SINGLETONBEANCALLROUTERCLASSNAME$6) != 0;
      }
   }

   public void setSingletonBeanCallRouterClassName(CallRouterClassNameType singletonBeanCallRouterClassName) {
      this.generatedSetterHelperImpl(singletonBeanCallRouterClassName, SINGLETONBEANCALLROUTERCLASSNAME$6, 0, (short)1);
   }

   public CallRouterClassNameType addNewSingletonBeanCallRouterClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CallRouterClassNameType target = null;
         target = (CallRouterClassNameType)this.get_store().add_element_user(SINGLETONBEANCALLROUTERCLASSNAME$6);
         return target;
      }
   }

   public void unsetSingletonBeanCallRouterClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SINGLETONBEANCALLROUTERCLASSNAME$6, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$8) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$8);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$8);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$8);
      }
   }
}
