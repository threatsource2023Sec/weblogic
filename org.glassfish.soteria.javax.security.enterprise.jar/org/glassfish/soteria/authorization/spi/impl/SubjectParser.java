package org.glassfish.soteria.authorization.spi.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.Principal;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.ejb.EJBContext;
import javax.enterprise.inject.spi.CDI;
import javax.security.auth.Subject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.soteria.authorization.EJB;

public class SubjectParser {
   private static Object geronimoPolicyConfigurationFactoryInstance;
   private static ConcurrentMap geronimoContextToRoleMapping;
   private Map groupToRoles = new HashMap();
   private boolean isJboss;
   private boolean isLiberty;
   private boolean oneToOneMapping;
   private boolean anyAuthenticatedUserRoleMapped = false;

   public static void onFactoryCreated() {
      tryInitGeronimo();
   }

   private static void tryInitGeronimo() {
      try {
         geronimoPolicyConfigurationFactoryInstance = Class.forName("org.apache.geronimo.security.jacc.mappingprovider.GeronimoPolicyConfigurationFactory").newInstance();
         geronimoContextToRoleMapping = new ConcurrentHashMap();
      } catch (Exception var1) {
      }

   }

   public static void onPolicyConfigurationCreated(final String contextID) {
      if (geronimoPolicyConfigurationFactoryInstance != null) {
         try {
            Class geronimoPolicyConfigurationClass = Class.forName("org.apache.geronimo.security.jacc.mappingprovider.GeronimoPolicyConfiguration");
            Object geronimoPolicyConfigurationProxy = Proxy.newProxyInstance(SubjectParser.class.getClassLoader(), new Class[]{geronimoPolicyConfigurationClass}, new InvocationHandler() {
               public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                  if (method.getName().equals("setPrincipalRoleMapping")) {
                     SubjectParser.geronimoContextToRoleMapping.put(contextID, (Map)args[0]);
                  }

                  return null;
               }
            });
            Class.forName("org.apache.geronimo.security.jacc.mappingprovider.GeronimoPolicyConfigurationFactory").getMethod("setPolicyConfiguration", String.class, geronimoPolicyConfigurationClass).invoke(geronimoPolicyConfigurationFactoryInstance, contextID, geronimoPolicyConfigurationProxy);
         } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException var3) {
         }
      }

   }

   public SubjectParser(String contextID, Collection allDeclaredRoles) {
      if (!this.tryGlassFish(contextID, allDeclaredRoles)) {
         if (!this.tryJBoss()) {
            if (!this.tryLiberty()) {
               if (!this.tryWebLogic(contextID, allDeclaredRoles)) {
                  if (!this.tryGeronimo(contextID, allDeclaredRoles)) {
                     this.oneToOneMapping = true;
                  }
               }
            }
         }
      }
   }

   public List getMappedRolesFromPrincipals(Principal[] principals) {
      return this.getMappedRolesFromPrincipals((Iterable)Arrays.asList(principals));
   }

   public boolean isAnyAuthenticatedUserRoleMapped() {
      return this.anyAuthenticatedUserRoleMapped;
   }

   public Principal getCallerPrincipalFromPrincipals(Iterable principals) {
      if (this.isJboss) {
         try {
            Subject subject = (Subject)PolicyContext.getContext("javax.security.auth.Subject.container");
            return subject == null ? null : this.doGetCallerPrincipalFromPrincipals(subject.getPrincipals());
         } catch (PolicyContextException var3) {
            return null;
         }
      } else {
         return this.doGetCallerPrincipalFromPrincipals(principals);
      }
   }

   public List getMappedRolesFromPrincipals(Iterable principals) {
      List groups = null;
      if (!this.isLiberty && !this.isJboss) {
         groups = this.getGroupsFromPrincipals(principals);
      } else {
         try {
            Subject subject = (Subject)PolicyContext.getContext("javax.security.auth.Subject.container");
            if (subject == null) {
               return Collections.emptyList();
            }

            if (this.isLiberty) {
               Set tables = subject.getPrivateCredentials(Hashtable.class);
               if (tables != null && !tables.isEmpty()) {
                  Hashtable table = (Hashtable)tables.iterator().next();
                  groups = (List)table.get("com.ibm.wsspi.security.cred.groups");
               }
            } else {
               groups = this.getGroupsFromPrincipals(subject.getPrincipals());
            }
         } catch (Exception var6) {
            var6.printStackTrace();
         }
      }

      return this.mapGroupsToRoles(groups);
   }

   private List mapGroupsToRoles(List groups) {
      if (this.oneToOneMapping) {
         return groups;
      } else {
         List roles = new ArrayList();
         Iterator var3 = groups.iterator();

         while(var3.hasNext()) {
            String group = (String)var3.next();
            if (this.groupToRoles.containsKey(group)) {
               roles.addAll((Collection)this.groupToRoles.get(group));
            }
         }

         return roles;
      }
   }

   private boolean tryJBoss() {
      try {
         Class.forName("org.jboss.as.security.service.JaccService", false, Thread.currentThread().getContextClassLoader());
         this.isJboss = true;
         this.oneToOneMapping = true;
         return true;
      } catch (Exception var2) {
         return false;
      }
   }

   private boolean tryLiberty() {
      this.isLiberty = System.getProperty("wlp.server.name") != null;
      this.oneToOneMapping = true;
      return this.isLiberty;
   }

   private boolean tryGlassFish(String contextID, Collection allDeclaredRoles) {
      try {
         Class SecurityRoleMapperFactoryClass = Class.forName("org.glassfish.deployment.common.SecurityRoleMapperFactory");
         Object factoryInstance = Class.forName("org.glassfish.internal.api.Globals").getMethod("get", SecurityRoleMapperFactoryClass.getClass()).invoke((Object)null, SecurityRoleMapperFactoryClass);
         Object securityRoleMapperInstance = SecurityRoleMapperFactoryClass.getMethod("getRoleMapper", String.class).invoke(factoryInstance, contextID);
         Map roleToSubjectMap = (Map)Class.forName("org.glassfish.deployment.common.SecurityRoleMapper").getMethod("getRoleToSubjectMapping").invoke(securityRoleMapperInstance);
         Iterator var7 = allDeclaredRoles.iterator();

         while(true) {
            String role;
            do {
               if (!var7.hasNext()) {
                  return true;
               }

               role = (String)var7.next();
            } while(!roleToSubjectMap.containsKey(role));

            Set principals = ((Subject)roleToSubjectMap.get(role)).getPrincipals();
            List groups = this.getGroupsFromPrincipals(principals);

            String group;
            for(Iterator var11 = groups.iterator(); var11.hasNext(); ((List)this.groupToRoles.get(group)).add(role)) {
               group = (String)var11.next();
               if (!this.groupToRoles.containsKey(group)) {
                  this.groupToRoles.put(group, new ArrayList());
               }
            }

            if ("**".equals(role) && !groups.isEmpty()) {
               this.anyAuthenticatedUserRoleMapped = true;
            }
         }
      } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException var13) {
         return false;
      }
   }

   private boolean tryWebLogic(String contextID, Collection allDeclaredRoles) {
      try {
         Class roleMapperFactoryClass = Class.forName("weblogic.security.jacc.RoleMapperFactory");
         Object roleMapperFactoryInstance = roleMapperFactoryClass.getMethod("getRoleMapperFactory").invoke((Object)null);
         Object roleMapperInstance = roleMapperFactoryClass.getMethod("getRoleMapperForContextID", String.class).invoke(roleMapperFactoryInstance, contextID);
         Map roleToPrincipalNamesMap = (Map)Class.forName("weblogic.security.jacc.simpleprovider.RoleMapperImpl").getMethod("getRolesToPrincipalNames").invoke(roleMapperInstance);
         Iterator var7 = allDeclaredRoles.iterator();

         while(true) {
            String role;
            do {
               if (!var7.hasNext()) {
                  return true;
               }

               role = (String)var7.next();
            } while(!roleToPrincipalNamesMap.containsKey(role));

            List groupsOrUserNames = Arrays.asList((Object[])roleToPrincipalNamesMap.get(role));
            String[] var10 = (String[])roleToPrincipalNamesMap.get(role);
            int var11 = var10.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               String groupOrUserName = var10[var12];
               if (!this.groupToRoles.containsKey(groupOrUserName)) {
                  this.groupToRoles.put(groupOrUserName, new ArrayList());
               }

               ((List)this.groupToRoles.get(groupOrUserName)).add(role);
            }

            if ("**".equals(role) && !groupsOrUserNames.isEmpty()) {
               this.anyAuthenticatedUserRoleMapped = true;
            }
         }
      } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException var14) {
         return false;
      }
   }

   private boolean tryGeronimo(String contextID, Collection allDeclaredRoles) {
      if (geronimoContextToRoleMapping == null) {
         return false;
      } else {
         if (geronimoContextToRoleMapping.containsKey(contextID)) {
            Map principalsToRoles = (Map)geronimoContextToRoleMapping.get(contextID);
            Iterator var4 = principalsToRoles.entrySet().iterator();

            while(var4.hasNext()) {
               Map.Entry entry = (Map.Entry)var4.next();
               Iterator var6 = this.principalToGroups((Principal)entry.getKey()).iterator();

               while(var6.hasNext()) {
                  String group = (String)var6.next();
                  if (!this.groupToRoles.containsKey(group)) {
                     this.groupToRoles.put(group, new ArrayList());
                  }

                  ((List)this.groupToRoles.get(group)).addAll((Collection)entry.getValue());
                  if (((Set)entry.getValue()).contains("**")) {
                     this.anyAuthenticatedUserRoleMapped = true;
                  }
               }
            }
         }

         return true;
      }
   }

   public List getGroupsFromPrincipals(Iterable principals) {
      List groups = new ArrayList();
      Iterator var3 = principals.iterator();

      Principal principal;
      do {
         if (!var3.hasNext()) {
            return groups;
         }

         principal = (Principal)var3.next();
      } while(!this.principalToGroups(principal, groups));

      return groups;
   }

   public List principalToGroups(Principal principal) {
      List groups = new ArrayList();
      this.principalToGroups(principal, groups);
      return groups;
   }

   private Principal doGetCallerPrincipalFromPrincipals(Iterable principals) {
      try {
         return ((HttpServletRequest)CDI.current().select(HttpServletRequest.class, new Annotation[0]).get()).getUserPrincipal();
      } catch (Exception var6) {
         EJBContext ejbContext = EJB.getEJBContext();
         if (ejbContext != null) {
            return this.getVendorCallerPrincipal(ejbContext.getCallerPrincipal(), true);
         } else {
            Iterator var3 = principals.iterator();

            Principal vendorCallerPrincipal;
            do {
               if (!var3.hasNext()) {
                  return null;
               }

               Principal principal = (Principal)var3.next();
               vendorCallerPrincipal = this.getVendorCallerPrincipal(principal, false);
            } while(vendorCallerPrincipal == null);

            return vendorCallerPrincipal;
         }
      }
   }

   private Principal getVendorCallerPrincipal(Principal principal, boolean isEjb) {
      switch (principal.getClass().getName()) {
         case "org.glassfish.security.common.PrincipalImpl":
            return this.getAuthenticatedPrincipal(principal, "ANONYMOUS", isEjb);
         case "weblogic.security.principal.WLSUserImpl":
            return this.getAuthenticatedPrincipal(principal, "<anonymous>", isEjb);
         case "com.ibm.ws.security.authentication.principals.WSPrincipal":
            return this.getAuthenticatedPrincipal(principal, "UNAUTHENTICATED", isEjb);
         case "org.jboss.security.SimplePrincipal":
            return this.getAuthenticatedPrincipal(principal, "anonymous", isEjb);
         case "org.jboss.security.SimpleGroup":
            if (principal.getName().equals("CallerPrincipal") && principal instanceof Group) {
               Enumeration groupMembers = ((Group)principal).members();
               if (groupMembers.hasMoreElements()) {
                  return this.getAuthenticatedPrincipal((Principal)groupMembers.nextElement(), "anonymous", isEjb);
               }
            }
            break;
         case "org.apache.tomee.catalina.TomcatSecurityService$TomcatUser":
            try {
               Principal tomeePrincipal = (Principal)Class.forName("org.apache.catalina.realm.GenericPrincipal").getMethod("getUserPrincipal").invoke(Class.forName("org.apache.tomee.catalina.TomcatSecurityService$TomcatUser").getMethod("getTomcatPrincipal").invoke(principal));
               return this.getAuthenticatedPrincipal(tomeePrincipal, "guest", isEjb);
            } catch (Exception var6) {
            }
      }

      return CallerPrincipal.class.isAssignableFrom(principal.getClass()) ? principal : null;
   }

   private Principal getAuthenticatedPrincipal(Principal principal, String anonymousCallerName, boolean isEjb) {
      return isEjb && anonymousCallerName.equals(principal.getName()) ? null : principal;
   }

   public boolean principalToGroups(Principal principal, List groups) {
      switch (principal.getClass().getName()) {
         case "org.glassfish.security.common.Group":
         case "org.apache.geronimo.security.realm.providers.GeronimoGroupPrincipal":
         case "weblogic.security.principal.WLSGroupImpl":
         case "jeus.security.resource.GroupPrincipalImpl":
            groups.add(principal.getName());
            break;
         case "org.jboss.security.SimpleGroup":
            if (principal.getName().equals("Roles") && principal instanceof Group) {
               Group rolesGroup = (Group)principal;
               Iterator var6 = Collections.list(rolesGroup.members()).iterator();

               while(var6.hasNext()) {
                  Principal groupPrincipal = (Principal)var6.next();
                  groups.add(groupPrincipal.getName());
               }

               return true;
            }
         case "org.apache.tomee.catalina.TomcatSecurityService$TomcatUser":
            try {
               groups.addAll(Arrays.asList((String[])((String[])Class.forName("org.apache.catalina.realm.GenericPrincipal").getMethod("getRoles").invoke(Class.forName("org.apache.tomee.catalina.TomcatSecurityService$TomcatUser").getMethod("getTomcatPrincipal").invoke(principal)))));
            } catch (Exception var8) {
            }
      }

      return false;
   }
}
