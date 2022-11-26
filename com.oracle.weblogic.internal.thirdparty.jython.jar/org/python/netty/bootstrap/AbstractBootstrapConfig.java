package org.python.netty.bootstrap;

import java.net.SocketAddress;
import java.util.Map;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.EventLoopGroup;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.StringUtil;

public abstract class AbstractBootstrapConfig {
   protected final AbstractBootstrap bootstrap;

   protected AbstractBootstrapConfig(AbstractBootstrap bootstrap) {
      this.bootstrap = (AbstractBootstrap)ObjectUtil.checkNotNull(bootstrap, "bootstrap");
   }

   public final SocketAddress localAddress() {
      return this.bootstrap.localAddress();
   }

   public final ChannelFactory channelFactory() {
      return this.bootstrap.channelFactory();
   }

   public final ChannelHandler handler() {
      return this.bootstrap.handler();
   }

   public final Map options() {
      return this.bootstrap.options();
   }

   public final Map attrs() {
      return this.bootstrap.attrs();
   }

   public final EventLoopGroup group() {
      return this.bootstrap.group();
   }

   public String toString() {
      StringBuilder buf = (new StringBuilder()).append(StringUtil.simpleClassName((Object)this)).append('(');
      EventLoopGroup group = this.group();
      if (group != null) {
         buf.append("group: ").append(StringUtil.simpleClassName((Object)group)).append(", ");
      }

      ChannelFactory factory = this.channelFactory();
      if (factory != null) {
         buf.append("channelFactory: ").append(factory).append(", ");
      }

      SocketAddress localAddress = this.localAddress();
      if (localAddress != null) {
         buf.append("localAddress: ").append(localAddress).append(", ");
      }

      Map options = this.options();
      if (!options.isEmpty()) {
         buf.append("options: ").append(options).append(", ");
      }

      Map attrs = this.attrs();
      if (!attrs.isEmpty()) {
         buf.append("attrs: ").append(attrs).append(", ");
      }

      ChannelHandler handler = this.handler();
      if (handler != null) {
         buf.append("handler: ").append(handler).append(", ");
      }

      if (buf.charAt(buf.length() - 1) == '(') {
         buf.append(')');
      } else {
         buf.setCharAt(buf.length() - 2, ')');
         buf.setLength(buf.length() - 1);
      }

      return buf.toString();
   }
}
