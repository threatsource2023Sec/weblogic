package weblogic.management.rest.lib.bean.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.debug.DebugLogger;
import org.glassfish.admin.rest.model.Message;
import org.glassfish.admin.rest.model.ResourceLink;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import org.glassfish.admin.rest.model.Message.Severity;
import org.glassfish.admin.rest.utils.AsyncUtil;
import org.glassfish.admin.rest.utils.JsonFilter;
import org.glassfish.admin.rest.utils.JsonUtil;
import org.glassfish.admin.rest.utils.MultipartRequestMap;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jettison.JettisonFeature;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.StreamDataBodyPart;

public class RestInvoker {
   public static final String OPTIONS = "OPTIONS";
   public static final String DELETE = "DELETE";
   public static final String GET = "GET";
   public static final String POST = "POST";
   public static final String PUT = "PUT";
   public static final String HEADER_RETURN_JSON_STREAMING_ALLOWED_HEADER = "X-Return_Json_Streaming_Allowed_Header";
   public static final String HEADER_JSON_STREAMING_ALLOWED = "X-Json-Streaming_Allowed";
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(RestInvoker.class);

   public static QueryParams queryParams() throws Exception {
      return new QueryParams();
   }

   public static ResponseWrapper options(InvocationContext ic, String url, QueryParams queryParams) throws Exception {
      return options(ic, url, queryParams.toMap());
   }

   public static ResponseWrapper options(InvocationContext ic, String url, MultivaluedMap queryParams) throws Exception {
      return options(ic, url, (MultivaluedMap)queryParams, (ResiliencyUtils.ResiliencyConfig)null);
   }

   public static ResponseWrapper options(InvocationContext ic, String url, QueryParams queryParams, ResiliencyUtils.ResiliencyConfig rc) throws Exception {
      return options(ic, url, queryParams.toMap(), rc);
   }

   public static ResponseWrapper options(InvocationContext ic, String url, MultivaluedMap queryParams, ResiliencyUtils.ResiliencyConfig rc) throws Exception {
      return methodJson(ic, "OPTIONS", url, (JSONObject)null, queryParams, rc);
   }

   public static ResponseWrapper get(InvocationContext ic, String url, QueryParams queryParams) throws Exception {
      return get(ic, url, queryParams.toMap());
   }

   public static ResponseWrapper get(InvocationContext ic, String url, MultivaluedMap queryParams) throws Exception {
      return get(ic, url, (MultivaluedMap)queryParams, (ResiliencyUtils.ResiliencyConfig)null);
   }

   public static ResponseWrapper get(InvocationContext ic, String url, QueryParams queryParams, ResiliencyUtils.ResiliencyConfig rc) throws Exception {
      return get(ic, url, queryParams.toMap(), rc);
   }

   public static ResponseWrapper get(InvocationContext ic, String url, MultivaluedMap queryParams, ResiliencyUtils.ResiliencyConfig rc) throws Exception {
      return methodJson(ic, "GET", url, (JSONObject)null, queryParams, rc);
   }

   public static ResponseWrapper post(InvocationContext ic, String url, JSONObject params, QueryParams queryParams) throws Exception {
      return post(ic, url, params, queryParams.toMap());
   }

   public static ResponseWrapper post(InvocationContext ic, String url, JSONObject params, MultivaluedMap queryParams) throws Exception {
      return post(ic, url, params, (MultivaluedMap)queryParams, (ResiliencyUtils.ResiliencyConfig)null);
   }

   public static ResponseWrapper post(InvocationContext ic, String url, JSONObject params, QueryParams queryParams, ResiliencyUtils.ResiliencyConfig rc) throws Exception {
      return post(ic, url, params, queryParams.toMap(), rc);
   }

   public static ResponseWrapper post(InvocationContext ic, String url, JSONObject params, MultivaluedMap queryParams, ResiliencyUtils.ResiliencyConfig rc) throws Exception {
      return methodJson(ic, "POST", url, params, queryParams, rc);
   }

   public static ResponseWrapper postMultipart(InvocationContext ic, String url, MultipartRequestMap params, QueryParams queryParams) throws Exception {
      return postMultipart(ic, url, params, queryParams.toMap());
   }

   public static ResponseWrapper postMultipart(InvocationContext ic, String url, MultipartRequestMap params, MultivaluedMap queryParams) throws Exception {
      return methodMultipart(ic, "POST", url, params, queryParams);
   }

   public static ResponseWrapper put(InvocationContext ic, String url, JSONObject params, QueryParams queryParams) throws Exception {
      return put(ic, url, params, queryParams.toMap());
   }

   public static ResponseWrapper put(InvocationContext ic, String url, JSONObject params, MultivaluedMap queryParams) throws Exception {
      return put(ic, url, params, (MultivaluedMap)queryParams, (ResiliencyUtils.ResiliencyConfig)null);
   }

   public static ResponseWrapper put(InvocationContext ic, String url, JSONObject params, QueryParams queryParams, ResiliencyUtils.ResiliencyConfig rc) throws Exception {
      return put(ic, url, params, queryParams.toMap(), rc);
   }

   public static ResponseWrapper put(InvocationContext ic, String url, JSONObject params, MultivaluedMap queryParams, ResiliencyUtils.ResiliencyConfig rc) throws Exception {
      return methodJson(ic, "PUT", url, params, queryParams, rc);
   }

   public static ResponseWrapper putMultipart(InvocationContext ic, String url, MultipartRequestMap params, QueryParams queryParams) throws Exception {
      return putMultipart(ic, url, params, queryParams.toMap());
   }

   public static ResponseWrapper putMultipart(InvocationContext ic, String url, MultipartRequestMap params, MultivaluedMap queryParams) throws Exception {
      return methodMultipart(ic, "PUT", url, params, queryParams);
   }

   public static ResponseWrapper delete(InvocationContext ic, String url, QueryParams queryParams) throws Exception {
      return delete(ic, url, queryParams.toMap());
   }

   public static ResponseWrapper delete(InvocationContext ic, String url, MultivaluedMap queryParams) throws Exception {
      return delete(ic, url, (MultivaluedMap)queryParams, (ResiliencyUtils.ResiliencyConfig)null);
   }

   public static ResponseWrapper delete(InvocationContext ic, String url, QueryParams queryParams, ResiliencyUtils.ResiliencyConfig rc) throws Exception {
      return delete(ic, url, queryParams.toMap(), rc);
   }

   public static ResponseWrapper delete(InvocationContext ic, String url, MultivaluedMap queryParams, ResiliencyUtils.ResiliencyConfig rc) throws Exception {
      return methodJson(ic, "DELETE", url, (JSONObject)null, queryParams, rc);
   }

   public static ResponseWrapper methodJson(InvocationContext ic, String method, String url, JSONObject params, MultivaluedMap queryParams, ResiliencyUtils.ResiliencyConfig rc) throws Exception {
      return methodJson(ic, method, url, params, queryParams, false, rc);
   }

   public static ResponseWrapper methodJson(InvocationContext ic, String method, String url, JSONObject params, MultivaluedMap queryParams, boolean streamResponse, ResiliencyUtils.ResiliencyConfig rc) throws Exception {
      Entity entity = params != null ? Entity.json(params) : null;
      return methodEntity(ic, method, url, entity, queryParams, streamResponse, rc);
   }

   public static ResponseWrapper methodMultipart(InvocationContext ic, String method, String url, MultipartRequestMap params, MultivaluedMap queryParams) throws Exception {
      return methodMultipart(ic, method, url, params, queryParams, false);
   }

   public static ResponseWrapper methodMultipart(InvocationContext ic, String method, String url, MultipartRequestMap params, MultivaluedMap queryParams, boolean streamResponse) throws Exception {
      FormDataMultiPart parts = new FormDataMultiPart();
      Iterator var7 = params.keySet().iterator();

      while(var7.hasNext()) {
         String name = (String)var7.next();
         if (params.isFile(name)) {
            parts.bodyPart(new StreamDataBodyPart(name, params.getInputStream(name), params.getFileName(name), MediaType.valueOf(params.getContentType(name))));
         } else {
            parts.field(name, params.getStringParameter(name), MediaType.APPLICATION_JSON_TYPE);
         }
      }

      return methodEntity(ic, method, url, Entity.entity(parts, "multipart/form-data"), queryParams, streamResponse, (ResiliencyUtils.ResiliencyConfig)null);
   }

   private static ResponseWrapper methodEntity(InvocationContext ic, String method, String url, Entity params, MultivaluedMap queryParams, boolean streamResponse, ResiliencyUtils.ResiliencyConfig rc) throws Exception {
      RestInvokerWorker worker = new RestInvokerWorker(ic, method, url, params, queryParams, streamResponse);
      boolean succeeded = false;

      ResponseWrapper var9;
      try {
         if (rc == null) {
            worker.doNonResilientWork();
         } else if (!ResiliencyUtils.doResilientWork(rc, worker)) {
            throw new WebApplicationException(Response.status(Status.GATEWAY_TIMEOUT).build());
         }

         succeeded = true;
         var9 = worker.getResponseWrapper();
      } finally {
         if (!succeeded) {
            worker.close();
         }

      }

      return var9;
   }

   private static void closeClient(Client client) {
      if (client != null) {
         try {
            client.close();
         } catch (Throwable var2) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Problem closing client", var2);
            }
         }

      }
   }

   public static Response copyResponse(HttpServletRequest request, ResponseWrapper rw, LinkTransformer linkTransformer) throws Exception {
      return copyResponse(request, Response.status(rw.getStatus()), rw, linkTransformer).build();
   }

   public static Response.ResponseBuilder copyResponse(HttpServletRequest request, Response.ResponseBuilder bldr, ResponseWrapper rw, LinkTransformer linkTransformer) throws Exception {
      if (rw.hasEntity()) {
         if (rw.streamResponse()) {
            bldr.entity(rw.getInputStream());
         } else {
            Object entity = rw.getEntity();
            if (entity instanceof JSONObject) {
               RestJsonResponseBody rb = new RestJsonResponseBody(request, (JsonFilter)null);
               copyEntity((JSONObject)entity, rb, linkTransformer);
               bldr.entity(rb);
            } else {
               bldr.entity(entity);
            }
         }
      }

      copyLinkHeaders(rw, bldr, linkTransformer, "Location");
      copyHeaders(rw, bldr, "Allow", "Content-Type", "Content-Disposition");
      return bldr;
   }

   private static void copyLinkHeaders(ResponseWrapper rw, Response.ResponseBuilder bldr, LinkTransformer linkTransformer, String... headers) throws Exception {
      String[] var4 = headers;
      int var5 = headers.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String header = var4[var6];
         String val = (String)rw.getResponse().getStringHeaders().getFirst(header);
         if (val != null) {
            bldr.header(header, transformLink(val, linkTransformer));
         }
      }

   }

   private static void copyHeaders(ResponseWrapper rw, Response.ResponseBuilder bldr, String... headers) throws Exception {
      String[] var3 = headers;
      int var4 = headers.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String header = var3[var5];
         String val = (String)rw.getResponse().getStringHeaders().getFirst(header);
         if (val != null) {
            bldr.header(header, val);
         }
      }

   }

   public static void copyEntity(JSONObject rspb, RestJsonResponseBody rb, LinkTransformer linkTransformer) throws Exception {
      JSONObject responseBody = JsonUtil.cloneJSONObject(rspb);
      transformLinks(responseBody, linkTransformer);
      copyMessages(responseBody, rb);
      copyLinks(responseBody, rb);
      JSONObject item = responseBody.optJSONObject("item");
      JSONArray items = responseBody.optJSONArray("items");
      if (item != null) {
         JSONObject entity = new JSONObject();
         entity.put("item", item);
         rb.setEntity(entity);
      } else if (items != null) {
         rb.setEntities(items);
      } else {
         responseBody.remove("links");
         responseBody.remove("messages");
         rb.setEntity(responseBody);
      }

   }

   public static void copyMessages(JSONObject responseBody, RestJsonResponseBody rb) throws Exception {
      JSONArray messages = responseBody.optJSONArray("messages");
      if (messages != null) {
         for(int i = 0; i < messages.length(); ++i) {
            JSONObject message = messages.optJSONObject(i);
            if (message != null) {
               copyMessage(message, rb);
            }
         }
      }

   }

   private static void copyMessage(JSONObject message, RestJsonResponseBody rb) throws Exception {
      Message.Severity severity = getSeverity(message);
      String msg = message.optString("message", (String)null);
      if (severity != null && msg != null) {
         String field = message.optString("field", (String)null);
         Message m = null;
         if (field != null) {
            m = new Message(severity, msg);
         } else {
            m = new Message(severity, field, msg);
         }

         rb.add(m);
      }
   }

   private static Message.Severity getSeverity(JSONObject message) throws Exception {
      String sev = message.optString("severity", (String)null);
      Message.Severity severity = Severity.SUCCESS;
      if (severity.toString().equals(sev)) {
         return severity;
      } else {
         severity = Severity.WARNING;
         if (severity.toString().equals(sev)) {
            return severity;
         } else {
            severity = Severity.FAILURE;
            return severity.toString().equals(sev) ? severity : null;
         }
      }
   }

   private static void copyLinks(JSONObject responseBody, RestJsonResponseBody rb) throws Exception {
      JSONArray links = responseBody.optJSONArray("links");
      if (links != null) {
         for(int i = 0; i < links.length(); ++i) {
            JSONObject link = links.optJSONObject(i);
            if (link != null) {
               copyLink(link, rb);
            }
         }
      }

   }

   private static void copyLink(JSONObject link, RestJsonResponseBody rb) throws Exception {
      String rel = link.optString("rel", (String)null);
      String href = link.optString("href", (String)null);
      if (rel != null && href != null) {
         URI uri = null;

         try {
            uri = new URI(href);
         } catch (Throwable var7) {
            return;
         }

         String title = link.optString("title", (String)null);
         ResourceLink l = null;
         if (title != null) {
            l = new ResourceLink(rel, title, uri);
         } else {
            l = new ResourceLink(rel, uri);
         }

         rb.add(l);
      }

   }

   private static void transformLinks(JSONObject item, LinkTransformer linkTransformer) throws Exception {
      if (item != null) {
         JSONArray links = item.optJSONArray("links");

         for(int i = 0; links != null && i < links.length(); ++i) {
            JSONObject link = links.getJSONObject(i);
            String href = link.getString("href");
            link.put("href", transformLink(href, linkTransformer));
         }

         Iterator it = item.keys();

         while(it.hasNext()) {
            String prop = (String)it.next();
            if (!"links".equals(prop)) {
               transformLinks(item.optJSONObject(prop), linkTransformer);
               transformLinks(item.optJSONArray(prop), linkTransformer);
            }
         }

      }
   }

   private static void transformLinks(JSONArray items, LinkTransformer linkTransformer) throws Exception {
      if (items != null) {
         for(int i = 0; items != null && i < items.length(); ++i) {
            transformLinks(items.optJSONObject(i), linkTransformer);
         }

      }
   }

   private static String transformLink(String href, LinkTransformer linkTransformer) throws Exception {
      return linkTransformer != null ? linkTransformer.transform(href) : href;
   }

   private static Invocation.Builder copyHeaders(Invocation.Builder builder, HttpServletRequest request, String... headerNames) throws Exception {
      Invocation.Builder bldr = builder;
      String[] var4 = headerNames;
      int var5 = headerNames.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String headerName = var4[var6];
         bldr = copyHeader(bldr, request, headerName);
      }

      return bldr;
   }

   private static Invocation.Builder copyHeader(Invocation.Builder builder, HttpServletRequest request, String headerName) throws Exception {
      String val = request.getHeader(headerName);
      if (val == null) {
         return builder;
      } else {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("  hdr: " + headerName + "=" + val);
         }

         return builder.header(headerName, val);
      }
   }

   private static class InputStreamWrapper extends InputStream {
      private ResponseWrapper rw;
      private InputStream is;

      private InputStreamWrapper(InputStream is) {
         this.rw = null;
         this.is = is;
      }

      private void setResponseWrapper(ResponseWrapper rw) {
         if (this.rw != null) {
            throw new AssertionError("The response wrapper for this input stream wrapper has already been set.");
         } else {
            this.rw = rw;
         }
      }

      public int read() throws IOException {
         return this.is.read();
      }

      public int read(byte[] a1) throws IOException {
         return this.is.read(a1);
      }

      public int read(byte[] a1, int a2, int a3) throws IOException {
         return this.is.read(a1, a2, a3);
      }

      public long skip(long a1) throws IOException {
         return this.is.skip(a1);
      }

      public int available() throws IOException {
         return this.is.available();
      }

      public void mark(int a1) {
         this.is.mark(a1);
      }

      public void reset() throws IOException {
         this.is.reset();
      }

      public boolean markSupported() {
         return this.is.markSupported();
      }

      public void close() throws IOException {
         if (this.rw != null) {
            this.rw.close();
         }

      }

      // $FF: synthetic method
      InputStreamWrapper(InputStream x0, Object x1) {
         this(x0);
      }
   }

   public static class ResponseWrapper {
      private Client client;
      private Response response;
      private boolean streamResponse;
      private Object entity;

      private ResponseWrapper(Client client, Response response, boolean streamResponse) {
         this.client = client;
         this.response = response;
         this.streamResponse = false;

         try {
            if (this.getResponse().hasEntity()) {
               MediaType type = this.getResponse().getMediaType();
               boolean jsonStreamingAllowed = Boolean.valueOf(this.getResponse().getHeaderString("X-Json-Streaming_Allowed"));
               if (MediaType.APPLICATION_JSON_TYPE.equals(type) && !jsonStreamingAllowed) {
                  this.entity = this.getResponse().readEntity(JSONObject.class);
               } else {
                  this.streamResponse = streamResponse;
                  if (this.streamResponse()) {
                     this.entity = new InputStreamWrapper((InputStream)this.getResponse().readEntity(InputStream.class));
                  } else {
                     this.entity = this.getResponse().readEntity(String.class);
                  }
               }
            } else {
               this.entity = null;
            }
         } finally {
            if (!this.streamResponse()) {
               this.close();
            }

         }

      }

      public Response getResponse() {
         return this.response;
      }

      public int getStatus() {
         return this.getResponse().getStatus();
      }

      public boolean hasEntity() {
         return this.entity != null;
      }

      public boolean streamResponse() {
         return this.streamResponse;
      }

      public JSONObject getJsonEntity() {
         return (JSONObject)this.getEntity();
      }

      public String getStringEntity() {
         return (String)this.getEntity();
      }

      public Object getEntity() {
         if (this.streamResponse()) {
            throw new AssertionError("Use getInputStream since this response should be streamed");
         } else {
            return this.entity;
         }
      }

      public InputStream getInputStream() {
         if (!this.streamResponse()) {
            throw new AssertionError("Use getEntity since this response should not be streamed");
         } else {
            InputStreamWrapper isw = (InputStreamWrapper)this.entity;
            isw.setResponseWrapper(this);
            return isw;
         }
      }

      public synchronized void close() {
         if (this.client != null) {
            RestInvoker.closeClient(this.client);
            this.client = null;
         }

      }

      // $FF: synthetic method
      ResponseWrapper(Client x0, Response x1, boolean x2, Object x3) {
         this(x0, x1, x2);
      }
   }

   public interface LinkTransformer {
      String transform(String var1) throws Exception;
   }

   private static class RestInvokerWorker implements ResiliencyUtils.ResilientWorker {
      private InvocationContext ic;
      private String method;
      private String url;
      private Entity params;
      private MultivaluedMap queryParams;
      private boolean streamResponse;
      private ResponseWrapper rw;

      private RestInvokerWorker(InvocationContext ic, String method, String url, Entity params, MultivaluedMap queryParams, boolean streamResponse) {
         this.ic = ic;
         this.method = method;
         this.url = url;
         this.params = params;
         this.queryParams = queryParams;
         this.streamResponse = streamResponse;
         this.rw = null;
      }

      private ResponseWrapper getResponseWrapper() {
         return this.rw;
      }

      public String getDescription() {
         return this.method + " " + this.url;
      }

      private void doNonResilientWork() throws Exception {
         this.close();
         this.rw = this.method(0, 0);
      }

      public boolean doWork(ResiliencyUtils.ResiliencyConfig rc, long startTime) throws Exception {
         this.close();
         this.rw = this.method(rc.connectTimeout(), rc.readTimeout());
         return !this.shouldRetry(startTime);
      }

      private void close() {
         if (this.rw != null) {
            this.rw.close();
         }

      }

      private ResponseWrapper method(int connectTimeout, int readTimeout) throws Exception {
         long startTime = System.currentTimeMillis();
         if (RestInvoker.DEBUG.isDebugEnabled()) {
            if (connectTimeout <= 0 && readTimeout <= 0) {
               RestInvoker.DEBUG.debug("request started=" + DebugLogger.formatDate(startTime) + " " + this.getDescription());
            } else {
               RestInvoker.DEBUG.debug("time limited request connectionTimeout=" + connectTimeout + " readTimeout=" + readTimeout + " started=" + DebugLogger.formatDate(startTime) + " " + this.getDescription());
            }

            if (RestInvoker.DEBUG.isDebugEnabled() && this.params != null && this.params.getEntity() instanceof JSONObject) {
               RestInvoker.DEBUG.debug("  body: " + BeanResourceRegistry.instance().getRequestBodyHelper().hideConfidentialProperties((JSONObject)this.params.getEntity()).toString(2));
            }
         }

         Client client = this.getClient(connectTimeout, readTimeout);

         ResponseWrapper var20;
         try {
            Invocation.Builder builder = this.getBuilder(client);
            Response resp = this.invokeMethod(builder);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            ResponseWrapper rw;
            if (resp == null) {
               if (RestInvoker.DEBUG.isDebugEnabled()) {
                  RestInvoker.DEBUG.debug("response started=" + DebugLogger.formatDate(startTime) + " " + this.getDescription() + " timed out, duration=" + duration + " ms");
               }

               rw = null;
               return rw;
            }

            rw = new ResponseWrapper(client, resp, this.streamResponse);
            if (RestInvoker.DEBUG.isDebugEnabled()) {
               RestInvoker.DEBUG.debug("response started=" + DebugLogger.formatDate(startTime) + " " + this.getDescription() + " status=" + rw.getStatus() + ", " + duration + " ms");
               Iterator body = resp.getStringHeaders().entrySet().iterator();

               while(body.hasNext()) {
                  Map.Entry e = (Map.Entry)body.next();
                  RestInvoker.DEBUG.debug("  hdr: " + (String)e.getKey() + "=" + e.getValue());
               }

               if (rw.hasEntity()) {
                  body = null;
                  String body;
                  if (rw.streamResponse()) {
                     body = "streaming response";
                  } else {
                     Object entity = rw.getEntity();
                     if (entity instanceof JSONObject) {
                        body = ((JSONObject)entity).toString(2);
                     } else {
                        body = entity.toString();
                     }
                  }

                  RestInvoker.DEBUG.debug("  body: " + body);
               }
            }

            client = null;
            var20 = rw;
         } finally {
            RestInvoker.closeClient(client);
         }

         return var20;
      }

      private boolean shouldRetry(long startTime) throws Exception {
         if (this.rw == null) {
            if (RestInvoker.DEBUG.isDebugEnabled()) {
               RestInvoker.DEBUG.debug("retrying resilient request because of client timeout started=" + DebugLogger.formatDate(startTime) + " " + this.getDescription());
            }

            return true;
         } else {
            int status = this.rw.getResponse().getStatus();
            if (status == Status.SERVICE_UNAVAILABLE.getStatusCode()) {
               if (RestInvoker.DEBUG.isDebugEnabled()) {
                  RestInvoker.DEBUG.debug("retrying resilient request because of SERVICE_UNAVAILABLE started=" + DebugLogger.formatDate(startTime) + " " + this.getDescription());
               }

               return true;
            } else if (status == Status.GATEWAY_TIMEOUT.getStatusCode()) {
               if (RestInvoker.DEBUG.isDebugEnabled()) {
                  RestInvoker.DEBUG.debug("retrying resilient request because of GATEWAY_TIMEOUT started=" + DebugLogger.formatDate(startTime) + " " + this.getDescription());
               }

               return true;
            } else if (status != Status.NOT_FOUND.getStatusCode() || !this.rw.streamResponse() && !(this.rw.getEntity() instanceof String)) {
               return false;
            } else {
               String body = this.rw.streamResponse() ? "streaming response" : this.rw.getEntity().toString();
               if (RestInvoker.DEBUG.isDebugEnabled()) {
                  RestInvoker.DEBUG.debug("retrying resilient request because of NOT_FOUND started=" + DebugLogger.formatDate(startTime) + " " + this.getDescription() + " " + body);
               }

               return true;
            }
         }
      }

      private Response invokeMethod(Invocation.Builder builder) throws Exception {
         long startTime = System.currentTimeMillis();
         if (RestInvoker.DEBUG.isDebugEnabled()) {
            RestInvoker.DEBUG.debug("invokeMethod start started=" + DebugLogger.formatDate(startTime) + " " + this.getDescription());
         }

         long duration;
         try {
            Response response = this.params != null ? builder.method(this.method, this.params) : builder.method(this.method);
            if (RestInvoker.DEBUG.isDebugEnabled()) {
               duration = System.currentTimeMillis() - startTime;
               RestInvoker.DEBUG.debug("invokeMethod end started=" + DebugLogger.formatDate(startTime) + " " + this.getDescription() + " duration=" + duration + " ms");
            }

            return response;
         } catch (Exception var8) {
            if (var8 instanceof ProcessingException) {
               Throwable cause = ((ProcessingException)var8).getCause();
               if (cause instanceof SocketTimeoutException || cause instanceof ConnectException) {
                  if (RestInvoker.DEBUG.isDebugEnabled()) {
                     long duration = System.currentTimeMillis() - startTime;
                     RestInvoker.DEBUG.debug("invokeMethod timed out started=" + DebugLogger.formatDate(startTime) + " " + this.getDescription() + " duration=" + duration + " ms + " + cause);
                  }

                  return null;
               }
            }

            if (RestInvoker.DEBUG.isDebugEnabled()) {
               duration = System.currentTimeMillis() - startTime;
               RestInvoker.DEBUG.debug("invokeMethod failed started=" + DebugLogger.formatDate(startTime) + " " + this.getDescription() + " duration=" + duration, var8);
            }

            throw var8;
         }
      }

      private Invocation.Builder getBuilder(Client client) throws Exception {
         WebTarget resource = client.target(this.url);
         if (this.queryParams != null) {
            Iterator var3 = this.queryParams.entrySet().iterator();

            while(var3.hasNext()) {
               Map.Entry e = (Map.Entry)var3.next();
               resource = resource.queryParam((String)e.getKey(), ((List)e.getValue()).toArray());
               if (RestInvoker.DEBUG.isDebugEnabled()) {
                  RestInvoker.DEBUG.debug("  qp: " + (String)e.getKey() + "=" + e.getValue());
               }
            }
         }

         Invocation.Builder bldr = RestInvoker.copyHeaders(IdentityUtils.addIdentityHeader(resource.request(), this.ic.request(), this.method, this.url), this.ic.request(), "Accept", "Accept-Language", "Content-Type", "X-Skip-Resource-Links", "X-Requested-By", "weblogic.edit.session");
         bldr = bldr.header("X-Return_Json_Streaming_Allowed_Header", "true");
         return AsyncUtil.configureSyncAsync(bldr, this.ic.async(), this.ic.syncMaxWaitMilliSeconds(), this.ic.intervalToPollMilliSeconds());
      }

      private Client getClient(int connectTimeout, int readTimeout) throws Exception {
         Client client = null;
         if (connectTimeout == 0 && readTimeout == 0) {
            client = ClientBuilder.newClient();
         } else {
            ClientConfig config = new ClientConfig();
            if (connectTimeout > 0) {
               config.property("jersey.config.client.connectTimeout", connectTimeout);
            }

            if (readTimeout > 0) {
               config.property("jersey.config.client.readTimeout", readTimeout);
            }

            client = ClientBuilder.newClient(config);
         }

         boolean ok = false;

         Client var5;
         try {
            client.register(JettisonFeature.class);
            client.register(MultiPartFeature.class);
            ok = true;
            var5 = client;
         } finally {
            if (!ok) {
               RestInvoker.closeClient(client);
            }

         }

         return var5;
      }

      // $FF: synthetic method
      RestInvokerWorker(InvocationContext x0, String x1, String x2, Entity x3, MultivaluedMap x4, boolean x5, Object x6) {
         this(x0, x1, x2, x3, x4, x5);
      }
   }

   public static class QueryParams {
      private MultivaluedMap map;

      private QueryParams() {
         this.map = null;
      }

      public QueryParams put(String name, String value) {
         if (this.map == null) {
            this.map = new MultivaluedHashMap();
         }

         this.map.putSingle(name, value);
         return this;
      }

      private MultivaluedMap toMap() {
         return this.map;
      }

      // $FF: synthetic method
      QueryParams(Object x0) {
         this();
      }
   }
}
