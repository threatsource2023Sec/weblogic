package weblogic.servlet.provider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import java.util.Map;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.management.configuration.WebAppContainerMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.HTTPDebugLogger;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletWorkContext;
import weblogic.servlet.internal.session.SessionInternal;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.servlet.spi.WorkContextProvider;
import weblogic.utils.io.FilteringObjectInputStream;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextMap;
import weblogic.workarea.spi.WorkContextMapInterceptor;
import weblogic.workarea.utils.WorkContextInputAdapter;
import weblogic.workarea.utils.WorkContextOutputAdapter;

public final class WlsWorkContextProvider implements WorkContextProvider {
   private static final WebAppContainerMBean webAppContainer = WebServerRegistry.getInstance().getWebAppContainerMBean();
   private final Map csidWorkContextMap = new Hashtable();

   public void initOrRestoreThreadContexts(ServletWorkContext ctx, ServletRequestImpl req) throws IOException {
      if (!this.isThreadContextIgnorable(req)) {
         SessionInternal session = (SessionInternal)req.getSession(false);
         String csid = req.getSessionHelper().getSessionID();

         try {
            if (csid != null) {
               boolean found = false;
               byte[] wcBytes;
               if (session != null) {
                  wcBytes = (byte[])((byte[])session.getInternalAttribute("weblogic.workContexts"));
                  if (wcBytes != null) {
                     found = true;
                     this.restoreWorkContexts(wcBytes);
                  }
               }

               wcBytes = (byte[])((byte[])this.csidWorkContextMap.get(csid));
               if (wcBytes != null) {
                  found = true;
                  this.restoreWorkContexts(wcBytes);
               }

               if (found) {
                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPDebugLogger.debug("*** HttpServer.WorkContextManager.restoreThreadContexts for CSID=" + csid + ", app=" + ctx.getAppDisplayName() + ", workCtxs=" + ApplicationVersionUtils.getDebugWorkContexts());
                  }

                  return;
               }
            }
         } finally {
            if (ctx.getVersionId() != null) {
               ApplicationVersionUtils.setCurrentVersionId(ctx.getApplicationName(), ctx.getVersionId());
            }

            if (ctx.isAdminMode()) {
               ApplicationVersionUtils.setCurrentAdminMode(true);
            }

         }

      }
   }

   public void copyThreadContexts(ServletWorkContext ctx, ServletRequestImpl request) {
      if (!this.isThreadContextIgnorable(request)) {
         try {
            WorkContextMapInterceptor interceptor = WorkContextHelper.getWorkContextHelper().getLocalInterceptor();
            if (!this.isWorkContextEmpty(interceptor) && webAppContainer.isWorkContextPropagationEnabled()) {
               SessionInternal session = (SessionInternal)request.getSession(false);
               if (session == null && request.getResponse().isCommitted()) {
                  return;
               }

               if (ctx != null && ctx.getVersionId() != null) {
                  if (session == null) {
                     ApplicationVersionUtils.removeAppWorkContextEntries();
                     if (this.isWorkContextEmpty(interceptor)) {
                        return;
                     }

                     session = (SessionInternal)request.getSession(true);
                  } else if (!session.hasStateAttributes()) {
                     ApplicationVersionUtils.removeAppWorkContextEntries();
                  }
               } else if (session == null) {
                  session = (SessionInternal)request.getSession(true);
               }

               byte[] wcBytes = this.getWorkContextsByteArray(interceptor);
               if (wcBytes == null) {
                  return;
               }

               String csid = request.getSessionHelper().getSessionID();
               this.csidWorkContextMap.put(csid, wcBytes);
               session.setInternalAttribute("weblogic.workContexts", wcBytes);
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("*** HttpServer.WorkContextManager.copyThreadContexts for CSID=" + csid + (ctx == null ? "" : ", app=" + ctx.getAppDisplayName()) + ", workCtxs=" + ApplicationVersionUtils.getDebugWorkContexts() + ", session=" + session);
               }

               return;
            }
         } catch (IOException var10) {
            HTTPLogger.logFailedToSaveWorkContexts(request.toString(), var10);
            return;
         } finally {
            if (ctx.isAdminMode()) {
               ApplicationVersionUtils.setCurrentAdminMode(false);
            }

         }

      }
   }

   private boolean isWorkContextEmpty(WorkContextMapInterceptor interceptor) {
      return interceptor == null || !((WorkContextMap)interceptor).isPropagationModePresent(128);
   }

   private byte[] getWorkContextsByteArray(WorkContextMapInterceptor interceptor) throws IOException {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      ObjectOutputStream out = new ObjectOutputStream(bout);

      try {
         WorkContextOutputAdapter wout = new WorkContextOutputAdapter(out);
         interceptor.sendRequest(wout, 128);
      } finally {
         out.flush();
         out.close();
      }

      return bout.toByteArray();
   }

   private void restoreWorkContexts(byte[] wcBytes) throws IOException {
      WorkContextMapInterceptor interceptor = WorkContextHelper.getWorkContextHelper().getInterceptor();
      if (interceptor != null) {
         ByteArrayInputStream bin = new ByteArrayInputStream(wcBytes);
         ObjectInputStream in = new FilteringObjectInputStream(bin);

         try {
            WorkContextInputAdapter win = new WorkContextInputAdapter(in);
            interceptor.receiveRequest(win);
         } finally {
            try {
               in.close();
            } catch (IOException var11) {
            }

         }

      }
   }

   public void updateWorkContexts(String csid, byte[] wcBytes) {
      this.csidWorkContextMap.put(csid, wcBytes);
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("*** HttpServer.WorkContextManager.updateThreadContexts for CSID=" + csid + ", workCtxs=" + new String(wcBytes));
      }

   }

   public void removeWorkContext(String csid) {
      this.csidWorkContextMap.remove(csid);
   }

   private boolean isThreadContextIgnorable(ServletRequestImpl req) {
      return req.getServletStub().isFileServlet() || req.getServletStub().isClasspathServlet() || req.getServletStub().isProxyServlet() || req.getServletStub().isPubSubControllerServlet();
   }
}
