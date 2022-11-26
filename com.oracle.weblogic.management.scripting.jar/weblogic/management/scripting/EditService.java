package weblogic.management.scripting;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.management.Descriptor;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import weblogic.descriptor.conflict.DiffConflictException;
import weblogic.management.ManagementException;
import weblogic.management.NoAccessRuntimeException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.SystemComponentConfigurationMBean;
import weblogic.management.configuration.SystemComponentMBean;
import weblogic.management.mbeanservers.edit.ActivationTaskMBean;
import weblogic.management.mbeanservers.edit.AutoResolveResult;
import weblogic.management.mbeanservers.edit.Change;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.EditTimedOutException;
import weblogic.management.mbeanservers.edit.FileChange;
import weblogic.management.mbeanservers.edit.NotEditorException;
import weblogic.management.mbeanservers.edit.ServerStatus;
import weblogic.management.mbeanservers.edit.ValidationException;
import weblogic.management.runtime.EditSessionConfigurationManagerMBean;
import weblogic.management.runtime.EditSessionConfigurationRuntimeMBean;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.management.scripting.utils.WLSTUtil;
import weblogic.server.ServiceFailureException;

public class EditService implements Serializable {
   private WLScriptContext ctx = null;
   private static final String ACTIVATE = "activate";
   private static final String UNDO = "undo";
   private static final String SAVE = "save";
   private static final String STOP_EDIT = "stopEdit";
   private static final String VALIDATE = "validate";
   private static final String SHOW_CHANGES = "showChanges";
   private transient WLSTMsgTextFormatter txtFmt;

   public EditService(WLScriptContext ctx) {
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   private void validateTree() throws ScriptException {
      WLScriptContext var10001 = this.ctx;
      if (!this.ctx.domainType.equals("ConfigEdit")) {
         var10001 = this.ctx;
         if (!this.ctx.domainType.equals("EditCustom_Domain")) {
            this.ctx.throwWLSTException(this.txtFmt.getCantCallEditFunctions());
         }
      }

   }

   private boolean validateCall(String call) throws ScriptException {
      if (this.ctx.newBrowseHandler.doesUserHasLock()) {
         this.ctx.isEditSessionInProgress = true;
         return true;
      } else if (this.ctx.isEditSessionInProgress && call.equals("stopEdit")) {
         return true;
      } else if (this.ctx.isEditSessionInProgress && !this.ctx.newBrowseHandler.doesUserHasLock()) {
         this.ctx.println(this.txtFmt.getEditSessionTerminated());
         this.cleanUp();
         return false;
      } else if (!this.ctx.isEditSessionInProgress) {
         this.ctx.throwWLSTException(this.txtFmt.getNeedEditSessionFor(call));
         return false;
      } else {
         return true;
      }
   }

   private void cleanUp() {
      this.ctx.resetEditSession();
      if (!WLSTUtil.runningWLSTAsModule()) {
         this.ctx.getWLSTInterpreter().exec("evaluatePrompt()");
      }

   }

   public void createEditSession(String name, String description) throws ScriptException {
      this.ctx.commandType = "createEditSession";
      if (!this.ctx.isNamedEditSessionAvailable) {
         this.ctx.throwWLSTException(this.txtFmt.getCommandNotRunInPreVersion(this.ctx.commandType));
      }

      try {
         this.ctx.getEditSessionConfigurationManager().createEditSessionConfiguration(name, description);
      } catch (IllegalArgumentException var4) {
         this.ctx.throwWLSTException(var4.getMessage());
      } catch (ServiceFailureException | ManagementException var5) {
         this.ctx.throwWLSTException(this.txtFmt.getUnexpectedError(var5.getMessage()), var5);
      }

   }

   public void destroyEditSession(String name, boolean force) throws ScriptException {
      this.ctx.commandType = "destroyEditSession";
      if (!this.ctx.isNamedEditSessionAvailable) {
         this.ctx.throwWLSTException(this.txtFmt.getCommandNotRunInPreVersion(this.ctx.commandType));
      }

      if (name == null || name.isEmpty()) {
         this.ctx.throwWLSTException(this.txtFmt.getDestroyDefaultError());
      }

      WLScriptContext var10001 = this.ctx;
      if (this.ctx.domainType.equals("ConfigEdit") && this.ctx.edit != null && this.ctx.edit.equalsName(name)) {
         this.ctx.println(this.txtFmt.getDestroyCurrentEditTree());
         this.ctx.newBrowseHandler.configRuntime();
      }

      if (this.ctx.edit.equalsName(name)) {
         this.ctx.setEdit((WLSTEditVariables)this.ctx.edits.get((Object)null));
      }

      try {
         EditSessionConfigurationManagerMBean editSessionConfigurationManager = this.ctx.getEditSessionConfigurationManager();
         EditSessionConfigurationRuntimeMBean runtimeMBean = editSessionConfigurationManager.lookupEditSessionConfiguration(name);
         if (runtimeMBean == null) {
            this.ctx.throwWLSTException(this.txtFmt.getEditSessionNotExist(name));
         }

         if (force) {
            editSessionConfigurationManager.forceDestroyEditSessionConfiguration(runtimeMBean);
         } else {
            editSessionConfigurationManager.destroyEditSessionConfiguration(runtimeMBean);
         }

         this.ctx.edits.remove(name);
      } catch (IllegalArgumentException var5) {
         this.ctx.throwWLSTException(var5.getMessage(), var5);
      } catch (ServiceFailureException | ManagementException var6) {
         this.ctx.throwWLSTException(this.txtFmt.getUnexpectedError(var6.getMessage()), var6);
      }

   }

   public void showEditSession(String name) {
      this.ctx.commandType = "showEditSession";
      if (!this.ctx.isNamedEditSessionAvailable) {
         this.ctx.printError(this.txtFmt.getCommandNotRunInPreVersion(this.ctx.commandType));
      } else {
         EditSessionConfigurationManagerMBean confManager = this.ctx.getEditSessionConfigurationManager();
         boolean listing = name == null || name.isEmpty();
         if (listing) {
            this.ctx.println(this.txtFmt.getEditSessionInfoListIntroduction());
         }

         EditSessionConfigurationRuntimeMBean[] var4 = confManager.getEditSessionConfigurations();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            EditSessionConfigurationRuntimeMBean esConf = var4[var6];
            String description;
            String currentEditor;
            if (!listing) {
               if (name.equals(esConf.getEditSessionName())) {
                  description = esConf.getDescription();
                  if (description != null && description.trim().length() != 0) {
                     description = "- " + description;
                  } else {
                     description = "";
                  }

                  currentEditor = esConf.getCurrentEditor();
                  if (currentEditor == null || currentEditor.length() == 0) {
                     currentEditor = this.txtFmt.getNone();
                  }

                  this.ctx.println(this.txtFmt.getEditSessionInfo(esConf.getEditSessionName(), description, esConf.getCreator(), currentEditor, esConf.isMergeNeeded() ? this.txtFmt.getYes() : this.txtFmt.getNo(), esConf.containsUnactivatedChanges() ? this.txtFmt.getYes() : this.txtFmt.getNo()));
                  return;
               }
            } else {
               description = esConf.getDescription();
               if (description != null && description.trim().length() != 0) {
                  description = " - " + description;
               } else {
                  description = "";
               }

               currentEditor = " " + esConf.getEditSessionName() + description;
               if (currentEditor.length() > 80) {
                  currentEditor = currentEditor.substring(0, 77) + "...";
               }

               this.ctx.println(currentEditor);
            }
         }

         if (name != null && !name.isEmpty()) {
            this.ctx.printError(this.txtFmt.getNamedEditSessionDoesNotExist());
         }

      }
   }

   public DomainMBean startEdit(int waitTimeInMillis, int timeOutInMillis, String exclusive) throws ScriptException {
      this.ctx.commandType = "startEdit";
      this.validateTree();
      DomainMBean bean = null;
      this.ctx.println(this.txtFmt.getStartingEditSession());

      String user;
      try {
         bean = this.ctx.getConfigManager().startEdit(waitTimeInMillis, timeOutInMillis, this.ctx.getBoolean(exclusive));
         String prompt = this.ctx.getPrompt();
         this.ctx.wlcmo = bean;
         this.ctx.browseHandler.cd("/" + prompt);
         this.ctx.println(this.txtFmt.getStartedEditSession());
         if (this.ctx.getBoolean(exclusive)) {
            this.ctx.print(this.txtFmt.getExclusiveSession());
            this.ctx.isEditSessionExclusive = true;
            this.ctx.edit.isEditSessionExclusive = true;
         }

         this.ctx.isEditSessionInProgress = true;
         if (this.ctx.isNamedEditSessionAvailable) {
            AutoResolveResult rr = this.ctx.getConfigManager().getStartEditResolveResult();
            if (rr != null) {
               Throwable throwable = rr.getThrowable();
               if (throwable == null) {
                  this.ctx.println(this.txtFmt.getAutoResolveOK());
               } else if (throwable instanceof DiffConflictException) {
                  this.ctx.println(this.txtFmt.getAutoResolveConflicts());
               } else {
                  this.ctx.println(this.txtFmt.getAutoResolveFail());
               }
            }
         }
      } catch (EditTimedOutException var8) {
         user = this.ctx.getConfigManager().getCurrentEditor();
         this.ctx.throwWLSTException(this.txtFmt.getEditLockHeld(user), var8);
      } catch (NoAccessRuntimeException var9) {
         user = new String(this.ctx.username_bytes);
         this.ctx.throwWLSTException(this.txtFmt.getNoPermissionForEdit(user), var9);
      }

      return bean;
   }

   public void save() throws ScriptException {
      this.ctx.commandType = "save";
      this.validateTree();
      if (this.validateCall("save")) {
         this.ctx.println(this.txtFmt.getSavingChanges());
         if (this.ctx.isNamedEditSessionAvailable && this.ctx.getConfigManager().isMergeNeeded()) {
            this.ctx.println(this.txtFmt.getEditConfigIsStale());
         }

         try {
            this.ctx.getConfigManager().save();
            this.ctx.println(this.txtFmt.getSavedChanges());
         } catch (NotEditorException var2) {
            this.ctx.throwWLSTException(this.txtFmt.getNoChangesYet(), var2);
         } catch (ValidationException var3) {
            this.ctx.throwWLSTException(this.txtFmt.getInvalidChanges(), var3);
         }

      }
   }

   public void resolve(boolean stopOnConflict) throws ScriptException {
      this.ctx.commandType = "resolve";
      if (!this.ctx.isNamedEditSessionAvailable) {
         this.ctx.throwWLSTException(this.txtFmt.getCommandNotRunInPreVersion(this.ctx.commandType));
      }

      this.validateTree();
      if (this.validateCall("resolve")) {
         try {
            ConfigurationManagerMBean configManager = this.ctx.getConfigManager();
            ActivationTaskMBean activationTask = configManager.resolve(stopOnConflict, 300000L);
            activationTask.waitForTaskCompletion(300000L);
            if (activationTask.getError() != null) {
               throw activationTask.getError();
            }

            this.ctx.println(activationTask.getDetails());
         } catch (DiffConflictException var4) {
            this.ctx.throwWLSTException(this.txtFmt.getCanNotResolve() + "\n" + var4.getMessage());
         } catch (Exception var5) {
            this.ctx.throwWLSTException(this.txtFmt.getCanNotResolve(), var5);
         }

      }
   }

   public ActivationTaskMBean activate(long timeout, String block) throws ScriptException {
      this.ctx.commandType = "activate";
      if (WLSTUtil.runningWLSTAsModule()) {
         block = "true";
      }

      this.validateTree();
      if (!this.validateCall("activate")) {
         return null;
      } else {
         try {
            if (block.equalsIgnoreCase("true")) {
               this.ctx.println(this.txtFmt.getActivatingChanges());
               this.ctx.activationTask = this.ctx.getConfigManager().activate(timeout);
               this.printServerRestartInfo(this.ctx.activationTask.getChanges());
               this.ctx.activationTask.waitForTaskCompletion(timeout);
               this.printServerStatusInfo(this.ctx.activationTask);
               if (this.ctx.activationTask.getError() != null) {
                  throw this.ctx.activationTask.getError();
               }

               if (this.ctx.isNamedEditSessionAvailable) {
                  String[] var4 = this.ctx.activationTask.getSystemComponentsToRestart();
                  int var13 = var4.length;

                  for(int var14 = 0; var14 < var13; ++var14) {
                     String scName = var4[var14];
                     this.ctx.println(this.txtFmt.getSCRestartRequired(scName));
                  }
               }

               this.ctx.println(this.txtFmt.getActivationComplete());
               if (!WLSTUtil.runningWLSTAsModule()) {
                  this.ctx.getWLSTInterpreter().set("activationTask", this.ctx.activationTask);
               }
            } else {
               this.ctx.println(this.txtFmt.getActivatingChangesNonBlocking());
               this.ctx.activationTask = this.ctx.getConfigManager().activate(timeout);
               this.printServerRestartInfo(this.ctx.activationTask.getChanges());
               this.ctx.getWLSTInterpreter().set("activationTask", this.ctx.activationTask);
               this.ctx.println(this.txtFmt.getActivationTaskCreated());
            }

            this.ctx.resetEditSession();
            return this.ctx.activationTask;
         } catch (NotEditorException var10) {
            this.ctx.throwWLSTException(this.txtFmt.getNoChangesYet(), var10);
         } catch (RuntimeException var11) {
            boolean threwError = false;
            if (var11.getCause() instanceof RemoteException) {
               RemoteException re = (RemoteException)var11.getCause();
               if (re.getCause() instanceof SecurityException) {
                  SecurityException se = (SecurityException)re.getCause();
                  if (se.getMessage().indexOf(this.txtFmt.getAdministratorRequiredString()) != -1) {
                     this.ctx.println(this.txtFmt.getReloginRequired());

                     try {
                        this.ctx.dc("true");
                     } catch (Throwable var9) {
                        this.ctx.throwWLSTException(this.txtFmt.getErrorDisconnecting(), var9);
                     }

                     threwError = true;
                  }
               }
            }

            if (!threwError) {
               this.cleanUp();
               this.ctx.throwWLSTException(this.txtFmt.getErrorActivating(), var11);
            }
         } catch (Throwable var12) {
            this.cleanUp();
            this.ctx.throwWLSTException(this.txtFmt.getErrorActivating(), var12);
         }

         this.ctx.resetEditSession();
         return this.ctx.activationTask;
      }
   }

   private void printServerRestartInfo(Change[] unActivatedChanges) throws NotEditorException {
      if (unActivatedChanges.length >= 1) {
         Map serverRestartBeans = new HashMap();
         Map partitionRestartBeans = new HashMap();

         for(int i = 0; i < unActivatedChanges.length; ++i) {
            Change chg = unActivatedChanges[i];
            if (chg.isRestartRequired() && chg.getAffectedBean() == null) {
               HashMap beans;
               if (chg.getEntityToRestart().equals("server")) {
                  beans = serverRestartBeans;
               } else {
                  beans = partitionRestartBeans;
               }

               if (beans.isEmpty()) {
                  beans.put(chg.getBean(), chg.getAttributeName());
               } else {
                  String attrNames = (String)beans.get(chg.getBean());
                  if (attrNames == null) {
                     beans.put(chg.getBean(), chg.getAttributeName());
                  } else {
                     attrNames = attrNames + ", " + chg.getAttributeName();
                     beans.put(chg.getBean(), attrNames);
                  }
               }
            }
         }

         if (this.ctx.isNamedEditSessionAvailable) {
            this.printRestartRequiredMBeans(unActivatedChanges);
         }

         if (!serverRestartBeans.isEmpty() || !partitionRestartBeans.isEmpty()) {
            this.printRestartMessageDueToNonDynamicAttributeChanges(serverRestartBeans, "server");
            if (this.ctx.isNamedEditSessionAvailable) {
               this.printRestartMessageDueToNonDynamicAttributeChanges(partitionRestartBeans, "partition");
            }

         }
      }
   }

   private void printRestartRequiredMBeans(Change[] unActivatedChanges) {
      Map changedBeanToAffectedBeanMap = this.groupSimilarMBean(unActivatedChanges);
      StringBuffer allAffectedBeans = new StringBuffer(15);
      Iterator var4 = changedBeanToAffectedBeanMap.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry changedBeanDetails = (Map.Entry)var4.next();
         this.ctx.println(this.txtFmt.getDependentBeansRestartMessage());
         this.ctx.println(this.txtFmt.getChangedBean(((ChangedBeanInfo)changedBeanDetails.getKey()).getBeanInstanceName()));
         this.ctx.println(this.txtFmt.getAttributesChanged(((ChangedBeanInfo)changedBeanDetails.getKey()).getChangedAttributeName()));
         Iterator var6 = ((Set)changedBeanDetails.getValue()).iterator();

         while(var6.hasNext()) {
            Object affectedBean = var6.next();
            allAffectedBeans.append("\n").append(affectedBean.toString());
         }

         this.ctx.println(this.txtFmt.getDependentBean(allAffectedBeans.toString()));
      }

   }

   private Map groupSimilarMBean(Change[] unActivatedChanges) {
      Map changedBeanToAffectedBeanMap = new HashMap();
      Set affectedBeans = null;
      Change[] var4 = unActivatedChanges;
      int var5 = unActivatedChanges.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Change change = var4[var6];
         if (change.getAffectedBean() != null) {
            ChangedBeanInfo changedBeanInfo = this.getChangedBeanInfo(change);
            if (changedBeanToAffectedBeanMap.get(changedBeanInfo) == null) {
               affectedBeans = new HashSet();
               affectedBeans.add(change.getAffectedBean());
               changedBeanToAffectedBeanMap.put(changedBeanInfo, affectedBeans);
            } else {
               ((Set)changedBeanToAffectedBeanMap.get(changedBeanInfo)).add(change.getAffectedBean());
            }
         }
      }

      return changedBeanToAffectedBeanMap;
   }

   private ChangedBeanInfo getChangedBeanInfo(Change change) {
      return new ChangedBeanInfo(change.getBean().toString(), change.getAttributeName());
   }

   private void printRestartMessageDueToNonDynamicAttributeChanges(Map serverRestartBeans, String entity) {
      if (!serverRestartBeans.isEmpty()) {
         this.ctx.println(this.txtFmt.getNonDynamicAttributes(entity));
         this.ctx.isRestartRequired = true;
         Iterator iter = serverRestartBeans.keySet().iterator();

         while(iter.hasNext()) {
            Object bean = iter.next();
            this.ctx.println(this.txtFmt.getMBeanChanged(this.asString(bean)));
            this.ctx.println(this.txtFmt.getAttributesChanged(this.asString(serverRestartBeans.get(bean))));
            this.ctx.println("");
         }
      }

   }

   public void undo(String unactivatedChanges) throws ScriptException {
      this.ctx.commandType = "undo";
      this.validateTree();
      if (this.validateCall("undo")) {
         try {
            if (unactivatedChanges.equals("true")) {
               this.ctx.getConfigManager().undoUnactivatedChanges();
               this.ctx.println(this.txtFmt.getDiscardedAllChanges());
            } else {
               this.ctx.getConfigManager().undo();
               this.ctx.println(this.txtFmt.getDiscardedAllInMemoryChanges());
            }
         } catch (NotEditorException var3) {
            this.ctx.throwWLSTException(this.txtFmt.getNoChangesYet(), var3);
         }

      }
   }

   public void cancelEdit() throws ScriptException {
      this.ctx.commandType = "cancelEdit";
      this.validateTree();
      this.ctx.getConfigManager().cancelEdit();
      this.ctx.resetEditSession();
   }

   public boolean isRestartRequired(String attrName) throws ScriptException {
      this.ctx.commandType = "isRestartRequired";

      try {
         boolean displayed = false;
         int i;
         if (attrName == null) {
            if (this.ctx.isEditSessionInProgress) {
               Change[] changes = this.ctx.getConfigManager().getUnactivatedChanges();
               boolean restartRequired = false;

               for(i = 0; i < changes.length; ++i) {
                  if (changes[i].isRestartRequired()) {
                     if (!displayed) {
                        this.ctx.println(this.txtFmt.getRestartRequired());
                     }

                     restartRequired = true;
                     displayed = true;
                  }
               }

               if (restartRequired) {
                  this.printServerRestartInfo(changes);
                  return true;
               }

               this.ctx.println(this.txtFmt.getRestartNotRequired());
               return false;
            }

            return this.ctx.isRestartRequired;
         }

         this.validateTree();
         ModelMBeanInfo modelInfo = (ModelMBeanInfo)this.ctx.getMBeanInfo(this.ctx.wlcmo);
         ModelMBeanAttributeInfo[] attrInfos = (ModelMBeanAttributeInfo[])((ModelMBeanAttributeInfo[])modelInfo.getAttributes());

         for(i = 0; i < attrInfos.length; ++i) {
            ModelMBeanAttributeInfo info = attrInfos[i];
            Descriptor desc;
            if (info.getName().equals(attrName)) {
               desc = info.getDescriptor();
               Boolean dyn = (Boolean)desc.getFieldValue("com.bea.dynamic");
               if (dyn != null && dyn) {
                  this.ctx.println(this.txtFmt.getRestartNotRequiredFor(attrName));
                  return false;
               }

               this.ctx.println(this.txtFmt.getRestartRequiredFor(attrName));
               return true;
            }

            if (attrName.equals("*")) {
               displayed = true;
               desc = info.getDescriptor();
               String _attrName = info.getName();
               Boolean dyn = (Boolean)desc.getFieldValue("com.bea.dynamic");
               if (dyn != null && dyn) {
                  this.ctx.println(this.txtFmt.getRestartNotRequiredFor(_attrName));
               }

               this.ctx.println(this.txtFmt.getRestartRequiredFor(_attrName));
            }
         }

         if (!displayed) {
            this.ctx.println(this.txtFmt.getAttributeNotFound(attrName));
         }
      } catch (Throwable var10) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorGettingRestartInfo(), var10);
      }

      return false;
   }

   public boolean validate() throws ScriptException {
      this.ctx.commandType = "validate";

      try {
         this.validateTree();
         if (!this.validateCall("validate")) {
            return false;
         }

         this.ctx.println(this.txtFmt.getValidatingChanges());
         this.ctx.getConfigManager().validate();
         this.ctx.println(this.txtFmt.getValidationSuccess());
         return true;
      } catch (NotEditorException var2) {
         this.ctx.throwWLSTException(this.txtFmt.getNoChangesYet(), var2);
      } catch (ValidationException var3) {
         this.ctx.throwWLSTException(this.txtFmt.getValidationErrors(), var3);
      }

      return false;
   }

   public void stopEdit() throws ScriptException {
      this.ctx.commandType = "stopEdit";
      this.validateTree();
      if (this.validateCall("stopEdit")) {
         try {
            this.ctx.getConfigManager().stopEdit();
            this.ctx.resetEditSession();
         } catch (NotEditorException var2) {
            this.ctx.throwWLSTException(this.txtFmt.getNoChangesYet(), var2);
         }

      }
   }

   public void showChanges(String onlyInMemory) throws ScriptException {
      this.ctx.commandType = "showChanges";
      this.validateTree();
      if (this.validateCall("showChanges")) {
         try {
            Change[] unSavedChanges = this.ctx.getConfigManager().getChanges();
            if (onlyInMemory.equals("true")) {
               this.ctx.println(this.txtFmt.getUnsavedChangesAre());
               this.printChanges(unSavedChanges);
               return;
            }

            Change[] unActivatedChanges = this.ctx.getConfigManager().getUnactivatedChanges();
            if (unActivatedChanges != null && unActivatedChanges.length > 0) {
               this.ctx.println("");
               this.ctx.println(this.txtFmt.getUnactivatedChangesAre());
               this.printChanges(unActivatedChanges);
            }

            if (this.ctx.getConfigManager().isMergeNeeded()) {
               this.ctx.println(this.txtFmt.getEditConfigIsStale());
            }
         } catch (NotEditorException var4) {
            this.ctx.throwWLSTException(this.txtFmt.getNoChangesYet(), var4);
         }

      }
   }

   private void printChanges(Change[] changes) {
      for(int i = 0; i < changes.length; ++i) {
         Change change = changes[i];
         this.ctx.println("");
         this.printChange(change);
         this.ctx.println("");
      }

   }

   private void printChange(Change change) {
      this.ctx.println(this.txtFmt.getMBeanChanged2(this.asString(change.getBean())));
      this.ctx.println(this.txtFmt.getOperationInvoked(this.asString(change.getOperation())));
      this.ctx.println(this.txtFmt.getAttributeModified(this.asString(change.getAttributeName())));
      this.ctx.println(this.txtFmt.getAttributesOldValue(this.asString(change.getOldValue())));
      this.ctx.println(this.txtFmt.getAttributesNewValue(this.asString(change.getNewValue())));
      this.ctx.println(this.txtFmt.getServerRestartRequired(this.asString(change.isRestartRequired())));
   }

   private String asString(boolean b) {
      return b ? "true" : "false";
   }

   private String asString(Object o) {
      return o == null ? "null" : o.toString();
   }

   public ActivationTaskMBean getActivationTask() throws ScriptException {
      this.ctx.commandType = "getActivationTask";
      this.validateTree();
      return this.ctx.activationTask;
   }

   private void printServerStatusInfo(ActivationTaskMBean task) {
      if (task != null) {
         ServerStatus[] stats = task.getStatusByServer();

         for(int i = 0; stats != null && i < stats.length; ++i) {
            ServerStatus status = stats[i];
            if (status != null && status.getServerState() == 8) {
               this.ctx.println(this.txtFmt.getChangesDeferred(status.getServerName()));
               if (status.getServerException() != null) {
                  this.ctx.println(this.txtFmt.getChangesDeferredError(status.getServerException().getMessage()));
               }
            }
         }

      }
   }

   public ServerStatus[] resync(String compName) throws ScriptException {
      this.ctx.commandType = "resync";
      if (!this.ctx.isNamedEditSessionAvailable) {
         this.ctx.println(this.txtFmt.getCommandNotRunInPreVersion(this.ctx.commandType));
         return null;
      } else {
         try {
            if (compName != null && !compName.trim().isEmpty()) {
               SystemComponentMBean comp = this.getSystemComponentByName(this.ctx.runtimeDomainMBean, compName);
               if (comp == null) {
                  this.ctx.println(this.txtFmt.getCAMComponentNotFound(compName));
                  return null;
               } else {
                  ServerStatus[] result = this.ctx.getConfigManager().resync(comp);
                  return result;
               }
            } else {
               this.ctx.println(this.txtFmt.getInvalidCAMComponentName());
               return null;
            }
         } catch (Exception var4) {
            this.ctx.throwWLSTException(var4.getMessage(), var4);
            return null;
         }
      }
   }

   public ServerStatus[] resyncAll() throws ScriptException {
      this.ctx.commandType = "resyncAll";
      if (!this.ctx.isNamedEditSessionAvailable) {
         this.ctx.println(this.txtFmt.getCommandNotRunInPreVersion(this.ctx.commandType));
         return null;
      } else {
         try {
            ServerStatus[] result = this.ctx.getConfigManager().resyncAll();
            return result;
         } catch (Exception var2) {
            this.ctx.throwWLSTException(var2.getMessage(), var2);
            return null;
         }
      }
   }

   public void pullComponentChanges(String compName) throws ScriptException {
      this.ctx.commandType = "pullComponentChanges";

      try {
         if (compName == null || compName.trim().isEmpty()) {
            this.ctx.println(this.txtFmt.getInvalidCAMComponentName());
            return;
         }

         SystemComponentMBean comp = this.getSystemComponentByName(this.ctx.edit.domainMBean, compName);
         if (comp == null) {
            this.ctx.println(this.txtFmt.getCAMComponentNotFound(compName));
            return;
         }

         FileChange[] changes = this.ctx.getConfigManager().updateConfigurationFromRemoteSystem(comp);
         MachineMBean machine = comp.getMachine();
         String machineName = machine == null ? "null" : machine.getName();
         if (changes != null && changes.length != 0) {
            this.ctx.println(this.txtFmt.getPullComponentChanges(compName, machineName));
            FileChange[] var6 = changes;
            int var7 = changes.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               FileChange change = var6[var8];
               this.ctx.println(change.getOperation() + " " + change.getPath().substring(28));
            }
         } else {
            this.ctx.println(this.txtFmt.getPullComponentChangesNotFound(compName, machineName));
         }
      } catch (Exception var10) {
         this.ctx.throwWLSTException(var10.getMessage(), var10);
      }

   }

   public void enableOverwriteComponentChanges() throws ScriptException {
      this.ctx.commandType = "enableOverwriteComponentChanges";
      if (!this.ctx.isNamedEditSessionAvailable) {
         this.ctx.throwWLSTException(this.txtFmt.getCommandNotRunInPreVersion(this.ctx.commandType));
      }

      try {
         this.ctx.getConfigManager().enableOverwriteComponentChanges();
      } catch (Exception var2) {
         this.ctx.throwWLSTException(var2.getMessage(), var2);
      }

   }

   private SystemComponentMBean getSystemComponentByName(DomainMBean domain, String compName) throws Exception {
      SystemComponentMBean[] comps = domain.getSystemComponents();
      SystemComponentMBean[] var4 = comps;
      int var5 = comps.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         SystemComponentMBean comp = var4[var6];
         if (compName.equals(comp.getName())) {
            return comp;
         }
      }

      return null;
   }

   private SystemComponentConfigurationMBean getSystemComponentConfigurationByName(DomainMBean domain, String compName) throws Exception {
      SystemComponentConfigurationMBean[] comps = domain.getSystemComponentConfigurations();
      SystemComponentConfigurationMBean[] var4 = comps;
      int var5 = comps.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         SystemComponentConfigurationMBean comp = var4[var6];
         if (compName.equals(comp.getName())) {
            return comp;
         }
      }

      return null;
   }

   public void showComponentChanges(String compName) throws ScriptException {
      this.ctx.commandType = "showComponentChanges";
      if (!this.ctx.isNamedEditSessionAvailable) {
         this.ctx.println(this.txtFmt.getCommandNotRunInPreVersion(this.ctx.commandType));
      } else {
         try {
            DomainMBean domain = this.ctx.isEditSessionInProgress ? this.ctx.edit.domainMBean : this.ctx.runtimeDomainMBean;
            SystemComponentMBean[] comps;
            if (compName != null && !compName.trim().isEmpty()) {
               SystemComponentMBean compMBean = this.getSystemComponentByName(domain, compName);
               if (compMBean == null) {
                  this.ctx.println(this.txtFmt.getCAMComponentNotFound(compName));
                  return;
               }

               comps = new SystemComponentMBean[]{compMBean};
            } else {
               comps = domain.getSystemComponents();
            }

            SystemComponentMBean[] var20 = comps;
            int var5 = comps.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               SystemComponentMBean comp = var20[var6];
               String name = comp.getName();
               MachineMBean machine = comp.getMachine();
               String machineName = machine == null ? "null" : machine.getName();

               FileChange[] changes;
               try {
                  changes = this.ctx.getConfigManager().getRemoteFileChanges(comp);
               } catch (UnsupportedOperationException var17) {
                  this.ctx.println(this.txtFmt.getShowComponentChangesNotSupport(name, machineName));
                  continue;
               } catch (IOException var18) {
                  this.ctx.println(this.txtFmt.getShowComponentChangesError(name, machineName, var18.getMessage()));
                  continue;
               }

               if (changes != null && changes.length != 0) {
                  SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
                  this.ctx.println(this.txtFmt.getShowComponentChanges(name, machineName));
                  FileChange[] var13 = changes;
                  int var14 = changes.length;

                  for(int var15 = 0; var15 < var14; ++var15) {
                     FileChange chg = var13[var15];
                     this.ctx.println(chg.getOperation() + " " + chg.getPath().substring(28) + " " + sdf.format(chg.getCurrentLastModifiedTime()) + " " + sdf.format(chg.getProposedLastModifiedTime()));
                  }
               } else {
                  this.ctx.println(this.txtFmt.getShowComponentChangesNoChangeFound(name, machineName));
               }
            }
         } catch (Exception var19) {
            this.ctx.throwWLSTException(var19.getMessage(), var19);
         }

      }
   }

   private class ChangedBeanInfo {
      private String beanInstanceName;
      private String changedAttributeName;

      public ChangedBeanInfo(String beanInstanceName, String changedAttrobuteName) {
         this.beanInstanceName = beanInstanceName;
         this.changedAttributeName = changedAttrobuteName;
      }

      public String getBeanInstanceName() {
         return this.beanInstanceName;
      }

      public String getChangedAttributeName() {
         return this.changedAttributeName;
      }

      public int hashCode() {
         int prime = true;
         int result = 1;
         result = 31 * result + (this.beanInstanceName == null ? 0 : this.beanInstanceName.hashCode());
         result = 31 * result + (this.changedAttributeName == null ? 0 : this.changedAttributeName.hashCode());
         return result;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            ChangedBeanInfo other = (ChangedBeanInfo)obj;
            if (this.beanInstanceName == null) {
               if (other.beanInstanceName != null) {
                  return false;
               }
            } else if (!this.beanInstanceName.equals(other.beanInstanceName)) {
               return false;
            }

            if (this.changedAttributeName == null) {
               if (other.changedAttributeName != null) {
                  return false;
               }
            } else if (!this.changedAttributeName.equals(other.changedAttributeName)) {
               return false;
            }

            return true;
         }
      }
   }
}
