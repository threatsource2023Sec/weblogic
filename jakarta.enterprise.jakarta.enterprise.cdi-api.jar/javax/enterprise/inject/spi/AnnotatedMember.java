package javax.enterprise.inject.spi;

import java.lang.reflect.Member;

public interface AnnotatedMember extends Annotated {
   Member getJavaMember();

   boolean isStatic();

   AnnotatedType getDeclaringType();
}
