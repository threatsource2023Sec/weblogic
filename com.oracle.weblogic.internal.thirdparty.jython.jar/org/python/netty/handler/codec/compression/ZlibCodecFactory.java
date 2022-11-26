package org.python.netty.handler.codec.compression;

import org.python.netty.util.internal.PlatformDependent;
import org.python.netty.util.internal.SystemPropertyUtil;
import org.python.netty.util.internal.logging.InternalLogger;
import org.python.netty.util.internal.logging.InternalLoggerFactory;

public final class ZlibCodecFactory {
   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ZlibCodecFactory.class);
   private static final int DEFAULT_JDK_WINDOW_SIZE = 15;
   private static final int DEFAULT_JDK_MEM_LEVEL = 8;
   private static final boolean noJdkZlibDecoder = SystemPropertyUtil.getBoolean("org.python.netty.noJdkZlibDecoder", PlatformDependent.javaVersion() < 7);
   private static final boolean noJdkZlibEncoder;
   private static final boolean supportsWindowSizeAndMemLevel;

   public static boolean isSupportingWindowSizeAndMemLevel() {
      return supportsWindowSizeAndMemLevel;
   }

   public static ZlibEncoder newZlibEncoder(int compressionLevel) {
      return (ZlibEncoder)(PlatformDependent.javaVersion() >= 7 && !noJdkZlibEncoder ? new JdkZlibEncoder(compressionLevel) : new JZlibEncoder(compressionLevel));
   }

   public static ZlibEncoder newZlibEncoder(ZlibWrapper wrapper) {
      return (ZlibEncoder)(PlatformDependent.javaVersion() >= 7 && !noJdkZlibEncoder ? new JdkZlibEncoder(wrapper) : new JZlibEncoder(wrapper));
   }

   public static ZlibEncoder newZlibEncoder(ZlibWrapper wrapper, int compressionLevel) {
      return (ZlibEncoder)(PlatformDependent.javaVersion() >= 7 && !noJdkZlibEncoder ? new JdkZlibEncoder(wrapper, compressionLevel) : new JZlibEncoder(wrapper, compressionLevel));
   }

   public static ZlibEncoder newZlibEncoder(ZlibWrapper wrapper, int compressionLevel, int windowBits, int memLevel) {
      return (ZlibEncoder)(PlatformDependent.javaVersion() >= 7 && !noJdkZlibEncoder && windowBits == 15 && memLevel == 8 ? new JdkZlibEncoder(wrapper, compressionLevel) : new JZlibEncoder(wrapper, compressionLevel, windowBits, memLevel));
   }

   public static ZlibEncoder newZlibEncoder(byte[] dictionary) {
      return (ZlibEncoder)(PlatformDependent.javaVersion() >= 7 && !noJdkZlibEncoder ? new JdkZlibEncoder(dictionary) : new JZlibEncoder(dictionary));
   }

   public static ZlibEncoder newZlibEncoder(int compressionLevel, byte[] dictionary) {
      return (ZlibEncoder)(PlatformDependent.javaVersion() >= 7 && !noJdkZlibEncoder ? new JdkZlibEncoder(compressionLevel, dictionary) : new JZlibEncoder(compressionLevel, dictionary));
   }

   public static ZlibEncoder newZlibEncoder(int compressionLevel, int windowBits, int memLevel, byte[] dictionary) {
      return (ZlibEncoder)(PlatformDependent.javaVersion() >= 7 && !noJdkZlibEncoder && windowBits == 15 && memLevel == 8 ? new JdkZlibEncoder(compressionLevel, dictionary) : new JZlibEncoder(compressionLevel, windowBits, memLevel, dictionary));
   }

   public static ZlibDecoder newZlibDecoder() {
      return (ZlibDecoder)(PlatformDependent.javaVersion() >= 7 && !noJdkZlibDecoder ? new JdkZlibDecoder() : new JZlibDecoder());
   }

   public static ZlibDecoder newZlibDecoder(ZlibWrapper wrapper) {
      return (ZlibDecoder)(PlatformDependent.javaVersion() >= 7 && !noJdkZlibDecoder ? new JdkZlibDecoder(wrapper) : new JZlibDecoder(wrapper));
   }

   public static ZlibDecoder newZlibDecoder(byte[] dictionary) {
      return (ZlibDecoder)(PlatformDependent.javaVersion() >= 7 && !noJdkZlibDecoder ? new JdkZlibDecoder(dictionary) : new JZlibDecoder(dictionary));
   }

   private ZlibCodecFactory() {
   }

   static {
      logger.debug("-Dio.netty.noJdkZlibDecoder: {}", (Object)noJdkZlibDecoder);
      noJdkZlibEncoder = SystemPropertyUtil.getBoolean("org.python.netty.noJdkZlibEncoder", false);
      logger.debug("-Dio.netty.noJdkZlibEncoder: {}", (Object)noJdkZlibEncoder);
      supportsWindowSizeAndMemLevel = noJdkZlibDecoder || PlatformDependent.javaVersion() >= 7;
   }
}
