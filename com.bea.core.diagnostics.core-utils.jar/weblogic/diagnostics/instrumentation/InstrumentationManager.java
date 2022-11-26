package weblogic.diagnostics.instrumentation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.descriptor.WLDFInstrumentationBean;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.image.ImageManager;
import weblogic.diagnostics.instrumentation.gathering.DataGatheringManager;
import weblogic.diagnostics.instrumentation.support.DyeInjectionMonitorSupport;
import weblogic.diagnostics.type.FeatureNotAvailableException;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;
import weblogic.diagnostics.watch.InstrumentationManagerService;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.WLDFServerDiagnosticMBean;
import weblogic.management.configuration.WLDFSystemResourceMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.classloaders.GenericClassLoader;

@Service
public final class InstrumentationManager implements PropertyChangeListener, InstrumentationManagerService {
   private boolean valid;
   private List instrumentationScopes = new ArrayList();
   private InstrumentationScope serverInstrumentationScope;
   private boolean synchronousEventPersistence;
   private long eventsPersistenceInterval;
   private InstrumentationEventListener[] eventsListeners;
   private Map classLoaderMap = Collections.synchronizedMap(new WeakHashMap());
   private Map serverMonitors = new HashMap();
   private Map serverManagedMonitors = new HashMap();
   private static final String WEBLOGIC_INSTRUMENTATION_SERVER_MANAGED_SCOPE = "_WL_INTERNAL_SERVER_MANAGED_SCOPE";
   private InstrumentationScope serverManagedScope;
   private InstrumentationStatistics statsForDeletedScopes = new InstrumentationStatistics();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private InstrumentationManager() {
      this.createServerManagedScope();
      this.eventsListeners = new InstrumentationEventListener[0];
      DiagnosticMonitor[] allMonitors = this.getAvailableMonitors();

      for(int i = 0; i < allMonitors.length; ++i) {
         DiagnosticMonitor mon = allMonitors[i];
         if (mon.isServerScopeAllowed()) {
            if (mon.isServerManaged()) {
               if (mon instanceof DiagnosticMonitorControl) {
                  try {
                     if (mon instanceof DelegatingMonitorControl) {
                        this.setupActionList((DelegatingMonitorControl)mon);
                     }

                     this.serverManagedScope.addMonitorControl((DiagnosticMonitorControl)mon);
                     this.serverManagedMonitors.put(mon.getType(), (DiagnosticMonitorControl)mon);
                  } catch (DuplicateMonitorException var5) {
                  }
               }
            } else {
               this.serverMonitors.put(mon.getType(), mon);
            }
         }
      }

      InstrumentationLibrary library = InstrumentationLibrary.getInstrumentationLibrary();
      if (!library.isValid()) {
         DiagnosticsLogger.logInvalidInstrumentationLibraryError();
      } else {
         DataGatheringManager.setEventClassNamesInUse(library.getEventClassNamesInUse());
         ValueRenderingManager.initialize(library.getValueRenderersByType());
         this.valid = true;
      }
   }

   /** @deprecated */
   @Deprecated
   public static InstrumentationManager getInstrumentationManager() {
      return InstrumentationManager.InstrumentationManagerInitializer.INSTANCE;
   }

   public void initialize() {
      try {
         if (!isRedefineClassesSupported()) {
            GenericClassLoader.addClassPreProcessor("weblogic.diagnostics.instrumentation.DiagnosticClassPreProcessor");
         }

         InstrumentationScope scope = this.getServerInstrumentationScope();
         if (Boolean.getBoolean("weblogic.diagnostics.instrumentation.VerifyEventClasses")) {
            this.verifyEnabledServerManagedMonitorEventClassesLoadable();
         }

         this.updateServerManagedMonitors(DataGatheringManager.getDiagnosticVolume());
      } catch (Exception var2) {
         DiagnosticsLogger.logServerInstrumentationScopeInitializationError(var2);
      }

   }

   public static boolean isRedefineClassesSupported() {
      boolean retVal = false;

      try {
         Class clz = Class.forName("weblogic.diagnostics.instrumentation.agent.WLDFInstrumentationAgent");
         Class[] argTypes = new Class[0];
         Method method = clz.getMethod("isRedefineClassesSupported", argTypes);
         Object[] args = new Object[0];
         Boolean stat = (Boolean)method.invoke((Object)null, args);
         retVal = stat;
      } catch (Exception var6) {
      }

      return retVal;
   }

   public void initializeInstrumentationParameters() {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      ServerMBean server = runtimeAccess.getServer();
      WLDFServerDiagnosticMBean diagnosticMBean = server != null ? server.getServerDiagnosticConfig() : null;
      if (diagnosticMBean != null) {
         this.eventsPersistenceInterval = diagnosticMBean.getEventPersistenceInterval();
         this.synchronousEventPersistence = diagnosticMBean.isSynchronousEventPersistenceEnabled();
         EventQueue.getInstance().setTimerInterval(this.eventsPersistenceInterval);
         ImageManager imageManager = (ImageManager)GlobalServiceLocator.getServiceLocator().getService(ImageManager.class, new Annotation[0]);
         if (imageManager != null) {
            imageManager.registerImageSource("InstrumentationImageSource", new InstrumentationImageSource());
         }

         diagnosticMBean.addPropertyChangeListener(this);
      }
   }

   InstrumentationScope getServerInstrumentationScope() throws Exception {
      InstrumentationScope scope = null;
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      String serverName = runtimeAccess.getServerName();
      DomainMBean domain = runtimeAccess.getDomain();
      if (domain == null) {
         return null;
      } else {
         WLDFSystemResourceMBean[] wldfResourceMBeans = domain.getWLDFSystemResources();
         int size = wldfResourceMBeans != null ? wldfResourceMBeans.length : 0;

         for(int i = 0; scope == null && i < size; ++i) {
            WLDFSystemResourceMBean wldf = wldfResourceMBeans[i];
            TargetMBean[] targets = wldf.getTargets();
            int targetCount = targets != null ? targets.length : 0;

            for(int j = 0; scope == null && j < targetCount; ++j) {
               if (targets[j].getServerNames().contains(serverName)) {
                  WLDFResourceBean wldfResource = wldf.getWLDFResource();
                  if (wldfResource == null) {
                     throw new IllegalArgumentException(DiagnosticsLogger.logWLDFResourceBeanNotFoundLoggable(wldf.getName()).getMessage());
                  }

                  WLDFInstrumentationBean instrumentationBean = wldfResource.getInstrumentation();
                  if (instrumentationBean != null) {
                     scope = this.createInstrumentationScope("_WL_INTERNAL_SERVER_SCOPE", instrumentationBean);
                  }
               }
            }
         }

         this.serverInstrumentationScope = scope;
         return scope;
      }
   }

   public DiagnosticMonitorControl getServerMonitor(String type) {
      return (DiagnosticMonitorControl)this.serverMonitors.get(type);
   }

   public static void initializeServerScope(ClassLoader gcl, byte[] bytes) throws Exception {
      ByteArrayInputStream bin = new ByteArrayInputStream(bytes);
      ObjectInputStream ois = null;
      InstrumentationScope scope = null;

      try {
         ois = new ObjectInputStream(bin);
         scope = (InstrumentationScope)ois.readObject();
      } finally {
         if (ois != null) {
            ois.close();
         }

      }

      InstrumentationManager mgr = getInstrumentationManager();
      mgr.addInstrumentationScope("_WL_INTERNAL_SERVER_SCOPE", scope, false);
      mgr.associateClassloaderWithScope(gcl, scope);
   }

   public static boolean isHotswapAvailable() {
      return isRedefineClassesSupported();
   }

   public DiagnosticMonitor[] getAvailableMonitors() {
      DiagnosticMonitor[] availableMonitors = InstrumentationLibrary.getInstrumentationLibrary().getAllAvailableMonitors();
      return availableMonitors;
   }

   public DiagnosticAction[] getAvailableActions() {
      DiagnosticAction[] availableActions = InstrumentationLibrary.getInstrumentationLibrary().getAvailableActions();
      return availableActions;
   }

   public InstrumentationScope[] getInstrumentationScopes() {
      synchronized(this.instrumentationScopes) {
         InstrumentationScope[] tmp = new InstrumentationScope[this.instrumentationScopes.size()];
         return (InstrumentationScope[])((InstrumentationScope[])this.instrumentationScopes.toArray(tmp));
      }
   }

   public InstrumentationScope createInstrumentationScope(String scopeName) throws ScopeAlreadyExistsException {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("Creating instrumentation scope: " + scopeName);
      }

      InstrumentationScope retVal = InstrumentationScope.createInstrumentationScope(scopeName, (WLDFInstrumentationBean)null);
      this.addInstrumentationScope(scopeName, retVal);
      return retVal;
   }

   public InstrumentationScope createInstrumentationScope(String scopeName, WLDFInstrumentationBean scopeBean) throws ScopeAlreadyExistsException {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("Creating instrumentation scope: " + scopeName);
      }

      InstrumentationScope retVal = InstrumentationScope.createInstrumentationScope(scopeName, scopeBean);
      this.addInstrumentationScope(scopeName, retVal);
      if ("_WL_INTERNAL_SERVER_SCOPE".equals(scopeName)) {
         this.serverInstrumentationScope = retVal;
      }

      return retVal;
   }

   private void addInstrumentationScope(String scopeName, InstrumentationScope scope) throws ScopeAlreadyExistsException {
      this.addInstrumentationScope(scopeName, scope, true);
   }

   private synchronized void addInstrumentationScope(String scopeName, InstrumentationScope scope, boolean installDyeInjector) throws ScopeAlreadyExistsException {
      if (!this.isValid()) {
         scope.setEnabled(false);
      }

      synchronized(this.instrumentationScopes) {
         if (this.findInstrumentationScope(scopeName) != null) {
            throw new ScopeAlreadyExistsException("Instrumentation scope already exists for " + scopeName);
         } else {
            if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
               InstrumentationDebug.DEBUG_CONFIG.debug("Adding instrumentation scope: " + scopeName);
            }

            this.instrumentationScopes.add(scope);
            if (installDyeInjector) {
               this.installDyeInjectionMonitor();
            }

         }
      }
   }

   private boolean hasEnabledScope() {
      Iterator var1 = this.instrumentationScopes.iterator();

      InstrumentationScope scope;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         scope = (InstrumentationScope)var1.next();
      } while(!scope.isEnabled());

      return true;
   }

   public void installDyeInjectionMonitor() {
      InstrumentationScope serverScope = this.findInstrumentationScope("_WL_INTERNAL_SERVER_SCOPE");
      DiagnosticMonitorControl dyeInjectionMonitor = null;
      if (serverScope != null) {
         dyeInjectionMonitor = serverScope.findDiagnosticMonitorControl("DyeInjection");
      }

      boolean computeDyes = dyeInjectionMonitor != null ? ((DiagnosticMonitorControl)dyeInjectionMonitor).isEnabled() : false;
      if (this.hasEnabledScope()) {
         if (dyeInjectionMonitor == null || !computeDyes) {
            dyeInjectionMonitor = new StandardMonitorControl("DyeInjection");
         }
      } else {
         dyeInjectionMonitor = null;
      }

      DyeInjectionMonitorSupport.setDyeInjectionMonitor((DiagnosticMonitorControl)dyeInjectionMonitor);
   }

   public InstrumentationScope findInstrumentationScope(String scopeName) {
      synchronized(this.instrumentationScopes) {
         Iterator var3 = this.instrumentationScopes.iterator();

         InstrumentationScope retVal;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            retVal = (InstrumentationScope)var3.next();
         } while(!scopeName.equals(retVal.getName()));

         return retVal;
      }
   }

   public void deleteInstrumentationScope(InstrumentationScope scope) {
      String scopeName = scope.getName();
      synchronized(this.instrumentationScopes) {
         if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
            InstrumentationDebug.DEBUG_CONFIG.debug("Deleting instrumentation scope: " + scopeName);
         }

         this.statsForDeletedScopes.add(scope.getInstrumentationStatistics());
         DiagnosticMonitorControl[] monitors = scope.getMonitorControlsInScope();
         int size = monitors != null ? monitors.length : 0;
         int i = 0;

         while(true) {
            if (i >= size) {
               this.instrumentationScopes.remove(scope);
               break;
            }

            monitors[i].setEnabled(false);
            ++i;
         }
      }

      if ("_WL_INTERNAL_SERVER_SCOPE".equals(scopeName)) {
         this.serverInstrumentationScope = null;
         DyeInjectionMonitorSupport.setDyeInjectionMonitor((DiagnosticMonitorControl)null);
      }

   }

   public void setDyeMask(InstrumentationScope scope, long dye_mask) {
      DiagnosticMonitor[] monitors = scope.getMonitorControlsInScope();
      int size = monitors != null ? monitors.length : 0;

      for(int i = 0; i < size; ++i) {
         this.setDyeMask((DiagnosticMonitor)monitors[i], dye_mask);
      }

   }

   public void setDyeMask(DiagnosticMonitor monitor, long dye_mask) {
      monitor.setDyeMask(dye_mask);
   }

   public void setDyeMask(InstrumentationScope scope, String actionType, long dye_mask) {
      DiagnosticMonitor[] monitors = scope.getMonitorControlsInScope();
      int monCnt = monitors != null ? monitors.length : 0;

      for(int i = 0; i < monCnt; ++i) {
         DiagnosticMonitor mon = monitors[i];
         this.setDyeMask((DiagnosticMonitor)mon, dye_mask);
      }

   }

   public void weaveClass(InstrumentationScope scope, String className) throws FeatureNotAvailableException, ClassNotFoundException, InstrumentationException {
   }

   public void associateClassloaderWithScope(ClassLoader gcl, InstrumentationScope scope) {
      this.classLoaderMap.put(gcl, scope);
   }

   public InstrumentationScope getAssociatedScope(ClassLoader gcl) {
      return (InstrumentationScope)this.classLoaderMap.get(gcl);
   }

   private List array2list(Object[] arr) {
      List list = new ArrayList();
      int size = arr != null ? arr.length : 0;

      for(int i = 0; i < size; ++i) {
         list.add(arr[i]);
      }

      return list;
   }

   public void addInstrumentationEventListener(InstrumentationEventListener listener) {
      synchronized(this) {
         List list = this.array2list(this.eventsListeners);
         if (!list.contains(listener)) {
            list.add(listener);
            InstrumentationEventListener[] tmp = new InstrumentationEventListener[list.size()];
            this.eventsListeners = (InstrumentationEventListener[])((InstrumentationEventListener[])list.toArray(tmp));
         }

      }
   }

   public void removeInstrumentationEventListener(InstrumentationEventListener listener) {
      synchronized(this) {
         List list = this.array2list(this.eventsListeners);
         if (list.contains(listener)) {
            list.remove(listener);
            InstrumentationEventListener[] tmp = new InstrumentationEventListener[list.size()];
            this.eventsListeners = (InstrumentationEventListener[])((InstrumentationEventListener[])list.toArray(tmp));
         }

      }
   }

   public InstrumentationStatistics getInstrumentationStatistics() {
      InstrumentationStatistics stats = new InstrumentationStatistics();
      stats.add(this.statsForDeletedScopes);
      Iterator var2 = this.instrumentationScopes.iterator();

      while(var2.hasNext()) {
         InstrumentationScope scope = (InstrumentationScope)var2.next();
         stats.add(scope.getInstrumentationStatistics());
      }

      return stats;
   }

   void propagateInstrumentationEvents(List eventsList) {
      InstrumentationEventListener[] listeners = this.eventsListeners;
      int size = listeners != null ? listeners.length : 0;

      for(int i = 0; i < size; ++i) {
         InstrumentationEventListener listener = listeners[i];

         try {
            Iterator it = eventsList.iterator();

            while(it.hasNext()) {
               DataRecord event = (DataRecord)it.next();
               listener.handleInstrumentationEvent(event);
            }
         } catch (Exception var8) {
            UnexpectedExceptionHandler.handle("Could not propagate events to listener " + listener, var8);
         }
      }

   }

   public boolean isValid() {
      return this.valid;
   }

   public boolean isEnabled() {
      return this.valid && this.serverInstrumentationScope != null ? this.serverInstrumentationScope.isEnabled() : false;
   }

   public boolean isSynchronousEventPersistenceEnabled() {
      return this.synchronousEventPersistence;
   }

   public void propertyChange(PropertyChangeEvent evt) {
      this.attributesChanged(evt.getSource());
   }

   private void attributesChanged(Object source) {
      if (source instanceof WLDFServerDiagnosticMBean) {
         WLDFServerDiagnosticMBean diagnosticMBean = (WLDFServerDiagnosticMBean)source;
         long interval = diagnosticMBean.getEventPersistenceInterval();
         if (interval != this.eventsPersistenceInterval) {
            EventQueue.getInstance().setTimerInterval(interval);
            this.eventsPersistenceInterval = interval;
         }

         this.synchronousEventPersistence = diagnosticMBean.isSynchronousEventPersistenceEnabled();
      }

   }

   Map findAttachedActionTypes() {
      Map map = new HashMap();
      if (this.serverInstrumentationScope != null) {
         this.serverInstrumentationScope.findAttachedActionTypes(map);
      }

      if (this.instrumentationScopes != null) {
         Iterator var2 = this.instrumentationScopes.iterator();

         while(var2.hasNext()) {
            InstrumentationScope scope = (InstrumentationScope)var2.next();
            scope.findAttachedActionTypes(map);
         }
      }

      return map;
   }

   static boolean isKernelIdentity() {
      AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(kernelId);
      return SecurityServiceManager.isKernelIdentity(subject);
   }

   public void updateServerManagedMonitors(int diagnosticVolume) {
      if (this.serverManagedMonitors != null && this.serverManagedMonitors.size() != 0) {
         boolean canBeEnabled = DataGatheringManager.isGatheringEnabled();
         boolean jfrActionsDisabled = DataGatheringManager.jfrActionsDisabled();
         Iterator var4 = this.serverManagedMonitors.values().iterator();

         while(true) {
            while(true) {
               DiagnosticMonitorControl monitor;
               do {
                  label51:
                  do {
                     for(; var4.hasNext(); monitor.setEnabled(false)) {
                        monitor = (DiagnosticMonitorControl)var4.next();
                        if (canBeEnabled && DataGatheringManager.convertVolume(monitor.getDiagnosticVolume()) <= diagnosticVolume) {
                           if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                              InstrumentationDebug.DEBUG_CONFIG.debug("monitor enabled: " + monitor.getType());
                           }

                           monitor.setEnabled(true);
                           continue label51;
                        }

                        if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                           InstrumentationDebug.DEBUG_CONFIG.debug("monitor disabled: " + monitor.getType());
                        }
                     }

                     return;
                  } while(!jfrActionsDisabled);
               } while(!(monitor instanceof DelegatingMonitorControl));

               DelegatingMonitorControl delegatingMonitor = (DelegatingMonitorControl)monitor;
               DiagnosticAction[] var7 = delegatingMonitor.getActions();
               int var8 = var7.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  DiagnosticAction action = var7[var9];
                  if (action.getType().equals("FlightRecorderAroundAction") || action.getType().equals("FlightRecorderStatelessAction")) {
                     try {
                        if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                           InstrumentationDebug.DEBUG_CONFIG.debug("Removing " + action.getType() + " from monitor: " + monitor.getType());
                        }

                        delegatingMonitor.removeAction(action);
                        if (action.requiresArgumentsCapture()) {
                           delegatingMonitor.subvertArgumentsCaptureNeededCheck();
                        }
                     } catch (ActionNotFoundException var12) {
                     }
                     break;
                  }
               }
            }
         }
      }
   }

   private void createServerManagedScope() {
      if (this.serverManagedScope == null) {
         this.serverManagedScope = InstrumentationScope.createInstrumentationScope("_WL_INTERNAL_SERVER_MANAGED_SCOPE", (WLDFInstrumentationBean)null);
      }
   }

   private void setupActionList(DelegatingMonitorControl monControl) {
      InstrumentationLibrary lib = InstrumentationLibrary.getInstrumentationLibrary();
      String[] var3 = monControl.getCompatibleActionTypes();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String actionType = var3[var5];

         try {
            DiagnosticAction action = lib.getDiagnosticAction(actionType);
            if (action == null) {
               DiagnosticsLogger.logNonExistentActionType(this.serverManagedScope.getName(), monControl.getType(), actionType);
            } else {
               monControl.addAction(action);
            }
         } catch (DuplicateActionException var8) {
            DiagnosticsLogger.logDuplicateActionInMonitor(this.serverManagedScope.getName(), monControl.getType(), actionType);
         } catch (IncompatibleActionException var9) {
            DiagnosticsLogger.logIncompatibleAction(this.serverManagedScope.getName(), monControl.getType(), actionType);
         }
      }

   }

   public DiagnosticMonitorControl getServerManagedMonitorControl(String monType) {
      return monType != null ? (DiagnosticMonitorControl)this.serverManagedMonitors.get(monType) : null;
   }

   private boolean verifyEnabledServerManagedMonitorEventClassesLoadable() {
      boolean isValid = true;
      if (this.serverManagedMonitors != null && !this.serverManagedMonitors.isEmpty()) {
         Iterator var2 = this.serverManagedMonitors.values().iterator();

         while(true) {
            while(var2.hasNext()) {
               DiagnosticMonitorControl monitor = (DiagnosticMonitorControl)var2.next();
               String eventClassName = monitor.getEventClassName();
               if (eventClassName == null) {
                  if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                     InstrumentationDebug.DEBUG_CONFIG.debug("Monitor has null event class name: " + monitor.getType());
                  }
               } else {
                  try {
                     Class testClass = Class.forName(eventClassName);
                     if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                        InstrumentationDebug.DEBUG_CONFIG.debug("Loaded event class for Monitor : " + monitor.getType());
                     }
                  } catch (Throwable var6) {
                     isValid = false;
                     if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                        InstrumentationDebug.DEBUG_CONFIG.debug("Unable to load event class: " + eventClassName + ", specified by Monitor: " + monitor.getType(), var6);
                     }
                  }
               }
            }

            return isValid;
         }
      } else {
         return isValid;
      }
   }

   public boolean isGatheringExtended() {
      InstrumentationLibrary lib = InstrumentationLibrary.getInstrumentationLibrary();
      return lib.isGatheringExtended();
   }

   public Map getValueRenderersByType() {
      InstrumentationLibrary lib = InstrumentationLibrary.getInstrumentationLibrary();
      return lib.getValueRenderersByType();
   }

   private static class InstrumentationManagerInitializer {
      private static final InstrumentationManager INSTANCE = (InstrumentationManager)LocatorUtilities.getService(InstrumentationManager.class);
   }
}
