package weblogic.servlet.security.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import weblogic.application.SecurityRole;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.j2ee.descriptor.AuthConstraintBean;
import weblogic.j2ee.descriptor.SecurityConstraintBean;
import weblogic.j2ee.descriptor.UserDataConstraintBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.WebResourceCollectionBean;
import weblogic.management.DeploymentException;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.WlsApplicationSecurity;
import weblogic.servlet.utils.StandardURLMapping;
import weblogic.servlet.utils.URLMappingFactory;

final class WebAppSecurityWLS extends WebAppSecurity {
   private HashMap constraintsMap;
   private ArrayList constraintsForOmittedMethod;

   WebAppSecurityWLS(ServletSecurityContext ctx, WlsApplicationSecurity as, ExternalRoleChecker roleChecker) throws DeploymentException {
      super(ctx, as, roleChecker);
   }

   private void deployRole(String rolename, String[] mapping) throws DeploymentException {
      try {
         this.appSecurity.deployRole(rolename, mapping);
      } catch (DeploymentException var4) {
         HTTPLogger.logCouldNotDeployRole(rolename, this.getSecurityContext().getContextURI(), ApplicationVersionUtils.getDisplayName(this.getSecurityContext().getApplicationId()), var4);
         throw new DeploymentException(var4);
      }
   }

   protected void deployRoles() throws DeploymentException {
      if (!this.roleNames.isEmpty()) {
         StringBuffer implicitlyMappedRoles = null;
         int count = 0;
         Iterator var3 = this.roleNames.iterator();

         while(true) {
            while(var3.hasNext()) {
               String roleName = (String)var3.next();
               String[] webPrincipals = (String[])this.roleMapping.get(roleName);
               boolean hasWebPrincipals = webPrincipals != null && webPrincipals.length > 0;
               if (this.appSecurity.isCompatibilitySecMode()) {
                  if (hasWebPrincipals) {
                     if (!this.isExternallyDefined(webPrincipals)) {
                        this.deployRole(roleName, webPrincipals);
                     }
                  } else if (!this.getApplicationSecurity().isCustomRolesEnabled()) {
                     this.deployRole(roleName, new String[]{roleName});
                     if (count < 1) {
                        implicitlyMappedRoles = new StringBuffer();
                        implicitlyMappedRoles.append(roleName);
                     } else {
                        implicitlyMappedRoles.append(", ").append(roleName);
                     }

                     ++count;
                  }
               } else if (!this.isExternallyDefined(webPrincipals)) {
                  SecurityRole appRole = this.getSecurityContext().getSecurityRole(roleName);
                  String[] appPrincipals = null;
                  if (appRole != null) {
                     appPrincipals = appRole.getPrincipalNames();
                  }

                  boolean hasAppPrincipals = appPrincipals != null && appPrincipals.length > 0;
                  if (!hasWebPrincipals && !hasAppPrincipals) {
                     if ("**".equals(roleName) && !this.isAnyAuthUserRoleDefinedInDD()) {
                        this.deployRole(roleName, new String[]{"users"});
                     } else if (this.appSecurity.isApplicationSecMode()) {
                        this.deployRole(roleName, new String[0]);
                     }
                  } else if (appRole != null && appRole.isExternallyDefined()) {
                     if (hasWebPrincipals) {
                        this.deployRole(roleName, webPrincipals);
                     }
                  } else {
                     if (hasAppPrincipals) {
                        if (!hasWebPrincipals) {
                           webPrincipals = appPrincipals;
                        } else {
                           String[] m = new String[appPrincipals.length + webPrincipals.length];
                           System.arraycopy(webPrincipals, 0, m, 0, webPrincipals.length);
                           System.arraycopy(appPrincipals, 0, m, webPrincipals.length, appPrincipals.length);
                           webPrincipals = m;
                        }
                     }

                     if (webPrincipals.length > 0) {
                        this.deployRole(roleName, webPrincipals);
                     }
                  }
               }
            }

            if (implicitlyMappedRoles != null) {
               HTTPLogger.logCreatingImplicitMapForRoles(this.getSecurityContext().getLogContext(), count == 1 ? "role" : "roles", count == 1 ? "has" : "have", implicitlyMappedRoles.toString());
            }

            return;
         }
      }
   }

   private WlsApplicationSecurity getApplicationSecurity() {
      return (WlsApplicationSecurity)this.appSecurity;
   }

   private void mergePolicy(ResourceConstraint curr) throws DeploymentException {
      if (URLMappingFactory.isInvalidUrlPattern(this.getSecurityContext().getUrlMatchMap(), curr.getResourceId())) {
         throw new DeploymentException("The url-pattern, '" + curr.getResourceId() + "' is not valid");
      } else {
         String method = curr.getHttpMethod();
         if (method == null) {
            method = "";
         }

         StandardURLMapping map = (StandardURLMapping)this.constraintsMap.get(method);
         if (map == null) {
            map = this.createStandardURLMapping();
            this.constraintsMap.put(method, map);
            map.put(curr.getResourceId(), curr);
         } else {
            ResourceConstraint prev = (ResourceConstraint)map.removePattern(curr.getResourceId());
            if (prev != null) {
               int currTG = curr.getTransportGuarantee();
               int prevTG = prev.getTransportGuarantee();
               if (currTG != prevTG) {
                  curr.setTransportGuarantee(currTG > prevTG ? currTG : prevTG);
               }

               if (prev.getRoles() != null && curr.getRoles() != null) {
                  if (prev.getRoles().length != 0 && curr.getRoles().length != 0) {
                     curr.addRoles(prev.getRoles());
                  } else {
                     curr = prev.getRoles().length == 0 ? prev : curr;
                  }
               } else {
                  curr = prev.getRoles() == null ? prev : curr;
               }
            }

            map.put(curr.getResourceId(), curr);
         }
      }
   }

   private void mergePolicyForUncoveredMethods(String method, SecurityConstraintBean scb, String pattern) {
      if (method == null) {
         method = "";
      }

      StandardURLMapping map = (StandardURLMapping)this.constraintsMap.get(method);
      if (map == null) {
         map = this.createStandardURLMapping();
         this.constraintsMap.put(method, map);
         map.put(pattern, this.createResourceConstraint(scb.getAuthConstraint(), scb.getUserDataConstraint(), pattern, method));
      }

      if (map.get(pattern) == null) {
         map.put(pattern, this.createResourceConstraint(scb.getAuthConstraint(), scb.getUserDataConstraint(), pattern, method));
      }

   }

   private void processConstraintsForOmittedMethod() {
      if (this.constraintsForOmittedMethod != null) {
         Iterator var1 = this.constraintsForOmittedMethod.iterator();

         while(var1.hasNext()) {
            ResourceConstraint rc = (ResourceConstraint)var1.next();
            String method = rc.getHttpMethod();
            StandardURLMapping map = (StandardURLMapping)this.constraintsMap.get(method);
            if (map == null) {
               map = this.createStandardURLMapping();
               this.constraintsMap.put(method, map);
               if (this.isDenyUncoveredMethodsSet()) {
                  rc.setRoles((String[])null);
               }

               map.put(rc.getResourceId(), rc);
            } else if (map.get(rc.getResourceId()) == null) {
               map.put(rc.getResourceId(), rc);
            }
         }

      }
   }

   private StandardURLMapping createStandardURLMapping() {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      StandardURLMapping map = URLMappingFactory.createCompatibleURLMapping(this.getSecurityContext().getUrlMatchMap(), classLoader, this.getSecurityContext().isCaseInsensitive(), getProvider().getEnforceStrictURLPattern());
      return map;
   }

   private void mergePatterns(String[] patterns, String method, SecurityConstraintBean scb, boolean isUncovered) throws DeploymentException {
      for(int i = 0; patterns != null && i < patterns.length; ++i) {
         String pattern = fixupURLPattern(patterns[i]);
         if (this.getSecurityContext().isInternalApp() && pattern.equals("/") && !getProvider().getEnforceStrictURLPattern()) {
            pattern = "/*";
         }

         if (isUncovered) {
            this.mergePolicyForUncoveredMethods(method, scb, pattern);
         } else {
            this.mergePolicy(this.createResourceConstraint(scb.getAuthConstraint(), scb.getUserDataConstraint(), pattern, method));
         }
      }

   }

   private void mergePatternsForOmittedMethods(WebResourceCollectionBean wrc, SecurityConstraintBean scb) throws DeploymentException {
      String[] omissionMethods = wrc.getHttpMethodOmissions();
      String[] patterns = wrc.getUrlPatterns();

      for(int i = 0; patterns != null && i < patterns.length; ++i) {
         String pattern = fixupURLPattern(patterns[i]);
         if (this.getSecurityContext().isInternalApp() && pattern.equals("/") && !getProvider().getEnforceStrictURLPattern()) {
            pattern = "/*";
         }

         this.mergePolicy(this.createResourceConstraint(scb.getAuthConstraint(), scb.getUserDataConstraint(), pattern, (String)null));
         String[] var7 = omissionMethods;
         int var8 = omissionMethods.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String method = var7[var9];
            if (this.constraintsForOmittedMethod == null) {
               this.constraintsForOmittedMethod = new ArrayList();
            }

            this.constraintsForOmittedMethod.add(this.createResourceConstraint((AuthConstraintBean)null, scb.getUserDataConstraint(), pattern, method));
         }
      }

   }

   private ResourceConstraint createResourceConstraint(AuthConstraintBean authCons, UserDataConstraintBean userDataCons, String pattern, String method) {
      ResourceConstraint rc = new ResourceConstraint(pattern, method);
      if (authCons != null) {
         rc.setRoles(authCons.getRoleNames());
      } else {
         rc.setUnrestricted();
      }

      if (userDataCons != null) {
         rc.setTransportGuarantee(ResourceConstraint.getTransportGuarantee(userDataCons.getTransportGuarantee()));
      }

      return rc;
   }

   protected void mergePolicies(WebAppBean webAppBean, SecurityConstraintBean[] scbs) throws DeploymentException {
      if (scbs != null && scbs.length >= 1) {
         if (this.constraintsMap == null) {
            this.constraintsMap = new HashMap();
         }

         SecurityConstraintBean[] uncoveredSecCons = this.findUncoveredHttpMethods(webAppBean, scbs, this.isDenyUncoveredMethodsSet());
         this.mergePoliciesInternal(scbs);
         this.processUncoveredSecCons(uncoveredSecCons);
         this.mergeRolesFromAllMethods();
         this.processConstraintsForOmittedMethod();
      }
   }

   private void mergeRolesFromAllMethods() {
      if (this.constraintsMap != null) {
         StandardURLMapping consForAllMethods = (StandardURLMapping)this.constraintsMap.get("");
         if (consForAllMethods != null) {
            Object[] var2 = consForAllMethods.values();
            int var3 = var2.length;

            label57:
            for(int var4 = 0; var4 < var3; ++var4) {
               Object o = var2[var4];
               ResourceConstraint rcForAllMethods = (ResourceConstraint)o;
               if (this.isRolesSpecified(rcForAllMethods)) {
                  String urlPatternForAllMethods = rcForAllMethods.getResourceId();
                  Iterator var8 = this.constraintsMap.values().iterator();

                  while(true) {
                     StandardURLMapping mapping;
                     do {
                        do {
                           if (!var8.hasNext()) {
                              continue label57;
                           }

                           mapping = (StandardURLMapping)var8.next();
                        } while(mapping == null);
                     } while(mapping == consForAllMethods);

                     Object[] var10 = mapping.values();
                     int var11 = var10.length;

                     for(int var12 = 0; var12 < var11; ++var12) {
                        Object obj = var10[var12];
                        ResourceConstraint rcForMethod = (ResourceConstraint)obj;
                        if (rcForMethod.getResourceId().equals(urlPatternForAllMethods) && this.isRolesSpecified(rcForMethod)) {
                           rcForMethod.addRoles(rcForAllMethods.getRoles());
                        }
                     }
                  }
               }
            }

         }
      }
   }

   private boolean isRolesSpecified(ResourceConstraint resourceConstraint) {
      if (resourceConstraint.isUnrestricted()) {
         return false;
      } else {
         String[] rolesForAllMethods = resourceConstraint.getRoles();
         return rolesForAllMethods != null && rolesForAllMethods.length > 0;
      }
   }

   private void processUncoveredSecCons(SecurityConstraintBean[] uncoveredSecCons) throws DeploymentException {
      if (this.isDenyUncoveredMethodsSet()) {
         SecurityConstraintBean[] var2 = uncoveredSecCons;
         int var3 = uncoveredSecCons.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            SecurityConstraintBean scb = var2[var4];
            WebResourceCollectionBean[] wrcs = scb.getWebResourceCollections();
            WebResourceCollectionBean[] var7 = wrcs;
            int var8 = wrcs.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               WebResourceCollectionBean wrc = var7[var9];
               String[] methods = wrc.getHttpMethods();
               if (methods != null && methods.length > 0) {
                  String[] var12 = methods;
                  int var13 = methods.length;

                  for(int var14 = 0; var14 < var13; ++var14) {
                     String method = var12[var14];
                     this.mergePatterns(wrc.getUrlPatterns(), method, scb, true);
                  }
               } else {
                  this.mergePatterns(wrc.getUrlPatterns(), (String)null, scb, true);
               }
            }
         }

      }
   }

   private void mergePoliciesInternal(SecurityConstraintBean[] scbs) throws DeploymentException {
      SecurityConstraintBean[] var2 = scbs;
      int var3 = scbs.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         SecurityConstraintBean scb = var2[var4];
         WebResourceCollectionBean[] wrcs = scb.getWebResourceCollections();
         WebResourceCollectionBean[] var7 = wrcs;
         int var8 = wrcs.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            WebResourceCollectionBean wrc = var7[var9];
            if (wrc != null) {
               String[] methods = wrc.getHttpMethods();
               String[] omissionMethods;
               if (methods != null && methods.length > 0) {
                  omissionMethods = methods;
                  int var13 = methods.length;

                  for(int var14 = 0; var14 < var13; ++var14) {
                     String method = omissionMethods[var14];
                     this.mergePatterns(wrc.getUrlPatterns(), method, scb, false);
                  }
               } else {
                  omissionMethods = wrc.getHttpMethodOmissions();
                  if (omissionMethods != null && omissionMethods.length != 0) {
                     this.mergePatternsForOmittedMethods(wrc, scb);
                  } else {
                     this.mergePatterns(wrc.getUrlPatterns(), (String)null, scb, false);
                  }
               }
            }
         }
      }

   }

   private SecurityConstraintBean[] findUncoveredHttpMethods(WebAppBean webappBean, SecurityConstraintBean[] secCons, boolean denyUncoveredHttpMethods) {
      Set coveredPatterns = new HashSet();
      Map urlMethodMap = new HashMap();
      Map urlOmittedMethodMap = new HashMap();
      List consForUncoveredHttpMethods = new ArrayList();
      SecurityConstraintBean[] var8 = secCons;
      int var9 = secCons.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         SecurityConstraintBean constraint = var8[var10];
         WebResourceCollectionBean[] collections = constraint.getWebResourceCollections();
         WebResourceCollectionBean[] var13 = collections;
         int var14 = collections.length;

         for(int var15 = 0; var15 < var14; ++var15) {
            WebResourceCollectionBean collection = var13[var15];
            String[] patterns = collection.getUrlPatterns();
            String[] methods = collection.getHttpMethods();
            String[] omittedMethods = collection.getHttpMethodOmissions();
            int var22;
            if (methods != null && methods.length != 0 || omittedMethods != null && omittedMethods.length != 0) {
               List omNew = null;
               if (omittedMethods != null && omittedMethods.length != 0) {
                  omNew = Arrays.asList(omittedMethods);
               }

               String[] var42 = patterns;
               var22 = patterns.length;

               for(int var43 = 0; var43 < var22; ++var43) {
                  String pattern = var42[var43];
                  if (!coveredPatterns.contains(pattern)) {
                     if (methods != null && methods.length != 0) {
                        Set m = (Set)urlMethodMap.get(pattern);
                        if (m == null) {
                           m = new HashSet();
                           urlMethodMap.put(pattern, m);
                        }

                        String[] var26 = methods;
                        int var27 = methods.length;

                        for(int var28 = 0; var28 < var27; ++var28) {
                           String method = var26[var28];
                           ((Set)m).add(method);
                        }
                     } else {
                        Set om = (Set)urlOmittedMethodMap.get(pattern);
                        if (om == null) {
                           Set om = new HashSet();
                           urlOmittedMethodMap.put(pattern, om);
                           om.addAll(omNew);
                        } else {
                           om.retainAll(omNew);
                        }
                     }
                  }
               }
            } else {
               String[] var20 = patterns;
               int var21 = patterns.length;

               for(var22 = 0; var22 < var21; ++var22) {
                  String pattern = var20[var22];
                  coveredPatterns.add(pattern);
               }
            }
         }
      }

      Iterator var30 = urlMethodMap.entrySet().iterator();

      while(true) {
         Map.Entry entry;
         String pattern;
         Set omittedMethods;
         while(var30.hasNext()) {
            entry = (Map.Entry)var30.next();
            pattern = (String)entry.getKey();
            if (coveredPatterns.contains(pattern)) {
               urlOmittedMethodMap.remove(pattern);
            } else {
               omittedMethods = (Set)urlOmittedMethodMap.remove(pattern);
               Set methods = (Set)entry.getValue();
               StringBuilder msg;
               Iterator var38;
               String method;
               if (omittedMethods == null) {
                  msg = new StringBuilder();
                  var38 = methods.iterator();

                  while(var38.hasNext()) {
                     method = (String)var38.next();
                     msg.append(method);
                     msg.append(' ');
                  }

                  if (denyUncoveredHttpMethods) {
                     consForUncoveredHttpMethods.add(this.createSecurityConstraintForUncoveredHttpMethods(webappBean, pattern, methods, true));
                  } else {
                     HTTPLogger.logUncoveredHttpMethods(pattern, this.securityContext.getContextName(), msg.toString());
                  }
               } else {
                  omittedMethods.removeAll(methods);
                  if (omittedMethods.size() > 0) {
                     msg = new StringBuilder();
                     var38 = omittedMethods.iterator();

                     while(var38.hasNext()) {
                        method = (String)var38.next();
                        msg.append(method);
                        msg.append(' ');
                     }

                     if (denyUncoveredHttpMethods) {
                        consForUncoveredHttpMethods.add(this.createSecurityConstraintForUncoveredHttpMethods(webappBean, pattern, omittedMethods, false));
                     } else {
                        HTTPLogger.logUncoveredHttpOmittedMethods(pattern, this.securityContext.getContextName(), msg.toString());
                     }
                  }
               }
            }
         }

         var30 = urlOmittedMethodMap.entrySet().iterator();

         while(true) {
            do {
               do {
                  if (!var30.hasNext()) {
                     return (SecurityConstraintBean[])consForUncoveredHttpMethods.toArray(new SecurityConstraintBean[consForUncoveredHttpMethods.size()]);
                  }

                  entry = (Map.Entry)var30.next();
                  pattern = (String)entry.getKey();
               } while(coveredPatterns.contains(pattern));

               omittedMethods = (Set)entry.getValue();
            } while(omittedMethods.size() <= 0);

            StringBuilder msg = new StringBuilder();
            Iterator var37 = omittedMethods.iterator();

            while(var37.hasNext()) {
               String method = (String)var37.next();
               msg.append(method);
               msg.append(' ');
            }

            if (denyUncoveredHttpMethods) {
               consForUncoveredHttpMethods.add(this.createSecurityConstraintForUncoveredHttpMethods(webappBean, pattern, omittedMethods, false));
            } else {
               HTTPLogger.logUncoveredHttpOmittedMethods(pattern, this.securityContext.getContextName(), msg.toString());
            }
         }
      }
   }

   private SecurityConstraintBean createSecurityConstraintForUncoveredHttpMethods(WebAppBean webappBean, String pattern, Set methods, boolean isOmitted) {
      SecurityConstraintBean scb = webappBean.createSecurityConstraint();
      WebResourceCollectionBean wrcb = scb.createWebResourceCollection();
      Iterator var7 = methods.iterator();

      while(var7.hasNext()) {
         String method = (String)var7.next();
         if (isOmitted) {
            wrcb.addHttpMethodOmission(method);
         } else {
            wrcb.addHttpMethod(method);
         }
      }

      wrcb.addUrlPattern(pattern);
      wrcb.setWebResourceName("deny-uncovered-http-methods");
      scb.createAuthConstraint();
      return scb;
   }

   protected void deployPolicies() throws DeploymentException {
      if (this.constraintsMap != null) {
         Iterator var1 = this.constraintsMap.values().iterator();

         while(var1.hasNext()) {
            StandardURLMapping servletMapping = (StandardURLMapping)var1.next();
            Object[] objs = servletMapping.values();

            for(int i = 0; i < objs.length; ++i) {
               this.deploy((ResourceConstraint)objs[i]);
            }
         }

      }
   }

   private void deploy(ResourceConstraint con) throws DeploymentException {
      try {
         if (con.isUnrestricted()) {
            this.appSecurity.deployUncheckedPolicy(con.getResourceId(), con.getHttpMethod());
         } else {
            String[] conRoles = con.getRoles();
            if (conRoles != null && conRoles.length >= 1) {
               for(int i = 0; i < conRoles.length; ++i) {
                  if (conRoles[i].equals("*")) {
                     if (this.getSecurityContext().isAllowAllRoles()) {
                        con.setLoginRequired();
                        if (this.isFullSecurityDelegationRequired()) {
                           this.appSecurity.deployUncheckedPolicy(con.getResourceId(), con.getHttpMethod());
                        }

                        return;
                     }

                     Set allRoles = new HashSet(this.roleNames.size());
                     allRoles.addAll(this.roleNames);
                     if (allRoles.contains("**") && !this.isAnyAuthUserRoleDefinedInDD()) {
                        allRoles.remove("**");
                     }

                     if (allRoles.size() == 0) {
                        con.setForbidden();
                        this.appSecurity.deployExcludedPolicy(con.getResourceId(), con.getHttpMethod());
                        return;
                     }

                     String[] s = new String[allRoles.size()];
                     allRoles.toArray(s);
                     con.setRoles(s);
                     break;
                  }
               }

               this.getApplicationSecurity().deployPolicy(con.getResourceId(), con.getHttpMethod(), con.getRoles());
            } else {
               con.setForbidden();
               this.appSecurity.deployExcludedPolicy(con.getResourceId(), con.getHttpMethod());
            }
         }
      } catch (DeploymentException var6) {
         con.setForbidden();
         HTTPLogger.logCouldNotDeployPolicy(con.getResourceId(), var6);
         throw new DeploymentException(var6);
      }
   }

   public void unregisterRolesAndPolicies() {
      super.unregisterRolesAndPolicies();

      try {
         this.appSecurity.unregisterPolicies();
      } catch (DeploymentException var3) {
         HTTPLogger.logFailedToUndeploySecurityPolicy(this.getSecurityContext().getLogContext(), var3);
      }

      try {
         this.appSecurity.unregisterRoles();
      } catch (DeploymentException var2) {
         HTTPLogger.logFailedToUndeploySecurityRole(this.getSecurityContext().getLogContext(), var2);
      }

   }

   public ResourceConstraint getConstraint(HttpServletRequest req) {
      return this.getConstraint(getRelativeURI(req), req.getMethod());
   }

   private ResourceConstraint getConstraint(String relURI, String method) {
      if (this.constraintsMap == null) {
         return null;
      } else {
         StandardURLMapping consForAllMethods = (StandardURLMapping)this.constraintsMap.get("");
         StandardURLMapping consForOneMethod = (StandardURLMapping)this.constraintsMap.get(method);
         ResourceConstraint rcForAllMethods = consForAllMethods == null ? null : (ResourceConstraint)consForAllMethods.get(relURI);
         ResourceConstraint rcForOneMethod = consForOneMethod == null ? null : (ResourceConstraint)consForOneMethod.get(relURI);
         if (rcForAllMethods == null && rcForOneMethod == null) {
            return null;
         } else if (rcForAllMethods == null && rcForOneMethod != null) {
            return rcForOneMethod;
         } else if (rcForAllMethods != null && rcForOneMethod == null) {
            return rcForAllMethods;
         } else if (rcForAllMethods.getResourceId().equals(rcForOneMethod.getResourceId())) {
            return rcForOneMethod;
         } else {
            StandardURLMapping map = this.createStandardURLMapping();
            map.put(rcForOneMethod.getResourceId(), rcForOneMethod);
            map.put(rcForAllMethods.getResourceId(), rcForAllMethods);
            return (ResourceConstraint)map.get(relURI);
         }
      }
   }

   public boolean isSubjectInRole(SubjectHandle subject, String rolename, HttpServletRequest request, HttpServletResponse response, ServletConfig servletConfig) {
      String roleLink = this.getRequestFacade().getRoleLink(servletConfig, rolename);
      if (roleLink != null) {
         rolename = roleLink;
      }

      if (subject == null) {
         subject = getProvider().getAnonymousSubject();
      }

      return this.appSecurity.isSubjectInRole(subject, rolename, request, response, (String)null);
   }

   public void registerRoleRefs(ServletConfig servletConfig) {
   }

   protected final boolean checkTransport(ResourceConstraint cons, HttpServletRequest req, HttpServletResponse rsp) throws IOException {
      if (cons == null) {
         return true;
      } else if (cons.getTransportGuarantee() != 0 && !req.isSecure()) {
         String securedURL = this.getSecuredURL(req, rsp, req.getRequestURI());
         if (securedURL != null) {
            rsp.sendRedirect(rsp.encodeURL(securedURL));
         } else {
            rsp.sendError(403);
         }

         return false;
      } else {
         return true;
      }
   }

   public boolean isSSLRequired(String relativeURI, String method) {
      ResourceConstraint cons = this.getConstraint(relativeURI, method);
      return cons != null && cons.getTransportGuarantee() != 0;
   }

   protected void deployRoleLink(ServletConfig servletConfig, String roleName, String roleLink) {
      this.getSecurityContext().getRequestFacade().addRoleLinkTo(servletConfig, roleName, roleLink);
   }
}
