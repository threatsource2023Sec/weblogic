package com.bea.core.repackaged.springframework.core.type.filter;

import com.bea.core.repackaged.aspectj.bridge.IMessageHandler;
import com.bea.core.repackaged.aspectj.weaver.ICrossReferenceHandler;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.bcel.BcelWorld;
import com.bea.core.repackaged.aspectj.weaver.patterns.Bindings;
import com.bea.core.repackaged.aspectj.weaver.patterns.FormalBinding;
import com.bea.core.repackaged.aspectj.weaver.patterns.IScope;
import com.bea.core.repackaged.aspectj.weaver.patterns.PatternParser;
import com.bea.core.repackaged.aspectj.weaver.patterns.SimpleScope;
import com.bea.core.repackaged.aspectj.weaver.patterns.TypePattern;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReader;
import com.bea.core.repackaged.springframework.core.type.classreading.MetadataReaderFactory;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.io.IOException;

public class AspectJTypeFilter implements TypeFilter {
   private final World world;
   private final TypePattern typePattern;

   public AspectJTypeFilter(String typePatternExpression, @Nullable ClassLoader classLoader) {
      this.world = new BcelWorld(classLoader, IMessageHandler.THROW, (ICrossReferenceHandler)null);
      this.world.setBehaveInJava5Way(true);
      PatternParser patternParser = new PatternParser(typePatternExpression);
      TypePattern typePattern = patternParser.parseTypePattern();
      typePattern.resolve(this.world);
      IScope scope = new SimpleScope(this.world, new FormalBinding[0]);
      this.typePattern = typePattern.resolveBindings(scope, Bindings.NONE, false, false);
   }

   public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
      String className = metadataReader.getClassMetadata().getClassName();
      ResolvedType resolvedType = this.world.resolve(className);
      return this.typePattern.matchesStatically(resolvedType);
   }
}
