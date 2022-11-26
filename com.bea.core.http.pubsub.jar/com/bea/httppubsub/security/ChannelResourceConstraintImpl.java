package com.bea.httppubsub.security;

import com.bea.httppubsub.descriptor.ChannelConstraintBean;
import java.util.ArrayList;
import java.util.Arrays;

public final class ChannelResourceConstraintImpl implements ChannelResourceConstraint {
   private final String pattern;
   private final ChannelAuthorizationManager.Action action;
   private String[] roles = null;
   private boolean forbidden = false;
   private boolean unrestricted = false;

   public ChannelResourceConstraintImpl(String channelPattern, ChannelAuthorizationManager.Action action, ChannelConstraintBean ccb) {
      this.pattern = channelPattern;
      this.action = action;
      if (ccb.getAuthConstraint() != null) {
         this.roles = ccb.getAuthConstraint().getRoleNames();
         if (this.roles == null) {
            this.roles = new String[0];
         }

         if (this.roles.length < 1) {
            this.forbidden = true;
         }
      } else {
         this.unrestricted = true;
      }

   }

   public void addRoles(String[] newRoles) {
      String[] allRoles = new String[this.roles.length + newRoles.length];
      ArrayList rols = new ArrayList(Arrays.asList(this.roles));
      rols.addAll(Arrays.asList(newRoles));
      rols.toArray(allRoles);
      this.roles = allRoles;
   }

   public ChannelAuthorizationManager.Action getAction() {
      return this.action;
   }

   public String getChannelPattern() {
      return this.pattern;
   }

   public String[] getRoles() {
      return this.roles;
   }

   public boolean isForbidden() {
      return this.forbidden;
   }

   public boolean isUnrestricted() {
      return this.unrestricted;
   }
}
