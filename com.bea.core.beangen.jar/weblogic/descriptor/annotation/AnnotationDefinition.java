package weblogic.descriptor.annotation;

import com.bea.util.jam.JAnnotatedElement;
import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JAnnotationValue;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JMember;
import java.util.ArrayList;
import java.util.List;
import weblogic.descriptor.beangen.BeanGenerationException;
import weblogic.descriptor.beangen.Context;
import weblogic.descriptor.beangen.PostGenValidation;
import weblogic.descriptor.beangen.PropertyDeclaration;
import weblogic.descriptor.beangen.PropertyType;

public class AnnotationDefinition {
   public static final int INHERIT_FROM_CLASS = 1;
   public static final int INHERIT_FROM_SUPERCLASS = 2;
   private final String[] names;
   private final boolean inheritFromSuper;
   private final boolean inheritFromClass;
   private List allowedTypesList;
   private String[] allowedTypesArray;
   private static final String PROPERTY_TYPE_CLASSNAME = PropertyType.class.getName();

   protected AnnotationDefinition(String[] names, int flags) {
      this.allowedTypesList = null;
      this.allowedTypesArray = null;
      this.names = names;
      this.inheritFromSuper = (flags & 2) > 0;
      this.inheritFromClass = (flags & 1) > 0;
   }

   protected AnnotationDefinition(String[] names) {
      this(names, 0);
   }

   protected AnnotationDefinition(String[] names, String[] allowedTypes) {
      this(names);
      this.allowedTypesArray = allowedTypes;
   }

   public AnnotationDefinition(String name) {
      this(new String[]{name});
   }

   protected AnnotationDefinition(String name, String alias) {
      this(new String[]{name, alias});
   }

   protected AnnotationDefinition(String name, String alias1, String alias2) {
      this(new String[]{name, alias1, alias2});
   }

   public boolean isDefined(JAnnotatedElement element) {
      return this.getAnnotationValue(element) != null;
   }

   public JAnnotationValue getAnnotationValue(JAnnotatedElement element) {
      JAnnotationValue a = null;
      if (element != null) {
         for(int i = 0; a == null && i < this.names.length; ++i) {
            a = ((JAnnotatedElement)element).getAnnotationValue(this.names[i]);
         }

         if (a == null && this.inheritFromClass && element instanceof JMember) {
            JClass containingClass = ((JMember)element).getContainingClass();
            if (containingClass != null) {
               element = containingClass;
               a = this.getAnnotationValue(containingClass);
            }
         }

         if (a == null && this.inheritFromSuper && element instanceof JClass) {
            JClass[] ifcs = ((JClass)element).getInterfaces();

            for(int i = 0; a == null && i < ifcs.length; ++i) {
               a = this.getAnnotationValue(ifcs[i]);
            }
         }
      }

      return a;
   }

   public JAnnotationValue[] getAnnotationValues(JAnnotatedElement element) {
      JAnnotationValue[] values = new JAnnotationValue[0];
      if (element != null) {
         for(int i = 0; values.length == 0 && i < this.names.length; ++i) {
            JAnnotation annotation = ((JAnnotatedElement)element).getAnnotation(this.names[i]);
            values = annotation == null ? values : annotation.getValues();
         }

         if (values.length == 0 && this.inheritFromClass && element instanceof JMember) {
            JClass containingClass = ((JMember)element).getContainingClass();
            if (containingClass != null) {
               element = containingClass;
               values = this.getAnnotationValues(containingClass);
            }
         }

         if (values.length == 0 && this.inheritFromSuper && element instanceof JClass) {
            JClass[] ifcs = ((JClass)element).getInterfaces();

            for(int i = 0; values.length == 0 && i < ifcs.length; ++i) {
               values = this.getAnnotationValues(ifcs[i]);
            }
         }
      }

      return values;
   }

   public String toString() {
      return this.names[0];
   }

   public String[] getAliases() {
      return this.names;
   }

   protected String constructMessage(String message, PropertyDeclaration declaration, String alias) {
      return message + " Refer annotation " + alias + " on property " + declaration.getName() + " in " + declaration.getBean().getInterfaceName();
   }

   protected void error(String message, PropertyDeclaration declaration, String alias) {
      Context.get().getLog().error(this.constructMessage(message, declaration, alias), declaration.getJElement());
   }

   protected void warning(String message, PropertyDeclaration declaration, String alias) {
      Context.get().getLog().warning(this.constructMessage(message, declaration, alias), declaration.getJElement());
   }

   public boolean isAllowedType(Class type) {
      if (this.allowedTypesArray == null) {
         return true;
      } else {
         if (this.allowedTypesList == null) {
            this.allowedTypesList = new ArrayList();

            for(int count = 0; count < this.allowedTypesArray.length; ++count) {
               Class allowedTypeClass = null;

               try {
                  allowedTypeClass = Class.forName(PROPERTY_TYPE_CLASSNAME + "$" + this.allowedTypesArray[count]);
               } catch (ClassNotFoundException var5) {
                  throw new BeanGenerationException("Unable to initialize Annotation data", var5);
               }

               this.allowedTypesList.add(allowedTypeClass);
            }
         }

         return this.allowedTypesList.contains(type);
      }
   }

   public List validate(PropertyDeclaration declaration, String alias) {
      return new ArrayList();
   }

   public static class UnSetValueAnnotation extends AnnotationDefinition {
      public UnSetValueAnnotation(String[] names, String[] allowedTypes) {
         super(names, allowedTypes);
      }

      public List validate(PropertyDeclaration declaration, String alias) {
         List postGenValidations = super.validate(declaration, alias);
         String legalValues = declaration.getLegalValues();
         if (legalValues == null && declaration.getDefault() != null && declaration.getDerivedDefault() != null) {
            this.error("This annotation can be declared only in the presence of @legalValues annotation and in the abscence of @default and @derivedDefault annotations", declaration, alias);
         } else {
            String unSetValue = declaration.getAnnotationString(this);
            if (unSetValue == null && declaration.getJClass().isPrimitiveType()) {
               this.error("Annotation value should be non-null for primitive type properties", declaration, alias);
            } else if (unSetValue != null && legalValues.indexOf(unSetValue) >= 0) {
               this.error("Annotation value cannot be one of the @legalValues", declaration, alias);
            }
         }

         return postGenValidations;
      }
   }

   public static class LegalZeroLengthStringAnnotation extends LegalStringAnnotation {
      public LegalZeroLengthStringAnnotation(String[] names, String[] allowedTypes) {
         super(names, allowedTypes);
      }

      public PostGenValidation getPostGenValidationType(String propName, String message, String defaultValue) {
         return new PostGenValidation.NonZeroLengthCheck(propName, message, defaultValue);
      }

      public String getPostGenValidationValidationString() {
         return "zero-length";
      }
   }

   public static class LegalNullStringAnnotation extends LegalStringAnnotation {
      public LegalNullStringAnnotation(String[] names, String[] allowedTypes) {
         super(names, allowedTypes);
      }

      public PostGenValidation getPostGenValidationType(String propName, String message, String defaultValue) {
         return new PostGenValidation.NonNullCheck(propName, message, defaultValue);
      }

      public String getPostGenValidationValidationString() {
         return "null";
      }
   }

   public abstract static class LegalStringAnnotation extends AnnotationDefinition {
      public LegalStringAnnotation(String[] names, String[] allowedTypes) {
         super(names, allowedTypes);
      }

      public abstract PostGenValidation getPostGenValidationType(String var1, String var2, String var3);

      public abstract String getPostGenValidationValidationString();

      public List validate(PropertyDeclaration declaration, String alias) {
         List postGenValidations = super.validate(declaration, alias);
         if (declaration.isArray()) {
            this.error("Annotation not allowed on array properties", declaration, alias);
         } else if (declaration.isAnnotationDefined(this) && !declaration.isAnnotationTrue(this) && declaration.getDerivedDefault() == null && !declaration.isRequired()) {
            if (declaration.getDefault() == null) {
               this.error("A false value on this annotation necessitates one of these other annotations with valid values: ' @required, @default or @derivedDefault", declaration, alias);
            } else {
               postGenValidations.add(this.getPostGenValidationType(declaration.getName(), this.constructMessage("The default value for the property  is " + this.getPostGenValidationValidationString() + ". Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-" + this.getPostGenValidationValidationString() + " value on @default annotation.", declaration, alias), declaration.getDefault()));
            }
         }

         return postGenValidations;
      }
   }

   public static class EnumerationAnnotation extends AnnotationDefinition {
      public EnumerationAnnotation(String[] names, String[] allowedTypes) {
         super(names, allowedTypes);
      }

      public List validate(PropertyDeclaration declaration, String alias) {
         List postGenValidations = super.validate(declaration, alias);
         if (declaration.isArray()) {
            this.error("Annotation not allowed on array properties", declaration, alias);
         } else {
            String values = declaration.getAnnotationString(this);
            String defaultValue = declaration.getDefault();
            String derivedDefault = declaration.getDerivedDefault();
            if (values != null && derivedDefault == null && !declaration.isRequired() && !declaration.isAnnotationDefined(PropertyAnnotations.UN_SET_VALUE)) {
               if (defaultValue == null) {
                  this.error("This annotation necessitates one of these other annotations with valid values: @required, @default, @derivedDefault or @unSetValue.", declaration, alias);
               } else if (values.indexOf(defaultValue) < 0) {
                  postGenValidations.add(new PostGenValidation.EnumCheck(declaration.getName(), this.constructMessage("Default value for a property  should be one of the legal values.", declaration, alias), declaration.getType(), declaration.getLegalValues(), declaration.getDefault()));
               }
            }
         }

         return postGenValidations;
      }
   }
}
