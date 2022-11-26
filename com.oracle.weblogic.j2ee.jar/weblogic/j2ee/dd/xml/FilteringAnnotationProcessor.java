package weblogic.j2ee.dd.xml;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Set;

public abstract class FilteringAnnotationProcessor implements AnnotationProcessor {
   private final Delegate delegate;

   public FilteringAnnotationProcessor(Set supportedAnnotations) {
      if (supportedAnnotations != null && !supportedAnnotations.isEmpty()) {
         this.delegate = new FilteredDelegate(supportedAnnotations);
      } else {
         this.delegate = new UnfilteredDelegate();
      }

   }

   public boolean isAnnotationPresent(AnnotatedElement e, Class a) {
      return this.delegate.isAnnotationPresent(e, a);
   }

   public Annotation getAnnotation(AnnotatedElement e, Class a) {
      return this.delegate.getAnnotation(e, a);
   }

   private static class FilteredDelegate implements Delegate {
      private final Set supportedAnnotations;

      private FilteredDelegate(Set supportedAnnotations) {
         this.supportedAnnotations = supportedAnnotations;
      }

      public boolean isAnnotationPresent(AnnotatedElement e, Class a) {
         return this.supportedAnnotations.contains(a) && e.isAnnotationPresent(a);
      }

      public Annotation getAnnotation(AnnotatedElement e, Class a) {
         return this.supportedAnnotations.contains(a) ? e.getAnnotation(a) : null;
      }

      // $FF: synthetic method
      FilteredDelegate(Set x0, Object x1) {
         this(x0);
      }
   }

   private static class UnfilteredDelegate implements Delegate {
      private UnfilteredDelegate() {
      }

      public boolean isAnnotationPresent(AnnotatedElement e, Class a) {
         return e.isAnnotationPresent(a);
      }

      public Annotation getAnnotation(AnnotatedElement e, Class a) {
         return e.getAnnotation(a);
      }

      // $FF: synthetic method
      UnfilteredDelegate(Object x0) {
         this();
      }
   }

   private interface Delegate {
      boolean isAnnotationPresent(AnnotatedElement var1, Class var2);

      Annotation getAnnotation(AnnotatedElement var1, Class var2);
   }
}
