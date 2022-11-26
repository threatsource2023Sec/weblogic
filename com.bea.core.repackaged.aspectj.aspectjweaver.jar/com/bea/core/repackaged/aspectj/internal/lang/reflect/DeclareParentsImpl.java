package com.bea.core.repackaged.aspectj.internal.lang.reflect;

import com.bea.core.repackaged.aspectj.lang.reflect.AjType;
import com.bea.core.repackaged.aspectj.lang.reflect.DeclareParents;
import com.bea.core.repackaged.aspectj.lang.reflect.TypePattern;
import java.lang.reflect.Type;

public class DeclareParentsImpl implements DeclareParents {
   private AjType declaringType;
   private TypePattern targetTypesPattern;
   private Type[] parents;
   private String parentsString;
   private String firstMissingTypeName;
   private boolean isExtends;
   private boolean parentsError = false;

   public DeclareParentsImpl(String targets, String parentsAsString, boolean isExtends, AjType declaring) {
      this.targetTypesPattern = new TypePatternImpl(targets);
      this.isExtends = isExtends;
      this.declaringType = declaring;
      this.parentsString = parentsAsString;

      try {
         this.parents = StringToType.commaSeparatedListToTypeArray(parentsAsString, declaring.getJavaClass());
      } catch (ClassNotFoundException var6) {
         this.parentsError = true;
         this.firstMissingTypeName = var6.getMessage();
      }

   }

   public AjType getDeclaringType() {
      return this.declaringType;
   }

   public TypePattern getTargetTypesPattern() {
      return this.targetTypesPattern;
   }

   public boolean isExtends() {
      return this.isExtends;
   }

   public boolean isImplements() {
      return !this.isExtends;
   }

   public Type[] getParentTypes() throws ClassNotFoundException {
      if (this.parentsError) {
         throw new ClassNotFoundException(this.firstMissingTypeName);
      } else {
         return this.parents;
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("declare parents : ");
      sb.append(this.getTargetTypesPattern().asString());
      sb.append(this.isExtends() ? " extends " : " implements ");
      sb.append(this.parentsString);
      return sb.toString();
   }
}
