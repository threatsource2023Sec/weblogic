package weblogic.socket;

import java.io.IOException;
import java.net.SocketException;
import java.util.Collection;
import java.util.Iterator;
import weblogic.kernel.Kernel;
import weblogic.utils.NestedError;
import weblogic.utils.net.SocketResetException;

final class PosixSocketMuxer extends ServerSocketMuxer {
   private final PosixSocketInfo.FdStruct[] fdStructs;
   private final int maxNumberOfFds;
   private int numberOfFds = 0;
   private final Object pollLock = new Object() {
   };

   public PosixSocketMuxer() throws IOException {
      try {
         System.loadLibrary("muxer");
         initNative();
         setDebug(Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail());
      } catch (IncompatibleVMException var2) {
         throw var2;
      } catch (UnsatisfiedLinkError var3) {
         throw var3;
      } catch (Throwable var4) {
         SocketLogger.logInitPerf();
         throw new NestedError("Could not initialize POSIX Performance Pack", var4);
      }

      SocketLogger.logFdLimit(getSoftFdLimit(), getHardFdLimit());
      this.maxNumberOfFds = getCurrentFdLimit();
      SocketLogger.logFdCurrent(this.maxNumberOfFds);
      SocketLogger.logTimeStamp(getBuildTime());
      this.initSocketReaderThreads(3, "weblogic.socket.Muxer", "weblogic.PosixSocketReaders");
      this.fdStructs = new PosixSocketInfo.FdStruct[this.maxNumberOfFds * 2];
   }

   public void register(MuxableSocket ms) throws IOException {
      ms.setSocketInfo(new PosixSocketInfo(ms));
      super.register(ms);
   }

   public void register(Collection muxableSockets) {
      Iterator var2 = muxableSockets.iterator();

      while(var2.hasNext()) {
         MuxableSocket ms = (MuxableSocket)var2.next();

         try {
            ms.setSocketInfo(new PosixSocketInfo(ms));
         } catch (IOException var5) {
            SocketLogger.logRegisterSocketProblem(ms.toString(), var5);
            muxableSockets.remove(ms);
            this.cancelIo(ms);
         }
      }

      super.register(muxableSockets);
   }

   public void read(Collection muxableSockets) {
      Iterator var2 = muxableSockets.iterator();

      while(var2.hasNext()) {
         MuxableSocket ms = (MuxableSocket)var2.next();
         SocketInfo info = ms.getSocketInfo();
         if (!this.initiateIO(info)) {
            muxableSockets.remove(ms);
         }

         PosixSocketInfo.FdStruct fdStruct = ((PosixSocketInfo)info).fdStruct;
         fdStruct.status = 0;
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
            SocketLogger.logDebug("initiate read for: " + fdStruct);
         }
      }

      synchronized(this.fdStructs) {
         if (this.numberOfFds == this.maxNumberOfFds * 2) {
            SocketLogger.logPosixMuxerMaxFdExceededError(this.maxNumberOfFds);
            return;
         }

         MuxableSocket ms;
         for(Iterator var8 = muxableSockets.iterator(); var8.hasNext(); this.fdStructs[this.numberOfFds++] = ((PosixSocketInfo)ms.getSocketInfo()).fdStruct) {
            ms = (MuxableSocket)var8.next();
         }

         this.fdStructs.notify();
      }

      wakeupPoll();
   }

   public void read(MuxableSocket ms) {
      SocketInfo info = ms.getSocketInfo();
      if (this.initiateIO(info)) {
         PosixSocketInfo.FdStruct fdStruct = ((PosixSocketInfo)info).fdStruct;
         fdStruct.status = 0;
         if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
            SocketLogger.logDebug("initiate read for: " + fdStruct);
         }

         synchronized(this.fdStructs) {
            if (this.numberOfFds == this.maxNumberOfFds * 2) {
               SocketLogger.logPosixMuxerMaxFdExceededError(this.maxNumberOfFds);
               return;
            }

            this.fdStructs[this.numberOfFds++] = fdStruct;
            this.fdStructs.notify();
         }

         wakeupPoll();
      }
   }

   protected void processSockets() {
      PosixSocketInfo.FdStruct[] readyFds = new PosixSocketInfo.FdStruct[this.maxNumberOfFds * 2];
      int numOfReadyFds = 0;

      while(true) {
         while(true) {
            try {
               synchronized(this.pollLock) {
                  int curNumberOfFds;
                  synchronized(this.fdStructs) {
                     while(true) {
                        if (this.numberOfFds != 0) {
                           curNumberOfFds = this.numberOfFds;
                           break;
                        }

                        this.fdStructs.wait();
                     }
                  }

                  if (!poll(this.fdStructs, curNumberOfFds)) {
                     continue;
                  }

                  synchronized(this.fdStructs) {
                     int i = 0;

                     while(i < curNumberOfFds) {
                        PosixSocketInfo.FdStruct curFdStruct = this.fdStructs[i];
                        if (curFdStruct.status == 0) {
                           ++i;
                           if (i != this.numberOfFds) {
                              continue;
                           }
                           break;
                        } else {
                           readyFds[numOfReadyFds++] = curFdStruct;
                           if (i != --this.numberOfFds) {
                              this.fdStructs[i] = this.fdStructs[this.numberOfFds];
                              this.fdStructs[this.numberOfFds] = null;
                           } else {
                              this.fdStructs[i] = null;
                              break;
                           }
                        }
                     }
                  }
               }

               for(int i = 0; i < numOfReadyFds; ++i) {
                  PosixSocketInfo.FdStruct curFdStruct = readyFds[i];
                  readyFds[i] = null;
                  PosixSocketInfo info = curFdStruct.info;
                  MuxableSocket ms = info.getMuxableSocket();
                  if (this.completeIO(ms, info)) {
                     if (curFdStruct.status == 1) {
                        try {
                           this.readReadySocket(ms, info, 0L);
                        } catch (Throwable var10) {
                           this.deliverHasException(info.getMuxableSocket(), var10);
                        }
                     } else if (curFdStruct.status == 2) {
                        this.deliverHasException(ms, new SocketException("Error in poll for fd=" + info.fd + ", revents=" + curFdStruct.revents));
                     } else if (curFdStruct.status == 3) {
                        this.deliverHasException(ms, new SocketResetException("Error in poll for fd=" + info.fd + ", revents=" + curFdStruct.revents));
                     }
                  }
               }

               numOfReadyFds = 0;
            } catch (ThreadDeath var14) {
               throw var14;
            } catch (Throwable var15) {
               SocketLogger.logUncaughtThrowable(var15);
            }
         }
      }
   }

   private static native void initNative() throws IncompatibleVMException;

   private static native boolean poll(PosixSocketInfo.FdStruct[] var0, int var1) throws IOException;

   private static native void wakeupPoll();

   private static native int getSoftFdLimit();

   private static native int getHardFdLimit();

   private static native int getCurrentFdLimit();

   private static native String getBuildTime();

   private static native void setDebug(boolean var0);
}
