package weblogic.admin.plugin;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChangeList {
   private final HashMap changes = new HashMap();
   private boolean forceOverride = false;

   public void addChange(Change.Type type, String relativePath, long version) {
      this.addChange(type, (String)null, relativePath, version, 0L);
   }

   public void addChange(Change.Type type, String compName, String relativePath, long version, long lastModifiedTime) {
      if (type == null) {
         throw new IllegalArgumentException("type is null");
      } else if (relativePath == null) {
         throw new IllegalArgumentException("path is null");
      } else {
         this.changes.put(relativePath, new ImmutableChange(type, compName, relativePath, version, lastModifiedTime));
      }
   }

   public Map getChanges() {
      return Collections.unmodifiableMap(this.changes);
   }

   public boolean isForceOverride() {
      return this.forceOverride;
   }

   public void setForceOverride(boolean value) {
      this.forceOverride = value;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ChangeList that = (ChangeList)o;
         return this.changes == null ? that.changes == null : this.changes.equals(that.changes);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.changes == null ? 0 : this.changes.hashCode();
   }

   private static class ImmutableChange implements Serializable, Change {
      private final Change.Type type;
      private final String compName;
      private final String relativePath;
      private final long version;
      private final long lastModifiedTime;

      private ImmutableChange(Change.Type type, String compName, String relativePath, long version, long lastModifiedTime) {
         assert type != null;

         this.type = type;
         this.compName = compName;

         assert relativePath != null;

         this.relativePath = relativePath;
         this.version = version;
         this.lastModifiedTime = lastModifiedTime;
      }

      public Change.Type getType() {
         return this.type;
      }

      public String getComponentName() {
         return this.compName;
      }

      public String getRelativePath() {
         return this.relativePath;
      }

      public long getVersion() {
         return this.version;
      }

      public long getLastModifiedTime() {
         return this.lastModifiedTime;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            ImmutableChange that = (ImmutableChange)o;
            if (this.version != that.version) {
               return false;
            } else {
               label40: {
                  if (this.relativePath != null) {
                     if (this.relativePath.equals(that.relativePath)) {
                        break label40;
                     }
                  } else if (that.relativePath == null) {
                     break label40;
                  }

                  return false;
               }

               if (this.compName != null) {
                  if (!this.compName.equals(that.compName)) {
                     return false;
                  }
               } else if (that.compName != null) {
                  return false;
               }

               if (this.type != that.type) {
                  return false;
               } else {
                  return true;
               }
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = this.type != null ? this.type.hashCode() : 0;
         result = 31 * result + (this.relativePath != null ? this.relativePath.hashCode() : 0);
         result = 31 * result + (int)(this.version ^ this.version >>> 32);
         return result;
      }

      // $FF: synthetic method
      ImmutableChange(Change.Type x0, String x1, String x2, long x3, long x4, Object x5) {
         this(x0, x1, x2, x3, x4);
      }
   }

   public interface Change {
      Type getType();

      String getComponentName();

      String getRelativePath();

      long getVersion();

      long getLastModifiedTime();

      public static enum Type {
         EDIT,
         ADD,
         REMOVE;
      }
   }
}
