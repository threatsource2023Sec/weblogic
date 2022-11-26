package org.glassfish.tyrus.core;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

class TextBuffer {
   private StringBuffer buffer;
   private int bufferSize;
   private static final Logger LOGGER = Logger.getLogger(BinaryBuffer.class.getName());

   void appendMessagePart(String message) {
      if (message != null && message.length() != 0) {
         if (this.buffer.length() + message.length() <= this.bufferSize) {
            this.buffer.append(message);
         } else {
            MessageTooBigException messageTooBigException = new MessageTooBigException(LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW());
            LOGGER.log(Level.FINE, LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW(), messageTooBigException);
            throw messageTooBigException;
         }
      }
   }

   String getBufferedContent() {
      return this.buffer.toString();
   }

   void resetBuffer(int bufferSize) {
      this.bufferSize = bufferSize;
      this.buffer = new StringBuffer();
   }
}
