package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactoryAware;
import com.bea.core.repackaged.springframework.context.EnvironmentAware;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.core.env.Environment;
import com.bea.core.repackaged.springframework.core.type.AnnotationMetadata;
import com.bea.core.repackaged.springframework.jmx.MBeanServerNotFoundException;
import com.bea.core.repackaged.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import com.bea.core.repackaged.springframework.jmx.support.RegistrationPolicy;
import com.bea.core.repackaged.springframework.jmx.support.WebSphereMBeanServerFactoryBean;
import com.bea.core.repackaged.springframework.jndi.JndiLocatorDelegate;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Map;
import javax.management.MBeanServer;
import javax.naming.NamingException;

@Configuration
public class MBeanExportConfiguration implements ImportAware, EnvironmentAware, BeanFactoryAware {
   private static final String MBEAN_EXPORTER_BEAN_NAME = "mbeanExporter";
   @Nullable
   private AnnotationAttributes enableMBeanExport;
   @Nullable
   private Environment environment;
   @Nullable
   private BeanFactory beanFactory;

   public void setImportMetadata(AnnotationMetadata importMetadata) {
      Map map = importMetadata.getAnnotationAttributes(EnableMBeanExport.class.getName());
      this.enableMBeanExport = AnnotationAttributes.fromMap(map);
      if (this.enableMBeanExport == null) {
         throw new IllegalArgumentException("@EnableMBeanExport is not present on importing class " + importMetadata.getClassName());
      }
   }

   public void setEnvironment(Environment environment) {
      this.environment = environment;
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      this.beanFactory = beanFactory;
   }

   @Bean(
      name = {"mbeanExporter"}
   )
   @Role(2)
   public AnnotationMBeanExporter mbeanExporter() {
      AnnotationMBeanExporter exporter = new AnnotationMBeanExporter();
      Assert.state(this.enableMBeanExport != null, "No EnableMBeanExport annotation found");
      this.setupDomain(exporter, this.enableMBeanExport);
      this.setupServer(exporter, this.enableMBeanExport);
      this.setupRegistrationPolicy(exporter, this.enableMBeanExport);
      return exporter;
   }

   private void setupDomain(AnnotationMBeanExporter exporter, AnnotationAttributes enableMBeanExport) {
      String defaultDomain = enableMBeanExport.getString("defaultDomain");
      if (StringUtils.hasLength(defaultDomain) && this.environment != null) {
         defaultDomain = this.environment.resolvePlaceholders(defaultDomain);
      }

      if (StringUtils.hasText(defaultDomain)) {
         exporter.setDefaultDomain(defaultDomain);
      }

   }

   private void setupServer(AnnotationMBeanExporter exporter, AnnotationAttributes enableMBeanExport) {
      String server = enableMBeanExport.getString("server");
      if (StringUtils.hasLength(server) && this.environment != null) {
         server = this.environment.resolvePlaceholders(server);
      }

      if (StringUtils.hasText(server)) {
         Assert.state(this.beanFactory != null, "No BeanFactory set");
         exporter.setServer((MBeanServer)this.beanFactory.getBean(server, MBeanServer.class));
      } else {
         SpecificPlatform specificPlatform = MBeanExportConfiguration.SpecificPlatform.get();
         if (specificPlatform != null) {
            MBeanServer mbeanServer = specificPlatform.getMBeanServer();
            if (mbeanServer != null) {
               exporter.setServer(mbeanServer);
            }
         }
      }

   }

   private void setupRegistrationPolicy(AnnotationMBeanExporter exporter, AnnotationAttributes enableMBeanExport) {
      RegistrationPolicy registrationPolicy = (RegistrationPolicy)enableMBeanExport.getEnum("registration");
      exporter.setRegistrationPolicy(registrationPolicy);
   }

   public static enum SpecificPlatform {
      WEBLOGIC("weblogic.management.Helper") {
         public MBeanServer getMBeanServer() {
            try {
               return (MBeanServer)(new JndiLocatorDelegate()).lookup("java:comp/env/jmx/runtime", MBeanServer.class);
            } catch (NamingException var2) {
               throw new MBeanServerNotFoundException("Failed to retrieve WebLogic MBeanServer from JNDI", var2);
            }
         }
      },
      WEBSPHERE("com.ibm.websphere.management.AdminServiceFactory") {
         public MBeanServer getMBeanServer() {
            WebSphereMBeanServerFactoryBean fb = new WebSphereMBeanServerFactoryBean();
            fb.afterPropertiesSet();
            return fb.getObject();
         }
      };

      private final String identifyingClass;

      private SpecificPlatform(String identifyingClass) {
         this.identifyingClass = identifyingClass;
      }

      @Nullable
      public abstract MBeanServer getMBeanServer();

      @Nullable
      public static SpecificPlatform get() {
         ClassLoader classLoader = MBeanExportConfiguration.class.getClassLoader();
         SpecificPlatform[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            SpecificPlatform environment = var1[var3];
            if (ClassUtils.isPresent(environment.identifyingClass, classLoader)) {
               return environment;
            }
         }

         return null;
      }

      // $FF: synthetic method
      SpecificPlatform(String x2, Object x3) {
         this(x2);
      }
   }
}
