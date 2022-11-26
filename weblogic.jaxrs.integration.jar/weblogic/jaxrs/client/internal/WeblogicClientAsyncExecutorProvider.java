package weblogic.jaxrs.client.internal;

import javax.annotation.Priority;
import org.glassfish.jersey.client.ClientAsyncExecutor;
import weblogic.jaxrs.concurrent.WeblogicExecutorServiceProvider;

@ClientAsyncExecutor
@Priority(5001)
final class WeblogicClientAsyncExecutorProvider extends WeblogicExecutorServiceProvider {
}
