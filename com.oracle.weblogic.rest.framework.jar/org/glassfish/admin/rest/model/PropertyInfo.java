package org.glassfish.admin.rest.model;

import java.util.LinkedList;
import java.util.List;

public class PropertyInfo implements Comparable {
   private ObjectTypeInfo objectType;
   private String name;
   private TypeInfo type;
   private boolean required;
   private String description;
   private List constraints = new LinkedList();

   PropertyInfo(ObjectTypeInfo objectType, String name, TypeInfo type, String description) throws Exception {
      this.objectType = objectType;
      this.name = name;
      this.type = type;
      this.description = description;
   }

   public ObjectTypeInfo getObjectType() {
      return this.objectType;
   }

   public String getName() {
      return this.name;
   }

   public TypeInfo getType() {
      return this.type;
   }

   public String getDescription() {
      return this.description;
   }

   public List getConstraints() {
      return this.constraints;
   }

   public void addConstraint(ConstraintInfo constraint) {
      this.constraints.add(constraint);
   }

   public void setRequired(boolean required) {
      this.required = required;
   }

   public boolean isRequired() {
      return this.required;
   }

   public int compareTo(PropertyInfo o) {
      return this.name.compareTo(o.name);
   }

   public String toString() {
      return "PropertyInfo [name=" + this.getName() + ", type=" + this.getType() + ", description=" + this.getDescription() + ", constraints=" + this.getConstraints() + "]";
   }
}
