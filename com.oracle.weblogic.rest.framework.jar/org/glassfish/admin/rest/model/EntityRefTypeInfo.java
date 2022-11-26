package org.glassfish.admin.rest.model;

public class EntityRefTypeInfo implements TypeInfo {
   private String entityDisplayName;
   private String entityClassName;

   public EntityRefTypeInfo(String entityDisplayName, String entityClassName) {
      this.entityDisplayName = entityDisplayName;
      this.entityClassName = entityClassName;
   }

   public String getEntityDisplayName() {
      return this.entityDisplayName;
   }

   public String getEntityClassName() {
      return this.entityClassName;
   }

   public String toString() {
      return "EntityRefTypeInfo [entityDisplayName=" + this.getEntityDisplayName() + ", entityClassName=" + this.getEntityClassName() + "]";
   }
}
