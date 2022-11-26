package weblogic.diagnostics.instrumentation.engine;

import com.bea.xml.XmlException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.DuplicateMonitorException;
import weblogic.diagnostics.instrumentation.InstrumentationDebug;
import weblogic.diagnostics.instrumentation.InstrumentationException;
import weblogic.diagnostics.instrumentation.InvalidPointcutException;
import weblogic.diagnostics.instrumentation.MonitorNotFoundException;
import weblogic.diagnostics.instrumentation.engine.base.InstrumentationEngineConstants;
import weblogic.diagnostics.instrumentation.engine.base.PointcutExpression;
import weblogic.diagnostics.instrumentation.engine.base.PointcutLexer;
import weblogic.diagnostics.instrumentation.engine.base.PointcutParser;
import weblogic.diagnostics.instrumentation.engine.i18n.DiagnosticsInstrumentorLogger;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsAction;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsActionGroup;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsActionGroups;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsActions;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsDyeFlag;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsDyeFlags;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsEngineDocument;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsEntryClass;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsEntryClasses;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsInstrumentationSupport;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsMonitor;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsMonitors;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsPackage;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsPackages;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsRenderer;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsValueRenderers;

public final class InstrumentationEngineConfiguration implements Serializable, InstrumentationEngineConstants {
   static final long serialVersionUID = -227604004646430710L;
   private static InstrumentationEngineConfiguration singleton;
   private transient WlsEngineDocument.WlsEngine engineElement;
   private transient boolean isCachedConfig;
   private boolean valid;
   private String name;
   private String parentName;
   private transient List childList = new ArrayList();
   private transient InstrumentationEngineConfiguration parentEngineConfig;
   private String instrumentationSupportClassName = "weblogic/diagnostics/instrumentation/InstrumentationSupport";
   private List actionClassNamesList = new ArrayList();
   private Map actionsMap = new HashMap();
   private Map actionGroupsMap = new HashMap();
   private Map pointcutMap = new HashMap();
   private Map monitorMap = new HashMap();
   private Map serverManagedMonitorMap = new HashMap();
   private Map valueRenderersByType = new HashMap();
   private Map valueRenderersByName = new HashMap();
   private Set urlSet = new HashSet();
   private PackagePattern[] globalPackagePatterns;
   private List entryClasses = new ArrayList();
   private Map dyeFlagsMap = new HashMap();
   private transient boolean unkeptPointcutsRemoved = false;

   public static InstrumentationEngineConfiguration getInstrumentationEngineConfiguration() {
      return getInstrumentationEngineConfiguration((String)null);
   }

   public static synchronized InstrumentationEngineConfiguration getInstrumentationEngineConfiguration(String serializedEngineConfigFile) {
      return getInstrumentationEngineConfiguration(serializedEngineConfigFile, true);
   }

   public static synchronized InstrumentationEngineConfiguration getInstrumentationEngineConfiguration(String serializedEngineConfigFile, boolean doSave) {
      ClassLoader loader = InstrumentationEngineConfiguration.class.getClassLoader();
      return getInstrumentationEngineConfiguration(serializedEngineConfigFile, loader, doSave);
   }

   public static synchronized InstrumentationEngineConfiguration getInstrumentationEngineConfiguration(String serializedEngineConfigFile, ClassLoader loader) {
      return getInstrumentationEngineConfiguration(serializedEngineConfigFile, loader, true);
   }

   public static synchronized InstrumentationEngineConfiguration getInstrumentationEngineConfiguration(String serializedEngineConfigFile, ClassLoader loader, boolean doSave) {
      if (singleton == null) {
         long t0 = System.currentTimeMillis();
         Set uris = null;

         try {
            uris = getConfigUris(loader);
         } catch (Exception var8) {
         }

         if (uris == null || uris.size() == 0) {
            singleton = createInvalidConfiguration();
            return singleton;
         }

         singleton = readConfig(serializedEngineConfigFile, uris);
         if (singleton == null) {
            singleton = readConfig(uris);
            if (serializedEngineConfigFile != null && singleton.isValid()) {
               singleton.urlSet = uris;
               if (doSave) {
                  saveEngineConfiguration(singleton, serializedEngineConfigFile);
               }
            }
         }

         if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
            long t1 = System.currentTimeMillis();
            InstrumentationDebug.DEBUG_CONFIG.debug("Loaded INST engine config in " + (t1 - t0) + " ms");
         }
      }

      return singleton;
   }

   private static InstrumentationEngineConfiguration createInvalidConfiguration() {
      return new InstrumentationEngineConfiguration();
   }

   private static Set getConfigUris(ClassLoader loader) throws IOException, ClassNotFoundException, URISyntaxException {
      HashSet set = new HashSet();
      URL serializedConfig = loader.getResource("weblogic/diagnostics/instrumentation/engine/InstrumentationEngineConfig.ser");
      if (serializedConfig != null) {
         set.add(serializedConfig.toURI());
      }

      Enumeration en = loader.getResources("weblogic/diagnostics/instrumentation/engine/InstrumentationEngineConfig.xml");

      while(en.hasMoreElements()) {
         URL u = (URL)en.nextElement();
         set.add(u.toURI());
         if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_CONFIG.debug("Found resource: " + u);
         }
      }

      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("Found " + set.size() + " " + "weblogic/diagnostics/instrumentation/engine/InstrumentationEngineConfig.xml");
      }

      return set;
   }

   public static void saveEngineConfiguration(InstrumentationEngineConfiguration conf, String serializedEngineConfigFile) {
      if (!conf.isCachedConfig) {
         FileOutputStream fos = null;
         ObjectOutputStream oos = null;

         try {
            File f = new File(serializedEngineConfigFile);
            File dir = f.getParentFile();
            boolean dirExists = dir.exists();
            if (!dirExists) {
               dirExists = dir.mkdirs();
            }

            if (dirExists) {
               fos = new FileOutputStream(f);
               if (!conf.getUnkeptPointcutsRemoved()) {
                  if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                     InstrumentationDebug.DEBUG_CONFIG.debug("Saving cached (Serialize): " + f);
                  }

                  oos = new ObjectOutputStream(fos);
                  oos.writeObject(conf);
                  oos.flush();
               } else if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_CONFIG.debug("Unkept pointcut expressions have been removed will not serialize the configuration");
               }
            }
         } catch (IOException var17) {
            DiagnosticsInstrumentorLogger.logEngineConfigSaveError(serializedEngineConfigFile, var17);
         } finally {
            if (oos != null) {
               try {
                  oos.close();
               } catch (IOException var19) {
                  if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                     InstrumentationDebug.DEBUG_CONFIG.debug("Unexpected exception", var19);
                  }
               }
            }

            if (fos != null) {
               try {
                  fos.close();
               } catch (IOException var18) {
                  if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                     InstrumentationDebug.DEBUG_CONFIG.debug("Unexpected exception", var18);
                  }
               }
            }

         }

      }
   }

   public String getName() {
      return this.name;
   }

   public String getParentName() {
      return this.parentName;
   }

   Map getPointcuts() {
      return this.pointcutMap;
   }

   Map getMonitors() {
      return this.monitorMap;
   }

   public String[] getGroupActionTypes(String actionGroupName) {
      String[] src = (String[])((String[])this.actionGroupsMap.get(actionGroupName));
      int size = src != null ? src.length : 0;
      String[] dst = new String[size];
      if (size > 0) {
         System.arraycopy(src, 0, dst, 0, size);
      }

      return dst;
   }

   public String[] getEntryClasses() {
      String[] arr = new String[this.entryClasses.size()];
      arr = (String[])((String[])this.entryClasses.toArray(arr));
      return arr;
   }

   public Map getDyeFlagsMap() {
      return this.dyeFlagsMap;
   }

   private InstrumentationEngineConfiguration() {
   }

   private InstrumentationEngineConfiguration(InputStream in) {
      try {
         this.parse(in);
         this.validate();
      } catch (XmlException var3) {
         DiagnosticsLogger.logInstrumentationConfigParseError("weblogic/diagnostics/instrumentation/engine/InstrumentationEngineConfig.xml", var3.getMessage());
      } catch (IOException var4) {
         DiagnosticsLogger.logInstrumentationConfigReadError("weblogic/diagnostics/instrumentation/engine/InstrumentationEngineConfig.xml");
      } catch (InstrumentationException var5) {
         DiagnosticsLogger.logEngineConfigurationFileError("weblogic/diagnostics/instrumentation/engine/InstrumentationEngineConfig.xml", var5.getMessage());
      }

   }

   private void parse(InputStream in) throws InstrumentationException, IOException, XmlException {
      WlsEngineDocument doc = WlsEngineDocument.Factory.parse(in);
      this.engineElement = doc.getWlsEngine();
      this.name = this.engineElement.getName();
      this.parentName = this.engineElement.getParent();
      if (this.name == null) {
         this.name = "";
      }

      if (this.parentName == null) {
         this.parentName = "";
      }

      this.identifyInstrumentationSupport();
      this.identifyEntryClasses();
      this.identifyDyeFlags();
      this.identifyGlobalIncludeExcludePackages();
      this.identifyActions();
      this.identifyActionGroups();
      this.identifyValueRenderers();
      this.identifyPointcuts();
      this.identifyMonitors();
   }

   private void identifyInstrumentationSupport() {
      WlsInstrumentationSupport instSupport = this.engineElement.getWlsInstrumentationSupport();
      if (instSupport != null) {
         this.instrumentationSupportClassName = instSupport.getClassName();
         if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_CONFIG.debug("instrumentationSupportClassName=" + this.instrumentationSupportClassName);
         }
      }

      this.instrumentationSupportClassName = this.instrumentationSupportClassName.replace('.', '/');
   }

   private void identifyEntryClasses() {
      WlsEntryClasses entClasses = this.engineElement.getWlsEntryClasses();
      if (entClasses != null) {
         WlsEntryClass[] entClassArr = entClasses.getWlsEntryClassArray();
         int size = entClassArr != null ? entClassArr.length : 0;

         for(int i = 0; i < size; ++i) {
            this.entryClasses.add(entClassArr[i].getClassname());
         }
      }

   }

   private void identifyDyeFlags() {
      WlsDyeFlags dyeFlags = this.engineElement.getWlsDyeFlags();
      if (dyeFlags != null) {
         WlsDyeFlag[] flags = dyeFlags.getWlsDyeFlagArray();
         int size = flags != null ? flags.length : 0;

         for(int i = 0; i < size; ++i) {
            this.addDyeFlag(flags[i].getName(), flags[i].getIndex());
         }
      }

   }

   private void addDyeFlag(String dyeName, Integer dyeIndex) {
      Iterator it = this.dyeFlagsMap.keySet().iterator();

      Object key;
      Object val;
      do {
         if (!it.hasNext()) {
            this.dyeFlagsMap.put(dyeName, dyeIndex);
            return;
         }

         key = it.next();
         val = this.dyeFlagsMap.get(key);
      } while(!dyeName.equals(key) && !dyeIndex.equals(val));

      DiagnosticsLogger.logDyeRegistrationFailureError(dyeName, dyeIndex);
   }

   private void identifyGlobalIncludeExcludePackages() {
      WlsPackages packages = this.engineElement.getWlsPackages();
      WlsPackage[] packagePatterns = packages != null ? packages.getWlsPackageArray() : null;
      int size = packagePatterns != null ? packagePatterns.length : 0;
      List list = new ArrayList();

      for(int i = 0; i < size; ++i) {
         WlsPackage p = packagePatterns[i];
         String patStr = p.getPackage();
         if (patStr != null) {
            PackagePattern packagePattern = new PackagePattern();
            packagePattern.pattern = patStr;
            String type = p.getType();
            if ("include".equals(type)) {
               packagePattern.include = true;
               list.add(packagePattern);
            } else if ("exclude".equals(type)) {
               packagePattern.include = false;
               list.add(packagePattern);
            }
         }
      }

      if (list.size() > 0) {
         PackagePattern[] arr = new PackagePattern[list.size()];
         this.globalPackagePatterns = (PackagePattern[])((PackagePattern[])list.toArray(arr));
      }

   }

   public boolean isEligibleClass(String className) {
      int size = this.globalPackagePatterns != null ? this.globalPackagePatterns.length : 0;

      for(int i = 0; i < size; ++i) {
         if (className.startsWith(this.globalPackagePatterns[i].pattern)) {
            return this.globalPackagePatterns[i].include;
         }
      }

      return true;
   }

   private void identifyActions() {
      WlsActions actionsElement = this.engineElement.getWlsActions();
      WlsAction[] actions = actionsElement != null ? actionsElement.getWlsActionArray() : null;
      int len = actions != null ? actions.length : 0;

      for(int i = 0; i < len; ++i) {
         String className = actions[i].getClassName();
         String actionType = actions[i].getType();
         this.actionsMap.put(actionType, className);
         this.actionClassNamesList.add(className);
      }

   }

   private void identifyActionGroups() {
      WlsActionGroups actionGroupsElement = this.engineElement.getWlsActionGroups();
      WlsActionGroup[] groups = actionGroupsElement != null ? actionGroupsElement.getWlsActionGroupArray() : null;
      int len = groups != null ? groups.length : 0;

      for(int i = 0; i < len; ++i) {
         WlsActionGroup grp = groups[i];
         String groupName = grp.getName();
         String actionTypes = grp.getActionTypes();
         if (actionTypes != null) {
            String groupActions = null;
            String[] aTypes = actionTypes.split(",");

            for(int j = 0; j < aTypes.length; ++j) {
               String actionType = aTypes[j].trim();
               if (this.actionsMap.get(actionType) != null) {
                  if (groupActions == null) {
                     groupActions = actionType;
                  } else {
                     groupActions = groupActions + "," + actionType;
                  }
               } else if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_CONFIG.debug("Unknown action type " + actionType + " in group " + groupName);
               }
            }

            if (groupActions != null) {
               this.actionGroupsMap.put(groupName, groupActions.split(","));
            }
         }
      }

   }

   public String[] getActionClassNames() {
      String[] classNames = new String[this.actionClassNamesList.size()];
      classNames = (String[])((String[])this.actionClassNamesList.toArray(classNames));
      return classNames;
   }

   public String[] getActionTypes() {
      String[] actionTypes = new String[this.actionsMap.size()];
      actionTypes = (String[])((String[])this.actionsMap.keySet().toArray(actionTypes));
      return actionTypes;
   }

   public String getActionClassName(String actionType) {
      return (String)this.actionsMap.get(actionType);
   }

   private void identifyPointcuts() throws InvalidPointcutException {
      String pointcutsSpecs = this.engineElement.getWlsPointcuts();
      ByteArrayInputStream in = null;

      try {
         in = new ByteArrayInputStream(pointcutsSpecs.getBytes());
         PointcutLexer lexer = new PointcutLexer(in);
         PointcutParser parser = new PointcutParser(lexer);
         parser.setValueRendererByNameMap(this.valueRenderersByName);
         parser.parse();
         this.pointcutMap = parser.getPointcuts();
      } catch (Exception var12) {
         throw new InvalidPointcutException(var12);
      } finally {
         if (in != null) {
            try {
               in.close();
            } catch (IOException var11) {
            }
         }

      }

   }

   private void identifyMonitors() throws InstrumentationException {
      WlsMonitors monitors = this.engineElement.getWlsMonitors();
      if (monitors == null) {
         throw new MonitorNotFoundException("No monitors configured");
      } else {
         WlsMonitor[] monitorSpecs = monitors.getWlsMonitorArray();
         int monCount = monitorSpecs != null ? monitorSpecs.length : 0;
         if (monCount == 0) {
            throw new MonitorNotFoundException("No monitors configured");
         } else {
            for(int i = 0; i < monCount; ++i) {
               MonitorSpecification mSpec = new MonitorSpecification(this, monitorSpecs[i]);
               String mType = mSpec.getType();
               if (mSpec.isApplicationScoped()) {
                  if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                     InstrumentationDebug.DEBUG_CONFIG.debug("Marking pointcut to be kept in memory for : " + mSpec.getType());
                  }

                  mSpec.markPointcutExpressionAsKeep();
               }

               if (this.monitorMap.get(mType) != null) {
                  throw new DuplicateMonitorException("Duplicate definition of monitor type " + mType);
               }

               this.monitorMap.put(mType, mSpec);
               if (mSpec.isServerManaged()) {
                  this.serverManagedMonitorMap.put(mType, mSpec);
               }
            }

         }
      }
   }

   private void identifyValueRenderers() throws InstrumentationException {
      WlsValueRenderers renderers = this.engineElement.getWlsValueRenderers();
      if (renderers == null) {
         if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_CONFIG.debug("no value renderers configured");
         }

      } else {
         WlsRenderer[] rendererSpecs = renderers.getWlsRendererArray();
         int count = rendererSpecs != null ? rendererSpecs.length : 0;
         if (count == 0) {
            if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_CONFIG.debug("no value renderers configured");
            }

         } else {
            if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_CONFIG.debug("ValueRenderer count: " + count);
            }

            for(int i = 0; i < count; ++i) {
               String rendererName = rendererSpecs[i].getName();
               if (this.valueRenderersByName.get(rendererName) != null) {
                  throw new InstrumentationException("Duplicate definition of value renderer with name: " + rendererName);
               }

               String className = rendererSpecs[i].getClassName();
               if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_CONFIG.debug("ValueRenderer added to valueRenderersByName (" + rendererName + ", " + className + ")");
               }

               this.valueRenderersByName.put(rendererName, className);
               if (rendererSpecs[i].isSetTypeClassName()) {
                  String typeClassName = rendererSpecs[i].getTypeClassName();
                  if (this.valueRenderersByType.get(typeClassName) != null) {
                     throw new InstrumentationException("Type specified by type based value renderer already handled, name: " + rendererName);
                  }

                  if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                     InstrumentationDebug.DEBUG_CONFIG.debug("ValueRenderer added to valueRenderersByType (" + typeClassName + ", " + className + ")");
                  }

                  this.valueRenderersByType.put(typeClassName, className);
               }
            }

         }
      }
   }

   public Map getValueRenderersByType() {
      return this.valueRenderersByType;
   }

   public Map getValueRenderersByName() {
      return this.valueRenderersByName;
   }

   private void validate() {
      this.valid = true;
   }

   public boolean isValid() {
      return this.valid;
   }

   public MonitorSpecification getMonitorSpecification(String type) {
      MonitorSpecification mSpec = (MonitorSpecification)this.monitorMap.get(type);
      return mSpec;
   }

   public Iterator getAllMonitorSpecifications() {
      return this.monitorMap.values().iterator();
   }

   public String getInstrumentationSupportClassName() {
      return this.instrumentationSupportClassName;
   }

   private static InstrumentationEngineConfiguration readConfig(String serializedEngineConfigFile, Set uris) {
      if (serializedEngineConfigFile == null) {
         return null;
      } else {
         File serFile = new File(serializedEngineConfigFile);
         if (!serFile.exists()) {
            return null;
         } else {
            long lastModified = serFile.lastModified();
            InputStream in = null;
            ObjectInputStream ois = null;
            InstrumentationEngineConfiguration conf = null;

            try {
               Iterator var8 = uris.iterator();

               while(var8.hasNext()) {
                  URI uri = (URI)var8.next();
                  URL url = uri.toURL();
                  String path = url.getPath();
                  String proto = url.getProtocol();
                  if (proto.equals("jar")) {
                     int ind = path.indexOf(58);
                     path = path.substring(ind + 1, path.length());
                     ind = path.indexOf(33);
                     path = path.substring(0, ind);
                  }

                  File urlFile = new File(path);
                  if (urlFile.lastModified() > lastModified) {
                     if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                        InstrumentationDebug.DEBUG_CONFIG.debug("Found newer config at " + url.toString());
                     }

                     Object var14 = null;
                     return (InstrumentationEngineConfiguration)var14;
                  }
               }

               in = new FileInputStream(serializedEngineConfigFile);
               if (in == null) {
                  return conf;
               } else {
                  ois = new ObjectInputStream(in);
                  conf = (InstrumentationEngineConfiguration)ois.readObject();
                  conf.isCachedConfig = true;
                  if (uris.equals(conf.urlSet)) {
                     return conf;
                  } else {
                     if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                        InstrumentationDebug.DEBUG_CONFIG.debug("Urls mismatch with previous set: " + conf.urlSet);
                     }

                     var8 = null;
                     return var8;
                  }
               }
            } catch (Exception var32) {
               if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_CONFIG.debug("Could not read serialized instrumentation engine config", var32);
               }

               return conf;
            } finally {
               if (ois != null) {
                  try {
                     ois.close();
                  } catch (Exception var31) {
                     if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                        InstrumentationDebug.DEBUG_CONFIG.debug("Unexpected exception", var31);
                     }
                  }
               }

               if (in != null) {
                  try {
                     in.close();
                  } catch (Exception var30) {
                     if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                        InstrumentationDebug.DEBUG_CONFIG.debug("Unexpected exception", var30);
                     }
                  }
               }

            }
         }
      }
   }

   private static InstrumentationEngineConfiguration readConfig(ClassLoader loader) throws IOException, ClassNotFoundException, URISyntaxException {
      Set uris = getConfigUris(loader);
      return readConfig(uris);
   }

   private static InstrumentationEngineConfiguration readConfig(Set uris) {
      Map configs = null;

      try {
         configs = readConfigurations(uris);
      } catch (IOException var5) {
         DiagnosticsInstrumentorLogger.logEngineConfigReadError(var5);
      } catch (ClassNotFoundException var6) {
         DiagnosticsInstrumentorLogger.logEngineConfigReadError(var6);
      }

      if (configs == null) {
         return createInvalidConfiguration();
      } else {
         InstrumentationEngineConfiguration root = (InstrumentationEngineConfiguration)configs.get("Builtin");
         if (configs.size() > 1) {
            mergeActionAndGroups(root, root);
            mergeMonitorSpecifications(root, root);
            Iterator it = configs.values().iterator();

            while(it.hasNext()) {
               InstrumentationEngineConfiguration conf = (InstrumentationEngineConfiguration)it.next();
               if (conf != root) {
                  mergeConfig(root, conf);
               }
            }
         }

         return root;
      }
   }

   private static void mergeConfig(InstrumentationEngineConfiguration root, InstrumentationEngineConfiguration node) {
      root.entryClasses.addAll(node.entryClasses);
      Map nodeDyeFlagsMap = node.getDyeFlagsMap();
      Set entries = nodeDyeFlagsMap.entrySet();
      Iterator var4 = entries.iterator();

      while(var4.hasNext()) {
         Map.Entry e = (Map.Entry)var4.next();
         String dyeName = (String)e.getKey();
         Integer dyeIndex = (Integer)e.getValue();
         root.addDyeFlag(dyeName, dyeIndex);
      }

   }

   private static void mergeActionAndGroups(InstrumentationEngineConfiguration root, InstrumentationEngineConfiguration node) {
      if (node.childList != null) {
         Iterator it = node.childList.iterator();

         while(it.hasNext()) {
            InstrumentationEngineConfiguration child = (InstrumentationEngineConfiguration)it.next();
            mergeActionAndGroups(root, child);
         }
      }

      if (node != root) {
         String prefix = node.getName() + "/";
         Iterator it = node.actionsMap.keySet().iterator();

         String typeName;
         String value;
         while(it.hasNext()) {
            typeName = (String)it.next();
            value = (String)node.actionsMap.get(typeName);
            typeName = prefix + typeName;
            root.actionsMap.put(typeName, value);
            root.actionClassNamesList.add(value);
         }

         String[] actionNames;
         for(it = node.actionGroupsMap.keySet().iterator(); it.hasNext(); root.actionGroupsMap.put(prefix + typeName, actionNames)) {
            typeName = (String)it.next();
            actionNames = (String[])((String[])node.actionGroupsMap.get(typeName));
            int size = actionNames != null ? actionNames.length : 0;

            for(int i = 0; i < size; ++i) {
               String actionName = actionNames[i];
               if (actionName.indexOf("/") < 0) {
                  actionNames[i] = prefix + actionNames[i];
               }
            }

            InstrumentationEngineConfiguration mergeTo = node.findEngineConfigurationWithActionGroup(typeName);
            if (mergeTo != null && mergeTo != node) {
               String[] mergedActionNames = (String[])((String[])mergeTo.actionGroupsMap.get(typeName));
               int oldSize = mergedActionNames != null ? mergedActionNames.length : 0;
               String[] tmp = new String[size + oldSize];
               System.arraycopy(mergedActionNames, 0, tmp, 0, oldSize);
               System.arraycopy(actionNames, 0, tmp, oldSize, size);
               mergeTo.actionGroupsMap.put(typeName, tmp);
            }
         }

         it = node.valueRenderersByName.keySet().iterator();

         while(it.hasNext()) {
            typeName = (String)it.next();
            value = (String)node.valueRenderersByName.get(typeName);
            root.valueRenderersByName.put(prefix + typeName, value);
         }

         it = node.valueRenderersByType.keySet().iterator();

         while(it.hasNext()) {
            typeName = (String)it.next();
            if (root.valueRenderersByType.get(typeName) == null) {
               value = (String)node.valueRenderersByType.get(typeName);
               root.valueRenderersByType.put(typeName, value);
            } else if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_CONFIG.debug("Type handled by root configuration, child configuration type based value renderer ignored for: " + typeName);
            }
         }

         it = node.pointcutMap.keySet().iterator();

         while(it.hasNext()) {
            typeName = (String)it.next();
            Object value = node.pointcutMap.get(typeName);
            root.pointcutMap.put(prefix + typeName, value);
         }
      }

   }

   private static void mergeMonitorSpecifications(InstrumentationEngineConfiguration root, InstrumentationEngineConfiguration node) {
      if (node.childList != null) {
         Iterator it = node.childList.iterator();

         while(it.hasNext()) {
            InstrumentationEngineConfiguration child = (InstrumentationEngineConfiguration)it.next();
            mergeMonitorSpecifications(root, child);
         }
      }

      String prefix = node.getName() + "/";

      MonitorSpecification monSpec;
      String mType;
      for(Iterator it = node.getAllMonitorSpecifications(); it.hasNext(); root.monitorMap.put(mType, monSpec)) {
         monSpec = (MonitorSpecification)it.next();
         mType = monSpec.getType();
         String groupName = monSpec.getActionGroupName();
         InstrumentationEngineConfiguration mergeTo = root;
         if (node != root) {
            mType = prefix + monSpec.getType();
            monSpec.setType(mType);
            mergeTo = node.findEngineConfigurationWithActionGroup(groupName);
         }

         if (node != root || mType.indexOf("/") < 0) {
            String[] actionTypeNames = null;
            if (mergeTo != null && groupName != null) {
               actionTypeNames = (String[])((String[])mergeTo.actionGroupsMap.get(groupName));
            }

            if (actionTypeNames == null) {
               actionTypeNames = new String[0];
            }

            monSpec.setActionTypes(actionTypeNames);
         }
      }

   }

   InstrumentationEngineConfiguration findEngineConfigurationWithActionGroup(String groupName) {
      if (this.parentEngineConfig != null) {
         InstrumentationEngineConfiguration ancestor = this.parentEngineConfig.findEngineConfigurationWithActionGroup(groupName);
         if (ancestor != null) {
            return ancestor;
         }
      }

      return this.actionGroupsMap.get(groupName) != null ? this : null;
   }

   private static Map readConfigurations(ClassLoader loader) throws IOException, ClassNotFoundException, URISyntaxException {
      Set uris = getConfigUris(loader);
      return readConfigurations(uris);
   }

   private static Map readConfigurations(Set uris) throws IOException, ClassNotFoundException {
      Map configs = new HashMap();
      Map rawDataMap = new HashMap();
      boolean status = true;
      Iterator it = uris.iterator();

      while(it.hasNext()) {
         URI uri = (URI)it.next();
         URL url = uri.toURL();
         InputStream in = null;
         ObjectInputStream ois = null;
         InstrumentationEngineConfiguration conf = null;

         try {
            byte[] dataBuf = readResource(url);
            in = new ByteArrayInputStream(dataBuf);
            String urlPath = url.getPath();
            if (urlPath.endsWith("InstrumentationEngineConfig.ser")) {
               ois = new ObjectInputStream(in);
               conf = (InstrumentationEngineConfiguration)ois.readObject();
            } else {
               conf = new InstrumentationEngineConfiguration(in);
            }

            String name = conf.getName();
            String parentName = conf.getParentName();
            if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_CONFIG.debug("Found InstrumentationEngineConfiguration: name=" + name + " parentName=" + parentName);
            }

            boolean isOk = true;
            if (name.equals("")) {
               DiagnosticsInstrumentorLogger.logMissingEngineConfigNameError(url.toString());
               isOk = false;
            }

            if (configs.get(name) != null && !compareRawData(name, rawDataMap, dataBuf)) {
               DiagnosticsInstrumentorLogger.logDuplicateEngineConfigNameError(name);
               isOk = false;
            }

            if (name.equals("Builtin") && !parentName.equals("")) {
               DiagnosticsInstrumentorLogger.logInvalidParentConfigError(name, parentName);
               isOk = false;
            }

            if (isOk) {
               configs.put(name, conf);
               rawDataMap.put(name, dataBuf);
            } else if (status) {
               status = false;
            }
         } finally {
            if (ois != null) {
               try {
                  ois.close();
               } catch (Exception var23) {
                  if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                     InstrumentationDebug.DEBUG_CONFIG.debug("Unexpected exception", var23);
                  }
               }
            }

            if (in != null) {
               try {
                  in.close();
               } catch (Exception var22) {
                  if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                     InstrumentationDebug.DEBUG_CONFIG.debug("Unexpected exception", var22);
                  }
               }
            }

         }
      }

      if (configs.get("Builtin") == null) {
         DiagnosticsInstrumentorLogger.logMissingRootConfigError();
         status = false;
      }

      it = configs.values().iterator();

      while(it.hasNext()) {
         InstrumentationEngineConfiguration conf = (InstrumentationEngineConfiguration)it.next();
         if (!checkParents(configs, conf)) {
            status = false;
         }
      }

      return status ? configs : null;
   }

   private static boolean compareRawData(String name, Map rawDataMap, byte[] newBuf) {
      byte[] oldBuf = (byte[])((byte[])rawDataMap.get(name));
      int newSize = newBuf != null ? newBuf.length : 0;
      int oldSize = oldBuf != null ? oldBuf.length : 0;
      if (newSize != oldSize) {
         return false;
      } else {
         for(int i = 0; i < newSize; ++i) {
            if (newBuf[i] != oldBuf[i]) {
               return false;
            }
         }

         if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_CONFIG.debug("Found identical instrumentation engine configurations for '" + name + "' loaded from distinct urls");
         }

         return true;
      }
   }

   private static boolean checkParents(Map configs, InstrumentationEngineConfiguration conf) {
      String name = conf.getName();
      String parentName = conf.getParentName();
      if (name.equals("Builtin")) {
         return true;
      } else {
         InstrumentationEngineConfiguration parent = (InstrumentationEngineConfiguration)configs.get(parentName);
         if (parent != null) {
            if (parent.childList == null) {
               parent.childList = new ArrayList();
            }

            parent.childList.add(conf);
         }

         conf.parentEngineConfig = parent;
         StringBuffer buf = new StringBuffer();
         buf.append(name);

         while(!parentName.equals("")) {
            buf.append(" -> ");
            buf.append(parentName);
            if (parentName.equals(name)) {
               DiagnosticsInstrumentorLogger.logCircularDepenencyConfigError(buf.toString());
               return false;
            }

            InstrumentationEngineConfiguration parentConf = (InstrumentationEngineConfiguration)configs.get(parentName);
            if (parentConf == null) {
               DiagnosticsInstrumentorLogger.logMissingParentConfigError(parentName, buf.toString());
               return false;
            }

            parentName = parentConf.getParentName();
         }

         return true;
      }
   }

   private static byte[] readResource(URL url) throws IOException {
      InputStream in = null;
      ByteArrayOutputStream bos = null;
      byte[] buf = new byte[8092];

      try {
         in = url.openStream();
         bos = new ByteArrayOutputStream();
         int nread = false;

         int nread;
         while((nread = in.read(buf)) > 0) {
            bos.write(buf, 0, nread);
         }

         bos.flush();
         byte[] var5 = bos.toByteArray();
         return var5;
      } finally {
         if (in != null) {
            in.close();
         }

         if (bos != null) {
            bos.close();
         }

      }
   }

   public static void main(String[] args) {
      if (args.length < 2) {
         System.out.println("Usage: java " + InstrumentationEngineConfiguration.class.getName() + " engine-config-file serialized-engine-config-file");
      } else {
         InputStream in = null;
         ObjectOutputStream oos = null;
         Set inputUris = new HashSet();

         for(int i = 0; i < args.length - 1; ++i) {
            File inFile = new File(args[i]);
            if (inFile.exists()) {
               inputUris.add(inFile.toURI());
            } else {
               System.out.println("Input file " + inFile + " does not exist");
            }
         }

         if (inputUris.size() < 1) {
            System.out.println("Failed to find input file(s)");
         } else {
            try {
               InstrumentationEngineConfiguration conf = readConfig((Set)inputUris);
               if (conf.isValid()) {
                  String outFile = args[args.length - 1];
                  oos = new ObjectOutputStream(new FileOutputStream(outFile));
                  oos.writeObject(conf);
                  oos.flush();
               }
            } catch (Exception var14) {
               var14.printStackTrace();
            } finally {
               if (oos != null) {
                  try {
                     oos.close();
                  } catch (Exception var13) {
                  }
               }

            }

         }
      }
   }

   public synchronized boolean getUnkeptPointcutsRemoved() {
      return this.unkeptPointcutsRemoved;
   }

   public synchronized void removeUnkeptPointcuts() {
      if (!this.unkeptPointcutsRemoved) {
         this.unkeptPointcutsRemoved = true;
         Iterator keys = this.monitorMap.values().iterator();

         Object key;
         while(keys.hasNext()) {
            key = keys.next();
            MonitorSpecification monSpec = (MonitorSpecification)key;
            monSpec.removeUnkeptPointcut();
         }

         keys = this.pointcutMap.keySet().iterator();

         while(keys.hasNext()) {
            key = keys.next();
            PointcutExpression pcExpr = (PointcutExpression)this.pointcutMap.get(key);
            if (pcExpr.getKeepHint()) {
               if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_CONFIG.debug("Keeping pointcut in memory: " + key);
               }
            } else {
               if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_CONFIG.debug("Removing unkept pointcut from memory: " + key);
               }

               keys.remove();
            }
         }

      }
   }

   private static class PackagePattern implements Serializable {
      static final long serialVersionUID = 1L;
      boolean include;
      String pattern;

      private PackagePattern() {
      }

      // $FF: synthetic method
      PackagePattern(Object x0) {
         this();
      }
   }
}
