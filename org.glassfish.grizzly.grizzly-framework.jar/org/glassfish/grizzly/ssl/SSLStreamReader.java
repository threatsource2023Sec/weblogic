package org.glassfish.grizzly.ssl;

import org.glassfish.grizzly.streams.StreamReader;
import org.glassfish.grizzly.streams.TransformerStreamReader;

public class SSLStreamReader extends TransformerStreamReader {
   public SSLStreamReader(StreamReader underlyingReader) {
      super(underlyingReader, new SSLDecoderTransformer());
   }
}
