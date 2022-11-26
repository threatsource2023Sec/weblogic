package weblogic.cluster.messaging.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public final class MessageUtils {
   public static Message getMessage(byte[] data) throws IOException {
      ByteArrayInputStream bais = new ByteArrayInputStream(data);
      ObjectInputStream ois = new ObjectInputStream(bais);

      Message var3;
      try {
         var3 = (Message)ois.readObject();
      } catch (ClassNotFoundException var7) {
         throw new AssertionError(var7);
      } finally {
         ois.close();
      }

      return var3;
   }

   public static Message createMessage(byte[] data, String serverName, long startTime) {
      return new MessageImpl(data, serverName, startTime, System.currentTimeMillis());
   }
}
