package com.oracle.tyrus.fallback.spi;

import java.nio.channels.CompletionHandler;
import java.util.Queue;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LongPollingAdapter {
   void read(HttpServletRequest var1, ReadHandler var2, CompletionHandler var3);

   void write(HttpServletResponse var1, Queue var2, CompletionHandler var3);
}
