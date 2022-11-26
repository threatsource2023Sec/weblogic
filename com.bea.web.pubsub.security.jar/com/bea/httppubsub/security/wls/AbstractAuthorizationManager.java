package com.bea.httppubsub.security.wls;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.httppubsub.AuthenticatedUser;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.PubSubContext;
import com.bea.httppubsub.descriptor.ChannelConstraintBean;
import com.bea.httppubsub.descriptor.ChannelResourceCollectionBean;
import com.bea.httppubsub.security.ChannelAuthorizationManager;
import com.bea.httppubsub.security.ChannelResourceConstraint;
import com.bea.httppubsub.security.ChannelResourceConstraintImpl;
import com.bea.httppubsub.security.ChannelAuthorizationManager.Action;
import com.bea.httppubsub.util.ChannelMapping;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.SecurityRoleAssignmentBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.ConsumptionException;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.spi.Resource;
import weblogic.security.utils.ResourceIDDContextWrapper;
import weblogic.servlet.httppubsub.PubSubHelper;
import weblogic.utils.http.HttpParsing;

public abstract class AbstractAuthorizationManager implements ChannelAuthorizationManager {
   protected final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugPubSubSecurity");
   protected final boolean DEBUG;
   private static final String WLS_PUBSUB_HELPER_CLASS = "weblogic.servlet.httppubsub.WlsPubSubHelper";
   protected static PubSubHelper helper;
   protected static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final AuthorizationManager authorizer;
   protected String appName;
   protected String webName;
   protected String pubsubName;
   private List channelConstraints;
   private HashMap actionConstraintsMap;
   private boolean useCustomRoles;
   private boolean useCustomPolicies;
   protected boolean isInitialized;

   public AbstractAuthorizationManager() {
      this.DEBUG = this.debugLogger.isDebugEnabled();
      this.channelConstraints = new ArrayList();
      this.actionConstraintsMap = null;
      this.useCustomRoles = false;
      this.useCustomPolicies = false;
   }

   public final void initialize(PubSubContext context) {
      try {
         if (helper == null) {
            helper = initializePubSubHelper();
         }
      } catch (Exception var3) {
         throw new IllegalStateException("Error initializing WLS PubSubHelper", var3);
      }

      if (context.getServletContext() == null) {
         throw new IllegalStateException("ServletContext cannot be null");
      } else if (!helper.getSecurityModel(context.getServletContext()).equals("DDOnly")) {
         throw new IllegalStateException("web application has security model different from DDOnly. PubSub Server requires a DDOnly model configured for the webapp.");
      } else {
         this.initialize(helper.getApplicationName(context.getServletContext()), helper.getContextPath(context.getServletContext()), context.getServer().getName(), context.getConfig().getChannelConstraints(), this.processRoleMappings(context.getServletContext()));
      }
   }

   public void initialize(String appName, String webName, String pubsubName, ChannelConstraintBean[] ccb, Map roleMappings) {
      this.appName = appName;
      this.webName = webName;
      this.pubsubName = pubsubName;
      Set roles = this.processChannelConstraints(ccb);
      if (this.useCustomPolicies) {
         if (this.DEBUG) {
            this.dbg("Ignoring DD roles & policies. Using custom");
         }

      } else {
         try {
            if (this.DEBUG) {
               this.dbg("Commencing policy & role deployment");
            }

            this.beginPolicyAndRoleDeployment();
            if (this.DEBUG) {
               this.dbg("Deploying default policies");
            }

            this.deployDefaultPolicies();
            if (this.DEBUG) {
               this.dbg("Deploying policies");
            }

            this.deployPolicies();
            if (!this.useCustomRoles) {
               if (this.DEBUG) {
                  this.dbg("Deploying roles");
               }

               this.deployRoles(roles, roleMappings);
               return;
            }

            if (this.DEBUG) {
               this.dbg("Ignoring DD roles. Using custom roles");
            }
         } catch (ConsumptionException var19) {
            var19.printStackTrace();
            return;
         } catch (ServiceInitializationException var20) {
            var20.printStackTrace();
            return;
         } finally {
            if (this.DEBUG) {
               this.dbg("Ending policy & role deployment");
            }

            try {
               this.endPolicyAndRoleDeployment();
            } catch (ConsumptionException var18) {
               var18.printStackTrace();
               if (this.DEBUG) {
                  this.dbg("Error ending policy & role deployment");
               }
            }

         }

      }
   }

   private static PubSubHelper initializePubSubHelper() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
      ClassLoader cl = AbstractAuthorizationManager.class.getClassLoader();
      Class c = cl == null ? Class.forName("weblogic.servlet.httppubsub.WlsPubSubHelper") : cl.loadClass("weblogic.servlet.httppubsub.WlsPubSubHelper");
      PubSubHelper helper = (PubSubHelper)c.newInstance();
      return helper;
   }

   public final boolean hasPermission(Client client, String channelPattern, ChannelAuthorizationManager.Action action) {
      AuthenticatedSubject subject = null;
      AuthenticatedUser authUser = client.getAuthenticatedUser();
      if (authUser != null) {
         HttpSession session = authUser.getSession();
         subject = (AuthenticatedSubject)helper.getAuthSubject(session);
      }

      if (subject == null) {
         subject = SubjectUtils.getAnonymousSubject();
      }

      return this.hasPermission0(subject, channelPattern, action);
   }

   public boolean hasPermission0(AuthenticatedSubject subject, String channel, ChannelAuthorizationManager.Action action) {
      Resource resource = this.createResource(channel, action);
      boolean authorized = authorizer.isAccessAllowed(subject, resource, new ResourceIDDContextWrapper());
      if (this.DEBUG) {
         this.dbg("isAccessAllowed invoked with subject:" + subject + " resource:" + resource + " action:" + action + " result:" + authorized);
      }

      return authorized;
   }

   public void destroy() {
      if (this.DEBUG) {
         this.dbg("Undeploying all roles and policies");
      }

      this.undeployRolesAndPolicies();
   }

   protected static String checkChannelURI(String channel) {
      if (channel.equals("/")) {
         throw new IllegalStateException("Root channel cannot be referenced");
      } else {
         if (!channel.startsWith("/")) {
            channel = "/" + channel;
         }

         if (channel.endsWith("/")) {
            channel = channel.substring(0, channel.length() - 1);
         }

         return channel;
      }
   }

   public Set processChannelConstraints(ChannelConstraintBean[] channelConstraints) {
      Set roles = new HashSet();

      for(int i = 0; i < channelConstraints.length; ++i) {
         this.processChannelConstraint(channelConstraints[i], roles);
      }

      return roles;
   }

   private Map processRoleMappings(ServletContext context) {
      WeblogicWebAppBean wlxml = (WeblogicWebAppBean)helper.getWeblogicWebAppBean(context);
      if (wlxml == null) {
         return null;
      } else {
         SecurityRoleAssignmentBean[] sr = wlxml.getSecurityRoleAssignments();
         if (sr != null && sr.length != 0) {
            Map roleMapping = new HashMap();

            for(int i = 0; i < sr.length; ++i) {
               String role = sr[i].getRoleName();
               if (sr[i].getExternallyDefined() != null) {
                  String[] mapping = new String[]{null};
                  roleMapping.put(role, mapping);
               } else if (sr[i].getPrincipalNames() != null && sr[i].getPrincipalNames().length > 0) {
                  roleMapping.put(role, sr[i].getPrincipalNames());
               }
            }

            return roleMapping;
         } else {
            return null;
         }
      }
   }

   private void processChannelConstraint(ChannelConstraintBean ccb, Set roles) {
      ChannelResourceCollectionBean[] crcbs = ccb.getChannelResourceCollections();

      for(int i = 0; i < crcbs.length; ++i) {
         String[] operations = crcbs[i].getChannelOperations();
         if (operations == null || operations.length == 0) {
            this.mergePatterns(crcbs[i].getChannelPatterns(), (String)null, ccb, roles);
            return;
         }

         for(int j = 0; operations != null && j < operations.length; ++j) {
            this.mergePatterns(crcbs[i].getChannelPatterns(), operations[j], ccb, roles);
         }
      }

   }

   private void mergePatterns(String[] patterns, String action, ChannelConstraintBean scb, Set roles) {
      for(int i = 0; patterns != null && i < patterns.length; ++i) {
         String pattern = HttpParsing.ensureStartingSlash(patterns[i]);
         ChannelResourceConstraintImpl crc = new ChannelResourceConstraintImpl(pattern, action == null ? null : getAction(action), scb);
         this.channelConstraints.add(crc);
         if (crc.getRoles() != null) {
            roles.addAll(Arrays.asList(crc.getRoles()));
         }

         this.mergePolicy(crc);
      }

   }

   private void mergePolicy(ChannelResourceConstraintImpl curr) {
      ChannelAuthorizationManager.Action action = curr.getAction();
      if (action != null) {
         if (this.actionConstraintsMap == null) {
            this.actionConstraintsMap = new HashMap();
         }

         ChannelMapping map = (ChannelMapping)this.actionConstraintsMap.get(action);
         if (map == null) {
            map = new ChannelMapping(false);
            this.actionConstraintsMap.put(action, map);
            map.put(curr.getChannelPattern(), curr);
         } else {
            ChannelResourceConstraintImpl prev = (ChannelResourceConstraintImpl)map.remove(curr.getChannelPattern());
            if (prev != null) {
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

            map.put(curr.getChannelPattern(), curr);
         }
      }
   }

   protected static ChannelAuthorizationManager.Action getAction(String action) {
      if (Action.CREATE.toString().equalsIgnoreCase(action)) {
         return Action.CREATE;
      } else if (Action.DELETE.toString().equalsIgnoreCase(action)) {
         return Action.DELETE;
      } else if (Action.SUBSCRIBE.toString().equalsIgnoreCase(action)) {
         return Action.SUBSCRIBE;
      } else if (Action.PUBLISH.toString().equalsIgnoreCase(action)) {
         return Action.PUBLISH;
      } else {
         throw new IllegalArgumentException("Illegal action: " + action);
      }
   }

   private void deployPolicies() throws ConsumptionException {
      Iterator var1 = this.channelConstraints.iterator();

      while(var1.hasNext()) {
         ChannelResourceConstraint constraint = (ChannelResourceConstraint)var1.next();
         String pattern = constraint.getChannelPattern();
         ChannelAuthorizationManager.Action op = constraint.getAction();
         Resource resource = this.createResource(pattern, op);
         if (constraint.isUnrestricted()) {
            if (this.DEBUG) {
               this.dbg("UNRESTRICTED policy for " + resource + " " + pattern);
            }

            this.deployUncheckedPolicy(resource);
         } else if (constraint.isForbidden()) {
            if (this.DEBUG) {
               this.dbg("EXCLUDED policy for " + resource + " " + pattern);
            }

            this.deployExcludedPolicy(resource);
         } else {
            if (this.DEBUG) {
               this.dbg("policy for " + resource + " " + pattern + " " + Arrays.asList(constraint.getRoles()));
            }

            this.deployPolicy(resource, constraint.getRoles());
         }
      }

   }

   public abstract void beginPolicyAndRoleDeployment() throws ConsumptionException, ServiceInitializationException;

   public abstract void deployDefaultPolicies() throws ConsumptionException;

   public abstract void deployPolicy(Resource var1, String[] var2) throws ConsumptionException;

   public abstract void deployExcludedPolicy(Resource var1) throws ConsumptionException;

   public abstract void deployUncheckedPolicy(Resource var1) throws ConsumptionException;

   public abstract void deployRoles(Set var1, Map var2) throws ConsumptionException;

   public abstract void endPolicyAndRoleDeployment() throws ConsumptionException;

   public void undeployRolesAndPolicies() {
   }

   public abstract Resource createResource(String var1, ChannelAuthorizationManager.Action var2);

   protected void dbg(String s) {
      this.debugLogger.debug(this.getClass().getName() + " " + s);
   }

   static {
      authorizer = (AuthorizationManager)SecurityServiceManager.getSecurityService(KERNEL_ID, "weblogicDEFAULT", ServiceType.AUTHORIZE);
   }
}
