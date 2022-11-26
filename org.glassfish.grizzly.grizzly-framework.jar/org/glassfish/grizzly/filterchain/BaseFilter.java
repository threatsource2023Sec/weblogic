package org.glassfish.grizzly.filterchain;

import java.io.IOException;
import org.glassfish.grizzly.Connection;

public class BaseFilter implements Filter {
   public void onAdded(FilterChain filterChain) {
   }

   public void onFilterChainChanged(FilterChain filterChain) {
   }

   public void onRemoved(FilterChain filterChain) {
   }

   public NextAction handleRead(FilterChainContext ctx) throws IOException {
      return ctx.getInvokeAction();
   }

   public NextAction handleWrite(FilterChainContext ctx) throws IOException {
      return ctx.getInvokeAction();
   }

   public NextAction handleConnect(FilterChainContext ctx) throws IOException {
      return ctx.getInvokeAction();
   }

   public NextAction handleAccept(FilterChainContext ctx) throws IOException {
      return ctx.getInvokeAction();
   }

   public NextAction handleEvent(FilterChainContext ctx, FilterChainEvent event) throws IOException {
      return ctx.getInvokeAction();
   }

   public NextAction handleClose(FilterChainContext ctx) throws IOException {
      return ctx.getInvokeAction();
   }

   public void exceptionOccurred(FilterChainContext ctx, Throwable error) {
   }

   public FilterChainContext createContext(Connection connection, FilterChainContext.Operation operation) {
      FilterChain filterChain = (FilterChain)connection.getProcessor();
      FilterChainContext ctx = filterChain.obtainFilterChainContext(connection);
      int idx = filterChain.indexOf(this);
      ctx.setOperation(operation);
      ctx.setFilterIdx(idx);
      ctx.setStartIdx(idx);
      return ctx;
   }

   public String toString() {
      return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode());
   }
}
