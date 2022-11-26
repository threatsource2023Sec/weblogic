package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.DistributedDestinationType;
import com.oracle.xmlns.weblogic.weblogicJms.UnitOfOrderRoutingType;
import javax.xml.namespace.QName;

public class DistributedDestinationTypeImpl extends NamedEntityTypeImpl implements DistributedDestinationType {
   private static final long serialVersionUID = 1L;
   private static final QName JNDINAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "jndi-name");
   private static final QName LOCALJNDINAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "local-jndi-name");
   private static final QName LOADBALANCINGPOLICY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "load-balancing-policy");
   private static final QName UNITOFORDERROUTING$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "unit-of-order-routing");
   private static final QName SAFEXPORTPOLICY$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-export-policy");

   public DistributedDestinationTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDINAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
         return target;
      }
   }

   public boolean isNilJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JNDINAME$0) != 0;
      }
   }

   public void setJndiName(String jndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDINAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JNDINAME$0);
         }

         target.setStringValue(jndiName);
      }
   }

   public void xsetJndiName(XmlString jndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JNDINAME$0);
         }

         target.set(jndiName);
      }
   }

   public void setNilJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JNDINAME$0);
         }

         target.setNil();
      }
   }

   public void unsetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JNDINAME$0, 0);
      }
   }

   public String getLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         return target;
      }
   }

   public boolean isNilLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCALJNDINAME$2) != 0;
      }
   }

   public void setLocalJndiName(String localJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOCALJNDINAME$2);
         }

         target.setStringValue(localJndiName);
      }
   }

   public void xsetLocalJndiName(XmlString localJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOCALJNDINAME$2);
         }

         target.set(localJndiName);
      }
   }

   public void setNilLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOCALJNDINAME$2);
         }

         target.setNil();
      }
   }

   public void unsetLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALJNDINAME$2, 0);
      }
   }

   public String getLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOADBALANCINGPOLICY$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOADBALANCINGPOLICY$4, 0);
         return target;
      }
   }

   public boolean isNilLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOADBALANCINGPOLICY$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOADBALANCINGPOLICY$4) != 0;
      }
   }

   public void setLoadBalancingPolicy(String loadBalancingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOADBALANCINGPOLICY$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOADBALANCINGPOLICY$4);
         }

         target.setStringValue(loadBalancingPolicy);
      }
   }

   public void xsetLoadBalancingPolicy(XmlString loadBalancingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOADBALANCINGPOLICY$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOADBALANCINGPOLICY$4);
         }

         target.set(loadBalancingPolicy);
      }
   }

   public void setNilLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOADBALANCINGPOLICY$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOADBALANCINGPOLICY$4);
         }

         target.setNil();
      }
   }

   public void unsetLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOADBALANCINGPOLICY$4, 0);
      }
   }

   public UnitOfOrderRoutingType.Enum getUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNITOFORDERROUTING$6, 0);
         return target == null ? null : (UnitOfOrderRoutingType.Enum)target.getEnumValue();
      }
   }

   public UnitOfOrderRoutingType xgetUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnitOfOrderRoutingType target = null;
         target = (UnitOfOrderRoutingType)this.get_store().find_element_user(UNITOFORDERROUTING$6, 0);
         return target;
      }
   }

   public boolean isSetUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNITOFORDERROUTING$6) != 0;
      }
   }

   public void setUnitOfOrderRouting(UnitOfOrderRoutingType.Enum unitOfOrderRouting) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNITOFORDERROUTING$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(UNITOFORDERROUTING$6);
         }

         target.setEnumValue(unitOfOrderRouting);
      }
   }

   public void xsetUnitOfOrderRouting(UnitOfOrderRoutingType unitOfOrderRouting) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnitOfOrderRoutingType target = null;
         target = (UnitOfOrderRoutingType)this.get_store().find_element_user(UNITOFORDERROUTING$6, 0);
         if (target == null) {
            target = (UnitOfOrderRoutingType)this.get_store().add_element_user(UNITOFORDERROUTING$6);
         }

         target.set(unitOfOrderRouting);
      }
   }

   public void unsetUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UNITOFORDERROUTING$6, 0);
      }
   }

   public DistributedDestinationType.SafExportPolicy.Enum getSafExportPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAFEXPORTPOLICY$8, 0);
         return target == null ? null : (DistributedDestinationType.SafExportPolicy.Enum)target.getEnumValue();
      }
   }

   public DistributedDestinationType.SafExportPolicy xgetSafExportPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedDestinationType.SafExportPolicy target = null;
         target = (DistributedDestinationType.SafExportPolicy)this.get_store().find_element_user(SAFEXPORTPOLICY$8, 0);
         return target;
      }
   }

   public boolean isSetSafExportPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAFEXPORTPOLICY$8) != 0;
      }
   }

   public void setSafExportPolicy(DistributedDestinationType.SafExportPolicy.Enum safExportPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAFEXPORTPOLICY$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SAFEXPORTPOLICY$8);
         }

         target.setEnumValue(safExportPolicy);
      }
   }

   public void xsetSafExportPolicy(DistributedDestinationType.SafExportPolicy safExportPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DistributedDestinationType.SafExportPolicy target = null;
         target = (DistributedDestinationType.SafExportPolicy)this.get_store().find_element_user(SAFEXPORTPOLICY$8, 0);
         if (target == null) {
            target = (DistributedDestinationType.SafExportPolicy)this.get_store().add_element_user(SAFEXPORTPOLICY$8);
         }

         target.set(safExportPolicy);
      }
   }

   public void unsetSafExportPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFEXPORTPOLICY$8, 0);
      }
   }

   public static class SafExportPolicyImpl extends JavaStringEnumerationHolderEx implements DistributedDestinationType.SafExportPolicy {
      private static final long serialVersionUID = 1L;

      public SafExportPolicyImpl(SchemaType sType) {
         super(sType, false);
      }

      protected SafExportPolicyImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
