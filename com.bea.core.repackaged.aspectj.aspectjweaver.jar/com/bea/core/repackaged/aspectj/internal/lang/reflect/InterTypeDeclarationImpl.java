package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.AjType;
import com.bea.core.repackaged.aspectj.lang.reflect.InterTypeDeclaration;

public class InterTypeDeclarationImpl implements InterTypeDeclaration {
   private AjType declaringType;
   protected String targetTypeName;
   private AjType targetType;
   private int modifiers;

   public InterTypeDeclarationImpl(AjType decType, String target, int mods) {
      this.declaringType = decType;
      this.targetTypeName = target;
      this.modifiers = mods;

      try {
         this.targetType = (AjType)StringToType.stringToType(target, decType.getJavaClass());
      } catch (ClassNotFoundException var5) {
      }

   }

   public InterTypeDeclarationImpl(AjType decType, AjType targetType, int mods) {
      this.declaringType = decType;
      this.targetType = targetType;
      this.targetTypeName = targetType.getName();
      this.modifiers = mods;
   }

   public AjType getDeclaringType() {
      return this.declaringType;
   }

   public AjType getTargetType() throws ClassNotFoundException {
      if (this.targetType == null) {
         throw new ClassNotFoundException(this.targetTypeName);
      } else {
         return this.targetType;
      }
   }

   public int getModifiers() {
      return this.modifiers;
   }
}
