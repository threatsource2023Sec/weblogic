package org.jboss.weld.context.http;

import javax.enterprise.util.AnnotationLiteral;

public class HttpLiteral extends AnnotationLiteral implements Http {
   public static final Http INSTANCE = new HttpLiteral();

   private HttpLiteral() {
   }
}
