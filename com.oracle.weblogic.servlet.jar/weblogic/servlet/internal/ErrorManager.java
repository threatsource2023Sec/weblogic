package weblogic.servlet.internal;

import java.io.IOException;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.security.PrivilegedAction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.http.HttpServletRequest;
import weblogic.j2ee.descriptor.ErrorPageBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.security.Utils;
import weblogic.servlet.security.internal.SecurityModule;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.collections.ConcurrentHashMap;
import weblogic.utils.http.HttpParsing;
import weblogic.utils.io.UnsyncByteArrayOutputStream;

public final class ErrorManager {
   public static final String ERROR_PAGE = "weblogic.servlet.errorPage";
   private final WebAppServletContext context;
   private final String[] statusErrors = new String[110];
   private final ConcurrentHashMap exceptionMap = new ConcurrentHashMap();
   private String defaultErrorPage = null;

   ErrorManager(WebAppServletContext ctx) {
      this.context = ctx;
   }

   String[] getStatusErrors() {
      return this.statusErrors;
   }

   private void registerError(int code, String location) {
      try {
         int ind = code - 400;
         if (!WebAppServletContext.isAbsoluteURL(location)) {
            location = HttpParsing.ensureStartingSlash(location);
         }

         this.statusErrors[ind] = location;
      } catch (ArrayIndexOutOfBoundsException var4) {
         HTTPLogger.logUnsupportedErrorCode(this.context.getLogContext(), var4);
      }

   }

   private void registerException(String name, String location) {
      try {
         Class cl = this.context.getServletClassLoader().loadClass(name);
         if (!WebAppServletContext.isAbsoluteURL(location)) {
            location = HttpParsing.ensureStartingSlash(location);
         }

         this.exceptionMap.put(cl, location);
      } catch (ClassNotFoundException var4) {
         HTTPLogger.logInvalidExceptionType(name, var4);
      }

   }

   void registerErrorPages(WebAppBean bean) {
      if (bean != null) {
         ErrorPageBean[] epArray = bean.getErrorPages();
         if (epArray != null) {
            for(int i = 0; i < epArray.length; ++i) {
               ErrorPageBean ep = epArray[i];
               int code = ep.getErrorCode();
               String eType = ep.getExceptionType();
               if (code > 0) {
                  this.registerError(code, ep.getLocation());
               } else if (eType != null) {
                  synchronized(this) {
                     this.registerException(eType, ep.getLocation());
                  }
               } else if (ep.getLocation() != null) {
                  this.defaultErrorPage = ep.getLocation();
               }
            }
         }

      }
   }

   public String getErrorLocation(String name) {
      try {
         int ind = Integer.parseInt(name) - 400;
         return this.statusErrors[ind] == null && this.defaultErrorPage != null ? this.defaultErrorPage : this.statusErrors[ind];
      } catch (NumberFormatException var3) {
         return null;
      } catch (ArrayIndexOutOfBoundsException var4) {
         return null;
      }
   }

   public String getExceptionLocation(Throwable e) {
      if (e == null) {
         return null;
      } else if (this.exceptionMap.isEmpty()) {
         return null;
      } else {
         String exceptionLocation = this.findExceptionLocation(e);
         if (exceptionLocation != null) {
            return exceptionLocation;
         } else if (e instanceof ServletException) {
            e = ((ServletException)e).getRootCause();
            return this.findExceptionLocation(e);
         } else {
            return this.defaultErrorPage != null ? this.defaultErrorPage : null;
         }
      }
   }

   public boolean useExceptionLocation(Throwable t) {
      return !this.context.getConfigManager().isWebAppSuspending() || !(t instanceof UnavailableException);
   }

   public void handleException(ServletRequestImpl req, ServletResponseImpl rsp, Throwable t) throws IOException {
      if (t instanceof ServletNestedRuntimeException) {
         t = t.getCause();
      }

      String exceptionLocation = this.getExceptionLocation(t);
      if (exceptionLocation != null && this.useExceptionLocation(t)) {
         if (WebAppServletContext.isAbsoluteURL(exceptionLocation)) {
            rsp.sendRedirect(exceptionLocation);
            return;
         }

         rsp.reset();
         RequestDispatcher rd = this.context.getRequestDispatcher(exceptionLocation, 3);
         if (rd != null) {
            rsp.setStatus(500);
            this.setErrorAttributes(req, req.getInputHelper().getNormalizedURI(), t);
            SubjectHandle subject = SecurityModule.getCurrentUser(this.context.getSecurityContext(), req);
            if (subject == null) {
               subject = WebServerRegistry.getInstance().getSecurityProvider().getAnonymousSubject();
            }

            PrivilegedAction forwardAction = new ForwardAction(rd, req, rsp);
            Throwable e = (Throwable)subject.run((PrivilegedAction)forwardAction);
            if (e != null) {
               if (e instanceof IOException) {
                  throw (IOException)e;
               }

               rsp.sendError(500);
               HTTPLogger.logSendError(this.context.getLogContext(), e);
            }

            return;
         }
      }

      short code;
      try {
         throw t;
      } catch (UnavailableException var9) {
         code = 503;
      } catch (ServletException var10) {
         code = 500;
      } catch (SocketTimeoutException var11) {
         code = 408;
      } catch (ThreadDeath var12) {
         throw var12;
      } catch (Throwable var13) {
         code = 500;
      }

      if ((!WebServerRegistry.getInstance().isProductionMode() || HTTPDebugLogger.isEnabled()) && code != -900) {
         Throwable err = (Throwable)req.getAttribute("javax.servlet.error.exception");
         if (err == null) {
            err = t;
         }

         String message = this.throwable2StackTraceWithXSSEncodedMessage(err);
         message = "<pre>" + message + "</pre>";
         message = ErrorMessages.getErrorPage(code, message);
         rsp.sendError(code, message, false);
      } else {
         rsp.sendError(code);
      }

   }

   public void setErrorAttributes(HttpServletRequest req, String request_uri, Throwable t) {
      ServletStubImpl eStub = null;
      String cpath = this.context.getContextPath();
      String elocation;
      if (cpath != null && !this.context.isDefaultContext() && request_uri.startsWith(cpath)) {
         elocation = request_uri.substring(cpath.length());
      } else {
         elocation = request_uri;
      }

      if (elocation != null) {
         eStub = this.context.getServletStub(elocation);
      }

      if (eStub != null) {
         req.setAttribute("javax.servlet.error.servlet_name", eStub.getServletName());
      }

      Throwable st = (Throwable)req.getAttribute("javax.servlet.error.exception");
      if (st != null) {
         t = st;
      }

      req.setAttribute("javax.servlet.error.exception_type", t.getClass());
      req.setAttribute("javax.servlet.error.exception", t);
      req.setAttribute("javax.servlet.error.message", t.getMessage());
      if (req.getAttribute("javax.servlet.error.request_uri") == null) {
         req.setAttribute("javax.servlet.error.request_uri", request_uri);
      }

      if (req.getAttribute("javax.servlet.jsp.jspException") == null) {
         req.setAttribute("javax.servlet.jsp.jspException", t);
      }

      req.setAttribute("javax.servlet.error.status_code", new Integer(500));
   }

   private String throwable2StackTraceWithXSSEncodedMessage(Throwable th) {
      if (th == null) {
         th = new Throwable("[Null exception passed, creating stack trace for offending caller]");
      }

      UnsyncByteArrayOutputStream ostr = new UnsyncByteArrayOutputStream();
      PrintStream s = new PrintStream(ostr);
      s.println(Utils.encodeXSS(th.toString()));
      StackTraceElement[] trace = th.getStackTrace();

      for(int i = 0; i < trace.length; ++i) {
         s.println("\tat " + trace[i]);
      }

      Throwable ourCause = th.getCause();
      if (ourCause != null) {
         this.printStackTraceAsCause(ourCause, s, trace);
      }

      return ostr.toString();
   }

   private void printStackTraceAsCause(Throwable th, PrintStream s, StackTraceElement[] causedTrace) {
      StackTraceElement[] trace = th.getStackTrace();
      int m = trace.length - 1;

      for(int n = causedTrace.length - 1; m >= 0 && n >= 0 && trace[m].equals(causedTrace[n]); --n) {
         --m;
      }

      int framesInCommon = trace.length - 1 - m;
      s.println("Caused by: " + Utils.encodeXSS(th.toString()));

      for(int i = 0; i <= m; ++i) {
         s.println("\tat " + trace[i]);
      }

      if (framesInCommon != 0) {
         s.println("\t... " + framesInCommon + " more");
      }

      Throwable ourCause = th.getCause();
      if (ourCause != null) {
         this.printStackTraceAsCause(ourCause, s, trace);
      }

   }

   private String findExceptionLocation(Throwable t) {
      if (t == null) {
         return null;
      } else {
         String errorLocation = null;

         for(Class clazz = t.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            errorLocation = (String)this.exceptionMap.get(clazz);
            if (errorLocation != null) {
               break;
            }
         }

         return errorLocation;
      }
   }
}
