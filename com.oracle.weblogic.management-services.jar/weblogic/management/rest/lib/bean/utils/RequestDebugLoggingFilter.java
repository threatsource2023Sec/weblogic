package weblogic.management.rest.lib.bean.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.debug.DebugLogger;

@Provider
@Priority(1200)
public class RequestDebugLoggingFilter implements ContainerRequestFilter {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(RequestDebugLoggingFilter.class);

   public void filter(ContainerRequestContext req) throws IOException {
      try {
         long start = System.currentTimeMillis();
         req.setProperty("FILTER_REQUEST_START_TIME", new Long(start));
         Object reqEntity = this.getRequestEntity(req);
         if (reqEntity != null) {
            req.setProperty("FILTER_REQUEST_ENTITY", reqEntity);
         }

         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("\nRequest\nuri=" + req.getUriInfo().getRequestUri().toString() + "\nmethod=" + req.getMethod() + "\nheaders=" + req.getHeaders() + "\nhasEntity=" + req.hasEntity() + "\nbody=" + this.entityToString(reqEntity));
         }
      } catch (Throwable var5) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Unexpected ignored exception", var5);
         }
      }

   }

   private Object getRequestEntity(ContainerRequestContext req) throws Exception {
      if (!req.hasEntity()) {
         return null;
      } else {
         MediaType type = req.getMediaType();
         if (type == null) {
            return null;
         } else {
            return !MediaType.APPLICATION_JSON_TYPE.isCompatible(type) ? null : this.readJsonEntity(req);
         }
      }
   }

   private Object readJsonEntity(ContainerRequestContext req) throws Exception {
      BufferedReader ir = new BufferedReader(new InputStreamReader(req.getEntityStream()));
      StringBuilder sb = new StringBuilder();
      String line = null;
      Charset cs = org.glassfish.jersey.message.MessageUtils.getCharset(req.getMediaType());

      do {
         line = ir.readLine();
         if (line != null) {
            sb.append(line);
         }
      } while(line != null);

      ir.close();
      String entity = sb.toString();
      req.setEntityStream(new ByteArrayInputStream(entity.getBytes(cs)));

      try {
         return BeanResourceRegistry.instance().getRequestBodyHelper().hideConfidentialProperties(new JSONObject(entity));
      } catch (JSONException var8) {
         return entity;
      }
   }

   private String entityToString(Object entity) throws Exception {
      if (entity instanceof JSONObject) {
         return ((JSONObject)entity).toString(2);
      } else {
         return entity != null ? null : null;
      }
   }
}
