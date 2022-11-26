package net.shibboleth.utilities.java.support.velocity;

import java.util.Properties;
import javax.annotation.Nonnull;

public final class VelocityEngine {
   private VelocityEngine() {
   }

   @Nonnull
   public static org.apache.velocity.app.VelocityEngine newVelocityEngine() {
      return newVelocityEngine(getDefaultProperties());
   }

   @Nonnull
   public static org.apache.velocity.app.VelocityEngine newVelocityEngine(@Nonnull Properties props) {
      org.apache.velocity.app.VelocityEngine engine = new org.apache.velocity.app.VelocityEngine();
      engine.init(props);
      return engine;
   }

   @Nonnull
   public static Properties getDefaultProperties() {
      Properties props = new Properties();
      props.setProperty("string.resource.loader.class", "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
      props.setProperty("classpath.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
      props.setProperty("resource.loader", "classpath, string");
      return props;
   }
}
