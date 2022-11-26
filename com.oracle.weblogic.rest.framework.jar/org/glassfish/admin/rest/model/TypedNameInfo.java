package org.glassfish.admin.rest.model;

public class TypedNameInfo implements Comparable {
   private String name;
   private String description;
   private TypeInfo type;

   TypedNameInfo(String name, String description, TypeInfo type) throws Exception {
      this.name = name;
      this.description = description;
      this.type = type;
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public TypeInfo getType() {
      return this.type;
   }

   public int compareTo(TypedNameInfo o) {
      return this.name.compareTo(o.name);
   }

   protected String toString(String namedType) {
      return namedType + "[name=" + this.getName() + ", description=" + this.getDescription() + "]";
   }
}
