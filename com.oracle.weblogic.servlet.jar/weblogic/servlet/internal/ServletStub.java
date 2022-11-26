package weblogic.servlet.internal;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface ServletStub {
   String getServletName();

   void execute(ServletRequest var1, ServletResponse var2) throws ServletException, IOException;

   void execute(ServletRequest var1, ServletResponse var2, FilterChainImpl var3) throws ServletException, IOException;
}
