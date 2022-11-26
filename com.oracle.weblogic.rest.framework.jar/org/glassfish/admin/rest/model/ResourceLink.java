package org.glassfish.admin.rest.model;

import java.net.URI;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class ResourceLink {
   private String rel;
   private String title;
   private URI uri;

   public ResourceLink(String rel, String title, URI uri) {
      this.rel = rel;
      this.title = title;
      this.uri = uri;
   }

   public ResourceLink(String rel, URI uri) {
      this.rel = rel;
      this.uri = uri;
   }

   public URI getURI() {
      return this.uri;
   }

   public void setURI(URI val) {
      this.uri = val;
   }

   public String getRelationship() {
      return this.rel;
   }

   public void setRelationship(String val) {
      this.rel = val;
   }

   public String getTitle() {
      return this.title;
   }

   public void setTitle(String val) {
      this.title = val;
   }

   public JSONObject toJson(String uriPropertyName) throws JSONException {
      JSONObject object = new JSONObject();
      object.put("rel", this.getRelationship());
      object.put(uriPropertyName, this.getURI().toASCIIString());
      String t = this.getTitle();
      if (t != null && t.length() > 0) {
         object.put("title", t);
      }

      return object;
   }
}
