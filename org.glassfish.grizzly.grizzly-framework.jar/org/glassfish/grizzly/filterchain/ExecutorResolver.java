package org.glassfish.grizzly.filterchain;

import java.io.IOException;

abstract class ExecutorResolver {
   public static final FilterExecutor UPSTREAM_EXECUTOR_SAMPLE = new UpstreamExecutor() {
      public NextAction execute(Filter filter, FilterChainContext context) throws IOException {
         throw new UnsupportedOperationException("Not supported yet.");
      }
   };
   public static final FilterExecutor DOWNSTREAM_EXECUTOR_SAMPLE = new DownstreamExecutor() {
      public NextAction execute(Filter filter, FilterChainContext context) throws IOException {
         throw new UnsupportedOperationException("Not supported yet.");
      }
   };
   private static final FilterExecutor CONNECT_EXECUTOR = new UpstreamExecutor() {
      public NextAction execute(Filter filter, FilterChainContext context) throws IOException {
         return filter.handleConnect(context);
      }
   };
   private static final FilterExecutor CLOSE_EXECUTOR = new UpstreamExecutor() {
      public NextAction execute(Filter filter, FilterChainContext context) throws IOException {
         return filter.handleClose(context);
      }
   };
   private static final FilterExecutor EVENT_UPSTREAM_EXECUTOR = new UpstreamExecutor() {
      public NextAction execute(Filter filter, FilterChainContext context) throws IOException {
         return filter.handleEvent(context, context.event);
      }
   };
   private static final FilterExecutor EVENT_DOWNSTREAM_EXECUTOR = new DownstreamExecutor() {
      public NextAction execute(Filter filter, FilterChainContext context) throws IOException {
         return filter.handleEvent(context, context.event);
      }
   };
   private static final FilterExecutor ACCEPT_EXECUTOR = new UpstreamExecutor() {
      public NextAction execute(Filter filter, FilterChainContext context) throws IOException {
         return filter.handleAccept(context);
      }
   };
   private static final FilterExecutor WRITE_EXECUTOR = new DownstreamExecutor() {
      public NextAction execute(Filter filter, FilterChainContext context) throws IOException {
         return filter.handleWrite(context);
      }
   };
   private static final FilterExecutor READ_EXECUTOR = new UpstreamExecutor() {
      public NextAction execute(Filter filter, FilterChainContext context) throws IOException {
         return filter.handleRead(context);
      }
   };

   public static FilterExecutor resolve(FilterChainContext context) {
      switch (context.getOperation()) {
         case READ:
            return READ_EXECUTOR;
         case WRITE:
            return WRITE_EXECUTOR;
         case ACCEPT:
            return ACCEPT_EXECUTOR;
         case CLOSE:
            return CLOSE_EXECUTOR;
         case CONNECT:
            return CONNECT_EXECUTOR;
         case EVENT:
            return context.getFilterIdx() != Integer.MIN_VALUE && context.getStartIdx() > context.getEndIdx() ? EVENT_DOWNSTREAM_EXECUTOR : EVENT_UPSTREAM_EXECUTOR;
         default:
            return null;
      }
   }

   public abstract static class DownstreamExecutor implements FilterExecutor {
      public final int defaultStartIdx(FilterChainContext context) {
         if (context.getFilterIdx() != Integer.MIN_VALUE) {
            return context.getFilterIdx();
         } else {
            int idx = context.getFilterChain().size() - 1;
            context.setFilterIdx(idx);
            return idx;
         }
      }

      public final int defaultEndIdx(FilterChainContext context) {
         return -1;
      }

      public final int getNextFilter(FilterChainContext context) {
         return context.getFilterIdx() - 1;
      }

      public final int getPreviousFilter(FilterChainContext context) {
         return context.getFilterIdx() + 1;
      }

      public final boolean hasNextFilter(FilterChainContext context, int idx) {
         return idx > 0;
      }

      public final boolean hasPreviousFilter(FilterChainContext context, int idx) {
         return idx < context.getFilterChain().size() - 1;
      }

      public final void initIndexes(FilterChainContext context) {
         int startIdx = this.defaultStartIdx(context);
         context.setStartIdx(startIdx);
         context.setFilterIdx(startIdx);
         context.setEndIdx(this.defaultEndIdx(context));
      }

      public final boolean isUpstream() {
         return false;
      }

      public final boolean isDownstream() {
         return true;
      }
   }

   public abstract static class UpstreamExecutor implements FilterExecutor {
      public final int defaultStartIdx(FilterChainContext context) {
         if (context.getFilterIdx() != Integer.MIN_VALUE) {
            return context.getFilterIdx();
         } else {
            context.setFilterIdx(0);
            return 0;
         }
      }

      public final int defaultEndIdx(FilterChainContext context) {
         return context.getFilterChain().size();
      }

      public final int getNextFilter(FilterChainContext context) {
         return context.getFilterIdx() + 1;
      }

      public final int getPreviousFilter(FilterChainContext context) {
         return context.getFilterIdx() - 1;
      }

      public final boolean hasNextFilter(FilterChainContext context, int idx) {
         return idx < context.getFilterChain().size() - 1;
      }

      public final boolean hasPreviousFilter(FilterChainContext context, int idx) {
         return idx > 0;
      }

      public final void initIndexes(FilterChainContext context) {
         int startIdx = this.defaultStartIdx(context);
         context.setStartIdx(startIdx);
         context.setFilterIdx(startIdx);
         context.setEndIdx(this.defaultEndIdx(context));
      }

      public final boolean isUpstream() {
         return true;
      }

      public final boolean isDownstream() {
         return false;
      }
   }
}
