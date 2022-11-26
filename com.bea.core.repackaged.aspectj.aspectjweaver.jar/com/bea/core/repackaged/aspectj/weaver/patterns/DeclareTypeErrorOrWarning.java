package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public class DeclareTypeErrorOrWarning extends Declare {
   private boolean isError;
   private TypePattern typePattern;
   private String message;

   public DeclareTypeErrorOrWarning(boolean isError, TypePattern typePattern, String message) {
      this.isError = isError;
      this.typePattern = typePattern;
      this.message = message;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("declare ");
      if (this.isError) {
         buf.append("error: ");
      } else {
         buf.append("warning: ");
      }

      buf.append(this.typePattern);
      buf.append(": ");
      buf.append("\"");
      buf.append(this.message);
      buf.append("\";");
      return buf.toString();
   }

   public boolean equals(Object other) {
      if (!(other instanceof DeclareTypeErrorOrWarning)) {
         return false;
      } else {
         DeclareTypeErrorOrWarning o = (DeclareTypeErrorOrWarning)other;
         return o.isError == this.isError && o.typePattern.equals(this.typePattern) && o.message.equals(this.message);
      }
   }

   public int hashCode() {
      int result = this.isError ? 19 : 23;
      result = 37 * result + this.typePattern.hashCode();
      result = 37 * result + this.message.hashCode();
      return result;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit((PatternNode)this, data);
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(7);
      s.writeBoolean(this.isError);
      this.typePattern.write(s);
      s.writeUTF(this.message);
      this.writeLocation(s);
   }

   public static Declare read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      Declare ret = new DeclareTypeErrorOrWarning(s.readBoolean(), TypePattern.read(s, context), s.readUTF());
      ret.readLocation(context, s);
      return ret;
   }

   public boolean isError() {
      return this.isError;
   }

   public String getMessage() {
      return this.message;
   }

   public TypePattern getTypePattern() {
      return this.typePattern;
   }

   public void resolve(IScope scope) {
      this.typePattern.resolve(scope.getWorld());
   }

   public Declare parameterizeWith(Map typeVariableBindingMap, World w) {
      Declare ret = new DeclareTypeErrorOrWarning(this.isError, this.typePattern.parameterizeWith(typeVariableBindingMap, w), this.message);
      ret.copyLocationFrom(this);
      return ret;
   }

   public boolean isAdviceLike() {
      return false;
   }

   public String getNameSuffix() {
      return "teow";
   }

   public String getName() {
      StringBuffer buf = new StringBuffer();
      buf.append("declare type ");
      if (this.isError) {
         buf.append("error");
      } else {
         buf.append("warning");
      }

      return buf.toString();
   }
}
