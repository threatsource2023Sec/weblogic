package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.AnnotatedElement;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public class BindingAnnotationFieldTypePattern extends ExactAnnotationTypePattern implements BindingPattern {
   protected int formalIndex;
   UnresolvedType formalType;

   public BindingAnnotationFieldTypePattern(UnresolvedType formalType, int formalIndex, UnresolvedType theAnnotationType) {
      super(theAnnotationType, (Map)null);
      this.formalIndex = formalIndex;
      this.formalType = formalType;
   }

   public void resolveBinding(World world) {
      if (!this.resolved) {
         this.resolved = true;
         this.formalType = world.resolve(this.formalType);
         this.annotationType = world.resolve(this.annotationType);
         ResolvedType annoType = (ResolvedType)this.annotationType;
         if (!annoType.isAnnotation()) {
            IMessage m = MessageUtil.error(WeaverMessages.format("referenceToNonAnnotationType", annoType.getName()), this.getSourceLocation());
            world.getMessageHandler().handleMessage(m);
            this.resolved = false;
         }

      }
   }

   public AnnotationTypePattern parameterizeWith(Map typeVariableMap, World w) {
      throw new BCException("Parameterization not implemented for annotation field binding construct (compiler limitation)");
   }

   public int getFormalIndex() {
      return this.formalIndex;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof BindingAnnotationFieldTypePattern)) {
         return false;
      } else {
         BindingAnnotationFieldTypePattern btp = (BindingAnnotationFieldTypePattern)obj;
         return btp.formalIndex == this.formalIndex && this.annotationType.equals(btp.annotationType) && this.formalType.equals(btp.formalType);
      }
   }

   public int hashCode() {
      return this.annotationType.hashCode() * 37 + this.formalIndex * 37 + this.formalType.hashCode();
   }

   public AnnotationTypePattern remapAdviceFormals(IntMap bindings) {
      if (!bindings.hasKey(this.formalIndex)) {
         throw new BCException("Annotation field binding reference must be bound (compiler limitation)");
      } else {
         int newFormalIndex = bindings.get(this.formalIndex);
         BindingAnnotationFieldTypePattern baftp = new BindingAnnotationFieldTypePattern(this.formalType, newFormalIndex, this.annotationType);
         baftp.formalName = this.formalName;
         return baftp;
      }
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(11);
      this.formalType.write(s);
      s.writeShort((short)this.formalIndex);
      this.annotationType.write(s);
      s.writeUTF(this.formalName);
      this.writeLocation(s);
   }

   public static AnnotationTypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      AnnotationTypePattern ret = new BindingAnnotationFieldTypePattern(UnresolvedType.read(s), s.readShort(), UnresolvedType.read(s));
      ret.readLocation(context, s);
      return ret;
   }

   public static AnnotationTypePattern read2(VersionedDataInputStream s, ISourceContext context) throws IOException {
      BindingAnnotationFieldTypePattern ret = new BindingAnnotationFieldTypePattern(UnresolvedType.read(s), s.readShort(), UnresolvedType.read(s));
      ret.formalName = s.readUTF();
      ret.readLocation(context, s);
      return ret;
   }

   public FuzzyBoolean matches(AnnotatedElement annotated, ResolvedType[] parameterAnnotations) {
      if (annotated.hasAnnotation(this.annotationType) && this.annotationType instanceof ReferenceType) {
         ReferenceType rt = (ReferenceType)this.annotationType;
         if (rt.getRetentionPolicy() != null && rt.getRetentionPolicy().equals("SOURCE")) {
            rt.getWorld().getMessageHandler().handleMessage(MessageUtil.warn(WeaverMessages.format("noMatchBecauseSourceRetention", this.annotationType, annotated), this.getSourceLocation()));
            return FuzzyBoolean.NO;
         } else {
            ResolvedMember[] methods = rt.getDeclaredMethods();
            boolean found = false;

            for(int i = 0; i < methods.length && !found; ++i) {
               if (methods[i].getReturnType().equals(this.formalType)) {
                  found = true;
               }
            }

            return found ? FuzzyBoolean.YES : FuzzyBoolean.NO;
         }
      } else {
         return FuzzyBoolean.NO;
      }
   }

   public UnresolvedType getFormalType() {
      return this.formalType;
   }
}
