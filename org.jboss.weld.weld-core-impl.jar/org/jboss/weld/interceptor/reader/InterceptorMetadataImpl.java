package org.jboss.weld.interceptor.reader;

import java.util.Map;
import org.jboss.weld.interceptor.spi.metadata.InterceptorClassMetadata;
import org.jboss.weld.interceptor.spi.metadata.InterceptorFactory;

public class InterceptorMetadataImpl extends AbstractInterceptorMetadata implements InterceptorClassMetadata {
   private final InterceptorFactory reference;
   private final Class javaClass;

   public InterceptorMetadataImpl(Class javaClass, InterceptorFactory reference, Map interceptorMethodMap) {
      super(interceptorMethodMap);
      this.reference = reference;
      this.javaClass = javaClass;
   }

   public InterceptorFactory getInterceptorFactory() {
      return this.reference;
   }

   protected boolean isTargetClassInterceptor() {
      return false;
   }

   public Class getJavaClass() {
      return this.javaClass;
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.javaClass == null ? 0 : this.javaClass.hashCode());
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
         InterceptorMetadataImpl other = (InterceptorMetadataImpl)obj;
         if (this.javaClass == null) {
            if (other.javaClass != null) {
               return false;
            }
         } else if (!this.javaClass.equals(other.javaClass)) {
            return false;
         }

         return true;
      }
   }

   public String toString() {
      return "InterceptorMetadataImpl [javaClass=" + this.javaClass + "]";
   }
}
