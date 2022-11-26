package weblogic.managedbean;

import com.oracle.pitchfork.intercept.InterceptionMetadata;
import com.oracle.pitchfork.interfaces.inject.Jsr250MetadataI;
import com.oracle.pitchfork.interfaces.inject.LifecycleEvent;
import com.oracle.pitchfork.spi.TargetWrapperImpl;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ManagedBeanCreatorImpl implements ManagedBeanCreator {
   private final ManagedBeanContributor contributor;
   private final ClassLoader moduleClassLoader;
   private final Map managedBeans = new HashMap();

   ManagedBeanCreatorImpl(ManagedBeanContributor mbc, ClassLoader cl) {
      this.contributor = mbc;
      this.moduleClassLoader = cl;
   }

   public Object createInstance(String className) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
      if (className == null) {
         return null;
      } else {
         Class clazz = this.moduleClassLoader.loadClass(className);
         return this.createInstance(clazz);
      }
   }

   public Object createInstance(Class clazz) throws IllegalAccessException, ClassNotFoundException, InstantiationException {
      try {
         Object bean = clazz.newInstance();
         Jsr250MetadataI metadata = this.contributor.getMetadata(clazz.getName());
         if (metadata != null) {
            metadata.inject(bean);
            bean = ((InterceptionMetadata)metadata).createProxyIfNecessary(new TargetWrapperImpl(bean), new HashMap());
            List preDestroyMethods = metadata.getLifecycleEventCallbackMethod(LifecycleEvent.PRE_DESTROY);
            if (preDestroyMethods != null && !preDestroyMethods.isEmpty()) {
               List beans = (List)this.managedBeans.get(clazz.getName());
               if (beans == null) {
                  beans = new LinkedList();
                  this.managedBeans.put(clazz.getName(), beans);
               }

               ((List)beans).add(bean);
            }
         }

         return bean;
      } catch (RuntimeException var6) {
         this.contributor.removeMetatdata(clazz.getName());
         throw var6;
      }
   }

   public void notifyPreDestroy(String compName, Object obj) {
      Jsr250MetadataI metadata = this.contributor.getMetadata(compName);
      if (metadata != null) {
         metadata.invokeLifecycleMethods(obj, LifecycleEvent.PRE_DESTROY);
      }

   }

   public void notifyPostConstruct(String compName, Object obj) {
      Jsr250MetadataI metadata = this.contributor.getMetadata(compName);
      if (metadata != null) {
         metadata.invokeLifecycleMethods(obj, LifecycleEvent.POST_CONSTRUCT);
      }

   }

   public void destroy() {
      Iterator var1 = this.managedBeans.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry e = (Map.Entry)var1.next();
         Iterator var3 = ((List)e.getValue()).iterator();

         while(var3.hasNext()) {
            Object bean = var3.next();
            this.notifyPreDestroy((String)e.getKey(), bean);
         }
      }

      this.managedBeans.clear();
   }
}
