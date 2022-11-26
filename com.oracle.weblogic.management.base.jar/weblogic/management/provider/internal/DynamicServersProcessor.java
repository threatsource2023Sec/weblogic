package weblogic.management.provider.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.DynamicServersMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.configuration.util.ReplicationPorts;

public class DynamicServersProcessor {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");

   public static void updateConfiguration(DomainMBean root) {
      ClusterMBean[] clusters = root.getClusters();
      if (clusters != null && clusters.length != 0) {
         ClusterMBean[] var2 = clusters;
         int var3 = clusters.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ClusterMBean cluster = var2[var4];
            DynamicServersMBean dynServer = cluster.getDynamicServers();
            ServerTemplateMBean serverTemplate = dynServer.getServerTemplate();
            if (serverTemplate != null && dynServer.getMaximumDynamicServerCount() > 0) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Processing dynamic server " + dynServer.getName() + " with template " + serverTemplate);
               }

               MachineMBean[] machines = null;
               if (dynServer.isCalculatedMachineNames()) {
                  machines = calculateMachineNames(root, dynServer);
               }

               int serverNameStartingIndex = dynServer.getServerNameStartingIndex();

               for(int i = 0; i < dynServer.getMaximumDynamicServerCount(); ++i) {
                  int instanceId = i + serverNameStartingIndex;

                  try {
                     String name = dynServer.getServerNamePrefix() + instanceId;
                     ServerMBean server = root.createServer(name);
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Created server name " + name);
                     }

                     if (server instanceof AbstractDescriptorBean) {
                        ((AbstractDescriptorBean)server).setInstanceId(instanceId);
                        ((AbstractDescriptorBean)server)._setTransient(true);
                     }

                     server.setServerTemplate(serverTemplate);
                     server.setCluster((ClusterMBean)dynServer.getParent());
                     if (dynServer.isCalculatedListenPorts()) {
                        calculateListenPorts(server, serverTemplate, instanceId);
                     }

                     if (dynServer.isCalculatedMachineNames()) {
                        setMachineName(root, server, machines, instanceId);
                     }

                     if (server.isAutoMigrationEnabled()) {
                        setCandidateMachines(root, server, instanceId);
                     }
                  } catch (Exception var14) {
                     ManagementLogger.logDynamicServerCreationFailed(var14);
                  }
               }
            }
         }

      }
   }

   private static void calculateListenPorts(ServerMBean server, ServerTemplateMBean serverTemplate, int instanceId) throws InvalidAttributeValueException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Calculating listen ports for " + server.getName());
      }

      if (serverTemplate.isSet("ListenPort")) {
         server.setListenPort(serverTemplate.getListenPort() + instanceId);
      } else {
         server.setListenPort(7100 + instanceId);
      }

      if (serverTemplate.getSSL().isSet("ListenPort")) {
         server.getSSL().setListenPort(serverTemplate.getSSL().getListenPort() + instanceId);
      } else {
         server.getSSL().setListenPort(8100 + instanceId);
      }

      if (serverTemplate.isAdministrationPortEnabled()) {
         if (serverTemplate.isSet("AdministrationPort")) {
            server.setAdministrationPort(serverTemplate.getAdministrationPort() + instanceId);
         } else {
            server.setAdministrationPort(9002 + instanceId);
         }
      }

      if (serverTemplate.getReplicationPorts() != null) {
         String baseReplicationPorts = serverTemplate.getReplicationPorts();
         String replicationPort = null;
         int[] ports = ReplicationPorts.parseReplicationPorts(baseReplicationPorts);
         if (ports.length > 1) {
            replicationPort = "" + (ports[0] + ports.length * (instanceId - 1)) + "-" + (ports[ports.length - 1] + ports.length * (instanceId - 1));
         } else if (ports.length == 1) {
            replicationPort = "" + (ports[0] + instanceId - 1);
         }

         server.setReplicationPorts(replicationPort);
      }

      NetworkAccessPointMBean[] var15 = serverTemplate.getNetworkAccessPoints();
      int var16 = var15.length;

      for(int var17 = 0; var17 < var16; ++var17) {
         NetworkAccessPointMBean nap = var15[var17];
         NetworkAccessPointMBean localNap = server.lookupNetworkAccessPoint(nap.getName());
         if (localNap == null) {
            localNap = server.createNetworkAccessPoint(nap.getName());
         }

         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Created NAP " + localNap.getName());
         }

         try {
            Class clz = localNap.getClass();
            Class[] params = new Class[]{clz};
            Method mthd = clz.getMethod("_setDelegateBean", params);
            Object[] args1 = new Object[]{nap};
            mthd.invoke(localNap, args1);
            if (nap.isSet("ListenPort")) {
               localNap.setListenPort(nap.getListenPort() + instanceId);
            } else {
               localNap.setListenPort(9100 + instanceId);
            }
         } catch (NoSuchMethodException var12) {
            throw new RuntimeException("Could not find the method to set the delegate MBean", var12);
         } catch (IllegalAccessException var13) {
            throw new RuntimeException("Illegal Access while setting the delegate MBean", var13);
         } catch (InvocationTargetException var14) {
            throw new RuntimeException("Invocation Target exception while setting the delegate MBean", var14);
         }
      }

   }

   public static MachineMBean[] calculateMachineNames(DomainMBean root, DynamicServersMBean dynamicServers) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Calculating machine names for " + dynamicServers.getName());
      }

      MachineMBean[] machines = null;
      if (dynamicServers.getMachineNameMatchExpression() == null && dynamicServers.getMachineMatchExpression() == null) {
         if (dynamicServers.getServerTemplate() != null && dynamicServers.getServerTemplate().isAutoMigrationEnabled()) {
            machines = ((ClusterMBean)dynamicServers.getParent()).getCandidateMachinesForMigratableServers();
         } else {
            machines = root.getMachines();
         }
      } else {
         String matchExpr;
         String[] tokens;
         ArrayList selectedMachines;
         MachineMBean[] var6;
         int var7;
         int var8;
         MachineMBean mach;
         if (dynamicServers.getMachineNameMatchExpression() != null || dynamicServers.getMachineMatchType().equals("name") && dynamicServers.getMachineMatchExpression() != null) {
            matchExpr = dynamicServers.getMachineNameMatchExpression();
            tokens = matchExpr.split(",");
            selectedMachines = new ArrayList();
            var6 = root.getMachines();
            var7 = var6.length;

            for(var8 = 0; var8 < var7; ++var8) {
               mach = var6[var8];
               boolean added = false;

               for(int i = 0; i < tokens.length && !added; ++i) {
                  String token = tokens[i].trim();
                  if (token.endsWith("*")) {
                     if (mach.getName().startsWith(token.substring(0, token.length() - 1))) {
                        selectedMachines.add(mach);
                        added = true;
                        if (debugLogger.isDebugEnabled()) {
                           debugLogger.debug("Selected machine via wildcard: " + mach.getName());
                        }
                     }
                  } else if (mach.getName().equals(token)) {
                     selectedMachines.add(mach);
                     added = true;
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Selected machine: " + mach.getName());
                     }
                  } else if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Did not select machine: " + mach.getName() + " for token: " + token);
                  }
               }
            }

            machines = (MachineMBean[])selectedMachines.toArray(new MachineMBean[0]);
         } else if (dynamicServers.getMachineMatchType().equals("tag") && dynamicServers.getMachineMatchExpression() != null) {
            matchExpr = dynamicServers.getMachineMatchExpression();
            tokens = matchExpr.split(",");
            selectedMachines = new ArrayList();
            var6 = root.getMachines();
            var7 = var6.length;

            for(var8 = 0; var8 < var7; ++var8) {
               mach = var6[var8];
               String[] tags = mach.getTags();
               if (tags != null) {
                  if (isMachineTagged(tokens, tags)) {
                     selectedMachines.add(mach);
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Selected machine: " + mach.getName());
                     }
                  } else if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Did not select machine: " + mach.getName() + " for tag: " + matchExpr);
                  }
               }
            }

            machines = (MachineMBean[])selectedMachines.toArray(new MachineMBean[0]);
         }
      }

      if (debugLogger.isDebugEnabled()) {
         MachineMBean[] var13 = machines;
         int var15 = machines.length;

         for(int var16 = 0; var16 < var15; ++var16) {
            MachineMBean mach = var13[var16];
            debugLogger.debug("Machine is " + mach.getName());
         }
      }

      int numMachines = machines.length;
      if (numMachines == 0) {
         ManagementLogger.logNoMatchingMachines(dynamicServers.getName());
      }

      return machines;
   }

   private static boolean isMachineTagged(String[] tagsExpression, String[] tagsMachine) {
      if (tagsExpression != null && tagsMachine != null) {
         if (tagsExpression.length != 0 && tagsMachine.length != 0) {
            if (tagsExpression.length > tagsMachine.length) {
               return false;
            } else {
               ArrayList machineTags = new ArrayList(tagsMachine.length);
               String[] var3 = tagsMachine;
               int var4 = tagsMachine.length;

               int var5;
               for(var5 = 0; var5 < var4; ++var5) {
                  String machineTag = var3[var5];
                  if (machineTag != null && !machineTag.isEmpty()) {
                     machineTags.add(machineTag);
                  }
               }

               boolean isSame = true;
               String[] var9 = tagsExpression;
               var5 = tagsExpression.length;

               for(int var10 = 0; var10 < var5; ++var10) {
                  String tagExp = var9[var10];
                  if (!machineTags.contains(tagExp)) {
                     isSame = false;
                     break;
                  }
               }

               return isSame;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static void setMachineName(DomainMBean root, ServerMBean server, MachineMBean[] machines, int instanceId) throws InvalidAttributeValueException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Set machine name for " + server.getName());
      }

      int numMachines = machines.length;
      if (numMachines != 0) {
         int idx = (instanceId - 1) % numMachines;
         server.setMachine(machines[idx]);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Calculated idx: " + idx + " Machine: " + machines[idx].getName());
         }

      }
   }

   private static void setCandidateMachines(DomainMBean root, ServerMBean server, int instanceId) throws InvalidAttributeValueException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Set candidate machines for " + server.getName());
      }

      ClusterMBean cluster = server.getCluster();
      if (cluster != null && cluster.getCandidateMachinesForMigratableServers() != null) {
         MachineMBean[] clusterMachines = cluster.getCandidateMachinesForMigratableServers();
         MachineMBean[] serverMachines = server.getCandidateMachines();
         if (serverMachines == null || serverMachines.length <= 0) {
            int numMachines = clusterMachines.length;
            serverMachines = new MachineMBean[numMachines];

            for(int i = 0; i < numMachines; ++i) {
               int idx = (instanceId - 1 + i) % numMachines;
               serverMachines[i] = clusterMachines[idx];
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Candidate machine[" + idx + "] = " + serverMachines[i].getName());
               }
            }

            server.setCandidateMachines(serverMachines);
         }
      }
   }
}
