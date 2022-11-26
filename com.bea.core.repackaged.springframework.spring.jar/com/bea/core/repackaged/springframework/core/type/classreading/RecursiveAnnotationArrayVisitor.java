package com.bea.core.repackaged.springframework.core.type.classreading;

import com.bea.core.repackaged.springframework.asm.AnnotationVisitor;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

class RecursiveAnnotationArrayVisitor extends AbstractRecursiveAnnotationVisitor {
   private final String attributeName;
   private final List allNestedAttributes = new ArrayList();

   public RecursiveAnnotationArrayVisitor(String attributeName, AnnotationAttributes attributes, @Nullable ClassLoader classLoader) {
      super(classLoader, attributes);
      this.attributeName = attributeName;
   }

   public void visit(String attributeName, Object attributeValue) {
      Object existingValue = this.attributes.get(this.attributeName);
      Object[] newValue;
      if (existingValue != null) {
         newValue = ObjectUtils.addObjectToArray((Object[])((Object[])existingValue), attributeValue);
      } else {
         Class arrayClass = attributeValue.getClass();
         if (Enum.class.isAssignableFrom(arrayClass)) {
            while(arrayClass.getSuperclass() != null && !arrayClass.isEnum()) {
               arrayClass = arrayClass.getSuperclass();
            }
         }

         Object[] newArray = (Object[])((Object[])Array.newInstance(arrayClass, 1));
         newArray[0] = attributeValue;
         newValue = newArray;
      }

      this.attributes.put(this.attributeName, newValue);
   }

   public AnnotationVisitor visitAnnotation(String attributeName, String asmTypeDescriptor) {
      String annotationType = Type.getType(asmTypeDescriptor).getClassName();
      AnnotationAttributes nestedAttributes = new AnnotationAttributes(annotationType, this.classLoader);
      this.allNestedAttributes.add(nestedAttributes);
      return new RecursiveAnnotationAttributesVisitor(annotationType, nestedAttributes, this.classLoader);
   }

   public void visitEnd() {
      if (!this.allNestedAttributes.isEmpty()) {
         this.attributes.put(this.attributeName, this.allNestedAttributes.toArray(new AnnotationAttributes[0]));
      } else if (!this.attributes.containsKey(this.attributeName)) {
         Class annotationType = this.attributes.annotationType();
         if (annotationType != null) {
            try {
               Class attributeType = annotationType.getMethod(this.attributeName).getReturnType();
               if (attributeType.isArray()) {
                  Class elementType = attributeType.getComponentType();
                  if (elementType.isAnnotation()) {
                     elementType = AnnotationAttributes.class;
                  }

                  this.attributes.put(this.attributeName, Array.newInstance(elementType, 0));
               }
            } catch (NoSuchMethodException var4) {
            }
         }
      }

   }
}
