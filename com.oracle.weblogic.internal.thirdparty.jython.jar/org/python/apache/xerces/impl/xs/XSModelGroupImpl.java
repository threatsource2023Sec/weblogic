package org.python.apache.xerces.impl.xs;

import org.python.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.python.apache.xerces.xs.XSAnnotation;
import org.python.apache.xerces.xs.XSModelGroup;
import org.python.apache.xerces.xs.XSNamespaceItem;
import org.python.apache.xerces.xs.XSObjectList;

public class XSModelGroupImpl implements XSModelGroup {
   public static final short MODELGROUP_CHOICE = 101;
   public static final short MODELGROUP_SEQUENCE = 102;
   public static final short MODELGROUP_ALL = 103;
   public short fCompositor;
   public XSParticleDecl[] fParticles = null;
   public int fParticleCount = 0;
   public XSObjectList fAnnotations = null;
   private String fDescription = null;

   public boolean isEmpty() {
      for(int var1 = 0; var1 < this.fParticleCount; ++var1) {
         if (!this.fParticles[var1].isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public int minEffectiveTotalRange() {
      return this.fCompositor == 101 ? this.minEffectiveTotalRangeChoice() : this.minEffectiveTotalRangeAllSeq();
   }

   private int minEffectiveTotalRangeAllSeq() {
      int var1 = 0;

      for(int var2 = 0; var2 < this.fParticleCount; ++var2) {
         var1 += this.fParticles[var2].minEffectiveTotalRange();
      }

      return var1;
   }

   private int minEffectiveTotalRangeChoice() {
      int var1 = 0;
      if (this.fParticleCount > 0) {
         var1 = this.fParticles[0].minEffectiveTotalRange();
      }

      for(int var2 = 1; var2 < this.fParticleCount; ++var2) {
         int var3 = this.fParticles[var2].minEffectiveTotalRange();
         if (var3 < var1) {
            var1 = var3;
         }
      }

      return var1;
   }

   public int maxEffectiveTotalRange() {
      return this.fCompositor == 101 ? this.maxEffectiveTotalRangeChoice() : this.maxEffectiveTotalRangeAllSeq();
   }

   private int maxEffectiveTotalRangeAllSeq() {
      int var1 = 0;

      for(int var2 = 0; var2 < this.fParticleCount; ++var2) {
         int var3 = this.fParticles[var2].maxEffectiveTotalRange();
         if (var3 == -1) {
            return -1;
         }

         var1 += var3;
      }

      return var1;
   }

   private int maxEffectiveTotalRangeChoice() {
      int var1 = 0;
      if (this.fParticleCount > 0) {
         var1 = this.fParticles[0].maxEffectiveTotalRange();
         if (var1 == -1) {
            return -1;
         }
      }

      for(int var2 = 1; var2 < this.fParticleCount; ++var2) {
         int var3 = this.fParticles[var2].maxEffectiveTotalRange();
         if (var3 == -1) {
            return -1;
         }

         if (var3 > var1) {
            var1 = var3;
         }
      }

      return var1;
   }

   public String toString() {
      if (this.fDescription == null) {
         StringBuffer var1 = new StringBuffer();
         if (this.fCompositor == 103) {
            var1.append("all(");
         } else {
            var1.append('(');
         }

         if (this.fParticleCount > 0) {
            var1.append(this.fParticles[0].toString());
         }

         for(int var2 = 1; var2 < this.fParticleCount; ++var2) {
            if (this.fCompositor == 101) {
               var1.append('|');
            } else {
               var1.append(',');
            }

            var1.append(this.fParticles[var2].toString());
         }

         var1.append(')');
         this.fDescription = var1.toString();
      }

      return this.fDescription;
   }

   public void reset() {
      this.fCompositor = 102;
      this.fParticles = null;
      this.fParticleCount = 0;
      this.fDescription = null;
      this.fAnnotations = null;
   }

   public short getType() {
      return 7;
   }

   public String getName() {
      return null;
   }

   public String getNamespace() {
      return null;
   }

   public short getCompositor() {
      if (this.fCompositor == 101) {
         return 2;
      } else {
         return (short)(this.fCompositor == 102 ? 1 : 3);
      }
   }

   public XSObjectList getParticles() {
      return new XSObjectListImpl(this.fParticles, this.fParticleCount);
   }

   public XSAnnotation getAnnotation() {
      return this.fAnnotations != null ? (XSAnnotation)this.fAnnotations.item(0) : null;
   }

   public XSObjectList getAnnotations() {
      return (XSObjectList)(this.fAnnotations != null ? this.fAnnotations : XSObjectListImpl.EMPTY_LIST);
   }

   public XSNamespaceItem getNamespaceItem() {
      return null;
   }
}
