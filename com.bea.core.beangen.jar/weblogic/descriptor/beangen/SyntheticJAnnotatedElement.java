package weblogic.descriptor.beangen;

import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JAnnotationValue;
import com.bea.util.jam.JComment;
import com.bea.util.jam.JElement;
import com.bea.util.jam.JSourcePosition;
import com.bea.util.jam.visitor.JVisitor;

public class SyntheticJAnnotatedElement implements JElement {
   private final String name;

   public SyntheticJAnnotatedElement(String name) {
      this.name = name;
   }

   public JElement getParent() {
      return null;
   }

   public String getSimpleName() {
      return this.name;
   }

   public String getQualifiedName() {
      return this.getSimpleName();
   }

   public JSourcePosition getSourcePosition() {
      return null;
   }

   public void accept(JVisitor jVisitor) {
   }

   public Object getArtifact() {
      return null;
   }

   public boolean isSourceAvailable() {
      return false;
   }

   public JAnnotation[] getAnnotations() {
      return new JAnnotation[0];
   }

   public JAnnotation getAnnotation(Class aClass) {
      return null;
   }

   public Object getAnnotationProxy(Class aClass) {
      return null;
   }

   public JAnnotation getAnnotation(String s) {
      return null;
   }

   public JAnnotationValue getAnnotationValue(String s) {
      return null;
   }

   public JComment getComment() {
      return null;
   }

   public JAnnotation[] getAllJavadocTags() {
      return new JAnnotation[0];
   }
}
