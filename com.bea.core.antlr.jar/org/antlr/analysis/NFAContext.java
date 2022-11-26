package org.antlr.analysis;

public class NFAContext {
   public static int MAX_SAME_RULE_INVOCATIONS_PER_NFA_CONFIG_STACK = 4;
   public NFAContext parent;
   public NFAState invokingState;
   protected int cachedHashCode;

   public NFAContext(NFAContext parent, NFAState invokingState) {
      this.parent = parent;
      this.invokingState = invokingState;
      if (invokingState != null) {
         this.cachedHashCode = invokingState.stateNumber;
      }

      if (parent != null) {
         this.cachedHashCode += parent.cachedHashCode;
      }

   }

   public boolean equals(Object o) {
      NFAContext other = (NFAContext)o;
      if (this.cachedHashCode != other.cachedHashCode) {
         return false;
      } else if (this == other) {
         return true;
      } else {
         NFAContext sp;
         for(sp = this; sp.parent != null && other.parent != null; other = other.parent) {
            if (sp.invokingState != other.invokingState) {
               return false;
            }

            sp = sp.parent;
         }

         return sp.parent == null && other.parent == null;
      }
   }

   public boolean conflictsWith(NFAContext other) {
      return this.suffix(other);
   }

   protected boolean suffix(NFAContext other) {
      for(NFAContext sp = this; sp.parent != null && other.parent != null; other = other.parent) {
         if (sp.invokingState != other.invokingState) {
            return false;
         }

         sp = sp.parent;
      }

      return true;
   }

   public int recursionDepthEmanatingFromState(int state) {
      NFAContext sp = this;

      int n;
      for(n = 0; sp.parent != null; sp = sp.parent) {
         if (sp.invokingState.stateNumber == state) {
            ++n;
         }
      }

      return n;
   }

   public int hashCode() {
      return this.cachedHashCode;
   }

   public boolean isEmpty() {
      return this.parent == null;
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      NFAContext sp = this;
      buf.append("[");

      while(sp.parent != null) {
         buf.append(sp.invokingState.stateNumber);
         buf.append(" ");
         sp = sp.parent;
      }

      buf.append("$]");
      return buf.toString();
   }
}
