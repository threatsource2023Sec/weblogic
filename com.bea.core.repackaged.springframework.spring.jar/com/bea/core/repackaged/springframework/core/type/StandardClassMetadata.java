package com.bea.core.repackaged.springframework.core.type;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedHashSet;

public class StandardClassMetadata implements ClassMetadata {
   private final Class introspectedClass;

   public StandardClassMetadata(Class introspectedClass) {
      Assert.notNull(introspectedClass, (String)"Class must not be null");
      this.introspectedClass = introspectedClass;
   }

   public final Class getIntrospectedClass() {
      return this.introspectedClass;
   }

   public String getClassName() {
      return this.introspectedClass.getName();
   }

   public boolean isInterface() {
      return this.introspectedClass.isInterface();
   }

   public boolean isAnnotation() {
      return this.introspectedClass.isAnnotation();
   }

   public boolean isAbstract() {
      return Modifier.isAbstract(this.introspectedClass.getModifiers());
   }

   public boolean isConcrete() {
      return !this.isInterface() && !this.isAbstract();
   }

   public boolean isFinal() {
      return Modifier.isFinal(this.introspectedClass.getModifiers());
   }

   public boolean isIndependent() {
      return !this.hasEnclosingClass() || this.introspectedClass.getDeclaringClass() != null && Modifier.isStatic(this.introspectedClass.getModifiers());
   }

   public boolean hasEnclosingClass() {
      return this.introspectedClass.getEnclosingClass() != null;
   }

   @Nullable
   public String getEnclosingClassName() {
      Class enclosingClass = this.introspectedClass.getEnclosingClass();
      return enclosingClass != null ? enclosingClass.getName() : null;
   }

   public boolean hasSuperClass() {
      return this.introspectedClass.getSuperclass() != null;
   }

   @Nullable
   public String getSuperClassName() {
      Class superClass = this.introspectedClass.getSuperclass();
      return superClass != null ? superClass.getName() : null;
   }

   public String[] getInterfaceNames() {
      Class[] ifcs = this.introspectedClass.getInterfaces();
      String[] ifcNames = new String[ifcs.length];

      for(int i = 0; i < ifcs.length; ++i) {
         ifcNames[i] = ifcs[i].getName();
      }

      return ifcNames;
   }

   public String[] getMemberClassNames() {
      LinkedHashSet memberClassNames = new LinkedHashSet(4);
      Class[] var2 = this.introspectedClass.getDeclaredClasses();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class nestedClass = var2[var4];
         memberClassNames.add(nestedClass.getName());
      }

      return StringUtils.toStringArray((Collection)memberClassNames);
   }
}
