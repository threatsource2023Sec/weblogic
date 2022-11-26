package com.bea.httppubsub.security;

import com.bea.httppubsub.AuthenticatedUser;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.PubSubContext;
import com.bea.httppubsub.PubSubLogger;
import com.bea.httppubsub.bayeux.BayeuxConstants;
import com.bea.httppubsub.descriptor.ChannelConstraintBean;
import com.bea.httppubsub.descriptor.ChannelResourceCollectionBean;
import com.bea.httppubsub.util.ChannelMapping;
import java.util.HashMap;
import java.util.Map;
import weblogic.utils.http.HttpParsing;

public class ChannelAuthorizationManagerImpl implements ChannelAuthorizationManager {
   private Map constraintsMap = null;

   public boolean hasPermission(Client client, String channelPattern, ChannelAuthorizationManager.Action action) {
      if (channelPattern == null) {
         throw new IllegalArgumentException("Parameter channelPattern can't be null");
      } else if (channelPattern.startsWith("/meta/") && !channelPattern.equals(BayeuxConstants.META_SUBSCRIBE)) {
         return true;
      } else {
         ChannelResourceConstraint constraint = this.getResourceConstraint(channelPattern, action);
         if (constraint != null && !constraint.isUnrestricted()) {
            if (constraint.isForbidden()) {
               PubSubLogger.logAlwaysSecurityConstraint(channelPattern, action.name());
               return false;
            } else {
               AuthenticatedUser authUser = client.getAuthenticatedUser();
               if (authUser.getUserPrincipal() == null) {
                  PubSubLogger.logNotLogin(client.getId(), channelPattern);
                  return false;
               } else {
                  String[] roles = constraint.getRoles();

                  for(int i = 0; i < roles.length; ++i) {
                     if (authUser.isUserInRole(roles[i])) {
                        PubSubLogger.logLoginAllowed(client.getId(), channelPattern, action.name());
                        return true;
                     }
                  }

                  PubSubLogger.logLoginDisallowed(client.getId(), channelPattern, action.name());
                  return false;
               }
            }
         } else {
            PubSubLogger.logNoSecurityConstraint(channelPattern, action.name());
            return true;
         }
      }
   }

   public void initialize(PubSubContext context) {
      ChannelConstraintBean[] ccb = context.getConfig().getChannelConstraints();
      if (ccb != null && ccb.length >= 1) {
         for(int i = 0; i < ccb.length; ++i) {
            this.registerSecurityConstraint(ccb[i]);
         }

      }
   }

   public void destroy() {
   }

   private void registerSecurityConstraint(ChannelConstraintBean ccb) {
      ChannelResourceCollectionBean[] crcbs = ccb.getChannelResourceCollections();

      for(int i = 0; i < crcbs.length; ++i) {
         String[] operations = crcbs[i].getChannelOperations();

         for(int j = 0; j < operations.length; ++j) {
            this.mergePatterns(crcbs[i].getChannelPatterns(), operations[j], ccb);
         }
      }

   }

   private void mergePatterns(String[] patterns, String action, ChannelConstraintBean scb) {
      for(int i = 0; patterns != null && i < patterns.length; ++i) {
         String pattern = HttpParsing.ensureStartingSlash(patterns[i]);
         this.mergePolicy(new ChannelResourceConstraintImpl(pattern, getAction(action), scb));
      }

   }

   private void mergePolicy(ChannelResourceConstraintImpl curr) {
      ChannelAuthorizationManager.Action action = curr.getAction();
      if (action == null) {
         throw new IllegalArgumentException("null action");
      } else {
         if (this.constraintsMap == null) {
            this.constraintsMap = new HashMap();
         }

         ChannelMapping map = (ChannelMapping)this.constraintsMap.get(action);
         if (map == null) {
            map = new ChannelMapping(false);
            this.constraintsMap.put(action, map);
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

   private ChannelResourceConstraint getResourceConstraint(String channelPattern, ChannelAuthorizationManager.Action action) {
      if (this.constraintsMap == null) {
         return null;
      } else {
         ChannelMapping map = (ChannelMapping)this.constraintsMap.get(action);
         return map == null ? null : (ChannelResourceConstraint)map.get(channelPattern);
      }
   }

   private static ChannelAuthorizationManager.Action getAction(String action) {
      if ("create".equalsIgnoreCase(action)) {
         return ChannelAuthorizationManager.Action.CREATE;
      } else if ("delete".equalsIgnoreCase(action)) {
         return ChannelAuthorizationManager.Action.DELETE;
      } else if ("subscribe".equalsIgnoreCase(action)) {
         return ChannelAuthorizationManager.Action.SUBSCRIBE;
      } else if ("publish".equalsIgnoreCase(action)) {
         return ChannelAuthorizationManager.Action.PUBLISH;
      } else {
         throw new IllegalArgumentException("Illegal action: " + action);
      }
   }
}
