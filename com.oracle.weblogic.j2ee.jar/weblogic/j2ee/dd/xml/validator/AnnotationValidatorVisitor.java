package weblogic.j2ee.dd.xml.validator;

import weblogic.descriptor.Visitor;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.j2ee.dd.xml.validator.injectiontarget.InjectionTargetValidatorFactory;
import weblogic.j2ee.dd.xml.validator.lifecyclecallback.LifecycleCallbackValidatorFactory;
import weblogic.j2ee.dd.xml.validator.listener.ListenerBeanValidator;
import weblogic.utils.ErrorCollectionException;

public class AnnotationValidatorVisitor implements Visitor {
   private ClassLoader classLoader = null;
   private ErrorCollectionException errors = null;

   public AnnotationValidatorVisitor(ClassLoader cl) {
      this.classLoader = cl;
      this.errors = new ErrorCollectionException();
   }

   public void visit(AbstractDescriptorBean bean) {
      this.visitLifeCycleCallbackBean(bean);
      this.visitInjectionTargetBean(bean);
      this.visitListenerBean(bean);
   }

   public ErrorCollectionException getErrors() {
      return this.errors;
   }

   private void visitLifeCycleCallbackBean(AbstractDescriptorBean bean) {
      AnnotationValidator validator = LifecycleCallbackValidatorFactory.getValidator(bean);
      if (validator != null) {
         try {
            validator.validate(bean, this.classLoader);
         } catch (ErrorCollectionException var4) {
            this.errors.add(var4);
         }
      }

   }

   private void visitInjectionTargetBean(AbstractDescriptorBean bean) {
      AnnotationValidator validator = InjectionTargetValidatorFactory.getValidator(bean);
      if (validator != null) {
         try {
            validator.validate(bean, this.classLoader);
         } catch (ErrorCollectionException var4) {
            this.errors.add(var4);
         }
      }

   }

   private void visitListenerBean(AbstractDescriptorBean bean) {
      try {
         ListenerBeanValidator validator = new ListenerBeanValidator();
         validator.validate(bean, this.classLoader);
      } catch (ErrorCollectionException var3) {
         this.errors.add(var3);
      }

   }
}
