package com.oracle.injection.integration;

import com.oracle.injection.InjectionArchiveType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import weblogic.application.ModuleContext;
import weblogic.application.ModuleExtensionContext;
import weblogic.ejb.spi.BeanInfo;
import weblogic.ejb.spi.ClientDrivenBeanInfo;
import weblogic.ejb.spi.DeploymentInfo;

class EjbModuleInjectionArchive extends ModuleInjectionArchive {
   private Collection cacheOfBeanClassNames = null;
   private final int[] cacheOfBeanClassesLock = new int[0];
   private static final int UNDERSCORE = 95;
   private static final String IMPL = "Impl";
   private static final String INTF = "Intf";
   private static final String STUB = "Stub";
   private static final String SKEL = "Skel";

   EjbModuleInjectionArchive(ModuleContext moduleContext, ModuleExtensionContext moduleExtensionContext) {
      super(moduleContext, moduleExtensionContext);
   }

   public InjectionArchiveType getArchiveType() {
      return InjectionArchiveType.EJB_JAR;
   }

   public Collection getBeanClassNames() {
      if (this.cacheOfBeanClassNames == null) {
         synchronized(this.cacheOfBeanClassesLock) {
            if (this.cacheOfBeanClassNames == null) {
               this.cacheOfBeanClassNames = this.internalGetBeanClassNames();
            }
         }
      }

      return this.cacheOfBeanClassNames;
   }

   private Collection internalGetBeanClassNames() {
      List clientImpls = this.getGeneratedClientImpls();
      List beanClassNames = new ArrayList();
      Collection modExtCtxBeanClassNames = this.m_moduleExtensionContext.getBeanClassNames();
      if (modExtCtxBeanClassNames != null) {
         Iterator var4 = modExtCtxBeanClassNames.iterator();

         while(var4.hasNext()) {
            String oneBeanClassName = (String)var4.next();
            if (isAGeneratedClass(oneBeanClassName)) {
               if (clientImpls.contains(oneBeanClassName)) {
                  beanClassNames.add(oneBeanClassName);
               }
            } else {
               beanClassNames.add(oneBeanClassName);
            }
         }
      }

      return beanClassNames;
   }

   private static boolean isAGeneratedClass(String name) {
      if (name == null) {
         return false;
      } else {
         int index = name.indexOf(95);
         if (index == -1) {
            return false;
         } else {
            String subString = name.substring(index);
            index = subString.indexOf(95);
            if (index == -1) {
               return false;
            } else {
               subString = subString.substring(index);
               return subString.endsWith("Impl") || subString.endsWith("Intf") || subString.endsWith("Stub") || subString.endsWith("Skel");
            }
         }
      }
   }

   private List getGeneratedClientImpls() {
      ArrayList retVal = new ArrayList();
      DeploymentInfo deploymentInfo = (DeploymentInfo)this.m_moduleContext.getRegistry().get(DeploymentInfo.class.getName());
      if (deploymentInfo != null) {
         Collection beanInfos = deploymentInfo.getBeanInfos();
         if (beanInfos != null) {
            Iterator var4 = beanInfos.iterator();

            while(var4.hasNext()) {
               BeanInfo beanInfo = (BeanInfo)var4.next();
               if (beanInfo instanceof ClientDrivenBeanInfo) {
                  retVal.add(((ClientDrivenBeanInfo)beanInfo).getGeneratedBeanClass().getName());
               }
            }
         }
      }

      return retVal;
   }
}
