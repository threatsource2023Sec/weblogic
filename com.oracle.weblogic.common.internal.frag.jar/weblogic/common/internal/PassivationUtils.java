package weblogic.common.internal;

import java.io.IOException;
import weblogic.protocol.ServerChannelManager;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.utils.io.Replacer;
import weblogic.utils.io.UnsyncByteArrayInputStream;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public final class PassivationUtils {
   private PassivationUtils() {
   }

   public static byte[] toByteArray(Object object) throws IOException {
      return toByteArray(object, RemoteObjectReplacer.getReplacer());
   }

   public static byte[] toByteArray(Object object, Replacer replacer) throws IOException {
      UnsyncByteArrayOutputStream baos = new UnsyncByteArrayOutputStream();
      WLObjectOutputStream out = new WLObjectOutputStream(baos);
      out.setReplacer(replacer);
      out.setServerChannel(ServerChannelManager.findDefaultLocalServerChannel());
      out.writeObject(object);
      out.flush();
      out.close();
      return baos.toByteArray();
   }

   public static Object toObject(byte[] b) throws ClassNotFoundException, IOException {
      return toObject(b, RemoteObjectReplacer.getReplacer());
   }

   public static Object toObject(byte[] b, Replacer replacer) throws ClassNotFoundException, IOException {
      UnsyncByteArrayInputStream bais = new UnsyncByteArrayInputStream(b);
      WLObjectInputStream in = new WLObjectInputStream(bais);
      in.setReplacer(replacer);
      return in.readObject();
   }

   public static int sizeOf(Object o) throws IOException {
      return toByteArray(o).length;
   }

   public static Object copy(Object o) throws ClassNotFoundException, IOException {
      return toObject(toByteArray(o));
   }

   public static boolean isSerializable(Object o) {
      try {
         toByteArray(o);
         return true;
      } catch (IOException var2) {
         return false;
      }
   }
}
