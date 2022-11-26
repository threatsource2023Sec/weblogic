package weblogic.apache.xerces.impl.xs;

import java.util.Hashtable;
import java.util.Vector;
import weblogic.apache.xerces.xni.QName;
import weblogic.apache.xerces.xs.XSObjectList;
import weblogic.apache.xerces.xs.XSSimpleTypeDefinition;
import weblogic.apache.xerces.xs.XSTypeDefinition;

public class SubstitutionGroupHandler {
   private static final XSElementDecl[] EMPTY_GROUP = new XSElementDecl[0];
   private final XSElementDeclHelper fXSElementDeclHelper;
   Hashtable fSubGroupsB = new Hashtable();
   private static final OneSubGroup[] EMPTY_VECTOR = new OneSubGroup[0];
   Hashtable fSubGroups = new Hashtable();

   public SubstitutionGroupHandler(XSElementDeclHelper var1) {
      this.fXSElementDeclHelper = var1;
   }

   public XSElementDecl getMatchingElemDecl(QName var1, XSElementDecl var2) {
      if (var1.localpart == var2.fName && var1.uri == var2.fTargetNamespace) {
         return var2;
      } else if (var2.fScope != 1) {
         return null;
      } else if ((var2.fBlock & 4) != 0) {
         return null;
      } else {
         XSElementDecl var3 = this.fXSElementDeclHelper.getGlobalElementDecl(var1);
         if (var3 == null) {
            return null;
         } else {
            return this.substitutionGroupOK(var3, var2, var2.fBlock) ? var3 : null;
         }
      }
   }

   protected boolean substitutionGroupOK(XSElementDecl var1, XSElementDecl var2, short var3) {
      if (var1 == var2) {
         return true;
      } else if ((var3 & 4) != 0) {
         return false;
      } else {
         XSElementDecl var4;
         for(var4 = var1.fSubGroup; var4 != null && var4 != var2; var4 = var4.fSubGroup) {
         }

         return var4 == null ? false : this.typeDerivationOK(var1.fType, var2.fType, var3);
      }
   }

   private boolean typeDerivationOK(XSTypeDefinition var1, XSTypeDefinition var2, short var3) {
      short var4 = 0;
      short var5 = var3;
      Object var6 = var1;

      while(var6 != var2 && var6 != SchemaGrammar.fAnyType) {
         if (((XSTypeDefinition)var6).getTypeCategory() == 15) {
            var4 |= ((XSComplexTypeDecl)var6).fDerivedBy;
         } else {
            var4 = (short)(var4 | 2);
         }

         var6 = ((XSTypeDefinition)var6).getBaseType();
         if (var6 == null) {
            var6 = SchemaGrammar.fAnyType;
         }

         if (((XSTypeDefinition)var6).getTypeCategory() == 15) {
            var5 |= ((XSComplexTypeDecl)var6).fBlock;
         }
      }

      if (var6 == var2) {
         return (var4 & var5) == 0;
      } else {
         if (var2.getTypeCategory() == 16) {
            XSSimpleTypeDefinition var7 = (XSSimpleTypeDefinition)var2;
            if (var7.getVariety() == 3) {
               XSObjectList var8 = var7.getMemberTypes();
               int var9 = var8.getLength();

               for(int var10 = 0; var10 < var9; ++var10) {
                  if (this.typeDerivationOK(var1, (XSTypeDefinition)var8.item(var10), var3)) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   public boolean inSubstitutionGroup(XSElementDecl var1, XSElementDecl var2) {
      return this.substitutionGroupOK(var1, var2, var2.fBlock);
   }

   public void reset() {
      this.fSubGroupsB.clear();
      this.fSubGroups.clear();
   }

   public void addSubstitutionGroup(XSElementDecl[] var1) {
      for(int var5 = var1.length - 1; var5 >= 0; --var5) {
         XSElementDecl var3 = var1[var5];
         XSElementDecl var2 = var3.fSubGroup;
         Vector var4 = (Vector)this.fSubGroupsB.get(var2);
         if (var4 == null) {
            var4 = new Vector();
            this.fSubGroupsB.put(var2, var4);
         }

         var4.addElement(var3);
      }

   }

   public XSElementDecl[] getSubstitutionGroup(XSElementDecl var1) {
      Object var2 = this.fSubGroups.get(var1);
      if (var2 != null) {
         return (XSElementDecl[])var2;
      } else if ((var1.fBlock & 4) != 0) {
         this.fSubGroups.put(var1, EMPTY_GROUP);
         return EMPTY_GROUP;
      } else {
         OneSubGroup[] var3 = this.getSubGroupB(var1, new OneSubGroup());
         int var4 = var3.length;
         int var5 = 0;
         XSElementDecl[] var6 = new XSElementDecl[var4];

         for(int var7 = 0; var7 < var4; ++var7) {
            if ((var1.fBlock & var3[var7].dMethod) == 0) {
               var6[var5++] = var3[var7].sub;
            }
         }

         if (var5 < var4) {
            XSElementDecl[] var8 = new XSElementDecl[var5];
            System.arraycopy(var6, 0, var8, 0, var5);
            var6 = var8;
         }

         this.fSubGroups.put(var1, var6);
         return var6;
      }
   }

   private OneSubGroup[] getSubGroupB(XSElementDecl var1, OneSubGroup var2) {
      Object var3 = this.fSubGroupsB.get(var1);
      if (var3 == null) {
         this.fSubGroupsB.put(var1, EMPTY_VECTOR);
         return EMPTY_VECTOR;
      } else if (var3 instanceof OneSubGroup[]) {
         return (OneSubGroup[])var3;
      } else {
         Vector var4 = (Vector)var3;
         Vector var5 = new Vector();

         for(int var11 = var4.size() - 1; var11 >= 0; --var11) {
            XSElementDecl var13 = (XSElementDecl)var4.elementAt(var11);
            if (this.getDBMethods(var13.fType, var1.fType, var2)) {
               short var7 = var2.dMethod;
               short var8 = var2.bMethod;
               var5.addElement(new OneSubGroup(var13, var2.dMethod, var2.bMethod));
               OneSubGroup[] var6 = this.getSubGroupB(var13, var2);

               for(int var12 = var6.length - 1; var12 >= 0; --var12) {
                  short var9 = (short)(var7 | var6[var12].dMethod);
                  short var10 = (short)(var8 | var6[var12].bMethod);
                  if ((var9 & var10) == 0) {
                     var5.addElement(new OneSubGroup(var6[var12].sub, var9, var10));
                  }
               }
            }
         }

         OneSubGroup[] var15 = new OneSubGroup[var5.size()];

         for(int var14 = var5.size() - 1; var14 >= 0; --var14) {
            var15[var14] = (OneSubGroup)var5.elementAt(var14);
         }

         this.fSubGroupsB.put(var1, var15);
         return var15;
      }
   }

   private boolean getDBMethods(XSTypeDefinition var1, XSTypeDefinition var2, OneSubGroup var3) {
      short var4 = 0;
      short var5 = 0;

      while(var1 != var2 && var1 != SchemaGrammar.fAnyType) {
         if (((XSTypeDefinition)var1).getTypeCategory() == 15) {
            var4 |= ((XSComplexTypeDecl)var1).fDerivedBy;
         } else {
            var4 = (short)(var4 | 2);
         }

         var1 = ((XSTypeDefinition)var1).getBaseType();
         if (var1 == null) {
            var1 = SchemaGrammar.fAnyType;
         }

         if (((XSTypeDefinition)var1).getTypeCategory() == 15) {
            var5 |= ((XSComplexTypeDecl)var1).fBlock;
         }
      }

      if (var1 == var2 && (var4 & var5) == 0) {
         var3.dMethod = var4;
         var3.bMethod = var5;
         return true;
      } else {
         return false;
      }
   }

   private static final class OneSubGroup {
      XSElementDecl sub;
      short dMethod;
      short bMethod;

      OneSubGroup() {
      }

      OneSubGroup(XSElementDecl var1, short var2, short var3) {
         this.sub = var1;
         this.dMethod = var2;
         this.bMethod = var3;
      }
   }
}
