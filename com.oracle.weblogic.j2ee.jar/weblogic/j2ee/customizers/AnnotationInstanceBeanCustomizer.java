package weblogic.j2ee.customizers;

import weblogic.j2ee.descriptor.wl.AnnotationInstanceBean;

public class AnnotationInstanceBeanCustomizer implements weblogic.j2ee.descriptor.wl.customizers.AnnotationInstanceBeanCustomizer {
   private AnnotationInstanceBean _customized;
   private String _shortDescription;

   public AnnotationInstanceBeanCustomizer(AnnotationInstanceBean customized) {
      assert customized != null : "The AnnotationInstanceBean to be customized cannot be null";

      this._customized = customized;
   }

   public String getShortDescription() {
      if (this._shortDescription == null) {
         String bundle = this._customized.getAnnotationClassName();
         String key = this._customized.getAnnotationClassName();
         this._shortDescription = AnnotationLocalizer.getShortDescription(bundle, key);
      }

      return this._shortDescription;
   }
}
