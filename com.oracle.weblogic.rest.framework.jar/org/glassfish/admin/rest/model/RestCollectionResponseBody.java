package org.glassfish.admin.rest.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriInfo;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.composite.RestModel;
import org.glassfish.admin.rest.utils.JsonUtil;

public class RestCollectionResponseBody extends ResponseBody {
   private HttpServletRequest request;
   private UriInfo uriInfo;
   private String collectionName;
   private List items = new ArrayList();
   private List itemInfos = new ArrayList();

   public RestCollectionResponseBody(HttpServletRequest request, UriInfo uriInfo, String collectionName) {
      super(request);
      this.request = request;
      this.setCollectionName(collectionName);
      this.setUriInfo(uriInfo);
   }

   public String getCollectionName() {
      return this.collectionName;
   }

   public void setCollectionName(String collectionName) {
      this.collectionName = collectionName;
   }

   public UriInfo getUriInfo() {
      return this.uriInfo;
   }

   public void setUriInfo(UriInfo uriInfo) {
      this.uriInfo = uriInfo;
   }

   public List getItems() {
      return this.items;
   }

   public void setItems(List items) {
      this.items = items;
   }

   public void addItem(RestModel item, String name) {
      URI uri = this.collectionName != null ? this.getItemUri(this.uriInfo, name) : null;
      this.addItem(item, name, uri);
   }

   public void addItem(RestModel item, String name, URI uri) {
      this.addItem(item, this.collectionName, name, uri);
   }

   public void addItem(RestModel item, String collectionName, String name, URI uri) {
      this.getItems().add(item);
      this.itemInfos.add(new ItemInfo(item, collectionName, name, uri));
   }

   protected URI getItemUri(UriInfo uriInfo, String name) {
      return uriInfo.getAbsolutePathBuilder().segment(new String[]{name}).build(new Object[0]);
   }

   protected void populateJson(JSONObject object) throws JSONException {
      JSONArray array = new JSONArray();
      Iterator var3 = this.getItemInfos().iterator();

      while(var3.hasNext()) {
         ItemInfo ii = (ItemInfo)var3.next();
         array.put(this.itemToJson(ii.getItem(), ii.getCollectionName(), ii.getName(), ii.getUri()));
      }

      object.put("items", array);
      super.populateJson(object);
   }

   protected JSONObject itemToJson(RestModel item, String collectionName, String name, URI uri) throws JSONException {
      RestJsonResponseBody itemRb = new RestJsonResponseBody(this.request);
      itemRb.setEntity(JsonUtil.getJsonForRestModel(item, true));
      if (collectionName != null && uri != null) {
         itemRb.addResourceLink("canonical", uri);
         itemRb.addResourceLink("self", uri);
      }

      return itemRb.toJson();
   }

   protected List getItemInfos() {
      return this.itemInfos;
   }

   public static class ItemInfo {
      private RestModel item;
      private String collectionName;
      private String name;
      private URI uri;

      private ItemInfo(RestModel item, String collectionName, String name, URI uri) {
         this.item = item;
         this.collectionName = collectionName;
         this.name = name;
         this.uri = uri;
      }

      public RestModel getItem() {
         return this.item;
      }

      public String getCollectionName() {
         return this.collectionName;
      }

      public String getName() {
         return this.name;
      }

      public URI getUri() {
         return this.uri;
      }

      // $FF: synthetic method
      ItemInfo(RestModel x0, String x1, String x2, URI x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }
}
