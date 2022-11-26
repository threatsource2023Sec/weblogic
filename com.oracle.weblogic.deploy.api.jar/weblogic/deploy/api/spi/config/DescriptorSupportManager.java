package weblogic.deploy.api.spi.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.deploy.shared.ModuleType;
import org.jvnet.hk2.annotations.Service;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.shared.WebLogicModuleType;
import weblogic.descriptor.beangen.XMLHelper;
import weblogic.j2ee.descriptor.wl.ConfigurationSupportBean;
import weblogic.j2ee.descriptor.wl.CustomModuleBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;

public class DescriptorSupportManager {
   private static final List ddSupport = new ArrayList();
   private static final boolean debug = Debug.isDebug("config");
   public static final String WEB_ROOT = "web-app";
   public static final String WLS_WEB_ROOT = "weblogic-web-app";
   public static final String WEB_NAMESPACE = "http://java.sun.com/xml/ns/j2ee";
   public static final String WLS_WEB_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   public static final String WEB_URI = "WEB-INF/web.xml";
   public static final String WLS_WEB_URI = "WEB-INF/weblogic.xml";
   public static final String EAR_ROOT = "application";
   public static final String WLS_EAR_ROOT = "weblogic-application";
   public static final String EAR_NAMESPACE = "http://java.sun.com/xml/ns/j2ee";
   public static final String WLS_EAR_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   public static final String EAR_URI = "META-INF/application.xml";
   public static final String WLS_EAR_URI = "META-INF/weblogic-application.xml";
   public static final String EJB_ROOT = "ejb-jar";
   public static final String WLS_EJB_ROOT = "weblogic-ejb-jar";
   public static final String EJB_NAMESPACE = "http://java.sun.com/xml/ns/j2ee";
   public static final String WLS_EJB_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   public static final String EJB_URI = "META-INF/ejb-jar.xml";
   public static final String WLS_EJB_URI = "META-INF/weblogic-ejb-jar.xml";
   public static final String EJB_IN_WAR_ROOT = "ejb-jar";
   public static final String WLS_EJB_IN_WAR_ROOT = "weblogic-ejb-jar";
   public static final String EJB_IN_WAR_NAMESPACE = "http://java.sun.com/xml/ns/j2ee";
   public static final String WLS_EJB_IN_WAR_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   public static final String EJB_IN_WAR_URI = "WEB-INF/ejb-jar.xml";
   public static final String WLS_EJB_IN_WAR_URI = "WEB-INF/weblogic-ejb-jar.xml";
   public static final String RAR_ROOT = "connector";
   public static final String WLS_RAR_ROOT = "weblogic-connector";
   public static final String RAR_NAMESPACE = "http://java.sun.com/xml/ns/j2ee";
   public static final String WLS_RAR_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   public static final String RAR_URI = "META-INF/ra.xml";
   public static final String WLS_RAR_URI = "META-INF/weblogic-ra.xml";
   public static final String CAR_ROOT = "application-client";
   public static final String WLS_CAR_ROOT = "weblogic-application-client";
   public static final String CAR_NAMESPACE = "http://java.sun.com/xml/ns/j2ee";
   public static final String WLS_CAR_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   public static final String CAR_URI = "META-INF/application-client.xml";
   public static final String WLS_CAR_URI = "META-INF/weblogic-application-client.xml";
   public static final String WLS_CMP_ROOT = "weblogic-rdbms-jar";
   public static final String WLS_CMP_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   public static final String WLS_CMP_URI = "META-INF/weblogic-cmp-rdbms-jar.xml";
   public static final String WLS_CMP11_ROOT = "weblogic-rdbms-jar";
   public static final String WLS_CMP11_NAMESPACE = "http://www.bea.com/ns/weblogic/60";
   public static final String WLS_CMP11_URI = "META-INF/weblogic-cmp-rdbms-jar.xml";
   public static final String WLS_CMP11_CLASS = "weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanImpl";
   public static final String WLS_CMP11_DCONFIGCLASS = "weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanDConfig";
   public static final String WLS_CMP11_CONFIGCLASS = "weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanImpl";
   public static final String WLS_JMS_ROOT = "weblogic-jms";
   public static final String WLS_JMS_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   public static final String WLS_JDBC_ROOT = "jdbc-data-source";
   public static final String WLS_JDBC_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   public static final String WLS_INTERCEPT_ROOT = "weblogic-interception";
   public static final String WLS_INTERCEPT_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   public static final String WSEE_ROOT_81 = "web-services";
   public static final String WSEE_ROOT = "webservices";
   public static final String WLS_WSEE_ROOT = "weblogic-webservices";
   public static final String WSEE_WEB_URI_81 = "WEB-INF/web-services.xml";
   public static final String WSEE_EJB_URI_81 = "META-INF/web-services.xml";
   public static final String WSEE_WEB_URI = "WEB-INF/webservices.xml";
   public static final String WSEE_EJB_URI = "META-INF/webservices.xml";
   public static final String WLS_WSEE_WEB_URI = "WEB-INF/weblogic-webservices.xml";
   public static final String WLS_WSEE_EJB_URI = "META-INF/weblogic-webservices.xml";
   public static final String WLS_WS_POLICY_WEB_URI = "WEB-INF/weblogic-webservices-policy.xml";
   public static final String WLS_WS_POLICY_EJB_URI = "META-INF/weblogic-webservices-policy.xml";
   public static final String REST_WEBSERVICES_BEAN_DESCRIPTOR_URI = "weblogic.j2ee.descriptor.wl.RestWebservicesBean";
   public static final String PERSISTENCE_ROOT = "persistence";
   public static final String WLS_PERSISTENCE_ROOT = "persistence-configuration";
   public static final String PERSISTENCE_URI = "META-INF/persistence.xml";
   public static final String WLS_PERSISTENCE_URI = "META-INF/persistence-configuration.xml";
   public static final String PERSISTENCE_NAMESPACE = "http://java.sun.com/xml/ns/persistence";
   public static final String WLS_PERSISTENCE_NAMESPACE = "http://bea.com/ns/weblogic/950/persistence";
   public static final String PERSISTENCE_BEAN_CLASS = "weblogic.j2ee.descriptor.PersistenceBean";
   public static final String WLS_PERSISTENCE_BEAN_CLASS = "kodo.jdbc.conf.descriptor.PersistenceConfigurationBean";
   public static final String WLS_PERSISTENCE_DCONFIG_CLASS = "kodo.jdbc.conf.descriptor.PersistenceConfigurationBeanDConfig";
   public static final String WLS_WLDF_ROOT = "wldf-resource";
   public static final String WLS_WLDF_NAMESPACE = "java:weblogic.diagnostics.descriptor";
   public static final String WLS_WLDF_URI = "META-INF/weblogic-diagnostics.xml";
   public static final String WLS_WLDF_BEAN_CLASS = "weblogic.diagnostics.descriptor.WLDFResourceBeanImpl";
   public static final String WLS_WLDF_DCONFIG_CLASS = "weblogic.diagnostics.descriptor.WLDFResourceBeanDConfig";
   public static final String WLS_GAR_ROOT = "coherence-application";
   public static final String WLS_GAR_NAMESPACE = "http://xmlns.oracle.com/coherence/coherence-application";
   public static final String WLS_GAR_URI = "META-INF/coherence-application.xml";
   private static Map forceWrites = new HashMap();
   public static final String WLS_EAR_EXT_URI = "META-INF/weblogic-extension.xml";
   public static final String WLS_WEB_EXT_URI = "WEB-INF/weblogic-extension.xml";
   public static final DescriptorSupport EJB_DESC_SUPPORT;
   private static final String EMPTY = "";

   public static void flush() {
      Iterator dsi = ddSupport.iterator();

      while(dsi.hasNext()) {
         DescriptorSupport o = (DescriptorSupport)dsi.next();
         if (o.isFlush()) {
            if (debug) {
               Debug.say("removing DS: " + o.toString());
            }

            dsi.remove();
         }
      }

   }

   public static void add(ModuleType module, String baseTag, String configTag, String baseNameSpace, String configNameSpace, String baseURI, String configURI, String stdClassName, String configClassName, String dConfigClassName) {
      add(new DescriptorSupport(module, baseTag, configTag, baseNameSpace, configNameSpace, baseURI, configURI, stdClassName, configClassName, dConfigClassName, false));
   }

   public static void add(ModuleType module, String baseTag, String configTag, String baseNameSpace, String configNameSpace, String baseURI, String configURI, String stdPackageName, String configPackageName) {
      add(module, baseTag, configTag, baseNameSpace, configNameSpace, baseURI, configURI, createImplClassName(stdPackageName, baseTag), createImplClassName(configPackageName, configTag), createDConfigClassName(configPackageName, configTag));
   }

   public static void add(DescriptorSupport ds) {
      if (!ddSupport.contains(ds)) {
         ds.setFlush(true);
         ddSupport.add(ds);
      }

   }

   private static String createImplClassName(String pkg, String tag) {
      return pkg.concat("." + XMLHelper.toPropName(tag) + "BeanImpl");
   }

   private static String createDConfigClassName(String pkg, String tag) {
      return pkg.concat("." + XMLHelper.toPropName(tag) + "BeanDConfig");
   }

   public static DescriptorSupport getForTag(String baseTag) {
      String tag = removeNamespace(baseTag);
      Iterator var2 = ddSupport.iterator();

      DescriptorSupport dds;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         dds = (DescriptorSupport)var2.next();
      } while(!dds.isPrimary() || !dds.getBaseTag().equals(tag));

      return copy(dds);
   }

   public static DescriptorSupport[] getForSecondaryTag(String baseTag) {
      String tag = removeNamespace(baseTag);
      List retDS = new ArrayList();
      Iterator var3 = ddSupport.iterator();

      while(var3.hasNext()) {
         DescriptorSupport dds = (DescriptorSupport)var3.next();
         if (!dds.isPrimary() && dds.getBaseTag().equals(tag)) {
            retDS.add(copy(dds));
         }
      }

      return (DescriptorSupport[])retDS.toArray(new DescriptorSupport[0]);
   }

   public static DescriptorSupport[] getForBaseURI(String uri) {
      List retDS = new ArrayList();
      Iterator var2 = ddSupport.iterator();

      while(var2.hasNext()) {
         DescriptorSupport dds = (DescriptorSupport)var2.next();
         if (dds.getBaseURI().equals(uri)) {
            retDS.add(copy(dds));
         }
      }

      return (DescriptorSupport[])retDS.toArray(new DescriptorSupport[0]);
   }

   private static String removeNamespace(String tag) {
      int ndx = tag.indexOf(58);
      if (ndx != -1) {
         tag = tag.substring(ndx + 1);
      }

      return tag;
   }

   public static DescriptorSupport[] getForModuleType(ModuleType module) throws IllegalArgumentException {
      List retDS = new ArrayList();
      Iterator var2 = ddSupport.iterator();

      while(var2.hasNext()) {
         DescriptorSupport dds = (DescriptorSupport)var2.next();
         if (dds.getModuleType().equals(module)) {
            retDS.add(copy(dds));
         }
      }

      return (DescriptorSupport[])retDS.toArray(new DescriptorSupport[0]);
   }

   public static DescriptorSupport getForDConfigClass(String className) throws IllegalArgumentException {
      Iterator var1 = ddSupport.iterator();

      DescriptorSupport dds;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         dds = (DescriptorSupport)var1.next();
      } while(!dds.getDConfigClassName().equals(className));

      return copy(dds);
   }

   public static DescriptorSupport getForConfigClass(String className) throws IllegalArgumentException {
      Iterator var1 = ddSupport.iterator();

      DescriptorSupport dds;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         dds = (DescriptorSupport)var1.next();
      } while(!dds.getConfigClassName().equals(className));

      return copy(dds);
   }

   public static DescriptorSupport getForStandardClass(String className) throws IllegalArgumentException {
      Iterator var1 = ddSupport.iterator();

      DescriptorSupport dds;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         dds = (DescriptorSupport)var1.next();
      } while(!dds.getStandardClassName().equals(className));

      return copy(dds);
   }

   public static DescriptorSupport getForClass(String className) throws IllegalArgumentException {
      Iterator var1 = ddSupport.iterator();

      DescriptorSupport dds;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         dds = (DescriptorSupport)var1.next();
         if (dds.getStandardClassName().equals(className)) {
            return copy(dds);
         }
      } while(!dds.getDConfigClassName().equals(className));

      return copy(dds);
   }

   public static DescriptorSupport[] getForModuleType(String module) throws IllegalArgumentException {
      List retDS = new ArrayList();
      Iterator var2 = ddSupport.iterator();

      while(var2.hasNext()) {
         DescriptorSupport dds = (DescriptorSupport)var2.next();
         if (dds.getModuleType().toString().equals(module)) {
            retDS.add(copy(dds));
         }
      }

      if (retDS.isEmpty()) {
         throw new IllegalArgumentException(SPIDeployerLogger.unsupportedModuleType(module.toString()));
      } else {
         return (DescriptorSupport[])retDS.toArray(new DescriptorSupport[0]);
      }
   }

   private static DescriptorSupport copy(DescriptorSupport ds) {
      return new DescriptorSupport(ds.getModuleType(), ds.getBaseTag(), ds.getConfigTag(), ds.getBaseNameSpace(), ds.getConfigNameSpace(), ds.getBaseURI(), ds.getConfigURI(), ds.getStandardClassName(), ds.getConfigClassName(), ds.getDConfigClassName(), ds.isPrimary());
   }

   private static void initDescriptorSupport() {
      try {
         ddSupport.add(new DescriptorSupport(ModuleType.WAR, "web-app", "weblogic-web-app", "http://java.sun.com/xml/ns/j2ee", "http://www.bea.com/ns/weblogic/90", "WEB-INF/web.xml", "WEB-INF/weblogic.xml", "weblogic.j2ee.descriptor.WebAppBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicWebAppBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicWebAppBeanDConfig", true));
         ddSupport.add(new DescriptorSupport(ModuleType.EAR, "application", "weblogic-application", "http://java.sun.com/xml/ns/j2ee", "http://www.bea.com/ns/weblogic/90", "META-INF/application.xml", "META-INF/weblogic-application.xml", "weblogic.j2ee.descriptor.ApplicationBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicApplicationBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicApplicationBeanDConfig", true));
         ddSupport.add(EJB_DESC_SUPPORT);
         ddSupport.add(new DescriptorSupport(ModuleType.RAR, "connector", "weblogic-connector", "http://java.sun.com/xml/ns/j2ee", "http://www.bea.com/ns/weblogic/90", "META-INF/ra.xml", "META-INF/weblogic-ra.xml", "weblogic.j2ee.descriptor.ConnectorBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicConnectorBeanDConfig", true));
         ddSupport.add(new DescriptorSupport(ModuleType.CAR, "application-client", "weblogic-application-client", "http://java.sun.com/xml/ns/j2ee", "http://www.bea.com/ns/weblogic/90", "META-INF/application-client.xml", "META-INF/weblogic-application-client.xml", "weblogic.j2ee.descriptor.ApplicationClientBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicApplicationClientBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicApplicationClientBeanDConfig", true));
         ddSupport.add(new DescriptorSupport(WebLogicModuleType.JMS, "weblogic-jms", "weblogic-jms", "http://www.bea.com/ns/weblogic/90", "http://www.bea.com/ns/weblogic/90", ".", ".", "weblogic.j2ee.descriptor.wl.JMSBeanImpl", "weblogic.j2ee.descriptor.wl.JMSBeanImpl", "weblogic.j2ee.descriptor.wl.JMSBeanDConfig", true));
         ddSupport.add(new DescriptorSupport(WebLogicModuleType.JDBC, "jdbc-data-source", "jdbc-data-source", "http://www.bea.com/ns/weblogic/90", "http://www.bea.com/ns/weblogic/90", ".", ".", "weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanImpl", "weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanImpl", "weblogic.j2ee.descriptor.wl.JDBCDataSourceBeanDConfig", true));
         ddSupport.add(new DescriptorSupport(WebLogicModuleType.INTERCEPT, "weblogic-interception", "weblogic-interception", "http://www.bea.com/ns/weblogic/90", "http://www.bea.com/ns/weblogic/90", ".", ".", "weblogic.j2ee.descriptor.wl.InterceptionBeanImpl", "weblogic.j2ee.descriptor.wl.InterceptionBeanImpl", "weblogic.j2ee.descriptor.wl.InterceptionBeanDConfig", true));
         ddSupport.add(new DescriptorSupport(ModuleType.EJB, "weblogic-rdbms-jar", "weblogic-rdbms-jar", "http://www.bea.com/ns/weblogic/90", "http://www.bea.com/ns/weblogic/90", "META-INF/weblogic-cmp-rdbms-jar.xml", "META-INF/weblogic-cmp-rdbms-jar.xml", "weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBeanDConfig", false));
         ddSupport.add(new DescriptorSupport(WebLogicModuleType.WLDF, "wldf-resource", "wldf-resource", "java:weblogic.diagnostics.descriptor", "java:weblogic.diagnostics.descriptor", "META-INF/weblogic-diagnostics.xml", "META-INF/weblogic-diagnostics.xml", "weblogic.diagnostics.descriptor.WLDFResourceBeanImpl", "weblogic.diagnostics.descriptor.WLDFResourceBeanImpl", "weblogic.diagnostics.descriptor.WLDFResourceBeanDConfig", false));
         ddSupport.add(new DescriptorSupport(WebLogicModuleType.WSEE, "webservices", "weblogic-webservices", "http://java.sun.com/xml/ns/j2ee", "http://www.bea.com/ns/weblogic/90", "META-INF/webservices.xml", "META-INF/weblogic-webservices.xml", "weblogic.j2ee.descriptor.WebservicesBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicWebservicesBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicWebservicesBeanDConfig", false));
         ddSupport.add(new DescriptorSupport(WebLogicModuleType.WSEE, "webservices", "weblogic-webservices", "http://java.sun.com/xml/ns/j2ee", "http://www.bea.com/ns/weblogic/90", "WEB-INF/webservices.xml", "WEB-INF/weblogic-webservices.xml", "weblogic.j2ee.descriptor.WebservicesBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicWebservicesBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicWebservicesBeanDConfig", false));
         ddSupport.add(new DescriptorSupport(WebLogicModuleType.WSEE, "web-services", "weblogic-webservices", "http://java.sun.com/xml/ns/j2ee", "http://www.bea.com/ns/weblogic/90", "META-INF/web-services.xml", "META-INF/weblogic-webservices.xml", "weblogic.j2ee.descriptor.WebservicesBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicWebservicesBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicWebservicesBeanDConfig", false));
         ddSupport.add(new DescriptorSupport(WebLogicModuleType.WSEE, "web-services", "weblogic-webservices", "http://java.sun.com/xml/ns/j2ee", "http://www.bea.com/ns/weblogic/90", "WEB-INF/web-services.xml", "WEB-INF/weblogic-webservices.xml", "weblogic.j2ee.descriptor.WebservicesBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicWebservicesBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicWebservicesBeanDConfig", false));
         DescriptorSupport ds = new DescriptorSupport(WebLogicModuleType.WSEE, "webservice-policy-ref", "webservice-policy-ref", "http://java.sun.com/xml/ns/j2ee", "http://www.bea.com/ns/weblogic/90", "META-INF/weblogic-webservices-policy.xml", "META-INF/weblogic-webservices-policy.xml", "weblogic.j2ee.descriptor.wl.WebservicePolicyRefBeanImpl", "weblogic.j2ee.descriptor.wl.WebservicePolicyRefBeanImpl", "weblogic.j2ee.descriptor.wl.WebservicePolicyRefBeanDConfig", false);
         ddSupport.add(ds);
         forceWrites.put("META-INF/weblogic-webservices-policy.xml", ds);
         ds = new DescriptorSupport(WebLogicModuleType.WSEE, "webservice-policy-ref", "webservice-policy-ref", "http://java.sun.com/xml/ns/j2ee", "http://www.bea.com/ns/weblogic/90", "WEB-INF/weblogic-webservices-policy.xml", "WEB-INF/weblogic-webservices-policy.xml", "weblogic.j2ee.descriptor.wl.WebservicePolicyRefBeanImpl", "weblogic.j2ee.descriptor.wl.WebservicePolicyRefBeanImpl", "weblogic.j2ee.descriptor.wl.WebservicePolicyRefBeanDConfig", false);
         ddSupport.add(ds);
         forceWrites.put("WEB-INF/weblogic-webservices-policy.xml", ds);
         ds = new DescriptorSupport(WebLogicModuleType.WAR, "ejb-jar", "weblogic-ejb-jar", "http://java.sun.com/xml/ns/j2ee", "http://www.bea.com/ns/weblogic/90", "WEB-INF/ejb-jar.xml", "WEB-INF/weblogic-ejb-jar.xml", "weblogic.j2ee.descriptor.EjbJarBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig", false);
         ddSupport.add(ds);
         ddSupport.add(new DescriptorSupport(WebLogicModuleType.GAR, "coherence-application", "coherence-application", "http://xmlns.oracle.com/coherence/coherence-application", "http://xmlns.oracle.com/coherence/coherence-application", "META-INF/coherence-application.xml", "META-INF/coherence-application.xml", "weblogic.coherence.app.descriptor.wl.CoherenceApplicationBeanImpl", "weblogic.coherence.app.descriptor.wl.CoherenceApplicationBeanImpl", "weblogic.coherence.app.descriptor.wl.CoherenceApplicationBeanDConfig", true));
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new AssertionError(var2.toString());
      }
   }

   public static DescriptorSupport getForceWriteDS(String uri) {
      return (DescriptorSupport)forceWrites.get(uri);
   }

   public static void registerWebLogicExtensions(WeblogicExtensionBean webExt, String uri) {
      if (webExt != null) {
         CustomModuleBean[] mods = webExt.getCustomModules();
         if (mods != null) {
            try {
               CustomModuleBean[] var3 = mods;
               int var4 = mods.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  CustomModuleBean mod = var3[var5];
                  ConfigurationSupportBean cfg = mod.getConfigurationSupport();
                  if (cfg != null) {
                     String baseTag = cfg.getBaseRootElement();
                     if (baseTag == null && debug) {
                        Debug.say(SPIDeployerLogger.getMissingExt(uri, "base-root-element", mod.getUri(), mod.getProviderName()));
                     }

                     String configTag = cfg.getConfigRootElement();
                     if (configTag == null) {
                        configTag = baseTag;
                     }

                     String baseNameSpace = cfg.getBaseNamespace();
                     if (baseNameSpace == null && debug) {
                        Debug.say(SPIDeployerLogger.getMissingExt(uri, "base-namespace", mod.getUri(), mod.getProviderName()));
                     }

                     String configNameSpace = cfg.getConfigNamespace();
                     if (configNameSpace == null) {
                        configNameSpace = baseNameSpace;
                     }

                     String baseUri = cfg.getBaseUri();
                     if (baseUri == null && debug) {
                        Debug.say(SPIDeployerLogger.getMissingExt(uri, "base-uri", mod.getUri(), mod.getProviderName()));
                     }

                     String configUri = cfg.getConfigUri();
                     if (configUri == null) {
                        configUri = baseUri;
                     }

                     String basePackageName = cfg.getBasePackageName();
                     if (basePackageName == null && debug) {
                        Debug.say(SPIDeployerLogger.getMissingExt(uri, "base-package-name", mod.getUri(), mod.getProviderName()));
                     }

                     String configPackageName = cfg.getConfigPackageName();
                     if (configPackageName == null) {
                        configPackageName = basePackageName;
                     }

                     SPIDeployerLogger.logAddDS(baseUri, configUri);
                     add(WebLogicModuleType.CONFIG, baseTag == null ? "" : baseTag, configTag == null ? "" : configTag, baseNameSpace == null ? "" : baseNameSpace, configNameSpace == null ? "" : configNameSpace, baseUri == null ? "" : baseUri, configUri == null ? "" : configUri, basePackageName == null ? "" : basePackageName, configPackageName == null ? "" : configPackageName);
                  }
               }
            } catch (IllegalArgumentException var16) {
               if (debug) {
                  Debug.say(var16.toString());
               }
            }

         }
      }
   }

   static {
      EJB_DESC_SUPPORT = new DescriptorSupport(ModuleType.EJB, "ejb-jar", "weblogic-ejb-jar", "http://java.sun.com/xml/ns/j2ee", "http://www.bea.com/ns/weblogic/90", "META-INF/ejb-jar.xml", "META-INF/weblogic-ejb-jar.xml", "weblogic.j2ee.descriptor.EjbJarBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanImpl", "weblogic.j2ee.descriptor.wl.WeblogicEjbJarBeanDConfig", true);
      initDescriptorSupport();
   }

   @Service
   public static class DescriptorSupportManagerImpl implements DescriptorSupportManagerInterface {
      public void add(ModuleType module, String baseTag, String configTag, String baseNameSpace, String configNameSpace, String baseURI, String configURI, String stdPackageName, String configPackageName) {
         DescriptorSupportManager.add(module, baseTag, configTag, baseNameSpace, configNameSpace, baseURI, configURI, stdPackageName, configPackageName);
      }

      public DescriptorSupport[] getForSecondaryTag(String baseTag) {
         return DescriptorSupportManager.getForSecondaryTag(baseTag);
      }

      public DescriptorSupport[] getForModuleType(ModuleType module) {
         return DescriptorSupportManager.getForModuleType(module);
      }

      public DescriptorSupport[] getForBaseURI(String uri) {
         return DescriptorSupportManager.getForBaseURI(uri);
      }
   }
}
