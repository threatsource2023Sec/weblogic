package weblogic.descriptor.codegen;

import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JAnnotationValue;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JamClassLoader;
import java.util.StringTokenizer;

public class Annotation {
   private static final Byte ZERO_BYTE = new Byte((byte)0);
   private static final Integer ZERO_INT = new Integer(0);
   private static final Long ZERO_LONG = new Long(0L);
   private static final Double ZERO_DOUBLE = new Double(0.0);
   private static final Short ZERO_SHORT = new Short((short)0);
   protected JAnnotationValue jAnnotationValue;
   protected JamClassLoader loader;
   protected String name;

   public Annotation(JAnnotation jAnnotation, JamClassLoader loader) {
      this.name = jAnnotation.getQualifiedName();
      this.jAnnotationValue = jAnnotation.getValue("value");
      this.loader = loader;
   }

   public String getName() {
      return this.name;
   }

   public String getStringValue() {
      return this.jAnnotationValue == null ? "" : this.jAnnotationValue.asString();
   }

   public String[] getStringTokens() {
      String s = this.getStringValue();
      if (s == null) {
         return new String[0];
      } else {
         StringTokenizer st = new StringTokenizer(s, ",");
         String[] tokens = new String[st.countTokens()];

         for(int i = 0; st.hasMoreTokens(); tokens[i++] = st.nextToken().trim()) {
         }

         return tokens;
      }
   }

   public AnnotatableClass getAnnotatableClassValue() {
      return this.jAnnotationValue == null ? null : this.newAnnotatableClass(this.loader.loadClass(this.jAnnotationValue.asString()));
   }

   protected AnnotatableClass newAnnotatableClass(JClass jClass) {
      return this.jAnnotationValue == null ? null : new AnnotatableClass(jClass);
   }

   public Integer getIntegerValue() {
      return this.jAnnotationValue == null ? ZERO_INT : new Integer(this.jAnnotationValue.asInt());
   }

   public Boolean getBooleanValue() {
      return this.jAnnotationValue == null ? Boolean.FALSE : new Boolean(this.jAnnotationValue.asBoolean());
   }

   public Long getLongValue() {
      return this.jAnnotationValue == null ? ZERO_LONG : new Long(this.jAnnotationValue.asLong());
   }

   public Short getShortValue() {
      return this.jAnnotationValue == null ? ZERO_SHORT : new Short(this.jAnnotationValue.asShort());
   }

   public Double getDoubleValue() {
      return this.jAnnotationValue == null ? ZERO_DOUBLE : new Double(this.jAnnotationValue.asDouble());
   }

   public Byte getByteValue() {
      return this.jAnnotationValue == null ? ZERO_BYTE : new Byte(this.jAnnotationValue.asByte());
   }

   public String toString() {
      return "@" + this.getName() + " " + this.getStringValue();
   }

   public boolean equals(Object other) {
      if (other instanceof Annotation) {
         Annotation oa = (Annotation)other;
         return this.toString().equals(oa.toString());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.toString().hashCode();
   }
}
