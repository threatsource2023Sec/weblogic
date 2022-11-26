package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JParameter;
import java.lang.reflect.Modifier;

public class SyntheticJMethod extends SyntheticJAnnotatedElement implements JMethod {
   private JClass returnType;
   private JParameter[] parameters;
   private JClass[] exceptions;
   private int modifiers;

   public SyntheticJMethod(JClass returnType, String name, JClass singleArg, JClass[] exceptions) {
      this(returnType, name, genParams(new JClass[]{singleArg}), exceptions, 1);
   }

   public SyntheticJMethod(JClass returnType, String name, JClass[] signature, JClass[] exceptions) {
      this(returnType, name, genParams(signature), exceptions, 1);
   }

   public SyntheticJMethod(JClass returnType, String name, JParameter[] params, JClass[] exceptions, int modifiers) {
      super(name);
      this.returnType = returnType;
      this.parameters = params;
      this.exceptions = exceptions;
      this.modifiers = modifiers;
   }

   private static JParameter[] genParams(JClass[] signature) {
      JParameter[] params = new JParameter[signature.length];

      for(int i = 0; i < signature.length; ++i) {
         params[i] = new SyntheticJParameter(signature[i], "param " + i);
      }

      return params;
   }

   public JClass getReturnType() {
      return this.returnType;
   }

   public JParameter[] getParameters() {
      return this.parameters;
   }

   public JClass[] getExceptionTypes() {
      return this.exceptions;
   }

   public int getModifiers() {
      return this.modifiers;
   }

   public boolean isPackagePrivate() {
      return !this.isPrivate() && !this.isPublic() && !this.isProtected();
   }

   public boolean isPrivate() {
      return Modifier.isPrivate(this.modifiers);
   }

   public boolean isProtected() {
      return Modifier.isProtected(this.modifiers);
   }

   public boolean isPublic() {
      return Modifier.isPublic(this.modifiers);
   }

   public boolean isFinal() {
      return false;
   }

   public boolean isStatic() {
      return false;
   }

   public boolean isAbstract() {
      return false;
   }

   public boolean isNative() {
      return false;
   }

   public boolean isSynchronized() {
      return false;
   }

   public JClass getContainingClass() {
      return null;
   }

   private static class SyntheticJParameter extends SyntheticJAnnotatedElement implements JParameter {
      private final JClass type;

      private SyntheticJParameter(JClass type, String name) {
         super(name);
         this.type = type;
      }

      public JClass getType() {
         return this.type;
      }

      // $FF: synthetic method
      SyntheticJParameter(JClass x0, String x1, Object x2) {
         this(x0, x1);
      }
   }
}
