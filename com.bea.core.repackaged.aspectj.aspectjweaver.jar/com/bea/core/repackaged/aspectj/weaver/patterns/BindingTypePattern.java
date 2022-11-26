package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public class BindingTypePattern extends ExactTypePattern implements BindingPattern {
   private int formalIndex;
   private String bindingName;

   public BindingTypePattern(UnresolvedType type, int index, boolean isVarArgs) {
      super(type, false, isVarArgs);
      this.formalIndex = index;
   }

   public BindingTypePattern(FormalBinding binding, boolean isVarArgs) {
      this(binding.getType(), binding.getIndex(), isVarArgs);
      this.bindingName = binding.getName();
   }

   public int getFormalIndex() {
      return this.formalIndex;
   }

   public String getBindingName() {
      return this.bindingName;
   }

   public boolean equals(Object other) {
      if (!(other instanceof BindingTypePattern)) {
         return false;
      } else {
         BindingTypePattern o = (BindingTypePattern)other;
         if (this.includeSubtypes != o.includeSubtypes) {
            return false;
         } else if (this.isVarArgs != o.isVarArgs) {
            return false;
         } else {
            return o.type.equals(this.type) && o.formalIndex == this.formalIndex;
         }
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + super.hashCode();
      result = 37 * result + this.formalIndex;
      return result;
   }

   public void write(CompressingDataOutputStream out) throws IOException {
      out.writeByte(3);
      this.type.write(out);
      out.writeShort((short)this.formalIndex);
      out.writeBoolean(this.isVarArgs);
      this.writeLocation(out);
   }

   public static TypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      UnresolvedType type = UnresolvedType.read(s);
      int index = s.readShort();
      boolean isVarargs = false;
      if (s.getMajorVersion() >= 2) {
         isVarargs = s.readBoolean();
      }

      TypePattern ret = new BindingTypePattern(type, index, isVarargs);
      ret.readLocation(context, s);
      return ret;
   }

   public TypePattern remapAdviceFormals(IntMap bindings) {
      if (!bindings.hasKey(this.formalIndex)) {
         return new ExactTypePattern(this.type, false, this.isVarArgs);
      } else {
         int newFormalIndex = bindings.get(this.formalIndex);
         return new BindingTypePattern(this.type, newFormalIndex, this.isVarArgs);
      }
   }

   public TypePattern parameterizeWith(Map typeVariableMap, World w) {
      ExactTypePattern superParameterized = (ExactTypePattern)super.parameterizeWith(typeVariableMap, w);
      BindingTypePattern ret = new BindingTypePattern(superParameterized.getExactType(), this.formalIndex, this.isVarArgs);
      ret.copyLocationFrom(this);
      return ret;
   }

   public String toString() {
      return "BindingTypePattern(" + super.toString() + ", " + this.formalIndex + ")";
   }
}
