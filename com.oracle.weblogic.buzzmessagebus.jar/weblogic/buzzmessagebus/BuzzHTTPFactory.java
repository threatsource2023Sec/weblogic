package weblogic.buzzmessagebus;

import java.io.IOException;
import java.net.Socket;
import org.jvnet.hk2.annotations.Contract;
import weblogic.utils.io.Chunk;

@Contract
public interface BuzzHTTPFactory {
   BuzzHTTP create(Chunk var1, Socket var2, BuzzHTTPSupport var3, boolean var4) throws IOException;
}
