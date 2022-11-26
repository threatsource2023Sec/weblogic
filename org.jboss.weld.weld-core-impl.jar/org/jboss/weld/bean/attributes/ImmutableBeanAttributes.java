package org.jboss.weld.bean.attributes;

import java.util.Set;
import javax.enterprise.inject.spi.BeanAttributes;
import org.jboss.weld.util.reflection.Formats;

public class ImmutableBeanAttributes implements BeanAttributes {
   private final Set stereotypes;
   private final boolean alternative;
   private final String name;
   private final Set qualifiers;
   private final Set types;
   private final Class scope;

   public ImmutableBeanAttributes(Set stereotypes, boolean alternative, String name, Set qualifiers, Set types, Class scope) {
      this.stereotypes = stereotypes;
      this.alternative = alternative;
      this.name = name;
      this.qualifiers = qualifiers;
      this.types = types;
      this.scope = scope;
   }

   public ImmutableBeanAttributes(Set qualifiers, String name, BeanAttributes attributes) {
      this(attributes.getStereotypes(), attributes.isAlternative(), name, qualifiers, attributes.getTypes(), attributes.getScope());
   }

   public Set getStereotypes() {
      return this.stereotypes;
   }

   public boolean isAlternative() {
      return this.alternative;
   }

   public String getName() {
      return this.name;
   }

   public Set getQualifiers() {
      return this.qualifiers;
   }

   public Set getTypes() {
      return this.types;
   }

   public Class getScope() {
      return this.scope;
   }

   public String toString() {
      return "BeanAttributes with types [" + Formats.formatTypes(this.types) + "] and qualifiers [" + Formats.formatAnnotations((Iterable)this.getQualifiers()) + "]";
   }
}
