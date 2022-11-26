package weblogic.apache.org.apache.log.output;

import java.io.IOException;
import java.net.InetAddress;
import weblogic.apache.org.apache.log.format.Formatter;

/** @deprecated */
public class DatagramOutputTarget extends weblogic.apache.org.apache.log.output.net.DatagramOutputTarget {
   public DatagramOutputTarget(InetAddress address, int port, Formatter formatter) throws IOException {
      super(address, port, formatter);
   }

   public DatagramOutputTarget(InetAddress address, int port) throws IOException {
      super(address, port);
   }
}
