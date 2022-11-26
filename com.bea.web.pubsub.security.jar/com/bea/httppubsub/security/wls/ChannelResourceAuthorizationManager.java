package com.bea.httppubsub.security.wls;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.security.service.PolicyConsumerService;
import com.bea.common.security.service.RoleConsumerService;
import com.bea.httppubsub.PubSubLogger;
import com.bea.httppubsub.security.ChannelAuthorizationManager;
import com.bea.httppubsub.security.ChannelResource;
import com.bea.httppubsub.security.ChannelAuthorizationManager.Action;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import weblogic.common.internal.VersionInfo;
import weblogic.security.service.ConsumptionException;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.spi.Resource;

public final class ChannelResourceAuthorizationManager extends AbstractAuthorizationManager {
   private static final SimpleDateFormat timeFormatter = getTimeFormatter();
   private static final String version = VersionInfo.theOne().getReleaseVersion();
   static final String[] EXCLUDED_USER = new String[]{"ExcludedPolicyPubSubUser"};
   private ChannelPolicyConsumer channelPolicyConsumer;
   private ChannelRoleConsumer channelRoleConsumer;
   private ChannelPolicyHandler channelPolicyHandler;
   private ChannelRoleHandler channelRoleHandler;
   private boolean deployExcludedRole;

   public void beginPolicyAndRoleDeployment() throws ConsumptionException, ServiceInitializationException {
      PolicyConsumerService policyConsumerService = SecurityServiceManager.getPolicyConsumerService(KERNEL_ID);
      RoleConsumerService roleConsumerService = SecurityServiceManager.getRoleConsumerService(KERNEL_ID);
      if (this.DEBUG) {
         this.dbg("PolicyConsumerService:" + policyConsumerService + " RoleConsumerService:" + roleConsumerService);
      }

      String date = timeFormatter.format(new Date());
      this.channelPolicyConsumer = new ChannelPolicyConsumer(policyConsumerService);
      if (this.DEBUG) {
         this.dbg("ChannelPolicyConsumer:" + this.channelPolicyConsumer);
      }

      String handlerName = this.appName + ":" + this.webName + ":" + this.pubsubName;
      this.channelPolicyHandler = this.channelPolicyConsumer.isEnabled() ? this.channelPolicyConsumer.getPolicyHandler(handlerName, version, date) : null;
      if (this.DEBUG) {
         this.dbg("ChannelPolicyHandler:" + this.channelPolicyHandler);
      }

      this.channelRoleConsumer = new ChannelRoleConsumer(roleConsumerService);
      if (this.DEBUG) {
         this.dbg("ChannelRoleConsumer:" + this.channelRoleConsumer);
      }

      this.channelRoleHandler = this.channelRoleConsumer.isEnabled() ? this.channelRoleConsumer.getRoleHandler(handlerName, version, date) : null;
      if (this.DEBUG) {
         this.dbg("ChannelRoleHandler:" + this.channelRoleHandler);
      }

   }

   public void deployDefaultPolicies() throws ConsumptionException {
      Resource resource = this.createResource((String)null, Action.SUBSCRIBE);
      if (this.DEBUG) {
         this.dbg("Deploying default policy: " + resource);
      }

      this.deployUncheckedPolicy(resource);
   }

   public void deployPolicy(Resource resource, String[] roles) throws ConsumptionException {
      this.channelPolicyHandler.setPolicy(resource, roles);
   }

   public void deployExcludedPolicy(Resource resource) throws ConsumptionException {
      this.channelPolicyHandler.setPolicy(resource, EXCLUDED_USER);
      this.deployExcludedRole = true;
   }

   public void deployUncheckedPolicy(Resource resource) throws ConsumptionException {
      this.channelPolicyHandler.setUncheckedPolicy(resource);
   }

   public void deployRoles(Set roles, Map roleMapping) throws ConsumptionException {
      Resource resource = this.createResource("/**", (ChannelAuthorizationManager.Action)null);
      Iterator var4 = roles.iterator();

      while(true) {
         while(var4.hasNext()) {
            String role = (String)var4.next();
            if (role.equals(EXCLUDED_USER[0])) {
               throw new IllegalStateException("Role name reserved - " + role);
            }

            String[] principals = roleMapping != null ? (String[])roleMapping.get(role) : null;
            if (principals == null) {
               this.channelRoleHandler.setRole(resource, role, new String[]{role});
               PubSubLogger.logImplicitRoleDeployment(role);
            } else if (principals.length == 1 && (principals[0] == null || principals[0].length() == 0)) {
               if (this.DEBUG) {
                  this.dbg("Ignoring deploy role GLOBAL - " + role);
               }
            } else {
               this.channelRoleHandler.setRole(resource, role, principals);
               if (this.DEBUG) {
                  this.dbg("deploy role:" + role + " principals:" + Arrays.toString(principals));
               }
            }
         }

         return;
      }
   }

   public void endPolicyAndRoleDeployment() throws ConsumptionException {
      if (this.channelPolicyHandler != null) {
         this.channelPolicyHandler.done();
      }

      if (this.channelRoleHandler != null) {
         this.channelRoleHandler.done();
      }

   }

   public Resource createResource(String pattern, ChannelAuthorizationManager.Action op) {
      return new ChannelResource(this.appName, this.webName, op == null ? null : op.toString(), pattern);
   }

   private static final SimpleDateFormat getTimeFormatter() {
      SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
      formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
      return formatter;
   }
}
