package weblogic.management.visibility.utils;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class MBeanNameUtil {
   public static final String PARTITION_KEY = "Partition";
   public static final String GLOBAL_PARTITION_NAME = "DOMAIN";
   public static final String REALM_RUNTIME_KEY = "RealmRuntime";
   private static String[] deploymentRuntimeMBeanTypes = new String[]{"AppDeploymentRuntime", "LibDeploymentRuntime", "DeploymentProgressObject"};
   private static String[] jdkDomains = new String[]{"JMImplementation", "com.sun.management", "java.lang", "java.nio", "java.util.logging"};

   public static boolean isJDKMBean(ObjectName objectName) {
      return objectName != null && Arrays.asList(jdkDomains).contains(objectName.getDomain());
   }

   public static boolean isGlobalMBean(ObjectName oname) {
      if (isWLSMBean(oname)) {
         if (oname.getKeyProperty("Partition") != null || oname.getKeyProperty("PartitionRuntime") != null || oname.getKeyProperty("DomainPartitionRuntime") != null || getPartitionNameFromParentKey(oname) != null) {
            return false;
         }
      } else if (oname.getKeyProperty("Partition") != null || oname.getKeyProperty("domainPartition") != null) {
         return false;
      }

      return true;
   }

   public static boolean isWLSMBean(ObjectName objectName) {
      return objectName != null && "com.bea".equals(objectName.getDomain());
   }

   public static String getPartitionNameFromParentKey(ObjectName objectName) {
      String parentKey = objectName.getKeyProperty("Parent");
      if (parentKey == null) {
         return null;
      } else {
         String partitionKey = findPartitionNameFromParentKey(parentKey, "Partition");
         if (partitionKey != null) {
            return partitionKey;
         } else {
            partitionKey = findPartitionNameFromParentKey(parentKey, "PartitionRuntime");
            return partitionKey != null ? partitionKey : findPartitionNameFromParentKey(parentKey, "DomainPartitionRuntime");
         }
      }
   }

   private static String findPartitionNameFromParentKey(String parentKey, String partitionIdentificationName) {
      String regexPrefix = ".+\\/";
      String regexSuffix = "s\\[(.*?)\\]";
      Pattern pattern = Pattern.compile(regexPrefix + partitionIdentificationName + regexSuffix);
      Matcher matcher = pattern.matcher(parentKey);
      return matcher.find() ? matcher.group(1) : null;
   }

   public static boolean containsDecorator(ObjectName objectName) {
      return objectName.getKeyPropertyList().containsKey("Partition") || objectName.getKeyPropertyList().containsKey("PartitionRuntime") || objectName.getKeyPropertyList().containsKey("DomainPartitionRuntime") || objectName.getKeyPropertyList().containsKey("domainPartition");
   }

   public static boolean isWLSMBeanInSamePartition(String partitionName, ObjectName oname) {
      return oname.getDomain().equals("com.bea") && oname.getKeyProperty("Partition") != null && oname.getKeyProperty("Partition").equals(partitionName) || oname.getKeyProperty("PartitionRuntime") != null && oname.getKeyProperty("PartitionRuntime").equals(partitionName) || oname.getKeyProperty("DomainPartitionRuntime") != null && oname.getKeyProperty("DomainPartitionRuntime").equals(partitionName);
   }

   public static boolean isMBeanInSamePartition(String partitionName, ObjectName oname) {
      return oname.getKeyProperty("Partition") != null && oname.getKeyProperty("Partition").equals(partitionName) || oname.getKeyProperty("PartitionRuntime") != null && oname.getKeyProperty("PartitionRuntime").equals(partitionName) || oname.getKeyProperty("DomainPartitionRuntime") != null && oname.getKeyProperty("DomainPartitionRuntime").equals(partitionName);
   }

   public static boolean isCoherenceMBean(ObjectName objectName) {
      return objectName != null && (objectName.getKeyPropertyList().containsKey("domainPartition") || "Coherence".equals(objectName.getDomain()));
   }

   public static boolean isCoherenceMBeanInSamePartition(String partitionName, ObjectName oname) {
      return oname.getKeyProperty("domainPartition") != null && oname.getKeyProperty("domainPartition").equals(partitionName);
   }

   public static boolean isResourceGroupMBean(ObjectName oname) {
      if (isGlobalMBean(oname)) {
         String type = oname.getKeyProperty("Type");
         if (type != null && type.equals("ResourceGroup")) {
            return true;
         }
      }

      return false;
   }

   public static boolean isResourceGroupMBeanInSamePartition(ObjectName oname, String[] rgNames) {
      if (isResourceGroupMBean(oname)) {
         String name = oname.getKeyProperty("Name");
         if (name != null && rgNames != null) {
            String[] var3 = rgNames;
            int var4 = rgNames.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               String rgName = var3[var5];
               if (name.equals(rgName)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public static boolean isWLSDeploymentRuntime(ObjectName oname) {
      if (isGlobalMBean(oname)) {
         String type = oname.getKeyProperty("Type");
         String[] var2 = deploymentRuntimeMBeanTypes;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String deploymentRuntimeMBean = var2[var4];
            if (type != null && type.equals(deploymentRuntimeMBean)) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean isPartitionOwnedWLSDeploymentMBeanInSamePartition(String partitionName, ObjectName oname) {
      if (isWLSDeploymentRuntime(oname)) {
         String name = oname.getKeyProperty("Name");
         if (name != null) {
            int index = name.indexOf("$");
            if (index != -1 && name.substring(index, name.length()).equals(partitionName)) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean isWLSPartitionConfigurationMBean(ObjectName oname) {
      String[] partitionTypes = new String[]{"Partition", "PartitionRuntime", "DomainPartitionRuntime", "PartitionLifeCycleRuntime", "ResourceGroupLifeCycleRuntime"};
      if (oname.getDomain().equals("com.bea")) {
         String type = oname.getKeyProperty("Type");
         String[] var3 = partitionTypes;
         int var4 = partitionTypes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String partitionType = var3[var5];
            if (type != null && type.equals(partitionType)) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean isWLSPartitionConfigurationMBeanInSamePartition(String partitionName, ObjectName oname) {
      if (isWLSPartitionConfigurationMBean(oname)) {
         String name = oname.getKeyProperty("Name");
         if (name != null && name.equals(partitionName)) {
            return true;
         }

         String rgLifeCycleRuntime = oname.getKeyProperty("PartitionLifeCycleRuntime");
         if (rgLifeCycleRuntime != null && rgLifeCycleRuntime.equals(partitionName)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isWLSVirtualTargetMBean(ObjectName oname) {
      return oname.getDomain().equals("com.bea") && oname.toString().contains("VirtualTarget");
   }

   public static boolean isAvailableTarget(String name, String[] targetNames) {
      for(int i = 0; i < targetNames.length; ++i) {
         if (name != null && name.equals(targetNames[i])) {
            return true;
         }
      }

      return false;
   }

   public static boolean isSecurityMBean(ObjectName name) {
      return "Security".equals(name.getDomain());
   }

   public static boolean isWLSRealmRuntimeMBean(ObjectName name) {
      return name.getDomain().equals("com.bea") && (name.getKeyProperty("RealmRuntime") != null || "RealmRuntime".equals(name.getKeyProperty("Type")));
   }

   public static String findPartitionName(ObjectName objectName) {
      String partitionKey = objectName.getKeyProperty("Partition");
      if (partitionKey != null) {
         return partitionKey;
      } else {
         partitionKey = objectName.getKeyProperty("PartitionRuntime");
         if (partitionKey != null) {
            return partitionKey;
         } else {
            partitionKey = objectName.getKeyProperty("DomainPartitionRuntime");
            if (partitionKey != null) {
               return partitionKey;
            } else {
               partitionKey = getPartitionNameFromParentKey(objectName);
               return partitionKey != null ? partitionKey : "DOMAIN";
            }
         }
      }
   }

   public static ObjectName addLocation(ObjectName name, String location) {
      Hashtable table = name.getKeyPropertyList();
      table.put("Location", location);

      try {
         return new ObjectName(name.getDomain(), table);
      } catch (MalformedObjectNameException var4) {
         throw new AssertionError(var4);
      }
   }

   public static ObjectName removeLocation(ObjectName objectName) {
      if (objectName == null) {
         return null;
      } else if (objectName.getKeyProperty("Location") == null) {
         return objectName;
      } else {
         Hashtable table = objectName.getKeyPropertyList();

         try {
            StringBuffer newObjectNameString = new StringBuffer();
            if (objectName.isDomainPattern()) {
               newObjectNameString.append("*:");
            } else {
               newObjectNameString.append(objectName.getDomain() + ":");
            }

            Iterator entries = table.entrySet().iterator();
            boolean firstEntry = true;

            while(entries.hasNext()) {
               Map.Entry entry = (Map.Entry)entries.next();
               if (!entry.getKey().equals("Location")) {
                  if (!firstEntry) {
                     newObjectNameString.append(",");
                  }

                  newObjectNameString.append(entry.getKey() + "=" + entry.getValue());
                  firstEntry = false;
               }
            }

            if (objectName.isPropertyPattern()) {
               if (!firstEntry) {
                  newObjectNameString.append(",");
               }

               newObjectNameString.append("*");
            }

            return new ObjectName(newObjectNameString.toString());
         } catch (MalformedObjectNameException var6) {
            throw new AssertionError(var6);
         }
      }
   }
}
