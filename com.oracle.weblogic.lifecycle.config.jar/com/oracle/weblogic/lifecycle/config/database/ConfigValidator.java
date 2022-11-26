package com.oracle.weblogic.lifecycle.config.database;

import java.util.Iterator;
import java.util.Map;
import org.glassfish.hk2.configuration.hub.api.Hub;
import org.glassfish.hk2.configuration.hub.api.Instance;
import org.glassfish.hk2.configuration.hub.api.Type;
import org.glassfish.hk2.configuration.hub.api.WriteableType;
import org.jvnet.hk2.annotations.Service;

@Service
public class ConfigValidator {
   public static void validateAdd(Hub hub, WriteableType type, String instanceId, Map mapOfValues) {
      String typeName = type.getName();
      Map instances = type.getInstances();
      Object newServiceID;
      Iterator var7;
      Instance instance;
      Map data;
      Object id;
      if (typeName.equals("/lifecycle-config/runtimes/runtime/partition")) {
         newServiceID = mapOfValues.get("id");
         if (newServiceID != null) {
            var7 = instances.values().iterator();

            do {
               if (!var7.hasNext()) {
                  return;
               }

               instance = (Instance)var7.next();
               data = (Map)instance.getBean();
               id = data.get("id");
            } while(!newServiceID.equals(id));

            throw new RuntimeException("Partition " + newServiceID + "already exists");
         }
      } else if (typeName.equals("/lifecycle-config/environments/environment/partition-ref")) {
         newServiceID = mapOfValues.get("id");
         if (newServiceID != null) {
            var7 = instances.values().iterator();

            do {
               if (!var7.hasNext()) {
                  return;
               }

               instance = (Instance)var7.next();
               data = (Map)instance.getBean();
               id = data.get("id");
            } while(!newServiceID.equals(id));

            throw new RuntimeException("Partition " + newServiceID + " already referenced");
         }
      } else if (typeName.equals("/lifecycle-config/tenants/tenant/service")) {
         newServiceID = mapOfValues.get("id");
         if (newServiceID != null) {
            var7 = instances.values().iterator();

            do {
               if (!var7.hasNext()) {
                  return;
               }

               instance = (Instance)var7.next();
               data = (Map)instance.getBean();
               id = data.get("id");
            } while(!newServiceID.equals(id));

            throw new RuntimeException("Service " + newServiceID + " already exists.");
         }
      }
   }

   public static void validateUpdate(Hub hub, WriteableType type, String instanceId, Map mapOfValues) {
   }

   public static void validateDelete(Hub hub, WriteableType type, String instanceId) {
      String typeName = type.getName();
      Map instances = type.getInstances();
      String deletedPartition;
      Type partitionRefType;
      Map partitionRefs;
      Iterator var8;
      Instance partitionRef;
      Map partitionRefData;
      if (typeName.equals("/lifecycle-config/environments/environment")) {
         deletedPartition = ConfigUtil.getInstanceValue(instanceId);
         if (deletedPartition != null) {
            partitionRefType = getType(hub, "/lifecycle-config/tenants/tenant/service");
            if (partitionRefType != null) {
               partitionRefs = partitionRefType.getInstances();
               var8 = partitionRefs.values().iterator();

               while(var8.hasNext()) {
                  partitionRef = (Instance)var8.next();
                  partitionRefData = (Map)partitionRef.getBean();
                  if (deletedPartition.equals(partitionRefData.get("environmentRef"))) {
                     throw new RuntimeException("Environment " + deletedPartition + " is referenced by Service");
                  }
               }
            }

         }
      } else if (typeName.equals("/lifecycle-config/runtimes/runtime/partition")) {
         deletedPartition = ConfigUtil.getInstanceValue(instanceId);
         if (deletedPartition != null) {
            partitionRefType = getType(hub, "/lifecycle-config/environments/environment/partition-ref");
            if (partitionRefType != null) {
               partitionRefs = partitionRefType.getInstances();
               var8 = partitionRefs.values().iterator();

               while(var8.hasNext()) {
                  partitionRef = (Instance)var8.next();
                  partitionRefData = (Map)partitionRef.getBean();
                  if (deletedPartition.equals(partitionRefData.get("id"))) {
                     throw new RuntimeException("Partition is referenced by environment");
                  }
               }
            }

         }
      }
   }

   private static Type getType(Hub hub, String name) {
      return hub.getCurrentDatabase().getType(name);
   }
}
