package org.jboss.weld.resolution;

import java.util.Set;
import org.jboss.weld.bean.DisposalMethod;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.util.Beans;

public class TypeSafeDisposerResolver extends TypeSafeResolver {
   private final AssignabilityRules rules = BeanTypeAssignabilityRules.instance();

   public TypeSafeDisposerResolver(Iterable disposers, WeldConfiguration configuration) {
      super(disposers, configuration);
   }

   protected boolean matches(Resolvable resolvable, DisposalMethod disposer) {
      return resolvable.getDeclaringBean().equals(disposer.getDeclaringBean()) && this.rules.matches(disposer.getGenericType(), resolvable.getTypes()) && Beans.containsAllQualifiers(disposer.getRequiredQualifiers(), resolvable.getQualifiers());
   }

   protected Resolvable wrap(final Resolvable resolvable) {
      return new ForwardingResolvable() {
         protected Resolvable delegate() {
            return resolvable;
         }

         public boolean equals(Object o) {
            if (o instanceof Resolvable && super.equals(o)) {
               Resolvable r = (Resolvable)o;
               return r.getDeclaringBean().equals(this.getDeclaringBean());
            } else {
               return false;
            }
         }

         public int hashCode() {
            return 31 * super.hashCode() + this.getDeclaringBean().hashCode();
         }
      };
   }

   protected Set filterResult(Set matched) {
      return matched;
   }

   protected Set sortResult(Set matched) {
      return matched;
   }
}
