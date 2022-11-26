package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicCoherence.ClusteringModeType;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceCacheType;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceClusterParamsType;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceClusterWellKnownAddressesType;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceServiceType;
import com.oracle.xmlns.weblogic.weblogicCoherence.IdentityAsserterType;
import com.oracle.xmlns.weblogic.weblogicCoherence.KeystoreParamsType;
import com.oracle.xmlns.weblogic.weblogicCoherence.TransportType;
import com.sun.java.xml.ns.javaee.TrueFalseType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class CoherenceClusterParamsTypeImpl extends XmlComplexContentImpl implements CoherenceClusterParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName CLUSTERLISTENPORT$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "cluster-listen-port");
   private static final QName UNICASTLISTENADDRESS$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "unicast-listen-address");
   private static final QName UNICASTLISTENPORT$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "unicast-listen-port");
   private static final QName UNICASTPORTAUTOADJUST$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "unicast-port-auto-adjust");
   private static final QName MULTICASTLISTENADDRESS$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "multicast-listen-address");
   private static final QName MULTICASTLISTENPORT$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "multicast-listen-port");
   private static final QName TIMETOLIVE$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "time-to-live");
   private static final QName COHERENCECLUSTERWELLKNOWNADDRESSES$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-cluster-well-known-addresses");
   private static final QName CLUSTERINGMODE$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "clustering-mode");
   private static final QName TRANSPORT$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "transport");
   private static final QName SECURITYFRAMEWORKENABLED$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "security-framework-enabled");
   private static final QName COHERENCEIDENTITYASSERTER$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-identity-asserter");
   private static final QName COHERENCEKEYSTOREPARAMS$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-keystore-params");
   private static final QName COHERENCECACHE$26 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-cache");
   private static final QName COHERENCESERVICE$28 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-service");

   public CoherenceClusterParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public XsdNonNegativeIntegerType getClusterListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(CLUSTERLISTENPORT$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetClusterListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLUSTERLISTENPORT$0) != 0;
      }
   }

   public void setClusterListenPort(XsdNonNegativeIntegerType clusterListenPort) {
      this.generatedSetterHelperImpl(clusterListenPort, CLUSTERLISTENPORT$0, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewClusterListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(CLUSTERLISTENPORT$0);
         return target;
      }
   }

   public void unsetClusterListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLUSTERLISTENPORT$0, 0);
      }
   }

   public String getUnicastListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNICASTLISTENADDRESS$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetUnicastListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(UNICASTLISTENADDRESS$2, 0);
         return target;
      }
   }

   public boolean isNilUnicastListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(UNICASTLISTENADDRESS$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetUnicastListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNICASTLISTENADDRESS$2) != 0;
      }
   }

   public void setUnicastListenAddress(String unicastListenAddress) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNICASTLISTENADDRESS$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(UNICASTLISTENADDRESS$2);
         }

         target.setStringValue(unicastListenAddress);
      }
   }

   public void xsetUnicastListenAddress(XmlString unicastListenAddress) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(UNICASTLISTENADDRESS$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(UNICASTLISTENADDRESS$2);
         }

         target.set(unicastListenAddress);
      }
   }

   public void setNilUnicastListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(UNICASTLISTENADDRESS$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(UNICASTLISTENADDRESS$2);
         }

         target.setNil();
      }
   }

   public void unsetUnicastListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UNICASTLISTENADDRESS$2, 0);
      }
   }

   public XsdNonNegativeIntegerType getUnicastListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(UNICASTLISTENPORT$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUnicastListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNICASTLISTENPORT$4) != 0;
      }
   }

   public void setUnicastListenPort(XsdNonNegativeIntegerType unicastListenPort) {
      this.generatedSetterHelperImpl(unicastListenPort, UNICASTLISTENPORT$4, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewUnicastListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(UNICASTLISTENPORT$4);
         return target;
      }
   }

   public void unsetUnicastListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UNICASTLISTENPORT$4, 0);
      }
   }

   public TrueFalseType getUnicastPortAutoAdjust() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(UNICASTPORTAUTOADJUST$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetUnicastPortAutoAdjust() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNICASTPORTAUTOADJUST$6) != 0;
      }
   }

   public void setUnicastPortAutoAdjust(TrueFalseType unicastPortAutoAdjust) {
      this.generatedSetterHelperImpl(unicastPortAutoAdjust, UNICASTPORTAUTOADJUST$6, 0, (short)1);
   }

   public TrueFalseType addNewUnicastPortAutoAdjust() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(UNICASTPORTAUTOADJUST$6);
         return target;
      }
   }

   public void unsetUnicastPortAutoAdjust() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UNICASTPORTAUTOADJUST$6, 0);
      }
   }

   public String getMulticastListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTICASTLISTENADDRESS$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMulticastListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MULTICASTLISTENADDRESS$8, 0);
         return target;
      }
   }

   public boolean isNilMulticastListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MULTICASTLISTENADDRESS$8, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetMulticastListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MULTICASTLISTENADDRESS$8) != 0;
      }
   }

   public void setMulticastListenAddress(String multicastListenAddress) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTICASTLISTENADDRESS$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MULTICASTLISTENADDRESS$8);
         }

         target.setStringValue(multicastListenAddress);
      }
   }

   public void xsetMulticastListenAddress(XmlString multicastListenAddress) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MULTICASTLISTENADDRESS$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MULTICASTLISTENADDRESS$8);
         }

         target.set(multicastListenAddress);
      }
   }

   public void setNilMulticastListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MULTICASTLISTENADDRESS$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MULTICASTLISTENADDRESS$8);
         }

         target.setNil();
      }
   }

   public void unsetMulticastListenAddress() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MULTICASTLISTENADDRESS$8, 0);
      }
   }

   public XsdNonNegativeIntegerType getMulticastListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(MULTICASTLISTENPORT$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMulticastListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MULTICASTLISTENPORT$10) != 0;
      }
   }

   public void setMulticastListenPort(XsdNonNegativeIntegerType multicastListenPort) {
      this.generatedSetterHelperImpl(multicastListenPort, MULTICASTLISTENPORT$10, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewMulticastListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(MULTICASTLISTENPORT$10);
         return target;
      }
   }

   public void unsetMulticastListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MULTICASTLISTENPORT$10, 0);
      }
   }

   public XsdNonNegativeIntegerType getTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(TIMETOLIVE$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMETOLIVE$12) != 0;
      }
   }

   public void setTimeToLive(XsdNonNegativeIntegerType timeToLive) {
      this.generatedSetterHelperImpl(timeToLive, TIMETOLIVE$12, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(TIMETOLIVE$12);
         return target;
      }
   }

   public void unsetTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMETOLIVE$12, 0);
      }
   }

   public CoherenceClusterWellKnownAddressesType getCoherenceClusterWellKnownAddresses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterWellKnownAddressesType target = null;
         target = (CoherenceClusterWellKnownAddressesType)this.get_store().find_element_user(COHERENCECLUSTERWELLKNOWNADDRESSES$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCoherenceClusterWellKnownAddresses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterWellKnownAddressesType target = null;
         target = (CoherenceClusterWellKnownAddressesType)this.get_store().find_element_user(COHERENCECLUSTERWELLKNOWNADDRESSES$14, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCoherenceClusterWellKnownAddresses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCECLUSTERWELLKNOWNADDRESSES$14) != 0;
      }
   }

   public void setCoherenceClusterWellKnownAddresses(CoherenceClusterWellKnownAddressesType coherenceClusterWellKnownAddresses) {
      this.generatedSetterHelperImpl(coherenceClusterWellKnownAddresses, COHERENCECLUSTERWELLKNOWNADDRESSES$14, 0, (short)1);
   }

   public CoherenceClusterWellKnownAddressesType addNewCoherenceClusterWellKnownAddresses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterWellKnownAddressesType target = null;
         target = (CoherenceClusterWellKnownAddressesType)this.get_store().add_element_user(COHERENCECLUSTERWELLKNOWNADDRESSES$14);
         return target;
      }
   }

   public void setNilCoherenceClusterWellKnownAddresses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceClusterWellKnownAddressesType target = null;
         target = (CoherenceClusterWellKnownAddressesType)this.get_store().find_element_user(COHERENCECLUSTERWELLKNOWNADDRESSES$14, 0);
         if (target == null) {
            target = (CoherenceClusterWellKnownAddressesType)this.get_store().add_element_user(COHERENCECLUSTERWELLKNOWNADDRESSES$14);
         }

         target.setNil();
      }
   }

   public void unsetCoherenceClusterWellKnownAddresses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCECLUSTERWELLKNOWNADDRESSES$14, 0);
      }
   }

   public ClusteringModeType.Enum getClusteringMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLUSTERINGMODE$16, 0);
         return target == null ? null : (ClusteringModeType.Enum)target.getEnumValue();
      }
   }

   public ClusteringModeType xgetClusteringMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClusteringModeType target = null;
         target = (ClusteringModeType)this.get_store().find_element_user(CLUSTERINGMODE$16, 0);
         return target;
      }
   }

   public boolean isNilClusteringMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClusteringModeType target = null;
         target = (ClusteringModeType)this.get_store().find_element_user(CLUSTERINGMODE$16, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetClusteringMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLUSTERINGMODE$16) != 0;
      }
   }

   public void setClusteringMode(ClusteringModeType.Enum clusteringMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLUSTERINGMODE$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CLUSTERINGMODE$16);
         }

         target.setEnumValue(clusteringMode);
      }
   }

   public void xsetClusteringMode(ClusteringModeType clusteringMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClusteringModeType target = null;
         target = (ClusteringModeType)this.get_store().find_element_user(CLUSTERINGMODE$16, 0);
         if (target == null) {
            target = (ClusteringModeType)this.get_store().add_element_user(CLUSTERINGMODE$16);
         }

         target.set(clusteringMode);
      }
   }

   public void setNilClusteringMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClusteringModeType target = null;
         target = (ClusteringModeType)this.get_store().find_element_user(CLUSTERINGMODE$16, 0);
         if (target == null) {
            target = (ClusteringModeType)this.get_store().add_element_user(CLUSTERINGMODE$16);
         }

         target.setNil();
      }
   }

   public void unsetClusteringMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLUSTERINGMODE$16, 0);
      }
   }

   public TransportType.Enum getTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSPORT$18, 0);
         return target == null ? null : (TransportType.Enum)target.getEnumValue();
      }
   }

   public TransportType xgetTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransportType target = null;
         target = (TransportType)this.get_store().find_element_user(TRANSPORT$18, 0);
         return target;
      }
   }

   public boolean isNilTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransportType target = null;
         target = (TransportType)this.get_store().find_element_user(TRANSPORT$18, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSPORT$18) != 0;
      }
   }

   public void setTransport(TransportType.Enum transport) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TRANSPORT$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TRANSPORT$18);
         }

         target.setEnumValue(transport);
      }
   }

   public void xsetTransport(TransportType transport) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransportType target = null;
         target = (TransportType)this.get_store().find_element_user(TRANSPORT$18, 0);
         if (target == null) {
            target = (TransportType)this.get_store().add_element_user(TRANSPORT$18);
         }

         target.set(transport);
      }
   }

   public void setNilTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransportType target = null;
         target = (TransportType)this.get_store().find_element_user(TRANSPORT$18, 0);
         if (target == null) {
            target = (TransportType)this.get_store().add_element_user(TRANSPORT$18);
         }

         target.setNil();
      }
   }

   public void unsetTransport() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSPORT$18, 0);
      }
   }

   public TrueFalseType getSecurityFrameworkEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(SECURITYFRAMEWORKENABLED$20, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSecurityFrameworkEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYFRAMEWORKENABLED$20) != 0;
      }
   }

   public void setSecurityFrameworkEnabled(TrueFalseType securityFrameworkEnabled) {
      this.generatedSetterHelperImpl(securityFrameworkEnabled, SECURITYFRAMEWORKENABLED$20, 0, (short)1);
   }

   public TrueFalseType addNewSecurityFrameworkEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(SECURITYFRAMEWORKENABLED$20);
         return target;
      }
   }

   public void unsetSecurityFrameworkEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYFRAMEWORKENABLED$20, 0);
      }
   }

   public IdentityAsserterType getCoherenceIdentityAsserter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IdentityAsserterType target = null;
         target = (IdentityAsserterType)this.get_store().find_element_user(COHERENCEIDENTITYASSERTER$22, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCoherenceIdentityAsserter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IdentityAsserterType target = null;
         target = (IdentityAsserterType)this.get_store().find_element_user(COHERENCEIDENTITYASSERTER$22, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCoherenceIdentityAsserter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCEIDENTITYASSERTER$22) != 0;
      }
   }

   public void setCoherenceIdentityAsserter(IdentityAsserterType coherenceIdentityAsserter) {
      this.generatedSetterHelperImpl(coherenceIdentityAsserter, COHERENCEIDENTITYASSERTER$22, 0, (short)1);
   }

   public IdentityAsserterType addNewCoherenceIdentityAsserter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IdentityAsserterType target = null;
         target = (IdentityAsserterType)this.get_store().add_element_user(COHERENCEIDENTITYASSERTER$22);
         return target;
      }
   }

   public void setNilCoherenceIdentityAsserter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         IdentityAsserterType target = null;
         target = (IdentityAsserterType)this.get_store().find_element_user(COHERENCEIDENTITYASSERTER$22, 0);
         if (target == null) {
            target = (IdentityAsserterType)this.get_store().add_element_user(COHERENCEIDENTITYASSERTER$22);
         }

         target.setNil();
      }
   }

   public void unsetCoherenceIdentityAsserter() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCEIDENTITYASSERTER$22, 0);
      }
   }

   public KeystoreParamsType getCoherenceKeystoreParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KeystoreParamsType target = null;
         target = (KeystoreParamsType)this.get_store().find_element_user(COHERENCEKEYSTOREPARAMS$24, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilCoherenceKeystoreParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KeystoreParamsType target = null;
         target = (KeystoreParamsType)this.get_store().find_element_user(COHERENCEKEYSTOREPARAMS$24, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCoherenceKeystoreParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCEKEYSTOREPARAMS$24) != 0;
      }
   }

   public void setCoherenceKeystoreParams(KeystoreParamsType coherenceKeystoreParams) {
      this.generatedSetterHelperImpl(coherenceKeystoreParams, COHERENCEKEYSTOREPARAMS$24, 0, (short)1);
   }

   public KeystoreParamsType addNewCoherenceKeystoreParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KeystoreParamsType target = null;
         target = (KeystoreParamsType)this.get_store().add_element_user(COHERENCEKEYSTOREPARAMS$24);
         return target;
      }
   }

   public void setNilCoherenceKeystoreParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         KeystoreParamsType target = null;
         target = (KeystoreParamsType)this.get_store().find_element_user(COHERENCEKEYSTOREPARAMS$24, 0);
         if (target == null) {
            target = (KeystoreParamsType)this.get_store().add_element_user(COHERENCEKEYSTOREPARAMS$24);
         }

         target.setNil();
      }
   }

   public void unsetCoherenceKeystoreParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCEKEYSTOREPARAMS$24, 0);
      }
   }

   public CoherenceCacheType[] getCoherenceCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(COHERENCECACHE$26, targetList);
         CoherenceCacheType[] result = new CoherenceCacheType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CoherenceCacheType getCoherenceCacheArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceCacheType target = null;
         target = (CoherenceCacheType)this.get_store().find_element_user(COHERENCECACHE$26, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCoherenceCacheArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCECACHE$26);
      }
   }

   public void setCoherenceCacheArray(CoherenceCacheType[] coherenceCacheArray) {
      this.check_orphaned();
      this.arraySetterHelper(coherenceCacheArray, COHERENCECACHE$26);
   }

   public void setCoherenceCacheArray(int i, CoherenceCacheType coherenceCache) {
      this.generatedSetterHelperImpl(coherenceCache, COHERENCECACHE$26, i, (short)2);
   }

   public CoherenceCacheType insertNewCoherenceCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceCacheType target = null;
         target = (CoherenceCacheType)this.get_store().insert_element_user(COHERENCECACHE$26, i);
         return target;
      }
   }

   public CoherenceCacheType addNewCoherenceCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceCacheType target = null;
         target = (CoherenceCacheType)this.get_store().add_element_user(COHERENCECACHE$26);
         return target;
      }
   }

   public void removeCoherenceCache(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCECACHE$26, i);
      }
   }

   public CoherenceServiceType[] getCoherenceServiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(COHERENCESERVICE$28, targetList);
         CoherenceServiceType[] result = new CoherenceServiceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public CoherenceServiceType getCoherenceServiceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceServiceType target = null;
         target = (CoherenceServiceType)this.get_store().find_element_user(COHERENCESERVICE$28, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfCoherenceServiceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCESERVICE$28);
      }
   }

   public void setCoherenceServiceArray(CoherenceServiceType[] coherenceServiceArray) {
      this.check_orphaned();
      this.arraySetterHelper(coherenceServiceArray, COHERENCESERVICE$28);
   }

   public void setCoherenceServiceArray(int i, CoherenceServiceType coherenceService) {
      this.generatedSetterHelperImpl(coherenceService, COHERENCESERVICE$28, i, (short)2);
   }

   public CoherenceServiceType insertNewCoherenceService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceServiceType target = null;
         target = (CoherenceServiceType)this.get_store().insert_element_user(COHERENCESERVICE$28, i);
         return target;
      }
   }

   public CoherenceServiceType addNewCoherenceService() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         CoherenceServiceType target = null;
         target = (CoherenceServiceType)this.get_store().add_element_user(COHERENCESERVICE$28);
         return target;
      }
   }

   public void removeCoherenceService(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCESERVICE$28, i);
      }
   }
}
