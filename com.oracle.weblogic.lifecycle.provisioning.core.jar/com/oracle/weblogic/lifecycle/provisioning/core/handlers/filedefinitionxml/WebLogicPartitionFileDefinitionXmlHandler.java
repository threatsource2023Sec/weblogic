package com.oracle.weblogic.lifecycle.provisioning.core.handlers.filedefinitionxml;

import com.oracle.cie.external.FileDefinitionProcessor;
import com.oracle.cie.external.FileDefinitionProcessorException;
import com.oracle.cie.external.FileDefinitionProcessorFactory;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningContext;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningEvent;
import com.oracle.weblogic.lifecycle.provisioning.api.ProvisioningException;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ApplicationServer;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Component;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.DeprovisioningOperation;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Handler;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.InitialProvisioningOperation;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.Partition;
import com.oracle.weblogic.lifecycle.provisioning.api.annotations.ProvisioningOperationScoped;
import com.oracle.weblogic.lifecycle.provisioning.core.ConfigurableAttributeValueProvider;
import com.oracle.weblogic.lifecycle.provisioning.core.annotations.MiddlewareHomeDirectory;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Provider;
import org.glassfish.hk2.api.messaging.MessageReceiver;
import org.glassfish.hk2.api.messaging.SubscribeTo;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import org.w3c.dom.Document;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ServerRuntimeMBean;

@Component("WebLogic")
@Handler
@MessageReceiver({ProvisioningEvent.class})
@ProvisioningOperationScoped
@Service
public class WebLogicPartitionFileDefinitionXmlHandler {
   private final Provider partitionFileSystemUnderConstructionProvider;
   private FileDefinitionProcessor fileDefinitionProcessor;
   private final Path middlewareHome;
   private final ProvisioningContext provisioningContext;
   private final ConfigurableAttributeValueProvider configurableAttributeValueProvider;

   /** @deprecated */
   @Deprecated
   public WebLogicPartitionFileDefinitionXmlHandler() {
      this((ProvisioningContext)null, (Provider)null, (Path)((Path)null), (ConfigurableAttributeValueProvider)null);
   }

   /** @deprecated */
   @Deprecated
   public WebLogicPartitionFileDefinitionXmlHandler(@ApplicationServer @Optional @Partition Provider partitionFileSystemUnderConstructionProvider, @Optional RuntimeAccess runtimeAccess) {
      this((ProvisioningContext)null, partitionFileSystemUnderConstructionProvider, (RuntimeAccess)runtimeAccess, (ConfigurableAttributeValueProvider)null);
   }

   /** @deprecated */
   @Deprecated
   public WebLogicPartitionFileDefinitionXmlHandler(@Optional ProvisioningContext provisioningContext, @ApplicationServer @Optional @Partition Provider partitionFileSystemUnderConstructionProvider, @Optional RuntimeAccess runtimeAccess, @Optional ConfigurableAttributeValueProvider configurableAttributeValueProvider) {
      this.provisioningContext = provisioningContext;
      this.partitionFileSystemUnderConstructionProvider = partitionFileSystemUnderConstructionProvider;
      this.configurableAttributeValueProvider = configurableAttributeValueProvider;
      if (runtimeAccess == null) {
         this.middlewareHome = null;
      } else {
         ServerRuntimeMBean serverRuntimeMBean = runtimeAccess.getServerRuntime();

         assert serverRuntimeMBean != null;

         String middlewareHomeDirectoryAsString = serverRuntimeMBean.getMiddlewareHome();
         if (middlewareHomeDirectoryAsString == null) {
            this.middlewareHome = null;
         } else {
            this.middlewareHome = Paths.get(middlewareHomeDirectoryAsString).toAbsolutePath();
         }
      }

      try {
         this.fileDefinitionProcessor = FileDefinitionProcessorFactory.getInstance().getFileDefinitionProcessor();
      } catch (FileDefinitionProcessorException var7) {
         throw new IllegalStateException(var7);
      }
   }

   @Inject
   public WebLogicPartitionFileDefinitionXmlHandler(@Optional ProvisioningContext provisioningContext, @ApplicationServer @Optional @Partition Provider partitionFileSystemUnderConstructionProvider, @MiddlewareHomeDirectory @Optional Path middlewareHome, @Optional ConfigurableAttributeValueProvider configurableAttributeValueProvider) {
      this.provisioningContext = provisioningContext;
      this.partitionFileSystemUnderConstructionProvider = partitionFileSystemUnderConstructionProvider;
      this.middlewareHome = middlewareHome;
      this.configurableAttributeValueProvider = configurableAttributeValueProvider;

      try {
         this.fileDefinitionProcessor = FileDefinitionProcessorFactory.getInstance().getFileDefinitionProcessor();
      } catch (FileDefinitionProcessorException var6) {
         throw new IllegalStateException(var6);
      }
   }

   public void handle(@InitialProvisioningOperation @SubscribeTo @WebLogicPartitionFileDefinitionXml ProvisioningEvent event) throws ProvisioningException {
      String className = WebLogicPartitionFileDefinitionXmlHandler.class.getName();
      String methodName = "handle";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "handle", event);
      }

      if (event != null) {
         Document fileDefinitionXml = event.getDocument();
         if (fileDefinitionXml != null) {
            if (logger != null && logger.isLoggable(Level.FINE)) {
               logger.logp(Level.FINE, className, "handle", "Processing {0}", fileDefinitionXml.getDocumentURI());
            }

            try {
               FileDefinitionProcessor processor = this.fileDefinitionProcessor;
               if (processor != null) {
                  java.util.Map keyValuePairs = new Map(this.provisioningContext, this.configurableAttributeValueProvider);
                  Path partitionFileSystemUnderConstruction;
                  String configDir;
                  if (this.partitionFileSystemUnderConstructionProvider != null) {
                     if (logger != null && logger.isLoggable(Level.FINER)) {
                        logger.logp(Level.FINER, className, "handle", "Acquiring TemporaryPartitionFilesystem instance...");
                     }

                     partitionFileSystemUnderConstruction = (Path)this.partitionFileSystemUnderConstructionProvider.get();

                     assert partitionFileSystemUnderConstruction != null;

                     if (logger != null && logger.isLoggable(Level.FINER)) {
                        logger.logp(Level.FINER, className, "handle", "...Acquired TemporaryPartitionFilesystem instance {0}", partitionFileSystemUnderConstruction);
                     }

                     configDir = partitionFileSystemUnderConstruction.toString();

                     assert configDir != null;

                     keyValuePairs.put("DOMAIN_HOME", configDir);
                     keyValuePairs.put("PARTITION_HOME", configDir);
                     if (logger != null && logger.isLoggable(Level.FINER)) {
                        Iterable entries = keyValuePairs.entrySet();

                        assert entries != null;

                        Iterator var11 = entries.iterator();

                        while(var11.hasNext()) {
                           java.util.Map.Entry entry = (java.util.Map.Entry)var11.next();

                           assert entry != null;

                           logger.logp(Level.FINER, className, "handle", "{0}={1}", new Object[]{entry.getKey(), entry.getValue()});
                        }
                     }
                  }

                  partitionFileSystemUnderConstruction = this.getMiddlewareHome();
                  if (partitionFileSystemUnderConstruction != null) {
                     configDir = partitionFileSystemUnderConstruction.toString();

                     assert configDir != null : "middlewareHomePath.toString() == null";

                     keyValuePairs.put("MIDDLEWARE_HOME", configDir);
                     keyValuePairs.put("MW_HOME", configDir);
                     keyValuePairs.put("ORACLE_COMMON_HOME", configDir + File.separator + "oracle_common");
                     keyValuePairs.put("ORACLE_HOME", configDir);
                  }

                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, className, "handle", "Invoking FileDefinitionProcessor.process(), passing {0} and {1}", new Object[]{fileDefinitionXml, keyValuePairs});
                  }

                  processor.process(fileDefinitionXml, keyValuePairs);
                  if (logger != null && logger.isLoggable(Level.FINE)) {
                     logger.logp(Level.FINE, className, "handle", "Invoked FileDefinitionProcessor.process() successfully.");
                     logger.logp(Level.FINE, className, "handle", "Completed processing {0}.", fileDefinitionXml.getDocumentURI());
                  }
               }
            } catch (FileDefinitionProcessorException var13) {
               throw new ProvisioningException("Exception encountered while processing " + fileDefinitionXml.getDocumentURI(), var13);
            }
         }
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "handle");
      }

   }

   public void deprovision(@DeprovisioningOperation @SubscribeTo @WebLogicPartitionFileDefinitionXml ProvisioningEvent event) throws ProvisioningException {
      String className = WebLogicPartitionFileDefinitionXmlHandler.class.getName();
      String methodName = "deprovision";
      Logger logger = Logger.getLogger(className);
      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.entering(className, "deprovision", event);
      }

      if (logger != null && logger.isLoggable(Level.FINER)) {
         logger.exiting(className, "deprovision");
      }

   }

   private final Path getMiddlewareHome() {
      return this.middlewareHome;
   }

   static final class Map extends HashMap {
      private static final long serialVersionUID = 1L;
      private final String currentProvisioningComponentName;
      private final ConfigurableAttributeValueProvider provider;

      Map(ProvisioningContext provisioningContext, ConfigurableAttributeValueProvider provider) {
         this(provisioningContext == null ? null : provisioningContext.getCurrentProvisioningComponentName(), provider);
      }

      Map(String currentProvisioningComponentName, ConfigurableAttributeValueProvider provider) {
         this.currentProvisioningComponentName = currentProvisioningComponentName;
         this.provider = provider;
      }

      public final String get(Object key) {
         Collection keySet = this.keySet();
         String returnValue;
         if (keySet != null && keySet.contains(key)) {
            returnValue = (String)super.get(key);
         } else if (key instanceof String) {
            ConfigurableAttributeValueProvider provider = this.provider;
            if (provider == null) {
               returnValue = null;
            } else {
               returnValue = provider.getConfigurableAttributeValue(this.currentProvisioningComponentName, (String)key);
            }
         } else {
            returnValue = null;
         }

         return returnValue;
      }
   }
}
