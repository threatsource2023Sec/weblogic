package com.sun.faces.application.annotation;

import java.lang.annotation.Annotation;
import java.util.Collection;
import javax.faces.context.FacesContext;

public interface ConfigAnnotationHandler {
   Collection getHandledAnnotations();

   void collect(Class var1, Annotation var2);

   void push(FacesContext var1);
}
