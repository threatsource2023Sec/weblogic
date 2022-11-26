package org.jboss.weld.injection.producer;

import java.util.Iterator;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.Producer;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.util.reflection.Formats;

public abstract class AbstractProducer implements Producer {
   protected void checkDelegateInjectionPoints() {
      Iterator var1 = this.getInjectionPoints().iterator();

      InjectionPoint injectionPoint;
      do {
         if (!var1.hasNext()) {
            return;
         }

         injectionPoint = (InjectionPoint)var1.next();
      } while(!injectionPoint.isDelegate());

      throw BeanLogger.LOG.delegateNotOnDecorator(injectionPoint, Formats.formatAsStackTraceElement(injectionPoint));
   }

   public abstract Annotated getAnnotated();

   public abstract Bean getBean();

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.getAnnotated() == null ? 0 : this.getAnnotated().hashCode());
      result = 31 * result + (this.getBean() == null ? 0 : this.getBean().hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         AbstractProducer other = (AbstractProducer)obj;
         if (this.getAnnotated() == null) {
            if (other.getAnnotated() != null) {
               return false;
            }
         } else if (!this.getAnnotated().equals(other.getAnnotated())) {
            return false;
         }

         if (this.getBean() == null) {
            if (other.getBean() != null) {
               return false;
            }
         } else if (!this.getBean().equals(other.getBean())) {
            return false;
         }

         return true;
      }
   }
}
