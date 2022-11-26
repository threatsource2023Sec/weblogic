package weblogic.connector.configuration.validation;

import java.beans.IntrospectionException;
import java.lang.annotation.Annotation;
import java.util.Set;
import javax.annotation.ManagedBean;
import javax.decorator.Decorator;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.NormalScope;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;
import javax.enterprise.inject.Stereotype;
import javax.enterprise.inject.Typed;
import javax.inject.Named;
import javax.inject.Qualifier;
import weblogic.connector.utils.ConnectorAPContext;
import weblogic.connector.utils.IntrospectorManager;
import weblogic.connector.utils.TypeUtils;

abstract class DefaultRAValidator extends DefaultValidator {
   private final ClassLoader rarClassloader;
   private final ConnectorAPContext navigator;

   protected final ClassLoader getClassLoader() {
      return this.rarClassloader;
   }

   DefaultRAValidator(ValidationContext context) {
      super(context);
      this.rarClassloader = context.getRarClassloader();
      this.navigator = context.getConnectorAPContext();
   }

   protected final Class checkClass(String stdClassName, String source, String classNameToInspect, String[] mustImplement, String[] shouldImplement, boolean shouldBeJavaBean, Boolean overrideEqualsAndHashCode) {
      String subComponent = "General";
      String key = "General";
      boolean overridesCorrect = true;
      Class theClass = null;
      if (classNameToInspect != null && classNameToInspect.length() != 0) {
         try {
            theClass = Class.forName(classNameToInspect, false, this.getClassLoader());
         } catch (ClassNotFoundException var14) {
            this.error(subComponent, key, fmt.CLASS_NOT_FOUND(source, stdClassName, classNameToInspect));
            return theClass;
         } catch (Throwable var15) {
            this.error(subComponent, key, fmt.COULDNT_LOAD_CLASS(source, stdClassName, classNameToInspect, var15.toString()));
            return theClass;
         }

         Set interfacesNameSet = TypeUtils.getInterfacesNameSet(theClass);
         if (mustImplement != null) {
            this.validateImplements(source, true, stdClassName, classNameToInspect, interfacesNameSet, mustImplement);
         }

         if (shouldImplement != null) {
            this.validateImplements(source, false, stdClassName, classNameToInspect, interfacesNameSet, shouldImplement);
         }

         if (shouldBeJavaBean) {
            this.checkJavaBeanCompliance(source, stdClassName, theClass);
         }

         if (overrideEqualsAndHashCode != null) {
            boolean mustOverride = overrideEqualsAndHashCode;
            overridesCorrect = TypeUtils.checkOverridesHashCodeEquals(theClass, mustOverride);
            if (!overridesCorrect) {
               if (mustOverride) {
                  this.error(subComponent, key, fmt.MUST_OVERRIDE(source, stdClassName, classNameToInspect));
               } else {
                  this.warning(fmt.SHOULD_NOT_OVERRIDE(source, stdClassName, classNameToInspect));
               }
            }
         }

         return theClass;
      } else {
         this.error(subComponent, key, fmt.ZERO_LENGTH_NAME(source, stdClassName, classNameToInspect));
         return theClass;
      }
   }

   protected final Class checkClass(String stdClassName, String source, Class classToInspect, String[] shouldImplement) {
      Set interfacesNameSet = TypeUtils.getInterfacesNameSet(classToInspect);
      if (shouldImplement != null) {
         this.validateImplements(source, false, stdClassName, classToInspect.getName(), interfacesNameSet, shouldImplement);
      }

      return classToInspect;
   }

   private final void validateImplements(String source, boolean mustImplement, String stdClassName, String classBeingInspected, Set interfaces, String[] interfacesToImplement) {
      assert interfacesToImplement != null;

      String[] var7 = interfacesToImplement;
      int var8 = interfacesToImplement.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         String intfToImplement = var7[var9];
         if (!interfaces.contains(intfToImplement)) {
            this.reportMissingInterface(source, mustImplement, stdClassName, classBeingInspected, intfToImplement);
         }
      }

   }

   protected final void reportMissingInterface(String source, boolean mustImplement, String stdClassName, String classBeingInspected, String missingInterface) {
      if (mustImplement) {
         this.error("General", "General", fmt.MUST_IMPLEMENT_INTERFACE(source, stdClassName, classBeingInspected, missingInterface));
      } else {
         this.warning(fmt.SHOULD_IMPLEMENT_INTERFACE(source, stdClassName, classBeingInspected, missingInterface));
      }

   }

   protected final void checkJavaBeanCompliance(String source, String stdClassName, Class theClass) {
      assert theClass != null;

      try {
         IntrospectorManager.getBeanInfo(theClass);
      } catch (IntrospectionException var5) {
         this.warning(fmt.NOT_A_JAVA_BEAN(source, stdClassName, theClass.getName(), var5.getMessage()));
      }

   }

   protected final Class checkClass(String stdClassName, String source, String classNameToInspect, String[] mustImplement, String[] shouldImplement, boolean shouldBeJavaBean) {
      return this.checkClass(stdClassName, source, classNameToInspect, mustImplement, shouldImplement, shouldBeJavaBean, (Boolean)null);
   }

   protected boolean isReadFromAnnotation(String type, String... name) {
      return this.navigator != null ? this.navigator.fromAnnotation(type, name) : false;
   }

   protected void validateAnnotations(Class aClass, String elementName, String sourceFile) {
      if (aClass != null) {
         this.validateOneAnnotation(aClass, ManagedBean.class, elementName, sourceFile);
         this.validateOneAnnotation(aClass, ApplicationScoped.class, elementName, sourceFile);
         this.validateOneAnnotation(aClass, ConversationScoped.class, elementName, sourceFile);
         this.validateOneAnnotation(aClass, Dependent.class, elementName, sourceFile);
         this.validateOneAnnotation(aClass, RequestScoped.class, elementName, sourceFile);
         this.validateOneAnnotation(aClass, SessionScoped.class, elementName, sourceFile);
         this.checkAnnotationsForAnnotationTypes(aClass, elementName, sourceFile);
         this.validateOneAnnotation(aClass, Named.class, elementName, sourceFile);
         this.validateOneAnnotation(aClass, Alternative.class, elementName, sourceFile);
         this.validateOneAnnotation(aClass, Specializes.class, elementName, sourceFile);
         this.validateOneAnnotation(aClass, Typed.class, elementName, sourceFile);
         this.validateOneAnnotation(aClass, Decorator.class, elementName, sourceFile);
      }
   }

   protected void checkAnnotationsForAnnotationTypes(Class aClass, String elementName, String sourceFile) {
      Annotation[] annotations = aClass.getAnnotations();
      Annotation[] var5 = annotations;
      int var6 = annotations.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Annotation oneAnno = var5[var7];
         this.checkOneAnnotationForAnnotationType(aClass, oneAnno, elementName, sourceFile);
      }

   }

   private void checkOneAnnotationForAnnotationType(Class outerClass, Annotation annotation, String elementName, String sourceFile) {
      Class aClass = annotation.annotationType();
      if (aClass.isAnnotationPresent(Qualifier.class) && !aClass.equals(Named.class)) {
         this.error("General", "General", fmt.INVALID_ANNOTATION_TYPE(sourceFile, elementName, outerClass.getName(), "Qualifier"));
      }

      if (aClass.isAnnotationPresent(Stereotype.class) && !aClass.equals(Decorator.class)) {
         this.error("General", "General", fmt.INVALID_ANNOTATION_TYPE(sourceFile, elementName, outerClass.getName(), "Stereotype"));
      }

      if (aClass.isAnnotationPresent(NormalScope.class)) {
         if (aClass.equals(SessionScoped.class) || aClass.equals(ConversationScoped.class) || aClass.equals(RequestScoped.class) || aClass.equals(ApplicationScoped.class)) {
            return;
         }

         this.error("General", "General", fmt.INVALID_ANNOTATION_TYPE(sourceFile, elementName, outerClass.getName(), "NormalScope"));
      }

   }

   private void validateOneAnnotation(Class aClass, Class annotation, String elementName, String sourceFile) {
      if (aClass.isAnnotationPresent(annotation)) {
         this.error("General", "General", fmt.INVALID_ANNOTATION(sourceFile, elementName, aClass.getName(), annotation.getName()));
      }

   }
}
