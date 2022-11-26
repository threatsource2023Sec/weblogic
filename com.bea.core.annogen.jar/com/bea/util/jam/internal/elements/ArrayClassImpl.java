package com.bea.util.jam.internal.elements;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JamClassLoader;
import java.io.StringWriter;

public final class ArrayClassImpl extends BuiltinClassImpl {
   private int mDimensions;
   private JClass mComponentType;

   public static JClass createClassForFD(String arrayFD, JamClassLoader loader) {
      if (!arrayFD.startsWith("[")) {
         throw new IllegalArgumentException("must be an array type fd: " + arrayFD);
      } else {
         int dims;
         if (arrayFD.endsWith(";")) {
            dims = arrayFD.indexOf("L");
            if (dims != -1 && dims < arrayFD.length() - 2) {
               String componentType = arrayFD.substring(dims + 1, arrayFD.length() - 1);
               return new ArrayClassImpl(loader.loadClass(componentType), dims);
            } else {
               throw new IllegalArgumentException("array type field descriptor '" + arrayFD + "' is malformed");
            }
         } else {
            dims = arrayFD.lastIndexOf("[") + 1;
            String compFd = arrayFD.substring(dims, dims + 1);
            JClass primType = loader.loadClass(compFd);
            if (primType == null) {
               throw new IllegalArgumentException("array type field descriptor '" + arrayFD + "' is malformed");
            } else {
               return new ArrayClassImpl(primType, dims);
            }
         }
      }
   }

   public static String normalizeArrayName(String declaration) {
      if (declaration.startsWith("[")) {
         return declaration;
      } else {
         if (declaration.endsWith("]")) {
            int bracket = declaration.indexOf(91);
            if (bracket != -1) {
               String typeName = declaration.substring(0, bracket);
               String fd = PrimitiveClassImpl.getPrimitiveClassForName(typeName);
               if (fd == null) {
                  fd = 'L' + typeName + ';';
               }

               StringWriter out = new StringWriter();

               do {
                  out.write(91);
                  bracket = declaration.indexOf(91, bracket + 1);
               } while(bracket != -1);

               out.write(fd);
               return out.toString();
            }
         }

         throw new IllegalArgumentException("'" + declaration + "' does not name an array");
      }
   }

   private ArrayClassImpl(JClass componentType, int dimensions) {
      super(((ElementImpl)componentType).getContext());
      if (dimensions < 1) {
         throw new IllegalArgumentException("dimensions=" + dimensions);
      } else if (componentType == null) {
         throw new IllegalArgumentException("null componentType");
      } else {
         this.mComponentType = componentType;
         this.mDimensions = dimensions;
      }
   }

   public String getSimpleName() {
      String out = this.getQualifiedName();
      int lastDot = out.lastIndexOf(46);
      return lastDot == -1 ? out : out.substring(lastDot + 1);
   }

   public String getQualifiedName() {
      StringWriter out = new StringWriter();
      out.write(this.mComponentType.getQualifiedName());

      for(int i = 0; i < this.mDimensions; ++i) {
         out.write("[]");
      }

      return out.toString();
   }

   public boolean isArrayType() {
      return true;
   }

   public JClass getArrayComponentType() {
      return this.mComponentType;
   }

   public int getArrayDimensions() {
      return this.mDimensions;
   }

   public JClass getSuperclass() {
      return this.getClassLoader().loadClass("java.lang.Object");
   }

   public boolean isAssignableFrom(JClass c) {
      return c.isArrayType() && c.getArrayDimensions() == this.mDimensions && this.mComponentType.isAssignableFrom(c.getArrayComponentType());
   }

   public String getFieldDescriptor() {
      StringWriter out = new StringWriter();

      for(int i = 0; i < this.mDimensions; ++i) {
         out.write("[");
      }

      if (this.mComponentType.isPrimitiveType()) {
         out.write(this.mComponentType.getFieldDescriptor());
      } else {
         out.write("L");
         out.write(this.mComponentType.getQualifiedName());
         out.write(";");
      }

      return out.toString();
   }
}
