package weblogic.management.configuration;

import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.utils.PlatformConstants;

public interface WLDFServerDiagnosticMBean extends ConfigurationMBean {
   String DEFAULT_DUMP_DIR = "logs" + PlatformConstants.FILE_SEP + "diagnostic_dumps";
   String DEFAULT_STORE_DIR = "data/store/diagnostics";
   String FILE_STORE_ARCHIVE_TYPE = "FileStoreArchive";
   String JDBC_ARCHIVE_TYPE = "JDBCArchive";
   long DEFAULT_EVENTS_IMAGE_CAPTURE_INTERVAL = 60000L;
   String OFF_VOLUME = "Off";
   String LOW_VOLUME = "Low";
   String MEDIUM_VOLUME = "Medium";
   String HIGH_VOLUME = "High";

   String getImageDir();

   void setImageDir(String var1);

   int getImageTimeout();

   void setImageTimeout(int var1);

   long getEventsImageCaptureInterval();

   void setEventsImageCaptureInterval(long var1);

   String getDiagnosticStoreDir();

   void setDiagnosticStoreDir(String var1);

   boolean isDiagnosticStoreFileLockingEnabled();

   void setDiagnosticStoreFileLockingEnabled(boolean var1);

   int getDiagnosticStoreMinWindowBufferSize();

   void setDiagnosticStoreMinWindowBufferSize(int var1);

   int getDiagnosticStoreMaxWindowBufferSize();

   void setDiagnosticStoreMaxWindowBufferSize(int var1);

   int getDiagnosticStoreIoBufferSize();

   void setDiagnosticStoreIoBufferSize(int var1);

   long getDiagnosticStoreMaxFileSize();

   void setDiagnosticStoreMaxFileSize(long var1);

   int getDiagnosticStoreBlockSize();

   void setDiagnosticStoreBlockSize(int var1);

   String getDiagnosticDataArchiveType();

   void setDiagnosticDataArchiveType(String var1);

   JDBCSystemResourceMBean getDiagnosticJDBCResource();

   void setDiagnosticJDBCResource(JDBCSystemResourceMBean var1);

   String getDiagnosticJDBCSchemaName();

   void setDiagnosticJDBCSchemaName(String var1);

   boolean isSynchronousEventPersistenceEnabled();

   void setSynchronousEventPersistenceEnabled(boolean var1);

   long getEventPersistenceInterval();

   void setEventPersistenceInterval(long var1);

   /** @deprecated */
   @Deprecated
   boolean isDiagnosticContextEnabled();

   /** @deprecated */
   @Deprecated
   void setDiagnosticContextEnabled(boolean var1);

   boolean isDataRetirementTestModeEnabled();

   void setDataRetirementTestModeEnabled(boolean var1);

   boolean isDataRetirementEnabled();

   void setDataRetirementEnabled(boolean var1);

   int getPreferredStoreSizeLimit();

   void setPreferredStoreSizeLimit(int var1);

   int getStoreSizeCheckPeriod();

   void setStoreSizeCheckPeriod(int var1);

   WLDFDataRetirementMBean[] getWLDFDataRetirements();

   WLDFDataRetirementMBean lookupWLDFDataRetirement(String var1);

   WLDFDataRetirementByAgeMBean[] getWLDFDataRetirementByAges();

   WLDFDataRetirementByAgeMBean createWLDFDataRetirementByAge(String var1);

   void destroyWLDFDataRetirementByAge(WLDFDataRetirementByAgeMBean var1);

   WLDFDataRetirementByAgeMBean lookupWLDFDataRetirementByAge(String var1);

   String getWLDFDiagnosticVolume();

   void setWLDFDiagnosticVolume(String var1);

   String getWLDFBuiltinSystemResourceType();

   void setWLDFBuiltinSystemResourceType(String var1);

   WLDFResourceBean getWLDFBuiltinSystemResourceDescriptorBean();

   String getDiagnosticDumpsDir();

   void setDiagnosticDumpsDir(String var1);

   int getMaxHeapDumpCount();

   void setMaxHeapDumpCount(int var1);

   int getMaxThreadDumpCount();

   void setMaxThreadDumpCount(int var1);
}
