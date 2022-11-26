package org.glassfish.grizzly.streams;

import org.glassfish.grizzly.Transformer;

public class TransformerStreamReader extends AbstractStreamReader {
   public TransformerStreamReader(StreamReader underlyingStream, Transformer transformer) {
      super(underlyingStream.getConnection(), new TransformerInput(transformer, new StreamInput(underlyingStream), underlyingStream.getConnection()));
   }
}
