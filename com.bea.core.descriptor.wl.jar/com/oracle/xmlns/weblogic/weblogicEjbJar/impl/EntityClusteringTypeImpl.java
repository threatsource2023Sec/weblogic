package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.CallRouterClassNameType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.EntityClusteringType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.LoadAlgorithmType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TrueFalseType;
import javax.xml.namespace.QName;

public class EntityClusteringTypeImpl extends XmlComplexContentImpl implements EntityClusteringType {
   private static final long serialVersionUID = 1L;
   private static final QName HOMEISCLUSTERABLE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "home-is-clusterable");
   private static final QName HOMELOADALGORITHM$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "home-load-algorithm");
   private static final QName HOMECALLROUTERCLASSNAME$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "home-call-router-class-name");
   private static final QName USESERVERSIDESTUBS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "use-serverside-stubs");
   private static final QName ID$8 = new QName("", "id");

   public EntityClusteringTypeImpl(SchemaType sType) {
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
