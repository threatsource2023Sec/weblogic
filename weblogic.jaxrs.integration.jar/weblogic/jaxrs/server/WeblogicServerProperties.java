package weblogic.jaxrs.server;

public final class WeblogicServerProperties {
   public static final String MONITORING_ENABLED = "jersey.config.wls.server.monitoring.enabled";
   public static final String MONITORING_EXTENDED_ENABLED = "jersey.config.wls.server.monitoring.extended.enabled";

   private WeblogicServerProperties() {
      throw new AssertionError("No instances allowed.");
   }
}
