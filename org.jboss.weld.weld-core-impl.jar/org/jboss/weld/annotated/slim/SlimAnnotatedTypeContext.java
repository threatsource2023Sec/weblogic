package org.jboss.weld.annotated.slim;

import java.util.Set;
import javax.enterprise.inject.spi.Extension;
import org.jboss.weld.resources.spi.ClassFileInfo;

public class SlimAnnotatedTypeContext {
   private final SlimAnnotatedType type;
   private final ClassFileInfo classInfo;
   private final Set resolvedProcessAnnotatedTypeObservers;
   private final Extension extension;

   public static SlimAnnotatedTypeContext of(SlimAnnotatedType type, ClassFileInfo classInfo, Set resolvedProcessAnnotatedTypeObservers) {
      return new SlimAnnotatedTypeContext(type, classInfo, resolvedProcessAnnotatedTypeObservers, (Extension)null);
   }

   public static SlimAnnotatedTypeContext of(SlimAnnotatedType type) {
      return new SlimAnnotatedTypeContext(type, (ClassFileInfo)null, (Set)null, (Extension)null);
   }

   public static SlimAnnotatedTypeContext of(SlimAnnotatedType type, Extension extension) {
      return new SlimAnnotatedTypeContext(type, (ClassFileInfo)null, (Set)null, extension);
   }

   private SlimAnnotatedTypeContext(SlimAnnotatedType type, ClassFileInfo classInfo, Set resolvedProcessAnnotatedTypeObservers, Extension extension) {
      this.type = type;
      this.classInfo = classInfo;
      this.resolvedProcessAnnotatedTypeObservers = resolvedProcessAnnotatedTypeObservers;
      this.extension = extension;
   }

   public SlimAnnotatedType getAnnotatedType() {
      return this.type;
   }

   public ClassFileInfo getClassInfo() {
      return this.classInfo;
   }

   public Set getResolvedProcessAnnotatedTypeObservers() {
      return this.resolvedProcessAnnotatedTypeObservers;
   }

   public Extension getExtension() {
      return this.extension;
   }

   public int hashCode() {
      return this.type.hashCode();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else {
         return obj instanceof SlimAnnotatedTypeContext ? this.type.equals(((SlimAnnotatedTypeContext)SlimAnnotatedTypeContext.class.cast(obj)).type) : false;
      }
   }

   public String toString() {
      return this.getClass().getSimpleName() + " for " + this.type.toString();
   }
}
