package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableBeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.context.ApplicationContextAware;
import com.bea.core.repackaged.springframework.context.ApplicationContextException;
import com.bea.core.repackaged.springframework.context.ConfigurableApplicationContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class LiveBeansView implements LiveBeansViewMBean, ApplicationContextAware {
   public static final String MBEAN_DOMAIN_PROPERTY_NAME = "spring.liveBeansView.mbeanDomain";
   public static final String MBEAN_APPLICATION_KEY = "application";
   private static final Set applicationContexts = new LinkedHashSet();
   @Nullable
   private static String applicationName;
   @Nullable
   private ConfigurableApplicationContext applicationContext;

   static void registerApplicationContext(ConfigurableApplicationContext applicationContext) {
      String mbeanDomain = applicationContext.getEnvironment().getProperty("spring.liveBeansView.mbeanDomain");
      if (mbeanDomain != null) {
         synchronized(applicationContexts) {
            if (applicationContexts.isEmpty()) {
               try {
                  MBeanServer server = ManagementFactory.getPlatformMBeanServer();
                  applicationName = applicationContext.getApplicationName();
                  server.registerMBean(new LiveBeansView(), new ObjectName(mbeanDomain, "application", applicationName));
               } catch (Throwable var5) {
                  throw new ApplicationContextException("Failed to register LiveBeansView MBean", var5);
               }
            }

            applicationContexts.add(applicationContext);
         }
      }

   }

   static void unregisterApplicationContext(ConfigurableApplicationContext applicationContext) {
      synchronized(applicationContexts) {
         if (applicationContexts.remove(applicationContext) && applicationContexts.isEmpty()) {
            try {
               MBeanServer server = ManagementFactory.getPlatformMBeanServer();
               String mbeanDomain = applicationContext.getEnvironment().getProperty("spring.liveBeansView.mbeanDomain");
               if (mbeanDomain != null) {
                  server.unregisterMBean(new ObjectName(mbeanDomain, "application", applicationName));
               }
            } catch (Throwable var9) {
               throw new ApplicationContextException("Failed to unregister LiveBeansView MBean", var9);
            } finally {
               applicationName = null;
            }
         }

      }
   }

   public void setApplicationContext(ApplicationContext applicationContext) {
      Assert.isTrue(applicationContext instanceof ConfigurableApplicationContext, "ApplicationContext does not implement ConfigurableApplicationContext");
      this.applicationContext = (ConfigurableApplicationContext)applicationContext;
   }

   public String getSnapshotAsJson() {
      Set contexts;
      if (this.applicationContext != null) {
         contexts = Collections.singleton(this.applicationContext);
      } else {
         contexts = this.findApplicationContexts();
      }

      return this.generateJson(contexts);
   }

   protected Set findApplicationContexts() {
      synchronized(applicationContexts) {
         return new LinkedHashSet(applicationContexts);
      }
   }

   protected String generateJson(Set contexts) {
      StringBuilder result = new StringBuilder("[\n");
      Iterator it = contexts.iterator();

      while(it.hasNext()) {
         ConfigurableApplicationContext context = (ConfigurableApplicationContext)it.next();
         result.append("{\n\"context\": \"").append(context.getId()).append("\",\n");
         if (context.getParent() != null) {
            result.append("\"parent\": \"").append(context.getParent().getId()).append("\",\n");
         } else {
            result.append("\"parent\": null,\n");
         }

         result.append("\"beans\": [\n");
         ConfigurableListableBeanFactory bf = context.getBeanFactory();
         String[] beanNames = bf.getBeanDefinitionNames();
         boolean elementAppended = false;
         String[] var8 = beanNames;
         int var9 = beanNames.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            String beanName = var8[var10];
            BeanDefinition bd = bf.getBeanDefinition(beanName);
            if (this.isBeanEligible(beanName, bd, bf)) {
               if (elementAppended) {
                  result.append(",\n");
               }

               result.append("{\n\"bean\": \"").append(beanName).append("\",\n");
               result.append("\"aliases\": ");
               this.appendArray(result, bf.getAliases(beanName));
               result.append(",\n");
               String scope = bd.getScope();
               if (!StringUtils.hasText(scope)) {
                  scope = "singleton";
               }

               result.append("\"scope\": \"").append(scope).append("\",\n");
               Class beanType = bf.getType(beanName);
               if (beanType != null) {
                  result.append("\"type\": \"").append(beanType.getName()).append("\",\n");
               } else {
                  result.append("\"type\": null,\n");
               }

               result.append("\"resource\": \"").append(this.getEscapedResourceDescription(bd)).append("\",\n");
               result.append("\"dependencies\": ");
               this.appendArray(result, bf.getDependenciesForBean(beanName));
               result.append("\n}");
               elementAppended = true;
            }
         }

         result.append("]\n");
         result.append("}");
         if (it.hasNext()) {
            result.append(",\n");
         }
      }

      result.append("]");
      return result.toString();
   }

   protected boolean isBeanEligible(String beanName, BeanDefinition bd, ConfigurableBeanFactory bf) {
      return bd.getRole() != 2 && (!bd.isLazyInit() || bf.containsSingleton(beanName));
   }

   @Nullable
   protected String getEscapedResourceDescription(BeanDefinition bd) {
      String resourceDescription = bd.getResourceDescription();
      if (resourceDescription == null) {
         return null;
      } else {
         StringBuilder result = new StringBuilder(resourceDescription.length() + 16);

         for(int i = 0; i < resourceDescription.length(); ++i) {
            char character = resourceDescription.charAt(i);
            if (character == '\\') {
               result.append('/');
            } else if (character == '"') {
               result.append("\\").append('"');
            } else {
               result.append(character);
            }
         }

         return result.toString();
      }
   }

   private void appendArray(StringBuilder result, String[] arr) {
      result.append('[');
      if (arr.length > 0) {
         result.append('"');
      }

      result.append(StringUtils.arrayToDelimitedString(arr, "\", \""));
      if (arr.length > 0) {
         result.append('"');
      }

      result.append(']');
   }
}
