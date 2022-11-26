package weblogic.servlet.http;

import java.io.IOException;
import java.util.Map;
import javax.servlet.Servlet;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.servlet.internal.ServletRequestImpl;
import weblogic.servlet.internal.ServletResponseImpl;
import weblogic.servlet.internal.ServletStubImpl;
import weblogic.servlet.internal.StubLifecycleHelper;
import weblogic.servlet.internal.WebAppServletContext;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public class AsyncServletSupportImpl implements AsyncServletSupport {
   static final long serialVersionUID = 9061902273868226534L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.http.AsyncServletSupportImpl");
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Async_Action_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public void notify(RequestResponseKey req, Object context) throws IOException {
      ServletRequestImpl request = ServletRequestImpl.getOriginalRequest(req.getRequest());
      ClassLoader ccl = null;

      try {
         ccl = request.getContext().pushEnvironment(Thread.currentThread());
         synchronized(req) {
            if (!req.isValid()) {
               return;
            }

            if (req.isCallDoResponse()) {
               ServletStubImpl stub = request.getServletStub();
               Servlet s = stub.getLifecycleHelper().getServlet();
               AbstractAsyncServlet var10000 = (AbstractAsyncServlet)s;
               RequestResponseKey var10001 = req;
               Object var10002 = context;
               LocalHolder var10;
               if ((var10 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
                  if (var10.argsCapture) {
                     var10.args = InstrumentationSupport.toSensitive(3);
                  }

                  InstrumentationSupport.createDynamicJoinPoint(var10);
                  InstrumentationSupport.preProcess(var10);
                  var10.resetPostBegin();
               }

               try {
                  var10000.doResponse(var10001, var10002);
                  if (var10 != null) {
                     InstrumentationSupport.postProcess(var10);
                  }
               } catch (Throwable var19) {
                  if (var10 != null) {
                     var10.th = var19;
                     InstrumentationSupport.postProcess(var10);
                  }

                  throw var19;
               }
            }

            req.closeResponse();
         }
      } catch (Throwable var21) {
         request.getContext().handleThrowableFromInvocation(var21, request, (ServletResponseImpl)req.getResponse());
      } finally {
         WebAppServletContext.popEnvironment(Thread.currentThread(), ccl);
      }

   }

   public void timeout(final RequestResponseKey entry) {
      final ServletStubImpl stub = ServletRequestImpl.getOriginalRequest(entry.getRequest()).getServletStub();
      if (entry.isValid()) {
         WorkManagerFactory.getInstance().getDefault().schedule(new WorkAdapter() {
            static final long serialVersionUID = -7864110671009275502L;
            static final String _WLDF$INST_VERSION = "9.0.0";
            // $FF: synthetic field
            static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.http.AsyncServletSupportImpl$1");
            static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Async_Action_Around_Medium;
            static final JoinPoint _WLDF$INST_JPFLD_0;
            static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

            public void run() {
               try {
                  synchronized(entry) {
                     if (entry.isValid()) {
                        StubLifecycleHelper helper = stub.getLifecycleHelper();
                        AbstractAsyncServlet servlet = null;
                        if (helper != null) {
                           servlet = (AbstractAsyncServlet)helper.getServlet();
                        }

                        if (servlet != null) {
                           AbstractAsyncServlet var10000 = servlet;
                           RequestResponseKey var10001 = entry;
                           LocalHolder var5;
                           if ((var5 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
                              if (var5.argsCapture) {
                                 var5.args = InstrumentationSupport.toSensitive(2);
                              }

                              InstrumentationSupport.createDynamicJoinPoint(var5);
                              InstrumentationSupport.preProcess(var5);
                              var5.resetPostBegin();
                           }

                           try {
                              var10000.doTimeout(var10001);
                              if (var5 != null) {
                                 InstrumentationSupport.postProcess(var5);
                              }
                           } catch (Throwable var9) {
                              if (var5 != null) {
                                 var5.th = var9;
                                 InstrumentationSupport.postProcess(var5);
                              }

                              throw var9;
                           }
                        } else {
                           entry.getResponse().setStatus(503);
                        }

                        entry.closeResponse();
                     }
                  }
               } catch (Exception var11) {
                  throw (RuntimeException)(new RuntimeException("AbstractAsyncServlet timeout failed")).initCause(var11);
               }
            }

            static {
               _WLDF$INST_FLD_Servlet_Async_Action_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Async_Action_Around_Medium");
               _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "AsyncServletSupportImpl.java", "weblogic.servlet.http.AbstractAsyncServlet", "doTimeout", "(Lweblogic/servlet/http/RequestResponseKey;)V", 69, "weblogic.servlet.http.AsyncServletSupportImpl$1", "run", "()V", (Map)null, (boolean)0);
               _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Async_Action_Around_Medium};
            }
         });
      }
   }

   static {
      _WLDF$INST_FLD_Servlet_Async_Action_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Async_Action_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "AsyncServletSupportImpl.java", "weblogic.servlet.http.AbstractAsyncServlet", "doResponse", "(Lweblogic/servlet/http/RequestResponseKey;Ljava/lang/Object;)V", 34, "weblogic.servlet.http.AsyncServletSupportImpl", "notify", "(Lweblogic/servlet/http/RequestResponseKey;Ljava/lang/Object;)V", (Map)null, (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Async_Action_Around_Medium};
   }
}
