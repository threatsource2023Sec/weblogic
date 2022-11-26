package org.glassfish.admin.rest.composite;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.RestLogger;
import org.glassfish.admin.rest.RestResource;
import org.glassfish.admin.rest.RestTextTextFormatter;
import org.glassfish.admin.rest.model.ResponseBody;
import org.glassfish.admin.rest.model.RestCollectionResponseBody;
import org.glassfish.admin.rest.model.RestModelResponseBody;
import org.glassfish.admin.rest.resources.AbstractResource;
import org.glassfish.admin.rest.utils.ExceptionUtil;
import org.glassfish.admin.rest.utils.JsonFilter;
import org.glassfish.admin.rest.utils.JsonUtil;
import org.glassfish.admin.rest.utils.MessageUtil;

public abstract class CompositeResource extends AbstractResource implements RestResource {
   public static final String CONSUMES_TYPE = "application/json";
   protected static final String MEDIA_TYPE_JSON = "application/json";
   public static final String MEDIA_TYPES = "application/json";
   protected static final String DETACHED = "__detached";
   protected static final String DETACHED_DEFAULT = "false";
   protected static final String SERIAL = "__serial";
   protected static final String SERIAL_DEFAULT = "false";
   protected static final String METHOD_OPTIONS = "OPTIONS";
   protected static final String METHOD_GET = "GET";
   protected static final String METHOD_PUT = "PUT";
   protected static final String METHOD_POST = "POST";
   protected static final String METHOD_DELETE = "DELETE";
   protected static final String FORMAT = "format";
   protected static final String FORMAT_DEFAULT = "compact";
   @Context
   private ServletContext context;
   @Context
   private HttpServletRequest tRequest;
   protected CompositeUtil compositeUtil = CompositeUtil.instance();

   protected ServletContext getContext() {
      return this.context;
   }

   protected HttpServletRequest getRequest() {
      return this.tRequest;
   }

   public CompositeUtil getCompositeUtil() {
      return this.compositeUtil;
   }

   public Object getSubResource(Class clazz) {
      try {
         Object resource = clazz.newInstance();
         CompositeResource cr = (CompositeResource)resource;
         cr.copyContext(this);
         return resource;
      } catch (Exception var4) {
         for(Throwable t = var4; t != null; t = ((Throwable)t).getCause()) {
            if (t instanceof WebApplicationException) {
               throw (WebApplicationException)t;
            }
         }

         this.internalServerError(var4);
         return null;
      }
   }

   protected void copyContext(CompositeResource other) {
      this.uriInfo = other.uriInfo;
      this.requestHeaders = other.requestHeaders;
      this.context = other.context;
      this.tRequest = other.tRequest;
   }

   protected Object newModel(Class modelIface) {
      return this.getCompositeUtil().getModel(modelIface);
   }

   protected RestModel newTemplate(Class modelIface) {
      RestModel template = (RestModel)this.newModel(modelIface);
      template.allFieldsSet();
      return template;
   }

   protected void validateModel(Class modelIface, JSONObject jsonModel) throws Exception {
      this.getTypedModel(modelIface, jsonModel);
   }

   protected RestModel getTypedModel(Class modelIface, JSONObject jsonModel) throws Exception {
      return jsonModel == null ? null : (RestModel)CompositeUtil.instance().unmarshallClass(this.getLocale(), modelIface, jsonModel);
   }

   protected JSONObject getJsonModel(RestModel typedModel) throws Exception {
      return (JSONObject)JsonUtil.getJsonObject(typedModel, false);
   }

   protected URI getSubUri(String... segments) {
      return this.getUri(this.uriInfo.getAbsolutePathBuilder(), segments);
   }

   protected URI getParentSubUri(String... segments) {
      int count = this.getParentUriSegmentsCount(false);
      return this.getRelativeUri(count, segments);
   }

   protected URI getRelativeUri(int count, String... segments) {
      List want = new ArrayList();
      List have = this.uriInfo.getPathSegments();

      for(int i = 0; i < count; ++i) {
         want.add(((PathSegment)have.get(i)).getPath());
      }

      String[] var9 = segments;
      int var6 = segments.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         String segment = var9[var7];
         want.add(segment);
      }

      return this.getUri((String[])want.toArray(new String[want.size()]));
   }

   protected URI getUri(String... segments) {
      return this.getUri(this.uriInfo.getBaseUriBuilder(), segments);
   }

   private URI getUri(UriBuilder bldr, String... segments) {
      String[] var3 = segments;
      int var4 = segments.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String segment = var3[var5];
         bldr.segment(new String[]{segment});
      }

      return bldr.build(new Object[0]);
   }

   protected void addResourceLink(ResponseBody rb, String rel) throws Exception {
      rb.addResourceLink(rel, this.getSubUri(rel));
   }

   protected void addActionResourceLink(ResponseBody rb, String action) throws Exception {
      rb.addActionResourceLink(action, this.getSubUri(action));
   }

   protected URI getParentUri() throws Exception {
      return this.getParentUri(false);
   }

   protected URI getCollectionChildParentUri() throws Exception {
      return this.getParentUri(true);
   }

   private int getParentUriSegmentsCount(boolean isCollectionChild) {
      List pathSegments = this.uriInfo.getPathSegments();
      int count = pathSegments.size() - 1;
      if (((PathSegment)pathSegments.get(count)).getPath().isEmpty()) {
         --count;
      }

      if (isCollectionChild) {
         --count;
      }

      return count;
   }

   protected URI getParentUri(boolean isCollectionChild) throws Exception {
      int count = this.getParentUriSegmentsCount(isCollectionChild);
      if (count <= 0) {
         return null;
      } else {
         List pathSegments = this.uriInfo.getPathSegments();
         UriBuilder bldr = this.uriInfo.getBaseUriBuilder();

         for(int i = 0; i < count; ++i) {
            bldr.path(((PathSegment)pathSegments.get(i)).getPath());
         }

         return bldr.build(new Object[0]);
      }
   }

   protected String generateDefaultName(String namePrefix, Collection usedNames) {
      for(int i = 1; i <= 100; ++i) {
         String name = namePrefix + "-" + i;
         if (!usedNames.contains(name)) {
            return name;
         }
      }

      return "";
   }

   protected Response created(String name, String message) throws Exception {
      return this.created(this.responseBody(), name, message);
   }

   protected Response created(ResponseBody rb, String name, String message) throws Exception {
      rb.addSuccess(message);
      return this.created(rb, name);
   }

   protected Response created(ResponseBody rb, String name) throws Exception {
      return this.created(rb, this.getSubUri(name));
   }

   protected Response created(ResponseBody rb, URI uri) throws Exception {
      return Response.created(uri).entity(rb).build();
   }

   protected Response updated(String message) {
      return this.updated(this.responseBody(), message);
   }

   protected Response updated(ResponseBody rb, String message) {
      rb.addSuccess(message);
      return this.updated(rb);
   }

   protected Response updated(ResponseBody rb) {
      return this.ok(rb);
   }

   protected Response deleted(String message) {
      return this.deleted(this.responseBody(), message);
   }

   protected Response deleted(ResponseBody rb, String message) {
      rb.addSuccess(message);
      return this.deleted(rb);
   }

   protected Response deleted(ResponseBody rb) {
      return this.ok(rb);
   }

   protected Response acted(String message) {
      return this.acted(this.responseBody(), message);
   }

   protected Response acted(ResponseBody rb, String message) {
      rb.addSuccess(message);
      return this.acted(rb);
   }

   protected Response acted(ResponseBody rb) {
      return this.ok(rb);
   }

   protected Response accepted(String message, URI newItemUri) {
      return this.accepted(this.responseBody(), message, newItemUri);
   }

   protected Response accepted(ResponseBody rb, String message, URI newItemUri) {
      rb.addSuccess(message);
      return this.accepted(rb, newItemUri);
   }

   protected Response accepted(ResponseBody rb, URI newItemUri) {
      Response.ResponseBuilder bldr = Response.status(Status.ACCEPTED).entity(rb);
      if (newItemUri != null) {
         bldr.header("Location", newItemUri);
      }

      return bldr.build();
   }

   protected Response ok(ResponseBody rb) {
      return Response.ok(rb).build();
   }

   protected Response badRequest(ResponseBody rb, String message) {
      rb.addFailure(message);
      return this.badRequest(rb);
   }

   protected Response badRequest(ResponseBody rb) {
      return Response.status(Status.BAD_REQUEST).entity(rb).build();
   }

   protected WebApplicationException badRequest(Throwable cause) {
      return new WebApplicationException(cause, Status.BAD_REQUEST);
   }

   protected WebApplicationException badRequest(String message) {
      return new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(message).build());
   }

   protected WebApplicationException notFound(String message) {
      return new WebApplicationException(Response.status(Status.NOT_FOUND).entity(message).build());
   }

   protected WebApplicationException notFound() {
      return this.notFound(this.formatter().msgNotFound(this.getResourceName()));
   }

   protected WebApplicationException methodNotAllowed(String allowed) {
      return new WebApplicationException(Response.status(405).entity(this.responseBody().addFailure(this.formatter().msgMethodNotAllowed())).header("Allow", allowed).build());
   }

   protected String getResourceName() {
      return ((PathSegment)this.uriInfo.getPathSegments().get(this.uriInfo.getPathSegments().size() - 1)).getPath();
   }

   protected void internalServerError(Exception e) {
      ExceptionUtil.log(this.getRequest(), e);
      throw new WebApplicationException(e, Status.INTERNAL_SERVER_ERROR);
   }

   protected WebApplicationException internalServerError(String message) {
      RestLogger.logGenericError(message);
      return new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(message).build());
   }

   protected RestCollectionResponseBody restCollectionResponseBody(Class modelIface, String collectionName, URI parentUri) {
      RestCollectionResponseBody rb = this.restCollectionResponseBody(modelIface, collectionName);
      rb.addParentResourceLink(parentUri);
      return rb;
   }

   protected RestCollectionResponseBody restCollectionResponseBody(Class modelIface, String collectionName) {
      return new RestCollectionResponseBody(this.getRequest(), this.uriInfo, collectionName);
   }

   protected RestModelResponseBody restModelResponseBody(Class modelIface, URI parentUri, RestModel entity) {
      RestModelResponseBody rb = this.restModelResponseBody(modelIface, parentUri);
      rb.setEntity(entity);
      return rb;
   }

   protected RestModelResponseBody restModelResponseBody(Class modelIface, URI parentUri) {
      RestModelResponseBody rb = this.restModelResponseBody(modelIface);
      rb.addParentResourceLink(parentUri);
      return rb;
   }

   protected RestModelResponseBody restModelResponseBody(Class modelIface) {
      return new RestModelResponseBody(this.getRequest());
   }

   protected ResponseBody responseBody() {
      return new ResponseBody(this.getRequest());
   }

   protected Response getResponse(ResponseBody responseBody) {
      return this.getResponse(Status.OK, responseBody);
   }

   protected Response getResponse(Response.Status status, ResponseBody responseBody) {
      return Response.status(status).entity(responseBody).build();
   }

   protected JsonFilter getFilter(String include, String exclude) throws Exception {
      return new JsonFilter(this.getLocale(), include, exclude, "fields", "excludeFields");
   }

   protected JsonFilter getFilter(String include, String exclude, String identityAttr) throws Exception {
      return new JsonFilter(this.getLocale(), include, exclude, identityAttr, "fields", "excludeFields");
   }

   protected RestModel filterModel(Class modelIface, RestModel unfilteredModel, String include, String exclude) throws Exception {
      return this.filterModel(modelIface, unfilteredModel, this.getFilter(include, exclude));
   }

   protected RestModel filterModel(Class modelIface, RestModel unfilteredModel, String include, String exclude, String identityAttr) throws Exception {
      return this.filterModel(modelIface, unfilteredModel, this.getFilter(include, exclude, identityAttr));
   }

   protected RestModel filterModel(Class modelIface, RestModel unfilteredModel, JsonFilter filter) throws Exception {
      JSONObject unfilteredJson = (JSONObject)JsonUtil.getJsonObject(unfilteredModel, false);
      JSONObject filteredJson = filter.trim(unfilteredJson);
      return this.getTypedModel(modelIface, filteredJson);
   }

   protected boolean isFullFormat(String format) {
      return "full".equals(format);
   }

   protected Locale getLocale() {
      return this.getRequest().getLocale();
   }

   protected String getPathParam(String name) {
      return (String)this.uriInfo.getPathParameters().getFirst(name);
   }

   protected boolean detachedRequested(String detached) {
      return Boolean.parseBoolean(detached);
   }

   protected RestTextTextFormatter formatter() {
      return MessageUtil.formatter(this.getRequest());
   }

   public abstract static class BasePopulator implements Runnable {
      private Throwable cause = null;
      private Object model;
      private HttpServletRequest request;
      private boolean fullFormat;
      private JsonFilter filter;

      protected BasePopulator(Object model, HttpServletRequest request) {
         this.model = model;
         this.request = request;
      }

      protected BasePopulator(Object model, HttpServletRequest request, boolean fullFormat) {
         this.model = model;
         this.request = request;
         this.fullFormat = fullFormat;
      }

      protected BasePopulator(Object model, HttpServletRequest request, JsonFilter filter) {
         this.model = model;
         this.request = request;
         this.filter = filter;
      }

      public abstract void run();

      protected void failed(Throwable cause) {
         this.cause = cause;
      }

      public Throwable getCause() {
         return this.cause;
      }

      public boolean succeeded() {
         return this.getCause() == null;
      }

      protected boolean fullFormat() {
         return this.fullFormat;
      }

      protected JsonFilter filter() {
         return this.filter;
      }

      protected HttpServletRequest getRequest() {
         return this.request;
      }

      public Object getModel() {
         return this.model;
      }
   }
}
