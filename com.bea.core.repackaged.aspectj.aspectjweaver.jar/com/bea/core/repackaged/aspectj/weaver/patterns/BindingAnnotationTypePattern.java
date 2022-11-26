package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.TypeVariableReference;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public class BindingAnnotationTypePattern extends ExactAnnotationTypePattern implements BindingPattern {
   protected int formalIndex;
   private static final byte VERSION = 1;

   public BindingAnnotationTypePattern(UnresolvedType annotationType, int index) {
      super(annotationType, (Map)null);
      this.formalIndex = index;
   }

   public BindingAnnotationTypePattern(FormalBinding binding) {
      this(binding.getType(), binding.getIndex());
   }

   public void resolveBinding(World world) {
      if (!this.resolved) {
         this.resolved = true;
         this.annotationType = this.annotationType.resolve(world);
         ResolvedType resolvedAnnotationType = (ResolvedType)this.annotationType;
         if (!resolvedAnnotationType.isAnnotation()) {
            IMessage m = MessageUtil.error(WeaverMessages.format("referenceToNonAnnotationType", this.annotationType.getName()), this.getSourceLocation());
            world.getMessageHandler().handleMessage(m);
            this.resolved = false;
         }

         if (!this.annotationType.isTypeVariableReference()) {
            this.verifyRuntimeRetention(world, resolvedAnnotationType);
         }
      }
   }

   private void verifyRuntimeRetention(World world, ResolvedType resolvedAnnotationType) {
      if (!resolvedAnnotationType.isAnnotationWithRuntimeRetention()) {
         IMessage m = MessageUtil.error(WeaverMessages.format("bindingNonRuntimeRetentionAnnotation", this.annotationType.getName()), this.getSourceLocation());
         world.getMessageHandler().handleMessage(m);
         this.resolved = false;
      }

   }

   public AnnotationTypePattern parameterizeWith(Map typeVariableMap, World w) {
      UnresolvedType newAnnotationType = this.annotationType;
      if (this.annotationType.isTypeVariableReference()) {
         TypeVariableReference t = (TypeVariableReference)this.annotationType;
         String key = t.getTypeVariable().getName();
         if (typeVariableMap.containsKey(key)) {
            newAnnotationType = (UnresolvedType)typeVariableMap.get(key);
         }
      } else if (this.annotationType.isParameterizedType()) {
         newAnnotationType = this.annotationType.parameterize(typeVariableMap);
      }

      BindingAnnotationTypePattern ret = new BindingAnnotationTypePattern(newAnnotationType, this.formalIndex);
      if (newAnnotationType instanceof ResolvedType) {
         ResolvedType rat = (ResolvedType)newAnnotationType;
         this.verifyRuntimeRetention(rat.getWorld(), rat);
      }

      ret.copyLocationFrom(this);
      return ret;
   }

   public int getFormalIndex() {
      return this.formalIndex;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof BindingAnnotationTypePattern)) {
         return false;
      } else {
         BindingAnnotationTypePattern btp = (BindingAnnotationTypePattern)obj;
         return super.equals(btp) && btp.formalIndex == this.formalIndex;
      }
   }

   public int hashCode() {
      return super.hashCode() * 37 + this.formalIndex;
   }

   public AnnotationTypePattern remapAdviceFormals(IntMap bindings) {
      if (!bindings.hasKey(this.formalIndex)) {
         return new ExactAnnotationTypePattern(this.annotationType, (Map)null);
      } else {
         int newFormalIndex = bindings.get(this.formalIndex);
         return new BindingAnnotationTypePattern(this.annotationType, newFormalIndex);
      }
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(2);
      s.writeByte(1);
      this.annotationType.write(s);
      s.writeShort((short)this.formalIndex);
      this.writeLocation(s);
   }

   public static AnnotationTypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      byte version = s.readByte();
      if (version > 1) {
         throw new BCException("BindingAnnotationTypePattern was written by a more recent version of AspectJ");
      } else {
         AnnotationTypePattern ret = new BindingAnnotationTypePattern(UnresolvedType.read(s), s.readShort());
         ret.readLocation(context, s);
         return ret;
      }
   }
}
