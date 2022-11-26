package weblogic.apache.xerces.impl.xs.models;

import weblogic.apache.xerces.impl.dtd.models.CMNode;
import weblogic.apache.xerces.impl.xs.XSComplexTypeDecl;
import weblogic.apache.xerces.impl.xs.XSDeclarationPool;
import weblogic.apache.xerces.impl.xs.XSElementDecl;
import weblogic.apache.xerces.impl.xs.XSModelGroupImpl;
import weblogic.apache.xerces.impl.xs.XSParticleDecl;

public class CMBuilder {
   private XSDeclarationPool fDeclPool = null;
   private static final XSEmptyCM fEmptyCM = new XSEmptyCM();
   private int fLeafCount;
   private int fParticleCount;
   private final CMNodeFactory fNodeFactory;

   public CMBuilder(CMNodeFactory var1) {
      this.fDeclPool = null;
      this.fNodeFactory = var1;
   }

   public void setDeclPool(XSDeclarationPool var1) {
      this.fDeclPool = var1;
   }

   public XSCMValidator getContentModel(XSComplexTypeDecl var1, boolean var2) {
      short var3 = var1.getContentType();
      if (var3 != 1 && var3 != 0) {
         XSParticleDecl var4 = (XSParticleDecl)var1.getParticle();
         if (var4 == null) {
            return fEmptyCM;
         } else {
            Object var5 = null;
            if (var4.fType == 3 && ((XSModelGroupImpl)var4.fValue).fCompositor == 103) {
               var5 = this.createAllCM(var4);
            } else {
               var5 = this.createDFACM(var4, var2);
            }

            this.fNodeFactory.resetNodeCount();
            if (var5 == null) {
               var5 = fEmptyCM;
            }

            return (XSCMValidator)var5;
         }
      } else {
         return null;
      }
   }

   XSCMValidator createAllCM(XSParticleDecl var1) {
      if (var1.fMaxOccurs == 0) {
         return null;
      } else {
         XSModelGroupImpl var2 = (XSModelGroupImpl)var1.fValue;
         XSAllCM var3 = new XSAllCM(var1.fMinOccurs == 0, var2.fParticleCount);

         for(int var4 = 0; var4 < var2.fParticleCount; ++var4) {
            var3.addElement((XSElementDecl)var2.fParticles[var4].fValue, var2.fParticles[var4].fMinOccurs == 0);
         }

         return var3;
      }
   }

   XSCMValidator createDFACM(XSParticleDecl var1, boolean var2) {
      this.fLeafCount = 0;
      this.fParticleCount = 0;
      CMNode var3 = this.useRepeatingLeafNodes(var1) ? this.buildCompactSyntaxTree(var1) : this.buildSyntaxTree(var1, var2);
      return var3 == null ? null : new XSDFACM(var3, this.fLeafCount);
   }

   private CMNode buildSyntaxTree(XSParticleDecl var1, boolean var2) {
      int var3 = var1.fMaxOccurs;
      int var4 = var1.fMinOccurs;
      boolean var5 = false;
      if (var2) {
         if (var4 > 1) {
            if (var3 <= var4 && !var1.getMaxOccursUnbounded()) {
               var4 = 2;
               var5 = true;
            } else {
               var4 = 1;
               var5 = true;
            }
         }

         if (var3 > 1) {
            var3 = 2;
            var5 = true;
         }
      }

      short var6 = var1.fType;
      CMNode var7 = null;
      if (var6 != 2 && var6 != 1) {
         if (var6 == 3) {
            XSModelGroupImpl var8 = (XSModelGroupImpl)var1.fValue;
            CMNode var9 = null;
            int var10 = 0;

            for(int var11 = 0; var11 < var8.fParticleCount; ++var11) {
               var9 = this.buildSyntaxTree(var8.fParticles[var11], var2);
               if (var9 != null) {
                  var5 |= var9.isCompactedForUPA();
                  ++var10;
                  if (var7 == null) {
                     var7 = var9;
                  } else {
                     var7 = this.fNodeFactory.getCMBinOpNode(var8.fCompositor, var7, var9);
                  }
               }
            }

            if (var7 != null) {
               if (var8.fCompositor == 101 && var10 < var8.fParticleCount) {
                  var7 = this.fNodeFactory.getCMUniOpNode(5, var7);
               }

               var7 = this.expandContentModel(var7, var4, var3);
               var7.setIsCompactUPAModel(var5);
            }
         }
      } else {
         var7 = this.fNodeFactory.getCMLeafNode(var1.fType, var1.fValue, this.fParticleCount++, this.fLeafCount++);
         var7 = this.expandContentModel(var7, var4, var3);
         if (var7 != null) {
            var7.setIsCompactUPAModel(var5);
         }
      }

      return var7;
   }

   private CMNode expandContentModel(CMNode var1, int var2, int var3) {
      CMNode var4 = null;
      if (var2 == 1 && var3 == 1) {
         var4 = var1;
      } else if (var2 == 0 && var3 == 1) {
         var4 = this.fNodeFactory.getCMUniOpNode(5, var1);
      } else if (var2 == 0 && var3 == -1) {
         var4 = this.fNodeFactory.getCMUniOpNode(4, var1);
      } else if (var2 == 1 && var3 == -1) {
         var4 = this.fNodeFactory.getCMUniOpNode(6, var1);
      } else if (var3 == -1) {
         var4 = this.fNodeFactory.getCMUniOpNode(6, var1);
         var4 = this.fNodeFactory.getCMBinOpNode(102, this.multiNodes(var1, var2 - 1, true), var4);
      } else {
         if (var2 > 0) {
            var4 = this.multiNodes(var1, var2, false);
         }

         if (var3 > var2) {
            var1 = this.fNodeFactory.getCMUniOpNode(5, var1);
            if (var4 == null) {
               var4 = this.multiNodes(var1, var3 - var2, false);
            } else {
               var4 = this.fNodeFactory.getCMBinOpNode(102, var4, this.multiNodes(var1, var3 - var2, true));
            }
         }
      }

      return var4;
   }

   private CMNode multiNodes(CMNode var1, int var2, boolean var3) {
      if (var2 == 0) {
         return null;
      } else if (var2 == 1) {
         return var3 ? this.copyNode(var1) : var1;
      } else {
         int var4 = var2 / 2;
         return this.fNodeFactory.getCMBinOpNode(102, this.multiNodes(var1, var4, var3), this.multiNodes(var1, var2 - var4, true));
      }
   }

   private CMNode copyNode(CMNode var1) {
      int var2 = var1.type();
      if (var2 != 101 && var2 != 102) {
         if (var2 != 4 && var2 != 6 && var2 != 5) {
            if (var2 == 1 || var2 == 2) {
               XSCMLeaf var5 = (XSCMLeaf)var1;
               var1 = this.fNodeFactory.getCMLeafNode(var5.type(), var5.getLeaf(), var5.getParticleId(), this.fLeafCount++);
            }
         } else {
            XSCMUniOp var4 = (XSCMUniOp)var1;
            var1 = this.fNodeFactory.getCMUniOpNode(var2, this.copyNode(var4.getChild()));
         }
      } else {
         XSCMBinOp var3 = (XSCMBinOp)var1;
         var1 = this.fNodeFactory.getCMBinOpNode(var2, this.copyNode(var3.getLeft()), this.copyNode(var3.getRight()));
      }

      return var1;
   }

   private CMNode buildCompactSyntaxTree(XSParticleDecl var1) {
      int var2 = var1.fMaxOccurs;
      int var3 = var1.fMinOccurs;
      short var4 = var1.fType;
      CMNode var5 = null;
      if (var4 != 2 && var4 != 1) {
         if (var4 == 3) {
            XSModelGroupImpl var6 = (XSModelGroupImpl)var1.fValue;
            if (var6.fParticleCount == 1 && (var3 != 1 || var2 != 1)) {
               return this.buildCompactSyntaxTree2(var6.fParticles[0], var3, var2);
            }

            CMNode var7 = null;
            int var8 = 0;

            for(int var9 = 0; var9 < var6.fParticleCount; ++var9) {
               var7 = this.buildCompactSyntaxTree(var6.fParticles[var9]);
               if (var7 != null) {
                  ++var8;
                  if (var5 == null) {
                     var5 = var7;
                  } else {
                     var5 = this.fNodeFactory.getCMBinOpNode(var6.fCompositor, var5, var7);
                  }
               }
            }

            if (var5 != null && var6.fCompositor == 101 && var8 < var6.fParticleCount) {
               var5 = this.fNodeFactory.getCMUniOpNode(5, var5);
            }
         }

         return var5;
      } else {
         return this.buildCompactSyntaxTree2(var1, var3, var2);
      }
   }

   private CMNode buildCompactSyntaxTree2(XSParticleDecl var1, int var2, int var3) {
      CMNode var4 = null;
      if (var2 == 1 && var3 == 1) {
         var4 = this.fNodeFactory.getCMLeafNode(var1.fType, var1.fValue, this.fParticleCount++, this.fLeafCount++);
      } else if (var2 == 0 && var3 == 1) {
         var4 = this.fNodeFactory.getCMLeafNode(var1.fType, var1.fValue, this.fParticleCount++, this.fLeafCount++);
         var4 = this.fNodeFactory.getCMUniOpNode(5, var4);
      } else if (var2 == 0 && var3 == -1) {
         var4 = this.fNodeFactory.getCMLeafNode(var1.fType, var1.fValue, this.fParticleCount++, this.fLeafCount++);
         var4 = this.fNodeFactory.getCMUniOpNode(4, var4);
      } else if (var2 == 1 && var3 == -1) {
         var4 = this.fNodeFactory.getCMLeafNode(var1.fType, var1.fValue, this.fParticleCount++, this.fLeafCount++);
         var4 = this.fNodeFactory.getCMUniOpNode(6, var4);
      } else {
         var4 = this.fNodeFactory.getCMRepeatingLeafNode(var1.fType, var1.fValue, var2, var3, this.fParticleCount++, this.fLeafCount++);
         if (var2 == 0) {
            var4 = this.fNodeFactory.getCMUniOpNode(4, var4);
         } else {
            var4 = this.fNodeFactory.getCMUniOpNode(6, var4);
         }
      }

      return var4;
   }

   private boolean useRepeatingLeafNodes(XSParticleDecl var1) {
      int var2 = var1.fMaxOccurs;
      int var3 = var1.fMinOccurs;
      short var4 = var1.fType;
      if (var4 == 3) {
         XSModelGroupImpl var5 = (XSModelGroupImpl)var1.fValue;
         if (var3 != 1 || var2 != 1) {
            if (var5.fParticleCount != 1) {
               return var5.fParticleCount == 0;
            }

            XSParticleDecl var8 = var5.fParticles[0];
            short var7 = var8.fType;
            return (var7 == 1 || var7 == 2) && var8.fMinOccurs == 1 && var8.fMaxOccurs == 1;
         }

         for(int var6 = 0; var6 < var5.fParticleCount; ++var6) {
            if (!this.useRepeatingLeafNodes(var5.fParticles[var6])) {
               return false;
            }
         }
      }

      return true;
   }
}
