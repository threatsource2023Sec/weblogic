package weblogic.management.runtime;

public interface EditSessionConfigurationRuntimeMBean extends RuntimeMBean {
   String getEditSessionName();

   String getPartitionName();

   String getDescription();

   boolean containsUnactivatedChanges();

   boolean isMergeNeeded();

   String getCurrentEditor();

   String getCreator();

   String getEditSessionServerJndi();
}
