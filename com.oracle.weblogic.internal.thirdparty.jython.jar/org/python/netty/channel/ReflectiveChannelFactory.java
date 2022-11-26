package org.python.netty.channel;

import org.python.netty.util.internal.StringUtil;

public class ReflectiveChannelFactory implements ChannelFactory {
   private final Class clazz;

   public ReflectiveChannelFactory(Class clazz) {
      if (clazz == null) {
         throw new NullPointerException("clazz");
      } else {
         this.clazz = clazz;
      }
   }

   public Channel newChannel() {
      try {
         return (Channel)this.clazz.newInstance();
      } catch (Throwable var2) {
         throw new ChannelException("Unable to create Channel from class " + this.clazz, var2);
      }
   }

   public String toString() {
      return StringUtil.simpleClassName(this.clazz) + ".class";
   }
}
