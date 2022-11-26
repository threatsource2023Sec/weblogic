package weblogic.servlet.security.internal;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.security.jacc.WebResourcePermission;
import javax.security.jacc.WebUserDataPermission;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.j2ee.descriptor.AuthConstraintBean;
import weblogic.j2ee.descriptor.SecurityConstraintBean;
import weblogic.j2ee.descriptor.SecurityRoleRefBean;
import weblogic.j2ee.descriptor.ServletBean;
import weblogic.j2ee.descriptor.UserDataConstraintBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.WebResourceCollectionBean;
import weblogic.management.DeploymentException;
import weblogic.security.jacc.PolicyContextManager;
import weblogic.security.jaspic.SecurityServices;
import weblogic.security.jaspic.SecurityServicesImpl;
import weblogic.server.GlobalServiceLocator;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.spi.JaccApplicationSecurity;
import weblogic.servlet.spi.SubjectHandle;

final class WebAppSecurityJacc extends WebAppSecurity {
   private static final boolean DEBUG = false;
   private static final char DELIMITER = '_';
   private static final String HTTP_METHODS_PROPERTY_NAME = "HttpMethods";
   private static final String HTTP_METHOD_OMISSIONS_PROPERTY_NAME = "HttpMethodOmissions";
   private final String contextId;
   private boolean isPathPrefixPatternDefined;
   private HashMap patterns;

   WebAppSecurityJacc(ServletSecurityContext ctx, JaccApplicationSecurity as, ExternalRoleChecker roleChecker) throws DeploymentException {
      this(ctx, as, roleChecker, (SecurityServices)GlobalServiceLocator.getServiceLocator().getService(SecurityServicesImpl.class, new Annotation[0]));
   }

   WebAppSecurityJacc(ServletSecurityContext ctx, JaccApplicationSecurity as, ExternalRoleChecker roleChecker, SecurityServices securityServices) throws DeploymentException {
      super(ctx, as, roleChecker, securityServices);
      this.isPathPrefixPatternDefined = false;
      this.patterns = new HashMap();
      String cp = ctx.getServletContext().getContextPath();
      cp = cp.replace('/', '_');
      String app = ApplicationVersionUtils.replaceDelimiter(ctx.getApplicationId(), '_');
      this.contextId = ctx.getServerName() + '_' + app + '_' + cp;
   }

   protected void deployRoles() throws DeploymentException {
      if (!this.roleNames.isEmpty()) {
         Iterator var1 = this.roleNames.iterator();

         while(var1.hasNext()) {
            String roleName = (String)var1.next();
            String[] mapping = (String[])this.roleMapping.get(roleName);
            if (!this.isExternallyDefined(mapping)) {
               this.appSecurity.deployRole(roleName, (String[])null);
            }
         }

      }
   }

   protected void mergePolicies(WebAppBean webAppBean, SecurityConstraintBean[] secCons) throws DeploymentException {
      this.collectPatterns(secCons);
      this.computeQualifiedPatterns();
      this.deployRegisteredPolicies(secCons);
      this.deployUncheckedPoliciesForException();
   }

   protected void deployPolicies() throws DeploymentException {
   }

   private void deployUncheckedPoliciesForException() throws DeploymentException {
      Iterator iter = this.patterns.values().iterator();

      while(iter.hasNext()) {
         PatternHelper helper = (PatternHelper)iter.next();
         HttpMethodCombination methods = helper.getHttpMethodCombinationForWRP();
         String action = methods.getFlippedAction();
         boolean deployWRP = false;
         if (action != null) {
            deployWRP = true;
            if (this.isPathPrefixPatternDefined && helper.getPattern().equals("/")) {
               deployWRP = false;
            }
         }

         if (deployWRP) {
            WebResourcePermission webResPerm = new WebResourcePermission(helper.getQualifiedPattern(), action);
            this.appSecurity.deployUncheckedPolicy(webResPerm);
         }

         methods = helper.getHttpMethodCombinationForUDC();
         action = methods.getFlippedAction();
         if (action != null) {
            WebUserDataPermission userDataPerm = new WebUserDataPermission(helper.getQualifiedPattern(), action);
            this.appSecurity.deployUncheckedPolicy(userDataPerm);
         }
      }

   }

   private void deployRegisteredPolicies(SecurityConstraintBean[] secCons) throws DeploymentException {
      if (secCons != null) {
         for(int i = 0; i < secCons.length; ++i) {
            SecurityConstraintBean cons = secCons[i];
            WebResourceCollectionBean[] wrcs = cons.getWebResourceCollections();
            if (wrcs != null) {
               this.registerWebResourceCollections(secCons[i], wrcs);
            }
         }

         if (this.isDenyUncoveredMethodsSet()) {
            this.deployExcludedPolicyForUncoveredMethods(secCons);
         }

      }
   }

   private void registerWebResourceCollections(SecurityConstraintBean secCon, WebResourceCollectionBean[] wrcs) throws DeploymentException {
      for(int i = 0; i < wrcs.length; ++i) {
         this.registerSecurityPattern(wrcs[i], secCon);
      }

   }

   private void registerSecurityPattern(WebResourceCollectionBean wrc, SecurityConstraintBean secCon) throws DeploymentException {
      String[] ptrns = wrc.getUrlPatterns();

      for(int i = 0; i < ptrns.length; ++i) {
         String pattern = fixupURLPattern(ptrns[i]);
         PatternHelper helper = (PatternHelper)this.patterns.get(pattern);
         helper.addHttpMethodLists(wrc);
         this.registerAuthConstraint(helper, secCon, wrc);
         UserDataConstraintBean udc = secCon.getUserDataConstraint();
         if (udc != null) {
            this.registerUserDataConstraints(udc, helper, wrc);
         }
      }

   }

   private void registerAuthConstraint(PatternHelper helper, SecurityConstraintBean secCon, WebResourceCollectionBean wrc) throws DeploymentException {
      AuthConstraintBean ac = secCon.getAuthConstraint();
      if (ac != null) {
         String[] roleNames = ac.getRoleNames();
         helper.addHttpMethodListsForWRP(wrc);
         if (roleNames != null && roleNames.length >= 1) {
            this.deployRoleBasedPolicies(roleNames, helper, wrc);
         } else {
            this.deployExcludedPolicy(helper, wrc);
            helper.addHttpMethodListsForUDC(wrc);
         }

      }
   }

   private void registerUserDataConstraints(UserDataConstraintBean udc, PatternHelper helper, WebResourceCollectionBean wrc) throws DeploymentException {
      String tg = udc.getTransportGuarantee();
      if (tg != null && (tg.equals("CONFIDENTIAL") || tg.equals("INTEGRAL"))) {
         helper.addHttpMethodListsForUDC(wrc);
         HttpMethodCombination httpMethodCombination = helper.createHttpMethodCombination(wrc);
         String action = httpMethodCombination.getAction();
         if (action == null) {
            action = "";
         }

         action = action + ":" + tg;
         WebUserDataPermission userDataPerm = new WebUserDataPermission(helper.getQualifiedPattern(), action);
         this.appSecurity.deployUncheckedPolicy(userDataPerm);
      }

   }

   private void deployRoleBasedPolicies(String[] roleNames, PatternHelper helper, WebResourceCollectionBean wrc) throws DeploymentException {
      for(int l = 0; l < roleNames.length; ++l) {
         if (roleNames[l].equals("*")) {
            roleNames = new String[this.roleNames.size()];
            this.roleNames.toArray(roleNames);
            if (!this.isAnyAuthUserRoleDefinedInDD) {
               removeElement(roleNames, "**");
            }
            break;
         }
      }

      HttpMethodCombination httpMethodCombination = helper.createHttpMethodCombination(wrc);
      String action = httpMethodCombination.getAction();

      for(int m = 0; m < roleNames.length; ++m) {
         this.getApplicationSecurity().deployRole(roleNames[m], helper.getQualifiedPattern(), action);
      }

   }

   private void deployExcludedPolicy(PatternHelper helper, WebResourceCollectionBean wrc) throws DeploymentException {
      HttpMethodCombination httpMethodCombination = helper.createHttpMethodCombination(wrc);
      String action = httpMethodCombination.getAction();
      this.appSecurity.deployExcludedPolicy(helper.getQualifiedPattern(), action);
   }

   private void deployExcludedPolicyForUncoveredMethods(SecurityConstraintBean[] secCons) throws DeploymentException {
      Map patternsCombinationMap = new HashMap();
      SecurityConstraintBean[] var3 = secCons;
      int var4 = secCons.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         SecurityConstraintBean cons = var3[var5];
         boolean skipProcessing = false;
         AuthConstraintBean ac = cons.getAuthConstraint();
         if (ac != null && (ac.getRoleNames() == null || ac.getRoleNames().length == 0)) {
            skipProcessing = true;
         }

         WebResourceCollectionBean[] wrcs = cons.getWebResourceCollections();
         if (wrcs != null) {
            WebResourceCollectionBean[] var10 = wrcs;
            int var11 = wrcs.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               WebResourceCollectionBean wrc = var10[var12];
               String[] patterns = wrc.getUrlPatterns();
               String[] var15 = patterns;
               int var16 = patterns.length;

               for(int var17 = 0; var17 < var16; ++var17) {
                  String pattern = var15[var17];
                  pattern = fixupURLPattern(pattern);
                  PatternHelper helper = (PatternHelper)this.patterns.get(pattern);
                  HttpMethodCombination combination = (HttpMethodCombination)patternsCombinationMap.get(helper);
                  if (combination == null) {
                     combination = helper.createHttpMethodCombination(wrc);
                     if (ac == null) {
                        combination.setAuthConstraintNull(true);
                     } else {
                        combination.setAuthConstraintNull(false);
                     }
                  }

                  String[] httpMethods = wrc.getHttpMethods();
                  String[] httpMethodOmissions = wrc.getHttpMethodOmissions();
                  Set uncoveredHttpMethods = combination.getUncoveredHttpMethods();
                  if (uncoveredHttpMethods == null) {
                     uncoveredHttpMethods = new HashSet();
                  }

                  Set coveredHttpMethods = combination.getCoveredHttpMethods();
                  if (coveredHttpMethods == null) {
                     coveredHttpMethods = new HashSet();
                  }

                  String[] var25;
                  int var26;
                  int var27;
                  String method;
                  if (httpMethods != null && httpMethods.length > 0 && !skipProcessing) {
                     var25 = httpMethods;
                     var26 = httpMethods.length;

                     for(var27 = 0; var27 < var26; ++var27) {
                        method = var25[var27];
                        ((Set)coveredHttpMethods).add(method);
                     }
                  } else {
                     if (httpMethodOmissions == null || httpMethodOmissions.length <= 0 || skipProcessing) {
                        continue;
                     }

                     var25 = wrc.getHttpMethodOmissions();
                     var26 = var25.length;

                     for(var27 = 0; var27 < var26; ++var27) {
                        method = var25[var27];
                        ((Set)uncoveredHttpMethods).add(method);
                     }
                  }

                  combination.setCoveredHttpMethods((Set)coveredHttpMethods);
                  combination.setUncoveredHttpMethods((Set)uncoveredHttpMethods);
                  patternsCombinationMap.put(helper, combination);
               }
            }
         }
      }

      Iterator var29 = patternsCombinationMap.entrySet().iterator();

      while(var29.hasNext()) {
         Map.Entry entry = (Map.Entry)var29.next();
         Set deniedMethods = ((HttpMethodCombination)entry.getValue()).getUncoveredHttpMethods();
         Set coveredHttpmethods = ((HttpMethodCombination)entry.getValue()).getCoveredHttpMethods();
         PatternHelper helper = (PatternHelper)entry.getKey();
         String action;
         if (!deniedMethods.isEmpty() && !((HttpMethodCombination)entry.getValue()).isAuthConstraintNull()) {
            HTTPLogger.logInfo("WebAppSecurityJacc", "The following Http  methods are denied for pattern " + ((PatternHelper)entry.getKey()).getQualifiedPattern() + ": " + deniedMethods);
            action = ((HttpMethodCombination)entry.getValue()).getActionString("", deniedMethods);
            this.appSecurity.deployExcludedPolicy(helper.getQualifiedPattern(), action);
         }

         HTTPLogger.logInfo("WebAppSecurityJacc", "The following Http  methods are allowed for pattern " + entry.getKey() + ": " + ((HttpMethodCombination)entry.getValue()).toString());
         action = ((HttpMethodCombination)entry.getValue()).getActionString("!", coveredHttpmethods);
         this.appSecurity.deployExcludedPolicy(helper.getQualifiedPattern(), action);
      }

   }

   private void collectPatterns(SecurityConstraintBean[] secCons) {
      this.patterns.put("/", new PatternHelper("/"));
      if (secCons != null) {
         for(int i = 0; i < secCons.length; ++i) {
            WebResourceCollectionBean[] wrc = secCons[i].getWebResourceCollections();
            if (wrc != null && wrc.length >= 1) {
               for(int j = 0; j < wrc.length; ++j) {
                  String[] ptrns = wrc[j].getUrlPatterns();
                  if (ptrns != null) {
                     for(int k = 0; k < ptrns.length; ++k) {
                        String pattern = fixupURLPattern(ptrns[k]);
                        if (pattern.equals("/*")) {
                           this.isPathPrefixPatternDefined = true;
                        }

                        this.patterns.put(pattern, new PatternHelper(pattern));
                     }
                  }
               }
            }
         }

      }
   }

   private void computeQualifiedPatterns() {
      if (!this.patterns.isEmpty()) {
         Iterator iter = this.patterns.values().iterator();

         while(iter.hasNext()) {
            PatternHelper helper = (PatternHelper)iter.next();
            helper.computeQualifiedPatterns(this.patterns);
         }

      }
   }

   protected void deployRoleLink(ServletConfig servletConfig, String refName, String roleName) throws DeploymentException {
      this.getApplicationSecurity().deployRoleLink(roleName, servletConfig.getServletName(), refName);
   }

   public void registerRoleRefs(ServletConfig servletConfig) throws DeploymentException {
      if (!this.roleNames.isEmpty()) {
         HashSet roleRef = this.getRoleRefsFromServletBean(servletConfig.getServletName());
         Iterator var3 = this.roleNames.iterator();

         while(var3.hasNext()) {
            String roleName = (String)var3.next();
            if (!roleRef.contains(roleName)) {
               this.getApplicationSecurity().deployRoleLink(roleName, servletConfig.getServletName(), roleName);
            }
         }

      }
   }

   private HashSet getRoleRefsFromServletBean(String servletName) {
      HashSet hs = new HashSet();
      ServletBean servletBean = this.getSecurityContext().lookupServlet(servletName);
      if (servletBean != null) {
         SecurityRoleRefBean[] roleRefs = servletBean.getSecurityRoleRefs();
         if (roleRefs != null && roleRefs.length > 0) {
            for(int i = 0; i < roleRefs.length; ++i) {
               hs.add(roleRefs[i].getRoleName());
            }
         }
      }

      return hs;
   }

   public void initContextHandler(HttpServletRequest req) {
      PolicyContextManager.setPolicyContext(this.getApplicationSecurity().createContextHandlerData(req));
      PolicyContextManager.setContextID(this.getApplicationSecurity().getContextID());
   }

   private JaccApplicationSecurity getApplicationSecurity() {
      return (JaccApplicationSecurity)this.appSecurity;
   }

   public void resetContextHandler() {
      PolicyContextManager.resetPolicyContext();
      PolicyContextManager.resetContextID();
   }

   protected boolean checkTransport(ResourceConstraint cons, HttpServletRequest req, HttpServletResponse rsp) throws IOException {
      String relUri = getRelativeURI(req);
      if (relUri.length() == 1 && relUri.charAt(0) == '/') {
         relUri = "";
      }

      String action = req.getMethod();
      if (req.isSecure()) {
         action = action + ":CONFIDENTIAL";
      }

      boolean ret = false;

      try {
         ret = this.checkTransport(relUri, action);
         if (ret) {
            return true;
         } else {
            if (req.isSecure()) {
               rsp.sendError(403);
            } else {
               action = action + ":CONFIDENTIAL";
               ret = this.checkTransport(relUri, action);
               if (!ret) {
                  rsp.sendError(403);
               } else {
                  String securedURL = this.getSecuredURL(req, rsp, req.getRequestURI());
                  if (securedURL != null) {
                     rsp.sendRedirect(securedURL);
                  } else {
                     rsp.sendError(403);
                  }
               }
            }

            return ret;
         }
      } catch (SecurityException var8) {
         HTTPLogger.logSecurityException("user data constraints check", req.getRequestURI(), this.getSecurityContext().getLogContext(), var8);
         return false;
      }
   }

   public boolean isSSLRequired(String relUri, String method) {
      if (relUri.length() == 1 && relUri.charAt(0) == '/') {
         relUri = "";
      }

      String action = method;

      try {
         boolean permission = this.checkTransport(relUri, action);
         if (permission) {
            return false;
         } else {
            action = action + ":CONFIDENTIAL";
            return this.checkTransport(relUri, action);
         }
      } catch (SecurityException var5) {
         HTTPLogger.logSecurityException("user data constraints check", relUri, this.getSecurityContext().getLogContext(), var5);
         return true;
      }
   }

   private boolean checkTransport(String relUri, String action) {
      return this.getApplicationSecurity().checkTransport(relUri, action);
   }

   public boolean isSubjectInRole(SubjectHandle subject, String rolename, HttpServletRequest request, HttpServletResponse response, ServletConfig servletConfig) {
      String name = this.getRequestFacade().isDynamicallyGenerated(servletConfig) ? "" : servletConfig.getServletName();
      return this.appSecurity.isSubjectInRole(subject, rolename, request, response, name);
   }

   public ResourceConstraint getConstraint(HttpServletRequest req) {
      return null;
   }

   public void unregisterRolesAndPolicies() {
      super.unregisterRolesAndPolicies();

      try {
         this.appSecurity.unregisterPolicies();
      } catch (DeploymentException var2) {
         HTTPLogger.logFailedToUndeploySecurityPolicy(var2.getMessage(), var2);
      }

   }

   private static String[] removeElement(String[] input, String element) {
      List result = new LinkedList();
      String[] var3 = input;
      int var4 = input.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String item = var3[var5];
         if (!element.equals(item)) {
            result.add(item);
         }
      }

      return (String[])result.toArray(input);
   }

   static {
      try {
         getProvider().initializeJACC();
      } catch (DeploymentException var1) {
         HTTPLogger.logFailedToRegisterPolicyContextHandlers(var1);
      }

   }

   private static class PatternHelper {
      private final String pattern;
      private final boolean prefixPattern;
      private final boolean exactPattern;
      private final boolean extensionPattern;
      private final boolean defaultPattern;
      private final String prefix;
      private HttpMethodCombination httpMethodCombination = new HttpMethodCombination();
      private HttpMethodCombination httpMethodCombinationForWRP = new HttpMethodCombination();
      private HttpMethodCombination httpMethodCombinationForUDC = new HttpMethodCombination();
      private String qualifiedPattern;
      private final String extension;

      PatternHelper(String p) {
         this.pattern = p;
         this.qualifiedPattern = this.escapeEncodeUrlPattern(this.pattern);
         if (p == null) {
            throw new IllegalArgumentException("pattern is null");
         } else {
            if (p.length() == 1 && p.charAt(0) == '/') {
               this.defaultPattern = true;
               this.exactPattern = false;
               this.extensionPattern = false;
               this.prefixPattern = false;
               this.prefix = "";
               this.extension = null;
            } else if (p.startsWith("*.")) {
               this.exactPattern = false;
               this.extensionPattern = true;
               this.prefixPattern = false;
               this.defaultPattern = false;
               this.prefix = null;
               this.extension = this.pattern.substring(1);
            } else if (p.length() == 2 && p.equals("/*")) {
               this.exactPattern = false;
               this.extensionPattern = false;
               this.prefixPattern = true;
               this.defaultPattern = true;
               this.prefix = "";
               this.extension = null;
            } else if (p.endsWith("/*")) {
               this.exactPattern = false;
               this.extensionPattern = false;
               this.prefixPattern = true;
               this.defaultPattern = false;
               this.prefix = this.pattern.substring(0, this.pattern.length() - 1);
               this.extension = null;
            } else {
               this.exactPattern = true;
               this.extensionPattern = false;
               this.prefixPattern = false;
               this.defaultPattern = false;
               this.prefix = p;
               this.extension = null;
            }

         }
      }

      String getPattern() {
         return this.pattern;
      }

      boolean isPrefixPattern() {
         return this.prefixPattern;
      }

      boolean isExactPattern() {
         return this.exactPattern;
      }

      boolean isExtensionPattern() {
         return this.extensionPattern;
      }

      boolean isDefaultPattern() {
         return this.defaultPattern;
      }

      String getPrefix() {
         return this.prefix;
      }

      String getQualifiedPattern() {
         return this.qualifiedPattern;
      }

      HttpMethodCombination getHttpMethodCombination() {
         return this.httpMethodCombination;
      }

      HttpMethodCombination getHttpMethodCombinationForWRP() {
         return this.httpMethodCombinationForWRP;
      }

      HttpMethodCombination getHttpMethodCombinationForUDC() {
         return this.httpMethodCombinationForUDC;
      }

      private String escapeEncodeUrlPattern(String urlPattern) {
         return urlPattern == null ? null : urlPattern.replace(":", "%3A");
      }

      private void computeQualifiedPatterns(HashMap patterns) {
         if (!this.isExactPattern()) {
            Iterator iter = patterns.values().iterator();

            while(true) {
               PatternHelper helper;
               do {
                  if (!iter.hasNext()) {
                     return;
                  }

                  helper = (PatternHelper)iter.next();
               } while(helper.getPattern().equals(this.pattern));

               boolean qualified = false;
               if (this.isDefaultPattern()) {
                  qualified = !helper.getPattern().equals("/") && !helper.getPattern().equals("/*");
               } else if (!this.isPrefixPattern()) {
                  if (helper.isPrefixPattern()) {
                     qualified = true;
                  } else if (helper.isExactPattern()) {
                     qualified = helper.getPattern().endsWith(this.extension);
                  }
               } else if (helper.isPrefixPattern()) {
                  qualified = helper.getPrefix().startsWith(this.prefix);
               } else if (helper.isExactPattern()) {
                  qualified = helper.getPattern().startsWith(this.prefix) || this.prefix.equals(helper.getPattern() + "/");
               }

               if (qualified) {
                  this.qualifiedPattern = this.qualifiedPattern + ":" + this.escapeEncodeUrlPattern(helper.getPattern());
               }
            }
         }
      }

      private HttpMethodCombination createHttpMethodCombination(WebResourceCollectionBean wrc) {
         HttpMethodCombination httpMethodCombination = new HttpMethodCombination();
         this.addHttpMethodLists(wrc, httpMethodCombination);
         return httpMethodCombination;
      }

      private void addHttpMethodLists(WebResourceCollectionBean wrc, HttpMethodCombination httpMethodCombination) {
         String[] httpMethods = null;
         String[] httpMethodOmissions = null;
         if (wrc instanceof AbstractDescriptorBean) {
            AbstractDescriptorBean adb = (AbstractDescriptorBean)wrc;
            if (adb.isSet("HttpMethods")) {
               httpMethods = wrc.getHttpMethods();
            }

            if (adb.isSet("HttpMethodOmissions")) {
               httpMethodOmissions = wrc.getHttpMethodOmissions();
            }
         } else {
            httpMethods = wrc.getHttpMethods();
            httpMethodOmissions = wrc.getHttpMethodOmissions();
         }

         httpMethodCombination.addHttpMethodList(httpMethods);
         if (httpMethodOmissions != null) {
            httpMethodCombination.addHttpMethodOmissionList(httpMethodOmissions);
         }

      }

      private void addHttpMethodLists(WebResourceCollectionBean wrc) {
         this.addHttpMethodLists(wrc, this.httpMethodCombination);
      }

      private void addHttpMethodListsForWRP(WebResourceCollectionBean wrc) {
         this.addHttpMethodLists(wrc, this.httpMethodCombinationForWRP);
      }

      private void addHttpMethodListsForUDC(WebResourceCollectionBean wrc) {
         this.addHttpMethodLists(wrc, this.httpMethodCombinationForUDC);
      }
   }
}
