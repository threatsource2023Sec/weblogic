package weblogic.connector.security.layer;

import com.oracle.injection.BeanManager;
import com.oracle.injection.InjectionContainer;
import com.oracle.injection.InjectionDeployment;
import com.oracle.injection.InjectionException;
import com.oracle.injection.integration.utils.InjectionBeanCreator;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.resource.ResourceException;
import javax.resource.spi.ActivationSpec;
import javax.resource.spi.BootstrapContext;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.DissociatableManagedConnection;
import javax.resource.spi.EISSystemException;
import javax.resource.spi.InvalidPropertyException;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.LocalTransactionException;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;
import javax.resource.spi.ResourceAdapterInternalException;
import javax.resource.spi.ValidatingManagedConnectionFactory;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginException;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.naming.ModuleRegistry;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.common.Utils;
import weblogic.connector.exception.RACommonException;
import weblogic.connector.exception.RAException;
import weblogic.connector.exception.RAInternalException;
import weblogic.connector.extensions.Suspendable;
import weblogic.connector.external.RAInfo;
import weblogic.connector.external.SuspendableEndpointFactory;
import weblogic.connector.security.SecurityHelper;
import weblogic.connector.security.SubjectStack;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.SubjectUtils;
import weblogic.security.WLSPrincipals;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.t3.srvr.ServerRuntime;
import weblogic.utils.StackTraceUtils;

public class AdapterLayer implements SubjectStack {
   private RAInstanceManager raIM;
   private static AuthenticatedSubject anonymousSubject = null;
   private boolean useCallerForRunAs;
   private AuthenticatedSubject runAsSubject;
   private AuthenticatedSubject manageAsSubject;
   private boolean useCallerForWorkAs;
   private AuthenticatedSubject runWorkAsSubject;
   private SecurityHelper securityHelper;
   private InjectionBeanCreator injectionBeanCreator;
   private AuthenticatedSubject kernelId;
   private ServerRuntimeMBean serverRuntime;

   public AdapterLayer(RAInstanceManager raIM, SecurityHelper securityHelper, AuthenticatedSubject kernelId) throws RAException {
      this(raIM, securityHelper, kernelId, ServerRuntime.theOne());
   }

   public AdapterLayer(RAInstanceManager raIM, SecurityHelper securityHelper, AuthenticatedSubject kernelId, ServerRuntimeMBean serverRuntime) throws RAException {
      this.raIM = null;
      this.useCallerForRunAs = false;
      this.runAsSubject = null;
      this.manageAsSubject = null;
      this.useCallerForWorkAs = false;
      this.runWorkAsSubject = null;
      this.raIM = raIM;
      this.securityHelper = securityHelper;
      this.kernelId = kernelId;
      this.serverRuntime = serverRuntime;
      this.initializeSecurityIdentities(kernelId);
      if (raIM != null) {
         ApplicationContextInternal appCtx = raIM.getAppContext();
         if (appCtx != null) {
            InjectionContainer injectionContainer = this.getInjectionContainer();
            if (injectionContainer != null) {
               this.injectionBeanCreator = new InjectionBeanCreator(injectionContainer, raIM.getModuleName());
            }
         }
      }

   }

   private InjectionContainer getInjectionContainer() {
      ModuleContext moduleContext = this.raIM.getAppContext().getModuleContext(this.raIM.getModuleName());
      ModuleRegistry moduleRegistry = moduleContext.getRegistry();
      return (InjectionContainer)moduleRegistry.get(InjectionContainer.class.getName());
   }

   public void setInjectionBeanCreator(InjectionBeanCreator injectionBeanCreator) {
      this.injectionBeanCreator = injectionBeanCreator;
   }

   protected InjectionBeanCreator getInjectionBeanCreator() {
      return this.injectionBeanCreator;
   }

   public void validate(ActivationSpec as, AuthenticatedSubject kernelId) throws InvalidPropertyException {
      this.pushSubject(kernelId);

      try {
         as.validate();
      } finally {
         this.popSubject(kernelId);
      }

   }

   public Object getConnectionHandle(ConnectionEvent ev, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      Object var3;
      try {
         var3 = ev.getConnectionHandle();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public Exception getException(ConnectionEvent ev, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      Exception var3;
      try {
         var3 = ev.getException();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public int getId(ConnectionEvent ev, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      int var3;
      try {
         var3 = ev.getId();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public void setConnectionHandle(ConnectionEvent ev, Object handle, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      try {
         ev.setConnectionHandle(handle);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void dissociateConnections(DissociatableManagedConnection dissociatableMC, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         dissociatableMC.dissociateConnections();
      } finally {
         this.popSubject(kernelId);
      }

   }

   private void xaDebug(String op, Xid xid, Integer flags, Boolean onePhase, Throwable t) {
      if (Debug.isXAoutEnabled()) {
         boolean isXaEx = t != null && t instanceof XAException;
         int xaErrorCode = 0;
         String xaErrString = null;
         if (isXaEx) {
            xaErrorCode = ((XAException)t).errorCode;
            xaErrString = xaErrorCodeToString(xaErrorCode, false);
         }

         Debug.xaOut("RA with JNDI name = " + this.raIM.getJndiName() + (t != null ? " threw exception" : "") + " calling " + op + (xid != null ? " Xid = " + xid : "") + (flags != null ? " flags = " + flags : "") + (onePhase != null ? " onePhase = " + onePhase : "") + (isXaEx ? " xaErrorCode = " + xaErrorCode : "") + (xaErrString != null ? " xaErrorString = " + xaErrString : "") + (t != null ? " throwable = " + t + " message = " + t.getMessage() + "\nStack trace = \n" + StackTraceUtils.throwable2StackTrace(t) : ""));
      }

   }

   public void commit(XAResource xares, Xid xid, boolean onePhase, AuthenticatedSubject kernelId) throws XAException {
      this.pushSubject(kernelId);

      try {
         if (Debug.isXAoutEnabled()) {
            this.xaDebug("commit", xid, (Integer)null, onePhase, (Throwable)null);
         }

         xares.commit(xid, onePhase);
      } catch (XAException var10) {
         this.xaDebug("commit", xid, (Integer)null, onePhase, var10);
         throw var10;
      } catch (Throwable var11) {
         this.xaDebug("commit", xid, (Integer)null, onePhase, var11);
         throw (XAException)(new XAException(-3)).initCause(var11);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void end(XAResource xares, Xid xid, int flags, AuthenticatedSubject kernelId) throws XAException {
      this.pushSubject(kernelId);

      try {
         if (Debug.isXAoutEnabled()) {
            this.xaDebug("end", xid, flags, (Boolean)null, (Throwable)null);
         }

         xares.end(xid, flags);
      } catch (XAException var10) {
         this.xaDebug("end", xid, flags, (Boolean)null, var10);
         throw var10;
      } catch (Throwable var11) {
         this.xaDebug("end", xid, flags, (Boolean)null, var11);
         throw (XAException)(new XAException(-3)).initCause(var11);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void forget(XAResource xares, Xid xid, AuthenticatedSubject kernelId) throws XAException {
      this.pushSubject(kernelId);

      try {
         if (Debug.isXAoutEnabled()) {
            this.xaDebug("forget", xid, (Integer)null, (Boolean)null, (Throwable)null);
         }

         xares.forget(xid);
      } catch (XAException var9) {
         this.xaDebug("forget", xid, (Integer)null, (Boolean)null, var9);
         throw var9;
      } catch (Throwable var10) {
         this.xaDebug("forget", xid, (Integer)null, (Boolean)null, var10);
         throw (XAException)(new XAException(-3)).initCause(var10);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public int getTransactionTimeout(XAResource xares, AuthenticatedSubject kernelId) throws XAException {
      this.pushSubject(kernelId);

      int var3;
      try {
         if (Debug.isXAoutEnabled()) {
            this.xaDebug("getTransactionTimeout", (Xid)null, (Integer)null, (Boolean)null, (Throwable)null);
         }

         var3 = xares.getTransactionTimeout();
      } catch (XAException var8) {
         this.xaDebug("getTransactionTimeout", (Xid)null, (Integer)null, (Boolean)null, var8);
         throw var8;
      } catch (Throwable var9) {
         this.xaDebug("getTransactionTimeout", (Xid)null, (Integer)null, (Boolean)null, var9);
         throw (XAException)(new XAException(-3)).initCause(var9);
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public boolean isSameRM(XAResource xares1, XAResource xares2, AuthenticatedSubject kernelId) throws XAException {
      this.pushSubject(kernelId);

      boolean var4;
      try {
         if (Debug.isXAoutEnabled()) {
            this.xaDebug("isSameRM", (Xid)null, (Integer)null, (Boolean)null, (Throwable)null);
         }

         var4 = xares1.isSameRM(xares2);
      } catch (XAException var9) {
         this.xaDebug("isSameRM", (Xid)null, (Integer)null, (Boolean)null, var9);
         throw var9;
      } catch (Throwable var10) {
         this.xaDebug("isSameRM", (Xid)null, (Integer)null, (Boolean)null, var10);
         throw (XAException)(new XAException(-3)).initCause(var10);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public int prepare(XAResource xares, Xid xid, AuthenticatedSubject kernelId) throws XAException {
      this.pushSubject(kernelId);

      int var4;
      try {
         if (Debug.isXAoutEnabled()) {
            this.xaDebug("prepare", xid, (Integer)null, (Boolean)null, (Throwable)null);
         }

         var4 = xares.prepare(xid);
      } catch (XAException var9) {
         this.xaDebug("prepare", xid, (Integer)null, (Boolean)null, var9);
         throw var9;
      } catch (Throwable var10) {
         this.xaDebug("prepare", xid, (Integer)null, (Boolean)null, var10);
         throw (XAException)(new XAException(-3)).initCause(var10);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public Xid[] recover(XAResource xares, int flag, AuthenticatedSubject kernelId) throws XAException {
      this.pushSubject(kernelId);

      Xid[] var4;
      try {
         if (Debug.isXAoutEnabled()) {
            this.xaDebug("recover", (Xid)null, flag, (Boolean)null, (Throwable)null);
         }

         var4 = xares.recover(flag);
      } catch (XAException var9) {
         this.xaDebug("recover", (Xid)null, flag, (Boolean)null, var9);
         throw var9;
      } catch (Throwable var10) {
         this.xaDebug("recover", (Xid)null, flag, (Boolean)null, var10);
         throw (XAException)(new XAException(-3)).initCause(var10);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public void rollback(XAResource xares, Xid xid, AuthenticatedSubject kernelId) throws XAException {
      this.pushSubject(kernelId);

      try {
         if (Debug.isXAoutEnabled()) {
            this.xaDebug("rollback", xid, (Integer)null, (Boolean)null, (Throwable)null);
         }

         xares.rollback(xid);
      } catch (XAException var9) {
         this.xaDebug("rollback", xid, (Integer)null, (Boolean)null, var9);
         throw var9;
      } catch (Throwable var10) {
         this.xaDebug("rollback", xid, (Integer)null, (Boolean)null, var10);
         throw (XAException)(new XAException(-3)).initCause(var10);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public boolean setTransactionTimeout(XAResource xares, int seconds, AuthenticatedSubject kernelId) throws XAException {
      this.pushSubject(kernelId);

      boolean var4;
      try {
         if (Debug.isXAoutEnabled()) {
            this.xaDebug("setTransactionTimeout", (Xid)null, (Integer)null, (Boolean)null, (Throwable)null);
         }

         var4 = xares.setTransactionTimeout(seconds);
      } catch (XAException var9) {
         this.xaDebug("setTransactionTimeout", (Xid)null, (Integer)null, (Boolean)null, var9);
         throw var9;
      } catch (Throwable var10) {
         this.xaDebug("setTransactionTimeout", (Xid)null, (Integer)null, (Boolean)null, var10);
         throw (XAException)(new XAException(-3)).initCause(var10);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public void start(XAResource xares, Xid xid, int flags, AuthenticatedSubject kernelId) throws XAException {
      this.pushSubject(kernelId);

      try {
         if (Debug.isXAoutEnabled()) {
            this.xaDebug("start", xid, flags, (Boolean)null, (Throwable)null);
         }

         xares.start(xid, flags);
      } catch (XAException var10) {
         this.xaDebug("start", xid, flags, (Boolean)null, var10);
         throw var10;
      } catch (Throwable var11) {
         this.xaDebug("start", xid, flags, (Boolean)null, var11);
         throw (XAException)(new XAException(-3)).initCause(var11);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void begin(LocalTransaction localTx, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         localTx.begin();
      } catch (LocalTransactionException var11) {
      } catch (ResourceAdapterInternalException var12) {
      } catch (EISSystemException var13) {
      } catch (ResourceException var14) {
      } catch (Throwable var15) {
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void commit(LocalTransaction localTx, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         localTx.commit();
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void rollback(LocalTransaction localTx, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         localTx.rollback();
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void addConnectionEventListener(ManagedConnection mc, ConnectionEventListener listener, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      try {
         mc.addConnectionEventListener(listener);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void associateConnection(ManagedConnection mc, Object connHandle, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         mc.associateConnection(connHandle);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void cleanup(ManagedConnection mc, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         mc.cleanup();
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void destroy(ManagedConnection mc, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         mc.destroy();
      } finally {
         this.popSubject(kernelId);
      }

   }

   public Object getConnection(ManagedConnection mc, Subject subject, ConnectionRequestInfo connReqInfo, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      Object var5;
      try {
         var5 = mc.getConnection(subject, connReqInfo);
      } finally {
         this.popSubject(kernelId);
      }

      return var5;
   }

   public LocalTransaction getLocalTransaction(ManagedConnection mc, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      LocalTransaction var3;
      try {
         var3 = mc.getLocalTransaction();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public PrintWriter getLogWriter(ManagedConnection mc, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      PrintWriter var3;
      try {
         var3 = mc.getLogWriter();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public ManagedConnectionMetaData getMetaData(ManagedConnection mc, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      ManagedConnectionMetaData var3;
      try {
         var3 = mc.getMetaData();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public XAResource getXAResource(ManagedConnection mc, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      XAResource var3;
      try {
         var3 = mc.getXAResource();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public void removeConnectionEventListener(ManagedConnection mc, ConnectionEventListener listener, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      try {
         mc.removeConnectionEventListener(listener);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void setLogWriter(ManagedConnection mc, PrintWriter pw, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         mc.setLogWriter(pw);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public Object createConnectionFactory(ManagedConnectionFactory mcf, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      Object var3;
      try {
         var3 = mcf.createConnectionFactory();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public Object createConnectionFactory(ManagedConnectionFactory mcf, ConnectionManager cm, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      Object var4;
      try {
         var4 = mcf.createConnectionFactory(cm);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public ManagedConnection createManagedConnection(ManagedConnectionFactory mcf, Subject subject, ConnectionRequestInfo connReqInfo, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      ManagedConnection var5;
      try {
         var5 = mcf.createManagedConnection(subject, connReqInfo);
      } finally {
         this.popSubject(kernelId);
      }

      return var5;
   }

   public PrintWriter getLogWriter(ManagedConnectionFactory mcf, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      PrintWriter var3;
      try {
         var3 = mcf.getLogWriter();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public ManagedConnection matchManagedConnection(ManagedConnectionFactory mcf, Set connectionSet, Subject subject, ConnectionRequestInfo connReqInfo, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      ManagedConnection var6;
      try {
         var6 = mcf.matchManagedConnections(connectionSet, subject, connReqInfo);
      } finally {
         this.popSubject(kernelId);
      }

      return var6;
   }

   public void setLogWriter(ManagedConnectionFactory mcf, PrintWriter pw, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         mcf.setLogWriter(pw);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public String getEISProductName(ManagedConnectionMetaData md, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      String var3;
      try {
         var3 = md.getEISProductName();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public String getEISProductVersion(ManagedConnectionMetaData md, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      String var3;
      try {
         var3 = md.getEISProductVersion();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public int getMaxConnections(ManagedConnectionMetaData md, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      int var3;
      try {
         var3 = md.getMaxConnections();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public String getUserName(ManagedConnectionMetaData md, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      String var3;
      try {
         var3 = md.getUserName();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public void endpointActivation(ResourceAdapter ra, MessageEndpointFactory endpointFactory, ActivationSpec spec, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         ra.endpointActivation(endpointFactory, spec);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void endpointDeactivation(ResourceAdapter ra, MessageEndpointFactory endpointFactory, ActivationSpec spec, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      try {
         ra.endpointDeactivation(endpointFactory, spec);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public XAResource[] getXAResources(ResourceAdapter ra, ActivationSpec[] specs, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      XAResource[] var4;
      try {
         var4 = ra.getXAResources(specs);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public void start(ResourceAdapter ra, BootstrapContext bsCtx, AuthenticatedSubject kernelId) throws ResourceAdapterInternalException {
      this.pushSubject(kernelId);

      try {
         ra.start(bsCtx);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void stop(ResourceAdapter ra, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      try {
         ra.stop();
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void init(Suspendable suspendable, ResourceAdapter ra, Properties props, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         suspendable.init(ra, props);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public boolean isSuspended(Suspendable suspendable, int type, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      boolean var4;
      try {
         var4 = suspendable.isSuspended(type);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public void resume(Suspendable suspendable, int type, Properties props, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         suspendable.resume(type, props);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void startVersioning(Suspendable suspendable, ResourceAdapter ra, Properties props, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         suspendable.startVersioning(ra, props);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public boolean supportsInit(Suspendable suspendable, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      boolean var3;
      try {
         var3 = suspendable.supportsInit();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public boolean supportsSuspend(Suspendable suspendable, int type, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      boolean var4;
      try {
         var4 = suspendable.supportsSuspend(type);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public boolean supportsVersioning(Suspendable suspendable, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      boolean var3;
      try {
         var3 = suspendable.supportsVersioning();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public void suspend(Suspendable suspendable, int type, Properties props, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         suspendable.suspend(type, props);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void suspendInbound(Suspendable suspendable, MessageEndpointFactory endpointFactory, Properties props, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         if (suspendable.supportsSuspend(1)) {
            suspendable.suspendInbound(endpointFactory, props);
         }
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void resumeInbound(Suspendable suspendable, MessageEndpointFactory endpointFactory, Properties props, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         if (suspendable.supportsSuspend(1)) {
            suspendable.resumeInbound(endpointFactory, props);
         }
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void suspend(SuspendableEndpointFactory suspEF, Properties props, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         suspEF.suspend(props);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void disconnect(SuspendableEndpointFactory suspEF, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         suspEF.disconnect();
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void resume(SuspendableEndpointFactory suspEF, Properties props, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         suspEF.resume(props);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public boolean isSuspended(SuspendableEndpointFactory suspEF, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      boolean var3;
      try {
         var3 = suspEF.isSuspended();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public ResourceAdapter getResourceAdapter(ResourceAdapterAssociation raAssoc, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      ResourceAdapter var3;
      try {
         var3 = raAssoc.getResourceAdapter();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public void setResourceAdapter(ResourceAdapterAssociation raAssoc, ResourceAdapter ra, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      try {
         raAssoc.setResourceAdapter(ra);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public Set getInvalidConnections(ValidatingManagedConnectionFactory validatingMCF, Set connectionSet, AuthenticatedSubject kernelId) throws ResourceException {
      this.pushSubject(kernelId);

      Set var4;
      try {
         var4 = validatingMCF.getInvalidConnections(connectionSet);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public boolean equals(Object obj, Object other, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      boolean var4;
      try {
         var4 = obj.equals(other);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public int hashCode(Object me, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      int var3;
      try {
         var3 = me.hashCode();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public String toString(Object obj, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      String var3;
      try {
         if (obj == null) {
            var3 = "";
            return var3;
         }

         var3 = obj.toString();
      } catch (Throwable var8) {
         String var4 = "null due to " + var8.toString();
         return var4;
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public Object invoke(Method method, Object obj, Object[] args, AuthenticatedSubject kernelId) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
      this.pushSubject(kernelId);

      Object var5;
      try {
         var5 = method.invoke(obj, args);
      } finally {
         this.popSubject(kernelId);
      }

      return var5;
   }

   public Object newInstance(Class cls, AuthenticatedSubject kernelId) throws InstantiationException, IllegalAccessException, RAInternalException {
      this.pushSubject(kernelId);

      Object var3;
      try {
         var3 = cls.newInstance();
      } catch (InstantiationException var10) {
         throw var10;
      } catch (IllegalAccessException var11) {
         throw var11;
      } catch (Throwable var12) {
         String exMsg = Debug.getExceptionRANewInstanceFailed(cls.getName(), "");
         throw new RAInternalException(exMsg, var12);
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public Class forName(String classname, boolean initialize, ClassLoader cl, AuthenticatedSubject kernelId) throws ClassNotFoundException {
      this.pushSubject(kernelId);

      Class var5;
      try {
         var5 = Class.forName(classname, initialize, cl);
      } finally {
         this.popSubject(kernelId);
      }

      return var5;
   }

   public Object forNameNewInstance(String classname, boolean initialize, ClassLoader classLoader, AuthenticatedSubject kernelId) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
      this.pushSubject(kernelId);
      Class cls = null;

      Object var6;
      try {
         cls = Class.forName(classname, initialize, classLoader);
         var6 = cls.newInstance();
      } finally {
         this.popSubject(kernelId);
      }

      return var6;
   }

   public Class loadClass(ClassLoader clsLoader, String name, AuthenticatedSubject kernelId) throws ClassNotFoundException {
      this.pushSubject(kernelId);

      Class var4;
      try {
         var4 = clsLoader.loadClass(name);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public Object createInstance(String className, boolean initialize, final ClassLoader classLoader, AuthenticatedSubject kernelId) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
      if (this.injectionBeanCreator == null) {
         return this.forNameNewInstance(className, initialize, classLoader, kernelId);
      } else {
         this.pushSubject(kernelId);
         final ClassLoader curContextClassLoader = (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               ClassLoader cl = Thread.currentThread().getContextClassLoader();
               Thread.currentThread().setContextClassLoader(classLoader);
               return cl;
            }
         });

         Object var6;
         try {
            var6 = this.injectionBeanCreator.newBeanInstance(className, false);
         } finally {
            AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  Thread.currentThread().setContextClassLoader(curContextClassLoader);
                  return null;
               }
            });
            this.popSubject(kernelId);
         }

         return var6;
      }
   }

   public Object getReference(String className, boolean initialize, final ClassLoader classLoader, AuthenticatedSubject kernelId) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
      if (this.injectionBeanCreator == null) {
         return this.forNameNewInstance(className, initialize, classLoader, kernelId);
      } else {
         this.pushSubject(kernelId);
         final ClassLoader curContextClassLoader = (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               ClassLoader cl = Thread.currentThread().getContextClassLoader();
               Thread.currentThread().setContextClassLoader(classLoader);
               return cl;
            }
         });

         Object var6;
         try {
            var6 = this.injectionBeanCreator.getReference(className);
         } finally {
            AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  Thread.currentThread().setContextClassLoader(curContextClassLoader);
                  return null;
               }
            });
            this.popSubject(kernelId);
         }

         return var6;
      }
   }

   public void invokePostConstruct(Object bean) throws InjectionException {
      this.pushSubject(this.kernelId);

      try {
         BeanManager beanManager = this.getBeanManagerFromInjectionBeanCreator();
         if (beanManager != null) {
            beanManager.invokePostConstruct(bean);
         }
      } finally {
         this.popSubject(this.kernelId);
      }

   }

   public void invokePreDestroy(Object bean, String info) {
      this.pushSubject(this.kernelId);

      try {
         if (bean != null) {
            BeanManager beanManager = this.getBeanManagerFromInjectionBeanCreator();
            if (beanManager != null) {
               beanManager.invokePreDestroy(bean);
               beanManager.destroyBean(bean);
            }
         }
      } catch (InjectionException var7) {
         ConnectorLogger.loginvokePreDestroy(info, bean, var7);
      } finally {
         this.popSubject(this.kernelId);
      }

   }

   private BeanManager getBeanManagerFromInjectionBeanCreator() {
      if (this.injectionBeanCreator != null) {
         InjectionContainer injectionContainer = this.injectionBeanCreator.getInjectionContainer();
         if (injectionContainer != null) {
            InjectionDeployment injectionDeployment = injectionContainer.getDeployment();
            if (injectionDeployment != null) {
               return injectionDeployment.getBeanManager(this.raIM.getModuleName());
            }
         }
      }

      return null;
   }

   public Object htClone(Hashtable ht, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      Object var3;
      try {
         var3 = ht.clone();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public boolean htContains(Hashtable ht, Object value, AuthenticatedSubject kernelId) throws NullPointerException {
      this.pushSubject(kernelId);

      boolean var4;
      try {
         var4 = ht.contains(value);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public boolean htContainsKey(Hashtable ht, Object key, AuthenticatedSubject kernelId) throws NullPointerException {
      this.pushSubject(kernelId);

      boolean var4;
      try {
         var4 = ht.containsKey(key);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public boolean htContainsValue(Hashtable ht, Object value, AuthenticatedSubject kernelId) throws NullPointerException {
      this.pushSubject(kernelId);

      boolean var4;
      try {
         var4 = ht.containsValue(value);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public Object htGet(Hashtable ht, Object key, AuthenticatedSubject kernelId) throws NullPointerException {
      this.pushSubject(kernelId);

      Object var4;
      try {
         var4 = ht.get(key);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public Object htPut(Hashtable ht, Object key, Object value, AuthenticatedSubject kernelId) throws NullPointerException {
      this.pushSubject(kernelId);

      Object var5;
      try {
         var5 = ht.put(key, value);
      } finally {
         this.popSubject(kernelId);
      }

      return var5;
   }

   public void htPutAll(Hashtable ht, Map map, AuthenticatedSubject kernelId) throws NullPointerException {
      this.pushSubject(kernelId);

      try {
         ht.putAll(map);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public Object htRemove(Hashtable ht, Object key, AuthenticatedSubject kernelId) throws NullPointerException {
      this.pushSubject(kernelId);

      Object var4;
      try {
         var4 = ht.remove(key);
      } finally {
         this.popSubject(kernelId);
      }

      return var4;
   }

   public Throwable fillInStackTrace(Throwable throwable, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      Throwable var3;
      try {
         var3 = throwable.fillInStackTrace();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public Throwable getCause(Throwable throwable, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      Throwable var3;
      try {
         var3 = throwable.getCause();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public String getLocalizedMessage(Throwable throwable, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      String var3;
      try {
         var3 = throwable.getLocalizedMessage();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public String getMessage(Throwable throwable, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      String var3;
      try {
         var3 = throwable.getMessage();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public StackTraceElement[] getStackTrace(Throwable throwable, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      StackTraceElement[] var3;
      try {
         var3 = throwable.getStackTrace();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public void initCause(Throwable throwable, Throwable cause, AuthenticatedSubject kernelId) throws IllegalArgumentException, IllegalStateException {
      this.pushSubject(kernelId);

      try {
         throwable.initCause(cause);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void printStackTrace(Throwable throwable, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      try {
         throwable.printStackTrace();
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void printStackTrace(Throwable throwable, PrintStream ps, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      try {
         throwable.printStackTrace(ps);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void printStackTrace(Throwable throwable, PrintWriter pw, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      try {
         throwable.printStackTrace(pw);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void setStackTrace(Throwable throwable, StackTraceElement[] stackTrace, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      try {
         throwable.setStackTrace(stackTrace);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public String getErrorCode(ResourceException resEx, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      String var3;
      try {
         var3 = resEx.getErrorCode();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public Exception getLinkedException(ResourceException resEx, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      Exception var3;
      try {
         var3 = resEx.getLinkedException();
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public void setErrorCode(ResourceException resEx, String errorCode, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      try {
         resEx.setErrorCode(errorCode);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public void setLinkedException(ResourceException resEx, Exception ex, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      try {
         resEx.setLinkedException(ex);
      } finally {
         this.popSubject(kernelId);
      }

   }

   public String throwable2StackTrace(Throwable throwable, AuthenticatedSubject kernelId) {
      this.pushSubject(kernelId);

      String var3;
      try {
         var3 = StackTraceUtils.throwable2StackTrace(throwable);
      } finally {
         this.popSubject(kernelId);
      }

      return var3;
   }

   public void pushSubject(AuthenticatedSubject kernelId) {
      AuthenticatedSubject subject = null;
      if (isManaging()) {
         subject = this.manageAsSubject;
      } else if (this.useCallerForRunAs) {
         subject = this.securityHelper.getCurrentSubject(kernelId);
         String subjectUserName = SubjectUtils.getUsername(subject);
         if (WLSPrincipals.isKernelUsername(subjectUserName)) {
            subject = SubjectUtils.getAnonymousSubject();
         }
      } else {
         subject = this.runAsSubject;
      }

      if (subject == null) {
         subject = this.getAnonymousSubject(kernelId);
      }

      this.securityHelper.pushSubject(kernelId, subject);
   }

   public void pushWorkSubject(AuthenticatedSubject kernelId, AuthenticatedSubject callerSubject) {
      AuthenticatedSubject subject = null;
      if (this.useCallerForWorkAs) {
         subject = callerSubject;
      } else {
         subject = this.runWorkAsSubject;
      }

      if (subject == null) {
         subject = this.getAnonymousSubject(kernelId);
      }

      this.pushGivenSubject(kernelId, subject);
   }

   public void pushGivenSubject(AuthenticatedSubject kernelId, AuthenticatedSubject subject) {
      this.securityHelper.pushSubject(kernelId, subject);
   }

   public void popSubject(AuthenticatedSubject kernelId) {
      this.securityHelper.popSubject(kernelId);
   }

   private AuthenticatedSubject getAnonymousSubject(AuthenticatedSubject kernelId) {
      if (anonymousSubject == null) {
         try {
            anonymousSubject = this.getAuthenticatedSubject((String)null, kernelId, "<use-anonymous-identity>");
         } catch (RACommonException var4) {
            String msgId = Debug.logGetAnonymousSubjectFailed();
            Debug.logStackTrace(msgId, var4);
         }
      }

      return anonymousSubject;
   }

   private static boolean isManaging() {
      return Utils.getManagementCount() > 0;
   }

   private void initializeSecurityIdentities(AuthenticatedSubject kernelId) throws RAException {
      RAInfo raInfo = this.raIM.getRAInfo();
      String defaultPrincipalName = null;
      String runAsPrincipalName = null;
      String runWorkAsPrincipalName = null;
      String manageAsPrincipalName = null;
      AuthenticatedSubject defaultSubject = null;
      if (raInfo.getSecurityIdentityInfo() != null) {
         defaultPrincipalName = raInfo.getSecurityIdentityInfo().getDefaultPrincipalName();
         this.useCallerForRunAs = raInfo.getSecurityIdentityInfo().useCallerForRunAs();
         this.useCallerForWorkAs = raInfo.getSecurityIdentityInfo().useCallerForRunWorkAs();
         runAsPrincipalName = raInfo.getSecurityIdentityInfo().getRunAsPrincipalName();
         runWorkAsPrincipalName = raInfo.getSecurityIdentityInfo().getRunWorkAsPrincipalName();
         manageAsPrincipalName = raInfo.getSecurityIdentityInfo().getManageAsPrincipalName();
      } else {
         this.useCallerForRunAs = true;
         this.useCallerForWorkAs = true;
      }

      defaultSubject = this.getAuthenticatedSubject(defaultPrincipalName, kernelId, "<default-principal-name>");
      this.checkDeployUserPrivileges(defaultSubject, "<default-principal-name>", kernelId);
      if (!this.useCallerForRunAs) {
         this.runAsSubject = this.getAuthenticatedSubject(runAsPrincipalName, kernelId, "<run-as-principal-name>");
         this.checkDeployUserPrivileges(this.runAsSubject, "<run-as-principal-name>", kernelId);
      }

      if (!this.useCallerForWorkAs) {
         this.runWorkAsSubject = this.getAuthenticatedSubject(runWorkAsPrincipalName, kernelId, "<run-work-as-principal-name>");
         this.checkDeployUserPrivileges(this.runWorkAsSubject, "<run-work-as-principal-name>", kernelId);
      }

      this.manageAsSubject = this.getAuthenticatedSubject(manageAsPrincipalName, kernelId, "<manage-as-principal-name>");
      this.checkDeployUserPrivileges(this.manageAsSubject, "<manage-as-principal-name>", kernelId);
   }

   private AuthenticatedSubject getAuthenticatedSubject(String username, AuthenticatedSubject kernelId, String elementName) throws RACommonException {
      AuthenticatedSubject subject = null;

      try {
         subject = this.getAuthenticatedSubject(username, kernelId);
         return subject;
      } catch (LoginException var7) {
         if (username == null || username.length() == 0) {
            username = Debug.getStringAnonymousUser();
         }

         String exMsg = Debug.getExceptionLoginException(username, var7.toString(), elementName);
         throw new RACommonException(exMsg);
      }
   }

   public AuthenticatedSubject getAuthenticatedSubject(String username, AuthenticatedSubject kernelId) throws LoginException {
      return this.securityHelper.getAuthenticatedSubject(username, kernelId);
   }

   private void checkDeployUserPrivileges(AuthenticatedSubject subject, String action, AuthenticatedSubject kernelId) throws RAException {
      AuthenticatedSubject currentSubject = null;
      if (this.raIM != null && this.raIM.getAppContext() != null) {
         currentSubject = this.raIM.getAppContext().getDeploymentInitiator();
      }

      if (currentSubject != null && (this.serverRuntime.getStateVal() != 1 || !this.securityHelper.isUserAnonymous(currentSubject)) && this.securityHelper.isAdminPrivilegeEscalation(currentSubject, subject)) {
         Set configuredPrincipals = subject.getPrincipals();
         Iterator iter = configuredPrincipals.iterator();
         String configuredUser = "";
         if (iter.hasNext()) {
            Principal principal = (Principal)iter.next();
            configuredUser = principal.getName();
         }

         Set currentPrincipals = currentSubject.getPrincipals();
         iter = currentPrincipals.iterator();
         String currentUser = "";
         if (iter.hasNext()) {
            Principal principal = (Principal)iter.next();
            currentUser = principal.getName();
         }

         String msg = Debug.getDeploySecurityBumpUpFailed(action, configuredUser, currentUser);
         throw new RAException(msg);
      }
   }

   static String xaFlagsToString(int flags) {
      switch (flags) {
         case 0:
            return "TMNOFLAGS";
         case 2097152:
            return "TMJOIN";
         case 33554432:
            return "TMSUSPEND";
         case 67108864:
            return "TMSUCCESS";
         case 134217728:
            return "TMRESUME";
         case 536870912:
            return "TMFAIL";
         case 1073741824:
            return "TMONEPHASE";
         default:
            return Integer.toHexString(flags).toUpperCase();
      }
   }

   private static String xaErrorCodeToString(int err, boolean detail) {
      StringBuffer msg = new StringBuffer(10);
      switch (err) {
         case -9:
            msg.append("XAER_OUTSIDE");
            if (detail) {
               msg.append(" : The resource manager is doing work outside global transaction");
            }

            return msg.toString();
         case -8:
            msg.append("XAER_DUPID");
            if (detail) {
               msg.append(" : The XID already exists");
            }

            return msg.toString();
         case -7:
            msg.append("XAER_RMFAIL");
            if (detail) {
               msg.append(" : Resource manager is unavailable");
            }

            return msg.toString();
         case -6:
            msg.append("XAER_PROTO");
            if (detail) {
               msg.append(" : Routine was invoked in an inproper context");
            }

            return msg.toString();
         case -5:
            msg.append("XAER_INVAL");
            if (detail) {
               msg.append(" : Invalid arguments were given");
            }

            return msg.toString();
         case -4:
            msg.append("XAER_NOTA");
            if (detail) {
               msg.append(" : The XID is not valid");
            }

            return msg.toString();
         case -3:
            msg.append("XAER_RMERR");
            if (detail) {
               msg.append(" : A resource manager error has occured in the transaction branch");
            }

            return msg.toString();
         case -2:
            msg.append("XAER_ASYNC");
            if (detail) {
               msg.append(" : Asynchronous operation already outstanding");
            }

            return msg.toString();
         case 0:
            return "XA_OK";
         case 3:
            return "XA_RDONLY";
         case 5:
            msg.append("XA_HEURMIX");
            if (detail) {
               msg.append(" : The transaction branch has been heuristically committed and rolled back");
            }

            return msg.toString();
         case 6:
            msg.append("XA_HEURRB");
            if (detail) {
               msg.append(" : The transaction branch has been heuristically rolled back");
            }

            return msg.toString();
         case 7:
            msg.append("XA_HEURCOM");
            if (detail) {
               msg.append(" : The transaction branch has been heuristically committed");
            }

            return msg.toString();
         case 8:
            msg.append("XA_HEURHAZ");
            if (detail) {
               msg.append(" : The transaction branch may have been heuristically completed");
            }

            return msg.toString();
         case 100:
            msg.append("XA_RBROLLBACK");
            if (detail) {
               msg.append(" : Rollback was caused by unspecified reason");
            }

            return msg.toString();
         case 101:
            msg.append("XA_RBCOMMFAIL");
            if (detail) {
               msg.append(" : Rollback was caused by communication failure");
            }

            return msg.toString();
         case 102:
            msg.append("XA_RBDEADLOCK");
            if (detail) {
               msg.append(" : A deadlock was detected");
            }

            return msg.toString();
         case 103:
            msg.append("XA_RBINTEGRITY");
            if (detail) {
               msg.append(" : A condition that violates the integrity of the resource was detected");
            }

            return msg.toString();
         case 104:
            msg.append("XA_RBOTHER");
            if (detail) {
               msg.append(" : The resource manager rolled back the transaction branch for a reason not on this list");
            }

            return msg.toString();
         case 105:
            msg.append("XA_RBPROTO");
            if (detail) {
               msg.append(" : A protocol error occured in the resource manager");
            }

            return msg.toString();
         case 106:
            msg.append("XA_RBTIMEOUT");
            if (detail) {
               msg.append(" : A transaction branch took too long");
            }

            return msg.toString();
         case 107:
            msg.append("XA_RBTRANSIENT");
            if (detail) {
               msg.append(" : May retry the transaction branch");
            }

            return msg.toString();
         default:
            return Integer.toHexString(err).toUpperCase();
      }
   }
}
