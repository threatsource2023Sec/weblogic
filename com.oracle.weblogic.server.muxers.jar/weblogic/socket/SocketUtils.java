package weblogic.socket;

import java.io.IOException;
import java.net.Socket;
import weblogic.platform.VM;

public class SocketUtils {
   public static int getFileDescriptor(Socket socket) throws IOException {
      if (socket instanceof WeblogicSocket) {
         socket = ((WeblogicSocket)socket).getSocket();
      }

      return VM.getVM().getFD(socket);
   }
}
