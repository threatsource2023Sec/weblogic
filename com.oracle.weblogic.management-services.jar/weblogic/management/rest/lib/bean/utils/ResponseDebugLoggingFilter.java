package weblogic.management.rest.lib.bean.utils;

import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.RestLogger;
import org.glassfish.admin.rest.debug.DebugLogger;
import org.glassfish.admin.rest.model.ErrorResponseBody;
import org.glassfish.admin.rest.model.ResponseBody;
import org.glassfish.admin.rest.utils.ExceptionUtil;

@Provider
@Priority(800)
public class ResponseDebugLoggingFilter implements ContainerResponseFilter {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(ResponseDebugLoggingFilter.class);

   public void filter(ContainerRequestContext req, ContainerResponseContext res) throws IOException {
      try {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug(this.describeResponse(req, res));
         } else if (req.getProperty(ExceptionUtil.LOGGED_ERROR) != null) {
            RestLogger.logGenericError(this.describeResponse(req, res));
         }
      } catch (Throwable var4) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Unexpected ignored exception", var4);
         }
      }

   }

   private String describeResponse(ContainerRequestContext req, ContainerResponseContext res) throws Exception {
      Object prop = req.getProperty("FILTER_REQUEST_START_TIME");
      long duration = -1L;
      if (prop != null) {
         long start = (Long)prop;
         long end = System.currentTimeMillis();
         duration = end - start;
      }

      return "\nResponse\nuri=" + req.getUriInfo().getRequestUri().toString() + "\nmethod=" + req.getMethod() + "\nduration=" + duration + " ms\nstatus=" + res.getStatus() + "\nres headers=" + res.getHeaders() + "\nres body=" + this.entityToString(res.getEntity()) + "\nreq headers=" + req.getHeaders() + "\nreq body=" + this.entityToString(req.getProperty("FILTER_REQUEST_ENTITY"));
   }

   private String entityToString(Object entity) throws Exception {
      if (entity instanceof InputStream) {
         return "streaming response";
      } else if (entity instanceof JSONObject) {
         return ((JSONObject)entity).toString(2);
      } else if (entity instanceof ResponseBody) {
         return ((ResponseBody)entity).toJson().toString(2);
      } else if (entity instanceof ErrorResponseBody) {
         return ((ErrorResponseBody)entity).toJson().toString(2);
      } else {
         return entity != null ? entity.toString() : null;
      }
   }
}
