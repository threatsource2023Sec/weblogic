package org.glassfish.grizzly.compression.lzma;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.filterchain.BaseFilter;
import org.glassfish.grizzly.filterchain.FilterChainContext;
import org.glassfish.grizzly.filterchain.NextAction;

public class LZMAFilter extends BaseFilter {
   private final LZMAEncoder encoder = new LZMAEncoder();
   private final LZMADecoder decoder = new LZMADecoder();

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
               this.decoder.finish(connection);
               var6 = ctx.getInvokeAction(remainder);
               return var6;
            case INCOMPLETE:
               var6 = ctx.getStopAction(remainder);
               return var6;
            case ERROR:
               throw new IllegalStateException("LZMA decode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
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
      if (!input.hasRemaining()) {
         input.tryDispose();
      } else {
         input.shrink();
      }

      NextAction var6;
      try {
         switch (result.getStatus()) {
            case COMPLETE:
               this.encoder.finish(connection);
            case INCOMPLETE:
               break;
            case ERROR:
               throw new IllegalStateException("LZMA encode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
            default:
               throw new IllegalStateException("Unexpected status: " + result.getStatus());
         }

         Buffer readyBuffer = (Buffer)result.getMessage();
         if (readyBuffer == null) {
            var6 = ctx.getStopAction();
            return var6;
         }

         ctx.setMessage(readyBuffer);
         var6 = ctx.getInvokeAction();
      } finally {
         result.recycle();
      }

      return var6;
   }

   public NextAction handleClose(FilterChainContext ctx) throws IOException {
      Connection connection = ctx.getConnection();
      this.decoder.release(connection);
      this.encoder.release(connection);
      return super.handleClose(ctx);
   }
}
