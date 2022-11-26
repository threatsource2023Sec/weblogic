package weblogic.management.scripting;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.xml.stream.XMLStreamException;
import weblogic.deploy.internal.DeploymentType;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JDBCSystemResourceMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.ServerFailureTriggerMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.WLDFSystemResourceMBean;
import weblogic.management.provider.ManagementServiceClient;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.security.ProviderMBean;
import weblogic.security.UserConfigFileManager;
import weblogic.security.UsernameAndPassword;
import weblogic.utils.Hex;
import weblogic.utils.StringUtils;

public class ConfigToScript {
   private DomainMBean domainMBean = null;
   private WLScriptContext ctx = null;
   private BeanInfoAccess beanInfoAccess = null;
   private List haveISeenYou = new ArrayList();
   private StringBuffer mainScriptBuffer = new StringBuffer();
   private StringBuffer coreFunctionCalls = new StringBuffer();
   private StringBuffer theScript = new StringBuffer();
   private StringBuffer theHeader = new StringBuffer();
   private StringBuffer theTail = new StringBuffer();
   private int beansCounter = 0;
   private List potentialTargetMBeanTypes = new ArrayList();
   private List functionNames = new ArrayList();
   private HashMap createdBeans = new HashMap();
   private List beanFunctionNameList = new ArrayList();
   private List referenceBeans = new ArrayList();
   private List referenceBeanArrays = new ArrayList();
   private HashMap createMethods = new HashMap();
   private List applicationBeans = new ArrayList();
   private List libraryBeans = new ArrayList();
   private List applicationDeploymentFunctionNames = new ArrayList();
   private StringBuffer applicationDeploymentScript = new StringBuffer();
   private StringBuffer applicationDeploymentFunctionCalls = new StringBuffer();
   private List dummyCreators = new ArrayList();
   private List excludedAttributeNames = new ArrayList();
   private HashMap jmsBeanToParentMap = new HashMap();
   private HashMap referredDestinationBeanMap = new HashMap();
   private boolean debug = false;
   private boolean processedApplicationBeans = false;
   private boolean processedLibraryBeans = false;
   private boolean cds = false;
   private static final String RELATIONSHIP = "relationship";
   private static final String CONTAINMENT = "containment";
   private static final String CREATOR = "creator";
   private static final String DESTROYER = "destroyer";
   private static final String REFERENCE = "reference";
   private static final String TRANSIENT = "transient";
   private static final String DEPRECATED = "deprecated";
   private File configFile = null;
   private File pyFile = null;
   private File propertiesFile = null;
   private String currentSystemResourceBeanName = null;
   private boolean processingJMSBean = false;
   private String CONFIG_TO_SCRIPT_USER_CONFIG_FILE = null;
   private String CONFIG_TO_SCRIPT_SECRET_FILE = null;

   ConfigToScript(File configFile, String pathToPyFile, String overWrite, String propFile, String createDeploymentScript, String resourcesOnlyConversion, WLScriptContext wlctx) throws ScriptException {
      this.ctx = wlctx;
      this.configFile = configFile;
      this.cds = this.ctx.getBoolean(createDeploymentScript);
      if (pathToPyFile == null) {
         pathToPyFile = configFile.getParentFile() + File.separator + "config.py";
      }

      this.pyFile = new File(pathToPyFile);
      if (this.pyFile.isDirectory()) {
         this.pyFile = new File(this.pyFile.getAbsolutePath() + File.separator + "config.py");
      }

      if (!this.ctx.getBoolean(overWrite) && this.pyFile.exists()) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getScriptAlreadyExists(pathToPyFile));
      }

      if (propFile == null) {
         this.propertiesFile = new File(this.pyFile.getAbsolutePath() + ".properties");
      } else {
         this.propertiesFile = new File(propFile);
      }

      this.domainMBean = this.loadConfiguration();
      this.ctx = wlctx;
      this.setUpBeanInfoService();
      this.setupAllTargetTypes();
      this.setupDummyCreators();
      this.setExcludedAttributeNames();
   }

   private void setupDummyCreators() {
      this.dummyCreators.add("createJTAMigratableTarget");
   }

   private void setExcludedAttributeNames() {
      this.excludedAttributeNames.add("PasswordEncrypted");
   }

   private void print(String s) {
      if (this.debug) {
         System.out.println(s);
      }

   }

   private void setupAllTargetTypes() {
      this.potentialTargetMBeanTypes.add("ServerMBean");
      this.potentialTargetMBeanTypes.add("ClusterMBean");
      this.potentialTargetMBeanTypes.add("JTAMigratableTargetMBean");
      this.potentialTargetMBeanTypes.add("MigratableTargetMBean");
      this.potentialTargetMBeanTypes.add("JMSServerMBean");
      this.potentialTargetMBeanTypes.add("VirtualHostMBean");
   }

   private DomainMBean loadConfiguration() throws ScriptException {
      try {
         String msg = this.ctx.getWLSTMsgFormatter().getLoadingConfiguration(this.configFile.getCanonicalPath());
         this.ctx.println(msg);
         return ManagementServiceClient.getClientAccess().getDomain(this.configFile.getCanonicalPath(), false);
      } catch (FileNotFoundException var2) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getCannotLocateConfigXml(), var2);
      } catch (IOException var3) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getIOExceptionLoadingConfig(), var3);
      } catch (XMLStreamException var4) {
         var4.printStackTrace();
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getFailedToLoadConfigXml(), var4);
      }

      return null;
   }

   private void setUpBeanInfoService() {
      this.beanInfoAccess = ManagementServiceClient.getBeanInfoAccess();
   }

   private void createDeploymentScript() throws ScriptException {
      try {
         File f = new File(this.pyFile.getParentFile().getAbsolutePath() + "/deploy.py");
         FileWriter out = new FileWriter(f);
         out.write(this.applicationDeploymentScript.toString());
         out.write(this.applicationDeploymentFunctionCalls.toString());
         out.flush();
         out.close();
      } catch (IOException var3) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getIOExceptionWritingDeploymentScript(var3.getMessage()), var3);
      }

   }

   void convert() throws ScriptException {
      this.ctx.println(this.ctx.getWLSTMsgFormatter().getConvertingResources());
      this.walkTheTree("weblogic.management.configuration.DomainMBean", this.domainMBean, "");
      this.setBeanAttributes();
      this.nowGenerateTheScript();
      this.encryptDefaultUsernameAndPassword();
      this.createDefaultProperties(this.propertiesFile);
      this.writeFile(this.pyFile);
      if (this.cds) {
         this.createDeploymentScript();
      }

      String msg = this.ctx.getWLSTMsgFormatter().getConfigToScriptComplete(this.pyFile.getAbsolutePath(), this.propertiesFile.getAbsolutePath());
      this.ctx.println(msg);
      this.printPasswordDisclaimer();
   }

   private void printPasswordDisclaimer() {
      if (this.CONFIG_TO_SCRIPT_USER_CONFIG_FILE != null) {
         String msg = this.ctx.getWLSTMsgFormatter().getPasswordDisclaimer(this.CONFIG_TO_SCRIPT_USER_CONFIG_FILE, this.CONFIG_TO_SCRIPT_SECRET_FILE);
         this.ctx.println(msg);
      }
   }

   private void nowGenerateTheScript() {
      this.setupCommonFunctions();
      this.callCoreFunctions();
      this.processDeploymentBeans(this.libraryBeans, DeploymentType.LIBRARY);
      this.processDeploymentBeans(this.applicationBeans, DeploymentType.DEFAULT_APP);
      this.addDeploymentFunctionCalls();
      this.theScript.append(this.mainScriptBuffer.toString());
      this.theScript.append(this.applicationDeploymentScript.toString());
      this.theScript.append("try:\n");
      this.theScript.append(this.theHeader.toString());
      this.theScript.append(this.coreFunctionCalls.toString());
      this.theScript.append("  endTransaction()\n");
      this.theScript.append(this.applicationDeploymentFunctionCalls.toString());
      this.theScript.append(this.theTail.toString());
      this.theScript.append("finally:\n");
      this.theScript.append("  endOfConfigToScriptRun()\n");
   }

   private void callCoreFunctions() {
      Iterator iter;
      FunctionNameBucket buck;
      String fname;
      String path;
      for(iter = this.beanFunctionNameList.iterator(); iter.hasNext(); this.coreFunctionCalls.append("  " + fname + "(\"" + path + "\", \"" + buck.getBeanName() + "\")\n")) {
         buck = (FunctionNameBucket)iter.next();
         fname = buck.getCreateFunctionName();
         String setterFuncName = buck.getSetFunctionName();
         path = buck.getPath();
         if (path.length() == 0) {
            path = "/";
         }
      }

      iter = this.beanFunctionNameList.iterator();

      while(iter.hasNext()) {
         buck = (FunctionNameBucket)iter.next();
         fname = buck.getSetFunctionName();
         if (buck.callSetter()) {
            this.coreFunctionCalls.append("  " + fname + "()\n");
         }
      }

      Iterator _iter = this.functionNames.iterator();

      while(_iter.hasNext()) {
         fname = (String)_iter.next();
         this.coreFunctionCalls.append("  " + fname + "()\n");
      }

   }

   private void headers() {
      this.theHeader.append("  initConfigToScriptRun()\n");
      this.theHeader.append("  startTransaction()\n");
   }

   private void tailers() {
   }

   private String addImports() {
      StringBuffer buf = new StringBuffer();
      buf.append("from weblogic.descriptor import BeanAlreadyExistsException\n");
      buf.append("from java.lang.reflect import UndeclaredThrowableException\n");
      buf.append("from java.lang import System\n");
      buf.append("import javax\n");
      buf.append("from javax import management\n");
      buf.append("from javax.management import MBeanException\n");
      buf.append("from javax.management import RuntimeMBeanException\n");
      buf.append("import javax.management.MBeanException\n");
      buf.append("from java.lang import UnsupportedOperationException\n");
      buf.append("\n");
      return buf.toString();
   }

   private void setupCommonFunctions() {
      this.theScript.append(this.header());
      this.theScript.append(this.tail());
      this.headers();
      this.tailers();
   }

   private void walkTheTree(String root, DescriptorBean parentBean, String path) throws ScriptException {
      try {
         BeanInfo beanInfo = this.beanInfoAccess.getBeanInfoForInterface(root, false, "9.0.0");
         if (beanInfo == null) {
            throw new AssertionError("Can't find BeanInfo class for " + root);
         }

         if (this.haveISeenYou.contains(parentBean)) {
            return;
         }

         if (root.equals("weblogic.management.configuration.DomainMBean")) {
            this.createdBeans.put(parentBean, path);
         }

         this.haveISeenYou.add(parentBean);
         if (this.isSystemResourceMBean(parentBean)) {
            this.currentSystemResourceBeanName = this.getName(parentBean);
            this.processingJMSBean = false;
         }

         if (this.isJMSBean(parentBean)) {
            this.processingJMSBean = true;
         }

         PropertyDescriptor[] propDescInfos = beanInfo.getPropertyDescriptors();

         for(int i = 0; i < propDescInfos.length; ++i) {
            PropertyDescriptor propDesc = propDescInfos[i];
            if (!this.isExcluded(propDesc) && (parentBean.isSet(propDesc.getName()) || this.isTransient(propDesc))) {
               Method getMethod;
               Object[] obj;
               Object referredBean;
               DescriptorBean theBean;
               String name;
               String name;
               String currentBeanPath;
               if (this.isChild(propDesc)) {
                  if (!this.applicationBean(propDesc, parentBean) && !this.libraryBean(propDesc, parentBean)) {
                     this.print(this.ctx.getWLSTMsgFormatter().getBeanIsAChild(propDesc.getName()));
                     getMethod = propDesc.getReadMethod();
                     obj = null;
                     referredBean = getMethod.invoke(parentBean, obj);
                     if (referredBean.toString().startsWith("[L")) {
                        obj = (Object[])((Object[])referredBean);
                        DescriptorBean[] children = (DescriptorBean[])((DescriptorBean[])referredBean);

                        for(int j = 0; j < children.length; ++j) {
                           DescriptorBean bean = children[j];
                           if (this.isJMSBean(bean)) {
                              this.jmsBeanToParentMap.put(bean, parentBean);
                           }

                           name = this.getName(obj[j]);
                           String currentBeanPath = "";
                           String templatedName = "";
                           boolean isTemplated = false;
                           if (!this.isTemplatingEnabled(propDesc.getName(), name)) {
                              currentBeanPath = "/" + propDesc.getName() + "/" + name;
                           }

                           if (path.length() == 0) {
                              this.createdBeans.put(obj[j], currentBeanPath);
                           } else {
                              this.createdBeans.put(obj[j], path + currentBeanPath);
                           }

                           if (!isTemplated) {
                              this.createBeanScript(name, propDesc, path, obj[j], isTemplated);
                           } else {
                              this.createBeanScript(templatedName, propDesc, path, obj[j], isTemplated);
                           }

                           this.walkTheTree(this.getInterfaceClassName(bean), bean, path + currentBeanPath);
                        }
                     } else {
                        theBean = (DescriptorBean)referredBean;
                        if (this.isJMSBean(referredBean)) {
                           this.jmsBeanToParentMap.put(theBean, parentBean);
                        }

                        name = this.getName(referredBean);
                        currentBeanPath = "";
                        name = "";
                        boolean isTemplated = false;
                        if (!this.isTemplatingEnabled(propDesc.getName(), name)) {
                           currentBeanPath = "/" + propDesc.getName() + "/" + name;
                        }

                        if (path.length() == 0) {
                           this.createdBeans.put(referredBean, currentBeanPath);
                        } else {
                           this.createdBeans.put(referredBean, path + currentBeanPath);
                        }

                        if (!isTemplated) {
                           this.createBeanScript(name, propDesc, path, referredBean, isTemplated);
                        } else {
                           this.createBeanScript(name, propDesc, path, referredBean, isTemplated);
                        }

                        this.walkTheTree(this.getInterfaceClassName(theBean), theBean, path + currentBeanPath);
                     }
                  }
               } else {
                  String[] paths;
                  if (this.isDefaulted(propDesc)) {
                     this.print(this.ctx.getWLSTMsgFormatter().getBeanIsDefaulted(propDesc.getName()));
                     getMethod = propDesc.getReadMethod();
                     obj = null;
                     referredBean = getMethod.invoke(parentBean, obj);
                     theBean = (DescriptorBean)referredBean;
                     paths = null;
                     if (referredBean != null) {
                        if (this.isJMSBean(theBean)) {
                           this.jmsBeanToParentMap.put(theBean, parentBean);
                        }

                        name = this.getName(referredBean);
                        if (name.equals("NO_NAME")) {
                           if (this.processingJMSBean) {
                              name = this.getName(parentBean);
                              if (name.equals("NO_NAME")) {
                                 name = this.currentSystemResourceBeanName;
                              }
                           } else {
                              name = this.currentSystemResourceBeanName;
                           }
                        }

                        currentBeanPath = "";
                        if (this.isSystemResourceMBean(parentBean)) {
                           name = this.getName(parentBean);
                           if (!name.equals(name)) {
                              currentBeanPath = "/" + propDesc.getName() + "/" + name;
                           } else {
                              currentBeanPath = "/" + propDesc.getName() + "/" + name;
                           }
                        } else {
                           currentBeanPath = "/" + propDesc.getName() + "/" + name;
                        }

                        if (path.length() == 0) {
                           this.createdBeans.put(referredBean, currentBeanPath);
                        } else {
                           this.createdBeans.put(referredBean, path + currentBeanPath);
                        }

                        this.walkTheTree(this.getInterfaceClassName(theBean), theBean, path + currentBeanPath);
                     }
                  } else if (!this.isReferal(propDesc)) {
                     if (this.isTransient(propDesc)) {
                        getMethod = propDesc.getReadMethod();
                        obj = null;
                        referredBean = null;

                        try {
                           referredBean = getMethod.invoke(parentBean, obj);
                        } catch (Throwable var18) {
                        }

                        if (referredBean != null && referredBean instanceof DescriptorBean) {
                           theBean = (DescriptorBean)referredBean;
                           if (this.isJMSBean(theBean)) {
                              this.jmsBeanToParentMap.put(theBean, parentBean);
                           }

                           name = this.getName(referredBean);
                           currentBeanPath = "/" + propDesc.getName() + "/" + name;
                           if (path.length() == 0) {
                              this.createdBeans.put(referredBean, currentBeanPath);
                           } else {
                              this.createdBeans.put(referredBean, path + currentBeanPath);
                           }

                           this.walkTheTree(this.getInterfaceClassName(theBean), theBean, path + currentBeanPath);
                        }
                     } else if (this.isReadonlyChild(propDesc) && !this.applicationBean(propDesc, parentBean) && this.libraryBean(propDesc, parentBean)) {
                     }
                  } else {
                     this.print(this.ctx.getWLSTMsgFormatter().getBeanIsAReference(propDesc.getName()));
                     if (parentBean.isSet(propDesc.getName())) {
                        getMethod = propDesc.getReadMethod();
                        obj = null;
                        referredBean = getMethod.invoke(parentBean, obj);
                        String pathToReferredBean;
                        ReferenceBucket rb;
                        if (referredBean instanceof WebLogicMBean) {
                           pathToReferredBean = this.getPathToBean(referredBean);
                           rb = new ReferenceBucket(path, pathToReferredBean, propDesc.getWriteMethod().getName());
                           this.referenceBeans.add(rb);
                        } else if (!(referredBean instanceof WebLogicMBean[])) {
                           if (this.isDestinationBean(referredBean)) {
                              if (this.createdBeans.containsKey(referredBean)) {
                                 pathToReferredBean = this.createdBeans.get(referredBean).toString();
                                 rb = new ReferenceBucket(path, pathToReferredBean, propDesc.getWriteMethod().getName());
                                 this.referenceBeans.add(rb);
                              } else {
                                 ReferenceBucket rb = new ReferenceBucket(path, (String)null, propDesc.getWriteMethod().getName());
                                 this.referredDestinationBeanMap.put(referredBean, rb);
                              }
                           }
                        } else {
                           Object[] objArray = (Object[])((Object[])referredBean);
                           paths = new String[objArray.length];

                           for(int k = 0; k < objArray.length; ++k) {
                              paths[k] = this.getPathToBean(objArray[k]);
                           }

                           ReferenceBucketArray rba = new ReferenceBucketArray(path, paths, propDesc.getWriteMethod().getName(), this.getClassName(propDesc.getPropertyType()).getName());
                           this.referenceBeanArrays.add(rba);
                        }
                     }
                  }
               }
            }
         }
      } catch (Exception var19) {
         var19.printStackTrace();
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getExceptionWalkingTree(), var19);
      }

   }

   private boolean isDestinationBean(Object referredBean) {
      return referredBean instanceof DestinationBean;
   }

   private boolean isTemplatingEnabled(String type, String name) {
      return false;
   }

   private boolean applicationBean(PropertyDescriptor pd, Object parentBean) throws ScriptException {
      String creator = (String)pd.getValue("creator");
      String des = (String)pd.getValue("destroyer");
      boolean result = false;

      try {
         if (pd.getName().equals("AppDeployments") || creator != null && creator.equals("createAppDeployment") && des != null && des.equals("destroyAppDeployment")) {
            result = true;
            if (!this.processedApplicationBeans) {
               Method mth = pd.getReadMethod();
               Object[] obj = null;
               Object[] appBeans = (Object[])((Object[])mth.invoke(parentBean, (Object[])obj));

               for(int i = 0; i < appBeans.length; ++i) {
                  this.applicationBeans.add(appBeans[i]);
               }

               this.processedApplicationBeans = true;
            }
         }
      } catch (Throwable var10) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorFindingAppBean(), var10);
      }

      return result;
   }

   private boolean libraryBean(PropertyDescriptor pd, Object parentBean) throws ScriptException {
      String creator = (String)pd.getValue("creator");
      String des = (String)pd.getValue("destroyer");
      boolean result = false;

      try {
         if (pd.getName().equals("Libraries") || creator != null && creator.equals("createLibrary") && des != null && des.equals("destroyLibrary")) {
            result = true;
            if (!this.processedLibraryBeans) {
               Method mth = pd.getReadMethod();
               Object[] obj = null;
               Object[] libBeans = (Object[])((Object[])mth.invoke(parentBean, (Object[])obj));

               for(int i = 0; i < libBeans.length; ++i) {
                  this.libraryBeans.add(libBeans[i]);
               }

               this.processedLibraryBeans = true;
            }
         }
      } catch (Throwable var10) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorFindingAppBean(), var10);
      }

      return result;
   }

   private String getSubDeployString(SubDeploymentMBean sbean) {
      TargetMBean[] targets = sbean.getTargets();
      String[] tgts = new String[targets.length];

      for(int i = 0; i < targets.length; ++i) {
         tgts[i] = sbean.getName() + "@" + targets[i].getName();
      }

      return StringUtils.join(tgts, ",");
   }

   private void processDeploymentBeans(List deploymentBeans, DeploymentType deploymentType) {
      Collections.sort(deploymentBeans, deploymentType);
      Iterator iter = deploymentBeans.iterator();

      while(true) {
         AppDeploymentMBean app;
         boolean isLibraryModule;
         do {
            if (!iter.hasNext()) {
               return;
            }

            app = (AppDeploymentMBean)iter.next();
            isLibraryModule = app instanceof LibraryMBean;
         } while(app.isInternalApp());

         String appName = app.getName();
         String appPath = this.replaceEscapeCharacters(app.getAbsoluteSourcePath());
         String planPath = this.replaceEscapeCharacters(app.getAbsolutePlanPath());
         String secModel = app.getSecurityDDModel();
         String stagingMode = app.getStagingMode();
         SubDeploymentMBean[] sbeans = app.getSubDeployments();
         String[] subTargets = new String[sbeans.length];

         for(int i = 0; i < sbeans.length; ++i) {
            subTargets[i] = this.getSubDeployString(sbeans[i]);
         }

         String targets = this.getTargetNames(app.getTargets());
         String fname = "deploy_" + appName + "_" + this.getCounter();
         fname = this.getFunctionNameFromResource(fname);
         this.applicationDeploymentFunctionNames.add(fname);
         this.applicationDeploymentScript.append("def " + fname + "():\n");
         this.applicationDeploymentScript.append("  try:\n");
         String depString = "deploy(\"" + appName + "\",\"" + appPath + "\",";
         if (targets != null && targets.length() > 0) {
            depString = depString + "\"" + targets + "\",";
         }

         if (subTargets.length > 0) {
            depString = depString + "subModuleTargets=\"" + StringUtils.join(subTargets, ",") + "\",";
         }

         if (stagingMode != null) {
            depString = depString + "stageMode=\"" + stagingMode + "\",";
         }

         if (secModel != null) {
            depString = depString + "securityModel=\"" + secModel + "\",";
         }

         if (isLibraryModule) {
            depString = depString + "libraryModule=\"true\",";
         }

         if (app.getDeploymentOrder() != 100) {
            depString = depString + "deploymentOrder=" + app.getDeploymentOrder() + ",";
         }

         if (planPath != null) {
            depString = depString + "planPath=\"" + planPath + "\",";
         }

         depString = depString + "block=\"true\")";
         this.applicationDeploymentScript.append("    " + depString + "\n");
         this.applicationDeploymentScript.append("  except:\n");
         this.applicationDeploymentScript.append("    print \"" + this.ctx.getWLSTMsgFormatter().getCouldNotDeployApp(appName) + "\"\n");
         this.applicationDeploymentScript.append("\n");
      }
   }

   private void addDeploymentFunctionCalls() {
      Iterator _iter = this.applicationDeploymentFunctionNames.iterator();

      while(_iter.hasNext()) {
         String _fname = (String)_iter.next();
         this.applicationDeploymentFunctionCalls.append("  " + _fname + "()\n");
      }

   }

   private String getTargetNames(TargetMBean[] beans) {
      String[] sa = new String[beans.length];

      for(int i = 0; i < beans.length; ++i) {
         sa[i] = beans[i].getName();
      }

      return StringUtils.join(sa, ",");
   }

   private Object getParentForBean(Object bean) throws ScriptException {
      String bs;
      try {
         Object[] obj = null;
         bs = null;
         Method getParent = bean.getClass().getMethod("getParent", bs);
         return getParent.invoke(bean, (Object[])obj);
      } catch (Throwable var5) {
         bs = bean == null ? null : bean.toString();
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorFindingParent(bs), var5);
         return null;
      }
   }

   private String getPathToBean(Object bean) {
      try {
         Stack beans = new Stack();
         beans.add(bean);
         Object par = this.getParentForBean(bean);

         while(par != null) {
            if (par instanceof DomainMBean) {
               par = null;
            } else {
               par = this.getParentForBean(par);
               beans.add(par);
            }
         }

         if (beans.size() == 1) {
            Object parent = this.getParentForBean(bean);
            BeanInfo info = this.beanInfoAccess.getBeanInfoForInstance(parent, false, "9.0.0");
            PropertyDescriptor[] pds = info.getPropertyDescriptors();
            String attrName = null;

            for(int i = 0; i < pds.length; ++i) {
               PropertyDescriptor pd = pds[i];
               String rel = (String)pd.getValue("relationship");
               if (rel != null && ((String)pd.getValue("relationship")).equals("containment")) {
                  Method mth = pd.getReadMethod();
                  Object[] objs = null;
                  Object children = mth.invoke(parent, (Object[])objs);
                  if (children != null && children.toString().startsWith("[L")) {
                     Object[] childs = (Object[])((Object[])children);

                     for(int j = 0; j < childs.length; ++j) {
                        if (childs[j].equals(bean)) {
                           attrName = pd.getName();
                           break;
                        }
                     }
                  }
               }
            }

            if (attrName != null) {
               String name = this.getName(bean);
               return "/" + attrName + "/" + name;
            }

            System.out.println(this.ctx.getWLSTMsgFormatter().getUnexpectedError("null attribute"));
         }
      } catch (Exception var16) {
         var16.printStackTrace();
      }

      return null;
   }

   private boolean resolveReferences(Object bean, String path, StringBuffer buf) {
      boolean didResolve = false;
      Iterator iter = this.referenceBeanArrays.iterator();

      while(true) {
         ReferenceBucketArray rba;
         String pathToBean;
         String type;
         String methodName;
         do {
            if (!iter.hasNext()) {
               Iterator _iter = this.referenceBeans.iterator();

               while(_iter.hasNext()) {
                  ReferenceBucket rb = (ReferenceBucket)_iter.next();
                  String pathToBean = rb.getReferalPath();
                  if (path.equals(pathToBean)) {
                     didResolve = true;
                     type = rb.getReferentPath();
                     methodName = rb.getMethodName();
                     buf.append("  bean = getMBean(\"" + type + "\")\n");
                     buf.append("  cmo." + methodName + "(bean)\n");
                     buf.append("\n");
                  }
               }

               return didResolve;
            }

            rba = (ReferenceBucketArray)iter.next();
            pathToBean = rba.getReferalPath();
         } while(!path.equals(pathToBean));

         didResolve = true;
         String[] refPaths = rba.getReferentPath();
         type = rba.getType();
         methodName = rba.getMethodName();
         String gets = "";
         String val = "";

         for(int i = 0; i < refPaths.length; ++i) {
            if (val.length() > 0) {
               val = val + ",refBean" + i;
            } else {
               val = val + "refBean" + i;
            }

            gets = gets + "  refBean" + i + " = getMBean(\"" + refPaths[i] + "\")\n";
         }

         buf.append(gets);
         buf.append("  theValue = jarray.array([" + val + "], Class.forName(\"" + type + "\"))\n");
         buf.append("  cmo." + methodName + "(theValue)\n");
         buf.append("\n");
      }
   }

   private Class getClassName(Class clz) throws ClassNotFoundException {
      return clz.getName().startsWith("[L") ? Class.forName(clz.getName().substring(2, clz.getName().length() - 1)) : clz;
   }

   private boolean isTransient(PropertyDescriptor pd) {
      Boolean rel = (Boolean)pd.getValue("transient");
      return rel == null ? false : rel;
   }

   private boolean isDeprecated(PropertyDescriptor pd) {
      String rel = (String)pd.getValue("deprecated");
      return rel == null ? false : Boolean.valueOf(rel);
   }

   private boolean isJMSBean(Object bean) {
      return bean instanceof JMSBean;
   }

   private String getNameForJMSBean(Object bean) {
      Object parent = this.jmsBeanToParentMap.get(bean);
      return parent != null ? ((JMSSystemResourceMBean)parent).getName() : null;
   }

   private String getName(Object bean) throws ScriptException {
      try {
         Object[] obj = null;
         Method nameMethod = null;
         Method[] mths = bean.getClass().getMethods();

         for(int i = 0; i < mths.length; ++i) {
            if (mths[i].getName().equals("getName")) {
               nameMethod = mths[i];
               break;
            }
         }

         if (nameMethod == null) {
            return this.isJMSBean(bean) ? this.getNameForJMSBean(bean) : "NO_NAME";
         } else {
            String name = (String)nameMethod.invoke(bean, (Object[])obj);
            if (name == null) {
               return this.isJMSBean(bean) ? this.getNameForJMSBean(bean) : "NO_NAME";
            } else {
               try {
                  ObjectName on = new ObjectName(name);
                  String x = on.getKeyProperty("Name");
                  if (x != null) {
                     return x;
                  }
               } catch (MalformedObjectNameException var8) {
                  return name;
               }

               return name;
            }
         }
      } catch (Throwable var9) {
         String bs = bean == null ? "null" : bean.toString();
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorGettingBeanName(bs), var9);
         return null;
      }
   }

   private String getInterfaceClassName(DescriptorBean bean) {
      return (String)this.beanInfoAccess.getBeanInfoForInstance(bean, false, "9.0.0").getBeanDescriptor().getValue("interfaceclassname");
   }

   private void setBeanAttributes() throws ScriptException {
      this.updateReferenceBeansList();
      Set beans = this.createdBeans.keySet();
      Iterator iter = beans.iterator();

      while(iter.hasNext()) {
         Object obj = iter.next();
         String path = (String)this.createdBeans.get(obj);
         this.createSetBeanAttributes(obj, path);
      }

   }

   private void updateReferenceBeansList() {
      Iterator var1 = this.referredDestinationBeanMap.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry referredDestinationBeanEntry = (Map.Entry)var1.next();
         String pathToReferredBean = this.createdBeans.get(referredDestinationBeanEntry.getKey()).toString();
         ReferenceBucket referenceBucket = (ReferenceBucket)referredDestinationBeanEntry.getValue();
         ReferenceBucket rb = new ReferenceBucket(referenceBucket.getReferalPath(), pathToReferredBean, referenceBucket.getMethodName());
         this.referenceBeans.add(rb);
      }

      if (!this.referredDestinationBeanMap.isEmpty()) {
         this.referredDestinationBeanMap.clear();
      }

   }

   private void encryptDefaultUsernameAndPassword() {
      this.createLocalStorageForPasswords("".toCharArray(), "weblogic.management", "");

      try {
         String msg = this.ctx.getWLSTMsgFormatter().getUpdateConfiguration(this.propertiesFile.getCanonicalPath());
         this.ctx.println(msg);
      } catch (Exception var2) {
      }

   }

   private void createSetBeanAttributes(Object bean, String path) throws ScriptException {
      try {
         BeanInfo info = this.beanInfoAccess.getBeanInfoForInstance(bean, false, "9.0.0");
         StringBuffer buf = new StringBuffer();
         PropertyDescriptor[] propDescInfos = info.getPropertyDescriptors();
         Iterator iter = this.beanFunctionNameList.iterator();
         String funcName = null;
         FunctionNameBucket _buck = null;

         while(iter.hasNext()) {
            FunctionNameBucket buck = (FunctionNameBucket)iter.next();
            if (buck.getBean().equals(bean)) {
               funcName = buck.getSetFunctionName();
               _buck = buck;
            }
         }

         boolean didInc = false;
         String mbeanType = this.getTypeFromDecsriptor(info.getBeanDescriptor());
         if (funcName == null) {
            funcName = "setAttributes_" + mbeanType + "_" + this.getCounter();
            didInc = true;
         }

         buf.append("def " + funcName + "():\n");
         if (path.length() > 0) {
            buf.append("  cd(\"" + path + "\")\n");
         } else {
            buf.append("  cd(\"/\")\n");
         }

         buf.append("  print \"" + this.ctx.getWLSTMsgFormatter().getSettingAttributes(mbeanType) + "\"\n");
         boolean didSet = false;

         for(int i = 0; i < propDescInfos.length; ++i) {
            PropertyDescriptor propDesc = propDescInfos[i];
            DescriptorBean dBean = (DescriptorBean)bean;
            String attrName = propDesc.getName();
            if (dBean.isSet(attrName) && propDesc.getWriteMethod() != null) {
               if (!this.isChild(propDesc) && !this.isDefaulted(propDesc) && !this.isReferal(propDesc) && (!(bean instanceof WebLogicMBean) || !attrName.equals("Name"))) {
                  Object[] objs = null;
                  Object s = propDesc.getReadMethod().invoke(bean, (Object[])objs);
                  if (s != null) {
                     if (s instanceof String) {
                        s = StringUtils.escapeString((String)s);
                     }

                     String _s;
                     if (this.isEncrypted(propDesc)) {
                        _s = "c2s" + this.getCounter();
                        if (attrName.endsWith("Encrypted")) {
                           attrName = StringUtils.replaceGlobal(attrName, "Encrypted", "");
                           Class[] clzz = null;
                           Method getP = bean.getClass().getMethod("get" + attrName, (Class[])clzz);
                           Object[] p1 = null;
                           s = getP.invoke(bean, (Object[])p1);
                        }

                        this.createLocalStorageForPasswords(s.toString().toCharArray(), _s, "");
                        buf.append("  setEncrypted(\"" + attrName + "\", \"" + _s + "\", \"" + this.CONFIG_TO_SCRIPT_USER_CONFIG_FILE + "\", \"" + this.CONFIG_TO_SCRIPT_SECRET_FILE + "\")\n");
                        didSet = true;
                     } else {
                        if (propDesc.getPropertyType().getName().equals("[B")) {
                           s = this.replaceByteArrayWithString(s);
                        }

                        this.print(this.ctx.getWLSTMsgFormatter().getAttributeSet(attrName, bean.toString()));
                        if (s.getClass().isArray()) {
                           _s = this.getArrayObject(s);
                           if (_s != null) {
                              buf.append("  set(\"" + attrName + "\", " + _s + ")\n");
                           }
                        } else {
                           buf.append("  set(\"" + attrName + "\", \"" + s.toString() + "\")\n");
                        }

                        didSet = true;
                     }
                  }
               } else {
                  this.print(this.ctx.getWLSTMsgFormatter().getContinueInBean());
               }
            }
         }

         boolean didSetReference = this.resolveReferences(bean, path, buf);
         if (_buck != null && !didSet && !didSetReference) {
            _buck.setCallSetter(false);
         }

         if (!didSet && !didSetReference) {
            if (didInc) {
               this.releaseCounter();
            }
         } else {
            if (_buck == null) {
               this.functionNames.add(funcName);
            }

            buf.append("\n");
            this.mainScriptBuffer.append(buf.toString());
         }
      } catch (Throwable var22) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getErrorMakingSetBeans(), var22);
      }

   }

   private String getArrayObject(Object obj) {
      if (obj instanceof String[]) {
         String[] values = (String[])((String[])obj);
         String s = "jarray.array([";

         for(int i = 0; i < values.length; ++i) {
            if (i != values.length - 1) {
               s = s + "\"" + values[i] + "\",";
            } else {
               s = s + "\"" + values[i] + "\"";
            }
         }

         s = s + "], String)";
         return s;
      } else {
         System.out.println("<DEBUG> ONLY String[] supported. If you see this Please file a CR.");
         return null;
      }
   }

   private void createLocalStorageForPasswords(char[] passwordValue, String propertyName, String userName) {
      System.setProperty("weblogic.management.confirmKeyfileCreation", "true");
      File pf = this.pyFile.getParentFile();
      if (pf == null) {
         pf = new File(".");
      }

      this.CONFIG_TO_SCRIPT_USER_CONFIG_FILE = this.replaceEscapeCharacters(pf.getAbsolutePath() + "/c2sConfig" + this.domainMBean.getName());
      this.CONFIG_TO_SCRIPT_SECRET_FILE = this.replaceEscapeCharacters(pf.getAbsolutePath() + "/c2sSecret" + this.domainMBean.getName());
      UsernameAndPassword up = new UsernameAndPassword(userName, passwordValue);
      UserConfigFileManager.setUsernameAndPassword(up, this.CONFIG_TO_SCRIPT_USER_CONFIG_FILE, this.CONFIG_TO_SCRIPT_SECRET_FILE, propertyName);
   }

   private String getTypeFromDecsriptor(BeanDescriptor bd) {
      String pack = (String)bd.getValue("package");
      String inter = (String)bd.getValue("interfaceclassname");
      String val = inter.replaceAll(pack + ".", "");
      if (val.endsWith("MBean")) {
         val = val.substring(0, val.length() - 5);
      } else if (val.endsWith("Bean")) {
         val = val.substring(0, val.length() - 4);
      }

      return val;
   }

   private String replaceEscapeCharacters(Object s) {
      if (s == null) {
         return null;
      } else {
         String val = (String)s;
         val = StringUtils.replaceGlobal(val, File.separator, "/");
         return val;
      }
   }

   private String replaceByteArrayWithString(Object s) {
      if (s == null) {
         return null;
      } else {
         String k = new String((byte[])((byte[])s));
         return k;
      }
   }

   private String getTypeFromCreateMethod(String cm) {
      return cm.substring(6, cm.length());
   }

   private void createBeanScript(String beanName, PropertyDescriptor pd, String path, Object bean, boolean isTemplated) {
      String creatorMethod = null;
      String baseCreatorMethod = (String)pd.getValue("creator");
      String intfName = this.getInterfaceClassName((DescriptorBean)bean);
      String beanType;
      if (intfName != null) {
         int pos = intfName.lastIndexOf(46) + 1;
         beanType = intfName.substring(pos, intfName.length());
         creatorMethod = (String)pd.getValue("creator." + beanType);
      }

      if (creatorMethod == null) {
         creatorMethod = baseCreatorMethod;
      }

      String lookupMethod = baseCreatorMethod.replaceFirst("create", "lookup");
      if (!this.dummyCreators.contains(creatorMethod)) {
         beanType = this.getTypeFromCreateMethod(creatorMethod);
         String funcName = "create_" + beanType + "_" + this.getCounter();
         funcName = this.getFunctionNameFromResource(funcName);
         String setterFuncName = "setAttributesFor_" + beanName + "_" + this.getCounter();
         setterFuncName = this.getFunctionNameFromResource(setterFuncName);
         String existingCreateFuncName = (String)this.createMethods.get(beanType);
         if (existingCreateFuncName != null) {
            funcName = existingCreateFuncName;
         }

         FunctionNameBucket bucket = new FunctionNameBucket(funcName, setterFuncName, bean, path, beanName);
         this.beanFunctionNameList.add(bucket);
         if (existingCreateFuncName == null) {
            this.createMethods.put(beanType, funcName);
            this.mainScriptBuffer.append("def " + funcName + "(path, beanName):\n");
            this.mainScriptBuffer.append("  cd(path)\n");
            this.mainScriptBuffer.append("  try:\n");
            this.mainScriptBuffer.append("    print \"" + this.ctx.getWLSTMsgFormatter().getCreatingMBean(this.getTypeFromCreateMethod(creatorMethod)) + " \"\n");
            if (this.isSecurityMBean(bean)) {
               String interfaceClzName = this.getInterfaceClassName((DescriptorBean)bean);
               interfaceClzName = interfaceClzName.substring(0, interfaceClzName.length() - 5);
               if (pd.getName().equals("Adjudicator")) {
                  this.mainScriptBuffer.append("    theBean = cmo.getAdjudicator()\n");
               } else {
                  this.mainScriptBuffer.append("    theBean = cmo." + lookupMethod + "(beanName)\n");
               }

               this.mainScriptBuffer.append("    if theBean == None:\n");
               this.mainScriptBuffer.append("      cmo." + creatorMethod + "(beanName,\"" + interfaceClzName + "\")\n");
            } else if (this.isServerFailureTriggerMBean(bean)) {
               this.mainScriptBuffer.append("    theBean = cmo.getServerFailureTrigger()\n");
               this.mainScriptBuffer.append("    if theBean == None:\n");
               this.mainScriptBuffer.append("      cmo." + creatorMethod + "()\n");
            } else if (this.isDeprecated(pd)) {
               this.mainScriptBuffer.append("    cmo." + creatorMethod + "(beanName)\n");
            } else {
               this.mainScriptBuffer.append("    theBean = cmo." + lookupMethod + "(beanName)\n");
               this.mainScriptBuffer.append("    if theBean == None:\n");
               if (!isTemplated) {
                  this.mainScriptBuffer.append("      cmo." + creatorMethod + "(beanName)\n");
               } else {
                  this.mainScriptBuffer.append("      cmo." + creatorMethod + "(beanName)\n");
               }
            }

            this.mainScriptBuffer.append("  except java.lang.UnsupportedOperationException, usoe:\n");
            this.mainScriptBuffer.append("    pass\n");
            this.mainScriptBuffer.append("  except weblogic.descriptor.BeanAlreadyExistsException,bae:\n");
            this.mainScriptBuffer.append("    pass\n");
            this.mainScriptBuffer.append("  except java.lang.reflect.UndeclaredThrowableException,udt:\n");
            this.mainScriptBuffer.append("    pass\n");
            if (creatorMethod.equals("createProperty")) {
               this.mainScriptBuffer.append("  except TypeError:\n");
               if (beanName == null) {
                  this.mainScriptBuffer.append("    pass\n");
                  this.createMethods.remove(beanType);
               } else {
                  this.mainScriptBuffer.append("    prop = cmo.createProperty()\n");
                  this.mainScriptBuffer.append("    prop.setName(beanName)\n");
               }
            }

            this.mainScriptBuffer.append("\n");
         }
      }
   }

   private boolean isSecurityMBean(Object bean) {
      return bean instanceof ProviderMBean;
   }

   private boolean isSystemResourceMBean(Object bean) {
      return bean instanceof JDBCSystemResourceMBean || bean instanceof WLDFSystemResourceMBean || bean instanceof JMSSystemResourceMBean;
   }

   private boolean isServerFailureTriggerMBean(Object bean) {
      return bean instanceof ServerFailureTriggerMBean;
   }

   private String getFunctionNameFromResource(String resource) {
      byte[] bytes = null;

      byte[] bytes;
      try {
         bytes = resource.getBytes("UTF-8");
      } catch (UnsupportedEncodingException var8) {
         bytes = resource.getBytes();
      }

      byte[] funcNameBuf = new byte[bytes.length * 3];
      int pos = 0;

      for(int i = 0; i < bytes.length; ++i) {
         if ((bytes[i] < 97 || bytes[i] > 122) && (bytes[i] < 65 || bytes[i] > 90) && (bytes[i] < 48 || bytes[i] > 57) && bytes[i] != 95) {
            switch (bytes[i]) {
               case 32:
               case 33:
               case 36:
               case 37:
               case 38:
               case 40:
               case 41:
               case 42:
               case 44:
               case 45:
               case 46:
               case 47:
               case 58:
               case 59:
               case 61:
               case 64:
               case 91:
               case 93:
               case 94:
               case 123:
               case 124:
               case 125:
               case 126:
                  funcNameBuf[pos++] = 95;
                  break;
               case 34:
               case 35:
               case 39:
               case 43:
               case 48:
               case 49:
               case 50:
               case 51:
               case 52:
               case 53:
               case 54:
               case 55:
               case 56:
               case 57:
               case 60:
               case 62:
               case 63:
               case 65:
               case 66:
               case 67:
               case 68:
               case 69:
               case 70:
               case 71:
               case 72:
               case 73:
               case 74:
               case 75:
               case 76:
               case 77:
               case 78:
               case 79:
               case 80:
               case 81:
               case 82:
               case 83:
               case 84:
               case 85:
               case 86:
               case 87:
               case 88:
               case 89:
               case 90:
               case 92:
               case 95:
               case 96:
               case 97:
               case 98:
               case 99:
               case 100:
               case 101:
               case 102:
               case 103:
               case 104:
               case 105:
               case 106:
               case 107:
               case 108:
               case 109:
               case 110:
               case 111:
               case 112:
               case 113:
               case 114:
               case 115:
               case 116:
               case 117:
               case 118:
               case 119:
               case 120:
               case 121:
               case 122:
               default:
                  String hex = Hex.asHex(bytes[i]);
                  byte[] hexBytes = hex.getBytes();
                  System.arraycopy(hexBytes, 0, funcNameBuf, pos, hexBytes.length);
                  pos += hexBytes.length;
            }
         } else {
            funcNameBuf[pos++] = bytes[i];
         }
      }

      return new String(funcNameBuf, 0, pos);
   }

   private int getCounter() {
      int i = this.beansCounter++;
      return i;
   }

   private int releaseCounter() {
      int i = this.beansCounter--;
      return i;
   }

   private boolean isChild(PropertyDescriptor pd) {
      String rel = (String)pd.getValue("relationship");
      if (rel == null) {
         return false;
      } else {
         String creator = (String)pd.getValue("creator");
         String des = (String)pd.getValue("destroyer");
         return rel.equals("containment") && creator != null && des != null;
      }
   }

   private boolean isReadonlyChild(PropertyDescriptor pd) {
      String rel = (String)pd.getValue("relationship");
      if (rel == null) {
         return false;
      } else {
         return rel.equals("containment");
      }
   }

   private boolean isDefaulted(PropertyDescriptor pd) {
      String rel = (String)pd.getValue("relationship");
      if (rel == null) {
         return false;
      } else {
         String creator = (String)pd.getValue("creator");
         String des = (String)pd.getValue("destroyer");
         if (rel.equals("containment") && creator != null && des != null) {
            return false;
         } else if (rel.equals("containment")) {
            return !pd.getName().equals("AppDeployments") && !pd.getName().equals("Libraries");
         } else {
            return false;
         }
      }
   }

   private boolean isEncrypted(PropertyDescriptor pd) {
      Boolean obj = (Boolean)pd.getValue("encrypted");
      return obj != null ? obj : false;
   }

   private boolean isExcluded(PropertyDescriptor pd) {
      Boolean obj = (Boolean)pd.getValue("exclude");
      return obj != null ? obj : false;
   }

   private boolean isReferal(PropertyDescriptor pd) {
      String rel = (String)pd.getValue("relationship");
      if (rel == null) {
         return false;
      } else {
         return rel.equals("reference");
      }
   }

   private void writeFile(File file) throws ScriptException {
      try {
         FileWriter out = new FileWriter(file);
         out.write(this.theScript.toString());
         out.flush();
         out.close();
      } catch (IOException var3) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getIOExceptionWritingFile(file.getAbsolutePath()), var3);
      }

   }

   private String header() {
      StringBuffer sb1 = new StringBuffer();
      sb1.append("\"\"\"\n");
      sb1.append("This is a WLST script that is generated by the WebLogic Scripting Tool (WLST)\n");

      try {
         sb1.append("Configuration file converted               : " + this.replaceEscapeCharacters(this.configFile.getCanonicalPath()) + "\n");
         sb1.append("WLST script generated to file              : " + this.replaceEscapeCharacters(this.pyFile.getCanonicalPath()) + "\n");
         sb1.append("Properties file associated with the script : " + this.replaceEscapeCharacters(this.propertiesFile.getCanonicalPath()) + "\n");
      } catch (IOException var3) {
         var3.printStackTrace();
      }

      sb1.append("\n");
      sb1.append("This script will first try to connect to a running server using the \nvalues in the properties file. If there is no server running, WLST\nwill start a server with the values in the properties file. You should change\nthese values to suit your environmental needs. After running the script, \nthe server that is started(if started one) will be shutdown. \nThis might exit you from your WLST shell.");
      sb1.append("\"\"\"\n");
      sb1.append("\n");
      sb1.append(this.addImports());
      sb1.append("\n");
      sb1.append("def initConfigToScriptRun():\n");
      sb1.append("  global startedNewServer\n");
      sb1.append("  loadProperties(\"" + this.replaceEscapeCharacters(this.propertiesFile.getAbsolutePath()) + "\")\n");
      sb1.append("  hideDisplay()\n");
      sb1.append("  hideDumpStack(\"true\")\n");
      sb1.append("  # try connecting to a running server if it is already running ... \n");
      sb1.append("  if connected==\"false\":\n");
      sb1.append("    try:\n");
      sb1.append("      URL=\"t3://\"+adminServerListenAddress+\":\"+adminServerListenPort\n");
      sb1.append("      connect(userName, passWord, URL, userConfigFile=\"" + this.CONFIG_TO_SCRIPT_USER_CONFIG_FILE + "\", " + "userKeyFile" + "=\"" + this.CONFIG_TO_SCRIPT_SECRET_FILE + "\")\n");
      sb1.append("    except WLSTException:\n");
      sb1.append("      print 'No server is running at '+URL+', the script will start a new server'\n");
      sb1.append("  hideDumpStack(\"false\")\n");
      sb1.append("  if connected==\"false\":\n");
      sb1.append("    print 'Starting a brand new server at '+URL+' with server name '+adminServerName\n");
      sb1.append("    print 'Please see the server log files for startup messages available at '+domainDir\n");
      sb1.append("    # If a config.xml exists in the domainDir, WLST will use that config.xml to bring up the server. \n");
      sb1.append("    # If you would like WLST to overwrite this directory, you should specify overWriteRootDir='true' as shown below\n");
      sb1.append("    # startServer(adminServerName, domName, URL, userName, passWord,domainDir, overWriteRootDir='true')\n");
      sb1.append("    _timeOut = Integer(TimeOut)\n");
      sb1.append("    # If you want to specify additional JVM arguments, set them using startServerJvmArgs in the property file or below\n");
      sb1.append("    _startServerJvmArgs=startServerJvmArgs\n");
      sb1.append("    if overWriteRootDir=='true':\n");
      sb1.append("      startServer(adminServerName, domName, URL, userName, passWord,domainDir, timeout=_timeOut.intValue(), overWriteRootDir='true', block='true', jvmArgs=_startServerJvmArgs)\n");
      sb1.append("    else:\n");
      sb1.append("      startServer(adminServerName, domName, URL, userName, passWord,domainDir, timeout=_timeOut.intValue(), block='true', jvmArgs=_startServerJvmArgs)\n");
      sb1.append("    startedNewServer=1\n");
      sb1.append("    print \"Started Server. Trying to connect to the server ... \"\n");
      sb1.append("    connect(userName, passWord, URL, userConfigFile=\"" + this.CONFIG_TO_SCRIPT_USER_CONFIG_FILE + "\", " + "userKeyFile" + "=\"" + this.CONFIG_TO_SCRIPT_SECRET_FILE + "\")\n");
      sb1.append("    if connected=='false':\n");
      sb1.append("      stopExecution('You need to be connected.')\n");
      sb1.append("\n");
      sb1.append("def startTransaction():\n");
      sb1.append("  edit()\n");
      sb1.append("  startEdit()\n");
      sb1.append("\n");
      sb1.append("def endTransaction():\n");
      sb1.append("  startEdit()\n");
      sb1.append("  save()\n");
      sb1.append("  activate(block=\"true\")\n");
      sb1.append("\n");
      sb1.append("from javax.management import InstanceAlreadyExistsException\n");
      sb1.append("from java.lang import Exception\n");
      sb1.append("from jarray import array\n");
      sb1.append("\n");
      return sb1.toString();
   }

   private String tail() {
      StringBuffer sb2 = new StringBuffer();
      sb2.append("def endOfConfigToScriptRun():\n");
      sb2.append("  global startedNewServer\n");
      sb2.append("  #Save the changes you have made\n");
      sb2.append("  # shutdown the server you have started\n");
      sb2.append("  if startedNewServer==1:\n");
      sb2.append("    print 'Shutting down the server that is started... '\n");
      sb2.append("    shutdown(force='true', block='true')\n");
      sb2.append("  print 'Done executing the script.'\n");
      sb2.append("\n");
      return sb2.toString();
   }

   private void createDefaultProperties(File propFile) throws ScriptException {
      try {
         FileOutputStream oStream = new FileOutputStream(propFile);
         Properties props = new Properties();
         props.setProperty("userName", "");
         props.setProperty("passWord", "");
         props.setProperty("domainDir", "WLSTConfigToScriptDomain");
         String asName = this.domainMBean.getAdminServerName();
         props.setProperty("adminServerName", asName);
         ServerMBean sbean = this.domainMBean.lookupServer(asName);
         String la = sbean.getListenAddress();
         if (la == null || la.length() == 0) {
            la = "localhost";
         }

         props.setProperty("adminServerListenAddress", la);
         props.setProperty("adminServerListenPort", String.valueOf(sbean.getListenPort()));
         props.setProperty("domName", this.domainMBean.getName());
         props.setProperty("exitonerror", "false");
         props.setProperty("startedNewServer", "0");
         props.setProperty("overWriteRootDir", "true");
         props.setProperty("TimeOut", "240000");
         props.setProperty("startServerJvmArgs", "");
         props.store(oStream, "WLST ConfigToScript Default Properties file ");
         oStream.close();
      } catch (IOException var7) {
         this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getIOExceptionWritingFile(propFile.toString()), var7);
      }

   }

   public static void main(String[] args) throws Exception {
      if (args.length != 1) {
         System.err.println("Usage: java weblogic.management.scripting.ConfigToScript configPath");
         System.exit(1);
      }

      ConfigToScript cts = new ConfigToScript(new File(args[0] + File.separator + "config" + File.separator + "config.xml"), (String)null, (String)null, (String)null, (String)null, (String)null, new WLScriptContext());
      cts.convert();
   }

   class ReferenceBucketArray {
      String referalBeanPath;
      String[] referentBeanPath;
      String methodName;
      String type;

      ReferenceBucketArray(String referal, String[] referent, String methName, String type) {
         this.referalBeanPath = referal;
         this.referentBeanPath = referent;
         this.methodName = methName;
         this.type = type;
      }

      String getReferalPath() {
         return this.referalBeanPath;
      }

      String[] getReferentPath() {
         return this.referentBeanPath;
      }

      String getMethodName() {
         return this.methodName;
      }

      String getType() {
         return this.type;
      }
   }

   class ReferenceBucket {
      String referalBeanPath;
      String referentBeanPath;
      String methodName;

      ReferenceBucket(String referal, String referent, String methName) {
         this.referalBeanPath = referal;
         this.referentBeanPath = referent;
         this.methodName = methName;
      }

      String getReferalPath() {
         return this.referalBeanPath;
      }

      String getReferentPath() {
         return this.referentBeanPath;
      }

      String getMethodName() {
         return this.methodName;
      }
   }

   class FunctionNameBucket {
      String create_function_name;
      String set_function_name;
      Object bean;
      String path;
      String beanName;
      boolean callTheSetter = true;

      FunctionNameBucket(String se, String rt, Object res, String path, String beanName) {
         this.create_function_name = se;
         this.set_function_name = rt;
         this.bean = res;
         this.path = path;
         this.beanName = beanName;
      }

      String getCreateFunctionName() {
         return this.create_function_name;
      }

      String getSetFunctionName() {
         return this.set_function_name;
      }

      Object getBean() {
         return this.bean;
      }

      String getPath() {
         return this.path;
      }

      String getBeanName() {
         return this.beanName;
      }

      boolean callSetter() {
         return this.callTheSetter;
      }

      void setCallSetter(boolean bool) {
         this.callTheSetter = bool;
      }
   }
}
