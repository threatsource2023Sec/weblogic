package weblogic.diagnostics.instrumentation;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.diagnostics.context.CorrelationHelper;
import weblogic.diagnostics.context.InvalidDyeException;
import weblogic.diagnostics.flightrecorder.FlightRecorderManager.Factory;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.engine.InstrumentationEngineConfiguration;
import weblogic.diagnostics.instrumentation.engine.MonitorSpecification;
import weblogic.diagnostics.instrumentation.engine.base.InstrumentationEngineConstants;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;
import weblogic.kernel.Kernel;
import weblogic.management.DomainDir;
import weblogic.management.provider.RuntimeAccess;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.PropertyHelper;
import weblogic.utils.jars.ManifestManager;

public final class InstrumentationLibrary implements InstrumentationEngineConstants {
   private static boolean DISABLE_REMOVE_SERVER_POINTCUTS = PropertyHelper.getBoolean("weblogic.diagnostics.instrumentation.DisableRemovalOfServerPointcuts");
   private static InstrumentationLibrary singleton;
   private HashMap monitorMap = new HashMap();
   private HashMap serverManagedMonitorMap = new HashMap();
   private HashMap actionMap = new HashMap();
   private ClassLoader actionsClassLoader;
   private List statelessActionTypes = new ArrayList();
   private List aroundActionTypes = new ArrayList();
   private List eventClassNamesInUse = new ArrayList();
   private InstrumentationEngineConfiguration engineConf;
   private boolean valid;

   private InstrumentationLibrary() {
   }

   public static synchronized InstrumentationLibrary getInstrumentationLibrary() {
      if (singleton == null) {
         InstrumentationLibrary tmp = new InstrumentationLibrary();
         tmp.loadInstrumentationEngineConfiguration();
         singleton = tmp;
      }

      return singleton;
   }

   public boolean isValid() {
      return this.valid;
   }

   public InstrumentationEngineConfiguration getInstrumentationEngineConfiguration() {
      return this.engineConf;
   }

   private void loadInstrumentationEngineConfiguration() {
      this.actionsClassLoader = Thread.currentThread().getContextClassLoader();
      if (this.actionsClassLoader == null) {
         this.actionsClassLoader = this.getClass().getClassLoader();
      }

      String serializedEngineConfigFilePath = null;
      if (Kernel.isServer()) {
         String serverName = null;
         RuntimeAccess rtAccess = (RuntimeAccess)GlobalServiceLocator.getServiceLocator().getService(RuntimeAccess.class, new Annotation[0]);
         if (rtAccess != null) {
            serverName = rtAccess.getServerName();
         }

         if (serverName == null || serverName.equals("")) {
            serverName = PropertyHelper.getProperty("weblogic.Name", "myserver");
         }

         if ("".equals(serverName)) {
            serverName = null;
         }

         if (serverName != null) {
            File f = new File(DomainDir.getPathRelativeServersCacheDir(serverName, "diagnostics"));
            f = new File(f, "InstrumentationEngineConfig.ser");
            serializedEngineConfigFilePath = f.getAbsolutePath();
         }
      }

      this.engineConf = InstrumentationEngineConfiguration.getInstrumentationEngineConfiguration(serializedEngineConfigFilePath, false);
      if (this.engineConf.isValid()) {
         Map dyeFlagsMap = this.engineConf.getDyeFlagsMap();
         Iterator it = dyeFlagsMap.keySet().iterator();

         while(it.hasNext()) {
            String dyeName = (String)it.next();
            int dyeIndex = (Integer)dyeFlagsMap.get(dyeName);

            try {
               CorrelationHelper.registerDye(dyeName, dyeIndex);
            } catch (InvalidDyeException var18) {
               if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_CONFIG.debug("Failed to register dye: ", var18);
               }

               DiagnosticsLogger.logDyeRegistrationFailureError(dyeName, dyeIndex);
            }
         }

         String[] actionTypes = this.engineConf.getActionTypes();
         String[] statelessActionNames = this.engineConf.getGroupActionTypes("StatelessActions");
         String[] aroundActionNames = this.engineConf.getGroupActionTypes("AroundActions");
         int size = actionTypes != null ? actionTypes.length : 0;

         for(int i = 0; i < size; ++i) {
            String actionType = actionTypes[i];
            String actionClassName = this.engineConf.getActionClassName(actionType);

            try {
               DiagnosticAction action = (DiagnosticAction)Class.forName(actionClassName).newInstance();
               this.actionMap.put(actionType, actionClassName);
               if (this.isInList(actionType, statelessActionNames)) {
                  this.statelessActionTypes.add(actionType);
               } else if (this.isInList(actionType, aroundActionNames)) {
                  this.aroundActionTypes.add(actionType);
               }
            } catch (Exception var17) {
               UnexpectedExceptionHandler.handle("Unknown diagnostic action " + actionClassName, var17);
            }
         }

         String eventPackagePrefix = Factory.getInstance().getInstrumentedEventPackagePrefix();
         Iterator it = this.engineConf.getAllMonitorSpecifications();

         while(it.hasNext()) {
            MonitorSpecification mSpec = (MonitorSpecification)it.next();
            String monType = mSpec.getType();
            DiagnosticMonitorControl monitor = null;
            if (mSpec.isStandardMonitor()) {
               monitor = new StandardMonitorControl(monType);
            } else {
               DelegatingMonitorControl delegatingMonitor = new DelegatingMonitorControl(monType);
               String[] aTypes = mSpec.getActionTypes();
               int actionCnt = aTypes != null ? aTypes.length : 0;
               List actionsList = new ArrayList();

               for(int i = 0; i < actionCnt; ++i) {
                  if (this.actionMap.get(aTypes[i]) != null) {
                     actionsList.add(aTypes[i]);
                  }
               }

               aTypes = (String[])((String[])actionsList.toArray(new String[0]));
               delegatingMonitor.setCompatibleActionTypes(aTypes);
               delegatingMonitor.setLocationType(mSpec.getLocation());
               monitor = delegatingMonitor;
            }

            ((DiagnosticMonitorControl)monitor).setAttributeNames(mSpec.getAttributeNames());
            ((DiagnosticMonitorControl)monitor).setServerScopeAllowed(mSpec.isServerScoped());
            ((DiagnosticMonitorControl)monitor).setComponentScopeAllowed(mSpec.isApplicationScoped());
            ((DiagnosticMonitorControl)monitor).setServerManaged(mSpec.isServerManaged());
            ((DiagnosticMonitorControl)monitor).setDiagnosticVolume(mSpec.getDiagnosticVolume());
            ((DiagnosticMonitorControl)monitor).setEventClassName(mSpec.getEventClassName());
            if (mSpec.isServerManaged()) {
               String eventName = mSpec.getEventClassName();
               if (eventName != null && !this.eventClassNamesInUse.contains(eventName)) {
                  eventName = eventPackagePrefix + eventName;
                  ((DiagnosticMonitorControl)monitor).setEventClassName(eventName);
                  this.eventClassNamesInUse.add(eventName);
               }

               this.serverManagedMonitorMap.put(monType, monitor);
            } else {
               this.monitorMap.put(monType, monitor);
            }
         }

         this.readCustomActions();
         this.mergeDyeDefinitions(dyeFlagsMap);
         this.valid = true;
      }
   }

   private void mergeDyeDefinitions(Map dyeFlagsMap) {
      DiagnosticMonitorControl dyeInjectionMonitor = (DiagnosticMonitorControl)this.monitorMap.get("DyeInjection");
      if (dyeInjectionMonitor != null) {
         if (dyeFlagsMap != null) {
            Set set = new HashSet();
            String[] attrNames = dyeInjectionMonitor.getAttributeNames();
            int size = attrNames != null ? attrNames.length : 0;

            for(int i = 0; i < size; ++i) {
               set.add(attrNames[i]);
            }

            set.addAll(dyeFlagsMap.keySet());
            attrNames = (String[])((String[])set.toArray(new String[0]));
            dyeInjectionMonitor.setAttributeNames(attrNames);
         }
      } else {
         DiagnosticsLogger.logMissingDiagnosticMonitor("DyeInjection");
      }

   }

   private boolean isInList(String name, String[] array) {
      int size = array != null ? array.length : 0;

      for(int i = 0; i < size; ++i) {
         if (name.equals(array[i])) {
            return true;
         }
      }

      return false;
   }

   public DiagnosticMonitorControl[] getAvailableMonitors() {
      int size = this.monitorMap.size();
      DiagnosticMonitorControl[] monitors = new DiagnosticMonitorControl[size];
      int ind = 0;

      String type;
      for(Iterator it = this.monitorMap.keySet().iterator(); it.hasNext(); monitors[ind++] = this.getDiagnosticMonitorControl(type)) {
         type = (String)it.next();
      }

      return monitors;
   }

   public DiagnosticMonitorControl[] getAllAvailableMonitors() {
      int size = this.monitorMap.size() + this.serverManagedMonitorMap.size();
      DiagnosticMonitorControl[] monitors = new DiagnosticMonitorControl[size];
      int ind = 0;

      Iterator it;
      String type;
      for(it = this.monitorMap.keySet().iterator(); it.hasNext(); monitors[ind++] = this.getDiagnosticMonitorControl(type)) {
         type = (String)it.next();
      }

      for(it = this.serverManagedMonitorMap.keySet().iterator(); it.hasNext(); monitors[ind++] = this.getDiagnosticMonitorControl(type)) {
         type = (String)it.next();
      }

      return monitors;
   }

   public DiagnosticMonitorControl getDiagnosticMonitorControl(String type) {
      DiagnosticMonitorControl ele = (DiagnosticMonitorControl)this.monitorMap.get(type);
      if (ele == null) {
         ele = (DiagnosticMonitorControl)this.serverManagedMonitorMap.get(type);
         if (ele == null) {
            return null;
         }
      }

      DiagnosticMonitorControl retVal = null;
      if (ele instanceof CustomMonitorControl) {
         retVal = new CustomMonitorControl((CustomMonitorControl)ele);
      } else if (ele instanceof DelegatingMonitorControl) {
         retVal = new DelegatingMonitorControl((DelegatingMonitorControl)ele);
      } else if (ele instanceof StandardMonitorControl) {
         retVal = new StandardMonitorControl((StandardMonitorControl)ele);
      }

      return (DiagnosticMonitorControl)retVal;
   }

   public DiagnosticAction[] getAvailableActions() {
      int size = this.actionMap.size();
      DiagnosticAction[] actions = new DiagnosticAction[size];
      int ind = 0;

      String type;
      for(Iterator it = this.actionMap.keySet().iterator(); it.hasNext(); actions[ind++] = this.getDiagnosticAction(type)) {
         type = (String)it.next();
      }

      return actions;
   }

   public DiagnosticAction getDiagnosticAction(String type) {
      String actionClassName = (String)this.actionMap.get(type);
      if (actionClassName == null) {
         return null;
      } else {
         DiagnosticAction retVal = null;

         try {
            retVal = (DiagnosticAction)this.actionsClassLoader.loadClass(actionClassName).newInstance();
         } catch (Exception var5) {
            UnexpectedExceptionHandler.handle("Unknown diagnostic action type " + type, var5);
         }

         retVal.setType(type);
         return retVal;
      }
   }

   public String[] getActionTypes() {
      ArrayList list = new ArrayList(this.statelessActionTypes);
      list.addAll(this.aroundActionTypes);
      String[] types = new String[list.size()];
      return (String[])((String[])list.toArray(types));
   }

   public String[] getStatelessDiagnosticActionTypes() {
      String[] types = new String[this.statelessActionTypes.size()];
      return (String[])((String[])this.statelessActionTypes.toArray(types));
   }

   public String[] getAroundDiagnosticActionTypes() {
      String[] types = new String[this.aroundActionTypes.size()];
      return (String[])((String[])this.aroundActionTypes.toArray(types));
   }

   public String getActionClassname(String actionType) {
      return (String)this.actionMap.get(actionType);
   }

   private void readCustomActions() {
      List customActionProviders = ManifestManager.getServices(DiagnosticAction.class);
      Iterator it = customActionProviders.iterator();

      while(true) {
         String actionType;
         String actionClassName;
         while(true) {
            DiagnosticAction action;
            do {
               if (!it.hasNext()) {
                  return;
               }

               action = (DiagnosticAction)it.next();
               actionType = action.getType();
               actionClassName = action.getClass().getName();
            } while(this.isAlreadyRegistered(actionType, actionClassName));

            if (action instanceof StatelessDiagnosticAction) {
               this.statelessActionTypes.add(actionType);
               break;
            }

            if (action instanceof AroundDiagnosticAction) {
               this.aroundActionTypes.add(actionType);
               break;
            }
         }

         this.actionMap.put(actionType, actionClassName);
         if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_CONFIG.debug("Found custom action class: " + actionClassName);
         }
      }
   }

   private boolean isAlreadyRegistered(String actionType, String actionClassName) {
      Iterator it = this.actionMap.keySet().iterator();

      String type;
      String className;
      do {
         if (!it.hasNext()) {
            return false;
         }

         type = (String)it.next();
         className = (String)this.actionMap.get(type);
      } while(!type.equals(actionType) && !className.equals(actionClassName));

      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("Action type " + actionType + " or action class " + actionClassName + " is already in use");
      }

      return true;
   }

   public boolean isGatheringExtended() {
      String[] statelessActions = this.engineConf.getGroupActionTypes("DataGatheringStatelessActions");
      String[] aroundActions = this.engineConf.getGroupActionTypes("DataGatheringAroundActions");
      int totalActions = statelessActions.length + aroundActions.length;
      return totalActions > 2;
   }

   public Map getValueRenderersByType() {
      return this.engineConf.getValueRenderersByType();
   }

   public List getEventClassNamesInUse() {
      return this.eventClassNamesInUse;
   }

   public void removeUnkeptPointcuts() {
      if (!DISABLE_REMOVE_SERVER_POINTCUTS) {
         this.engineConf.removeUnkeptPointcuts();
      }

   }
}
