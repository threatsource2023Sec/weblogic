package weblogic.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.kernel.Kernel;
import weblogic.utils.io.Chunk;

public class NetworkInterfaceInfo {
   private static Map map = new ConcurrentHashMap(10);
   private boolean asyncMuxer;
   private InetAddress localAddress;
   private int mtuSize;
   private boolean supportsGatheredWrites;
   private boolean supportsScatteredReads;
   private int numBuffers;
   private final int directBufferSize;

   public static NetworkInterfaceInfo getNetworkInterfaceInfo(InetAddress address) {
      NetworkInterfaceInfo nInfo = (NetworkInterfaceInfo)map.get(address);
      if (nInfo == null) {
         synchronized(map) {
            nInfo = (NetworkInterfaceInfo)map.get(address);
            if (nInfo == null) {
               nInfo = create(address);
               map.put(address, nInfo);
            }
         }
      }

      return nInfo;
   }

   private static NetworkInterfaceInfo create(InetAddress address) {
      int mtu = 1500;

      try {
         NetworkInterface ni = NetworkInterface.getByInetAddress(address);
         if (ni != null) {
            mtu = ni.getMTU();
         }
      } catch (IOException var3) {
      }

      NetworkInterfaceInfo info = new NetworkInterfaceInfo(address, SocketMuxer.getMuxer().isAsyncMuxer(), mtu);
      return info;
   }

   private NetworkInterfaceInfo(InetAddress addr, boolean asyncMuxer, int mtu) {
      this.localAddress = addr;
      this.asyncMuxer = asyncMuxer;
      this.mtuSize = mtu;
      this.supportsGatheredWrites = this.isGatheredWritesEnabled() && asyncMuxer;
      this.supportsScatteredReads = this.isScatteredReadsEnabled() && asyncMuxer;
      this.numBuffers = this.mtuSize / Chunk.CHUNK_SIZE;
      if (this.numBuffers == 0) {
         this.numBuffers = 16;
      }

      this.directBufferSize = Math.max(this.mtuSize, Chunk.CHUNK_SIZE);
   }

   public InetAddress getLocalInetAddress() {
      return this.localAddress;
   }

   public int getMTU() {
      return this.mtuSize;
   }

   public int getDirectBufferSize() {
      return this.directBufferSize;
   }

   public boolean supportsGatheredWrites() {
      return this.supportsGatheredWrites;
   }

   public boolean supportsScatteredReads() {
      return this.supportsScatteredReads;
   }

   public int getOptimalNumberOfBuffers() {
      return this.numBuffers;
   }

   private boolean isGatheredWritesEnabled() {
      return Kernel.getConfig().isGatheredWritesEnabled();
   }

   private boolean isScatteredReadsEnabled() {
      return Kernel.getConfig().isScatteredReadsEnabled();
   }
}
