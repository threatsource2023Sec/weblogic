package org.python.netty.channel.group;

import org.python.netty.channel.Channel;
import org.python.netty.channel.ServerChannel;

public final class ChannelMatchers {
   private static final ChannelMatcher ALL_MATCHER = new ChannelMatcher() {
      public boolean matches(Channel channel) {
         return true;
      }
   };
   private static final ChannelMatcher SERVER_CHANNEL_MATCHER = isInstanceOf(ServerChannel.class);
   private static final ChannelMatcher NON_SERVER_CHANNEL_MATCHER = isNotInstanceOf(ServerChannel.class);

   private ChannelMatchers() {
   }

   public static ChannelMatcher all() {
      return ALL_MATCHER;
   }

   public static ChannelMatcher isNot(Channel channel) {
      return invert(is(channel));
   }

   public static ChannelMatcher is(Channel channel) {
      return new InstanceMatcher(channel);
   }

   public static ChannelMatcher isInstanceOf(Class clazz) {
      return new ClassMatcher(clazz);
   }

   public static ChannelMatcher isNotInstanceOf(Class clazz) {
      return invert(isInstanceOf(clazz));
   }

   public static ChannelMatcher isServerChannel() {
      return SERVER_CHANNEL_MATCHER;
   }

   public static ChannelMatcher isNonServerChannel() {
      return NON_SERVER_CHANNEL_MATCHER;
   }

   public static ChannelMatcher invert(ChannelMatcher matcher) {
      return new InvertMatcher(matcher);
   }

   public static ChannelMatcher compose(ChannelMatcher... matchers) {
      if (matchers.length < 1) {
         throw new IllegalArgumentException("matchers must at least contain one element");
      } else {
         return (ChannelMatcher)(matchers.length == 1 ? matchers[0] : new CompositeMatcher(matchers));
      }
   }

   private static final class ClassMatcher implements ChannelMatcher {
      private final Class clazz;

      ClassMatcher(Class clazz) {
         this.clazz = clazz;
      }

      public boolean matches(Channel ch) {
         return this.clazz.isInstance(ch);
      }
   }

   private static final class InstanceMatcher implements ChannelMatcher {
      private final Channel channel;

      InstanceMatcher(Channel channel) {
         this.channel = channel;
      }

      public boolean matches(Channel ch) {
         return this.channel == ch;
      }
   }

   private static final class InvertMatcher implements ChannelMatcher {
      private final ChannelMatcher matcher;

      InvertMatcher(ChannelMatcher matcher) {
         this.matcher = matcher;
      }

      public boolean matches(Channel channel) {
         return !this.matcher.matches(channel);
      }
   }

   private static final class CompositeMatcher implements ChannelMatcher {
      private final ChannelMatcher[] matchers;

      CompositeMatcher(ChannelMatcher... matchers) {
         this.matchers = matchers;
      }

      public boolean matches(Channel channel) {
         ChannelMatcher[] var2 = this.matchers;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ChannelMatcher m = var2[var4];
            if (!m.matches(channel)) {
               return false;
            }
         }

         return true;
      }
   }
}
