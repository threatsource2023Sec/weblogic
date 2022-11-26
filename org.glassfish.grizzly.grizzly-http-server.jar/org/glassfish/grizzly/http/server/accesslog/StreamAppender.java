package org.glassfish.grizzly.http.server.accesslog;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

public class StreamAppender implements AccessLogAppender {
   private static final String LINE_SEPARATOR = System.getProperty("line.separator");
   private final Writer writer;

   public StreamAppender(OutputStream output) {
      this.writer = new OutputStreamWriter(output, Charset.forName("UTF-8"));
   }

   public void append(String accessLogEntry) throws IOException {
      synchronized(this) {
         this.writer.write(accessLogEntry);
         this.writer.write(LINE_SEPARATOR);
         this.writer.flush();
      }
   }

   public void close() throws IOException {
      this.writer.close();
   }
}
