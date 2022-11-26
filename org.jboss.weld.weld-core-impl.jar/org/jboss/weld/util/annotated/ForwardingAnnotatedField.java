package org.jboss.weld.util.annotated;

import java.lang.reflect.Field;
import javax.enterprise.inject.spi.AnnotatedField;

public abstract class ForwardingAnnotatedField extends ForwardingAnnotatedMember implements AnnotatedField {
   protected abstract AnnotatedField delegate();

   public Field getJavaMember() {
      return this.delegate().getJavaMember();
   }
}
