package org.glassfish.admin.rest.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.utils.ExceptionUtil;
import org.glassfish.admin.rest.utils.JsonFilter;

public class ResponseBody {
   private List messages;
   private boolean includeResourceLinks;
   private List links;
   private JsonFilter.Scope filter;

   public ResponseBody(HttpServletRequest request, URI parentUri) {
      this(request, parentUri, (JsonFilter)null);
   }

   public ResponseBody(HttpServletRequest request, URI parentUri, JsonFilter filter) {
      this(request, filter);
      this.addParentResourceLink(parentUri);
   }

   public ResponseBody(HttpServletRequest request) {
      this(request, (JsonFilter)null);
   }

   public ResponseBody(HttpServletRequest request, JsonFilter filter) {
      this(!Boolean.valueOf(request.getHeader("X-Skip-Resource-Links")), filter);
   }

   public ResponseBody(boolean includeResourceLinks) {
      this(includeResourceLinks, (JsonFilter)null);
   }

   public ResponseBody(boolean includeResourceLinks, JsonFilter filter) {
      this.messages = new ArrayList();
      this.includeResourceLinks = true;
      this.links = new ArrayList();
      this.filter = null;
      this.includeResourceLinks = includeResourceLinks;
      if (filter == null) {
         filter = new JsonFilter();
      }

      this.filter = filter.newScope();
   }

   public List getMessages() {
      return this.messages;
   }

   public void setMessages(List val) {
      this.messages = val;
   }

   public ResponseBody addSuccess(String message) {
      return this.addMessage(Message.Severity.SUCCESS, message);
   }

   public ResponseBody addWarning(String message) {
      return this.addMessage(Message.Severity.WARNING, message);
   }

   public ResponseBody addWarning(String field, String message) {
      return this.addMessage(Message.Severity.WARNING, field, message);
   }

   public ResponseBody addFailure(Throwable t) {
      this.addFailure(ExceptionUtil.getThrowableMessage(t));
      return this;
   }

   public ResponseBody addFailure(String message) {
      return this.addMessage(Message.Severity.FAILURE, message);
   }

   public ResponseBody addFailure(String field, String message) {
      return this.addMessage(Message.Severity.FAILURE, field, message);
   }

   public ResponseBody addMessage(Message.Severity severity, String field, String message) {
      return this.add(new Message(severity, field, message));
   }

   public ResponseBody addMessage(Message.Severity severity, String message) {
      return this.add(new Message(severity, message));
   }

   public ResponseBody add(Message message) {
      this.getMessages().add(message);
      return this;
   }

   public List getResourceLinks() {
      return this.links;
   }

   public void setResourceLinks(List val) {
      this.links = val;
   }

   public ResponseBody addSelfResourceLinks(URI uri) {
      this.addResourceLink("self", uri);
      this.addResourceLink("canonical", uri);
      return this;
   }

   public ResponseBody addParentResourceLink(URI uri) {
      return uri == null ? this : this.addResourceLink("parent", uri);
   }

   public void addActionResourceLink(String action, URI uri) {
      this.addResourceLink("action", action, uri);
   }

   public ResponseBody addResourceLink(String rel, URI uri) {
      return this.add(new ResourceLink(rel, uri));
   }

   public ResponseBody addResourceLink(String rel, String title, URI uri) {
      return this.add(new ResourceLink(rel, title, uri));
   }

   public ResponseBody add(ResourceLink link) {
      if (this.filter.include(link.getRelationship())) {
         this.getResourceLinks().add(link);
      }

      return this;
   }

   public JSONObject toJson() throws JSONException {
      JSONObject object = new JSONObject();
      this.populateJson(object);
      return object;
   }

   protected void populateJson(JSONObject object) throws JSONException {
      JSONArray links;
      if (!this.getMessages().isEmpty()) {
         links = new JSONArray();
         Iterator var3 = this.getMessages().iterator();

         while(var3.hasNext()) {
            Message message = (Message)var3.next();
            links.put(message.toJson());
         }

         object.put("messages", links);
      }

      if (this.includeResourceLinks) {
         links = this.getLinksJson();
         if (links.length() > 0) {
            object.put("links", links);
         }
      }

   }

   protected JSONArray getLinksJson() throws JSONException {
      JSONArray array = new JSONArray();
      Iterator var2 = this.getResourceLinks().iterator();

      while(var2.hasNext()) {
         ResourceLink link = (ResourceLink)var2.next();
         array.put(this.getLinkJson(link));
      }

      return array;
   }

   protected JSONObject getLinkJson(ResourceLink link) throws JSONException {
      return link.toJson(this.getLinkUriPropertyName());
   }

   protected String getLinkUriPropertyName() {
      return "href";
   }
}
