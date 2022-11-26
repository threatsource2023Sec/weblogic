package com.bea.staxb.buildtime.internal.logger;

import java.io.PrintWriter;

public class SimpleMessageSink implements MessageSink {
   public static final MessageSink STDOUT = new SimpleMessageSink();
   private PrintWriter mOut;

   public SimpleMessageSink() {
      this(new PrintWriter(System.out));
   }

   public SimpleMessageSink(PrintWriter out) {
      if (out == null) {
         throw new IllegalArgumentException();
      } else {
         this.mOut = out;
      }
   }

   public void log(Message msg) {
      this.mOut.print(msg.toString());
      this.mOut.flush();
   }
}
