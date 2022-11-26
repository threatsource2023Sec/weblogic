package weblogic.management.scripting;

import java.io.IOException;
import java.util.ArrayList;
import javax.management.AttributeNotFoundException;
import javax.management.Descriptor;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import weblogic.descriptor.DescriptorClassLoader;
import weblogic.utils.StringUtils;

public class WLSTPathUtil {
   private static final String JMS_SR = "JMSSystemResources";
   private static final String JDBC_SR = "JDBCSystemResources";
   private static final String WLDF_SR = "WLDFSystemResources";
   private static final String COHERENCE_SR = "CoherenceClusterSystemResources";
   private static final String REALM_MBEAN = "weblogic.management.security.RealmMBean";
   private static final String SECURITY_STORE_MBEAN = "weblogic.management.security.RDBMSSecurityStoreMBean";
   private static final String ULM_MBEAN = "weblogic.management.security.authentication.UserLockoutManagerMBean";
   private static final String PROVIDER_MBEAN = "weblogic.management.security.ProviderMBean";

   public static String lookupPath(MBeanServerConnection connection, String domainName, ObjectName on) throws Exception {
      ObjectName bean = on;
      String name = on.getKeyProperty("Name");
      ObjectName beanForName = null;
      String appName = null;

      String prefix;
      String thePath;
      String suffix;
      try {
         beanForName = (ObjectName)connection.getAttribute(bean, "Parent");
      } catch (AttributeNotFoundException var15) {
         String parent = on.getKeyProperty("Parent");
         if (parent != null) {
            suffix = on.getKeyProperty("Path");
            return generatePathFromParent(parent, suffix);
         }

         ModelMBeanInfo info = (ModelMBeanInfo)connection.getMBeanInfo(on);
         prefix = info.getClassName();
         Class securityMBean = DescriptorClassLoader.loadClass(prefix);
         if (isSecurityMBean(securityMBean)) {
            thePath = "SecurityConfiguration/" + domainName;
            if (isRealm(securityMBean)) {
               thePath = thePath + "/Realms/" + on.getKeyProperty("Name");
               return thePath;
            }

            ObjectName realmON;
            if (isProvider(securityMBean)) {
               realmON = findRealmON(on, connection);
               String type = getAttributeNameFromRealm(realmON, on, connection);
               if (type != null) {
                  thePath = lookupPath(connection, domainName, realmON);
                  thePath = thePath + "/" + type + "/" + (String)connection.getAttribute(on, "Name");
                  return thePath;
               }
            } else if (isUserLockOut(securityMBean)) {
               realmON = findRealmON(on, connection);
               thePath = lookupPath(connection, domainName, realmON);
               thePath = thePath + "/UserLockoutManager/UserLockoutManager";
               return thePath;
            }
         }
      }

      while(name == null && beanForName != null) {
         name = beanForName.getKeyProperty("Name");
         beanForName = (ObjectName)connection.getAttribute(bean, "Parent");
      }

      String typ = getRightType(bean.getKeyProperty("Type"), (ObjectName)connection.getAttribute(bean, "Parent"), connection);
      ObjectName parent = (ObjectName)connection.getAttribute(bean, "Parent");
      if (parent != null) {
         typ = getTheRightAttributeName(connection, parent, typ);
      }

      suffix = typ + "/" + name;
      prefix = "";

      String type;
      while(parent != null && !connection.isInstanceOf(parent, "weblogic.management.configuration.DomainMBean") && !connection.isInstanceOf(parent, "weblogic.management.runtime.DomainRuntimeMBean") && !connection.isInstanceOf(parent, "weblogic.management.runtime.ServerRuntimeMBean")) {
         type = getRightType(parent.getKeyProperty("Type"), (ObjectName)connection.getAttribute(parent, "Parent"), connection);
         if (connection.getAttribute(parent, "Parent") != null) {
            type = getTheRightAttributeName(connection, (ObjectName)connection.getAttribute(parent, "Parent"), type);
         }

         if (type.equals("Application") || type.equals("ComponentRuntimes")) {
            appName = bean.getKeyProperty("Name");
         }

         if (prefix.length() == 0) {
            prefix = type + "/" + parent.getKeyProperty("Name") + prefix;
         } else {
            prefix = type + "/" + parent.getKeyProperty("Name") + "/" + prefix;
         }

         if (type.equals("Application")) {
            appName = parent.getKeyProperty("Name");
         }

         if (connection.getAttribute(parent, "Parent") != null) {
            parent = (ObjectName)connection.getAttribute(parent, "Parent");
         } else {
            parent = bean;
         }
      }

      type = "";
      if (prefix.length() != 0) {
         type = prefix + "/" + suffix;
      } else {
         type = suffix;
      }

      thePath = hackThePath(type, appName);
      return thePath;
   }

   static String generatePathFromParent(String parent, String pathKey) {
      if (parent.indexOf("/") == -1) {
         return null;
      } else {
         String sysResStr = parent.substring(parent.indexOf("/"), parent.length());
         int fromIndex = sysResStr.indexOf("/");
         String entityType = null;
         String entityName = null;

         StringBuffer wlstPath;
         int endIndex;
         for(wlstPath = new StringBuffer(); fromIndex < sysResStr.length(); fromIndex = endIndex + 1) {
            int beginIndex = sysResStr.indexOf("[", fromIndex);
            endIndex = 0;
            if (beginIndex != -1) {
               endIndex = sysResStr.indexOf("]", fromIndex);
            }

            if (beginIndex == -1 || endIndex == -1) {
               wlstPath.append(sysResStr.substring(fromIndex));
               break;
            }

            entityType = sysResStr.substring(fromIndex + 1, beginIndex);
            entityName = sysResStr.substring(beginIndex + 1, endIndex);
            if (isSystemResource(entityType)) {
               break;
            }

            if (wlstPath.length() != 0) {
               wlstPath.append("/");
            }

            wlstPath.append(entityType);
            wlstPath.append("/");
            wlstPath.append(entityName);
         }

         return isSystemResource(entityType) ? getSystemResourcePath(pathKey, entityType, entityName, wlstPath.toString()) : null;
      }
   }

   private static boolean isSystemResource(String entityType) {
      return entityType != null && (entityType.equals("JMSSystemResources") || entityType.equals("JDBCSystemResources") || entityType.equals("WLDFSystemResources") || entityType.equals("CoherenceClusterSystemResources"));
   }

   private static String getTheRightAttributeName(MBeanServerConnection conn, ObjectName parent, String attrType) throws Exception {
      String retType = getTheRightAttributeName(conn, parent, attrType, true);
      return retType != null ? retType : getTheRightAttributeName(conn, parent, attrType, false);
   }

   private static String getTheRightAttributeName(MBeanServerConnection conn, ObjectName parent, String attrType, boolean exactMatch) throws Exception {
      ModelMBeanInfo modelInfo = (ModelMBeanInfo)conn.getMBeanInfo(parent);
      MBeanAttributeInfo[] attrInfo = modelInfo.getAttributes();
      String retType = "";
      if (attrType.endsWith("Runtime")) {
         retType = "weblogic.management.runtime." + attrType + "MBean";
      } else {
         retType = "weblogic.management.configuration." + attrType + "MBean";
      }

      for(int i = 0; i < attrInfo.length; ++i) {
         Descriptor desc = ((ModelMBeanAttributeInfo)attrInfo[i]).getDescriptor();
         String intClzName = (String)((ModelMBeanAttributeInfo)attrInfo[i]).getDescriptor().getFieldValue("interfaceClassName");
         if (intClzName != null) {
            if (intClzName.startsWith("[L")) {
               intClzName = intClzName.substring(2, intClzName.length() - 1);
            }

            if (retType.equals(intClzName)) {
               return attrInfo[i].getName();
            }

            if (intClzName.endsWith("MBean") && !exactMatch && Class.forName(intClzName).isAssignableFrom(Class.forName(retType)) && !attrInfo[i].getName().equals("Parent") && !attrInfo[i].getName().equals("Targets")) {
               return attrInfo[i].getName();
            }
         }
      }

      if (exactMatch) {
         return null;
      } else {
         return attrType;
      }
   }

   private static String getSystemResourcePath(String path, String sysResourceType, String sysModuleName, String preWlstPath) {
      int fromIndex = 0;
      int endIndex = 0;
      StringBuffer fixedPath = new StringBuffer();
      ArrayList entityNames = new ArrayList();
      String resourceName;
      if (path != null) {
         while(fromIndex < path.length()) {
            int beginIndex = path.indexOf("[", fromIndex);
            if (beginIndex != -1) {
               endIndex = path.indexOf("]", beginIndex);
            }

            if (beginIndex == -1 || endIndex == -1) {
               fixedPath.append(path.substring(fromIndex));
               break;
            }

            resourceName = path.substring(beginIndex + 1, endIndex);
            entityNames.add(resourceName);
            fixedPath.append(path.substring(fromIndex, beginIndex));
            fixedPath.append("[]");
            fromIndex = endIndex + 1;
         }
      }

      StringBuffer wlstPath = new StringBuffer();
      if (preWlstPath != null && !preWlstPath.isEmpty()) {
         wlstPath.append(preWlstPath);
         wlstPath.append("/");
      }

      wlstPath.append(sysResourceType);
      wlstPath.append("/");
      wlstPath.append(sysModuleName);
      resourceName = sysModuleName;
      String[] paths = StringUtils.splitCompletely(fixedPath.toString(), "/");

      for(int i = 0; i < paths.length; ++i) {
         wlstPath.append("/");
         int index = paths[i].indexOf("[]");
         if (index != -1) {
            resourceName = (String)entityNames.remove(0);
            wlstPath.append(paths[i].substring(0, index));
         } else {
            wlstPath.append(paths[i]);
         }

         wlstPath.append("/");
         wlstPath.append(resourceName);
      }

      return wlstPath.toString();
   }

   private static String getAttributeNameFromRealm(ObjectName realm, ObjectName prov, MBeanServerConnection mbs) throws Exception {
      MBeanAttributeInfo[] info = mbs.getMBeanInfo(realm).getAttributes();

      for(int i = 0; i < info.length; ++i) {
         MBeanAttributeInfo ainfo = info[i];
         if (ainfo.getType().equals("javax.management.ObjectName")) {
            ObjectName _on = (ObjectName)mbs.getAttribute(realm, ainfo.getName());
            if (_on != null && _on.toString().equals(prov.toString())) {
               return ainfo.getName();
            }
         } else if (ainfo.getType().equals("[Ljavax.management.ObjectName;")) {
            ObjectName[] _on = (ObjectName[])((ObjectName[])mbs.getAttribute(realm, ainfo.getName()));
            if (_on != null && _on.length > 0) {
               for(int j = 0; j < _on.length; ++j) {
                  if (_on[j].toString().equals(prov.toString())) {
                     return ainfo.getName();
                  }
               }
            }
         }
      }

      return null;
   }

   private static ObjectName findRealmON(ObjectName oname, MBeanServerConnection mbs) throws MBeanException, AttributeNotFoundException, ReflectionException, InstanceNotFoundException, IOException {
      return (ObjectName)mbs.getAttribute(oname, "Realm");
   }

   private static String hackThePath(String origPath, String appName) {
      String newPath;
      if (!origPath.startsWith("/Application/") && !origPath.startsWith("Application/")) {
         if (origPath.indexOf("/EJBTransactionRuntime/") != -1) {
            newPath = StringUtils.replaceGlobal(origPath, "/EJBTransactionRuntime/", "/EJBRuntimes/" + appName + "/TransactionRuntime/");
            return newPath;
         } else if (origPath.indexOf("/EJBCacheRuntime/") != -1) {
            newPath = StringUtils.replaceGlobal(origPath, "/EJBCacheRuntime/", "/EJBRuntimes/" + appName + "/CacheRuntime/");
            return newPath;
         } else if (origPath.indexOf("/EJBLockingRuntime/") != -1) {
            newPath = StringUtils.replaceGlobal(origPath, "/EJBLockingRuntime/", "/EJBRuntimes/" + appName + "/LockingRuntime/");
            return newPath;
         } else if (origPath.indexOf("/EJBPoolRuntime/") != -1) {
            newPath = StringUtils.replaceGlobal(origPath, "/EJBPoolRuntime/", "/EJBRuntimes/" + appName + "/PoolRuntime/");
            return newPath;
         } else {
            return origPath;
         }
      } else {
         newPath = origPath.replaceFirst("Application/" + appName, "AppDeployments/" + appName + "/AppMBean/" + appName);
         return newPath;
      }
   }

   private static String getRightType(String type, ObjectName parent, MBeanServerConnection connection) {
      if (parent == null) {
         return type + "s";
      } else {
         try {
            MBeanAttributeInfo[] info = connection.getMBeanInfo(parent).getAttributes();

            for(int i = 0; i < info.length; ++i) {
               MBeanAttributeInfo attrInfo = info[i];
               String attrName = attrInfo.getName();
               String attrType = attrInfo.getType();
               if (attrInfo.getName().startsWith(type)) {
                  if (attrInfo.getType().indexOf("MBean") != -1) {
                     return attrInfo.getName();
                  }
               } else {
                  String typeFromAttrInfo = attrInfo.getType();
                  String fullType;
                  Class typeClass;
                  if (typeFromAttrInfo.startsWith("[L") && typeFromAttrInfo.indexOf("MBean") != -1) {
                     typeFromAttrInfo = typeFromAttrInfo.substring(2, typeFromAttrInfo.length() - 1);
                     typeClass = Class.forName(typeFromAttrInfo);
                     if (type.endsWith("Runtime")) {
                        fullType = "weblogic.management.runtime." + type + "MBean";
                     } else {
                        fullType = "weblogic.management.configuration." + type + "MBean";
                     }

                     if (typeClass.isAssignableFrom(Class.forName(fullType))) {
                        return attrInfo.getName();
                     }
                  } else if (attrInfo.getType().indexOf("MBean") != -1 && !attrInfo.getType().equals("weblogic.management.WebLogicMBean")) {
                     typeClass = Class.forName(typeFromAttrInfo);
                     if (type.endsWith("Runtime")) {
                        fullType = "weblogic.management.runtime." + type + "MBean";
                     } else {
                        fullType = "weblogic.management.configuration." + type + "MBean";
                     }

                     if (typeClass.isAssignableFrom(Class.forName(fullType))) {
                        return attrInfo.getName();
                     }
                  }
               }
            }

            return type;
         } catch (Throwable var11) {
            return type;
         }
      }
   }

   private static boolean isSecurityMBean(Class securityMBean) throws ClassNotFoundException {
      return Class.forName("weblogic.management.security.RealmMBean").isAssignableFrom(securityMBean) || Class.forName("weblogic.management.security.ProviderMBean").isAssignableFrom(securityMBean) || Class.forName("weblogic.management.security.authentication.UserLockoutManagerMBean").isAssignableFrom(securityMBean) || Class.forName("weblogic.management.security.RDBMSSecurityStoreMBean").isAssignableFrom(securityMBean);
   }

   private static boolean isRealm(Class securityMBean) throws ClassNotFoundException {
      return Class.forName("weblogic.management.security.RealmMBean").isAssignableFrom(securityMBean);
   }

   private static boolean isProvider(Class securityMBean) throws ClassNotFoundException {
      return Class.forName("weblogic.management.security.ProviderMBean").isAssignableFrom(securityMBean);
   }

   private static boolean isUserLockOut(Class securityMBean) throws ClassNotFoundException {
      return Class.forName("weblogic.management.security.authentication.UserLockoutManagerMBean").isAssignableFrom(securityMBean);
   }
}
