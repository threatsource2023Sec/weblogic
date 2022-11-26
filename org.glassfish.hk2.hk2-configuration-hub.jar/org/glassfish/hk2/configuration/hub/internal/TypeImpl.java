package org.glassfish.hk2.configuration.hub.internal;

import java.util.Collections;
import java.util.Map;
import org.glassfish.hk2.configuration.hub.api.Instance;
import org.glassfish.hk2.configuration.hub.api.Type;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;

public class TypeImpl implements Type {
   private final String name;
   private final Map instances;
   private final ClassReflectionHelper helper;
   private Object metadata;

   TypeImpl(Type baseType, ClassReflectionHelper helper) {
      this.name = baseType.getName();
      this.instances = Collections.unmodifiableMap(baseType.getInstances());
      this.helper = helper;
      this.metadata = baseType.getMetadata();
   }

   public String getName() {
      return this.name;
   }

   public Map getInstances() {
      return this.instances;
   }

   public Instance getInstance(String key) {
      return (Instance)this.instances.get(key);
   }

   ClassReflectionHelper getHelper() {
      return this.helper;
   }

   public synchronized Object getMetadata() {
      return this.metadata;
   }

   public synchronized void setMetadata(Object metadata) {
      this.metadata = metadata;
   }

   public String toString() {
      return "TypeImpl(" + this.name + "," + System.identityHashCode(this) + ")";
   }
}
