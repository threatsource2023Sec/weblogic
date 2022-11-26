package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.Message;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DeclareParents extends Declare {
   protected TypePattern child;
   protected TypePatternList parents;
   private boolean isWildChild;
   protected boolean isExtends;

   public DeclareParents(TypePattern child, List parents, boolean isExtends) {
      this(child, new TypePatternList(parents), isExtends);
   }

   protected DeclareParents(TypePattern child, TypePatternList parents, boolean isExtends) {
      this.isWildChild = false;
      this.isExtends = true;
      this.child = child;
      this.parents = parents;
      this.isExtends = isExtends;
      if (child instanceof WildTypePattern) {
         this.isWildChild = true;
      }

   }

   public boolean match(ResolvedType typeX) {
      if (!this.child.matchesStatically(typeX)) {
         return false;
      } else {
         if (typeX.getWorld().getLint().typeNotExposedToWeaver.isEnabled() && !typeX.isExposedToWeaver()) {
            typeX.getWorld().getLint().typeNotExposedToWeaver.signal(typeX.getName(), this.getSourceLocation());
         }

         return true;
      }
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Declare parameterizeWith(Map typeVariableBindingMap, World w) {
      DeclareParents ret = new DeclareParents(this.child.parameterizeWith(typeVariableBindingMap, w), this.parents.parameterizeWith(typeVariableBindingMap, w), this.isExtends);
      ret.copyLocationFrom(this);
      return ret;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("declare parents: ");
      buf.append(this.child);
      buf.append(this.isExtends ? " extends " : " implements ");
      buf.append(this.parents);
      buf.append(";");
      return buf.toString();
   }

   public boolean equals(Object other) {
      if (!(other instanceof DeclareParents)) {
         return false;
      } else {
         DeclareParents o = (DeclareParents)other;
         return o.child.equals(this.child) && o.parents.equals(this.parents);
      }
   }

   public int hashCode() {
      int result = 23;
      result = 37 * result + this.child.hashCode();
      result = 37 * result + this.parents.hashCode();
      return result;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(2);
      this.child.write(s);
      this.parents.write(s);
      this.writeLocation(s);
   }

   public static Declare read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      DeclareParents ret = new DeclareParents(TypePattern.read(s, context), TypePatternList.read(s, context), true);
      ret.readLocation(context, s);
      return ret;
   }

   public boolean parentsIncludeInterface(World w) {
      for(int i = 0; i < this.parents.size(); ++i) {
         if (this.parents.get(i).getExactType().resolve(w).isInterface()) {
            return true;
         }
      }

      return false;
   }

   public boolean parentsIncludeClass(World w) {
      for(int i = 0; i < this.parents.size(); ++i) {
         if (this.parents.get(i).getExactType().resolve(w).isClass()) {
            return true;
         }
      }

      return false;
   }

   public void resolve(IScope scope) {
      this.child = this.child.resolveBindings(scope, Bindings.NONE, false, false);
      this.isWildChild = this.child instanceof WildTypePattern;
      this.parents = this.parents.resolveBindings(scope, Bindings.NONE, false, true);
   }

   public TypePatternList getParents() {
      return this.parents;
   }

   public TypePattern getChild() {
      return this.child;
   }

   public boolean isExtends() {
      return this.isExtends;
   }

   public boolean isAdviceLike() {
      return false;
   }

   private ResolvedType maybeGetNewParent(ResolvedType targetType, TypePattern typePattern, World world, boolean reportErrors) {
      if (typePattern == TypePattern.NO) {
         return null;
      } else {
         UnresolvedType iType = typePattern.getExactType();
         ResolvedType parentType = iType.resolve(world);
         if (targetType.equals(world.getCoreType(UnresolvedType.OBJECT))) {
            world.showMessage(IMessage.ERROR, WeaverMessages.format("decpObject"), this.getSourceLocation(), (ISourceLocation)null);
            return null;
         } else {
            if (parentType.isParameterizedType() || parentType.isRawType()) {
               boolean isOK = this.verifyNoInheritedAlternateParameterization(targetType, parentType, world);
               if (!isOK) {
                  return null;
               }
            }

            if (parentType.isAssignableFrom(targetType)) {
               return null;
            } else {
               if (reportErrors && this.isWildChild && targetType.isEnum()) {
                  world.getLint().enumAsTargetForDecpIgnored.signal(targetType.toString(), this.getSourceLocation());
               }

               if (reportErrors && this.isWildChild && targetType.isAnnotation()) {
                  world.getLint().annotationAsTargetForDecpIgnored.signal(targetType.toString(), this.getSourceLocation());
               }

               if (targetType.isEnum() && parentType.isInterface()) {
                  if (reportErrors && !this.isWildChild) {
                     world.showMessage(IMessage.ERROR, WeaverMessages.format("cantDecpOnEnumToImplInterface", targetType), this.getSourceLocation(), (ISourceLocation)null);
                  }

                  return null;
               } else if (targetType.isAnnotation() && parentType.isInterface()) {
                  if (reportErrors && !this.isWildChild) {
                     world.showMessage(IMessage.ERROR, WeaverMessages.format("cantDecpOnAnnotationToImplInterface", targetType), this.getSourceLocation(), (ISourceLocation)null);
                  }

                  return null;
               } else if (targetType.isEnum() && parentType.isClass()) {
                  if (reportErrors && !this.isWildChild) {
                     world.showMessage(IMessage.ERROR, WeaverMessages.format("cantDecpOnEnumToExtendClass", targetType), this.getSourceLocation(), (ISourceLocation)null);
                  }

                  return null;
               } else if (targetType.isAnnotation() && parentType.isClass()) {
                  if (reportErrors && !this.isWildChild) {
                     world.showMessage(IMessage.ERROR, WeaverMessages.format("cantDecpOnAnnotationToExtendClass", targetType), this.getSourceLocation(), (ISourceLocation)null);
                  }

                  return null;
               } else if (parentType.getSignature().equals(UnresolvedType.ENUM.getSignature())) {
                  if (reportErrors && !this.isWildChild) {
                     world.showMessage(IMessage.ERROR, WeaverMessages.format("cantDecpToMakeEnumSupertype", targetType), this.getSourceLocation(), (ISourceLocation)null);
                  }

                  return null;
               } else if (parentType.getSignature().equals(UnresolvedType.ANNOTATION.getSignature())) {
                  if (reportErrors && !this.isWildChild) {
                     world.showMessage(IMessage.ERROR, WeaverMessages.format("cantDecpToMakeAnnotationSupertype", targetType), this.getSourceLocation(), (ISourceLocation)null);
                  }

                  return null;
               } else if (parentType.isAssignableFrom(targetType)) {
                  return null;
               } else if (targetType.isAssignableFrom(parentType)) {
                  world.showMessage(IMessage.ERROR, WeaverMessages.format("cantExtendSelf", targetType.getName()), this.getSourceLocation(), (ISourceLocation)null);
                  return null;
               } else if (parentType.isClass()) {
                  if (targetType.isInterface()) {
                     world.showMessage(IMessage.ERROR, WeaverMessages.format("interfaceExtendClass"), this.getSourceLocation(), (ISourceLocation)null);
                     return null;
                  } else if (!targetType.getSuperclass().isAssignableFrom(parentType)) {
                     world.showMessage(IMessage.ERROR, WeaverMessages.format("decpHierarchy", iType.getName(), targetType.getSuperclass().getName()), this.getSourceLocation(), (ISourceLocation)null);
                     return null;
                  } else {
                     return parentType;
                  }
               } else {
                  return parentType;
               }
            }
         }
      }
   }

   private boolean verifyNoInheritedAlternateParameterization(ResolvedType typeToVerify, ResolvedType newParent, World world) {
      if (typeToVerify.equals(ResolvedType.OBJECT)) {
         return true;
      } else {
         ResolvedType newParentGenericType = newParent.getGenericType();
         Iterator iter = typeToVerify.getDirectSupertypes();

         ResolvedType supertype;
         do {
            if (!iter.hasNext()) {
               return true;
            }

            supertype = (ResolvedType)iter.next();
            if ((supertype.isRawType() && newParent.isParameterizedType() || supertype.isParameterizedType() && newParent.isRawType()) && newParentGenericType.equals(supertype.getGenericType())) {
               world.getMessageHandler().handleMessage(new Message(WeaverMessages.format("cantDecpMultipleParameterizations", newParent.getName(), typeToVerify.getName(), supertype.getName()), this.getSourceLocation(), true, new ISourceLocation[]{typeToVerify.getSourceLocation()}));
               return false;
            }

            if (supertype.isParameterizedType()) {
               ResolvedType generictype = supertype.getGenericType();
               if (generictype.isAssignableFrom(newParentGenericType) && !supertype.isAssignableFrom(newParent)) {
                  world.getMessageHandler().handleMessage(new Message(WeaverMessages.format("cantDecpMultipleParameterizations", newParent.getName(), typeToVerify.getName(), supertype.getName()), this.getSourceLocation(), true, new ISourceLocation[]{typeToVerify.getSourceLocation()}));
                  return false;
               }
            }
         } while(this.verifyNoInheritedAlternateParameterization(supertype, newParent, world));

         return false;
      }
   }

   public List findMatchingNewParents(ResolvedType onType, boolean reportErrors) {
      if (((ResolvedType)onType).isRawType()) {
         onType = ((ResolvedType)onType).getGenericType();
      }

      if (!this.match((ResolvedType)onType)) {
         return Collections.emptyList();
      } else {
         List ret = new ArrayList();

         for(int i = 0; i < this.parents.size(); ++i) {
            ResolvedType t = this.maybeGetNewParent((ResolvedType)onType, this.parents.get(i), ((ResolvedType)onType).getWorld(), reportErrors);
            if (t != null) {
               ret.add(t);
            }
         }

         return ret;
      }
   }

   public String getNameSuffix() {
      return "parents";
   }

   public boolean isMixin() {
      return false;
   }
}
