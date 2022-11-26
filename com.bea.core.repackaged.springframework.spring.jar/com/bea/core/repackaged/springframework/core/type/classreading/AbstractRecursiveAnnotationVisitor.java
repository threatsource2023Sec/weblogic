package com.bea.core.repackaged.springframework.core.type.classreading;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.asm.AnnotationVisitor;
import com.bea.core.repackaged.springframework.asm.Type;
import com.bea.core.repackaged.springframework.core.annotation.AnnotationAttributes;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.security.AccessControlException;

abstract class AbstractRecursiveAnnotationVisitor extends AnnotationVisitor {
   protected final Log logger = LogFactory.getLog(this.getClass());
   protected final AnnotationAttributes attributes;
   @Nullable
   protected final ClassLoader classLoader;

   public AbstractRecursiveAnnotationVisitor(@Nullable ClassLoader classLoader, AnnotationAttributes attributes) {
      super(458752);
      this.classLoader = classLoader;
      this.attributes = attributes;
   }

   public void visit(String attributeName, Object attributeValue) {
      this.attributes.put(attributeName, attributeValue);
   }

   public AnnotationVisitor visitAnnotation(String attributeName, String asmTypeDescriptor) {
      String annotationType = Type.getType(asmTypeDescriptor).getClassName();
      AnnotationAttributes nestedAttributes = new AnnotationAttributes(annotationType, this.classLoader);
      this.attributes.put(attributeName, nestedAttributes);
      return new RecursiveAnnotationAttributesVisitor(annotationType, nestedAttributes, this.classLoader);
   }

   public AnnotationVisitor visitArray(String attributeName) {
      return new RecursiveAnnotationArrayVisitor(attributeName, this.attributes, this.classLoader);
   }

   public void visitEnum(String attributeName, String asmTypeDescriptor, String attributeValue) {
      Object newValue = this.getEnumValue(asmTypeDescriptor, attributeValue);
      this.visit(attributeName, newValue);
   }

   protected Object getEnumValue(String asmTypeDescriptor, String attributeValue) {
      Object valueToUse = attributeValue;

      try {
         Class enumType = ClassUtils.forName(Type.getType(asmTypeDescriptor).getClassName(), this.classLoader);
         Field enumConstant = ReflectionUtils.findField(enumType, attributeValue);
         if (enumConstant != null) {
            ReflectionUtils.makeAccessible(enumConstant);
            valueToUse = enumConstant.get((Object)null);
         }
      } catch (NoClassDefFoundError | ClassNotFoundException var6) {
         this.logger.debug("Failed to classload enum type while reading annotation metadata", var6);
      } catch (AccessControlException | IllegalAccessException var7) {
         this.logger.debug("Could not access enum value while reading annotation metadata", var7);
      }

      return valueToUse;
   }
}
