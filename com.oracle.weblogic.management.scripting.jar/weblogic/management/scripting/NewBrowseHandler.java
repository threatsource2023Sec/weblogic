package weblogic.management.scripting;

import java.util.Iterator;
import java.util.Stack;
import javax.management.AttributeNotFoundException;
import javax.management.ObjectName;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;

public class NewBrowseHandler {
   WLScriptContext ctx = null;
   private WLSTMsgTextFormatter txtFmt;
   boolean runtimeRuntimeNavigatedBefore = false;
   boolean configRuntimeNavigatedBefore = false;
   boolean configDomainRuntimeNavigatedBefore = false;
   boolean runtimeDomainRuntimeNavigatedBefore = false;
   boolean jndiNavigatedBefore = false;
   boolean editNavigatedBefore = false;
   boolean runtimeRuntimeNavigateFirstTime = true;
   boolean configRuntimeNavigateFirstTime = true;
   boolean configDomainRuntimeNavigateFirstTime = true;
   boolean runtimeDomainRuntimeNavigateFirstTime = true;
   boolean editNavigateFirstTime = true;

   public NewBrowseHandler(WLScriptContext ctx) {
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   public void configRuntime() throws ScriptException {
      if (!this.ctx.isRuntimeServerEnabled) {
         this.ctx.println(this.txtFmt.getConfigRuntimeServerNotEnabled());
      } else {
         try {
            WLScriptContext var10001;
            if (!this.configRuntimeNavigateFirstTime) {
               var10001 = this.ctx;
               if (this.ctx.domainType.equals("RuntimeConfigServerDomain")) {
                  this.ctx.println(this.txtFmt.getAlreadyInConfigRuntime());
                  return;
               }
            }

            if (!this.configRuntimeNavigatedBefore) {
               if (!WLSTHelper.globalMBeansVisibleToPartitions && this.ctx.partitionName != null && !this.ctx.partitionName.equals("DOMAIN")) {
                  this.ctx.println(this.txtFmt.getLocationChangedToPartitionRoot());
               } else {
                  this.ctx.println(this.txtFmt.getLocationChangedToConfigRuntime());
               }
            }

            this.configRuntimeNavigatedBefore = true;
            this.saveLastPlaceInPreviousTree();
            this.ctx.wlcmo = this.ctx.runtimeDomainMBean;
            this.configRuntimeNavigateFirstTime = false;
            this.ctx.prompts = new Stack();
            this.ctx.beans = new Stack();
            this.ctx.beans.add(this.ctx.wlcmo);
            this.ctx.prompt = "";
            var10001 = this.ctx;
            this.ctx.domainType = "RuntimeConfigServerDomain";
            this.ctx.browseHandler.changeToBeanLevel();
            this.goToLastPlaceInCurrentTree();
            this.ctx.mbs = this.ctx.getMBSConnection((String)null);
         } catch (Throwable var2) {
            if (var2 instanceof ScriptException) {
               throw (ScriptException)var2;
            }

            this.ctx.throwWLSTException(this.txtFmt.getErrorTraversingToConfigRuntime(), var2);
         }

      }
   }

   void goToLastPlaceInCurrentTree() throws ScriptException {
      WLScriptContext var10001 = this.ctx;
      if (this.ctx.domainType.equals("Domain") && this.ctx.lastPlaceInConfig.length() != 0) {
         this.ctx.browseHandler.cd(this.ctx.lastPlaceInConfig);
      } else {
         var10001 = this.ctx;
         if (this.ctx.domainType.equals("DomainConfig") && this.ctx.lastPlaceInAdminConfig.length() != 0) {
            this.ctx.browseHandler.cd(this.ctx.lastPlaceInAdminConfig);
         } else {
            var10001 = this.ctx;
            if (this.ctx.domainType.equals("DomainRuntime") && this.ctx.lastPlaceInRuntime.length() != 0) {
               this.ctx.browseHandler.cd(this.ctx.lastPlaceInRuntime);
            } else {
               var10001 = this.ctx;
               if (this.ctx.domainType.equals("RuntimeConfigServerDomain") && this.ctx.lastPlaceInConfigRuntime.length() != 0) {
                  this.ctx.browseHandler.cd(this.ctx.lastPlaceInConfigRuntime);
               } else {
                  var10001 = this.ctx;
                  if (this.ctx.domainType.equals("RuntimeRuntimeServerDomain") && this.ctx.lastPlaceInRuntimeRuntime.length() != 0) {
                     this.ctx.browseHandler.cd(this.ctx.lastPlaceInRuntimeRuntime);
                  } else {
                     var10001 = this.ctx;
                     if (this.ctx.domainType.equals("RuntimeDomainRuntime") && this.ctx.lastPlaceInRuntimeDomainRuntime.length() != 0) {
                        this.ctx.browseHandler.cd(this.ctx.lastPlaceInRuntimeDomainRuntime);
                     } else {
                        var10001 = this.ctx;
                        if (this.ctx.domainType.equals("ConfigDomainRuntime") && this.ctx.lastPlaceInConfigDomainRuntime.length() != 0) {
                           this.ctx.browseHandler.cd(this.ctx.lastPlaceInConfigDomainRuntime);
                        } else {
                           var10001 = this.ctx;
                           if (this.ctx.domainType.equals("JNDI") && this.ctx.lastPlaceInJNDI.length() != 0) {
                              this.ctx.browseHandler.cd(this.ctx.lastPlaceInJNDI);
                           } else {
                              var10001 = this.ctx;
                              if (this.ctx.domainType.equals("Custom_Domain") && this.ctx.lastPlaceInCustom.length() != 0) {
                                 this.ctx.browseHandler.cd(this.ctx.lastPlaceInCustom);
                              } else {
                                 var10001 = this.ctx;
                                 if (this.ctx.domainType.equals("DomainCustom_Domain") && this.ctx.lastPlaceInDomainCustom.length() != 0) {
                                    this.ctx.browseHandler.cd(this.ctx.lastPlaceInDomainCustom);
                                 } else {
                                    var10001 = this.ctx;
                                    if (this.ctx.domainType.equals("EditCustom_Domain") && this.ctx.lastPlaceInEditCustom.length() != 0) {
                                       this.ctx.browseHandler.cd(this.ctx.lastPlaceInEditCustom);
                                    } else {
                                       var10001 = this.ctx;
                                       if (this.ctx.domainType.equals("ConfigEdit") && this.ctx.edit != null && this.ctx.edit.lastPlaceInEdit.length() != 0) {
                                          this.ctx.browseHandler.cd(this.ctx.edit.lastPlaceInEdit);
                                       } else {
                                          var10001 = this.ctx;
                                          if (this.ctx.domainType.equals("JSR77") && this.ctx.lastPlaceInJSR77.length() != 0) {
                                             this.ctx.browseHandler.cd(this.ctx.lastPlaceInJSR77);
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   void saveLastPlaceInPreviousTree() throws ScriptException {
      String currentPrompt = this.ctx.getPrompt();
      WLScriptContext var10001 = this.ctx;
      if (this.ctx.domainType.equals("Domain")) {
         this.ctx.lastPlaceInConfig = currentPrompt;
      } else {
         var10001 = this.ctx;
         if (this.ctx.domainType.equals("DomainConfig")) {
            this.ctx.lastPlaceInAdminConfig = currentPrompt;
         } else {
            var10001 = this.ctx;
            if (!this.ctx.domainType.equals("DomainRuntime")) {
               var10001 = this.ctx;
               if (this.ctx.domainType.equals("RuntimeConfigServerDomain")) {
                  this.ctx.lastPlaceInConfigRuntime = currentPrompt;
               } else {
                  var10001 = this.ctx;
                  if (this.ctx.domainType.equals("RuntimeRuntimeServerDomain")) {
                     this.ctx.lastPlaceInRuntimeRuntime = currentPrompt;
                  } else {
                     var10001 = this.ctx;
                     if (this.ctx.domainType.equals("RuntimeDomainRuntime")) {
                        this.ctx.lastPlaceInRuntimeDomainRuntime = currentPrompt;
                     } else {
                        var10001 = this.ctx;
                        if (this.ctx.domainType.equals("ConfigDomainRuntime")) {
                           this.ctx.lastPlaceInConfigDomainRuntime = currentPrompt;
                        } else {
                           var10001 = this.ctx;
                           if (this.ctx.domainType.equals("ConfigEdit") && this.ctx.edit != null) {
                              this.ctx.edit.lastPlaceInEdit = currentPrompt;
                           } else {
                              var10001 = this.ctx;
                              if (this.ctx.domainType.equals("JSR77")) {
                                 this.ctx.lastPlaceInJSR77 = currentPrompt;
                              } else {
                                 var10001 = this.ctx;
                                 if (this.ctx.domainType.equals("Custom_Domain")) {
                                    this.ctx.lastPlaceInCustom = currentPrompt;
                                 } else {
                                    var10001 = this.ctx;
                                    if (this.ctx.domainType.equals("DomainCustom_Domain")) {
                                       this.ctx.lastPlaceInDomainCustom = currentPrompt;
                                    } else {
                                       var10001 = this.ctx;
                                       if (this.ctx.domainType.equals("EditCustom_Domain")) {
                                          this.ctx.lastPlaceInEditCustom = currentPrompt;
                                       } else {
                                          var10001 = this.ctx;
                                          if (this.ctx.domainType.equals("JNDI")) {
                                             this.ctx.lastPlaceInJNDI = currentPrompt;
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            } else {
               if (this.ctx.isAdminServer) {
                  this.ctx.lastPlaceInRuntime = currentPrompt;
               } else if (this.ctx.prompts.size() == 2) {
                  this.ctx.lastPlaceInRuntime = "/";
               } else {
                  this.ctx.prompts.remove(0);
                  this.ctx.prompts.remove(0);
                  currentPrompt = "";

                  for(Iterator i = this.ctx.prompts.iterator(); i.hasNext(); currentPrompt = currentPrompt + "/" + (String)i.next() + "/") {
                  }

                  this.ctx.lastPlaceInRuntime = currentPrompt;
               }

            }
         }
      }
   }

   public void runtimeRuntime() throws ScriptException {
      if (!this.ctx.isRuntimeServerEnabled) {
         this.ctx.println(this.txtFmt.getRuntimeServerNotEnabled());
      } else {
         try {
            WLScriptContext var10001;
            if (!this.runtimeRuntimeNavigateFirstTime) {
               var10001 = this.ctx;
               if (this.ctx.domainType.equals("RuntimeRuntimeServerDomain")) {
                  this.ctx.println(this.txtFmt.getAlreadyInServerRuntime());
                  return;
               }
            }

            if (!this.runtimeRuntimeNavigatedBefore) {
               if (!WLSTHelper.globalMBeansVisibleToPartitions && this.ctx.partitionName != null && !this.ctx.partitionName.equals("DOMAIN")) {
                  this.ctx.println(this.txtFmt.getLocationChangedToPartitionRuntimeRoot());
               } else {
                  this.ctx.println(this.txtFmt.getLocationChangedToServerRuntime());
               }
            }

            this.runtimeRuntimeNavigatedBefore = true;
            this.saveLastPlaceInPreviousTree();
            this.ctx.wlcmo = this.ctx.runtimeServerRuntimeMBean;
            this.ctx.prompts = new Stack();
            this.ctx.beans = new Stack();
            this.ctx.beans.add(this.ctx.wlcmo);
            this.ctx.prompt = "";
            var10001 = this.ctx;
            this.ctx.domainType = "RuntimeRuntimeServerDomain";
            this.ctx.browseHandler.changeToBeanLevel();
            this.goToLastPlaceInCurrentTree();
            this.ctx.mbs = this.ctx.getMBSConnection((String)null);
            if (!WLSTHelper.globalMBeansVisibleToPartitions && this.runtimeRuntimeNavigateFirstTime && this.ctx.partitionName != null && !this.ctx.partitionName.equals("DOMAIN")) {
               PartitionRuntimeMBean partitionRuntimeMBean = this.ctx.runtimeServerRuntimeMBean.lookupPartitionRuntime(this.ctx.partitionName);
               if (partitionRuntimeMBean != null && this.ctx.lastPlaceInRuntimeRuntime.isEmpty()) {
                  this.ctx.browseHandler.cd("PartitionRuntimes/" + this.ctx.partitionName);
               }
            }

            this.runtimeRuntimeNavigateFirstTime = false;
         } catch (Throwable var2) {
            if (var2 instanceof ScriptException) {
               throw (ScriptException)var2;
            }

            this.ctx.throwWLSTException(this.txtFmt.getErrorTraversingToServerRuntime(), var2);
         }

      }
   }

   public void configDomainRuntime() throws ScriptException {
      if (!this.ctx.isAdminServer) {
         this.ctx.println(this.txtFmt.getDomainRuntimeNotAvailableOnMS());
      } else if (!this.ctx.isDomainRuntimeServerEnabled) {
         this.ctx.println(this.txtFmt.getDomainRuntimeServerNotEnabled());
      } else {
         try {
            WLScriptContext var10001 = this.ctx;
            if (this.ctx.domainType.equals("ConfigDomainRuntime")) {
               this.ctx.println(this.txtFmt.getAlreadyInDomainConfig());
            } else {
               if (!this.configDomainRuntimeNavigatedBefore) {
                  if (!WLSTHelper.globalMBeansVisibleToPartitions && this.ctx.partitionName != null && !this.ctx.partitionName.equals("DOMAIN")) {
                     this.ctx.println(this.txtFmt.getLocationChangedToDomainPartitionRuntimeRoot());
                  } else {
                     this.ctx.println(this.txtFmt.getLocationChangedToDomainConfig());
                  }
               }

               this.configDomainRuntimeNavigatedBefore = true;
               this.saveLastPlaceInPreviousTree();
               this.ctx.wlcmo = this.ctx.configDomainRuntimeDRMBean;
               var10001 = this.ctx;
               this.ctx.domainType = "ConfigDomainRuntime";
               this.ctx.browseHandler.initCommonVariables();
               this.goToLastPlaceInCurrentTree();
               this.ctx.mbs = this.ctx.getMBSConnection((String)null);
               if (!WLSTHelper.globalMBeansVisibleToPartitions && this.configDomainRuntimeNavigateFirstTime && this.ctx.partitionName != null && !this.ctx.partitionName.equals("DOMAIN") && this.ctx.lastPlaceInConfigDomainRuntime.isEmpty()) {
                  this.ctx.browseHandler.cd("Partitions/" + this.ctx.partitionName);
               }

               this.configDomainRuntimeNavigateFirstTime = false;
            }
         } catch (Throwable var2) {
            if (var2 instanceof ScriptException) {
               throw (ScriptException)var2;
            }

            this.ctx.throwWLSTException(this.txtFmt.getErrorTraversingToDomainConfig(), var2);
         }

      }
   }

   public void configEdit(String name) throws ScriptException {
      if (!this.ctx.isAdminServer) {
         this.ctx.println(this.txtFmt.getEditNotAvailableOnMS());
      } else if (this.ctx.edits.isEmpty()) {
         this.ctx.println(this.txtFmt.getEditServerNotEnabled());
      } else {
         try {
            WLScriptContext var10001 = this.ctx;
            if (this.ctx.domainType.equals("ConfigEdit") && this.ctx.edit != null && this.ctx.edit.equalsName(name)) {
               this.ctx.println(this.txtFmt.getAlreadyInEdit());
            } else {
               WLSTEditVariables edit = this.ctx.getEditByName(name, true);
               if (edit == null) {
                  this.ctx.throwWLSTException(this.txtFmt.getNamedEditSessionDoesNotExist());
               }

               this.saveLastPlaceInPreviousTree();
               this.ctx.setEdit(edit);
               if (!this.editNavigatedBefore) {
                  this.ctx.println(this.txtFmt.getLocationChangedToEdit());
                  this.editNavigatedBefore = true;
               }

               this.ctx.addEditChangeListener();
               this.ctx.wlcmo = this.ctx.edit.domainMBean;
               var10001 = this.ctx;
               this.ctx.domainType = "ConfigEdit";
               this.ctx.browseHandler.initCommonVariables();
               if (this.doesUserHasLock()) {
                  this.ctx.isEditSessionInProgress = true;
                  this.ctx.edit.isEditSessionInProgress = true;
                  this.ctx.println(this.txtFmt.getEditSessionInProgress());
               }

               this.goToLastPlaceInCurrentTree();
               this.ctx.mbs = this.ctx.getMBSConnection((String)null);
            }

            if (this.ctx.edit.isEditSessionInProgress && this.ctx.getConfigManager().isMergeNeeded()) {
               this.ctx.println(this.txtFmt.getEditConfigIsStale());
            }

            if (!WLSTHelper.globalMBeansVisibleToPartitions && this.editNavigateFirstTime && this.ctx.partitionName != null && !this.ctx.partitionName.equals("DOMAIN") && this.ctx.edit.lastPlaceInEdit.isEmpty()) {
               this.ctx.browseHandler.cd("Partitions/" + this.ctx.partitionName);
            }

            this.editNavigateFirstTime = false;
         } catch (Throwable var3) {
            if (var3 instanceof ScriptException) {
               throw (ScriptException)var3;
            }

            this.ctx.throwWLSTException(this.txtFmt.getErrorTraversingToEdit(), var3);
         }

      }
   }

   boolean doesUserHasLock() {
      return this.ctx.edit.configurationManager.isEditor() && (!this.ctx.edit.configurationManager.isCurrentEditorExclusive() || this.ctx.edit.isEditSessionExclusive);
   }

   public void jsr77() throws ScriptException {
      if (!this.ctx.isAdminServer) {
         this.ctx.println(this.txtFmt.getJSR77NotAvailableOnMS());
      } else if (this.ctx.edit == null) {
         this.ctx.println(this.txtFmt.getJSR77ServerNotEnabled());
      } else {
         this.ctx.println("Not implemented yet!");
      }
   }

   public void runtimeDomainRuntime() throws ScriptException {
      if (!this.ctx.isAdminServer) {
         this.ctx.println(this.txtFmt.getDomainRuntimeNotAvailableOnMS());
      } else if (!this.ctx.isDomainRuntimeServerEnabled) {
         this.ctx.println(this.txtFmt.getDomainRuntimeServerNotEnabled());
      } else {
         try {
            WLScriptContext var10001 = this.ctx;
            if (this.ctx.domainType.equals("RuntimeDomainRuntime")) {
               this.ctx.println(this.txtFmt.getAlreadyInDomainRuntime());
            } else {
               if (!this.runtimeDomainRuntimeNavigatedBefore) {
                  if (!WLSTHelper.globalMBeansVisibleToPartitions && this.ctx.partitionName != null && !this.ctx.partitionName.equals("DOMAIN")) {
                     this.ctx.println(this.txtFmt.getLocationChangedToDomainPartitionRuntimeRootRuntime());
                  } else {
                     this.ctx.println(this.txtFmt.getLocationChangedToDomainRuntime());
                  }
               }

               this.runtimeDomainRuntimeNavigatedBefore = true;
               this.saveLastPlaceInPreviousTree();
               this.ctx.wlcmo = this.ctx.runtimeDomainRuntimeDRMBean;
               var10001 = this.ctx;
               this.ctx.domainType = "RuntimeDomainRuntime";
               this.ctx.browseHandler.initCommonVariables();
               this.goToLastPlaceInCurrentTree();
               this.ctx.mbs = this.ctx.getMBSConnection((String)null);
               if (!WLSTHelper.globalMBeansVisibleToPartitions && this.runtimeDomainRuntimeNavigateFirstTime && this.ctx.partitionName != null && !this.ctx.partitionName.equals("DOMAIN") && this.ctx.lastPlaceInRuntimeDomainRuntime.isEmpty()) {
                  this.ctx.browseHandler.cd("DomainPartitionRuntimes/" + this.ctx.partitionName);
               }

               this.runtimeDomainRuntimeNavigateFirstTime = false;
            }
         } catch (Throwable var2) {
            if (var2 instanceof ScriptException) {
               throw (ScriptException)var2;
            }

            this.ctx.throwWLSTException(this.txtFmt.getErrorTraversingToDomainRuntime(), var2);
         }

      }
   }

   void pop() throws ScriptException {
      if (this.ctx.inMBeanType) {
         this.ctx.browseHandler.changeToBeanLevel();
         this.ctx.browseHandler.popAndPeek();
      } else if (this.ctx.inMBeanTypes) {
         this.ctx.browseHandler.changeToBeanLevel();
         this.ctx.browseHandler.popAndPeek();
      } else if (this.ctx.atBeanLevel) {
         this.ctx.browseHandler.popAndPeek();
         Object obj = null;
         String prevName = null;
         if (this.ctx.prompts.size() == 0) {
            this.ctx.prompt = this.ctx.evaluatePrompt();
            this.ctx.browseHandler.changeToBeanLevel();
            return;
         }

         prevName = (String)this.ctx.prompts.peek();

         try {
            obj = this.ctx.getMBSConnection(this.ctx.domainType).getAttribute(this.ctx.getObjectName(this.ctx.beans.peek()), prevName);
         } catch (AttributeNotFoundException var4) {
            this.ctx.prompt = this.ctx.evaluatePrompt();
            this.ctx.browseHandler.changeToBeanLevel();
            return;
         } catch (Throwable var5) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorCdingToBean(), var5);
         }

         if (obj instanceof ObjectName || obj instanceof ObjectName[]) {
            if (obj instanceof ObjectName) {
               this.ctx.wlInstanceObjName = (ObjectName)obj;
               this.ctx.browseHandler.populateNames(obj);
               this.ctx.browseHandler.changeToMBeanTypeLevel();
            } else {
               this.ctx.wlInstanceObjNames = (ObjectName[])((ObjectName[])obj);
               this.ctx.browseHandler.populateNames(obj);
               this.ctx.browseHandler.changeToMBeanTypesLevel();
            }

            this.ctx.prompt = this.ctx.evaluatePrompt();
         }
      }

      this.ctx.prompt = this.ctx.evaluatePrompt();
   }
}
