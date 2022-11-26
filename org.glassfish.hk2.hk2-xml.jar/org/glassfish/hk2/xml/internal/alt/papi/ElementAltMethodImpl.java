package org.glassfish.hk2.xml.internal.alt.papi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.glassfish.hk2.xml.internal.Utilities;
import org.glassfish.hk2.xml.internal.alt.AltAnnotation;
import org.glassfish.hk2.xml.internal.alt.AltClass;
import org.glassfish.hk2.xml.internal.alt.AltMethod;
import org.glassfish.hk2.xml.internal.alt.MethodInformationI;
import org.glassfish.hk2.xml.internal.alt.clazz.ClassAltClassImpl;

public class ElementAltMethodImpl implements AltMethod {
   private final ExecutableElement method;
   private final ProcessingEnvironment processingEnv;
   private List parameters;
   private AltClass returnType;
   private Map annotations;
   private MethodInformationI methodInformation;

   public ElementAltMethodImpl(Element method, ProcessingEnvironment processingEnv) {
      this.method = (ExecutableElement)method;
      this.processingEnv = processingEnv;
   }

   public String getName() {
      return Utilities.convertNameToString(this.method.getSimpleName());
   }

   public synchronized AltClass getReturnType() {
      if (this.returnType != null) {
         return this.returnType;
      } else {
         ExecutableType executable = (ExecutableType)this.method.asType();
         TypeMirror returnMirror = executable.getReturnType();
         AltClass retVal = Utilities.convertTypeMirror(returnMirror, this.processingEnv);
         this.returnType = retVal;
         return this.returnType;
      }
   }

   public synchronized List getParameterTypes() {
      if (this.parameters != null) {
         return this.parameters;
      } else {
         ExecutableType executable = (ExecutableType)this.method.asType();
         List paramMirrors = executable.getParameterTypes();
         List retVal = new ArrayList(paramMirrors.size());
         Iterator var4 = paramMirrors.iterator();

         while(var4.hasNext()) {
            TypeMirror paramMirror = (TypeMirror)var4.next();
            retVal.add(Utilities.convertTypeMirror(paramMirror, this.processingEnv));
         }

         this.parameters = Collections.unmodifiableList(retVal);
         return this.parameters;
      }
   }

   public AltClass getFirstTypeArgument() {
      TypeMirror typeMirror = this.method.getReturnType();
      if (!(typeMirror instanceof DeclaredType)) {
         return null;
      } else {
         DeclaredType declaredReturn = (DeclaredType)typeMirror;
         List types = declaredReturn.getTypeArguments();
         if (types != null && types.size() >= 1) {
            TypeMirror firstTypeMirror = (TypeMirror)types.get(0);
            return Utilities.convertTypeMirror(firstTypeMirror, this.processingEnv);
         } else {
            return null;
         }
      }
   }

   public AltClass getFirstTypeArgumentOfParameter(int index) {
      VariableElement ve = (VariableElement)this.method.getParameters().get(index);
      TypeMirror tm = ve.asType();
      if (!TypeKind.DECLARED.equals(tm.getKind())) {
         return ClassAltClassImpl.OBJECT;
      } else {
         DeclaredType dt = (DeclaredType)tm;
         List typeParams = dt.getTypeArguments();
         if (typeParams != null && typeParams.size() >= 1) {
            TypeMirror firstTypeParam = (TypeMirror)typeParams.get(0);
            if (!TypeKind.DECLARED.equals(firstTypeParam.getKind())) {
               return ClassAltClassImpl.OBJECT;
            } else {
               Element ele = ((DeclaredType)firstTypeParam).asElement();
               return (AltClass)(!(ele instanceof TypeElement) ? ClassAltClassImpl.OBJECT : new TypeElementAltClassImpl((TypeElement)ele, this.processingEnv));
            }
         } else {
            return ClassAltClassImpl.OBJECT;
         }
      }
   }

   public synchronized AltAnnotation getAnnotation(String annotation) {
      if (this.annotations == null) {
         this.getAnnotations();
      }

      return (AltAnnotation)this.annotations.get(annotation);
   }

   public synchronized List getAnnotations() {
      if (this.annotations != null) {
         return Collections.unmodifiableList(new ArrayList(this.annotations.values()));
      } else {
         Map retVal = new LinkedHashMap();
         Iterator var2 = this.method.getAnnotationMirrors().iterator();

         while(var2.hasNext()) {
            AnnotationMirror annoMirror = (AnnotationMirror)var2.next();
            AnnotationMirrorAltAnnotationImpl addMe = new AnnotationMirrorAltAnnotationImpl(annoMirror, this.processingEnv);
            retVal.put(addMe.annotationType(), addMe);
         }

         this.annotations = Collections.unmodifiableMap(retVal);
         return Collections.unmodifiableList(new ArrayList(this.annotations.values()));
      }
   }

   public void setMethodInformation(MethodInformationI methodInfo) {
      this.methodInformation = methodInfo;
   }

   public MethodInformationI getMethodInformation() {
      return this.methodInformation;
   }

   public boolean isVarArgs() {
      return this.method.isVarArgs();
   }

   public String toString() {
      return "ElementAltMethodImpl(" + this.method + ")";
   }
}
