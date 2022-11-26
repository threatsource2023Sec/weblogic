package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ClusterRemoteCommitProviderType;
import javax.xml.namespace.QName;

public class ClusterRemoteCommitProviderTypeImpl extends RemoteCommitProviderTypeImpl implements ClusterRemoteCommitProviderType {
   private static final long serialVersionUID = 1L;
   private static final QName BUFFERSIZE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "buffer-size");
   private static final QName RECOVERACTION$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "recover-action");
   private static final QName CACHETOPICS$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "cache-topics");

   public ClusterRemoteCommitProviderTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getBufferSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BUFFERSIZE$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetBufferSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(BUFFERSIZE$0, 0);
         return target;
      }
   }

   public boolean isSetBufferSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BUFFERSIZE$0) != 0;
      }
   }

   public void setBufferSize(int bufferSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BUFFERSIZE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BUFFERSIZE$0);
         }

         target.setIntValue(bufferSize);
      }
   }

   public void xsetBufferSize(XmlInt bufferSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(BUFFERSIZE$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(BUFFERSIZE$0);
         }

         target.set(bufferSize);
      }
   }

   public void unsetBufferSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BUFFERSIZE$0, 0);
      }
   }

   public ClusterRemoteCommitProviderType.RecoverAction.Enum getRecoverAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RECOVERACTION$2, 0);
         return target == null ? null : (ClusterRemoteCommitProviderType.RecoverAction.Enum)target.getEnumValue();
      }
   }

   public ClusterRemoteCommitProviderType.RecoverAction xgetRecoverAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClusterRemoteCommitProviderType.RecoverAction target = null;
         target = (ClusterRemoteCommitProviderType.RecoverAction)this.get_store().find_element_user(RECOVERACTION$2, 0);
         return target;
      }
   }

   public boolean isSetRecoverAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RECOVERACTION$2) != 0;
      }
   }

   public void setRecoverAction(ClusterRemoteCommitProviderType.RecoverAction.Enum recoverAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RECOVERACTION$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RECOVERACTION$2);
         }

         target.setEnumValue(recoverAction);
      }
   }

   public void xsetRecoverAction(ClusterRemoteCommitProviderType.RecoverAction recoverAction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClusterRemoteCommitProviderType.RecoverAction target = null;
         target = (ClusterRemoteCommitProviderType.RecoverAction)this.get_store().find_element_user(RECOVERACTION$2, 0);
         if (target == null) {
            target = (ClusterRemoteCommitProviderType.RecoverAction)this.get_store().add_element_user(RECOVERACTION$2);
         }

         target.set(recoverAction);
      }
   }

   public void unsetRecoverAction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RECOVERACTION$2, 0);
      }
   }

   public String getCacheTopics() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHETOPICS$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCacheTopics() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CACHETOPICS$4, 0);
         return target;
      }
   }

   public boolean isNilCacheTopics() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CACHETOPICS$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCacheTopics() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CACHETOPICS$4) != 0;
      }
   }

   public void setCacheTopics(String cacheTopics) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHETOPICS$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CACHETOPICS$4);
         }

         target.setStringValue(cacheTopics);
      }
   }

   public void xsetCacheTopics(XmlString cacheTopics) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CACHETOPICS$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CACHETOPICS$4);
         }

         target.set(cacheTopics);
      }
   }

   public void setNilCacheTopics() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CACHETOPICS$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CACHETOPICS$4);
         }

         target.setNil();
      }
   }

   public void unsetCacheTopics() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHETOPICS$4, 0);
      }
   }

   public static class RecoverActionImpl extends JavaStringEnumerationHolderEx implements ClusterRemoteCommitProviderType.RecoverAction {
      private static final long serialVersionUID = 1L;

      public RecoverActionImpl(SchemaType sType) {
         super(sType, false);
      }

      protected RecoverActionImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
