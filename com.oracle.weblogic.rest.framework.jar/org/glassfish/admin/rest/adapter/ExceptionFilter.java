package org.glassfish.admin.rest.adapter;

import java.io.IOException;
import java.util.List;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.ext.Provider;
import org.glassfish.admin.rest.debug.DebugLogger;
import org.glassfish.admin.rest.model.ErrorResponseBody;
import org.glassfish.admin.rest.model.ResponseBody;

@Provider
@Priority(1000)
public class ExceptionFilter implements ContainerResponseFilter {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(ExceptionFilter.class);

   public void filter(ContainerRequestContext req, ContainerResponseContext res) throws IOException {
      List segments = req.getUriInfo().getPathSegments();
      String resourceCategory = segments.size() > 0 ? ((PathSegment)segments.get(0)).getPath() : null;
      if (!"tenant-monitoring".equals(resourceCategory) && !"elasticity-monitoring".equals(resourceCategory)) {
         int status = res.getStatus();
         if (status < 200 || status > 299) {
            Object entity = res.getEntity();
            if (entity != null) {
               ResponseBody rb;
               if ("wls".equals(resourceCategory)) {
                  if (entity instanceof String) {
                     rb = this.getResponseBodyForErrorMessage((String)entity);
                     this.replaceEntity(res, rb);
                  }
               } else {
                  rb = null;
                  if (entity instanceof String) {
                     rb = this.getResponseBodyForErrorMessage((String)entity);
                  } else if (entity instanceof ResponseBody) {
                     rb = (ResponseBody)entity;
                  }

                  if (rb != null && rb.getMessages().size() > 0) {
                     this.replaceEntity(res, new ErrorResponseBody(status, rb));
                  }

               }
            }
         }
      }
   }

   private void replaceEntity(ContainerResponseContext res, Object newEntity) {
      res.setEntity(newEntity, res.getEntityAnnotations(), MediaType.APPLICATION_JSON_TYPE);
   }

   private ResponseBody getResponseBodyForErrorMessage(String errorMsg) {
      ResponseBody rb = new ResponseBody(false);
      rb.addFailure(errorMsg);
      return rb;
   }
}
