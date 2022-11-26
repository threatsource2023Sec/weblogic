package com.bea.core.repackaged.springframework.instrument.classloading;

import com.bea.core.repackaged.springframework.instrument.InstrumentationSavingAgent;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.List;

public class InstrumentationLoadTimeWeaver implements LoadTimeWeaver {
   private static final boolean AGENT_CLASS_PRESENT = ClassUtils.isPresent("com.bea.core.repackaged.springframework.instrument.InstrumentationSavingAgent", InstrumentationLoadTimeWeaver.class.getClassLoader());
   @Nullable
   private final ClassLoader classLoader;
   @Nullable
   private final Instrumentation instrumentation;
   private final List transformers;

   public InstrumentationLoadTimeWeaver() {
      this(ClassUtils.getDefaultClassLoader());
   }

   public InstrumentationLoadTimeWeaver(@Nullable ClassLoader classLoader) {
      this.transformers = new ArrayList(4);
      this.classLoader = classLoader;
      this.instrumentation = getInstrumentation();
   }

   public void addTransformer(ClassFileTransformer transformer) {
      Assert.notNull(transformer, (String)"Transformer must not be null");
      FilteringClassFileTransformer actualTransformer = new FilteringClassFileTransformer(transformer, this.classLoader);
      synchronized(this.transformers) {
         Assert.state(this.instrumentation != null, "Must start with Java agent to use InstrumentationLoadTimeWeaver. See Spring documentation.");
         this.instrumentation.addTransformer(actualTransformer);
         this.transformers.add(actualTransformer);
      }
   }

   public ClassLoader getInstrumentableClassLoader() {
      Assert.state(this.classLoader != null, "No ClassLoader available");
      return this.classLoader;
   }

   public ClassLoader getThrowawayClassLoader() {
      return new SimpleThrowawayClassLoader(this.getInstrumentableClassLoader());
   }

   public void removeTransformers() {
      synchronized(this.transformers) {
         if (this.instrumentation != null && !this.transformers.isEmpty()) {
            for(int i = this.transformers.size() - 1; i >= 0; --i) {
               this.instrumentation.removeTransformer((ClassFileTransformer)this.transformers.get(i));
            }

            this.transformers.clear();
         }

      }
   }

   public static boolean isInstrumentationAvailable() {
      return getInstrumentation() != null;
   }

   @Nullable
   private static Instrumentation getInstrumentation() {
      return AGENT_CLASS_PRESENT ? InstrumentationLoadTimeWeaver.InstrumentationAccessor.getInstrumentation() : null;
   }

   private static class FilteringClassFileTransformer implements ClassFileTransformer {
      private final ClassFileTransformer targetTransformer;
      @Nullable
      private final ClassLoader targetClassLoader;

      public FilteringClassFileTransformer(ClassFileTransformer targetTransformer, @Nullable ClassLoader targetClassLoader) {
         this.targetTransformer = targetTransformer;
         this.targetClassLoader = targetClassLoader;
      }

      @Nullable
      public byte[] transform(ClassLoader loader, String className, Class classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
         return this.targetClassLoader != loader ? null : this.targetTransformer.transform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
      }

      public String toString() {
         return "FilteringClassFileTransformer for: " + this.targetTransformer.toString();
      }
   }

   private static class InstrumentationAccessor {
      public static Instrumentation getInstrumentation() {
         return InstrumentationSavingAgent.getInstrumentation();
      }
   }
}
