package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import java.util.HashMap;
import java.util.Map;

public class BeanCustomizer extends ClassIntrospector {
   private final BeanField field;
   private final String initializer;
   private final String factoryInit;
   private Map superMethodMap;
   private static final String THIS = "this";

   private static ClassNameAndParamList parseClassNameAndParamList(String className) {
      String paramList = "(this)";
      int openParen = className.indexOf("(");
      if (openParen != -1) {
         paramList = className.substring(openParen);
         className = className.substring(0, openParen);
      }

      return new ClassNameAndParamList(className, paramList);
   }

   public static BeanCustomizer create(JClass parent, String className) {
      ClassNameAndParamList cap = parseClassNameAndParamList(className);
      JClass clazz = parent.getClassLoader().loadClass(cap.getClassName());
      if (clazz.isUnresolvedType()) {
         clazz = parent;
      }

      String initializer = "new " + cap.getClassName() + cap.getParamList();
      BeanField field = new BeanField(clazz, "_customizer", (String)null);
      field.setTransient(true);
      return new BeanCustomizer(clazz, field, initializer);
   }

   public static BeanCustomizer create(JClass parent, String className, String factoryClassName) {
      if (factoryClassName != null && factoryClassName.length() != 0) {
         ClassNameAndParamList cap = parseClassNameAndParamList(className);
         JClass clazz = parent.getClassLoader().loadClass(cap.getClassName());
         if (clazz.isUnresolvedType()) {
            return null;
         } else {
            String factoryVariableName = "customizerFactory";
            String factoryInit = "weblogic.descriptor.beangen.CustomizerFactory " + factoryVariableName + " = weblogic.descriptor.beangen.CustomizerFactoryBuilder.buildFactory(\"" + factoryClassName + "\")";
            String initializer = "(" + cap.getClassName() + ")" + factoryVariableName + ".createCustomizer(this)";
            BeanField field = new BeanField(clazz, "_customizer", (String)null);
            field.setTransient(true);
            return new BeanCustomizer(clazz, field, initializer, factoryInit);
         }
      } else {
         return create(parent, className);
      }
   }

   private BeanCustomizer(JClass jClass, BeanField field, String initializer) {
      this(jClass, field, initializer, (String)null);
   }

   private BeanCustomizer(JClass jClass, BeanField field, String initializer, String factoryInit) {
      super(jClass);
      this.field = field;
      this.initializer = initializer;
      this.factoryInit = factoryInit;
      this.initSuperMethodMap(jClass);
   }

   private void initSuperMethodMap(JClass jclass) {
      JClass superclass = jclass.getSuperclass();
      if (superclass == null) {
         this.superMethodMap = new HashMap(0);
      } else {
         MethodFactory factory = MethodFactory.SINGLETON;
         JMethod[] methods = superclass.getMethods();
         this.superMethodMap = new HashMap(methods.length);

         for(int i = 0; i < methods.length; ++i) {
            if (methods[i].isPublic()) {
               MethodDeclaration decl = factory.createDeclaration((BeanClass)null, methods[i], true);
               this.superMethodMap.put(decl, decl);
            }
         }

      }
   }

   public boolean definesMethodInSuperClass(MethodDeclaration key) {
      return this.superMethodMap == null ? false : this.superMethodMap.containsKey(key);
   }

   public BeanField getField() {
      return this.field;
   }

   public String getInitializer() {
      return this.initializer;
   }

   public String getContextualInitializer(String reference) {
      int indexOfThis = this.initializer.indexOf("this");
      if (indexOfThis == -1) {
         return this.initializer;
      } else {
         StringBuffer cloner = new StringBuffer(this.initializer.substring(0, indexOfThis));
         cloner.append(reference);
         cloner.append(this.initializer.substring(indexOfThis + "this".length()));
         return cloner.toString();
      }
   }

   public boolean isUseFactory() {
      return this.factoryInit != null;
   }

   public String getFactoryInit() {
      return this.factoryInit;
   }

   private static class ClassNameAndParamList {
      private final String className;
      private final String paramList;

      public ClassNameAndParamList(String className, String paramList) {
         this.className = className;
         this.paramList = paramList;
      }

      public String getClassName() {
         return this.className;
      }

      public String getParamList() {
         return this.paramList;
      }
   }
}
