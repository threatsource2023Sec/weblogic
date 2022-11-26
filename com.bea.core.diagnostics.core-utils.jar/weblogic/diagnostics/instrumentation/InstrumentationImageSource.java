package weblogic.diagnostics.instrumentation;

import java.io.OutputStream;
import java.security.AccessController;
import java.util.Iterator;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.accessor.ColumnInfo;
import weblogic.diagnostics.accessor.DataRecord;
import weblogic.diagnostics.accessor.DiagnosticAccessRuntime;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.diagnostics.image.PartitionAwareImageSource;
import weblogic.diagnostics.image.descriptor.ColumnDataBean;
import weblogic.diagnostics.image.descriptor.InstrumentationEventBean;
import weblogic.diagnostics.image.descriptor.InstrumentationImageSourceBean;
import weblogic.diagnostics.type.UnexpectedExceptionHandler;
import weblogic.invocation.PartitionTable;
import weblogic.invocation.PartitionTableEntry;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.WLDFServerDiagnosticMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.WLDFDataAccessRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

class InstrumentationImageSource implements PartitionAwareImageSource {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticImage");
   private boolean timeoutRequested;
   private String[] columnNames;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void createDiagnosticImage(String partitionName, OutputStream out) throws ImageSourceCreationException {
      this.createDiagnosticImageInternal(partitionName, out);
   }

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      this.createDiagnosticImageInternal((String)null, out);
   }

   private void createDiagnosticImageInternal(String partitionName, OutputStream out) throws ImageSourceCreationException {
      String partitionId = null;
      if (partitionName != null) {
         PartitionTable table = PartitionTable.getInstance();
         if (table != null) {
            PartitionTableEntry entry = PartitionTable.getInstance().lookupByName(partitionName);
            if (entry != null) {
               partitionId = entry.getPartitionID();
            }
         }

         if (partitionId == null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Instrumentation Image source not captured, unable to determine partitionId for " + partitionName);
            }

            return;
         }
      }

      DescriptorManager dm = new DescriptorManager();
      Descriptor desc = dm.createDescriptorRoot(InstrumentationImageSourceBean.class);
      InstrumentationImageSourceBean root = (InstrumentationImageSourceBean)desc.getRootBean();
      this.writeRecentEvents(root, partitionId);

      try {
         dm.writeDescriptorBeanAsXML((DescriptorBean)root, out);
      } catch (Exception var8) {
         throw new ImageSourceCreationException(var8);
      }
   }

   public void timeoutImageCreation() {
      this.timeoutRequested = true;
   }

   private String[] getColumnNames(WLDFDataAccessRuntimeMBean archive) throws Exception {
      if (this.columnNames == null) {
         ColumnInfo[] columns = archive.getColumns();
         this.columnNames = new String[columns.length];

         for(int i = 0; i < columns.length; ++i) {
            this.columnNames[i] = columns[i].getColumnName();
         }
      }

      return this.columnNames;
   }

   private void writeRecentEvents(InstrumentationImageSourceBean root, String partitionId) {
      try {
         this.timeoutRequested = false;
         ServerMBean serverConfig = ManagementService.getRuntimeAccess(kernelId).getServer();
         WLDFServerDiagnosticMBean wldfConfig = serverConfig.getServerDiagnosticConfig();
         long interval = wldfConfig.getEventsImageCaptureInterval();
         if (interval > 0L) {
            DiagnosticAccessRuntime accessor = DiagnosticAccessRuntime.getInstance();
            WLDFDataAccessRuntimeMBean archive = accessor.lookupWLDFDataAccessRuntime("EventsDataArchive");
            long endTimeStamp = System.currentTimeMillis();
            long beginTimeStamp = endTimeStamp - interval;
            String[] columnNames = this.getColumnNames(archive);
            if (partitionId != null && columnNames.length <= 22) {
               return;
            }

            Iterator it = archive.retrieveDataRecords(beginTimeStamp, endTimeStamp, (String)null);

            while(true) {
               DataRecord record;
               String eventPartitionId;
               do {
                  if (!it.hasNext() || this.timeoutRequested) {
                     return;
                  }

                  record = (DataRecord)it.next();
                  if (partitionId == null) {
                     break;
                  }

                  eventPartitionId = (String)record.get(22);
               } while(eventPartitionId == null || !partitionId.equals(eventPartitionId));

               InstrumentationEventBean event = root.createInstrumentationEvent();

               for(int i = 0; i < columnNames.length; ++i) {
                  String colName = columnNames[i];
                  ColumnDataBean col = event.createColumnData();
                  col.setName(colName);
                  Object value = record.get(i);
                  if (value != null) {
                     col.setValue(value.toString());
                  }
               }
            }
         }
      } catch (Exception var21) {
         UnexpectedExceptionHandler.handle("Error in InstrumentationImageSource.", var21);
      }

   }
}
