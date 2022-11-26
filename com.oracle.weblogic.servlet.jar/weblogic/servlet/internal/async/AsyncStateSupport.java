package weblogic.servlet.internal.async;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncListener;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface AsyncStateSupport extends AsyncContext {
   void setTimeoutInternal(long var1);

   void registerTimeout();

   void unregisterTimeout();

   void setAsyncState(AsyncState var1);

   AsyncState getAsyncState();

   AsyncEventsManager getAsyncEventsManager();

   void registerListener(AsyncListener var1, ServletRequest var2, ServletResponse var3);

   void notifyOnCompleteEvent();

   void initDispatcher(ServletContext var1, String var2);

   void scheduleDispatch();

   void commitResponse();

   void dispatchErrorPage(Throwable var1);
}
