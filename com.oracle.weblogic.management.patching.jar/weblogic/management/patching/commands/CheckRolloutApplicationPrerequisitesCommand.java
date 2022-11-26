package weblogic.management.patching.commands;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import weblogic.management.patching.ApplicationProperties;
import weblogic.management.patching.PatchingDebugLogger;
import weblogic.management.patching.model.Node;
import weblogic.management.patching.model.PartitionApps;
import weblogic.management.patching.model.Server;
import weblogic.management.patching.model.ServerGroup;
import weblogic.management.workflow.CommandFailedNoTraceException;
import weblogic.management.workflow.command.SharedState;

public class CheckRolloutApplicationPrerequisitesCommand extends CheckPrerequisitesBaseCommand {
   private static final long serialVersionUID = -9032212767291537628L;
   public static final String APPLICATION_PROPERTIES_KEY = "applicationProperties";
   public static final String PLAN_PARAM = "planLocation";
   public static final String VERBOSE_PARAM = "VERBOSE";
   @SharedState
   protected transient ApplicationProperties applicationProperties;

   public boolean execute() throws Exception {
      boolean result = super.execute();
      if (!result) {
         return result;
      } else {
         String className = this.getClass().getName();
         if (this.applicationProperties != null) {
            className = className + " - " + this.applicationProperties.getApplicationName();
         } else {
            className = className + " - " + this.applicationProperties;
            this.applicationProperties = new ApplicationProperties();
         }

         String workflowId = this.getContext().getWorkflowId();
         PatchingLogger.logExecutingStep(workflowId, className, this.logTarget);

         try {
            String applicationName = this.applicationProperties.getApplicationName();
            String patchedLocation = this.applicationProperties.getPatchedLocation();
            String currentLocation = this.applicationProperties.getCurrentLocation();
            String backupLoacation = this.applicationProperties.getBackupLocation();
            if (applicationName != null && !applicationName.isEmpty()) {
               if (patchedLocation != null && !patchedLocation.isEmpty()) {
                  if (currentLocation != null && !currentLocation.isEmpty()) {
                     if (backupLoacation != null && !backupLoacation.isEmpty()) {
                        result = this.validatePaths();
                        PatchingLogger.logCompletedStep(workflowId, className, this.logTarget);
                        return result;
                     } else {
                        throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getApplicationBackupNotSpecified(applicationName));
                     }
                  } else {
                     throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getInvalidApplicationName(applicationName));
                  }
               } else {
                  throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getApplicationPatchedLocationNotSpecified(applicationName));
               }
            } else {
               throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getApplicationNameNotSpecified());
            }
         } catch (Exception var8) {
            PatchingLogger.logFailedStepNoError(workflowId, className, this.logTarget);
            throw var8;
         }
      }
   }

   public boolean validatePaths() throws CommandException {
      boolean result = false;
      if (this.applicationProperties.isStaged()) {
         result = this.validateStageModePaths();
      } else if (this.applicationProperties.isNoStaged()) {
         result = this.validateNoStageModePaths();
      } else if (this.applicationProperties.isExternalStaged()) {
         result = this.validateExternalStageModePaths();
      } else {
         result = this.validateServerDefaultStageModePaths();
      }

      return result;
   }

   public boolean validateServerDefaultStageModePaths() throws CommandException {
      boolean result = true;
      DomainModelIterator iterator = new DomainModelIterator(this.domainModel);

      while(iterator.hasNextNode()) {
         Node node = iterator.nextNode();
         String machineName = node.getNodeName();
         boolean noStageValidation = false;
         boolean stageValidation = false;

         while(iterator.hasNextServerGroup()) {
            ServerGroup serverGroup = iterator.nextServerGroup();

            while(iterator.hasNextServer()) {
               Server server = iterator.nextServer();
               String defaultStagingMode = (new ServerUtils()).getStagingModeForServer(server.getServerName());
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("CHECKPREREQ: CheckRolloutApplicationPrerequisites  command, server staging mode: " + defaultStagingMode + ", appName: " + this.applicationProperties.getApplicationName() + ", targeting machine: " + machineName);
               }

               Objects.requireNonNull(defaultStagingMode);
               if (defaultStagingMode.equalsIgnoreCase("nostage")) {
                  if (!noStageValidation) {
                     result = this.validatePathWithNodeManager(machineName, this.applicationProperties.getCurrentLocation(), this.applicationProperties.getPatchedLocation(), this.applicationProperties.getBackupLocation());
                     if (!result) {
                        throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getFailureValidatingNoStageModePaths(this.applicationProperties.getApplicationName(), machineName));
                     }

                     noStageValidation = true;
                  }
               } else if (defaultStagingMode.equalsIgnoreCase("stage")) {
                  if (!stageValidation) {
                     result = this.validateStageModePaths();
                     if (!result) {
                        throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getFailureValidatingStageModePaths(this.applicationProperties.getApplicationName(), machineName));
                     }

                     stageValidation = true;
                  }
               } else if (defaultStagingMode.equalsIgnoreCase("external_stage")) {
                  this.validateExternalStageModePathsForServer(server, iterator, machineName, this.applicationProperties.getApplicationName());
               } else if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("CHECKPREREQ: CheckRolloutApplicationPrerequisites command, server staging mode does not match stage/nostage/external_stage " + defaultStagingMode + ", appName: " + this.applicationProperties.getApplicationName() + ", targeting machine: " + machineName);
               }
            }
         }
      }

      return result;
   }

   public boolean validateStageModePaths() throws CommandException {
      boolean result = true;
      ServerUtils serverUtils = new ServerUtils();
      Server adminNode = this.domainModel.getAdminServer();
      if (adminNode != null) {
         String machineName = adminNode.getServerGroup().getNode().getNodeName();
         String partitionName;
         String currentPath;
         if (this.applicationProperties.getResourceGroupTemplateName() != null && !this.applicationProperties.getResourceGroupTemplateName().isEmpty()) {
            String currentPath = this.applicationProperties.getCurrentLocation();
            partitionName = this.applicationProperties.getPatchedLocation();
            currentPath = this.applicationProperties.getBackupLocation();
            result = this.validatePathWithNodeManager(machineName, currentPath, partitionName, currentPath);
            if (!result) {
               throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getFailureValidatingStageModePaths(this.applicationProperties.getApplicationName(), machineName));
            }
         } else if (this.applicationProperties.getPartitionNames().size() > 0) {
            Iterator var5 = this.applicationProperties.getPartitionNames().iterator();

            while(var5.hasNext()) {
               partitionName = (String)var5.next();
               if (PatchingDebugLogger.isDebugEnabled()) {
                  PatchingDebugLogger.debug("CHECKPREREQ: CheckRolloutApplicationPrerequisites command, mode: " + this.applicationProperties.getStageMode() + ", appName: " + this.applicationProperties.getApplicationName() + "partitionName = " + partitionName + ", currentSource = " + this.applicationProperties.getCurrentLocation());
               }

               currentPath = serverUtils.resolvePath(adminNode.getServerName(), partitionName, this.applicationProperties.getCurrentLocation());
               String patchedPath = serverUtils.resolvePath(adminNode.getServerName(), partitionName, this.applicationProperties.getPatchedLocation());
               String backupPath = serverUtils.resolvePath(adminNode.getServerName(), partitionName, this.applicationProperties.getBackupLocation());
               result = this.validatePathWithNodeManager(machineName, currentPath, patchedPath, backupPath);
               if (!result) {
                  throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getFailureValidatingStageModePaths(this.applicationProperties.getApplicationName(), machineName));
               }
            }
         } else {
            if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("CHECKPREREQ: CheckRolloutApplicationPrerequisites command, mode: " + this.applicationProperties.getStageMode() + ", appName: " + this.applicationProperties.getApplicationName() + ", targeting machine: " + machineName);
            }

            result = this.validatePathWithNodeManager(machineName, this.applicationProperties.getCurrentLocation(), this.applicationProperties.getPatchedLocation(), this.applicationProperties.getBackupLocation());
            if (!result) {
               throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getFailureValidatingStageModePaths(this.applicationProperties.getApplicationName(), machineName));
            }
         }
      }

      return result;
   }

   public boolean validateNoStageModePaths() throws CommandException {
      boolean result = true;
      DomainModelIterator iterator = new DomainModelIterator(this.domainModel);

      String machineName;
      do {
         if (!iterator.hasNextNode()) {
            return result;
         }

         Node n = iterator.nextNode();
         machineName = n.getNodeName();
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("CHECKPREREQ: CheckRolloutApplicationPrerequisites command, mode: " + this.applicationProperties.getStageMode() + ", appName: " + this.applicationProperties.getApplicationName() + ", targeting machine: " + machineName);
         }

         result = this.validatePathWithNodeManager(machineName, this.applicationProperties.getCurrentLocation(), this.applicationProperties.getPatchedLocation(), this.applicationProperties.getBackupLocation());
      } while(result);

      throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getFailureValidatingNoStageModePaths(this.applicationProperties.getApplicationName(), machineName));
   }

   public boolean validateExternalStageModePaths() throws CommandException {
      DomainModelIterator iterator = new DomainModelIterator(this.domainModel);

      boolean result;
      Node node;
      for(result = true; iterator.hasNextNode(); result = this.validateExternalStageModePaths(node)) {
         node = iterator.nextNode();
         String machineName = node.getNodeName();
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("CHECKPREREQ: CheckRolloutApplicationPrerequisites command, mode: " + this.applicationProperties.getStageMode() + ", appName: " + this.applicationProperties.getApplicationName() + ", checking " + machineName);
         }
      }

      return result;
   }

   protected boolean validateExternalStageModePaths(Node node) throws CommandException {
      boolean result = true;
      String applicationName = this.applicationProperties.getApplicationName();
      String machineName = node.getNodeName();
      DomainModelIterator iterator = new DomainModelIterator(node.getDomainModel());
      if (iterator.skipToNode(machineName) != null) {
         while(iterator.hasNextServerGroup()) {
            ServerGroup serverGroup = iterator.nextServerGroup();

            while(iterator.hasNextServer()) {
               Server server = iterator.nextServer();
               this.validateExternalStageModePathsForServer(server, iterator, machineName, applicationName);
            }
         }
      }

      return result;
   }

   protected void validateExternalStageModePathsForServer(Server server, DomainModelIterator iterator, String machineName, String applicationName) throws CommandException {
      boolean result = true;
      String serverName = server.getServerName();
      String serverStagingDirectory = server.getServerInfo().getStagingDirectory();
      if (iterator.hasNextPartitionApps()) {
         while(iterator.hasNextPartitionApps()) {
            PartitionApps partitionApps = iterator.nextPartitionApps();
            String partitionName = partitionApps.getPartitionName();
            if (this.applicationProperties.isPartitionTargeted(partitionName)) {
               result = this.validateAllPathsWithNodeManager(machineName, serverName, partitionName, serverStagingDirectory);
            } else if (PatchingDebugLogger.isDebugEnabled()) {
               PatchingDebugLogger.debug("Skipping CheckRolloutApplicationPrerequisitesCommand for external stage app: " + applicationName + " for partition " + partitionName + " on machine: " + machineName + " because it is not targeted to " + partitionName);
            }
         }
      } else if (this.applicationProperties.isServerTargeted(serverName)) {
         String rootDir = (new ServerUtils()).getRootDirectoryForServer(serverName);
         result = this.validateAllPathsWithNodeManager(machineName, serverName, (String)null, serverStagingDirectory);
      } else if (PatchingDebugLogger.isDebugEnabled()) {
         PatchingDebugLogger.debug("Skipping CheckRolloutApplicationPrerequisitesCommand for external stage app: " + applicationName + " for server " + serverName + " on machine: " + machineName + " because it is not targeted to " + serverName);
      }

      if (!result) {
         throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getFailureValidatingExternalStageModePaths(this.applicationProperties.getApplicationName(), machineName, server.getServerName()));
      }
   }

   protected boolean validatePathWithNodeManager(String machineName, String currentPath, String patchedLoc, String backupLoc) throws CommandException {
      Map scriptEnv = new HashMap();
      scriptEnv.put("CURRENT", currentPath);
      scriptEnv.put("PATCHED", patchedLoc);
      scriptEnv.put("BACKUP_DIR", backupLoc);
      scriptEnv.put("ACTION", "checkreq");
      scriptEnv.put("VERBOSE", String.valueOf(PatchingDebugLogger.isDebugEnabled()));
      return this.validatePathWithNodeManager(machineName, "UpdateApplication", scriptEnv);
   }

   protected boolean validateAllPathsWithNodeManager(String machineName, String serverName, String partitionName, String serverStagingDirectory) throws CommandException {
      boolean result = false;
      ServerUtils serverUtils = new ServerUtils();
      String domainDir;
      String rootDir;
      String currentPath;
      if (partitionName != null) {
         domainDir = serverUtils.getRootDirectoryForServer(serverName);
         rootDir = serverUtils.getPartitionSystemRoot(partitionName);
         currentPath = rootDir + File.separator + serverUtils.getRelativePath(serverStagingDirectory, domainDir);
         String relativeStagingDirectory = serverUtils.getRelativePath(currentPath, domainDir);
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("CHECKPREREQ: CheckRolloutApplicationPrerequisites command, built current external stage path from targetName = " + serverName + ", partitionName = " + partitionName + ", serverStagingDirectory = " + serverStagingDirectory + ", rootDirectory = " + rootDir + ", relativeStagingDirectory = " + relativeStagingDirectory + ", currentSource = " + relativeStagingDirectory + File.separator + this.applicationProperties.getStagingSubdirectory());
         }

         String currentPath = relativeStagingDirectory + File.separator + this.applicationProperties.getStagingSubdirectory();
         String patchedLoc = serverUtils.resolvePath(serverName, partitionName, this.applicationProperties.getPatchedLocation());
         String backupLoc = serverUtils.resolvePath(serverName, partitionName, this.applicationProperties.getBackupLocation());
         result = this.validatePathWithNodeManager(machineName, currentPath, patchedLoc, backupLoc);
      } else {
         domainDir = serverUtils.getRootDirectoryForServer(serverName);
         rootDir = serverUtils.getRelativePath(serverStagingDirectory, domainDir);
         if (PatchingDebugLogger.isDebugEnabled()) {
            PatchingDebugLogger.debug("CHECKPREREQ: CheckRolloutApplicationPrerequisites command, built current external stage path from targetName = " + serverName + ", serverStagingDirectory = " + serverStagingDirectory + ", rootDirectory = " + domainDir + ", relativeStagingDirectory = " + rootDir + ", currentSource = " + rootDir + File.separator + this.applicationProperties.getStagingSubdirectory());
         }

         currentPath = rootDir + File.separator + this.applicationProperties.getStagingSubdirectory();
         result = this.validatePathWithNodeManager(machineName, currentPath, this.applicationProperties.getPatchedLocation(), this.applicationProperties.getBackupLocation());
      }

      if (!result) {
         throw new CommandFailedNoTraceException(PatchingMessageTextFormatter.getInstance().getFailureValidatingExternalStageModePaths(this.applicationProperties.getApplicationName(), machineName, serverName));
      } else {
         return result;
      }
   }
}
