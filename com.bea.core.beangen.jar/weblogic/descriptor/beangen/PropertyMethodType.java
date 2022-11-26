package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;

public class PropertyMethodType extends MethodType {
   public static final PropertyMethodType PROPERTY_METHOD = new PropertyMethodType();
   public static final PropertyMethodType GETTER;
   public static final PropertyMethodType SETTER;
   public static final PropertyMethodType STRING_GETTER;
   public static final PropertyMethodType STRING_SETTER;
   public static final PropertyMethodType ISSET;
   public static final PropertyMethodType IS_INHERITED;
   public static final PropertyMethodType CREATOR;
   public static final PropertyMethodType DESTROYER;
   public static final PropertyMethodType ADDER;
   public static final PropertyMethodType REMOVER;
   public static final PropertyMethodType FINDER;
   private String suffix;

   protected PropertyMethodType() {
      this.suffix = "";
   }

   protected PropertyMethodType(String prefix, JClass returnType, JClass paramType) {
      this(prefix, "", returnType, paramType);
   }

   protected PropertyMethodType(String prefix, String suffix, JClass returnType, JClass paramType) {
      this(new String[]{prefix}, returnType, paramType);
      this.suffix = suffix;
   }

   protected PropertyMethodType(String[] prefixes, JClass returnType, JClass paramType) {
      super(prefixes, returnType, paramType);
      this.suffix = "";
   }

   public String getPrefix(String methodName) {
      String[] prefixes = this.getNames();
      if (prefixes.length == 0) {
         return "";
      } else {
         for(int i = 0; i < prefixes.length; ++i) {
            if (methodName.startsWith(prefixes[i])) {
               return prefixes[i];
            }
         }

         throw new AssertionError("getPrefix(JMethod m) passed method, " + methodName + ", which is does not have the expected prefix (" + this.getPrefix() + ")");
      }
   }

   public boolean isType(MethodType type) {
      return type == this || type == PROPERTY_METHOD;
   }

   public boolean matches(JMethod method) {
      return super.matches(method) && method.getSimpleName().endsWith(this.suffix);
   }

   public MethodDeclaration createDeclaration(BeanClass bean, JMethod method) {
      return new PropertyMethodDeclaration(bean, this, method);
   }

   public MethodDeclaration createDeclaration(BeanClass bean, JClass type, String propName) {
      throw new UnsupportedOperationException();
   }

   protected boolean matchesNamePattern(String methodName) {
      if (this.suffix.length() > 0 && !methodName.endsWith(this.suffix)) {
         return false;
      } else {
         String[] prefixes = this.getNames();

         for(int i = 0; i < prefixes.length; ++i) {
            if (methodName.startsWith(prefixes[i])) {
               return true;
            }
         }

         return false;
      }
   }

   protected String getName(String propName) {
      return this.getPrefix() + propName + this.suffix;
   }

   private String getPrefix() {
      return this.getName();
   }

   static {
      GETTER = new PropertyMethodType(new String[]{"get", "is"}, ANY, VOID);
      SETTER = new PropertyMethodType("set", VOID, ANY) {
         public MethodDeclaration createDeclaration(BeanClass bean, JClass type, String propName) {
            JClass[] exceptions = new JClass[]{JClasses.INVALID_ATTRIBUTE_VALUE_EXCEPTION};
            return this.createDeclaration(bean, new SyntheticJMethod(VOID, this.getName(propName), type, exceptions));
         }
      };
      STRING_GETTER = new PropertyMethodType("get", "AsString", ANY, VOID) {
         public MethodDeclaration createDeclaration(BeanClass bean, JClass type, String propName) {
            return this.createDeclaration(bean, new SyntheticJMethod(STRING, this.getName(propName), NO_ARGS, NO_EXCEPTIONS));
         }
      };
      STRING_SETTER = new PropertyMethodType("set", "AsString", ANY, VOID) {
         public MethodDeclaration createDeclaration(BeanClass bean, JClass type, String propName) {
            return this.createDeclaration(bean, new SyntheticJMethod(VOID, this.getName(propName), STRING, NO_EXCEPTIONS));
         }
      };
      ISSET = new PropertyMethodType("is", "Set", BOOLEAN, VOID) {
         public MethodDeclaration createDeclaration(BeanClass bean, JClass type, String propName) {
            return this.createDeclaration(bean, new SyntheticJMethod(BOOLEAN, this.getName(propName), NO_ARGS, NO_EXCEPTIONS));
         }
      };
      IS_INHERITED = new PropertyMethodType("is", "Inherited", BOOLEAN, VOID) {
         public MethodDeclaration createDeclaration(BeanClass bean, JClass type, String propName) {
            return this.createDeclaration(bean, new SyntheticJMethod(BOOLEAN, this.getName(propName), NO_ARGS, NO_EXCEPTIONS));
         }
      };
      CREATOR = new PropertyMethodType("create", BASE, ANY);
      DESTROYER = new PropertyMethodType("destroy", VOID, ANY) {
         public boolean matches(JMethod m) {
            return this.matches(m, VOID, OBJECT) || this.matches(m, VOID, VOID);
         }
      };
      ADDER = new PropertyMethodType("add", ANY, ANY) {
         public MethodDeclaration createDeclaration(BeanClass bean, JClass type, String propName) {
            return this.createDeclaration(bean, new SyntheticJMethod(VOID, this.getName(propName), type, NO_EXCEPTIONS));
         }
      };
      REMOVER = new PropertyMethodType("remove", ANY, ANY) {
         public MethodDeclaration createDeclaration(BeanClass bean, JClass type, String propName) {
            return this.createDeclaration(bean, new SyntheticJMethod(VOID, this.getName(propName), type, NO_EXCEPTIONS));
         }
      };
      FINDER = new PropertyMethodType("lookup", BASE, ANY);
   }
}
