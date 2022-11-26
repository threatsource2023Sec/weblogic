package weblogic.iiop.protocol;

import org.omg.CORBA_2_3.portable.InputStream;

public class ProtocolUtils {
   public static int readUnsignedShort(InputStream in) {
      return in.read_ushort() & '\uffff';
   }
}
