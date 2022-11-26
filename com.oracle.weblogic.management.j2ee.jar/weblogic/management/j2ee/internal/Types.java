package weblogic.management.j2ee.internal;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class Types {
   private static final Map weblogicTypeToJ2EEType = new HashMap(29);
   public static final String WLS_DOMAIN_TYPE = "DomainRuntime";
   public static final String WLS_SERVER_TYPE = "ServerRuntime";
   public static final String WLS_APPLICATION_TYPE = "ApplicationRuntime";
   public static final String WLS_EJBMODULE_TYPE = "EJBComponentRuntime";
   public static final String WLS_WEBMODULE_TYPE = "WebAppComponentRuntime";
   public static final String WLS_RESOURCEADAPTORMODULE_TYPE = "ConnectorComponentRuntime";
   public static final String WLS_ENTITYBEAN_TYPE = "EntityEJBRuntime";
   public static final String WLS_STATEFULLSESSIONBEAN_TYPE = "StatefulEJBRuntime";
   public static final String WLS_STATELESSSESSIONBEAN_TYPE = "StatelessEJBRuntime";
   public static final String WLS_MESSAGEDRIVENBEAN_TYPE = "MessageDrivenEJBRuntime";
   public static final String WLS_SERVLET_TYPE = "ServletRuntime";
   public static final String WLS_JAVAMAILRESOURCE_TYPE = "JavaMailRuntime";
   public static final String WLS_JCACONNECTIONFACTORY_TYPE = "ConnectorConnectionPoolRuntime";
   public static final String WLS_JDBCRESOURCE_TYPE = "JDBCServiceRuntime";
   public static final String WLS_JDBCDATASOURCE_TYPE = "JDBCDataSourceRuntime";
   public static final String WLS_JDBCDRIVER_TYPE = "JDBCDriverRuntime";
   public static final String WLS_JNDIRESOURCE_TYPE = "JNDIResourceRuntime";
   public static final String WLS_RMI_IIOPRESOURCE_TYPE = "RMI_IIOPResourceRuntime";
   public static final String WLS_URLRESOURCE_TYPE = "URLResourceRuntime";
   public static final String WLS_APPCLIENTMODULE_TYPE = "AppClientModuleRuntime";
   public static final String WLS_JMSRESOURCE_TYPE = "JMSRuntime";
   public static final String WLS_JTARESOURCE_TYPE = "JTARuntime";
   public static final String WLS_JVM_TYPE = "JVMRuntime";
   public static final String WLS_JVM_JROCKIT_TYPE = "JRockitRuntime";
   public static final String J2EE_DOMAIN_TYPE = "J2EEDomain";
   public static final String J2EE_SERVER_TYPE = "J2EEServer";
   public static final String J2EE_APPLICATION_TYPE = "J2EEApplication";
   public static final String J2EE_APPCLIENTMODULE_TYPE = "AppClientModule";
   public static final String J2EE_EJBMODULE_TYPE = "EJBModule";
   public static final String J2EE_WEBMODULE_TYPE = "WebModule";
   public static final String J2EE_RESOURCEADAPTORMODULE_TYPE = "ResourceAdapterModule";
   public static final String J2EE_ENTITYBEAN_TYPE = "EntityBean";
   public static final String J2EE_STATEFULLSESSIONBEAN_TYPE = "StatefulSessionBean";
   public static final String J2EE_STATELESSSESSIONBEAN_TYPE = "StatelessSessionBean";
   public static final String J2EE_MESSAGEDRIVENBEAN_TYPE = "MessageDrivenBean";
   public static final String J2EE_SERVLET_TYPE = "Servlet";
   public static final String J2EE_RESOURCEADAPTER_TYPE = "ResourceAdapter";
   public static final String J2EE_JAVAMAILRESOURCE_TYPE = "JavaMailResource";
   public static final String J2EE_JCARESOURCE_TYPE = "JCAResource";
   public static final String J2EE_JCACONNECTIONFACTORY_TYPE = "JCAConnectionFactory";
   public static final String J2EE_JCAMANAGEDCONNECTIONFACTORY_TYPE = "JCAManagedConnectionFactory";
   public static final String J2EE_JDBCRESOURCE_TYPE = "JDBCResource";
   public static final String J2EE_JDBCDATASOURCE_TYPE = "JDBCDataSource";
   public static final String J2EE_JDBCDRIVER_TYPE = "JDBCDriver";
   public static final String J2EE_JMSRESOURCE_TYPE = "JMSResource";
   public static final String J2EE_JNDIRESOURCE_TYPE = "JNDIResource";
   public static final String J2EE_JTARESOURCE_TYPE = "JTAResource";
   public static final String J2EE_RMI_IIOPRESOURCE_TYPE = "RMI_IIOPResource";
   public static final String J2EE_URLRESOURCE_TYPE = "URLResource";
   public static final String J2EE_JVM_TYPE = "JVM";

   static boolean isValidWLSType(String type) {
      return weblogicTypeToJ2EEType.containsKey(type);
   }

   static String getJ2EETypeForWLSType(String type) {
      return (String)weblogicTypeToJ2EEType.get(type);
   }

   static String getWLSTypeForJ2EEType(String type) {
      Set keys = weblogicTypeToJ2EEType.keySet();
      Iterator it = keys.iterator();

      String wlsType;
      String j2eeType;
      do {
         if (!it.hasNext()) {
            return null;
         }

         wlsType = (String)it.next();
         j2eeType = (String)weblogicTypeToJ2EEType.get(wlsType);
      } while(!j2eeType.equals(type));

      return wlsType;
   }

   static {
      weblogicTypeToJ2EEType.put("DomainRuntime", "J2EEDomain");
      weblogicTypeToJ2EEType.put("ServerRuntime", "J2EEServer");
      weblogicTypeToJ2EEType.put("ApplicationRuntime", "J2EEApplication");
      weblogicTypeToJ2EEType.put("AppClientModuleRuntime", "AppClientModule");
      weblogicTypeToJ2EEType.put("EJBComponentRuntime", "EJBModule");
      weblogicTypeToJ2EEType.put("WebAppComponentRuntime", "WebModule");
      weblogicTypeToJ2EEType.put("ConnectorComponentRuntime", "ResourceAdapterModule");
      weblogicTypeToJ2EEType.put("EntityEJBRuntime", "EntityBean");
      weblogicTypeToJ2EEType.put("StatefulEJBRuntime", "StatefulSessionBean");
      weblogicTypeToJ2EEType.put("StatelessEJBRuntime", "StatelessSessionBean");
      weblogicTypeToJ2EEType.put("MessageDrivenEJBRuntime", "MessageDrivenBean");
      weblogicTypeToJ2EEType.put("ServletRuntime", "Servlet");
      weblogicTypeToJ2EEType.put("JavaMailRuntime", "JavaMailResource");
      weblogicTypeToJ2EEType.put("ConnectorConnectionPoolRuntime", "JCAConnectionFactory");
      weblogicTypeToJ2EEType.put("JDBCServiceRuntime", "JDBCResource");
      weblogicTypeToJ2EEType.put("JDBCDataSourceRuntime", "JDBCDataSource");
      weblogicTypeToJ2EEType.put("JDBCDriverRuntime", "JDBCDriver");
      weblogicTypeToJ2EEType.put("JMSRuntime", "JMSResource");
      weblogicTypeToJ2EEType.put("JNDIResourceRuntime", "JNDIResource");
      weblogicTypeToJ2EEType.put("JTARuntime", "JTAResource");
      weblogicTypeToJ2EEType.put("RMI_IIOPResourceRuntime", "RMI_IIOPResource");
      weblogicTypeToJ2EEType.put("URLResourceRuntime", "URLResource");
      weblogicTypeToJ2EEType.put("JVMRuntime", "JVM");
      weblogicTypeToJ2EEType.put("JRockitRuntime", "JVM");
   }
}
