package weblogic.nodemanager.rest.utils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.glassfish.admin.rest.debug.DebugLogger;
import org.glassfish.admin.rest.utils.ExceptionUtil;
import weblogic.nodemanager.NodeManagerRestTextFormatter;

@Provider
public class NMExceptionMapper implements ExceptionMapper {
   private static final NodeManagerRestTextFormatter nmRestText = NodeManagerRestTextFormatter.getInstance();
   public static final String METHOD_NOT_ALLOWED;
   private static final DebugLogger DEBUG;
   @Context
   private HttpServletRequest request;

   public Response toResponse(Exception e) {
      Response r = null;

      for(Throwable t = e; r == null && t != null; t = ((Throwable)t).getCause()) {
         if (t instanceof WebApplicationException) {
            r = ((WebApplicationException)t).getResponse();
         }
      }

      if (r == null) {
         r = Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getLocalizedMessage()).build();
      }

      if (r.getStatus() == 405) {
         r = Response.fromResponse(r).entity(METHOD_NOT_ALLOWED).build();
      } else if (r.getStatus() == Status.INTERNAL_SERVER_ERROR.getStatusCode()) {
         ExceptionUtil.log(this.request, e);
      } else if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("toResponse:\n" + ExceptionUtil.getThrowableMessage(e) + "\n" + ExceptionUtil.getThrowableStackTrace(e));
      }

      return r;
   }

   static {
      METHOD_NOT_ALLOWED = nmRestText.msgMethodNotAllowed();
      DEBUG = DebugLogger.getDebugLogger(NMExceptionMapper.class);
   }
}
