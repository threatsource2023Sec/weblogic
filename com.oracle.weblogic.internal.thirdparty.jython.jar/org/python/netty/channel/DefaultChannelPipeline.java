package org.python.netty.channel;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.WeakHashMap;
import java.util.concurrent.RejectedExecutionException;
import org.python.netty.util.ReferenceCountUtil;
import org.python.netty.util.ResourceLeakDetector;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.concurrent.EventExecutorGroup;
import org.python.netty.util.concurrent.FastThreadLocal;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.StringUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class DefaultChannelPipeline implements ChannelPipeline {
   static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultChannelPipeline.class);
   private static final String HEAD_NAME = generateName0(HeadContext.class);
   private static final String TAIL_NAME = generateName0(TailContext.class);
   private static final FastThreadLocal nameCaches = new FastThreadLocal() {
      protected Map initialValue() throws Exception {
         return new WeakHashMap();
      }
   };
   final AbstractChannelHandlerContext head;
   final AbstractChannelHandlerContext tail;
   private final Channel channel;
   private final ChannelFuture succeededFuture;
   private final VoidChannelPromise voidPromise;
   private final boolean touch = ResourceLeakDetector.isEnabled();
   private Map childExecutors;
   private MessageSizeEstimator.Handle estimatorHandle;
   private boolean firstRegistration = true;
   private PendingHandlerCallback pendingHandlerCallbackHead;
   private boolean registered;

   protected DefaultChannelPipeline(Channel channel) {
      this.channel = (Channel)ObjectUtil.checkNotNull(channel, "channel");
      this.succeededFuture = new SucceededChannelFuture(channel, (EventExecutor)null);
      this.voidPromise = new VoidChannelPromise(channel, true);
      this.tail = new TailContext(this);
      this.head = new HeadContext(this);
      this.head.next = this.tail;
      this.tail.prev = this.head;
   }

   final MessageSizeEstimator.Handle estimatorHandle() {
      if (this.estimatorHandle == null) {
         this.estimatorHandle = this.channel.config().getMessageSizeEstimator().newHandle();
      }

      return this.estimatorHandle;
   }

   final Object touch(Object msg, AbstractChannelHandlerContext next) {
      return this.touch ? ReferenceCountUtil.touch(msg, next) : msg;
   }

   private AbstractChannelHandlerContext newContext(EventExecutorGroup group, String name, ChannelHandler handler) {
      return new DefaultChannelHandlerContext(this, this.childExecutor(group), name, handler);
   }

   private EventExecutor childExecutor(EventExecutorGroup group) {
      if (group == null) {
         return null;
      } else {
         Boolean pinEventExecutor = (Boolean)this.channel.config().getOption(ChannelOption.SINGLE_EVENTEXECUTOR_PER_GROUP);
         if (pinEventExecutor != null && !pinEventExecutor) {
            return group.next();
         } else {
            Map childExecutors = this.childExecutors;
            if (childExecutors == null) {
               childExecutors = this.childExecutors = new IdentityHashMap(4);
            }

            EventExecutor childExecutor = (EventExecutor)childExecutors.get(group);
            if (childExecutor == null) {
               childExecutor = group.next();
               childExecutors.put(group, childExecutor);
            }

            return childExecutor;
         }
      }
   }

   public final Channel channel() {
      return this.channel;
   }

   public final ChannelPipeline addFirst(String name, ChannelHandler handler) {
      return this.addFirst((EventExecutorGroup)null, name, handler);
   }

   public final ChannelPipeline addFirst(EventExecutorGroup group, String name, ChannelHandler handler) {
      final AbstractChannelHandlerContext newCtx;
      synchronized(this) {
         checkMultiplicity(handler);
         name = this.filterName(name, handler);
         newCtx = this.newContext(group, name, handler);
         this.addFirst0(newCtx);
         if (!this.registered) {
            newCtx.setAddPending();
            this.callHandlerCallbackLater(newCtx, true);
            return this;
         }

         EventExecutor executor = newCtx.executor();
         if (!executor.inEventLoop()) {
            newCtx.setAddPending();
            executor.execute(new Runnable() {
               public void run() {
                  DefaultChannelPipeline.this.callHandlerAdded0(newCtx);
               }
            });
            return this;
         }
      }

      this.callHandlerAdded0(newCtx);
      return this;
   }

   private void addFirst0(AbstractChannelHandlerContext newCtx) {
      AbstractChannelHandlerContext nextCtx = this.head.next;
      newCtx.prev = this.head;
      newCtx.next = nextCtx;
      this.head.next = newCtx;
      nextCtx.prev = newCtx;
   }

   public final ChannelPipeline addLast(String name, ChannelHandler handler) {
      return this.addLast((EventExecutorGroup)null, name, handler);
   }

   public final ChannelPipeline addLast(EventExecutorGroup group, String name, ChannelHandler handler) {
      final AbstractChannelHandlerContext newCtx;
      synchronized(this) {
         checkMultiplicity(handler);
         newCtx = this.newContext(group, this.filterName(name, handler), handler);
         this.addLast0(newCtx);
         if (!this.registered) {
            newCtx.setAddPending();
            this.callHandlerCallbackLater(newCtx, true);
            return this;
         }

         EventExecutor executor = newCtx.executor();
         if (!executor.inEventLoop()) {
            newCtx.setAddPending();
            executor.execute(new Runnable() {
               public void run() {
                  DefaultChannelPipeline.this.callHandlerAdded0(newCtx);
               }
            });
            return this;
         }
      }

      this.callHandlerAdded0(newCtx);
      return this;
   }

   private void addLast0(AbstractChannelHandlerContext newCtx) {
      AbstractChannelHandlerContext prev = this.tail.prev;
      newCtx.prev = prev;
      newCtx.next = this.tail;
      prev.next = newCtx;
      this.tail.prev = newCtx;
   }

   public final ChannelPipeline addBefore(String baseName, String name, ChannelHandler handler) {
      return this.addBefore((EventExecutorGroup)null, baseName, name, handler);
   }

   public final ChannelPipeline addBefore(EventExecutorGroup group, String baseName, String name, ChannelHandler handler) {
      final AbstractChannelHandlerContext newCtx;
      synchronized(this) {
         checkMultiplicity(handler);
         name = this.filterName(name, handler);
         AbstractChannelHandlerContext ctx = this.getContextOrDie(baseName);
         newCtx = this.newContext(group, name, handler);
         addBefore0(ctx, newCtx);
         if (!this.registered) {
            newCtx.setAddPending();
            this.callHandlerCallbackLater(newCtx, true);
            return this;
         }

         EventExecutor executor = newCtx.executor();
         if (!executor.inEventLoop()) {
            newCtx.setAddPending();
            executor.execute(new Runnable() {
               public void run() {
                  DefaultChannelPipeline.this.callHandlerAdded0(newCtx);
               }
            });
            return this;
         }
      }

      this.callHandlerAdded0(newCtx);
      return this;
   }

   private static void addBefore0(AbstractChannelHandlerContext ctx, AbstractChannelHandlerContext newCtx) {
      newCtx.prev = ctx.prev;
      newCtx.next = ctx;
      ctx.prev.next = newCtx;
      ctx.prev = newCtx;
   }

   private String filterName(String name, ChannelHandler handler) {
      if (name == null) {
         return this.generateName(handler);
      } else {
         this.checkDuplicateName(name);
         return name;
      }
   }

   public final ChannelPipeline addAfter(String baseName, String name, ChannelHandler handler) {
      return this.addAfter((EventExecutorGroup)null, baseName, name, handler);
   }

   public final ChannelPipeline addAfter(EventExecutorGroup group, String baseName, String name, ChannelHandler handler) {
      final AbstractChannelHandlerContext newCtx;
      synchronized(this) {
         checkMultiplicity(handler);
         name = this.filterName(name, handler);
         AbstractChannelHandlerContext ctx = this.getContextOrDie(baseName);
         newCtx = this.newContext(group, name, handler);
         addAfter0(ctx, newCtx);
         if (!this.registered) {
            newCtx.setAddPending();
            this.callHandlerCallbackLater(newCtx, true);
            return this;
         }

         EventExecutor executor = newCtx.executor();
         if (!executor.inEventLoop()) {
            newCtx.setAddPending();
            executor.execute(new Runnable() {
               public void run() {
                  DefaultChannelPipeline.this.callHandlerAdded0(newCtx);
               }
            });
            return this;
         }
      }

      this.callHandlerAdded0(newCtx);
      return this;
   }

   private static void addAfter0(AbstractChannelHandlerContext ctx, AbstractChannelHandlerContext newCtx) {
      newCtx.prev = ctx;
      newCtx.next = ctx.next;
      ctx.next.prev = newCtx;
      ctx.next = newCtx;
   }

   public final ChannelPipeline addFirst(ChannelHandler... handlers) {
      return this.addFirst((EventExecutorGroup)null, (ChannelHandler[])handlers);
   }

   public final ChannelPipeline addFirst(EventExecutorGroup executor, ChannelHandler... handlers) {
      if (handlers == null) {
         throw new NullPointerException("handlers");
      } else if (handlers.length != 0 && handlers[0] != null) {
         int size;
         for(size = 1; size < handlers.length && handlers[size] != null; ++size) {
         }

         for(int i = size - 1; i >= 0; --i) {
            ChannelHandler h = handlers[i];
            this.addFirst(executor, (String)null, h);
         }

         return this;
      } else {
         return this;
      }
   }

   public final ChannelPipeline addLast(ChannelHandler... handlers) {
      return this.addLast((EventExecutorGroup)null, (ChannelHandler[])handlers);
   }

   public final ChannelPipeline addLast(EventExecutorGroup executor, ChannelHandler... handlers) {
      if (handlers == null) {
         throw new NullPointerException("handlers");
      } else {
         ChannelHandler[] var3 = handlers;
         int var4 = handlers.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            ChannelHandler h = var3[var5];
            if (h == null) {
               break;
            }

            this.addLast(executor, (String)null, h);
         }

         return this;
      }
   }

   private String generateName(ChannelHandler handler) {
      Map cache = (Map)nameCaches.get();
      Class handlerType = handler.getClass();
      String name = (String)cache.get(handlerType);
      if (name == null) {
         name = generateName0(handlerType);
         cache.put(handlerType, name);
      }

      if (this.context0(name) != null) {
         String baseName = name.substring(0, name.length() - 1);
         int i = 1;

         while(true) {
            String newName = baseName + i;
            if (this.context0(newName) == null) {
               name = newName;
               break;
            }

            ++i;
         }
      }

      return name;
   }

   private static String generateName0(Class handlerType) {
      return StringUtil.simpleClassName(handlerType) + "#0";
   }

   public final ChannelPipeline remove(ChannelHandler handler) {
      this.remove(this.getContextOrDie(handler));
      return this;
   }

   public final ChannelHandler remove(String name) {
      return this.remove(this.getContextOrDie(name)).handler();
   }

   public final ChannelHandler remove(Class handlerType) {
      return this.remove(this.getContextOrDie(handlerType)).handler();
   }

   private AbstractChannelHandlerContext remove(final AbstractChannelHandlerContext ctx) {
      assert ctx != this.head && ctx != this.tail;

      synchronized(this) {
         remove0(ctx);
         if (!this.registered) {
            this.callHandlerCallbackLater(ctx, false);
            return ctx;
         }

         EventExecutor executor = ctx.executor();
         if (!executor.inEventLoop()) {
            executor.execute(new Runnable() {
               public void run() {
                  DefaultChannelPipeline.this.callHandlerRemoved0(ctx);
               }
            });
            return ctx;
         }
      }

      this.callHandlerRemoved0(ctx);
      return ctx;
   }

   private static void remove0(AbstractChannelHandlerContext ctx) {
      AbstractChannelHandlerContext prev = ctx.prev;
      AbstractChannelHandlerContext next = ctx.next;
      prev.next = next;
      next.prev = prev;
   }

   public final ChannelHandler removeFirst() {
      if (this.head.next == this.tail) {
         throw new NoSuchElementException();
      } else {
         return this.remove(this.head.next).handler();
      }
   }

   public final ChannelHandler removeLast() {
      if (this.head.next == this.tail) {
         throw new NoSuchElementException();
      } else {
         return this.remove(this.tail.prev).handler();
      }
   }

   public final ChannelPipeline replace(ChannelHandler oldHandler, String newName, ChannelHandler newHandler) {
      this.replace(this.getContextOrDie(oldHandler), newName, newHandler);
      return this;
   }

   public final ChannelHandler replace(String oldName, String newName, ChannelHandler newHandler) {
      return this.replace(this.getContextOrDie(oldName), newName, newHandler);
   }

   public final ChannelHandler replace(Class oldHandlerType, String newName, ChannelHandler newHandler) {
      return this.replace(this.getContextOrDie(oldHandlerType), newName, newHandler);
   }

   private ChannelHandler replace(final AbstractChannelHandlerContext ctx, String newName, ChannelHandler newHandler) {
      assert ctx != this.head && ctx != this.tail;

      final AbstractChannelHandlerContext newCtx;
      synchronized(this) {
         checkMultiplicity(newHandler);
         if (newName == null) {
            newName = this.generateName(newHandler);
         } else {
            boolean sameName = ctx.name().equals(newName);
            if (!sameName) {
               this.checkDuplicateName(newName);
            }
         }

         newCtx = this.newContext(ctx.executor, newName, newHandler);
         replace0(ctx, newCtx);
         if (!this.registered) {
            this.callHandlerCallbackLater(newCtx, true);
            this.callHandlerCallbackLater(ctx, false);
            return ctx.handler();
         }

         EventExecutor executor = ctx.executor();
         if (!executor.inEventLoop()) {
            executor.execute(new Runnable() {
               public void run() {
                  DefaultChannelPipeline.this.callHandlerAdded0(newCtx);
                  DefaultChannelPipeline.this.callHandlerRemoved0(ctx);
               }
            });
            return ctx.handler();
         }
      }

      this.callHandlerAdded0(newCtx);
      this.callHandlerRemoved0(ctx);
      return ctx.handler();
   }

   private static void replace0(AbstractChannelHandlerContext oldCtx, AbstractChannelHandlerContext newCtx) {
      AbstractChannelHandlerContext prev = oldCtx.prev;
      AbstractChannelHandlerContext next = oldCtx.next;
      newCtx.prev = prev;
      newCtx.next = next;
      prev.next = newCtx;
      next.prev = newCtx;
      oldCtx.prev = newCtx;
      oldCtx.next = newCtx;
   }

   private static void checkMultiplicity(ChannelHandler handler) {
      if (handler instanceof ChannelHandlerAdapter) {
         ChannelHandlerAdapter h = (ChannelHandlerAdapter)handler;
         if (!h.isSharable() && h.added) {
            throw new ChannelPipelineException(h.getClass().getName() + " is not a @Sharable handler, so can't be added or removed multiple times.");
         }

         h.added = true;
      }

   }

   private void callHandlerAdded0(AbstractChannelHandlerContext ctx) {
      try {
         ctx.handler().handlerAdded(ctx);
         ctx.setAddComplete();
      } catch (Throwable var10) {
         boolean removed = false;

         try {
            remove0(ctx);

            try {
               ctx.handler().handlerRemoved(ctx);
            } finally {
               ctx.setRemoved();
            }

            removed = true;
         } catch (Throwable var9) {
            if (logger.isWarnEnabled()) {
               logger.warn("Failed to remove a handler: " + ctx.name(), var9);
            }
         }

         if (removed) {
            this.fireExceptionCaught(new ChannelPipelineException(ctx.handler().getClass().getName() + ".handlerAdded() has thrown an exception; removed.", var10));
         } else {
            this.fireExceptionCaught(new ChannelPipelineException(ctx.handler().getClass().getName() + ".handlerAdded() has thrown an exception; also failed to remove.", var10));
         }
      }

   }

   private void callHandlerRemoved0(AbstractChannelHandlerContext ctx) {
      try {
         try {
            ctx.handler().handlerRemoved(ctx);
         } finally {
            ctx.setRemoved();
         }
      } catch (Throwable var6) {
         this.fireExceptionCaught(new ChannelPipelineException(ctx.handler().getClass().getName() + ".handlerRemoved() has thrown an exception.", var6));
      }

   }

   final void invokeHandlerAddedIfNeeded() {
      assert this.channel.eventLoop().inEventLoop();

      if (this.firstRegistration) {
         this.firstRegistration = false;
         this.callHandlerAddedForAllHandlers();
      }

   }

   public final ChannelHandler first() {
      ChannelHandlerContext first = this.firstContext();
      return first == null ? null : first.handler();
   }

   public final ChannelHandlerContext firstContext() {
      AbstractChannelHandlerContext first = this.head.next;
      return first == this.tail ? null : this.head.next;
   }

   public final ChannelHandler last() {
      AbstractChannelHandlerContext last = this.tail.prev;
      return last == this.head ? null : last.handler();
   }

   public final ChannelHandlerContext lastContext() {
      AbstractChannelHandlerContext last = this.tail.prev;
      return last == this.head ? null : last;
   }

   public final ChannelHandler get(String name) {
      ChannelHandlerContext ctx = this.context(name);
      return ctx == null ? null : ctx.handler();
   }

   public final ChannelHandler get(Class handlerType) {
      ChannelHandlerContext ctx = this.context(handlerType);
      return ctx == null ? null : ctx.handler();
   }

   public final ChannelHandlerContext context(String name) {
      if (name == null) {
         throw new NullPointerException("name");
      } else {
         return this.context0(name);
      }
   }

   public final ChannelHandlerContext context(ChannelHandler handler) {
      if (handler == null) {
         throw new NullPointerException("handler");
      } else {
         for(AbstractChannelHandlerContext ctx = this.head.next; ctx != null; ctx = ctx.next) {
            if (ctx.handler() == handler) {
               return ctx;
            }
         }

         return null;
      }
   }

   public final ChannelHandlerContext context(Class handlerType) {
      if (handlerType == null) {
         throw new NullPointerException("handlerType");
      } else {
         for(AbstractChannelHandlerContext ctx = this.head.next; ctx != null; ctx = ctx.next) {
            if (handlerType.isAssignableFrom(ctx.handler().getClass())) {
               return ctx;
            }
         }

         return null;
      }
   }

   public final List names() {
      List list = new ArrayList();

      for(AbstractChannelHandlerContext ctx = this.head.next; ctx != null; ctx = ctx.next) {
         list.add(ctx.name());
      }

      return list;
   }

   public final Map toMap() {
      Map map = new LinkedHashMap();

      for(AbstractChannelHandlerContext ctx = this.head.next; ctx != this.tail; ctx = ctx.next) {
         map.put(ctx.name(), ctx.handler());
      }

      return map;
   }

   public final Iterator iterator() {
      return this.toMap().entrySet().iterator();
   }

   public final String toString() {
      StringBuilder buf = (new StringBuilder()).append(StringUtil.simpleClassName((Object)this)).append('{');
      AbstractChannelHandlerContext ctx = this.head.next;

      while(ctx != this.tail) {
         buf.append('(').append(ctx.name()).append(" = ").append(ctx.handler().getClass().getName()).append(')');
         ctx = ctx.next;
         if (ctx == this.tail) {
            break;
         }

         buf.append(", ");
      }

      buf.append('}');
      return buf.toString();
   }

   public final ChannelPipeline fireChannelRegistered() {
      AbstractChannelHandlerContext.invokeChannelRegistered(this.head);
      return this;
   }

   public final ChannelPipeline fireChannelUnregistered() {
      AbstractChannelHandlerContext.invokeChannelUnregistered(this.head);
      return this;
   }

   private synchronized void destroy() {
      this.destroyUp(this.head.next, false);
   }

   private void destroyUp(final AbstractChannelHandlerContext ctx, boolean inEventLoop) {
      Thread currentThread = Thread.currentThread();
      AbstractChannelHandlerContext tail = this.tail;

      while(true) {
         if (ctx == tail) {
            this.destroyDown(currentThread, tail.prev, inEventLoop);
            break;
         }

         EventExecutor executor = ctx.executor();
         if (!inEventLoop && !executor.inEventLoop(currentThread)) {
            executor.execute(new Runnable() {
               public void run() {
                  DefaultChannelPipeline.this.destroyUp(ctx, true);
               }
            });
            break;
         }

         ctx = ctx.next;
         inEventLoop = false;
      }

   }

   private void destroyDown(Thread currentThread, final AbstractChannelHandlerContext ctx, boolean inEventLoop) {
      for(AbstractChannelHandlerContext head = this.head; ctx != head; inEventLoop = false) {
         EventExecutor executor = ctx.executor();
         if (!inEventLoop && !executor.inEventLoop(currentThread)) {
            executor.execute(new Runnable() {
               public void run() {
                  DefaultChannelPipeline.this.destroyDown(Thread.currentThread(), ctx, true);
               }
            });
            break;
         }

         synchronized(this) {
            remove0(ctx);
         }

         this.callHandlerRemoved0(ctx);
         ctx = ctx.prev;
      }

   }

   public final ChannelPipeline fireChannelActive() {
      AbstractChannelHandlerContext.invokeChannelActive(this.head);
      return this;
   }

   public final ChannelPipeline fireChannelInactive() {
      AbstractChannelHandlerContext.invokeChannelInactive(this.head);
      return this;
   }

   public final ChannelPipeline fireExceptionCaught(Throwable cause) {
      AbstractChannelHandlerContext.invokeExceptionCaught(this.head, cause);
      return this;
   }

   public final ChannelPipeline fireUserEventTriggered(Object event) {
      AbstractChannelHandlerContext.invokeUserEventTriggered(this.head, event);
      return this;
   }

   public final ChannelPipeline fireChannelRead(Object msg) {
      AbstractChannelHandlerContext.invokeChannelRead(this.head, msg);
      return this;
   }

   public final ChannelPipeline fireChannelReadComplete() {
      AbstractChannelHandlerContext.invokeChannelReadComplete(this.head);
      return this;
   }

   public final ChannelPipeline fireChannelWritabilityChanged() {
      AbstractChannelHandlerContext.invokeChannelWritabilityChanged(this.head);
      return this;
   }

   public final ChannelFuture bind(SocketAddress localAddress) {
      return this.tail.bind(localAddress);
   }

   public final ChannelFuture connect(SocketAddress remoteAddress) {
      return this.tail.connect(remoteAddress);
   }

   public final ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
      return this.tail.connect(remoteAddress, localAddress);
   }

   public final ChannelFuture disconnect() {
      return this.tail.disconnect();
   }

   public final ChannelFuture close() {
      return this.tail.close();
   }

   public final ChannelFuture deregister() {
      return this.tail.deregister();
   }

   public final ChannelPipeline flush() {
      this.tail.flush();
      return this;
   }

   public final ChannelFuture bind(SocketAddress localAddress, ChannelPromise promise) {
      return this.tail.bind(localAddress, promise);
   }

   public final ChannelFuture connect(SocketAddress remoteAddress, ChannelPromise promise) {
      return this.tail.connect(remoteAddress, promise);
   }

   public final ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
      return this.tail.connect(remoteAddress, localAddress, promise);
   }

   public final ChannelFuture disconnect(ChannelPromise promise) {
      return this.tail.disconnect(promise);
   }

   public final ChannelFuture close(ChannelPromise promise) {
      return this.tail.close(promise);
   }

   public final ChannelFuture deregister(ChannelPromise promise) {
      return this.tail.deregister(promise);
   }

   public final ChannelPipeline read() {
      this.tail.read();
      return this;
   }

   public final ChannelFuture write(Object msg) {
      return this.tail.write(msg);
   }

   public final ChannelFuture write(Object msg, ChannelPromise promise) {
      return this.tail.write(msg, promise);
   }

   public final ChannelFuture writeAndFlush(Object msg, ChannelPromise promise) {
      return this.tail.writeAndFlush(msg, promise);
   }

   public final ChannelFuture writeAndFlush(Object msg) {
      return this.tail.writeAndFlush(msg);
   }

   public final ChannelPromise newPromise() {
      return new DefaultChannelPromise(this.channel);
   }

   public final ChannelProgressivePromise newProgressivePromise() {
      return new DefaultChannelProgressivePromise(this.channel);
   }

   public final ChannelFuture newSucceededFuture() {
      return this.succeededFuture;
   }

   public final ChannelFuture newFailedFuture(Throwable cause) {
      return new FailedChannelFuture(this.channel, (EventExecutor)null, cause);
   }

   public final ChannelPromise voidPromise() {
      return this.voidPromise;
   }

   private void checkDuplicateName(String name) {
      if (this.context0(name) != null) {
         throw new IllegalArgumentException("Duplicate handler name: " + name);
      }
   }

   private AbstractChannelHandlerContext context0(String name) {
      for(AbstractChannelHandlerContext context = this.head.next; context != this.tail; context = context.next) {
         if (context.name().equals(name)) {
            return context;
         }
      }

      return null;
   }

   private AbstractChannelHandlerContext getContextOrDie(String name) {
      AbstractChannelHandlerContext ctx = (AbstractChannelHandlerContext)this.context(name);
      if (ctx == null) {
         throw new NoSuchElementException(name);
      } else {
         return ctx;
      }
   }

   private AbstractChannelHandlerContext getContextOrDie(ChannelHandler handler) {
      AbstractChannelHandlerContext ctx = (AbstractChannelHandlerContext)this.context(handler);
      if (ctx == null) {
         throw new NoSuchElementException(handler.getClass().getName());
      } else {
         return ctx;
      }
   }

   private AbstractChannelHandlerContext getContextOrDie(Class handlerType) {
      AbstractChannelHandlerContext ctx = (AbstractChannelHandlerContext)this.context(handlerType);
      if (ctx == null) {
         throw new NoSuchElementException(handlerType.getName());
      } else {
         return ctx;
      }
   }

   private void callHandlerAddedForAllHandlers() {
      PendingHandlerCallback pendingHandlerCallbackHead;
      synchronized(this) {
         assert !this.registered;

         this.registered = true;
         pendingHandlerCallbackHead = this.pendingHandlerCallbackHead;
         this.pendingHandlerCallbackHead = null;
      }

      for(PendingHandlerCallback task = pendingHandlerCallbackHead; task != null; task = task.next) {
         task.execute();
      }

   }

   private void callHandlerCallbackLater(AbstractChannelHandlerContext ctx, boolean added) {
      assert !this.registered;

      PendingHandlerCallback task = added ? new PendingHandlerAddedTask(ctx) : new PendingHandlerRemovedTask(ctx);
      PendingHandlerCallback pending = this.pendingHandlerCallbackHead;
      if (pending == null) {
         this.pendingHandlerCallbackHead = (PendingHandlerCallback)task;
      } else {
         while(pending.next != null) {
            pending = pending.next;
         }

         pending.next = (PendingHandlerCallback)task;
      }

   }

   protected void onUnhandledInboundException(Throwable cause) {
      try {
         logger.warn("An exceptionCaught() event was fired, and it reached at the tail of the pipeline. It usually means the last handler in the pipeline did not handle the exception.", cause);
      } finally {
         ReferenceCountUtil.release(cause);
      }

   }

   protected void onUnhandledInboundMessage(Object msg) {
      try {
         logger.debug("Discarded inbound message {} that reached at the tail of the pipeline. Please check your pipeline configuration.", msg);
      } finally {
         ReferenceCountUtil.release(msg);
      }

   }

   private final class PendingHandlerRemovedTask extends PendingHandlerCallback {
      PendingHandlerRemovedTask(AbstractChannelHandlerContext ctx) {
         super(ctx);
      }

      public void run() {
         DefaultChannelPipeline.this.callHandlerRemoved0(this.ctx);
      }

      void execute() {
         EventExecutor executor = this.ctx.executor();
         if (executor.inEventLoop()) {
            DefaultChannelPipeline.this.callHandlerRemoved0(this.ctx);
         } else {
            try {
               executor.execute(this);
            } catch (RejectedExecutionException var3) {
               if (DefaultChannelPipeline.logger.isWarnEnabled()) {
                  DefaultChannelPipeline.logger.warn("Can't invoke handlerRemoved() as the EventExecutor {} rejected it, removing handler {}.", executor, this.ctx.name(), var3);
               }

               this.ctx.setRemoved();
            }
         }

      }
   }

   private final class PendingHandlerAddedTask extends PendingHandlerCallback {
      PendingHandlerAddedTask(AbstractChannelHandlerContext ctx) {
         super(ctx);
      }

      public void run() {
         DefaultChannelPipeline.this.callHandlerAdded0(this.ctx);
      }

      void execute() {
         EventExecutor executor = this.ctx.executor();
         if (executor.inEventLoop()) {
            DefaultChannelPipeline.this.callHandlerAdded0(this.ctx);
         } else {
            try {
               executor.execute(this);
            } catch (RejectedExecutionException var3) {
               if (DefaultChannelPipeline.logger.isWarnEnabled()) {
                  DefaultChannelPipeline.logger.warn("Can't invoke handlerAdded() as the EventExecutor {} rejected it, removing handler {}.", executor, this.ctx.name(), var3);
               }

               DefaultChannelPipeline.remove0(this.ctx);
               this.ctx.setRemoved();
            }
         }

      }
   }

   private abstract static class PendingHandlerCallback implements Runnable {
      final AbstractChannelHandlerContext ctx;
      PendingHandlerCallback next;

      PendingHandlerCallback(AbstractChannelHandlerContext ctx) {
         this.ctx = ctx;
      }

      abstract void execute();
   }

   final class HeadContext extends AbstractChannelHandlerContext implements ChannelOutboundHandler, ChannelInboundHandler {
      private final Channel.Unsafe unsafe;

      HeadContext(DefaultChannelPipeline pipeline) {
         super(pipeline, (EventExecutor)null, DefaultChannelPipeline.HEAD_NAME, false, true);
         this.unsafe = pipeline.channel().unsafe();
         this.setAddComplete();
      }

      public ChannelHandler handler() {
         return this;
      }

      public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      }

      public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
      }

      public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
         this.unsafe.bind(localAddress, promise);
      }

      public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
         this.unsafe.connect(remoteAddress, localAddress, promise);
      }

      public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
         this.unsafe.disconnect(promise);
      }

      public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
         this.unsafe.close(promise);
      }

      public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
         this.unsafe.deregister(promise);
      }

      public void read(ChannelHandlerContext ctx) {
         this.unsafe.beginRead();
      }

      public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
         this.unsafe.write(msg, promise);
      }

      public void flush(ChannelHandlerContext ctx) throws Exception {
         this.unsafe.flush();
      }

      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
         ctx.fireExceptionCaught(cause);
      }

      public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
         DefaultChannelPipeline.this.invokeHandlerAddedIfNeeded();
         ctx.fireChannelRegistered();
      }

      public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
         ctx.fireChannelUnregistered();
         if (!DefaultChannelPipeline.this.channel.isOpen()) {
            DefaultChannelPipeline.this.destroy();
         }

      }

      public void channelActive(ChannelHandlerContext ctx) throws Exception {
         ctx.fireChannelActive();
         this.readIfIsAutoRead();
      }

      public void channelInactive(ChannelHandlerContext ctx) throws Exception {
         ctx.fireChannelInactive();
      }

      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
         ctx.fireChannelRead(msg);
      }

      public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
         ctx.fireChannelReadComplete();
         this.readIfIsAutoRead();
      }

      private void readIfIsAutoRead() {
         if (DefaultChannelPipeline.this.channel.config().isAutoRead()) {
            DefaultChannelPipeline.this.channel.read();
         }

      }

      public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
         ctx.fireUserEventTriggered(evt);
      }

      public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
         ctx.fireChannelWritabilityChanged();
      }
   }

   final class TailContext extends AbstractChannelHandlerContext implements ChannelInboundHandler {
      TailContext(DefaultChannelPipeline pipeline) {
         super(pipeline, (EventExecutor)null, DefaultChannelPipeline.TAIL_NAME, true, false);
         this.setAddComplete();
      }

      public ChannelHandler handler() {
         return this;
      }

      public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
      }

      public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
      }

      public void channelActive(ChannelHandlerContext ctx) throws Exception {
      }

      public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      }

      public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
      }

      public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
      }

      public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
      }

      public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
         ReferenceCountUtil.release(evt);
      }

      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
         DefaultChannelPipeline.this.onUnhandledInboundException(cause);
      }

      public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
         DefaultChannelPipeline.this.onUnhandledInboundMessage(msg);
      }

      public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      }
   }
}
