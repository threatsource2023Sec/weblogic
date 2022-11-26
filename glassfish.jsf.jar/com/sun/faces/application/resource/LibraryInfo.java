package com.sun.faces.application.resource;

public class LibraryInfo {
   private String name;
   private VersionInfo version;
   private String localePrefix;
   private String contract;
   private ResourceHelper helper;
   private String path;
   private String nonLocalizedPath;

   LibraryInfo(String name, VersionInfo version, String localePrefix, String contract, ResourceHelper helper) {
      this.name = name;
      this.version = version;
      this.localePrefix = localePrefix;
      this.contract = contract;
      this.helper = helper;
      this.initPath();
   }

   LibraryInfo(LibraryInfo other, boolean copyLocalePrefix) {
      this.name = other.name;
      this.version = other.version;
      if (copyLocalePrefix) {
         this.contract = other.contract;
         this.localePrefix = other.localePrefix;
      }

      this.helper = other.helper;
      this.initPath();
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else if (this == obj) {
         return true;
      } else {
         LibraryInfo other;
         label71: {
            other = (LibraryInfo)obj;
            if (this.name == null) {
               if (other.name == null) {
                  break label71;
               }
            } else if (this.name.equals(other.name)) {
               break label71;
            }

            return false;
         }

         if (this.version != other.version && (this.version == null || !this.version.equals(other.version))) {
            return false;
         } else {
            label56: {
               if (this.localePrefix == null) {
                  if (other.localePrefix == null) {
                     break label56;
                  }
               } else if (this.localePrefix.equals(other.localePrefix)) {
                  break label56;
               }

               return false;
            }

            if (this.contract == null) {
               if (other.contract != null) {
                  return false;
               }
            } else if (!this.contract.equals(other.contract)) {
               return false;
            }

            if (this.path == null) {
               if (other.path != null) {
                  return false;
               }
            } else if (!this.path.equals(other.path)) {
               return false;
            }

            return true;
         }
      }
   }

   public int hashCode() {
      int hash = 5;
      hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
      hash = 37 * hash + (this.version != null ? this.version.hashCode() : 0);
      hash = 37 * hash + (this.localePrefix != null ? this.localePrefix.hashCode() : 0);
      hash = 37 * hash + (this.contract != null ? this.contract.hashCode() : 0);
      hash = 37 * hash + (this.path != null ? this.path.hashCode() : 0);
      return hash;
   }

   public String getName() {
      return this.name;
   }

   public VersionInfo getVersion() {
      return this.version;
   }

   public ResourceHelper getHelper() {
      return this.helper;
   }

   public String getPath() {
      return this.path;
   }

   public String getPath(String localePrefix) {
      String result = null;
      if (null == localePrefix) {
         result = this.nonLocalizedPath;
      } else {
         result = this.path;
      }

      return result;
   }

   public String getLocalePrefix() {
      return this.localePrefix;
   }

   public String getContract() {
      return this.contract;
   }

   public String toString() {
      return "LibraryInfo{name='" + (this.name != null ? this.name : "NONE") + '\'' + ", version=" + (this.version != null ? this.version : "NONE") + '\'' + ", localePrefix='" + (this.localePrefix != null ? this.localePrefix : "NONE") + '\'' + ", contract='" + (this.contract != null ? this.contract : "NONE") + '\'' + ", path='" + this.path + '\'' + '}';
   }

   private void initPath() {
      StringBuilder builder = new StringBuilder(64);
      StringBuilder noLocaleBuilder = new StringBuilder(64);
      this.appendBasePath(builder);
      this.appendBasePath(noLocaleBuilder);
      if (this.localePrefix != null) {
         builder.append('/').append(this.localePrefix);
      }

      if (this.name != null) {
         builder.append('/').append(this.name);
         noLocaleBuilder.append('/').append(this.name);
      }

      if (this.version != null) {
         builder.append('/').append(this.version.getVersion());
         noLocaleBuilder.append('/').append(this.version.getVersion());
      }

      this.path = builder.toString();
      this.nonLocalizedPath = noLocaleBuilder.toString();
   }

   private void appendBasePath(StringBuilder builder) {
      if (this.contract == null) {
         builder.append(this.helper.getBaseResourcePath());
      } else {
         builder.append(this.helper.getBaseContractsPath()).append('/').append(this.contract);
      }

   }
}
