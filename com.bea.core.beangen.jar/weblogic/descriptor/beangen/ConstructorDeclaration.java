package weblogic.descriptor.beangen;

import com.bea.util.jam.JMethod;

public class ConstructorDeclaration extends MethodDeclaration {
   protected ConstructorDeclaration(BeanClass bean, JMethod method) {
      super(bean, ConstructorType.CONSTRUCTOR, method);
      this.setOrder(0);
   }

   public String getDeclaration() {
      String name = this.getName();
      String params = this.getParamDeclList();
      return this.getAccess() + " " + name + "(" + params + ")";
   }
}
