package weblogic.diagnostics.instrumentation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.WeakHashMap;
import java.util.regex.PatternSyntaxException;
import weblogic.diagnostics.context.CorrelationHelper;
import weblogic.diagnostics.descriptor.WLDFInstrumentationBean;
import weblogic.diagnostics.descriptor.WLDFInstrumentationMonitorBean;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.instrumentation.engine.InstrumentationEngineConfiguration;
import weblogic.diagnostics.instrumentation.engine.InstrumentorEngine;
import weblogic.diagnostics.instrumentation.engine.MonitorSpecification;
import weblogic.diagnostics.instrumentation.engine.base.PointcutExpression;
import weblogic.diagnostics.instrumentation.engine.base.PointcutExpression.Factory;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.WLDFRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class InstrumentationScope implements Serializable {
   static final long serialVersionUID = -566868405759147402L;
   private String name;
   private String description;
   private List diagnosticMonitorControls;
   private boolean enabled;
   private String[] includes;
   private String[] excludes;
   private transient InstrumentorEngine instrumentorEngine;
   private transient InstrumentationRuntimeMBeanImpl runtimeMbean;
   private WeakHashMap classLoadersMap = new WeakHashMap();
   private Map descriptorMap;
   private boolean modified;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public InstrumentationScope(String name) {
      this.name = name;
      this.description = "";
      this.diagnosticMonitorControls = new ArrayList();
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public DiagnosticMonitorControl findDiagnosticMonitorControl(String monitorType) {
      synchronized(this.diagnosticMonitorControls) {
         Iterator it = this.diagnosticMonitorControls.iterator();

         DiagnosticMonitorControl retVal;
         do {
            if (!it.hasNext()) {
               return null;
            }

            retVal = (DiagnosticMonitorControl)it.next();
         } while(!monitorType.equals(retVal.getType()));

         return retVal;
      }
   }

   public DiagnosticMonitorControl[] getMonitorControlsInScope() {
      synchronized(this.diagnosticMonitorControls) {
         DiagnosticMonitorControl[] monitors = new DiagnosticMonitorControl[this.diagnosticMonitorControls.size()];
         return (DiagnosticMonitorControl[])((DiagnosticMonitorControl[])this.diagnosticMonitorControls.toArray(monitors));
      }
   }

   public Map getActiveDescriptorBeans() {
      Map copy = new HashMap(this.descriptorMap);
      return copy;
   }

   public void addMonitorControl(DiagnosticMonitorControl monitor) throws DuplicateMonitorException {
      synchronized(this.diagnosticMonitorControls) {
         String monitorType = monitor.getType();
         InstrumentationDebug.DEBUG_CONFIG.debug("Addmin monitor " + monitorType + " to scope " + this.getName());
         if (this.findDiagnosticMonitorControl(monitorType) != null) {
            throw new DuplicateMonitorException("Diagnostic monitor of type " + monitorType + " already exists in instrumentation scope " + this.getName());
         } else {
            this.diagnosticMonitorControls.add(monitor);
            if (monitor.getInstrumentationScope() == null || !"_WL_INTERNAL_SERVER_SCOPE".equals(this.getName())) {
               monitor.setInstrumentationScope(this);
            }

         }
      }
   }

   public void removeMonitorControl(DiagnosticMonitorControl monitor) throws MonitorNotFoundException {
      synchronized(this.diagnosticMonitorControls) {
         String monitorType = monitor.getType();
         InstrumentationDebug.DEBUG_CONFIG.debug("Removing monitor " + monitorType + " from scope " + this.getName());
         monitor = this.findDiagnosticMonitorControl(monitorType);
         if (monitor == null) {
            throw new MonitorNotFoundException("Diagnostic monitor of type " + monitorType + " does not exist in instrumentation scope " + this.getName());
         } else {
            monitor.setEnabled(false);
            monitor.setInstrumentationScope((InstrumentationScope)null);
            this.diagnosticMonitorControls.remove(monitor);
         }
      }
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean enable) {
      this.enabled = enable;
      if (!this.enabled) {
         this.disableAllMonitors();
      }

   }

   public String[] getIncludes() {
      return this.includes;
   }

   public void setIncludes(String[] includeRegex) {
      this.includes = includeRegex;
   }

   public String[] getExcludes() {
      return this.excludes;
   }

   public void setExcludes(String[] excludeRegex) {
      this.excludes = excludeRegex;
   }

   public InstrumentationStatistics getInstrumentationStatistics() {
      return this.instrumentorEngine != null ? this.instrumentorEngine.getInstrumentationStatistics() : new InstrumentationStatistics();
   }

   byte[] instrumentClass(ClassLoader classLoader, String className, byte[] bytes) {
      byte[] retVal = bytes;
      if (this.isEnabled() && this.instrumentorEngine != null) {
         byte[] modifiedBytes = this.instrumentorEngine.instrumentClass(classLoader, className, bytes);
         if (modifiedBytes != null) {
            retVal = modifiedBytes;
         }
      }

      synchronized(this.classLoadersMap) {
         this.classLoadersMap.put(classLoader, new WeakReference(classLoader));
         return retVal;
      }
   }

   private void redefineClasses() {
      DiagnosticsLogger.logDiagnosticsClassRedefinition(this.getName());
      this.initializeEngine();
      ArrayList loaderList = new ArrayList();
      synchronized(this.classLoadersMap) {
         Iterator it = this.classLoadersMap.keySet().iterator();

         while(true) {
            if (!it.hasNext()) {
               break;
            }

            loaderList.add(it.next());
         }
      }

      if (loaderList.size() > 0) {
         try {
            Class clz = Class.forName("weblogic.diagnostics.instrumentation.agent.WLDFInstrumentationAgent");
            Class[] argTypes = new Class[]{List.class};
            Method method = clz.getMethod("redefineClasses", argTypes);
            Object[] args = new Object[]{loaderList};
            method.invoke((Object)null, args);
         } catch (Throwable var7) {
            DiagnosticsLogger.logDiagnosticsClassRedefinitionFailed(this.getName(), var7);
         }
      }

   }

   void findAttachedActionTypes(Map map) {
      Iterator it = this.diagnosticMonitorControls.iterator();

      while(true) {
         Object obj;
         do {
            if (!it.hasNext()) {
               return;
            }

            obj = it.next();
         } while(!(obj instanceof DelegatingMonitorControl));

         DelegatingMonitorControl ctl = (DelegatingMonitorControl)obj;
         DiagnosticAction[] actions = ctl.getActions();
         int size = actions != null ? actions.length : 0;

         for(int i = 0; i < size; ++i) {
            String type = actions[i].getType();
            synchronized(map) {
               String usedInScopes = (String)map.get(type);
               usedInScopes = usedInScopes == null ? this.getName() : usedInScopes + "," + this.getName();
               map.put(type, usedInScopes);
            }
         }
      }
   }

   private void initializeEngine() {
      InstrumentationEngineConfiguration engineConfig = InstrumentationEngineConfiguration.getInstrumentationEngineConfiguration();
      List monitorSpecificationList = new ArrayList();
      Iterator it = this.diagnosticMonitorControls.iterator();

      while(it.hasNext()) {
         DiagnosticMonitorControl monControl = (DiagnosticMonitorControl)it.next();
         MonitorSpecification monSpec = null;
         if (monControl instanceof CustomMonitorControl) {
            monSpec = ((CustomMonitorControl)monControl).getMonitorSpecification();
         } else {
            monSpec = engineConfig.getMonitorSpecification(monControl.getType());
         }

         if (monSpec != null) {
            monitorSpecificationList.add(monSpec);
         }
      }

      MonitorSpecification[] mSpecs = new MonitorSpecification[monitorSpecificationList.size()];
      mSpecs = (MonitorSpecification[])((MonitorSpecification[])monitorSpecificationList.toArray(mSpecs));
      boolean hotswapAllowed = InstrumentationManager.isHotswapAvailable();
      this.instrumentorEngine = new InstrumentorEngine(this.name, mSpecs, hotswapAllowed);
      this.instrumentorEngine.setDiagnosticMonitors(this.diagnosticMonitorControls);

      try {
         if (this.includes != null) {
            this.instrumentorEngine.setIncludePatterns(this.includes);
         }
      } catch (PatternSyntaxException var7) {
         DiagnosticsLogger.logInvalidInclusionPatternError(this.name);
      }

      try {
         if (this.excludes != null) {
            this.instrumentorEngine.setExcludePatterns(this.excludes);
         }
      } catch (PatternSyntaxException var6) {
         DiagnosticsLogger.logInvalidExclusionPatternError(this.name);
      }

   }

   void registerRuntime(RuntimeMBean parent) {
      try {
         if (this.runtimeMbean == null && ManagementService.getRuntimeAccess(kernelId) != null) {
            ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
            WLDFRuntimeMBean wldfRuntime = null;
            if (serverRuntime != null) {
               wldfRuntime = serverRuntime.getWLDFRuntime();
            }

            if (parent == null) {
               parent = wldfRuntime;
            }

            this.runtimeMbean = new InstrumentationRuntimeMBeanImpl(this, (RuntimeMBean)parent);
            if (wldfRuntime != null) {
               wldfRuntime.addWLDFInstrumentationRuntime(this.runtimeMbean);
            }
         }
      } catch (ManagementException var4) {
         UnexpectedExceptionHandler.handle("Internal error: failed to create runtime mbean for instrumentation scope " + this.getName(), var4);
      }

   }

   void unregisterRuntime() {
      try {
         if (this.runtimeMbean != null) {
            if (ManagementService.getRuntimeAccess(kernelId) != null) {
               ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime();
               if (serverRuntime != null) {
                  WLDFRuntimeMBean wldfRuntime = serverRuntime.getWLDFRuntime();
                  wldfRuntime.removeWLDFInstrumentationRuntime(this.runtimeMbean);
               }
            }

            this.runtimeMbean.unregister();
            this.runtimeMbean = null;
         }
      } catch (ManagementException var3) {
         UnexpectedExceptionHandler.handle("Internal error: failed to unregister runtime mbean for instrumentation scope " + this.getName(), var3);
      }

   }

   public synchronized void merge(InstrumentationScope other) {
      if (this.name.equals(other.getName())) {
         boolean scopeChanged = false;
         if (this.enabled != other.enabled) {
            scopeChanged = true;
         }

         if (!this.isIdenticalList(this.includes, other.includes)) {
            scopeChanged = true;
         }

         if (!this.isIdenticalList(this.excludes, other.excludes)) {
            scopeChanged = true;
         }

         this.enabled = this.enabled || other.enabled;
         DiagnosticMonitorControl[] monitors = this.getMonitorControlsInScope();
         int size = monitors != null ? monitors.length : 0;

         int i;
         for(i = 0; i < size; ++i) {
            if (other.findDiagnosticMonitorControl(monitors[i].getType()) == null) {
               try {
                  this.removeMonitorControl(monitors[i]);
                  scopeChanged = true;
               } catch (Exception var10) {
               }
            }
         }

         monitors = other.getMonitorControlsInScope();
         size = monitors != null ? monitors.length : 0;

         for(i = 0; i < size; ++i) {
            DiagnosticMonitorControl otherMon = monitors[i];
            DiagnosticMonitorControl mon = this.findDiagnosticMonitorControl(otherMon.getType());
            if (mon != null) {
               if (mon instanceof CustomMonitorControl && otherMon instanceof CustomMonitorControl) {
                  CustomMonitorControl cust1 = (CustomMonitorControl)mon;
                  if (cust1.isStructurallyDifferent((CustomMonitorControl)otherMon)) {
                     scopeChanged = true;
                  }
               }

               if (!this.isIdenticalList(mon.getIncludes(), otherMon.getIncludes())) {
                  scopeChanged = true;
               }

               if (!this.isIdenticalList(mon.getExcludes(), otherMon.getExcludes())) {
                  scopeChanged = true;
               }

               mon.merge(otherMon);
            } else {
               try {
                  this.addMonitorControl(monitors[i]);
                  scopeChanged = true;
               } catch (Exception var9) {
               }
            }
         }

         this.installDyeInjectionMonitor(other, scopeChanged);
      }
   }

   private void disableAllMonitors() {
      if (this.diagnosticMonitorControls != null) {
         Iterator it = this.diagnosticMonitorControls.iterator();

         while(it.hasNext()) {
            DiagnosticMonitorControl mon = (DiagnosticMonitorControl)it.next();
            mon.setEnabled(false);
         }
      }

   }

   private boolean isIdenticalList(String[] list1, String[] list2) {
      int len1 = list1 != null ? list1.length : 0;
      int len2 = list2 != null ? list2.length : 0;
      if (len1 != len2) {
         return false;
      } else {
         for(int i = 0; i < len1; ++i) {
            String val = list1[i];
            boolean found = false;

            for(int j = 0; !found && j < len2; ++j) {
               if (val.equals(list2[j])) {
                  found = true;
               }
            }

            if (!found) {
               return false;
            }
         }

         return true;
      }
   }

   public static InstrumentationScope createInstrumentationScope(String scopeName, WLDFInstrumentationBean scopeBean) {
      InstrumentationScope scope = new InstrumentationScope(scopeName);
      scope.initializeScopeFromBean(scopeBean, true);
      return scope;
   }

   public synchronized void addDescriptor(WLDFInstrumentationBean scopeBean) {
      if (scopeBean != null) {
         if (this.descriptorMap == null) {
            this.descriptorMap = new HashMap(4);
         }

         this.descriptorMap.put(scopeBean.getName(), scopeBean);
         this.modified = true;
      }
   }

   private void removeDescriptor(WLDFInstrumentationBean scopeBean) {
      if (this.descriptorMap != null) {
         WLDFInstrumentationBean removed = (WLDFInstrumentationBean)this.descriptorMap.remove(scopeBean.getName());
         if (removed != null) {
            this.modified = true;
         }
      }

   }

   private void initializeScopeFromBean(WLDFInstrumentationBean scopeBean, boolean forceNewInstance) {
      if (scopeBean != null) {
         this.setEnabled(scopeBean.isEnabled());
         this.setExcludes(scopeBean.getExcludes());
         this.setIncludes(scopeBean.getIncludes());
         readMonitors(this, scopeBean.getWLDFInstrumentationMonitors(), forceNewInstance);
      }

      if (!this.isEnabled()) {
         this.disableAllMonitors();
      }

   }

   private static void readMonitors(InstrumentationScope scope, WLDFInstrumentationMonitorBean[] monitors, boolean forceNewInstance) {
      int size = monitors != null ? monitors.length : 0;
      InstrumentationEngineConfiguration engineConfig = InstrumentationEngineConfiguration.getInstrumentationEngineConfiguration();

      for(int i = 0; i < size; ++i) {
         WLDFInstrumentationMonitorBean mon = monitors[i];
         String monType = mon.getName();
         DiagnosticMonitorControl monControl = null;
         MonitorSpecification mSpec = engineConfig.getMonitorSpecification(monType);
         if (mSpec != null) {
            monControl = createMonitorControl(scope, mon, forceNewInstance);
         } else {
            monControl = readCustomMonitor(scope, mon);
         }

         if (monControl != null) {
            ((DiagnosticMonitorControl)monControl).setEnabled(mon.isEnabled());
            ((DiagnosticMonitorControl)monControl).setDyeFilteringEnabled(mon.isDyeFilteringEnabled());
            ((DiagnosticMonitorControl)monControl).setDyeMask(CorrelationHelper.parseDyeMask(mon.getDyeMask()));
            ((DiagnosticMonitorControl)monControl).setIncludes(mon.getIncludes());
            ((DiagnosticMonitorControl)monControl).setExcludes(mon.getExcludes());
            if ("HttpSessionDebug".equals(monType)) {
               ((DiagnosticMonitorControl)monControl).argumentsCaptureNeeded = true;
            }

            readProperties((DiagnosticMonitorControl)monControl, mon.getProperties());
            if (monControl instanceof DelegatingMonitorControl) {
               readMonitorActions(scope, mon, (DelegatingMonitorControl)monControl);
            }

            try {
               scope.addMonitorControl((DiagnosticMonitorControl)monControl);
            } catch (DuplicateMonitorException var11) {
               DiagnosticsLogger.logDuplicateMonitorInScope(scope.getName(), ((DiagnosticMonitorControl)monControl).getType());
            }
         }
      }

   }

   private static void readProperties(DiagnosticMonitorControl monControl, String properties) {
      if (properties != null) {
         ByteArrayInputStream in = null;

         try {
            in = new ByteArrayInputStream(properties.getBytes());
            Properties props = new Properties();
            props.load(in);
            String[] attrNames = monControl.getAttributeNames();
            int size = attrNames != null ? attrNames.length : 0;

            for(int i = 0; i < size; ++i) {
               monControl.setAttribute(attrNames[i], (String)null);
            }

            Enumeration en = props.propertyNames();

            while(en.hasMoreElements()) {
               String name = (String)en.nextElement();
               String value = props.getProperty(name);
               monControl.setAttribute(name, value);
            }
         } catch (IOException var17) {
            DiagnosticsLogger.logMonitorAttributeError(monControl.getName(), properties, var17);
         } finally {
            if (in != null) {
               try {
                  in.close();
               } catch (Exception var16) {
               }
            }

         }

      }
   }

   private static void readMonitorActions(InstrumentationScope scope, WLDFInstrumentationMonitorBean mon, DelegatingMonitorControl monControl) {
      InstrumentationLibrary lib = InstrumentationLibrary.getInstrumentationLibrary();
      String[] actionNames = mon.getActions();
      monControl.removeAllActions();
      int size = actionNames != null ? actionNames.length : 0;

      for(int i = 0; i < size; ++i) {
         String actionType = actionNames[i];

         try {
            DiagnosticAction action = lib.getDiagnosticAction(actionType);
            if (action == null) {
               DiagnosticsLogger.logNonExistentActionType(scope.getName(), monControl.getType(), actionType);
            } else {
               monControl.addAction(action);
            }
         } catch (DuplicateActionException var9) {
            DiagnosticsLogger.logDuplicateActionInMonitor(scope.getName(), monControl.getType(), actionType);
         } catch (IncompatibleActionException var10) {
            DiagnosticsLogger.logIncompatibleAction(scope.getName(), monControl.getType(), actionType);
         }
      }

   }

   private static CustomMonitorControl readCustomMonitor(InstrumentationScope scope, WLDFInstrumentationMonitorBean mon) {
      InstrumentationLibrary lib = InstrumentationLibrary.getInstrumentationLibrary();
      CustomMonitorControl monControl = null;
      String monType = mon.getName();
      if ("_WL_INTERNAL_SERVER_SCOPE".equals(scope.getName())) {
         DiagnosticsLogger.logCustomMonitorInServerScopeError(monType);
         return null;
      } else if (!MonitorSpecification.isValidType(monType)) {
         DiagnosticsLogger.logInvalidCharactersInMonitorType(scope.getName(), monType);
         return null;
      } else {
         try {
            int locationType = MonitorSpecification.getLocationType(mon.getLocationType());
            if (locationType == 0) {
               DiagnosticsLogger.logMissingLocationForCustomMonitor(monType, scope.getName());
               return null;
            }

            String pointcutSpec = mon.getPointcut();
            PointcutExpression pExpr = Factory.parse(pointcutSpec);
            MonitorSpecification mSpec = new MonitorSpecification(monType, locationType, pExpr, true, true);
            monControl = new CustomMonitorControl(monType);
            monControl.setLocationType(locationType);
            monControl.setMonitorSpecification(mSpec);
            monControl.setPointcut(pointcutSpec);
         } catch (Exception var9) {
            DiagnosticsLogger.logMonitorConfigurationError(monType, scope.getName(), var9);
         }

         return monControl;
      }
   }

   private static DiagnosticMonitorControl createMonitorControl(InstrumentationScope scope, WLDFInstrumentationMonitorBean mon, boolean forceNewInstance) {
      String monType = mon.getName();
      DiagnosticMonitorControl monControl = null;
      if (!forceNewInstance && "_WL_INTERNAL_SERVER_SCOPE".equals(scope.getName())) {
         InstrumentationManager mgr = InstrumentationManager.getInstrumentationManager();
         monControl = mgr.getServerMonitor(monType);
      } else {
         InstrumentationLibrary lib = InstrumentationLibrary.getInstrumentationLibrary();
         monControl = lib.getDiagnosticMonitorControl(monType);
      }

      if (monControl == null) {
         DiagnosticsLogger.logUnknownMonitorTypeInScope(scope.getName(), monType);
         return null;
      } else {
         if ("_WL_INTERNAL_SERVER_SCOPE".equals(scope.getName())) {
            if (!monControl.isServerScopeAllowed()) {
               DiagnosticsLogger.logInvalidMonitorInServerScope(monType);
               monControl = null;
            }
         } else if (!monControl.isComponentScopeAllowed()) {
            DiagnosticsLogger.logInvalidMonitorInApplicationScope(monType, scope.getName());
            monControl = null;
         }

         return monControl;
      }
   }

   public synchronized void initializeScope() {
      this.mergeAllDescriptors();
      this.initializeEngine();
   }

   public synchronized void deactivateDescriptor(WLDFInstrumentationBean bean) {
      this.removeDescriptor(bean);
      if (this.descriptorMap == null || this.descriptorMap.size() == 0) {
         this.setEnabled(false);
      }

   }

   private void mergeAllDescriptors() {
      if (this.modified) {
         InstrumentationScope mergedScope = new InstrumentationScope(this.name);
         Collection beanList = this.descriptorMap.values();
         Iterator var3 = beanList.iterator();

         while(var3.hasNext()) {
            WLDFInstrumentationBean bean = (WLDFInstrumentationBean)var3.next();
            if (bean.isEnabled()) {
               InstrumentationScope beanScope = new InstrumentationScope(this.getName());
               beanScope.initializeScopeFromBean(bean, true);
               mergedScope.unionOf(beanScope);
            }
         }

         this.merge(mergedScope);
         this.modified = false;
      }
   }

   private boolean unionOf(InstrumentationScope other) {
      boolean scopeChanged = false;
      if (this.enabled != other.enabled) {
         scopeChanged = true;
      }

      if (!this.isIdenticalList(this.includes, other.includes)) {
         scopeChanged = true;
      }

      if (!this.isIdenticalList(this.excludes, other.excludes)) {
         scopeChanged = true;
      }

      this.enabled = this.enabled || other.enabled;
      this.includes = InstrumentationUtils.unionOf(this.includes, other.includes);
      this.excludes = InstrumentationUtils.unionOf(this.excludes, other.excludes);
      DiagnosticMonitorControl[] otherMonitors = other.getMonitorControlsInScope();
      int size = otherMonitors != null ? otherMonitors.length : 0;

      for(int i = 0; i < size; ++i) {
         DiagnosticMonitorControl otherMon = otherMonitors[i];
         DiagnosticMonitorControl existingMon = this.findDiagnosticMonitorControl(otherMon.getType());
         if (existingMon != null) {
            if (existingMon instanceof CustomMonitorControl && otherMon instanceof CustomMonitorControl) {
               CustomMonitorControl cust1 = (CustomMonitorControl)existingMon;
               if (cust1.isStructurallyDifferent((CustomMonitorControl)otherMon)) {
                  scopeChanged = true;
               }
            }

            if (!this.isIdenticalList(existingMon.getIncludes(), otherMon.getIncludes())) {
               scopeChanged = true;
            }

            if (!this.isIdenticalList(existingMon.getExcludes(), otherMon.getExcludes())) {
               scopeChanged = true;
            }

            existingMon.unionOf(otherMon);
         } else {
            try {
               this.addMonitorControl(otherMon);
               scopeChanged = true;
            } catch (Exception var9) {
            }
         }
      }

      return scopeChanged;
   }

   private void installDyeInjectionMonitor(InstrumentationScope other, boolean scopeChanged) {
      InstrumentationManager mgr = InstrumentationManager.getInstrumentationManager();
      if ("_WL_INTERNAL_SERVER_SCOPE".equals(this.getName())) {
         this.enabled = other.isEnabled();
         if (!this.enabled) {
            this.disableAllMonitors();
         }

         mgr.installDyeInjectionMonitor();
      } else if (scopeChanged) {
         if (InstrumentationManager.isHotswapAvailable()) {
            this.enabled = other.isEnabled();
            this.includes = other.includes;
            this.excludes = other.excludes;
            mgr.installDyeInjectionMonitor();
            this.redefineClasses();
         } else {
            DiagnosticsLogger.logWarnHotswapUnavailable(this.getName());
         }
      }

   }
}
