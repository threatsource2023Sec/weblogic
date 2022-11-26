package weblogic.management.provider;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.Iterator;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.SystemComponentMBean;
import weblogic.management.mbeanservers.edit.AutoResolveResult;
import weblogic.management.mbeanservers.edit.NotEditorException;

public interface EditAccess {
   void register(EventListener var1);

   DomainMBean startEdit(int var1, int var2) throws EditWaitTimedOutException, EditFailedException;

   DomainMBean startEdit(int var1, int var2, boolean var3) throws EditWaitTimedOutException, EditFailedException;

   AutoResolveResult getStartEditResolveResult();

   DomainMBean getDomainBean() throws EditNotEditorException, EditFailedException;

   DomainMBean getCurrentDomainBean() throws EditNotEditorException, EditFailedException;

   DomainMBean getDomainBeanWithoutLock() throws EditFailedException;

   boolean isDomainBeanTreeLoaded();

   void stopEdit() throws EditNotEditorException, EditFailedException;

   void cancelEdit() throws EditFailedException;

   void undoUnsavedChanges() throws EditNotEditorException, EditFailedException;

   Iterator getUnsavedChanges() throws EditNotEditorException, EditFailedException;

   String getCreator();

   String getEditor();

   String getEditSessionName();

   boolean isDefault();

   String getEditSessionDescription();

   String getPartitionName();

   boolean isEditor();

   long getEditorStartTime();

   long getEditorExpirationTime();

   boolean isEditorExclusive();

   void validateChanges() throws EditNotEditorException, EditChangesValidationException;

   void reload() throws EditNotEditorException, EditChangesValidationException;

   void saveChanges() throws EditNotEditorException, EditSaveChangesFailedException, EditChangesValidationException;

   ActivateTask activateChanges(long var1) throws EditNotEditorException, EditFailedException;

   ActivateTask activateChangesAndWaitForCompletion(long var1) throws EditNotEditorException, EditFailedException;

   void undoUnactivatedChanges() throws EditNotEditorException, EditFailedException;

   Iterator getUnactivatedChanges() throws EditNotEditorException, EditFailedException;

   boolean isModified();

   boolean isPendingChange();

   boolean isMergeNeeded();

   void markMergeNeeded();

   void cancelActivate() throws EditFailedException;

   BeanInfo getBeanInfo(DescriptorBean var1);

   PropertyDescriptor getPropertyDescriptor(BeanInfo var1, String var2);

   boolean getRestartValue(PropertyDescriptor var1);

   void shutdown();

   void destroy();

   void forceDestroy();

   void registerCallbackHandler(EditAccessCallbackHandler var1);

   void updateApplication() throws EditNotEditorException;

   String[] getAddedFiles();

   String[] getRemovedFiles();

   String[] getEditedFiles();

   MachineStatus[] resync(SystemComponentMBean var1) throws NotEditorException;

   MachineStatus[] resyncAll() throws NotEditorException;

   void syncPartitionConfig(String var1) throws Exception;

   EditAccess createEditAccess(String var1, String var2, String var3);

   ResolveTask resolve(boolean var1, long var2) throws EditNotEditorException;

   void nonConfigTaskCompleted(long var1, boolean var3);

   public interface EventListener {
      void onDestroy(EditAccess var1, DomainMBean var2);
   }
}
