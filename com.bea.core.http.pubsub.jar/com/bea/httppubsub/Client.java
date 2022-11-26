package com.bea.httppubsub;

import java.io.Serializable;
import java.util.Set;

public interface Client extends Serializable {
   String getId();

   long getLastAccessTime();

   Set getChannelSubscriptions();

   long getPublishedMessageCount();

   void updateLastAccessTime();

   AuthenticatedUser getAuthenticatedUser();

   String getBrowserId();

   boolean isMultiFrame();

   void setMultiFrame(boolean var1);

   public static enum ConnectionType {
      LONGPOLLING {
         public String toString() {
            return "long-polling";
         }
      },
      CALLBACKPOLLING {
         public String toString() {
            return "callback-polling";
         }
      },
      IFRAME {
         public String toString() {
            return "iframe";
         }
      },
      FLASH {
         public String toString() {
            return "flash";
         }
      };

      private ConnectionType() {
      }

      public String getName() {
         return this.toString();
      }

      // $FF: synthetic method
      ConnectionType(Object x2) {
         this();
      }
   }
}
