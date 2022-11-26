package weblogic.management.provider.internal;

import com.bea.xml.XmlError;
import com.bea.xml.XmlException;
import com.bea.xml.XmlValidationError;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import org.jvnet.hk2.annotations.Service;
import org.xml.sax.SAXParseException;
import weblogic.common.internal.VersionInfo;
import weblogic.descriptor.BeanCreationInterceptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorCache;
import weblogic.descriptor.DescriptorCreationListener;
import weblogic.descriptor.DescriptorException;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.SettableBean;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.Loggable;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.ManagementLogger;
import weblogic.management.SpecialPropertiesProcessor;
import weblogic.management.configuration.ConfigurationException;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PropertyValueVBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.SimplePropertyValueVBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.filelock.FileLockHandle;
import weblogic.management.filelock.ManagementFileLockService;
import weblogic.management.internal.AdminServerMBeanProcessor;
import weblogic.management.internal.BootStrapStruct;
import weblogic.management.internal.ProductionModeHelper;
import weblogic.management.internal.Utils;
import weblogic.management.partition.admin.WorkingVirtualTargetManager;
import weblogic.management.provider.AccessCallback;
import weblogic.management.provider.MSIService;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.provider.RuntimeAccessSettable;
import weblogic.management.provider.UpdateException;
import weblogic.management.provider.internal.situationalconfig.SituationalPropertiesProcessor;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.upgrade.ConfigFileHelper;
import weblogic.management.utils.situationalconfig.SituationalConfigManager;
import weblogic.protocol.ConnectMonitorFactory;
import weblogic.rmi.extensions.ConnectEvent;
import weblogic.rmi.extensions.ConnectListener;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.ServiceFailureException;
import weblogic.utils.Debug;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.annotation.Secure;

@Service
@Secure
@Named
public class RuntimeAccessImpl extends RegistrationManagerImpl implements RuntimeAccess, RuntimeAccessSettable, ConnectListener {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   private static final String ADMIN_HOST_PROP = "weblogic.management.server";
   private static final String OLD_ADMIN_HOST_PROP = "weblogic.admin.host";
   private static int ONE_MINUTE_TIMEOUT = 60000;
   private final ManagementFileLockService managementFileLockService;
   private DomainMBean domain;
   private ServerMBean server;
   private ServerRuntimeMBean serverRuntime;
   private String adminHostProperty;
   private MSIService msiService = null;
   private PartitionProcessor partitionProcessor = null;
   private static final AuthenticatedSubject kernelID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private List accessCallbackList = new ArrayList();
   public static final String SCHEMA_VALIDATION_ENABLED_PROP = "weblogic.configuration.schemaValidationEnabled";
   private static final boolean schemaValidationEnabled = getBooleanProperty("weblogic.configuration.schemaValidationEnabled", true);
   private SituationalConfigManager situationalConfigManager;
   private SituationalPropertiesProcessor situationalPropManager;

   public static boolean getBooleanProperty(String prop, boolean _default) {
      String value = System.getProperty(prop);
      return value != null ? Boolean.parseBoolean(value) : _default;
   }

   @Inject
   private RuntimeAccessImpl(ManagementFileLockService mfls) throws ManagementException {
      this.managementFileLockService = mfls;
      this.adminHostProperty = System.getProperty("weblogic.management.server");
      if (this.adminHostProperty == null) {
         this.adminHostProperty = System.getProperty("weblogic.admin.host");
      }

      try {
         long t0 = 0L;
         long t1 = 0L;
         if (debug.isDebugEnabled()) {
            t0 = System.currentTimeMillis();
         }

         this.domain = this.parseNewStyleConfig();
         if (debug.isDebugEnabled()) {
            t1 = System.currentTimeMillis();
            Debug.say("CONFIG PARSE TOOK " + (t1 - t0) + " milliseconds");
         }
      } catch (ManagementException var7) {
         throw var7;
      } catch (Throwable var8) {
         Loggable l = ManagementLogger.logConfigurationParseErrorLoggable("config.xml", this.getRootCauseMessage(var8));
         throw new ManagementException(l.getMessage(), var8);
      }

      if (this.isAdminServer()) {
         try {
            if (this.domain.isConfigBackupEnabled()) {
               if (debug.isDebugEnabled()) {
                  Debug.say("BACKUP");
               }

               ConfigBackup.saveOriginal();
            }
         } catch (IOException var6) {
         }
      }

   }

   private String getRootCauseMessage(Throwable t) {
      String result = t.getMessage();

      for(t = t.getCause(); t != null; t = t.getCause()) {
         String msg = t.getMessage();
         if (msg != null && msg.length() > 0) {
            result = msg;
         }
      }

      return result;
   }

   private DomainMBean parseNewStyleConfig() throws ManagementException {
      Loggable l;
      try {
         DescriptorHelper.setSkipSetProductionMode(true);
         File configDir = new File(DomainDir.getConfigDir());
         if (!configDir.exists()) {
            String path = configDir.getAbsolutePath();
            if (!this.isAdminServer() && !this.isAdminServerAvailable()) {
               l = ManagementLogger.logConfigurationDirMissingNoAdminLoggable(path);
               throw new ManagementException(l.getMessage());
            } else {
               l = ManagementLogger.logConfigurationDirMissingLoggable(path);
               throw new ManagementException(l.getMessage());
            }
         } else {
            File newFile = new File(configDir, "config.xml");
            if (!newFile.exists()) {
               String path = configDir.getAbsolutePath();
               Loggable l;
               if (!this.isAdminServer() && !this.isAdminServerAvailable()) {
                  l = ManagementLogger.logConfigFileMissingNoAdminLoggable(path, "config.xml");
                  throw new ManagementException(l.getMessage());
               } else {
                  l = ManagementLogger.logConfigFileMissingLoggable(path, "config.xml");
                  throw new ManagementException(l.getMessage());
               }
            } else {
               boolean prodModeEnabledInConfig = ConfigFileHelper.getProductionModeEnabled();
               if (ProductionModeHelper.isProductionModePropertySet() && ManagementService.getPropertyService(kernelID).isAdminServer()) {
                  if (prodModeEnabledInConfig && !ProductionModeHelper.getProductionModeProperty()) {
                     ManagementLogger.logDevelopmentModePropertyDiffersFromConfig();
                  } else if (!prodModeEnabledInConfig && ProductionModeHelper.getProductionModeProperty()) {
                     ManagementLogger.logProductionModePropertyDiffersFromConfig();
                  }
               }

               DescriptorCache descCache = DescriptorCache.getInstance();
               IOHelperImpl ioHelper = new IOHelperImpl(newFile);
               boolean m = prodModeEnabledInConfig || Boolean.getBoolean("weblogic.ProductionModeEnabled");
               ioHelper.setProductionModeEnabled(m);
               File cacheDir = new File(configDir + File.separator + "configCache");
               FileLockHandle configFileLock = this.managementFileLockService.getConfigFileLock((long)ONE_MINUTE_TIMEOUT);

               for(int i = 0; configFileLock == null && i < 14; ++i) {
                  ManagementLogger.logCouldNotGetConfigFileLockRetry("" + ONE_MINUTE_TIMEOUT / 1000);
                  configFileLock = this.managementFileLockService.getConfigFileLock((long)ONE_MINUTE_TIMEOUT);
               }

               if (configFileLock == null) {
                  ManagementLogger.logCouldNotGetConfigFileLock();
               }

               ArrayList errs = null;
               InputStream in = null;

               DomainMBean var15;
               try {
                  String version = VersionInfo.theOne().getReleaseVersion();
                  boolean cacheExists = cacheDir.exists();
                  boolean changed = descCache.hasChanged(cacheDir, ioHelper);
                  if (changed && !cacheExists && (new File(DomainDir.getInitInfoDir())).exists()) {
                     changed = false;
                  }

                  ioHelper.setValidate(changed);
                  if (!changed && !descCache.hasVersionChanged(cacheDir, version) || ConfigFileHelper.getConfigurationVersion() >= 11) {
                     ioHelper.setNeedsTransformation(false);
                  }

                  in = new FileInputStream(newFile);
                  DomainMBean result = (DomainMBean)ioHelper.parseXML(in);
                  errs = ioHelper.getErrs();
                  this.processSchemaErrors(errs, newFile);
                  if (ioHelper.isNeedsTransformation() && errs != null && errs.size() == 0 && !ioHelper.isTransformed()) {
                     descCache.writeVersion(cacheDir, version);
                  }

                  DescriptorHelper.setSkipSetProductionMode(false);
                  var15 = result;
               } finally {
                  if (configFileLock != null) {
                     configFileLock.close();
                     configFileLock = null;
                  }

                  if (errs == null || errs.size() > 0) {
                     descCache.removeCRC(cacheDir);
                     descCache.removeVersion(cacheDir);
                  }

                  if (in != null) {
                     try {
                        in.close();
                     } catch (Exception var25) {
                     }
                  }

               }

               return var15;
            }
         }
      } catch (XMLStreamException var27) {
         DescriptorHelper.setSkipSetProductionMode(false);
         throw this.convertXMLStreamException("config.xml", var27);
      } catch (DescriptorException var28) {
         DescriptorHelper.setSkipSetProductionMode(false);
         Throwable t = var28.getCause();
         if (t instanceof XmlException) {
            throw this.convertXmlException("config.xml", (XmlException)t);
         } else {
            l = ManagementLogger.logConfigurationParseErrorLoggable("config.xml", this.getRootCauseMessage(var28));
            throw new ManagementException(l.getMessage(), var28);
         }
      } catch (IOException var29) {
         DescriptorHelper.setSkipSetProductionMode(false);
         Loggable l = ManagementLogger.logConfigurationParseErrorLoggable("config.xml", this.getRootCauseMessage(var29));
         throw new ManagementException(l.getMessage(), var29);
      }
   }

   private void processSchemaErrors(List errs, File fileName) throws SchemaValidationException {
      if (errs.size() > 0) {
         int noErrs = errs.size();
         Iterator i = errs.iterator();

         while(i.hasNext()) {
            Object o = i.next();
            if (o instanceof XmlValidationError) {
               XmlValidationError ve = (XmlValidationError)o;
               if (ConfigFileHelper.isAcceptableXmlValidationError(ve)) {
                  --noErrs;
               } else {
                  String absPath = fileName.getAbsolutePath();
                  if (ve.getSourceName() == null && ve.getColumn() != -1 && ve.getLine() != -1) {
                     absPath = absPath + "<" + ve.getLine() + ":" + ve.getColumn() + ">";
                  }

                  ManagementLogger.logConfigurationValidationProblem(absPath, ve.getMessage());
               }
            } else {
               ManagementLogger.logConfigurationValidationProblem(fileName.getAbsolutePath(), o.toString());
            }
         }

         if (schemaValidationEnabled && this.isAdminServer() && noErrs > 0) {
            String filename = fileName.getAbsolutePath();
            String option = "-Dweblogic.configuration.schemaValidationEnabled=false";
            Loggable l = ManagementLogger.logConfigurationSchemaFailureLoggable(filename, option);
            throw new SchemaValidationException(l.getMessage());
         }
      }

   }

   private ParseException convertXmlException(String fileName, XmlException e) {
      XmlError err = e.getError();
      if (err != null) {
         int line = err.getLine();
         int column = err.getColumn();
         return this.getParseFailureException(fileName, line, column, e.getMessage(), e);
      } else {
         return this.getParseFailureException(fileName, -1, -1, e.getMessage(), e);
      }
   }

   private ParseException convertXMLStreamException(String fileName, XMLStreamException e) {
      int line = -1;
      int column = -1;
      Location loc = e.getLocation();
      if (loc != null) {
         line = loc.getLineNumber();
         column = loc.getColumnNumber();
      } else {
         Throwable cause = e.getNestedException();
         if (cause instanceof SAXParseException) {
            SAXParseException spe = (SAXParseException)cause;
            line = spe.getLineNumber();
            column = spe.getColumnNumber();
         }
      }

      return this.getParseFailureException(fileName, line, column, e.getMessage(), e);
   }

   private ParseException getParseFailureException(String fileName, int line, int column, String cause, Throwable e) {
      Loggable l;
      if (line <= 0) {
         l = ManagementLogger.logConfigurationParseErrorLoggable(fileName, cause);
         return new ParseException(l.getMessage(), e);
      } else if (column <= 0) {
         l = ManagementLogger.logConfigurationParseError2Loggable(fileName, line, cause);
         return new ParseException(l.getMessage(), e);
      } else {
         l = ManagementLogger.logConfigurationParseError3Loggable(fileName, line, column, cause);
         return new ParseException(l.getMessage(), e);
      }
   }

   void initialize() throws ManagementException {
      String serverName = null;

      try {
         this.situationalConfigManager = (SituationalConfigManager)LocatorUtilities.getService(SituationalConfigManager.class);
         this.situationalPropManager = (SituationalPropertiesProcessor)LocatorUtilities.getService(SituationalPropertiesProcessor.class);
         DynamicServersProcessor.updateConfiguration(this.domain);
         serverName = Utils.findServerName(this.domain);
         if (serverName == null) {
            String msg = "Unable to find a default server";
            throw new ManagementException(msg);
         }

         this.server = this.domain.lookupServer(serverName);
         PartitionProcessor.updateConfiguration(this.domain, true);

         try {
            this.situationalPropManager.loadConfiguration(this.domain);
         } catch (Exception var5) {
            throw new ManagementException("Failure Loading Situational Properties", var5);
         }

         SpecialPropertiesProcessor.updateConfiguration(this.domain, false);
         DynamicMBeanProcessor.getInstance().updateConfiguration(this.domain);
      } catch (UpdateException var10) {
         throw new ManagementException(var10);
      }

      this.addAccessCallbackClass(AdminServerMBeanProcessor.class.getName());
      if (!ManagementService.getPropertyService(kernelID).isAdminServer()) {
         try {
            BootStrapHelper.getBootStrapStruct();
            if (ProductionModeHelper.isGlobalProductionModeSet()) {
               this.domain.setProductionModeEnabled(ProductionModeHelper.getGlobalProductionMode());
            } else if (this.domain.isProductionModeEnabled()) {
               DescriptorHelper.setDescriptorTreeProductionMode(this.domain.getDescriptor(), true);
            }
         } catch (ConfigurationException var9) {
            if (debug.isDebugEnabled()) {
               debug.debug("Error in configuration: " + var9, var9);
            }

            if (var9 instanceof BootStrapHelper.UnknownServerException) {
               throw new ManagementException(var9.getMessage());
            }

            ConnectMonitorFactory.getConnectMonitor().addConnectListener(this);
         }
      } else if (ProductionModeHelper.isProductionModePropertySet()) {
         this.domain.setProductionModeEnabled(ProductionModeHelper.getProductionModeProperty());
      } else if (this.domain.isProductionModeEnabled()) {
         DescriptorHelper.setDescriptorTreeProductionMode(this.domain.getDescriptor(), true);
      }

      if (!this.situationalConfigManager.isInitialized()) {
         long poll = (long)(this.server.getSitConfigPollingInterval() * 1000);

         try {
            this.situationalConfigManager.initialize(poll);
         } catch (Exception var8) {
            if (debug.isDebugEnabled()) {
               Debug.say("[SitConfig] SituationalConfig disabled");
               var8.printStackTrace();
            }
         }
      }

      try {
         this.situationalConfigManager.findAndLoadSitConfigFiles();
      } catch (ServiceFailureException var6) {
         if (debug.isDebugEnabled()) {
            debug.debug("[SitConfig] Exception in SituationalService ", var6);
         }

         throw new RuntimeException(var6);
      } catch (Exception var7) {
         if (debug.isDebugEnabled()) {
            debug.debug("[SitConfig] SituationalConfig exception during load of initial files", var7);
         }
      }

      if (this.server == null) {
         this.server = this.domain.lookupServer(serverName);
      }

      if (this.server == null) {
         ServerMBean[] s = this.domain.getServers();
         String tmp = "{";

         for(int i = 0; i < s.length; ++i) {
            if (i > 0) {
               tmp = tmp + ",";
            }

            tmp = tmp + s[i].getName();
         }

         tmp = tmp + "}";
         Loggable l = ManagementLogger.logServerNameDoesNotExistLoggable(serverName, tmp);
         l.log();
         throw new ManagementException(l.getMessage());
      }
   }

   public void addAccessCallbackClass(String className) {
      this.accessCallbackList.add(className);
   }

   public AccessCallback[] initializeCallbacks(final DomainMBean domain) {
      AccessCallback[] result = (AccessCallback[])((AccessCallback[])SecurityServiceManager.runAs(kernelID, kernelID, new PrivilegedAction() {
         public Object run() {
            return RuntimeAccessImpl.this._initializeCallbacks(domain);
         }
      }));
      return result;
   }

   private AccessCallback[] _initializeCallbacks(DomainMBean domain) {
      DescriptorImpl desc = (DescriptorImpl)domain.getDescriptor();
      boolean modified = desc.isModified();
      AccessCallback[] result = new AccessCallback[this.accessCallbackList.size()];

      for(int i = 0; i < result.length; ++i) {
         String accessCallbackClassname = (String)this.accessCallbackList.get(i);
         Class clazz = null;

         try {
            clazz = Class.forName(accessCallbackClassname);
            Constructor cons = clazz.getConstructor((Class[])null);
            result[i] = (AccessCallback)cons.newInstance((Object[])null);
            result[i].accessed(domain);
         } catch (Exception var9) {
            throw new RuntimeException("Failure Initializing Access Callbacks", var9);
         }
      }

      if (!modified && desc.isModified()) {
         desc.setModified(false);
      }

      return result;
   }

   public DomainMBean getDomain() {
      return this.domain;
   }

   public ServerMBean getServer() {
      return this.server;
   }

   public String getServerName() {
      return this.server == null ? null : this.server.getName();
   }

   public ServerRuntimeMBean getServerRuntime() {
      return this.serverRuntime;
   }

   public void setServerRuntime(ServerRuntimeMBean serverRuntime) {
      if (this.serverRuntime != null) {
         throw new AssertionError("ServerRuntimeMBean may only be set once.");
      } else {
         this.serverRuntime = serverRuntime;
         ((RuntimeAccessSettable)this.situationalConfigManager).setServerRuntime(serverRuntime);
         ((RuntimeAccessSettable)this.situationalPropManager).setServerRuntime(serverRuntime);
      }
   }

   public boolean isAdminServer() {
      return this.adminHostProperty == null;
   }

   private MSIService getMSIService() {
      if (this.msiService == null) {
         this.msiService = (MSIService)GlobalServiceLocator.getServiceLocator().getService(MSIService.class, new Annotation[0]);
      }

      return this.msiService;
   }

   public boolean isAdminServerAvailable() {
      return this.getMSIService().isAdminServerAvailable();
   }

   public String getDomainName() {
      return this.domain.getName();
   }

   public String getAdminServerName() {
      String adminServerName = this.domain.getAdminServerName();

      try {
         return adminServerName != null ? adminServerName : (ManagementService.getPropertyService(kernelID).isAdminServer() ? ManagementService.getPropertyService(kernelID).getServerName() : BootStrapHelper.getBootStrapStruct().getAdminServerName());
      } catch (ConfigurationException var3) {
         return adminServerName;
      }
   }

   public void onConnect(ConnectEvent event) {
      if (event.getServerName().equals(this.getAdminServerName())) {
         try {
            ManagementService.getPropertyService(kernelID).waitForChannelServiceReady();
            if (this.getMSIService().isAdminRequiredButNotSpecifiedOnBoot()) {
               return;
            }

            BootStrapStruct bss = BootStrapHelper.getBootStrapStruct();
            ConnectMonitorFactory.getConnectMonitor().removeConnectListener(this);
         } catch (ConfigurationException var3) {
            if (debug.isDebugEnabled()) {
               debug.debug("Error in configuration: " + var3, var3);
            }
         }

      }
   }

   public PropertyValueVBean[] getPropertyValues(ConfigurationMBean bean, String[] propertyNames) throws Exception {
      return this.getPartitionProcessor().getPropertyValues(bean, propertyNames);
   }

   public PropertyValueVBean[] getPropertyValues(ConfigurationMBean bean, String[] navigationAttributeNames, SettableBean[] beans, String[] propertyNames) throws Exception {
      return this.getPartitionProcessor().getPropertyValues(bean, navigationAttributeNames, beans, propertyNames);
   }

   public SimplePropertyValueVBean[] getEffectiveValues(ConfigurationMBean bean, String[] navigationAttributeNames, SettableBean[] beans, String[] propertyNames) throws Exception {
      return this.getPartitionProcessor().getEffectiveValues(bean, navigationAttributeNames, beans, propertyNames);
   }

   public SimplePropertyValueVBean[] getEffectiveValues(ConfigurationMBean bean, String[] propertyNames) throws Exception {
      return this.getPartitionProcessor().getEffectiveValues(bean, propertyNames);
   }

   public SimplePropertyValueVBean[] getWorkingValues(ConfigurationMBean bean, String[] propertyNames) throws Exception {
      if (bean instanceof VirtualTargetMBean) {
         WorkingVirtualTargetManager workingVirtualTargetManager = (WorkingVirtualTargetManager)GlobalServiceLocator.getServiceLocator().getService(WorkingVirtualTargetManager.class, new Annotation[0]);
         VirtualTargetMBean workingVT = workingVirtualTargetManager.lookupWorkingVirtualTarget((VirtualTargetMBean)bean);
         return this.getPartitionProcessor().getPropertyValues(workingVT, propertyNames);
      } else {
         throw new IllegalArgumentException("Expected VirtualTargetMBean. Got " + bean.toString());
      }
   }

   private synchronized PartitionProcessor getPartitionProcessor() {
      if (this.partitionProcessor == null) {
         this.partitionProcessor = (PartitionProcessor)GlobalServiceLocator.getServiceLocator().getService(PartitionProcessor.class, new Annotation[0]);
      }

      return this.partitionProcessor;
   }

   private class IOHelperImpl implements DescriptorCache.IOHelper {
      private File file;
      private ArrayList errs = new ArrayList();
      private boolean validate = true;
      private boolean needsTransformation = true;
      private boolean transformed = false;
      private boolean productionModeEnabled = false;

      public IOHelperImpl(File file) {
         this.file = file;
      }

      public InputStream openInputStream() throws IOException {
         InputStream is = new FileInputStream(this.file);
         return is;
      }

      private DescriptorBean readCachedDescriptor(File desFile) throws IOException {
         ObjectInputStream ois = null;

         DescriptorBean var3;
         try {
            ois = new ObjectInputStream(new FileInputStream(desFile));
            var3 = (DescriptorBean)ois.readObject();
         } catch (ClassNotFoundException var12) {
            throw (IOException)(new IOException(var12.getMessage())).initCause(var12);
         } finally {
            if (ois != null) {
               try {
                  ois.close();
               } catch (IOException var11) {
               }
            }

         }

         return var3;
      }

      public Object readCachedBean(File f) throws IOException {
         DescriptorImpl desc = DescriptorImpl.beginConstruction(false, RuntimeAccessImpl.READONLY_DESCRIPTOR_MANAGER_SINGLETON.instance, (DescriptorCreationListener)null, (BeanCreationInterceptor)null);
         DescriptorBean db = null;

         try {
            db = this.readCachedDescriptor(f);
         } finally {
            DescriptorImpl.endConstruction(db);
         }

         return db;
      }

      public Object parseXML(InputStream in) throws IOException, XMLStreamException {
         DescriptorManagerHelperContext ctx = new DescriptorManagerHelperContext();
         ctx.setEditable(false);
         ctx.setValidate(this.validate);
         ctx.setTransform(this.needsTransformation);
         ctx.setErrors(this.errs);
         boolean m = ConfigFileHelper.getProductionModeEnabled() || Boolean.getBoolean("weblogic.ProductionModeEnabled");
         if (m) {
            ctx.setRProductionModeEnabled(m);
            ctx.setEProductionModeEnabled(m);
         }

         Object obj = DescriptorManagerHelper.loadDescriptor(in, ctx).getRootBean();
         this.transformed = ctx.isTransformed();
         return obj;
      }

      protected ArrayList getErrs() {
         return this.errs;
      }

      public boolean useCaching() {
         return false;
      }

      void setValidate(boolean validate) {
         this.validate = validate;
      }

      void setNeedsTransformation(boolean transform) {
         this.needsTransformation = transform;
      }

      boolean isNeedsTransformation() {
         return this.needsTransformation;
      }

      boolean isTransformed() {
         return this.transformed;
      }

      void setProductionModeEnabled(boolean enabled) {
         this.productionModeEnabled = enabled;
      }

      boolean isProductionModeEnabled() {
         return this.productionModeEnabled;
      }
   }

   private static class READONLY_DESCRIPTOR_MANAGER_SINGLETON {
      static DescriptorManager instance = new DescriptorManager();
   }

   public class ParseException extends ManagementException {
      public ParseException(String message) {
         super(message);
      }

      public ParseException(String message, Throwable t) {
         super(message, t);
      }
   }

   public class SchemaValidationException extends ManagementException {
      public SchemaValidationException(String message) {
         super(message);
      }
   }
}
