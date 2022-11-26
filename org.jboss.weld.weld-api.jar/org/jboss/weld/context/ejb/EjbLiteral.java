package org.jboss.weld.context.ejb;

import javax.enterprise.util.AnnotationLiteral;

public class EjbLiteral extends AnnotationLiteral implements Ejb {
   public static final Ejb INSTANCE = new EjbLiteral();

   private EjbLiteral() {
   }
}
