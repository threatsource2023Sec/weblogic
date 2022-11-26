package weblogic.servlet.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.ManagedBean;
import javax.security.auth.login.LoginException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.application.ApplicationAccess;
import weblogic.application.ApplicationContextInternal;
import weblogic.i18n.logging.Loggable;
import weblogic.managedbean.ManagedBeanCreator;
import weblogic.management.DeploymentException;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.jsp.JspStub;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.t3.srvr.ServerRuntime;

final class StubSecurityHelper {
   private final ServletStubImpl stub;
   private SubjectHandle initAs = null;
   private SubjectHandle destroyAs = null;
   private SubjectHandle runAs = null;
   private String runAsRoleName = null;
   private String runAsIdentity = null;
   private String initAsIdentity = null;
   private String destroyAsIdentity = null;
   private ConcurrentHashMap securityRoleMap;

   StubSecurityHelper(ServletStubImpl stub) {
      this.stub = stub;
   }

   void setRunAsRoleName(String runAsRoleName) {
      this.runAsRoleName = runAsRoleName;
   }

   void setRunAsIdentity(String runAsIdentity) {
      this.runAsIdentity = runAsIdentity;
   }

   void setInitAsIdentity(String initAsIdentity) {
      this.initAsIdentity = initAsIdentity;
   }

   void setDestroyAsIdentity(String destroyAsIdentity) {
      this.destroyAsIdentity = destroyAsIdentity;
   }

   public final void addRoleLink(String roleName, String roleLink) {
      if (this.securityRoleMap == null) {
         this.securityRoleMap = new ConcurrentHashMap();
      }

      this.securityRoleMap.put(roleName, roleLink);
   }

   public final String getRoleLink(String roleName) {
      return this.securityRoleMap == null ? null : (String)this.securityRoleMap.get(roleName);
   }

   final Iterator getRoleNames() {
      return this.securityRoleMap == null ? null : this.securityRoleMap.keySet().iterator();
   }

   public Servlet createServlet(Class c) throws ServletException {
      ServletInitAction action = new ServletInitAction(this.stub, c);
      this.initServletInstance(action);
      return action.getServlet();
   }

   public Servlet createServlet(Servlet servlet) throws ServletException {
      ServletInitAction action = new ServletInitAction(this.stub, servlet);
      this.initServletInstance(action);
      return action.getServlet();
   }

   private void initServletInstance(ServletInitAction action) throws ServletException {
      Throwable e = (Throwable)this.getInitAsSubject().run((PrivilegedAction)action);
      if (e != null) {
         if (e instanceof ServletException) {
            throw (ServletException)e;
         } else {
            throw new ServletException(e);
         }
      }
   }

   private SubjectHandle getInitAsSubject() {
      if (this.initAs != null) {
         return this.initAs;
      } else {
         return this.runAs != null ? this.runAs : WebAppSecurity.getProvider().getAnonymousSubject();
      }
   }

   public void destroyServlet(Servlet theServlet) {
      PrivilegedAction action = new ServletDestroyAction(theServlet, this.stub.getContext());
      Throwable e = (Throwable)this.getDestroyAsSubject().run((PrivilegedAction)action);
      if (e != null) {
         HTTPLogger.logServletFailedOnDestroy(this.stub.getContext().getLogContext(), this.stub.getServletName(), e);
      }

   }

   private SubjectHandle getDestroyAsSubject() {
      if (this.destroyAs != null) {
         return this.destroyAs;
      } else {
         return this.runAs != null ? this.runAs : WebAppSecurity.getProvider().getAnonymousSubject();
      }
   }

   public Throwable invokeServlet(ServletRequest req, HttpServletRequest httpReq, ServletRequestImpl reqi, ServletResponse rsp, HttpServletResponse httpRes, Servlet s) throws ServletException {
      PrivilegedAction action = new ServletServiceAction(req, reqi, rsp, s, this.stub);
      return this.runAs != null ? (Throwable)this.runAs.run((PrivilegedAction)action) : (Throwable)action.run();
   }

   public Object[] invokeAnnotatedMethods(Servlet s, List methods, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      if (methods != null && methods.size() != 0) {
         SubjectHandle subject = (SubjectHandle)AccessController.doPrivileged(new PrivilegedAction() {
            public SubjectHandle run() {
               return WebAppSecurity.getProvider().getCurrentSubject();
            }
         });
         if (subject == null) {
            subject = WebAppSecurity.getProvider().getAnonymousSubject();
         }

         ServletInvokeAnnotatedMethodsAction action = new ServletInvokeAnnotatedMethodsAction(s, methods, args);
         Throwable throwable = (Throwable)subject.run((PrivilegedAction)action);
         if (throwable == null) {
            return action.getResult();
         } else if (throwable instanceof IllegalAccessException) {
            throw (IllegalAccessException)throwable;
         } else if (throwable instanceof IllegalArgumentException) {
            throw (IllegalArgumentException)throwable;
         } else if (throwable instanceof InvocationTargetException) {
            throw (InvocationTargetException)throwable;
         } else if (throwable instanceof RuntimeException) {
            throw (RuntimeException)throwable;
         } else if (throwable instanceof Error) {
            throw (Error)throwable;
         } else {
            throw new RuntimeException(throwable);
         }
      } else {
         return new Object[0];
      }
   }

   void resolveSubjects(WebAppSecurity securityManager) throws DeploymentException {
      this.resolveInitAsSubject(securityManager);
      this.resolveDestroyAsSubject(securityManager);
      this.resolveRunAsSubject(securityManager);
   }

   private void resolveInitAsSubject(WebAppSecurity securityManager) throws DeploymentException {
      if (this.initAsIdentity != null) {
         this.initAs = this.resolveSubject(securityManager, this.initAsIdentity);
         this.checkDeployUserPrivileges(this.initAs, "init-as");
      }
   }

   private void resolveDestroyAsSubject(WebAppSecurity securityManager) throws DeploymentException {
      if (this.destroyAsIdentity != null) {
         this.destroyAs = this.resolveSubject(securityManager, this.destroyAsIdentity);
         this.checkDeployUserPrivileges(this.destroyAs, "destroy-as");
      }
   }

   private void resolveRunAsSubject(WebAppSecurity securityManager) throws DeploymentException {
      if (this.runAsRoleName != null) {
         this.runAsIdentity = securityManager.getRunAsPrincipalName(this.runAsIdentity, this.runAsRoleName);
         if (this.runAsIdentity != null) {
            this.runAs = this.resolveSubject(securityManager, this.runAsIdentity);
            this.checkDeployUserPrivileges(this.runAs, "run-as");
         }
      }
   }

   final String getRunAsRoleName() {
      return this.runAsRoleName;
   }

   final String getRunAsIdentity() {
      return this.runAsIdentity;
   }

   private SubjectHandle resolveSubject(WebAppSecurity securityManager, String identity) throws DeploymentException {
      try {
         return securityManager.getAppSecurityProvider().impersonate(identity, this.stub.getContext().getSecurityRealmName(), (HttpServletRequest)null, (HttpServletResponse)null);
      } catch (LoginException var5) {
         Loggable l = HTTPLogger.logRunAsUserCouldNotBeResolvedLoggable(identity, this.stub.getServletName(), this.stub.getContext().getContextPath(), var5);
         l.log();
         throw new DeploymentException(l.getMessage());
      }
   }

   private void checkDeployUserPrivileges(SubjectHandle subject, String action) throws DeploymentException {
      if (subject != null) {
         ApplicationContextInternal aci = ApplicationAccess.getApplicationAccess().getCurrentApplicationContext();
         SubjectHandle currentSubject = WebServerRegistry.getInstance().getContainerSupportProvider().getDeploymentInitiator(this.stub.getContext());
         if (currentSubject != null && (ServerRuntime.theOne().getStateVal() != 1 || !currentSubject.isAnonymous()) && WebAppSecurity.getProvider().isAdminPrivilegeEscalation(currentSubject, subject)) {
            throw new DeploymentException("The " + action + " user : " + subject + " has higher privileges than the deployment user : " + currentSubject + ". Hence this deployment user cannot perform the current deployment action. Try the deployment action with admin privileged user.");
         }
      }

   }

   private static final class ServletInvokeAnnotatedMethodsAction implements PrivilegedAction {
      private Servlet s;
      private List methods;
      private Object[] args;
      private Object[] result;

      public ServletInvokeAnnotatedMethodsAction(Servlet s, List methods, Object[] args) {
         this.s = s;
         this.methods = methods;
         this.args = args;
      }

      public Throwable run() {
         this.result = new Object[this.methods.size()];

         for(int i = 0; i < this.methods.size(); ++i) {
            try {
               this.result[i] = ((Method)this.methods.get(i)).invoke(this.s, this.args);
            } catch (Throwable var3) {
               return var3;
            }
         }

         return null;
      }

      public Object[] getResult() {
         return this.result;
      }
   }

   private static final class ServletDestroyAction implements PrivilegedAction {
      final Servlet servlet;
      final WebAppServletContext context;

      ServletDestroyAction(Servlet s, WebAppServletContext c) {
         this.servlet = s;
         this.context = c;
      }

      public Throwable run() {
         try {
            this.servlet.destroy();
         } catch (Throwable var3) {
            return var3;
         }

         try {
            this.context.getComponentCreator().notifyPreDestroy(this.servlet);
            return null;
         } catch (Throwable var2) {
            return var2;
         }
      }
   }

   private static final class ServletInitAction implements PrivilegedAction {
      private final ServletStubImpl stub;
      private final Class clazz;
      private Servlet servlet = null;

      public ServletInitAction(ServletStubImpl stub, Class clazz) {
         this.stub = stub;
         this.clazz = clazz;
         this.servlet = null;
      }

      public ServletInitAction(ServletStubImpl stub, Servlet servlet) {
         this.stub = stub;
         this.clazz = null;
         this.servlet = servlet;
      }

      public Servlet getServlet() {
         return this.servlet;
      }

      public Throwable run() {
         try {
            this.newServletInstanceIfNecessary();
         } catch (NoSuchMethodError var3) {
            HTTPLogger.logInstantiateError(this.stub.getContext().getLogContext(), this.stub.getServletName(), var3);
            return new ServletException("Servlet class: '" + this.stub.getClassName() + "' doesn't have a default constructor");
         } catch (InstantiationException var4) {
            HTTPLogger.logInstantiateError(this.stub.getContext().getLogContext(), this.stub.getServletName(), var4);
            return new ServletException("Servlet class: '" + this.stub.getClassName() + "' couldn't be instantiated");
         } catch (IllegalAccessException var5) {
            HTTPLogger.logIllegalAccessOnInstantiate(this.stub.getContext().getLogContext(), this.stub.getServletName(), var5);
            return new ServletException("Servlet class: '" + this.stub.getClassName() + "' couldn't be instantiated");
         } catch (ClassCastException var6) {
            HTTPLogger.logCastingError(this.stub.getContext().getLogContext(), this.stub.getServletName(), var6);
            return new ServletException("Servlet class: '" + this.stub.getClassName() + "' does not implement javax.servlet.Servlet");
         } catch (Throwable var7) {
            return var7;
         }

         try {
            this.servlet.init(this.stub);
            return null;
         } catch (Throwable var2) {
            return var2;
         }
      }

      private void newServletInstanceIfNecessary() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
         if (this.servlet == null) {
            if (this.stub instanceof JspStub) {
               this.servlet = (Servlet)this.clazz.newInstance();
            } else {
               ManagedBeanCreator creator = this.stub.getContext().getManagedBeanCreator();
               if (this.clazz != null && this.clazz.isAnnotationPresent(ManagedBean.class) && creator != null) {
                  this.servlet = (Servlet)creator.createInstance(this.clazz);
                  creator.notifyPostConstruct(this.clazz.getName(), this.servlet);
               } else {
                  this.servlet = this.stub.getContext().getComponentCreator().createServletInstance(this.stub.getClassName());
               }
            }

         }
      }
   }

   private static final class ServletServiceAction implements PrivilegedAction {
      private final ServletRequest req;
      private final ServletRequestImpl reqi;
      private final ServletResponse rsp;
      private final Servlet servlet;
      private final ServletStubImpl stub;

      ServletServiceAction(ServletRequest rq, ServletRequestImpl rqi, ServletResponse rs, Servlet s, ServletStubImpl stub) {
         this.req = rq;
         this.reqi = rqi;
         this.rsp = rs;
         this.servlet = s;
         this.stub = stub;
      }

      public Throwable run() {
         Thread thread = null;
         ClassLoader oldClassLoader = null;
         if (Thread.currentThread().getContextClassLoader() != this.stub.getClassLoader()) {
            thread = Thread.currentThread();
            oldClassLoader = this.reqi.getContext().pushEnvironment(thread);
         }

         Throwable var4;
         try {
            if (this.stub == this.reqi.getServletStub() && this.stub.isFutureResponseServlet()) {
               this.reqi.enableFutureResponse();
            }

            this.reqi.setAsyncSupported(this.stub.isAsyncSupported());
            this.servlet.service(this.req, this.rsp);
            return null;
         } catch (Throwable var8) {
            var4 = var8;
         } finally {
            if (thread != null && oldClassLoader != null) {
               WebAppServletContext.popEnvironment(thread, oldClassLoader);
            }

         }

         return var4;
      }
   }
}
