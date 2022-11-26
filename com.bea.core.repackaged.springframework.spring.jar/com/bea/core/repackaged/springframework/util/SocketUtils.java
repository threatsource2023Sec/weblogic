package com.bea.core.repackaged.springframework.util;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.net.ServerSocketFactory;

public class SocketUtils {
   public static final int PORT_RANGE_MIN = 1024;
   public static final int PORT_RANGE_MAX = 65535;
   private static final Random random = new Random(System.currentTimeMillis());

   public static int findAvailableTcpPort() {
      return findAvailableTcpPort(1024);
   }

   public static int findAvailableTcpPort(int minPort) {
      return findAvailableTcpPort(minPort, 65535);
   }

   public static int findAvailableTcpPort(int minPort, int maxPort) {
      return SocketUtils.SocketType.TCP.findAvailablePort(minPort, maxPort);
   }

   public static SortedSet findAvailableTcpPorts(int numRequested) {
      return findAvailableTcpPorts(numRequested, 1024, 65535);
   }

   public static SortedSet findAvailableTcpPorts(int numRequested, int minPort, int maxPort) {
      return SocketUtils.SocketType.TCP.findAvailablePorts(numRequested, minPort, maxPort);
   }

   public static int findAvailableUdpPort() {
      return findAvailableUdpPort(1024);
   }

   public static int findAvailableUdpPort(int minPort) {
      return findAvailableUdpPort(minPort, 65535);
   }

   public static int findAvailableUdpPort(int minPort, int maxPort) {
      return SocketUtils.SocketType.UDP.findAvailablePort(minPort, maxPort);
   }

   public static SortedSet findAvailableUdpPorts(int numRequested) {
      return findAvailableUdpPorts(numRequested, 1024, 65535);
   }

   public static SortedSet findAvailableUdpPorts(int numRequested, int minPort, int maxPort) {
      return SocketUtils.SocketType.UDP.findAvailablePorts(numRequested, minPort, maxPort);
   }

   private static enum SocketType {
      TCP {
         protected boolean isPortAvailable(int port) {
            try {
               ServerSocket serverSocket = ServerSocketFactory.getDefault().createServerSocket(port, 1, InetAddress.getByName("localhost"));
               serverSocket.close();
               return true;
            } catch (Exception var3) {
               return false;
            }
         }
      },
      UDP {
         protected boolean isPortAvailable(int port) {
            try {
               DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName("localhost"));
               socket.close();
               return true;
            } catch (Exception var3) {
               return false;
            }
         }
      };

      private SocketType() {
      }

      protected abstract boolean isPortAvailable(int var1);

      private int findRandomPort(int minPort, int maxPort) {
         int portRange = maxPort - minPort;
         return minPort + SocketUtils.random.nextInt(portRange + 1);
      }

      int findAvailablePort(int minPort, int maxPort) {
         Assert.isTrue(minPort > 0, "'minPort' must be greater than 0");
         Assert.isTrue(maxPort >= minPort, "'maxPort' must be greater than or equal to 'minPort'");
         Assert.isTrue(maxPort <= 65535, "'maxPort' must be less than or equal to 65535");
         int portRange = maxPort - minPort;
         int searchCounter = 0;

         while(searchCounter <= portRange) {
            int candidatePort = this.findRandomPort(minPort, maxPort);
            ++searchCounter;
            if (this.isPortAvailable(candidatePort)) {
               return candidatePort;
            }
         }

         throw new IllegalStateException(String.format("Could not find an available %s port in the range [%d, %d] after %d attempts", this.name(), minPort, maxPort, searchCounter));
      }

      SortedSet findAvailablePorts(int numRequested, int minPort, int maxPort) {
         Assert.isTrue(minPort > 0, "'minPort' must be greater than 0");
         Assert.isTrue(maxPort > minPort, "'maxPort' must be greater than 'minPort'");
         Assert.isTrue(maxPort <= 65535, "'maxPort' must be less than or equal to 65535");
         Assert.isTrue(numRequested > 0, "'numRequested' must be greater than 0");
         Assert.isTrue(maxPort - minPort >= numRequested, "'numRequested' must not be greater than 'maxPort' - 'minPort'");
         SortedSet availablePorts = new TreeSet();
         int attemptCount = 0;

         while(true) {
            ++attemptCount;
            if (attemptCount > numRequested + 100 || availablePorts.size() >= numRequested) {
               if (availablePorts.size() != numRequested) {
                  throw new IllegalStateException(String.format("Could not find %d available %s ports in the range [%d, %d]", numRequested, this.name(), minPort, maxPort));
               } else {
                  return availablePorts;
               }
            }

            availablePorts.add(this.findAvailablePort(minPort, maxPort));
         }
      }

      // $FF: synthetic method
      SocketType(Object x2) {
         this();
      }
   }
}
