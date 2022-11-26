package weblogic.iiop.ior;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressHelper {
   private static final Delegate delegate = new Delegate() {
      public InetAddress getByName(String host) throws UnknownHostException {
         return InetAddress.getByName(host);
      }
   };

   public static InetAddress getByName(String host) throws UnknownHostException {
      return delegate.getByName(host);
   }

   public interface Delegate {
      InetAddress getByName(String var1) throws UnknownHostException;
   }
}
