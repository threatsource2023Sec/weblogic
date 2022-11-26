package org.jboss.weld.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.util.TypeLiteral;

public interface WeldInstance extends Instance {
   Handler getHandler();

   Iterable handlers();

   default Stream handlersStream() {
      return StreamSupport.stream(this.handlers().spliterator(), false);
   }

   Comparator getPriorityComparator();

   WeldInstance select(Annotation... var1);

   WeldInstance select(Class var1, Annotation... var2);

   WeldInstance select(TypeLiteral var1, Annotation... var2);

   WeldInstance select(Type var1, Annotation... var2);

   public interface Handler extends AutoCloseable {
      Object get();

      Bean getBean();

      void destroy();

      void close();
   }
}
