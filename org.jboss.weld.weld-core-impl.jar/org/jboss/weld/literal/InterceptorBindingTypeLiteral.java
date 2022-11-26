package org.jboss.weld.literal;

import javax.enterprise.util.AnnotationLiteral;
import javax.interceptor.InterceptorBinding;

public class InterceptorBindingTypeLiteral extends AnnotationLiteral implements InterceptorBinding {
   private static final long serialVersionUID = 978485112467708038L;
   public static final InterceptorBinding INSTANCE = new InterceptorBindingTypeLiteral();

   private InterceptorBindingTypeLiteral() {
   }
}
