package weblogic.timers.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLong;
import weblogic.kernel.KernelStatus;

class TimerThread {
   static final int STARTED = 0;
   static final int STOPPED = 1;
   static final int HALTED = 2;
   private static TimerThread singleton;
   private Thread thread;
   private volatile int state;
   private final ArrayList managerList;
   private final AtomicLong earliestWakeup;
   private Object waitLock = new Object();
   private long notifyCount;

   public TimerThread() {
      Class var1 = TimerThread.class;
      synchronized(TimerThread.class) {
         if (singleton != null) {
            throw new IllegalStateException();
         } else {
            if (!KernelStatus.isInitialized()) {
               String KERNEL_CLAZZ_NAME = "weblogic.kernel.Kernel";

               try {
                  Class.forName(KERNEL_CLAZZ_NAME, true, ClassLoader.getSystemClassLoader());
                  throw new AssertionError("Kernel needs to be initialized before starting TimerThread.");
               } catch (ClassNotFoundException var5) {
               } catch (NoClassDefFoundError var6) {
               }
            }

            this.managerList = new ArrayList();
            this.earliestWakeup = new AtomicLong(-1L);
            this.startThread();
            singleton = this;
         }
      }
   }

   void startThread() {
      this.thread = new Thread(this);
      this.thread.start();
   }

   private void doWait(long waitMillis, long lastNotifyCount) {
      synchronized(this.waitLock) {
         if (this.notifyCount == lastNotifyCount) {
            try {
               this.waitLock.wait(waitMillis);
            } catch (InterruptedException var8) {
            }

         }
      }
   }

   private void doNotify() {
      ++this.notifyCount;
      synchronized(this.waitLock) {
         this.waitLock.notify();
      }
   }

   private long getNotifyCount() {
      return this.notifyCount;
   }

   synchronized void stop() {
      this.state = 1;
   }

   synchronized void start() {
      this.state = 0;
      this.doNotify();
   }

   synchronized void halt() {
      this.state = 2;
      this.doNotify();
   }

   void register(TimerManagerImpl manager) {
      synchronized(this.managerList) {
         manager.idx = this.managerList.size();
         this.managerList.add(manager);
      }
   }

   void unregister(TimerManagerImpl manager) {
      synchronized(this.managerList) {
         if (manager.idx != -1) {
            TimerManagerImpl last = (TimerManagerImpl)this.managerList.remove(this.managerList.size() - 1);
            if (manager != last) {
               last.idx = manager.idx;
               this.managerList.set(last.idx, last);
            }

            manager.idx = -1;
         }
      }
   }

   void ping(long firstTimeout) {
      long wakeup;
      do {
         wakeup = this.earliestWakeup.get();
         if (wakeup > 0L && firstTimeout >= wakeup) {
            return;
         }
      } while(!this.earliestWakeup.compareAndSet(wakeup, firstTimeout));

      synchronized(this) {
         if (this.state == 0) {
            this.doNotify();
         }

      }
   }

   boolean isStarted() {
      return this.state == 0;
   }

   boolean isStopped() {
      return this.state == 1;
   }

   boolean isHalted() {
      return this.state == 2;
   }

   static TimerThread getTimerThread() {
      Class var0 = TimerThread.class;
      synchronized(TimerThread.class) {
         return singleton != null ? singleton : new TimerThread();
      }
   }

   class Thread extends java.lang.Thread {
      private TimerThread timerThread;

      public Thread(TimerThread timerThread) {
         this.timerThread = timerThread;
         this.setName("weblogic.timers.TimerThread");
         this.setPriority(9);
         this.setDaemon(true);
      }

      public void run() {
         long waitMillis = -1L;
         long lastNotifyCount = 0L;
         ArrayList execManagers = new ArrayList();

         while(true) {
            boolean isHalted;
            label121:
            while(true) {
               long currentTime;
               while(true) {
                  if (waitMillis >= 0L) {
                     this.timerThread.doWait(waitMillis, lastNotifyCount);
                     waitMillis = -1L;
                  }

                  currentTime = System.currentTimeMillis();
                  isHalted = false;
                  synchronized(this.timerThread) {
                     lastNotifyCount = this.timerThread.getNotifyCount();
                     if (!this.timerThread.isStopped()) {
                        isHalted = this.timerThread.isHalted();
                        break;
                     }

                     waitMillis = 0L;
                     TimerThread.this.earliestWakeup.set(-1L);
                  }
               }

               synchronized(TimerThread.this.managerList) {
                  if (isHalted) {
                     if (TimerThread.this.managerList.isEmpty()) {
                        return;
                     }

                     Iterator iterxx = TimerThread.this.managerList.iterator();

                     while(true) {
                        if (!iterxx.hasNext()) {
                           break label121;
                        }

                        execManagers.add(iterxx.next());
                     }
                  } else if (TimerThread.this.managerList.isEmpty()) {
                     waitMillis = 0L;
                     TimerThread.this.earliestWakeup.set(-1L);
                  } else {
                     long wakeup = TimerThread.this.earliestWakeup.get();
                     if (wakeup > currentTime) {
                        waitMillis = wakeup - currentTime;
                     } else {
                        long min = -1L;
                        Iterator iter = TimerThread.this.managerList.iterator();

                        while(true) {
                           while(true) {
                              TimerManagerImpl managerx;
                              long firstTimeout;
                              do {
                                 if (!iter.hasNext()) {
                                    if (!TimerThread.this.earliestWakeup.compareAndSet(wakeup, min)) {
                                    }
                                    break label121;
                                 }

                                 managerx = (TimerManagerImpl)iter.next();
                                 firstTimeout = managerx.earliestWakeup();
                              } while(firstTimeout < 0L);

                              if (firstTimeout <= currentTime) {
                                 execManagers.add(managerx);
                              } else if (min < 0L || min > firstTimeout) {
                                 min = firstTimeout;
                              }
                           }
                        }
                     }
                  }
               }
            }

            Iterator iterx = execManagers.iterator();

            while(iterx.hasNext()) {
               TimerManagerImpl manager = (TimerManagerImpl)iterx.next();
               if (isHalted) {
                  if (!manager.isStopped()) {
                     manager.stop();
                  }
               } else {
                  manager.execute();
               }
            }

            execManagers.clear();
            if (isHalted) {
               waitMillis = -1L;
               TimerThread.this.earliestWakeup.set(-1L);
            } else {
               long wakeupx = TimerThread.this.earliestWakeup.get();
               if (wakeupx < 0L) {
                  waitMillis = 0L;
               } else {
                  long now = System.currentTimeMillis();
                  waitMillis = wakeupx > now ? wakeupx - now : -1L;
               }
            }
         }
      }
   }
}
