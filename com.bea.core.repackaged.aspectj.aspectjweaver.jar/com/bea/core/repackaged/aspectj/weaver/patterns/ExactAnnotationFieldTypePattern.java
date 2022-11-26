package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.AnnotatedElement;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ReferenceType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public class ExactAnnotationFieldTypePattern extends ExactAnnotationTypePattern {
   UnresolvedType annotationType;
   private ResolvedMember field;

   public ExactAnnotationFieldTypePattern(ExactAnnotationTypePattern p, String formalName) {
      super(formalName);
      this.annotationType = p.annotationType;
      this.copyLocationFrom(p);
   }

   public ExactAnnotationFieldTypePattern(UnresolvedType annotationType, String formalName) {
      super(formalName);
      this.annotationType = annotationType;
   }

   public AnnotationTypePattern resolveBindings(IScope scope, Bindings bindings, boolean allowBinding) {
      if (this.resolved) {
         return this;
      } else {
         this.resolved = true;
         FormalBinding formalBinding = scope.lookupFormal(this.formalName);
         if (formalBinding == null) {
            scope.message(IMessage.ERROR, this, "When using @annotation(<annotationType>(<annotationField>)), <annotationField> must be bound");
            return this;
         } else {
            this.annotationType = scope.getWorld().resolve(this.annotationType, true);
            if (ResolvedType.isMissing(this.annotationType)) {
               String cleanname = this.annotationType.getName();

               UnresolvedType type;
               int lastDot;
               for(type = null; ResolvedType.isMissing(type = scope.lookupType(cleanname, this)); cleanname = cleanname.substring(0, lastDot) + "$" + cleanname.substring(lastDot + 1)) {
                  lastDot = cleanname.lastIndexOf(46);
                  if (lastDot == -1) {
                     break;
                  }
               }

               this.annotationType = scope.getWorld().resolve(type, true);
               if (ResolvedType.isMissing(this.annotationType)) {
                  return this;
               }
            }

            this.verifyIsAnnotationType((ResolvedType)this.annotationType, scope);
            ResolvedType formalBindingType = formalBinding.getType().resolve(scope.getWorld());
            String bindingTypeSignature = formalBindingType.getSignature();
            if (!formalBindingType.isEnum() && !bindingTypeSignature.equals("Ljava/lang/String;") && !bindingTypeSignature.equals("I")) {
               scope.message(IMessage.ERROR, this, "The field within the annotation must be an enum, string or int. '" + formalBinding.getType() + "' is not (compiler limitation)");
            }

            this.bindingPattern = true;
            ReferenceType theAnnotationType = (ReferenceType)this.annotationType;
            ResolvedMember[] annotationFields = theAnnotationType.getDeclaredMethods();
            this.field = null;
            boolean looksAmbiguous = false;

            for(int i = 0; i < annotationFields.length; ++i) {
               ResolvedMember resolvedMember = annotationFields[i];
               if (resolvedMember.getReturnType().equals(formalBinding.getType())) {
                  if (this.field != null) {
                     boolean haveProblem = true;
                     if (this.field.getName().equals(this.formalName)) {
                        haveProblem = false;
                     } else if (resolvedMember.getName().equals(this.formalName)) {
                        this.field = resolvedMember;
                        haveProblem = false;
                     }

                     if (haveProblem) {
                        looksAmbiguous = true;
                     }
                  } else {
                     this.field = resolvedMember;
                  }
               }
            }

            if (looksAmbiguous && (this.field == null || !this.field.getName().equals(this.formalName))) {
               scope.message(IMessage.ERROR, this, "The field type '" + formalBinding.getType() + "' is ambiguous for annotation type '" + theAnnotationType.getName() + "'");
            }

            if (this.field == null) {
               scope.message(IMessage.ERROR, this, "No field of type '" + formalBinding.getType() + "' exists on annotation type '" + theAnnotationType.getName() + "'");
            }

            BindingAnnotationFieldTypePattern binding = new BindingAnnotationFieldTypePattern(formalBinding.getType(), formalBinding.getIndex(), theAnnotationType);
            binding.copyLocationFrom(this);
            binding.formalName = this.formalName;
            bindings.register(binding, scope);
            binding.resolveBinding(scope.getWorld());
            return binding;
         }
      }
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(9);
      s.writeUTF(this.formalName);
      this.annotationType.write(s);
      this.writeLocation(s);
   }

   public static AnnotationTypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      String formalName = s.readUTF();
      UnresolvedType annotationType = UnresolvedType.read(s);
      ExactAnnotationFieldTypePattern ret = new ExactAnnotationFieldTypePattern(annotationType, formalName);
      ret.readLocation(context, s);
      return ret;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit((ExactAnnotationTypePattern)this, data);
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof ExactAnnotationFieldTypePattern)) {
         return false;
      } else {
         ExactAnnotationFieldTypePattern other = (ExactAnnotationFieldTypePattern)obj;
         return other.annotationType.equals(this.annotationType) && other.field.equals(this.field) && other.formalName.equals(this.formalName);
      }
   }

   public int hashCode() {
      int hashcode = this.annotationType.hashCode();
      hashcode = hashcode * 37 + this.field.hashCode();
      hashcode = hashcode * 37 + this.formalName.hashCode();
      return hashcode;
   }

   public FuzzyBoolean fastMatches(AnnotatedElement annotated) {
      throw new BCException("unimplemented");
   }

   public UnresolvedType getAnnotationType() {
      throw new BCException("unimplemented");
   }

   public Map getAnnotationValues() {
      throw new BCException("unimplemented");
   }

   public ResolvedType getResolvedAnnotationType() {
      throw new BCException("unimplemented");
   }

   public FuzzyBoolean matches(AnnotatedElement annotated, ResolvedType[] parameterAnnotations) {
      throw new BCException("unimplemented");
   }

   public FuzzyBoolean matches(AnnotatedElement annotated) {
      throw new BCException("unimplemented");
   }

   public FuzzyBoolean matchesRuntimeType(AnnotatedElement annotated) {
      throw new BCException("unimplemented");
   }

   public AnnotationTypePattern parameterizeWith(Map typeVariableMap, World w) {
      throw new BCException("unimplemented");
   }

   public void resolve(World world) {
      throw new BCException("unimplemented");
   }

   public String toString() {
      if (!this.resolved && this.formalName != null) {
         return this.formalName;
      } else {
         StringBuffer ret = new StringBuffer();
         ret.append("@").append(this.annotationType.toString());
         ret.append("(").append(this.formalName).append(")");
         return ret.toString();
      }
   }
}
