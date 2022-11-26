package com.oracle.injection.provider.weld;

import com.oracle.injection.ejb.EjbType;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.jboss.weld.ejb.spi.BusinessInterfaceDescriptor;
import org.jboss.weld.ejb.spi.EjbDescriptor;
import org.jboss.weld.ejb.spi.SubclassedComponentDescriptor;

class WeldEjbDescriptorAdapter implements EjbDescriptor, SubclassedComponentDescriptor {
   private final com.oracle.injection.ejb.EjbDescriptor m_genericDescriptor;

   WeldEjbDescriptorAdapter(com.oracle.injection.ejb.EjbDescriptor genericDescriptor) {
      if (genericDescriptor == null) {
         throw new IllegalArgumentException("EjbDescriptor parameter cannot be null.");
      } else {
         this.m_genericDescriptor = genericDescriptor;
      }
   }

   public Class getBeanClass() {
      return this.m_genericDescriptor.getEJBClass();
   }

   public Collection getLocalBusinessInterfaces() {
      Collection localInterfaces = new ArrayList();
      Iterator var2 = this.m_genericDescriptor.getLocalBusinessInterfaceClasses().iterator();

      while(var2.hasNext()) {
         final Class localInterfaceType = (Class)var2.next();
         localInterfaces.add(new BusinessInterfaceDescriptor() {
            public Class getInterface() {
               return localInterfaceType;
            }
         });
      }

      return localInterfaces;
   }

   public Collection getRemoteBusinessInterfaces() {
      Collection remoteInterfaces = new ArrayList();
      Iterator var2 = this.m_genericDescriptor.getRemoteBusinessInterfaceClasses().iterator();

      while(var2.hasNext()) {
         final Class localInterfaceType = (Class)var2.next();
         remoteInterfaces.add(new BusinessInterfaceDescriptor() {
            public Class getInterface() {
               return localInterfaceType;
            }
         });
      }

      return remoteInterfaces;
   }

   public String getEjbName() {
      return this.m_genericDescriptor.getEjbName();
   }

   public Collection getRemoveMethods() {
      return this.m_genericDescriptor.getRemoveMethods();
   }

   public boolean isStateless() {
      return this.m_genericDescriptor.getEjbType() == EjbType.STATELESS_SESSION;
   }

   public boolean isSingleton() {
      return this.m_genericDescriptor.getEjbType() == EjbType.SINGLETON_SESSION;
   }

   public boolean isStateful() {
      return this.m_genericDescriptor.getEjbType() == EjbType.STATEFUL_SESSION;
   }

   public boolean isMessageDriven() {
      return this.m_genericDescriptor.getEjbType() == EjbType.MESSAGE_DRIVEN;
   }

   public boolean isPassivationCapable() {
      return this.m_genericDescriptor.isPassivationCapable();
   }

   com.oracle.injection.ejb.EjbDescriptor getInternalDescriptor() {
      return this.m_genericDescriptor;
   }

   public Class getComponentSubclass() {
      Constructor[] constructors = this.m_genericDescriptor.getEJBClass().getConstructors();
      if (constructors != null && constructors.length > 0) {
         Constructor[] var2 = constructors;
         int var3 = constructors.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Constructor constructor = var2[var4];
            Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
            if (parameterAnnotations != null && parameterAnnotations.length > 0 && parameterAnnotations[0].length > 0) {
               return this.m_genericDescriptor.getEJBClass();
            }
         }
      }

      return this.m_genericDescriptor.getEJBGeneratedSubClass();
   }
}
