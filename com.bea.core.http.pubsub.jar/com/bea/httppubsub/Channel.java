package com.bea.httppubsub;

import java.util.List;

public interface Channel {
   String getName();

   Channel getParentChannel();

   List getSubChannels();

   List getAllSubChannels();

   void addSubChannel(Channel var1);

   void removeSubChannel(Channel var1);

   void destroy(Client var1);

   List getClients(ChannelPattern var1);

   void subscribe(Client var1) throws PubSubSecurityException;

   void subscribe(Client var1, ChannelPattern var2) throws PubSubSecurityException;

   void unsubscribe(Client var1);

   void unsubscribe(Client var1, ChannelPattern var2);

   void publish(BayeuxMessage var1, ChannelPattern var2) throws PubSubSecurityException;

   void publish(Client var1, BayeuxMessage var2, ChannelPattern var3) throws PubSubSecurityException;

   long getPublishedMessageCount();

   boolean isMetaChannel();

   boolean isServiceChannel();

   boolean isPersistentChannel();

   public static enum ChannelPattern {
      ITSELF {
         public String toString() {
            return "";
         }
      },
      IMMEDIATE_SUBCHANNELS {
         public String toString() {
            return "/*";
         }
      },
      ALL_SUBCHANNELS {
         public String toString() {
            return "/**";
         }
      };

      private ChannelPattern() {
      }

      public static ChannelPattern getPattern(String url) {
         if (url.endsWith("/**")) {
            return ALL_SUBCHANNELS;
         } else {
            return url.endsWith("/*") ? IMMEDIATE_SUBCHANNELS : ITSELF;
         }
      }

      // $FF: synthetic method
      ChannelPattern(Object x2) {
         this();
      }
   }
}
