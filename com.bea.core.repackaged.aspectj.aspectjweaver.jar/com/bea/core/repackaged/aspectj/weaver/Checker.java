package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.weaver.patterns.DeclareErrorOrWarning;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class Checker extends ShadowMunger {
   private boolean isError;
   private String message;
   private volatile int hashCode = -1;

   private Checker() {
   }

   public Checker(DeclareErrorOrWarning deow) {
      super(deow.getPointcut(), deow.getStart(), deow.getEnd(), deow.getSourceContext(), 2);
      this.message = deow.getMessage();
      this.isError = deow.isError();
   }

   private Checker(Pointcut pointcut, int start, int end, ISourceContext context, String message, boolean isError) {
      super(pointcut, start, end, context, 2);
      this.message = message;
      this.isError = isError;
   }

   public boolean isError() {
      return this.isError;
   }

   public String getMessage(Shadow shadow) {
      return this.format(this.message, shadow);
   }

   public void specializeOn(Shadow shadow) {
      throw new IllegalStateException("Cannot call specializeOn(...) for a Checker");
   }

   public boolean implementOn(Shadow shadow) {
      throw new IllegalStateException("Cannot call implementOn(...) for a Checker");
   }

   public boolean match(Shadow shadow, World world) {
      if (super.match(shadow, world)) {
         world.reportCheckerMatch(this, shadow);
      }

      return false;
   }

   public int compareTo(Object other) {
      return 0;
   }

   public boolean mustCheckExceptions() {
      return true;
   }

   public Collection getThrownExceptions() {
      return Collections.emptyList();
   }

   public boolean equals(Object other) {
      if (!(other instanceof Checker)) {
         return false;
      } else {
         boolean var10000;
         label31: {
            Checker o = (Checker)other;
            if (o.isError == this.isError) {
               if (o.pointcut == null) {
                  if (this.pointcut == null) {
                     break label31;
                  }
               } else if (o.pointcut.equals(this.pointcut)) {
                  break label31;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public int hashCode() {
      if (this.hashCode == -1) {
         int result = 17;
         result = 37 * result + (this.isError ? 1 : 0);
         result = 37 * result + (this.pointcut == null ? 0 : this.pointcut.hashCode());
         this.hashCode = result;
      }

      return this.hashCode;
   }

   public ShadowMunger parameterizeWith(ResolvedType declaringType, Map typeVariableMap) {
      Checker ret = new Checker(this.pointcut.parameterizeWith(typeVariableMap, declaringType.getWorld()), this.start, this.end, this.sourceContext, this.message, this.isError);
      return ret;
   }

   public ShadowMunger concretize(ResolvedType theAspect, World world, PerClause clause) {
      this.pointcut = this.pointcut.concretize(theAspect, this.getDeclaringType(), 0, this);
      this.hashCode = -1;
      return this;
   }

   public ResolvedType getConcreteAspect() {
      return this.getDeclaringType();
   }

   private int nextCurly(String string, int pos) {
      do {
         int curlyIndex = string.indexOf(123, pos);
         if (curlyIndex == -1) {
            return -1;
         }

         if (curlyIndex == 0) {
            return 0;
         }

         if (string.charAt(curlyIndex - 1) != '\\') {
            return curlyIndex;
         }

         pos = curlyIndex + 1;
      } while(pos < string.length());

      return -1;
   }

   private String format(String msg, Shadow shadow) {
      int pos = 0;
      int curlyIndex = this.nextCurly(msg, 0);
      if (curlyIndex == -1) {
         return msg.indexOf(123) != -1 ? msg.replace("\\{", "{") : msg;
      } else {
         StringBuffer ret;
         for(ret = new StringBuffer(); curlyIndex >= 0; curlyIndex = this.nextCurly(msg, pos)) {
            if (curlyIndex > 0) {
               ret.append(msg.substring(pos, curlyIndex).replace("\\{", "{"));
            }

            int endCurly = msg.indexOf(125, curlyIndex);
            if (endCurly == -1) {
               ret.append('{');
               pos = curlyIndex + 1;
            } else {
               ret.append(this.getValue(msg.substring(curlyIndex + 1, endCurly), shadow));
            }

            pos = endCurly + 1;
         }

         ret.append(msg.substring(pos, msg.length()));
         return ret.toString();
      }
   }

   private String getValue(String key, Shadow shadow) {
      if (key.equalsIgnoreCase("joinpoint")) {
         return shadow.toString();
      } else if (key.equalsIgnoreCase("joinpoint.kind")) {
         return shadow.getKind().getName();
      } else if (key.equalsIgnoreCase("joinpoint.enclosingclass")) {
         return shadow.getEnclosingType().getName();
      } else {
         Member member;
         if (key.equalsIgnoreCase("joinpoint.enclosingmember.name")) {
            member = shadow.getEnclosingCodeSignature();
            return member == null ? "" : member.getName();
         } else if (key.equalsIgnoreCase("joinpoint.enclosingmember")) {
            member = shadow.getEnclosingCodeSignature();
            return member == null ? "" : member.toString();
         } else if (key.equalsIgnoreCase("joinpoint.signature")) {
            return shadow.getSignature().toString();
         } else if (key.equalsIgnoreCase("joinpoint.signature.declaringtype")) {
            return shadow.getSignature().getDeclaringType().toString();
         } else if (key.equalsIgnoreCase("joinpoint.signature.name")) {
            return shadow.getSignature().getName();
         } else {
            ISourceLocation loc;
            if (key.equalsIgnoreCase("joinpoint.sourcelocation.sourcefile")) {
               loc = shadow.getSourceLocation();
               return loc != null && loc.getSourceFile() != null ? loc.getSourceFile().toString() : "UNKNOWN";
            } else if (key.equalsIgnoreCase("joinpoint.sourcelocation.line")) {
               loc = shadow.getSourceLocation();
               return loc != null ? Integer.toString(loc.getLine()) : "-1";
            } else if (key.equalsIgnoreCase("advice.aspecttype")) {
               return this.getDeclaringType().getName();
            } else if (key.equalsIgnoreCase("advice.sourcelocation.line")) {
               loc = this.getSourceLocation();
               return loc != null && loc.getSourceFile() != null ? Integer.toString(loc.getLine()) : "-1";
            } else if (key.equalsIgnoreCase("advice.sourcelocation.sourcefile")) {
               loc = this.getSourceLocation();
               return loc != null && loc.getSourceFile() != null ? loc.getSourceFile().toString() : "UNKNOWN";
            } else {
               return "UNKNOWN_KEY{" + key + "}";
            }
         }
      }
   }
}
