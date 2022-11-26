package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JParameter;

public class PropertyMethodDeclaration extends MethodDeclaration {
   private final String prefix;
   private PropertyDeclaration property;

   protected PropertyMethodDeclaration(BeanClass bean, PropertyMethodType declType, JMethod method) {
      super(bean, declType, method);
      this.prefix = declType.getPrefix(method.getSimpleName());
   }

   public JClass getPropertyJClass() {
      JClass returnJClass = this.getReturnJClass();
      return !returnJClass.isVoidType() && !returnJClass.isPrimitiveType() ? returnJClass : this.getFirstParamJClass();
   }

   public final boolean isPropertyMethod() {
      return true;
   }

   public String getPropertyName() {
      return this.getName().substring(this.prefix.length());
   }

   public void setProperty(PropertyDeclaration property) {
      this.property = property;
   }

   public PropertyDeclaration getProperty() {
      return this.property;
   }

   protected Comparable getComparator() {
      return this.getPropertyName() + this.getName();
   }

   protected JClass getFirstParamJClass() {
      JParameter[] params = this.getMethod().getParameters();
      return params.length == 0 ? null : params[0].getType();
   }
}
