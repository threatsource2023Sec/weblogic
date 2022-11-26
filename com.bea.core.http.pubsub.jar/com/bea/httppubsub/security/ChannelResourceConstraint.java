package com.bea.httppubsub.security;

public interface ChannelResourceConstraint {
   String getChannelPattern();

   ChannelAuthorizationManager.Action getAction();

   String[] getRoles();

   boolean isForbidden();

   boolean isUnrestricted();
}
