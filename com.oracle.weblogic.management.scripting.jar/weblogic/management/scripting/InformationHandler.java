package weblogic.management.scripting;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.MarshalException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.Descriptor;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.naming.AuthenticationException;
import javax.naming.CannotProceedException;
import javax.naming.CommunicationException;
import javax.naming.ConfigurationException;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import weblogic.diagnostics.harvester.HarvesterException;
import weblogic.management.WebLogicObjectName;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.scripting.core.InformationCoreHandler;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.management.scripting.utils.WLSTUtil;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.security.UserConfigFileManager;
import weblogic.security.UsernameAndPassword;
import weblogic.utils.ArrayUtils;
import weblogic.utils.StringUtils;

public class InformationHandler extends InformationCoreHandler {
   private WLScriptContext ctx = null;
   private String EMPTY_STRING = "";
   private String JNDI_SEP = ".";
   private static final String NO_PERMISSION = "-";
   private static final String READ_ONLY_FILE = "-r--";
   private static final String READ_WRITE_FILE = "-rw-";
   private static final String READ_ONLY_DIR = "dr--";
   private static final String READ_WRITE_DIR = "drw-";
   private static final String READ_SPEC = "r";
   private static final String WRITE_SPEC = "w";
   private static final String DIRECTORY_SPEC = "d";
   private static final String READ_WRITE_EXEC = "-rwx";
   private static final String READ_EXEC = "-r-x";
   private static final String INITIAL_CONTEXT_FACTORY = "weblogic.jndi.WLInitialContextFactory";
   private static final WLSTMsgTextFormatter txtFmt = new WLSTMsgTextFormatter();

   public InformationHandler(WLScriptContext ctx) {
      super(ctx);
      this.ctx = ctx;
   }

   Object handleJNDIls(boolean doPrint, String attribute) throws ScriptException {
      String bindName = this.EMPTY_STRING;
      InitialContext svrCtx = null;
      int k;
      if (this.ctx.prompts.size() == 0) {
         if (!this.ctx.isAdminServer) {
            TreeMap map = new TreeMap();
            map.put(this.ctx.serverName, "dr--");
            return this.ctx.printAttributes(map);
         } else {
            ServerRuntimeMBean[] beans = this.ctx.domainRuntimeServiceMBean.getServerRuntimes();
            TreeMap map = new TreeMap();

            for(k = 0; k < beans.length; ++k) {
               map.put(beans[k].getName(), "dr--");
            }

            return this.ctx.printAttributes(map);
         }
      } else {
         String svrName;
         if (this.ctx.prompts.size() == 1) {
            svrName = (String)this.ctx.prompts.peek();
            svrCtx = this.getMSICtx(svrName);
         } else {
            svrName = (String)this.ctx.prompts.firstElement();
            svrCtx = this.getMSICtx(svrName);
            Enumeration enum_ = this.ctx.prompts.elements();
            k = 0;

            while(enum_.hasMoreElements()) {
               String binder = (String)enum_.nextElement();
               if (k == 0) {
                  ++k;
               } else {
                  ++k;
                  if (bindName.length() == 0) {
                     bindName = bindName + binder;
                  } else {
                     bindName = bindName + this.JNDI_SEP + binder;
                  }
               }
            }
         }

         return this.printJNDIElements(svrCtx, this.listElements(bindName, svrCtx), bindName, doPrint, attribute);
      }
   }

   private InitialContext getMSICtx(String svrName) throws ScriptException {
      if (this.ctx.isAdminServer) {
         return svrName.equals(this.ctx.serverName) ? this.ctx.iContext : this._getMSICtx(svrName, false);
      } else {
         return this.ctx.iContext;
      }
   }

   private InitialContext _getMSICtx(String svrName, boolean useAdmin) throws ScriptException {
      InitialContext ictx = (InitialContext)this.ctx.initialContexts.get(svrName);
      if (ictx != null) {
         return ictx;
      } else {
         String svrUrl = null;
         if (!useAdmin) {
            svrUrl = this.ctx.domainRuntimeServiceMBean.lookupServerRuntime(svrName).getDefaultURL();
         } else {
            svrUrl = this.ctx.domainRuntimeServiceMBean.lookupServerRuntime(svrName).getAdministrationURL();
            String protocol = this.ctx.getProtocol(svrUrl);
            if (protocol.equals("admin")) {
               svrUrl = this.ctx.getProtocol(this.ctx.url) + "://" + this.ctx.getListenAddress(svrUrl) + ":" + this.ctx.getListenPort(svrUrl);
            }
         }

         Hashtable h = new Hashtable();
         h.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
         h.put("java.naming.provider.url", svrUrl);
         h.put("java.naming.security.principal", new String(this.ctx.username_bytes));
         h.put("java.naming.security.credentials", new String(this.ctx.password_bytes));
         if (this.ctx.idd_bytes != null) {
            h.put("weblogic.jndi.identityDomain", new String(this.ctx.idd_bytes));
         }

         try {
            ictx = new InitialContext(h);
            this.ctx.initialContexts.put(svrName, ictx);
            return ictx;
         } catch (CommunicationException var9) {
            this.ctx.throwWLSTException(var9.getMessage(), var9);
         } catch (NamingException var10) {
            if (var10 instanceof CommunicationException) {
               Throwable th = var10.getRootCause();
               this.ctx.errorMsg = th.getMessage();
            } else if (var10 instanceof AuthenticationException) {
               AuthenticationException ae = (AuthenticationException)var10;
               SecurityException se = (SecurityException)ae.getRootCause();
               if (!useAdmin && se.getMessage().indexOf(this.ctx.getWLSTMsgFormatter().getAdministratorRequiredString()) != -1) {
                  return this._getMSICtx(svrName, true);
               }

               if (se.getMessage() != null) {
                  this.ctx.errorMsg = se.getMessage();
               } else {
                  this.ctx.errorMsg = this.ctx.getWLSTMsgFormatter().getInvalidUserOrPassword();
               }
            } else if (var10 instanceof ConfigurationException) {
               this.ctx.errorMsg = this.ctx.getWLSTMsgFormatter().getMalformedManagedServerURL(svrUrl);
            }

            this.ctx.throwWLSTException(this.ctx.errorMsg, var10);
         }

         return ictx;
      }
   }

   private List listElements(String bindingName, InitialContext ictx) throws ScriptException {
      try {
         NamingEnumeration bindings = ictx.list(bindingName);
         this.ctx.jndiNames = new ArrayList();

         while(bindings.hasMoreElements()) {
            NameClassPair pair = (NameClassPair)bindings.nextElement();
            this.ctx.jndiNames.add(pair.getName());
         }
      } catch (NamingException var5) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getNamingException(bindingName), var5);
      }

      return this.ctx.jndiNames;
   }

   private Object printJNDIElements(InitialContext ictx, List bindList, String parentName, boolean doPrint, String attribute) throws ScriptException {
      TreeMap map = new TreeMap();
      TreeMap attrMap = new TreeMap();
      String result = this.EMPTY_STRING;
      Iterator iter_ = bindList.iterator();

      while(true) {
         while(true) {
            String name_;
            NamingEnumeration bindings;
            while(true) {
               if (!iter_.hasNext()) {
                  if (doPrint) {
                     if (attribute != null) {
                        if (attribute.equals("c")) {
                           if (!map.isEmpty()) {
                              result = result + this.ctx.printAttributes(map);
                           } else {
                              result = result + this.ctx.printAttributes(attrMap);
                           }
                        } else {
                           result = result + this.ctx.printAttributes(attrMap);
                        }
                     } else if (!map.isEmpty()) {
                        result = result + this.ctx.printAttributes(map);
                        if (!attrMap.isEmpty()) {
                           result = result + "\n" + this.ctx.printAttributes(attrMap);
                        }
                     } else {
                        result = result + "\n" + this.ctx.printAttributes(attrMap);
                     }
                  }

                  return result;
               }

               String name = this.EMPTY_STRING;
               name_ = (String)iter_.next();
               if (parentName.length() != 0) {
                  name = parentName + this.JNDI_SEP + name_;
               } else {
                  name = name_;
               }

               bindings = null;

               try {
                  bindings = ictx.list(name);
                  break;
               } catch (CannotProceedException var18) {
                  try {
                     bindings = ictx.list(parentName);
                  } catch (NamingException var17) {
                     this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getNamingException(parentName), var17);
                  }

                  while(bindings.hasMoreElements()) {
                     NameClassPair pair = (NameClassPair)bindings.nextElement();
                     String n = pair.getName();
                     if (n.equals(name_)) {
                        attrMap.put(pair.getName() + this.ctx.calculateTabSpace(pair.getName()) + pair.getClassName(), "-r--");
                        break;
                     }
                  }
               } catch (NamingException var19) {
                  this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getNamingException(parentName), var19);
                  break;
               }
            }

            if (bindings.hasMoreElements()) {
               map.put(name_, "dr--");
            } else {
               try {
                  bindings = ictx.list(parentName);
               } catch (NamingException var16) {
                  this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getNamingException(parentName), var16);
               }

               while(bindings.hasMoreElements()) {
                  NameClassPair pair = (NameClassPair)bindings.nextElement();
                  String n = pair.getName();
                  if (n.equals(name_)) {
                     attrMap.put(pair.getName() + this.ctx.calculateTabSpace(pair.getName()) + pair.getClassName(), "-r--");
                     break;
                  }
               }
            }
         }
      }
   }

   String getPermission() {
      WLScriptContext var10001 = this.ctx;
      return !this.ctx.domainType.equals("RuntimeDomainRuntime") && !this.ctx.domainType.equals("RuntimeRuntimeServerDomain") && !this.ctx.domainType.equals("JNDI") && !this.ctx.domainType.equals("RuntimeConfigServerDomain") && !this.ctx.domainType.equals("DomainRuntime") && !this.ctx.domainType.equals("ConfigDomainRuntime") && (!this.ctx.domainType.equals("ConfigEdit") || this.ctx.isEditSessionInProgress) ? "drw-" : "dr--";
   }

   private Object newLs(String attribute, String returnType, boolean showInheritance) throws ScriptException {
      try {
         String perm;
         if (!attribute.equals("c")) {
            if (attribute.equals("a")) {
               return this.la(showInheritance);
            }

            if (attribute.equals("o")) {
               return this.lo(true);
            }

            String currentPwd = this.ctx.prompt;
            String currenTree = this.ctx.getCurrentTree();

            String var8;
            try {
               perm = this.ctx.getTreeFromArgument(attribute);
               String retString;
               if (perm != null) {
                  retString = this.ctx.removeTreeFromArgument(attribute);
                  this.ctx.browseHandler.jumpTree(perm);
                  this.ctx.browseHandler.takeBackToRoot();
                  this.ctx.browseHandler.cd(retString);
               } else {
                  this.ctx.browseHandler.cd(attribute);
               }

               retString = (String)this.ls(returnType, (String)null);
               var8 = retString;
            } finally {
               this.ctx.browseHandler.jumpTree(currenTree);
               this.ctx.browseHandler.takeBackToRoot();
               this.ctx.browseHandler.cd(currentPwd);
            }

            return var8;
         }

         if (this.ctx.inMBeanType) {
            if (this.ctx.wlInstanceObjName != null) {
               if (this.allowRealPermissionDisplay()) {
                  this.ctx.println(this.getPermission() + "   " + this.ctx.wlInstanceObjName_name);
                  return "drw-   " + this.ctx.wlInstanceObjName_name;
               }

               this.ctx.println("dr--   " + this.ctx.wlInstanceObjName_name);
               return "dr--   " + this.ctx.wlInstanceObjName_name;
            }

            return this.EMPTY_STRING;
         }

         TreeMap attribPerms;
         if (this.ctx.inMBeanTypes) {
            if (this.ctx.browseHandler.delegateToDomainRuntimeHandler(attribute)) {
               return this.ctx.domainRuntimeHandler.ls(attribute);
            }

            if (this.ctx.browseHandler.delegateToServerRuntimeHandler(attribute)) {
               return this.ctx.serverRuntimeHandler.ls(attribute);
            }

            attribPerms = new TreeMap();
            if (this.ctx.wlInstanceObjNames != null) {
               for(int i = 0; i < this.ctx.wlInstanceObjNames.length; ++i) {
                  perm = this.allowRealPermissionDisplay() ? this.getPermission() : "dr--";
                  if (this.ctx.wlInstanceObjNames[i] != null) {
                     attribPerms.put(this.ctx.wlInstanceObjNames_names[i], perm);
                  }
               }
            }

            return this.ctx.printAttributes(attribPerms);
         }

         if (this.ctx.atBeanLevel) {
            attribPerms = new TreeMap();

            try {
               attribPerms = this.getReferenceAndChildAttributeNames(this.ctx.getObjectName(), showInheritance);
            } catch (InstanceNotFoundException var13) {
               perm = this.ctx.getWLSTMsgFormatter().getCurrentLocationNoLongerExists(this.ctx.getObjectName().toString(), this.ctx.prompt);
               this.ctx.throwWLSTException(perm);
            }

            if (WLSTHelper.globalMBeansVisibleToPartitions || this.ctx.partitionName == null || this.ctx.partitionName.equals("DOMAIN")) {
               if (this.delegateToDomainRuntimeHandler()) {
                  attribPerms.put("DomainServices", "dr--");
                  attribPerms.put("ServerServices", "dr--");
               }

               if (this.delegateToServerRuntimeHandler()) {
                  attribPerms.put("ServerServices", "dr--");
               }
            }

            if (this.delegateToDomainRuntimeHandler()) {
               attribPerms.put("ServerRuntimes", "dr--");
            }

            if (attribPerms.isEmpty()) {
               return this.EMPTY_STRING;
            }

            return this.ctx.printAttributes(attribPerms);
         }
      } catch (Throwable var15) {
         if (var15 instanceof ScriptException) {
            throw (ScriptException)var15;
         }

         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorOnLs(), var15);
      }

      return null;
   }

   private TreeMap getReferenceAndChildAttributeNames(ObjectName mbeanON, boolean showInheritance) throws InstanceNotFoundException, ReflectionException, IntrospectionException, IOException {
      TreeMap attrPerms = new TreeMap();
      String[] attributeNames = null;
      MBeanInfo info = this.ctx.getMBSConnection(this.ctx.domainType).getMBeanInfo(mbeanON);
      if (info instanceof ModelMBeanInfo) {
         ModelMBeanInfo modelInfo = (ModelMBeanInfo)info;
         MBeanAttributeInfo[] attrInfo = modelInfo.getAttributes();

         for(int i = 0; i < attrInfo.length; ++i) {
            if (!attrInfo[i].getName().equals("MBeanInfo") && !attrInfo[i].getName().equals("Parent") && !attrInfo[i].getName().equals("ObjectName") && !this.isExcluded((ModelMBeanAttributeInfo)attrInfo[i]) && this.isChildOrReference((ModelMBeanAttributeInfo)attrInfo[i])) {
               if (!this.allowRealPermissionDisplay() && !this.ctx.inDomainRT()) {
                  attrPerms.put(attrInfo[i].getName(), this.getPermission());
               } else {
                  attrPerms.put(attrInfo[i].getName(), "dr--");
               }
            }
         }

         if (showInheritance) {
            Set nameSet = attrPerms.keySet();
            String[] names = new String[nameSet.size()];
            int j = 0;

            for(Iterator var11 = nameSet.iterator(); var11.hasNext(); ++j) {
               Object name = var11.next();
               names[j] = (String)name;
            }

            String inheritedMsg = txtFmt.getInherited();

            try {
               String[] inheriedNames = (String[])((String[])this.ctx.getMBSConnection(this.ctx.domainType).invoke(mbeanON, "getInheritedProperties", new Object[]{names}, new String[]{"[Ljava.lang.String;"}));
               String[] var13 = inheriedNames;
               int var14 = inheriedNames.length;

               for(int var15 = 0; var15 < var14; ++var15) {
                  String inheritedName = var13[var15];
                  Object perm = attrPerms.remove(inheritedName);
                  attrPerms.put(inheritedName + " (" + inheritedMsg + ")", perm);
               }
            } catch (MBeanException var18) {
               var18.printStackTrace();
            }
         }
      }

      return attrPerms;
   }

   private boolean delegateToDomainRuntimeHandler() {
      WLScriptContext var10001 = this.ctx;
      return this.ctx.domainType.equals("RuntimeDomainRuntime") && this.ctx.wlcmo instanceof DomainRuntimeMBean && this.ctx.prompts.size() == 0;
   }

   private boolean delegateToServerRuntimeHandler() {
      WLScriptContext var10001 = this.ctx;
      return this.ctx.domainType.equals("RuntimeRuntimeServerDomain") && this.ctx.wlcmo instanceof ServerRuntimeMBean && this.ctx.prompts.size() == 0;
   }

   private Object customMBeanLS(String attribute, String type, String domainType) throws ScriptException {
      if (this.ctx.inMBeanType) {
         return this.customBeanTypeLS(attribute, type, domainType);
      } else {
         return this.ctx.atDomainLevel ? this.customBeanDomainTypeLS(attribute, type, domainType) : this.customBeanInstanceLS(attribute, domainType);
      }
   }

   private Object customBeanTypeLS(String attribute, String type, String domainType) throws ScriptException {
      return this.customMBeanTypeDomainLS(attribute, type, domainType);
   }

   private Object customBeanDomainTypeLS(String attribute, String type, String domainType) throws ScriptException {
      TreeMap domains = new TreeMap();
      Iterator iter = null;
      if (domainType.equals("Custom_Domain")) {
         iter = this.ctx.customMBeanDomainObjNameMap.keySet().iterator();
      } else if (domainType.equals("DomainCustom_Domain")) {
         iter = this.ctx.domainCustomMBeanDomainObjNameMap.keySet().iterator();
      } else if (domainType.equals("EditCustom_Domain")) {
         iter = this.ctx.editCustomMBeanDomainObjNameMap.keySet().iterator();
      }

      while(iter.hasNext()) {
         domains.put(iter.next(), "drw-");
      }

      if (attribute != null && !attribute.equals("c")) {
         return !attribute.equals("a") && !attribute.equals("o") ? this.handleCustomLsAttribute(attribute, type) : null;
      } else {
         return this.ctx.printAttributes(domains);
      }
   }

   private String handleCustomLsAttribute(String attribute, String type) throws ScriptException {
      String currentPwd = this.ctx.prompt;
      String currenTree = this.ctx.getCurrentTree();

      String var7;
      try {
         String tree = this.ctx.getTreeFromArgument(attribute);
         String retString;
         if (tree != null) {
            retString = this.ctx.removeTreeFromArgument(attribute);
            this.ctx.browseHandler.jumpTree(tree);
            this.ctx.browseHandler.takeBackToRoot();
            this.ctx.browseHandler.cd(retString);
         } else {
            this.ctx.browseHandler.cd(attribute);
         }

         retString = (String)this.ls(type, (String)null);
         var7 = retString;
      } finally {
         this.ctx.browseHandler.jumpTree(currenTree);
         this.ctx.browseHandler.takeBackToRoot();
         this.ctx.browseHandler.cd(currentPwd);
      }

      return var7;
   }

   private String[] getAttrNames(MBeanAttributeInfo[] ainfo) throws ScriptException {
      String[] names = new String[ainfo.length];

      for(int i = 0; i < ainfo.length; ++i) {
         names[i] = ainfo[i].getName();
         this.ctx.printDebug("Attribute " + names[i] + " has data type " + ainfo[i].getType());
      }

      return names;
   }

   private Object customMBeanTypeDomainLS(String attribute, String type, String domainType) throws ScriptException {
      String dn = (String)this.ctx.prompts.peek();
      if (attribute != null && !attribute.equals("c")) {
         return !attribute.equals("a") && !attribute.equals("o") ? this.handleCustomLsAttribute(attribute, type) : null;
      } else {
         List onames = null;
         if (domainType.equals("Custom_Domain")) {
            onames = (List)this.ctx.customMBeanDomainObjNameMap.get(dn);
         } else if (domainType.equals("DomainCustom_Domain")) {
            onames = (List)this.ctx.domainCustomMBeanDomainObjNameMap.get(dn);
         } else if (domainType.equals("EditCustom_Domain")) {
            onames = (List)this.ctx.editCustomMBeanDomainObjNameMap.get(dn);
         }

         Iterator it = onames.iterator();
         TreeMap map = new TreeMap();

         while(it.hasNext()) {
            map.put((String)it.next(), "drw-");
         }

         return this.ctx.printAttributes(map);
      }
   }

   private Object customBeanInstanceLS(String attribute, String domainType) throws ScriptException {
      TreeMap treeMap = new TreeMap();

      try {
         String sOn = (String)this.ctx.prompts.peek();
         ObjectName con = new ObjectName(sOn);
         MBeanServerConnection theMBS = null;
         MBeanInfo minfo = null;
         if (domainType.equals("DomainCustom_Domain") && this.ctx.domainRTMSC.isRegistered(con)) {
            theMBS = this.ctx.domainRTMSC;
         } else if (domainType.equals("Custom_Domain") && this.ctx.runtimeMSC.isRegistered(con)) {
            theMBS = this.ctx.runtimeMSC;
         } else if (domainType.equals("EditCustom_Domain") && this.ctx.edit.msc.isRegistered(con)) {
            theMBS = this.ctx.edit.msc;
         } else {
            theMBS = this.ctx.runtimeMSC;
         }

         minfo = theMBS.getMBeanInfo(con);
         MBeanAttributeInfo[] attrInfo = null;
         MBeanOperationInfo[] operInfo = null;
         String operPerm = "-rwx";
         if (attribute == null) {
            attribute = "a";
         }

         if (attribute.equals("a")) {
            attrInfo = minfo.getAttributes();
            String[] attrNames = this.getAttrNames(attrInfo);
            AttributeList attrList = theMBS.getAttributes(con, attrNames);
            TreeMap attributeMap = this.fillInAttrPerms(minfo, attrList, false);
            treeMap.putAll(attributeMap);
         } else {
            if (attribute.equals("c")) {
               return null;
            }

            if (attribute.equals("o")) {
               operInfo = minfo.getOperations();

               for(int i = 0; i < operInfo.length; ++i) {
                  MBeanParameterInfo[] parInfo = operInfo[i].getSignature();
                  String[] str = new String[parInfo.length];

                  for(int j = 0; j < parInfo.length; ++j) {
                     str[j] = parInfo[j].getType();
                  }

                  String tabSpace = this.ctx.calculateTabSpace(operInfo[i].getName());
                  treeMap.put(operInfo[i].getName() + tabSpace + operInfo[i].getReturnType() + " : " + StringUtils.join(str, ","), operPerm);
               }
            } else {
               this.ctx.throwWLSTException(txtFmt.getCannotCDToAttribute(attribute));
            }
         }
      } catch (Throwable var15) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorGettingCustomBeans(), var15);
      }

      return this.ctx.printAttributes(treeMap);
   }

   public Object ls(String attribute, String type) throws ScriptException {
      return this.ls(attribute, type, "false");
   }

   public Object ls(String attribute, String type, String inheritance) throws ScriptException {
      if (this.ctx.domainType.equals("JNDI")) {
         return this.handleJNDIls(true, attribute);
      } else if (!this.ctx.domainType.equals("Custom_Domain") && !this.ctx.domainType.equals("DomainCustom_Domain") && !this.ctx.domainType.equals("EditCustom_Domain")) {
         boolean showInheritance = Boolean.parseBoolean(inheritance);
         String result = this.EMPTY_STRING;
         if (attribute == null) {
            if (this.ctx.atBeanLevel) {
               result = (String)this.newLs("c", type, showInheritance);
               if (result == null) {
                  return null;
               } else {
                  this.ctx.println(this.EMPTY_STRING);
                  result = result + (String)this.la(showInheritance);
                  this.ctx.println(this.EMPTY_STRING);
                  result = result + (String)this.lo(false);
                  return result;
               }
            } else {
               return this.newLs("c", type, showInheritance);
            }
         } else {
            return this.newLs(attribute, type, showInheritance);
         }
      } else {
         return this.customMBeanLS(attribute, type, this.ctx.domainType);
      }
   }

   private Object la(boolean showInheritance) throws ScriptException {
      TreeMap attrPermss = new TreeMap();

      try {
         if (this.ctx.inMBeanType) {
            if (this.ctx.wlInstanceObjName == null) {
               return this.EMPTY_STRING;
            }

            attrPermss.put(this.ctx.wlInstanceObjName_name, "drw-");
            return this.ctx.printAttributes(attrPermss);
         }

         if (this.ctx.inMBeanTypes) {
            if (this.ctx.wlInstanceObjNames == null) {
               return this.EMPTY_STRING;
            }

            for(int i = 0; i < this.ctx.wlInstanceObjNames.length; ++i) {
               attrPermss.put(this.ctx.wlInstanceObjNames_names[i], "drw-");
            }

            return this.ctx.printAttributes(attrPermss);
         }

         if (this.ctx.atBeanLevel) {
            AttributeList getAttribs = new AttributeList();
            MBeanInfo info = this.ctx.getMBeanInfo(this.ctx.wlcmo);
            String[] requestedAttributeNames = this.getAllAttributes(this.ctx.getObjectName());

            for(int i = 0; i < requestedAttributeNames.length; ++i) {
               if (!requestedAttributeNames[i].equals("ThreadStackDump")) {
                  ModelMBeanInfo obj;
                  if (info instanceof ModelMBeanInfo) {
                     obj = (ModelMBeanInfo)info;
                     ModelMBeanAttributeInfo modelAttrInfo = obj.getAttribute(requestedAttributeNames[i]);
                     if (modelAttrInfo != null && this.isExcluded(modelAttrInfo)) {
                        continue;
                     }

                     if (modelAttrInfo != null && this.isEncrypted(modelAttrInfo)) {
                        getAttribs.add(new Attribute(requestedAttributeNames[i], "********"));
                        continue;
                     }
                  }

                  try {
                     obj = null;
                     Object obj = this.ctx.getMBSConnection(this.ctx.domainType).getAttribute(this.ctx.getObjectName(), requestedAttributeNames[i]);
                     getAttribs.add(new Attribute(requestedAttributeNames[i], obj));
                  } catch (RemoteRuntimeException var21) {
                     if (!(var21.getNestedException() instanceof MarshalException)) {
                        throw var21;
                     }
                  } catch (NullPointerException var22) {
                  } catch (MBeanException var23) {
                     if (!(var23.getCause() instanceof HarvesterException.HarvestingNotEnabled)) {
                        throw var23;
                     }

                     getAttribs.add(new Attribute(requestedAttributeNames[i], txtFmt.getHarvesterNotEnabled()));
                  }
               }
            }

            MBeanAttributeInfo[] attributes = info.getAttributes();
            TreeMap attrPerms = new TreeMap();
            String perm;
            if (this.ctx.inNewTree()) {
               attrPerms = this.fillInAttrPerms(info, getAttribs);
            } else {
               ModelMBeanAttributeInfo[] extAttributes = null;
               if (null != attributes && ModelMBeanAttributeInfo[].class.isAssignableFrom(attributes.getClass())) {
                  extAttributes = (ModelMBeanAttributeInfo[])((ModelMBeanAttributeInfo[])attributes);
               }

               if (extAttributes == null) {
                  attrPerms = this.fillInAttrPerms(info, getAttribs);
               } else {
                  for(int i = 0; i < getAttribs.size(); ++i) {
                     Attribute attr = (Attribute)getAttribs.get(i);
                     String name = attr.getName();
                     if (extAttributes != null) {
                        for(int j = 0; j < extAttributes.length; ++j) {
                           if (extAttributes[j].getName().equals(name)) {
                              if (this.isEncrypted(extAttributes[j])) {
                                 if (attr.getValue() != null) {
                                    perm = "-";
                                    if (this.allowRealPermissionDisplay()) {
                                       perm = perm + (extAttributes[j].isReadable() ? "r" : "-");
                                       perm = perm + (extAttributes[j].isWritable() ? "w" : "-");
                                       perm = perm + "-";
                                    } else {
                                       perm = "-r--";
                                    }

                                    attrPerms.put(name + this.ctx.calculateTabSpace(name) + "******", perm);
                                    getAttribs.remove(i);
                                 }
                              } else if (!this.isExcluded(extAttributes[j]) && !(attr.getValue() instanceof WebLogicObjectName) && !(attr.getValue() instanceof WebLogicObjectName[])) {
                                 perm = "-";
                                 if (this.allowRealPermissionDisplay()) {
                                    perm = perm + (extAttributes[j].isReadable() ? "r" : "-");
                                    perm = perm + (extAttributes[j].isWritable() ? "w" : "-");
                                    perm = perm + "-";
                                 } else {
                                    perm = "-r--";
                                 }

                                 attrPerms.put(name + this.ctx.calculateTabSpace(name) + this.massage(attr.getValue()), perm);
                              }
                           }
                        }
                     }
                  }
               }
            }

            if (showInheritance) {
               Set nameSet = attrPerms.keySet();
               String[] names = new String[nameSet.size()];
               Hashtable nameToPermKeyMap = new Hashtable();
               int j = 0;

               for(Iterator var34 = nameSet.iterator(); var34.hasNext(); ++j) {
                  Object permKey = var34.next();
                  String namefield = ((String)permKey).substring(0, 45);
                  names[j] = namefield.split(" +")[0];
                  nameToPermKeyMap.put(names[j], (String)permKey);
               }

               try {
                  String[] inheriedNames = (String[])((String[])this.ctx.getMBSConnection(this.ctx.domainType).invoke(this.ctx.getObjectName(), "getInheritedProperties", new Object[]{names}, new String[]{"[Ljava.lang.String;"}));
                  perm = txtFmt.getInherited();
                  String[] var36 = inheriedNames;
                  int var15 = inheriedNames.length;

                  for(int var16 = 0; var16 < var15; ++var16) {
                     String inheritedName = var36[var16];
                     String permKey = (String)nameToPermKeyMap.get(inheritedName);
                     Object perm = attrPerms.remove(permKey);
                     attrPerms.put(permKey + " (" + perm + ")", perm);
                  }
               } catch (MBeanException var20) {
                  var20.printStackTrace();
               }
            }

            return this.ctx.printAttributes(attrPerms);
         }
      } catch (Throwable var24) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorRetrievingAttributeNameValue(), var24);
      }

      return null;
   }

   private void dumpAttrInfo(MBeanAttributeInfo attrInfo) {
      ModelMBeanAttributeInfo info = (ModelMBeanAttributeInfo)attrInfo;
      String[] names = info.getDescriptor().getFieldNames();

      for(int i = 0; i < names.length; ++i) {
         System.out.println(this.stripBEA(names[i]) + " : " + this.prettyValues(info.getDescriptor().getFieldValue(names[i])));
      }

   }

   private TreeMap fillInAttrPerms(MBeanInfo info, AttributeList getAttribs) {
      return this.fillInAttrPerms(info, getAttribs, true);
   }

   private TreeMap fillInAttrPerms(MBeanInfo info, AttributeList getAttribs, boolean skipObjectNames) {
      TreeMap attrPerms = new TreeMap();
      MBeanAttributeInfo[] attrInfo = info.getAttributes();

      for(int i = 0; i < getAttribs.size(); ++i) {
         Attribute attr = (Attribute)getAttribs.get(i);
         String name = attr.getName();

         for(int j = 0; j < attrInfo.length; ++j) {
            if (attrInfo[j].getName().equals(name) && (!skipObjectNames || !(attr.getValue() instanceof ObjectName) && !(attr.getValue() instanceof ObjectName[]))) {
               String perm = "-";
               if (this.allowRealPermissionDisplay()) {
                  perm = perm + (attrInfo[j].isReadable() ? "r" : "-");
                  if (!attrInfo[j].isWritable()) {
                     perm = perm + "-";
                  } else if (this.ctx.partitionName != null && !this.ctx.partitionName.equals("DOMAIN")) {
                     Descriptor descriptor = attrInfo[j].getDescriptor();
                     if (descriptor == null) {
                        perm = perm + "-";
                     } else {
                        String field = (String)descriptor.getFieldValue("com.bea.VisibleToPartitionsOnSetter");
                        if (field != null && field.equals("NEVER")) {
                           perm = perm + "-";
                        } else {
                           perm = perm + "w";
                        }
                     }
                  } else {
                     perm = perm + "w";
                  }

                  perm = perm + "-";
               } else {
                  perm = "-r--";
               }

               boolean encrypted = false;
               boolean excluded = false;
               if (attrInfo[j] instanceof ModelMBeanAttributeInfo) {
                  ModelMBeanAttributeInfo modelAttributeInfo = (ModelMBeanAttributeInfo)attrInfo[j];
                  encrypted = this.isEncrypted(modelAttributeInfo);
                  excluded = this.isExcluded(modelAttributeInfo);
               }

               if (encrypted) {
                  attrPerms.put(name + this.ctx.calculateTabSpace(name) + "******", perm);
               } else if (!excluded) {
                  attrPerms.put(name + this.ctx.calculateTabSpace(name) + this.massage(attr.getValue()), perm);
               }
            }
         }
      }

      return attrPerms;
   }

   boolean isEncrypted(ModelMBeanAttributeInfo info) {
      Boolean obj = (Boolean)info.getDescriptor().getFieldValue("com.bea.encrypted");
      return obj != null ? obj : false;
   }

   private boolean isExcluded(ModelMBeanAttributeInfo info) {
      if (this.ctx.showExcluded()) {
         return false;
      } else {
         Boolean obj = (Boolean)info.getDescriptor().getFieldValue("com.bea.exclude");
         return obj != null ? obj : false;
      }
   }

   private boolean isOperationExcluded(ModelMBeanOperationInfo info) {
      if (this.ctx.showExcluded()) {
         return false;
      } else {
         Boolean obj = (Boolean)info.getDescriptor().getFieldValue("com.bea.exclude");
         return obj != null ? obj : false;
      }
   }

   private boolean isChildOrReference(ModelMBeanAttributeInfo info) {
      String relation = (String)info.getDescriptor().getFieldValue("com.bea.relationship");
      return relation != null && (relation.equals("containment") || relation.equals("reference"));
   }

   private Object massage(Object value) {
      Object mass = value;
      if (value instanceof Object[]) {
         try {
            mass = ArrayUtils.toString((Object[])((Object[])value));
         } catch (NullPointerException var4) {
            return value;
         }
      }

      return mass;
   }

   private Object lo(boolean showCRUDs) throws ScriptException {
      try {
         if (this.ctx.inMBeanType) {
            return this.EMPTY_STRING;
         }

         if (this.ctx.inMBeanTypes) {
            return this.EMPTY_STRING;
         }

         if (this.ctx.atBeanLevel) {
            new AttributeList();
            MBeanOperationInfo[] operInfo = this.ctx.getMBeanInfo(this.ctx.wlcmo).getOperations();
            TreeMap operPerms = new TreeMap();

            for(int i = 0; i < operInfo.length; ++i) {
               if ((showCRUDs || !this.isCRUD(operInfo[i].getName())) && (!(operInfo[i] instanceof ModelMBeanOperationInfo) || !this.isOperationExcluded((ModelMBeanOperationInfo)operInfo[i]))) {
                  MBeanParameterInfo[] parInfo = operInfo[i].getSignature();
                  String[] str = new String[parInfo.length];

                  String type;
                  String parName;
                  for(int j = 0; j < parInfo.length; ++j) {
                     type = parInfo[j].getType();
                     parName = "(" + parInfo[j].getName() + ")";
                     if (type != null) {
                        if (type.equals("javax.management.ObjectName")) {
                           type = "WebLogicMBean" + parName;
                        } else if (type.equals("[Ljavax.management.ObjectName;")) {
                           type = "WebLogicMBean[]" + parName;
                        } else if (type.startsWith("[L")) {
                           type = type.substring(2, type.length() - 1) + "[]";
                           if (type.startsWith("java.lang.")) {
                              type = type.substring(10, type.length()) + parName;
                           }
                        } else if (type.startsWith("java.lang.")) {
                           type = type.substring(10, type.length()) + parName;
                        }
                     }

                     if (parName != null) {
                        if (parName.equals("javax.management.ObjectName")) {
                           parName = "WebLogicMBean";
                        } else if (parName.equals("[Ljavax.management.ObjectName;")) {
                           parName = "WebLogicMBean[]";
                        } else if (parName.startsWith("[L")) {
                           parName = parName.substring(2, parName.length() - 1) + "[]";
                           if (parName.startsWith("java.lang.")) {
                              parName = parName.substring(10, parName.length());
                           }
                        } else if (parName.startsWith("java.lang.")) {
                           parName = parName.substring(10, parName.length());
                        }
                     }

                     str[j] = type;
                  }

                  String perms = "-r-x";
                  type = this.ctx.calculateTabSpace(operInfo[i].getName());
                  parName = operInfo[i].getReturnType();
                  if (parName != null) {
                     if (parName.equals("javax.management.ObjectName")) {
                        parName = "WebLogicMBean";
                     } else if (parName.equals("[Ljavax.management.ObjectName;")) {
                        parName = "WebLogicMBean[]";
                     } else if (parName.startsWith("[L")) {
                        parName = parName.substring(2, parName.length() - 1) + "[]";
                        if (parName.startsWith("java.lang.")) {
                           parName = parName.substring(10, parName.length());
                        }
                     } else if (parName.startsWith("java.lang.")) {
                        parName = parName.substring(10, parName.length());
                     }
                  }

                  operPerms.put(operInfo[i].getName() + type + parName + " : " + StringUtils.join(str, ","), perms);
               }
            }

            return this.ctx.printAttributes(operPerms);
         }
      } catch (Throwable var11) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorRetrievingOperationInfo(), var11);
      }

      return this.EMPTY_STRING;
   }

   private boolean isCRUD(String operName) {
      return operName.startsWith("create") || operName.startsWith("destroy") || operName.startsWith("lookup");
   }

   private String[] getAllAttributes(ObjectName mbeanON) throws InstanceNotFoundException, ReflectionException, IntrospectionException, IOException {
      AttributeList attribList = null;
      String[] attributeNames = null;
      ArrayList attrStrs = new ArrayList();
      MBeanInfo info = this.ctx.getMBSConnection(this.ctx.domainType).getMBeanInfo(mbeanON);
      MBeanAttributeInfo[] attributes = info.getAttributes();
      ModelMBeanAttributeInfo[] extAttributes = null;

      for(int i = 0; i < attributes.length; ++i) {
         if (!attributes[i].getName().equals("MBeanInfo")) {
            attrStrs.add(attributes[i].getName());
         }
      }

      attributeNames = new String[attrStrs.size()];
      attrStrs.toArray(attributeNames);
      return attributeNames;
   }

   public void help(String methodName) throws ScriptException {
      if (methodName.equals("default1")) {
         this.printDefaultHelp();
      } else {
         this.printHelp(methodName);
      }

   }

   private void printDefaultHelp() throws ScriptException {
      this.ctx.scriptCmdHelp.printDefaultHelp();
   }

   private void printHelp(String method) throws ScriptException {
      this.ctx.scriptCmdHelp.printHelp(method);
   }

   public void writeIniFile(String filePath) throws ScriptException {
      try {
         File file = new File(filePath);
         if (file.exists()) {
            if (!file.isFile()) {
               this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getOutputFileIsDir(filePath));
               return;
            }

            file.delete();
            file.createNewFile();
         } else {
            file.createNewFile();
         }

         WLSTUtil.writeWLSTAsModule(file.getAbsolutePath());
         this.ctx.println(this.ctx.getWLSTMsgFormatter().getWroteIniFile(filePath));
      } catch (Throwable var3) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorWritingIni(), var3);
      }

   }

   public void dumpVariables() {
      this.ctx.println("cmgr" + this.ctx.calculateTabSpace("cmgr", 30) + (this.ctx.edit == null ? null : this.ctx.edit.configurationManager));
      this.ctx.println("cmo" + this.ctx.calculateTabSpace("cmo", 30) + this.ctx.wlcmo);
      this.ctx.println("connected" + this.ctx.calculateTabSpace("connected", 30) + this.ctx.connected);
      this.ctx.println("domainName" + this.ctx.calculateTabSpace("domainName", 30) + this.ctx.domainName);
      this.ctx.println("domainRuntimeService" + this.ctx.calculateTabSpace("domainRuntimeService", 30) + this.ctx.domainRuntimeServiceMBean);
      this.ctx.println("editService" + this.ctx.calculateTabSpace("editService", 30) + (this.ctx.edit == null ? null : this.ctx.edit.serviceMBean));
      this.ctx.println("isAdminServer" + this.ctx.calculateTabSpace("isAdminServer", 30) + this.ctx.isAdminServer);
      this.ctx.println("mbs" + this.ctx.calculateTabSpace("mbs", 30) + this.ctx.getMBSConnection(this.ctx.domainType));
      this.ctx.println("recording" + this.ctx.calculateTabSpace("recording", 30) + this.ctx.recording);
      this.ctx.println("runtimeService" + this.ctx.calculateTabSpace("runtimeService", 30) + this.ctx.runtimeServiceMBean);
      this.ctx.println("scriptMode" + this.ctx.calculateTabSpace("scriptMode", 30) + this.ctx.getScriptMode());
      this.ctx.println("serverName" + this.ctx.calculateTabSpace("serverName", 30) + this.ctx.serverName);
      this.ctx.println("typeService" + this.ctx.calculateTabSpace("typeService", 30) + this.ctx.mbeanTypeService);
      this.ctx.println("username" + this.ctx.calculateTabSpace("username", 30) + new String(this.ctx.username_bytes));
      this.ctx.println("idd" + this.ctx.calculateTabSpace("idd", 30) + (this.ctx.idd_bytes != null ? new String(this.ctx.idd_bytes) : ""));
      this.ctx.println("version" + this.ctx.calculateTabSpace("version", 30) + this.ctx.version);
   }

   void storeUserConfig(String userConfigFile, String userKeyFile, String nm) throws ScriptException {
      System.setProperty("weblogic.management.confirmKeyfileCreation", "true");
      if (this.ctx.isConnected() && nm.toLowerCase(Locale.US).equals("false")) {
         this.storeUsernameAndPassword(userConfigFile, userKeyFile, this.ctx.encodeUserAndIDDBytes(this.ctx.username_bytes, this.ctx.idd_bytes), this.ctx.password_bytes, false);
      } else if (this.ctx.nmService.nm() && nm.toLowerCase(Locale.US).equals("true")) {
         this.storeUsernameAndPassword(userConfigFile, userKeyFile, ((NodeManagerService)NodeManagerService.class.cast(this.ctx.nmService)).getNMUser(), ((NodeManagerService)NodeManagerService.class.cast(this.ctx.nmService)).getNMPwd(), true);
      } else {
         this.ctx.println(this.ctx.getWLSTMsgFormatter().getNeedWlsOrNm());
      }

   }

   void storeUsernameAndPassword(String userConfigFile, String userKeyFile, byte[] uname, byte[] pwd, boolean isNM) throws ScriptException {
      File f;
      if (userConfigFile != null) {
         f = new File(userConfigFile);
         if (f.getParentFile() != null && !f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
         }
      }

      if (userKeyFile != null) {
         f = new File(userKeyFile);
         if (f.getParentFile() != null && !f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
         }
      }

      UsernameAndPassword UandP = new UsernameAndPassword(uname != null ? new String(uname) : null, pwd != null ? (new String(pwd)).toCharArray() : null);
      UserConfigFileManager.setUsernameAndPassword(UandP, userConfigFile, userKeyFile, "weblogic.management");
      if (userConfigFile == null) {
         userConfigFile = UserConfigFileManager.getDefaultConfigFileName();
      }

      if (userKeyFile == null) {
         userKeyFile = UserConfigFileManager.getDefaultKeyFileName();
      }

      String msg = null;
      if (!isNM) {
         msg = this.ctx.getWLSTMsgFormatter().getUsernamePasswordStored("WebLogic Server", userConfigFile, userKeyFile);
      } else {
         msg = this.ctx.getWLSTMsgFormatter().getUsernamePasswordStored("WebLogic NodeManager", userConfigFile, userKeyFile);
      }

      File keyFile = new File(userKeyFile);
      if (keyFile.exists()) {
         this.ctx.println(msg);
      }

   }

   public String man(String attributeName) throws ScriptException {
      MBeanInfo mbeanInfo = this.ctx.getMBeanInfo(this.ctx.wlcmo);
      if (mbeanInfo == null) {
         return this.EMPTY_STRING;
      } else {
         String result = this.EMPTY_STRING;
         if (attributeName == null) {
            result = this.stripHTML(mbeanInfo.getDescription());
            this.ctx.println(result);
            return result;
         } else if (attributeName.equals("a")) {
            return this.printAttributeInfo(mbeanInfo);
         } else if (attributeName.equals("o")) {
            return this.printOperationInfo(mbeanInfo);
         } else if (attributeName.equals("c")) {
            return this.printChildrenInfo(mbeanInfo);
         } else if (attributeName.equals("*")) {
            result = result + this.stripHTML(mbeanInfo.getDescription());
            this.ctx.println(result);
            result = result + this.printAttributeInfo(mbeanInfo);
            result = result + this.printOperationInfo(mbeanInfo);
            return result;
         } else {
            return this.printAttributeHelp(mbeanInfo, attributeName);
         }
      }
   }

   public String getInterfaceClassname(String attrName) throws ScriptException {
      MBeanInfo mbeanInfo = this.ctx.getMBeanInfo(this.ctx.wlcmo);
      ModelMBeanInfo modelInfo = (ModelMBeanInfo)mbeanInfo;
      MBeanAttributeInfo[] attrInfo1 = modelInfo.getAttributes();

      for(int i = 0; i < attrInfo1.length; ++i) {
         String name = attrInfo1[i].getName();
         if (name.equals(attrName)) {
            Descriptor desc = ((ModelMBeanAttributeInfo)attrInfo1[i]).getDescriptor();
            String[] fNames = desc.getFieldNames();
            this.stripHTML(attrInfo1[i].getDescription());

            for(int j = 0; j < fNames.length; ++j) {
               if (fNames[j].equals("interfaceclassname")) {
                  return (String)desc.getFieldValue(fNames[j]);
               }
            }

            return this.EMPTY_STRING;
         }
      }

      return this.EMPTY_STRING;
   }

   private String printAttributeHelp(MBeanInfo mbeanInfo, String attrName) {
      String result = this.EMPTY_STRING;
      boolean found = false;
      String name;
      int j;
      if (!(mbeanInfo instanceof ModelMBeanInfo)) {
         MBeanAttributeInfo[] attrInfo = mbeanInfo.getAttributes();

         for(int i = 0; i < attrInfo.length; ++i) {
            String name = attrInfo[i].getName();
            if (name.equals(attrName)) {
               result = this.stripHTML(attrInfo[i].getDescription());
               this.ctx.println(result);
               return result;
            }
         }

         MBeanOperationInfo[] operInfo = mbeanInfo.getOperations();

         for(j = 0; j < operInfo.length; ++j) {
            name = operInfo[j].getName();
            if (name.equals(attrName)) {
               result = this.stripHTML(operInfo[j].getDescription());
               this.ctx.println(result);
               return result;
            }
         }
      } else {
         ModelMBeanInfo modelInfo = (ModelMBeanInfo)mbeanInfo;
         MBeanAttributeInfo[] attrInfo1 = modelInfo.getAttributes();

         for(j = 0; j < attrInfo1.length; ++j) {
            name = attrInfo1[j].getName();
            if (name.equals(attrName)) {
               found = true;
               Descriptor desc = ((ModelMBeanAttributeInfo)attrInfo1[j]).getDescriptor();
               String[] fNames = desc.getFieldNames();
               this.stripHTML(attrInfo1[j].getDescription());

               for(int j = 0; j < fNames.length; ++j) {
                  result = result + "||" + this.stripBEA(fNames[j]) + " : " + this.prettyValues(desc.getFieldValue(fNames[j]));
                  this.ctx.println(this.stripBEA(fNames[j]) + " : " + this.prettyValues(desc.getFieldValue(fNames[j])));
               }

               return result;
            }
         }

         ModelMBeanOperationInfo[] operInfo = (ModelMBeanOperationInfo[])((ModelMBeanOperationInfo[])modelInfo.getOperations());

         for(int k = 0; k < operInfo.length; ++k) {
            String name = operInfo[k].getName();
            if (name.equals(attrName)) {
               found = true;
               Descriptor desc = operInfo[k].getDescriptor();
               String[] fNames = desc.getFieldNames();
               String description = this.stripHTML(operInfo[k].getDescription());
               this.ctx.println("Description : " + description);
               result = result + "||Description : " + description;

               for(int m = 0; m < fNames.length; ++m) {
                  result = result + "||" + this.stripBEA(fNames[m]) + " : " + this.prettyValues(desc.getFieldValue(fNames[m]));
                  this.ctx.println(this.stripBEA(fNames[m]) + " : " + this.prettyValues(desc.getFieldValue(fNames[m])));
               }

               return result;
            }
         }
      }

      if (!found) {
         this.ctx.println(this.ctx.getWLSTMsgFormatter().getNoAttrDescription(attrName));
      }

      return result;
   }

   private String printAttributeInfo(MBeanInfo mbeanInfo) {
      MBeanAttributeInfo[] attrInfo = mbeanInfo.getAttributes();
      String result = this.EMPTY_STRING;
      if (mbeanInfo instanceof ModelMBeanInfo) {
         ModelMBeanInfo modelInfo = (ModelMBeanInfo)mbeanInfo;
         MBeanAttributeInfo[] attrInfo1 = modelInfo.getAttributes();

         for(int i = 0; i < attrInfo1.length; ++i) {
            this.ctx.println("Attribute : " + attrInfo1[i].getName());
            Descriptor desc = ((ModelMBeanAttributeInfo)attrInfo1[i]).getDescriptor();
            String[] fNames = desc.getFieldNames();

            for(int j = 0; j < fNames.length; ++j) {
               this.ctx.println(this.stripBEA(fNames[j]) + " : " + this.prettyValues(desc.getFieldValue(fNames[j])));
            }

            this.ctx.println(this.EMPTY_STRING);
         }
      } else {
         for(int i = 0; i < attrInfo.length; ++i) {
            MBeanAttributeInfo aInfo = attrInfo[i];
            result = result + aInfo.getName() + " : " + this.stripHTML(aInfo.getDescription());
            this.ctx.println(aInfo.getName() + " : " + this.stripHTML(aInfo.getDescription()));
         }
      }

      return result;
   }

   private String printOperationInfo(MBeanInfo mbeanInfo) {
      String result = this.EMPTY_STRING;
      if (!(mbeanInfo instanceof ModelMBeanInfo)) {
         MBeanOperationInfo[] operInfo = mbeanInfo.getOperations();

         for(int i = 0; i < operInfo.length; ++i) {
            MBeanOperationInfo oInfo = operInfo[i];
            result = result + oInfo.getName() + " : " + this.stripHTML(oInfo.getDescription());
            this.ctx.println(oInfo.getName() + " : " + this.stripHTML(oInfo.getDescription()));
         }
      } else {
         ModelMBeanInfo modelInfo = (ModelMBeanInfo)mbeanInfo;
         MBeanOperationInfo[] attrInfo1 = modelInfo.getOperations();

         for(int i = 0; i < attrInfo1.length; ++i) {
            this.ctx.println("Operation : " + attrInfo1[i].getName());
            String description = this.stripHTML(attrInfo1[i].getDescription());
            this.ctx.println("Description : " + description);
            Descriptor desc = ((ModelMBeanOperationInfo)attrInfo1[i]).getDescriptor();
            String[] fNames = desc.getFieldNames();

            for(int j = 0; j < fNames.length; ++j) {
               this.ctx.println(this.stripBEA(fNames[j]) + " : " + this.prettyValues(desc.getFieldValue(fNames[j])));
               result = result + this.stripBEA(fNames[j]) + " : " + this.prettyValues(desc.getFieldValue(fNames[j]));
            }

            this.ctx.println(this.EMPTY_STRING);
         }
      }

      return result;
   }

   private String printChildrenInfo(MBeanInfo mbeanInfo) {
      MBeanAttributeInfo[] attrInfo = mbeanInfo.getAttributes();
      String result = this.EMPTY_STRING;

      for(int i = 0; i < attrInfo.length; ++i) {
         MBeanAttributeInfo attr = attrInfo[i];
         if (attr.getType().endsWith("MBean")) {
            result = result + attr.getName() + " : " + this.stripHTML(attr.getDescription());
            this.ctx.println(attr.getName() + " : " + this.stripHTML(attr.getDescription()));
         }
      }

      return result;
   }

   List getChildrenTypes() throws ScriptException {
      List list = new ArrayList();
      ObjectName oname = this.ctx.getObjectName();
      MBeanInfo info = this.ctx.getMBeanInfo(this.ctx.wlcmo);
      ModelMBeanInfo modelInfo = (ModelMBeanInfo)info;
      MBeanAttributeInfo[] attrInfo = modelInfo.getAttributes();

      for(int i = 0; i < attrInfo.length; ++i) {
         Descriptor desc = ((ModelMBeanAttributeInfo)attrInfo[i]).getDescriptor();
         String creator = (String)((ModelMBeanAttributeInfo)attrInfo[i]).getDescriptor().getFieldValue("com.bea.creator");
         if (creator != null) {
            Object clzName = ((ModelMBeanAttributeInfo)attrInfo[i]).getDescriptor().getFieldValue("interfaceClassName");
            list.add(this.ctx.getRightType((String)desc.getFieldValue("Name")));
         }
      }

      if (list.isEmpty()) {
         this.ctx.println(this.ctx.getWLSTMsgFormatter().getNoChildBeans(oname.getKeyProperty("Type")));
      }

      return list;
   }

   private List getChildrenAttributes() throws ScriptException {
      List list = new ArrayList();
      ObjectName oname = this.ctx.getObjectName();
      MBeanInfo info = this.ctx.getMBeanInfo(this.ctx.wlcmo);
      ModelMBeanInfo modelInfo = (ModelMBeanInfo)info;
      MBeanAttributeInfo[] attrInfo = modelInfo.getAttributes();

      for(int i = 0; i < attrInfo.length; ++i) {
         Descriptor desc = ((ModelMBeanAttributeInfo)attrInfo[i]).getDescriptor();
         String creator = (String)((ModelMBeanAttributeInfo)attrInfo[i]).getDescriptor().getFieldValue("com.bea.creator");
         if (creator != null) {
            Object clzName = ((ModelMBeanAttributeInfo)attrInfo[i]).getDescriptor().getFieldValue("interfaceClassName");
            list.add(desc.getFieldValue("Name"));
         }
      }

      if (list.isEmpty()) {
         this.ctx.println(this.ctx.getWLSTMsgFormatter().getNoChildBeans(oname.getKeyProperty("Type")));
      }

      return list;
   }

   public List listChildrenTypes(String parent) throws ScriptException {
      if (parent != null) {
         this.ctx.println(this.ctx.getWLSTMsgFormatter().getUnsupportedCommand("listChildTypes(null)"));
         return new ArrayList();
      } else {
         TreeSet set = new TreeSet();
         List list = this.getChildrenAttributes();
         Iterator iter = list.iterator();

         while(iter.hasNext()) {
            String type = (String)iter.next();
            set.add(type);
         }

         this.ctx.printAttrs(set);
         return list;
      }
   }

   public Object getMBI(String mbeanType) throws ScriptException {
      try {
         return mbeanType == null ? this.ctx.getMBeanInfo(this.ctx.wlcmo) : this.ctx.getMBeanTypeService().getMBeanInfo(mbeanType);
      } catch (Throwable var3) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorGettingMBeanInfo(mbeanType), var3);
         return null;
      }
   }

   public Object threadDump(String writeToFile, String fileName, String serverName) throws ScriptException {
      String td = this.EMPTY_STRING;

      try {
         if (serverName == null) {
            serverName = this.ctx.serverName;
         }

         if (serverName.equals(this.ctx.serverName)) {
            this.ctx.printDebug(this.ctx.getWLSTMsgFormatter().getRequestedThreadDump());
            td = this.ctx.getServerRuntimeServerRuntimeMBean().getJVMRuntime().getThreadStackDump();
         } else {
            if (!this.ctx.isAdminServer) {
               this.ctx.println(this.ctx.getWLSTMsgFormatter().getThreadDumpNeedsConnection());
               return this.EMPTY_STRING;
            }

            ServerRuntimeMBean srBean = this.ctx.getDomainRuntimeServiceMBean().lookupServerRuntime(serverName);
            if (srBean == null) {
               this.ctx.println(this.ctx.getWLSTMsgFormatter().getThreadDumpServerNotRunning());
               return this.EMPTY_STRING;
            }

            td = srBean.getJVMRuntime().getThreadStackDump();
         }

         if (this.ctx.getBoolean(writeToFile)) {
            if (fileName == null) {
               fileName = this.ctx.calculateThreadDumpFileName() + "_" + serverName + ".txt";
            }

            File file = new File(fileName);
            if (file.exists()) {
               if (!file.isFile()) {
                  this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getOutputFileIsDir(fileName));
                  return this.EMPTY_STRING;
               }

               file.delete();
               this.ensureParentExists(file);
               file.createNewFile();
            } else {
               this.ensureParentExists(file);
               file.createNewFile();
            }

            FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeBytes(this.ctx.getWLSTMsgFormatter().getThreadDumpHeader(serverName, new Date()));
            dos.flush();
            dos.writeBytes(td);
            dos.flush();
            dos.close();
            bos.close();
            fos.close();
            this.ctx.println(this.ctx.getWLSTMsgFormatter().getThreadDumpHeader2(serverName));
            this.ctx.println(td);
            this.ctx.println(this.ctx.getWLSTMsgFormatter().getThreadDumpFooter(serverName, fileName));
         } else {
            this.ctx.println(this.ctx.getWLSTMsgFormatter().getThreadDumpHeader2(serverName));
            this.ctx.println(td);
         }
      } catch (IOException var9) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getThreadDumpFileError(), var9);
      }

      return td;
   }

   public void addHelpCommandGroup(String groupName, String resourceBundleName) throws ScriptException {
      try {
         this.ctx.scriptCmdHelp.addHelpCommandGroup(groupName, resourceBundleName);
      } catch (MissingResourceException var4) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorGettingResourceBundle(), var4);
      }

   }

   public void addHelpCommand(String commandName, String groupName, String offlineStr, String onlineStr) throws ScriptException {
      boolean offline = false;
      boolean online = false;
      if (offlineStr.toLowerCase(Locale.US).equals("true")) {
         offline = true;
      }

      if (onlineStr.toLowerCase(Locale.US).equals("true")) {
         online = true;
      }

      this.ctx.scriptCmdHelp.addHelpCommand(commandName, groupName, offline, online);
   }

   private void ensureParentExists(File file) throws IOException {
      if (file != null) {
         String parent = file.getParent();
         if (parent != null) {
            File parentFile = new File(parent);
            if (!parentFile.exists() && !parentFile.mkdirs()) {
               throw new IOException(this.ctx.getWLSTMsgFormatter().getCouldNotCreateParentDir(file.getAbsolutePath()));
            }
         }

      }
   }

   void viewMBean(Object obj) throws ScriptException {
      try {
         ObjectName on = this.ctx.getObjectName(obj);
         MBeanServerConnection mbs = this.ctx.getMBeanServer();
         MBeanAttributeInfo[] attrInfo = mbs.getMBeanInfo(on).getAttributes();
         AttributeList attrList = new AttributeList();

         for(int i = 0; i < attrInfo.length; ++i) {
            ModelMBeanAttributeInfo ainfo = (ModelMBeanAttributeInfo)attrInfo[i];
            if (!this.isEncrypted(ainfo) && !this.isExcluded(ainfo)) {
               Object _obj = mbs.getAttribute(on, ainfo.getName());
               attrList.add(new Attribute(ainfo.getName(), _obj));
            }
         }

         Iterator iter = attrList.iterator();
         this.ctx.println(txtFmt.getAttributeNamesAndValues());
         this.ctx.println("");

         while(iter.hasNext()) {
            Attribute attr = (Attribute)iter.next();
            this.ctx.println(attr.getName() + this.ctx.calculateTabSpace(attr.getName()) + this.massage(attr.getValue()));
         }

         MBeanOperationInfo[] opers = mbs.getMBeanInfo(on).getOperations();
         if (opers.length == 0) {
            return;
         }

         this.ctx.println(txtFmt.getOperationsOnThisMBean());

         for(int i = 0; i < opers.length; ++i) {
            MBeanOperationInfo oinfo = opers[i];
            if (!this.isOperationExcluded((ModelMBeanOperationInfo)oinfo)) {
               this.ctx.println(oinfo.getName());
            }
         }
      } catch (Throwable var10) {
         this.ctx.throwWLSTException(txtFmt.getErrorViewingTheMBean(), var10);
      }

   }

   String getPath(Object obj) throws ScriptException {
      try {
         ObjectName oname = this.ctx.watchUtil.getONFromObject(obj);
         String path = this.ctx.findUtil.lookupPath(oname);
         if (path != null) {
            return path;
         }

         this.ctx.println(this.ctx.getWLSTMsgFormatter().getPathNotFound());
      } catch (Throwable var4) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorGettingPath(), var4);
      }

      return null;
   }

   private boolean allowRealPermissionDisplay() {
      return this.ctx.isAdminServer || "Custom_Domain".equals(this.ctx.domainType);
   }

   private String stripHTML(String txt) {
      String result = txt;
      if (txt == null) {
         return txt;
      } else {
         if (txt.indexOf(60) != -1) {
            result = txt.replaceAll("\\<.*?>", "");
         }

         if (result.indexOf(38) != -1) {
            result = result.replaceAll("&lt;", "<");
            result = result.replaceAll("&gt;", ">");
            result = result.replaceAll("&amp;", "&");
            result = result.replaceAll("&#35;", "");
            result = result.replaceAll("&#92;", "\\");
         }

         return result;
      }
   }

   private String stripBEA(String txt) {
      return txt == null ? txt : txt.replaceFirst("com.bea.", "");
   }

   private String prettyValues(Object fieldValues) {
      if (fieldValues == null) {
         return this.EMPTY_STRING;
      } else if (fieldValues instanceof String) {
         return this.stripHTML((String)fieldValues);
      } else if (fieldValues instanceof Object[]) {
         Object[] arr = (Object[])((Object[])fieldValues);
         String result = this.EMPTY_STRING;
         Object[] var4 = arr;
         int var5 = arr.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Object element = var4[var6];
            if (result.length() > 0) {
               result = result + ", ";
            }

            if (element instanceof String) {
               result = result + this.stripHTML((String)element);
            } else {
               result = result + element;
            }
         }

         return result;
      } else {
         return this.EMPTY_STRING + fieldValues;
      }
   }
}
