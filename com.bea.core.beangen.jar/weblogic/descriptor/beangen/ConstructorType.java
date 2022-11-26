package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;

public class ConstructorType extends MethodType {
   public static final ConstructorType CONSTRUCTOR = new ConstructorType() {
      public boolean matches(JMethod m) {
         String cName = m.getContainingClass().getSimpleName();
         return m.isStatic() && m.getSimpleName().equals(cName);
      }
   };

   protected ConstructorType() {
      super("", VOID, ANY);
   }

   public MethodDeclaration createDeclaration(BeanClass bean, JMethod method) {
      return new ConstructorDeclaration(bean, method);
   }

   public MethodDeclaration createDeclaration(BeanClass bean, JClass[] sig) {
      return new ConstructorDeclaration(bean, new SyntheticJMethod(VOID, bean.getClassName(), sig, NO_EXCEPTIONS));
   }

   public boolean matches(JMethod m) {
      String cName = m.getContainingClass().getSimpleName();
      return m.isStatic() && m.getSimpleName().equals(cName);
   }
}
