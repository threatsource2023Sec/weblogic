package weblogic.j2ee.customizers;

import java.io.IOException;
import java.util.StringTokenizer;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.AnnotationInstanceBean;
import weblogic.j2ee.descriptor.wl.ArrayMemberBean;
import weblogic.utils.encoders.BASE64Decoder;
import weblogic.utils.encoders.BASE64Encoder;

public class ArrayMemberBeanCustomizer implements weblogic.j2ee.descriptor.wl.customizers.ArrayMemberBeanCustomizer {
   private ArrayMemberBean _customized;
   private AnnotationInstanceBean _belongingAnnotation;
   private String _shortDescription;
   private static final BASE64Encoder ENCODER = new BASE64Encoder();
   private static final BASE64Decoder DECODER = new BASE64Decoder();
   private static final String DELIM = "@@";

   public ArrayMemberBeanCustomizer(ArrayMemberBean customized) {
      this._customized = customized;
   }

   public void _postCreate() {
      if (this._customized instanceof DescriptorBean) {
         DescriptorBean parent = ((DescriptorBean)this._customized).getParentBean();
         if (parent instanceof AnnotationInstanceBean) {
            this._belongingAnnotation = (AnnotationInstanceBean)parent;
         }
      }

   }

   public String[] getOverrideValues() {
      String[] overrideValue = null;
      if (this._customized.getRequiresEncryption()) {
         String concatenated = this._customized.getSecuredOverrideValue();
         return this.stringToArray(concatenated);
      } else {
         overrideValue = this._customized.getCleartextOverrideValues();
         return overrideValue;
      }
   }

   public void setOverrideValues(String[] values) {
      if (values != null) {
         if (this._customized.getRequiresEncryption()) {
            this._customized.setSecuredOverrideValue(this.arrayToString(values));
         } else {
            this._customized.setCleartextOverrideValues(values);
         }

      }
   }

   private String arrayToString(String[] values) {
      StringBuilder builder = new StringBuilder();

      for(int i = 0; i < values.length; ++i) {
         String value = values[i];
         if (value != null) {
            builder.append(ENCODER.encodeBuffer(value.getBytes()));
         }

         if (i != values.length - 1) {
            builder.append("@@");
         }
      }

      return builder.toString();
   }

   private String[] stringToArray(String values) {
      if (values != null && values.length() != 0) {
         StringTokenizer st = new StringTokenizer(values, "@@", false);
         String[] result = new String[st.countTokens()];

         String item;
         for(int i = 0; st.hasMoreTokens(); result[i++] = item) {
            item = null;

            try {
               byte[] bytes = DECODER.decodeBuffer(st.nextToken());
               item = new String(bytes);
            } catch (IOException var7) {
               var7.printStackTrace();
            }
         }

         return result;
      } else {
         return new String[0];
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
