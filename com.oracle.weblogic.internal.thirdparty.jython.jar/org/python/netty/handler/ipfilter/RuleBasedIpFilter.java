package org.python.netty.handler.ipfilter;

import java.net.InetSocketAddress;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;

@ChannelHandler.Sharable
public class RuleBasedIpFilter extends AbstractRemoteAddressFilter {
   private final IpFilterRule[] rules;

   public RuleBasedIpFilter(IpFilterRule... rules) {
      if (rules == null) {
         throw new NullPointerException("rules");
      } else {
         this.rules = rules;
      }
   }

   protected boolean accept(ChannelHandlerContext ctx, InetSocketAddress remoteAddress) throws Exception {
      IpFilterRule[] var3 = this.rules;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         IpFilterRule rule = var3[var5];
         if (rule == null) {
            break;
         }

         if (rule.matches(remoteAddress)) {
            return rule.ruleType() == IpFilterRuleType.ACCEPT;
         }
      }

      return true;
   }
}
