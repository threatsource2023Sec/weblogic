package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.TypeVariableReferenceType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public class DeclareSoft extends Declare {
   private TypePattern exception;
   private Pointcut pointcut;

   public DeclareSoft(TypePattern exception, Pointcut pointcut) {
      this.exception = exception;
      this.pointcut = pointcut;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Declare parameterizeWith(Map typeVariableBindingMap, World w) {
      DeclareSoft ret = new DeclareSoft(this.exception.parameterizeWith(typeVariableBindingMap, w), this.pointcut.parameterizeWith(typeVariableBindingMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("declare soft: ");
      buf.append(this.exception);
      buf.append(": ");
      buf.append(this.pointcut);
      buf.append(";");
      return buf.toString();
   }

   public boolean equals(Object other) {
      if (!(other instanceof DeclareSoft)) {
         return false;
      } else {
         DeclareSoft o = (DeclareSoft)other;
         return o.pointcut.equals(this.pointcut) && o.exception.equals(this.exception);
      }
   }

   public int hashCode() {
      int result = 19;
      result = 37 * result + this.pointcut.hashCode();
      result = 37 * result + this.exception.hashCode();
      return result;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(3);
      this.exception.write(s);
      this.pointcut.write(s);
      this.writeLocation(s);
   }

   public static Declare read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      Declare ret = new DeclareSoft(TypePattern.read(s, context), Pointcut.read(s, context));
      ret.readLocation(context, s);
      return ret;
   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }

   public TypePattern getException() {
      return this.exception;
   }

   public void resolve(IScope scope) {
      this.exception = this.exception.resolveBindings(scope, (Bindings)null, false, true);
      ResolvedType excType = this.exception.getExactType().resolve(scope.getWorld());
      if (!excType.isMissing()) {
         if (excType.isTypeVariableReference()) {
            TypeVariableReferenceType typeVariableRT = (TypeVariableReferenceType)excType;
            excType = typeVariableRT.getTypeVariable().getFirstBound().resolve(scope.getWorld());
         }

         if (!scope.getWorld().getCoreType(UnresolvedType.THROWABLE).isAssignableFrom(excType)) {
            scope.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("notThrowable", excType.getName()), this.exception.getSourceLocation(), (ISourceLocation)null);
            this.pointcut = Pointcut.makeMatchesNothing(Pointcut.RESOLVED);
            return;
         }

         if (scope.getWorld().getCoreType(UnresolvedType.RUNTIME_EXCEPTION).isAssignableFrom(excType)) {
            scope.getWorld().getLint().runtimeExceptionNotSoftened.signal(new String[]{excType.getName()}, this.exception.getSourceLocation(), (ISourceLocation[])null);
            this.pointcut = Pointcut.makeMatchesNothing(Pointcut.RESOLVED);
            return;
         }
      }

      this.pointcut = this.pointcut.resolve(scope);
   }

   public boolean isAdviceLike() {
      return false;
   }

   public String getNameSuffix() {
      return "soft";
   }
}
