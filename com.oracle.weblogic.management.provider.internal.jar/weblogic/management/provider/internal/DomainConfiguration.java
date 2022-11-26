package weblogic.management.provider.internal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.stream.XMLStreamException;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorCache;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.DescriptorUpdateFailedException;
import weblogic.descriptor.DescriptorUpdateRejectedException;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.management.DomainDir;
import weblogic.management.bootstrap.BootStrap;
import weblogic.management.configuration.DomainMBean;

public class DomainConfiguration {
   private static volatile DomainConfiguration instance = null;
   private Descriptor currentTree = this.loadBeanTreeFromActive(BootStrap.getConfigDirectoryConfigFile());
   private DomainMBean currentDomainMBean;

   public static DomainConfiguration getInstance() throws IOException {
      if (instance == null) {
         Class var0 = DomainConfiguration.class;
         synchronized(DomainConfiguration.class) {
            if (instance == null) {
               instance = new DomainConfiguration();
            }
         }
      }

      return instance;
   }

   private DomainConfiguration() throws IOException {
      this.currentDomainMBean = (DomainMBean)this.currentTree.getRootBean();
   }

   public DomainMBean getDomainMBean() {
      return this.currentDomainMBean;
   }

   void update(Descriptor newCurrentTree) throws DescriptorUpdateRejectedException, DescriptorUpdateFailedException {
      DescriptorDiff diff = this.currentTree.computeDiff(newCurrentTree);
      this.currentTree.applyDiff(diff);
      Iterator it = DescriptorInfoUtils.getDescriptorInfos(newCurrentTree);

      while(it != null && it.hasNext()) {
         DescriptorInfo descInfo = (DescriptorInfo)it.next();
         Descriptor desc = descInfo.getDescriptor();
         Descriptor externalCurrentTree = EditAccessImpl.getExternalTree(descInfo, this.currentTree);
         if (externalCurrentTree != null) {
            diff = externalCurrentTree.computeDiff(desc);
            externalCurrentTree.applyDiff(diff);
         }
      }

   }

   private Descriptor loadBeanTreeFromActive(File inputFile) throws IOException {
      try {
         InputStream is = new FileInputStream(inputFile);
         Throwable var23 = null;

         Descriptor var7;
         try {
            DescriptorManager descMgr = DescriptorManagerHelper.getDescriptorManager(false);
            ArrayList errs = new ArrayList();
            Descriptor result = descMgr.createDescriptor(new ConfigReader(is), errs, true);
            EditAccessImpl.checkErrors(inputFile.getAbsolutePath(), errs);
            EditAccessImpl.setProductionModeInfo(result);
            var7 = result;
         } catch (Throwable var18) {
            var23 = var18;
            throw var18;
         } finally {
            if (is != null) {
               if (var23 != null) {
                  try {
                     is.close();
                  } catch (Throwable var17) {
                     var23.addSuppressed(var17);
                  }
               } else {
                  is.close();
               }
            }

         }

         return var7;
      } catch (XMLStreamException var20) {
         IOException ioe = new IOException("Error loading " + inputFile + ": " + var20.getMessage());
         ioe.initCause(var20);
         throw ioe;
      } catch (DescriptorValidateException var21) {
         DescriptorCache descCache = DescriptorCache.getInstance();
         descCache.removeCRC(new File(DomainDir.getConfigDir() + System.getProperty("file.separator") + "configCache"));
         throw var21;
      }
   }
}
