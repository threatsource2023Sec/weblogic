package org.glassfish.grizzly.nio;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.spi.SelectorProvider;

public final class Selectors {
   public static Selector newSelector(SelectorProvider provider) throws IOException {
      try {
         return provider.openSelector();
      } catch (NullPointerException var5) {
         int i = 0;

         while(i < 5) {
            try {
               return provider.openSelector();
            } catch (NullPointerException var4) {
               ++i;
            }
         }

         throw new IOException("Can not open Selector due to NPE");
      }
   }
}
