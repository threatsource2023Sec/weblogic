package org.glassfish.admin.rest.model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.ws.rs.core.Response.Status;

public class MethodInfo {
   public static final String GET = "GET";
   public static final String POST = "POST";
   public static final String DELETE = "DELETE";
   public static final int OK;
   public static final int CREATED;
   public static final int ACCEPTED;
   private ResourceInfo resource;
   private String name = "";
   private String consumes = "application/json";
   private String produces = "application/json";
   private int statusCode;
   private String verb;
   private String summary;
   private String description;
   private Set roles;
   private TypeInfo requestBodyType;
   private TypeInfo responseBodyType;
   private String requestBodyDesc;
   private String responseBodyDesc;
   private List examples;
   private Set links;
   private ParamNames queryParamNames;
   private ParamNames requestHeaderNames;
   private ParamNames responseHeaderNames;
   private boolean supportsAsync;
   private String scope;

   MethodInfo(ResourceInfo resource, String name, String verb, String summary, String description) {
      this.statusCode = OK;
      this.verb = "";
      this.summary = "";
      this.description = "";
      this.roles = new TreeSet();
      this.requestBodyDesc = "";
      this.responseBodyDesc = "";
      this.examples = new LinkedList();
      this.links = new TreeSet();
      this.queryParamNames = new ParamNames();
      this.requestHeaderNames = new ParamNames();
      this.responseHeaderNames = new ParamNames();
      this.resource = resource;
      this.name = name;
      this.verb = verb;
      this.summary = summary;
      this.description = description;
      this.setScope("/" + verb);
   }

   public ResourceInfo getResource() {
      return this.resource;
   }

   public String getName() {
      return this.name;
   }

   public String getVerb() {
      return this.verb;
   }

   public String getDescription() {
      return this.description;
   }

   public String getSummary() {
      return this.summary;
   }

   public Set getQueryParamNames() {
      return this.queryParamNames;
   }

   public Set getRequestHeaderNames() {
      return this.requestHeaderNames;
   }

   public Set getResponseHeaderNames() {
      return this.responseHeaderNames;
   }

   public void addQueryParamName(String queryParamName) {
      this.getQueryParamNames().add(queryParamName);
   }

   public void addRequestHeaderName(String requestHeaderName) {
      this.getRequestHeaderNames().add(requestHeaderName);
   }

   public void addResponseHeaderName(String responseHeaderName) {
      this.getResponseHeaderNames().add(responseHeaderName);
   }

   public String getConsumes() {
      return this.consumes;
   }

   public void setConsumes(String consumes) {
      this.consumes = consumes;
   }

   public String getProduces() {
      return this.produces;
   }

   public void setProduces(String produces) {
      this.produces = produces;
   }

   public Set getRoles() {
      return this.roles;
   }

   public void setRoles(Set roles) {
      this.roles.clear();
      if (roles != null) {
         Iterator var2 = roles.iterator();

         while(var2.hasNext()) {
            String role = (String)var2.next();
            String r = role.substring(0, 1).toUpperCase() + role.substring(1, role.length());
            this.roles.add(r);
         }
      }

   }

   public int getStatusCode() {
      return this.statusCode;
   }

   public void setStatusCode(int statusCode) {
      this.statusCode = statusCode;
   }

   public ExampleInfo createExample(String title) {
      ExampleInfo example = new ExampleInfo(this, title);
      this.examples.add(example);
      return example;
   }

   public List getExamples() {
      return this.examples;
   }

   public TypeInfo getRequestBodyType() {
      return this.requestBodyType;
   }

   public void setRequestBodyType(TypeInfo requestBodyType) {
      this.requestBodyType = requestBodyType;
   }

   public TypeInfo getResponseBodyType() {
      return this.responseBodyType;
   }

   public void setResponseBodyType(TypeInfo responseBodyType) {
      this.responseBodyType = responseBodyType;
   }

   public String getRequestBodyDesc() {
      return this.requestBodyDesc;
   }

   public void setRequestBodyDesc(String requestBodyDesc) {
      this.requestBodyDesc = requestBodyDesc;
   }

   public String getResponseBodyDesc() {
      return this.responseBodyDesc;
   }

   public void setResponseBodyDesc(String responseBodyDesc) {
      this.responseBodyDesc = responseBodyDesc;
   }

   public Set getLinks() {
      return this.links;
   }

   public LinkInfo createLink(String rel, String uri, String title, String desc) {
      LinkInfo link = new LinkInfo(this, rel, uri, title, desc);
      if (this.links.contains(link)) {
         throw new AssertionError("Link already exists : " + link);
      } else {
         this.links.add(link);
         return link;
      }
   }

   public boolean supportsAsync() {
      return this.supportsAsync;
   }

   public void setSupportsAsync(boolean val) {
      this.supportsAsync = val;
   }

   public String getScope() {
      return this.scope;
   }

   public void setScope(String val) {
      this.scope = val;
   }

   public String toString() {
      return "MethodInfo [consumes=" + this.getConsumes() + ", produces=" + this.getProduces() + ", verb=" + this.getVerb() + ", statusCode=" + this.getStatusCode() + ", roles=" + this.getRoles() + ", supportsAsync=" + this.supportsAsync() + ", scope=" + this.getScope() + ", description=" + this.getDescription() + ", summary=" + this.getSummary() + ", requestBodyType=" + this.getRequestBodyType() + ", requestBodyDesc=" + this.getRequestBodyDesc() + ", responseBodyType=" + this.getResponseBodyType() + ", responseBodyDesc=" + this.getResponseBodyDesc() + ", examples=" + this.getExamples() + "]";
   }

   static {
      OK = Status.OK.getStatusCode();
      CREATED = Status.CREATED.getStatusCode();
      ACCEPTED = Status.ACCEPTED.getStatusCode();
   }

   private class ParamNames extends TreeSet {
      private ParamNames() {
      }

      public boolean add(String val) {
         if (this.contains(val)) {
            throw new AssertionError("Param name " + val + " already exists");
         } else {
            return super.add(val);
         }
      }

      // $FF: synthetic method
      ParamNames(Object x1) {
         this();
      }
   }
}
