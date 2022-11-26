package net.shibboleth.utilities.java.support.httpclient;

import java.io.IOException;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;

@ThreadSafe
public interface HttpClientContextHandler {
   void invokeBefore(@Nonnull HttpClientContext var1, @Nonnull HttpUriRequest var2) throws IOException;

   void invokeAfter(@Nonnull HttpClientContext var1, @Nonnull HttpUriRequest var2) throws IOException;
}
