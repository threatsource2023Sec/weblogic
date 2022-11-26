package org.hibernate.validator.cfg;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.annotation.AnnotationDescriptor;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public abstract class AnnotationDef {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final AnnotationDescriptor.Builder annotationDescriptorBuilder;
   private final Map annotationsAsParameters;
   private final Map annotationsAsParametersTypes;

   protected AnnotationDef(Class annotationType) {
      this.annotationDescriptorBuilder = new AnnotationDescriptor.Builder(annotationType);
      this.annotationsAsParameters = new HashMap();
      this.annotationsAsParametersTypes = new HashMap();
   }

   protected AnnotationDef(AnnotationDef original) {
      this.annotationDescriptorBuilder = original.annotationDescriptorBuilder;
      this.annotationsAsParameters = original.annotationsAsParameters;
      this.annotationsAsParametersTypes = original.annotationsAsParametersTypes;
   }

   private AnnotationDef getThis() {
      return this;
   }

   protected AnnotationDef addParameter(String key, Object value) {
      this.annotationDescriptorBuilder.setAttribute(key, value);
      return this.getThis();
   }

   protected AnnotationDef addAnnotationAsParameter(String key, AnnotationDef value) {
      this.annotationsAsParameters.compute(key, (k, oldValue) -> {
         if (oldValue == null) {
            return Collections.singletonList(value);
         } else {
            List resultingList = CollectionHelper.newArrayList(oldValue);
            resultingList.add(value);
            return resultingList;
         }
      });
      this.annotationsAsParametersTypes.putIfAbsent(key, value.annotationDescriptorBuilder.getType());
      return this.getThis();
   }

   private AnnotationDescriptor createAnnotationDescriptor() {
      Iterator var1 = this.annotationsAsParameters.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry annotationAsParameter = (Map.Entry)var1.next();
         this.annotationDescriptorBuilder.setAttribute((String)annotationAsParameter.getKey(), this.toAnnotationParameterArray((List)annotationAsParameter.getValue(), (Class)this.annotationsAsParametersTypes.get(annotationAsParameter.getKey())));
      }

      try {
         return this.annotationDescriptorBuilder.build();
      } catch (RuntimeException var3) {
         throw LOG.getUnableToCreateAnnotationForConfiguredConstraintException(var3);
      }
   }

   private Annotation createAnnotationProxy() {
      return this.createAnnotationDescriptor().getAnnotation();
   }

   private Object[] toAnnotationParameterArray(List list, Class aClass) {
      return list.stream().map(AnnotationDef::createAnnotationProxy).toArray((n) -> {
         return (Object[])((Object[])Array.newInstance(aClass, n));
      });
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.getClass().getSimpleName());
      sb.append('{');
      sb.append(this.annotationDescriptorBuilder);
      sb.append('}');
      return sb.toString();
   }
}
