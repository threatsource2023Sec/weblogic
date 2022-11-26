package org.glassfish.grizzly.filterchain;

import java.io.IOException;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.Transformer;

public abstract class AbstractCodecFilter extends BaseFilter implements CodecFilter {
   private final Transformer decoder;
   private final Transformer encoder;

   public AbstractCodecFilter(Transformer decoder, Transformer encoder) {
      this.decoder = decoder;
      this.encoder = encoder;
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      Connection connection = ctx.getConnection();
      Object message = ctx.getMessage();
      TransformationResult result = this.decoder.transform(connection, message);
      switch (result.getStatus()) {
         case COMPLETE:
            Object remainder = result.getExternalRemainder();
            boolean hasRemaining = this.decoder.hasInputRemaining(connection, remainder);
            this.decoder.release(connection);
            ctx.setMessage(result.getMessage());
            return hasRemaining ? ctx.getInvokeAction(remainder) : ctx.getInvokeAction();
         case INCOMPLETE:
            return ctx.getStopAction(message);
         case ERROR:
            throw new TransformationException(this.getClass().getName() + " transformation error: (" + result.getErrorCode() + ") " + result.getErrorDescription());
         default:
            return ctx.getInvokeAction();
      }
   }

   public NextAction handleWrite(FilterChainContext ctx) throws IOException {
      Connection connection = ctx.getConnection();
      Object message = ctx.getMessage();
      TransformationResult result = this.encoder.transform(connection, message);
      switch (result.getStatus()) {
         case COMPLETE:
            ctx.setMessage(result.getMessage());
            Object remainder = result.getExternalRemainder();
            boolean hasRemaining = this.encoder.hasInputRemaining(connection, remainder);
            this.encoder.release(connection);
            return hasRemaining ? ctx.getInvokeAction(remainder) : ctx.getInvokeAction();
         case INCOMPLETE:
            return ctx.getStopAction(message);
         case ERROR:
            throw new TransformationException(this.getClass().getName() + " transformation error: (" + result.getErrorCode() + ") " + result.getErrorDescription());
         default:
            return ctx.getInvokeAction();
      }
   }

   public Transformer getDecoder() {
      return this.decoder;
   }

   public Transformer getEncoder() {
      return this.encoder;
   }
}
