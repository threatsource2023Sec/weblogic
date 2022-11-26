package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.annotation.ElementType;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.engine.valueextraction.ValueExtractorManager;
import org.hibernate.validator.internal.metadata.facets.Cascadable;
import org.hibernate.validator.internal.util.ReflectionHelper;

public class GetterCascadable implements Cascadable {
   private final Method method;
   private final String propertyName;
   private final Type cascadableType;
   private final CascadingMetaData cascadingMetaData;

   GetterCascadable(Method method, CascadingMetaData cascadingMetaData) {
      this.method = method;
      this.propertyName = ReflectionHelper.getPropertyName(method);
      this.cascadableType = ReflectionHelper.typeOf(method);
      this.cascadingMetaData = cascadingMetaData;
   }

   public ElementType getElementType() {
      return ElementType.METHOD;
   }

   public Type getCascadableType() {
      return this.cascadableType;
   }

   public Object getValue(Object parent) {
      return ReflectionHelper.getValue(this.method, parent);
   }

   public void appendTo(PathImpl path) {
      path.addPropertyNode(this.propertyName);
   }

   public CascadingMetaData getCascadingMetaData() {
      return this.cascadingMetaData;
   }

   public static class Builder implements Cascadable.Builder {
      private final ValueExtractorManager valueExtractorManager;
      private final Method method;
      private CascadingMetaDataBuilder cascadingMetaDataBuilder;

      public Builder(ValueExtractorManager valueExtractorManager, Method method, CascadingMetaDataBuilder cascadingMetaDataBuilder) {
         this.valueExtractorManager = valueExtractorManager;
         this.method = method;
         this.cascadingMetaDataBuilder = cascadingMetaDataBuilder;
      }

      public void mergeCascadingMetaData(CascadingMetaDataBuilder cascadingMetaData) {
         this.cascadingMetaDataBuilder = this.cascadingMetaDataBuilder.merge(cascadingMetaData);
      }

      public GetterCascadable build() {
         return new GetterCascadable(this.method, this.cascadingMetaDataBuilder.build(this.valueExtractorManager, this.method));
      }
   }
}
