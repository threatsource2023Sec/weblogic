package weblogic.j2ee.dd.xml.validator.injectiontarget;

import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.dd.xml.validator.AnnotationValidator;
import weblogic.j2ee.descriptor.ApplicationClientBean;
import weblogic.j2ee.descriptor.InjectionTargetBean;

public class InjectionTargetValidatorFactory {
   public static AnnotationValidator getValidator(DescriptorBean bean) {
      if (!(bean instanceof InjectionTargetBean)) {
         return null;
      } else {
         DescriptorBean root = bean.getDescriptor().getRootBean();
         return root instanceof ApplicationClientBean ? InjectionTargetValidatorFactory.Holder.CLIENT_VALIDATOR : InjectionTargetValidatorFactory.Holder.J2EE_VALIDATOR;
      }
   }

   static class Holder {
      private static AnnotationValidator CLIENT_VALIDATOR = new ClientValidator();
      private static AnnotationValidator J2EE_VALIDATOR = new J2EEValidator();
   }
}
