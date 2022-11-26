package weblogic.socket;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import weblogic.kernel.Kernel;
import weblogic.utils.Debug;
import weblogic.utils.NestedError;
import weblogic.utils.net.SocketResetException;

final class DevPollSocketMuxer extends ServerSocketMuxer {
   private final DevPollSocketInfo[] sockRecords;
   private final int maxNumberOfFds;
   private final Object pollLock = new String("pollLock");
   private final Object pollSetLock = new String("pollSetLock");
   private static final boolean debug = Debug.getCategory("weblogic.socket.DevPollSocketMuxer").isEnabled();
   static final byte FD_ADD = 2;
   static final byte FD_REM = 4;

   public DevPollSocketMuxer() throws IOException {
      Debug.assertion(Kernel.isServer());
      System.loadLibrary("muxer");

      try {
         this.maxNumberOfFds = initDevPoll() + 1;
      } catch (IOException var2) {
         SocketLogger.logInitPerf();
         throw new NestedError("Could not initialize /dev/poll Performance Pack. Ensure that /dev/poll device exists and is initialized.", var2);
      }

      SocketLogger.logFdCurrent(this.maxNumberOfFds);
      SocketLogger.logTimeStamp(getBuildTime());
      this.initSocketReaderThreads(3, "weblogic.socket.Muxer", "weblogic.DevPollSocketReaders");
      this.sockRecords = new DevPollSocketInfo[this.maxNumberOfFds];
   }

   public void register(MuxableSocket ms) throws IOException {
      DevPollSocketInfo sockInfo = new DevPollSocketInfo(ms);
      ms.setSocketInfo(sockInfo);
      super.register(ms);
      if (debug) {
         p("Registered ms=" + ms + " info=" + sockInfo);
      }

   }

   public void register(Collection muxableSockets) {
      Iterator mSockets = muxableSockets.iterator();

      while(mSockets.hasNext()) {
         MuxableSocket ms = (MuxableSocket)mSockets.next();

         try {
            DevPollSocketInfo sockInfo = new DevPollSocketInfo(ms);
            ms.setSocketInfo(sockInfo);
            if (debug) {
               p("Registered ms=" + ms + " info=" + sockInfo);
            }
         } catch (IOException var5) {
            SocketLogger.logRegisterSocketProblem(ms.toString(), var5);
            mSockets.remove();
            this.cancelIo(ms);
         }
      }

      if (!muxableSockets.isEmpty()) {
         super.register(muxableSockets);
      }

   }

   public void read(Collection muxableSockets) {
      Iterator var2 = muxableSockets.iterator();

      while(var2.hasNext()) {
         MuxableSocket ms = (MuxableSocket)var2.next();
         this.read(ms);
      }

   }

   public void read(MuxableSocket ms) {
      SocketInfo info = ms.getSocketInfo();
      if (this.initiateIO(info)) {
         DevPollSocketInfo sockInfo = (DevPollSocketInfo)info;
         int fd = sockInfo.fd;
         synchronized(sockInfo) {
            this.sockRecords[fd] = sockInfo;
            synchronized(this.pollSetLock) {
               try {
                  editPollSet(fd, (byte)2);
               } catch (IOException var10) {
                  throw new NestedError("Error adding FD to pollset " + sockInfo, var10);
               }
            }

            if (debug) {
               p("Read ms=" + ms + " info=" + ms.getSocketInfo());
            }

         }
      }
   }

   protected void processSockets() {
      while(true) {
         int[][] polledFds = (int[][])null;
         synchronized(this.pollLock) {
            try {
               if (debug) {
                  p("processSockets about to poll ........... ");
               }

               polledFds = doPoll();
            } catch (ThreadDeath var10) {
               throw var10;
            } catch (Throwable var11) {
               SocketLogger.logUncaughtThrowable(var11);
               continue;
            }

            if (debug) {
               p("processSockets poll returned " + polledFds);
            }

            if (polledFds == null) {
               continue;
            }
         }

         for(int i = 0; i < polledFds[0].length; ++i) {
            int fd = polledFds[0][i];
            if (fd != 0) {
               int revent = polledFds[1][i];
               DevPollSocketInfo sockInfo = this.sockRecords[fd];
               if (sockInfo == null) {
                  SocketLogger.logSocketInfoNotFound(fd, revent);
               } else {
                  MuxableSocket ms = sockInfo.getMuxableSocket();
                  if (this.completeIO(ms, sockInfo)) {
                     if (revent == 0) {
                        try {
                           if (debug) {
                              p("processSockets dispatching " + sockInfo);
                           }

                           this.readReadySocket(ms, sockInfo, 0L);
                        } catch (Throwable var9) {
                           if (debug) {
                              p("processSockets dispatching exception " + sockInfo);
                           }

                           this.deliverHasException(ms, var9);
                        }
                     } else {
                        if (debug) {
                           p("processSockets exception " + sockInfo);
                        }

                        this.deliverHasException(ms, new SocketResetException("Error detected on fd " + sockInfo.fd + " revents=" + revent));
                     }
                  }
               }
            }
         }

         try {
            if (DELAYPOLLWAKEUP > 0L) {
               Thread.sleep(DELAYPOLLWAKEUP);
            }
         } catch (InterruptedException var8) {
            Thread.interrupted();
         }
      }
   }

   void cleanupSocket(MuxableSocket ms, SocketInfo info) {
      if (info == null) {
         if (debug) {
            p("cancelIo() could not find internal data record for socket : " + ms);
         }

         MuxableSocket socketFilter = ms.getSocketFilter();
         if (socketFilter != null) {
            info = socketFilter.getSocketInfo();
         }
      }

      if (info != null) {
         int fd = info.getFD();
         synchronized(this.pollSetLock) {
            try {
               editPollSet(fd, (byte)4);
            } catch (IOException var7) {
               throw new NestedError("Error removing FD from pollset " + info, var7);
            }
         }

         this.sockRecords[fd] = null;
      }

      super.cleanupSocket(ms, info);
   }

   private static void p(String s) {
      SocketLogger.logDebug(Thread.currentThread().getName() + " + + + + + " + s);
   }

   protected void cancelIo(MuxableSocket ms) {
      this.cleanupSocket(ms, ms.getSocketInfo());
      super.cancelIo(ms);
   }

   private static native int initDevPoll() throws IOException;

   private static native int[][] doPoll() throws IOException;

   static native void editPollSet(int var0, byte var1) throws IOException;

   private static native String getBuildTime();
}
