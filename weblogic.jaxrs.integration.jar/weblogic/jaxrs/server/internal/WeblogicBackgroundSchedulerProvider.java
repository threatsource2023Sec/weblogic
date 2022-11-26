package weblogic.jaxrs.server.internal;

import javax.annotation.Priority;
import org.glassfish.jersey.server.BackgroundScheduler;
import weblogic.jaxrs.concurrent.WeblogicScheduledExecutorServiceProvider;

@BackgroundScheduler
@Priority(5001)
final class WeblogicBackgroundSchedulerProvider extends WeblogicScheduledExecutorServiceProvider {
}
