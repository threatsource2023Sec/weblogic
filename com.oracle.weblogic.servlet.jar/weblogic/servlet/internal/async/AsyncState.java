package weblogic.servlet.internal.async;

import javax.servlet.AsyncListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

interface AsyncState {
   void start(AsyncStateSupport var1);

   void complete(AsyncStateSupport var1);

   void dispatch(AsyncStateSupport var1, ServletContext var2, String var3);

   void returnToContainer(AsyncStateSupport var1);

   void addListener(AsyncStateSupport var1, AsyncListener var2, ServletRequest var3, ServletResponse var4);

   void setTimeout(AsyncStateSupport var1, long var2);

   void notifyError(AsyncStateSupport var1, Throwable var2);

   void notifyTimeout(AsyncStateSupport var1);

   void dispatchErrorPage(AsyncStateSupport var1, Throwable var2);
}
