package weblogic.work;

import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.collections.MaybeMapper;

public class CalendarQueue implements PriorityRequestQueue {
   private static final DebugCategory debug = Debug.getCategory("weblogic.CalendarQueue");
   private int[] calendar;
   private int size;
   private long now;
   private int freeList;
   private int[] next;
   private int[] last;
   private long[] time;
   private Object[] data;
   private long[] mt;
   private long busyPeriodStart;
   private final int initialCapacity;
   private final boolean allowShrinking;
   private volatile boolean empty;
   private long queueEmptiedCounter;
   private MaybeMapper expiredElementMapper;
   private RequestManager.Callable OR_ELSE;

   CalendarQueue(boolean allowShrinking, MaybeMapper expiredElementMapper) {
      this(400, allowShrinking, expiredElementMapper);
   }

   CalendarQueue(int capacity, boolean allowShrinking, MaybeMapper expiredElementMapper) {
      this.calendar = new int[2048];
      this.freeList = 0;
      this.empty = true;
      this.OR_ELSE = new RequestManager.Callable() {
         public Object call(Object closure) {
            return closure;
         }
      };
      this.initialCapacity = capacity;
      this.allowShrinking = allowShrinking;
      this.expiredElementMapper = expiredElementMapper;
      this.init(capacity);
   }

   private void init(int capacity) {
      this.next = new int[capacity];
      this.last = new int[capacity];
      this.time = new long[capacity];
      this.data = (Object[])(new Object[capacity]);
      this.mt = new long[capacity];
   }

   private void shrink() {
      if (this.allowShrinking && this.arraySize() > this.initialCapacity) {
         assert this.verifyEmptyCalendarQueue() : "shrink() does not expect non-empty calendar[]";

         this.init(this.initialCapacity);
         this.freeList = 0;
      }

   }

   private boolean verifyEmptyCalendarQueue() {
      int arrayLen = this.calendar.length;

      for(int i = 0; i < arrayLen; ++i) {
         if (this.calendar[i] != 0) {
            return false;
         }
      }

      return true;
   }

   int arraySize() {
      return this.next.length;
   }

   private void resetVirtualTime() {
      this.now = 0L;
   }

   public synchronized Object add(Object o, long maybeToken, RequestClass p, RequestManager.Callable andThen, Object closure) {
      if (this.size == 0) {
         this.busyPeriodStart = System.currentTimeMillis();
         this.empty = false;
      }

      long t = this.now + p.getVirtualTimeIncrement(this.now, this.queueEmptiedCounter);
      int n = this.allocNode();
      this.data[n] = o;
      this.mt[n] = maybeToken;
      this.time[n] = t;
      int i = t <= (this.now | 255L) ? (int)t & 255 : (t <= (this.now | 65535L) ? 256 + ((int)(t >>> 8) & 255) : (t <= (this.now | 16777215L) ? 512 + ((int)(t >>> 16) & 255) : (t <= (this.now | 4294967295L) ? 768 + ((int)(t >>> 24) & 255) : (t <= (this.now | 1099511627775L) ? 1024 + ((int)(t >>> 32) & 255) : (t <= (this.now | 281474976710655L) ? 1280 + ((int)(t >>> 40) & 255) : (t <= (this.now | 72057594037927935L) ? 1536 + ((int)(t >>> 48) & 255) : 1792 + ((int)(t >>> 56) & 255)))))));
      this.add(i, n);
      return andThen.call(closure);
   }

   private void add(int index, int node) {
      int c = this.calendar[index];
      if (c == 0) {
         this.calendar[index] = node;
         this.last[node] = node;
      } else {
         this.next[this.last[c]] = node;
         this.last[c] = node;
      }

   }

   private int allocNode() {
      int s = ++this.size;
      int f = this.freeList;
      if (f != 0) {
         this.freeList = this.next[f];
         return f;
      } else {
         if (s == this.next.length) {
            if (this.allowShrinking) {
               int removed = this.removeExpiredElements();
               if (removed > this.next.length / 2) {
                  if (debugEnabled()) {
                     debug("no need to grow");
                  }

                  f = this.freeList;

                  assert f != 0 : "freeList after gc() should not be empty!";

                  this.freeList = this.next[f];
                  return f;
               }
            }

            this.grow();
            if (debugEnabled()) {
               debug("grow() called. Arrays are of length " + this.next.length);
            }
         }

         return s;
      }
   }

   /** @deprecated */
   @Deprecated
   public synchronized Object pop(Object suggestion, RequestClass rci) {
      return suggestion != null ? suggestion : this.pop((MaybeMapper)null, this.OR_ELSE, (Object)null);
   }

   /** @deprecated */
   @Deprecated
   public synchronized Object pop() {
      return this.pop((MaybeMapper)null, this.OR_ELSE, (Object)null);
   }

   public synchronized Object pop(MaybeMapper mu, RequestManager.Callable orElse, Object closure) {
      RuntimeException unboxException = null;

      Object result;
      do {
         if (this.size == 0) {
            this.empty = true;
            if (this.busyPeriodStart > 0L) {
               this.busyPeriodStart = 0L;
               this.resetVirtualTime();
               this.shrink();
               ++this.queueEmptiedCounter;
            }

            return orElse.call(closure);
         }

         --this.size;
         int m = 1;
         int i = (int)this.now & 255;

         while(this.calendar[i] == 0) {
            ++i;
            if ((i & 255) == 0) {
               i = 256 * m + ((int)(this.now >>> 8 * m) & 255);
               ++m;
            }
         }

         int c;
         int l;
         if (i >= 256) {
            if (i >= 512) {
               if (i >= 768) {
                  if (i >= 1024) {
                     if (i >= 1280) {
                        if (i >= 1536) {
                           if (i >= 1792) {
                              i = this.promote(1536, 48, i);
                           }

                           i = this.promote(1280, 40, i);
                        }

                        i = this.promote(1024, 32, i);
                     }

                     i = this.promote(768, 24, i);
                  }

                  i = this.promote(512, 16, i);
               }

               i = this.promote(256, 8, i);
            }

            c = this.calendar[i];
            this.calendar[i] = 0;
            l = this.last[c];
            i = (int)this.time[c] & 255;
            int minI = i;
            this.last[c] = c;
            this.calendar[i] = c;

            while(c != l) {
               c = this.next[c];
               i = (int)this.time[c] & 255;
               this.add(i, c);
               if (i < minI) {
                  minI = i;
               }
            }

            i = minI;
         }

         c = this.calendar[i];
         if (this.last[c] == c) {
            this.calendar[i] = 0;
         } else {
            l = this.next[c];
            this.calendar[i] = l;
            this.last[l] = this.last[c];
         }

         this.next[c] = this.freeList;
         this.freeList = c;
         this.now = this.time[c];
         result = this.data[c];
         this.data[c] = null;
         long maybeToken = this.mt[c];
         if (mu != null) {
            try {
               result = mu.unbox(result, maybeToken);
            } catch (RuntimeException var12) {
               unboxException = var12;
               break;
            }
         }
      } while(result == null);

      if (unboxException != null) {
         if (orElse != null) {
            orElse.call(closure);
         }

         throw unboxException;
      } else {
         return result;
      }
   }

   private int promote(int off, int shift, int i) {
      int c = this.calendar[i];
      this.calendar[i] = 0;
      int l = this.last[c];
      i = off + ((int)(this.time[c] >>> shift) & 255);
      int minI = i;
      this.last[c] = c;
      this.calendar[i] = c;

      while(c != l) {
         c = this.next[c];
         i = off + ((int)(this.time[c] >>> shift) & 255);
         this.add(i, c);
         if (i < minI) {
            minI = i;
         }
      }

      return minI;
   }

   private void grow() {
      int n = 2 * this.next.length;
      this.next = this.copy(this.next, new int[n]);
      this.last = this.copy(this.last, new int[n]);
      this.time = this.copy(this.time, new long[n]);
      this.data = this.copy(this.data, (Object[])(new Object[n]));
      this.mt = this.copy(this.mt, new long[n]);
   }

   private int removeExpiredElements() {
      if (this.expiredElementMapper == null) {
         return 0;
      } else {
         int removed = 0;

         for(int i = 0; i < this.calendar.length; ++i) {
            if (this.calendar[i] > 0) {
               int node = this.calendar[i];
               int lastNode = this.last[node];
               int prev = 0;

               while(node > 0) {
                  int nextNode = this.next[node];
                  Object value = this.data[node];
                  if (this.expiredElementMapper.unbox(value, this.mt[node]) == null) {
                     prev = node;
                  } else {
                     this.data[node] = null;
                     if (prev == 0) {
                        if (node == lastNode) {
                           this.calendar[i] = 0;
                        } else {
                           this.calendar[i] = this.next[node];
                           this.last[this.next[node]] = this.last[node];
                        }
                     } else if (node == lastNode) {
                        this.last[this.calendar[i]] = prev;
                        this.next[prev] = 0;
                     } else {
                        this.next[prev] = this.next[node];
                     }

                     this.next[node] = this.freeList;
                     this.freeList = node;
                     ++removed;
                  }

                  if (node == lastNode) {
                     node = 0;
                  } else {
                     node = nextNode;
                  }
               }
            }
         }

         if (debug.isEnabled()) {
            debug("removeExpiredElements() removed " + removed + " entries");
         }

         this.size -= removed;
         return removed;
      }
   }

   private int[] copy(int[] src, int[] dst) {
      System.arraycopy(src, 0, dst, 0, src.length);
      return dst;
   }

   private long[] copy(long[] src, long[] dst) {
      System.arraycopy(src, 0, dst, 0, src.length);
      return dst;
   }

   private Object[] copy(Object[] src, Object[] dst) {
      System.arraycopy(src, 0, dst, 0, src.length);
      return dst;
   }

   public synchronized int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.empty;
   }

   public long getMaxValue() {
      return Long.MAX_VALUE;
   }

   private void dump() {
      for(int i = 0; i < this.calendar.length; i += 256) {
         this.dumpPeriod(i);
      }

   }

   private void dumpPeriod(int low) {
      int high = low + 256;
      StringBuilder s = new StringBuilder("{");
      String sep = "";

      for(int i = low; i < high; ++i) {
         int c = this.calendar[i];
         if (c != 0) {
            int l = this.last[c];

            while(true) {
               s.append(sep);
               s.append(this.data[c]);
               s.append("(");
               s.append(this.mt[c]);
               s.append(")@");
               s.append(Long.toHexString(this.time[c]));
               sep = ", ";
               if (c == l) {
                  break;
               }

               c = this.next[c];
            }
         }
      }

      System.out.println(s.append("}").toString());
   }

   private static boolean debugEnabled() {
      return debug.isEnabled() || SelfTuningWorkManagerImpl.debugEnabled();
   }

   private static void debug(String str) {
      SelfTuningWorkManagerImpl.debug("<CalendarQueue>" + str);
   }
}
