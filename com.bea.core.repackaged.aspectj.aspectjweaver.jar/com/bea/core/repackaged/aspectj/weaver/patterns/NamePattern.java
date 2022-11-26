package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import java.io.IOException;

public class NamePattern extends PatternNode {
   char[] pattern;
   int starCount;
   private int hashcode;
   public static final NamePattern ELLIPSIS = new NamePattern("");
   public static final NamePattern ANY = new NamePattern("*");

   public NamePattern(String name) {
      this(name.toCharArray());
   }

   public NamePattern(char[] pattern) {
      this.starCount = 0;
      this.hashcode = -1;
      this.pattern = pattern;
      int i = 0;

      for(int len = pattern.length; i < len; ++i) {
         if (pattern[i] == '*') {
            ++this.starCount;
         }
      }

      this.hashcode = (new String(pattern)).hashCode();
   }

   public boolean matches(char[] a2) {
      char[] a1 = this.pattern;
      int len1 = a1.length;
      int len2 = a2.length;
      int i2;
      if (this.starCount == 0) {
         if (len1 != len2) {
            return false;
         } else {
            for(i2 = 0; i2 < len1; ++i2) {
               if (a1[i2] != a2[i2]) {
                  return false;
               }
            }

            return true;
         }
      } else if (this.starCount == 1) {
         if (len1 == 1) {
            return true;
         } else if (len1 > len2 + 1) {
            return false;
         } else {
            i2 = 0;

            for(int i1 = 0; i1 < len1; ++i1) {
               char c1 = a1[i1];
               if (c1 == '*') {
                  i2 = len2 - (len1 - (i1 + 1));
               } else if (c1 != a2[i2++]) {
                  return false;
               }
            }

            return true;
         }
      } else {
         boolean b = outOfStar(a1, a2, 0, 0, len1 - this.starCount, len2, this.starCount);
         return b;
      }
   }

   private static boolean outOfStar(char[] pattern, char[] target, int pi, int ti, int pLeft, int tLeft, int starsLeft) {
      if (pLeft > tLeft) {
         return false;
      } else {
         while(tLeft != 0) {
            if (pLeft == 0) {
               return starsLeft > 0;
            }

            if (pattern[pi] == '*') {
               return inStar(pattern, target, pi + 1, ti, pLeft, tLeft, starsLeft - 1);
            }

            if (target[ti] != pattern[pi]) {
               return false;
            }

            ++pi;
            ++ti;
            --pLeft;
            --tLeft;
         }

         return true;
      }
   }

   private static boolean inStar(char[] pattern, char[] target, int pi, int ti, int pLeft, int tLeft, int starsLeft) {
      char patternChar;
      for(patternChar = pattern[pi]; patternChar == '*'; patternChar = pattern[pi]) {
         --starsLeft;
         ++pi;
      }

      while(pLeft <= tLeft) {
         if (target[ti] == patternChar && outOfStar(pattern, target, pi + 1, ti + 1, pLeft - 1, tLeft - 1, starsLeft)) {
            return true;
         }

         ++ti;
         --tLeft;
      }

      return false;
   }

   public boolean matches(String other) {
      return this.starCount == 1 && this.pattern.length == 1 ? true : this.matches(other.toCharArray());
   }

   public String toString() {
      return new String(this.pattern);
   }

   public boolean equals(Object other) {
      if (other instanceof NamePattern) {
         NamePattern otherPat = (NamePattern)other;
         if (otherPat.starCount != this.starCount) {
            return false;
         } else if (otherPat.pattern.length != this.pattern.length) {
            return false;
         } else {
            for(int i = 0; i < this.pattern.length; ++i) {
               if (this.pattern[i] != otherPat.pattern[i]) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.hashcode;
   }

   public void write(CompressingDataOutputStream out) throws IOException {
      out.writeUTF(new String(this.pattern));
   }

   public static NamePattern read(VersionedDataInputStream in) throws IOException {
      String s = in.readUTF();
      return s.length() == 0 ? ELLIPSIS : new NamePattern(s);
   }

   public String maybeGetSimpleName() {
      return this.starCount == 0 && this.pattern.length > 0 ? new String(this.pattern) : null;
   }

   public boolean isAny() {
      return this.starCount == 1 && this.pattern.length == 1;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }
}
