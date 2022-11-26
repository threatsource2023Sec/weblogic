package weblogic.servlet.internal;

import java.util.EventListener;
import javax.servlet.http.HttpSessionEvent;

public interface AuthenticationListener extends EventListener {
   void onLogout(HttpSessionEvent var1);
}
