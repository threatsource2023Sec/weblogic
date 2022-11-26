package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.AjType;
import com.bea.core.repackaged.aspectj.lang.reflect.AjTypeSystem;
import com.bea.core.repackaged.aspectj.lang.reflect.InterTypeFieldDeclaration;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public class InterTypeFieldDeclarationImpl extends InterTypeDeclarationImpl implements InterTypeFieldDeclaration {
   private String name;
   private AjType type;
   private Type genericType;

   public InterTypeFieldDeclarationImpl(AjType decType, String target, int mods, String name, AjType type, Type genericType) {
      super(decType, target, mods);
      this.name = name;
      this.type = type;
      this.genericType = genericType;
   }

   public InterTypeFieldDeclarationImpl(AjType decType, AjType targetType, Field base) {
      super(decType, targetType, base.getModifiers());
      this.name = base.getName();
      this.type = AjTypeSystem.getAjType(base.getType());
      Type gt = base.getGenericType();
      if (gt instanceof Class) {
         this.genericType = AjTypeSystem.getAjType((Class)gt);
      } else {
         this.genericType = gt;
      }

   }

   public String getName() {
      return this.name;
   }

   public AjType getType() {
      return this.type;
   }

   public Type getGenericType() {
      return this.genericType;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append(Modifier.toString(this.getModifiers()));
      sb.append(" ");
      sb.append(this.getType().toString());
      sb.append(" ");
      sb.append(this.targetTypeName);
      sb.append(".");
      sb.append(this.getName());
      return sb.toString();
   }
}
