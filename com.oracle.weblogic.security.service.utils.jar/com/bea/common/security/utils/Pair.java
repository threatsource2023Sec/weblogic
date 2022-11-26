package com.bea.common.security.utils;

public class Pair {
   Object left;
   Object right;

   public Pair(Object left, Object right) {
      this.left = left;
      this.right = right;
   }

   public Object getLeft() {
      return this.left;
   }

   public Object getRight() {
      return this.right;
   }

   public int hashCode() {
      int result = 23;
      result = HashCodeUtil.hash(result, this.left);
      result = HashCodeUtil.hash(result, this.right);
      return result;
   }

   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof Pair)) {
         return false;
      } else {
         Pair p = (Pair)other;
         return (this.left == p.left || this.left != null && this.left.equals(p.left)) && (this.right == p.right || this.right != null && this.right.equals(p.right));
      }
   }
}
