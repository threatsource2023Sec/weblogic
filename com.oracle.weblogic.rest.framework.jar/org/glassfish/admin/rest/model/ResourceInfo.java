package org.glassfish.admin.rest.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ResourceInfo implements Comparable {
   private ApiInfo api;
   private String className;
   private String entityClassName;
   private String entityDisplayName;
   private String uri;
   private Map methods = new TreeMap();
   private Set pathParamNames;
   private CategoryInfo category;

   ResourceInfo(ApiInfo api, String className, String entityClassName, String entityDisplayName, String uri, Set pathParamNames) {
      this.api = api;
      this.className = className;
      this.entityClassName = entityClassName;
      this.entityDisplayName = entityDisplayName;
      this.uri = uri;
      this.pathParamNames = pathParamNames;
   }

   public ApiInfo getApi() {
      return this.api;
   }

   public String getClassName() {
      return this.className;
   }

   public String getEntityClassName() {
      return this.entityClassName;
   }

   public String getEntityDisplayName() {
      return this.entityDisplayName;
   }

   public String getUri() {
      return this.uri;
   }

   public Map getMethods() {
      return this.methods;
   }

   public Set getPathParamNames() {
      return this.pathParamNames;
   }

   public CategoryInfo getCategory() {
      return this.category;
   }

   public MethodInfo createMethod(String name, String verb, String summary, String description) throws Exception {
      MethodInfo method = new MethodInfo(this, name, verb, summary, description);
      List list = (List)this.methods.get(verb);
      if (list == null) {
         list = new LinkedList();
         this.methods.put(verb, list);
      }

      ((List)list).add(method);
      return method;
   }

   public void setCategory(CategoryInfo category) throws Exception {
      this.category = category;
   }

   public int compareTo(ResourceInfo o) {
      return this.getUri().compareTo(o.getUri());
   }

   public String toString() {
      return "ResourceInfo [className=" + this.getClassName() + ", entityClassName=" + this.getEntityClassName() + ", entityDisplayName=" + this.getEntityDisplayName() + ", uri=" + this.getUri() + ", methods=" + this.getMethods() + "]";
   }
}
