package org.python.netty.bootstrap;

import java.util.Map;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.EventLoopGroup;
import org.python.netty.util.internal.StringUtil;

public final class ServerBootstrapConfig extends AbstractBootstrapConfig {
   ServerBootstrapConfig(ServerBootstrap bootstrap) {
      super(bootstrap);
   }

   public EventLoopGroup childGroup() {
      return ((ServerBootstrap)this.bootstrap).childGroup();
   }

   public ChannelHandler childHandler() {
      return ((ServerBootstrap)this.bootstrap).childHandler();
   }

   public Map childOptions() {
      return ((ServerBootstrap)this.bootstrap).childOptions();
   }

   public Map childAttrs() {
      return ((ServerBootstrap)this.bootstrap).childAttrs();
   }

   public String toString() {
      StringBuilder buf = new StringBuilder(super.toString());
      buf.setLength(buf.length() - 1);
      buf.append(", ");
      EventLoopGroup childGroup = this.childGroup();
      if (childGroup != null) {
         buf.append("childGroup: ");
         buf.append(StringUtil.simpleClassName((Object)childGroup));
         buf.append(", ");
      }

      Map childOptions = this.childOptions();
      if (!childOptions.isEmpty()) {
         buf.append("childOptions: ");
         buf.append(childOptions);
         buf.append(", ");
      }

      Map childAttrs = this.childAttrs();
      if (!childAttrs.isEmpty()) {
         buf.append("childAttrs: ");
         buf.append(childAttrs);
         buf.append(", ");
      }

      ChannelHandler childHandler = this.childHandler();
      if (childHandler != null) {
         buf.append("childHandler: ");
         buf.append(childHandler);
         buf.append(", ");
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
