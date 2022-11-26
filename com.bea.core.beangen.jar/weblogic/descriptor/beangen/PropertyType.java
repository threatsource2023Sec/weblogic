package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JElement;
import com.bea.util.jam.JParameter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.descriptor.annotation.AnnotationDefinition;
import weblogic.descriptor.annotation.PropertyAnnotations;

public abstract class PropertyType {
   private static PropertyType PRIMITIVE = new PrimitivePropertyType();
   private static PropertyType PRIMITIVE_BOOLEAN = new BooleanPropertyType();
   private static PropertyType PRIMITIVE_INT = new IntPropertyType();
   private static PropertyType STRING = new StringPropertyType();
   private static PropertyType CHILD = new ChildPropertyType();
   private static PropertyType REFERENCE = new ReferencePropertyType();
   private static PropertyType[] allowedTypes;

   public List validateDeclaration(PropertyDeclaration declaration) {
      String[] declaredAnnotations = declaration.getAllAnnotationNames();
      ArrayList postGenValidations = new ArrayList();

      for(int count = 0; count < declaredAnnotations.length; ++count) {
         AnnotationDefinition annotationDef = PropertyAnnotations.getAnnotationDefinition(declaredAnnotations[count]);
         if (annotationDef != null) {
            if (annotationDef.isAllowedType(this.getClass())) {
               postGenValidations.addAll(annotationDef.validate(declaration, declaredAnnotations[count]));
            } else {
               Context.get().getLog().error("Cannot place annotation '" + declaredAnnotations[count] + "' on this property " + declaration.getName(), declaration.getJElement());
            }
         }
      }

      return postGenValidations;
   }

   private boolean isPropertyTransient(BeanElement getter, BeanElement setter) {
      if (getter != null && getter.isAnnotationDefined(PropertyAnnotations.TRANSIENT)) {
         return getter.isAnnotationTrue(PropertyAnnotations.TRANSIENT);
      } else {
         return setter != null && setter.isAnnotationDefined(PropertyAnnotations.TRANSIENT) ? setter.isAnnotationTrue(PropertyAnnotations.TRANSIENT) : false;
      }
   }

   private void validateTransientRule(PropertyDeclaration declaration, JClass beanClass, boolean checkTransient, List errors) {
      JClass[] ifcs = beanClass.getInterfaces();

      for(int i = 0; i < ifcs.length; ++i) {
         if (Context.get().getBaseBeanInterface().isObjectType() || Context.get().getBaseBeanInterface().isAssignableFrom(ifcs[i]) && !Context.get().getBaseBeanInterface().equals(ifcs[i])) {
            ClassIntrospector introscpector = new ClassIntrospector(ifcs[i]);
            MethodDeclaration getterMethod = introscpector.getMethod(declaration.getter);
            MethodDeclaration setterMethod;
            if (declaration.setter != null) {
               setterMethod = introscpector.getMethod(declaration.setter);
            } else {
               JClass[] signature = new JClass[]{declaration.getJClass()};
               Object key = MethodFactory.SINGLETON.createKey("set" + declaration.getName(), signature);
               setterMethod = introscpector.getMethod((MethodDeclaration)key);
            }

            if ((getterMethod != null || setterMethod != null) && checkTransient ^ this.isPropertyTransient(getterMethod, setterMethod)) {
               errors.add(new ErrorInfo("It is not legal to re-define the transient nature of a property in a subclass. The property " + ifcs[i].getQualifiedName() + "." + declaration.getName() + " is transient(" + !checkTransient + "), whereas it has been declared transient(" + checkTransient + ") in an extended interface", getterMethod != null ? getterMethod.getJElement() : setterMethod.getJElement()));
            }

            this.validateTransientRule(declaration, ifcs[i], checkTransient, errors);
         }
      }

   }

   protected void validateAddRemoveCustomization(MethodDeclaration method, PropertyDeclaration declaration) {
      if (method.getImplementation().isImplementedByCustomizer()) {
         if (method.isSynthetic()) {
            Context.get().getLog().error("Customization of Adder or Remover is allowed only when declared in the Bean inteface." + declaration.getBean().getInterfaceName() + "." + method.getName() + "()", declaration.getJElement());
         } else if (!declaration.isObsolete() && !declaration.isTransient()) {
            Context.get().getLog().error("Customization of Adder or Remover is allowed only for obsolete or transient properties: " + declaration.getBean().getInterfaceName() + "." + method.getName() + "()", declaration.getJElement());
         }
      }

   }

   protected List validateGeneration(PropertyDeclaration declaration) {
      List postGenValidations = new ArrayList(1);
      List errors = new ArrayList();
      if (declaration.isAnnotationTrue(PropertyAnnotations.TRANSIENT)) {
         this.validateTransientRule(declaration, declaration.getBean().getJClass(), declaration.isAnnotationTrue(PropertyAnnotations.TRANSIENT), errors);
      }

      Iterator i = errors.iterator();

      while(i.hasNext()) {
         ErrorInfo info = (ErrorInfo)i.next();
         Context.get().getLog().error(info.errorMessage, info.element);
      }

      return postGenValidations;
   }

   protected boolean hasTransientPropertyOverride(PropertyDeclaration declaration) {
      if (declaration.isAnnotationTrue(PropertyAnnotations.TRANSIENT)) {
         List errors = new ArrayList();
         this.validateTransientRule(declaration, declaration.getBean().getJClass(), declaration.isAnnotationTrue(PropertyAnnotations.TRANSIENT), errors);
         return !errors.isEmpty();
      } else {
         return false;
      }
   }

   public abstract boolean matches(PropertyDeclaration var1);

   public String getRemoveValidator(PropertyDeclaration declaration) {
      return null;
   }

   public static PropertyType getPropertyType(PropertyDeclaration declaration) {
      for(int count = 0; count < allowedTypes.length; ++count) {
         if (allowedTypes[count].matches(declaration)) {
            return allowedTypes[count];
         }
      }

      return null;
   }

   static {
      allowedTypes = new PropertyType[]{PRIMITIVE_BOOLEAN, PRIMITIVE_INT, STRING, CHILD, REFERENCE, PRIMITIVE};
   }

   public static class ReferencePropertyType extends PropertyType {
      public boolean matches(PropertyDeclaration declaration) {
         return declaration.isReference();
      }

      public String getRemoveValidator(PropertyDeclaration declaration) {
         return declaration.getRemover() == null ? null : declaration.getRemover().getAnnotationString(PropertyAnnotations.REMOVE_VALIDATOR);
      }

      protected List validateGeneration(PropertyDeclaration declaration) {
         List postGenValidations = super.validateGeneration(declaration);
         MethodDeclaration[] methods = declaration.getMethods();

         for(int count = 0; count < methods.length; ++count) {
            if (methods[count].isAdder() || methods[count].isRemover()) {
               this.validateAddRemoveCustomization(methods[count], declaration);
            }
         }

         return postGenValidations;
      }
   }

   public static class ChildPropertyType extends PropertyType {
      public boolean matches(PropertyDeclaration declaration) {
         return declaration.isChild();
      }

      public List validateDeclaration(PropertyDeclaration declaration) {
         List postGenValidations = super.validateDeclaration(declaration);
         MethodDeclaration[] methods = declaration.getMethods();

         for(int count = 0; count < methods.length; ++count) {
            if ((methods[count].isSetter() || methods[count].isAdder() || methods[count].isRemover()) && !methods[count].isDeprecated() && !methods[count].isAnnotationDefined(PropertyAnnotations.INTERNAL)) {
               Context.get().getLog().error("Setter, Adder or Remover declared for child property: " + declaration.getBean().getInterfaceName() + "." + methods[count].getName() + "()", methods[count].getJElement());
            }
         }

         return postGenValidations;
      }

      protected List validateGeneration(PropertyDeclaration declaration) {
         List postGenValidations = super.validateGeneration(declaration);
         MethodDeclaration[] methods = declaration.getMethods();

         for(int count = 0; count < methods.length; ++count) {
            if (methods[count].isDeclared() && methods[count].isType(PropertyMethodType.CREATOR)) {
               if (!methods[count].getImplementation().isImplementedByCustomizer()) {
                  JClass type = methods[count].getReturnJClass();
                  JParameter[] params = methods[count].getMethod().getParameters();
                  if (params.length > 0) {
                     int i = 0;
                     if (JClasses.CLASS.isAssignableFrom(params[0].getType()) || methods[count].isCreatorClone() && type.isAssignableFrom(params[0].getType())) {
                        i = 1;
                     }

                     for(ClassIntrospector introspector = new ClassIntrospector(type); i < params.length; ++i) {
                        String propName = Character.toUpperCase(params[i].getSimpleName().charAt(0)) + params[i].getSimpleName().substring(1);
                        if (!introspector.definesMethod("get" + propName, new JClass[0])) {
                           Context.get().getLog().error("get" + propName + "() not found in " + type.getQualifiedName() + "\nParameter names in a creator method have to be property names in the bean being created by it.", declaration.getJElement());
                        }
                     }
                  }
               }
            } else if (methods[count].isAdder() || methods[count].isRemover()) {
               this.validateAddRemoveCustomization(methods[count], declaration);
            }
         }

         return postGenValidations;
      }

      public String getRemoveValidator(PropertyDeclaration declaration) {
         return declaration.getDestroyer() == null ? null : declaration.getDestroyer().getAnnotationString(PropertyAnnotations.REMOVE_VALIDATOR);
      }
   }

   public static class StringPropertyType extends PropertyType {
      public boolean matches(PropertyDeclaration declaration) {
         return declaration.getComponentJClass().equals(JClasses.STRING);
      }
   }

   public static class IntPropertyType extends PrimitivePropertyType {
      public boolean matches(PropertyDeclaration declaration) {
         return declaration.getComponentJClass().equals(JClasses.INT);
      }
   }

   public static class BooleanPropertyType extends PrimitivePropertyType {
      public boolean matches(PropertyDeclaration declaration) {
         return declaration.getComponentJClass().equals(JClasses.BOOLEAN);
      }
   }

   public static class PrimitivePropertyType extends PropertyType {
      public boolean matches(PropertyDeclaration declaration) {
         return true;
      }
   }

   private static class ErrorInfo {
      String errorMessage;
      JElement element;

      ErrorInfo(String msg, JElement element) {
         this.errorMessage = msg;
         this.element = element;
      }
   }
}
