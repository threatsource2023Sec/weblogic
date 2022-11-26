package org.python.netty.bootstrap;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Iterator;
import java.util.Map;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelFutureListener;
import org.python.netty.channel.ChannelPipeline;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.channel.EventLoop;
import org.python.netty.channel.EventLoopGroup;
import org.python.netty.resolver.AddressResolver;
import org.python.netty.resolver.AddressResolverGroup;
import org.python.netty.resolver.DefaultAddressResolverGroup;
import org.python.netty.util.AttributeKey;
import org.python.netty.util.concurrent.Future;
import org.python.netty.util.concurrent.FutureListener;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class Bootstrap extends AbstractBootstrap {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(Bootstrap.class);
   private static final AddressResolverGroup DEFAULT_RESOLVER;
   private final BootstrapConfig config = new BootstrapConfig(this);
   private volatile AddressResolverGroup resolver;
   private volatile SocketAddress remoteAddress;

   public Bootstrap() {
      this.resolver = DEFAULT_RESOLVER;
   }

   private Bootstrap(Bootstrap bootstrap) {
      super(bootstrap);
      this.resolver = DEFAULT_RESOLVER;
      this.resolver = bootstrap.resolver;
      this.remoteAddress = bootstrap.remoteAddress;
   }

   public Bootstrap resolver(AddressResolverGroup resolver) {
      this.resolver = resolver == null ? DEFAULT_RESOLVER : resolver;
      return this;
   }

   public Bootstrap remoteAddress(SocketAddress remoteAddress) {
      this.remoteAddress = remoteAddress;
      return this;
   }

   public Bootstrap remoteAddress(String inetHost, int inetPort) {
      this.remoteAddress = InetSocketAddress.createUnresolved(inetHost, inetPort);
      return this;
   }

   public Bootstrap remoteAddress(InetAddress inetHost, int inetPort) {
      this.remoteAddress = new InetSocketAddress(inetHost, inetPort);
      return this;
   }

   public ChannelFuture connect() {
      this.validate();
      SocketAddress remoteAddress = this.remoteAddress;
      if (remoteAddress == null) {
         throw new IllegalStateException("remoteAddress not set");
      } else {
         return this.doResolveAndConnect(remoteAddress, this.config.localAddress());
      }
   }

   public ChannelFuture connect(String inetHost, int inetPort) {
      return this.connect(InetSocketAddress.createUnresolved(inetHost, inetPort));
   }

   public ChannelFuture connect(InetAddress inetHost, int inetPort) {
      return this.connect(new InetSocketAddress(inetHost, inetPort));
   }

   public ChannelFuture connect(SocketAddress remoteAddress) {
      if (remoteAddress == null) {
         throw new NullPointerException("remoteAddress");
      } else {
         this.validate();
         return this.doResolveAndConnect(remoteAddress, this.config.localAddress());
      }
   }

   public ChannelFuture connect(SocketAddress remoteAddress, SocketAddress localAddress) {
      if (remoteAddress == null) {
         throw new NullPointerException("remoteAddress");
      } else {
         this.validate();
         return this.doResolveAndConnect(remoteAddress, localAddress);
      }
   }

   private ChannelFuture doResolveAndConnect(final SocketAddress remoteAddress, final SocketAddress localAddress) {
      ChannelFuture regFuture = this.initAndRegister();
      final Channel channel = regFuture.channel();
      if (regFuture.isDone()) {
         return !regFuture.isSuccess() ? regFuture : this.doResolveAndConnect0(channel, remoteAddress, localAddress, channel.newPromise());
      } else {
         final AbstractBootstrap.PendingRegistrationPromise promise = new AbstractBootstrap.PendingRegistrationPromise(channel);
         regFuture.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) throws Exception {
               Throwable cause = future.cause();
               if (cause != null) {
                  promise.setFailure(cause);
               } else {
                  promise.registered();
                  Bootstrap.this.doResolveAndConnect0(channel, remoteAddress, localAddress, promise);
               }

            }
         });
         return promise;
      }
   }

   private ChannelFuture doResolveAndConnect0(final Channel channel, SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
      try {
         EventLoop eventLoop = channel.eventLoop();
         AddressResolver resolver = this.resolver.getResolver(eventLoop);
         if (!resolver.isSupported(remoteAddress) || resolver.isResolved(remoteAddress)) {
            doConnect(remoteAddress, localAddress, promise);
            return promise;
         }

         Future resolveFuture = resolver.resolve(remoteAddress);
         if (resolveFuture.isDone()) {
            Throwable resolveFailureCause = resolveFuture.cause();
            if (resolveFailureCause != null) {
               channel.close();
               promise.setFailure(resolveFailureCause);
            } else {
               doConnect((SocketAddress)resolveFuture.getNow(), localAddress, promise);
            }

            return promise;
         }

         resolveFuture.addListener(new FutureListener() {
            public void operationComplete(Future future) throws Exception {
               if (future.cause() != null) {
                  channel.close();
                  promise.setFailure(future.cause());
               } else {
                  Bootstrap.doConnect((SocketAddress)future.getNow(), localAddress, promise);
               }

            }
         });
      } catch (Throwable var9) {
         promise.tryFailure(var9);
      }

      return promise;
   }

   private static void doConnect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise connectPromise) {
      final Channel channel = connectPromise.channel();
      channel.eventLoop().execute(new Runnable() {
         public void run() {
            if (localAddress == null) {
               channel.connect(remoteAddress, connectPromise);
            } else {
               channel.connect(remoteAddress, localAddress, connectPromise);
            }

            connectPromise.addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
         }
      });
   }

   void init(Channel channel) throws Exception {
      ChannelPipeline p = channel.pipeline();
      p.addLast(this.config.handler());
      Map options = this.options0();
      synchronized(options) {
         setChannelOptions(channel, options, logger);
      }

      Map attrs = this.attrs0();
      synchronized(attrs) {
         Iterator var6 = attrs.entrySet().iterator();

         while(var6.hasNext()) {
            Map.Entry e = (Map.Entry)var6.next();
            channel.attr((AttributeKey)e.getKey()).set(e.getValue());
         }

      }
   }

   public Bootstrap validate() {
      super.validate();
      if (this.config.handler() == null) {
         throw new IllegalStateException("handler not set");
      } else {
         return this;
      }
   }

   public Bootstrap clone() {
      return new Bootstrap(this);
   }

   public Bootstrap clone(EventLoopGroup group) {
      Bootstrap bs = new Bootstrap(this);
      bs.group = group;
      return bs;
   }

   public final BootstrapConfig config() {
      return this.config;
   }

   final SocketAddress remoteAddress() {
      return this.remoteAddress;
   }

   final AddressResolverGroup resolver() {
      return this.resolver;
   }

   static {
      DEFAULT_RESOLVER = DefaultAddressResolverGroup.INSTANCE;
   }
}
