package weblogic.management.mbeanservers;

import java.security.AccessController;
import java.util.Iterator;
import java.util.List;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.InstanceNotFoundException;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.logging.Loggable;
import weblogic.management.jmx.JMXLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class PartitionDecorator {
   public static final DebugLogger debug = DebugLogger.getDebugLogger("DebugPartitionJMX");
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final String PARTITION_KEY;
   public static final String PARTITION_KEY_AND_EQUALS;
   public static final int PARTITION_KEY_AND_EQUALS_LENGTH;
   public static final String PARTITION_RUNTIME_KEY = "PartitionRuntime";
   public static final String DOMAIN_PARTITION_RUNTIME_KEY = "DomainPartitionRuntime";
   public static final String COHERENCE_KEY = "domainPartition";

   public static ObjectName addPartitionToObjectName(ObjectName objectName, String partitionName) throws InstanceNotFoundException {
      if (objectName == null) {
         return objectName;
      } else if (partitionName != null && !partitionName.equals("DOMAIN")) {
         if (containsKeyDecoratorWithCurrentPartition(objectName, partitionName)) {
            return objectName;
         } else {
            StringBuilder newPropsString = new StringBuilder();
            newPropsString.append(objectName.getDomain() + ":");
            newPropsString.append(PARTITION_KEY + "=" + partitionName + ",");
            String listString = objectName.toString();
            String propList = listString.substring(listString.indexOf(58) + 1);
            newPropsString.append(propList);

            try {
               return new ObjectName(newPropsString.toString());
            } catch (MalformedObjectNameException var6) {
               throw new AssertionError(var6);
            }
         }
      } else {
         return objectName;
      }
   }

   public static ObjectName addPartitionToObjectNameForRegistration(ObjectName objectName, String partitionName) throws InstanceNotFoundException {
      if (objectName == null) {
         return objectName;
      } else if (partitionName != null && !partitionName.equals("DOMAIN")) {
         if (containsKeyDecoratorWithCurrentPartitionForRegistration(objectName, partitionName)) {
            return objectName;
         } else {
            StringBuilder newPropsString = new StringBuilder();
            newPropsString.append(objectName.getDomain() + ":");
            newPropsString.append(PARTITION_KEY + "=" + partitionName + ",");
            String listString = objectName.toString();
            String propList = listString.substring(listString.indexOf(58) + 1);
            newPropsString.append(propList);

            try {
               return new ObjectName(newPropsString.toString());
            } catch (MalformedObjectNameException var6) {
               throw new AssertionError(var6);
            }
         }
      } else {
         return objectName;
      }
   }

   public static Object removePartitionFromAttributeList(AttributeList object) {
      if (object == null) {
         return object;
      } else if (MBeanPartitionUtil.isGlobalRuntime()) {
         return object;
      } else {
         String partitionName = findPartitionName();
         AttributeList newList = new AttributeList();
         List attrs = object.asList();
         Iterator var4 = attrs.iterator();

         while(var4.hasNext()) {
            Attribute attr = (Attribute)var4.next();
            if (attr.getValue() instanceof ObjectName) {
               newList.add(new Attribute(attr.getName(), removePartitionFromObjectName((ObjectName)attr.getValue())));
            } else {
               newList.add(removePartitionFromObjectNameStringAttribute(attr, partitionName));
            }
         }

         return newList;
      }
   }

   public static ObjectInstance removePartitionFromObjectInstance(ObjectInstance result) {
      if (result == null) {
         return result;
      } else {
         return MBeanPartitionUtil.isGlobalRuntime() ? result : new ObjectInstance(removePartitionFromObjectName(result.getObjectName()), result.getClassName());
      }
   }

   public static ObjectName removePartitionFromObjectName(ObjectName objectName) {
      if (objectName == null) {
         return objectName;
      } else if (objectName.getKeyProperty(PARTITION_KEY) != null) {
         String keyList = objectName.getKeyPropertyListString();
         StringBuilder newName = new StringBuilder();
         int begin = keyList.indexOf(PARTITION_KEY + "=");
         newName.append(objectName.getDomain());
         newName.append(":");
         newName.append(keyList.substring(0, begin));
         keyList = keyList.substring(begin + PARTITION_KEY.length() + 1, keyList.length());
         if (keyList.indexOf(",") != -1) {
            newName.append(keyList.substring(keyList.indexOf(",") + 1, keyList.length()));
         }

         if (newName.lastIndexOf(",") == newName.length() - 1) {
            newName = newName.deleteCharAt(newName.lastIndexOf(","));
         }

         try {
            return new ObjectName(newName.toString());
         } catch (MalformedObjectNameException var5) {
            throw new AssertionError(var5);
         }
      } else {
         return objectName;
      }
   }

   public static Object removePartitionFromResult(Object result) {
      if (result == null) {
         return result;
      } else if (MBeanPartitionUtil.isGlobalRuntime()) {
         return result;
      } else if (result instanceof ObjectName) {
         return removePartitionFromObjectName((ObjectName)result);
      } else if (!(result instanceof ObjectName[])) {
         return result instanceof Attribute ? removePartitionFromObjectNameStringAttribute((Attribute)result, findPartitionName()) : result;
      } else {
         ObjectName[] resultArray = (ObjectName[])((ObjectName[])result);

         for(int i = 0; i < resultArray.length; ++i) {
            resultArray[i] = removePartitionFromObjectName(resultArray[i]);
         }

         return resultArray;
      }
   }

   private static Object removePartitionFromObjectNameStringAttribute(Attribute attribute, String partitionName) {
      String name = attribute.getName();
      if (name != null && name.equals("objectName")) {
         Object value = attribute.getValue();
         if (value instanceof String) {
            String wasFixed = removePartitionFromString((String)value, partitionName);
            if (wasFixed != null) {
               return new Attribute(name, wasFixed);
            }
         }

         return attribute;
      } else {
         return attribute;
      }
   }

   static String removePartitionFromString(String before, String partitionName) {
      int beforeLength = before.length();
      if (partitionName != null && !partitionName.isEmpty()) {
         String entireTag = PARTITION_KEY_AND_EQUALS + partitionName;
         int entireTagLength = entireTag.length();
         if (beforeLength < entireTagLength) {
            return null;
         } else {
            int start = before.indexOf(entireTag);
            if (start < 0) {
               return null;
            } else {
               int end = before.indexOf(44, start + entireTagLength);
               if (end >= 0 && end + 1 != beforeLength) {
                  StringBuilder sb = new StringBuilder();
                  sb.append(before.substring(0, start));
                  sb.append(before.substring(end + 1));
                  return sb.toString();
               } else {
                  return before.substring(0, start);
               }
            }
         }
      } else {
         return null;
      }
   }

   public static boolean containsKeyDecoratorWithCurrentPartition(ObjectName objectName, String partitionName) throws InstanceNotFoundException {
      Loggable loggable;
      if (MBeanInfoBuilder.globalMBeansVisibleToPartitions) {
         if (objectName.getKeyPropertyList().containsKey(PARTITION_KEY)) {
            if (objectName.getKeyProperty(PARTITION_KEY).equals(partitionName)) {
               return true;
            }

            loggable = JMXLogger.logMBeanAnnotatedFailedLoggable(objectName.toString(), partitionName);
            throw new InstanceNotFoundException(loggable.getMessage());
         }
      } else if (objectName.getKeyPropertyList().containsKey(PARTITION_KEY) || objectName.getKeyPropertyList().containsKey("PartitionRuntime") || objectName.getKeyPropertyList().containsKey("DomainPartitionRuntime")) {
         if (objectName.getKeyProperty(PARTITION_KEY) != null && objectName.getKeyProperty(PARTITION_KEY).equals(partitionName)) {
            return true;
         }

         if (objectName.getKeyProperty("PartitionRuntime") != null && objectName.getKeyProperty("PartitionRuntime").equals(partitionName)) {
            return true;
         }

         if (objectName.getKeyProperty("DomainPartitionRuntime") != null && objectName.getKeyProperty("DomainPartitionRuntime").equals(partitionName)) {
            return true;
         }

         loggable = JMXLogger.logMBeanAnnotatedFailedLoggable(objectName.toString(), partitionName);
         throw new InstanceNotFoundException(loggable.getMessage());
      }

      if (objectName.getKeyPropertyList().containsKey("domainPartition")) {
         if (objectName.getKeyProperty("domainPartition").equals(partitionName)) {
            return true;
         } else {
            loggable = JMXLogger.logMBeanAnnotatedFailedLoggable(objectName.toString(), partitionName);
            throw new InstanceNotFoundException(loggable.getMessage());
         }
      } else {
         return false;
      }
   }

   public static boolean containsKeyDecoratorWithCurrentPartitionForRegistration(ObjectName objectName, String partitionName) throws InstanceNotFoundException {
      Loggable loggable;
      if (MBeanInfoBuilder.globalMBeansVisibleToPartitions) {
         if (objectName.getKeyPropertyList().containsKey(PARTITION_KEY)) {
            if (objectName.getKeyProperty(PARTITION_KEY).equals(partitionName)) {
               return true;
            }

            loggable = JMXLogger.logMBeanAnnotatedFailedLoggable(objectName.toString(), partitionName);
            throw new InstanceNotFoundException(loggable.getMessage());
         }
      } else if (objectName.getKeyPropertyList().containsKey(PARTITION_KEY) || objectName.getKeyPropertyList().containsKey("PartitionRuntime") || objectName.getKeyPropertyList().containsKey("DomainPartitionRuntime")) {
         if (MBeanPartitionUtil.getApplicationId() == null) {
            return true;
         }

         if (objectName.getKeyProperty(PARTITION_KEY) != null && objectName.getKeyProperty(PARTITION_KEY).equals(partitionName)) {
            return true;
         }

         if (objectName.getKeyProperty("PartitionRuntime") != null && objectName.getKeyProperty("PartitionRuntime").equals(partitionName)) {
            return true;
         }

         if (objectName.getKeyProperty("DomainPartitionRuntime") != null && objectName.getKeyProperty("DomainPartitionRuntime").equals(partitionName)) {
            return true;
         }

         loggable = JMXLogger.logMBeanAnnotatedFailedLoggable(objectName.toString(), partitionName);
         throw new InstanceNotFoundException(loggable.getMessage());
      }

      if (objectName.getKeyPropertyList().containsKey("domainPartition")) {
         if (objectName.getKeyProperty("domainPartition").equals(partitionName)) {
            return true;
         } else {
            loggable = JMXLogger.logMBeanAnnotatedFailedLoggable(objectName.toString(), partitionName);
            throw new InstanceNotFoundException(loggable.getMessage());
         }
      } else {
         return false;
      }
   }

   private static String findPartitionName() {
      ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(kernelId);
      ComponentInvocationContext cic = cicm.getCurrentComponentInvocationContext();
      return cic.getPartitionName();
   }

   static {
      if (MBeanInfoBuilder.globalMBeansVisibleToPartitions) {
         PARTITION_KEY = "_partitionName_";
      } else {
         PARTITION_KEY = "Partition";
      }

      PARTITION_KEY_AND_EQUALS = PARTITION_KEY + "=";
      PARTITION_KEY_AND_EQUALS_LENGTH = PARTITION_KEY_AND_EQUALS.length();
   }
}
