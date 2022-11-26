package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicCoherence.CoherenceFederationParamsType;
import com.sun.java.xml.ns.javaee.XsdNonNegativeIntegerType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class CoherenceFederationParamsTypeImpl extends XmlComplexContentImpl implements CoherenceFederationParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName FEDERATIONTOPOLOGY$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "federation-topology");
   private static final QName REMOTEPARTICIPANTHOST$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "remote-participant-host");
   private static final QName REMOTECOHERENCECLUSTERNAME$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "remote-coherence-cluster-name");
   private static final QName REMOTECOHERENCECLUSTERLISTENPORT$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "remote-coherence-cluster-listen-port");

   public CoherenceFederationParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getFederationTopology() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FEDERATIONTOPOLOGY$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetFederationTopology() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FEDERATIONTOPOLOGY$0, 0);
         return target;
      }
   }

   public boolean isSetFederationTopology() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FEDERATIONTOPOLOGY$0) != 0;
      }
   }

   public void setFederationTopology(String federationTopology) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FEDERATIONTOPOLOGY$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FEDERATIONTOPOLOGY$0);
         }

         target.setStringValue(federationTopology);
      }
   }

   public void xsetFederationTopology(XmlString federationTopology) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(FEDERATIONTOPOLOGY$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(FEDERATIONTOPOLOGY$0);
         }

         target.set(federationTopology);
      }
   }

   public void unsetFederationTopology() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FEDERATIONTOPOLOGY$0, 0);
      }
   }

   public String[] getRemoteParticipantHostArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(REMOTEPARTICIPANTHOST$2, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getRemoteParticipantHostArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REMOTEPARTICIPANTHOST$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetRemoteParticipantHostArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(REMOTEPARTICIPANTHOST$2, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetRemoteParticipantHostArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REMOTEPARTICIPANTHOST$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilRemoteParticipantHostArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REMOTEPARTICIPANTHOST$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfRemoteParticipantHostArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REMOTEPARTICIPANTHOST$2);
      }
   }

   public void setRemoteParticipantHostArray(String[] remoteParticipantHostArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(remoteParticipantHostArray, REMOTEPARTICIPANTHOST$2);
      }
   }

   public void setRemoteParticipantHostArray(int i, String remoteParticipantHost) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REMOTEPARTICIPANTHOST$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(remoteParticipantHost);
         }
      }
   }

   public void xsetRemoteParticipantHostArray(XmlString[] remoteParticipantHostArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(remoteParticipantHostArray, REMOTEPARTICIPANTHOST$2);
      }
   }

   public void xsetRemoteParticipantHostArray(int i, XmlString remoteParticipantHost) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REMOTEPARTICIPANTHOST$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(remoteParticipantHost);
         }
      }
   }

   public void setNilRemoteParticipantHostArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REMOTEPARTICIPANTHOST$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public void insertRemoteParticipantHost(int i, String remoteParticipantHost) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(REMOTEPARTICIPANTHOST$2, i);
         target.setStringValue(remoteParticipantHost);
      }
   }

   public void addRemoteParticipantHost(String remoteParticipantHost) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(REMOTEPARTICIPANTHOST$2);
         target.setStringValue(remoteParticipantHost);
      }
   }

   public XmlString insertNewRemoteParticipantHost(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(REMOTEPARTICIPANTHOST$2, i);
         return target;
      }
   }

   public XmlString addNewRemoteParticipantHost() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(REMOTEPARTICIPANTHOST$2);
         return target;
      }
   }

   public void removeRemoteParticipantHost(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REMOTEPARTICIPANTHOST$2, i);
      }
   }

   public String getRemoteCoherenceClusterName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REMOTECOHERENCECLUSTERNAME$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetRemoteCoherenceClusterName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REMOTECOHERENCECLUSTERNAME$4, 0);
         return target;
      }
   }

   public boolean isNilRemoteCoherenceClusterName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REMOTECOHERENCECLUSTERNAME$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public void setRemoteCoherenceClusterName(String remoteCoherenceClusterName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REMOTECOHERENCECLUSTERNAME$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REMOTECOHERENCECLUSTERNAME$4);
         }

         target.setStringValue(remoteCoherenceClusterName);
      }
   }

   public void xsetRemoteCoherenceClusterName(XmlString remoteCoherenceClusterName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REMOTECOHERENCECLUSTERNAME$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(REMOTECOHERENCECLUSTERNAME$4);
         }

         target.set(remoteCoherenceClusterName);
      }
   }

   public void setNilRemoteCoherenceClusterName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(REMOTECOHERENCECLUSTERNAME$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(REMOTECOHERENCECLUSTERNAME$4);
         }

         target.setNil();
      }
   }

   public XsdNonNegativeIntegerType getRemoteCoherenceClusterListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(REMOTECOHERENCECLUSTERLISTENPORT$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilRemoteCoherenceClusterListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(REMOTECOHERENCECLUSTERLISTENPORT$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public void setRemoteCoherenceClusterListenPort(XsdNonNegativeIntegerType remoteCoherenceClusterListenPort) {
      this.generatedSetterHelperImpl(remoteCoherenceClusterListenPort, REMOTECOHERENCECLUSTERLISTENPORT$6, 0, (short)1);
   }

   public XsdNonNegativeIntegerType addNewRemoteCoherenceClusterListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(REMOTECOHERENCECLUSTERLISTENPORT$6);
         return target;
      }
   }

   public void setNilRemoteCoherenceClusterListenPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XsdNonNegativeIntegerType target = null;
         target = (XsdNonNegativeIntegerType)this.get_store().find_element_user(REMOTECOHERENCECLUSTERLISTENPORT$6, 0);
         if (target == null) {
            target = (XsdNonNegativeIntegerType)this.get_store().add_element_user(REMOTECOHERENCECLUSTERLISTENPORT$6);
         }

         target.setNil();
      }
   }
}
