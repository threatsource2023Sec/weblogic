package org.glassfish.tyrus.core;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

class BinaryBuffer {
   private final List list = new ArrayList();
   private int bufferSize;
   private int currentlyBuffered = 0;
   private static final Logger LOGGER = Logger.getLogger(BinaryBuffer.class.getName());

   void appendMessagePart(ByteBuffer message) {
      if (this.currentlyBuffered + message.remaining() <= this.bufferSize) {
         this.currentlyBuffered += message.remaining();
         this.list.add(message);
      } else {
         MessageTooBigException messageTooBigException = new MessageTooBigException(LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW());
         LOGGER.log(Level.FINE, LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW(), messageTooBigException);
         throw messageTooBigException;
      }
   }

   ByteBuffer getBufferedContent() {
      ByteBuffer b = ByteBuffer.allocate(this.currentlyBuffered);
      Iterator var2 = this.list.iterator();

      while(var2.hasNext()) {
         ByteBuffer buffered = (ByteBuffer)var2.next();
         b.put(buffered);
      }

      b.flip();
      this.resetBuffer(0);
      return b;
   }

   void resetBuffer(int bufferSize) {
      this.bufferSize = bufferSize;
      this.list.clear();
      this.currentlyBuffered = 0;
   }
}
