package oracle.jrockit.jfr;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class ChunksChannel implements ReadableByteChannel {
   private final Iterator chunks;
   private RepositoryChunk current;
   private ReadableByteChannel channel;

   public ChunksChannel(List chunks) throws IOException {
      if (chunks.isEmpty()) {
         throw new FileNotFoundException("No chunks");
      } else {
         ArrayList l = new ArrayList(chunks.size());
         Iterator i$ = chunks.iterator();

         while(i$.hasNext()) {
            RepositoryChunk c = (RepositoryChunk)i$.next();
            c.use();
            l.add(c);
         }

         this.chunks = l.iterator();
         this.nextChannel();
      }
   }

   private boolean nextChunk() {
      if (!this.chunks.hasNext()) {
         return false;
      } else {
         this.current = (RepositoryChunk)this.chunks.next();
         return true;
      }
   }

   private boolean nextChannel() throws IOException {
      if (!this.nextChunk()) {
         return false;
      } else {
         this.channel = Channels.newChannel(this.current.newInputStream());
         return true;
      }
   }

   public int read(ByteBuffer dst) throws IOException {
      do {
         if (this.channel != null) {
            assert this.current != null;

            int r = this.channel.read(dst);
            if (r != -1) {
               return r;
            }

            this.channel.close();
            this.current.release();
            this.channel = null;
            this.current = null;
         }
      } while(this.nextChannel());

      return -1;
   }

   public void transferTo(FileChannel out) throws IOException {
      long pos = 0L;

      do {
         if (this.channel != null) {
            assert this.current != null;

            long w;
            for(long rem = this.current.getSize(); rem > 0L; rem -= w) {
               long n = Math.min(rem, 1048576L);
               w = out.transferFrom(this.channel, pos, n);
               pos += w;
            }

            this.channel.close();
            this.current.release();
            this.channel = null;
            this.current = null;
         }
      } while(this.nextChannel());

   }

   public void close() throws IOException {
      if (this.channel != null) {
         this.channel.close();
         this.channel = null;
      }

      do {
         if (this.current == null) {
            return;
         }

         this.current.release();
      } while(this.nextChunk());

   }

   public boolean isOpen() {
      return this.channel != null;
   }

   protected void finalize() throws Throwable {
      super.finalize();
      this.close();
   }
}
