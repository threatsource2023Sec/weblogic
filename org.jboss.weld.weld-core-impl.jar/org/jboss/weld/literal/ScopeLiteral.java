package org.jboss.weld.literal;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Scope;

public class ScopeLiteral extends AnnotationLiteral implements Scope {
   private static final long serialVersionUID = -653676020289890924L;
   public static final Scope INSTANCE = new ScopeLiteral();

   private ScopeLiteral() {
   }
}
