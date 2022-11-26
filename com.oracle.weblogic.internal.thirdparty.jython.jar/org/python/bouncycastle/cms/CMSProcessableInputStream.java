package org.python.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.python.bouncycastle.util.io.Streams;

class CMSProcessableInputStream implements CMSProcessable, CMSReadable {
   private InputStream input;
   private boolean used = false;

   public CMSProcessableInputStream(InputStream var1) {
      this.input = var1;
   }

   public InputStream getInputStream() {
      this.checkSingleUsage();
      return this.input;
   }

   public void write(OutputStream var1) throws IOException, CMSException {
      this.checkSingleUsage();
      Streams.pipeAll(this.input, var1);
      this.input.close();
   }

   public Object getContent() {
      return this.getInputStream();
   }

   private synchronized void checkSingleUsage() {
      if (this.used) {
         throw new IllegalStateException("CMSProcessableInputStream can only be used once");
      } else {
         this.used = true;
      }
   }
}
