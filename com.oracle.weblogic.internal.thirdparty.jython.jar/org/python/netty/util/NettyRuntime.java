package org.python.netty.util;

import java.util.Locale;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.SystemPropertyUtil;

public final class NettyRuntime {
   private static final AvailableProcessorsHolder holder = new AvailableProcessorsHolder();

   public static void setAvailableProcessors(int availableProcessors) {
      holder.setAvailableProcessors(availableProcessors);
   }

   public static int availableProcessors() {
      return holder.availableProcessors();
   }

   private NettyRuntime() {
   }

   static class AvailableProcessorsHolder {
      private int availableProcessors;

      synchronized void setAvailableProcessors(int availableProcessors) {
         ObjectUtil.checkPositive(availableProcessors, "availableProcessors");
         if (this.availableProcessors != 0) {
            String message = String.format(Locale.ROOT, "availableProcessors is already set to [%d], rejecting [%d]", this.availableProcessors, availableProcessors);
            throw new IllegalStateException(message);
         } else {
            this.availableProcessors = availableProcessors;
         }
      }

      @SuppressForbidden(
         reason = "to obtain default number of available processors"
      )
      synchronized int availableProcessors() {
         if (this.availableProcessors == 0) {
            int availableProcessors = SystemPropertyUtil.getInt("org.python.netty.availableProcessors", Runtime.getRuntime().availableProcessors());
            this.setAvailableProcessors(availableProcessors);
         }

         return this.availableProcessors;
      }
   }
}
