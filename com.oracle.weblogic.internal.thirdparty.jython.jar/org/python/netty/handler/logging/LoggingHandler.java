package org.python.netty.handler.logging;

import java.net.SocketAddress;
import org.python.netty.buffer.ByteBuf;
import org.python.netty.buffer.ByteBufHolder;
import org.python.netty.buffer.ByteBufUtil;
import org.python.netty.channel.ChannelDuplexHandler;
import org.python.netty.channel.ChannelHandler;
import org.python.netty.channel.ChannelHandlerContext;
import org.python.netty.channel.ChannelPromise;
import org.python.netty.util.internal.StringUtil;
import org.python.netty.util.internal.logging.InternalLogLevel;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

@ChannelHandler.Sharable
public class LoggingHandler extends ChannelDuplexHandler {
   private static final LogLevel DEFAULT_LEVEL;
   protected final InternalLogger logger;
   protected final InternalLogLevel internalLevel;
   private final LogLevel level;

   public LoggingHandler() {
      this(DEFAULT_LEVEL);
   }

   public LoggingHandler(LogLevel level) {
      if (level == null) {
         throw new NullPointerException("level");
      } else {
         this.logger = InternalLoggerFactory.getInstance(this.getClass());
         this.level = level;
         this.internalLevel = level.toInternalLevel();
      }
   }

   public LoggingHandler(Class clazz) {
      this(clazz, DEFAULT_LEVEL);
   }

   public LoggingHandler(Class clazz, LogLevel level) {
      if (clazz == null) {
         throw new NullPointerException("clazz");
      } else if (level == null) {
         throw new NullPointerException("level");
      } else {
         this.logger = InternalLoggerFactory.getInstance(clazz);
         this.level = level;
         this.internalLevel = level.toInternalLevel();
      }
   }

   public LoggingHandler(String name) {
      this(name, DEFAULT_LEVEL);
   }

   public LoggingHandler(String name, LogLevel level) {
      if (name == null) {
         throw new NullPointerException("name");
      } else if (level == null) {
         throw new NullPointerException("level");
      } else {
         this.logger = InternalLoggerFactory.getInstance(name);
         this.level = level;
         this.internalLevel = level.toInternalLevel();
      }
   }

   public LogLevel level() {
      return this.level;
   }

   public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "REGISTERED"));
      }

      ctx.fireChannelRegistered();
   }

   public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "UNREGISTERED"));
      }

      ctx.fireChannelUnregistered();
   }

   public void channelActive(ChannelHandlerContext ctx) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "ACTIVE"));
      }

      ctx.fireChannelActive();
   }

   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "INACTIVE"));
      }

      ctx.fireChannelInactive();
   }

   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "EXCEPTION", cause), cause);
      }

      ctx.fireExceptionCaught(cause);
   }

   public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "USER_EVENT", evt));
      }

      ctx.fireUserEventTriggered(evt);
   }

   public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "BIND", localAddress));
      }

      ctx.bind(localAddress, promise);
   }

   public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "CONNECT", remoteAddress, localAddress));
      }

      ctx.connect(remoteAddress, localAddress, promise);
   }

   public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "DISCONNECT"));
      }

      ctx.disconnect(promise);
   }

   public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "CLOSE"));
      }

      ctx.close(promise);
   }

   public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "DEREGISTER"));
      }

      ctx.deregister(promise);
   }

   public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "READ COMPLETE"));
      }

      ctx.fireChannelReadComplete();
   }

   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "READ", msg));
      }

      ctx.fireChannelRead(msg);
   }

   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "WRITE", msg));
      }

      ctx.write(msg, promise);
   }

   public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "WRITABILITY CHANGED"));
      }

      ctx.fireChannelWritabilityChanged();
   }

   public void flush(ChannelHandlerContext ctx) throws Exception {
      if (this.logger.isEnabled(this.internalLevel)) {
         this.logger.log(this.internalLevel, this.format(ctx, "FLUSH"));
      }

      ctx.flush();
   }

   protected String format(ChannelHandlerContext ctx, String eventName) {
      String chStr = ctx.channel().toString();
      return (new StringBuilder(chStr.length() + 1 + eventName.length())).append(chStr).append(' ').append(eventName).toString();
   }

   protected String format(ChannelHandlerContext ctx, String eventName, Object arg) {
      if (arg instanceof ByteBuf) {
         return formatByteBuf(ctx, eventName, (ByteBuf)arg);
      } else {
         return arg instanceof ByteBufHolder ? formatByteBufHolder(ctx, eventName, (ByteBufHolder)arg) : formatSimple(ctx, eventName, arg);
      }
   }

   protected String format(ChannelHandlerContext ctx, String eventName, Object firstArg, Object secondArg) {
      if (secondArg == null) {
         return formatSimple(ctx, eventName, firstArg);
      } else {
         String chStr = ctx.channel().toString();
         String arg1Str = String.valueOf(firstArg);
         String arg2Str = secondArg.toString();
         StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName + 2 + arg1Str.length() + 2 + arg2Str.length());
         buf.append(chStr).append(' ').append(eventName).append(": ").append(arg1Str).append(", ").append(arg2Str);
         return buf.toString();
      }
   }

   private static String formatByteBuf(ChannelHandlerContext ctx, String eventName, ByteBuf msg) {
      String chStr = ctx.channel().toString();
      int length = msg.readableBytes();
      if (length == 0) {
         StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 4);
         buf.append(chStr).append(' ').append(eventName).append(": 0B");
         return buf.toString();
      } else {
         int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
         StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + 10 + 1 + 2 + rows * 80);
         buf.append(chStr).append(' ').append(eventName).append(": ").append(length).append('B').append(StringUtil.NEWLINE);
         ByteBufUtil.appendPrettyHexDump(buf, msg);
         return buf.toString();
      }
   }

   private static String formatByteBufHolder(ChannelHandlerContext ctx, String eventName, ByteBufHolder msg) {
      String chStr = ctx.channel().toString();
      String msgStr = msg.toString();
      ByteBuf content = msg.content();
      int length = content.readableBytes();
      if (length == 0) {
         StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + msgStr.length() + 4);
         buf.append(chStr).append(' ').append(eventName).append(", ").append(msgStr).append(", 0B");
         return buf.toString();
      } else {
         int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
         StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + msgStr.length() + 2 + 10 + 1 + 2 + rows * 80);
         buf.append(chStr).append(' ').append(eventName).append(": ").append(msgStr).append(", ").append(length).append('B').append(StringUtil.NEWLINE);
         ByteBufUtil.appendPrettyHexDump(buf, content);
         return buf.toString();
      }
   }

   private static String formatSimple(ChannelHandlerContext ctx, String eventName, Object msg) {
      String chStr = ctx.channel().toString();
      String msgStr = String.valueOf(msg);
      StringBuilder buf = new StringBuilder(chStr.length() + 1 + eventName.length() + 2 + msgStr.length());
      return buf.append(chStr).append(' ').append(eventName).append(": ").append(msgStr).toString();
   }

   static {
      DEFAULT_LEVEL = LogLevel.DEBUG;
   }
}
