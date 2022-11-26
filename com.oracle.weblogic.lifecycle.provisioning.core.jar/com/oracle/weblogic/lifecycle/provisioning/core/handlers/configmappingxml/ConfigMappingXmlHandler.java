package com.oracle.weblogic.lifecycle.provisioning.core.handlers.configmappingxml;

import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningEvent;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import com.oracle.weblogic.lifecycle.provisioning.api.TransactionalHandler;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ApplicationServer;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Component;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.DeprovisioningOperation;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Handler;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.InitialProvisioningOperation;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Partition;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationProperty;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationScoped;
import com.oracle.weblogic.lifecycle.provisioning.core.annotations.WebLogicDomainRootDirectory;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Provider;
import org.glassfish.hk2.api.messaging.MessageReceiver;
import org.glassfish.hk2.api.messaging.SubscribeTo;
import org.jvnet.hk2.annotations.Optional;
import org.w3c.dom.Document;

@Component("JRF Cross Component Wiring")
@Handler
@MessageReceiver({ProvisioningEvent.class})
@ProvisioningOperationScoped
public class ConfigMappingXmlHandler extends AbstractConfigMappingXmlHandler implements TransactionalHandler {
   private String domainPath;
   private String partitionConfigDirectory;
   private List configMappingDocuments;

   /** @deprecated */
   @Deprecated
   public ConfigMappingXmlHandler() {
      this((String)null, (Provider)null, (Provider)null);
   }

   @Inject
   public ConfigMappingXmlHandler(@ProvisioningOperationProperty("wlsPartitionName") String partitionName, @WebLogicDomainRootDirectory Provider webLogicDomainRootDirectoryProvider, @ApplicationServer @Optional @Partition Provider partitionFileSystemUnderConstructionProvider) {
      super(partitionName, partitionFileSystemUnderConstructionProvider);
      this.configMappingDocuments = new ArrayList();
      if (webLogicDomainRootDirectoryProvider != null && webLogicDomainRootDirectoryProvider.get() != null) {
         this.domainPath = ((Path)webLogicDomainRootDirectoryProvider.get()).toString();
      }

      assert this.domainPath != null;

      this.partitionConfigDirectory = this.domainPath + File.separator + "config" + File.separator + "partitions" + File.separator + partitionName;
   }

   public void handle(@InitialProvisioningOperation @SubscribeTo @ConfigMappingXml ProvisioningEvent provisioningEvent) throws ProvisioningException {
      String className = ConfigMappingXmlHandler.class.getName();
      String methodName = "handle";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "handle", provisioningEvent);
      }

      if (provisioningEvent != null) {
         Document configMappingXml = provisioningEvent.getDocument();
         if (configMappingXml != null) {
            if (logger != null && logger.isLoggable(Level.FINER)) {
               logger.logp(Level.FINE, className, "handle", "CCW: Location of the config-mapping.xml ", configMappingXml.getDocumentURI());
            }

            this.configMappingDocuments.add(configMappingXml);
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "handle");
      }

   }

   public void deprovision(@DeprovisioningOperation @SubscribeTo @ConfigMappingXml ProvisioningEvent provisioningEvent) throws ProvisioningException {
      String className = ConfigMappingXmlHandler.class.getName();
      String methodName = "deprovision";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "deprovision", provisioningEvent);
      }

      if (provisioningEvent != null) {
         Document configMappingXml = provisioningEvent.getDocument();
         this.deleteServices(configMappingXml);
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "deprovision");
      }

   }

   public void commit() {
      String className = ConfigMappingXmlHandler.class.getName();
      String methodName = "commit";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "commit");
      }

      logger.logp(Level.FINE, className, "commit", "CCW: Total no. of config-mapping.xml files to be processed ", this.configMappingDocuments.size());
      logger.logp(Level.FINE, className, "commit", "CCW: Name of the partition being provisioned ", this.getPartitionName());
      logger.logp(Level.FINE, className, "commit", "CCW: Path to the WebLogic domain ", this.domainPath);
      this.processConfigMapping(this.partitionConfigDirectory, this.configMappingDocuments);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "commit");
      }

   }

   public void rollback() {
      String className = ConfigMappingXmlHandler.class.getName();
      String methodName = "rollback";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "rollback");
      }

      Iterator var4 = this.configMappingDocuments.iterator();

      while(var4.hasNext()) {
         Document configMappingXml = (Document)var4.next();
         this.deleteServices(configMappingXml);
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "rollback");
      }

   }
}
