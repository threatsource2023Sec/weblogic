package weblogic.ejb.container.persistence;

import java.util.HashSet;
import java.util.Set;
import weblogic.ejb.container.persistence.spi.Dependent;

public final class DependentImpl implements Dependent {
   private String description;
   private String dependentClassName;
   private String name;
   private Set fields = new HashSet();
   private Set pkFields = new HashSet();

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getDependentClassName() {
      return this.dependentClassName;
   }

   public void setDependentClassName(String name) {
      this.dependentClassName = name;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Set getCMFieldNames() {
      return this.fields;
   }

   public void addCMFieldName(String name) {
      this.fields.add(name);
   }

   public Set getPrimaryKeyFieldNames() {
      return this.pkFields;
   }

   public void addPrimaryKeyFieldName(String name) {
      this.pkFields.add(name);
   }
}
