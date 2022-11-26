package org.glassfish.admin.rest.model;

public class EntityInfo extends ObjectTypeInfo implements Comparable {
   private ApiInfo api;
   private String className;
   private String description;

   EntityInfo(ApiInfo api, String className, String displayName, String description) {
      super(displayName);
      this.api = api;
      this.className = className;
      this.description = description;
   }

   public ApiInfo getApi() {
      return this.api;
   }

   public String getClassName() {
      return this.className;
   }

   public String getDescription() {
      return this.description;
   }

   public int compareTo(EntityInfo o) {
      return this.getDisplayName().compareTo(o.getDisplayName());
   }

   public String toString() {
      return "EntityInfo [className=" + this.getClassName() + ", displayName=" + this.getDisplayName() + ", description=" + this.getDescription() + "]";
   }
}
