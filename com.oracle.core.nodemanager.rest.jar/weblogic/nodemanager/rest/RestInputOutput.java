package weblogic.nodemanager.rest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.UUID;
import weblogic.nodemanager.common.NMInputOutput;

public class RestInputOutput extends NMInputOutput {
   private static final String EMPTY_LINE = "\r\n";
   private static final String BOUNDARY_LINE_FORMAT = "--%s\r\n";
   private static final String CONTENT_TYPE_FORMAT = "Content-Type: %s\r\n";
   private InputStream in = null;
   private OutputStream out = null;
   private Deque cmdQueue = new ArrayDeque();
   private Deque resQueue = new ArrayDeque();
   private boolean isMultiPartStream = false;
   private String multiPartBoundry = null;
   private String multiPartContentType = "application/octet-stream";
   private boolean stopOnError = true;

   public RestInputOutput() {
      super(new ByteArrayInputStream(new byte[2]), new ByteArrayOutputStream());
   }

   public RestInputOutput(InputStream in, OutputStream out) {
      super(in, out);
      this.in = in;
      this.out = out;
   }

   public String readLine() throws IOException {
      if (this.isStopOnError() && !this.resQueue.isEmpty() && ((String)this.resQueue.peekLast()).startsWith("-ERR ")) {
         this.cmdQueue.clear();
         return null;
      } else {
         return (String)this.cmdQueue.poll();
      }
   }

   public void writeLine(String line) throws IOException {
      this.resQueue.add(line);
   }

   public void copy(InputStream is) throws IOException {
      this.copy(is, Long.MAX_VALUE, false);
   }

   public void copy(InputStream is, long bytesToCopy) throws IOException {
      this.copy(is, bytesToCopy, true);
   }

   private void copy(InputStream is, long bytesToCopy, boolean restrict) throws IOException {
      if (this.isMultiPartStream) {
         this.out.write(String.format("--%s\r\n", this.multiPartBoundry).getBytes());
         this.out.write(String.format("Content-Type: %s\r\n", this.multiPartContentType).getBytes());
         this.out.write("\r\n".getBytes());
      }

      int read;
      if (restrict) {
         for(int writtenSize = 0; (read = is.read()) != -1 && (long)writtenSize < bytesToCopy; ++writtenSize) {
            this.out.write((byte)read);
         }
      } else {
         while((read = is.read()) != -1) {
            this.out.write((byte)read);
         }
      }

      if (this.isMultiPartStream) {
         this.out.write("\r\n".getBytes());
      }

      this.out.flush();
   }

   public void resetInput(InputStream in) {
      this.in = in;
   }

   public void resetOutput(OutputStream out) {
      this.out = out;
   }

   public void addCommand(String cmd) {
      this.cmdQueue.add(cmd);
   }

   public boolean isMultiPartStream() {
      return this.isMultiPartStream;
   }

   public void setMultiPartStream(boolean isMultiPartStream) {
      if (isMultiPartStream && this.multiPartBoundry == null) {
         this.multiPartBoundry = UUID.randomUUID().toString();
      }

      this.isMultiPartStream = isMultiPartStream;
   }

   public String getMultiPartBoundry() {
      return this.multiPartBoundry;
   }

   public String getResponse() {
      return (String)this.resQueue.poll();
   }

   public String getLatestResponse() {
      return (String)this.resQueue.peekLast();
   }

   public boolean isStopOnError() {
      return this.stopOnError;
   }

   public void setStopOnError(boolean stopOnError) {
      this.stopOnError = stopOnError;
   }
}
