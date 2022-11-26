package jsr166e;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import sun.misc.Unsafe;

public class StampedLock implements Serializable {
   private static final long serialVersionUID = -6001602636862214147L;
   private static final int NCPU = Runtime.getRuntime().availableProcessors();
   private static final int SPINS;
   private static final int HEAD_SPINS;
   private static final int MAX_HEAD_SPINS;
   private static final int OVERFLOW_YIELD_RATE = 7;
   private static final int LG_READERS = 7;
   private static final long RUNIT = 1L;
   private static final long WBIT = 128L;
   private static final long RBITS = 127L;
   private static final long RFULL = 126L;
   private static final long ABITS = 255L;
   private static final long SBITS = -128L;
   private static final long ORIGIN = 256L;
   private static final long INTERRUPTED = 1L;
   private static final int WAITING = -1;
   private static final int CANCELLED = 1;
   private static final int RMODE = 0;
   private static final int WMODE = 1;
   private transient volatile WNode whead;
   private transient volatile WNode wtail;
   transient ReadLockView readLockView;
   transient WriteLockView writeLockView;
   transient ReadWriteLockView readWriteLockView;
   private transient volatile long state = 256L;
   private transient int readerOverflow;
   private static final Unsafe U;
   private static final long STATE;
   private static final long WHEAD;
   private static final long WTAIL;
   private static final long WNEXT;
   private static final long WSTATUS;
   private static final long WCOWAIT;
   private static final long PARKBLOCKER;

   public long writeLock() {
      long s;
      long next;
      return ((s = this.state) & 255L) == 0L && U.compareAndSwapLong(this, STATE, s, next = s + 128L) ? next : this.acquireWrite(false, 0L);
   }

   public long tryWriteLock() {
      long s;
      long next;
      return ((s = this.state) & 255L) == 0L && U.compareAndSwapLong(this, STATE, s, next = s + 128L) ? next : 0L;
   }

   public long tryWriteLock(long time, TimeUnit unit) throws InterruptedException {
      long nanos = unit.toNanos(time);
      if (!Thread.interrupted()) {
         long next;
         if ((next = this.tryWriteLock()) != 0L) {
            return next;
         }

         if (nanos <= 0L) {
            return 0L;
         }

         long deadline;
         if ((deadline = System.nanoTime() + nanos) == 0L) {
            deadline = 1L;
         }

         if ((next = this.acquireWrite(true, deadline)) != 1L) {
            return next;
         }
      }

      throw new InterruptedException();
   }

   public long writeLockInterruptibly() throws InterruptedException {
      long next;
      if (!Thread.interrupted() && (next = this.acquireWrite(true, 0L)) != 1L) {
         return next;
      } else {
         throw new InterruptedException();
      }
   }

   public long readLock() {
      long s = this.state;
      long next;
      return this.whead == this.wtail && (s & 255L) < 126L && U.compareAndSwapLong(this, STATE, s, next = s + 1L) ? next : this.acquireRead(false, 0L);
   }

   public long tryReadLock() {
      long s;
      long m;
      while((m = (s = this.state) & 255L) != 128L) {
         long next;
         if (m < 126L) {
            if (U.compareAndSwapLong(this, STATE, s, next = s + 1L)) {
               return next;
            }
         } else if ((next = this.tryIncReaderOverflow(s)) != 0L) {
            return next;
         }
      }

      return 0L;
   }

   public long tryReadLock(long time, TimeUnit unit) throws InterruptedException {
      long nanos = unit.toNanos(time);
      if (!Thread.interrupted()) {
         long s;
         long m;
         long next;
         if ((m = (s = this.state) & 255L) != 128L) {
            if (m < 126L) {
               if (U.compareAndSwapLong(this, STATE, s, next = s + 1L)) {
                  return next;
               }
            } else if ((next = this.tryIncReaderOverflow(s)) != 0L) {
               return next;
            }
         }

         if (nanos <= 0L) {
            return 0L;
         }

         long deadline;
         if ((deadline = System.nanoTime() + nanos) == 0L) {
            deadline = 1L;
         }

         if ((next = this.acquireRead(true, deadline)) != 1L) {
            return next;
         }
      }

      throw new InterruptedException();
   }

   public long readLockInterruptibly() throws InterruptedException {
      long next;
      if (!Thread.interrupted() && (next = this.acquireRead(true, 0L)) != 1L) {
         return next;
      } else {
         throw new InterruptedException();
      }
   }

   public long tryOptimisticRead() {
      long s;
      return ((s = this.state) & 128L) == 0L ? s & -128L : 0L;
   }

   public boolean validate(long stamp) {
      return (stamp & -128L) == (U.getLongVolatile(this, STATE) & -128L);
   }

   public void unlockWrite(long stamp) {
      if (this.state == stamp && (stamp & 128L) != 0L) {
         this.state = (stamp += 128L) == 0L ? 256L : stamp;
         WNode h;
         if ((h = this.whead) != null && h.status != 0) {
            this.release(h);
         }

      } else {
         throw new IllegalMonitorStateException();
      }
   }

   public void unlockRead(long stamp) {
      while(true) {
         long s;
         long m;
         if (((s = this.state) & -128L) == (stamp & -128L) && (stamp & 255L) != 0L && (m = s & 255L) != 0L && m != 128L) {
            if (m < 126L) {
               if (!U.compareAndSwapLong(this, STATE, s, s - 1L)) {
                  continue;
               }

               WNode h;
               if (m == 1L && (h = this.whead) != null && h.status != 0) {
                  this.release(h);
               }
            } else if (this.tryDecReaderOverflow(s) == 0L) {
               continue;
            }

            return;
         }

         throw new IllegalMonitorStateException();
      }
   }

   public void unlock(long stamp) {
      long a = stamp & 255L;

      long m;
      long s;
      while(((s = this.state) & -128L) == (stamp & -128L) && (m = s & 255L) != 0L) {
         WNode h;
         if (m == 128L) {
            if (a == m) {
               this.state = (s += 128L) == 0L ? 256L : s;
               if ((h = this.whead) != null && h.status != 0) {
                  this.release(h);
               }

               return;
            }
            break;
         }

         if (a == 0L || a >= 128L) {
            break;
         }

         if (m < 126L) {
            if (U.compareAndSwapLong(this, STATE, s, s - 1L)) {
               if (m == 1L && (h = this.whead) != null && h.status != 0) {
                  this.release(h);
               }

               return;
            }
         } else if (this.tryDecReaderOverflow(s) != 0L) {
            return;
         }
      }

      throw new IllegalMonitorStateException();
   }

   public long tryConvertToWriteLock(long stamp) {
      long a = stamp & 255L;

      long s;
      while(((s = this.state) & -128L) == (stamp & -128L)) {
         long m;
         long next;
         if ((m = s & 255L) == 0L) {
            if (a != 0L) {
               break;
            }

            if (U.compareAndSwapLong(this, STATE, s, next = s + 128L)) {
               return next;
            }
         } else {
            if (m == 128L) {
               if (a == m) {
                  return stamp;
               }
               break;
            }

            if (m != 1L || a == 0L) {
               break;
            }

            if (U.compareAndSwapLong(this, STATE, s, next = s - 1L + 128L)) {
               return next;
            }
         }
      }

      return 0L;
   }

   public long tryConvertToReadLock(long stamp) {
      long a = stamp & 255L;

      long s;
      while(((s = this.state) & -128L) == (stamp & -128L)) {
         long m;
         long next;
         if ((m = s & 255L) != 0L) {
            if (m == 128L) {
               if (a == m) {
                  this.state = next = s + 129L;
                  WNode h;
                  if ((h = this.whead) != null && h.status != 0) {
                     this.release(h);
                  }

                  return next;
               }
            } else if (a != 0L && a < 128L) {
               return stamp;
            }
            break;
         }

         if (a != 0L) {
            break;
         }

         if (m < 126L) {
            if (U.compareAndSwapLong(this, STATE, s, next = s + 1L)) {
               return next;
            }
         } else if ((next = this.tryIncReaderOverflow(s)) != 0L) {
            return next;
         }
      }

      return 0L;
   }

   public long tryConvertToOptimisticRead(long stamp) {
      long a = stamp & 255L;

      while(true) {
         long s = U.getLongVolatile(this, STATE);
         if (((s = this.state) & -128L) != (stamp & -128L)) {
            break;
         }

         long m;
         if ((m = s & 255L) == 0L) {
            if (a == 0L) {
               return s;
            }
            break;
         }

         long next;
         WNode h;
         if (m == 128L) {
            if (a == m) {
               this.state = next = (s += 128L) == 0L ? 256L : s;
               if ((h = this.whead) != null && h.status != 0) {
                  this.release(h);
               }

               return next;
            }
            break;
         }

         if (a == 0L || a >= 128L) {
            break;
         }

         if (m < 126L) {
            if (U.compareAndSwapLong(this, STATE, s, next = s - 1L)) {
               if (m == 1L && (h = this.whead) != null && h.status != 0) {
                  this.release(h);
               }

               return next & -128L;
            }
         } else if ((next = this.tryDecReaderOverflow(s)) != 0L) {
            return next & -128L;
         }
      }

      return 0L;
   }

   public boolean tryUnlockWrite() {
      long s;
      if (((s = this.state) & 128L) != 0L) {
         this.state = (s += 128L) == 0L ? 256L : s;
         WNode h;
         if ((h = this.whead) != null && h.status != 0) {
            this.release(h);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean tryUnlockRead() {
      while(true) {
         long s;
         long m;
         if ((m = (s = this.state) & 255L) != 0L && m < 128L) {
            if (m < 126L) {
               if (!U.compareAndSwapLong(this, STATE, s, s - 1L)) {
                  continue;
               }

               WNode h;
               if (m == 1L && (h = this.whead) != null && h.status != 0) {
                  this.release(h);
               }

               return true;
            }

            if (this.tryDecReaderOverflow(s) == 0L) {
               continue;
            }

            return true;
         }

         return false;
      }
   }

   private int getReadLockCount(long s) {
      long readers;
      if ((readers = s & 127L) >= 126L) {
         readers = 126L + (long)this.readerOverflow;
      }

      return (int)readers;
   }

   public boolean isWriteLocked() {
      return (this.state & 128L) != 0L;
   }

   public boolean isReadLocked() {
      return (this.state & 127L) != 0L;
   }

   public int getReadLockCount() {
      return this.getReadLockCount(this.state);
   }

   public String toString() {
      long s = this.state;
      return super.toString() + ((s & 255L) == 0L ? "[Unlocked]" : ((s & 128L) != 0L ? "[Write-locked]" : "[Read-locks:" + this.getReadLockCount(s) + "]"));
   }

   public Lock asReadLock() {
      ReadLockView v;
      return (v = this.readLockView) != null ? v : (this.readLockView = new ReadLockView());
   }

   public Lock asWriteLock() {
      WriteLockView v;
      return (v = this.writeLockView) != null ? v : (this.writeLockView = new WriteLockView());
   }

   public ReadWriteLock asReadWriteLock() {
      ReadWriteLockView v;
      return (v = this.readWriteLockView) != null ? v : (this.readWriteLockView = new ReadWriteLockView());
   }

   final void unstampedUnlockWrite() {
      long s;
      if (((s = this.state) & 128L) == 0L) {
         throw new IllegalMonitorStateException();
      } else {
         this.state = (s += 128L) == 0L ? 256L : s;
         WNode h;
         if ((h = this.whead) != null && h.status != 0) {
            this.release(h);
         }

      }
   }

   final void unstampedUnlockRead() {
      while(true) {
         long s;
         long m;
         if ((m = (s = this.state) & 255L) != 0L && m < 128L) {
            if (m < 126L) {
               if (!U.compareAndSwapLong(this, STATE, s, s - 1L)) {
                  continue;
               }

               WNode h;
               if (m == 1L && (h = this.whead) != null && h.status != 0) {
                  this.release(h);
               }
            } else if (this.tryDecReaderOverflow(s) == 0L) {
               continue;
            }

            return;
         }

         throw new IllegalMonitorStateException();
      }
   }

   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
      s.defaultReadObject();
      this.state = 256L;
   }

   private long tryIncReaderOverflow(long s) {
      if ((s & 255L) == 126L) {
         if (U.compareAndSwapLong(this, STATE, s, s | 127L)) {
            ++this.readerOverflow;
            this.state = s;
            return s;
         }
      } else if ((ThreadLocalRandom.current().nextInt() & 7) == 0) {
         Thread.yield();
      }

      return 0L;
   }

   private long tryDecReaderOverflow(long s) {
      if ((s & 255L) == 126L) {
         if (U.compareAndSwapLong(this, STATE, s, s | 127L)) {
            int r;
            long next;
            if ((r = this.readerOverflow) > 0) {
               this.readerOverflow = r - 1;
               next = s;
            } else {
               next = s - 1L;
            }

            this.state = next;
            return next;
         }
      } else if ((ThreadLocalRandom.current().nextInt() & 7) == 0) {
         Thread.yield();
      }

      return 0L;
   }

   private void release(WNode h) {
      if (h != null) {
         U.compareAndSwapInt(h, WSTATUS, -1, 0);
         WNode q;
         if ((q = h.next) == null || q.status == 1) {
            for(WNode t = this.wtail; t != null && t != h; t = t.prev) {
               if (t.status <= 0) {
                  q = t;
               }
            }
         }

         Thread w;
         if (q != null && (w = q.thread) != null) {
            U.unpark(w);
         }
      }

   }

   private long acquireWrite(boolean interruptible, long deadline) {
      WNode node = null;
      int spins = -1;

      long m;
      long s;
      long time;
      do {
         while((m = (s = this.state) & 255L) != 0L) {
            if (spins >= 0) {
               if (spins > 0) {
                  if (ThreadLocalRandom.current().nextInt() >= 0) {
                     --spins;
                  }
               } else {
                  WNode p;
                  if ((p = this.wtail) == null) {
                     WNode hd = new WNode(1, (WNode)null);
                     if (U.compareAndSwapObject(this, WHEAD, (Object)null, hd)) {
                        this.wtail = hd;
                     }
                  } else if (node == null) {
                     node = new WNode(1, p);
                  } else if (node.prev != p) {
                     node.prev = p;
                  } else if (U.compareAndSwapObject(this, WTAIL, p, node)) {
                     p.next = node;
                     spins = -1;

                     while(true) {
                        while(true) {
                           WNode h;
                           label107:
                           do {
                              if ((h = this.whead) != p) {
                                 WNode c;
                                 if (h != null) {
                                    while((c = h.cowait) != null) {
                                       Thread w;
                                       if (U.compareAndSwapObject(h, WCOWAIT, c, c.cowait) && (w = c.thread) != null) {
                                          U.unpark(w);
                                       }
                                    }
                                 }
                              } else {
                                 if (spins < 0) {
                                    spins = HEAD_SPINS;
                                 } else if (spins < MAX_HEAD_SPINS) {
                                    spins <<= 1;
                                 }

                                 int k = spins;

                                 long s;
                                 long ns;
                                 do {
                                    while(((s = this.state) & 255L) != 0L) {
                                       if (ThreadLocalRandom.current().nextInt() >= 0) {
                                          --k;
                                          if (k <= 0) {
                                             continue label107;
                                          }
                                       }
                                    }
                                 } while(!U.compareAndSwapLong(this, STATE, s, ns = s + 128L));

                                 this.whead = node;
                                 node.prev = null;
                                 return ns;
                              }
                           } while(this.whead != h);

                           WNode np;
                           if ((np = node.prev) != p) {
                              if (np != null) {
                                 p = np;
                                 np.next = node;
                              }
                           } else {
                              int ps;
                              if ((ps = p.status) == 0) {
                                 U.compareAndSwapInt(p, WSTATUS, 0, -1);
                              } else if (ps == 1) {
                                 WNode pp;
                                 if ((pp = p.prev) != null) {
                                    node.prev = pp;
                                    pp.next = node;
                                 }
                              } else {
                                 if (deadline == 0L) {
                                    time = 0L;
                                 } else if ((time = deadline - System.nanoTime()) <= 0L) {
                                    return this.cancelWaiter(node, node, false);
                                 }

                                 Thread wt = Thread.currentThread();
                                 U.putObject(wt, PARKBLOCKER, this);
                                 node.thread = wt;
                                 if (p.status < 0 && (p != h || (this.state & 255L) != 0L) && this.whead == h && node.prev == p) {
                                    U.park(false, time);
                                 }

                                 node.thread = null;
                                 U.putObject(wt, PARKBLOCKER, (Object)null);
                                 if (interruptible && Thread.interrupted()) {
                                    return this.cancelWaiter(node, node, true);
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            } else {
               spins = m == 128L && this.wtail == this.whead ? SPINS : 0;
            }
         }
      } while(!U.compareAndSwapLong(this, STATE, s, time = s + 128L));

      return time;
   }

   private long acquireRead(boolean interruptible, long deadline) {
      WNode node = null;
      int spins = -1;

      while(true) {
         WNode p;
         WNode h;
         long m;
         if ((h = this.whead) == (p = this.wtail)) {
            label272: {
               while(true) {
                  long m;
                  long s;
                  if ((m = (s = this.state) & 255L) < 126L) {
                     if (U.compareAndSwapLong(this, STATE, s, m = s + 1L)) {
                        break;
                     }
                  } else if (m < 128L && (m = this.tryIncReaderOverflow(s)) != 0L) {
                     break;
                  }

                  if (m >= 128L) {
                     if (spins > 0) {
                        if (ThreadLocalRandom.current().nextInt() >= 0) {
                           --spins;
                        }
                     } else {
                        if (spins == 0) {
                           WNode nh = this.whead;
                           WNode np = this.wtail;
                           if (nh == h && np == p) {
                              break label272;
                           }

                           h = nh;
                           p = np;
                           if (nh != np) {
                              break label272;
                           }
                        }

                        spins = SPINS;
                     }
                  }
               }

               return m;
            }
         }

         WNode np;
         if (p == null) {
            np = new WNode(1, (WNode)null);
            if (U.compareAndSwapObject(this, WHEAD, (Object)null, np)) {
               this.wtail = np;
            }
         } else if (node == null) {
            node = new WNode(0, p);
         } else {
            WNode pp;
            Thread wt;
            long time;
            if (h != p && p.mode == 0) {
               if (!U.compareAndSwapObject(p, WCOWAIT, node.cowait = p.cowait, node)) {
                  node.cowait = null;
               } else {
                  while(true) {
                     Thread w;
                     if ((h = this.whead) != null && (pp = h.cowait) != null && U.compareAndSwapObject(h, WCOWAIT, pp, pp.cowait) && (w = pp.thread) != null) {
                        U.unpark(w);
                     }

                     if (h == (np = p.prev) || h == p || np == null) {
                        label282: {
                           long ns;
                           while(true) {
                              long s;
                              if ((time = (s = this.state) & 255L) < 126L) {
                                 if (U.compareAndSwapLong(this, STATE, s, ns = s + 1L)) {
                                    break;
                                 }
                              } else if (time < 128L && (ns = this.tryIncReaderOverflow(s)) != 0L) {
                                 break;
                              }

                              if (time >= 128L) {
                                 break label282;
                              }
                           }

                           return ns;
                        }
                     }

                     if (this.whead == h && p.prev == np) {
                        if (np == null || h == p || p.status > 0) {
                           node = null;
                           break;
                        }

                        if (deadline == 0L) {
                           time = 0L;
                        } else if ((time = deadline - System.nanoTime()) <= 0L) {
                           return this.cancelWaiter(node, p, false);
                        }

                        wt = Thread.currentThread();
                        U.putObject(wt, PARKBLOCKER, this);
                        node.thread = wt;
                        if ((h != np || (this.state & 255L) == 128L) && this.whead == h && p.prev == np) {
                           U.park(false, time);
                        }

                        node.thread = null;
                        U.putObject(wt, PARKBLOCKER, (Object)null);
                        if (interruptible && Thread.interrupted()) {
                           return this.cancelWaiter(node, p, true);
                        }
                     }
                  }
               }
            } else if (node.prev != p) {
               node.prev = p;
            } else if (U.compareAndSwapObject(this, WTAIL, p, node)) {
               p.next = node;
               spins = -1;

               long ns;
               label194:
               while(true) {
                  if ((h = this.whead) == p) {
                     if (spins < 0) {
                        spins = HEAD_SPINS;
                     } else if (spins < MAX_HEAD_SPINS) {
                        spins <<= 1;
                     }

                     int k = spins;

                     do {
                        do {
                           do {
                              long s;
                              if ((m = (s = this.state) & 255L) < 126L) {
                                 if (U.compareAndSwapLong(this, STATE, s, ns = s + 1L)) {
                                    break label194;
                                 }
                              } else if (m < 128L && (ns = this.tryIncReaderOverflow(s)) != 0L) {
                                 break label194;
                              }
                           } while(m < 128L);
                        } while(ThreadLocalRandom.current().nextInt() < 0);

                        --k;
                     } while(k > 0);
                  } else {
                     WNode c;
                     if (h != null) {
                        while((c = h.cowait) != null) {
                           Thread w;
                           if (U.compareAndSwapObject(h, WCOWAIT, c, c.cowait) && (w = c.thread) != null) {
                              U.unpark(w);
                           }
                        }
                     }
                  }

                  if (this.whead == h) {
                     if ((np = node.prev) != p) {
                        if (np != null) {
                           p = np;
                           np.next = node;
                        }
                     } else {
                        int ps;
                        if ((ps = p.status) == 0) {
                           U.compareAndSwapInt(p, WSTATUS, 0, -1);
                        } else if (ps == 1) {
                           if ((pp = p.prev) != null) {
                              node.prev = pp;
                              pp.next = node;
                           }
                        } else {
                           if (deadline == 0L) {
                              time = 0L;
                           } else if ((time = deadline - System.nanoTime()) <= 0L) {
                              return this.cancelWaiter(node, node, false);
                           }

                           wt = Thread.currentThread();
                           U.putObject(wt, PARKBLOCKER, this);
                           node.thread = wt;
                           if (p.status < 0 && (p != h || (this.state & 255L) == 128L) && this.whead == h && node.prev == p) {
                              U.park(false, time);
                           }

                           node.thread = null;
                           U.putObject(wt, PARKBLOCKER, (Object)null);
                           if (interruptible && Thread.interrupted()) {
                              return this.cancelWaiter(node, node, true);
                           }
                        }
                     }
                  }
               }

               this.whead = node;
               node.prev = null;

               WNode c;
               while((c = node.cowait) != null) {
                  Thread w;
                  if (U.compareAndSwapObject(node, WCOWAIT, c, c.cowait) && (w = c.thread) != null) {
                     U.unpark(w);
                  }
               }

               return ns;
            }
         }
      }
   }

   private long cancelWaiter(WNode node, WNode group, boolean interrupted) {
      WNode pp;
      WNode q;
      if (node != null && group != null) {
         node.status = 1;
         WNode pred = group;

         WNode succ;
         while((succ = pred.cowait) != null) {
            if (succ.status == 1) {
               U.compareAndSwapObject(pred, WCOWAIT, succ, succ.cowait);
               pred = group;
            } else {
               pred = succ;
            }
         }

         if (group == node) {
            Thread w;
            for(pred = group.cowait; pred != null; pred = pred.cowait) {
               if ((w = pred.thread) != null) {
                  U.unpark(w);
               }
            }

            for(pred = node.prev; pred != null; pred = pp) {
               label132: {
                  WNode var10003;
                  do {
                     if ((succ = node.next) != null && succ.status != 1) {
                        break label132;
                     }

                     q = null;

                     for(WNode t = this.wtail; t != null && t != node; t = t.prev) {
                        if (t.status != 1) {
                           q = t;
                        }
                     }

                     if (succ == q) {
                        break;
                     }

                     var10003 = succ;
                     succ = q;
                  } while(!U.compareAndSwapObject(node, WNEXT, var10003, q));

                  if (succ == null && node == this.wtail) {
                     U.compareAndSwapObject(this, WTAIL, node, pred);
                  }
               }

               if (pred.next == node) {
                  U.compareAndSwapObject(pred, WNEXT, node, succ);
               }

               if (succ != null && (w = succ.thread) != null) {
                  succ.thread = null;
                  U.unpark(w);
               }

               if (pred.status != 1 || (pp = pred.prev) == null) {
                  break;
               }

               node.prev = pp;
               U.compareAndSwapObject(pp, WNEXT, pred, succ);
            }
         }
      }

      WNode h;
      while((h = this.whead) != null) {
         if ((pp = h.next) == null || pp.status == 1) {
            for(q = this.wtail; q != null && q != h; q = q.prev) {
               if (q.status <= 0) {
                  pp = q;
               }
            }
         }

         if (h == this.whead) {
            long s;
            if (pp != null && h.status == 0 && ((s = this.state) & 255L) != 128L && (s == 0L || pp.mode == 0)) {
               this.release(h);
            }
            break;
         }
      }

      return !interrupted && !Thread.interrupted() ? 0L : 1L;
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
      SPINS = NCPU > 1 ? 64 : 0;
      HEAD_SPINS = NCPU > 1 ? 1024 : 0;
      MAX_HEAD_SPINS = NCPU > 1 ? 65536 : 0;

      try {
         U = getUnsafe();
         Class k = StampedLock.class;
         Class wk = WNode.class;
         STATE = U.objectFieldOffset(k.getDeclaredField("state"));
         WHEAD = U.objectFieldOffset(k.getDeclaredField("whead"));
         WTAIL = U.objectFieldOffset(k.getDeclaredField("wtail"));
         WSTATUS = U.objectFieldOffset(wk.getDeclaredField("status"));
         WNEXT = U.objectFieldOffset(wk.getDeclaredField("next"));
         WCOWAIT = U.objectFieldOffset(wk.getDeclaredField("cowait"));
         Class tk = Thread.class;
         PARKBLOCKER = U.objectFieldOffset(tk.getDeclaredField("parkBlocker"));
      } catch (Exception var3) {
         throw new Error(var3);
      }
   }

   final class ReadWriteLockView implements ReadWriteLock {
      public Lock readLock() {
         return StampedLock.this.asReadLock();
      }

      public Lock writeLock() {
         return StampedLock.this.asWriteLock();
      }
   }

   final class WriteLockView implements Lock {
      public void lock() {
         StampedLock.this.writeLock();
      }

      public void lockInterruptibly() throws InterruptedException {
         StampedLock.this.writeLockInterruptibly();
      }

      public boolean tryLock() {
         return StampedLock.this.tryWriteLock() != 0L;
      }

      public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
         return StampedLock.this.tryWriteLock(time, unit) != 0L;
      }

      public void unlock() {
         StampedLock.this.unstampedUnlockWrite();
      }

      public Condition newCondition() {
         throw new UnsupportedOperationException();
      }
   }

   final class ReadLockView implements Lock {
      public void lock() {
         StampedLock.this.readLock();
      }

      public void lockInterruptibly() throws InterruptedException {
         StampedLock.this.readLockInterruptibly();
      }

      public boolean tryLock() {
         return StampedLock.this.tryReadLock() != 0L;
      }

      public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
         return StampedLock.this.tryReadLock(time, unit) != 0L;
      }

      public void unlock() {
         StampedLock.this.unstampedUnlockRead();
      }

      public Condition newCondition() {
         throw new UnsupportedOperationException();
      }
   }

   static final class WNode {
      volatile WNode prev;
      volatile WNode next;
      volatile WNode cowait;
      volatile Thread thread;
      volatile int status;
      final int mode;

      WNode(int m, WNode p) {
         this.mode = m;
         this.prev = p;
      }
   }
}
