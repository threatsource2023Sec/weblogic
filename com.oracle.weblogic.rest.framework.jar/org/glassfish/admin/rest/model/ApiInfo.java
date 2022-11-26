package org.glassfish.admin.rest.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ApiInfo {
   private String description;
   private String title;
   private String version;
   private NamedAndSortedSet resources = new NamedAndSortedSet("Resource");
   private NamedAndSortedSet entities = new NamedAndSortedSet("Entity");
   private NamedAndSortedSet categories = new NamedAndSortedSet("Category");
   private NamedAndSortedSet pathParams = new NamedAndSortedSet("PathParam");
   private NamedAndSortedSet queryParams = new NamedAndSortedSet("QueryParam");
   private NamedAndSortedSet requestHeaders = new NamedAndSortedSet("RequestHeader");
   private NamedAndSortedSet responseHeaders = new NamedAndSortedSet("ResponseHeader");

   public ApiInfo(String description, String title, String version) {
      this.description = description;
      this.title = title;
      this.version = version;
   }

   public String getDescription() {
      return this.description;
   }

   public String getTitle() {
      return this.title;
   }

   public String getVersion() {
      return this.version;
   }

   public ResourceInfo createResource(String className, String entityClassName, String entityDisplayName, String uri, Set pathParamNames) throws Exception {
      return (ResourceInfo)this.resources.add(uri, new ResourceInfo(this, className, entityClassName, entityDisplayName, uri, pathParamNames));
   }

   public boolean resourceExists(String uri) {
      return this.resources.exists(uri);
   }

   public ResourceInfo getResource(String uri) {
      return (ResourceInfo)this.resources.get(uri);
   }

   public Set getResources() {
      return this.resources.vals();
   }

   public EntityInfo createEntity(String className, String displayName, String description) throws Exception {
      return (EntityInfo)this.entities.add(className, new EntityInfo(this, className, displayName, description));
   }

   public boolean entityExists(String className) {
      return this.entities.exists(className);
   }

   public EntityInfo getEntity(String className) {
      return (EntityInfo)this.entities.get(className);
   }

   public Set getEntities() {
      return this.entities.vals();
   }

   public CategoryInfo createCategory(String name, String description) throws Exception {
      return (CategoryInfo)this.categories.add(name, new CategoryInfo(name, description, (CategoryInfo)null));
   }

   public boolean categoryExists(String name) {
      return this.categories.exists(name);
   }

   public CategoryInfo getCategory(String name) {
      return (CategoryInfo)this.categories.get(name);
   }

   public Set getCategories() {
      return this.categories.vals();
   }

   public PathParamInfo createPathParam(String name, String description, TypeInfo type) throws Exception {
      return (PathParamInfo)this.pathParams.add(name, new PathParamInfo(name, description, type));
   }

   public boolean pathParamExists(String name) {
      return this.pathParams.exists(name);
   }

   public PathParamInfo getPathParam(String name) {
      return (PathParamInfo)this.pathParams.get(name);
   }

   public Set getPathParams() {
      return this.pathParams.vals();
   }

   public QueryParamInfo createQueryParam(String name, String description, TypeInfo type, boolean isRequired) throws Exception {
      return (QueryParamInfo)this.queryParams.add(name, new QueryParamInfo(name, description, type, isRequired));
   }

   public boolean queryParamExists(String name) {
      return this.queryParams.exists(name);
   }

   public QueryParamInfo getQueryParam(String name) {
      return (QueryParamInfo)this.queryParams.get(name);
   }

   public Set getQueryParams() {
      return this.queryParams.vals();
   }

   public RequestHeaderInfo createRequestHeader(String name, String description, TypeInfo type, boolean isRequired) throws Exception {
      return (RequestHeaderInfo)this.requestHeaders.add(name, new RequestHeaderInfo(name, description, type, isRequired));
   }

   public boolean requestHeaderExists(String name) {
      return this.requestHeaders.exists(name);
   }

   public RequestHeaderInfo getRequestHeaders(String name) {
      return (RequestHeaderInfo)this.requestHeaders.get(name);
   }

   public Set getRequestHeaders() {
      return this.requestHeaders.vals();
   }

   public ResponseHeaderInfo createResponseHeader(String name, String description, TypeInfo type) throws Exception {
      return (ResponseHeaderInfo)this.responseHeaders.add(name, new ResponseHeaderInfo(name, description, type));
   }

   public boolean responseHeaderExists(String name) {
      return this.responseHeaders.exists(name);
   }

   public ResponseHeaderInfo getResponseHeaders(String name) {
      return (ResponseHeaderInfo)this.responseHeaders.get(name);
   }

   public Set getResponseHeaders() {
      return this.responseHeaders.vals();
   }

   public String toString() {
      return "ApiInfo [description=" + this.getDescription() + ", title=" + this.getTitle() + ", version=" + this.getVersion() + "]";
   }

   private class NamedAndSortedSet {
      private String context;
      private Set vals;
      private Map nameToVal;

      private NamedAndSortedSet(String context) {
         this.vals = new TreeSet();
         this.nameToVal = new HashMap();
         this.context = context;
      }

      private Object add(String name, Object val) throws Exception {
         if (this.exists(name)) {
            throw new AssertionError(this.context + " " + name + " already exists");
         } else {
            this.nameToVal.put(name, val);
            this.vals().add(val);
            return val;
         }
      }

      private boolean exists(String name) {
         return this.get(name) != null;
      }

      private Object get(String name) {
         return this.nameToVal.get(name);
      }

      private Set vals() {
         return this.vals;
      }

      // $FF: synthetic method
      NamedAndSortedSet(String x1, Object x2) {
         this(x1);
      }
   }
}
