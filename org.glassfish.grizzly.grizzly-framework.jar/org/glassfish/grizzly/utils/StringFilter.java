package org.glassfish.grizzly.utils;

import java.nio.charset.Charset;
import org.glassfish.grizzly.filterchain.AbstractCodecFilter;

public final class StringFilter extends AbstractCodecFilter {
   public StringFilter() {
      this((Charset)null, (String)null);
   }

   public StringFilter(Charset charset) {
      this(charset, (String)null);
   }

   public StringFilter(Charset charset, String stringTerminatingSymb) {
      super(new StringDecoder(charset, stringTerminatingSymb), new StringEncoder(charset, stringTerminatingSymb));
   }
}
