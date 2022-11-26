package weblogic.management.mbeans.custom;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.glassfish.hk2.api.ServiceHandle;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.PartitionConfiguratorMBean;
import weblogic.management.configuration.PartitionTemplateMBean;
import weblogic.management.pconfigurator.PartitionConfigurator;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.management.provider.internal.PartitionTemplateException;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.StringUtils;

public final class PartitionTemplate extends ConfigurationMBeanCustomizer {
   private static final String descriptorBean = "weblogic.descriptor.DescriptorBean";

   public PartitionTemplate(ConfigurationMBeanCustomized base) {
      super(base);
   }

   protected PartitionTemplateMBean getConfig() {
      return (PartitionTemplateMBean)this.getMbean();
   }

   private static PartitionConfiguratorMBean createPConfiguratorMBean(PartitionTemplateMBean template, Class beanIfaceClass) throws PartitionTemplateException {
      StringBuilder beanImplName = new StringBuilder();
      beanImplName.append(beanIfaceClass.getName());
      beanImplName.append("Impl");

      try {
         ClassLoader cl = beanIfaceClass.getClassLoader();
         Class c = cl.loadClass(beanImplName.toString());
         Class desc = cl.loadClass("weblogic.descriptor.DescriptorBean");
         Constructor cstr = c.getConstructor(desc, Integer.TYPE);
         PartitionConfiguratorMBean res = (PartitionConfiguratorMBean)cstr.newInstance(template, -1);
         return res;
      } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException var8) {
         throw new PartitionTemplateException(var8);
      }
   }

   private static void addConfigProvider(PartitionTemplateMBean template, PartitionConfiguratorMBean config) throws PartitionTemplateException {
      try {
         Method m = template.getClass().getMethod("addPartitionConfigurator", PartitionConfiguratorMBean.class);
         m.invoke(template, config);
      } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var3) {
         throw new PartitionTemplateException(var3);
      }
   }

   private static PartitionConfigurator getService(String serviceName) {
      if (StringUtils.isEmptyString(serviceName)) {
         List allServices = GlobalServiceLocator.getServiceLocator().getAllServices(PartitionConfigurator.class, new Annotation[0]);
         return allServices != null && allServices.size() == 1 ? (PartitionConfigurator)allServices.get(0) : null;
      } else {
         return (PartitionConfigurator)GlobalServiceLocator.getServiceLocator().getService(PartitionConfigurator.class, serviceName, new Annotation[0]);
      }
   }

   public PartitionConfiguratorMBean createPartitionConfigurator(String name, String serviceName) {
      PartitionTemplateMBean template = this.getConfig();

      try {
         PartitionConfigurator service = getService(serviceName);
         if (service == null) {
            throw new PartitionTemplateException("Not such service - " + serviceName);
         } else {
            PartitionConfiguratorMBean config = null;
            config = createPConfiguratorMBean(template, service.getPartitionConfiguratorMBeanIface());
            addConfigProvider(template, config);
            return config;
         }
      } catch (PartitionTemplateException var6) {
         ManagementLogger.logExceptionInCustomizer(var6);
         return null;
      }
   }

   public String[] listPartitionConfiguratorServiceNames() {
      List allServiceHandles = GlobalServiceLocator.getServiceLocator().getAllServiceHandles(PartitionConfigurator.class, new Annotation[0]);
      List result = new ArrayList();
      Iterator var3 = allServiceHandles.iterator();

      while(var3.hasNext()) {
         ServiceHandle handle = (ServiceHandle)var3.next();
         String name = handle.getActiveDescriptor().getName();
         if (!StringUtils.isEmptyString(name)) {
            result.add(name);
         }
      }

      return (String[])result.toArray(new String[result.size()]);
   }
}
