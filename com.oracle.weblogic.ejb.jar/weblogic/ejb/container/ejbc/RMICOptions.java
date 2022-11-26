package weblogic.ejb.container.ejbc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.dd.ClusteringDescriptor;
import weblogic.ejb.container.interfaces.ClientDrivenBeanInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.interfaces.IIOPSecurityDescriptor;
import weblogic.ejb.container.interfaces.MethodInfo;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.interfaces.StatefulSessionBeanInfo;
import weblogic.rmi.rmic.RmicMethodDescriptor;
import weblogic.utils.StringUtils;
import weblogic.utils.annotation.BeaSynthetic.Helper;

final class RMICOptions {
   private static final DebugLogger debugLogger;
   static final String INTEGRITY_OPT = "integrity";
   static final String CONFIDENTIALITY_OPT = "confidentiality";
   static final String CLIENTCERTAUTHENTICATION_OPT = "clientCertAuthentication";
   static final String CLIENTAUTHENTICATION_OPT = "clientAuthentication";
   static final String IDENTITYASSERTION_OPT = "identityAssertion";
   private static final boolean noMangledNames = true;
   private static final boolean noExit = true;
   private boolean clusterable = false;
   private boolean methodsAreIdempotent = false;
   private boolean propagateEnvironment = false;
   private String loadAlgorithm = null;
   private String callRouter = null;
   private String replicaHandler = null;
   private String dgcPolicy = null;
   private String dispatchPolicy = null;
   private boolean stickToFirstServer = false;
   private boolean useServersideStubs = false;
   private String remoteRefClassName = null;
   private boolean activatable = false;
   private String integrity = null;
   private String confidentiality = null;
   private String clientCertAuthentication = null;
   private String clientAuthentication = null;
   private String identityAssertion = null;
   private final ClientDrivenBeanInfo bd;
   private final ClusteringDescriptor cd;
   private Collection rmicMethodDescriptors;

   RMICOptions(ClientDrivenBeanInfo bd) {
      this.bd = bd;
      this.cd = bd.getClusteringDescriptor();
   }

   private boolean isStatefulSession() {
      return this.bd instanceof SessionBeanInfo && ((SessionBeanInfo)this.bd).isStateful();
   }

   private boolean isStateless() {
      return this.bd instanceof SessionBeanInfo && ((SessionBeanInfo)this.bd).isStateless();
   }

   private boolean isSingleton() {
      return this.bd instanceof SessionBeanInfo && ((SessionBeanInfo)this.bd).isSingleton();
   }

   private boolean isEntityBean() {
      return this.bd instanceof EntityBeanInfo;
   }

   private boolean isStatefulBeanClusterable() {
      return this.isStatefulSession() && ((StatefulSessionBeanInfo)this.bd).isReplicated();
   }

   private boolean isStatelessBeanClusterable() {
      return this.isStateless() && this.cd.getBeanIsClusterable();
   }

   private boolean isSingletonBeanClusterable() {
      return this.isSingleton() && this.cd.getBeanIsClusterable();
   }

   private boolean isEntityBeanClusterable() {
      return this.isEntityBean() && this.cd.getHomeIsClusterable();
   }

   private boolean usesBeanManagedTx() {
      return this.bd.isSessionBean() && ((SessionBeanInfo)this.bd).usesBeanManagedTx();
   }

   private static String rmiLoadAlgorithmOption(String ddLoadAlgorithmString) {
      if ("roundrobin".equalsIgnoreCase(ddLoadAlgorithmString)) {
         return "round-robin";
      } else if ("weightbased".equalsIgnoreCase(ddLoadAlgorithmString)) {
         return "weight-based";
      } else if ("roundrobinaffinity".equalsIgnoreCase(ddLoadAlgorithmString)) {
         return "round-robin-affinity";
      } else if ("weightbasedaffinity".equalsIgnoreCase(ddLoadAlgorithmString)) {
         return "weight-based-affinity";
      } else {
         return "randomaffinity".equalsIgnoreCase(ddLoadAlgorithmString) ? "random-affinity" : ddLoadAlgorithmString;
      }
   }

   void setIIOPSecurityOptions() {
      IIOPSecurityDescriptor iiop = this.bd.getIIOPSecurityDescriptor();
      this.integrity = iiop.getTransport_integrity();
      this.confidentiality = iiop.getTransport_confidentiality();
      this.clientCertAuthentication = iiop.getTransport_client_cert_authentication();
      this.clientAuthentication = iiop.getClient_authentication();
      this.identityAssertion = iiop.getIdentity_assertion();
   }

   void setHomeOptions() {
      if (this.cd.getHomeIsClusterable()) {
         this.clusterable = true;
         this.loadAlgorithm = rmiLoadAlgorithmOption(this.cd.getHomeLoadAlgorithm());
         this.callRouter = this.cd.getHomeCallRouterClassName();
         this.propagateEnvironment = this.isStatelessBeanClusterable() || this.isStatefulBeanClusterable() || this.isEntityBeanClusterable();
         if (this.isStateless()) {
            this.methodsAreIdempotent = true;
         }

         this.buildRmicMethodDescriptors(this.bd.getAllHomeMethodInfos(), true);
      } else {
         this.buildRmicMethodDescriptors(this.bd.getAllHomeMethodInfos(), false);
      }

      this.dgcPolicy = "managed";
      this.dispatchPolicy = this.bd.getDispatchPolicy();
      this.stickToFirstServer = this.bd.getStickToFirstServer();
      this.useServersideStubs = this.cd.getUseServersideStubs();
   }

   void setEOOptions(Class remoteIface) {
      Collection methods = new ArrayList();
      Method[] var3 = remoteIface.getMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method m = var3[var5];
         if (!Helper.isBeaSyntheticMethod(m)) {
            methods.add(this.bd.getRemoteMethodInfo(m));
         }
      }

      if (this.isStateless() || this.isSingleton()) {
         this.dgcPolicy = "managed";
      }

      if (this.isStatelessBeanClusterable()) {
         this.clusterable = true;
         this.loadAlgorithm = rmiLoadAlgorithmOption(this.cd.getBeanLoadAlgorithm());
         this.callRouter = this.cd.getBeanCallRouterClassName();
         this.propagateEnvironment = true;
         this.methodsAreIdempotent = false;
         this.buildRmicMethodDescriptors(methods, true);
      } else if (this.isSingletonBeanClusterable()) {
         this.clusterable = true;
         this.loadAlgorithm = rmiLoadAlgorithmOption(this.cd.getBeanLoadAlgorithm());
         this.callRouter = this.cd.getBeanCallRouterClassName();
         this.propagateEnvironment = true;
         this.methodsAreIdempotent = false;
         this.buildRmicMethodDescriptors(methods, true);
      } else if (this.isEntityBeanClusterable()) {
         this.clusterable = true;
         this.propagateEnvironment = true;
         this.remoteRefClassName = "weblogic.rmi.cluster.ClusterActivatableRemoteRef";
         this.dgcPolicy = "managed";
         this.methodsAreIdempotent = ((EntityBeanInfo)this.bd).getConcurrencyStrategy() == 5;
         this.replicaHandler = "weblogic.rmi.cluster.EntityBeanReplicaHandler";
         this.buildRmicMethodDescriptors(methods, true);
      } else if (this.isStatefulBeanClusterable()) {
         this.clusterable = true;
         this.replicaHandler = "weblogic.rmi.cluster.PrimarySecondaryReplicaHandler";
         this.buildRmicMethodDescriptors(methods, true);
      } else {
         this.buildRmicMethodDescriptors(methods, false);
      }

      this.dispatchPolicy = this.bd.getDispatchPolicy();
      this.stickToFirstServer = this.bd.getStickToFirstServer();
      if (this.isEntityBean() && !this.isEntityBeanClusterable() || this.isStatefulSession() && !this.isStatefulBeanClusterable()) {
         this.activatable = true;
      }

   }

   public List asList() {
      List a = new ArrayList();
      a.add("-nomanglednames");
      a.add("-noexit");
      if (this.clusterable) {
         a.add("-clusterable");
      }

      if (this.methodsAreIdempotent) {
         a.add("-methodsAreIdempotent");
      }

      if (this.propagateEnvironment) {
         a.add("-propagateEnvironment");
      }

      if (this.loadAlgorithm != null) {
         a.add("-loadAlgorithm");
         a.add(this.loadAlgorithm.toLowerCase(Locale.ENGLISH));
      }

      if (this.callRouter != null) {
         a.add("-callRouter");
         a.add(this.callRouter);
      }

      if (this.replicaHandler != null) {
         a.add("-replicaHandler");
         a.add(this.replicaHandler);
      }

      if (this.useServersideStubs) {
         a.add("-serverSideStubs");
      }

      if (this.dgcPolicy != null) {
         a.add("-dgcPolicy");
         a.add(this.dgcPolicy);
      }

      if (this.dispatchPolicy != null) {
         a.add("-dispatchPolicy");
         a.add(this.dispatchPolicy);
      }

      if (this.stickToFirstServer) {
         a.add("-stickToFirstServer");
      }

      if (!this.bd.useCallByReference()) {
         a.add("-enforceCallByValue");
      }

      if (this.bd.getNetworkAccessPoint() != null) {
         a.add("-networkAccessPoint");
         a.add(this.bd.getNetworkAccessPoint());
      }

      if ("weblogic.rmi.cluster.EntityRemoteRef".equals(this.remoteRefClassName)) {
         a.add("-remoteRefClassName");
         a.add(this.remoteRefClassName);
         a.add("-serverRefClassName");
         a.add("weblogic.rmi.cluster.EntityServerRef");
      } else if ("weblogic.rmi.cluster.ClusterActivatableRemoteRef".equals(this.remoteRefClassName)) {
         a.add("-remoteRefClassName");
         a.add(this.remoteRefClassName);
         a.add("-serverRefClassName");
         a.add("weblogic.rmi.cluster.ClusterActivatableServerRef");
      }

      if (this.activatable) {
         a.add("-activatable");
      }

      if (this.integrity != null) {
         a.add("-integrity");
         a.add(this.integrity.toLowerCase(Locale.ENGLISH));
      }

      if (this.confidentiality != null) {
         a.add("-confidentiality");
         a.add(this.confidentiality.toLowerCase(Locale.ENGLISH));
      }

      if (this.clientCertAuthentication != null) {
         a.add("-clientCertAuthentication");
         a.add(this.clientCertAuthentication.toLowerCase(Locale.ENGLISH));
      }

      if (this.clientAuthentication != null) {
         a.add("-clientAuthentication");
         a.add(this.clientAuthentication.toLowerCase(Locale.ENGLISH));
      }

      if (this.identityAssertion != null) {
         a.add("-identityAssertion");
         a.add(this.identityAssertion.toLowerCase(Locale.ENGLISH));
      }

      return a;
   }

   public Collection getRmicMethodDescriptors() {
      return this.rmicMethodDescriptors;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(" -nomanglednames");
      sb.append(" -noexit");
      if (this.clusterable) {
         sb.append(" -clusterable");
      }

      if (this.methodsAreIdempotent) {
         sb.append(" -methodsAreIdempotent");
      }

      if (this.propagateEnvironment) {
         sb.append(" -propagateEnvironment");
      }

      if (this.loadAlgorithm != null) {
         sb.append(" -loadAlgorithm " + this.loadAlgorithm);
      }

      if (this.callRouter != null) {
         sb.append(" -callRouter " + this.callRouter);
      }

      if (this.replicaHandler != null) {
         sb.append(" -replicaHandler " + this.replicaHandler);
      }

      if (this.useServersideStubs) {
         sb.append(" -serverSideStubs");
      }

      if (this.dgcPolicy != null) {
         sb.append(" -dgcPolicy " + this.dgcPolicy);
      }

      if (this.dispatchPolicy != null) {
         sb.append(" -dispatchPolicy " + this.dispatchPolicy);
      }

      if (this.stickToFirstServer) {
         sb.append(" -stickToFirstServer");
      }

      if (!this.bd.useCallByReference()) {
         sb.append(" -enforceCallByValue");
      }

      if (this.activatable) {
         sb.append(" -activatable");
      }

      if (this.integrity != null) {
         sb.append(" -integrity ");
         sb.append(this.integrity);
      }

      if (this.confidentiality != null) {
         sb.append(" -confidentiality ");
         sb.append(this.confidentiality);
      }

      if (this.clientCertAuthentication != null) {
         sb.append(" -clientCertAuthentication ");
         sb.append(this.clientCertAuthentication);
      }

      if (this.clientAuthentication != null) {
         sb.append(" -clientAuthentication ");
         sb.append(this.clientAuthentication);
      }

      if (this.identityAssertion != null) {
         sb.append(" -identityAssertion ");
         sb.append(this.identityAssertion);
      }

      return sb.toString();
   }

   private void buildRmicMethodDescriptors(Collection methods, boolean clusteringEnabled) {
      boolean hasAsyncMethods = this.bd.isSessionBean() && ((SessionBeanInfo)this.bd).hasAsyncMethods();
      this.rmicMethodDescriptors = new LinkedHashSet();
      Iterator var4 = methods.iterator();

      while(var4.hasNext()) {
         MethodInfo mi = (MethodInfo)var4.next();
         boolean requiresTX = false;
         boolean isTransactional = false;
         if (!this.usesBeanManagedTx()) {
            short txAttribute = mi.getTransactionAttribute();
            switch (txAttribute) {
               case 0:
                  isTransactional = false;
                  break;
               case 1:
                  requiresTX = true;
                  isTransactional = true;
                  break;
               case 2:
                  requiresTX = true;
                  isTransactional = true;
                  break;
               case 3:
                  isTransactional = true;
                  break;
               case 4:
                  requiresTX = true;
                  isTransactional = true;
                  break;
               case 5:
                  requiresTX = true;
                  isTransactional = false;
            }
         }

         RmicMethodDescriptor md = null;
         if (mi.isIdempotent() && clusteringEnabled || requiresTX || !isTransactional) {
            md = new RmicMethodDescriptor(this.createRMIMethodSignature(mi));
            if (mi.isIdempotent() && clusteringEnabled) {
               md.setIdempotent("true");
            }

            if (requiresTX) {
               md.setRequiresTransaction(true);
            }
         }

         if (this.bd.getRemoteClientTimeout() != 0) {
            if (md == null) {
               md = new RmicMethodDescriptor(this.createRMIMethodSignature(mi));
            }

            long timeoutMillis = (long)this.bd.getRemoteClientTimeout() * 1000L;
            if (isTransactional && this.bd.getTransactionTimeoutSeconds() > 0) {
               timeoutMillis = Math.max(timeoutMillis, (long)this.bd.getTransactionTimeoutSeconds() * 1000L);
            }

            md.setTimeOut(Long.valueOf(timeoutMillis).toString());
            if (debugLogger.isDebugEnabled()) {
               debug("Setting the remote-client-timeout to " + this.bd.getRemoteClientTimeout() + " seconds for the method: " + mi.getSignature());
            }
         }

         if (hasAsyncMethods && ((SessionBeanInfo)this.bd).isAsyncMethod(mi.getMethod())) {
            if (md == null) {
               md = new RmicMethodDescriptor(this.createRMIMethodSignature(mi));
            }

            if (mi.getMethod().getReturnType() == Void.TYPE) {
               md.setOneway("true");
            } else {
               md.setAsynchronousResult("true");
            }
         }

         if (md != null) {
            this.rmicMethodDescriptors.add(md);
         }
      }

      if (this.rmicMethodDescriptors.isEmpty()) {
         this.rmicMethodDescriptors = null;
      }

   }

   private String createRMIMethodSignature(MethodInfo mi) {
      String[] params = mi.getMethodParams();

      for(int i = 0; i < params.length; ++i) {
         int first = params[i].indexOf(91);
         if (first != -1) {
            int last = params[i].lastIndexOf(91);
            int dimension = last - first + 1;
            String type = null;
            char c = params[i].charAt(last + 1);
            switch (c) {
               case 'B':
                  type = "byte";
                  break;
               case 'C':
                  type = "char";
                  break;
               case 'D':
                  type = "double";
                  break;
               case 'E':
               case 'G':
               case 'H':
               case 'K':
               case 'M':
               case 'N':
               case 'O':
               case 'P':
               case 'Q':
               case 'R':
               case 'T':
               case 'U':
               case 'V':
               case 'W':
               case 'X':
               case 'Y':
               default:
                  throw new AssertionError("Bad object type: " + c);
               case 'F':
                  type = "float";
                  break;
               case 'I':
                  type = "int";
                  break;
               case 'J':
                  type = "long";
                  break;
               case 'L':
                  type = params[i].substring(last + 2, params[i].length() - 1);
                  break;
               case 'S':
                  type = "short";
                  break;
               case 'Z':
                  type = "boolean";
            }

            StringBuilder sb = new StringBuilder();
            sb.append(type);

            for(int j = 0; j < dimension; ++j) {
               sb.append("[]");
            }

            params[i] = sb.toString();
         }
      }

      return mi.getMethodName() + "(" + StringUtils.join(params, ",") + ")";
   }

   private static void debug(String s) {
      debugLogger.debug("[RMICOptions] " + s);
   }

   static {
      debugLogger = EJBDebugService.compilationLogger;
   }
}
