package weblogic.coherence.service.internal;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheFactoryBuilder;
import com.tangosol.net.ConfigurableCacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.net.ScopedCacheFactoryBuilder;
import com.tangosol.net.Service;
import com.tangosol.run.xml.XmlElement;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.WeakHashMap;

public class WLSCacheFactoryBuilder extends ScopedCacheFactoryBuilder {
   private static final String CACHE_RESOURCE_NAME = "META-INF/coherence-cache-config.xml";
   private static final String COHERENCE_APP_DESCRIPTOR_URI = "META-INF/coherence-application.xml";
   private WeakHashMap mapContainerLoaders = new WeakHashMap();
   private CacheFactoryBuilder defaultCacheFactoryBuilder = CacheFactory.getCacheFactoryBuilder();

   public WLSCacheFactoryBuilder() {
      CacheFactory.setCacheFactoryBuilder(this);
   }

   public ConfigurableCacheFactory getConfigurableCacheFactory(ClassLoader loader) {
      if (this.isAnApplicationClassLoader(loader)) {
         return this.defaultCacheFactoryBuilder.getConfigurableCacheFactory(loader);
      } else {
         URL ddUrl = loader.getResource("META-INF/coherence-application.xml");
         URL url = loader.getResource("META-INF/coherence-cache-config.xml");
         return ddUrl == null && url != null ? this.defaultCacheFactoryBuilder.getConfigurableCacheFactory("META-INF/coherence-cache-config.xml", loader) : this.defaultCacheFactoryBuilder.getConfigurableCacheFactory(loader);
      }
   }

   private boolean isAnApplicationClassLoader(ClassLoader loader) {
      ClassLoader loaderCheck = loader;

      while(!this.mapContainerLoaders.containsKey(loaderCheck)) {
         if ((loaderCheck = loaderCheck.getParent()) == null) {
            return false;
         }
      }

      if (loaderCheck != loader) {
         this.mapContainerLoaders.put(loader, true);
      }

      return true;
   }

   public ConfigurableCacheFactory getConfigurableCacheFactory(String sConfigURI, ClassLoader loader) {
      return this.defaultCacheFactoryBuilder.getConfigurableCacheFactory(sConfigURI, loader);
   }

   public void setCacheConfiguration(ClassLoader loader, XmlElement xmlConfig) {
      this.defaultCacheFactoryBuilder.setCacheConfiguration(loader, xmlConfig);
   }

   public void setCacheConfiguration(String sConfigURI, ClassLoader loader, XmlElement xmlConfig) {
      this.defaultCacheFactoryBuilder.setCacheConfiguration(sConfigURI, loader, xmlConfig);
   }

   public ConfigurableCacheFactory setConfigurableCacheFactory(ConfigurableCacheFactory ccf, String sConfigURI, ClassLoader loader, boolean fReplace) {
      return this.defaultCacheFactoryBuilder.setConfigurableCacheFactory(ccf, sConfigURI, loader, fReplace);
   }

   public void releaseAll(ClassLoader loader) {
      this.release(this.getConfigurableCacheFactory(loader));
      this.defaultCacheFactoryBuilder.releaseAll(loader);
      this.mapContainerLoaders.remove(loader);
   }

   public void release(ConfigurableCacheFactory factory) {
      this.defaultCacheFactoryBuilder.release(factory);
   }

   public NamedCache ensureCache(String cacheName, ClassLoader loader) {
      ConfigurableCacheFactory factory = this.getConfigurableCacheFactory(loader);
      return factory.ensureCache(cacheName, loader);
   }

   public Service ensureService(String service) {
      ConfigurableCacheFactory factory = this.getConfigurableCacheFactory(Thread.currentThread().getContextClassLoader());
      return factory.ensureService(service);
   }

   public void addToContainerClassLoaders(List loaders) {
      Iterator var2 = loaders.iterator();

      while(var2.hasNext()) {
         ClassLoader loader = (ClassLoader)var2.next();
         this.mapContainerLoaders.put(loader, true);
      }

   }

   public void removeFromContainerClassLoaders(List loaders) {
      Iterator var2 = loaders.iterator();

      while(var2.hasNext()) {
         ClassLoader loader = (ClassLoader)var2.next();
         this.mapContainerLoaders.remove(loader);
      }

   }
}
