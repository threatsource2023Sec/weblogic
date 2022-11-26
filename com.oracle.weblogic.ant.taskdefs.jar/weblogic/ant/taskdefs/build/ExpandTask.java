package weblogic.ant.taskdefs.build;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public final class ExpandTask extends Task {
   private String src;
   private String dest = ".";

   public void setSrc(String src) {
      this.src = src;
   }

   public void setDest(String dest) {
      this.dest = dest;
   }

   public void execute() throws BuildException {
      ZipInputStream zis = null;
      ByteBuffer buf = ByteBuffer.allocate(3000000);
      File root = new File(this.dest);

      try {
         zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(this.src), 512));

         while(true) {
            while(true) {
               ZipEntry e = zis.getNextEntry();
               if (e == null) {
                  return;
               }

               File f = new File(root, e.getName());
               if (e.isDirectory()) {
                  ensureDir(f);
               } else {
                  ensureDir(f.getParentFile());
                  byte[] b = buf.array();
                  int remaining = b.length;
                  int off = 0;

                  while(true) {
                     int n = zis.read(b, off, remaining);
                     if (n == -1) {
                        zis.closeEntry();
                        buf.limit(off);
                        FileChannel c = (new RandomAccessFile(f, "rw")).getChannel();
                        c.map(MapMode.READ_WRITE, 0L, (long)off).put(buf);
                        c.close();
                        buf.clear();
                        break;
                     }

                     off += n;
                     remaining -= n;
                     if (remaining == 0) {
                        buf = ByteBuffer.allocate(2 * off);
                        System.out.println("expanding from " + b.length + " to " + buf.capacity() + " for " + f);
                        buf.put(b, 0, off);
                        b = buf.array();
                        remaining = b.length - off;
                     }
                  }
               }
            }
         }
      } catch (IOException var17) {
         throw new BuildException(var17);
      } finally {
         try {
            if (zis != null) {
               zis.close();
            }
         } catch (IOException var16) {
         }

      }
   }

   private static void ensureDir(File f) throws IOException {
      if (f != null) {
         if (!f.exists() || !f.isDirectory()) {
            if (!f.mkdirs()) {
               throw new IOException("failed to create directory " + f.getPath());
            }
         }
      }
   }

   public static void main(String[] argv) throws Exception {
      ExpandTask t = new ExpandTask();
      t.setDest(".");
      t.setSrc(argv[0]);
      t.execute();
   }
}
