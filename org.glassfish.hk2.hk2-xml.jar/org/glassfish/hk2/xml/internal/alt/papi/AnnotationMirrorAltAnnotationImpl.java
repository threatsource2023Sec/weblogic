package org.glassfish.hk2.xml.internal.alt.papi;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.glassfish.hk2.xml.internal.Utilities;
import org.glassfish.hk2.xml.internal.alt.AltAnnotation;
import org.glassfish.hk2.xml.internal.alt.AltClass;
import org.glassfish.hk2.xml.internal.alt.AltEnum;

public class AnnotationMirrorAltAnnotationImpl implements AltAnnotation {
   private final AnnotationMirror annotation;
   private final ProcessingEnvironment processingEnv;
   private String type;
   private Map values;

   public AnnotationMirrorAltAnnotationImpl(AnnotationMirror annotation, ProcessingEnvironment processingEnv) {
      this.annotation = annotation;
      this.processingEnv = processingEnv;
   }

   public synchronized String annotationType() {
      if (this.type != null) {
         return this.type;
      } else {
         DeclaredType dt = this.annotation.getAnnotationType();
         TypeElement clazzType = (TypeElement)dt.asElement();
         this.type = Utilities.convertNameToString(clazzType.getQualifiedName());
         return this.type;
      }
   }

   public String getStringValue(String methodName) {
      this.getAnnotationValues();
      return (String)this.values.get(methodName);
   }

   public boolean getBooleanValue(String methodName) {
      this.getAnnotationValues();
      return (Boolean)this.values.get(methodName);
   }

   public synchronized String[] getStringArrayValue(String methodName) {
      this.getAnnotationValues();
      Object retVal = this.values.get(methodName);
      return retVal != null && retVal instanceof String[] ? (String[])((String[])this.values.get(methodName)) : null;
   }

   public AltAnnotation[] getAnnotationArrayValue(String methodName) {
      this.getAnnotationValues();
      return (AltAnnotation[])((AltAnnotation[])this.values.get(methodName));
   }

   public AltClass getClassValue(String methodName) {
      this.getAnnotationValues();
      return (AltClass)this.values.get(methodName);
   }

   public synchronized Map getAnnotationValues() {
      if (this.values != null) {
         return this.values;
      } else {
         Map rawValues = this.processingEnv.getElementUtils().getElementValuesWithDefaults(this.annotation);
         Map retVal = new TreeMap();

         String key;
         Object value;
         for(Iterator var3 = rawValues.entrySet().iterator(); var3.hasNext(); retVal.put(key, value)) {
            Map.Entry entry = (Map.Entry)var3.next();
            ExecutableElement annoMethod = (ExecutableElement)entry.getKey();
            AnnotationValue annoValue = (AnnotationValue)entry.getValue();
            key = Utilities.convertNameToString(annoMethod.getSimpleName());
            value = annoValue.getValue();
            if (value instanceof TypeMirror) {
               value = Utilities.convertTypeMirror((TypeMirror)value, this.processingEnv);
            } else if (value instanceof VariableElement) {
               VariableElement variable = (VariableElement)value;
               TypeElement enclosing = (TypeElement)variable.getEnclosingElement();
               String annoClassName = Utilities.convertNameToString(enclosing.getQualifiedName());
               String annoVal = Utilities.convertNameToString(variable.getSimpleName());
               value = new StringAltEnumImpl(annoClassName, annoVal);
            } else {
               if (value instanceof AnnotationMirror) {
                  throw new AssertionError("The annotation " + this.annotation + " key " + key + " has unimplemented type AnnotationMirror");
               }

               if (value instanceof List) {
                  ArrayType returnType = (ArrayType)annoMethod.getReturnType();
                  TypeMirror arrayTypeMirror = returnType.getComponentType();
                  TypeKind arrayTypeKind = arrayTypeMirror.getKind();
                  List array = (List)value;
                  int lcv;
                  Iterator var15;
                  AnnotationValue item;
                  if (TypeKind.INT.equals(arrayTypeMirror.getKind())) {
                     int[] iValue = new int[array.size()];
                     lcv = 0;

                     for(var15 = array.iterator(); var15.hasNext(); iValue[lcv++] = (Integer)item.getValue()) {
                        item = (AnnotationValue)var15.next();
                     }

                     value = iValue;
                  } else if (TypeKind.DECLARED.equals(arrayTypeMirror.getKind())) {
                     AltClass[] cValue = new AltClass[array.size()];
                     AltEnum[] eValue = new AltEnum[array.size()];
                     String[] sValue = new String[array.size()];
                     AltAnnotation[] aValue = new AltAnnotation[array.size()];
                     boolean isClass = true;
                     boolean isEnum = true;
                     boolean isAnnos = false;
                     int lcv = 0;
                     Iterator var21 = array.iterator();

                     while(var21.hasNext()) {
                        AnnotationValue item = (AnnotationValue)var21.next();
                        Object itemValue = item.getValue();
                        if (itemValue instanceof TypeMirror) {
                           isClass = true;
                           isEnum = false;
                           isAnnos = false;
                           cValue[lcv++] = Utilities.convertTypeMirror((TypeMirror)itemValue, this.processingEnv);
                        } else if (itemValue instanceof VariableElement) {
                           isClass = false;
                           isEnum = true;
                           isAnnos = false;
                           VariableElement variable = (VariableElement)itemValue;
                           TypeElement enclosing = (TypeElement)variable.getEnclosingElement();
                           String annoClassName = Utilities.convertNameToString(enclosing.getQualifiedName());
                           String annoVal = Utilities.convertNameToString(variable.getSimpleName());
                           eValue[lcv++] = new StringAltEnumImpl(annoClassName, annoVal);
                        } else if (itemValue instanceof String) {
                           isClass = false;
                           isEnum = false;
                           isAnnos = false;
                           sValue[lcv++] = (String)itemValue;
                        } else {
                           if (itemValue instanceof List) {
                              throw new AssertionError("Unimplemented declared List type in " + this);
                           }

                           if (!(itemValue instanceof AnnotationMirror)) {
                              throw new AssertionError("Unknown declared type: " + itemValue.getClass().getName());
                           }

                           isClass = false;
                           isEnum = false;
                           isAnnos = true;
                           aValue[lcv++] = new AnnotationMirrorAltAnnotationImpl((AnnotationMirror)itemValue, this.processingEnv);
                        }
                     }

                     if (isClass) {
                        value = cValue;
                     } else if (isEnum) {
                        value = eValue;
                     } else if (isAnnos) {
                        value = aValue;
                     } else {
                        value = sValue;
                     }
                  } else if (TypeKind.LONG.equals(arrayTypeMirror.getKind())) {
                     long[] iValue = new long[array.size()];
                     lcv = 0;

                     for(var15 = array.iterator(); var15.hasNext(); iValue[lcv++] = (Long)item.getValue()) {
                        item = (AnnotationValue)var15.next();
                     }

                     value = iValue;
                  } else if (TypeKind.SHORT.equals(arrayTypeMirror.getKind())) {
                     short[] iValue = new short[array.size()];
                     lcv = 0;

                     for(var15 = array.iterator(); var15.hasNext(); iValue[lcv++] = (Short)item.getValue()) {
                        item = (AnnotationValue)var15.next();
                     }

                     value = iValue;
                  } else if (TypeKind.CHAR.equals(arrayTypeMirror.getKind())) {
                     char[] iValue = new char[array.size()];
                     lcv = 0;

                     for(var15 = array.iterator(); var15.hasNext(); iValue[lcv++] = (Character)item.getValue()) {
                        item = (AnnotationValue)var15.next();
                     }

                     value = iValue;
                  } else if (TypeKind.FLOAT.equals(arrayTypeMirror.getKind())) {
                     float[] iValue = new float[array.size()];
                     lcv = 0;

                     for(var15 = array.iterator(); var15.hasNext(); iValue[lcv++] = (Float)item.getValue()) {
                        item = (AnnotationValue)var15.next();
                     }

                     value = iValue;
                  } else if (TypeKind.DOUBLE.equals(arrayTypeMirror.getKind())) {
                     double[] iValue = new double[array.size()];
                     lcv = 0;

                     for(var15 = array.iterator(); var15.hasNext(); iValue[lcv++] = (Double)item.getValue()) {
                        item = (AnnotationValue)var15.next();
                     }

                     value = iValue;
                  } else if (TypeKind.BOOLEAN.equals(arrayTypeMirror.getKind())) {
                     boolean[] iValue = new boolean[array.size()];
                     lcv = 0;

                     for(var15 = array.iterator(); var15.hasNext(); iValue[lcv++] = (Boolean)item.getValue()) {
                        item = (AnnotationValue)var15.next();
                     }

                     value = iValue;
                  } else {
                     if (!TypeKind.BYTE.equals(arrayTypeMirror.getKind())) {
                        throw new AssertionError("Array type " + arrayTypeKind + " is not implemented");
                     }

                     byte[] iValue = new byte[array.size()];
                     lcv = 0;

                     for(var15 = array.iterator(); var15.hasNext(); iValue[lcv++] = (Byte)item.getValue()) {
                        item = (AnnotationValue)var15.next();
                     }

                     value = iValue;
                  }
               }
            }
         }

         this.values = Collections.unmodifiableMap(retVal);
         return this.values;
      }
   }

   public String toString() {
      return "AnnotationMirrorAltAnnotationImpl(" + this.annotationType() + ")";
   }
}
