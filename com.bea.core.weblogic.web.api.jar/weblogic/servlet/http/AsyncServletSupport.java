package weblogic.servlet.http;

import java.io.IOException;

public interface AsyncServletSupport {
   void notify(RequestResponseKey var1, Object var2) throws IOException;

   void timeout(RequestResponseKey var1);
}
