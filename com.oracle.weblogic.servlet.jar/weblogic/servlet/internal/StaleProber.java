package weblogic.servlet.internal;

public interface StaleProber {
   boolean shouldReloadResource(long var1, String var3);

   boolean isResourceStaleCheckDisabled(String var1);

   boolean shouldReloadServlet(long var1);

   boolean isServletStaleCheckDisabled();
}
