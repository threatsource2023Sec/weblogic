package org.apache.openjpa.util;

import java.security.AccessController;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import serp.util.Numbers;

public final class Id extends OpenJPAId {
   private static final Localizer _loc = Localizer.forPackage(Id.class);
   private final long _id;

   public static Id newInstance(Class cls, Object val) {
      if (val instanceof Id) {
         return (Id)val;
      } else if (val instanceof String) {
         return new Id(cls, (String)val);
      } else if (val instanceof Number) {
         return new Id(cls, ((Number)val).longValue());
      } else if (val == null) {
         return new Id(cls, 0L);
      } else {
         throw new UserException(_loc.get("unknown-oid", cls, val, val.getClass()));
      }
   }

   public Id(String str) {
      this(str, (ClassLoader)null);
   }

   public Id(String str, OpenJPAConfiguration conf, ClassLoader brokerLoader) {
      this(str, conf == null ? brokerLoader : conf.getClassResolverInstance().getClassLoader(Id.class, brokerLoader));
   }

   public Id(String str, ClassLoader loader) {
      if (loader == null) {
         loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
      }

      if (str == null) {
         this._id = 0L;
      } else {
         int dash = str.indexOf(45);

         try {
            this.type = Class.forName(str.substring(0, dash), true, loader);
         } catch (Throwable var5) {
            throw new UserException(_loc.get("string-id", (Object)str), var5);
         }

         this._id = Long.parseLong(str.substring(dash + 1));
      }

   }

   public Id(Class cls, String key) {
      super(cls);
      if (key == null) {
         this._id = 0L;
      } else {
         int dash = key.indexOf(45);
         if (dash > 0) {
            key = key.substring(dash + 1);
         }

         this._id = Long.parseLong(key);
      }

   }

   public Id(Class cls, Long key) {
      this(cls, key == null ? 0L : key);
   }

   public Id(Class cls, long key) {
      super(cls);
      this._id = key;
   }

   public Id(Class cls, long key, boolean subs) {
      super(cls, subs);
      this._id = key;
   }

   public long getId() {
      return this._id;
   }

   public Object getIdObject() {
      return Numbers.valueOf(this._id);
   }

   protected int idHash() {
      return (int)(this._id ^ this._id >>> 32);
   }

   protected boolean idEquals(OpenJPAId other) {
      return this._id == ((Id)other)._id;
   }
}
