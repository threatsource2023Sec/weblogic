package weblogic.diagnostics.accessor;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.connector.external.RAUtil;
import weblogic.diagnostics.accessor.runtime.AccessRuntimeMBean;
import weblogic.diagnostics.accessor.runtime.ArchiveRuntimeMBean;
import weblogic.diagnostics.accessor.runtime.DataAccessRuntimeMBean;
import weblogic.diagnostics.accessor.runtime.DataRetirementTaskRuntimeMBean;
import weblogic.diagnostics.archive.DiagnosticArchiveRuntime;
import weblogic.diagnostics.archive.DiagnosticStoreRepository;
import weblogic.diagnostics.archive.EditableDataArchive;
import weblogic.diagnostics.archive.WLDFDataRetirementByAgeTaskImpl;
import weblogic.diagnostics.archive.WLDFDiagnosticDbstoreArchiveRuntime;
import weblogic.diagnostics.archive.WLDFDiagnosticFileArchiveRuntime;
import weblogic.diagnostics.archive.WLDFDiagnosticWlstoreArchiveRuntime;
import weblogic.diagnostics.archive.dbstore.JdbcDataArchive;
import weblogic.diagnostics.archive.filestore.FileDataArchive;
import weblogic.diagnostics.archive.wlstore.PersistentStoreDataArchive;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsLogger;
import weblogic.diagnostics.utils.ArchiveHelper;
import weblogic.diagnostics.utils.JMSAccessorHelper;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.WebServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WLDFAccessRuntimeMBean;
import weblogic.management.runtime.WLDFArchiveRuntimeMBean;
import weblogic.management.runtime.WLDFDataAccessRuntimeMBean;
import weblogic.management.runtime.WLDFRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.servlet.utils.ServletAccessorHelper;

public class WLSAccessorMBeanFactoryImpl implements AccessorMBeanFactory, AccessorConstants {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugDiagnosticAccessor");
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final RuntimeAccess runtimeAccess;

   public String[] getAvailableDiagnosticDataAccessorNames() throws ManagementException {
      AccessorUtils.ensureUserAuthorized();
      HashSet logicalNames = new HashSet();
      logicalNames.add("ServerLog");
      if (runtimeAccess.isAdminServer()) {
         logicalNames.add("DomainLog");
      }

      if (!ArchiveHelper.isFilestoreNeededAndNotAvailable()) {
         logicalNames.add("HarvestedDataArchive");
         logicalNames.add("EventsDataArchive");
      }

      logicalNames.add("HTTPAccessLog");
      logicalNames.add("DataSourceLog");
      Iterator hosts = AccessorUtils.getAvailableVirtualHosts().iterator();

      while(hosts.hasNext()) {
         VirtualHostMBean vh = (VirtualHostMBean)hosts.next();
         logicalNames.add("HTTPAccessLog/" + vh.getName());
      }

      Set webAppLogs = this.getLogicalNamesForAvailableWebAppLogs();
      if (webAppLogs.size() > 0) {
         logicalNames.addAll(webAppLogs);
      }

      Set connectorLogs = RAUtil.getAvailableConnectorLogNames("");
      if (connectorLogs.size() > 0) {
         logicalNames.addAll(connectorLogs);
      }

      Iterator jmsServers = AccessorUtils.getAvailableJMSServers().iterator();

      while(jmsServers.hasNext()) {
         String jmsServer = (String)jmsServers.next();
         logicalNames.add(JMSAccessorHelper.getLogicalNameForJMSMessageLog(jmsServer));
      }

      Iterator safAgents = AccessorUtils.getAvailableSAFAgents().iterator();

      while(safAgents.hasNext()) {
         String safAgent = (String)safAgents.next();
         logicalNames.add(JMSAccessorHelper.getLogicalNameForJMSSAFMessageLog(safAgent));
      }

      if (DiagnosticStoreRepository.getInstance().isValid()) {
         Iterator customArchives = AccessorUtils.getAvailableCustomArchives().iterator();

         while(customArchives.hasNext()) {
            WLDFArchiveRuntimeMBean customArchive = (WLDFArchiveRuntimeMBean)customArchives.next();
            logicalNames.add(customArchive.getName());
         }
      }

      String[] results = new String[logicalNames.size()];
      logicalNames.toArray(results);
      return results;
   }

   private Set getLogicalNamesForAvailableWebAppLogs() {
      HashSet logicalNames = new HashSet();
      Set webServers = AccessorUtils.getAvailableVirtualHosts();
      webServers.add(runtimeAccess.getServer().getWebServer());
      Iterator iter = webServers.iterator();

      while(iter.hasNext()) {
         WebServerMBean webServer = (WebServerMBean)iter.next();
         Set s = ServletAccessorHelper.getLogicalNamesForWebApps(webServer);
         if (!s.isEmpty()) {
            logicalNames.addAll(s);
         }
      }

      return logicalNames;
   }

   public AccessRuntimeMBean createDiagnosticAccessRuntime(AccessorConfigurationProvider confProvider, AccessorSecurityProvider securityProvider, RuntimeMBean parent) throws ManagementException {
      return new DiagnosticAccessRuntime(this, confProvider, securityProvider, parent);
   }

   public DataAccessRuntimeMBean createDiagnosticDataAccessRuntime(final String logicalName, final ColumnInfo[] columns, final AccessRuntimeMBean parent) throws ManagementException {
      try {
         WLDFDataAccessRuntimeMBean ddar = (WLDFDataAccessRuntimeMBean)SecurityServiceManager.runAs(KERNEL_ID, SecurityServiceManager.getCurrentSubject(KERNEL_ID), new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return WLSAccessorMBeanFactoryImpl.this.createDataAccessRuntime(logicalName, columns, (WLDFAccessRuntimeMBean)parent);
            }
         });
         return ddar;
      } catch (PrivilegedActionException var5) {
         throw new ManagementException((Throwable)(var5.getCause() == null ? var5 : var5.getCause()));
      }
   }

   private WLDFDataAccessRuntimeMBean createDataAccessRuntime(String logicalName, ColumnInfo[] columns, WLDFAccessRuntimeMBean parent) throws ManagementException {
      try {
         DataAccessRuntime.DiagnosticDataAccessServiceStruct ddas = DataAccessRuntime.createDiagnosticDataAccessService(logicalName, columns);
         DiagnosticDataAccessService service = ddas.getDiagnosticDataAccessService();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Creating DiagnosticDataAccessRuntime with Name=" + logicalName);
         }

         DiagnosticDataAccessRuntime ddar = new DiagnosticDataAccessRuntime(logicalName, parent, service);
         ddar.setDataArchiveParameters(ddas.getCreateParams());
         return ddar;
      } catch (Exception var7) {
         Loggable l = DiagnosticsLogger.logErrorCreatingDiagnosticDataRuntimeLoggable(logicalName, var7);
         l.log();
         throw new ManagementException(l.getMessageBody());
      }
   }

   public DiagnosticDataAccessService createDiagnosticDataAccessService(String logicalName, String logType, ColumnInfo[] columns, Map params) throws UnknownLogTypeException, DataAccessServiceCreateException {
      if (!logType.equals("CUSTOM")) {
         columns = null;
      }

      DiagnosticDataAccessService service = DiagnosticDataAccessServiceFactory.createDiagnosticDataAccessService(logicalName, logType, columns, params);
      return service;
   }

   public ArchiveRuntimeMBean createDiagnosticArchiveRuntime(DiagnosticDataAccessService archive) throws ManagementException {
      WLDFRuntimeMBean wldfRuntime = runtimeAccess.getServerRuntime().getWLDFRuntime();
      WLDFArchiveRuntimeMBean archiveRuntime = null;
      if (archive instanceof FileDataArchive) {
         archiveRuntime = new WLDFDiagnosticFileArchiveRuntime((FileDataArchive)archive, wldfRuntime);
      } else if (archive instanceof JdbcDataArchive) {
         archiveRuntime = new WLDFDiagnosticDbstoreArchiveRuntime((JdbcDataArchive)archive, wldfRuntime);
      } else {
         if (!(archive instanceof PersistentStoreDataArchive)) {
            throw new ManagementException("Unknown archive type: " + archive);
         }

         archiveRuntime = new WLDFDiagnosticWlstoreArchiveRuntime((PersistentStoreDataArchive)archive, wldfRuntime);
      }

      wldfRuntime.addWLDFArchiveRuntime((WLDFArchiveRuntimeMBean)archiveRuntime);
      return (ArchiveRuntimeMBean)archiveRuntime;
   }

   public void destroyDiagnosticArchiveRuntime(ArchiveRuntimeMBean archiveRuntime) throws ManagementException {
      if (archiveRuntime instanceof WLDFArchiveRuntimeMBean) {
         WLDFRuntimeMBean wldfRuntime = runtimeAccess.getServerRuntime().getWLDFRuntime();
         wldfRuntime.removeWLDFArchiveRuntime((WLDFArchiveRuntimeMBean)archiveRuntime);
      }

      DiagnosticArchiveRuntime rmb = (DiagnosticArchiveRuntime)archiveRuntime;
      rmb.unregister();
   }

   public DataRetirementTaskRuntimeMBean createRetirementByAgeTask(DiagnosticDataAccessService archive, long endTime) throws ManagementException {
      DataRetirementTaskRuntimeMBean task = null;
      if (archive instanceof EditableDataArchive) {
         task = new WLDFDataRetirementByAgeTaskImpl((EditableDataArchive)archive, endTime);
         return task;
      } else {
         throw new ManagementException("Can not create retirement task for non-editable archive " + archive.getName());
      }
   }

   static {
      runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
   }
}
