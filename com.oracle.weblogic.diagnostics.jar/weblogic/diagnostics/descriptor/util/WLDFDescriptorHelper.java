package weblogic.diagnostics.descriptor.util;

import java.io.File;
import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public class WLDFDescriptorHelper {
   public static final String WL_DIAG_URI = "META-INF/weblogic-diagnostics.xml";

   public static DescriptorBean getDiagnosticDescriptor(String moduleUri, String moduleType, VirtualJarFile moduleVjf, DeploymentPlanBean dpBean, File configDir, boolean isStandaloneModule) {
      File diagDesFile = getExternalDiagnosticDescriptorFile(moduleUri, moduleType, dpBean, configDir);

      try {
         WLDFDescriptorLoader diagDescriptorLoader;
         if (diagDesFile != null) {
            diagDescriptorLoader = new WLDFDescriptorLoader(diagDesFile, configDir, dpBean, moduleUri, "META-INF/weblogic-diagnostics.xml");
         } else {
            diagDescriptorLoader = new WLDFDescriptorLoader(moduleVjf, configDir, dpBean, moduleUri, "META-INF/weblogic-diagnostics.xml");
         }

         DescriptorBean wldfBean = diagDescriptorLoader.loadDescriptorBean();
         if (wldfBean != null) {
            return wldfBean;
         }

         if (isStandaloneModule) {
            return (new DescriptorManager()).createDescriptorRoot(WLDFResourceBean.class).getRootBean();
         }
      } catch (Exception var9) {
      }

      return null;
   }

   private static File getExternalDiagnosticDescriptorFile(String moduleUri, String moduleType, DeploymentPlanBean dpBean, File configDir) {
      if (dpBean != null) {
         ModuleOverrideBean[] overrideModules = dpBean.getModuleOverrides();

         for(int i = 0; i < overrideModules.length; ++i) {
            if (overrideModules[i].getModuleName().equals(moduleUri) && overrideModules[i].getModuleType().equals(moduleType.toString())) {
               ModuleDescriptorBean[] moduleDesBeans = overrideModules[i].getModuleDescriptors();

               for(int j = 0; j < moduleDesBeans.length; ++j) {
                  if (moduleDesBeans[j].isExternal() && moduleDesBeans[j].getRootElement().equals("wldf-resource")) {
                     File diagDesFile = new File(configDir, moduleDesBeans[j].getUri());
                     if (diagDesFile.isFile() && diagDesFile.exists()) {
                        return diagDesFile;
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   public static DescriptorBean getDescriptorBean(DescriptorManager dm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String name, String uri) throws IOException, XMLStreamException {
      WLDFDescriptorLoader myDescriptorLoader = new WLDFDescriptorLoader(dm, gcl, configDir, plan, name, uri);
      return myDescriptorLoader.loadDescriptorBean();
   }

   public static DescriptorBean getDescriptorBean(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String name, String uri) throws IOException, XMLStreamException {
      WLDFDescriptorLoader myDescriptorLoader = new WLDFDescriptorLoader(vjar, configDir, plan, name, uri);
      return myDescriptorLoader.loadDescriptorBean();
   }

   public static DescriptorBean getDescriptorBean(File ddFile, File configDir, DeploymentPlanBean plan, String name, String uri) throws IOException, XMLStreamException {
      WLDFDescriptorLoader myDescriptorLoader = new WLDFDescriptorLoader(ddFile, configDir, plan, name, uri);
      return myDescriptorLoader.loadDescriptorBean();
   }
}
