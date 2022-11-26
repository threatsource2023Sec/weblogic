package weblogic.servlet.http;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public abstract class AbstractAsyncServlet extends HttpServlet implements FutureResponseModel {
   public static final int DEFAULT_TIMEOUT = 40000;
   public static final int DEFAULT_SCAVANGE_INTERVAL = 40000;
   private static final String AYNC_SERVLET_SUPPORT_CLASS_NAME = System.getProperty("weblogic.servlet.http.AsyncServletSupport.class", "weblogic.servlet.http.AsyncServletSupportImpl");
   private static final ConcurrentHashMap pendingResponses = new ConcurrentHashMap();
   private int timeout = 40000;
   private static int scavangeInterval = 40000;
   private static final AsyncServletSupport support = initSupport();

   protected final void service(HttpServletRequest req, HttpServletResponse rsp) throws IOException, ServletException {
      if (this.timeout != -1) {
         boolean var3 = AbstractAsyncServlet.Initializer.initialized;
      }

      RequestResponseKey rrk = new RequestResponseKey(req, rsp, this.timeout);
      if (pendingResponses.get(rrk) != null) {
         throw new ServletException("Duplicate request entry for: " + rrk);
      } else {
         if (!this.doRequest(rrk)) {
            rrk.notifyRequestComplete();
         } else {
            synchronized(rrk) {
               if (rrk.isValid()) {
                  pendingResponses.put(rrk, rrk);
               }
            }
         }

      }
   }

   public static final void notify(RequestResponseKey id, Object context) throws IOException {
      pendingResponses.remove(id);
      synchronized(id) {
         if (!id.isValid()) {
            throw new IOException("Response has already been committed for: " + id);
         }
      }

      if (support == null) {
         throw new IOException("No implemnetation class of AsyncServletSupport");
      } else {
         support.notify(id, context);
      }
   }

   protected abstract boolean doRequest(RequestResponseKey var1) throws IOException, ServletException;

   protected abstract void doResponse(RequestResponseKey var1, Object var2) throws IOException, ServletException;

   protected abstract void doTimeout(RequestResponseKey var1) throws IOException, ServletException;

   protected void setTimeout(int period) {
      this.timeout = period;
   }

   public static final void setScavangeInterval(int period) {
      if (period <= 0) {
         throw new IllegalArgumentException("Interval must be greater than zero");
      } else {
         scavangeInterval = period;
      }
   }

   private static synchronized boolean startScavenger() {
      if (support == null) {
         return false;
      } else {
         TimerListener tunscav = new TimerListener() {
            public void timerExpired(Timer timer) {
               long currentTime = System.currentTimeMillis();
               Iterator iter = AbstractAsyncServlet.pendingResponses.values().iterator();

               while(iter.hasNext()) {
                  RequestResponseKey entry = (RequestResponseKey)iter.next();
                  if (entry.isTimeout(currentTime)) {
                     iter.remove();
                     AbstractAsyncServlet.support.timeout(entry);
                  }
               }

            }
         };
         TimerManager tm = TimerManagerFactory.getTimerManagerFactory().getTimerManager(AbstractAsyncServlet.class.getName(), WorkManagerFactory.getInstance().getSystem());
         tm.scheduleAtFixedRate(tunscav, 0L, (long)scavangeInterval);
         return true;
      }
   }

   private static final AsyncServletSupport initSupport() {
      try {
         Class clz = AbstractAsyncServlet.class.getClassLoader().loadClass(AYNC_SERVLET_SUPPORT_CLASS_NAME);
         return (AsyncServletSupport)clz.newInstance();
      } catch (Throwable var1) {
         return null;
      }
   }

   private static final class Initializer {
      private static final boolean initialized = AbstractAsyncServlet.startScavenger();
   }
}
