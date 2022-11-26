package weblogic.socket;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import weblogic.kernel.Kernel;
import weblogic.utils.AssertionError;
import weblogic.utils.collections.Stack;
import weblogic.utils.io.Chunk;
import weblogic.utils.net.SocketResetException;

public final class NTSocketMuxer extends ServerSocketMuxer {
   private static final int ARRAY_CHUNK_SIZE = 1024;
   private static ArrayList socketInfos = new ArrayList(1024);
   private static Stack freeIndexes = new Stack(1024);
   private static int nativeArrayCapacity = 1024;

   public NTSocketMuxer() throws IOException {
      System.loadLibrary("wlntio");
      initNative(10000, 1024, Chunk.CHUNK_SIZE);
      String time_stamp = getBuildTime();
      SocketLogger.logTimeStamp(time_stamp);
      this.init();
   }

   private void init() {
      setDebug(Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail());
      this.initSocketReaderThreads(2, "weblogic.socket.Muxer", "weblogic.NTSocketReaders");

      for(int i = 0; i < 1024; ++i) {
         socketInfos.add((Object)null);
         freeIndexes.push(new Integer(i));
      }

   }

   public void register(MuxableSocket ms) throws IOException {
      NTSocketInfo info = null;

      try {
         info = new NTSocketInfo(ms);
      } catch (IOException var4) {
         SocketLogger.logRegisterSocketProblem(ms.toString(), var4);
         throw var4;
      }

      ms.setSocketInfo(info);
      super.register(ms);
   }

   public void register(Collection muxableSockets) {
      Iterator mSockets = muxableSockets.iterator();

      while(mSockets.hasNext()) {
         MuxableSocket ms = (MuxableSocket)mSockets.next();
         NTSocketInfo info = null;

         try {
            info = new NTSocketInfo(ms);
         } catch (IOException var6) {
            SocketLogger.logRegisterSocketProblem(ms.toString(), var6);
            mSockets.remove();
            this.cancelIo(ms);
            continue;
         }

         ms.setSocketInfo(info);
      }

      super.register(muxableSockets);
   }

   public void read(Collection muxableSockets) {
      Iterator var2 = muxableSockets.iterator();

      while(var2.hasNext()) {
         MuxableSocket ms = (MuxableSocket)var2.next();
         this.read(ms);
      }

   }

   public void read(MuxableSocket ms) {
      this.internalRead(ms, ms.getSocketInfo());
   }

   private void internalRead(MuxableSocket ms, SocketInfo info) {
      if (this.initiateIO(info)) {
         try {
            if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
               SocketLogger.logDebug("initiateNativeIo for: " + info);
            }

            initiateNativeIo(((NTSocketInfo)info).nativeIndex);
         } catch (SocketException var4) {
            SocketException ex = var4;
            if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerException()) {
               SocketLogger.logNTMuxerInitiateIOError(info.toString(), var4);
            }

            this.completeIO(ms, info);
            if (SocketResetException.isResetException(var4)) {
               ex = new SocketResetException(var4);
            }

            this.deliverHasException(ms, (Throwable)ex);
         }

      }
   }

   public void processSockets() {
      IoCompletionData ioCompletionData = new IoCompletionData();

      while(true) {
         ioCompletionData.clear();
         MuxableSocket ms = null;

         try {
            boolean success = getIoCompletionResult(ioCompletionData);
            int index = ioCompletionData.index;
            NTSocketInfo info = (NTSocketInfo)socketInfos.get(index);
            if (info == null) {
               SocketLogger.logNTMuxerSocketInfoNotFound("" + index, success);
            } else {
               ms = info.getMuxableSocket();
               if (this.completeIO(ms, info)) {
                  if (success) {
                     if (ioCompletionData.numAvailableBytes <= 0) {
                        this.deliverEndOfStream(ms);
                     } else {
                        if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
                           SocketLogger.logDebug("data ready for " + info + ": " + ioCompletionData);
                        }

                        if (this.copyDataFromNativeBuffer(ms, ioCompletionData)) {
                           if (ms.isMessageComplete()) {
                              info.messageCompleted();
                              if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxerDetail()) {
                                 SocketLogger.logDebug("dispatch: " + info);
                              }

                              ms.dispatch();
                           } else {
                              info.messageInitiated();
                              this.internalRead(ms, info);
                           }
                        }
                     }
                  } else {
                     this.deliverHasException(ms, new SocketResetException());
                  }
               }
            }
         } catch (ThreadDeath var6) {
            if (Kernel.isServer()) {
               if (!Kernel.isIntentionalShutdown()) {
                  SocketLogger.logThreadDeath(var6);
               }

               throw var6;
            }
         } catch (Throwable var7) {
            SocketLogger.logUncaughtThrowable(var7);
            if (ms != null) {
               this.deliverHasException(ms, var7);
            }
         }
      }
   }

   private boolean copyDataFromNativeBuffer(MuxableSocket ms, IoCompletionData data) {
      NTSocketInfo info = (NTSocketInfo)ms.getSocketInfo();
      int numAvailableBytes = data.numAvailableBytes;
      byte[] msBuffer = null;
      int msBufferOffset = false;
      int nativeBufferOffset = 0;
      int ioRecordIndex = info.nativeIndex;

      while(numAvailableBytes > 0) {
         byte[] msBuffer = ms.getBuffer();
         int msBufferOffset = ms.getBufferOffset();
         int msNumBytesToCopy = msBuffer.length - msBufferOffset;
         if (msNumBytesToCopy <= 0) {
            throw new AssertionError("Buffer offset >= buffer length for socket ms=" + info);
         }

         int numBytesToCopy = Math.min(msNumBytesToCopy, numAvailableBytes);
         copyData(msBuffer, msBufferOffset, numBytesToCopy, ioRecordIndex, nativeBufferOffset);
         numAvailableBytes -= numBytesToCopy;

         try {
            ms.incrementBufferOffset(numBytesToCopy);
            nativeBufferOffset += numBytesToCopy;
         } catch (MaxMessageSizeExceededException var12) {
            if (Kernel.DEBUG && Kernel.getDebug().getDebugMuxer()) {
               SocketLogger.logDebugThrowable("Exception while copying data for " + info, var12);
            }

            this.deliverHasException(ms, var12);
            return false;
         }
      }

      return true;
   }

   static int add(NTSocketInfo info) throws IOException {
      int ioRecordIndex = getNewIoRecord(info);

      try {
         initIoRecord(ioRecordIndex, info.fd);
         return ioRecordIndex;
      } catch (IOException var3) {
         freeIoRecord(ioRecordIndex);
         throw var3;
      }
   }

   static void remove(int ioRecordIndex) {
      freeIoRecord(ioRecordIndex);
   }

   private static int getNewIoRecord(NTSocketInfo info) {
      synchronized(socketInfos) {
         int len;
         if (!freeIndexes.isEmpty()) {
            len = (Integer)freeIndexes.pop();
            socketInfos.set(len, info);
            return len;
         } else {
            len = socketInfos.size();
            socketInfos.add(len, info);
            if (len == nativeArrayCapacity) {
               addIoRecordArrayChunk();
               nativeArrayCapacity += 1024;
            }

            return len;
         }
      }
   }

   private static void freeIoRecord(int index) {
      synchronized(socketInfos) {
         socketInfos.set(index, (Object)null);
         freeIndexes.push(new Integer(index));
      }
   }

   private static native void initNative(int var0, int var1, int var2) throws IOException;

   private static native void addIoRecordArrayChunk();

   private static native void initIoRecord(int var0, int var1) throws IOException;

   private static native void initiateNativeIo(int var0) throws SocketException;

   private static native boolean getIoCompletionResult(IoCompletionData var0);

   private static native void copyData(byte[] var0, int var1, int var2, int var3, int var4);

   private static native String getBuildTime();

   private static native void setDebug(boolean var0);

   private static final class IoCompletionData {
      private int fd;
      private int index;
      private int numAvailableBytes;

      private IoCompletionData() {
      }

      private void clear() {
         this.fd = -1;
         this.index = -1;
         this.numAvailableBytes = 0;
      }

      public String toString() {
         return this.getClass().getName() + "[fd=" + this.fd + ", index=" + this.index + ", numAvailableBytes=" + this.numAvailableBytes + "]";
      }

      // $FF: synthetic method
      IoCompletionData(Object x0) {
         this();
      }
   }
}
