package weblogic.j2ee.customizers;

import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.AnnotationInstanceBean;
import weblogic.j2ee.descriptor.wl.MemberBean;

public class MemberBeanCustomizer implements weblogic.j2ee.descriptor.wl.customizers.MemberBeanCustomizer {
   private MemberBean _customized;
   private AnnotationInstanceBean _belongingAnnotation;
   private String _shortDescription;

   public MemberBeanCustomizer(MemberBean paramCustomized) {
      this._customized = paramCustomized;
   }

   public void _postCreate() {
      if (this._customized instanceof DescriptorBean) {
         DescriptorBean parent = ((DescriptorBean)this._customized).getParentBean();
         if (parent instanceof AnnotationInstanceBean) {
            this._belongingAnnotation = (AnnotationInstanceBean)parent;
         }
      }

   }

   public String getOverrideValue() {
      String overrideValue = null;
      if (this._customized.getRequiresEncryption()) {
         overrideValue = this._customized.getSecuredOverrideValue();
      } else {
         overrideValue = this._customized.getCleartextOverrideValue();
      }

      return overrideValue;
   }

   public void setOverrideValue(String value) {
      if (value != null) {
         if (this._customized.getRequiresEncryption()) {
            this._customized.setSecuredOverrideValue(value);
         } else {
            this._customized.setCleartextOverrideValue(value);
         }

      }
   }

   public String getShortDescription() {
      if (this._shortDescription == null && this._belongingAnnotation != null) {
         String className = this._belongingAnnotation.getAnnotationClassName();
         String key = className + "." + this._customized.getMemberName();
         this._shortDescription = AnnotationLocalizer.getShortDescription(className, key);
      }

      return this._shortDescription;
   }
}
