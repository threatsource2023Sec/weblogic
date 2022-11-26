package weblogic.management.j2ee.internal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

class JMOTypesHelper {
   public static final int J2EEDOMAIN = 0;
   public static final int J2EESERVER = 1;
   public static final int J2EEAPPLICATION = 2;
   public static final int APPCLIENTMODULE = 3;
   public static final int EJBMODULE = 4;
   public static final int WEBMODULE = 5;
   public static final int RESOURCEADAPTORMODULE = 6;
   public static final int ENTITYBEAN = 7;
   public static final int STATEFULSESSIONBEAN = 8;
   public static final int STATELESSSESSIONBEAN = 9;
   public static final int MESSAGEDRIVENBEAN = 10;
   public static final int SERVLET = 11;
   public static final int JAVAMAILRESOURCE = 12;
   public static final int JCACONNECTIONFACTORY = 13;
   public static final int JDBCRESOURCE = 14;
   public static final int JDBCDATASOURCE = 15;
   public static final int JDBCDRIVER = 16;
   public static final int JMSRESOURCE = 17;
   public static final int JTARESOURCE = 18;
   public static final int URLRESOURCE = 19;
   public static final int JVM = 20;
   private static Map typesToParents = null;
   public static final String TYPE = "j2eeType";
   private static List typeList;
   static List resourceList;
   static List moduleList;
   static List ejbList;

   static void validateParents(ObjectName name) throws InvalidObjectNameException {
      String type = name.getKeyProperty("j2eeType");
      if (!typesToParents.containsKey(type)) {
         throw new InvalidObjectNameException("Object is not valid J2EE Type");
      } else if (!type.equals("J2EEDomain") && !type.equals("J2EEServer")) {
         String[] validParents = (String[])((String[])typesToParents.get(type));
         validateParents(name, validParents);
      }
   }

   private static void validateParents(ObjectName name, String[] validParents) throws InvalidObjectNameException {
      Hashtable table = name.getKeyPropertyList();

      for(int i = 0; i < validParents.length; ++i) {
         String parent = validParents[i];
         if (!table.containsKey(parent)) {
            throw new InvalidObjectNameException("Object Name does not have " + parent + "as a prent");
         }

         if (table.get(parent) == null) {
            throw new InvalidObjectNameException("Object Name does not have   " + parent + "as a parent");
         }
      }

   }

   static String[] getParents(String type) {
      return (String[])((String[])typesToParents.get(type));
   }

   static int getTypeIndex(String type) {
      return typeList.indexOf(type);
   }

   public static String[] getParentsForType(String type) {
      return (String[])((String[])typesToParents.get(type));
   }

   public static String getKeyValue(String sObjectName, String key) {
      try {
         return (new ObjectName(sObjectName)).getKeyProperty(key);
      } catch (MalformedObjectNameException var3) {
         throw new AssertionError("MalformedObjectName" + var3);
      }
   }

   static {
      typesToParents = new HashMap(29);
      typesToParents.put("J2EEDomain", new String[0]);
      typesToParents.put("J2EEServer", new String[0]);
      typesToParents.put("J2EEApplication", new String[]{"J2EEServer"});
      typesToParents.put("AppClientModule", new String[]{"J2EEApplication", "J2EEServer"});
      typesToParents.put("EJBModule", new String[]{"J2EEApplication", "J2EEServer"});
      typesToParents.put("WebModule", new String[]{"J2EEApplication", "J2EEServer"});
      typesToParents.put("ResourceAdapterModule", new String[]{"J2EEApplication", "J2EEServer"});
      typesToParents.put("EntityBean", new String[]{"EJBModule", "J2EEApplication", "J2EEServer"});
      typesToParents.put("StatefulSessionBean", new String[]{"EJBModule", "J2EEApplication", "J2EEServer"});
      typesToParents.put("StatelessSessionBean", new String[]{"EJBModule", "J2EEApplication", "J2EEServer"});
      typesToParents.put("MessageDrivenBean", new String[]{"EJBModule", "J2EEApplication", "J2EEServer"});
      typesToParents.put("Servlet", new String[]{"WebModule", "J2EEApplication", "J2EEServer"});
      typesToParents.put("ResourceAdapter", new String[]{"ResourceAdapterModule", "J2EEApplication", "J2EEServer"});
      typesToParents.put("JavaMailResource", new String[]{"J2EEServer"});
      typesToParents.put("JCAResource", new String[]{"ResourceAdapter", "J2EEServer"});
      typesToParents.put("JCAConnectionFactory", new String[]{"JCAResource", "J2EEServer"});
      typesToParents.put("JCAManagedConnectionFactory", new String[]{"J2EEServer"});
      typesToParents.put("JDBCResource", new String[]{"J2EEServer"});
      typesToParents.put("JDBCDataSource", new String[]{"JDBCResource", "J2EEServer"});
      typesToParents.put("JDBCDriver", new String[]{"J2EEServer"});
      typesToParents.put("JMSResource", new String[]{"J2EEServer"});
      typesToParents.put("JNDIResource", new String[]{"J2EEServer"});
      typesToParents.put("JTAResource", new String[]{"J2EEServer"});
      typesToParents.put("RMI_IIOPResource", new String[]{"J2EEServer"});
      typesToParents.put("URL", new String[]{"J2EEServer"});
      typesToParents.put("JVM", new String[]{"J2EEServer"});
      typeList = Arrays.asList((Object[])(new String[]{"J2EEDomain", "J2EEServer", "J2EEApplication", "AppClientModule", "EJBModule", "WebModule", "ResourceAdapterModule", "EntityBean", "StatefulSessionBean", "StatelessSessionBean", "MessageDrivenBean", "Servlet", "JavaMailResource", "JCAConnectionFactory", "JDBCResource", "JDBCDataSource", "JDBCDriver", "JMSResource", "JTAResource", "URLResource", "JVM"}));
      resourceList = Arrays.asList((Object[])(new String[]{"JavaMailResource", "JCAResource", "JCAConnectionFactory", "JCAManagedConnectionFactory", "JDBCResource", "JDBCDataSource", "JDBCDriver", "JMSResource", "JNDIResource", "JTAResource", "RMI_IIOPResource", "URLResource", "JVM"}));
      moduleList = Arrays.asList((Object[])(new String[]{"AppClientModule", "EJBModule", "WebModule", "ResourceAdapterModule"}));
      ejbList = Arrays.asList((Object[])(new String[]{"EntityBean", "StatefulSessionBean", "StatelessSessionBean", "MessageDrivenBean"}));
   }
}
