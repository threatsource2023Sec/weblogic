package org.hibernate.validator.internal.metadata.facets;

import java.lang.annotation.ElementType;
import java.lang.reflect.Type;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaData;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;

public interface Cascadable {
   ElementType getElementType();

   Type getCascadableType();

   Object getValue(Object var1);

   void appendTo(PathImpl var1);

   CascadingMetaData getCascadingMetaData();

   public interface Builder {
      void mergeCascadingMetaData(CascadingMetaDataBuilder var1);

      Cascadable build();
   }
}
