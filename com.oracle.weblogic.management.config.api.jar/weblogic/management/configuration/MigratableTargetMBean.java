package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

public interface MigratableTargetMBean extends SingletonServiceBaseMBean, TargetMBean {
   String DEFAULT_MIGRATABLETARGET_SUFFIX = " (migratable)";
   String NONE = "manual";
   String EXACTLY_ONCE = "exactly-once";
   String FAILURE_RECOVERY = "failure-recovery";
   String SHUTDOWN_RECOVERY = "shutdown-recovery";

   ServerMBean[] getConstrainedCandidateServers();

   void setConstrainedCandidateServers(ServerMBean[] var1) throws InvalidAttributeValueException;

   boolean addConstrainedCandidateServer(ServerMBean var1) throws InvalidAttributeValueException;

   boolean removeConstrainedCandidateServer(ServerMBean var1) throws InvalidAttributeValueException;

   void setUserPreferredServer(ServerMBean var1);

   ClusterMBean getCluster();

   void setCluster(ClusterMBean var1);

   ServerMBean[] getAllCandidateServers();

   void setAllCandidateServers(ServerMBean[] var1);

   boolean isManualActiveOn(ServerMBean var1);

   boolean isCandidate(ServerMBean var1);

   ServerMBean getDestinationServer();

   void setDestinationServer(ServerMBean var1);

   String getMigrationPolicy();

   void setMigrationPolicy(String var1);

   String getPreScript();

   void setPreScript(String var1);

   String getPostScript();

   void setPostScript(String var1);

   boolean isPostScriptFailureFatal();

   void setPostScriptFailureFatal(boolean var1);

   boolean isNonLocalPostAllowed();

   void setNonLocalPostAllowed(boolean var1);

   boolean getRestartOnFailure();

   void setRestartOnFailure(boolean var1);

   int getSecondsBetweenRestarts();

   void setSecondsBetweenRestarts(int var1);

   int getNumberOfRestartAttempts();

   void setNumberOfRestartAttempts(int var1);

   boolean isCritical();

   void setCritical(boolean var1);
}
