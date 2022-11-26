package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TcpRemoteCommitProviderType;
import javax.xml.namespace.QName;

public class TcpRemoteCommitProviderTypeImpl extends RemoteCommitProviderTypeImpl implements TcpRemoteCommitProviderType {
   private static final long serialVersionUID = 1L;
   private static final QName MAXIDLE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "max-idle");
   private static final QName NUMBROADCASTTHREADS$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "num-broadcast-threads");
   private static final QName RECOVERYTIMEMILLIS$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "recovery-time-millis");
   private static final QName MAXACTIVE$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "max-active");
   private static final QName PORT$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "port");
   private static final QName ADDRESSES$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "addresses");

   public TcpRemoteCommitProviderTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getMaxIdle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXIDLE$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxIdle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXIDLE$0, 0);
         return target;
      }
   }

   public boolean isSetMaxIdle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXIDLE$0) != 0;
      }
   }

   public void setMaxIdle(int maxIdle) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXIDLE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXIDLE$0);
         }

         target.setIntValue(maxIdle);
      }
   }

   public void xsetMaxIdle(XmlInt maxIdle) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXIDLE$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXIDLE$0);
         }

         target.set(maxIdle);
      }
   }

   public void unsetMaxIdle() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXIDLE$0, 0);
      }
   }

   public int getNumBroadcastThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NUMBROADCASTTHREADS$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetNumBroadcastThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(NUMBROADCASTTHREADS$2, 0);
         return target;
      }
   }

   public boolean isSetNumBroadcastThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NUMBROADCASTTHREADS$2) != 0;
      }
   }

   public void setNumBroadcastThreads(int numBroadcastThreads) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NUMBROADCASTTHREADS$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NUMBROADCASTTHREADS$2);
         }

         target.setIntValue(numBroadcastThreads);
      }
   }

   public void xsetNumBroadcastThreads(XmlInt numBroadcastThreads) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(NUMBROADCASTTHREADS$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(NUMBROADCASTTHREADS$2);
         }

         target.set(numBroadcastThreads);
      }
   }

   public void unsetNumBroadcastThreads() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NUMBROADCASTTHREADS$2, 0);
      }
   }

   public int getRecoveryTimeMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RECOVERYTIMEMILLIS$4, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetRecoveryTimeMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(RECOVERYTIMEMILLIS$4, 0);
         return target;
      }
   }

   public boolean isSetRecoveryTimeMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RECOVERYTIMEMILLIS$4) != 0;
      }
   }

   public void setRecoveryTimeMillis(int recoveryTimeMillis) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RECOVERYTIMEMILLIS$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RECOVERYTIMEMILLIS$4);
         }

         target.setIntValue(recoveryTimeMillis);
      }
   }

   public void xsetRecoveryTimeMillis(XmlInt recoveryTimeMillis) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(RECOVERYTIMEMILLIS$4, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(RECOVERYTIMEMILLIS$4);
         }

         target.set(recoveryTimeMillis);
      }
   }

   public void unsetRecoveryTimeMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RECOVERYTIMEMILLIS$4, 0);
      }
   }

   public int getMaxActive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXACTIVE$6, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxActive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXACTIVE$6, 0);
         return target;
      }
   }

   public boolean isSetMaxActive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXACTIVE$6) != 0;
      }
   }

   public void setMaxActive(int maxActive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXACTIVE$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXACTIVE$6);
         }

         target.setIntValue(maxActive);
      }
   }

   public void xsetMaxActive(XmlInt maxActive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXACTIVE$6, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXACTIVE$6);
         }

         target.set(maxActive);
      }
   }

   public void unsetMaxActive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXACTIVE$6, 0);
      }
   }

   public int getPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PORT$8, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(PORT$8, 0);
         return target;
      }
   }

   public boolean isSetPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PORT$8) != 0;
      }
   }

   public void setPort(int port) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PORT$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PORT$8);
         }

         target.setIntValue(port);
      }
   }

   public void xsetPort(XmlInt port) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(PORT$8, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(PORT$8);
         }

         target.set(port);
      }
   }

   public void unsetPort() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PORT$8, 0);
      }
   }

   public String getAddresses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ADDRESSES$10, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAddresses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADDRESSES$10, 0);
         return target;
      }
   }

   public boolean isNilAddresses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADDRESSES$10, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetAddresses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ADDRESSES$10) != 0;
      }
   }

   public void setAddresses(String addresses) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ADDRESSES$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ADDRESSES$10);
         }

         target.setStringValue(addresses);
      }
   }

   public void xsetAddresses(XmlString addresses) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADDRESSES$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ADDRESSES$10);
         }

         target.set(addresses);
      }
   }

   public void setNilAddresses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADDRESSES$10, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ADDRESSES$10);
         }

         target.setNil();
      }
   }

   public void unsetAddresses() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ADDRESSES$10, 0);
      }
   }
}
