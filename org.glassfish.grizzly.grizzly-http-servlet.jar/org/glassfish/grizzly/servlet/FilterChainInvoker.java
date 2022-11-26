package org.glassfish.grizzly.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface FilterChainInvoker {
   void invokeFilterChain(ServletRequest var1, ServletResponse var2) throws IOException, ServletException;
}
