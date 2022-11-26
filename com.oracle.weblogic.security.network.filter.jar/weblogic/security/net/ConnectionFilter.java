package weblogic.security.net;

import java.util.EventListener;

public interface ConnectionFilter extends EventListener {
   void accept(ConnectionEvent var1) throws FilterException;
}
