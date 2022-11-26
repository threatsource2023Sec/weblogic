package weblogic.deploy.api.spi.config;

import javax.enterprise.deploy.shared.ModuleType;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface DescriptorSupportManagerInterface {
   String WEB_ROOT = "web-app";
   String WLS_WEB_ROOT = "weblogic-web-app";
   String WEB_NAMESPACE = "http://java.sun.com/xml/ns/j2ee";
   String WLS_WEB_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   String WEB_URI = "WEB-INF/web.xml";
   String WLS_WEB_URI = "WEB-INF/weblogic.xml";
   String EAR_ROOT = "application";
   String WLS_EAR_ROOT = "weblogic-application";
   String EAR_NAMESPACE = "http://java.sun.com/xml/ns/j2ee";
   String WLS_EAR_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   String EAR_URI = "META-INF/application.xml";
   String WLS_EAR_URI = "META-INF/weblogic-application.xml";
   String EJB_ROOT = "ejb-jar";
   String WLS_EJB_ROOT = "weblogic-ejb-jar";
   String EJB_NAMESPACE = "http://java.sun.com/xml/ns/j2ee";
   String WLS_EJB_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   String EJB_URI = "META-INF/ejb-jar.xml";
   String WLS_EJB_URI = "META-INF/weblogic-ejb-jar.xml";
   String EJB_IN_WAR_ROOT = "ejb-jar";
   String WLS_EJB_IN_WAR_ROOT = "weblogic-ejb-jar";
   String EJB_IN_WAR_NAMESPACE = "http://java.sun.com/xml/ns/j2ee";
   String WLS_EJB_IN_WAR_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   String EJB_IN_WAR_URI = "WEB-INF/ejb-jar.xml";
   String WLS_EJB_IN_WAR_URI = "WEB-INF/weblogic-ejb-jar.xml";
   String RAR_ROOT = "connector";
   String WLS_RAR_ROOT = "weblogic-connector";
   String RAR_NAMESPACE = "http://java.sun.com/xml/ns/j2ee";
   String WLS_RAR_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   String RAR_URI = "META-INF/ra.xml";
   String WLS_RAR_URI = "META-INF/weblogic-ra.xml";
   String CAR_ROOT = "application-client";
   String WLS_CAR_ROOT = "weblogic-application-client";
   String CAR_NAMESPACE = "http://java.sun.com/xml/ns/j2ee";
   String WLS_CAR_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   String CAR_URI = "META-INF/application-client.xml";
   String WLS_CAR_URI = "META-INF/weblogic-application-client.xml";
   String WLS_CMP_ROOT = "weblogic-rdbms-jar";
   String WLS_CMP_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   String WLS_CMP_URI = "META-INF/weblogic-cmp-rdbms-jar.xml";
   String WLS_CMP11_ROOT = "weblogic-rdbms-jar";
   String WLS_CMP11_NAMESPACE = "http://www.bea.com/ns/weblogic/60";
   String WLS_CMP11_URI = "META-INF/weblogic-cmp-rdbms-jar.xml";
   String WLS_CMP11_CLASS = "weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanImpl";
   String WLS_CMP11_DCONFIGCLASS = "weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanDConfig";
   String WLS_CMP11_CONFIGCLASS = "weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBeanImpl";
   String WLS_JMS_ROOT = "weblogic-jms";
   String WLS_JMS_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   String WLS_JDBC_ROOT = "jdbc-data-source";
   String WLS_JDBC_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   String WLS_INTERCEPT_ROOT = "weblogic-interception";
   String WLS_INTERCEPT_NAMESPACE = "http://www.bea.com/ns/weblogic/90";
   String WSEE_ROOT_81 = "web-services";
   String WSEE_ROOT = "webservices";
   String WLS_WSEE_ROOT = "weblogic-webservices";
   String WSEE_WEB_URI_81 = "WEB-INF/web-services.xml";
   String WSEE_EJB_URI_81 = "META-INF/web-services.xml";
   String WSEE_WEB_URI = "WEB-INF/webservices.xml";
   String WSEE_EJB_URI = "META-INF/webservices.xml";
   String WLS_WSEE_WEB_URI = "WEB-INF/weblogic-webservices.xml";
   String WLS_WSEE_EJB_URI = "META-INF/weblogic-webservices.xml";
   String WLS_WS_POLICY_WEB_URI = "WEB-INF/weblogic-webservices-policy.xml";
   String WLS_WS_POLICY_EJB_URI = "META-INF/weblogic-webservices-policy.xml";
   String REST_WEBSERVICES_BEAN_DESCRIPTOR_URI = "weblogic.j2ee.descriptor.wl.RestWebservicesBean";
   String PERSISTENCE_ROOT = "persistence";
   String WLS_PERSISTENCE_ROOT = "persistence-configuration";
   String PERSISTENCE_URI = "META-INF/persistence.xml";
   String WLS_PERSISTENCE_URI = "META-INF/persistence-configuration.xml";
   String PERSISTENCE_NAMESPACE = "http://java.sun.com/xml/ns/persistence";
   String WLS_PERSISTENCE_NAMESPACE = "http://bea.com/ns/weblogic/950/persistence";
   String PERSISTENCE_BEAN_CLASS = "weblogic.j2ee.descriptor.PersistenceBean";
   String WLS_PERSISTENCE_BEAN_CLASS = "kodo.jdbc.conf.descriptor.PersistenceConfigurationBean";
   String WLS_PERSISTENCE_DCONFIG_CLASS = "kodo.jdbc.conf.descriptor.PersistenceConfigurationBeanDConfig";
   String WLS_WLDF_ROOT = "wldf-resource";
   String WLS_WLDF_NAMESPACE = "java:weblogic.diagnostics.descriptor";
   String WLS_WLDF_URI = "META-INF/weblogic-diagnostics.xml";
   String WLS_WLDF_BEAN_CLASS = "weblogic.diagnostics.descriptor.WLDFResourceBeanImpl";
   String WLS_WLDF_DCONFIG_CLASS = "weblogic.diagnostics.descriptor.WLDFResourceBeanDConfig";
   String WLS_GAR_ROOT = "coherence-application";
   String WLS_GAR_NAMESPACE = "http://xmlns.oracle.com/coherence/coherence-application";
   String WLS_GAR_URI = "META-INF/coherence-application.xml";
   String WLS_EAR_EXT_URI = "META-INF/weblogic-extension.xml";
   String WLS_WEB_EXT_URI = "WEB-INF/weblogic-extension.xml";

   void add(ModuleType var1, String var2, String var3, String var4, String var5, String var6, String var7, String var8, String var9);

   DescriptorSupport[] getForSecondaryTag(String var1);

   DescriptorSupport[] getForModuleType(ModuleType var1);

   DescriptorSupport[] getForBaseURI(String var1);
}
