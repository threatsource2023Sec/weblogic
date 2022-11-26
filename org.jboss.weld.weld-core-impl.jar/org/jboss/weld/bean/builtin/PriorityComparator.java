package org.jboss.weld.bean.builtin;

import java.util.Comparator;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Prioritized;
import org.jboss.weld.bean.ClassBean;
import org.jboss.weld.inject.WeldInstance;
import org.jboss.weld.util.AnnotationApiAbstraction;

public class PriorityComparator implements Comparator {
   private final AnnotationApiAbstraction annotationApi;

   public PriorityComparator(AnnotationApiAbstraction annotationApi) {
      this.annotationApi = annotationApi;
   }

   public int compare(WeldInstance.Handler h1, WeldInstance.Handler h2) {
      return Integer.compare(this.getPriority(h2), this.getPriority(h1));
   }

   private int getPriority(WeldInstance.Handler handler) {
      Bean bean = handler.getBean();
      if (bean instanceof ClassBean) {
         ClassBean classBean = (ClassBean)bean;
         Object priority = classBean.getAnnotated().getAnnotation(this.annotationApi.PRIORITY_ANNOTATION_CLASS);
         if (priority != null) {
            return this.annotationApi.getPriority(priority);
         }
      } else if (bean instanceof Prioritized) {
         return ((Prioritized)bean).getPriority();
      }

      return 0;
   }
}
