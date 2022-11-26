package org.python.netty.bootstrap;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelFutureListener;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelOption;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.channel.DefaultChannelPromise;
import org.python.netty.channel.EventLoopGroup;
import org.python.netty.channel.ReflectiveChannelFactory;
import org.python.netty.util.AttributeKey;
import org.python.netty.util.concurrent.EventExecutor;
import org.python.netty.util.concurrent.GlobalEventExecutor;
import org.python.netty.util.internal.SocketUtils;
import org.python.netty.util.internal.StringUtil;
import org.python.netty.util.internal.logging.InternalLogger;

public abstract class AbstractBootstrap implements Cloneable {
   volatile EventLoopGroup group;
   private volatile ChannelFactory channelFactory;
   private volatile SocketAddress localAddress;
   private final Map options = new LinkedHashMap();
   private final Map attrs = new LinkedHashMap();
   private volatile ChannelHandler handler;

   AbstractBootstrap() {
   }

   AbstractBootstrap(AbstractBootstrap bootstrap) {
      this.group = bootstrap.group;
      this.channelFactory = bootstrap.channelFactory;
      this.handler = bootstrap.handler;
      this.localAddress = bootstrap.localAddress;
      synchronized(bootstrap.options) {
         this.options.putAll(bootstrap.options);
      }

      synchronized(bootstrap.attrs) {
         this.attrs.putAll(bootstrap.attrs);
      }
   }

   public AbstractBootstrap group(EventLoopGroup group) {
      if (group == null) {
         throw new NullPointerException("group");
      } else if (this.group != null) {
         throw new IllegalStateException("group set already");
      } else {
         this.group = group;
         return this;
      }
   }

   public AbstractBootstrap channel(Class channelClass) {
      if (channelClass == null) {
         throw new NullPointerException("channelClass");
      } else {
         return this.channelFactory((org.python.netty.channel.ChannelFactory)(new ReflectiveChannelFactory(channelClass)));
      }
   }

   /** @deprecated */
   @Deprecated
   public AbstractBootstrap channelFactory(ChannelFactory channelFactory) {
      if (channelFactory == null) {
         throw new NullPointerException("channelFactory");
      } else if (this.channelFactory != null) {
         throw new IllegalStateException("channelFactory set already");
      } else {
         this.channelFactory = channelFactory;
         return this;
      }
   }

   public AbstractBootstrap channelFactory(org.python.netty.channel.ChannelFactory channelFactory) {
      return this.channelFactory((ChannelFactory)channelFactory);
   }

   public AbstractBootstrap localAddress(SocketAddress localAddress) {
      this.localAddress = localAddress;
      return this;
   }

   public AbstractBootstrap localAddress(int inetPort) {
      return this.localAddress(new InetSocketAddress(inetPort));
   }

   public AbstractBootstrap localAddress(String inetHost, int inetPort) {
      return this.localAddress(SocketUtils.socketAddress(inetHost, inetPort));
   }

   public AbstractBootstrap localAddress(InetAddress inetHost, int inetPort) {
      return this.localAddress(new InetSocketAddress(inetHost, inetPort));
   }

   public AbstractBootstrap option(ChannelOption option, Object value) {
      if (option == null) {
         throw new NullPointerException("option");
      } else {
         if (value == null) {
            synchronized(this.options) {
               this.options.remove(option);
            }
         } else {
            synchronized(this.options) {
               this.options.put(option, value);
            }
         }

         return this;
      }
   }

   public AbstractBootstrap attr(AttributeKey key, Object value) {
      if (key == null) {
         throw new NullPointerException("key");
      } else {
         if (value == null) {
            synchronized(this.attrs) {
               this.attrs.remove(key);
            }
         } else {
            synchronized(this.attrs) {
               this.attrs.put(key, value);
            }
         }

         return this;
      }
   }

   public AbstractBootstrap validate() {
      if (this.group == null) {
         throw new IllegalStateException("group not set");
      } else if (this.channelFactory == null) {
         throw new IllegalStateException("channel or channelFactory not set");
      } else {
         return this;
      }
   }

   public abstract AbstractBootstrap clone();

   public ChannelFuture register() {
      this.validate();
      return this.initAndRegister();
   }

   public ChannelFuture bind() {
      this.validate();
      SocketAddress localAddress = this.localAddress;
      if (localAddress == null) {
         throw new IllegalStateException("localAddress not set");
      } else {
         return this.doBind(localAddress);
      }
   }

   public ChannelFuture bind(int inetPort) {
      return this.bind(new InetSocketAddress(inetPort));
   }

   public ChannelFuture bind(String inetHost, int inetPort) {
      return this.bind(SocketUtils.socketAddress(inetHost, inetPort));
   }

   public ChannelFuture bind(InetAddress inetHost, int inetPort) {
      return this.bind(new InetSocketAddress(inetHost, inetPort));
   }

   public ChannelFuture bind(SocketAddress localAddress) {
      this.validate();
      if (localAddress == null) {
         throw new NullPointerException("localAddress");
      } else {
         return this.doBind(localAddress);
      }
   }

   private ChannelFuture doBind(final SocketAddress localAddress) {
      final ChannelFuture regFuture = this.initAndRegister();
      final Channel channel = regFuture.channel();
      if (regFuture.cause() != null) {
         return regFuture;
      } else if (regFuture.isDone()) {
         ChannelPromise promise = channel.newPromise();
         doBind0(regFuture, channel, localAddress, promise);
         return promise;
      } else {
         final PendingRegistrationPromise promise = new PendingRegistrationPromise(channel);
         regFuture.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
               Throwable cause = future.cause();
               if (cause != null) {
                  promise.setFailure(cause);
               } else {
                  promise.registered();
                  AbstractBootstrap.doBind0(regFuture, channel, localAddress, promise);
               }

            }
         });
         return promise;
      }
   }

   final ChannelFuture initAndRegister() {
      Channel channel = null;

      try {
         channel = this.channelFactory.newChannel();
         this.init(channel);
      } catch (Throwable var3) {
         if (channel != null) {
            channel.unsafe().closeForcibly();
         }

         return (new DefaultChannelPromise(channel, GlobalEventExecutor.INSTANCE)).setFailure(var3);
      }

      ChannelFuture regFuture = this.config().group().register(channel);
      if (regFuture.cause() != null) {
         if (channel.isRegistered()) {
            channel.close();
         } else {
            channel.unsafe().closeForcibly();
         }
      }

      return regFuture;
   }

   abstract void init(Channel var1) throws Exception;

   private static void doBind0(final ChannelFuture regFuture, final Channel channel, final SocketAddress localAddress, final ChannelPromise promise) {
      channel.eventLoop().execute(new Runnable() {
         public void run() {
            if (regFuture.isSuccess()) {
               channel.bind(localAddress, promise).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            } else {
               promise.setFailure(regFuture.cause());
            }

         }
      });
   }

   public AbstractBootstrap handler(ChannelHandler handler) {
      if (handler == null) {
         throw new NullPointerException("handler");
      } else {
         this.handler = handler;
         return this;
      }
   }

   /** @deprecated */
   @Deprecated
   public final EventLoopGroup group() {
      return this.group;
   }

   public abstract AbstractBootstrapConfig config();

   static Map copiedMap(Map map) {
      LinkedHashMap copied;
      synchronized(map) {
         if (map.isEmpty()) {
            return Collections.emptyMap();
         }

         copied = new LinkedHashMap(map);
      }

      return Collections.unmodifiableMap(copied);
   }

   final Map options0() {
      return this.options;
   }

   final Map attrs0() {
      return this.attrs;
   }

   final SocketAddress localAddress() {
      return this.localAddress;
   }

   final ChannelFactory channelFactory() {
      return this.channelFactory;
   }

   final ChannelHandler handler() {
      return this.handler;
   }

   final Map options() {
      return copiedMap(this.options);
   }

   final Map attrs() {
      return copiedMap(this.attrs);
   }

   static void setChannelOptions(Channel channel, Map options, InternalLogger logger) {
      Iterator var3 = options.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry e = (Map.Entry)var3.next();
         setChannelOption(channel, (ChannelOption)e.getKey(), e.getValue(), logger);
      }

   }

   static void setChannelOptions(Channel channel, Map.Entry[] options, InternalLogger logger) {
      Map.Entry[] var3 = options;
      int var4 = options.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Map.Entry e = var3[var5];
         setChannelOption(channel, (ChannelOption)e.getKey(), e.getValue(), logger);
      }

   }

   private static void setChannelOption(Channel channel, ChannelOption option, Object value, InternalLogger logger) {
      try {
         if (!channel.config().setOption(option, value)) {
            logger.warn("Unknown channel option '{}' for channel '{}'", option, channel);
         }
      } catch (Throwable var5) {
         logger.warn("Failed to set channel option '{}' with value '{}' for channel '{}'", option, value, channel, var5);
      }

   }

   public String toString() {
      StringBuilder buf = (new StringBuilder()).append(StringUtil.simpleClassName((Object)this)).append('(').append(this.config()).append(')');
      return buf.toString();
   }

   static final class PendingRegistrationPromise extends DefaultChannelPromise {
      private volatile boolean registered;

      PendingRegistrationPromise(Channel channel) {
         super(channel);
      }

      void registered() {
         this.registered = true;
      }

      protected EventExecutor executor() {
         return (EventExecutor)(this.registered ? super.executor() : GlobalEventExecutor.INSTANCE);
      }
   }
}
