package com.bea.core.repackaged.springframework.expression.spel;

import com.bea.core.repackaged.springframework.core.SpringProperties;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class SpelParserConfiguration {
   private static final SpelCompilerMode defaultCompilerMode;
   private final SpelCompilerMode compilerMode;
   @Nullable
   private final ClassLoader compilerClassLoader;
   private final boolean autoGrowNullReferences;
   private final boolean autoGrowCollections;
   private final int maximumAutoGrowSize;

   public SpelParserConfiguration() {
      this((SpelCompilerMode)null, (ClassLoader)null, false, false, Integer.MAX_VALUE);
   }

   public SpelParserConfiguration(@Nullable SpelCompilerMode compilerMode, @Nullable ClassLoader compilerClassLoader) {
      this(compilerMode, compilerClassLoader, false, false, Integer.MAX_VALUE);
   }

   public SpelParserConfiguration(boolean autoGrowNullReferences, boolean autoGrowCollections) {
      this((SpelCompilerMode)null, (ClassLoader)null, autoGrowNullReferences, autoGrowCollections, Integer.MAX_VALUE);
   }

   public SpelParserConfiguration(boolean autoGrowNullReferences, boolean autoGrowCollections, int maximumAutoGrowSize) {
      this((SpelCompilerMode)null, (ClassLoader)null, autoGrowNullReferences, autoGrowCollections, maximumAutoGrowSize);
   }

   public SpelParserConfiguration(@Nullable SpelCompilerMode compilerMode, @Nullable ClassLoader compilerClassLoader, boolean autoGrowNullReferences, boolean autoGrowCollections, int maximumAutoGrowSize) {
      this.compilerMode = compilerMode != null ? compilerMode : defaultCompilerMode;
      this.compilerClassLoader = compilerClassLoader;
      this.autoGrowNullReferences = autoGrowNullReferences;
      this.autoGrowCollections = autoGrowCollections;
      this.maximumAutoGrowSize = maximumAutoGrowSize;
   }

   public SpelCompilerMode getCompilerMode() {
      return this.compilerMode;
   }

   @Nullable
   public ClassLoader getCompilerClassLoader() {
      return this.compilerClassLoader;
   }

   public boolean isAutoGrowNullReferences() {
      return this.autoGrowNullReferences;
   }

   public boolean isAutoGrowCollections() {
      return this.autoGrowCollections;
   }

   public int getMaximumAutoGrowSize() {
      return this.maximumAutoGrowSize;
   }

   static {
      String compilerMode = SpringProperties.getProperty("spring.expression.compiler.mode");
      defaultCompilerMode = compilerMode != null ? SpelCompilerMode.valueOf(compilerMode.toUpperCase()) : SpelCompilerMode.OFF;
   }
}
