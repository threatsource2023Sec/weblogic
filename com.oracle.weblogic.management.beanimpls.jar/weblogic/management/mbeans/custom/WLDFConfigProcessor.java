package weblogic.management.mbeans.custom;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.descriptor.util.WLDFDescriptorLoader;
import weblogic.management.DomainDir;
import weblogic.management.ManagementRuntimeException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.WLDFSystemResourceMBean;

public class WLDFConfigProcessor {
   public WLDFSystemResourceMBean createWLDFSystemResource(DomainMBean domain, String name, String profile) {
      String file = name + ".xml";
      this.cloneProfile(profile, file, name);
      WLDFSystemResourceMBean resource = domain.createWLDFSystemResource(name, file);
      return resource;
   }

   private void cloneProfile(String profile, String toFile, String name) {
      String profileLocation = null;
      if ("Low".equals(profile)) {
         profileLocation = "META-INF/wldf-server-resource-low.xml";
      } else if ("Medium".equals(profile)) {
         profileLocation = "META-INF/wldf-server-resource-medium.xml";
      } else {
         profileLocation = "META-INF/wldf-server-resource-high.xml";
      }

      String profileFile = DomainDir.getConfigDir() + File.separator + "diagnostics" + File.separator + toFile;
      OutputStream out = null;
      InputStream in = null;

      try {
         out = new FileOutputStream(profileFile);
         in = this.getClass().getClassLoader().getResourceAsStream(profileLocation);
         DescriptorManager manager = new DescriptorManager();
         WLDFDescriptorLoader loader = new WLDFDescriptorLoader(in);
         loader.setDescriptorManager(manager);
         WLDFResourceBean bean = (WLDFResourceBean)loader.loadDescriptorBean();
         bean.setName(name);
         bean.getHarvester().setName(name);
         bean.getWatchNotification().setName(name);
         bean.getInstrumentation().setName(name);
         manager.writeDescriptorBeanAsXML((DescriptorBean)bean, out);
      } catch (Exception var18) {
         throw new ManagementRuntimeException("Cloning a builtin module failed", var18);
      } finally {
         try {
            if (out != null) {
               out.close();
            }

            if (in != null) {
               in.close();
            }
         } catch (IOException var17) {
         }

      }

   }
}
