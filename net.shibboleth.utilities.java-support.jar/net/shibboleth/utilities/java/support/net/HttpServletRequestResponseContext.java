package net.shibboleth.utilities.java.support.net;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.logic.Constraint;

public final class HttpServletRequestResponseContext {
   private static ThreadLocal currentRequest = new ThreadLocal();
   private static ThreadLocal currentResponse = new ThreadLocal();

   private HttpServletRequestResponseContext() {
   }

   public static void loadCurrent(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response) {
      Constraint.isNotNull(request, "HttpServletRequest may not be null");
      Constraint.isNotNull(response, "HttpServletResponse may not be null");
      currentRequest.set(request);
      currentResponse.set(response);
   }

   public static void clearCurrent() {
      currentRequest.remove();
      currentResponse.remove();
   }

   @Nullable
   public static HttpServletRequest getRequest() {
      return (HttpServletRequest)currentRequest.get();
   }

   @Nullable
   public static HttpServletResponse getResponse() {
      return (HttpServletResponse)currentResponse.get();
   }
}
