package com.sun.faces.application.resource;

public class VersionInfo implements Comparable {
   private String version;
   private String extension;

   public VersionInfo(String version, String extension) {
      this.version = version;
      this.extension = extension;
   }

   public String getVersion() {
      return this.version;
   }

   public String getExtension() {
      return this.extension;
   }

   public String toString() {
      return this.version;
   }

   public int hashCode() {
      return this.version.hashCode() ^ (this.extension != null ? this.extension.hashCode() : 0);
   }

   public boolean equals(Object obj) {
      if (obj != null && obj instanceof VersionInfo) {
         if (this == obj) {
            return true;
         } else {
            VersionInfo passed = (VersionInfo)obj;
            boolean versionsEqual = this.version.equals(passed.version);
            boolean extensionEqual;
            if (this.extension == null) {
               extensionEqual = passed.extension == null;
            } else {
               extensionEqual = this.extension.equals(passed.extension);
            }

            return versionsEqual && extensionEqual;
         }
      } else {
         return false;
      }
   }

   public int compareTo(Object o) {
      assert o instanceof VersionInfo;

      VersionInfo c = (VersionInfo)o;
      return this.version.compareTo(c.version);
   }
}
