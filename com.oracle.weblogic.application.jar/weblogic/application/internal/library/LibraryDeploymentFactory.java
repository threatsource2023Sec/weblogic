package weblogic.application.internal.library;

import java.io.File;
import java.util.jar.Attributes;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.Deployment;
import weblogic.application.DeploymentFactory;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.library.LibraryConstants;
import weblogic.application.library.LibraryData;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryDeploymentException;
import weblogic.application.library.LoggableLibraryProcessingException;
import weblogic.application.utils.LibraryLoggingUtils;
import weblogic.application.utils.ManagementUtils;
import weblogic.application.utils.PathUtils;
import weblogic.management.DeploymentException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.LibraryMBean;
import weblogic.management.configuration.SystemResourceMBean;

public class LibraryDeploymentFactory implements DeploymentFactory {
   public boolean isSupportedBasic(BasicDeploymentMBean mbean, File file) {
      return mbean instanceof LibraryMBean;
   }

   public boolean isSupportedAdvanced(BasicDeploymentMBean mbean, File file) {
      return this.isSupportedBasic(mbean, file);
   }

   public Deployment createDeployment(AppDeploymentMBean b, File f) throws LibraryDeploymentException {
      if (!(b instanceof LibraryMBean)) {
         throw new LibraryDeploymentException("Input must be an instance of LibraryMBean");
      } else {
         LibraryMBean mbean = (LibraryMBean)b;
         LibraryData data = this.getLibData(mbean, f);
         String tmpPath = PathUtils.generateTempPath(ManagementUtils.getServerName(), data.getName(), data.getSpecificationVersion() + data.getImplementationVersion());
         String appSourceDir = null;
         if (mbean.isCacheInAppDirectory()) {
            appSourceDir = (new File(mbean.getSourcePath())).getParent();
         }

         File extractDir = PathUtils.getAppTempDir(mbean.getName(), tmpPath, mbean.isInternalApp(), appSourceDir);
         LibraryDefinition def = null;

         try {
            ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
            def = LibraryLoggingUtils.getLibraryDefinition(data, extractDir, afm.getLibraryFactories());
         } catch (LoggableLibraryProcessingException var10) {
            throw new LibraryDeploymentException(var10.getLoggable().getMessage());
         }

         return this.createDeployment(def, mbean);
      }
   }

   private Deployment createDeployment(LibraryDefinition def, LibraryMBean b) {
      return new LibraryDeployment(def, b);
   }

   public Deployment createDeployment(SystemResourceMBean mbean, File f) {
      return null;
   }

   private LibraryData getLibData(LibraryMBean mbean, File f) throws LibraryDeploymentException {
      LibraryData mbeanData = null;

      try {
         mbeanData = LibraryLoggingUtils.initLibraryData(mbean, f);
      } catch (LoggableLibraryProcessingException var9) {
         throw new LibraryDeploymentException(var9.getLoggable().getMessage());
      }

      LibraryData manifestData = null;
      Attributes attributes = mbeanData.getAttributes();

      try {
         manifestData = LibraryLoggingUtils.initLibraryData(f, attributes);
      } catch (LoggableLibraryProcessingException var8) {
         throw new LibraryDeploymentException(var8.getLoggable().getMessage());
      }

      manifestData = manifestData.importData(mbeanData);

      try {
         if (mbean.getConfiguredApplicationIdentifier() != null) {
            LibraryLoggingUtils.handleLibraryInfoMismatch(manifestData, mbeanData, LibraryConstants.LIBRARY_NAME);
         } else {
            LibraryLoggingUtils.handleLibraryInfoMismatch(manifestData, mbeanData);
         }

         return mbeanData;
      } catch (LoggableLibraryProcessingException var7) {
         throw new LibraryDeploymentException(var7.getLoggable().getMessage());
      }
   }

   public Deployment createDeployment(AppDeploymentMBean mbean, ApplicationArchive app) throws DeploymentException {
      return null;
   }

   public Deployment createDeployment(SystemResourceMBean mbean, ApplicationArchive f) throws DeploymentException {
      return null;
   }
}
