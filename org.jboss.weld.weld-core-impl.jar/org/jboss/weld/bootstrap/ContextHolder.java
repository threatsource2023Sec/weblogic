package org.jboss.weld.bootstrap;

import java.util.Set;
import javax.enterprise.context.spi.Context;

public class ContextHolder {
   private final Context context;
   private final Class type;
   private final Set qualifiers;

   public ContextHolder(Context context, Class type, Set qualifiers) {
      this.context = context;
      this.type = type;
      this.qualifiers = qualifiers;
   }

   public Context getContext() {
      return this.context;
   }

   public Class getType() {
      return this.type;
   }

   public Set getQualifiers() {
      return this.qualifiers;
   }
}
