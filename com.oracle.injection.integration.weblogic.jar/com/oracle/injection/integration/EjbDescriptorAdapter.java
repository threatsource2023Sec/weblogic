package com.oracle.injection.integration;

import com.oracle.injection.ejb.EjbDescriptor;
import com.oracle.injection.ejb.EjbInstanceManager;
import com.oracle.injection.ejb.EjbType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.ejb.spi.BeanInfo;
import weblogic.ejb.spi.DeploymentInfo;
import weblogic.ejb.spi.MessageDrivenBeanInfo;
import weblogic.ejb.spi.SessionBeanInfo;
import weblogic.ejb.spi.SessionBeanReference;
import weblogic.ejb.spi.StatefulSessionBeanInfo;
import weblogic.ejb.spi.StatefulSessionBeanReference;
import weblogic.utils.io.FilteringObjectInputStream;

class EjbDescriptorAdapter implements EjbDescriptor {
   private final BeanInfo m_beanInfo;
   private final String m_moduleName;

   EjbDescriptorAdapter(BeanInfo beanInfo, String moduleName) {
      if (beanInfo == null) {
         throw new IllegalArgumentException("BeanInfo argument cannot be null");
      } else if (moduleName == null) {
         throw new IllegalArgumentException("ModuleName argument cannot be null");
      } else {
         this.m_beanInfo = beanInfo;
         this.m_moduleName = moduleName;
      }
   }

   EjbDescriptorAdapter(SessionBeanInfo sessionBeanInfo, String moduleName) {
      this((BeanInfo)sessionBeanInfo, moduleName);
   }

   EjbDescriptorAdapter(MessageDrivenBeanInfo messageDrivenBeanInfo, String moduleName) {
      this((BeanInfo)messageDrivenBeanInfo, moduleName);
   }

   public Class getEJBClass() {
      return this.getNonGeneratedEjbBeanClass();
   }

   public String getEjbName() {
      return this.m_beanInfo.getEJBName();
   }

   public Collection getLocalBusinessInterfaceClasses() {
      Set classes = new HashSet();
      if (this.m_beanInfo instanceof SessionBeanInfo) {
         SessionBeanInfo sessionBeanInfo = (SessionBeanInfo)this.m_beanInfo;
         classes.addAll(sessionBeanInfo.getBusinessLocals());
         if (sessionBeanInfo.hasNoIntfView()) {
            classes.add(this.m_beanInfo.getBeanClass());
         }

         return classes;
      } else if (this.m_beanInfo instanceof MessageDrivenBeanInfo) {
         classes.add(this.m_beanInfo.getBeanClass());
         MessageDrivenBeanInfo mdbi = (MessageDrivenBeanInfo)this.m_beanInfo;
         Class messagingTypeInterface = mdbi.getMessagingTypeInterfaceClass();
         if (messagingTypeInterface != null) {
            classes.add(messagingTypeInterface);
         }

         return classes;
      } else {
         return Collections.emptySet();
      }
   }

   public EjbType getEjbType() {
      if (this.m_beanInfo instanceof SessionBeanInfo) {
         SessionBeanInfo sessionBeanInfo = (SessionBeanInfo)this.m_beanInfo;
         if (sessionBeanInfo.isStateful()) {
            return EjbType.STATEFUL_SESSION;
         } else if (sessionBeanInfo.isStateless()) {
            return EjbType.STATELESS_SESSION;
         } else {
            return sessionBeanInfo.isSingleton() ? EjbType.SINGLETON_SESSION : null;
         }
      } else {
         return this.m_beanInfo instanceof MessageDrivenBeanInfo ? EjbType.MESSAGE_DRIVEN : null;
      }
   }

   public Collection getRemoveMethods() {
      if (this.m_beanInfo instanceof SessionBeanInfo) {
         SessionBeanInfo sessionBeanInfo = (SessionBeanInfo)this.m_beanInfo;
         if (sessionBeanInfo.isStateful()) {
            return ((StatefulSessionBeanInfo)this.m_beanInfo).getRemoveMethods();
         }
      }

      return Collections.emptyList();
   }

   public EjbInstanceManager getEjbInstanceManager() {
      return new EjbInstanceManagerAdapter(this.m_beanInfo, this.m_moduleName);
   }

   public Class getEJBGeneratedSubClass() {
      if (this.m_beanInfo instanceof SessionBeanInfo) {
         return ((SessionBeanInfo)this.m_beanInfo).getGeneratedBeanClass();
      } else {
         MessageDrivenBeanInfo mdbi = (MessageDrivenBeanInfo)this.m_beanInfo;
         return mdbi.implementsMessageListener() ? mdbi.getBeanClass() : mdbi.getGeneratedBeanClass();
      }
   }

   Class getNonGeneratedEjbBeanClass() {
      return this.m_beanInfo.getBeanClass();
   }

   public boolean isPassivationCapable() {
      return this.getEjbType() == EjbType.STATEFUL_SESSION ? ((StatefulSessionBeanInfo)this.m_beanInfo).isPassivationCapable() : false;
   }

   public Collection getRemoteBusinessInterfaceClasses() {
      Set classes = new HashSet();
      if (this.m_beanInfo instanceof SessionBeanInfo) {
         SessionBeanInfo sessionBeanInfo = (SessionBeanInfo)this.m_beanInfo;
         classes.addAll(sessionBeanInfo.getBusinessRemotes());
         return classes;
      } else {
         if (this.m_beanInfo instanceof MessageDrivenBeanInfo) {
         }

         return Collections.emptySet();
      }
   }

   static class EjbInstanceManagerAdapter implements EjbInstanceManager, Serializable {
      private BeanInfo m_beanInfo;
      private SessionBeanReference m_beanReference;
      private String m_moduleName;

      EjbInstanceManagerAdapter(BeanInfo beanInfo, String moduleName) {
         this.m_beanInfo = beanInfo;
         if (this.m_beanInfo instanceof SessionBeanInfo) {
            this.m_beanReference = ((SessionBeanInfo)this.m_beanInfo).getSessionBeanReference();
         } else {
            this.m_beanReference = null;
         }

         this.m_moduleName = moduleName;
      }

      EjbInstanceManagerAdapter() {
      }

      private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
         objectOutputStream.writeObject(this.m_moduleName);
         objectOutputStream.writeObject(this.m_beanInfo.getEJBName());
      }

      private void readObject(ObjectInputStream originalObjectInputStream) throws IOException, ClassNotFoundException {
         ObjectInputStream objectInputStream = new FilteringObjectInputStream(originalObjectInputStream);
         String moduleName = (String)objectInputStream.readObject();
         String ejbName = (String)objectInputStream.readObject();
         ApplicationContextInternal appCtx = ApplicationAccess.getApplicationAccess().getCurrentApplicationContext();
         ModuleContext moduleContext = appCtx.getModuleContext(moduleName);
         DeploymentInfo deploymentInfo = (DeploymentInfo)moduleContext.getRegistry().get(DeploymentInfo.class.getName());
         this.m_beanInfo = deploymentInfo.getBeanInfo(ejbName);
         if (this.m_beanInfo instanceof SessionBeanInfo) {
            this.m_beanReference = ((SessionBeanInfo)this.m_beanInfo).getSessionBeanReference();
         } else {
            this.m_beanReference = null;
         }

         this.m_moduleName = moduleName;
      }

      public Object getEjbInstance(Class businessInterfaceType) {
         return this.m_beanReference.getBusinessObject(businessInterfaceType);
      }

      public void remove() {
         if (this.m_beanReference instanceof StatefulSessionBeanReference) {
            ((StatefulSessionBeanReference)this.m_beanReference).remove();
         }

      }

      public boolean isRemoved() {
         return this.m_beanReference instanceof StatefulSessionBeanReference ? ((StatefulSessionBeanReference)this.m_beanReference).isRemoved() : false;
      }
   }
}
