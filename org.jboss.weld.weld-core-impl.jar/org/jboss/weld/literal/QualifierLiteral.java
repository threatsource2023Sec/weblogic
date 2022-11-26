package org.jboss.weld.literal;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;

public class QualifierLiteral extends AnnotationLiteral implements Qualifier {
   private static final long serialVersionUID = -1865461877555038671L;
   public static final Qualifier INSTANCE = new QualifierLiteral();

   private QualifierLiteral() {
   }
}
