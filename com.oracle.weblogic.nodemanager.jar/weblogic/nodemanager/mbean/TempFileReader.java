package weblogic.nodemanager.mbean;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class TempFileReader extends FileReader {
   private File file;
   private boolean eof;
   private boolean closed;
   private static HashMap files = new HashMap();

   public TempFileReader(File file) throws IOException {
      super(file);
      this.file = file;
      synchronized(files) {
         if (files.containsKey(file)) {
            throw new IllegalArgumentException("A reader is already active for the file");
         } else {
            files.put(file, this);
         }
      }
   }

   public int read() throws IOException {
      synchronized(this.lock) {
         if (this.closed) {
            throw new IOException("Stream is closed");
         } else if (this.eof) {
            return -1;
         } else {
            int c = super.read();
            if (c == -1) {
               this.eof = true;
               this.deleteFile();
            }

            return c;
         }
      }
   }

   public int read(char[] cbuf, int off, int len) throws IOException {
      synchronized(this.lock) {
         if (this.closed) {
            throw new IOException("Stream is closed");
         } else if (this.eof) {
            return -1;
         } else {
            len = super.read(cbuf, off, len);
            if (len == -1) {
               this.eof = true;
               this.deleteFile();
            }

            return len;
         }
      }
   }

   public int read(char[] cbuf) throws IOException {
      return this.read(cbuf, 0, cbuf.length);
   }

   public long skip(long n) throws IOException {
      synchronized(this.lock) {
         if (this.closed) {
            throw new IOException("Stream is closed");
         } else if (this.eof) {
            return -1L;
         } else {
            n = super.skip(n);
            if (n == -1L) {
               this.eof = true;
               this.deleteFile();
            }

            return n;
         }
      }
   }

   public void close() {
      synchronized(this.lock) {
         this.closed = true;
         this.deleteFile();
      }
   }

   private void deleteFile() {
      try {
         super.close();
      } catch (IOException var2) {
      }

      this.file.delete();
   }

   static {
      Runtime.getRuntime().addShutdownHook(new ShutdownHook());
   }

   private static class ShutdownHook extends Thread {
      private ShutdownHook() {
      }

      public void run() {
         File file;
         for(Iterator it = TempFileReader.files.entrySet().iterator(); it.hasNext(); file.delete()) {
            Map.Entry entry = (Map.Entry)it.next();
            file = (File)entry.getKey();
            FileReader reader = (FileReader)entry.getValue();

            try {
               reader.close();
            } catch (IOException var6) {
            }
         }

      }

      // $FF: synthetic method
      ShutdownHook(Object x0) {
         this();
      }
   }
}
