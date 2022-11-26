package org.glassfish.grizzly.streams;

import org.glassfish.grizzly.TransformationException;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.Transformer;
import org.glassfish.grizzly.utils.ResultAware;
import org.glassfish.grizzly.utils.conditions.Condition;

public class StreamDecodeCondition implements Condition {
   private final StreamReader streamReader;
   private final Transformer decoder;
   private final ResultAware resultAware;

   public StreamDecodeCondition(StreamReader streamReader, Transformer decoder, ResultAware resultAware) {
      this.streamReader = streamReader;
      this.decoder = decoder;
      this.resultAware = resultAware;
   }

   public boolean check() {
      TransformationResult result = this.decoder.transform(this.streamReader.getConnection(), this.streamReader);
      TransformationResult.Status status = result.getStatus();
      if (status == TransformationResult.Status.COMPLETE) {
         this.resultAware.setResult(result.getMessage());
         return true;
      } else if (status == TransformationResult.Status.INCOMPLETE) {
         return false;
      } else {
         throw new TransformationException(result.getErrorCode() + ": " + result.getErrorDescription());
      }
   }
}
