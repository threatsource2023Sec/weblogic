package org.glassfish.grizzly.nio.transport;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.Interceptor;
import org.glassfish.grizzly.ReadResult;
import org.glassfish.grizzly.Reader;
import org.glassfish.grizzly.Transport;
import org.glassfish.grizzly.streams.AbstractStreamReader;
import org.glassfish.grizzly.streams.BufferedInput;

public final class DefaultStreamReader extends AbstractStreamReader {
   public DefaultStreamReader(Connection connection) {
      super(connection, new Input());
      ((Input)this.input).parentStreamReader = this;
   }

   public Input getSource() {
      return (Input)this.input;
   }

   public static final class Input extends BufferedInput {
      private DefaultStreamReader parentStreamReader;
      private InputInterceptor interceptor;

      protected void onOpenInputSource() throws IOException {
         Connection connection = this.parentStreamReader.getConnection();
         Transport transport = connection.getTransport();
         Reader reader = transport.getReader(connection);
         this.interceptor = new InputInterceptor();
         reader.read(connection, (Buffer)null, (CompletionHandler)null, this.interceptor);
      }

      protected void onCloseInputSource() throws IOException {
         this.interceptor.isDone = true;
         this.interceptor = null;
      }

      protected void notifyCompleted(CompletionHandler completionHandler) {
         if (completionHandler != null) {
            completionHandler.completed(this.compositeBuffer.remaining());
         }

      }

      protected void notifyFailure(CompletionHandler completionHandler, Throwable failure) {
         if (completionHandler != null) {
            completionHandler.failed(failure);
         }

      }

      private class InputInterceptor implements Interceptor {
         boolean isDone;

         private InputInterceptor() {
            this.isDone = false;
         }

         public int intercept(int event, Object context, ReadResult result) {
            if (event == 1) {
               Buffer buffer = (Buffer)result.getMessage();
               result.setMessage((Object)null);
               if (buffer == null) {
                  return 2;
               } else {
                  buffer.trim();
                  Input.this.append(buffer);
                  return this.isDone ? 1 : 6;
               }
            } else {
               return 0;
            }
         }

         // $FF: synthetic method
         InputInterceptor(Object x1) {
            this();
         }
      }
   }
}
