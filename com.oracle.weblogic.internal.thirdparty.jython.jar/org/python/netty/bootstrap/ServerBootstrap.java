package org.python.netty.bootstrap;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.python.netty.channel.Channel;
import org.python.netty.channel.ChannelConfig;
import org.python.netty.channel.ChannelFuture;
import org.python.netty.channel.ChannelFutureListener;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelInboundHandlerAdapter;
import org.python.netty.channel.ChannelInitializer;
import org.python.netty.channel.ChannelOption;
import org.python.netty.channel.ChannelPipeline;
import org.python.netty.channel.EventLoopGroup;
import org.python.netty.util.AttributeKey;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public class ServerBootstrap extends AbstractBootstrap {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ServerBootstrap.class);
   private final Map childOptions = new LinkedHashMap();
   private final Map childAttrs = new LinkedHashMap();
   private final ServerBootstrapConfig config = new ServerBootstrapConfig(this);
   private volatile EventLoopGroup childGroup;
   private volatile ChannelHandler childHandler;

   public ServerBootstrap() {
   }

   private ServerBootstrap(ServerBootstrap bootstrap) {
      super(bootstrap);
      this.childGroup = bootstrap.childGroup;
      this.childHandler = bootstrap.childHandler;
      synchronized(bootstrap.childOptions) {
         this.childOptions.putAll(bootstrap.childOptions);
      }

      synchronized(bootstrap.childAttrs) {
         this.childAttrs.putAll(bootstrap.childAttrs);
      }
   }

   public ServerBootstrap group(EventLoopGroup group) {
      return this.group(group, group);
   }

   public ServerBootstrap group(EventLoopGroup parentGroup, EventLoopGroup childGroup) {
      super.group(parentGroup);
      if (childGroup == null) {
         throw new NullPointerException("childGroup");
      } else if (this.childGroup != null) {
         throw new IllegalStateException("childGroup set already");
      } else {
         this.childGroup = childGroup;
         return this;
      }
   }

   public ServerBootstrap childOption(ChannelOption childOption, Object value) {
      if (childOption == null) {
         throw new NullPointerException("childOption");
      } else {
         if (value == null) {
            synchronized(this.childOptions) {
               this.childOptions.remove(childOption);
            }
         } else {
            synchronized(this.childOptions) {
               this.childOptions.put(childOption, value);
            }
         }

         return this;
      }
   }

   public ServerBootstrap childAttr(AttributeKey childKey, Object value) {
      if (childKey == null) {
         throw new NullPointerException("childKey");
      } else {
         if (value == null) {
            this.childAttrs.remove(childKey);
         } else {
            this.childAttrs.put(childKey, value);
         }

         return this;
      }
   }

   public ServerBootstrap childHandler(ChannelHandler childHandler) {
      if (childHandler == null) {
         throw new NullPointerException("childHandler");
      } else {
         this.childHandler = childHandler;
         return this;
      }
   }

   void init(Channel channel) throws Exception {
      Map options = this.options0();
      synchronized(options) {
         setChannelOptions(channel, options, logger);
      }

      Map attrs = this.attrs0();
      synchronized(attrs) {
         Iterator var5 = attrs.entrySet().iterator();

         while(true) {
            if (!var5.hasNext()) {
               break;
            }

            Map.Entry e = (Map.Entry)var5.next();
            AttributeKey key = (AttributeKey)e.getKey();
            channel.attr(key).set(e.getValue());
         }
      }

      ChannelPipeline p = channel.pipeline();
      final EventLoopGroup currentChildGroup = this.childGroup;
      final ChannelHandler currentChildHandler = this.childHandler;
      final Map.Entry[] currentChildOptions;
      synchronized(this.childOptions) {
         currentChildOptions = (Map.Entry[])this.childOptions.entrySet().toArray(newOptionArray(this.childOptions.size()));
      }

      final Map.Entry[] currentChildAttrs;
      synchronized(this.childAttrs) {
         currentChildAttrs = (Map.Entry[])this.childAttrs.entrySet().toArray(newAttrArray(this.childAttrs.size()));
      }

      p.addLast(new ChannelInitializer() {
         public void initChannel(final Channel ch) throws Exception {
            final ChannelPipeline pipeline = ch.pipeline();
            ChannelHandler handler = ServerBootstrap.this.config.handler();
            if (handler != null) {
               pipeline.addLast(handler);
            }

            ch.eventLoop().execute(new Runnable() {
               public void run() {
                  pipeline.addLast(new ServerBootstrapAcceptor(ch, currentChildGroup, currentChildHandler, currentChildOptions, currentChildAttrs));
               }
            });
         }
      });
   }

   public ServerBootstrap validate() {
      super.validate();
      if (this.childHandler == null) {
         throw new IllegalStateException("childHandler not set");
      } else {
         if (this.childGroup == null) {
            logger.warn("childGroup is not set. Using parentGroup instead.");
            this.childGroup = this.config.group();
         }

         return this;
      }
   }

   private static Map.Entry[] newAttrArray(int size) {
      return new Map.Entry[size];
   }

   private static Map.Entry[] newOptionArray(int size) {
      return new Map.Entry[size];
   }

   public ServerBootstrap clone() {
      return new ServerBootstrap(this);
   }

   /** @deprecated */
   @Deprecated
   public EventLoopGroup childGroup() {
      return this.childGroup;
   }

   final ChannelHandler childHandler() {
      return this.childHandler;
   }

   final Map childOptions() {
      return copiedMap(this.childOptions);
   }

   final Map childAttrs() {
      return copiedMap(this.childAttrs);
   }

   public final ServerBootstrapConfig config() {
      return this.config;
   }

   private static class ServerBootstrapAcceptor extends ChannelInboundHandlerAdapter {
      private final EventLoopGroup childGroup;
      private final ChannelHandler childHandler;
      private final Map.Entry[] childOptions;
      private final Map.Entry[] childAttrs;
      private final Runnable enableAutoReadTask;

      ServerBootstrapAcceptor(final Channel channel, EventLoopGroup childGroup, ChannelHandler childHandler, Map.Entry[] childOptions, Map.Entry[] childAttrs) {
         this.childGroup = childGroup;
         this.childHandler = childHandler;
         this.childOptions = childOptions;
         this.childAttrs = childAttrs;
         this.enableAutoReadTask = new Runnable() {
            public void run() {
               channel.config().setAutoRead(true);
            }
         };
      }

      public void channelRead(ChannelHandlerContext ctx, Object msg) {
         final Channel child = (Channel)msg;
         child.pipeline().addLast(this.childHandler);
         AbstractBootstrap.setChannelOptions(child, this.childOptions, ServerBootstrap.logger);
         Map.Entry[] var4 = this.childAttrs;
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Map.Entry e = var4[var6];
            child.attr((AttributeKey)e.getKey()).set(e.getValue());
         }

         try {
            this.childGroup.register(child).addListener(new ChannelFutureListener() {
               public void operationComplete(ChannelFuture future) throws Exception {
                  if (!future.isSuccess()) {
                     ServerBootstrap.ServerBootstrapAcceptor.forceClose(child, future.cause());
                  }

               }
            });
         } catch (Throwable var8) {
            forceClose(child, var8);
         }

      }

      private static void forceClose(Channel child, Throwable t) {
         child.unsafe().closeForcibly();
         ServerBootstrap.logger.warn("Failed to register an accepted channel: {}", child, t);
      }

      public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
         ChannelConfig config = ctx.channel().config();
         if (config.isAutoRead()) {
            config.setAutoRead(false);
            ctx.channel().eventLoop().schedule(this.enableAutoReadTask, 1L, TimeUnit.SECONDS);
         }

         ctx.fireExceptionCaught(cause);
      }
   }
}
