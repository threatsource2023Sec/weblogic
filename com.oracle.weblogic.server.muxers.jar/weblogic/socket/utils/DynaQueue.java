package weblogic.socket.utils;

public final class DynaQueue implements QueueDef {
   private static final boolean DEBUG = false;
   private boolean verbose = false;
   private Object[] q;
   private int count = 0;
   private int getPos = 0;
   private int putPos = 0;
   private Object[] qput;
   private Object[] qlast;
   private int blockCount = 0;
   private int maxBlockCount = 0;
   private int blockSize = 256;
   private boolean cancelled = false;
   private String name = "(unknown)";
   private int departures = 0;

   public void setVerbose(boolean b) {
      this.verbose = b;
   }

   public String toString() {
      return this.name;
   }

   public DynaQueue(String name, int blockSize) {
      if (blockSize <= 0) {
         throw new IllegalArgumentException("Illegal block size: " + blockSize);
      } else {
         this.q = new Object[blockSize + 1];
         this.name = name;
         this.blockSize = blockSize;
         this.qput = this.qlast = this.q;
         this.q[blockSize] = this.q;
         this.blockCount = 1;
         this.resize(1);
      }
   }

   public int count() {
      return this.count;
   }

   public int size() {
      return this.maxBlockCount == 0 ? Integer.MAX_VALUE : this.maxBlockCount * this.blockSize;
   }

   public int departures() {
      return this.departures;
   }

   public synchronized void put(Object o) throws QueueFullException {
      if (this.putPos == this.blockSize) {
         if (this.qput == this.qlast) {
            if (this.blockCount == this.maxBlockCount) {
               throw new QueueFullException();
            }

            this.resize(1);
         }

         this.qput = (Object[])((Object[])this.qput[this.blockSize]);
         this.putPos = 0;
      }

      ++this.count;
      this.qput[this.putPos++] = o;
      this.notify();
   }

   private final boolean full() {
      return this.blockCount == this.maxBlockCount && this.putPos == this.blockSize;
   }

   public synchronized void putW(Object o) {
      while(this.full() && !this.cancelled) {
         try {
            this.wait();
         } catch (InterruptedException var3) {
         }
      }

      if (!this.full()) {
         try {
            this.put(o);
         } catch (QueueFullException var4) {
         }

      } else if (this.cancelled) {
         if (!this.full()) {
            System.out.println(this + "******!!!!!!!!!! QUEUE2 " + this.cancelled + " count = " + this.count);
         }

         this.cancelled = false;
      } else {
         throw new AssertionError("Queue invariant failed count=" + this.count + "; cancel = " + this.cancelled);
      }
   }

   public synchronized void cancelWait() {
      this.cancelled = true;
      this.notifyAll();
   }

   public void resetCancel() {
      this.cancelled = false;
   }

   public synchronized boolean empty() {
      return this.count == 0;
   }

   public Object peek() {
      return this.count <= 0 ? null : this.q(this.getPos);
   }

   public synchronized Object get() {
      Object next = null;
      if (this.count <= 0) {
         return null;
      } else {
         --this.count;
         next = this.q(this.getPos);
         this.q[this.getPos] = null;
         if (++this.getPos == this.blockSize) {
            if (this.qput == this.q) {
               this.qput = (Object[])((Object[])this.q[this.blockSize]);
               this.putPos = 0;
            }

            if (this.count >= (this.blockCount - 2) * this.blockSize) {
               this.qlast = this.q;
               this.q = (Object[])((Object[])this.q[this.blockSize]);
            } else {
               --this.blockCount;
               Object[] qloser = this.q;
               this.q = (Object[])((Object[])qloser[this.blockSize]);
               qloser[this.blockSize] = null;
               this.qlast[this.blockSize] = this.q;
            }

            this.getPos = 0;
         }

         ++this.departures;
         return next;
      }
   }

   public synchronized Object getW() {
      while(this.count <= 0 && !this.cancelled) {
         try {
            this.wait();
         } catch (InterruptedException var2) {
         }
      }

      if (this.count > 0) {
         return this.get();
      } else if (this.cancelled) {
         if (this.count != 0) {
            System.out.println(this + "******!!!!!!!!!! QUEUE2 " + this.cancelled + " count = " + this.count);
         }

         this.cancelled = false;
         return null;
      } else {
         throw new AssertionError("Queue invarient failed count=" + this.count + "; cancel = " + this.cancelled);
      }
   }

   public synchronized Object getW(int maxWaitMilliseconds) {
      long start = System.currentTimeMillis();
      long toWait = (long)maxWaitMilliseconds;

      while(this.count <= 0 && !this.cancelled) {
         long now = System.currentTimeMillis();
         toWait -= now - start;
         if (toWait <= 0L) {
            break;
         }

         start = now;

         try {
            this.wait(toWait);
         } catch (InterruptedException var9) {
         }
      }

      if (this.count > 0) {
         return this.get();
      } else {
         if (this.cancelled) {
            if (this.count != 0) {
               System.out.println(this + "******!!!!!!!!!! QUEUE2 " + this.cancelled + " count = " + this.count);
            }

            this.cancelled = false;
         }

         return null;
      }
   }

   public synchronized void resize(int delta) {
      Object[] qnext;
      for(this.blockCount += delta; delta > 0; --delta) {
         qnext = new Object[this.blockSize + 1];
         this.qlast[this.blockSize] = qnext;
         qnext[this.blockSize] = this.q;
         this.qlast = qnext;
      }

      while(delta < 0) {
         qnext = (Object[])((Object[])this.qput[this.blockSize]);
         this.qput[this.blockSize] = qnext[this.blockSize];
         qnext[this.blockSize] = null;
         ++delta;
      }

   }

   private Object q(int index) {
      return this.q[index];
   }
}
