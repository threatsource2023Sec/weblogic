package org.glassfish.hk2.configuration.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Context;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.Visibility;
import org.glassfish.hk2.configuration.api.ConfiguredBy;

@Singleton
@Visibility(DescriptorVisibility.LOCAL)
public class ConfiguredByContext implements Context {
   private static final ThreadLocal workingOn = ThreadLocal.withInitial(() -> {
      return null;
   });
   private final Object lock = new Object();
   private final Map db = new HashMap();

   public Class getScope() {
      return ConfiguredBy.class;
   }

   public Object findOrCreate(ActiveDescriptor activeDescriptor, ServiceHandle root) {
      ActiveDescriptor previousValue = (ActiveDescriptor)workingOn.get();
      workingOn.set(activeDescriptor);

      Object var4;
      try {
         var4 = this.internalFindOrCreate(activeDescriptor, root);
      } finally {
         workingOn.set(previousValue);
      }

      return var4;
   }

   private Object internalFindOrCreate(ActiveDescriptor activeDescriptor, ServiceHandle root) {
      synchronized(this.lock) {
         Object retVal = this.db.get(activeDescriptor);
         if (retVal != null) {
            return retVal;
         } else if (activeDescriptor.getName() == null) {
            throw new MultiException(new IllegalStateException("ConfiguredBy services without names are templates and cannot be created directly"));
         } else {
            retVal = activeDescriptor.create(root);
            this.db.put(activeDescriptor, retVal);
            return retVal;
         }
      }
   }

   public boolean containsKey(ActiveDescriptor descriptor) {
      synchronized(this.lock) {
         return this.db.containsKey(descriptor);
      }
   }

   public void destroyOne(ActiveDescriptor descriptor) {
      synchronized(this.lock) {
         Object destroyMe = this.db.remove(descriptor);
         if (destroyMe != null) {
            descriptor.dispose(destroyMe);
         }
      }
   }

   public boolean supportsNullCreation() {
      return false;
   }

   public boolean isActive() {
      return true;
   }

   public void shutdown() {
      synchronized(this.lock) {
         Set activeDescriptors = new HashSet(this.db.keySet());
         Iterator var3 = activeDescriptors.iterator();

         while(var3.hasNext()) {
            ActiveDescriptor killMe = (ActiveDescriptor)var3.next();
            this.destroyOne(killMe);
         }

      }
   }

   ActiveDescriptor getWorkingOn() {
      return (ActiveDescriptor)workingOn.get();
   }

   Object findOnly(ActiveDescriptor descriptor) {
      synchronized(this.lock) {
         return this.db.get(descriptor);
      }
   }
}
