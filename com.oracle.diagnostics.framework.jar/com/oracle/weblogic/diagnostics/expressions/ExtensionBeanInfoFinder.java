package com.oracle.weblogic.diagnostics.expressions;

import java.beans.BeanInfo;
import java.util.Iterator;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.IterableProvider;
import org.jvnet.hk2.annotations.Service;

@Service
@Singleton
public class ExtensionBeanInfoFinder {
   @Inject
   IterableProvider beanInfoProviders;

   public BeanInfo findBeanInfo(String ns, String beanName, Locale l) {
      BeanInfo info = null;
      if (this.beanInfoProviders != null) {
         Iterator var5 = this.beanInfoProviders.iterator();

         while(var5.hasNext()) {
            ExtensionBeanInfoProvider provider = (ExtensionBeanInfoProvider)var5.next();
            info = provider.getBeanInfo(ns, beanName, l);
            if (info != null) {
               break;
            }
         }
      }

      return info;
   }

   public BeanInfo findBeanInfo(Class forClass, Locale l) {
      BeanInfo info = null;
      if (this.beanInfoProviders != null) {
         Iterator var4 = this.beanInfoProviders.iterator();

         while(var4.hasNext()) {
            ExtensionBeanInfoProvider provider = (ExtensionBeanInfoProvider)var4.next();
            info = provider.getBeanInfo(forClass, l);
            if (info != null) {
               break;
            }
         }
      }

      return info;
   }
}
