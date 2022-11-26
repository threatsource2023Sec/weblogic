package com.bea.httppubsub.security;

import com.bea.httppubsub.Client;
import com.bea.httppubsub.PubSubContext;

public interface ChannelAuthorizationManager {
   void initialize(PubSubContext var1);

   boolean hasPermission(Client var1, String var2, Action var3);

   void destroy();

   public static enum Action {
      CREATE {
         public String toString() {
            return "CREATE";
         }
      },
      DELETE {
         public String toString() {
            return "DELETE";
         }
      },
      SUBSCRIBE {
         public String toString() {
            return "SUBSCRIBE";
         }
      },
      PUBLISH {
         public String toString() {
            return "PUBLISH";
         }
      };

      private Action() {
      }

      // $FF: synthetic method
      Action(Object x2) {
         this();
      }
   }
}
