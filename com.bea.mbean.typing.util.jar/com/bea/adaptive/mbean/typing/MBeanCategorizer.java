package com.bea.adaptive.mbean.typing;

import java.io.Serializable;
import java.util.Arrays;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;

public interface MBeanCategorizer extends Serializable {
   String categorize(MBeanServerConnection var1, ObjectName var2);

   TypeInfo getTypeInfo(MBeanServerConnection var1, ObjectName var2);

   boolean handles(MBeanServerConnection var1, ObjectName var2);

   public static class Impl implements MBeanCategorizer {
      static final long serialVersionUID = 1L;
      private Plugin[] plugins;

      public Impl(Plugin[] plugins) {
         this.plugins = (Plugin[])Arrays.copyOf(plugins, plugins.length);
      }

      public String categorize(MBeanServerConnection mbs, ObjectName mbean) {
         try {
            for(int i = 0; i < this.plugins.length; ++i) {
               Plugin pi = this.plugins[i];
               if (pi.handles(mbs, mbean)) {
                  return pi.getCategoryName();
               }
            }

            return null;
         } catch (Exception var5) {
            throw new RuntimeException(var5);
         }
      }

      public TypeInfo getTypeInfo(MBeanServerConnection mbs, ObjectName mbean) {
         try {
            for(int i = 0; i < this.plugins.length; ++i) {
               Plugin pi = this.plugins[i];
               if (pi.handles(mbs, mbean)) {
                  String typeName = pi.getTypeName(mbs, mbean);
                  if (typeName != null) {
                     return new TypeInfoImpl(typeName, pi.getCategoryName());
                  }
               }
            }

            return null;
         } catch (Exception var6) {
            throw new RuntimeException(var6);
         }
      }

      public boolean handles(MBeanServerConnection mbs, ObjectName mbean) {
         Plugin[] var3 = this.plugins;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Plugin pi = var3[var5];
            if (pi.handles(mbs, mbean)) {
               return true;
            }
         }

         return false;
      }
   }

   public static class TypeInfoImpl implements TypeInfo {
      String type;
      String category;

      TypeInfoImpl(String type, String category) {
         this.type = type;
         this.category = category;
      }

      public String getTypeName() {
         return this.type;
      }

      public String getCategoryName() {
         return this.category;
      }
   }

   public interface Plugin extends Serializable {
      boolean handles(MBeanServerConnection var1, ObjectName var2);

      String getTypeName(MBeanServerConnection var1, ObjectName var2);

      String getCategoryName();
   }

   public interface TypeInfo {
      String getTypeName();

      String getCategoryName();
   }
}
