package weblogic.jaxrs.server.internal;

import javax.annotation.Priority;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.FeatureContext;
import org.glassfish.jersey.internal.spi.ForcedAutoDiscoverable;

@ConstrainedTo(RuntimeType.SERVER)
@Priority(1900)
public final class WeblogicIntegrationAutoDiscoverable implements ForcedAutoDiscoverable {
   public void configure(FeatureContext context) {
   }
}
