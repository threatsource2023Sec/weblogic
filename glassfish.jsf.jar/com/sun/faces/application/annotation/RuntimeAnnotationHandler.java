package com.sun.faces.application.annotation;

import javax.faces.context.FacesContext;

interface RuntimeAnnotationHandler {
   void apply(FacesContext var1, Object... var2);
}
