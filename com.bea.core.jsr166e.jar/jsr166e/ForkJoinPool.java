package jsr166e;

import java.lang.Thread.State;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import sun.misc.Unsafe;

public class ForkJoinPool extends AbstractExecutorService {
   static final ThreadLocal submitters;
   public static final ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory;
   private static final RuntimePermission modifyThreadPermission;
   static final ForkJoinPool common;
   static final int commonParallelism;
   private static int poolNumberSequence;
   private static final long IDLE_TIMEOUT = 2000000000L;
   private static final long FAST_IDLE_TIMEOUT = 200000000L;
   private static final long TIMEOUT_SLOP = 2000000L;
   private static final int MAX_HELP = 64;
   private static final int SEED_INCREMENT = 1640531527;
   private static final int AC_SHIFT = 48;
   private static final int TC_SHIFT = 32;
   private static final int ST_SHIFT = 31;
   private static final int EC_SHIFT = 16;
   private static final int SMASK = 65535;
   private static final int MAX_CAP = 32767;
   private static final int EVENMASK = 65534;
   private static final int SQMASK = 126;
   private static final int SHORT_SIGN = 32768;
   private static final int INT_SIGN = Integer.MIN_VALUE;
   private static final long STOP_BIT = 2147483648L;
   private static final long AC_MASK = -281474976710656L;
   private static final long TC_MASK = 281470681743360L;
   private static final long TC_UNIT = 4294967296L;
   private static final long AC_UNIT = 281474976710656L;
   private static final int UAC_SHIFT = 16;
   private static final int UTC_SHIFT = 0;
   private static final int UAC_MASK = -65536;
   private static final int UTC_MASK = 65535;
   private static final int UAC_UNIT = 65536;
   private static final int UTC_UNIT = 1;
   private static final int E_MASK = Integer.MAX_VALUE;
   private static final int E_SEQ = 65536;
   private static final int SHUTDOWN = Integer.MIN_VALUE;
   private static final int PL_LOCK = 2;
   private static final int PL_SIGNAL = 1;
   private static final int PL_SPINS = 256;
   static final int LIFO_QUEUE = 0;
   static final int FIFO_QUEUE = 1;
   static final int SHARED_QUEUE = -1;
   volatile long pad00;
   volatile long pad01;
   volatile long pad02;
   volatile long pad03;
   volatile long pad04;
   volatile long pad05;
   volatile long pad06;
   volatile long stealCount;
   volatile long ctl;
   volatile int plock;
   volatile int indexSeed;
   final short parallelism;
   final short mode;
   WorkQueue[] workQueues;
   final ForkJoinWorkerThreadFactory factory;
   final Thread.UncaughtExceptionHandler ueh;
   final String workerNamePrefix;
   volatile Object pad10;
   volatile Object pad11;
   volatile Object pad12;
   volatile Object pad13;
   volatile Object pad14;
   volatile Object pad15;
   volatile Object pad16;
   volatile Object pad17;
   volatile Object pad18;
   volatile Object pad19;
   volatile Object pad1a;
   volatile Object pad1b;
   private static final Unsafe U;
   private static final long CTL;
   private static final long PARKBLOCKER;
   private static final int ABASE;
   private static final int ASHIFT;
   private static final long STEALCOUNT;
   private static final long PLOCK;
   private static final long INDEXSEED;
   private static final long QBASE;
   private static final long QLOCK;

   private static void checkPermission() {
      SecurityManager security = System.getSecurityManager();
      if (security != null) {
         security.checkPermission(modifyThreadPermission);
      }

   }

   private static final synchronized int nextPoolId() {
      return ++poolNumberSequence;
   }

   private int acquirePlock() {
      int spins = 256;

      int ps;
      int nps;
      while(((ps = this.plock) & 2) != 0 || !U.compareAndSwapInt(this, PLOCK, ps, nps = ps + 2)) {
         if (spins >= 0) {
            if (ThreadLocalRandom.current().nextInt() >= 0) {
               --spins;
            }
         } else if (U.compareAndSwapInt(this, PLOCK, ps, ps | 1)) {
            synchronized(this) {
               if ((this.plock & 1) != 0) {
                  try {
                     this.wait();
                  } catch (InterruptedException var9) {
                     try {
                        Thread.currentThread().interrupt();
                     } catch (SecurityException var8) {
                     }
                  }
               } else {
                  this.notifyAll();
               }
            }
         }
      }

      return nps;
   }

   private void releasePlock(int ps) {
      this.plock = ps;
      synchronized(this) {
         this.notifyAll();
      }
   }

   private void tryAddWorker() {
      while(true) {
         long c;
         int u;
         int e;
         if ((u = (int)((c = this.ctl) >>> 32)) < 0 && (u & '耀') != 0 && (e = (int)c) >= 0) {
            long nc = (long)(u + 1 & '\uffff' | u + 65536 & -65536) << 32 | (long)e;
            if (!U.compareAndSwapLong(this, CTL, c, nc)) {
               continue;
            }

            Throwable ex = null;
            ForkJoinWorkerThread wt = null;

            try {
               ForkJoinWorkerThreadFactory fac;
               if ((fac = this.factory) != null && (wt = fac.newThread(this)) != null) {
                  wt.start();
                  return;
               }
            } catch (Throwable var11) {
               ex = var11;
            }

            this.deregisterWorker(wt, ex);
         }

         return;
      }
   }

   final WorkQueue registerWorker(ForkJoinWorkerThread wt) {
      wt.setDaemon(true);
      Thread.UncaughtExceptionHandler handler;
      if ((handler = this.ueh) != null) {
         wt.setUncaughtExceptionHandler(handler);
      }

      int s;
      int var10003;
      do {
         do {
            var10003 = s = this.indexSeed;
            s += 1640531527;
         } while(!U.compareAndSwapInt(this, INDEXSEED, var10003, s));
      } while(s == 0);

      int ps;
      WorkQueue w;
      label120: {
         w = new WorkQueue(this, wt, this.mode, s);
         if (((ps = this.plock) & 2) == 0) {
            var10003 = ps;
            ps += 2;
            if (U.compareAndSwapInt(this, PLOCK, var10003, ps)) {
               break label120;
            }
         }

         ps = this.acquirePlock();
      }

      int nps = ps & Integer.MIN_VALUE | ps + 2 & Integer.MAX_VALUE;

      try {
         WorkQueue[] ws;
         if ((ws = this.workQueues) != null) {
            int n = ws.length;
            int m = n - 1;
            int r = s << 1 | 1;
            if (ws[r &= m] != null) {
               int probes = 0;
               int step = n <= 4 ? 2 : (n >>> 1 & '\ufffe') + 2;

               while(ws[r = r + step & m] != null) {
                  ++probes;
                  if (probes >= n) {
                     this.workQueues = ws = (WorkQueue[])Arrays.copyOf(ws, n <<= 1);
                     m = n - 1;
                     probes = 0;
                  }
               }
            }

            w.poolIndex = (short)r;
            w.eventCount = r;
            ws[r] = w;
         }
      } finally {
         if (!U.compareAndSwapInt(this, PLOCK, ps, nps)) {
            this.releasePlock(nps);
         }

      }

      wt.setName(this.workerNamePrefix.concat(Integer.toString(w.poolIndex >>> 1)));
      return w;
   }

   final void deregisterWorker(ForkJoinWorkerThread wt, Throwable ex) {
      WorkQueue w = null;
      if (wt != null && (w = wt.workQueue) != null) {
         w.qlock = -1;

         long sc;
         while(!U.compareAndSwapLong(this, STEALCOUNT, sc = this.stealCount, sc + (long)w.nsteals)) {
         }

         int ps;
         label192: {
            if (((ps = this.plock) & 2) == 0) {
               int var10003 = ps;
               ps += 2;
               if (U.compareAndSwapInt(this, PLOCK, var10003, ps)) {
                  break label192;
               }
            }

            ps = this.acquirePlock();
         }

         int nps = ps & Integer.MIN_VALUE | ps + 2 & Integer.MAX_VALUE;

         try {
            int idx = w.poolIndex;
            WorkQueue[] ws = this.workQueues;
            if (ws != null && idx >= 0 && idx < ws.length && ws[idx] == w) {
               ws[idx] = null;
            }
         } finally {
            if (!U.compareAndSwapInt(this, PLOCK, ps, nps)) {
               this.releasePlock(nps);
            }

         }
      }

      long c;
      while(!U.compareAndSwapLong(this, CTL, c = this.ctl, c - 281474976710656L & -281474976710656L | c - 4294967296L & 281470681743360L | c & 4294967295L)) {
      }

      if (!this.tryTerminate(false, false) && w != null && w.array != null) {
         w.cancelAll();

         int e;
         int u;
         while((u = (int)((c = this.ctl) >>> 32)) < 0 && (e = (int)c) >= 0) {
            if (e <= 0) {
               if ((short)u < 0) {
                  this.tryAddWorker();
               }
               break;
            }

            WorkQueue[] ws;
            int i;
            WorkQueue v;
            if ((ws = this.workQueues) == null || (i = e & '\uffff') >= ws.length || (v = ws[i]) == null) {
               break;
            }

            long nc = (long)(v.nextWait & Integer.MAX_VALUE) | (long)(u + 65536) << 32;
            if (v.eventCount != (e | Integer.MIN_VALUE)) {
               break;
            }

            if (U.compareAndSwapLong(this, CTL, c, nc)) {
               v.eventCount = e + 65536 & Integer.MAX_VALUE;
               Thread p;
               if ((p = v.parker) != null) {
                  U.unpark(p);
               }
               break;
            }
         }
      }

      if (ex == null) {
         ForkJoinTask.helpExpungeStaleExceptions();
      } else {
         ForkJoinTask.rethrow(ex);
      }

   }

   final void externalPush(ForkJoinTask task) {
      Submitter z = (Submitter)submitters.get();
      int ps = this.plock;
      WorkQueue[] ws = this.workQueues;
      WorkQueue q;
      int r;
      int m;
      if (z != null && ps > 0 && ws != null && (m = ws.length - 1) >= 0 && (q = ws[m & (r = z.seed) & 126]) != null && r != 0 && U.compareAndSwapInt(q, QLOCK, 0, 1)) {
         int s;
         int n;
         int am;
         ForkJoinTask[] a;
         if ((a = q.array) != null && (am = a.length - 1) > (n = (s = q.top) - q.base)) {
            int j = ((am & s) << ASHIFT) + ABASE;
            U.putOrderedObject(a, (long)j, task);
            q.top = s + 1;
            q.qlock = 0;
            if (n <= 1) {
               this.signalWork(ws, q);
            }

            return;
         }

         q.qlock = 0;
      }

      this.fullExternalPush(task);
   }

   private void fullExternalPush(ForkJoinTask task) {
      int r = 0;
      Submitter z = (Submitter)submitters.get();

      while(true) {
         while(true) {
            while(true) {
               int var10003;
               if (z == null) {
                  var10003 = r = this.indexSeed;
                  r += 1640531527;
                  if (U.compareAndSwapInt(this, INDEXSEED, var10003, r) && r != 0) {
                     submitters.set(z = new Submitter(r));
                  }
               } else if (r == 0) {
                  r = z.seed;
                  r ^= r << 13;
                  r ^= r >>> 17;
                  z.seed = r ^= r << 5;
               }

               int ps;
               if ((ps = this.plock) < 0) {
                  throw new RejectedExecutionException();
               }

               WorkQueue[] ws;
               int m;
               int nps;
               int s;
               int j;
               if (ps != 0 && (ws = this.workQueues) != null && (m = ws.length - 1) >= 0) {
                  WorkQueue q;
                  int k;
                  if ((q = ws[k = r & m & 126]) != null) {
                     if (q.qlock == 0 && U.compareAndSwapInt(q, QLOCK, 0, 1)) {
                        ForkJoinTask[] a = q.array;
                        s = q.top;
                        boolean submitted = false;

                        try {
                           if (a != null && a.length > s + 1 - q.base || (a = q.growArray()) != null) {
                              j = ((a.length - 1 & s) << ASHIFT) + ABASE;
                              U.putOrderedObject(a, (long)j, task);
                              q.top = s + 1;
                              submitted = true;
                           }
                        } finally {
                           q.qlock = 0;
                        }

                        if (submitted) {
                           this.signalWork(ws, q);
                           return;
                        }
                     }

                     r = 0;
                  } else if ((this.plock & 2) != 0) {
                     r = 0;
                  } else {
                     label234: {
                        q = new WorkQueue(this, (ForkJoinWorkerThread)null, -1, r);
                        q.poolIndex = (short)k;
                        if (((ps = this.plock) & 2) == 0) {
                           var10003 = ps;
                           ps += 2;
                           if (U.compareAndSwapInt(this, PLOCK, var10003, ps)) {
                              break label234;
                           }
                        }

                        ps = this.acquirePlock();
                     }

                     if ((ws = this.workQueues) != null && k < ws.length && ws[k] == null) {
                        ws[k] = q;
                     }

                     nps = ps & Integer.MIN_VALUE | ps + 2 & Integer.MAX_VALUE;
                     if (!U.compareAndSwapInt(this, PLOCK, ps, nps)) {
                        this.releasePlock(nps);
                     }
                  }
               } else {
                  WorkQueue[] nws;
                  label214: {
                     nps = this.parallelism;
                     s = nps > 1 ? nps - 1 : 1;
                     s |= s >>> 1;
                     s |= s >>> 2;
                     s |= s >>> 4;
                     s |= s >>> 8;
                     s |= s >>> 16;
                     s = s + 1 << 1;
                     nws = (ws = this.workQueues) != null && ws.length != 0 ? null : new WorkQueue[s];
                     if (((ps = this.plock) & 2) == 0) {
                        var10003 = ps;
                        ps += 2;
                        if (U.compareAndSwapInt(this, PLOCK, var10003, ps)) {
                           break label214;
                        }
                     }

                     ps = this.acquirePlock();
                  }

                  if (((ws = this.workQueues) == null || ws.length == 0) && nws != null) {
                     this.workQueues = nws;
                  }

                  j = ps & Integer.MIN_VALUE | ps + 2 & Integer.MAX_VALUE;
                  if (!U.compareAndSwapInt(this, PLOCK, ps, j)) {
                     this.releasePlock(j);
                  }
               }
            }
         }
      }
   }

   final void incrementActiveCount() {
      long c;
      while(!U.compareAndSwapLong(this, CTL, c = this.ctl, c & 281474976710655L | (c & -281474976710656L) + 281474976710656L)) {
      }

   }

   final void signalWork(WorkQueue[] ws, WorkQueue q) {
      while(true) {
         long c;
         int u;
         if ((u = (int)((c = this.ctl) >>> 32)) < 0) {
            int e;
            if ((e = (int)c) <= 0) {
               if ((short)u < 0) {
                  this.tryAddWorker();
               }
            } else {
               int i;
               WorkQueue w;
               if (ws != null && ws.length > (i = e & '\uffff') && (w = ws[i]) != null) {
                  long nc = (long)(w.nextWait & Integer.MAX_VALUE) | (long)(u + 65536) << 32;
                  int ne = e + 65536 & Integer.MAX_VALUE;
                  if (w.eventCount == (e | Integer.MIN_VALUE) && U.compareAndSwapLong(this, CTL, c, nc)) {
                     w.eventCount = ne;
                     Thread p;
                     if ((p = w.parker) != null) {
                        U.unpark(p);
                     }
                  } else if (q == null || q.base < q.top) {
                     continue;
                  }
               }
            }
         }

         return;
      }
   }

   final void runWorker(WorkQueue w) {
      w.growArray();

      for(int r = w.hint; this.scan(w, r) == 0; r ^= r << 5) {
         r ^= r << 13;
         r ^= r >>> 17;
      }

   }

   private final int scan(WorkQueue w, int r) {
      long c = this.ctl;
      WorkQueue[] ws;
      int m;
      if ((ws = this.workQueues) != null && (m = ws.length - 1) >= 0 && w != null) {
         int j = m + m + 1;
         int ec = w.eventCount;

         while(true) {
            WorkQueue q;
            int b;
            ForkJoinTask[] a;
            long nc;
            if ((q = ws[r - j & m]) != null && (b = q.base) - q.top < 0 && (a = q.array) != null) {
               nc = (long)(((a.length - 1 & b) << ASHIFT) + ABASE);
               ForkJoinTask t;
               if ((t = (ForkJoinTask)U.getObjectVolatile(a, nc)) != null) {
                  if (ec < 0) {
                     this.helpRelease(c, ws, w, q, b);
                  } else if (q.base == b && U.compareAndSwapObject(a, nc, t, (Object)null)) {
                     U.putOrderedInt(q, QBASE, b + 1);
                     if (b + 1 - q.top < 0) {
                        this.signalWork(ws, q);
                     }

                     w.runTask(t);
                  }
               }
               break;
            }

            --j;
            if (j < 0) {
               int e;
               if ((ec | (e = (int)c)) < 0) {
                  return this.awaitWork(w, c, ec);
               }

               if (this.ctl == c) {
                  nc = (long)ec | c - 281474976710656L & -4294967296L;
                  w.nextWait = e;
                  w.eventCount = ec | Integer.MIN_VALUE;
                  if (!U.compareAndSwapLong(this, CTL, c, nc)) {
                     w.eventCount = ec;
                  }
               }
               break;
            }
         }
      }

      return 0;
   }

   private final int awaitWork(WorkQueue w, long c, int ec) {
      int stat;
      if ((stat = w.qlock) >= 0 && w.eventCount == ec && this.ctl == c && !Thread.interrupted()) {
         int e = (int)c;
         int u = (int)(c >>> 32);
         int d = (u >> 16) + this.parallelism;
         if (e >= 0 && (d > 0 || !this.tryTerminate(false, false))) {
            int ns;
            long pc;
            if ((ns = w.nsteals) != 0) {
               w.nsteals = 0;

               while(!U.compareAndSwapLong(this, STEALCOUNT, pc = this.stealCount, pc + (long)ns)) {
               }
            } else {
               pc = d <= 0 && ec == (e | Integer.MIN_VALUE) ? (long)(w.nextWait & Integer.MAX_VALUE) | (long)(u + 65536) << 32 : 0L;
               long parkTime;
               long deadline;
               if (pc != 0L) {
                  int dc = -((short)((int)(c >>> 32)));
                  parkTime = dc < 0 ? 200000000L : (long)(dc + 1) * 2000000000L;
                  deadline = System.nanoTime() + parkTime - 2000000L;
               } else {
                  deadline = 0L;
                  parkTime = 0L;
               }

               if (w.eventCount == ec && this.ctl == c) {
                  Thread wt = Thread.currentThread();
                  U.putObject(wt, PARKBLOCKER, this);
                  w.parker = wt;
                  if (w.eventCount == ec && this.ctl == c) {
                     U.park(false, parkTime);
                  }

                  w.parker = null;
                  U.putObject(wt, PARKBLOCKER, (Object)null);
                  if (parkTime != 0L && this.ctl == c && deadline - System.nanoTime() <= 0L && U.compareAndSwapLong(this, CTL, c, pc)) {
                     stat = w.qlock = -1;
                  }
               }
            }
         } else {
            stat = w.qlock = -1;
         }
      }

      return stat;
   }

   private final void helpRelease(long c, WorkQueue[] ws, WorkQueue w, WorkQueue q, int b) {
      WorkQueue v;
      int e;
      int i;
      if (w != null && w.eventCount < 0 && (e = (int)c) > 0 && ws != null && ws.length > (i = e & '\uffff') && (v = ws[i]) != null && this.ctl == c) {
         long nc = (long)(v.nextWait & Integer.MAX_VALUE) | (long)((int)(c >>> 32) + 65536) << 32;
         int ne = e + 65536 & Integer.MAX_VALUE;
         if (q != null && q.base == b && w.eventCount < 0 && v.eventCount == (e | Integer.MIN_VALUE) && U.compareAndSwapLong(this, CTL, c, nc)) {
            v.eventCount = ne;
            Thread p;
            if ((p = v.parker) != null) {
               U.unpark(p);
            }
         }
      }

   }

   private int tryHelpStealer(WorkQueue joiner, ForkJoinTask task) {
      int stat = 0;
      int steps = 0;
      if (task != null && joiner != null && joiner.base - joiner.top >= 0) {
         label119:
         while(true) {
            ForkJoinTask subtask = task;

            WorkQueue v;
            int s;
            for(WorkQueue j = joiner; (s = task.status) >= 0; j = v) {
               WorkQueue[] ws;
               int m;
               if ((ws = this.workQueues) == null || (m = ws.length - 1) <= 0) {
                  return stat;
               }

               int h;
               if ((v = ws[h = (j.hint | 1) & m]) == null || v.currentSteal != subtask) {
                  int origin = h;

                  while(true) {
                     if (((h = h + 2 & m) & 15) == 1 && (subtask.status < 0 || j.currentJoin != subtask)) {
                        continue label119;
                     }

                     if ((v = ws[h]) != null && v.currentSteal == subtask) {
                        j.hint = h;
                        break;
                     }

                     if (h == origin) {
                        return stat;
                     }
                  }
               }

               while(true) {
                  if (subtask.status < 0) {
                     continue label119;
                  }

                  int b;
                  ForkJoinTask[] a;
                  if ((b = v.base) - v.top >= 0 || (a = v.array) == null) {
                     ForkJoinTask next = v.currentJoin;
                     if (subtask.status < 0 || j.currentJoin != subtask || v.currentSteal != subtask) {
                        continue label119;
                     }

                     if (next == null) {
                        return stat;
                     }

                     ++steps;
                     if (steps == 64) {
                        return stat;
                     }

                     subtask = next;
                     break;
                  }

                  int i = ((a.length - 1 & b) << ASHIFT) + ABASE;
                  ForkJoinTask t = (ForkJoinTask)U.getObjectVolatile(a, (long)i);
                  if (subtask.status < 0 || j.currentJoin != subtask || v.currentSteal != subtask) {
                     continue label119;
                  }

                  stat = 1;
                  if (v.base == b) {
                     if (t == null) {
                        return stat;
                     }

                     if (U.compareAndSwapObject(a, (long)i, t, (Object)null)) {
                        U.putOrderedInt(v, QBASE, b + 1);
                        ForkJoinTask ps = joiner.currentSteal;
                        int jt = joiner.top;

                        do {
                           joiner.currentSteal = t;
                           t.doExec();
                        } while(task.status >= 0 && joiner.top != jt && (t = joiner.pop()) != null);

                        joiner.currentSteal = ps;
                        return stat;
                     }
                  }
               }
            }

            stat = s;
            break;
         }
      }

      return stat;
   }

   private int helpComplete(WorkQueue joiner, CountedCompleter task) {
      int s = 0;
      WorkQueue[] ws;
      int m;
      if ((ws = this.workQueues) != null && (m = ws.length - 1) >= 0 && joiner != null && task != null) {
         int j = joiner.poolIndex;
         int scans = m + m + 1;
         long c = 0L;

         for(int k = scans; (s = task.status) >= 0; j += 2) {
            if (joiner.internalPopAndExecCC(task)) {
               k = scans;
            } else {
               if ((s = task.status) < 0) {
                  break;
               }

               WorkQueue q;
               if ((q = ws[j & m]) != null && q.pollAndExecCC(task)) {
                  k = scans;
               } else {
                  --k;
                  if (k < 0) {
                     if (c == (c = this.ctl)) {
                        break;
                     }

                     k = scans;
                  }
               }
            }
         }
      }

      return s;
   }

   final boolean tryCompensate(long c) {
      WorkQueue[] ws = this.workQueues;
      int pc = this.parallelism;
      int e = (int)c;
      int m;
      if (ws != null && (m = ws.length - 1) >= 0 && e >= 0 && this.ctl == c) {
         WorkQueue w = ws[e & m];
         if (e != 0 && w != null) {
            long nc = (long)(w.nextWait & Integer.MAX_VALUE) | c & -4294967296L;
            int ne = e + 65536 & Integer.MAX_VALUE;
            if (w.eventCount == (e | Integer.MIN_VALUE) && U.compareAndSwapLong(this, CTL, c, nc)) {
               w.eventCount = ne;
               Thread p;
               if ((p = w.parker) != null) {
                  U.unpark(p);
               }

               return true;
            }
         } else {
            short tc;
            long nc;
            if ((tc = (short)((int)(c >>> 32))) >= 0 && (int)(c >> 48) + pc > 1) {
               nc = c - 281474976710656L & -281474976710656L | c & 281474976710655L;
               if (U.compareAndSwapLong(this, CTL, c, nc)) {
                  return true;
               }
            } else if (tc + pc < 32767) {
               nc = c + 4294967296L & 281470681743360L | c & -281470681743361L;
               if (U.compareAndSwapLong(this, CTL, c, nc)) {
                  Throwable ex = null;
                  ForkJoinWorkerThread wt = null;

                  try {
                     ForkJoinWorkerThreadFactory fac;
                     if ((fac = this.factory) != null && (wt = fac.newThread(this)) != null) {
                        wt.start();
                        return true;
                     }
                  } catch (Throwable var15) {
                     ex = var15;
                  }

                  this.deregisterWorker(wt, ex);
               }
            }
         }
      }

      return false;
   }

   final int awaitJoin(WorkQueue joiner, ForkJoinTask task) {
      int s = 0;
      if (task != null && (s = task.status) >= 0 && joiner != null) {
         ForkJoinTask prevJoin = joiner.currentJoin;
         joiner.currentJoin = task;

         while(joiner.tryRemoveAndExec(task) && (s = task.status) >= 0) {
         }

         if (s >= 0 && task instanceof CountedCompleter) {
            s = this.helpComplete(joiner, (CountedCompleter)task);
         }

         long cc = 0L;

         while(true) {
            while(true) {
               do {
                  do {
                     if (s < 0 || (s = task.status) < 0) {
                        joiner.currentJoin = prevJoin;
                        return s;
                     }
                  } while((s = this.tryHelpStealer(joiner, task)) != 0);
               } while((s = task.status) < 0);

               if (!this.tryCompensate(cc)) {
                  cc = this.ctl;
               } else {
                  if (task.trySetSignal() && (s = task.status) >= 0) {
                     synchronized(task) {
                        if (task.status >= 0) {
                           try {
                              task.wait();
                           } catch (InterruptedException var10) {
                           }
                        } else {
                           task.notifyAll();
                        }
                     }
                  }

                  long c;
                  while(!U.compareAndSwapLong(this, CTL, c = this.ctl, c & 281474976710655L | (c & -281474976710656L) + 281474976710656L)) {
                  }
               }
            }
         }
      } else {
         return s;
      }
   }

   final void helpJoinOnce(WorkQueue joiner, ForkJoinTask task) {
      int s;
      if (joiner != null && task != null && (s = task.status) >= 0) {
         ForkJoinTask prevJoin = joiner.currentJoin;
         joiner.currentJoin = task;

         while(joiner.tryRemoveAndExec(task) && (s = task.status) >= 0) {
         }

         if (s >= 0) {
            if (task instanceof CountedCompleter) {
               this.helpComplete(joiner, (CountedCompleter)task);
            }

            while(task.status >= 0 && this.tryHelpStealer(joiner, task) > 0) {
            }
         }

         joiner.currentJoin = prevJoin;
      }

   }

   private WorkQueue findNonEmptyStealQueue() {
      int r = ThreadLocalRandom.current().nextInt();

      int ps;
      do {
         ps = this.plock;
         int m;
         WorkQueue[] ws;
         if ((ws = this.workQueues) != null && (m = ws.length - 1) >= 0) {
            for(int j = m + 1 << 2; j >= 0; --j) {
               WorkQueue q;
               if ((q = ws[(r - j << 1 | 1) & m]) != null && q.base - q.top < 0) {
                  return q;
               }
            }
         }
      } while(this.plock != ps);

      return null;
   }

   final void helpQuiescePool(WorkQueue w) {
      ForkJoinTask ps = w.currentSteal;
      boolean active = true;

      while(true) {
         while(true) {
            ForkJoinTask t;
            while((t = w.nextLocalTask()) != null) {
               t.doExec();
            }

            long c;
            WorkQueue q;
            if ((q = this.findNonEmptyStealQueue()) == null) {
               if (active) {
                  long nc = (c = this.ctl) & 281474976710655L | (c & -281474976710656L) - 281474976710656L;
                  if ((int)(nc >> 48) + this.parallelism == 0) {
                     return;
                  }

                  if (U.compareAndSwapLong(this, CTL, c, nc)) {
                     active = false;
                  }
               } else if ((int)((c = this.ctl) >> 48) + this.parallelism <= 0 && U.compareAndSwapLong(this, CTL, c, c & 281474976710655L | (c & -281474976710656L) + 281474976710656L)) {
                  return;
               }
            } else {
               if (!active) {
                  active = true;

                  while(!U.compareAndSwapLong(this, CTL, c = this.ctl, c & 281474976710655L | (c & -281474976710656L) + 281474976710656L)) {
                  }
               }

               int b;
               if ((b = q.base) - q.top < 0 && (t = q.pollAt(b)) != null) {
                  (w.currentSteal = t).doExec();
                  w.currentSteal = ps;
               }
            }
         }
      }
   }

   final ForkJoinTask nextTaskFor(WorkQueue w) {
      ForkJoinTask t;
      WorkQueue q;
      int b;
      do {
         if ((t = w.nextLocalTask()) != null) {
            return t;
         }

         if ((q = this.findNonEmptyStealQueue()) == null) {
            return null;
         }
      } while((b = q.base) - q.top >= 0 || (t = q.pollAt(b)) == null);

      return t;
   }

   static int getSurplusQueuedTaskCount() {
      Thread t;
      if ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
         ForkJoinWorkerThread wt;
         ForkJoinPool pool;
         int p = (pool = (wt = (ForkJoinWorkerThread)t).pool).parallelism;
         WorkQueue q;
         int n = (q = wt.workQueue).top - q.base;
         int a = (int)(pool.ctl >> 48) + p;
         return n - (a > (p >>>= 1) ? 0 : (a > (p >>>= 1) ? 1 : (a > (p >>>= 1) ? 2 : (a > (p >>>= 1) ? 4 : 8))));
      } else {
         return 0;
      }
   }

   private boolean tryTerminate(boolean now, boolean enable) {
      if (this == common) {
         return false;
      } else {
         int ps;
         if ((ps = this.plock) >= 0) {
            if (!enable) {
               return false;
            }

            label131: {
               if ((ps & 2) == 0) {
                  int var10003 = ps;
                  ps += 2;
                  if (U.compareAndSwapInt(this, PLOCK, var10003, ps)) {
                     break label131;
                  }
               }

               ps = this.acquirePlock();
            }

            int nps = ps + 2 & Integer.MAX_VALUE | Integer.MIN_VALUE;
            if (!U.compareAndSwapInt(this, PLOCK, ps, nps)) {
               this.releasePlock(nps);
            }
         }

         long c;
         while(((c = this.ctl) & 2147483648L) == 0L) {
            if (!now) {
               if ((int)(c >> 48) + this.parallelism > 0) {
                  return false;
               }

               WorkQueue[] ws;
               if ((ws = this.workQueues) != null) {
                  for(int i = 0; i < ws.length; ++i) {
                     WorkQueue w;
                     if ((w = ws[i]) != null && (!w.isEmpty() || (i & 1) != 0 && w.eventCount >= 0)) {
                        this.signalWork(ws, w);
                        return false;
                     }
                  }
               }
            }

            if (U.compareAndSwapLong(this, CTL, c, c | 2147483648L)) {
               for(int pass = 0; pass < 3; ++pass) {
                  WorkQueue[] ws;
                  if ((ws = this.workQueues) != null) {
                     int n = ws.length;

                     int i;
                     WorkQueue w;
                     for(i = 0; i < n; ++i) {
                        if ((w = ws[i]) != null) {
                           w.qlock = -1;
                           if (pass > 0) {
                              w.cancelAll();
                              ForkJoinWorkerThread wt;
                              if (pass > 1 && (wt = w.owner) != null) {
                                 if (!wt.isInterrupted()) {
                                    try {
                                       wt.interrupt();
                                    } catch (Throwable var19) {
                                    }
                                 }

                                 U.unpark(wt);
                              }
                           }
                        }
                     }

                     int e;
                     long cc;
                     while((e = (int)(cc = this.ctl) & Integer.MAX_VALUE) != 0 && (i = e & '\uffff') < n && i >= 0 && (w = ws[i]) != null) {
                        long nc = (long)(w.nextWait & Integer.MAX_VALUE) | cc + 281474976710656L & -281474976710656L | cc & 281472829227008L;
                        if (w.eventCount == (e | Integer.MIN_VALUE) && U.compareAndSwapLong(this, CTL, cc, nc)) {
                           w.eventCount = e + 65536 & Integer.MAX_VALUE;
                           w.qlock = -1;
                           Thread p;
                           if ((p = w.parker) != null) {
                              U.unpark(p);
                           }
                        }
                     }
                  }
               }
            }
         }

         if ((short)((int)(c >>> 32)) + this.parallelism <= 0) {
            synchronized(this) {
               this.notifyAll();
            }
         }

         return true;
      }
   }

   static WorkQueue commonSubmitterQueue() {
      Submitter z;
      ForkJoinPool p;
      WorkQueue[] ws;
      int m;
      return (z = (Submitter)submitters.get()) != null && (p = common) != null && (ws = p.workQueues) != null && (m = ws.length - 1) >= 0 ? ws[m & z.seed & 126] : null;
   }

   final boolean tryExternalUnpush(ForkJoinTask task) {
      Submitter z = (Submitter)submitters.get();
      WorkQueue[] ws = this.workQueues;
      boolean popped = false;
      WorkQueue joiner;
      ForkJoinTask[] a;
      int m;
      int s;
      if (z != null && ws != null && (m = ws.length - 1) >= 0 && (joiner = ws[z.seed & m & 126]) != null && joiner.base != (s = joiner.top) && (a = joiner.array) != null) {
         long j = (long)(((a.length - 1 & s - 1) << ASHIFT) + ABASE);
         if (U.getObject(a, j) == task && U.compareAndSwapInt(joiner, QLOCK, 0, 1)) {
            if (joiner.top == s && joiner.array == a && U.compareAndSwapObject(a, j, task, (Object)null)) {
               joiner.top = s - 1;
               popped = true;
            }

            joiner.qlock = 0;
         }
      }

      return popped;
   }

   final int externalHelpComplete(CountedCompleter task) {
      Submitter z = (Submitter)submitters.get();
      WorkQueue[] ws = this.workQueues;
      int s = 0;
      WorkQueue joiner;
      int m;
      int j;
      if (z != null && ws != null && (m = ws.length - 1) >= 0 && (joiner = ws[(j = z.seed) & m & 126]) != null && task != null) {
         int scans = m + m + 1;
         long c = 0L;
         j |= 1;

         for(int k = scans; (s = task.status) >= 0; j += 2) {
            if (joiner.externalPopAndExecCC(task)) {
               k = scans;
            } else {
               if ((s = task.status) < 0) {
                  break;
               }

               WorkQueue q;
               if ((q = ws[j & m]) != null && q.pollAndExecCC(task)) {
                  k = scans;
               } else {
                  --k;
                  if (k < 0) {
                     if (c == (c = this.ctl)) {
                        break;
                     }

                     k = scans;
                  }
               }
            }
         }
      }

      return s;
   }

   public ForkJoinPool() {
      this(Math.min(32767, Runtime.getRuntime().availableProcessors()), defaultForkJoinWorkerThreadFactory, (Thread.UncaughtExceptionHandler)null, false);
   }

   public ForkJoinPool(int parallelism) {
      this(parallelism, defaultForkJoinWorkerThreadFactory, (Thread.UncaughtExceptionHandler)null, false);
   }

   public ForkJoinPool(int parallelism, ForkJoinWorkerThreadFactory factory, Thread.UncaughtExceptionHandler handler, boolean asyncMode) {
      this(checkParallelism(parallelism), checkFactory(factory), handler, asyncMode ? 1 : 0, "ForkJoinPool-" + nextPoolId() + "-worker-");
      checkPermission();
   }

   private static int checkParallelism(int parallelism) {
      if (parallelism > 0 && parallelism <= 32767) {
         return parallelism;
      } else {
         throw new IllegalArgumentException();
      }
   }

   private static ForkJoinWorkerThreadFactory checkFactory(ForkJoinWorkerThreadFactory factory) {
      if (factory == null) {
         throw new NullPointerException();
      } else {
         return factory;
      }
   }

   private ForkJoinPool(int parallelism, ForkJoinWorkerThreadFactory factory, Thread.UncaughtExceptionHandler handler, int mode, String workerNamePrefix) {
      this.workerNamePrefix = workerNamePrefix;
      this.factory = factory;
      this.ueh = handler;
      this.mode = (short)mode;
      this.parallelism = (short)parallelism;
      long np = (long)(-parallelism);
      this.ctl = np << 48 & -281474976710656L | np << 32 & 281470681743360L;
   }

   public static ForkJoinPool commonPool() {
      return common;
   }

   public Object invoke(ForkJoinTask task) {
      if (task == null) {
         throw new NullPointerException();
      } else {
         this.externalPush(task);
         return task.join();
      }
   }

   public void execute(ForkJoinTask task) {
      if (task == null) {
         throw new NullPointerException();
      } else {
         this.externalPush(task);
      }
   }

   public void execute(Runnable task) {
      if (task == null) {
         throw new NullPointerException();
      } else {
         Object job;
         if (task instanceof ForkJoinTask) {
            job = (ForkJoinTask)task;
         } else {
            job = new ForkJoinTask.RunnableExecuteAction(task);
         }

         this.externalPush((ForkJoinTask)job);
      }
   }

   public ForkJoinTask submit(ForkJoinTask task) {
      if (task == null) {
         throw new NullPointerException();
      } else {
         this.externalPush(task);
         return task;
      }
   }

   public ForkJoinTask submit(Callable task) {
      ForkJoinTask job = new ForkJoinTask.AdaptedCallable(task);
      this.externalPush(job);
      return job;
   }

   public ForkJoinTask submit(Runnable task, Object result) {
      ForkJoinTask job = new ForkJoinTask.AdaptedRunnable(task, result);
      this.externalPush(job);
      return job;
   }

   public ForkJoinTask submit(Runnable task) {
      if (task == null) {
         throw new NullPointerException();
      } else {
         Object job;
         if (task instanceof ForkJoinTask) {
            job = (ForkJoinTask)task;
         } else {
            job = new ForkJoinTask.AdaptedRunnableAction(task);
         }

         this.externalPush((ForkJoinTask)job);
         return (ForkJoinTask)job;
      }
   }

   public List invokeAll(Collection tasks) {
      ArrayList futures = new ArrayList(tasks.size());
      boolean done = false;
      boolean var11 = false;

      ArrayList var14;
      int i;
      try {
         var11 = true;
         Iterator i$ = tasks.iterator();

         while(i$.hasNext()) {
            Callable t = (Callable)i$.next();
            ForkJoinTask f = new ForkJoinTask.AdaptedCallable(t);
            futures.add(f);
            this.externalPush(f);
         }

         int i = 0;
         i = futures.size();

         while(true) {
            if (i >= i) {
               done = true;
               var14 = futures;
               var11 = false;
               break;
            }

            ((ForkJoinTask)futures.get(i)).quietlyJoin();
            ++i;
         }
      } finally {
         if (var11) {
            if (!done) {
               int i = 0;

               for(int size = futures.size(); i < size; ++i) {
                  ((Future)futures.get(i)).cancel(false);
               }
            }

         }
      }

      if (!done) {
         i = 0;

         for(int size = futures.size(); i < size; ++i) {
            ((Future)futures.get(i)).cancel(false);
         }
      }

      return var14;
   }

   public ForkJoinWorkerThreadFactory getFactory() {
      return this.factory;
   }

   public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
      return this.ueh;
   }

   public int getParallelism() {
      short par;
      return (par = this.parallelism) > 0 ? par : 1;
   }

   public static int getCommonPoolParallelism() {
      return commonParallelism;
   }

   public int getPoolSize() {
      return this.parallelism + (short)((int)(this.ctl >>> 32));
   }

   public boolean getAsyncMode() {
      return this.mode == 1;
   }

   public int getRunningThreadCount() {
      int rc = 0;
      WorkQueue[] ws;
      if ((ws = this.workQueues) != null) {
         for(int i = 1; i < ws.length; i += 2) {
            WorkQueue w;
            if ((w = ws[i]) != null && w.isApparentlyUnblocked()) {
               ++rc;
            }
         }
      }

      return rc;
   }

   public int getActiveThreadCount() {
      int r = this.parallelism + (int)(this.ctl >> 48);
      return r <= 0 ? 0 : r;
   }

   public boolean isQuiescent() {
      return this.parallelism + (int)(this.ctl >> 48) <= 0;
   }

   public long getStealCount() {
      long count = this.stealCount;
      WorkQueue[] ws;
      if ((ws = this.workQueues) != null) {
         for(int i = 1; i < ws.length; i += 2) {
            WorkQueue w;
            if ((w = ws[i]) != null) {
               count += (long)w.nsteals;
            }
         }
      }

      return count;
   }

   public long getQueuedTaskCount() {
      long count = 0L;
      WorkQueue[] ws;
      if ((ws = this.workQueues) != null) {
         for(int i = 1; i < ws.length; i += 2) {
            WorkQueue w;
            if ((w = ws[i]) != null) {
               count += (long)w.queueSize();
            }
         }
      }

      return count;
   }

   public int getQueuedSubmissionCount() {
      int count = 0;
      WorkQueue[] ws;
      if ((ws = this.workQueues) != null) {
         for(int i = 0; i < ws.length; i += 2) {
            WorkQueue w;
            if ((w = ws[i]) != null) {
               count += w.queueSize();
            }
         }
      }

      return count;
   }

   public boolean hasQueuedSubmissions() {
      WorkQueue[] ws;
      if ((ws = this.workQueues) != null) {
         for(int i = 0; i < ws.length; i += 2) {
            WorkQueue w;
            if ((w = ws[i]) != null && !w.isEmpty()) {
               return true;
            }
         }
      }

      return false;
   }

   protected ForkJoinTask pollSubmission() {
      WorkQueue[] ws;
      if ((ws = this.workQueues) != null) {
         for(int i = 0; i < ws.length; i += 2) {
            WorkQueue w;
            ForkJoinTask t;
            if ((w = ws[i]) != null && (t = w.poll()) != null) {
               return t;
            }
         }
      }

      return null;
   }

   protected int drainTasksTo(Collection c) {
      int count = 0;
      WorkQueue[] ws;
      if ((ws = this.workQueues) != null) {
         for(int i = 0; i < ws.length; ++i) {
            WorkQueue w;
            ForkJoinTask t;
            if ((w = ws[i]) != null) {
               while((t = w.poll()) != null) {
                  c.add(t);
                  ++count;
               }
            }
         }
      }

      return count;
   }

   public String toString() {
      long qt = 0L;
      long qs = 0L;
      int rc = 0;
      long st = this.stealCount;
      long c = this.ctl;
      WorkQueue[] ws;
      int size;
      if ((ws = this.workQueues) != null) {
         for(int i = 0; i < ws.length; ++i) {
            WorkQueue w;
            if ((w = ws[i]) != null) {
               size = w.queueSize();
               if ((i & 1) == 0) {
                  qs += (long)size;
               } else {
                  qt += (long)size;
                  st += (long)w.nsteals;
                  if (w.isApparentlyUnblocked()) {
                     ++rc;
                  }
               }
            }
         }
      }

      int pc = this.parallelism;
      size = pc + (short)((int)(c >>> 32));
      int ac = pc + (int)(c >> 48);
      if (ac < 0) {
         ac = 0;
      }

      String level;
      if ((c & 2147483648L) != 0L) {
         level = size == 0 ? "Terminated" : "Terminating";
      } else {
         level = this.plock < 0 ? "Shutting down" : "Running";
      }

      return super.toString() + "[" + level + ", parallelism = " + pc + ", size = " + size + ", active = " + ac + ", running = " + rc + ", steals = " + st + ", tasks = " + qt + ", submissions = " + qs + "]";
   }

   public void shutdown() {
      checkPermission();
      this.tryTerminate(false, true);
   }

   public List shutdownNow() {
      checkPermission();
      this.tryTerminate(true, true);
      return Collections.emptyList();
   }

   public boolean isTerminated() {
      long c = this.ctl;
      return (c & 2147483648L) != 0L && (short)((int)(c >>> 32)) + this.parallelism <= 0;
   }

   public boolean isTerminating() {
      long c = this.ctl;
      return (c & 2147483648L) != 0L && (short)((int)(c >>> 32)) + this.parallelism > 0;
   }

   public boolean isShutdown() {
      return this.plock < 0;
   }

   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
      if (Thread.interrupted()) {
         throw new InterruptedException();
      } else if (this == common) {
         this.awaitQuiescence(timeout, unit);
         return false;
      } else {
         long nanos = unit.toNanos(timeout);
         if (this.isTerminated()) {
            return true;
         } else if (nanos <= 0L) {
            return false;
         } else {
            long deadline = System.nanoTime() + nanos;
            synchronized(this) {
               while(!this.isTerminated()) {
                  if (nanos <= 0L) {
                     return false;
                  }

                  long millis = TimeUnit.NANOSECONDS.toMillis(nanos);
                  this.wait(millis > 0L ? millis : 1L);
                  nanos = deadline - System.nanoTime();
               }

               return true;
            }
         }
      }
   }

   public boolean awaitQuiescence(long timeout, TimeUnit unit) {
      long nanos = unit.toNanos(timeout);
      Thread thread = Thread.currentThread();
      ForkJoinWorkerThread wt;
      if (thread instanceof ForkJoinWorkerThread && (wt = (ForkJoinWorkerThread)thread).pool == this) {
         this.helpQuiescePool(wt.workQueue);
         return true;
      } else {
         long startTime = System.nanoTime();
         int r = 0;
         boolean found = true;

         WorkQueue[] ws;
         int m;
         while(!this.isQuiescent() && (ws = this.workQueues) != null && (m = ws.length - 1) >= 0) {
            if (!found) {
               if (System.nanoTime() - startTime > nanos) {
                  return false;
               }

               Thread.yield();
            }

            found = false;

            for(int j = m + 1 << 2; j >= 0; --j) {
               WorkQueue q;
               int b;
               if ((q = ws[r++ & m]) != null && (b = q.base) - q.top < 0) {
                  found = true;
                  ForkJoinTask t;
                  if ((t = q.pollAt(b)) != null) {
                     t.doExec();
                  }
                  break;
               }
            }
         }

         return true;
      }
   }

   static void quiesceCommonPool() {
      common.awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
   }

   public static void managedBlock(ManagedBlocker blocker) throws InterruptedException {
      Thread t = Thread.currentThread();
      if (t instanceof ForkJoinWorkerThread) {
         ForkJoinPool p = ((ForkJoinWorkerThread)t).pool;

         while(!blocker.isReleasable()) {
            if (p.tryCompensate(p.ctl)) {
               try {
                  while(!blocker.isReleasable() && !blocker.block()) {
                  }

                  return;
               } finally {
                  p.incrementActiveCount();
               }
            }
         }
      } else {
         while(!blocker.isReleasable() && !blocker.block()) {
         }
      }

   }

   protected RunnableFuture newTaskFor(Runnable runnable, Object value) {
      return new ForkJoinTask.AdaptedRunnable(runnable, value);
   }

   protected RunnableFuture newTaskFor(Callable callable) {
      return new ForkJoinTask.AdaptedCallable(callable);
   }

   private static ForkJoinPool makeCommonPool() {
      int parallelism = -1;
      ForkJoinWorkerThreadFactory factory = defaultForkJoinWorkerThreadFactory;
      Thread.UncaughtExceptionHandler handler = null;

      try {
         String pp = System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism");
         String fp = System.getProperty("java.util.concurrent.ForkJoinPool.common.threadFactory");
         String hp = System.getProperty("java.util.concurrent.ForkJoinPool.common.exceptionHandler");
         if (pp != null) {
            parallelism = Integer.parseInt(pp);
         }

         if (fp != null) {
            factory = (ForkJoinWorkerThreadFactory)ClassLoader.getSystemClassLoader().loadClass(fp).newInstance();
         }

         if (hp != null) {
            handler = (Thread.UncaughtExceptionHandler)ClassLoader.getSystemClassLoader().loadClass(hp).newInstance();
         }
      } catch (Exception var6) {
      }

      if (parallelism < 0 && (parallelism = Runtime.getRuntime().availableProcessors() - 1) < 0) {
         parallelism = 0;
      }

      if (parallelism > 32767) {
         parallelism = 32767;
      }

      return new ForkJoinPool(parallelism, factory, handler, 0, "ForkJoinPool.commonPool-worker-");
   }

   private static Unsafe getUnsafe() {
      try {
         return Unsafe.getUnsafe();
      } catch (SecurityException var2) {
         try {
            return (Unsafe)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Unsafe run() throws Exception {
                  Class k = Unsafe.class;
                  Field[] arr$ = k.getDeclaredFields();
                  int len$ = arr$.length;

                  for(int i$ = 0; i$ < len$; ++i$) {
                     Field f = arr$[i$];
                     f.setAccessible(true);
                     Object x = f.get((Object)null);
                     if (k.isInstance(x)) {
                        return (Unsafe)k.cast(x);
                     }
                  }

                  throw new NoSuchFieldError("the Unsafe");
               }
            });
         } catch (PrivilegedActionException var1) {
            throw new RuntimeException("Could not initialize intrinsics", var1.getCause());
         }
      }
   }

   static {
      try {
         U = getUnsafe();
         Class k = ForkJoinPool.class;
         CTL = U.objectFieldOffset(k.getDeclaredField("ctl"));
         STEALCOUNT = U.objectFieldOffset(k.getDeclaredField("stealCount"));
         PLOCK = U.objectFieldOffset(k.getDeclaredField("plock"));
         INDEXSEED = U.objectFieldOffset(k.getDeclaredField("indexSeed"));
         Class tk = Thread.class;
         PARKBLOCKER = U.objectFieldOffset(tk.getDeclaredField("parkBlocker"));
         Class wk = WorkQueue.class;
         QBASE = U.objectFieldOffset(wk.getDeclaredField("base"));
         QLOCK = U.objectFieldOffset(wk.getDeclaredField("qlock"));
         Class ak = ForkJoinTask[].class;
         ABASE = U.arrayBaseOffset(ak);
         int scale = U.arrayIndexScale(ak);
         if ((scale & scale - 1) != 0) {
            throw new Error("data type scale not a power of two");
         }

         ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
      } catch (Exception var5) {
         throw new Error(var5);
      }

      submitters = new ThreadLocal();
      defaultForkJoinWorkerThreadFactory = new DefaultForkJoinWorkerThreadFactory();
      modifyThreadPermission = new RuntimePermission("modifyThread");
      common = (ForkJoinPool)AccessController.doPrivileged(new PrivilegedAction() {
         public ForkJoinPool run() {
            return ForkJoinPool.makeCommonPool();
         }
      });
      int par = common.parallelism;
      commonParallelism = par > 0 ? par : 1;
   }

   public interface ManagedBlocker {
      boolean block() throws InterruptedException;

      boolean isReleasable();
   }

   static final class Submitter {
      int seed;

      Submitter(int s) {
         this.seed = s;
      }
   }

   static final class WorkQueue {
      static final int INITIAL_QUEUE_CAPACITY = 8192;
      static final int MAXIMUM_QUEUE_CAPACITY = 67108864;
      volatile long pad00;
      volatile long pad01;
      volatile long pad02;
      volatile long pad03;
      volatile long pad04;
      volatile long pad05;
      volatile long pad06;
      volatile int eventCount;
      int nextWait;
      int nsteals;
      int hint;
      short poolIndex;
      final short mode;
      volatile int qlock;
      volatile int base;
      int top;
      ForkJoinTask[] array;
      final ForkJoinPool pool;
      final ForkJoinWorkerThread owner;
      volatile Thread parker;
      volatile ForkJoinTask currentJoin;
      ForkJoinTask currentSteal;
      volatile Object pad10;
      volatile Object pad11;
      volatile Object pad12;
      volatile Object pad13;
      volatile Object pad14;
      volatile Object pad15;
      volatile Object pad16;
      volatile Object pad17;
      volatile Object pad18;
      volatile Object pad19;
      volatile Object pad1a;
      volatile Object pad1b;
      volatile Object pad1c;
      volatile Object pad1d;
      private static final Unsafe U;
      private static final long QBASE;
      private static final long QLOCK;
      private static final int ABASE;
      private static final int ASHIFT;

      WorkQueue(ForkJoinPool pool, ForkJoinWorkerThread owner, int mode, int seed) {
         this.pool = pool;
         this.owner = owner;
         this.mode = (short)mode;
         this.hint = seed;
         this.base = this.top = 4096;
      }

      final int queueSize() {
         int n = this.base - this.top;
         return n >= 0 ? 0 : -n;
      }

      final boolean isEmpty() {
         int s;
         int n = this.base - (s = this.top);
         ForkJoinTask[] a;
         int m;
         return n >= 0 || n == -1 && ((a = this.array) == null || (m = a.length - 1) < 0 || U.getObject(a, (long)((m & s - 1) << ASHIFT) + (long)ABASE) == null);
      }

      final void push(ForkJoinTask task) {
         int s = this.top;
         ForkJoinTask[] a;
         if ((a = this.array) != null) {
            int m = a.length - 1;
            U.putOrderedObject(a, (long)(((m & s) << ASHIFT) + ABASE), task);
            int n;
            if ((n = (this.top = s + 1) - this.base) <= 2) {
               ForkJoinPool p;
               (p = this.pool).signalWork(p.workQueues, this);
            } else if (n >= m) {
               this.growArray();
            }
         }

      }

      final ForkJoinTask[] growArray() {
         ForkJoinTask[] oldA = this.array;
         int size = oldA != null ? oldA.length << 1 : 8192;
         if (size > 67108864) {
            throw new RejectedExecutionException("Queue capacity exceeded");
         } else {
            ForkJoinTask[] a = this.array = new ForkJoinTask[size];
            int oldMask;
            int t;
            int b;
            if (oldA != null && (oldMask = oldA.length - 1) >= 0 && (t = this.top) - (b = this.base) > 0) {
               int mask = size - 1;

               do {
                  int oldj = ((b & oldMask) << ASHIFT) + ABASE;
                  int j = ((b & mask) << ASHIFT) + ABASE;
                  ForkJoinTask x = (ForkJoinTask)U.getObjectVolatile(oldA, (long)oldj);
                  if (x != null && U.compareAndSwapObject(oldA, (long)oldj, x, (Object)null)) {
                     U.putObjectVolatile(a, (long)j, x);
                  }

                  ++b;
               } while(b != t);
            }

            return a;
         }
      }

      final ForkJoinTask pop() {
         ForkJoinTask[] a;
         int m;
         int s;
         if ((a = this.array) != null && (m = a.length - 1) >= 0) {
            while((s = this.top - 1) - this.base >= 0) {
               long j = (long)(((m & s) << ASHIFT) + ABASE);
               ForkJoinTask t;
               if ((t = (ForkJoinTask)U.getObject(a, j)) == null) {
                  break;
               }

               if (U.compareAndSwapObject(a, j, t, (Object)null)) {
                  this.top = s;
                  return t;
               }
            }
         }

         return null;
      }

      final ForkJoinTask pollAt(int b) {
         ForkJoinTask[] a;
         if ((a = this.array) != null) {
            int j = ((a.length - 1 & b) << ASHIFT) + ABASE;
            ForkJoinTask t;
            if ((t = (ForkJoinTask)U.getObjectVolatile(a, (long)j)) != null && this.base == b && U.compareAndSwapObject(a, (long)j, t, (Object)null)) {
               U.putOrderedInt(this, QBASE, b + 1);
               return t;
            }
         }

         return null;
      }

      final ForkJoinTask poll() {
         while(true) {
            ForkJoinTask[] a;
            int b;
            if ((b = this.base) - this.top < 0 && (a = this.array) != null) {
               int j = ((a.length - 1 & b) << ASHIFT) + ABASE;
               ForkJoinTask t = (ForkJoinTask)U.getObjectVolatile(a, (long)j);
               if (t != null) {
                  if (!U.compareAndSwapObject(a, (long)j, t, (Object)null)) {
                     continue;
                  }

                  U.putOrderedInt(this, QBASE, b + 1);
                  return t;
               }

               if (this.base != b) {
                  continue;
               }

               if (b + 1 != this.top) {
                  Thread.yield();
                  continue;
               }
            }

            return null;
         }
      }

      final ForkJoinTask nextLocalTask() {
         return this.mode == 0 ? this.pop() : this.poll();
      }

      final ForkJoinTask peek() {
         ForkJoinTask[] a = this.array;
         int m;
         if (a != null && (m = a.length - 1) >= 0) {
            int i = this.mode == 0 ? this.top - 1 : this.base;
            int j = ((i & m) << ASHIFT) + ABASE;
            return (ForkJoinTask)U.getObjectVolatile(a, (long)j);
         } else {
            return null;
         }
      }

      final boolean tryUnpush(ForkJoinTask t) {
         ForkJoinTask[] a;
         int s;
         if ((a = this.array) != null && (s = this.top) != this.base) {
            int var10002 = a.length - 1;
            --s;
            if (U.compareAndSwapObject(a, (long)(((var10002 & s) << ASHIFT) + ABASE), t, (Object)null)) {
               this.top = s;
               return true;
            }
         }

         return false;
      }

      final void cancelAll() {
         ForkJoinTask.cancelIgnoringExceptions(this.currentJoin);
         ForkJoinTask.cancelIgnoringExceptions(this.currentSteal);

         ForkJoinTask t;
         while((t = this.poll()) != null) {
            ForkJoinTask.cancelIgnoringExceptions(t);
         }

      }

      final void pollAndExecAll() {
         ForkJoinTask t;
         while((t = this.poll()) != null) {
            t.doExec();
         }

      }

      final void runTask(ForkJoinTask task) {
         if ((this.currentSteal = task) != null) {
            task.doExec();
            ForkJoinTask[] a = this.array;
            int md = this.mode;
            ++this.nsteals;
            this.currentSteal = null;
            if (md != 0) {
               this.pollAndExecAll();
            } else if (a != null) {
               int m = a.length - 1;

               int s;
               while((s = this.top - 1) - this.base >= 0) {
                  long i = (long)(((m & s) << ASHIFT) + ABASE);
                  ForkJoinTask t = (ForkJoinTask)U.getObject(a, i);
                  if (t == null) {
                     break;
                  }

                  if (U.compareAndSwapObject(a, i, t, (Object)null)) {
                     this.top = s;
                     t.doExec();
                  }
               }
            }
         }

      }

      final boolean tryRemoveAndExec(ForkJoinTask task) {
         boolean stat;
         ForkJoinTask[] a;
         int m;
         int s;
         int b;
         int n;
         if (task != null && (a = this.array) != null && (m = a.length - 1) >= 0 && (n = (s = this.top) - (b = this.base)) > 0) {
            boolean removed = false;
            boolean empty = true;
            stat = true;

            while(true) {
               --s;
               long j = (long)(((s & m) << ASHIFT) + ABASE);
               ForkJoinTask t = (ForkJoinTask)U.getObject(a, j);
               if (t == null) {
                  break;
               }

               if (t == task) {
                  if (s + 1 == this.top) {
                     if (U.compareAndSwapObject(a, j, task, (Object)null)) {
                        this.top = s;
                        removed = true;
                     }
                  } else if (this.base == b) {
                     removed = U.compareAndSwapObject(a, j, task, new EmptyTask());
                  }
                  break;
               }

               if (t.status >= 0) {
                  empty = false;
               } else if (s + 1 == this.top) {
                  if (U.compareAndSwapObject(a, j, t, (Object)null)) {
                     this.top = s;
                  }
                  break;
               }

               --n;
               if (n == 0) {
                  if (!empty && this.base == b) {
                     stat = false;
                  }
                  break;
               }
            }

            if (removed) {
               task.doExec();
            }
         } else {
            stat = false;
         }

         return stat;
      }

      final boolean pollAndExecCC(CountedCompleter root) {
         ForkJoinTask[] a;
         int b;
         if ((b = this.base) - this.top < 0 && (a = this.array) != null) {
            long j = (long)(((a.length - 1 & b) << ASHIFT) + ABASE);
            Object o;
            if ((o = U.getObjectVolatile(a, j)) == null) {
               return true;
            }

            if (o instanceof CountedCompleter) {
               CountedCompleter t = (CountedCompleter)o;
               CountedCompleter r = t;

               do {
                  if (r == root) {
                     if (this.base == b && U.compareAndSwapObject(a, j, t, (Object)null)) {
                        U.putOrderedInt(this, QBASE, b + 1);
                        t.doExec();
                     }

                     return true;
                  }
               } while((r = r.completer) != null);
            }
         }

         return false;
      }

      final boolean externalPopAndExecCC(CountedCompleter root) {
         ForkJoinTask[] a;
         int s;
         if (this.base - (s = this.top) < 0 && (a = this.array) != null) {
            long j = (long)(((a.length - 1 & s - 1) << ASHIFT) + ABASE);
            Object o;
            if ((o = U.getObject(a, j)) instanceof CountedCompleter) {
               CountedCompleter t = (CountedCompleter)o;
               CountedCompleter r = t;

               do {
                  if (r == root) {
                     if (U.compareAndSwapInt(this, QLOCK, 0, 1)) {
                        if (this.top == s && this.array == a && U.compareAndSwapObject(a, j, t, (Object)null)) {
                           this.top = s - 1;
                           this.qlock = 0;
                           t.doExec();
                        } else {
                           this.qlock = 0;
                        }
                     }

                     return true;
                  }
               } while((r = r.completer) != null);
            }
         }

         return false;
      }

      final boolean internalPopAndExecCC(CountedCompleter root) {
         ForkJoinTask[] a;
         int s;
         if (this.base - (s = this.top) < 0 && (a = this.array) != null) {
            long j = (long)(((a.length - 1 & s - 1) << ASHIFT) + ABASE);
            Object o;
            if ((o = U.getObject(a, j)) instanceof CountedCompleter) {
               CountedCompleter t = (CountedCompleter)o;
               CountedCompleter r = t;

               do {
                  if (r == root) {
                     if (U.compareAndSwapObject(a, j, t, (Object)null)) {
                        this.top = s - 1;
                        t.doExec();
                     }

                     return true;
                  }
               } while((r = r.completer) != null);
            }
         }

         return false;
      }

      final boolean isApparentlyUnblocked() {
         ForkJoinWorkerThread wt;
         Thread.State s;
         return this.eventCount >= 0 && (wt = this.owner) != null && (s = wt.getState()) != State.BLOCKED && s != State.WAITING && s != State.TIMED_WAITING;
      }

      static {
         try {
            U = ForkJoinPool.getUnsafe();
            Class k = WorkQueue.class;
            Class ak = ForkJoinTask[].class;
            QBASE = U.objectFieldOffset(k.getDeclaredField("base"));
            QLOCK = U.objectFieldOffset(k.getDeclaredField("qlock"));
            ABASE = U.arrayBaseOffset(ak);
            int scale = U.arrayIndexScale(ak);
            if ((scale & scale - 1) != 0) {
               throw new Error("data type scale not a power of two");
            } else {
               ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
            }
         } catch (Exception var3) {
            throw new Error(var3);
         }
      }
   }

   static final class EmptyTask extends ForkJoinTask {
      private static final long serialVersionUID = -7721805057305804111L;

      EmptyTask() {
         this.status = -268435456;
      }

      public final Void getRawResult() {
         return null;
      }

      public final void setRawResult(Void x) {
      }

      public final boolean exec() {
         return true;
      }
   }

   static final class DefaultForkJoinWorkerThreadFactory implements ForkJoinWorkerThreadFactory {
      public final ForkJoinWorkerThread newThread(ForkJoinPool pool) {
         return new ForkJoinWorkerThread(pool);
      }
   }

   public interface ForkJoinWorkerThreadFactory {
      ForkJoinWorkerThread newThread(ForkJoinPool var1);
   }
}
