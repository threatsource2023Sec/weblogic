package weblogic.work;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.collections.MaybeMapper;
import weblogic.utils.collections.Turnstile;

public class ConcurrentCalendarQueue implements PriorityRequestQueue {
   private static final DebugCategory debug = Debug.getCategory("weblogic.CalendarQueue");
   private static final int SECONDS = Integer.getInteger("CALENDAR_QUEUE_SECONDS", 2);
   private static final int CAL_SIZE;
   private int[] calendar;
   private int[] last;
   private long[] card_table;
   private AtomicInteger size;
   private long now;
   private int freeList;
   private int allocated;
   private int[] next;
   private long[] time;
   private Object[] data;
   private long[] mt;
   private long busyPeriodStart;
   private final int initialCapacity;
   private final boolean allowShrinking;
   private volatile boolean empty;
   private long queueEmptiedCounter;
   private MaybeMapper expiredElementMapper;
   private long outlier_t;
   private Turnstile outlier_seq;
   private int outliers_head;
   private RequestManager.Callable OR_ELSE;
   private static final int SPIN_AMOUNT;
   private static final int FAST_LANE_SIZE;
   int fw;
   AtomicInteger fr;
   AtomicLong popper_tkt;
   Turnstile popper_seq;
   Object[] fastLane;
   int stolen;

   private static int pow2(int n) {
      if (n < 2) {
         return 1;
      } else {
         --n;
         n |= n >>> 1;
         n |= n >>> 2;
         n |= n >>> 4;
         n |= n >>> 8;
         n |= n >>> 16;
         return n + 1;
      }
   }

   ConcurrentCalendarQueue(boolean allowShrinking, MaybeMapper expiredElementMapper) {
      this(400, allowShrinking, expiredElementMapper);
   }

   ConcurrentCalendarQueue(int capacity, boolean allowShrinking, MaybeMapper expiredElementMapper) {
      this.calendar = new int[CAL_SIZE];
      this.last = new int[this.calendar.length];
      this.card_table = new long[CAL_SIZE / 64];
      this.size = new AtomicInteger();
      this.freeList = 0;
      this.allocated = 0;
      this.empty = true;
      this.outlier_t = 0L;
      this.outlier_seq = new Turnstile();
      this.outliers_head = 0;
      this.OR_ELSE = new RequestManager.Callable() {
         public Object call(Object closure) {
            return closure;
         }
      };
      this.fw = 0;
      this.fr = new AtomicInteger();
      this.popper_tkt = new AtomicLong();
      this.popper_seq = new Turnstile();
      this.fastLane = (Object[])(new Object[FAST_LANE_SIZE]);
      this.initialCapacity = capacity;
      this.allowShrinking = allowShrinking;
      this.expiredElementMapper = expiredElementMapper;
      this.init(capacity);
   }

   private void init(int capacity) {
      this.next = new int[capacity];
      this.time = new long[capacity];
      this.data = (Object[])(new Object[capacity]);
      this.mt = new long[capacity];
   }

   private int shrink() {
      if (this.allowShrinking && this.arraySize() > this.initialCapacity) {
         assert this.verifyEmptyCalendarQueue() : "shrink() does not expect non-empty calendar[]";

         this.init(this.initialCapacity);
         this.freeList = 0;
         this.allocated = 0;
      }

      return this.freeList;
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

   public Object add(Object o, long maybeToken, RequestClass rc, RequestManager.Callable andThen, Object closure) {
      while(true) {
         int n;
         Object v;
         long ticket;
         long t;
         int p;
         label79: {
            synchronized(this) {
               n = this.allocNode();
               if (n > 0) {
                  if (this.size.getAndIncrement() == 0) {
                     this.busyPeriodStart = System.currentTimeMillis();
                     this.empty = false;
                  }

                  long dt = rc.getVirtualTimeIncrement(this.now, this.queueEmptiedCounter);
                  t = this.now + dt;
                  this.data[n] = o;
                  this.mt[n] = maybeToken;
                  this.time[n] = t;
                  v = andThen.call(closure);
                  if (dt < (long)CAL_SIZE) {
                     this.add(this.cal_offset(t), n);
                     return v;
                  }

                  p = this.outliers_head;
                  if (p != 0 && this.time[p] <= t) {
                     ticket = (long)(this.outlier_t++);
                     break label79;
                  }

                  this.next[n] = p;
                  this.outliers_head = n;
                  return v;
               }
            }

            long pt;
            do {
               pt = this.popper_tkt.get();
               this.popper_seq.awaitNonInterruptibly(pt);
            } while(!this.popper_tkt.compareAndSet(pt, pt + 1L));

            synchronized(this) {
               if (this.tooSmall()) {
                  this.outlier_seq.awaitNonInterruptibly((long)(this.outlier_t++));
                  this.grow();
                  this.outlier_seq.advanceAlone();
               }
            }

            this.popper_seq.advanceAlone();
            continue;
         }

         this.outlier_seq.awaitNonInterruptibly(ticket);

         int np;
         while((np = this.next[p]) != 0 && this.time[np] <= t) {
            p = np;
         }

         this.next[n] = np;
         this.next[p] = n;
         this.outlier_seq.advanceAlone();
         return v;
      }
   }

   private void add(int index, int node) {
      int c = this.calendar[index];
      if (c == 0) {
         this.calendar[index] = node;
         this.set_card_table(index);
      } else {
         this.next[this.last[index]] = node;
      }

      this.last[index] = node;
   }

   private boolean tooSmall() {
      return this.allocated == this.next.length - 1;
   }

   private int allocNode() {
      int s = this.freeList;
      int f;
      if (s != 0 && (f = this.next[s]) != 0) {
         this.next[s] = this.next[f];
         this.next[f] = 0;
         return f;
      } else {
         if (this.tooSmall()) {
            s = 0;
         } else {
            s = ++this.allocated;
         }

         return s;
      }
   }

   /** @deprecated */
   @Deprecated
   public Object pop(Object suggestion, RequestClass rci) {
      return suggestion != null ? suggestion : this.pop((MaybeMapper)null, this.OR_ELSE, (Object)null);
   }

   /** @deprecated */
   @Deprecated
   public Object pop() {
      return this.pop((MaybeMapper)null, this.OR_ELSE, (Object)null);
   }

   private int find_next(int i, int lim) {
      int j = i >>> 6;
      long c = this.card_table[j];
      if ((c & 1L << i) != 0L) {
         return i;
      } else {
         assert lim <= CAL_SIZE;

         assert i <= CAL_SIZE;

         assert this.card_table.length << 6 == CAL_SIZE;

         long mask = -1L << i;
         c &= mask;
         if (c == 0L) {
            do {
               ++j;
               if (j > lim - 1 >> 6) {
                  return lim;
               }
            } while((c = this.card_table[j]) == 0L);
         }

         i = (j << 6) + Long.numberOfTrailingZeros(c);
         if (i > lim) {
            i = lim;
         }

         return i;
      }
   }

   private long epoch_begin(long t) {
      return t & (long)(-CAL_SIZE);
   }

   private long epoch_end(long t) {
      return this.epoch_begin(t + (long)CAL_SIZE);
   }

   private int cal_offset(long t) {
      return (int)t & CAL_SIZE - 1;
   }

   private void set_card_table(int b) {
      long[] var10000 = this.card_table;
      var10000[b >>> 6] |= 1L << b;
   }

   private void clear_card_table(int b) {
      long[] var10000 = this.card_table;
      var10000[b >>> 6] &= ~(1L << b);
   }

   private int promote_outliers(long t) {
      t = this.epoch_end(t);
      long c = this.time[this.outliers_head];
      if (c >= t) {
         return -1;
      } else {
         int r = this.cal_offset(c);
         this.outlier_seq.awaitNonInterruptibly((long)(this.outlier_t++));

         do {
            int j;
            for(j = this.outliers_head; this.next[j] != 0 && this.time[this.next[j]] == c; j = this.next[j]) {
            }

            int cn = this.calendar[this.cal_offset(c)];
            if (cn == 0) {
               this.last[this.cal_offset(c)] = j;
               this.set_card_table(this.cal_offset(c));
            }

            this.calendar[this.cal_offset(c)] = this.outliers_head;
            this.outliers_head = this.next[j];
            this.next[j] = cn;
         } while(this.outliers_head != 0 && (c = this.time[this.outliers_head]) < t);

         this.outlier_seq.advanceAlone();
         return r;
      }
   }

   public Object pop(MaybeMapper mu, RequestManager.Callable orElse, Object closure) {
      long me = this.popper_tkt.getAndIncrement();
      this.popper_seq.spinAwaitNonInterruptibly(me, SPIN_AMOUNT);

      Object result;
      int r;
      while((r = this.fr.get()) != this.fw) {
         result = this.fastLane[r & FAST_LANE_SIZE - 1];
         if (this.fr.compareAndSet(r, r + 1)) {
            return result;
         }
      }

      int popped = 0;
      int fl = this.freeList;
      RuntimeException unboxException = null;

      while(true) {
         if (this.stolen == 0) {
            synchronized(this) {
               if (this.size.get() == popped) {
                  this.empty = true;
                  if (this.busyPeriodStart > 0L) {
                     long elapsedTime = System.currentTimeMillis() - this.busyPeriodStart;
                     this.busyPeriodStart = 0L;
                     this.resetVirtualTime();
                     fl = this.shrink();
                     ++this.queueEmptiedCounter;
                  }

                  result = orElse.call(closure);
                  break;
               }

               int lim = this.cal_offset(this.now);
               this.stolen = this.calendar[lim];
               if (this.stolen == 0) {
                  int i = this.find_next(lim, CAL_SIZE);
                  if (i == CAL_SIZE) {
                     if (this.outliers_head != 0 && this.promote_outliers(this.epoch_end(this.now)) >= 0) {
                        lim = CAL_SIZE;
                     }

                     i = this.find_next(0, lim);
                     if (i == lim) {
                        assert this.outliers_head != 0 : "since size was non-zero, and calendar is empty, there must be entries in the outliers queue" + this.calqState(popped);

                        i = this.promote_outliers(this.time[this.outliers_head]);
                     }
                  }

                  lim = i;
                  this.stolen = this.calendar[i];

                  assert this.stolen != 0 : "expect to find a non-empty calendar entry" + this.calqState(popped);

                  this.now = this.time[this.stolen];
               }

               this.calendar[lim] = 0;
               this.clear_card_table(lim);
            }
         }

         int c = this.stolen;
         ++popped;
         this.stolen = this.next[c];
         this.next[c] = fl;
         fl = c;
         result = this.data[c];
         this.data[c] = null;
         long maybeToken = this.mt[c];

         assert result != null : "expected a non-null item in the queue; observed c=" + c + ", stolen=" + this.stolen + ", maybeToken=" + maybeToken + this.calqState(popped);

         if (mu != null) {
            try {
               result = mu.unbox(result, maybeToken);
            } catch (RuntimeException var17) {
               unboxException = var17;
               break;
            }
         }

         if (result != null) {
            if (this.fr.get() + FAST_LANE_SIZE == this.fw || this.popper_tkt.get() == ++me) {
               break;
            }

            this.fastLane[this.fw & FAST_LANE_SIZE - 1] = result;
            ++this.fw;
            this.popper_seq.advanceAlone();
         }
      }

      this.size.getAndAdd(-popped);
      this.freeList = fl;
      this.popper_seq.advanceAlone();
      if (unboxException != null) {
         orElse.call(closure);
         throw unboxException;
      } else {
         return result;
      }
   }

   private void grow() {
      int removed;
      if (this.allowShrinking) {
         removed = this.removeExpiredOutliers();
         if (removed > this.next.length / 2) {
            if (debugEnabled()) {
               debug("no need to grow");
            }

            return;
         }
      }

      removed = 2 * this.next.length;
      this.time = this.copy(this.time, new long[removed]);
      this.data = this.copy(this.data, (Object[])(new Object[removed]));
      this.mt = this.copy(this.mt, new long[removed]);
      this.next = this.copy(this.next, new int[removed]);
      if (debugEnabled()) {
         debug("grow() called. Arrays are of length " + this.next.length);
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

   public int removeExpiredOutliers() {
      if (this.expiredElementMapper == null) {
         return 0;
      } else {
         int node = this.outliers_head;
         int removed = 0;

         int nextNode;
         for(int prev = 0; node > 0; node = nextNode) {
            Object value = this.data[node];
            nextNode = this.next[node];
            if (this.expiredElementMapper.unbox(value, this.mt[node]) == null) {
               prev = node;
            } else {
               this.data[node] = null;
               if (prev == 0) {
                  this.outliers_head = this.next[node];
               } else {
                  this.next[prev] = this.next[node];
               }

               this.next[node] = this.freeList;
               this.freeList = node;
               ++removed;
            }
         }

         this.size.getAndAdd(-removed);
         if (debug.isEnabled()) {
            debug("removeExpiredOutliers() removed " + removed + " entries");
         }

         return removed;
      }
   }

   public int size() {
      return this.size.get();
   }

   public boolean isEmpty() {
      return this.empty;
   }

   public long getMaxValue() {
      return Long.MAX_VALUE - (long)CAL_SIZE;
   }

   private String calqState(int popped) {
      StringBuilder s = new StringBuilder("\n--- Dump begins: ---\n");
      s.append("calendar[" + this.calendar.length + "]\n");
      s.append(this.dumpArray(this.calendar));
      s.append("last[" + this.last.length + "]\n");
      s.append(this.dumpArray(this.last));
      s.append("next[" + this.next.length + "]\n");
      s.append(this.dumpArray(this.next));
      s.append("data[" + this.data.length + "]\n");
      s.append(this.dumpDataArray(this.data));
      s.append("card_table[" + this.card_table.length + "]\n");
      s.append(this.dumpLongArray(this.card_table));
      s.append("now=" + this.now + ", stolen=" + this.stolen + ", popped=" + popped + ", size=" + this.size + "\n");
      return s.toString();
   }

   private String dumpArray(int[] array) {
      StringBuilder s = new StringBuilder();

      for(int i = 0; i < array.length; ++i) {
         s.append(array[i]);
         s.append(" ");
      }

      return s.append("\n").toString();
   }

   private String dumpDataArray(Object[] array) {
      StringBuilder s = new StringBuilder();

      for(int i = 0; i < array.length; ++i) {
         s.append(array[i] == null ? "0" : "1");
         s.append(" ");
      }

      return s.append("\n").toString();
   }

   private String dumpLongArray(long[] array) {
      StringBuilder s = new StringBuilder();

      for(int i = 0; i < array.length; ++i) {
         s.append(Long.toHexString(array[i]));
         s.append(" ");
      }

      return s.append("\n").toString();
   }

   private static boolean debugEnabled() {
      return debug.isEnabled() || SelfTuningWorkManagerImpl.debugEnabled();
   }

   private static void debug(String str) {
      SelfTuningWorkManagerImpl.debug("<ConcurrentCalendarQueue>" + str);
   }

   static {
      CAL_SIZE = 1024 * pow2(SECONDS);
      SPIN_AMOUNT = Integer.getInteger("CALQ_SPIN", 3000);
      FAST_LANE_SIZE = Integer.getInteger("FAST_LANE_SIZE", 256);
   }
}
