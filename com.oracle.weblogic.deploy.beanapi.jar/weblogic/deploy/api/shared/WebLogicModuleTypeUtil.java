package weblogic.deploy.api.shared;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.ConfigHelperLowLevel;
import weblogic.deploy.api.spi.config.DescriptorSupport;
import weblogic.deploy.api.spi.config.DescriptorSupportManagerInterface;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.application.WarDetector;
import weblogic.utils.jars.VirtualJarFile;

public class WebLogicModuleTypeUtil {
   private static DescriptorSupportManagerInterface descriptorSupportManager = (DescriptorSupportManagerInterface)LocatorUtilities.getService(DescriptorSupportManagerInterface.class);

   public static ModuleType getFileModuleType(File path) {
      if (path == null) {
         return null;
      } else if (path.getName().endsWith(".xml")) {
         return getWebLogicModuleType(path.getPath());
      } else {
         VirtualJarFile j = null;

         ModuleType var4;
         try {
            ModuleTypeManager mtm;
            try {
               ModuleTypeManagerFactory mtmf = (ModuleTypeManagerFactory)GlobalServiceLocator.getServiceLocator().getService(ModuleTypeManagerFactory.class, new Annotation[0]);
               mtm = mtmf.create(path);
               if (mtm.isSplitDirectory()) {
                  var4 = ModuleType.EAR;
                  return var4;
               }

               j = mtm.createVirtualJarFile();
               var4 = getStandardModuleType(path.toString(), j);
            } catch (IOException var15) {
               SPIDeployerLogger.logAppReadError(path.toString(), var15.toString(), var15);
               mtm = null;
               return mtm;
            }
         } finally {
            if (j != null) {
               try {
                  j.close();
               } catch (IOException var14) {
               }
            }

         }

         return var4;
      }
   }

   private static ModuleType getWebLogicModuleType(String uri) {
      if (uri.endsWith("-jms.xml")) {
         return WebLogicModuleType.JMS;
      } else if (uri.endsWith("-jdbc.xml")) {
         return WebLogicModuleType.JDBC;
      } else if (uri.endsWith("-interception.xml")) {
         return WebLogicModuleType.INTERCEPT;
      } else if (uri.endsWith("-coherence.xml")) {
         return WebLogicModuleType.COHERENCE_CLUSTER;
      } else if (uri.endsWith("coherence-application.xml")) {
         return WebLogicModuleType.GAR;
      } else {
         String np = uri.replace('\\', '/');
         DescriptorSupport[] dss = descriptorSupportManager.getForBaseURI(np);
         if (dss.length > 0) {
            return dss[0].getModuleType();
         } else if (uri.endsWith("weblogic-diagnostics.xml")) {
            return WebLogicModuleType.WLDF;
         } else if (uri.endsWith("webservices.xml")) {
            return WebLogicModuleType.WSEE;
         } else {
            return uri.endsWith("web-services.xml") ? WebLogicModuleType.WSEE : WebLogicModuleType.CONFIG;
         }
      }
   }

   public static ModuleType getStandardModuleType(String uri, VirtualJarFile j) {
      DescriptorSupportManagerInterface var10001 = descriptorSupportManager;
      if (j.getEntry("META-INF/application.xml") == null && !j.getName().endsWith(".ear")) {
         var10001 = descriptorSupportManager;
         if (j.getEntry("META-INF/ejb-jar.xml") != null) {
            return ModuleType.EJB;
         } else {
            var10001 = descriptorSupportManager;
            if (j.getEntry("WEB-INF/web.xml") != null) {
               return ModuleType.WAR;
            } else {
               var10001 = descriptorSupportManager;
               if (j.getEntry("META-INF/ra.xml") != null) {
                  return ModuleType.RAR;
               } else {
                  var10001 = descriptorSupportManager;
                  if (j.getEntry("META-INF/weblogic-ra.xml") != null) {
                     return ModuleType.RAR;
                  } else {
                     var10001 = descriptorSupportManager;
                     if (j.getEntry("META-INF/application-client.xml") != null) {
                        return ModuleType.CAR;
                     } else {
                        var10001 = descriptorSupportManager;
                        if (j.getEntry("META-INF/weblogic-application-client.xml") != null) {
                           return ModuleType.CAR;
                        } else if (WarDetector.instance.suffixed(uri)) {
                           return ModuleType.WAR;
                        } else if (j.getEntry("META-INF/coherence-application.xml") != null) {
                           return WebLogicModuleType.GAR;
                        } else {
                           J2EEArchiveService jas = (J2EEArchiveService)GlobalServiceLocator.getServiceLocator().getService(J2EEArchiveService.class, new Annotation[0]);

                           try {
                              if (jas.isEJB(j)) {
                                 return ModuleType.EJB;
                              }
                           } catch (IOException var5) {
                           }

                           try {
                              File f = new File(uri);
                              if (f.exists() && jas.isRar(f)) {
                                 return ModuleType.RAR;
                              }
                           } catch (Exception var4) {
                           }

                           return j.getEntry("META-INF/sca-contribution.xml") != null ? WebLogicModuleType.SCA_COMPOSITE : null;
                        }
                     }
                  }
               }
            }
         }
      } else {
         return ModuleType.EAR;
      }
   }

   public static ModuleType getFileModuleType(String uri, VirtualJarFile vjf) {
      if (uri == null && vjf == null) {
         return null;
      } else if (uri != null && uri.endsWith(".xml")) {
         return getWebLogicModuleType(uri);
      } else {
         return vjf != null ? getStandardModuleType(uri, vjf) : null;
      }
   }

   public static String getFileModuleTypeAsString(File path) {
      ConfigHelperLowLevel.checkParam("File", path);
      ModuleType mt = getFileModuleType(path);
      return mt == null ? null : mt.toString();
   }

   public static String getDDUri(int i) {
      ModuleType mt = WebLogicModuleType.getModuleType(i);
      DescriptorSupport[] descriptorSupport = descriptorSupportManager.getForModuleType(mt);
      return mt != null && descriptorSupport.length > 0 ? descriptorSupport[0].getBaseURI() : null;
   }

   public static String[] getWLSDDUri(int v) {
      ModuleType mt = WebLogicModuleType.getModuleType(v);
      if (mt == null) {
         return new String[0];
      } else {
         DescriptorSupport[] dss = descriptorSupportManager.getForModuleType(mt);
         String[] uris = new String[dss.length];

         for(int i = 0; i < dss.length; ++i) {
            uris[i] = dss[i].getConfigURI();
         }

         return uris;
      }
   }
}
