package weblogic.application.descriptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import javax.xml.stream.XMLStreamException;
import weblogic.descriptor.BeanCreationInterceptor;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorCache;
import weblogic.descriptor.DescriptorCreationListener;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.kernel.KernelStatus;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public abstract class CachingDescriptorLoader2 extends AbstractDescriptorLoader2 {
   private static final boolean isServer = KernelStatus.isServer();
   private static final DescriptorCache cache = DescriptorCache.getInstance();
   private static final boolean DEBUG = false;
   private static final boolean useCaching = Boolean.getBoolean("weblogic.EnableDescriptorCache");
   private final File descriptorCacheDir;

   public CachingDescriptorLoader2(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName, String documentURI) {
      super(vjar, configDir, plan, moduleName, documentURI);
      this.descriptorCacheDir = null;
   }

   public CachingDescriptorLoader2(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName, String documentURI, File descriptorCacheDir) {
      super(vjar, configDir, plan, moduleName, documentURI);
      this.descriptorCacheDir = descriptorCacheDir;
   }

   public CachingDescriptorLoader2(File altDD, File configDir, DeploymentPlanBean plan, String moduleName, String documentURI, File descriptorCacheDir) {
      super(altDD, configDir, plan, moduleName, documentURI);
      this.descriptorCacheDir = descriptorCacheDir;
   }

   public CachingDescriptorLoader2(DescriptorManager dm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName, String documentURI, File descriptorCacheDir) {
      super(dm, gcl, configDir, plan, moduleName, documentURI);
      this.descriptorCacheDir = descriptorCacheDir;
   }

   public CachingDescriptorLoader2(DescriptorManager dm, GenericClassLoader gcl, String documentURI, File descriptorCacheDir) {
      super(dm, gcl, documentURI);
      this.descriptorCacheDir = descriptorCacheDir;
   }

   protected File findCacheDir() {
      return this.descriptorCacheDir == null ? null : this.findCacheDir(this.descriptorCacheDir);
   }

   protected File findCacheDir(File baseDir) {
      String moduleName = this.getModuleName();
      if (moduleName == null) {
         moduleName = "";
      }

      return new File(baseDir, moduleName + File.separator + this.getDocumentURI());
   }

   protected Descriptor createDescriptor() throws IOException, XMLStreamException {
      if (isServer && this.getDeploymentPlan() == null) {
         File cacheDir = this.findCacheDir();
         if (cacheDir == null) {
            DescriptorBean b = super.loadDescriptorBean();
            return b == null ? null : b.getDescriptor();
         } else {
            IOHelperImpl helper = new IOHelperImpl(useCaching);
            helper.setValidate(cache.hasChanged(cacheDir, helper));

            DescriptorBean b;
            try {
               b = (DescriptorBean)cache.parseXML(cacheDir, helper);
            } catch (IOException var6) {
               cache.removeCRC(cacheDir);
               throw var6;
            } catch (XMLStreamException var7) {
               cache.removeCRC(cacheDir);
               throw var7;
            } catch (Throwable var8) {
               cache.removeCRC(cacheDir);
               IOException ioe = new IOException(var8.toString());
               ioe.initCause(var8);
               throw ioe;
            }

            return b == null ? null : b.getDescriptor();
         }
      } else {
         DescriptorBean b = this.loadDescriptorBean();
         return b == null ? null : b.getDescriptor();
      }
   }

   private class IOHelperImpl implements DescriptorCache.IOHelper {
      private boolean useCaching = false;
      private boolean validate = true;

      public IOHelperImpl(boolean useCaching) {
         this.useCaching = useCaching;
      }

      public InputStream openInputStream() throws IOException {
         return CachingDescriptorLoader2.this.getInputStream();
      }

      private DescriptorBean readCachedDescriptor(File desFile) throws IOException {
         ObjectInputStream ois = null;

         DescriptorBean var3;
         try {
            ois = new ObjectInputStream(new FileInputStream(desFile));
            var3 = (DescriptorBean)ois.readObject();
         } catch (ClassNotFoundException var12) {
            throw (IOException)(new IOException(var12.getMessage())).initCause(var12);
         } finally {
            if (ois != null) {
               try {
                  ois.close();
               } catch (IOException var11) {
               }
            }

         }

         return var3;
      }

      public Object readCachedBean(File f) throws IOException {
         DescriptorImpl desc = DescriptorImpl.beginConstruction(false, CachingDescriptorLoader2.READONLY_DESCRIPTOR_MANAGER_SINGLETON.instance, (DescriptorCreationListener)null, (BeanCreationInterceptor)null);
         DescriptorBean db = null;

         try {
            db = this.readCachedDescriptor(f);
         } finally {
            DescriptorImpl.endConstruction(db);
         }

         return db;
      }

      public Object parseXML(InputStream notUsed) throws IOException, XMLStreamException {
         return CachingDescriptorLoader2.this.loadDescriptorBean();
      }

      public boolean useCaching() {
         return this.useCaching;
      }

      protected void setValidate(boolean validate) {
         this.validate = validate;
      }
   }

   private static class READONLY_DESCRIPTOR_MANAGER_SINGLETON {
      static DescriptorManager instance = new DescriptorManager();
   }
}
