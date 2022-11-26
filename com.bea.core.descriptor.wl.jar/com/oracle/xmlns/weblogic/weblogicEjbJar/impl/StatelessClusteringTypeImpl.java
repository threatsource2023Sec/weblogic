package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.CallRouterClassNameType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.LoadAlgorithmType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.StatelessClusteringType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TrueFalseType;
import javax.xml.namespace.QName;

public class StatelessClusteringTypeImpl extends XmlComplexContentImpl implements StatelessClusteringType {
   private static final long serialVersionUID = 1L;
   private static final QName HOMEISCLUSTERABLE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "home-is-clusterable");
   private static final QName HOMELOADALGORITHM$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "home-load-algorithm");
   private static final QName HOMECALLROUTERCLASSNAME$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "home-call-router-class-name");
   private static final QName USESERVERSIDESTUBS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "use-serverside-stubs");
   private static final QName STATELESSBEANISCLUSTERABLE$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "stateless-bean-is-clusterable");
   private static final QName STATELESSBEANLOADALGORITHM$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "stateless-bean-load-algorithm");
   private static final QName STATELESSBEANCALLROUTERCLASSNAME$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "stateless-bean-call-router-class-name");
   private static final QName ID$14 = new QName("", "id");

   public StatelessClusteringTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TrueFalseType getHomeIsClusterable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(HOMEISCLUSTERABLE$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetHomeIsClusterable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HOMEISCLUSTERABLE$0) != 0;
      }
   }

   public void setHomeIsClusterable(TrueFalseType homeIsClusterable) {
      this.generatedSetterHelperImpl(homeIsClusterable, HOMEISCLUSTERABLE$0, 0, (short)1);
   }

   public TrueFalseType addNewHomeIsClusterable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(HOMEISCLUSTERABLE$0);
         return target;
      }
   }

   public void unsetHomeIsClusterable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HOMEISCLUSTERABLE$0, 0);
      }
   }

   public LoadAlgorithmType getHomeLoadAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoadAlgorithmType target = null;
         target = (LoadAlgorithmType)this.get_store().find_element_user(HOMELOADALGORITHM$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetHomeLoadAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HOMELOADALGORITHM$2) != 0;
      }
   }

   public void setHomeLoadAlgorithm(LoadAlgorithmType homeLoadAlgorithm) {
      this.generatedSetterHelperImpl(homeLoadAlgorithm, HOMELOADALGORITHM$2, 0, (short)1);
   }

   public LoadAlgorithmType addNewHomeLoadAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoadAlgorithmType target = null;
         target = (LoadAlgorithmType)this.get_store().add_element_user(HOMELOADALGORITHM$2);
         return target;
      }
   }

   public void unsetHomeLoadAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HOMELOADALGORITHM$2, 0);
      }
   }

   public CallRouterClassNameType getHomeCallRouterClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CallRouterClassNameType target = null;
         target = (CallRouterClassNameType)this.get_store().find_element_user(HOMECALLROUTERCLASSNAME$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetHomeCallRouterClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(HOMECALLROUTERCLASSNAME$4) != 0;
      }
   }

   public void setHomeCallRouterClassName(CallRouterClassNameType homeCallRouterClassName) {
      this.generatedSetterHelperImpl(homeCallRouterClassName, HOMECALLROUTERCLASSNAME$4, 0, (short)1);
   }

   public CallRouterClassNameType addNewHomeCallRouterClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CallRouterClassNameType target = null;
         target = (CallRouterClassNameType)this.get_store().add_element_user(HOMECALLROUTERCLASSNAME$4);
         return target;
      }
   }

   public void unsetHomeCallRouterClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(HOMECALLROUTERCLASSNAME$4, 0);
      }
   }

   public TrueFalseType getUseServersideStubs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(USESERVERSIDESTUBS$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUseServersideStubs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USESERVERSIDESTUBS$6) != 0;
      }
   }

   public void setUseServersideStubs(TrueFalseType useServersideStubs) {
      this.generatedSetterHelperImpl(useServersideStubs, USESERVERSIDESTUBS$6, 0, (short)1);
   }

   public TrueFalseType addNewUseServersideStubs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(USESERVERSIDESTUBS$6);
         return target;
      }
   }

   public void unsetUseServersideStubs() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USESERVERSIDESTUBS$6, 0);
      }
   }

   public TrueFalseType getStatelessBeanIsClusterable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(STATELESSBEANISCLUSTERABLE$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStatelessBeanIsClusterable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STATELESSBEANISCLUSTERABLE$8) != 0;
      }
   }

   public void setStatelessBeanIsClusterable(TrueFalseType statelessBeanIsClusterable) {
      this.generatedSetterHelperImpl(statelessBeanIsClusterable, STATELESSBEANISCLUSTERABLE$8, 0, (short)1);
   }

   public TrueFalseType addNewStatelessBeanIsClusterable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(STATELESSBEANISCLUSTERABLE$8);
         return target;
      }
   }

   public void unsetStatelessBeanIsClusterable() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STATELESSBEANISCLUSTERABLE$8, 0);
      }
   }

   public LoadAlgorithmType getStatelessBeanLoadAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoadAlgorithmType target = null;
         target = (LoadAlgorithmType)this.get_store().find_element_user(STATELESSBEANLOADALGORITHM$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStatelessBeanLoadAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STATELESSBEANLOADALGORITHM$10) != 0;
      }
   }

   public void setStatelessBeanLoadAlgorithm(LoadAlgorithmType statelessBeanLoadAlgorithm) {
      this.generatedSetterHelperImpl(statelessBeanLoadAlgorithm, STATELESSBEANLOADALGORITHM$10, 0, (short)1);
   }

   public LoadAlgorithmType addNewStatelessBeanLoadAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoadAlgorithmType target = null;
         target = (LoadAlgorithmType)this.get_store().add_element_user(STATELESSBEANLOADALGORITHM$10);
         return target;
      }
   }

   public void unsetStatelessBeanLoadAlgorithm() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STATELESSBEANLOADALGORITHM$10, 0);
      }
   }

   public CallRouterClassNameType getStatelessBeanCallRouterClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CallRouterClassNameType target = null;
         target = (CallRouterClassNameType)this.get_store().find_element_user(STATELESSBEANCALLROUTERCLASSNAME$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStatelessBeanCallRouterClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STATELESSBEANCALLROUTERCLASSNAME$12) != 0;
      }
   }

   public void setStatelessBeanCallRouterClassName(CallRouterClassNameType statelessBeanCallRouterClassName) {
      this.generatedSetterHelperImpl(statelessBeanCallRouterClassName, STATELESSBEANCALLROUTERCLASSNAME$12, 0, (short)1);
   }

   public CallRouterClassNameType addNewStatelessBeanCallRouterClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CallRouterClassNameType target = null;
         target = (CallRouterClassNameType)this.get_store().add_element_user(STATELESSBEANCALLROUTERCLASSNAME$12);
         return target;
      }
   }

   public void unsetStatelessBeanCallRouterClassName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STATELESSBEANCALLROUTERCLASSNAME$12, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$14) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$14);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$14);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$14);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$14);
      }
   }
}
