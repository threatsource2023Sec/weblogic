package weblogic.apache.org.apache.velocity.runtime.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.commons.collections.ExtendedProperties;
import weblogic.apache.org.apache.velocity.exception.ParseErrorException;
import weblogic.apache.org.apache.velocity.exception.ResourceNotFoundException;
import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;
import weblogic.apache.org.apache.velocity.runtime.resource.loader.ResourceLoader;
import weblogic.apache.org.apache.velocity.runtime.resource.loader.ResourceLoaderFactory;

public class ResourceManagerImpl implements ResourceManager {
   public static final int RESOURCE_TEMPLATE = 1;
   public static final int RESOURCE_CONTENT = 2;
   private static final String RESOURCE_LOADER_IDENTIFIER = "_RESOURCE_LOADER_IDENTIFIER_";
   protected ResourceCache globalCache = null;
   protected ArrayList resourceLoaders = new ArrayList();
   private ArrayList sourceInitializerList = new ArrayList();
   private Hashtable sourceInitializerMap = new Hashtable();
   private boolean resourceLoaderInitializersActive = false;
   private boolean logWhenFound = true;
   protected RuntimeServices rsvc = null;

   public void initialize(RuntimeServices rs) throws Exception {
      this.rsvc = rs;
      this.rsvc.info("Default ResourceManager initializing. (" + this.getClass() + ")");
      this.assembleResourceLoaderInitializers();

      for(int i = 0; i < this.sourceInitializerList.size(); ++i) {
         ExtendedProperties configuration = (ExtendedProperties)this.sourceInitializerList.get(i);
         String loaderClass = configuration.getString("class");
         if (loaderClass == null) {
            this.rsvc.error("Unable to find '" + configuration.getString("_RESOURCE_LOADER_IDENTIFIER_") + ".resource.loader.class' specification in configuation." + " This is a critical value.  Please adjust configuration.");
         } else {
            ResourceLoader resourceLoader = ResourceLoaderFactory.getLoader(this.rsvc, loaderClass);
            resourceLoader.commonInit(this.rsvc, configuration);
            resourceLoader.init(configuration);
            this.resourceLoaders.add(resourceLoader);
         }
      }

      this.logWhenFound = this.rsvc.getBoolean("resource.manager.logwhenfound", true);
      String claz = this.rsvc.getString("resource.manager.cache.class");
      Object o = null;
      if (claz != null && claz.length() > 0) {
         try {
            o = Class.forName(claz).newInstance();
         } catch (ClassNotFoundException var8) {
            String err = "The specified class for ResourceCache (" + claz + ") does not exist (or is not accessible to the current classlaoder).";
            this.rsvc.error(err);
            o = null;
         }

         if (!(o instanceof ResourceCache)) {
            String err = "The specified class for ResourceCache (" + claz + ") does not implement org.apache.runtime.resource.ResourceCache." + " ResourceManager. Using default ResourceCache implementation.";
            this.rsvc.error(err);
            o = null;
         }
      }

      if (o == null) {
         o = new ResourceCacheImpl();
      }

      this.globalCache = (ResourceCache)o;
      this.globalCache.initialize(this.rsvc);
      this.rsvc.info("Default ResourceManager initialization complete.");
   }

   private void assembleResourceLoaderInitializers() {
      if (!this.resourceLoaderInitializersActive) {
         Vector resourceLoaderNames = this.rsvc.getConfiguration().getVector("resource.loader");

         for(int i = 0; i < resourceLoaderNames.size(); ++i) {
            String loaderID = resourceLoaderNames.get(i) + "." + "resource.loader";
            ExtendedProperties loaderConfiguration = this.rsvc.getConfiguration().subset(loaderID);
            if (loaderConfiguration == null) {
               this.rsvc.warn("ResourceManager : No configuration information for resource loader named '" + resourceLoaderNames.get(i) + "'. Skipping.");
            } else {
               loaderConfiguration.setProperty("_RESOURCE_LOADER_IDENTIFIER_", resourceLoaderNames.get(i));
               this.sourceInitializerList.add(loaderConfiguration);
            }
         }

         this.resourceLoaderInitializersActive = true;
      }
   }

   public Resource getResource(String resourceName, int resourceType, String encoding) throws ResourceNotFoundException, ParseErrorException, Exception {
      Resource resource = this.globalCache.get(resourceName);
      if (resource != null) {
         try {
            this.refreshResource(resource, encoding);
         } catch (ResourceNotFoundException var11) {
            this.globalCache.remove(resourceName);
            return this.getResource(resourceName, resourceType, encoding);
         } catch (ParseErrorException var12) {
            this.rsvc.error("ResourceManager.getResource() exception: " + var12);
            throw var12;
         } catch (Exception var13) {
            this.rsvc.error("ResourceManager.getResource() exception: " + var13);
            throw var13;
         }
      } else {
         try {
            resource = this.loadResource(resourceName, resourceType, encoding);
            if (resource.getResourceLoader().isCachingOn()) {
               this.globalCache.put(resourceName, resource);
            }
         } catch (ResourceNotFoundException var8) {
            this.rsvc.error("ResourceManager : unable to find resource '" + resourceName + "' in any resource loader.");
            throw var8;
         } catch (ParseErrorException var9) {
            this.rsvc.error("ResourceManager.getResource() parse exception: " + var9);
            throw var9;
         } catch (Exception var10) {
            this.rsvc.error("ResourceManager.getResource() exception new: " + var10);
            throw var10;
         }
      }

      return resource;
   }

   protected Resource loadResource(String resourceName, int resourceType, String encoding) throws ResourceNotFoundException, ParseErrorException, Exception {
      Resource resource = ResourceFactory.getResource(resourceName, resourceType);
      resource.setRuntimeServices(this.rsvc);
      resource.setName(resourceName);
      resource.setEncoding(encoding);
      long howOldItWas = 0L;
      ResourceLoader resourceLoader = null;

      for(int i = 0; i < this.resourceLoaders.size(); ++i) {
         resourceLoader = (ResourceLoader)this.resourceLoaders.get(i);
         resource.setResourceLoader(resourceLoader);

         try {
            if (resource.process()) {
               if (this.logWhenFound) {
                  this.rsvc.info("ResourceManager : found " + resourceName + " with loader " + resourceLoader.getClassName());
               }

               howOldItWas = resourceLoader.getLastModified(resource);
               break;
            }
         } catch (ResourceNotFoundException var10) {
         }
      }

      if (resource.getData() == null) {
         throw new ResourceNotFoundException("Unable to find resource '" + resourceName + "'");
      } else {
         resource.setLastModified(howOldItWas);
         resource.setModificationCheckInterval(resourceLoader.getModificationCheckInterval());
         resource.touch();
         return resource;
      }
   }

   protected void refreshResource(Resource resource, String encoding) throws ResourceNotFoundException, ParseErrorException, Exception {
      if (resource.requiresChecking()) {
         resource.touch();
         if (resource.isSourceModified()) {
            if (!resource.getEncoding().equals(encoding)) {
               this.rsvc.error("Declared encoding for template '" + resource.getName() + "' is different on reload.  Old = '" + resource.getEncoding() + "'  New = '" + encoding);
               resource.setEncoding(encoding);
            }

            long howOldItWas = resource.getResourceLoader().getLastModified(resource);
            resource.process();
            resource.setLastModified(howOldItWas);
         }
      }

   }

   /** @deprecated */
   public Resource getResource(String resourceName, int resourceType) throws ResourceNotFoundException, ParseErrorException, Exception {
      return this.getResource(resourceName, resourceType, "ISO-8859-1");
   }

   public String getLoaderNameForResource(String resourceName) {
      ResourceLoader resourceLoader = null;

      for(int i = 0; i < this.resourceLoaders.size(); ++i) {
         resourceLoader = (ResourceLoader)this.resourceLoaders.get(i);
         InputStream is = null;

         String var5;
         try {
            is = resourceLoader.getResourceStream(resourceName);
            if (is == null) {
               continue;
            }

            var5 = resourceLoader.getClass().toString();
         } catch (ResourceNotFoundException var16) {
            continue;
         } finally {
            if (is != null) {
               try {
                  is.close();
               } catch (IOException var15) {
               }
            }

         }

         return var5;
      }

      return null;
   }
}
