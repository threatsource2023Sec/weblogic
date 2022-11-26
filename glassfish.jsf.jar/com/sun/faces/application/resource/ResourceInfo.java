package com.sun.faces.application.resource;

public class ResourceInfo {
   ResourceHelper helper;
   LibraryInfo library;
   ContractInfo contract;
   String libraryName;
   String localePrefix;
   String name;
   String path;
   VersionInfo version;
   boolean doNotCache = false;

   public ResourceInfo(LibraryInfo library, ContractInfo contract, String name, VersionInfo version) {
      this.contract = contract;
      this.library = library;
      this.helper = library.getHelper();
      this.localePrefix = library.getLocalePrefix();
      this.name = name;
      this.version = version;
      this.libraryName = library.getName();
   }

   public ResourceInfo(ContractInfo contract, String name, VersionInfo version, ResourceHelper helper) {
      this.contract = contract;
      this.name = name;
      this.version = version;
      this.helper = helper;
   }

   public ResourceInfo(ResourceInfo other, boolean copyLocalePrefix) {
      this.helper = other.helper;
      this.library = new LibraryInfo(other.library, copyLocalePrefix);
      this.libraryName = this.library.getName();
      if (copyLocalePrefix) {
         this.localePrefix = other.localePrefix;
      }

      this.name = other.name;
      this.path = other.path;
      this.version = other.version;
   }

   public void copy(ResourceInfo other) {
      this.helper = other.helper;
      this.library = other.library;
      this.libraryName = other.libraryName;
      this.localePrefix = other.localePrefix;
      this.name = other.name;
      this.path = other.path;
      this.version = other.version;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         ResourceInfo other = (ResourceInfo)obj;
         if (this.helper == other.helper || this.helper != null && this.helper.equals(other.helper)) {
            if (this.library != other.library && (this.library == null || !this.library.equals(other.library))) {
               return false;
            } else {
               if (this.libraryName == null) {
                  if (other.libraryName != null) {
                     return false;
                  }
               } else if (!this.libraryName.equals(other.libraryName)) {
                  return false;
               }

               label73: {
                  if (this.localePrefix == null) {
                     if (other.localePrefix == null) {
                        break label73;
                     }
                  } else if (this.localePrefix.equals(other.localePrefix)) {
                     break label73;
                  }

                  return false;
               }

               if (this.name == null) {
                  if (other.name != null) {
                     return false;
                  }
               } else if (!this.name.equals(other.name)) {
                  return false;
               }

               if (this.path == null) {
                  if (other.path != null) {
                     return false;
                  }
               } else if (!this.path.equals(other.path)) {
                  return false;
               }

               if (this.version == other.version || this.version != null && this.version.equals(other.version)) {
                  if (this.doNotCache != other.doNotCache) {
                     return false;
                  } else {
                     return true;
                  }
               } else {
                  return false;
               }
            }
         } else {
            return false;
         }
      }
   }

   public int hashCode() {
      int hash = 7;
      hash = 17 * hash + (this.helper != null ? this.helper.hashCode() : 0);
      hash = 17 * hash + (this.library != null ? this.library.hashCode() : 0);
      hash = 17 * hash + (this.libraryName != null ? this.libraryName.hashCode() : 0);
      hash = 17 * hash + (this.localePrefix != null ? this.localePrefix.hashCode() : 0);
      hash = 17 * hash + (this.name != null ? this.name.hashCode() : 0);
      hash = 17 * hash + (this.path != null ? this.path.hashCode() : 0);
      hash = 17 * hash + (this.version != null ? this.version.hashCode() : 0);
      hash = 17 * hash + (this.doNotCache ? 1 : 0);
      return hash;
   }

   public boolean isDoNotCache() {
      return this.doNotCache;
   }

   public void setDoNotCache(boolean doNotCache) {
      this.doNotCache = doNotCache;
   }

   public ResourceHelper getHelper() {
      return this.helper;
   }

   public LibraryInfo getLibraryInfo() {
      return this.library;
   }

   public String getLocalePrefix() {
      return this.localePrefix;
   }

   public String getName() {
      return this.name;
   }

   public String getPath() {
      return this.path;
   }

   public String getContract() {
      return null != this.contract ? this.contract.toString() : null;
   }

   public VersionInfo getVersion() {
      return this.version;
   }
}
