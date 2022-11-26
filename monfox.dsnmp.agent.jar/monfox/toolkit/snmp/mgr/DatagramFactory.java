package monfox.toolkit.snmp.mgr;

import java.net.DatagramPacket;
import java.net.InetAddress;
import monfox.toolkit.snmp.engine.SnmpBuffer;

public final class DatagramFactory {
   private static DatagramFactory a = new DatagramFactory();
   private static final String b = "$Id: DatagramFactory.java,v 1.5 2001/06/21 22:08:19 sking Exp $";

   public static void setDatagramFactory(DatagramFactory var0) {
      a = var0;
   }

   public static DatagramPacket newInstance(SnmpBuffer var0, InetAddress var1, int var2) {
      return a.newPacket(var0, var1, var2);
   }

   public DatagramPacket newPacket(SnmpBuffer var1, InetAddress var2, int var3) {
      if (var1.offset == 0) {
         return new DatagramPacket(var1.data, var1.length, var2, var3);
      } else {
         byte[] var4 = new byte[var1.length];
         System.arraycopy(var1.data, var1.offset, var4, 0, var1.length);
         return new DatagramPacket(var4, var4.length, var2, var3);
      }
   }
}
