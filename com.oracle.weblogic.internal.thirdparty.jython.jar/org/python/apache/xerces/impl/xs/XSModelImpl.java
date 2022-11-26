package org.python.apache.xerces.impl.xs;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Vector;
import org.python.apache.xerces.impl.xs.util.StringListImpl;
import org.python.apache.xerces.impl.xs.util.XSNamedMap4Types;
import org.python.apache.xerces.impl.xs.util.XSNamedMapImpl;
import org.python.apache.xerces.impl.xs.util.XSObjectListImpl;
import org.python.apache.xerces.util.SymbolHash;
import org.python.apache.xerces.util.XMLSymbols;
import org.python.apache.xerces.xs.StringList;
import org.python.apache.xerces.xs.XSAttributeDeclaration;
import org.python.apache.xerces.xs.XSAttributeGroupDefinition;
import org.python.apache.xerces.xs.XSElementDeclaration;
import org.python.apache.xerces.xs.XSIDCDefinition;
import org.python.apache.xerces.xs.XSModel;
import org.python.apache.xerces.xs.XSModelGroupDefinition;
import org.python.apache.xerces.xs.XSNamedMap;
import org.python.apache.xerces.xs.XSNamespaceItem;
import org.python.apache.xerces.xs.XSNamespaceItemList;
import org.python.apache.xerces.xs.XSNotationDeclaration;
import org.python.apache.xerces.xs.XSObject;
import org.python.apache.xerces.xs.XSObjectList;
import org.python.apache.xerces.xs.XSTypeDefinition;

public final class XSModelImpl extends AbstractList implements XSModel, XSNamespaceItemList {
   private static final short MAX_COMP_IDX = 16;
   private static final boolean[] GLOBAL_COMP = new boolean[]{false, true, true, true, false, true, true, false, false, false, true, true, false, false, false, true, true};
   private final int fGrammarCount;
   private final String[] fNamespaces;
   private final SchemaGrammar[] fGrammarList;
   private final SymbolHash fGrammarMap;
   private final SymbolHash fSubGroupMap;
   private final XSNamedMap[] fGlobalComponents;
   private final XSNamedMap[][] fNSComponents;
   private final StringList fNamespacesList;
   private XSObjectList fAnnotations;
   private final boolean fHasIDC;

   public XSModelImpl(SchemaGrammar[] var1) {
      this(var1, (short)1);
   }

   public XSModelImpl(SchemaGrammar[] var1, short var2) {
      this.fAnnotations = null;
      int var3 = var1.length;
      int var4 = Math.max(var3 + 1, 5);
      String[] var5 = new String[var4];
      SchemaGrammar[] var6 = new SchemaGrammar[var4];
      boolean var7 = false;

      SchemaGrammar var9;
      for(int var8 = 0; var8 < var3; ++var8) {
         var9 = var1[var8];
         String var10 = var9.getTargetNamespace();
         var5[var8] = var10;
         var6[var8] = var9;
         if (var10 == SchemaSymbols.URI_SCHEMAFORSCHEMA) {
            var7 = true;
         }
      }

      if (!var7) {
         var5[var3] = SchemaSymbols.URI_SCHEMAFORSCHEMA;
         var6[var3++] = SchemaGrammar.getS4SGrammar(var2);
      }

      int var11;
      for(var11 = 0; var11 < var3; ++var11) {
         var9 = var6[var11];
         Vector var12 = var9.getImportedGrammars();

         for(int var13 = var12 == null ? -1 : var12.size() - 1; var13 >= 0; --var13) {
            SchemaGrammar var17 = (SchemaGrammar)var12.elementAt(var13);

            int var14;
            for(var14 = 0; var14 < var3 && var17 != var6[var14]; ++var14) {
            }

            if (var14 == var3) {
               if (var3 == var6.length) {
                  String[] var15 = new String[var3 * 2];
                  System.arraycopy(var5, 0, var15, 0, var3);
                  var5 = var15;
                  SchemaGrammar[] var16 = new SchemaGrammar[var3 * 2];
                  System.arraycopy(var6, 0, var16, 0, var3);
                  var6 = var16;
               }

               var5[var3] = var17.getTargetNamespace();
               var6[var3] = var17;
               ++var3;
            }
         }
      }

      this.fNamespaces = var5;
      this.fGrammarList = var6;
      boolean var18 = false;
      this.fGrammarMap = new SymbolHash(var3 * 2);

      for(var11 = 0; var11 < var3; ++var11) {
         this.fGrammarMap.put(null2EmptyString(this.fNamespaces[var11]), this.fGrammarList[var11]);
         if (this.fGrammarList[var11].hasIDConstraints()) {
            var18 = true;
         }
      }

      this.fHasIDC = var18;
      this.fGrammarCount = var3;
      this.fGlobalComponents = new XSNamedMap[17];
      this.fNSComponents = new XSNamedMap[var3][17];
      this.fNamespacesList = new StringListImpl(this.fNamespaces, this.fGrammarCount);
      this.fSubGroupMap = this.buildSubGroups();
   }

   private SymbolHash buildSubGroups_Org() {
      SubstitutionGroupHandler var1 = new SubstitutionGroupHandler((XSElementDeclHelper)null);

      for(int var2 = 0; var2 < this.fGrammarCount; ++var2) {
         var1.addSubstitutionGroup(this.fGrammarList[var2].getSubstitutionGroups());
      }

      XSNamedMap var3 = this.getComponents((short)2);
      int var4 = var3.getLength();
      SymbolHash var5 = new SymbolHash(var4 * 2);

      for(int var6 = 0; var6 < var4; ++var6) {
         XSElementDecl var7 = (XSElementDecl)var3.item(var6);
         XSElementDecl[] var8 = var1.getSubstitutionGroup(var7);
         var5.put(var7, var8.length > 0 ? new XSObjectListImpl(var8, var8.length) : XSObjectListImpl.EMPTY_LIST);
      }

      return var5;
   }

   private SymbolHash buildSubGroups() {
      SubstitutionGroupHandler var1 = new SubstitutionGroupHandler((XSElementDeclHelper)null);

      for(int var2 = 0; var2 < this.fGrammarCount; ++var2) {
         var1.addSubstitutionGroup(this.fGrammarList[var2].getSubstitutionGroups());
      }

      XSObjectListImpl var3 = this.getGlobalElements();
      int var4 = var3.getLength();
      SymbolHash var5 = new SymbolHash(var4 * 2);

      for(int var6 = 0; var6 < var4; ++var6) {
         XSElementDecl var7 = (XSElementDecl)var3.item(var6);
         XSElementDecl[] var8 = var1.getSubstitutionGroup(var7);
         var5.put(var7, var8.length > 0 ? new XSObjectListImpl(var8, var8.length) : XSObjectListImpl.EMPTY_LIST);
      }

      return var5;
   }

   private XSObjectListImpl getGlobalElements() {
      SymbolHash[] var1 = new SymbolHash[this.fGrammarCount];
      int var2 = 0;

      for(int var3 = 0; var3 < this.fGrammarCount; ++var3) {
         var1[var3] = this.fGrammarList[var3].fAllGlobalElemDecls;
         var2 += var1[var3].getLength();
      }

      if (var2 == 0) {
         return XSObjectListImpl.EMPTY_LIST;
      } else {
         XSObject[] var4 = new XSObject[var2];
         int var5 = 0;

         for(int var6 = 0; var6 < this.fGrammarCount; ++var6) {
            var1[var6].getValues(var4, var5);
            var5 += var1[var6].getLength();
         }

         return new XSObjectListImpl(var4, var2);
      }
   }

   public StringList getNamespaces() {
      return this.fNamespacesList;
   }

   public XSNamespaceItemList getNamespaceItems() {
      return this;
   }

   public synchronized XSNamedMap getComponents(short var1) {
      if (var1 > 0 && var1 <= 16 && GLOBAL_COMP[var1]) {
         SymbolHash[] var2 = new SymbolHash[this.fGrammarCount];
         if (this.fGlobalComponents[var1] == null) {
            for(int var3 = 0; var3 < this.fGrammarCount; ++var3) {
               switch (var1) {
                  case 1:
                     var2[var3] = this.fGrammarList[var3].fGlobalAttrDecls;
                     break;
                  case 2:
                     var2[var3] = this.fGrammarList[var3].fGlobalElemDecls;
                     break;
                  case 3:
                  case 15:
                  case 16:
                     var2[var3] = this.fGrammarList[var3].fGlobalTypeDecls;
                  case 4:
                  case 7:
                  case 8:
                  case 9:
                  case 12:
                  case 13:
                  case 14:
                  default:
                     break;
                  case 5:
                     var2[var3] = this.fGrammarList[var3].fGlobalAttrGrpDecls;
                     break;
                  case 6:
                     var2[var3] = this.fGrammarList[var3].fGlobalGroupDecls;
                     break;
                  case 10:
                     var2[var3] = this.fGrammarList[var3].fGlobalIDConstraintDecls;
                     break;
                  case 11:
                     var2[var3] = this.fGrammarList[var3].fGlobalNotationDecls;
               }
            }

            if (var1 != 15 && var1 != 16) {
               this.fGlobalComponents[var1] = new XSNamedMapImpl(this.fNamespaces, var2, this.fGrammarCount);
            } else {
               this.fGlobalComponents[var1] = new XSNamedMap4Types(this.fNamespaces, var2, this.fGrammarCount, var1);
            }
         }

         return this.fGlobalComponents[var1];
      } else {
         return XSNamedMapImpl.EMPTY_MAP;
      }
   }

   public synchronized XSNamedMap getComponentsByNamespace(short var1, String var2) {
      if (var1 > 0 && var1 <= 16 && GLOBAL_COMP[var1]) {
         int var3 = 0;
         if (var2 != null) {
            while(var3 < this.fGrammarCount && !var2.equals(this.fNamespaces[var3])) {
               ++var3;
            }
         } else {
            while(var3 < this.fGrammarCount && this.fNamespaces[var3] != null) {
               ++var3;
            }
         }

         if (var3 == this.fGrammarCount) {
            return XSNamedMapImpl.EMPTY_MAP;
         } else {
            if (this.fNSComponents[var3][var1] == null) {
               SymbolHash var4 = null;
               switch (var1) {
                  case 1:
                     var4 = this.fGrammarList[var3].fGlobalAttrDecls;
                     break;
                  case 2:
                     var4 = this.fGrammarList[var3].fGlobalElemDecls;
                     break;
                  case 3:
                  case 15:
                  case 16:
                     var4 = this.fGrammarList[var3].fGlobalTypeDecls;
                  case 4:
                  case 7:
                  case 8:
                  case 9:
                  case 12:
                  case 13:
                  case 14:
                  default:
                     break;
                  case 5:
                     var4 = this.fGrammarList[var3].fGlobalAttrGrpDecls;
                     break;
                  case 6:
                     var4 = this.fGrammarList[var3].fGlobalGroupDecls;
                     break;
                  case 10:
                     var4 = this.fGrammarList[var3].fGlobalIDConstraintDecls;
                     break;
                  case 11:
                     var4 = this.fGrammarList[var3].fGlobalNotationDecls;
               }

               if (var1 != 15 && var1 != 16) {
                  this.fNSComponents[var3][var1] = new XSNamedMapImpl(var2, var4);
               } else {
                  this.fNSComponents[var3][var1] = new XSNamedMap4Types(var2, var4, var1);
               }
            }

            return this.fNSComponents[var3][var1];
         }
      } else {
         return XSNamedMapImpl.EMPTY_MAP;
      }
   }

   public XSTypeDefinition getTypeDefinition(String var1, String var2) {
      SchemaGrammar var3 = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(var2));
      return var3 == null ? null : (XSTypeDefinition)var3.fGlobalTypeDecls.get(var1);
   }

   public XSTypeDefinition getTypeDefinition(String var1, String var2, String var3) {
      SchemaGrammar var4 = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(var2));
      return var4 == null ? null : var4.getGlobalTypeDecl(var1, var3);
   }

   public XSAttributeDeclaration getAttributeDeclaration(String var1, String var2) {
      SchemaGrammar var3 = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(var2));
      return var3 == null ? null : (XSAttributeDeclaration)var3.fGlobalAttrDecls.get(var1);
   }

   public XSAttributeDeclaration getAttributeDeclaration(String var1, String var2, String var3) {
      SchemaGrammar var4 = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(var2));
      return var4 == null ? null : var4.getGlobalAttributeDecl(var1, var3);
   }

   public XSElementDeclaration getElementDeclaration(String var1, String var2) {
      SchemaGrammar var3 = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(var2));
      return var3 == null ? null : (XSElementDeclaration)var3.fGlobalElemDecls.get(var1);
   }

   public XSElementDeclaration getElementDeclaration(String var1, String var2, String var3) {
      SchemaGrammar var4 = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(var2));
      return var4 == null ? null : var4.getGlobalElementDecl(var1, var3);
   }

   public XSAttributeGroupDefinition getAttributeGroup(String var1, String var2) {
      SchemaGrammar var3 = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(var2));
      return var3 == null ? null : (XSAttributeGroupDefinition)var3.fGlobalAttrGrpDecls.get(var1);
   }

   public XSAttributeGroupDefinition getAttributeGroup(String var1, String var2, String var3) {
      SchemaGrammar var4 = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(var2));
      return var4 == null ? null : var4.getGlobalAttributeGroupDecl(var1, var3);
   }

   public XSModelGroupDefinition getModelGroupDefinition(String var1, String var2) {
      SchemaGrammar var3 = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(var2));
      return var3 == null ? null : (XSModelGroupDefinition)var3.fGlobalGroupDecls.get(var1);
   }

   public XSModelGroupDefinition getModelGroupDefinition(String var1, String var2, String var3) {
      SchemaGrammar var4 = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(var2));
      return var4 == null ? null : var4.getGlobalGroupDecl(var1, var3);
   }

   public XSIDCDefinition getIDCDefinition(String var1, String var2) {
      SchemaGrammar var3 = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(var2));
      return var3 == null ? null : (XSIDCDefinition)var3.fGlobalIDConstraintDecls.get(var1);
   }

   public XSIDCDefinition getIDCDefinition(String var1, String var2, String var3) {
      SchemaGrammar var4 = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(var2));
      return var4 == null ? null : var4.getIDConstraintDecl(var1, var3);
   }

   public XSNotationDeclaration getNotationDeclaration(String var1, String var2) {
      SchemaGrammar var3 = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(var2));
      return var3 == null ? null : (XSNotationDeclaration)var3.fGlobalNotationDecls.get(var1);
   }

   public XSNotationDeclaration getNotationDeclaration(String var1, String var2, String var3) {
      SchemaGrammar var4 = (SchemaGrammar)this.fGrammarMap.get(null2EmptyString(var2));
      return var4 == null ? null : var4.getGlobalNotationDecl(var1, var3);
   }

   public synchronized XSObjectList getAnnotations() {
      if (this.fAnnotations != null) {
         return this.fAnnotations;
      } else {
         int var1 = 0;

         for(int var2 = 0; var2 < this.fGrammarCount; ++var2) {
            var1 += this.fGrammarList[var2].fNumAnnotations;
         }

         if (var1 == 0) {
            this.fAnnotations = XSObjectListImpl.EMPTY_LIST;
            return this.fAnnotations;
         } else {
            XSAnnotationImpl[] var3 = new XSAnnotationImpl[var1];
            int var4 = 0;

            for(int var5 = 0; var5 < this.fGrammarCount; ++var5) {
               SchemaGrammar var6 = this.fGrammarList[var5];
               if (var6.fNumAnnotations > 0) {
                  System.arraycopy(var6.fAnnotations, 0, var3, var4, var6.fNumAnnotations);
                  var4 += var6.fNumAnnotations;
               }
            }

            this.fAnnotations = new XSObjectListImpl(var3, var3.length);
            return this.fAnnotations;
         }
      }
   }

   private static final String null2EmptyString(String var0) {
      return var0 == null ? XMLSymbols.EMPTY_STRING : var0;
   }

   public boolean hasIDConstraints() {
      return this.fHasIDC;
   }

   public XSObjectList getSubstitutionGroup(XSElementDeclaration var1) {
      return (XSObjectList)this.fSubGroupMap.get(var1);
   }

   public int getLength() {
      return this.fGrammarCount;
   }

   public XSNamespaceItem item(int var1) {
      return var1 >= 0 && var1 < this.fGrammarCount ? this.fGrammarList[var1] : null;
   }

   public Object get(int var1) {
      if (var1 >= 0 && var1 < this.fGrammarCount) {
         return this.fGrammarList[var1];
      } else {
         throw new IndexOutOfBoundsException("Index: " + var1);
      }
   }

   public int size() {
      return this.getLength();
   }

   public Iterator iterator() {
      return this.listIterator0(0);
   }

   public ListIterator listIterator() {
      return this.listIterator0(0);
   }

   public ListIterator listIterator(int var1) {
      if (var1 >= 0 && var1 < this.fGrammarCount) {
         return this.listIterator0(var1);
      } else {
         throw new IndexOutOfBoundsException("Index: " + var1);
      }
   }

   private ListIterator listIterator0(int var1) {
      return new XSNamespaceItemListIterator(var1);
   }

   public Object[] toArray() {
      Object[] var1 = new Object[this.fGrammarCount];
      this.toArray0(var1);
      return var1;
   }

   public Object[] toArray(Object[] var1) {
      if (var1.length < this.fGrammarCount) {
         Class var2 = var1.getClass();
         Class var3 = var2.getComponentType();
         var1 = (Object[])Array.newInstance(var3, this.fGrammarCount);
      }

      this.toArray0(var1);
      if (var1.length > this.fGrammarCount) {
         var1[this.fGrammarCount] = null;
      }

      return var1;
   }

   private void toArray0(Object[] var1) {
      if (this.fGrammarCount > 0) {
         System.arraycopy(this.fGrammarList, 0, var1, 0, this.fGrammarCount);
      }

   }

   private final class XSNamespaceItemListIterator implements ListIterator {
      private int index;

      public XSNamespaceItemListIterator(int var2) {
         this.index = var2;
      }

      public boolean hasNext() {
         return this.index < XSModelImpl.this.fGrammarCount;
      }

      public Object next() {
         if (this.index < XSModelImpl.this.fGrammarCount) {
            return XSModelImpl.this.fGrammarList[this.index++];
         } else {
            throw new NoSuchElementException();
         }
      }

      public boolean hasPrevious() {
         return this.index > 0;
      }

      public Object previous() {
         if (this.index > 0) {
            return XSModelImpl.this.fGrammarList[--this.index];
         } else {
            throw new NoSuchElementException();
         }
      }

      public int nextIndex() {
         return this.index;
      }

      public int previousIndex() {
         return this.index - 1;
      }

      public void remove() {
         throw new UnsupportedOperationException();
      }

      public void set(Object var1) {
         throw new UnsupportedOperationException();
      }

      public void add(Object var1) {
         throw new UnsupportedOperationException();
      }
   }
}
