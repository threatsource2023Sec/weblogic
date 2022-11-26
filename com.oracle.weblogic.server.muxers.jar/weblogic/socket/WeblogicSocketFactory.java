package weblogic.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.SocketFactory;

public abstract class WeblogicSocketFactory extends SocketFactory {
   public abstract Socket createSocket(InetAddress var1, int var2, int var3) throws IOException;
}
