package com.sun.faces.application.annotation;

interface Scanner {
   Class getAnnotation();

   RuntimeAnnotationHandler scan(Class var1);
}
