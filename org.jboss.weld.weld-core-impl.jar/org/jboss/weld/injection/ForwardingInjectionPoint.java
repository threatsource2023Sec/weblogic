package org.jboss.weld.injection;

import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.Set;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.util.reflection.Reflections;

public abstract class ForwardingInjectionPoint implements InjectionPoint {
   protected abstract InjectionPoint delegate();

   public Annotated getAnnotated() {
      return this.delegate().getAnnotated();
   }

   public Type getType() {
      return this.delegate().getType();
   }

   public Set getQualifiers() {
      return this.delegate().getQualifiers();
   }

   public Bean getBean() {
      return this.delegate().getBean();
   }

   public Member getMember() {
      return this.delegate().getMember();
   }

   public boolean isDelegate() {
      return this.delegate().isDelegate();
   }

   public boolean isTransient() {
      return this.delegate().isTransient();
   }

   public boolean equals(Object obj) {
      return obj instanceof ForwardingInjectionPoint ? this.delegate().equals(((ForwardingInjectionPoint)Reflections.cast(obj)).delegate()) : this.delegate().equals(obj);
   }

   public int hashCode() {
      return this.delegate().hashCode();
   }

   public String toString() {
      return this.delegate().toString();
   }
}
