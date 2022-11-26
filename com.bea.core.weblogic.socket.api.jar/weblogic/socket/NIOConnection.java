package weblogic.socket;

import java.net.InetAddress;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

public interface NIOConnection {
   InetAddress getLocalInetAddress();

   int getMTU();

   boolean supportsGatheredWrites();

   ScatteringByteChannel getScatteringByteChannel();

   boolean supportsScatteredReads();

   GatheringByteChannel getGatheringByteChannel();

   int getOptimalNumberOfBuffers();
}
