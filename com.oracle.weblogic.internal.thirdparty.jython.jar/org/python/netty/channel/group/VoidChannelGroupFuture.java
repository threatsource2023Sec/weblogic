package org.python.netty.channel.group;

import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.util.concurrent.GenericFutureListener;

final class VoidChannelGroupFuture implements ChannelGroupFuture {
   private static final Iterator EMPTY = Collections.emptyList().iterator();
   private final ChannelGroup group;

   VoidChannelGroupFuture(ChannelGroup group) {
      this.group = group;
   }

   public ChannelGroup group() {
      return this.group;
   }

   public ChannelFuture find(Channel channel) {
      return null;
   }

   public boolean isSuccess() {
      return false;
   }

   public ChannelGroupException cause() {
      return null;
   }

   public boolean isPartialSuccess() {
      return false;
   }

   public boolean isPartialFailure() {
      return false;
   }

   public ChannelGroupFuture addListener(GenericFutureListener listener) {
      throw reject();
   }

   public ChannelGroupFuture addListeners(GenericFutureListener... listeners) {
      throw reject();
   }

   public ChannelGroupFuture removeListener(GenericFutureListener listener) {
      throw reject();
   }

   public ChannelGroupFuture removeListeners(GenericFutureListener... listeners) {
      throw reject();
   }

   public ChannelGroupFuture await() {
      throw reject();
   }

   public ChannelGroupFuture awaitUninterruptibly() {
      throw reject();
   }

   public ChannelGroupFuture syncUninterruptibly() {
      throw reject();
   }

   public ChannelGroupFuture sync() {
      throw reject();
   }

   public Iterator iterator() {
      return EMPTY;
   }

   public boolean isCancellable() {
      return false;
   }

   public boolean await(long timeout, TimeUnit unit) {
      throw reject();
   }

   public boolean await(long timeoutMillis) {
      throw reject();
   }

   public boolean awaitUninterruptibly(long timeout, TimeUnit unit) {
      throw reject();
   }

   public boolean awaitUninterruptibly(long timeoutMillis) {
      throw reject();
   }

   public Void getNow() {
      return null;
   }

   public boolean cancel(boolean mayInterruptIfRunning) {
      return false;
   }

   public boolean isCancelled() {
      return false;
   }

   public boolean isDone() {
      return false;
   }

   public Void get() {
      throw reject();
   }

   public Void get(long timeout, TimeUnit unit) {
      throw reject();
   }

   private static RuntimeException reject() {
      return new IllegalStateException("void future");
   }
}
