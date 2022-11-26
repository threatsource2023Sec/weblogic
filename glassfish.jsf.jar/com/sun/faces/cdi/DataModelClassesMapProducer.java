package com.sun.faces.cdi;

import java.lang.annotation.Annotation;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;

public class DataModelClassesMapProducer extends CdiProducer {
   private static final long serialVersionUID = 1L;

   public DataModelClassesMapProducer() {
      super.name("comSunFacesDataModelClassesMap").scope(ApplicationScoped.class).qualifiers(new DataModelClassesAnnotationLiteral()).types(Map.class, Object.class).beanClass(Map.class).create((e) -> {
         return ((CdiExtension)CDI.current().select(CdiExtension.class, new Annotation[0]).get()).getForClassToDataModelClass();
      });
   }
}
