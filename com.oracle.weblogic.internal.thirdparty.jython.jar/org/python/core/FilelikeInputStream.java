package org.python.core;

import java.io.IOException;
import java.io.InputStream;
import org.python.core.util.StringUtil;

public class FilelikeInputStream extends InputStream {
   private PyObject filelike;

   public FilelikeInputStream(PyObject filelike) {
      this.filelike = filelike;
   }

   public int read() throws IOException {
      byte[] oneB = new byte[1];
      int numread = this.read(oneB, 0, 1);
      return numread == -1 ? -1 : oneB[0];
   }

   public int read(byte[] b, int off, int len) throws IOException {
      if (b == null) {
         throw new NullPointerException();
      } else if (off >= 0 && off <= b.length && len >= 0 && off + len <= b.length && off + len >= 0) {
         if (len == 0) {
            return 0;
         } else {
            String result = ((PyString)this.filelike.__getattr__("read").__call__((PyObject)(new PyInteger(len)))).getString();
            if (result.length() == 0) {
               return -1;
            } else {
               System.arraycopy(StringUtil.toBytes(result), 0, b, off, result.length());
               return result.length();
            }
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void close() throws IOException {
      this.filelike.__getattr__("close").__call__();
   }
}
