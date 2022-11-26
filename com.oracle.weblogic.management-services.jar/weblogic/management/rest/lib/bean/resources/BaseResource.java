package weblogic.management.rest.lib.bean.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.composite.CompositeResource;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import org.glassfish.admin.rest.utils.JsonFilter;
import weblogic.management.rest.lib.bean.utils.BeanTextTextFormatter;
import weblogic.management.rest.lib.bean.utils.MessageUtils;
import weblogic.management.rest.lib.bean.utils.QueryUtils;

public abstract class BaseResource extends CompositeResource {
   protected static final String INCLUDE_FIELDS = "fields";
   protected static final String EXCLUDE_FIELDS = "excludeFields";
   protected static final String INCLUDE_LINKS = "links";
   protected static final String EXCLUDE_LINKS = "excludeLinks";

   protected BeanTextTextFormatter beanFormatter() {
      return MessageUtils.beanFormatter(this.getRequest());
   }

   protected URI getTreeRelativeUri(String... segments) {
      return this.getRelativeUri(3, segments);
   }

   protected RestJsonResponseBody restJsonResponseBody() {
      return this.restJsonResponseBody((JsonFilter)null);
   }

   protected RestJsonResponseBody restJsonResponseBody(JsonFilter linksFilter) {
      return new RestJsonResponseBody(this.getRequest(), linksFilter);
   }

   protected void verifyOptions() throws Exception {
      if (!this.supportsOptions()) {
         this.throwMethodNotAllowed();
      }

   }

   protected void verifyGet() throws Exception {
      if (!this.supportsGet()) {
         this.throwMethodNotAllowed();
      }

   }

   protected void verifyPut() throws Exception {
      if (!this.supportsPut()) {
         this.throwMethodNotAllowed();
      }

   }

   protected void verifyPost() throws Exception {
      if (!this.supportsPost()) {
         this.throwMethodNotAllowed();
      }

   }

   protected void verifyDelete() throws Exception {
      if (!this.supportsDelete()) {
         this.throwMethodNotAllowed();
      }

   }

   protected boolean supportsOptions() throws Exception {
      return true;
   }

   protected boolean supportsGet() throws Exception {
      return true;
   }

   protected boolean supportsPut() throws Exception {
      return false;
   }

   protected boolean supportsPost() throws Exception {
      return false;
   }

   protected boolean supportsDelete() throws Exception {
      return false;
   }

   protected void throwMethodNotAllowed() throws Exception {
      throw this.methodNotAllowed(this.allowedMethods());
   }

   protected Response options() throws Exception {
      this.verifyOptions();
      return this._options();
   }

   protected Response get() throws Exception {
      this.verifyGet();
      return this._get();
   }

   protected Response put() throws Exception {
      this.verifyPut();
      return this._put();
   }

   protected Response post() throws Exception {
      this.verifyPost();
      return this._post();
   }

   protected Response delete() throws Exception {
      this.verifyDelete();
      return this._delete();
   }

   protected Response _options() throws Exception {
      return Response.ok().header("Allow", this.allowedMethods()).build();
   }

   protected Response _get() throws Exception {
      throw new AssertionError("Generic GET not supported");
   }

   protected Response _put() throws Exception {
      throw new AssertionError("Generic PUT not supported");
   }

   protected Response _post() throws Exception {
      throw new AssertionError("Generic POST not supported");
   }

   protected Response _delete() throws Exception {
      throw new AssertionError("Generic DELETE not supported");
   }

   protected JSONObject getQuery(String includeFields, String excludeFields, String includeLinks, String excludeLinks) throws Exception {
      return QueryUtils.getIncludeExcludeQuery(includeFields, excludeFields, includeLinks, excludeLinks);
   }

   protected JsonFilter getPropertiesFilter(JSONObject query) throws Exception {
      return QueryUtils.getPropertiesFilter(this.getRequest(), query);
   }

   protected JsonFilter getLinksFilter(JSONObject query) throws Exception {
      return QueryUtils.getLinksFilter(this.getRequest(), query);
   }

   private String allowedMethods() throws Exception {
      List methods = new ArrayList();
      if (this.supportsOptions()) {
         methods.add("OPTIONS");
      }

      if (this.supportsGet()) {
         methods.add("GET");
      }

      if (this.supportsPut()) {
         methods.add("PUT");
      }

      if (this.supportsPost()) {
         methods.add("POST");
      }

      if (this.supportsDelete()) {
         methods.add("DELETE");
      }

      StringBuilder sb = new StringBuilder();
      boolean first = true;

      for(Iterator var4 = methods.iterator(); var4.hasNext(); first = false) {
         String method = (String)var4.next();
         if (!first) {
            sb.append(",");
         }

         sb.append(method);
      }

      return sb.toString();
   }
}
