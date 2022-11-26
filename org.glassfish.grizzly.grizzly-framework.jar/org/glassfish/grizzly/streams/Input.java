package org.glassfish.grizzly.streams;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.utils.conditions.Condition;

public interface Input {
   GrizzlyFuture notifyCondition(Condition var1, CompletionHandler var2);

   byte read() throws IOException;

   void skip(int var1);

   boolean isBuffered();

   Buffer getBuffer();

   Buffer takeBuffer();

   int size();

   void close() throws IOException;
}
