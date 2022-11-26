package weblogic.socket;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import weblogic.utils.collections.PartialOrderSet;

final class NIOSharedWorkSocketMuxer extends NIOSocketMuxer {
   private static final int SPIN_AMOUNT = Integer.getInteger("NIO_SPIN", 10000);
   private final PartialOrderSet jobs;

   private static int getSelectors() {
      return Math.min(Math.max(Integer.getInteger("SELECTORS", MUXERS), 1), MUXERS);
   }

   public NIOSharedWorkSocketMuxer() throws IOException {
      super(getSelectors());
      this.jobs = new PartialOrderSet(10 * MUXERS, MUXERS, SPIN_AMOUNT, SPIN_AMOUNT);

      for(int i = 0; i < this.SELECTORS; ++i) {
         try {
            this.jobs.put(i);
         } catch (InterruptedException var3) {
         }
      }

      this.startSocketReaderThreads("weblogic.socket.Muxer");
   }

   private void addToJobs(ArrayList al) {
      int r = this.jobs.addNonBlocking(al);
      if (r != 0) {
         int i;
         for(i = 0; r < al.size(); ++r) {
            al.set(i, al.get(r));
            ++i;
         }

         if (i == 0) {
            al.clear();
         } else {
            while(r-- > i) {
               al.remove(r);
            }
         }

      }
   }

   public void processSockets() {
      ArrayList al = new ArrayList(0);

      while(true) {
         try {
            int als = al.size();
            Object o;
            if (als == 0) {
               o = this.jobs.take();
            } else if (als == 1) {
               o = this.jobs.putAndTake(al.remove(0));
            } else {
               o = al.remove(als - 2);
               this.addToJobs(al);
            }

            SelectionKey key;
            if (o instanceof SelectionKey) {
               key = (SelectionKey)o;
            } else {
               Integer idx = (Integer)o;
               this.selectFrom(idx, al);
               key = (SelectionKey)al.get(al.size() - 1);
               al.set(al.size() - 1, idx);
               this.addToJobs(al);
            }

            this.process(key);
         } catch (ThreadDeath var6) {
            throw var6;
         } catch (Throwable var7) {
            SocketLogger.logUncaughtThrowable(var7);
         }

         this.takeABreak();
      }
   }
}
