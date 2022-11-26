package weblogic.management.mbeanservers.edit.internal;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.management.ObjectName;
import javax.management.openmbean.OpenType;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.PropertyValueVBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.SimplePropertyValueVBean;
import weblogic.management.configuration.SystemComponentMBean;
import weblogic.management.internal.EditDirectoryManager;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.mbeanservers.Service;
import weblogic.management.mbeanservers.edit.ActivationTaskMBean;
import weblogic.management.mbeanservers.edit.AutoResolveResult;
import weblogic.management.mbeanservers.edit.Change;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.EditException;
import weblogic.management.mbeanservers.edit.EditTimedOutException;
import weblogic.management.mbeanservers.edit.FileChange;
import weblogic.management.mbeanservers.edit.NotEditorException;
import weblogic.management.mbeanservers.edit.ServerStatus;
import weblogic.management.mbeanservers.edit.ValidationException;
import weblogic.management.mbeanservers.internal.ServiceImpl;
import weblogic.management.provider.ActivateTask;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.EditChangesValidationException;
import weblogic.management.provider.EditFailedException;
import weblogic.management.provider.EditNotEditorException;
import weblogic.management.provider.EditSaveChangesFailedException;
import weblogic.management.provider.EditWaitTimedOutException;
import weblogic.management.provider.MachineStatus;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.ResolveTask;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.internal.CommonAdminConfigurationManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;

public class ConfigurationManagerMBeanImpl extends ServiceImpl implements ConfigurationManagerMBean, TimerListener {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugJMXEdit");
   private EditAccess edit;
   private WLSModelMBeanContext context;
   private final List completedTasks;
   private long completedActivationTasksCount = 10L;
   private final List uncompletedTasks;
   private TimerManager timerManager;
   private Timer timer;
   public static final long STATUS_CHECK_INTERVAL = 10000L;

   public ConfigurationManagerMBeanImpl(EditAccess edit, WLSModelMBeanContext context) {
      super("ConfigurationManager", ConfigurationManagerMBean.class.getName(), (Service)null);
      if (edit == null) {
         throw new AssertionError("EditAccess should not be null");
      } else {
         this.edit = edit;
         this.context = context;
         this.completedTasks = Collections.synchronizedList(new ArrayList());
         this.uncompletedTasks = Collections.synchronizedList(new ArrayList());
         this.timerManager = TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager();
      }
   }

   public DomainMBean startEdit(int waitTimeInMillis, int timeOutInMillis) throws EditTimedOutException {
      return this.startEdit(waitTimeInMillis, timeOutInMillis, false);
   }

   public DomainMBean startEdit(int waitTimeInMillis, int timeOutInMillis, boolean exclusive) throws EditTimedOutException {
      try {
         return this.edit.startEdit(waitTimeInMillis, timeOutInMillis, exclusive);
      } catch (EditWaitTimedOutException var5) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Timed out starting edit ", var5);
         }

         throw new EditTimedOutException(var5);
      } catch (EditFailedException var6) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Exception starting edit ", var6);
         }

         throw new RuntimeException(var6);
      }
   }

   public AutoResolveResult getStartEditResolveResult() {
      return this.edit.getStartEditResolveResult();
   }

   public void stopEdit() throws NotEditorException {
      try {
         this.edit.stopEdit();
      } catch (EditNotEditorException var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Not editor for stop ", var2);
         }

         throw new NotEditorException(var2);
      } catch (EditFailedException var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Exception stopping edit ", var3);
         }

         throw new RuntimeException(var3);
      }
   }

   public void cancelEdit() {
      try {
         this.edit.cancelEdit();
      } catch (EditFailedException var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Exception canceling edit ", var2);
         }

         throw new RuntimeException(var2);
      }
   }

   public String getCurrentEditor() {
      return this.edit.getEditor();
   }

   public boolean isEditor() {
      return this.edit.isEditor();
   }

   public long getCurrentEditorStartTime() {
      return this.edit.getEditorStartTime();
   }

   public long getCurrentEditorExpirationTime() {
      return this.edit.getEditorExpirationTime();
   }

   public boolean isCurrentEditorExclusive() {
      return this.edit.isEditorExclusive();
   }

   public boolean isCurrentEditorExpired() {
      long expirationTime = this.edit.getEditorExpirationTime();
      if (expirationTime <= 0L) {
         return false;
      } else {
         return expirationTime <= System.currentTimeMillis();
      }
   }

   public Change[] getChanges() throws NotEditorException {
      try {
         Iterator changes = this.edit.getUnsavedChanges();
         return this.convertBeanUpdatesToChanges(changes);
      } catch (EditNotEditorException var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Getting changes not editor ", var2);
         }

         throw new NotEditorException(var2);
      } catch (EditFailedException var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Exception getting changes ", var3);
         }

         throw new RuntimeException(var3);
      }
   }

   public FileChange[] getFileChanges() {
      String[] addedFiles = this.edit.getAddedFiles();
      String[] removedFiles = this.edit.getRemovedFiles();
      String[] editedFiles = this.edit.getEditedFiles();
      int numFileChanges = addedFiles.length + removedFiles.length + editedFiles.length;
      FileChange[] fileChanges = new FileChange[numFileChanges];
      int curr = 0;
      String[] var7 = addedFiles;
      int var8 = addedFiles.length;

      int var9;
      String editedFile;
      for(var9 = 0; var9 < var8; ++var9) {
         editedFile = var7[var9];
         fileChanges[curr++] = new FileChangeImpl(editedFile, "add");
      }

      var7 = removedFiles;
      var8 = removedFiles.length;

      for(var9 = 0; var9 < var8; ++var9) {
         editedFile = var7[var9];
         fileChanges[curr++] = new FileChangeImpl(editedFile, "remove");
      }

      var7 = editedFiles;
      var8 = editedFiles.length;

      for(var9 = 0; var9 < var8; ++var9) {
         editedFile = var7[var9];
         fileChanges[curr++] = new FileChangeImpl(editedFile, "edit");
      }

      return fileChanges;
   }

   public void validate() throws NotEditorException, ValidationException {
      try {
         this.edit.validateChanges();
      } catch (EditNotEditorException var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Validating changes not editor ", var2);
         }

         throw new NotEditorException(var2);
      } catch (EditChangesValidationException var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Exception validating changes ", var3);
         }

         throw new ValidationException(var3);
      }
   }

   public void reload() throws NotEditorException, ValidationException {
      try {
         this.edit.reload();
      } catch (EditNotEditorException var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Reloading changes not editor ", var2);
         }

         throw new NotEditorException(var2);
      } catch (EditChangesValidationException var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Exception reloading changes ", var3);
         }

         throw new ValidationException(var3);
      }
   }

   public void save() throws NotEditorException, ValidationException {
      try {
         this.edit.saveChanges();
      } catch (EditNotEditorException var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Saving changes not editor ", var2);
         }

         throw new NotEditorException(var2);
      } catch (EditSaveChangesFailedException var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Saving changes failed ", var3);
         }

         throw new RuntimeException(var3);
      } catch (EditChangesValidationException var4) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Exception validating changes ", var4);
         }

         throw new ValidationException(var4);
      }
   }

   public void undo() throws NotEditorException {
      try {
         this.removeReferences(this.edit.getUnsavedChanges());
         this.edit.undoUnsavedChanges();
      } catch (EditNotEditorException var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Undo changes not editor ", var2);
         }

         throw new NotEditorException(var2);
      } catch (EditFailedException var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Exception undoing changes ", var3);
         }

         throw new RuntimeException(var3);
      }
   }

   public boolean haveUnactivatedChanges() {
      if (this.edit.isPendingChange()) {
         return true;
      } else if (!this.edit.isModified()) {
         return false;
      } else {
         try {
            Iterator changes = this.edit.getUnactivatedChanges();
            return changes.hasNext();
         } catch (Exception var2) {
            return false;
         }
      }
   }

   public Change[] getUnactivatedChanges() throws NotEditorException {
      try {
         Iterator changes = this.edit.getUnactivatedChanges();
         return this.convertBeanUpdatesToChanges(changes);
      } catch (EditNotEditorException var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Getting unactivated changes not editor ", var2);
         }

         throw new NotEditorException(var2);
      } catch (EditFailedException var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Exception getting unactivated changes ", var3);
         }

         throw new RuntimeException(var3);
      }
   }

   public void undoUnactivatedChanges() throws NotEditorException {
      try {
         this.removeReferences(this.edit.getUnactivatedChanges());
         this.edit.undoUnactivatedChanges();
      } catch (EditNotEditorException var2) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Undo unactivated changes not editor ", var2);
         }

         throw new NotEditorException(var2);
      } catch (EditFailedException var3) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Exception undoing unactivated changes ", var3);
         }

         throw new RuntimeException(var3);
      }
   }

   public ActivationTaskMBean activate(long timeout) throws NotEditorException {
      try {
         this.edit.updateApplication();
         ActivateTask task;
         if (timeout == 0L) {
            task = this.edit.activateChanges(Long.MAX_VALUE);
         } else {
            if (timeout == -1L) {
               timeout = Long.MAX_VALUE;
            }

            task = this.edit.activateChangesAndWaitForCompletion(timeout);
         }

         ActivationTaskMBean activationTask = new ActivationTaskMBeanImpl(this, task);
         this.uncompletedTasks.add(activationTask);
         synchronized(this) {
            if (this.timer == null) {
               this.timer = this.timerManager.schedule(this, 10000L, 10000L);
            }
         }

         return activationTask;
      } catch (EditNotEditorException var8) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Activate changes not editor ", var8);
         }

         throw new NotEditorException(var8);
      } catch (EditFailedException var9) {
         debugLogger.debug("Exception activating changes ", var9);
         throw new RuntimeException(var9);
      }
   }

   public ActivationTaskMBean[] getCompletedActivationTasks() {
      this.moveCompletedTasksToCompletedList();
      ActivationTaskMBean[] taskArray = new ActivationTaskMBean[this.completedTasks.size()];
      if (this.completedTasks.size() == 0) {
         return taskArray;
      } else {
         this.completedTasks.toArray(taskArray);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Get all completed tasks" + Arrays.toString(taskArray));
         }

         return taskArray;
      }
   }

   public long getCompletedActivationTasksCount() {
      return this.completedActivationTasksCount;
   }

   public void setCompletedActivationTasksCount(long maxCount) {
      this.completedActivationTasksCount = maxCount;
   }

   public ActivationTaskMBean[] getActivationTasks() {
      ArrayList result = new ArrayList();
      result.addAll(this.completedTasks);
      result.addAll(this.uncompletedTasks);
      ActivationTaskMBean[] resultArray = new ActivationTaskMBean[result.size()];
      return (ActivationTaskMBean[])((ActivationTaskMBean[])result.toArray(resultArray));
   }

   public ActivationTaskMBean[] getActiveActivationTasks() {
      this.moveCompletedTasksToCompletedList();
      ActivationTaskMBean[] taskArray = new ActivationTaskMBean[this.uncompletedTasks.size()];
      if (this.uncompletedTasks.size() == 0) {
         return taskArray;
      } else {
         this.uncompletedTasks.toArray(taskArray);
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Get all active tasks" + Arrays.toString(taskArray));
         }

         return taskArray;
      }
   }

   public void purgeCompletedActivationTasks() {
      this.moveCompletedTasksToCompletedList();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Purging all completed tasks");
      }

      this.completedTasks.clear();
   }

   public Change[] getChangesToDestroyBean(DescriptorBean configBean) {
      if (configBean == null) {
         return new Change[0];
      } else {
         try {
            List list = configBean.getDescriptor().getResolvedReferences(configBean);
            if (list == null) {
               return new Change[0];
            } else {
               Object[] refs = list.toArray();
               Change[] changes = new Change[refs.length];

               for(int i = 0; i < refs.length; ++i) {
                  ResolvedReference ref = (ResolvedReference)refs[i];
                  DescriptorBean proposedBean = ref.getBean();
                  String propertyName = ref.getPropertyName();
                  if (propertyName.indexOf(47) != -1) {
                     propertyName = propertyName.substring(propertyName.lastIndexOf(47) + 1);
                  }

                  Object proposedMBean = this.context.mapToJMX(this.getType(proposedBean), proposedBean, (OpenType)null);
                  BeanInfo beanInfo = this.edit.getBeanInfo(proposedBean);
                  PropertyDescriptor propertyDescriptor = this.edit.getPropertyDescriptor(beanInfo, propertyName);
                  Method getterMethod = propertyDescriptor.getReadMethod();
                  Object oldValue = getterMethod.invoke(proposedBean, (Object[])null);
                  oldValue = this.mapValueToJMX(oldValue);
                  Object newValue = null;
                  boolean requiresRestart = this.edit.getRestartValue(propertyDescriptor);
                  if (oldValue != null && oldValue instanceof Object[] && ((Object[])((Object[])oldValue)).length > 0) {
                     oldValue = this.mapValueToJMX(configBean);
                     changes[i] = new ChangeImpl(proposedMBean, propertyName, "remove", oldValue, newValue, requiresRestart, this.getEntityType(proposedBean, (BeanUpdateEvent)null));
                  } else {
                     changes[i] = new ChangeImpl(proposedMBean, propertyName, "modify", oldValue, newValue, requiresRestart, this.getEntityType(proposedBean, (BeanUpdateEvent)null));
                  }
               }

               return changes;
            }
         } catch (Exception var16) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Exception getting reference changes ", var16);
            }

            throw new RuntimeException(var16);
         }
      }
   }

   public void removeReferencesToBean(DescriptorBean configBean) throws NotEditorException {
      if (!this.isEditor()) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("removeReferencesToBean - not editor ");
         }

         throw new NotEditorException("Not edit lock owner");
      } else if (configBean != null) {
         try {
            List list = configBean.getDescriptor().getResolvedReferences(configBean);
            if (list != null) {
               Object[] refs = list.toArray();
               Object[] var4 = refs;
               int var5 = refs.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  Object ref1 = var4[var6];
                  ResolvedReference ref = (ResolvedReference)ref1;
                  DescriptorBean proposedBean = ref.getBean();
                  String propertyName = ref.getPropertyName();
                  if (propertyName.indexOf(47) != -1) {
                     propertyName = propertyName.substring(propertyName.lastIndexOf(47) + 1);
                  }

                  BeanInfo beanInfo = this.edit.getBeanInfo(proposedBean);
                  PropertyDescriptor propertyDescriptor = this.edit.getPropertyDescriptor(beanInfo, propertyName);
                  Method getterMethod = propertyDescriptor.getReadMethod();
                  Method setterMethod = propertyDescriptor.getWriteMethod();
                  Object oldValue = getterMethod.invoke(proposedBean, (Object[])null);
                  if (oldValue != null && oldValue instanceof Object[] && ((Object[])((Object[])oldValue)).length > 0) {
                     Object[] newValue = this.removeBeanFromArray((Object[])((Object[])oldValue), configBean);
                     setterMethod.invoke(proposedBean, (Object)newValue);
                  } else {
                     setterMethod.invoke(proposedBean, null);
                     proposedBean.unSet(propertyName);
                  }
               }

            }
         } catch (Exception var17) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Exception removing references ", var17);
            }

            throw new RuntimeException(var17);
         }
      }
   }

   private Object[] removeBeanFromArray(Object[] arr, DescriptorBean configBean) {
      int newSize = 0;
      Object[] newValue = arr;
      int pos = arr.length;

      for(int var6 = 0; var6 < pos; ++var6) {
         Object anArr = newValue[var6];
         if (!configBean.equals(anArr)) {
            ++newSize;
         }
      }

      newValue = (Object[])((Object[])Array.newInstance(arr.getClass().getComponentType(), newSize));
      pos = 0;
      Object[] var10 = arr;
      int var11 = arr.length;

      for(int var8 = 0; var8 < var11; ++var8) {
         Object anArr = var10[var8];
         if (!configBean.equals(anArr)) {
            newValue[pos++] = anArr;
         }
      }

      return newValue;
   }

   private synchronized void moveCompletedTasksToCompletedList() {
      if (this.uncompletedTasks.size() == 0) {
         if (this.timer != null) {
            this.timer.cancel();
            this.timer = null;
         }

      } else {
         ActivationTaskMBean[] oldArray = new ActivationTaskMBean[this.completedTasks.size()];
         this.completedTasks.toArray(oldArray);
         boolean addedCompletedTasks = false;
         synchronized(this.uncompletedTasks) {
            Iterator it = this.uncompletedTasks.iterator();

            label51:
            while(true) {
               while(true) {
                  ActivationTaskMBeanImpl task;
                  do {
                     if (!it.hasNext()) {
                        break label51;
                     }

                     task = (ActivationTaskMBeanImpl)it.next();
                  } while(task.getState() != 4 && task.getState() != 5);

                  it.remove();
                  addedCompletedTasks = true;
                  if (this.getCompletedActivationTasksCount() > 0L) {
                     task.movingToCompleted();
                     this.completedTasks.add(task);

                     while((long)this.completedTasks.size() > this.getCompletedActivationTasksCount()) {
                        ActivationTaskMBean deletedTask = (ActivationTaskMBean)this.completedTasks.remove(0);
                        this.context.unregister(deletedTask);
                     }
                  } else {
                     this.context.unregister(task);
                  }
               }
            }
         }

         if (addedCompletedTasks) {
            ActivationTaskMBean[] newArray = new ActivationTaskMBean[this.completedTasks.size()];
            this.completedTasks.toArray(newArray);
            this._postSet("CompletedActivationTasks", oldArray, newArray);
         }

      }
   }

   public Change[] convertBeanUpdatesToChanges(Iterator beanUpdates) {
      if (this.context == null) {
         return new Change[0];
      } else {
         try {
            Vector changes = new Vector();

            while(beanUpdates.hasNext()) {
               BeanUpdateEvent event = (BeanUpdateEvent)beanUpdates.next();
               DescriptorBean sourceBean = event.getSourceBean();
               DescriptorBean proposedBean = event.getProposedBean();
               BeanInfo beanInfo = this.edit.getBeanInfo(proposedBean);
               Object proposedMBean = this.context.mapToJMX(this.getType(proposedBean), proposedBean, (OpenType)null);
               BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
               BeanUpdateEvent.PropertyUpdate[] var9 = updates;
               int var10 = updates.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  BeanUpdateEvent.PropertyUpdate update = var9[var11];
                  String propertyName = update.getPropertyName();
                  AbstractDescriptorBean addedBean = null;
                  Object oldValue = null;
                  Object newValue = null;
                  PropertyDescriptor propertyDescriptor = this.edit.getPropertyDescriptor(beanInfo, propertyName);
                  boolean requiresRestart = !update.isDynamic();
                  boolean isUnset = update.isUnsetUpdate();
                  Collection restartElements = update.getRestartElements();
                  List restartBeans = this.convertRestartElements(restartElements);
                  if (propertyDescriptor == null) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Skipping update for internal property name " + propertyName + " update:" + update);
                     }
                  } else {
                     Method getterMethod = propertyDescriptor.getReadMethod();
                     String relationship = (String)propertyDescriptor.getValue("relationship");
                     boolean reference = true;
                     if (relationship != null && relationship.equals("containment")) {
                        reference = false;
                     }

                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Processing update for property name " + propertyName + " update:" + update);
                     }

                     String operation;
                     switch (update.getUpdateType()) {
                        case 1:
                           if (isUnset) {
                              operation = "unset";
                           } else {
                              operation = "modify";
                           }

                           oldValue = getterMethod.invoke(sourceBean, (Object[])null);
                           oldValue = this.mapValueToJMX(oldValue);
                           newValue = getterMethod.invoke(proposedBean, (Object[])null);
                           newValue = this.mapValueToJMX(newValue);
                           break;
                        case 2:
                           if (reference) {
                              operation = "add";
                           } else {
                              operation = "create";
                           }

                           newValue = update.getAddedObject();
                           Object proposedValue = getterMethod.invoke(proposedBean, (Object[])null);
                           if (newValue instanceof AbstractDescriptorBean && proposedValue instanceof AbstractDescriptorBean) {
                              addedBean = (AbstractDescriptorBean)proposedValue;
                           }

                           if (newValue instanceof AbstractDescriptorBean && proposedValue instanceof DescriptorBean[]) {
                              DescriptorBean[] beans = (DescriptorBean[])((DescriptorBean[])proposedValue);
                              AbstractDescriptorBean bean = (AbstractDescriptorBean)newValue;
                              DescriptorBean[] var29 = beans;
                              int var30 = beans.length;

                              for(int var31 = 0; var31 < var30; ++var31) {
                                 DescriptorBean bean1 = var29[var31];
                                 AbstractDescriptorBean element = (AbstractDescriptorBean)bean1;
                                 if (bean._getKey().equals(element._getKey())) {
                                    addedBean = element;
                                 }
                              }
                           }

                           newValue = this.mapValueToJMX(newValue);
                           break;
                        case 3:
                           if (reference) {
                              operation = "remove";
                           } else {
                              operation = "destroy";
                           }

                           oldValue = update.getRemovedObject();
                           oldValue = this.mapValueToJMX(oldValue);
                           break;
                        default:
                           throw new AssertionError("Unexpected updateType:" + update.getUpdateType());
                     }

                     if (!isFromRGT(proposedBean)) {
                        if (restartBeans != null && restartBeans.size() > 0) {
                           Iterator var37 = restartBeans.iterator();

                           while(var37.hasNext()) {
                              Object bean = var37.next();
                              Change change = new ChangeImpl(proposedMBean, propertyName, operation, oldValue, newValue, requiresRestart, bean, "none");
                              changes.add(change);
                           }
                        } else {
                           Change change = new ChangeImpl(proposedMBean, propertyName, operation, oldValue, newValue, requiresRestart, this.getEntityType(proposedBean, event));
                           changes.add(change);
                        }
                     }

                     if (addedBean != null) {
                        this.addModifiesForNewBean(addedBean, changes, proposedBean, sourceBean, requiresRestart, event);
                     }
                  }
               }
            }

            Change[] changesArray = new Change[changes.size()];
            changes.toArray(changesArray);
            return changesArray;
         } catch (Exception var34) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Exception converting bean events ", var34);
            }

            throw new RuntimeException(var34);
         }
      }
   }

   private List convertRestartElements(Collection restartElements) {
      List restartBeans = new ArrayList();
      if (restartElements != null) {
         Iterator var3 = restartElements.iterator();

         while(var3.hasNext()) {
            SettableBean bean = (SettableBean)var3.next();
            Object proposedMBean = this.context.mapToJMX(this.getType(bean), bean, (OpenType)null);
            restartBeans.add(proposedMBean);
         }
      }

      return restartBeans;
   }

   private String getEntityType(DescriptorBean bean, BeanUpdateEvent originalEvent) {
      if (originalEvent != null && originalEvent.isParentEntitySet()) {
         return originalEvent.getParentEntity().toString();
      } else {
         return isFromPartition(bean) ? "partition" : "server";
      }
   }

   private static ResourceGroupTemplateMBean containedByRGT(SettableBean bean) {
      while(bean != null) {
         if (bean instanceof ResourceGroupTemplateMBean && !(bean instanceof ResourceGroupMBean)) {
            return (ResourceGroupTemplateMBean)bean;
         }

         if (bean instanceof ConfigurationMBean) {
            Object o = ((ConfigurationMBean)bean).getParent();
            bean = o instanceof ConfigurationMBean ? (ConfigurationMBean)o : null;
         }
      }

      return null;
   }

   private static boolean isFromRGT(DescriptorBean bean) {
      if (!(bean instanceof ConfigurationMBean)) {
         return false;
      } else {
         ConfigurationMBean configBean = (ConfigurationMBean)bean;
         return containedByRGT(configBean) != null;
      }
   }

   private static PartitionMBean containedByPartition(ConfigurationMBean bean) {
      while(bean != null) {
         if (bean instanceof PartitionMBean) {
            return (PartitionMBean)bean;
         }

         Object o = bean.getParent();
         bean = o instanceof ConfigurationMBean ? (ConfigurationMBean)o : null;
      }

      return null;
   }

   private static boolean isFromPartition(DescriptorBean bean) {
      if (!(bean instanceof ConfigurationMBean)) {
         return false;
      } else {
         ConfigurationMBean configBean = (ConfigurationMBean)bean;
         return containedByPartition(configBean) != null;
      }
   }

   public void addModifiesForNewBean(AbstractDescriptorBean newBean, Vector changes, DescriptorBean proposedBean, DescriptorBean sourceBean, boolean requiresRestart, BeanUpdateEvent originalEvent) {
      try {
         BeanInfo beanInfo = this.edit.getBeanInfo(newBean);
         Object newMBean = this.context.mapToJMX(ObjectName.class, newBean, (OpenType)null);
         PropertyDescriptor[] props = beanInfo.getPropertyDescriptors();

         for(int i = 0; props != null && i < props.length; ++i) {
            PropertyDescriptor prop = props[i];
            Boolean dyn = (Boolean)prop.getValue("key");
            if ((dyn == null || !dyn) && prop.getWriteMethod() != null && newBean.isSet(prop.getName())) {
               Method getterMethod = prop.getReadMethod();
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Getter method is " + getterMethod);
                  debugLogger.debug("Source bean is " + sourceBean);
               }

               Object newValue = getterMethod.invoke(newBean, (Object[])null);
               Object defaultValue = prop.getValue("default");
               if (defaultValue == null && newValue == null || defaultValue != null && defaultValue.equals(newValue)) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Skipping default value " + prop.getName());
                  }
               } else if (newValue instanceof DescriptorBean[]) {
                  DescriptorBean[] beans = (DescriptorBean[])((DescriptorBean[])newValue);
                  DescriptorBean[] var17 = beans;
                  int var18 = beans.length;

                  for(int var19 = 0; var19 < var18; ++var19) {
                     DescriptorBean bean = var17[var19];
                     newValue = this.mapValueToJMX(bean);
                     Change newChange = new ChangeImpl(newMBean, prop.getName(), "add", (Object)null, newValue, requiresRestart, this.getEntityType(proposedBean, originalEvent));
                     changes.add(newChange);
                  }
               } else {
                  newValue = this.mapValueToJMX(newValue);
                  Change newChange = new ChangeImpl(newMBean, prop.getName(), "modify", (Object)null, newValue, requiresRestart, this.getEntityType(proposedBean, originalEvent));
                  changes.add(newChange);
               }
            }
         }

      } catch (Exception var22) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Exception add modifies for new bean ", var22);
         }

         throw new RuntimeException(var22);
      }
   }

   private Object mapValueToJMX(Object value) {
      if (value instanceof AbstractDescriptorBean) {
         AbstractDescriptorBean bean = (AbstractDescriptorBean)value;
         return bean._getKey();
      } else if (value instanceof DescriptorBean[]) {
         DescriptorBean[] beans = (DescriptorBean[])((DescriptorBean[])value);
         Object[] values = new String[beans.length];

         for(int i = 0; i < beans.length; ++i) {
            if (beans[i] != null) {
               values[i] = ((AbstractDescriptorBean)beans[i])._getKey().toString();
            }
         }

         return values;
      } else {
         return value;
      }
   }

   private Class getType(Object instance) {
      Class clazz = instance.getClass();
      Class[] interfaces = clazz.getInterfaces();
      Class[] var4 = interfaces;
      int var5 = interfaces.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Class anInterface = var4[var6];
         if (anInterface != DescriptorBean.class) {
            String interfaceName = anInterface.getName();
            if (interfaceName.endsWith("Bean")) {
               return anInterface;
            }
         }
      }

      return instance.getClass();
   }

   public void timerExpired(Timer timer) {
      this.moveCompletedTasksToCompletedList();
   }

   private void removeReferences(Iterator beanUpdateEvents) throws NotEditorException {
      if (beanUpdateEvents != null) {
         while(beanUpdateEvents.hasNext()) {
            BeanUpdateEvent event = (BeanUpdateEvent)beanUpdateEvents.next();
            BeanUpdateEvent.PropertyUpdate[] updates = event.getUpdateList();
            BeanUpdateEvent.PropertyUpdate[] var4 = updates;
            int var5 = updates.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               BeanUpdateEvent.PropertyUpdate update = var4[var6];
               if (update.getUpdateType() == 2) {
                  Object bean = update.getAddedObject();
                  if (bean instanceof DescriptorBean) {
                     this.removeReferencesToBean((DescriptorBean)bean);
                  }
               }
            }
         }

      }
   }

   EditAccess getEditAccess() {
      return this.edit;
   }

   public FileChange[] getRemoteFileChanges(SystemComponentMBean sysCompMBean) throws IOException {
      DomainMBean domain = this.getDomainMBean();
      return CommonAdminConfigurationManager.getInstance().getRemoteFileChanges(domain, sysCompMBean);
   }

   private DomainMBean getDomainMBean() throws IOException {
      try {
         DomainMBean domain;
         if (this.getCurrentEditor() == null) {
            domain = this.edit.getCurrentDomainBean();
         } else {
            domain = this.edit.getDomainBean();
         }

         return domain;
      } catch (Exception var3) {
         throw new IOException(var3.getMessage(), var3);
      }
   }

   public byte[] getRemoteFileContents(SystemComponentMBean sysCompMBean, FileChange chg) throws IOException {
      return CommonAdminConfigurationManager.getInstance().getRemoteFileContents(sysCompMBean, chg);
   }

   public byte[] getFileContents(String componentType, String relativePath) throws IOException {
      return CommonAdminConfigurationManager.getInstance().getFileContents(componentType, relativePath);
   }

   public FileChange[] updateConfigurationFromRemoteSystem(SystemComponentMBean sysComp) throws NotEditorException, IOException {
      this.checkEditLock();

      DomainMBean domain;
      try {
         domain = this.edit.getDomainBean();
      } catch (Exception var4) {
         throw new IOException(var4.getMessage(), var4);
      }

      EditDirectoryManager directoryMgr = EditDirectoryManager.getDirectoryManager(this.edit.getPartitionName(), this.edit.getEditSessionName());
      return CommonAdminConfigurationManager.getInstance().updateConfigurationFromRemoteSystem(domain, directoryMgr, sysComp);
   }

   public void enableOverwriteComponentChanges() throws NotEditorException, IOException {
      this.checkEditLock();

      DomainMBean domain;
      try {
         domain = this.edit.getDomainBean();
      } catch (Exception var3) {
         throw new IOException(var3.getMessage(), var3);
      }

      CommonAdminConfigurationManager.getInstance().enableOverwriteComponentChanges(domain);
   }

   public ServerStatus[] resync(SystemComponentMBean sysComp) throws NotEditorException {
      MachineStatus[] result = this.edit.resync(sysComp);
      return this.machineStatusArray2ServerStatusArray(result);
   }

   public ServerStatus[] resyncAll() throws NotEditorException {
      MachineStatus[] result = this.edit.resyncAll();
      return this.machineStatusArray2ServerStatusArray(result);
   }

   private ServerStatus[] machineStatusArray2ServerStatusArray(MachineStatus[] r) {
      if (r == null) {
         return null;
      } else {
         ServerStatus[] result = new ServerStatus[r.length];

         for(int i = 0; i < r.length; ++i) {
            result[i] = new ServerStatusImpl(r[i].getName(), r[i].getState(), r[i].getException());
         }

         return result;
      }
   }

   private void checkEditLock() throws NotEditorException {
      if (this.getCurrentEditor() == null) {
         throw new NotEditorException("No edit session started");
      } else if (!this.isEditor()) {
         throw new NotEditorException("Not edit lock owner");
      }
   }

   public String getEditSessionName() {
      return this.edit.getEditSessionName();
   }

   public ActivationTaskMBean resolve(boolean stopOnConflict, long timeout) throws EditException {
      ResolveTask resolveTask;
      try {
         if (timeout == 0L) {
            resolveTask = this.edit.resolve(stopOnConflict, Long.MAX_VALUE);
         } else {
            if (timeout == -1L) {
               timeout = Long.MAX_VALUE;
            }

            resolveTask = this.edit.resolve(stopOnConflict, timeout);
            resolveTask.waitForTaskCompletion();
         }
      } catch (EditNotEditorException var9) {
         throw new NotEditorException(var9);
      }

      ResolveActivationTaskMBeanImpl activationTaskMBean = new ResolveActivationTaskMBeanImpl(this, resolveTask);
      this.uncompletedTasks.add(activationTaskMBean);
      if (activationTaskMBean.getActivateTask().getState() == 4 || activationTaskMBean.getActivateTask().getState() == 5) {
         this.moveCompletedTasksToCompletedList();
      }

      synchronized(this) {
         if (this.timer == null) {
            this.timer = this.timerManager.schedule(this, 10000L, 10000L);
         }

         return activationTaskMBean;
      }
   }

   public void syncPartitionConfig(String partitionName) throws Exception {
      this.startEdit(0, 0);

      try {
         this.edit.syncPartitionConfig(partitionName);
      } catch (EditNotEditorException var3) {
         throw new NotEditorException(var3.toString());
      } catch (EditFailedException var4) {
         this.undoUnactivatedChanges();
         throw new EditException(var4.toString());
      }

      this.activate(10000L);
   }

   public PropertyValueVBean[] getPropertyValues(ConfigurationMBean bean, String[] propertyNames) throws Exception {
      AuthenticatedSubject s = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      RuntimeAccess runTime = ManagementService.getRuntimeAccess(s);
      return runTime.getPropertyValues(bean, propertyNames);
   }

   public PropertyValueVBean[] getPropertyValues(ConfigurationMBean configBean, String[] navigationAttributeNames, SettableBean[] beans, String[] propertyNames) throws Exception {
      AuthenticatedSubject s = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      RuntimeAccess runTime = ManagementService.getRuntimeAccess(s);
      return runTime.getPropertyValues(configBean, navigationAttributeNames, beans, propertyNames);
   }

   public SimplePropertyValueVBean[] getEffectiveValues(ConfigurationMBean configBean, String[] navigationAttributeNames, SettableBean[] beans, String[] propertyNames) throws Exception {
      AuthenticatedSubject s = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      RuntimeAccess runTime = ManagementService.getRuntimeAccess(s);
      return runTime.getEffectiveValues(configBean, navigationAttributeNames, beans, propertyNames);
   }

   public SimplePropertyValueVBean[] getEffectiveValues(ConfigurationMBean configBean, String[] propertyNames) throws Exception {
      AuthenticatedSubject s = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      RuntimeAccess runTime = ManagementService.getRuntimeAccess(s);
      return runTime.getEffectiveValues(configBean, propertyNames);
   }

   public SimplePropertyValueVBean[] getWorkingValues(ConfigurationMBean configBean, String[] propertyNames) throws Exception {
      AuthenticatedSubject s = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      RuntimeAccess runTime = ManagementService.getRuntimeAccess(s);
      return runTime.getWorkingValues(configBean, propertyNames);
   }

   public boolean isMergeNeeded() {
      return this.edit.isMergeNeeded();
   }

   public void releaseEditAccess() {
      this.edit = null;
   }
}
