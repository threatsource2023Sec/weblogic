package weblogic.j2ee.customizers;

import weblogic.j2ee.descriptor.wl.AnnotatedClassBean;

public class AnnotatedClassBeanCustomizer implements weblogic.j2ee.descriptor.wl.customizers.AnnotatedClassBeanCustomizer {
   private AnnotatedClassBean _customized;
   private String _shortDescription;

   public AnnotatedClassBeanCustomizer(AnnotatedClassBean customized) {
      this._customized = customized;
   }

   public String getShortDescription() {
      if (this._shortDescription == null) {
         String bundle = this._customized.getAnnotatedClassName();
         String key = this._customized.getAnnotatedClassName();
         this._shortDescription = AnnotationLocalizer.getShortDescription(bundle, key);
      }

      return this._shortDescription;
   }
}
