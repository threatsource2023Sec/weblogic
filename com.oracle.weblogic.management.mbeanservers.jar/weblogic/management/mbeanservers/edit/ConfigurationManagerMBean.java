package weblogic.management.mbeanservers.edit;

import java.io.IOException;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PropertyValueVBean;
import weblogic.management.configuration.SimplePropertyValueVBean;
import weblogic.management.configuration.SystemComponentMBean;
import weblogic.management.mbeanservers.Service;

public interface ConfigurationManagerMBean extends Service {
   String OBJECT_NAME = "com.bea:Name=ConfigurationManager,Type=" + ConfigurationManagerMBean.class.getName();

   DomainMBean startEdit(int var1, int var2) throws EditTimedOutException;

   DomainMBean startEdit(int var1, int var2, boolean var3) throws EditTimedOutException;

   AutoResolveResult getStartEditResolveResult();

   void stopEdit() throws NotEditorException;

   void cancelEdit();

   String getCurrentEditor();

   boolean isEditor();

   long getCurrentEditorStartTime();

   long getCurrentEditorExpirationTime();

   boolean isCurrentEditorExclusive();

   boolean isCurrentEditorExpired();

   Change[] getChanges() throws NotEditorException;

   FileChange[] getFileChanges();

   void validate() throws NotEditorException, ValidationException;

   void reload() throws NotEditorException, ValidationException;

   void save() throws NotEditorException, ValidationException;

   void undo() throws NotEditorException;

   boolean haveUnactivatedChanges();

   Change[] getUnactivatedChanges() throws NotEditorException;

   void undoUnactivatedChanges() throws NotEditorException;

   ActivationTaskMBean activate(long var1) throws NotEditorException;

   ActivationTaskMBean[] getCompletedActivationTasks();

   long getCompletedActivationTasksCount();

   void setCompletedActivationTasksCount(long var1);

   ActivationTaskMBean[] getActiveActivationTasks();

   void purgeCompletedActivationTasks();

   ActivationTaskMBean[] getActivationTasks();

   Change[] getChangesToDestroyBean(DescriptorBean var1);

   void removeReferencesToBean(DescriptorBean var1) throws NotEditorException;

   FileChange[] getRemoteFileChanges(SystemComponentMBean var1) throws IOException;

   byte[] getRemoteFileContents(SystemComponentMBean var1, FileChange var2) throws IOException;

   byte[] getFileContents(String var1, String var2) throws IOException;

   FileChange[] updateConfigurationFromRemoteSystem(SystemComponentMBean var1) throws NotEditorException, IOException;

   void enableOverwriteComponentChanges() throws NotEditorException, IOException;

   ServerStatus[] resync(SystemComponentMBean var1) throws NotEditorException;

   ServerStatus[] resyncAll() throws NotEditorException;

   void syncPartitionConfig(String var1) throws Exception;

   String getEditSessionName();

   ActivationTaskMBean resolve(boolean var1, long var2) throws EditException;

   PropertyValueVBean[] getPropertyValues(ConfigurationMBean var1, String[] var2) throws Exception;

   PropertyValueVBean[] getPropertyValues(ConfigurationMBean var1, String[] var2, SettableBean[] var3, String[] var4) throws Exception;

   SimplePropertyValueVBean[] getEffectiveValues(ConfigurationMBean var1, String[] var2) throws Exception;

   SimplePropertyValueVBean[] getEffectiveValues(ConfigurationMBean var1, String[] var2, SettableBean[] var3, String[] var4) throws Exception;

   SimplePropertyValueVBean[] getWorkingValues(ConfigurationMBean var1, String[] var2) throws Exception;

   boolean isMergeNeeded();

   void releaseEditAccess();
}
