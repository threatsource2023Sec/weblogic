package com.bea.core.repackaged.aspectj.runtime.reflect;

import com.bea.core.repackaged.aspectj.lang.JoinPoint;
import com.bea.core.repackaged.aspectj.lang.ProceedingJoinPoint;
import com.bea.core.repackaged.aspectj.lang.Signature;
import com.bea.core.repackaged.aspectj.lang.reflect.SourceLocation;
import com.bea.core.repackaged.aspectj.runtime.internal.AroundClosure;

class JoinPointImpl implements ProceedingJoinPoint {
   Object _this;
   Object target;
   Object[] args;
   JoinPoint.StaticPart staticPart;
   private AroundClosure arc;

   public JoinPointImpl(JoinPoint.StaticPart staticPart, Object _this, Object target, Object[] args) {
      this.staticPart = staticPart;
      this._this = _this;
      this.target = target;
      this.args = args;
   }

   public Object getThis() {
      return this._this;
   }

   public Object getTarget() {
      return this.target;
   }

   public Object[] getArgs() {
      if (this.args == null) {
         this.args = new Object[0];
      }

      Object[] argsCopy = new Object[this.args.length];
      System.arraycopy(this.args, 0, argsCopy, 0, this.args.length);
      return argsCopy;
   }

   public JoinPoint.StaticPart getStaticPart() {
      return this.staticPart;
   }

   public String getKind() {
      return this.staticPart.getKind();
   }

   public Signature getSignature() {
      return this.staticPart.getSignature();
   }

   public SourceLocation getSourceLocation() {
      return this.staticPart.getSourceLocation();
   }

   public final String toString() {
      return this.staticPart.toString();
   }

   public final String toShortString() {
      return this.staticPart.toShortString();
   }

   public final String toLongString() {
      return this.staticPart.toLongString();
   }

   public void set$AroundClosure(AroundClosure arc) {
      this.arc = arc;
   }

   public Object proceed() throws Throwable {
      return this.arc == null ? null : this.arc.run(this.arc.getState());
   }

   public Object proceed(Object[] adviceBindings) throws Throwable {
      if (this.arc == null) {
         return null;
      } else {
         int flags = this.arc.getFlags();
         boolean unset = (flags & 1048576) != 0;
         boolean thisTargetTheSame = (flags & 65536) != 0;
         boolean hasThis = (flags & 4096) != 0;
         boolean bindsThis = (flags & 256) != 0;
         boolean hasTarget = (flags & 16) != 0;
         boolean bindsTarget = (flags & 1) != 0;
         Object[] state = this.arc.getState();
         int firstArgumentIndexIntoAdviceBindings = 0;
         int firstArgumentIndexIntoState = 0;
         firstArgumentIndexIntoState += hasThis ? 1 : 0;
         firstArgumentIndexIntoState += hasTarget && !thisTargetTheSame ? 1 : 0;
         if (hasThis && bindsThis) {
            firstArgumentIndexIntoAdviceBindings = 1;
            state[0] = adviceBindings[0];
         }

         if (hasTarget && bindsTarget) {
            if (thisTargetTheSame) {
               firstArgumentIndexIntoAdviceBindings = 1 + (bindsThis ? 1 : 0);
               state[0] = adviceBindings[bindsThis ? 1 : 0];
            } else {
               firstArgumentIndexIntoAdviceBindings = (hasThis ? 1 : 0) + 1;
               state[hasThis ? 1 : 0] = adviceBindings[hasThis ? 1 : 0];
            }
         }

         for(int i = firstArgumentIndexIntoAdviceBindings; i < adviceBindings.length; ++i) {
            state[firstArgumentIndexIntoState + (i - firstArgumentIndexIntoAdviceBindings)] = adviceBindings[i];
         }

         return this.arc.run(state);
      }
   }

   static class EnclosingStaticPartImpl extends StaticPartImpl implements JoinPoint.EnclosingStaticPart {
      public EnclosingStaticPartImpl(int count, String kind, Signature signature, SourceLocation sourceLocation) {
         super(count, kind, signature, sourceLocation);
      }
   }

   static class StaticPartImpl implements JoinPoint.StaticPart {
      String kind;
      Signature signature;
      SourceLocation sourceLocation;
      private int id;

      public StaticPartImpl(int id, String kind, Signature signature, SourceLocation sourceLocation) {
         this.kind = kind;
         this.signature = signature;
         this.sourceLocation = sourceLocation;
         this.id = id;
      }

      public int getId() {
         return this.id;
      }

      public String getKind() {
         return this.kind;
      }

      public Signature getSignature() {
         return this.signature;
      }

      public SourceLocation getSourceLocation() {
         return this.sourceLocation;
      }

      String toString(StringMaker sm) {
         StringBuffer buf = new StringBuffer();
         buf.append(sm.makeKindName(this.getKind()));
         buf.append("(");
         buf.append(((SignatureImpl)this.getSignature()).toString(sm));
         buf.append(")");
         return buf.toString();
      }

      public final String toString() {
         return this.toString(StringMaker.middleStringMaker);
      }

      public final String toShortString() {
         return this.toString(StringMaker.shortStringMaker);
      }

      public final String toLongString() {
         return this.toString(StringMaker.longStringMaker);
      }
   }
}
