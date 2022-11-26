package weblogic.management.provider.internal;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import weblogic.descriptor.BasicDescriptorManager;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorMacroSubstitutor;
import weblogic.descriptor.SecurityService;
import weblogic.descriptor.internal.DescriptorBeanClassName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PartitionTemplateMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.beaninfo.BeanInfoAccess;

public abstract class PartitionTemplateUtils {
   private static final DescriptorMacroSubstitutor ms = new PartitionMacroSubstitutor();
   private static volatile BeanInfoAccess beanInfoAccess = null;
   private static Set excludes = null;
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugConfigurationRuntime");

   private static List getAttributesForInterface(String iface) {
      BeanInfo bInfo = getBeanInfoAccess().getBeanInfoForInterface(iface, true, (String)null);
      if (bInfo != null && bInfo.getPropertyDescriptors() != null) {
         List res = new ArrayList();
         PropertyDescriptor[] var3 = bInfo.getPropertyDescriptors();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PropertyDescriptor desc = var3[var5];
            String attr = desc.getName();
            if (!attr.equals("Tags") && !attr.equals("Notes")) {
               res.add(attr);
            }
         }

         return res;
      } else {
         return Collections.EMPTY_LIST;
      }
   }

   static synchronized Set getExcludedAttributes() {
      if (excludes == null) {
         excludes = new HashSet();
         excludes.addAll(getAttributesForInterface(ConfigurationMBean.class.getName()));
         excludes.addAll(getAttributesForInterface(WebLogicMBean.class.getName()));
      }

      return excludes;
   }

   private static BeanInfoAccess getBeanInfoAccess() {
      if (beanInfoAccess != null) {
         return beanInfoAccess;
      } else {
         beanInfoAccess = ManagementService.getBeanInfoAccess();
         if (beanInfoAccess == null) {
            throw new IllegalStateException("Unable to get BeanInfoAccess");
         } else {
            return beanInfoAccess;
         }
      }
   }

   private static PropertyDescriptor getPropertyDescriptor(BeanInfo bInfo, String attr) {
      return getBeanInfoAccess().getPropertyDescriptor(bInfo, attr);
   }

   private static BeanInfo getBeanInfo(Object o) {
      BeanInfo bi = getBeanInfoAccess().getBeanInfoForInstance(o, false, (String)null);
      return bi;
   }

   public static DomainMBean createDomain() throws Exception {
      Class domainClazz = DomainMBean.class;
      ClassLoader cl = PartitionTemplateUtils.class.getClassLoader();
      BasicDescriptorManager bdm = new BasicDescriptorManager(cl, true, (SecurityService)null);
      DescriptorBean parent = bdm.createDescriptorRoot(domainClazz).getRootBean();
      String domainImpl = DescriptorBeanClassName.toImpl(domainClazz.getName());
      Constructor cstr = cl.loadClass(domainImpl).getConstructor(DescriptorBean.class, Integer.TYPE);
      return (DomainMBean)cstr.newInstance(parent, -1);
   }

   private static void debug(String msg) {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug(PartitionTemplateUtils.class.getSimpleName() + " - " + msg);
      }

   }

   private static boolean isTemplatized(DescriptorBean bean) {
      while(bean != null) {
         if (bean instanceof PartitionTemplateMBean) {
            return true;
         }

         bean = bean.getParentBean();
      }

      return false;
   }

   private static Object _resolveNewAttributeValue(Object templateValue, PropertyDescriptor templateDescriptor, DescriptorBean bean, PartitionMBean partition) throws PartitionTemplateException {
      Object newVal = null;
      if (WebLogicMBean.class.isAssignableFrom(templateValue.getClass()) && DescriptorBean.class.isAssignableFrom(templateValue.getClass())) {
         if (!isTemplatized((DescriptorBean)templateValue)) {
            return templateValue;
         }

         String creatorName = (String)templateDescriptor.getValue("creator");

         try {
            Method read;
            if (creatorName != null) {
               read = bean.getClass().getMethod(creatorName, String.class);
               String name = ms.performMacroSubstitution(((WebLogicMBean)templateValue).getName(), partition);
               newVal = read.invoke(bean, name);
            } else {
               if (templateDescriptor.getWriteMethod() != null) {
                  return null;
               }

               read = templateDescriptor.getReadMethod();
               if (read != null) {
                  newVal = read.invoke(bean);
               }
            }
         } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | IllegalAccessException var8) {
            throw new PartitionTemplateException(var8);
         }

         replicateDescriptor(newVal, templateValue, partition);
      } else if (templateValue instanceof String) {
         if (partition != null) {
            newVal = ms.performMacroSubstitution((String)templateValue, partition);
         } else {
            newVal = templateValue;
         }
      } else {
         newVal = templateValue;
      }

      return newVal;
   }

   private static void resolveNewAttributeValue(Object templateValue, PropertyDescriptor templateDescriptor, DescriptorBean bean, PartitionMBean partition) throws PartitionTemplateException {
      if (templateValue != null && bean != null) {
         Object newVal;
         if (templateValue.getClass().isArray()) {
            int length = Array.getLength(templateValue);
            if (length == 0) {
               return;
            }

            newVal = Array.newInstance(templateValue.getClass().getComponentType(), length);

            for(int i = 0; i < length; ++i) {
               Object curOldVal = Array.get(templateValue, i);
               Object curNewVal = _resolveNewAttributeValue(curOldVal, templateDescriptor, bean, partition);
               Array.set(newVal, i, curNewVal);
            }
         } else {
            newVal = _resolveNewAttributeValue(templateValue, templateDescriptor, bean, partition);
         }

         Method write = templateDescriptor.getWriteMethod();
         if (write != null) {
            try {
               write.invoke(bean, newVal);
            } catch (IllegalStateException | IllegalArgumentException | InvocationTargetException | IllegalAccessException var9) {
               throw new PartitionTemplateException(var9);
            }
         }

      }
   }

   private static ConfigurationMBean replicateDescriptor(Object bean, Object template, PartitionMBean part) throws PartitionTemplateException {
      BeanInfo beanInfo = getBeanInfo(bean);
      BeanInfo templateInfo = getBeanInfo(template);
      PropertyDescriptor[] var5 = beanInfo.getPropertyDescriptors();
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         PropertyDescriptor beanDescriptor = var5[var7];
         String attr = beanDescriptor.getName();
         if (!getExcludedAttributes().contains(attr) && ((DescriptorBean)template).isSet(attr)) {
            PropertyDescriptor templateDescriptor = getPropertyDescriptor(templateInfo, attr);
            if (templateDescriptor == null) {
               debug(String.format("Unable to get property descriptor for %s.%s", template.getClass().getSimpleName(), attr));
            } else {
               try {
                  Method read = templateDescriptor.getReadMethod();
                  if (read != null) {
                     Object templateValue = read.invoke(template, (Object[])null);
                     if (templateValue != null) {
                        Object def = templateDescriptor.getValue("default");
                        if (def == null || !def.equals(templateValue)) {
                           resolveNewAttributeValue(templateValue, templateDescriptor, (DescriptorBean)bean, part);
                        }
                     }
                  }
               } catch (IllegalStateException | IllegalArgumentException | InvocationTargetException | IllegalAccessException var14) {
                  debug(String.format("An error occured while reading value of %s.%s in %s - %s", bean.getClass().getSimpleName(), attr, template.toString(), var14.getLocalizedMessage()));
               }
            }
         }
      }

      return (ConfigurationMBean)bean;
   }

   public static ConfigurationMBean replicateDescriptor(ConfigurationMBean bean, ConfigurationMBean template) throws PartitionTemplateException {
      PartitionMBean part = null;
      if (bean instanceof PartitionMBean) {
         part = (PartitionMBean)bean;
      }

      return replicateDescriptor(bean, template, part);
   }
}
