package weblogic.servlet.utils;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;

public class StatOutputStream extends FilterOutputStream implements OutputStreamStatInfo {
   private long cpuTime;
   private long bytesWritten;

   public StatOutputStream(OutputStream out) {
      super(out);
   }

   public long getCpuTime() {
      return this.cpuTime;
   }

   public long getContentLength() {
      return this.bytesWritten;
   }

   public void write(int b) throws IOException {
      long beforeWrite = this.getCurrentThreadCpuTime();
      this.out.write(b);
      this.cpuTime += this.getCurrentThreadCpuTime() - beforeWrite;
      ++this.bytesWritten;
   }

   public void write(byte[] b, int off, int len) throws IOException {
      long beforeWrite = this.getCurrentThreadCpuTime();
      this.out.write(b, off, len);
      this.cpuTime += this.getCurrentThreadCpuTime() - beforeWrite;
      this.bytesWritten += (long)len;
   }

   public void finish() throws IOException {
      if (this.out instanceof ChunkedGzipOutputStream) {
         ((ChunkedGzipOutputStream)this.out).finish();
      }

   }

   private long getCurrentThreadCpuTime() {
      return ManagementFactory.getThreadMXBean().isCurrentThreadCpuTimeSupported() ? ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime() : System.currentTimeMillis();
   }
}
