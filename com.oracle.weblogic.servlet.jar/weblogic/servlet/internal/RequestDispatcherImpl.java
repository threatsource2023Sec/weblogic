package weblogic.servlet.internal;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.servlet.internal.session.SessionInternal;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.utils.http.HttpParsing;

public final class RequestDispatcherImpl implements RequestDispatcher {
   public static final String MAPPING_INCLUDE = "javax.servlet.include.mapping";
   public static final String REQUEST_URI_INCLUDE = "javax.servlet.include.request_uri";
   public static final String CONTEXT_PATH_INCLUDE = "javax.servlet.include.context_path";
   public static final String SERVLET_PATH_INCLUDE = "javax.servlet.include.servlet_path";
   public static final String PATH_INFO_INCLUDE = "javax.servlet.include.path_info";
   public static final String QUERY_STRING_INCLUDE = "javax.servlet.include.query_string";
   public static final String MAPPING_FORWARD = "javax.servlet.forward.mapping";
   public static final String REQUEST_URI_FORWARD = "javax.servlet.forward.request_uri";
   private static final String CONTEXT_PATH_FORWARD = "javax.servlet.forward.context_path";
   private static final String SERVLET_PATH_FORWARD = "javax.servlet.forward.servlet_path";
   private static final String PATH_INFO_FORWARD = "javax.servlet.forward.path_info";
   private static final String QUERY_STRING_FORWARD = "javax.servlet.forward.query_string";
   private static final String SERVLET_PATH_FORWARD_TARGET = "weblogic.servlet.forward.target_servlet_path";
   static final String CROSS_CONTEXT_PATH = "weblogic.servlet.internal.crosscontext.path";
   static final String CROSS_CONTEXT_TYPE = "weblogic.servlet.internal.crosscontext.type";
   static final String INCLUDE = "include";
   static final String FORWARD = "forward";
   private final WebAppServletContext context;
   private final String requestPath;
   private final String param;
   private final int mode;
   private ServletStubImpl sstub;
   private boolean filtersDiabled;
   private boolean namedDispatcher;
   static final long serialVersionUID = 5837926349150265391L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.internal.RequestDispatcherImpl");
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Request_Dispatch_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   public RequestDispatcherImpl(String servletPath, WebAppServletContext sci, int mode) {
      this(servletPath, (String)null, sci, mode);
   }

   public RequestDispatcherImpl(String servletPath, String param, WebAppServletContext sci, int mode) {
      this.filtersDiabled = false;
      this.namedDispatcher = false;
      this.context = sci;
      String cp = sci.getContextPath();
      if (cp != null && cp.length() > 1) {
         this.requestPath = cp + servletPath;
      } else {
         this.requestPath = servletPath;
      }

      this.param = param;
      this.sstub = null;
      this.mode = mode;
   }

   public RequestDispatcherImpl(ServletStubImpl ssi, WebAppServletContext sci, int mode) {
      this.filtersDiabled = false;
      this.namedDispatcher = false;
      this.requestPath = null;
      this.param = null;
      this.context = sci;
      this.sstub = ssi;
      this.mode = mode;
      this.namedDispatcher = true;
   }

   public void forward(ServletRequest req, ServletResponse rsp) throws IOException, ServletException {
      LocalHolder var43;
      if ((var43 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var43.argsCapture) {
            var43.args = new Object[3];
            Object[] var10000 = var43.args;
            var10000[0] = this;
            var10000[1] = req;
            var10000[2] = rsp;
         }

         InstrumentationSupport.createDynamicJoinPoint(var43);
         InstrumentationSupport.preProcess(var43);
         var43.resetPostBegin();
      }

      label6438: {
         label6439: {
            label6440: {
               label6441: {
                  label6442: {
                     label6443: {
                        try {
                           label6444: {
                              ServletRequestImpl reqi;
                              ServletResponseImpl rspi;
                              boolean wrapper;
                              Object originalReqURI;
                              Object originalContextPath;
                              Object originalServletPath;
                              Object originalPathInfo;
                              Object originalQueryString;
                              Object originalMapping;
                              Object origTargetServletPath;
                              String origServletPath;
                              String origPathInfo;
                              String previousServletPath;
                              String previousRelativeUri;
                              boolean propagateException;
                              HttpSession originalSession;
                              SubjectHandle originalSubject;
                              boolean originalSessionMarker;
                              WebAppServletContext originalCtx;
                              boolean acrossContext;
                              ClassLoader oldCl;
                              Thread thread;
                              Object outerContextType;
                              ServletOutputStreamImpl soi;
                              HttpSession newSession;
                              boolean suspended;
                              label6445: {
                                 reqi = ServletRequestImpl.getOriginalRequest(req);
                                 rspi = null;

                                 try {
                                    rspi = ServletResponseImpl.getOriginalResponse(rsp);
                                 } catch (ClassCastException var169) {
                                    rspi = reqi.getResponse();
                                 }

                                 if (rsp.isCommitted()) {
                                    throw new IllegalStateException("Cannot forward a response that is already committed");
                                 }

                                 wrapper = rsp instanceof ServletResponseWrapper;
                                 if (wrapper) {
                                    rsp.resetBuffer();
                                 } else {
                                    rspi.resetBuffer();
                                 }

                                 rspi.resetOutputState();
                                 this.clearIncludeAttributes(req);
                                 originalReqURI = req.getAttribute("javax.servlet.forward.request_uri");
                                 originalContextPath = req.getAttribute("javax.servlet.forward.context_path");
                                 originalServletPath = req.getAttribute("javax.servlet.forward.servlet_path");
                                 originalPathInfo = req.getAttribute("javax.servlet.forward.path_info");
                                 originalQueryString = req.getAttribute("javax.servlet.forward.query_string");
                                 originalMapping = req.getAttribute("javax.servlet.forward.mapping");
                                 origTargetServletPath = req.getAttribute("weblogic.servlet.forward.target_servlet_path");
                                 origServletPath = null;
                                 origPathInfo = null;
                                 if (this.sstub == null && req.getAttribute("javax.servlet.forward.request_uri") == null) {
                                    this.setAttributesForForward(req, reqi);
                                 }

                                 previousServletPath = reqi.getServletPath();
                                 previousRelativeUri = reqi.getRelativeUri();
                                 propagateException = false;
                                 originalSession = null;
                                 originalSubject = null;
                                 originalSessionMarker = false;
                                 originalCtx = reqi.getContext();
                                 acrossContext = originalCtx != this.context;
                                 oldCl = null;
                                 thread = null;
                                 outerContextType = null;
                                 boolean var168 = false;

                                 label6446: {
                                    try {
                                       var168 = true;
                                       if (acrossContext) {
                                          thread = Thread.currentThread();
                                          oldCl = this.context.pushEnvironment(thread);
                                          outerContextType = reqi.getAttribute("weblogic.servlet.internal.crosscontext.type");
                                          reqi.initContext(this.context);
                                          rspi.initContext(this.context);
                                          reqi.setAttribute("weblogic.servlet.internal.crosscontext.type", "forward");
                                          originalSession = reqi.getSession(false);
                                          originalSessionMarker = reqi.getSessionHelper().getSessionExistanceChecked();
                                          originalSubject = reqi.getCurrentSubject();
                                          if (originalSession != null) {
                                             if (originalSession.isNew()) {
                                                reqi.getSessionHelper().rememberSessionID(originalCtx.getSessionContext().getConfigMgr().getCookieName(), originalCtx.getSessionContext().getConfigMgr().getCookiePath(), ((SessionInternal)originalSession).getIdWithServerInfo());
                                             }

                                             originalCtx.exitingContext(reqi, rspi, originalSession);
                                          }

                                          boolean initSess = !this.isSessionCookieShared(originalCtx);
                                          reqi.getSessionHelper().resetSession(initSess);
                                       }

                                       if (this.namedDispatcher && this.mode == 3) {
                                          Object objErrorPageAttr = req.getAttribute("weblogic.servlet.errorPage");
                                          if (objErrorPageAttr != null) {
                                             if (req.getAttribute("javax.servlet.forward.servlet_path") == null) {
                                                this.setAttributesForForward(req, reqi);
                                             }

                                             reqi.initFromRequestURI(this.context.getContextPath() + objErrorPageAttr.toString());
                                             origServletPath = reqi.getServletPath();
                                             origPathInfo = reqi.getPathInfo();
                                             reqi.setServletPathAndPathInfo(reqi.getRelativeUri(), this.context.resolveForwardServletPath(reqi));
                                          }
                                       }

                                       if (!this.namedDispatcher) {
                                          if (this.requestPath == null) {
                                             throw new ServletException("Cannot resolve request - requestPath was null");
                                          }

                                          reqi.initFromRequestURI(this.requestPath);
                                          reqi.addForwardParameter(this.param);
                                          reqi.initInputEncoding();
                                          this.sstub = this.context.resolveForwardedRequest(reqi, req);
                                          if (reqi.getSendRedirect()) {
                                             rspi.sendRedirect(reqi.getRedirectURI());
                                             var168 = false;
                                             break label6445;
                                          }

                                          if (this.sstub == null) {
                                             throw new ServletException("Cannot forward request - servlet for path: '" + this.requestPath + "' not found.");
                                          }
                                       }

                                       if (this.context.getConfigManager().isCheckAuthOnForwardEnabled() && !this.namedDispatcher && !this.context.getSecurityManagerWithPrivilege().checkAccess(reqi, rspi, false, false)) {
                                          var168 = false;
                                          break label6446;
                                       }

                                       ServletResponse parent = rsp;
                                       ServletResponseWrapper wrappedRes = null;
                                       boolean foundFirstNonBodyResponse = false;
                                       if (rsp instanceof ServletResponseWrapper && !(rsp instanceof RemoveWrapperOnForward)) {
                                          foundFirstNonBodyResponse = true;
                                          wrappedRes = (ServletResponseWrapper)rsp;
                                       }

                                       while(parent instanceof ServletResponseWrapper) {
                                          parent = ((ServletResponseWrapper)parent).getResponse();
                                          if (!(parent instanceof RemoveWrapperOnForward)) {
                                             if (!foundFirstNonBodyResponse) {
                                                rsp = parent;
                                                foundFirstNonBodyResponse = true;
                                             }

                                             if (wrappedRes != null) {
                                                wrappedRes.setResponse(parent);
                                             }

                                             if (parent instanceof ServletResponseWrapper) {
                                                wrappedRes = (ServletResponseWrapper)parent;
                                             }
                                          }
                                       }

                                       req.setAttribute("weblogic.servlet.forward.target_servlet_path", this.namedDispatcher ? null : reqi.getServletPath());
                                       if (rsp instanceof ServletResponseImpl) {
                                          wrapper = false;
                                       }

                                       int m = this.mode;
                                       if (m == -1) {
                                          m = 1;
                                       }

                                       this.invokeServlet(acrossContext, req, rsp, rspi, m);
                                       var168 = false;
                                    } catch (Throwable var178) {
                                       if (!rspi.isCommitted()) {
                                          propagateException = true;
                                       }

                                       if (var178 instanceof ServletException) {
                                          throw (ServletException)var178;
                                       }

                                       if (var178 instanceof IOException) {
                                          throw (IOException)var178;
                                       }

                                       if (var178 instanceof RuntimeException) {
                                          throw (RuntimeException)var178;
                                       }

                                       propagateException = false;
                                       throw new ServletException(var178);
                                    } finally {
                                       if (var168) {
                                          if (reqi.isAsyncMode()) {
                                             if (wrapper) {
                                                rsp.flushBuffer();
                                             }
                                             break label6444;
                                          } else {
                                             boolean var143 = false;

                                             try {
                                                var143 = true;
                                                if (!propagateException) {
                                                   ServletOutputStreamImpl soi = (ServletOutputStreamImpl)rspi.getOutputStreamNoCheck();
                                                   if (wrapper) {
                                                      boolean suspended = soi.isSuspended();
                                                      soi.setSuspended(false);

                                                      try {
                                                         rsp.flushBuffer();
                                                      } finally {
                                                         if (suspended) {
                                                            soi.setSuspended(true);
                                                         }

                                                      }

                                                      var143 = false;
                                                   } else {
                                                      soi.setDoFinish(true);
                                                      soi.commit();
                                                      var143 = false;
                                                   }
                                                } else {
                                                   var143 = false;
                                                }
                                             } finally {
                                                if (var143) {
                                                   if (acrossContext) {
                                                      HttpSession newSession = reqi.getSession(false);
                                                      if (newSession != null) {
                                                         this.context.exitingContext(reqi, rspi, newSession);
                                                      }

                                                      WebAppServletContext.popEnvironment(thread, oldCl);
                                                      reqi.setAttribute("weblogic.servlet.internal.crosscontext.type", outerContextType);
                                                      reqi.initContext(originalCtx);
                                                      rspi.initContext(originalCtx);
                                                      if (originalSession != null) {
                                                         originalCtx.enteringContext(reqi, rspi, originalSession);
                                                      }

                                                      reqi.getSessionHelper().setSession(originalSession);
                                                      reqi.getSessionHelper().setSessionExistanceChecked(originalSessionMarker);
                                                      reqi.setCurrentSubject(originalSubject);
                                                   }

                                                   if (this.param != null && !this.namedDispatcher) {
                                                      reqi.removeRequestDispatcherQueryString();
                                                   }

                                                   reqi.setServletPathAndPathInfo(previousRelativeUri, previousServletPath);
                                                   reqi.initFromRequestParser();
                                                   if (this.namedDispatcher && this.mode == 3) {
                                                      reqi.setServletPath(origServletPath);
                                                      reqi.setPathInfo(origPathInfo);
                                                   }

                                                   req.setAttribute("javax.servlet.forward.request_uri", originalReqURI);
                                                   req.setAttribute("javax.servlet.forward.context_path", originalContextPath);
                                                   req.setAttribute("javax.servlet.forward.servlet_path", originalServletPath);
                                                   req.setAttribute("javax.servlet.forward.path_info", originalPathInfo);
                                                   req.setAttribute("javax.servlet.forward.query_string", originalQueryString);
                                                   req.setAttribute("javax.servlet.forward.mapping", originalMapping);
                                                   req.setAttribute("weblogic.servlet.forward.target_servlet_path", origTargetServletPath);
                                                }
                                             }

                                             if (acrossContext) {
                                                HttpSession newSession = reqi.getSession(false);
                                                if (newSession != null) {
                                                   this.context.exitingContext(reqi, rspi, newSession);
                                                }

                                                WebAppServletContext.popEnvironment(thread, oldCl);
                                                reqi.setAttribute("weblogic.servlet.internal.crosscontext.type", outerContextType);
                                                reqi.initContext(originalCtx);
                                                rspi.initContext(originalCtx);
                                                if (originalSession != null) {
                                                   originalCtx.enteringContext(reqi, rspi, originalSession);
                                                }

                                                reqi.getSessionHelper().setSession(originalSession);
                                                reqi.getSessionHelper().setSessionExistanceChecked(originalSessionMarker);
                                                reqi.setCurrentSubject(originalSubject);
                                             }

                                             if (this.param != null && !this.namedDispatcher) {
                                                reqi.removeRequestDispatcherQueryString();
                                             }

                                             reqi.setServletPathAndPathInfo(previousRelativeUri, previousServletPath);
                                             reqi.initFromRequestParser();
                                             if (this.namedDispatcher && this.mode == 3) {
                                                reqi.setServletPath(origServletPath);
                                                reqi.setPathInfo(origPathInfo);
                                             }

                                             req.setAttribute("javax.servlet.forward.request_uri", originalReqURI);
                                             req.setAttribute("javax.servlet.forward.context_path", originalContextPath);
                                             req.setAttribute("javax.servlet.forward.servlet_path", originalServletPath);
                                             req.setAttribute("javax.servlet.forward.path_info", originalPathInfo);
                                             req.setAttribute("javax.servlet.forward.query_string", originalQueryString);
                                             req.setAttribute("javax.servlet.forward.mapping", originalMapping);
                                             req.setAttribute("weblogic.servlet.forward.target_servlet_path", origTargetServletPath);
                                          }
                                       }
                                    }

                                    if (reqi.isAsyncMode()) {
                                       if (wrapper) {
                                          rsp.flushBuffer();
                                       }
                                       break label6442;
                                    }

                                    boolean var68 = false;

                                    try {
                                       var68 = true;
                                       if (!propagateException) {
                                          soi = (ServletOutputStreamImpl)rspi.getOutputStreamNoCheck();
                                          if (wrapper) {
                                             suspended = soi.isSuspended();
                                             soi.setSuspended(false);

                                             try {
                                                rsp.flushBuffer();
                                             } finally {
                                                if (suspended) {
                                                   soi.setSuspended(true);
                                                }

                                             }

                                             var68 = false;
                                          } else {
                                             soi.setDoFinish(true);
                                             soi.commit();
                                             var68 = false;
                                          }
                                       } else {
                                          var68 = false;
                                       }
                                    } finally {
                                       if (var68) {
                                          if (acrossContext) {
                                             HttpSession newSession = reqi.getSession(false);
                                             if (newSession != null) {
                                                this.context.exitingContext(reqi, rspi, newSession);
                                             }

                                             WebAppServletContext.popEnvironment(thread, oldCl);
                                             reqi.setAttribute("weblogic.servlet.internal.crosscontext.type", outerContextType);
                                             reqi.initContext(originalCtx);
                                             rspi.initContext(originalCtx);
                                             if (originalSession != null) {
                                                originalCtx.enteringContext(reqi, rspi, originalSession);
                                             }

                                             reqi.getSessionHelper().setSession(originalSession);
                                             reqi.getSessionHelper().setSessionExistanceChecked(originalSessionMarker);
                                             reqi.setCurrentSubject(originalSubject);
                                          }

                                          if (this.param != null && !this.namedDispatcher) {
                                             reqi.removeRequestDispatcherQueryString();
                                          }

                                          reqi.setServletPathAndPathInfo(previousRelativeUri, previousServletPath);
                                          reqi.initFromRequestParser();
                                          if (this.namedDispatcher && this.mode == 3) {
                                             reqi.setServletPath(origServletPath);
                                             reqi.setPathInfo(origPathInfo);
                                          }

                                          req.setAttribute("javax.servlet.forward.request_uri", originalReqURI);
                                          req.setAttribute("javax.servlet.forward.context_path", originalContextPath);
                                          req.setAttribute("javax.servlet.forward.servlet_path", originalServletPath);
                                          req.setAttribute("javax.servlet.forward.path_info", originalPathInfo);
                                          req.setAttribute("javax.servlet.forward.query_string", originalQueryString);
                                          req.setAttribute("javax.servlet.forward.mapping", originalMapping);
                                          req.setAttribute("weblogic.servlet.forward.target_servlet_path", origTargetServletPath);
                                       }
                                    }

                                    if (acrossContext) {
                                       newSession = reqi.getSession(false);
                                       if (newSession != null) {
                                          this.context.exitingContext(reqi, rspi, newSession);
                                       }

                                       WebAppServletContext.popEnvironment(thread, oldCl);
                                       reqi.setAttribute("weblogic.servlet.internal.crosscontext.type", outerContextType);
                                       reqi.initContext(originalCtx);
                                       rspi.initContext(originalCtx);
                                       if (originalSession != null) {
                                          originalCtx.enteringContext(reqi, rspi, originalSession);
                                       }

                                       reqi.getSessionHelper().setSession(originalSession);
                                       reqi.getSessionHelper().setSessionExistanceChecked(originalSessionMarker);
                                       reqi.setCurrentSubject(originalSubject);
                                    }

                                    if (this.param != null && !this.namedDispatcher) {
                                       reqi.removeRequestDispatcherQueryString();
                                    }

                                    reqi.setServletPathAndPathInfo(previousRelativeUri, previousServletPath);
                                    reqi.initFromRequestParser();
                                    if (this.namedDispatcher && this.mode == 3) {
                                       reqi.setServletPath(origServletPath);
                                       reqi.setPathInfo(origPathInfo);
                                    }

                                    req.setAttribute("javax.servlet.forward.request_uri", originalReqURI);
                                    req.setAttribute("javax.servlet.forward.context_path", originalContextPath);
                                    req.setAttribute("javax.servlet.forward.servlet_path", originalServletPath);
                                    req.setAttribute("javax.servlet.forward.path_info", originalPathInfo);
                                    req.setAttribute("javax.servlet.forward.query_string", originalQueryString);
                                    req.setAttribute("javax.servlet.forward.mapping", originalMapping);
                                    req.setAttribute("weblogic.servlet.forward.target_servlet_path", origTargetServletPath);
                                    break label6443;
                                 }

                                 if (reqi.isAsyncMode()) {
                                    if (wrapper) {
                                       rsp.flushBuffer();
                                    }
                                    break label6440;
                                 }

                                 boolean var81 = false;

                                 try {
                                    var81 = true;
                                    if (!propagateException) {
                                       soi = (ServletOutputStreamImpl)rspi.getOutputStreamNoCheck();
                                       if (wrapper) {
                                          suspended = soi.isSuspended();
                                          soi.setSuspended(false);

                                          try {
                                             rsp.flushBuffer();
                                          } finally {
                                             if (suspended) {
                                                soi.setSuspended(true);
                                             }

                                          }

                                          var81 = false;
                                       } else {
                                          soi.setDoFinish(true);
                                          soi.commit();
                                          var81 = false;
                                       }
                                    } else {
                                       var81 = false;
                                    }
                                 } finally {
                                    if (var81) {
                                       if (acrossContext) {
                                          HttpSession newSession = reqi.getSession(false);
                                          if (newSession != null) {
                                             this.context.exitingContext(reqi, rspi, newSession);
                                          }

                                          WebAppServletContext.popEnvironment(thread, oldCl);
                                          reqi.setAttribute("weblogic.servlet.internal.crosscontext.type", outerContextType);
                                          reqi.initContext(originalCtx);
                                          rspi.initContext(originalCtx);
                                          if (originalSession != null) {
                                             originalCtx.enteringContext(reqi, rspi, originalSession);
                                          }

                                          reqi.getSessionHelper().setSession(originalSession);
                                          reqi.getSessionHelper().setSessionExistanceChecked(originalSessionMarker);
                                          reqi.setCurrentSubject(originalSubject);
                                       }

                                       if (this.param != null && !this.namedDispatcher) {
                                          reqi.removeRequestDispatcherQueryString();
                                       }

                                       reqi.setServletPathAndPathInfo(previousRelativeUri, previousServletPath);
                                       reqi.initFromRequestParser();
                                       if (this.namedDispatcher && this.mode == 3) {
                                          reqi.setServletPath(origServletPath);
                                          reqi.setPathInfo(origPathInfo);
                                       }

                                       req.setAttribute("javax.servlet.forward.request_uri", originalReqURI);
                                       req.setAttribute("javax.servlet.forward.context_path", originalContextPath);
                                       req.setAttribute("javax.servlet.forward.servlet_path", originalServletPath);
                                       req.setAttribute("javax.servlet.forward.path_info", originalPathInfo);
                                       req.setAttribute("javax.servlet.forward.query_string", originalQueryString);
                                       req.setAttribute("javax.servlet.forward.mapping", originalMapping);
                                       req.setAttribute("weblogic.servlet.forward.target_servlet_path", origTargetServletPath);
                                    }
                                 }

                                 if (acrossContext) {
                                    newSession = reqi.getSession(false);
                                    if (newSession != null) {
                                       this.context.exitingContext(reqi, rspi, newSession);
                                    }

                                    WebAppServletContext.popEnvironment(thread, oldCl);
                                    reqi.setAttribute("weblogic.servlet.internal.crosscontext.type", outerContextType);
                                    reqi.initContext(originalCtx);
                                    rspi.initContext(originalCtx);
                                    if (originalSession != null) {
                                       originalCtx.enteringContext(reqi, rspi, originalSession);
                                    }

                                    reqi.getSessionHelper().setSession(originalSession);
                                    reqi.getSessionHelper().setSessionExistanceChecked(originalSessionMarker);
                                    reqi.setCurrentSubject(originalSubject);
                                 }

                                 if (this.param != null && !this.namedDispatcher) {
                                    reqi.removeRequestDispatcherQueryString();
                                 }

                                 reqi.setServletPathAndPathInfo(previousRelativeUri, previousServletPath);
                                 reqi.initFromRequestParser();
                                 if (this.namedDispatcher && this.mode == 3) {
                                    reqi.setServletPath(origServletPath);
                                    reqi.setPathInfo(origPathInfo);
                                 }

                                 req.setAttribute("javax.servlet.forward.request_uri", originalReqURI);
                                 req.setAttribute("javax.servlet.forward.context_path", originalContextPath);
                                 req.setAttribute("javax.servlet.forward.servlet_path", originalServletPath);
                                 req.setAttribute("javax.servlet.forward.path_info", originalPathInfo);
                                 req.setAttribute("javax.servlet.forward.query_string", originalQueryString);
                                 req.setAttribute("javax.servlet.forward.mapping", originalMapping);
                                 req.setAttribute("weblogic.servlet.forward.target_servlet_path", origTargetServletPath);
                                 break label6441;
                              }

                              if (reqi.isAsyncMode()) {
                                 if (wrapper) {
                                    rsp.flushBuffer();
                                 }
                                 break label6438;
                              }

                              boolean var94 = false;

                              try {
                                 var94 = true;
                                 if (!propagateException) {
                                    soi = (ServletOutputStreamImpl)rspi.getOutputStreamNoCheck();
                                    if (wrapper) {
                                       suspended = soi.isSuspended();
                                       soi.setSuspended(false);

                                       try {
                                          rsp.flushBuffer();
                                       } finally {
                                          if (suspended) {
                                             soi.setSuspended(true);
                                          }

                                       }

                                       var94 = false;
                                    } else {
                                       soi.setDoFinish(true);
                                       soi.commit();
                                       var94 = false;
                                    }
                                 } else {
                                    var94 = false;
                                 }
                              } finally {
                                 if (var94) {
                                    if (acrossContext) {
                                       HttpSession newSession = reqi.getSession(false);
                                       if (newSession != null) {
                                          this.context.exitingContext(reqi, rspi, newSession);
                                       }

                                       WebAppServletContext.popEnvironment(thread, oldCl);
                                       reqi.setAttribute("weblogic.servlet.internal.crosscontext.type", outerContextType);
                                       reqi.initContext(originalCtx);
                                       rspi.initContext(originalCtx);
                                       if (originalSession != null) {
                                          originalCtx.enteringContext(reqi, rspi, originalSession);
                                       }

                                       reqi.getSessionHelper().setSession(originalSession);
                                       reqi.getSessionHelper().setSessionExistanceChecked(originalSessionMarker);
                                       reqi.setCurrentSubject(originalSubject);
                                    }

                                    if (this.param != null && !this.namedDispatcher) {
                                       reqi.removeRequestDispatcherQueryString();
                                    }

                                    reqi.setServletPathAndPathInfo(previousRelativeUri, previousServletPath);
                                    reqi.initFromRequestParser();
                                    if (this.namedDispatcher && this.mode == 3) {
                                       reqi.setServletPath(origServletPath);
                                       reqi.setPathInfo(origPathInfo);
                                    }

                                    req.setAttribute("javax.servlet.forward.request_uri", originalReqURI);
                                    req.setAttribute("javax.servlet.forward.context_path", originalContextPath);
                                    req.setAttribute("javax.servlet.forward.servlet_path", originalServletPath);
                                    req.setAttribute("javax.servlet.forward.path_info", originalPathInfo);
                                    req.setAttribute("javax.servlet.forward.query_string", originalQueryString);
                                    req.setAttribute("javax.servlet.forward.mapping", originalMapping);
                                    req.setAttribute("weblogic.servlet.forward.target_servlet_path", origTargetServletPath);
                                 }
                              }

                              if (acrossContext) {
                                 newSession = reqi.getSession(false);
                                 if (newSession != null) {
                                    this.context.exitingContext(reqi, rspi, newSession);
                                 }

                                 WebAppServletContext.popEnvironment(thread, oldCl);
                                 reqi.setAttribute("weblogic.servlet.internal.crosscontext.type", outerContextType);
                                 reqi.initContext(originalCtx);
                                 rspi.initContext(originalCtx);
                                 if (originalSession != null) {
                                    originalCtx.enteringContext(reqi, rspi, originalSession);
                                 }

                                 reqi.getSessionHelper().setSession(originalSession);
                                 reqi.getSessionHelper().setSessionExistanceChecked(originalSessionMarker);
                                 reqi.setCurrentSubject(originalSubject);
                              }

                              if (this.param != null && !this.namedDispatcher) {
                                 reqi.removeRequestDispatcherQueryString();
                              }

                              reqi.setServletPathAndPathInfo(previousRelativeUri, previousServletPath);
                              reqi.initFromRequestParser();
                              if (this.namedDispatcher && this.mode == 3) {
                                 reqi.setServletPath(origServletPath);
                                 reqi.setPathInfo(origPathInfo);
                              }

                              req.setAttribute("javax.servlet.forward.request_uri", originalReqURI);
                              req.setAttribute("javax.servlet.forward.context_path", originalContextPath);
                              req.setAttribute("javax.servlet.forward.servlet_path", originalServletPath);
                              req.setAttribute("javax.servlet.forward.path_info", originalPathInfo);
                              req.setAttribute("javax.servlet.forward.query_string", originalQueryString);
                              req.setAttribute("javax.servlet.forward.mapping", originalMapping);
                              req.setAttribute("weblogic.servlet.forward.target_servlet_path", origTargetServletPath);
                              break label6439;
                           }
                        } catch (Throwable var180) {
                           if (var43 != null) {
                              var43.th = var180;
                              InstrumentationSupport.postProcess(var43);
                           }

                           throw var180;
                        }

                        if (var43 != null) {
                           InstrumentationSupport.postProcess(var43);
                        }

                        return;
                     }

                     if (var43 != null) {
                        InstrumentationSupport.postProcess(var43);
                     }

                     return;
                  }

                  if (var43 != null) {
                     InstrumentationSupport.postProcess(var43);
                  }

                  return;
               }

               if (var43 != null) {
                  InstrumentationSupport.postProcess(var43);
               }

               return;
            }

            if (var43 != null) {
               InstrumentationSupport.postProcess(var43);
            }

            return;
         }

         if (var43 != null) {
            InstrumentationSupport.postProcess(var43);
         }

         return;
      }

      if (var43 != null) {
         InstrumentationSupport.postProcess(var43);
      }

   }

   public void include(ServletRequest rq, ServletResponse rp) throws IOException, ServletException {
      LocalHolder var27;
      if ((var27 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var27.argsCapture) {
            var27.args = new Object[3];
            Object[] var10000 = var27.args;
            var10000[0] = this;
            var10000[1] = rq;
            var10000[2] = rp;
         }

         InstrumentationSupport.createDynamicJoinPoint(var27);
         InstrumentationSupport.preProcess(var27);
         var27.resetPostBegin();
      }

      try {
         ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(rq);
         ServletResponseImpl rspi = reqi.getResponse();
         ServletOutputStreamImpl sos = (ServletOutputStreamImpl)rspi.getOutputStreamNoCheck();
         boolean originalDoFinish = sos.getDoFinish();
         Object originalReqURI = rq.getAttribute("javax.servlet.include.request_uri");
         Object originalContextPath = rq.getAttribute("javax.servlet.include.context_path");
         Object originalServletPath = rq.getAttribute("javax.servlet.include.servlet_path");
         Object originalPathInfo = rq.getAttribute("javax.servlet.include.path_info");
         Object originalQueryString = rq.getAttribute("javax.servlet.include.query_string");
         Object originalMapping = rq.getAttribute("javax.servlet.include.mapping");
         HttpSession originalSession = null;
         SubjectHandle originalSubject = null;
         boolean originalSessionMarker = false;
         WebAppServletContext originalCtx = reqi.getContext();
         boolean acrossContext = originalCtx != this.context;
         boolean wrapper = rp instanceof ServletResponseWrapper;
         Thread thread = null;
         ClassLoader oldCl = null;
         Object outerContextPath = null;
         Object outerDispatchType = null;
         boolean var32 = false;

         try {
            var32 = true;
            if (acrossContext) {
               thread = Thread.currentThread();
               oldCl = this.context.pushEnvironment(thread);
               originalSession = reqi.getSession(false);
               originalSessionMarker = reqi.getSessionHelper().getSessionExistanceChecked();
               originalSubject = reqi.getCurrentSubject();
               outerContextPath = reqi.getAttribute("weblogic.servlet.internal.crosscontext.path");
               outerDispatchType = reqi.getAttribute("weblogic.servlet.internal.crosscontext.type");
               reqi.initContext(this.context);
               rspi.initContext(this.context);
               reqi.setAttribute("weblogic.servlet.internal.crosscontext.path", originalCtx.getContextPath());
               reqi.setAttribute("weblogic.servlet.internal.crosscontext.type", "include");
               if (originalSession != null) {
                  if (originalSession.isNew()) {
                     reqi.getSessionHelper().rememberSessionID(originalCtx.getSessionContext().getConfigMgr().getCookieName(), originalCtx.getSessionContext().getConfigMgr().getCookiePath(), ((SessionInternal)originalSession).getIdWithServerInfo());
                  }

                  originalCtx.exitingContext(reqi, rspi, originalSession);
               }

               boolean initSess = !this.isSessionCookieShared(originalCtx);
               reqi.getSessionHelper().resetSession(initSess);
            }

            if (!this.namedDispatcher) {
               if (this.requestPath == null) {
                  throw new ServletException("requestPath was null");
               }

               String reqPath = this.requestPath;
               rq.setAttribute("javax.servlet.include.context_path", this.context.getContextPath());
               if (this.param != null) {
                  rq.setAttribute("javax.servlet.include.query_string", this.param);
                  reqi.addIncludeParameter(this.param);
               }

               reqPath = HttpParsing.unescape(reqPath).trim();
               rq.setAttribute("javax.servlet.include.request_uri", reqPath);
               this.sstub = this.context.resolveIncludedRequest(reqi, rq);
               if (this.sstub == null) {
                  throw new ServletException("Failed to resolve path: " + reqPath);
               }
            }

            Object nest;
            if (!wrapper) {
               nest = new NestedServletResponse(rspi);
            } else {
               nest = nestWrapperResponse(rp);
            }

            sos.setDoFinish(false);
            int m = this.mode;
            if (m == -1) {
               m = 2;
            }

            this.invokeServlet(acrossContext, rq, (ServletResponse)nest, rspi, m);
            var32 = false;
         } finally {
            if (var32) {
               if (acrossContext) {
                  HttpSession newSession = reqi.getSession(false);
                  if (newSession != null) {
                     this.context.exitingContext(reqi, rspi, newSession);
                  }

                  WebAppServletContext.popEnvironment(thread, oldCl);
                  reqi.initContext(originalCtx);
                  rspi.initContext(originalCtx);
                  if (originalSession != null) {
                     originalCtx.enteringContext(reqi, rspi, originalSession);
                  }

                  reqi.getSessionHelper().setSession(originalSession);
                  reqi.getSessionHelper().setSessionExistanceChecked(originalSessionMarker);
                  reqi.setCurrentSubject(originalSubject);
                  reqi.setAttribute("weblogic.servlet.internal.crosscontext.path", outerContextPath);
                  reqi.setAttribute("weblogic.servlet.internal.crosscontext.type", outerDispatchType);
               }

               sos.setDoFinish(originalDoFinish);
               if (this.param != null && !this.namedDispatcher) {
                  reqi.removeRequestDispatcherQueryString();
               }

               rq.setAttribute("javax.servlet.include.request_uri", originalReqURI);
               rq.setAttribute("javax.servlet.include.context_path", originalContextPath);
               rq.setAttribute("javax.servlet.include.servlet_path", originalServletPath);
               rq.setAttribute("javax.servlet.include.path_info", originalPathInfo);
               rq.setAttribute("javax.servlet.include.query_string", originalQueryString);
               rq.setAttribute("javax.servlet.include.mapping", originalMapping);
               if (wrapper) {
                  rp = unsetNestedWrapper(rp);
               }

            }
         }

         if (acrossContext) {
            HttpSession newSession = reqi.getSession(false);
            if (newSession != null) {
               this.context.exitingContext(reqi, rspi, newSession);
            }

            WebAppServletContext.popEnvironment(thread, oldCl);
            reqi.initContext(originalCtx);
            rspi.initContext(originalCtx);
            if (originalSession != null) {
               originalCtx.enteringContext(reqi, rspi, originalSession);
            }

            reqi.getSessionHelper().setSession(originalSession);
            reqi.getSessionHelper().setSessionExistanceChecked(originalSessionMarker);
            reqi.setCurrentSubject(originalSubject);
            reqi.setAttribute("weblogic.servlet.internal.crosscontext.path", outerContextPath);
            reqi.setAttribute("weblogic.servlet.internal.crosscontext.type", outerDispatchType);
         }

         sos.setDoFinish(originalDoFinish);
         if (this.param != null && !this.namedDispatcher) {
            reqi.removeRequestDispatcherQueryString();
         }

         rq.setAttribute("javax.servlet.include.request_uri", originalReqURI);
         rq.setAttribute("javax.servlet.include.context_path", originalContextPath);
         rq.setAttribute("javax.servlet.include.servlet_path", originalServletPath);
         rq.setAttribute("javax.servlet.include.path_info", originalPathInfo);
         rq.setAttribute("javax.servlet.include.query_string", originalQueryString);
         rq.setAttribute("javax.servlet.include.mapping", originalMapping);
         if (wrapper) {
            rp = unsetNestedWrapper(rp);
         }
      } catch (Throwable var34) {
         if (var27 != null) {
            var27.th = var34;
            InstrumentationSupport.postProcess(var27);
         }

         throw var34;
      }

      if (var27 != null) {
         InstrumentationSupport.postProcess(var27);
      }

   }

   private boolean isSessionCookieShared(WebAppServletContext original) {
      return original.getSessionContext().getConfigMgr().getCookieName().equals(this.context.getSessionContext().getConfigMgr().getCookieName()) && original.getSessionContext().getConfigMgr().getCookiePath().equals(this.context.getSessionContext().getConfigMgr().getCookiePath());
   }

   private void clearIncludeAttributes(ServletRequest rq) {
      rq.setAttribute("javax.servlet.include.request_uri", (Object)null);
      rq.setAttribute("javax.servlet.include.context_path", (Object)null);
      rq.setAttribute("javax.servlet.include.servlet_path", (Object)null);
      rq.setAttribute("javax.servlet.include.path_info", (Object)null);
      rq.setAttribute("javax.servlet.include.query_string", (Object)null);
      rq.setAttribute("javax.servlet.include.mapping", (Object)null);
      rq.setAttribute("weblogic.servlet.BodyTagOutput", (Object)null);
      rq.setAttribute("javax.servlet.jsp.PageContext.out", (Object)null);
   }

   private void setAttributesForForward(ServletRequest rq, ServletRequestImpl req) {
      rq.setAttribute("javax.servlet.forward.request_uri", req.getRequestURI());
      rq.setAttribute("javax.servlet.forward.context_path", req.getContextPath());
      rq.setAttribute("javax.servlet.forward.servlet_path", req.getServletPath());
      rq.setAttribute("javax.servlet.forward.path_info", req.getPathInfo());
      rq.setAttribute("javax.servlet.forward.query_string", req.getOriginalQueryString());
      rq.setAttribute("javax.servlet.forward.mapping", req.getHttpServletMapping());
   }

   public void disableFilters() {
      this.filtersDiabled = true;
   }

   static boolean isCrossContext(ServletRequest req) {
      return req.getAttribute("weblogic.servlet.internal.crosscontext.type") != null;
   }

   private void invokeServlet(boolean acrossContext, ServletRequest rq, ServletResponse wrapper, ServletResponseImpl rsp, int mode) throws IOException, ServletException {
      ServletRequestImpl reqi = ServletRequestImpl.getOriginalRequest(rq);
      DispatcherType origDispatcherType = reqi.getDispatcherType();
      ClassLoader oldCL = null;
      Thread currentThread = null;
      if (rq instanceof HttpServletRequest && acrossContext) {
         this.context.getSecurityManagerWithPrivilege().initContextHandler((HttpServletRequest)rq);
      }

      boolean needReqFilter = false;
      boolean needCDIRequestListener = false;

      try {
         if (mode == 0) {
            reqi.setDispatcherType(DispatcherType.REQUEST);
         } else if (mode == 1) {
            reqi.setDispatcherType(DispatcherType.FORWARD);
         } else if (mode == 2) {
            reqi.setDispatcherType(DispatcherType.INCLUDE);
         } else if (mode == 3) {
            reqi.setDispatcherType(DispatcherType.ERROR);
            if (!acrossContext) {
               currentThread = Thread.currentThread();
               if (currentThread.getContextClassLoader() != this.context.getServletClassLoader()) {
                  oldCL = this.context.pushEnvironment(currentThread);
               }
            }
         }

         needReqFilter = this.context.getEventsManager().hasRequestListeners() && (acrossContext || rq.getAttribute("requestInitEventNotified") == null);
         needCDIRequestListener = this.needToNotifyCDIRequestListenerAgain(this.context, rq);
         if (!needReqFilter && needCDIRequestListener) {
            this.context.getEventsManager().notifyCDIRequestLifetimeEvent(rq, true);
         }

         if (this.filtersDiabled || !this.context.getFilterManager().hasFilters() && !needReqFilter) {
            this.sstub.execute(rq, wrapper);
         } else {
            FilterChainImpl fc = this.context.getFilterManager().getFilterChain(this.sstub, rq, rsp, needReqFilter, mode);
            if (fc == null) {
               this.sstub.execute(rq, wrapper);
            } else {
               fc.doFilter(rq, wrapper);
            }
         }
      } finally {
         reqi.setDispatcherType(origDispatcherType);
         if (!needReqFilter && needCDIRequestListener) {
            this.context.getEventsManager().notifyCDIRequestLifetimeEvent(rq, false);
         }

         if (currentThread != null && oldCL != null) {
            WebAppServletContext var10000 = this.context;
            WebAppServletContext.popEnvironment(currentThread, oldCL);
         }

         if (rq instanceof HttpServletRequest && acrossContext) {
            this.context.getSecurityManagerWithPrivilege().resetContextHandler();
         }

      }

   }

   private boolean needToNotifyCDIRequestListenerAgain(WebAppServletContext context, ServletRequest req) {
      if (context.isCDIWebApplication() && context.getEventsManager().hasRequestListeners() && req.getAttribute("requestInitEventNotified") != null && req.getAttribute("requestDestroyEventNotified") != null) {
         req.removeAttribute("requestInitEventNotified");
         req.removeAttribute("requestDestroyEventNotified");
         return true;
      } else {
         return false;
      }
   }

   private static ServletResponse nestWrapperResponse(ServletResponse rp) {
      ServletResponse orig = rp;

      ServletResponseWrapper lastInChain;
      for(lastInChain = null; rp instanceof ServletResponseWrapper; rp = ((ServletResponseWrapper)rp).getResponse()) {
         lastInChain = (ServletResponseWrapper)rp;
      }

      if (lastInChain != null) {
         lastInChain.setResponse(new NestedServletResponse(rp));
      }

      return orig;
   }

   private static ServletResponse unsetNestedWrapper(ServletResponse rp) {
      ServletResponse orig = rp;

      ServletResponseWrapper lastInChain;
      for(lastInChain = null; rp instanceof ServletResponseWrapper; rp = ((ServletResponseWrapper)rp).getResponse()) {
         lastInChain = (ServletResponseWrapper)rp;
      }

      if (rp instanceof NestedServletResponse) {
         ServletResponse rs = ((NestedServletResponse)rp).getOriginalResponse();
         if (rs instanceof ServletResponseImpl && lastInChain != null) {
            lastInChain.setResponse(rs);
         }
      }

      return orig;
   }

   static {
      _WLDF$INST_FLD_Servlet_Request_Dispatch_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Request_Dispatch_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "RequestDispatcherImpl.java", "weblogic.servlet.internal.RequestDispatcherImpl", "forward", "(Ljavax/servlet/ServletRequest;Ljavax/servlet/ServletResponse;)V", 120, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Request_Dispatch_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Request_Dispatch_Around_Medium};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "RequestDispatcherImpl.java", "weblogic.servlet.internal.RequestDispatcherImpl", "include", "(Ljavax/servlet/ServletRequest;Ljavax/servlet/ServletResponse;)V", 418, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Request_Dispatch_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Request_Dispatch_Around_Medium};
   }
}
