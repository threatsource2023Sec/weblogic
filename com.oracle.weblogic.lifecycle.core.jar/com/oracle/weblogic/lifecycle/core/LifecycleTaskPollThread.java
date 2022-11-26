package com.oracle.weblogic.lifecycle.core;

import com.oracle.weblogic.lifecycle.LifecycleContext;
import com.oracle.weblogic.lifecycle.LifecycleException;
import com.oracle.weblogic.lifecycle.LifecycleOperationType;
import com.oracle.weblogic.lifecycle.LifecyclePartition;
import com.oracle.weblogic.lifecycle.LifecycleRuntime;
import com.oracle.weblogic.lifecycle.LifecycleTask;
import com.oracle.weblogic.lifecycle.LifecycleTaskPlugin;
import com.oracle.weblogic.lifecycle.PartitionPlugin;
import com.oracle.weblogic.lifecycle.RuntimeManager;
import com.oracle.weblogic.lifecycle.RuntimePlugin;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class LifecycleTaskPollThread implements Runnable {
   private LifecycleTask task;
   private LifecycleContext context;
   private LifecycleRuntime runtime;
   private LifecycleOperationType operationType;
   private LifecyclePluginFactory pluginFactory;
   private Map properties;
   private RuntimeManager runtimeManager;
   private static final String PARTITION_NAME = "partitionName";
   private static final String OPERATION = "operation";

   LifecycleTaskPollThread(LifecyclePluginFactory pluginFactory, LifecycleRuntime runtime, LifecycleTask task, LifecycleContext context, Map properties, RuntimeManager runtimeManager) {
      if (LifecycleUtils.isAppServer()) {
         Objects.requireNonNull(properties);
         Objects.requireNonNull(runtime);
         Objects.requireNonNull(pluginFactory);
         Objects.requireNonNull(context);
         this.task = task;
         this.context = context;
         this.runtime = runtime;
         this.pluginFactory = pluginFactory;
         this.properties = properties;
         this.runtimeManager = runtimeManager;
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("LifecycleTaskPollThread constructor: Runtime=" + runtime.getRuntimeName());
            if (task != null && task.getProperties() != null) {
               LifecycleUtils.debug("LifecycleTaskPollThread constructor: task=" + task.getProperties().toString());
            }

            if (context != null && context.getProperties() != null) {
               LifecycleUtils.debug("LifecycleTaskPollThread constructor: Context=" + context.getProperties().toString());
            }

            LifecycleUtils.debug("LifecycleTaskPollThread constructor: Properties=" + properties.toString());
         }
      }

   }

   public void run() {
      String runtimeType = this.runtime.getRuntimeType();
      Objects.requireNonNull(runtimeType);
      if (!runtimeType.equalsIgnoreCase("wls")) {
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("Invalid Runtime Type in LifecycleTaskPollThread: " + runtimeType);
         }

      } else {
         LifecycleTaskPlugin taskPlugin = this.pluginFactory.getTaskPlugin(runtimeType);
         Objects.requireNonNull(taskPlugin);
         boolean asyncTaskCompleted = false;
         LifecyclePartition originatingPartition = null;
         this.putOperationInContext();
         originatingPartition = (LifecyclePartition)this.properties.get("originatingPartition");
         this.putPartitionNameInContext(originatingPartition);
         if (LifecycleUtils.isDebugEnabled()) {
            LifecycleUtils.debug("In Task Poll before calling taskPlugin.taskCompleted");
         }

         if (this.task != null) {
            try {
               asyncTaskCompleted = taskPlugin.taskCompleted(this.runtime, this.task, this.context);
            } catch (LifecycleException var15) {
               if (LifecycleUtils.isDebugEnabled()) {
                  LifecycleUtils.debug(var15.getMessage());
               }

               throw new RuntimeException(CatalogUtils.getMsgExceptionWhenWaitingForTaskCompletion(), var15);
            }
         }

         LifecycleOperationType operationType = (LifecycleOperationType)this.properties.get("operationtype");
         if (operationType == null) {
            throw new IllegalArgumentException(CatalogUtils.getMsgNullOperationInLifecycleTaskPoll());
         } else {
            Iterator iterator;
            switch (operationType) {
               case START_RUNTIME:
                  if (asyncTaskCompleted) {
                     if (LifecycleUtils.isDebugEnabled()) {
                        LifecycleUtils.debug("LifecycleTaskPollThread.run:START_RUNTIME");
                     }

                     List runtimeList = (List)this.properties.get("otdlist");
                     if (runtimeList == null || runtimeList.size() <= 0) {
                        if (LifecycleUtils.isDebugEnabled()) {
                           LifecycleUtils.debug("LifecycleTaskPollThread.run:START_RUNTIME - No OTD Runtimes registered");
                        }

                        return;
                     }

                     RuntimePlugin otdplugin = this.pluginFactory.getRuntimePlugin("otd");
                     if (otdplugin == null) {
                        if (LifecycleUtils.isDebugEnabled()) {
                           LifecycleUtils.debug("LifecycleTaskPollThread.run:START_RUNTIME - No OTD Plugin registered");
                        }

                        return;
                     }

                     iterator = runtimeList.iterator();
                     String phase = (String)this.properties.get("phase");

                     while(iterator.hasNext()) {
                        LifecycleRuntime otdRuntime = (LifecycleRuntime)iterator.next();
                        if (LifecycleUtils.isDebugEnabled()) {
                           LifecycleUtils.debug("LifecycleTaskPollThread.run: Calling Start on OTD Plugin OTD Runtime Name=" + otdRuntime.getRuntimeName());
                           if (this.context != null && this.context.getProperties() != null) {
                              LifecycleUtils.debug("Start Runtime: Calling Start on OTD Plugin Ctx" + this.context.getProperties().toString());
                           }
                        }

                        try {
                           otdplugin.start(otdRuntime.getRuntimeName(), phase, this.context);
                        } catch (LifecycleException var18) {
                           if (LifecycleUtils.isDebugEnabled()) {
                              LifecycleUtils.debug(var18.getMessage());
                           }

                           throw new RuntimeException(CatalogUtils.getMsgExceptionCallingOTDRuntimePluginStart(), var18);
                        }
                     }

                     return;
                  }
               case START_PARTITION:
                  if (asyncTaskCompleted) {
                     Set partitionSet = (Set)this.properties.get("partitionSet");
                     String phase = (String)this.properties.get("phase");
                     if (partitionSet != null && partitionSet.size() > 0) {
                        iterator = partitionSet.iterator();

                        while(iterator.hasNext()) {
                           LifecyclePartition partition = (LifecyclePartition)iterator.next();
                           if (!partition.getId().equals(originatingPartition.getId())) {
                              PartitionPlugin plugin = this.pluginFactory.getService(partition.getPartitionType());
                              String runtimeName = partition.getRuntimeName();
                              LifecycleRuntime runtimeObject = null;
                              if (runtimeName != null) {
                                 runtimeObject = this.runtimeManager.getRuntime(runtimeName);
                              }

                              try {
                                 plugin.start(partition.getPartitionName(), phase, this.context, runtimeObject);
                              } catch (LifecycleException var14) {
                                 if (LifecycleUtils.isDebugEnabled()) {
                                    LifecycleUtils.debug(var14.getMessage());
                                 }

                                 throw new RuntimeException(CatalogUtils.getMsgExceptionCallingPartitionPluginStart(), var14);
                              }
                           }
                        }

                        return;
                     }

                     if (LifecycleUtils.isDebugEnabled()) {
                        LifecycleUtils.debug("LifecycleTaskPollThread.run:START_PARTITION - Empty Partition Set");
                     }

                     return;
                  }
               case CREATE_RUNTIME:
                  if (LifecycleUtils.isDebugEnabled()) {
                     LifecycleUtils.debug("LifecycleTaskPollThread.run:CREATE_RUNTIME");
                  }

                  RuntimePlugin plugin = this.pluginFactory.getRuntimePlugin(runtimeType);
                  if (plugin == null) {
                     throw new RuntimeException(CatalogUtils.getMsgRuntimePluginNotFound(runtimeType));
                  }

                  try {
                     plugin.create(this.runtime.getRuntimeName(), this.context);
                     break;
                  } catch (LifecycleException var17) {
                     if (LifecycleUtils.isDebugEnabled()) {
                        LifecycleUtils.debug(var17.getMessage());
                     }

                     throw new RuntimeException(CatalogUtils.getMsgExceptionOnCreate(), var17);
                  }
               case SCALE_UP_RUNTIME:
                  if (LifecycleUtils.isDebugEnabled()) {
                     LifecycleUtils.debug("LifecycleTaskPollThread.run:SCALE_UP_RUNTIME");
                  }

                  try {
                     this.scaleOTD(operationType);
                  } catch (Exception var16) {
                     if (LifecycleUtils.isDebugEnabled()) {
                        LifecycleUtils.debug(var16.getMessage());
                     }

                     throw new RuntimeException(CatalogUtils.getMsgExceptionOnScaling(), var16);
                  }
            }

         }
      }
   }

   private void scaleOTD(LifecycleOperationType operationType) throws LifecycleException {
      Objects.requireNonNull(operationType);
      List runtimeList = (List)this.properties.get("otdlist");
      if (runtimeList.size() != 0) {
         RuntimePlugin plugin = this.pluginFactory.getRuntimePlugin("otd");
         if (plugin == null) {
            throw new RuntimeException(CatalogUtils.getMsgRuntimePluginNotFound("otd"));
         } else if (this.task != null && this.task.getProperties() != null) {
            Iterator var4 = runtimeList.iterator();

            while(var4.hasNext()) {
               LifecycleRuntime runtime = (LifecycleRuntime)var4.next();
               Object scaleFactorObject = this.task.getProperties().get("scaleFactor");
               if (scaleFactorObject == null) {
                  return;
               }

               if (!(scaleFactorObject instanceof Integer)) {
                  return;
               }

               int scaleFactor = (Integer)((Integer)scaleFactorObject);
               Map scaledInstances = (Map)this.context.getProperties().get("scaledInstances");
               if (operationType.equals(LifecycleOperationType.SCALE_DOWN_RUNTIME)) {
                  Map clusterDetails = (Map)this.task.getProperties().get("clusterDetails");
                  Iterator var10 = scaledInstances.keySet().iterator();

                  while(var10.hasNext()) {
                     String scaledInstance = (String)var10.next();
                     Map props = (Map)clusterDetails.get(scaledInstance);
                     if (props != null) {
                        ((Map)scaledInstances.get(scaledInstance)).put("address", props.get("address"));
                        ((Map)scaledInstances.get(scaledInstance)).put("port", props.get("port"));
                     }
                  }
               }

               LifecycleContext ctx = new LifecycleContextImpl((Map)this.context.getProperties().get("scaledInstances"));
               if (ctx.getProperties() == null) {
                  if (LifecycleUtils.isDebugEnabled()) {
                     LifecycleUtils.debug("OTD not being scaled. No WLS scaledInstances available.");
                  }
               } else if (operationType == LifecycleOperationType.SCALE_UP_RUNTIME) {
                  if (LifecycleUtils.isDebugEnabled()) {
                     LifecycleUtils.debug("LifecycleTaskPollThread.scaleOTD:Before calling scale up on OTD runtime=" + runtime.getRuntimeName());
                     if (ctx != null && ctx.getProperties() != null) {
                        LifecycleUtils.debug("LifecycleTaskPollThread.scaleOTD:Before calling scaleDown on OTD Context properties=" + ctx.getProperties().toString());
                     }
                  }

                  plugin.scaleUp(runtime.getRuntimeName(), scaleFactor, ctx);
               } else {
                  if (LifecycleUtils.isDebugEnabled()) {
                     LifecycleUtils.debug("LifecycleTaskPollThread.scaleOTD:Before calling scaleDown on OTD runtime=" + runtime.getRuntimeName());
                     if (ctx != null && ctx.getProperties() != null) {
                        LifecycleUtils.debug("LifecycleTaskPollThread.scaleOTD:Before calling scaleDown on OTD Context properties=" + ctx.getProperties().toString());
                     }
                  }

                  plugin.scaleDown(runtime.getRuntimeName(), scaleFactor, ctx);
               }
            }

         }
      }
   }

   private void putOperationInContext() {
      if (this.properties.containsKey("operationtype")) {
         LifecycleOperationType operation = (LifecycleOperationType)this.properties.get("operationtype");
         if (this.context != null && this.context.getProperties() != null) {
            this.context.getProperties().put("operation", operation);
         }
      }

   }

   private void putPartitionNameInContext(LifecyclePartition originatingPartition) {
      if (originatingPartition != null) {
         this.context.getProperties().put("partitionName", originatingPartition.getPartitionName());
      }

   }
}
