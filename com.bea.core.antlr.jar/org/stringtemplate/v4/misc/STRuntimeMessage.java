package org.stringtemplate.v4.misc;

import org.stringtemplate.v4.InstanceScope;
import org.stringtemplate.v4.Interpreter;

public class STRuntimeMessage extends STMessage {
   final Interpreter interp;
   public final int ip;
   public final InstanceScope scope;

   public STRuntimeMessage(Interpreter interp, ErrorType error, int ip) {
      this(interp, error, ip, (InstanceScope)null);
   }

   public STRuntimeMessage(Interpreter interp, ErrorType error, int ip, InstanceScope scope) {
      this(interp, error, ip, scope, (Object)null);
   }

   public STRuntimeMessage(Interpreter interp, ErrorType error, int ip, InstanceScope scope, Object arg) {
      this(interp, error, ip, scope, (Throwable)null, arg, (Object)null);
   }

   public STRuntimeMessage(Interpreter interp, ErrorType error, int ip, InstanceScope scope, Throwable e, Object arg) {
      this(interp, error, ip, scope, e, arg, (Object)null);
   }

   public STRuntimeMessage(Interpreter interp, ErrorType error, int ip, InstanceScope scope, Throwable e, Object arg, Object arg2) {
      this(interp, error, ip, scope, e, arg, arg2, (Object)null);
   }

   public STRuntimeMessage(Interpreter interp, ErrorType error, int ip, InstanceScope scope, Throwable e, Object arg, Object arg2, Object arg3) {
      super(error, scope != null ? scope.st : null, e, arg, arg2, arg3);
      this.interp = interp;
      this.ip = ip;
      this.scope = scope;
   }

   public String getSourceLocation() {
      if (this.ip >= 0 && this.self.impl != null) {
         Interval I = this.self.impl.sourceMap[this.ip];
         if (I == null) {
            return null;
         } else {
            int i = I.a;
            Coordinate loc = Misc.getLineCharPosition(this.self.impl.template, i);
            return loc.toString();
         }
      } else {
         return null;
      }
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      String loc = this.getSourceLocation();
      if (this.self != null) {
         buf.append("context [");
         if (this.interp != null) {
            buf.append(Interpreter.getEnclosingInstanceStackString(this.scope));
         }

         buf.append("]");
      }

      if (loc != null) {
         buf.append(" " + loc);
      }

      buf.append(" " + super.toString());
      return buf.toString();
   }
}
