package org.glassfish.grizzly.nio.tmpselectors;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.Reader;
import org.glassfish.grizzly.Writer;
import org.glassfish.grizzly.localization.LogMessages;

public class TemporarySelectorIO {
   private static final Logger LOGGER = Grizzly.logger(TemporarySelectorIO.class);
   protected TemporarySelectorPool selectorPool;
   private final Reader reader;
   private final Writer writer;

   public TemporarySelectorIO(Reader reader, Writer writer) {
      this(reader, writer, (TemporarySelectorPool)null);
   }

   public TemporarySelectorIO(Reader reader, Writer writer, TemporarySelectorPool selectorPool) {
      this.reader = reader;
      this.writer = writer;
      this.selectorPool = selectorPool;
   }

   public TemporarySelectorPool getSelectorPool() {
      return this.selectorPool;
   }

   public void setSelectorPool(TemporarySelectorPool selectorPool) {
      this.selectorPool = selectorPool;
   }

   public Reader getReader() {
      return this.reader;
   }

   public Writer getWriter() {
      return this.writer;
   }

   protected void recycleTemporaryArtifacts(Selector selector, SelectionKey selectionKey) {
      if (selectionKey != null) {
         try {
            selectionKey.cancel();
         } catch (Exception var4) {
            LOGGER.log(Level.WARNING, LogMessages.WARNING_GRIZZLY_TEMPORARY_SELECTOR_IO_CANCEL_KEY_EXCEPTION(selectionKey), var4);
         }
      }

      if (selector != null) {
         this.selectorPool.offer(selector);
      }

   }
}
