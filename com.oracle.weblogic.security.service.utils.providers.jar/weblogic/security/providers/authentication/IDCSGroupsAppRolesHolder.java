package weblogic.security.providers.authentication;

import java.util.Collections;
import java.util.Set;

public class IDCSGroupsAppRolesHolder {
   private String id;
   private Set groups;
   private Set appRoles;
   private String tenant;

   public IDCSGroupsAppRolesHolder(Set groups, Set appRoles, String tenant) {
      this((String)null, groups, appRoles, tenant);
   }

   public IDCSGroupsAppRolesHolder(String id, Set groups, Set appRoles, String tenant) {
      this.id = id;
      this.groups = groups != null ? Collections.unmodifiableSet(groups) : null;
      this.appRoles = appRoles != null ? Collections.unmodifiableSet(appRoles) : null;
      this.tenant = tenant;
   }

   public String getId() {
      return this.id;
   }

   public Set getGroups() {
      return this.groups;
   }

   public Set getAppRoles() {
      return this.appRoles;
   }

   public String getTenant() {
      return this.tenant;
   }

   public String toString() {
      return "id: " + this.id + " - groups: " + this.groups + " - approles: " + this.appRoles + " - tenant: " + this.tenant;
   }

   public static class Entry {
      private String name;
      private String guid;
      private String reference;
      private String appName;
      private String appGuid;

      public Entry(String name, String guid) {
         this.reference = null;
         this.appName = null;
         this.appGuid = null;
         if (name == null) {
            throw new IllegalArgumentException("Entry name must not be null");
         } else {
            this.name = name;
            this.guid = guid;
         }
      }

      public Entry(String name, String guid, String reference) {
         this(name, guid);
         this.reference = reference;
      }

      public Entry(String name, String guid, String reference, String appName, String appGuid) {
         this(name, guid, reference);
         this.appName = appName;
         this.appGuid = appGuid;
      }

      public String getName() {
         return this.name;
      }

      public String getGuid() {
         return this.guid;
      }

      public String getReference() {
         return this.reference;
      }

      public String getAppName() {
         return this.appName;
      }

      public String getAppGuid() {
         return this.appGuid;
      }

      public int hashCode() {
         String concat = getConcat(this.name, this.guid, this.reference, this.appName, this.appGuid);
         return concat.hashCode();
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (other == null) {
            return false;
         } else if (this.getClass() != other.getClass()) {
            return false;
         } else {
            String concat = getConcat(this.name, this.guid, this.reference, this.appName, this.appGuid);
            String concatOther = getConcat((Entry)other);
            return concat.equalsIgnoreCase(concatOther);
         }
      }

      public String toString() {
         return "Name: " + this.name + " (" + this.guid + ") - $ref: " + this.reference + (this.appName == null ? "" : " - App: " + this.appName + " (" + this.appGuid + ")");
      }

      private static String getConcat(Entry other) {
         return getConcat(other.name, other.guid, other.reference, other.appName, other.appGuid);
      }

      private static String getConcat(String n, String id, String ref, String app, String appId) {
         StringBuffer sb = new StringBuffer(n);
         if (id != null) {
            sb.append(id);
         }

         if (ref != null) {
            sb.append(ref);
         }

         if (app != null) {
            sb.append(app);
         }

         if (appId != null) {
            sb.append(appId);
         }

         return sb.toString();
      }
   }
}
