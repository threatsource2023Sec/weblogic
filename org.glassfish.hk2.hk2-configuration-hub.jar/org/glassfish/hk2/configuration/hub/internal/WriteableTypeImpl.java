package org.glassfish.hk2.configuration.hub.internal;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.glassfish.hk2.configuration.hub.api.Change;
import org.glassfish.hk2.configuration.hub.api.Instance;
import org.glassfish.hk2.configuration.hub.api.WriteableType;
import org.glassfish.hk2.utilities.reflection.BeanReflectionHelper;
import org.glassfish.hk2.utilities.reflection.ClassReflectionHelper;
import org.glassfish.hk2.utilities.reflection.internal.ClassReflectionHelperImpl;

public class WriteableTypeImpl implements WriteableType {
   private final WriteableBeanDatabaseImpl parent;
   private final String name;
   private final HashMap beanMap = new HashMap();
   private final ClassReflectionHelper helper;
   private Object metadata;

   WriteableTypeImpl(WriteableBeanDatabaseImpl parent, TypeImpl mother) {
      this.parent = parent;
      this.name = mother.getName();
      this.metadata = mother.getMetadata();
      this.beanMap.putAll(mother.getInstances());
      this.helper = mother.getHelper();
   }

   WriteableTypeImpl(WriteableBeanDatabaseImpl parent, String name) {
      this.parent = parent;
      this.name = name;
      this.helper = new ClassReflectionHelperImpl();
   }

   public String getName() {
      return this.name;
   }

   public synchronized Map getInstances() {
      return Collections.unmodifiableMap(this.beanMap);
   }

   public synchronized Instance getInstance(String key) {
      return (Instance)this.beanMap.get(key);
   }

   public synchronized Instance addInstance(String key, Object bean) {
      return this.addInstance(key, bean, (Object)null);
   }

   public synchronized Instance addInstance(String key, Object bean, Object metadata) {
      if (key != null && bean != null) {
         InstanceImpl ii = new InstanceImpl(bean, metadata);
         this.parent.addChange(new ChangeImpl(Change.ChangeCategory.ADD_INSTANCE, this, key, ii, (Instance)null, (List)null));
         this.beanMap.put(key, ii);
         return ii;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public synchronized Instance removeInstance(String key) {
      if (key == null) {
         throw new IllegalArgumentException();
      } else {
         Instance removedValue = (Instance)this.beanMap.remove(key);
         if (removedValue == null) {
            return null;
         } else {
            this.parent.addChange(new ChangeImpl(Change.ChangeCategory.REMOVE_INSTANCE, this, key, removedValue, (Instance)null, (List)null));
            return removedValue;
         }
      }
   }

   public synchronized PropertyChangeEvent[] modifyInstance(String key, Object newBean, PropertyChangeEvent... propChanges) {
      if (key != null && newBean != null) {
         Instance oldInstance = (Instance)this.beanMap.get(key);
         if (oldInstance == null) {
            throw new IllegalStateException("Attempting to modify bean with key " + key + " but no such bean exists");
         } else {
            InstanceImpl newInstance = new InstanceImpl(newBean, oldInstance.getMetadata());
            if (propChanges.length == 0) {
               propChanges = BeanReflectionHelper.getChangeEvents(this.helper, oldInstance.getBean(), newInstance.getBean());
            }

            this.beanMap.put(key, newInstance);
            ArrayList propChangesList = new ArrayList(propChanges.length);
            PropertyChangeEvent[] var7 = propChanges;
            int var8 = propChanges.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               PropertyChangeEvent pce = var7[var9];
               propChangesList.add(pce);
            }

            this.parent.addChange(new ChangeImpl(Change.ChangeCategory.MODIFY_INSTANCE, this, key, newInstance, oldInstance, propChangesList));
            return propChanges;
         }
      } else {
         throw new IllegalArgumentException();
      }
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
      return "WriteableTypeImpl(" + this.name + "," + this.metadata + "," + System.identityHashCode(this) + ")";
   }
}
