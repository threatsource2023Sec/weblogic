package org.jboss.weld.bootstrap.api;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface ServiceRegistry extends Iterable {
   void add(Class var1, Service var2);

   Service addIfAbsent(Class var1, Service var2);

   void addAll(Collection var1);

   Set entrySet();

   Service get(Class var1);

   Optional getOptional(Class var1);

   Service getRequired(Class var1);

   boolean contains(Class var1);

   void cleanup();

   void cleanupAfterBoot();
}
