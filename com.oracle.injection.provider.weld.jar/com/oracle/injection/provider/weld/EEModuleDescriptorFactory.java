package com.oracle.injection.provider.weld;

import com.oracle.injection.InjectionArchive;
import org.jboss.weld.bootstrap.spi.EEModuleDescriptor;
import org.jboss.weld.bootstrap.spi.EEModuleDescriptor.ModuleType;
import org.jboss.weld.bootstrap.spi.helpers.EEModuleDescriptorImpl;

public class EEModuleDescriptorFactory {
   public EEModuleDescriptor createEEModuleDescriptor(InjectionArchive moduleInjectionArchive) {
      EEModuleDescriptor.ModuleType moduleType;
      switch (moduleInjectionArchive.getArchiveType()) {
         case APP_CLIENT_JAR:
            moduleType = ModuleType.APPLICATION_CLIENT;
            break;
         case EJB_JAR:
            moduleType = ModuleType.EJB_JAR;
            break;
         case JAR:
            moduleType = ModuleType.EAR;
            break;
         case RAR:
            moduleType = ModuleType.CONNECTOR;
            break;
         case WAR:
            moduleType = ModuleType.WEB;
            break;
         default:
            moduleType = null;
      }

      return new EEModuleDescriptorImpl(moduleInjectionArchive.getArchiveName(), moduleType);
   }
}
