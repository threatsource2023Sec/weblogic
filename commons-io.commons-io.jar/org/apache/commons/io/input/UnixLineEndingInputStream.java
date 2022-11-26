package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

public class UnixLineEndingInputStream extends InputStream {
   private boolean slashNSeen = false;
   private boolean slashRSeen = false;
   private boolean eofSeen = false;
   private final InputStream target;
   private final boolean ensureLineFeedAtEndOfFile;

   public UnixLineEndingInputStream(InputStream in, boolean ensureLineFeedAtEndOfFile) {
      this.target = in;
      this.ensureLineFeedAtEndOfFile = ensureLineFeedAtEndOfFile;
   }

   private int readWithUpdate() throws IOException {
      int target = this.target.read();
      this.eofSeen = target == -1;
      if (this.eofSeen) {
         return target;
      } else {
         this.slashNSeen = target == 10;
         this.slashRSeen = target == 13;
         return target;
      }
   }

   public int read() throws IOException {
      boolean previousWasSlashR = this.slashRSeen;
      if (this.eofSeen) {
         return this.eofGame(previousWasSlashR);
      } else {
         int target = this.readWithUpdate();
         if (this.eofSeen) {
            return this.eofGame(previousWasSlashR);
         } else if (this.slashRSeen) {
            return 10;
         } else {
            return previousWasSlashR && this.slashNSeen ? this.read() : target;
         }
      }
   }

   private int eofGame(boolean previousWasSlashR) {
      if (!previousWasSlashR && this.ensureLineFeedAtEndOfFile) {
         if (!this.slashNSeen) {
            this.slashNSeen = true;
            return 10;
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   public void close() throws IOException {
      super.close();
      this.target.close();
   }

   public synchronized void mark(int readlimit) {
      throw new UnsupportedOperationException("Mark notsupported");
   }
}
