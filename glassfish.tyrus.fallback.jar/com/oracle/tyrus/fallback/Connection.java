package com.oracle.tyrus.fallback;

import com.oracle.tyrus.fallback.spi.ReadHandler;
import java.io.Closeable;
import org.glassfish.tyrus.spi.CompletionHandler;

public interface Connection extends Closeable {
   String getRequestURI();

   void setReadHandler(ReadHandler var1);

   void write(byte[] var1, int var2, int var3, CompletionHandler var4);
}
