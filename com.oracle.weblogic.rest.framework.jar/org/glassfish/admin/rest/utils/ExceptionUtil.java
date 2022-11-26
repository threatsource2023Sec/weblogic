package org.glassfish.admin.rest.utils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.admin.rest.RestLogger;
import org.glassfish.admin.rest.debug.DebugLogger;
import org.glassfish.admin.rest.model.ResponseBody;
import weblogic.utils.StackTraceUtils;

public class ExceptionUtil {
   public static final String LOGGED_ERROR = ExceptionUtil.class.getName() + ".LoggedError";
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(ExceptionUtil.class);

   public static String getThrowableStackTrace(Throwable t, boolean useNestedCause) {
      Throwable cause = useNestedCause ? getNestedCause(t) : t;
      return StackTraceUtils.throwable2StackTrace(cause);
   }

   public static String getThrowableStackTrace(Throwable t) {
      return getThrowableStackTrace(t, true);
   }

   public static String getThrowableMessage(Throwable t, boolean useNestedCause) {
      Throwable cause = useNestedCause ? getNestedCause(t) : t;
      return getExceptionMessage(cause);
   }

   public static String getThrowableMessage(Throwable t) {
      return getThrowableMessage(t, true);
   }

   public static void log(HttpServletRequest request, Throwable t, boolean useNestedCause) {
      log(t, useNestedCause);
      if (!DEBUG.isDebugEnabled()) {
         if (request != null) {
            RestLogger.logGenericError("\nRequest\nuri=" + request.getRequestURI() + "\nmethod=" + request.getMethod());
            request.setAttribute(LOGGED_ERROR, true);
         }
      }
   }

   public static void log(HttpServletRequest request, Throwable t) {
      log(request, t, true);
   }

   public static void log(Throwable t, boolean useNestedCause) {
      RestLogger.logGenericError(getThrowableMessage(t, useNestedCause));
      RestLogger.logGenericError(getThrowableStackTrace(t, useNestedCause));
      StringBuilder sb = new StringBuilder();
      StackTraceElement[] var3 = Thread.currentThread().getStackTrace();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         StackTraceElement e = var3[var5];
         sb.append("\n" + e);
      }

      RestLogger.logGenericError(sb.toString());
   }

   public static void log(Throwable t) {
      log(t, true);
   }

   public static void badRequest(String msg) throws Exception {
      throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(msg).build());
   }

   public static WebApplicationException badRequest(Throwable t, boolean useNestedCause) {
      ResponseBody rb = new ResponseBody(false);
      Throwable cause = useNestedCause ? getNestedCause(t) : t;
      String msg = getExceptionMessage(cause);
      rb.addFailure(msg);
      return new WebApplicationException(msg, cause, Response.status(Status.BAD_REQUEST).entity(rb).build());
   }

   public static WebApplicationException badRequest(Throwable t) {
      return badRequest(t, true);
   }

   private static String getExceptionMessage(Throwable t) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("getExceptionMessage", t);
      }

      String message = t.getMessage();
      if (message == null || message.length() < 1) {
         message = t.toString();
      }

      if (message == null || message.length() < 1) {
         message = t.getClass().getName();
      }

      return message;
   }

   private static Throwable getNestedCause(Throwable t) {
      Throwable result;
      for(result = t; result.getCause() != null; result = result.getCause()) {
      }

      return result;
   }

   public static void rethrow(Throwable t) throws Exception {
      if (t instanceof Exception) {
         throw (Exception)t;
      } else if (t instanceof Error) {
         throw (Error)t;
      } else {
         throw new AssertionError("Not rethrowable: " + t);
      }
   }

   public static void rethrowAsUnchecked(Throwable t) {
      if (t instanceof RuntimeException) {
         throw (RuntimeException)t;
      } else if (t instanceof Error) {
         throw (Error)t;
      } else {
         throw new RuntimeException(t);
      }
   }
}
