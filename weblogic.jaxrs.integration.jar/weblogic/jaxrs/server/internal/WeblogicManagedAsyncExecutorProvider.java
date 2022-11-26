package weblogic.jaxrs.server.internal;

import javax.annotation.Priority;
import org.glassfish.jersey.server.ManagedAsyncExecutor;
import weblogic.jaxrs.concurrent.WeblogicExecutorServiceProvider;

@ManagedAsyncExecutor
@Priority(5001)
final class WeblogicManagedAsyncExecutorProvider extends WeblogicExecutorServiceProvider {
}
