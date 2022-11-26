package org.jboss.weld.bean.builtin.ee;

import java.lang.reflect.Field;
import java.security.AccessController;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.BeanAttributes;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedField;
import org.jboss.weld.bean.AbstractClassBean;
import org.jboss.weld.bean.DisposalMethod;
import org.jboss.weld.bootstrap.api.ServiceRegistry;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.injection.FieldInjectionPoint;
import org.jboss.weld.injection.InjectionContextImpl;
import org.jboss.weld.injection.InjectionPointFactory;
import org.jboss.weld.injection.ResourceInjection;
import org.jboss.weld.injection.ResourceInjectionFactory;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.security.GetAccessibleCopyOfMember;
import org.jboss.weld.util.reflection.Reflections;

public class StaticEEResourceProducerField extends EEResourceProducerField {
   private final ResourceInjection resourceInjection;
   private final Field accessibleField;
   private final InjectionContextImpl injectionContext;

   public static StaticEEResourceProducerField of(BeanAttributes attributes, EnhancedAnnotatedField field, AbstractClassBean declaringBean, DisposalMethod disposalMethod, BeanManagerImpl manager, ServiceRegistry services) {
      return new StaticEEResourceProducerField(attributes, field, declaringBean, disposalMethod, manager, services);
   }

   protected StaticEEResourceProducerField(BeanAttributes attributes, EnhancedAnnotatedField field, AbstractClassBean declaringBean, DisposalMethod disposalMethod, BeanManagerImpl manager, ServiceRegistry services) {
      super(attributes, field, declaringBean, disposalMethod, manager, services);
      this.resourceInjection = this.getResourceInjection(field, declaringBean, manager);
      this.accessibleField = (Field)AccessController.doPrivileged(new GetAccessibleCopyOfMember(field.getJavaMember()));
      this.injectionContext = new InjectionContextImpl(manager, declaringBean.getInjectionTarget(), declaringBean.getAnnotated(), (Object)null) {
         public void proceed() {
         }
      };
   }

   protected ResourceInjection getResourceInjection(EnhancedAnnotatedField field, AbstractClassBean declaringBean, BeanManagerImpl manager) {
      FieldInjectionPoint injectionPoint = (FieldInjectionPoint)Reflections.cast(InjectionPointFactory.silentInstance().createFieldInjectionPoint(field, declaringBean, declaringBean.getBeanClass(), manager));
      return ((ResourceInjectionFactory)manager.getServices().get(ResourceInjectionFactory.class)).getStaticProducerFieldResourceInjection(injectionPoint, this.beanManager);
   }

   public Object create(CreationalContext creationalContext) {
      if (this.resourceInjection != null) {
         return this.resourceInjection.getResourceReference(creationalContext);
      } else {
         this.injectionContext.run();

         try {
            return Reflections.cast(this.accessibleField.get((Object)null));
         } catch (IllegalAccessException var3) {
            throw new WeldException(var3);
         }
      }
   }
}
