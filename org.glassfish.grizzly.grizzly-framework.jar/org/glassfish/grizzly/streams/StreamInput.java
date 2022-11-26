package org.glassfish.grizzly.streams;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.utils.conditions.Condition;

public class StreamInput implements Input {
   private final StreamReader streamReader;

   public StreamInput(StreamReader streamReader) {
      this.streamReader = streamReader;
   }

   public GrizzlyFuture notifyCondition(Condition condition, CompletionHandler completionHandler) {
      return this.streamReader.notifyCondition(condition, completionHandler);
   }

   public byte read() throws IOException {
      return this.streamReader.readByte();
   }

   public void skip(int length) {
      this.streamReader.skip(length);
   }

   public boolean isBuffered() {
      return this.streamReader.isSupportBufferWindow();
   }

   public Buffer getBuffer() {
      return this.streamReader.getBufferWindow();
   }

   public Buffer takeBuffer() {
      return this.streamReader.takeBufferWindow();
   }

   public int size() {
      return this.streamReader.available();
   }

   public void close() throws IOException {
      this.streamReader.close();
   }
}
