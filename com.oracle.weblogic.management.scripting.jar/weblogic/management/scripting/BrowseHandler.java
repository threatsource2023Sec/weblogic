package weblogic.management.scripting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.management.AttributeNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.NotificationFilter;
import javax.management.ObjectName;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.scripting.utils.ErrorInformation;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.utils.StringUtils;

public class BrowseHandler extends BrowseHelper {
   private boolean currentlyResetting = false;
   private WLSTMsgTextFormatter txtFmt;
   private static final String DOTDOT = "..";
   private static final String NAME_KEY = "Name";
   private static final String SLASH = "/";
   private static final String BACKSLASH = "\\";
   private static final String EMPTY_STRING = "";
   private ObjectName delegateObjectName;
   int slipCdCount = 0;

   public BrowseHandler(WLScriptContext ctx) {
      super(ctx);
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();

      try {
         this.delegateObjectName = new ObjectName("JMImplementation:type=MBeanServerDelegate");
      } catch (Exception var3) {
      }

   }

   void pop() throws ScriptException {
      try {
         WLScriptContext var10001 = this.ctx;
         if (this.ctx.domainType.equals("JNDI")) {
            this.handleJndiPop();
         } else {
            var10001 = this.ctx;
            if (this.ctx.domainType.equals("DomainRuntime") && !this.ctx.isAdminServer) {
               if (this.ctx.prompts.size() == 2) {
                  return;
               }
            } else if (this.ctx.prompts.size() == 0 || this.ctx.beans.size() == 0) {
               return;
            }

            var10001 = this.ctx;
            if (this.ctx.domainType.equals("Custom_Domain")) {
               this.handleCustomDomainPop();
            } else {
               var10001 = this.ctx;
               if (this.ctx.domainType.equals("DomainCustom_Domain")) {
                  this.handleDomainCustomDomainPop();
               } else {
                  var10001 = this.ctx;
                  if (this.ctx.domainType.equals("EditCustom_Domain")) {
                     this.handleEditCustomDomainPop();
                  } else if (this.delegateToDomainRuntimeHandler("..")) {
                     this.ctx.domainRuntimeHandler.pop();
                  } else if (this.delegateToServerRuntimeHandler("..")) {
                     this.ctx.serverRuntimeHandler.pop();
                  } else {
                     this.ctx.newBrowseHandler.pop();
                  }
               }
            }
         }
      } catch (Throwable var2) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorCdingToBean(), var2);
      }
   }

   private void splitPush(String[] strs, String delimiter) throws ScriptException {
      String curPrompt = this.ctx.prompt;

      for(int i = 0; i < strs.length; ++i) {
         try {
            if (this.slipCdCount > 0) {
               for(int j = 0; j < this.slipCdCount; ++j) {
                  ++i;
               }

               this.slipCdCount = 0;
            }

            if (i >= strs.length) {
               this.slipCdCount = 0;
            } else if (this.incrementCdCount) {
               this.incrementCdCount = false;
            } else {
               String restCdArgument = strs[i];
               if (i < strs.length - 1) {
                  this.nextCdValue = strs[i + 1];

                  for(int j = i + 1; j < strs.length; ++j) {
                     restCdArgument = restCdArgument + delimiter + strs[j];
                  }
               }

               this.regularPush(strs[i], restCdArgument);
               this.nextCdValue = "";
            }
         } catch (Throwable var7) {
            this.resetCD(curPrompt);
            throw new ScriptException(this.txtFmt.getErrorCdingToBean(), var7, "cd");
         }
      }

   }

   private void regularPush(String mname, String restCdArgument) throws ScriptException {
      if (!this.cdDone) {
         String currentPrompt = this.ctx.prompt;

         try {
            WLScriptContext var10001;
            if (mname.equals("..")) {
               var10001 = this.ctx;
               if (this.ctx.domainType.equals("JNDI")) {
                  this.handleJndiPop();
               } else {
                  this.pop();
               }

               return;
            }

            String tree = this.ctx.getTreeFromArgument(mname + "/");
            if (tree != null) {
               this.jumpTree(tree);
               this.takeBackToRoot();
               return;
            }

            var10001 = this.ctx;
            if (this.ctx.domainType.equals("JNDI")) {
               this.handleJndiCd(mname);
               return;
            }

            var10001 = this.ctx;
            if (this.ctx.domainType.equals("Custom_Domain")) {
               this.cdToCustomBeanInstance(mname, restCdArgument);
               return;
            }

            var10001 = this.ctx;
            if (this.ctx.domainType.equals("DomainCustom_Domain")) {
               this.cdToDomainCustomBeanInstance(mname, restCdArgument);
               return;
            }

            var10001 = this.ctx;
            if (this.ctx.domainType.equals("EditCustom_Domain")) {
               this.cdToEditCustomBeanInstance(mname, restCdArgument);
               return;
            }

            boolean fnd;
            if (this.ctx.inMBeanType) {
               fnd = false;
               String name;
               if (this.ctx.wlInstanceObjName == null) {
                  this.ctx.errorMsg = this.txtFmt.getAttributeNotFound(mname);
                  this.ctx.errorInfo = new ErrorInformation(this.ctx.errorMsg);
                  this.ctx.exceptionHandler.handleException(this.ctx.errorInfo);
               } else if (!this.ctx.wlInstanceObjName.getKeyProperty("Name").equals(mname) && !this.ctx.wlInstanceObjName_name.equals(mname)) {
                  if (this.ctx.wlInstanceObjName.getKeyProperty("Name").indexOf("/") != -1 || this.ctx.wlInstanceObjName.getKeyProperty("Name").indexOf("\\") != -1 || this.ctx.wlInstanceObjName_name.indexOf("/") != -1 || this.ctx.wlInstanceObjName_name.indexOf("\\") != -1) {
                     this.ctx.printDebug(this.txtFmt.getMBeanHasSlash());
                     name = this.ctx.wlInstanceObjName.getKeyProperty("Name");
                     if (name.equals(this.cdArgument) || this.ctx.wlInstanceObjName_name.equals(this.cdArgument)) {
                        this.ctx.prompts.add(this.ctx.wlInstanceObjName_name);
                        this.ctx.prompt = this.ctx.evaluatePrompt();
                        this.changeToBeanLevel();
                        this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(this.ctx.wlInstanceObjName);
                        this.ctx.beans.push(this.ctx.wlcmo);
                        this.cdDone = true;
                        fnd = true;
                     }
                  }
               } else {
                  this.ctx.prompts.add(mname);
                  this.ctx.prompt = this.ctx.evaluatePrompt();
                  this.changeToBeanLevel();
                  this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(this.ctx.wlInstanceObjName);
                  this.ctx.beans.push(this.ctx.wlcmo);
                  fnd = true;
               }

               if (!fnd && (this.ctx.wlInstanceObjName.getKeyProperty("Name").indexOf("/") != -1 || this.ctx.wlInstanceObjName.getKeyProperty("Name").indexOf("\\") != -1 || this.ctx.wlInstanceObjName_name.indexOf("/") != -1 || this.ctx.wlInstanceObjName_name.indexOf("\\") != -1)) {
                  this.ctx.printDebug(this.txtFmt.getMBeanHasSlash());
                  name = this.ctx.wlInstanceObjName.getKeyProperty("Name");
                  if (this.ctx.wlInstanceObjName_name.equals(mname + "/" + this.nextCdValue) || restCdArgument != null && restCdArgument.indexOf(this.ctx.wlInstanceObjName_name) != -1) {
                     this.ctx.prompts.add(this.ctx.wlInstanceObjName_name);
                     this.ctx.prompt = this.ctx.evaluatePrompt();
                     this.changeToBeanLevel();
                     this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(this.ctx.wlInstanceObjName);
                     this.ctx.beans.push(this.ctx.wlcmo);
                     fnd = true;
                     this.slipCdCount = this.determineSlipCount(this.ctx.wlInstanceObjName_name);
                     if (this.slipCdCount != 0 && this.slipCdCount != 1) {
                        --this.slipCdCount;
                     } else {
                        this.slipCdCount = 0;
                     }
                  }
               }

               if (!fnd) {
                  this.ctx.throwWLSTException(this.txtFmt.getAttributeNotFound(mname));
               }
            } else if (!this.ctx.inMBeanTypes) {
               if (this.ctx.atBeanLevel) {
                  Object obj = null;
                  if (this.delegateToDomainRuntimeHandler(mname)) {
                     this.ctx.domainRuntimeHandler.cd(mname);
                     return;
                  }

                  if (this.delegateToServerRuntimeHandler(mname)) {
                     this.ctx.serverRuntimeHandler.cd(mname);
                     return;
                  }

                  obj = this.ctx.getMBSConnection(this.ctx.domainType).getAttribute(this.ctx.getObjectName(), mname);
                  if (obj != null && !(obj instanceof ObjectName) && !(obj instanceof ObjectName[])) {
                     this.ctx.throwWLSTException(this.txtFmt.getCannotCDToAttribute(mname));
                  } else {
                     if (obj instanceof ObjectName) {
                        if (this.ctx.skipSingletons) {
                           this.ctx.prompts.add(mname);
                           this.ctx.prompt = this.ctx.evaluatePrompt();
                           this.changeToBeanLevel();
                           this.ctx.wlcmo = this.ctx.getMBeanFromObjectName((ObjectName)obj);
                           this.ctx.beans.push(this.ctx.wlcmo);
                           return;
                        }

                        this.ctx.wlInstanceObjName = (ObjectName)obj;
                        this.changeToMBeanTypeLevel();
                        this.populateNames(obj);
                     } else if (obj instanceof ObjectName[]) {
                        this.ctx.wlInstanceObjNames = (ObjectName[])((ObjectName[])obj);
                        this.changeToMBeanTypesLevel();
                        this.populateNames(obj);
                     } else if (this.ctx.isPlural(mname)) {
                        this.ctx.wlInstanceObjNames = (ObjectName[])((ObjectName[])obj);
                        this.changeToMBeanTypesLevel();
                     } else {
                        this.ctx.wlInstanceObjName = (ObjectName)obj;
                        this.changeToMBeanTypeLevel();
                     }

                     this.ctx.prompts.add(mname);
                     this.ctx.beans.push(this.ctx.wlcmo);
                     this.ctx.prompt = this.ctx.evaluatePrompt();
                  }
               }
            } else {
               if (this.delegateToDomainRuntimeHandler(mname)) {
                  this.ctx.domainRuntimeHandler.cd(mname);
                  return;
               }

               if (this.ctx.wlInstanceObjNames == null) {
                  this.ctx.throwWLSTException(this.txtFmt.getAttributeNotFound(mname));
               }

               fnd = false;

               int k;
               ObjectName objName;
               for(k = 0; k < this.ctx.wlInstanceObjNames.length; ++k) {
                  if (this.ctx.wlInstanceObjNames[k] != null) {
                     objName = this.ctx.wlInstanceObjNames[k];
                     if (objName.getKeyProperty("Name").equals(this.cdArgument) || this.ctx.wlInstanceObjNames_names[k].equals(this.cdArgument)) {
                        this.ctx.prompts.add(this.cdArgument);
                        this.ctx.prompt = this.ctx.evaluatePrompt();
                        this.changeToBeanLevel();
                        this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(objName);
                        this.ctx.beans.push(this.ctx.wlcmo);
                        fnd = true;
                        this.cdDone = true;
                        break;
                     }
                  }
               }

               if (!fnd) {
                  for(k = 0; k < this.ctx.wlInstanceObjNames.length; ++k) {
                     objName = this.ctx.wlInstanceObjNames[k];
                     if (this.ctx.wlInstanceObjNames[k] != null && (objName.getKeyProperty("Name").equals(mname) || this.ctx.wlInstanceObjNames_names[k].equals(mname))) {
                        for(int k = 0; k < this.ctx.wlInstanceObjNames.length; ++k) {
                           ObjectName _oname = this.ctx.wlInstanceObjNames[k];
                           if (this.ctx.wlInstanceObjNames[k] != null && (_oname.getKeyProperty("Name").equals(mname + "/" + this.nextCdValue) || this.ctx.wlInstanceObjNames_names[k].equals(mname + "/" + this.nextCdValue))) {
                              this.ctx.prompts.add(mname + "/" + this.nextCdValue);
                              this.ctx.prompt = this.ctx.evaluatePrompt();
                              this.changeToBeanLevel();
                              this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(_oname);
                              this.ctx.beans.push(this.ctx.wlcmo);
                              fnd = true;
                              this.incrementCdCount = true;
                              break;
                           }
                        }

                        if (!fnd) {
                           this.ctx.prompts.add(mname);
                           this.ctx.prompt = this.ctx.evaluatePrompt();
                           this.changeToBeanLevel();
                           this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(objName);
                           this.ctx.beans.push(this.ctx.wlcmo);
                           fnd = true;
                           break;
                        }
                     }
                  }
               }

               String oname;
               String _oname;
               if (!fnd) {
                  for(k = 0; k < this.ctx.wlInstanceObjNames.length; ++k) {
                     objName = this.ctx.wlInstanceObjNames[k];
                     oname = objName.getKeyProperty("Name");
                     _oname = this.ctx.wlInstanceObjNames_names[k];
                     if ((oname.indexOf("/") != -1 || oname.indexOf("\\") != -1 || _oname.indexOf("/") != -1 || _oname.indexOf("\\") != -1) && (oname.equals(this.cdArgument) || _oname.equals(this.cdArgument))) {
                        this.ctx.prompts.add(_oname);
                        this.ctx.prompt = this.ctx.evaluatePrompt();
                        this.changeToBeanLevel();
                        this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(objName);
                        this.ctx.beans.push(this.ctx.wlcmo);
                        fnd = true;
                        this.cdDone = true;
                        break;
                     }
                  }
               }

               if (!fnd) {
                  for(k = 0; k < this.ctx.wlInstanceObjNames.length; ++k) {
                     objName = this.ctx.wlInstanceObjNames[k];
                     oname = objName.getKeyProperty("Name");
                     _oname = this.ctx.wlInstanceObjNames_names[k];
                     if ((oname.indexOf("/") != -1 || oname.indexOf("\\") != -1 || _oname.indexOf("/") != -1 || _oname.indexOf("\\") != -1) && (oname.indexOf(mname) != -1 || _oname.indexOf(mname) != -1) && (this.cdArgument.indexOf(oname) != -1 || this.cdArgument.indexOf(_oname) != -1)) {
                        this.ctx.prompts.add(_oname);
                        this.ctx.prompt = this.ctx.evaluatePrompt();
                        this.changeToBeanLevel();
                        this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(objName);
                        this.ctx.beans.push(this.ctx.wlcmo);
                        fnd = true;
                        this.slipCdCount = this.determineSlipCount(_oname);
                        if (this.slipCdCount != 0 && this.slipCdCount != 1) {
                           --this.slipCdCount;
                        } else {
                           this.slipCdCount = 0;
                        }
                        break;
                     }
                  }
               }

               if (!fnd) {
                  this.ctx.throwWLSTException(this.txtFmt.getAttributeNotFound(mname));
               }
            }
         } catch (MBeanException var10) {
            if (var10.getTargetException() instanceof AttributeNotFoundException) {
               this.resetCD(currentPrompt);
               this.ctx.throwWLSTException(this.txtFmt.getAttributeNotFound(mname), var10.getTargetException());
            } else {
               this.resetCD(currentPrompt);
               this.ctx.throwWLSTException(this.txtFmt.getMBeanExceptionOccurred(), var10);
            }
         } catch (Throwable var11) {
            this.resetCD(currentPrompt);
            if (var11 instanceof ScriptException) {
               throw (ScriptException)var11;
            }

            this.ctx.throwWLSTException(this.txtFmt.getErrorBrowsingBeans(), var11);
         }

      }
   }

   private synchronized void resetCD(String curPrompt) throws ScriptException {
      if (!this.currentlyResetting) {
         try {
            this.currentlyResetting = true;
            this.takeBackToRoot();
            this.cd(curPrompt);
         } finally {
            this.currentlyResetting = false;
         }

      }
   }

   void jumpTree(String tree) throws ScriptException {
      if (!this.ctx.getCurrentTree().equals(tree)) {
         WLScriptContext var10001 = this.ctx;
         if (tree.equals("custom")) {
            this.custom((String)null);
         }

         var10001 = this.ctx;
         if (tree.equals("domainCustom")) {
            this.domainCustom((String)null);
         }

         var10001 = this.ctx;
         if (tree.equals("editCustom")) {
            this.editCustom((String)null);
         }

         var10001 = this.ctx;
         if (tree.equals("jndi")) {
            this.jndi((String)null);
         }

         var10001 = this.ctx;
         if (tree.equals("serverConfig")) {
            this.ctx.newBrowseHandler.configRuntime();
         }

         var10001 = this.ctx;
         if (tree.equals("serverRuntime")) {
            this.ctx.newBrowseHandler.runtimeRuntime();
         }

         var10001 = this.ctx;
         if (tree.equals("domainConfig")) {
            this.ctx.newBrowseHandler.configDomainRuntime();
         }

         var10001 = this.ctx;
         if (tree.equals("domainRuntime")) {
            this.ctx.newBrowseHandler.runtimeDomainRuntime();
         }

         var10001 = this.ctx;
         if (tree.equals("edit")) {
            this.ctx.newBrowseHandler.configEdit((String)null);
         }

      }
   }

   void custom(String pattern) throws ScriptException {
      try {
         WLScriptContext var10001 = this.ctx;
         if (!this.ctx.domainType.equals("Custom_Domain")) {
            this.ctx.println(this.txtFmt.getLocationChangedToCustomTree());
            this.ctx.println(this.txtFmt.getUseCustomHelp());
            this.ctx.newBrowseHandler.saveLastPlaceInPreviousTree();
            this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
            var10001 = this.ctx;
            this.ctx.domainType = "Custom_Domain";
            this.initCommonVariables();
            this.ctx.atDomainLevel = true;
            if (this.ctx.customMBeanListener == null) {
               this.ctx.customMBeanListener = new CustomMBeanChangeListener(this.ctx.customMBeanDomainObjNameMap);
               MBeanServerConnection mbs = this.ctx.getMBSConnection("Custom_Domain");
               if (mbs != null) {
                  mbs.addNotificationListener(this.delegateObjectName, this.ctx.customMBeanListener, (NotificationFilter)null, (Object)null);
               }
            }

            this.ctx.getCustomMBeans(pattern);
            this.ctx.newBrowseHandler.goToLastPlaceInCurrentTree();
            this.ctx.mbs = this.ctx.getMBSConnection((String)null);
         } else {
            this.ctx.println(this.txtFmt.getAlreadyInTree("custom"));
         }
      } catch (Throwable var3) {
         if (var3 instanceof ScriptException) {
            throw (ScriptException)var3;
         }

         this.ctx.throwWLSTException(this.txtFmt.getErrorBrowsingTree("custom"), var3);
      }

   }

   void domainCustom(String pattern) throws ScriptException {
      if (!this.ctx.isAdminServer) {
         this.ctx.println(this.txtFmt.getDomainCustomCommandNotOnMS());
      } else if (!this.ctx.isDomainRuntimeServerEnabled) {
         this.ctx.println(this.txtFmt.getDomainRuntimeServerNotEnabled());
      } else {
         try {
            WLScriptContext var10001 = this.ctx;
            if (!this.ctx.domainType.equals("DomainCustom_Domain")) {
               this.ctx.println(this.txtFmt.getLocationChangedToDomainCustomTree());
               this.ctx.println(this.txtFmt.getUseDomainCustomHelp());
               this.ctx.newBrowseHandler.saveLastPlaceInPreviousTree();
               this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
               var10001 = this.ctx;
               this.ctx.domainType = "DomainCustom_Domain";
               this.initCommonVariables();
               this.ctx.atDomainLevel = true;
               if (this.ctx.domainCustomMBeanListener == null) {
                  this.ctx.domainCustomMBeanListener = new CustomMBeanChangeListener(this.ctx.domainCustomMBeanDomainObjNameMap);
                  MBeanServerConnection mbs = this.ctx.getMBSConnection("DomainCustom_Domain");
                  if (mbs != null) {
                     mbs.addNotificationListener(this.delegateObjectName, this.ctx.domainCustomMBeanListener, (NotificationFilter)null, (Object)null);
                  }
               }

               this.ctx.getDomainCustomMBeans(pattern);
               this.ctx.newBrowseHandler.goToLastPlaceInCurrentTree();
               this.ctx.mbs = this.ctx.getMBSConnection((String)null);
            } else {
               this.ctx.println(this.txtFmt.getAlreadyInTree("domainCustom"));
            }
         } catch (Throwable var3) {
            if (var3 instanceof ScriptException) {
               throw (ScriptException)var3;
            }

            this.ctx.throwWLSTException(this.txtFmt.getErrorBrowsingTree("domainCustom"), var3);
         }

      }
   }

   void editCustom(String pattern) throws ScriptException {
      if (!this.ctx.isAdminServer) {
         this.ctx.println(this.txtFmt.getEditCustomCommandNotOnMS());
      } else if (this.ctx.edits.isEmpty()) {
         this.ctx.println(this.txtFmt.getEditServerNotEnabled());
      } else {
         try {
            WLScriptContext var10001 = this.ctx;
            if (!this.ctx.domainType.equals("EditCustom_Domain")) {
               this.ctx.println(this.txtFmt.getLocationChangedToEditCustomTree());
               this.ctx.println(this.txtFmt.getUseEditCustomHelp());
               this.ctx.newBrowseHandler.saveLastPlaceInPreviousTree();
               if (this.ctx.edit != null && !this.ctx.edit.isGlobalSession()) {
                  this.ctx.setEdit((WLSTEditVariables)this.ctx.edits.get((Object)null));
               }

               this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
               var10001 = this.ctx;
               this.ctx.domainType = "EditCustom_Domain";
               this.initCommonVariables();
               this.ctx.atDomainLevel = true;
               if (this.ctx.editCustomMBeanListener == null) {
                  this.ctx.editCustomMBeanListener = new CustomMBeanChangeListener(this.ctx.editCustomMBeanDomainObjNameMap);
                  MBeanServerConnection mbs = this.ctx.getMBSConnection("ConfigEdit");
                  if (mbs != null) {
                     mbs.addNotificationListener(this.delegateObjectName, this.ctx.editCustomMBeanListener, (NotificationFilter)null, (Object)null);
                  }
               }

               this.ctx.getEditCustomMBeans(pattern);
               this.ctx.newBrowseHandler.goToLastPlaceInCurrentTree();
               this.ctx.mbs = this.ctx.getMBSConnection((String)null);
            } else {
               this.ctx.println(this.txtFmt.getAlreadyInTree("editCustom"));
            }
         } catch (Throwable var3) {
            if (var3 instanceof ScriptException) {
               throw (ScriptException)var3;
            }

            this.ctx.throwWLSTException(this.txtFmt.getErrorBrowsingTree("editCustom"), var3);
         }

      }
   }

   void takeBackToRoot() throws ScriptException {
      if (this.ctx.debug) {
         this.ctx.printDebug(this.txtFmt.getBrowsingBackToRoot());
      }

      this.reset();
   }

   private void handleJndiCd(String mname) throws ScriptException {
      try {
         if (this.ctx.prompts.size() == 0) {
            if (!this.ctx.isAdminServer) {
               ServerRuntimeMBean bean = this.ctx.runtimeServerRuntimeMBean;
               if (bean.getName().equals(mname)) {
                  this.ctx.prompts.add(mname);
                  this.ctx.prompt = this.ctx.evaluatePrompt();
                  return;
               }

               this.ctx.throwWLSTException(this.txtFmt.getCannotFindJndiEntry(mname));
            } else {
               ServerRuntimeMBean[] beans = this.ctx.domainRuntimeServiceMBean.getServerRuntimes();
               int i = 0;

               while(true) {
                  if (i >= beans.length) {
                     this.ctx.throwWLSTException(this.txtFmt.getCannotFindJndiEntry(mname));
                     break;
                  }

                  if (beans[i].getName().equals(mname)) {
                     this.ctx.prompts.add(mname);
                     this.ctx.prompt = this.ctx.evaluatePrompt();
                     return;
                  }

                  ++i;
               }
            }
         }

         if (this.ctx.jndiNames.size() == 0) {
            ((InformationHandler)InformationHandler.class.cast(this.ctx.infoHandler)).handleJNDIls(false, "c");
         }

         if (this.ctx.jndiNames.size() == 0) {
            return;
         }

         Iterator iter = this.ctx.jndiNames.iterator();

         while(iter.hasNext()) {
            String name = (String)iter.next();
            if (name.equals(mname)) {
               this.ctx.prompts.add(mname);
               this.ctx.prompt = this.ctx.evaluatePrompt();
               this.ctx.jndiNames = new ArrayList();
               return;
            }
         }

         this.ctx.jndiNames = new ArrayList();
         this.ctx.throwWLSTException(this.txtFmt.getCannotFindJndiEntry(mname));
      } catch (Throwable var4) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorBrowsingTree("jndi"), var4);
      }

   }

   void cd(String mname) throws ScriptException {
      this.cdArgument = mname;
      int slipCdCount = false;
      this.cdDone = false;
      if (mname.startsWith("/")) {
         if (this.checkNameStartsWithSlash(mname)) {
            this.ctx.nameHasSlash = false;
            return;
         }

         String currentPrompt = this.ctx.prompt;

         try {
            this.takeBackToRoot();
            mname = mname.substring(1, mname.length());
            this.cd(mname);
         } catch (ScriptException var5) {
            this.resetCD(currentPrompt);
            throw var5;
         }
      } else {
         String[] strs;
         if (mname.indexOf("\\") != -1) {
            strs = StringUtils.splitCompletely(mname, "\\");
            if (strs.length == 0) {
               this.takeBackToRoot();
            }

            this.splitPush(strs, "\\");
         } else if (mname.indexOf("/") != -1) {
            strs = StringUtils.splitCompletely(mname, "/");
            if (strs.length == 0) {
               this.takeBackToRoot();
            }

            this.splitPush(strs, "/");
         } else {
            if (mname.length() == 0) {
               return;
            }

            this.regularPush(mname, (String)null);
         }
      }

   }

   void cdToConfig() throws ScriptException {
      this.ctx.println(this.txtFmt.getCannotChangeToConfigTree());
   }

   void reset() throws ScriptException {
      this.ctx.lastPlaceInConfigRuntime = "";
      this.ctx.lastPlaceInRuntimeRuntime = "";
      this.ctx.lastPlaceInConfigDomainRuntime = "";
      this.ctx.lastPlaceInRuntimeDomainRuntime = "";
      this.ctx.lastPlaceInJNDI = "";
      this.ctx.lastPlaceInCustom = "";
      this.ctx.lastPlaceInDomainCustom = "";
      this.ctx.lastPlaceInEditCustom = "";
      this.ctx.lastPlaceInJSR77 = "";
      if (this.ctx.edit != null) {
         this.ctx.edit.lastPlaceInEdit = "";
      }

      WLScriptContext var10001;
      try {
         var10001 = this.ctx;
         if (this.ctx.domainType.equals("Custom_Domain")) {
            this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
            this.initCommonVariables();
            this.ctx.atDomainLevel = true;
         } else {
            var10001 = this.ctx;
            if (!this.ctx.domainType.equals("DomainCustom_Domain")) {
               var10001 = this.ctx;
               if (!this.ctx.domainType.equals("EditCustom_Domain")) {
                  var10001 = this.ctx;
                  if (this.ctx.domainType.equals("RuntimeConfigServerDomain")) {
                     this.ctx.wlcmo = this.ctx.runtimeDomainMBean;
                     if (!this.ctx.newBrowseHandler.configRuntimeNavigateFirstTime) {
                        this.ctx.prompt = "";
                     }

                     var10001 = this.ctx;
                     this.ctx.domainType = "RuntimeConfigServerDomain";
                     this.initCommonVariables();
                     return;
                  } else {
                     var10001 = this.ctx;
                     if (this.ctx.domainType.equals("RuntimeRuntimeServerDomain")) {
                        this.ctx.wlcmo = this.ctx.runtimeServerRuntimeMBean;
                        var10001 = this.ctx;
                        this.ctx.domainType = "RuntimeRuntimeServerDomain";
                        if (!this.ctx.newBrowseHandler.runtimeRuntimeNavigateFirstTime) {
                           this.ctx.prompt = "";
                        }

                        this.initCommonVariables();
                        return;
                     } else {
                        var10001 = this.ctx;
                        if (this.ctx.domainType.equals("RuntimeDomainRuntime")) {
                           this.ctx.wlcmo = this.ctx.runtimeDomainRuntimeDRMBean;
                           var10001 = this.ctx;
                           this.ctx.domainType = "RuntimeDomainRuntime";
                           if (!this.ctx.newBrowseHandler.runtimeDomainRuntimeNavigateFirstTime) {
                              this.ctx.prompt = "";
                           }

                           this.initCommonVariables();
                           return;
                        } else {
                           var10001 = this.ctx;
                           if (this.ctx.domainType.equals("ConfigDomainRuntime")) {
                              this.ctx.wlcmo = this.ctx.configDomainRuntimeDRMBean;
                              var10001 = this.ctx;
                              this.ctx.domainType = "ConfigDomainRuntime";
                              if (!this.ctx.newBrowseHandler.configDomainRuntimeNavigateFirstTime) {
                                 this.ctx.prompt = "";
                              }

                              this.initCommonVariables();
                              return;
                           } else {
                              var10001 = this.ctx;
                              if (this.ctx.domainType.equals("JNDI")) {
                                 this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
                                 var10001 = this.ctx;
                                 this.ctx.domainType = "JNDI";
                                 this.initCommonVariables();
                                 return;
                              } else {
                                 var10001 = this.ctx;
                                 if (this.ctx.domainType.equals("ConfigEdit")) {
                                    this.ctx.wlcmo = this.ctx.edit == null ? null : this.ctx.edit.domainMBean;
                                    var10001 = this.ctx;
                                    this.ctx.domainType = "ConfigEdit";
                                    if (!this.ctx.newBrowseHandler.editNavigateFirstTime) {
                                       this.ctx.prompt = "";
                                    }

                                    this.initCommonVariables();
                                 } else {
                                    var10001 = this.ctx;
                                    if (this.ctx.domainType.equals("JSR77")) {
                                       this.ctx.wlcmo = null;
                                       var10001 = this.ctx;
                                       this.ctx.domainType = "ConfigEdit";
                                       this.initCommonVariables();
                                       return;
                                    }
                                 }

                                 return;
                              }
                           }
                        }
                     }
                  }
               }
            }

            this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
            this.initCommonVariables();
            this.ctx.atDomainLevel = true;
         }
      } catch (Throwable var2) {
         var10001 = this.ctx;
         this.ctx.throwWLSTException("Unexpected Error: ", var2);
      }

   }

   void cdToRuntime() throws ScriptException {
      this.ctx.println(this.txtFmt.getCannotChangeToRuntimeTree());
   }

   private void cdToCustomBeanInstance(String mname, String restCdArgument) throws ScriptException {
      if (this.ctx.inMBeanType) {
         String inDomain = (String)this.ctx.prompts.peek();
         List oNames = (List)this.ctx.customMBeanDomainObjNameMap.get(inDomain);
         Iterator itt = oNames.iterator();

         while(itt.hasNext()) {
            String on = (String)itt.next();
            if (!on.equals(mname) && !this.isObjectNameEqual(this.getObjectName(on), this.getObjectName(mname))) {
               if (!on.equals(restCdArgument) && !this.isObjectNameEqual(this.getObjectName(on), this.getObjectName(restCdArgument))) {
                  continue;
               }

               this.ctx.prompts.add(restCdArgument);
               this.slipCdCount = this.determineSlipCount(on);
               if (this.slipCdCount != 0 && this.slipCdCount != 1) {
                  --this.slipCdCount;
               } else {
                  this.slipCdCount = 0;
               }

               this.ctx.prompt = this.ctx.evaluatePrompt();
               this.changeToBeanLevel();
               this.ctx.atDomainLevel = false;

               try {
                  this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(new ObjectName(on));
               } catch (Throwable var8) {
                  this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
               }

               this.ctx.beans.push(this.ctx.wlcmo);
               return;
            }

            this.ctx.prompts.add(mname);
            this.ctx.prompt = this.ctx.evaluatePrompt();
            this.changeToBeanLevel();
            this.ctx.atDomainLevel = false;

            try {
               this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(new ObjectName(on));
            } catch (Throwable var9) {
               this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
            }

            this.ctx.beans.push(this.ctx.wlcmo);
            return;
         }
      }

      if (this.ctx.atDomainLevel) {
         Iterator iter = this.ctx.customMBeanDomainObjNameMap.keySet().iterator();

         while(iter.hasNext()) {
            String dn = (String)iter.next();
            if (dn.equals(mname)) {
               this.ctx.prompts.add(mname);
               this.ctx.prompt = this.ctx.evaluatePrompt();
               this.ctx.atDomainLevel = false;
               this.changeToMBeanTypeLevel();
               this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
               this.ctx.beans.push(this.ctx.wlcmo);
               return;
            }
         }
      }

      this.ctx.throwWLSTException(this.txtFmt.getAttributeNotFound(mname));
   }

   private void cdToDomainCustomBeanInstance(String mname, String restCdArgument) throws ScriptException {
      if (this.ctx.inMBeanType) {
         String inDomain = (String)this.ctx.prompts.peek();
         List oNames = (List)this.ctx.domainCustomMBeanDomainObjNameMap.get(inDomain);
         Iterator itt = oNames.iterator();

         while(itt.hasNext()) {
            String on = (String)itt.next();
            if (!on.equals(mname) && !this.isObjectNameEqual(this.getObjectName(on), this.getObjectName(mname))) {
               if (!on.equals(restCdArgument) && !this.isObjectNameEqual(this.getObjectName(on), this.getObjectName(restCdArgument))) {
                  continue;
               }

               this.ctx.prompts.add(restCdArgument);
               this.slipCdCount = this.determineSlipCount(on);
               if (this.slipCdCount != 0 && this.slipCdCount != 1) {
                  --this.slipCdCount;
               } else {
                  this.slipCdCount = 0;
               }

               this.ctx.prompt = this.ctx.evaluatePrompt();
               this.changeToBeanLevel();
               this.ctx.atDomainLevel = false;

               try {
                  this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(new ObjectName(on));
               } catch (Throwable var8) {
                  this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
               }

               this.ctx.beans.push(this.ctx.wlcmo);
               return;
            }

            this.ctx.prompts.add(mname);
            this.ctx.prompt = this.ctx.evaluatePrompt();
            this.changeToBeanLevel();
            this.ctx.atDomainLevel = false;

            try {
               this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(new ObjectName(on));
            } catch (Throwable var9) {
               this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
            }

            this.ctx.beans.push(this.ctx.wlcmo);
            return;
         }
      }

      if (this.ctx.atDomainLevel) {
         Iterator iter = this.ctx.domainCustomMBeanDomainObjNameMap.keySet().iterator();

         while(iter.hasNext()) {
            String dn = (String)iter.next();
            if (dn.equals(mname)) {
               this.ctx.prompts.add(mname);
               this.ctx.prompt = this.ctx.evaluatePrompt();
               this.ctx.atDomainLevel = false;
               this.changeToMBeanTypeLevel();
               this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
               this.ctx.beans.push(this.ctx.wlcmo);
               return;
            }
         }
      }

      this.ctx.throwWLSTException(this.txtFmt.getAttributeNotFound(mname));
   }

   private void cdToEditCustomBeanInstance(String mname, String restCdArgument) throws ScriptException {
      if (this.ctx.inMBeanType) {
         String inDomain = (String)this.ctx.prompts.peek();
         List oNames = (List)this.ctx.editCustomMBeanDomainObjNameMap.get(inDomain);
         Iterator itt = oNames.iterator();

         while(itt.hasNext()) {
            String on = (String)itt.next();
            if (!on.equals(mname) && !this.isObjectNameEqual(this.getObjectName(on), this.getObjectName(mname))) {
               if (!on.equals(restCdArgument) && !this.isObjectNameEqual(this.getObjectName(on), this.getObjectName(restCdArgument))) {
                  continue;
               }

               this.ctx.prompts.add(restCdArgument);
               this.slipCdCount = this.determineSlipCount(on);
               if (this.slipCdCount != 0 && this.slipCdCount != 1) {
                  --this.slipCdCount;
               } else {
                  this.slipCdCount = 0;
               }

               this.ctx.prompt = this.ctx.evaluatePrompt();
               this.changeToBeanLevel();
               this.ctx.atDomainLevel = false;

               try {
                  this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(new ObjectName(on));
               } catch (Throwable var8) {
                  this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
               }

               this.ctx.beans.push(this.ctx.wlcmo);
               return;
            }

            this.ctx.prompts.add(mname);
            this.ctx.prompt = this.ctx.evaluatePrompt();
            this.changeToBeanLevel();
            this.ctx.atDomainLevel = false;

            try {
               this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(new ObjectName(on));
            } catch (Throwable var9) {
               this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
            }

            this.ctx.beans.push(this.ctx.wlcmo);
            return;
         }
      }

      if (this.ctx.atDomainLevel) {
         Iterator iter = this.ctx.editCustomMBeanDomainObjNameMap.keySet().iterator();

         while(iter.hasNext()) {
            String dn = (String)iter.next();
            if (dn.equals(mname)) {
               this.ctx.prompts.add(mname);
               this.ctx.prompt = this.ctx.evaluatePrompt();
               this.ctx.atDomainLevel = false;
               this.changeToMBeanTypeLevel();
               this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
               this.ctx.beans.push(this.ctx.wlcmo);
               return;
            }
         }
      }

      this.ctx.throwWLSTException(this.txtFmt.getAttributeNotFound(mname));
   }

   private boolean isObjectNameEqual(ObjectName first, ObjectName second) {
      return first != null && second != null ? first.equals(second) : false;
   }

   private ObjectName getObjectName(String oname) {
      if (oname == null) {
         return null;
      } else {
         try {
            return new ObjectName(oname);
         } catch (MalformedObjectNameException var3) {
            return null;
         }
      }
   }

   void jndi(String serverName) throws ScriptException {
      if (serverName != null && !serverName.equals(this.ctx.serverName)) {
         if (!this.ctx.isAdminServer && !serverName.equals(this.ctx.serverName)) {
            this.ctx.println(this.txtFmt.getCannotBrowsJNDIOfOtherServer());
            return;
         }
      } else {
         try {
            WLScriptContext var10001 = this.ctx;
            if (!this.ctx.domainType.equals("JNDI")) {
               this.ctx.println(this.txtFmt.getLocationChangedToJndiTree());
               this.ctx.newBrowseHandler.saveLastPlaceInPreviousTree();
               this.ctx.wlcmo = this.txtFmt.getNoStubAvailable();
               var10001 = this.ctx;
               this.ctx.domainType = "JNDI";
               this.initCommonVariables();
               this.ctx.newBrowseHandler.goToLastPlaceInCurrentTree();
            } else {
               this.ctx.println(this.txtFmt.getAlreadyInTree("jndi"));
               this.ctx.jndiNames = new ArrayList();
            }
         } catch (Throwable var3) {
            if (var3 instanceof ScriptException) {
               throw (ScriptException)var3;
            }

            this.ctx.throwWLSTException(this.txtFmt.getErrorBrowsingTree("jndi"), var3);
         }
      }

   }
}
