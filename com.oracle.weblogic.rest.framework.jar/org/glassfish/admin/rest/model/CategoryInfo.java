package org.glassfish.admin.rest.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CategoryInfo implements Comparable {
   private String name;
   private String description;
   private CategoryInfo parent;
   private Set subCategories = new TreeSet();
   private Map nameToSubCategory = new HashMap();

   CategoryInfo(String name, String description, CategoryInfo parent) throws Exception {
      this.name = name;
      this.description = description;
      this.parent = parent;
   }

   public CategoryInfo createSubCategory(String name, String description) throws Exception {
      if (this.subCategoryExists(name)) {
         throw new AssertionError("Subcategory " + name + " already exists");
      } else {
         CategoryInfo subCategory = new CategoryInfo(name, description, this);
         this.nameToSubCategory.put(subCategory.getName(), subCategory);
         this.subCategories.add(subCategory);
         return subCategory;
      }
   }

   public boolean subCategoryExists(String name) {
      return this.getSubCategory(name) != null;
   }

   public CategoryInfo getSubCategory(String name) {
      return (CategoryInfo)this.nameToSubCategory.get(name);
   }

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public CategoryInfo getParent() {
      return this.parent;
   }

   public Set getSubCategories() {
      return this.subCategories;
   }

   public int compareTo(CategoryInfo o) {
      if (this.getParent() != o.getParent()) {
         throw new AssertionError("Can only compare categories that have the same parent");
      } else {
         return this.name.compareTo(o.name);
      }
   }

   public String toString() {
      return "CategoryInfo [name=" + this.getName() + ", description=" + this.getDescription() + ", parent=" + this.getParent().getName() + ", subCategories=" + this.nameToSubCategory.keySet() + "]";
   }
}
