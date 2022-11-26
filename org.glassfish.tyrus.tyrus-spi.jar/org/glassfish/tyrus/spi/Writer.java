package org.glassfish.tyrus.spi;

import java.io.Closeable;
import java.nio.ByteBuffer;

public abstract class Writer implements Closeable {
   public abstract void write(ByteBuffer var1, CompletionHandler var2);
}
