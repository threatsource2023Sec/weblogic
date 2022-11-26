package weblogic.application.descriptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import javax.xml.stream.XMLStreamException;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.descriptor.BeanCreationInterceptor;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorCache;
import weblogic.descriptor.DescriptorCreationListener;
import weblogic.descriptor.DescriptorManager;
import weblogic.descriptor.internal.DescriptorImpl;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.kernel.Kernel;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.jars.VirtualJarFile;

public abstract class CachingDescriptorLoader extends AbstractDescriptorLoader {
   private static final boolean isServer = Kernel.isServer();
   private static final DescriptorCache cache = DescriptorCache.getInstance();
   private static final boolean DEBUG = false;
   private static final boolean useCaching = Boolean.getBoolean("weblogic.EnableDescriptorCache");
   private final ApplicationAccess access;

   public CachingDescriptorLoader(VirtualJarFile vjar, File configDir, DeploymentPlanBean plan, String moduleName) {
      super(vjar, configDir, plan, moduleName);
      this.access = isServer ? ApplicationAccess.getApplicationAccess() : null;
   }

   public CachingDescriptorLoader(File altDD, File configDir, DeploymentPlanBean plan, String moduleName) {
      super(altDD, configDir, plan, moduleName);
      this.access = isServer ? ApplicationAccess.getApplicationAccess() : null;
   }

   public CachingDescriptorLoader(GenericClassLoader gcl) {
      super(gcl);
      this.access = isServer ? ApplicationAccess.getApplicationAccess() : null;
   }

   public CachingDescriptorLoader(GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
      super(gcl, configDir, plan, moduleName);
      this.access = isServer ? ApplicationAccess.getApplicationAccess() : null;
   }

   public CachingDescriptorLoader(DescriptorManager dm, GenericClassLoader gcl, File configDir, DeploymentPlanBean plan, String moduleName) {
      super(dm, gcl, configDir, plan, moduleName);
      this.access = isServer ? ApplicationAccess.getApplicationAccess() : null;
   }

   public CachingDescriptorLoader(DescriptorManager dm, GenericClassLoader gcl) {
      super(dm, gcl);
      this.access = isServer ? ApplicationAccess.getApplicationAccess() : null;
   }

   public CachingDescriptorLoader(VirtualJarFile vjar) {
      super(vjar);
      this.access = isServer ? ApplicationAccess.getApplicationAccess() : null;
   }

   public CachingDescriptorLoader(File altDD) {
      super(altDD);
      this.access = isServer ? ApplicationAccess.getApplicationAccess() : null;
   }

   protected File findCacheDir() {
      ApplicationContextInternal appCtx = this.access.getCurrentApplicationContext();
      return appCtx == null ? null : this.findCacheDir(appCtx.getDescriptorCacheDir());
   }

   protected File findCacheDir(File baseDir) {
      String moduleName = this.getModuleName();
      if (moduleName == null) {
         moduleName = "";
      }

      return new File(baseDir, moduleName + File.separator + this.getDocumentURI());
   }

   private Descriptor superCreateDescriptor(InputStream is) throws IOException, XMLStreamException {
      return super.createDescriptor(is);
   }

   private Descriptor superCreateDescriptor(InputStream is, boolean validate) throws IOException, XMLStreamException {
      return super.createDescriptor(is, validate);
   }

   protected Descriptor createDescriptor(InputStream is) throws IOException, XMLStreamException {
      if (isServer && this.getDeploymentPlan() == null) {
         File cacheDir = this.findCacheDir();
         if (cacheDir == null) {
            return super.createDescriptor(is);
         } else {
            IOHelperImpl helper = new IOHelperImpl(useCaching);
            helper.setValidate(cache.hasChanged(cacheDir, helper));

            DescriptorBean b;
            try {
               b = (DescriptorBean)cache.parseXML(cacheDir, helper);
            } catch (IOException var7) {
               cache.removeCRC(cacheDir);
               throw var7;
            } catch (XMLStreamException var8) {
               cache.removeCRC(cacheDir);
               throw var8;
            } catch (Throwable var9) {
               cache.removeCRC(cacheDir);
               IOException ioe = new IOException(var9.toString());
               ioe.initCause(var9);
               throw ioe;
            }

            return b == null ? null : b.getDescriptor();
         }
      } else {
         return super.createDescriptor(is);
      }
   }

   private class IOHelperImpl implements DescriptorCache.IOHelper {
      private boolean useCaching = false;
      private boolean validate = true;

      public IOHelperImpl(boolean useCaching) {
         this.useCaching = useCaching;
      }

      public InputStream openInputStream() throws IOException {
         return CachingDescriptorLoader.this.getInputStream();
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
         DescriptorImpl desc = DescriptorImpl.beginConstruction(false, CachingDescriptorLoader.READONLY_DESCRIPTOR_MANAGER_SINGLETON.instance, (DescriptorCreationListener)null, (BeanCreationInterceptor)null);
         DescriptorBean db = null;

         try {
            db = this.readCachedDescriptor(f);
         } finally {
            DescriptorImpl.endConstruction(db);
         }

         return db;
      }

      public Object parseXML(InputStream in) throws IOException, XMLStreamException {
         Descriptor d = CachingDescriptorLoader.this.superCreateDescriptor(in, this.validate);
         return d.getRootBean();
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
