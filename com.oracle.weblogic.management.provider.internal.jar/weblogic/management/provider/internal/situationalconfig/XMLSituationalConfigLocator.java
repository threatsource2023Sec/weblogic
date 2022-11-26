package weblogic.management.provider.internal.situationalconfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementLogger;
import weblogic.management.provider.internal.federatedconfig.FederatedConfigFragmentsProcessor;
import weblogic.management.utils.federatedconfig.FederatedConfig;
import weblogic.management.utils.situationalconfig.SituationalConfigFile;
import weblogic.management.utils.situationalconfig.SituationalConfigLocator;

public class XMLSituationalConfigLocator implements SituationalConfigLocator {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugSituationalConfig");
   private List situationalConfigFiles = new ArrayList();
   FileComparator fc = new FileComparator();

   public Iterator sources(Set excludes, String descriptorFileName) throws Exception {
      ArrayList sources = new ArrayList();
      List files = new ArrayList();
      files.addAll(this.getOrderedCollection());
      if (files.size() == 0) {
         throw new AssertionError("Should never be called when empty");
      } else {
         File parent = ((File)files.get(0)).getParentFile();

         try {
            sources.add(this.createTransformationFromConfigFragments(parent, files, descriptorFileName));
         } catch (Exception var7) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("[SitConfig] Exception for " + descriptorFileName + "  occurred during create transformation: " + var7);
            }

            ManagementLogger.logInvalidSituationalConfigException(descriptorFileName, var7);
         }

         return sources.iterator();
      }
   }

   public boolean registerUpdateListener(FederatedConfig.UpdateListener listener) {
      return false;
   }

   public boolean unregisterUpdateListener(FederatedConfig.UpdateListener listener) {
      return false;
   }

   public void setFiles(List sitCfgFiles) {
      this.situationalConfigFiles.clear();
      Iterator var2 = sitCfgFiles.iterator();

      while(var2.hasNext()) {
         SituationalConfigFile oneSitCfgFile = (SituationalConfigFile)var2.next();
         File file = oneSitCfgFile.getFile();
         if (file.exists()) {
            this.situationalConfigFiles.add(file);
         }
      }

      Collections.sort(this.situationalConfigFiles, this.fc);
   }

   public String toString() {
      return this.getOrderedCollection().toString();
   }

   Source createTransformationFromConfigFragments(File fragmentParent, List children, String descFileName) throws IOException, XMLStreamException {
      FederatedConfigFragmentsProcessor proc = new FederatedConfigFragmentsProcessor();
      return proc.getTransformation(fragmentParent, children.iterator(), descFileName);
   }

   Collection getOrderedCollection() {
      return this.situationalConfigFiles;
   }

   class FileComparator implements Comparator {
      public int compare(File f1, File f2) {
         int rvalx = false;
         long t0 = f1.lastModified();
         long t1 = f2.lastModified();
         String name0 = f1.getName();
         String name1 = f2.getName();
         long diff = t0 - t1;
         int rval;
         if (diff == 0L) {
            rval = name0.compareTo(name1);
         } else {
            rval = diff < 0L ? -1 : 1;
         }

         return rval;
      }
   }
}
