package org.glassfish.grizzly.compression.zip;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;
import org.glassfish.grizzly.memory.Buffers;

public class GZipFilter extends BaseFilter {
   private final GZipDecoder decoder;
   private final GZipEncoder encoder;

   public GZipFilter() {
      this(512, 512);
   }

   public GZipFilter(int inBufferSize, int outBufferSize) {
      this.decoder = new GZipDecoder(inBufferSize);
      this.encoder = new GZipEncoder(outBufferSize);
   }

   public NextAction handleClose(FilterChainContext ctx) throws IOException {
      Connection connection = ctx.getConnection();
      this.decoder.release(connection);
      this.encoder.release(connection);
      return super.handleClose(ctx);
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      Connection connection = ctx.getConnection();
      Buffer input = (Buffer)ctx.getMessage();
      TransformationResult result = this.decoder.transform(connection, input);
      Buffer remainder = (Buffer)result.getExternalRemainder();
      if (remainder == null) {
         input.tryDispose();
      } else {
         input.shrink();
      }

      try {
         NextAction var6;
         switch (result.getStatus()) {
            case COMPLETE:
               ctx.setMessage(result.getMessage());
               var6 = ctx.getInvokeAction(remainder);
               return var6;
            case INCOMPLETE:
               var6 = ctx.getStopAction(remainder);
               return var6;
            case ERROR:
               throw new IllegalStateException("GZip decode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
            default:
               throw new IllegalStateException("Unexpected status: " + result.getStatus());
         }
      } finally {
         result.recycle();
      }
   }

   public NextAction handleWrite(FilterChainContext ctx) throws IOException {
      Connection connection = ctx.getConnection();
      Buffer input = (Buffer)ctx.getMessage();
      TransformationResult result = this.encoder.transform(connection, input);
      input.dispose();

      try {
         switch (result.getStatus()) {
            case COMPLETE:
            case INCOMPLETE:
               Buffer readyBuffer = (Buffer)result.getMessage();
               Buffer finishBuffer = this.encoder.finish(connection);
               Buffer resultBuffer = Buffers.appendBuffers(connection.getMemoryManager(), readyBuffer, finishBuffer);
               NextAction var8;
               if (resultBuffer == null) {
                  var8 = ctx.getStopAction();
                  return var8;
               }

               ctx.setMessage(resultBuffer);
               var8 = ctx.getInvokeAction();
               return var8;
            case ERROR:
               throw new IllegalStateException("GZip decode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
            default:
               throw new IllegalStateException("Unexpected status: " + result.getStatus());
         }
      } finally {
         result.recycle();
      }
   }
}
