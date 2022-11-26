package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import com.bea.util.jam.JParameter;
import weblogic.descriptor.annotation.MethodAnnotations;

public class MethodType {
   protected static final JClass VOID;
   protected static final JClass OBJECT;
   protected static final JClass STRING;
   protected static final JClass BOOLEAN;
   protected static final JClass[] NO_ARGS;
   protected static final JClass[] NO_EXCEPTIONS;
   protected static final JClass BASE;
   protected static final JClass ANY;
   public static final MethodType OPERATION;
   public static final MethodType EXPLICIT_OPERATION;
   public static final MethodType IMPLICIT_OPERATION;
   public static final MethodType VALIDATOR;
   public static final MethodType KEY_GETTER;
   private final String[] names;
   private final JClass returnType;
   private final JClass paramType;

   protected MethodType() {
      this(new String[0], ANY, ANY);
   }

   protected MethodType(String name, JClass returnType, JClass paramType) {
      this(new String[]{name}, returnType, paramType);
   }

   protected MethodType(String[] names, JClass returnType, JClass paramType) {
      this.names = names;
      this.returnType = returnType;
      this.paramType = paramType;
   }

   public boolean isType(MethodType type) {
      return this == type;
   }

   public String getName() {
      return this.names.length > 0 ? this.names[0] : "";
   }

   public MethodDeclaration createDeclaration(BeanClass bean, JMethod method) {
      return new MethodDeclaration(bean, this, method);
   }

   public MethodDeclaration createDeclaration(BeanClass bean) {
      throw new UnsupportedOperationException();
   }

   public boolean matches(JMethod method) {
      return this.matches(method, this.getReturnType(), this.getParamType());
   }

   protected String[] getNames() {
      return this.names;
   }

   protected boolean matches(JMethod method, JClass returnType, JClass paramType) {
      if (!this.matchesNamePattern(method.getSimpleName())) {
         return false;
      } else {
         if (this.getReturnType() != ANY) {
            if (VOID.equals(this.getReturnType())) {
               if (!VOID.equals(method.getReturnType())) {
                  return false;
               }
            } else if (OBJECT.equals(this.getReturnType())) {
               if (method.getReturnType().isPrimitiveType()) {
                  return false;
               }
            } else if (!this.getReturnType().isAssignableFrom(method.getReturnType())) {
               return false;
            }
         }

         if (paramType != ANY) {
            JParameter[] params = method.getParameters();
            if (VOID.equals(paramType)) {
               if (params.length != 0) {
                  return false;
               }
            } else {
               if (params.length != 1) {
                  return false;
               }

               if (OBJECT.equals(paramType)) {
                  if (method.getReturnType().isPrimitiveType()) {
                     return false;
                  }
               } else if (!paramType.isAssignableFrom(params[0].getType())) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   protected boolean matchesNamePattern(String methodName) {
      for(int i = 0; i < this.names.length; ++i) {
         if (methodName.equals(this.names[i])) {
            return true;
         }
      }

      return false;
   }

   private JClass getReturnType() {
      return !BASE.equals(this.returnType) ? this.returnType : Context.get().getBaseBeanInterface();
   }

   private JClass getParamType() {
      return !BASE.equals(this.paramType) ? this.paramType : Context.get().getBaseBeanInterface();
   }

   static {
      VOID = JClasses.VOID;
      OBJECT = JClasses.OBJECT;
      STRING = JClasses.STRING;
      BOOLEAN = JClasses.BOOLEAN;
      NO_ARGS = new JClass[0];
      NO_EXCEPTIONS = NO_ARGS;
      BASE = JClasses.load(Runtime.class);
      ANY = null;
      OPERATION = new OperationType();
      EXPLICIT_OPERATION = new OperationType() {
         public boolean matches(JMethod m) {
            return MethodAnnotations.OPERATION.isDefined(m) || m.getSimpleName().equals("getParentBean");
         }
      };
      IMPLICIT_OPERATION = new OperationType() {
         public boolean matches(JMethod m) {
            return !m.isStatic();
         }
      };
      VALIDATOR = new MethodType("_validate", VOID, VOID) {
         public MethodDeclaration createDeclaration(BeanClass bean) {
            JClass[] exceptions = new JClass[]{JClasses.ILLEGAL_ARGUMENT_EXCEPTION};
            return this.createDeclaration(bean, new SyntheticJMethod(VOID, this.getName(), NO_ARGS, exceptions));
         }
      };
      KEY_GETTER = new MethodType("_getKey", OBJECT, VOID) {
         public MethodDeclaration createDeclaration(BeanClass bean) {
            return this.createDeclaration(bean, new SyntheticJMethod(OBJECT, this.getName(), NO_ARGS, NO_EXCEPTIONS));
         }
      };
   }

   public static class OperationType extends MethodType {
      public boolean isType(MethodType type) {
         return type == this || type == OPERATION;
      }
   }
}
