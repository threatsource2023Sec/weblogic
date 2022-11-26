package org.glassfish.hk2.xml.internal.alt.papi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import org.glassfish.hk2.utilities.general.GeneralUtilities;
import org.glassfish.hk2.xml.internal.Utilities;
import org.glassfish.hk2.xml.internal.alt.AltClass;
import org.glassfish.hk2.xml.internal.alt.clazz.ClassAltClassImpl;

public class TypeElementAltClassImpl implements AltClass {
   private final TypeElement clazz;
   private final ProcessingEnvironment processingEnv;
   private List methods;
   private List annotations;
   private static final Set POSSIBLE_NO_HANDLE = new HashSet(Arrays.asList("getClass", "hashCode", "equals", "toString", "notify", "notifyAll", "wait"));

   public TypeElementAltClassImpl(TypeElement clazz, ProcessingEnvironment processingEnv) {
      this.clazz = clazz;
      this.processingEnv = processingEnv;
   }

   public String getName() {
      return Utilities.convertNameToString(this.processingEnv.getElementUtils().getBinaryName(this.clazz));
   }

   public String getSimpleName() {
      return Utilities.convertNameToString(this.clazz.getSimpleName());
   }

   public synchronized List getAnnotations() {
      if (this.annotations != null) {
         return this.annotations;
      } else {
         List annoMirrors = this.processingEnv.getElementUtils().getAllAnnotationMirrors(this.clazz);
         ArrayList retVal = new ArrayList(annoMirrors.size());
         Iterator var3 = annoMirrors.iterator();

         while(var3.hasNext()) {
            AnnotationMirror annoMirror = (AnnotationMirror)var3.next();
            AnnotationMirrorAltAnnotationImpl anno = new AnnotationMirrorAltAnnotationImpl(annoMirror, this.processingEnv);
            retVal.add(anno);
         }

         this.annotations = Collections.unmodifiableList(new ArrayList(retVal));
         return this.annotations;
      }
   }

   private boolean isMethodToGenerate(Element element) {
      if (!ElementKind.METHOD.equals(element.getKind())) {
         return false;
      } else {
         ExecutableElement executable = (ExecutableElement)element;
         String methodName = executable.getSimpleName().toString();
         if (!POSSIBLE_NO_HANDLE.contains(methodName)) {
            return true;
         } else {
            List parameters = ((ExecutableType)executable.asType()).getParameterTypes();
            if (("getClass".equals(methodName) || "hashCode".equals(methodName) || "toString".equals(methodName) || "notify".equals(methodName) || "notifyAll".equals(methodName) || "wait".equals(methodName)) && parameters.size() == 0) {
               return false;
            } else {
               TypeMirror param0;
               AltClass ac;
               if ("equals".equals(methodName) && parameters.size() == 1) {
                  param0 = (TypeMirror)parameters.get(0);
                  ac = Utilities.convertTypeMirror(param0, this.processingEnv);
                  if (Object.class.getName().equals(ac.getName())) {
                     return false;
                  }
               }

               if ("wait".equals(methodName) && parameters.size() == 1) {
                  param0 = (TypeMirror)parameters.get(0);
                  ac = Utilities.convertTypeMirror(param0, this.processingEnv);
                  if (ClassAltClassImpl.LONG.equals(ac)) {
                     return false;
                  }
               }

               if ("wait".equals(methodName) && parameters.size() == 2) {
                  param0 = (TypeMirror)parameters.get(0);
                  TypeMirror param1 = (TypeMirror)parameters.get(1);
                  AltClass ac0 = Utilities.convertTypeMirror(param0, this.processingEnv);
                  AltClass ac1 = Utilities.convertTypeMirror(param1, this.processingEnv);
                  if (ClassAltClassImpl.LONG.equals(ac0) && ClassAltClassImpl.INT.equals(ac1)) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   public synchronized List getMethods() {
      if (this.methods != null) {
         return this.methods;
      } else {
         List innerElements = this.processingEnv.getElementUtils().getAllMembers(this.clazz);
         TreeMap reorderByEnclosingClass = new TreeMap();
         String clazzName = this.getName();
         Iterator var4 = innerElements.iterator();

         String enclosingName;
         while(var4.hasNext()) {
            Element innerElementElement = (Element)var4.next();
            if (this.isMethodToGenerate(innerElementElement)) {
               TypeElement enclosingElement = (TypeElement)innerElementElement.getEnclosingElement();
               enclosingName = Utilities.convertNameToString(this.processingEnv.getElementUtils().getBinaryName(enclosingElement));
               List addedList = (List)reorderByEnclosingClass.get(enclosingName);
               if (addedList == null) {
                  addedList = new LinkedList();
                  reorderByEnclosingClass.put(enclosingName, addedList);
               }

               ((List)addedList).add(innerElementElement);
            }
         }

         List innerElementsReordered = new ArrayList(innerElements.size());
         Iterator var10 = reorderByEnclosingClass.entrySet().iterator();

         while(var10.hasNext()) {
            Map.Entry listByEnclosing = (Map.Entry)var10.next();
            enclosingName = (String)listByEnclosing.getKey();
            if (!clazzName.equals(enclosingName)) {
               innerElementsReordered.addAll((Collection)listByEnclosing.getValue());
            }
         }

         List topClass = (List)reorderByEnclosingClass.get(clazzName);
         if (topClass != null) {
            innerElementsReordered.addAll(topClass);
         }

         ArrayList retVal = new ArrayList(innerElementsReordered.size());
         Iterator var14 = innerElementsReordered.iterator();

         while(var14.hasNext()) {
            Element innerElementElement = (Element)var14.next();
            retVal.add(new ElementAltMethodImpl(innerElementElement, this.processingEnv));
         }

         this.methods = Collections.unmodifiableList(retVal);
         return this.methods;
      }
   }

   public AltClass getSuperParameterizedType(AltClass superclass, int paramIndex) {
      if (paramIndex < 0) {
         return null;
      } else {
         String stopName = superclass.getName();

         TypeElement nextClass;
         for(TypeElement currentClass = this.clazz; currentClass != null; currentClass = nextClass) {
            String currentName = Utilities.convertNameToString(this.processingEnv.getElementUtils().getBinaryName(currentClass));
            TypeMirror superMirror = currentClass.getSuperclass();
            if (superMirror == null) {
               return null;
            }

            if (!(superMirror instanceof DeclaredType)) {
               return null;
            }

            DeclaredType superDeclared = (DeclaredType)superMirror;
            nextClass = (TypeElement)superDeclared.asElement();
            String superName = Utilities.convertNameToString(this.processingEnv.getElementUtils().getBinaryName(nextClass));
            if (GeneralUtilities.safeEquals(superName, stopName)) {
               List genericParams = superDeclared.getTypeArguments();
               if (genericParams != null && !genericParams.isEmpty()) {
                  if (paramIndex >= genericParams.size()) {
                     throw new IllegalStateException("Class " + currentName + " which is a superclass of " + stopName + " does not have " + paramIndex + " types.  It only has " + genericParams.size());
                  }

                  TypeMirror tpe = (TypeMirror)genericParams.get(paramIndex);
                  if (!(tpe instanceof DeclaredType)) {
                     return null;
                  }

                  DeclaredType retValDecl = (DeclaredType)tpe;
                  TypeElement retValElement = (TypeElement)retValDecl.asElement();
                  return new TypeElementAltClassImpl(retValElement, this.processingEnv);
               }

               throw new IllegalStateException("Class " + currentName + " which is a superclass of " + stopName + " does is not a parameterized type");
            }
         }

         return null;
      }
   }

   public boolean isInterface() {
      return ElementKind.INTERFACE.equals(this.clazz.getKind());
   }

   public boolean isArray() {
      return false;
   }

   public AltClass getComponentType() {
      return null;
   }

   public int hashCode() {
      return this.getName().hashCode();
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (!(o instanceof AltClass)) {
         return false;
      } else {
         AltClass oac = (AltClass)o;
         return GeneralUtilities.safeEquals(oac.getName(), this.getName());
      }
   }

   public String toString() {
      return "TypeElementAltClassImpl(" + this.clazz.getQualifiedName() + "," + System.identityHashCode(this) + ")";
   }
}
