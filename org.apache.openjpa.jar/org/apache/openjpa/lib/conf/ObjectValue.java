package org.apache.openjpa.lib.conf;

import java.security.AccessController;
import org.apache.commons.lang.ObjectUtils;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.concurrent.ConcurrentReferenceHashMap;

public class ObjectValue extends Value {
   private static final Localizer _loc = Localizer.forPackage(ObjectValue.class);
   private static ConcurrentReferenceHashMap _classloaderCache = new ConcurrentReferenceHashMap(0, 1);
   private Object _value = null;

   public ObjectValue(String prop) {
      super(prop);
   }

   public Object get() {
      return this._value;
   }

   public void set(Object obj) {
      this.set(obj, false);
   }

   public void set(Object obj, boolean derived) {
      if (!derived) {
         this.assertChangeable();
      }

      Object oldValue = this._value;
      this._value = obj;
      if (!derived && !ObjectUtils.equals(obj, oldValue)) {
         this.objectChanged();
         this.valueChanged();
      }

   }

   public Object instantiate(Class type, Configuration conf) {
      return this.instantiate(type, conf, true);
   }

   public Object instantiate(Class type, Configuration conf, boolean fatal) {
      throw new UnsupportedOperationException();
   }

   public Object newInstance(String clsName, Class type, Configuration conf, boolean fatal) {
      ClassLoader cl = (ClassLoader)_classloaderCache.get(type);
      if (cl == null) {
         cl = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(type));
         if (cl == null) {
            cl = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getSystemClassLoaderAction());
         }

         _classloaderCache.put(type, cl);
      }

      return Configurations.newInstance(clsName, this, conf, cl, fatal);
   }

   public Class getValueType() {
      return Object.class;
   }

   protected void objectChanged() {
   }

   protected String getInternalString() {
      return null;
   }

   protected void setInternalString(String str) {
      if (str == null) {
         this.set((Object)null);
      } else {
         throw new IllegalArgumentException(_loc.get("cant-set-string", (Object)this.getProperty()).getMessage());
      }
   }

   protected void setInternalObject(Object obj) {
      this.set(obj);
   }
}
