package weblogic.jaxrs.server.internal;

import java.io.IOException;
import java.io.OutputStream;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;
import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.server.ChunkedOutput;
import weblogic.servlet.internal.ServletOutputStreamImpl;

@Priority(5001)
final class ChunkedOutputWriter implements WriterInterceptor {
   @Inject
   private Provider responseProvider;

   public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
      Class entityType = context.getType();
      if (ChunkedOutput.class.isAssignableFrom(entityType) || OutboundEvent.class.isAssignableFrom(entityType)) {
         final MediaType mediaType = context.getMediaType();
         final OutputStream stream = ((HttpServletResponse)((Ref)this.responseProvider.get()).get()).getOutputStream();
         OutputStream outputStream = context.getOutputStream();
         if (stream instanceof ServletOutputStreamImpl && !(outputStream instanceof DelegatingOutputStream)) {
            context.setOutputStream(new DelegatingOutputStream(outputStream) {
               public void close() throws IOException {
                  super.close();
                  if (!SseFeature.SERVER_SENT_EVENTS_TYPE.equals(mediaType)) {
                     ((ServletOutputStreamImpl)stream).finish();
                  }

               }
            });
         }
      }

      context.proceed();
   }

   private static class DelegatingOutputStream extends ServletOutputStream {
      private final OutputStream delegate;

      private DelegatingOutputStream(OutputStream delegate) {
         this.delegate = delegate;
      }

      public void write(int b) throws IOException {
         this.delegate.write(b);
      }

      public void write(byte[] b) throws IOException {
         this.delegate.write(b);
      }

      public void write(byte[] b, int off, int len) throws IOException {
         this.delegate.write(b, off, len);
      }

      public void flush() throws IOException {
         this.delegate.flush();
      }

      public void close() throws IOException {
         this.delegate.close();
      }

      public boolean isReady() {
         return false;
      }

      public void setWriteListener(WriteListener writeListener) {
         throw new IllegalStateException("Not Supported");
      }

      // $FF: synthetic method
      DelegatingOutputStream(OutputStream x0, Object x1) {
         this(x0);
      }
   }
}
