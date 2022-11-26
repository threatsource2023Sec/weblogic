package org.glassfish.hk2.xml.internal.alt.papi;

import java.util.Collections;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.glassfish.hk2.xml.internal.Utilities;
import org.glassfish.hk2.xml.internal.alt.AltClass;

public class ArrayTypeAltClassImpl implements AltClass {
   private final ArrayType arrayType;
   private final ProcessingEnvironment processingEnv;
   private String name;
   private String simpleName;

   public ArrayTypeAltClassImpl(ArrayType arrayType, ProcessingEnvironment processingEnv) {
      this.arrayType = arrayType;
      this.processingEnv = processingEnv;
   }

   private void calculateNames() {
      StringBuffer sb = new StringBuffer();
      TypeMirror currentMirror = this.arrayType;

      int numBraces;
      for(numBraces = 0; TypeKind.ARRAY.equals(((TypeMirror)currentMirror).getKind()); ++numBraces) {
         sb.append("[");
         currentMirror = ((ArrayType)currentMirror).getComponentType();
      }

      String lSimpleName;
      if (((TypeMirror)currentMirror).getKind().isPrimitive()) {
         if (TypeKind.INT.equals(((TypeMirror)currentMirror).getKind())) {
            sb.append("I");
            lSimpleName = "int";
         } else if (TypeKind.LONG.equals(((TypeMirror)currentMirror).getKind())) {
            sb.append("J");
            lSimpleName = "long";
         } else if (TypeKind.BYTE.equals(((TypeMirror)currentMirror).getKind())) {
            sb.append("B");
            lSimpleName = "byte";
         } else if (TypeKind.BOOLEAN.equals(((TypeMirror)currentMirror).getKind())) {
            sb.append("Z");
            lSimpleName = "boolean";
         } else if (TypeKind.CHAR.equals(((TypeMirror)currentMirror).getKind())) {
            sb.append("C");
            lSimpleName = "char";
         } else if (TypeKind.DOUBLE.equals(((TypeMirror)currentMirror).getKind())) {
            sb.append("D");
            lSimpleName = "double";
         } else if (TypeKind.FLOAT.equals(((TypeMirror)currentMirror).getKind())) {
            sb.append("F");
            lSimpleName = "float";
         } else {
            if (!TypeKind.SHORT.equals(((TypeMirror)currentMirror).getKind())) {
               throw new AssertionError("Unknown primitive type " + ((TypeMirror)currentMirror).getKind() + " for array " + this.arrayType);
            }

            sb.append("S");
            lSimpleName = "short";
         }
      } else {
         if (!TypeKind.DECLARED.equals(((TypeMirror)currentMirror).getKind())) {
            throw new AssertionError("Unknown array type: " + ((TypeMirror)currentMirror).getKind() + " for array " + this.arrayType);
         }

         AltClass ac = Utilities.convertTypeMirror((TypeMirror)currentMirror, this.processingEnv);
         sb.append("L" + ac.getName() + ";");
         lSimpleName = ac.getSimpleName();
      }

      this.name = sb.toString();
      StringBuffer simpleNameSB = new StringBuffer(lSimpleName);

      for(int lcv = 0; lcv < numBraces; ++lcv) {
         simpleNameSB.append("[]");
      }

      this.simpleName = simpleNameSB.toString();
   }

   public synchronized String getName() {
      if (this.name != null) {
         return this.name;
      } else {
         this.calculateNames();
         return this.name;
      }
   }

   public synchronized String getSimpleName() {
      if (this.simpleName != null) {
         return this.simpleName;
      } else {
         this.calculateNames();
         return this.simpleName;
      }
   }

   public List getAnnotations() {
      return Collections.emptyList();
   }

   public List getMethods() {
      return Collections.emptyList();
   }

   public AltClass getSuperParameterizedType(AltClass superclass, int paramIndex) {
      return null;
   }

   public boolean isInterface() {
      return false;
   }

   public boolean isArray() {
      return true;
   }

   public AltClass getComponentType() {
      TypeMirror compTypeAsMirror = this.arrayType.getComponentType();
      return Utilities.convertTypeMirror(compTypeAsMirror, this.processingEnv);
   }
}
