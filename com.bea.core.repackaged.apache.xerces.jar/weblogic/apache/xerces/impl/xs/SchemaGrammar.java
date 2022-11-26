package weblogic.apache.xerces.impl.xs;

import java.lang.ref.SoftReference;
import java.util.Vector;
import org.xml.sax.SAXException;
import weblogic.apache.xerces.impl.dv.SchemaDVFactory;
import weblogic.apache.xerces.impl.dv.ValidatedInfo;
import weblogic.apache.xerces.impl.dv.XSSimpleType;
import weblogic.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import weblogic.apache.xerces.impl.xs.identity.IdentityConstraint;
import weblogic.apache.xerces.impl.xs.util.ObjectListImpl;
import weblogic.apache.xerces.impl.xs.util.SimpleLocator;
import weblogic.apache.xerces.impl.xs.util.StringListImpl;
import weblogic.apache.xerces.impl.xs.util.XSNamedMap4Types;
import weblogic.apache.xerces.impl.xs.util.XSNamedMapImpl;
import weblogic.apache.xerces.impl.xs.util.XSObjectListImpl;
import weblogic.apache.xerces.parsers.DOMParser;
import weblogic.apache.xerces.parsers.SAXParser;
import weblogic.apache.xerces.parsers.XML11Configuration;
import weblogic.apache.xerces.util.SymbolHash;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.xni.NamespaceContext;
import weblogic.apache.xerces.xni.grammars.XMLGrammarDescription;
import weblogic.apache.xerces.xni.grammars.XSGrammar;
import weblogic.apache.xerces.xs.StringList;
import weblogic.apache.xerces.xs.XSAnnotation;
import weblogic.apache.xerces.xs.XSAttributeDeclaration;
import weblogic.apache.xerces.xs.XSAttributeGroupDefinition;
import weblogic.apache.xerces.xs.XSElementDeclaration;
import weblogic.apache.xerces.xs.XSIDCDefinition;
import weblogic.apache.xerces.xs.XSModel;
import weblogic.apache.xerces.xs.XSModelGroupDefinition;
import weblogic.apache.xerces.xs.XSNamedMap;
import weblogic.apache.xerces.xs.XSNamespaceItem;
import weblogic.apache.xerces.xs.XSNotationDeclaration;
import weblogic.apache.xerces.xs.XSObjectList;
import weblogic.apache.xerces.xs.XSTypeDefinition;
import weblogic.apache.xerces.xs.datatypes.ObjectList;

public class SchemaGrammar implements XSGrammar, XSNamespaceItem {
   String fTargetNamespace;
   SymbolHash fGlobalAttrDecls;
   SymbolHash fGlobalAttrGrpDecls;
   SymbolHash fGlobalElemDecls;
   SymbolHash fGlobalGroupDecls;
   SymbolHash fGlobalNotationDecls;
   SymbolHash fGlobalIDConstraintDecls;
   SymbolHash fGlobalTypeDecls;
   SymbolHash fGlobalAttrDeclsExt;
   SymbolHash fGlobalAttrGrpDeclsExt;
   SymbolHash fGlobalElemDeclsExt;
   SymbolHash fGlobalGroupDeclsExt;
   SymbolHash fGlobalNotationDeclsExt;
   SymbolHash fGlobalIDConstraintDeclsExt;
   SymbolHash fGlobalTypeDeclsExt;
   SymbolHash fAllGlobalElemDecls;
   XSDDescription fGrammarDescription = null;
   XSAnnotationImpl[] fAnnotations = null;
   int fNumAnnotations;
   private SymbolTable fSymbolTable = null;
   private SoftReference fSAXParser = null;
   private SoftReference fDOMParser = null;
   private boolean fIsImmutable = false;
   private static final int BASICSET_COUNT = 29;
   private static final int FULLSET_COUNT = 46;
   private static final int GRAMMAR_XS = 1;
   private static final int GRAMMAR_XSI = 2;
   Vector fImported = null;
   private static final int INITIAL_SIZE = 16;
   private static final int INC_SIZE = 16;
   private int fCTCount = 0;
   private XSComplexTypeDecl[] fComplexTypeDecls = new XSComplexTypeDecl[16];
   private SimpleLocator[] fCTLocators = new SimpleLocator[16];
   private static final int REDEFINED_GROUP_INIT_SIZE = 2;
   private int fRGCount = 0;
   private XSGroupDecl[] fRedefinedGroupDecls = new XSGroupDecl[2];
   private SimpleLocator[] fRGLocators = new SimpleLocator[1];
   boolean fFullChecked = false;
   private int fSubGroupCount = 0;
   private XSElementDecl[] fSubGroups = new XSElementDecl[16];
   public static final XSComplexTypeDecl fAnyType = new XSAnyType();
   public static final BuiltinSchemaGrammar SG_SchemaNS = new BuiltinSchemaGrammar(1, (short)1);
   private static final BuiltinSchemaGrammar SG_SchemaNSExtended = new BuiltinSchemaGrammar(1, (short)2);
   public static final XSSimpleType fAnySimpleType;
   public static final BuiltinSchemaGrammar SG_XSI;
   private static final short MAX_COMP_IDX = 16;
   private static final boolean[] GLOBAL_COMP;
   private XSNamedMap[] fComponents = null;
   private ObjectList[] fComponentsExt = null;
   private Vector fDocuments = null;
   private Vector fLocations = null;

   protected SchemaGrammar() {
   }

   public SchemaGrammar(String var1, XSDDescription var2, SymbolTable var3) {
      this.fTargetNamespace = var1;
      this.fGrammarDescription = var2;
      this.fSymbolTable = var3;
      this.fGlobalAttrDecls = new SymbolHash(12);
      this.fGlobalAttrGrpDecls = new SymbolHash(5);
      this.fGlobalElemDecls = new SymbolHash(25);
      this.fGlobalGroupDecls = new SymbolHash(5);
      this.fGlobalNotationDecls = new SymbolHash(1);
      this.fGlobalIDConstraintDecls = new SymbolHash(3);
      this.fGlobalAttrDeclsExt = new SymbolHash(12);
      this.fGlobalAttrGrpDeclsExt = new SymbolHash(5);
      this.fGlobalElemDeclsExt = new SymbolHash(25);
      this.fGlobalGroupDeclsExt = new SymbolHash(5);
      this.fGlobalNotationDeclsExt = new SymbolHash(1);
      this.fGlobalIDConstraintDeclsExt = new SymbolHash(3);
      this.fGlobalTypeDeclsExt = new SymbolHash(25);
      this.fAllGlobalElemDecls = new SymbolHash(25);
      if (this.fTargetNamespace == SchemaSymbols.URI_SCHEMAFORSCHEMA) {
         this.fGlobalTypeDecls = SG_SchemaNS.fGlobalTypeDecls.makeClone();
      } else {
         this.fGlobalTypeDecls = new SymbolHash(25);
      }

   }

   public SchemaGrammar(SchemaGrammar var1) {
      this.fTargetNamespace = var1.fTargetNamespace;
      this.fGrammarDescription = var1.fGrammarDescription.makeClone();
      this.fSymbolTable = var1.fSymbolTable;
      this.fGlobalAttrDecls = var1.fGlobalAttrDecls.makeClone();
      this.fGlobalAttrGrpDecls = var1.fGlobalAttrGrpDecls.makeClone();
      this.fGlobalElemDecls = var1.fGlobalElemDecls.makeClone();
      this.fGlobalGroupDecls = var1.fGlobalGroupDecls.makeClone();
      this.fGlobalNotationDecls = var1.fGlobalNotationDecls.makeClone();
      this.fGlobalIDConstraintDecls = var1.fGlobalIDConstraintDecls.makeClone();
      this.fGlobalTypeDecls = var1.fGlobalTypeDecls.makeClone();
      this.fGlobalAttrDeclsExt = var1.fGlobalAttrDeclsExt.makeClone();
      this.fGlobalAttrGrpDeclsExt = var1.fGlobalAttrGrpDeclsExt.makeClone();
      this.fGlobalElemDeclsExt = var1.fGlobalElemDeclsExt.makeClone();
      this.fGlobalGroupDeclsExt = var1.fGlobalGroupDeclsExt.makeClone();
      this.fGlobalNotationDeclsExt = var1.fGlobalNotationDeclsExt.makeClone();
      this.fGlobalIDConstraintDeclsExt = var1.fGlobalIDConstraintDeclsExt.makeClone();
      this.fGlobalTypeDeclsExt = var1.fGlobalTypeDeclsExt.makeClone();
      this.fAllGlobalElemDecls = var1.fAllGlobalElemDecls.makeClone();
      this.fNumAnnotations = var1.fNumAnnotations;
      if (this.fNumAnnotations > 0) {
         this.fAnnotations = new XSAnnotationImpl[var1.fAnnotations.length];
         System.arraycopy(var1.fAnnotations, 0, this.fAnnotations, 0, this.fNumAnnotations);
      }

      this.fSubGroupCount = var1.fSubGroupCount;
      if (this.fSubGroupCount > 0) {
         this.fSubGroups = new XSElementDecl[var1.fSubGroups.length];
         System.arraycopy(var1.fSubGroups, 0, this.fSubGroups, 0, this.fSubGroupCount);
      }

      this.fCTCount = var1.fCTCount;
      if (this.fCTCount > 0) {
         this.fComplexTypeDecls = new XSComplexTypeDecl[var1.fComplexTypeDecls.length];
         this.fCTLocators = new SimpleLocator[var1.fCTLocators.length];
         System.arraycopy(var1.fComplexTypeDecls, 0, this.fComplexTypeDecls, 0, this.fCTCount);
         System.arraycopy(var1.fCTLocators, 0, this.fCTLocators, 0, this.fCTCount);
      }

      this.fRGCount = var1.fRGCount;
      if (this.fRGCount > 0) {
         this.fRedefinedGroupDecls = new XSGroupDecl[var1.fRedefinedGroupDecls.length];
         this.fRGLocators = new SimpleLocator[var1.fRGLocators.length];
         System.arraycopy(var1.fRedefinedGroupDecls, 0, this.fRedefinedGroupDecls, 0, this.fRGCount);
         System.arraycopy(var1.fRGLocators, 0, this.fRGLocators, 0, this.fRGCount / 2);
      }

      int var2;
      if (var1.fImported != null) {
         this.fImported = new Vector();

         for(var2 = 0; var2 < var1.fImported.size(); ++var2) {
            this.fImported.add(var1.fImported.elementAt(var2));
         }
      }

      if (var1.fLocations != null) {
         for(var2 = 0; var2 < var1.fLocations.size(); ++var2) {
            this.addDocument((Object)null, (String)var1.fLocations.elementAt(var2));
         }
      }

   }

   public XMLGrammarDescription getGrammarDescription() {
      return this.fGrammarDescription;
   }

   public boolean isNamespaceAware() {
      return true;
   }

   public void setImportedGrammars(Vector var1) {
      this.fImported = var1;
   }

   public Vector getImportedGrammars() {
      return this.fImported;
   }

   public final String getTargetNamespace() {
      return this.fTargetNamespace;
   }

   public void addGlobalAttributeDecl(XSAttributeDecl var1) {
      this.fGlobalAttrDecls.put(var1.fName, var1);
      var1.setNamespaceItem(this);
   }

   public void addGlobalAttributeDecl(XSAttributeDecl var1, String var2) {
      this.fGlobalAttrDeclsExt.put((var2 != null ? var2 : "") + "," + var1.fName, var1);
      if (var1.getNamespaceItem() == null) {
         var1.setNamespaceItem(this);
      }

   }

   public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl var1) {
      this.fGlobalAttrGrpDecls.put(var1.fName, var1);
      var1.setNamespaceItem(this);
   }

   public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl var1, String var2) {
      this.fGlobalAttrGrpDeclsExt.put((var2 != null ? var2 : "") + "," + var1.fName, var1);
      if (var1.getNamespaceItem() == null) {
         var1.setNamespaceItem(this);
      }

   }

   public void addGlobalElementDeclAll(XSElementDecl var1) {
      if (this.fAllGlobalElemDecls.get(var1) == null) {
         this.fAllGlobalElemDecls.put(var1, var1);
         if (var1.fSubGroup != null) {
            if (this.fSubGroupCount == this.fSubGroups.length) {
               this.fSubGroups = resize(this.fSubGroups, this.fSubGroupCount + 16);
            }

            this.fSubGroups[this.fSubGroupCount++] = var1;
         }
      }

   }

   public void addGlobalElementDecl(XSElementDecl var1) {
      this.fGlobalElemDecls.put(var1.fName, var1);
      var1.setNamespaceItem(this);
   }

   public void addGlobalElementDecl(XSElementDecl var1, String var2) {
      this.fGlobalElemDeclsExt.put((var2 != null ? var2 : "") + "," + var1.fName, var1);
      if (var1.getNamespaceItem() == null) {
         var1.setNamespaceItem(this);
      }

   }

   public void addGlobalGroupDecl(XSGroupDecl var1) {
      this.fGlobalGroupDecls.put(var1.fName, var1);
      var1.setNamespaceItem(this);
   }

   public void addGlobalGroupDecl(XSGroupDecl var1, String var2) {
      this.fGlobalGroupDeclsExt.put((var2 != null ? var2 : "") + "," + var1.fName, var1);
      if (var1.getNamespaceItem() == null) {
         var1.setNamespaceItem(this);
      }

   }

   public void addGlobalNotationDecl(XSNotationDecl var1) {
      this.fGlobalNotationDecls.put(var1.fName, var1);
      var1.setNamespaceItem(this);
   }

   public void addGlobalNotationDecl(XSNotationDecl var1, String var2) {
      this.fGlobalNotationDeclsExt.put((var2 != null ? var2 : "") + "," + var1.fName, var1);
      if (var1.getNamespaceItem() == null) {
         var1.setNamespaceItem(this);
      }

   }

   public void addGlobalTypeDecl(XSTypeDefinition var1) {
      this.fGlobalTypeDecls.put(var1.getName(), var1);
      if (var1 instanceof XSComplexTypeDecl) {
         ((XSComplexTypeDecl)var1).setNamespaceItem(this);
      } else if (var1 instanceof XSSimpleTypeDecl) {
         ((XSSimpleTypeDecl)var1).setNamespaceItem(this);
      }

   }

   public void addGlobalTypeDecl(XSTypeDefinition var1, String var2) {
      this.fGlobalTypeDeclsExt.put((var2 != null ? var2 : "") + "," + var1.getName(), var1);
      if (var1.getNamespaceItem() == null) {
         if (var1 instanceof XSComplexTypeDecl) {
            ((XSComplexTypeDecl)var1).setNamespaceItem(this);
         } else if (var1 instanceof XSSimpleTypeDecl) {
            ((XSSimpleTypeDecl)var1).setNamespaceItem(this);
         }
      }

   }

   public void addGlobalComplexTypeDecl(XSComplexTypeDecl var1) {
      this.fGlobalTypeDecls.put(var1.getName(), var1);
      var1.setNamespaceItem(this);
   }

   public void addGlobalComplexTypeDecl(XSComplexTypeDecl var1, String var2) {
      this.fGlobalTypeDeclsExt.put((var2 != null ? var2 : "") + "," + var1.getName(), var1);
      if (var1.getNamespaceItem() == null) {
         var1.setNamespaceItem(this);
      }

   }

   public void addGlobalSimpleTypeDecl(XSSimpleType var1) {
      this.fGlobalTypeDecls.put(var1.getName(), var1);
      if (var1 instanceof XSSimpleTypeDecl) {
         ((XSSimpleTypeDecl)var1).setNamespaceItem(this);
      }

   }

   public void addGlobalSimpleTypeDecl(XSSimpleType var1, String var2) {
      this.fGlobalTypeDeclsExt.put((var2 != null ? var2 : "") + "," + var1.getName(), var1);
      if (var1.getNamespaceItem() == null && var1 instanceof XSSimpleTypeDecl) {
         ((XSSimpleTypeDecl)var1).setNamespaceItem(this);
      }

   }

   public final void addIDConstraintDecl(XSElementDecl var1, IdentityConstraint var2) {
      var1.addIDConstraint(var2);
      this.fGlobalIDConstraintDecls.put(var2.getIdentityConstraintName(), var2);
   }

   public final void addIDConstraintDecl(XSElementDecl var1, IdentityConstraint var2, String var3) {
      this.fGlobalIDConstraintDeclsExt.put((var3 != null ? var3 : "") + "," + var2.getIdentityConstraintName(), var2);
   }

   public final XSAttributeDecl getGlobalAttributeDecl(String var1) {
      return (XSAttributeDecl)this.fGlobalAttrDecls.get(var1);
   }

   public final XSAttributeDecl getGlobalAttributeDecl(String var1, String var2) {
      return (XSAttributeDecl)this.fGlobalAttrDeclsExt.get((var2 != null ? var2 : "") + "," + var1);
   }

   public final XSAttributeGroupDecl getGlobalAttributeGroupDecl(String var1) {
      return (XSAttributeGroupDecl)this.fGlobalAttrGrpDecls.get(var1);
   }

   public final XSAttributeGroupDecl getGlobalAttributeGroupDecl(String var1, String var2) {
      return (XSAttributeGroupDecl)this.fGlobalAttrGrpDeclsExt.get((var2 != null ? var2 : "") + "," + var1);
   }

   public final XSElementDecl getGlobalElementDecl(String var1) {
      return (XSElementDecl)this.fGlobalElemDecls.get(var1);
   }

   public final XSElementDecl getGlobalElementDecl(String var1, String var2) {
      return (XSElementDecl)this.fGlobalElemDeclsExt.get((var2 != null ? var2 : "") + "," + var1);
   }

   public final XSGroupDecl getGlobalGroupDecl(String var1) {
      return (XSGroupDecl)this.fGlobalGroupDecls.get(var1);
   }

   public final XSGroupDecl getGlobalGroupDecl(String var1, String var2) {
      return (XSGroupDecl)this.fGlobalGroupDeclsExt.get((var2 != null ? var2 : "") + "," + var1);
   }

   public final XSNotationDecl getGlobalNotationDecl(String var1) {
      return (XSNotationDecl)this.fGlobalNotationDecls.get(var1);
   }

   public final XSNotationDecl getGlobalNotationDecl(String var1, String var2) {
      return (XSNotationDecl)this.fGlobalNotationDeclsExt.get((var2 != null ? var2 : "") + "," + var1);
   }

   public final XSTypeDefinition getGlobalTypeDecl(String var1) {
      return (XSTypeDefinition)this.fGlobalTypeDecls.get(var1);
   }

   public final XSTypeDefinition getGlobalTypeDecl(String var1, String var2) {
      return (XSTypeDefinition)this.fGlobalTypeDeclsExt.get((var2 != null ? var2 : "") + "," + var1);
   }

   public final IdentityConstraint getIDConstraintDecl(String var1) {
      return (IdentityConstraint)this.fGlobalIDConstraintDecls.get(var1);
   }

   public final IdentityConstraint getIDConstraintDecl(String var1, String var2) {
      return (IdentityConstraint)this.fGlobalIDConstraintDeclsExt.get((var2 != null ? var2 : "") + "," + var1);
   }

   public final boolean hasIDConstraints() {
      return this.fGlobalIDConstraintDecls.getLength() > 0;
   }

   public void addComplexTypeDecl(XSComplexTypeDecl var1, SimpleLocator var2) {
      if (this.fCTCount == this.fComplexTypeDecls.length) {
         this.fComplexTypeDecls = resize(this.fComplexTypeDecls, this.fCTCount + 16);
         this.fCTLocators = resize(this.fCTLocators, this.fCTCount + 16);
      }

      this.fCTLocators[this.fCTCount] = var2;
      this.fComplexTypeDecls[this.fCTCount++] = var1;
   }

   public void addRedefinedGroupDecl(XSGroupDecl var1, XSGroupDecl var2, SimpleLocator var3) {
      if (this.fRGCount == this.fRedefinedGroupDecls.length) {
         this.fRedefinedGroupDecls = resize(this.fRedefinedGroupDecls, this.fRGCount << 1);
         this.fRGLocators = resize(this.fRGLocators, this.fRGCount);
      }

      this.fRGLocators[this.fRGCount / 2] = var3;
      this.fRedefinedGroupDecls[this.fRGCount++] = var1;
      this.fRedefinedGroupDecls[this.fRGCount++] = var2;
   }

   final XSComplexTypeDecl[] getUncheckedComplexTypeDecls() {
      if (this.fCTCount < this.fComplexTypeDecls.length) {
         this.fComplexTypeDecls = resize(this.fComplexTypeDecls, this.fCTCount);
         this.fCTLocators = resize(this.fCTLocators, this.fCTCount);
      }

      return this.fComplexTypeDecls;
   }

   final SimpleLocator[] getUncheckedCTLocators() {
      if (this.fCTCount < this.fCTLocators.length) {
         this.fComplexTypeDecls = resize(this.fComplexTypeDecls, this.fCTCount);
         this.fCTLocators = resize(this.fCTLocators, this.fCTCount);
      }

      return this.fCTLocators;
   }

   final XSGroupDecl[] getRedefinedGroupDecls() {
      if (this.fRGCount < this.fRedefinedGroupDecls.length) {
         this.fRedefinedGroupDecls = resize(this.fRedefinedGroupDecls, this.fRGCount);
         this.fRGLocators = resize(this.fRGLocators, this.fRGCount / 2);
      }

      return this.fRedefinedGroupDecls;
   }

   final SimpleLocator[] getRGLocators() {
      if (this.fRGCount < this.fRedefinedGroupDecls.length) {
         this.fRedefinedGroupDecls = resize(this.fRedefinedGroupDecls, this.fRGCount);
         this.fRGLocators = resize(this.fRGLocators, this.fRGCount / 2);
      }

      return this.fRGLocators;
   }

   final void setUncheckedTypeNum(int var1) {
      this.fCTCount = var1;
      this.fComplexTypeDecls = resize(this.fComplexTypeDecls, this.fCTCount);
      this.fCTLocators = resize(this.fCTLocators, this.fCTCount);
   }

   final XSElementDecl[] getSubstitutionGroups() {
      if (this.fSubGroupCount < this.fSubGroups.length) {
         this.fSubGroups = resize(this.fSubGroups, this.fSubGroupCount);
      }

      return this.fSubGroups;
   }

   public static SchemaGrammar getS4SGrammar(short var0) {
      return var0 == 1 ? SG_SchemaNS : SG_SchemaNSExtended;
   }

   static final XSComplexTypeDecl[] resize(XSComplexTypeDecl[] var0, int var1) {
      XSComplexTypeDecl[] var2 = new XSComplexTypeDecl[var1];
      System.arraycopy(var0, 0, var2, 0, Math.min(var0.length, var1));
      return var2;
   }

   static final XSGroupDecl[] resize(XSGroupDecl[] var0, int var1) {
      XSGroupDecl[] var2 = new XSGroupDecl[var1];
      System.arraycopy(var0, 0, var2, 0, Math.min(var0.length, var1));
      return var2;
   }

   static final XSElementDecl[] resize(XSElementDecl[] var0, int var1) {
      XSElementDecl[] var2 = new XSElementDecl[var1];
      System.arraycopy(var0, 0, var2, 0, Math.min(var0.length, var1));
      return var2;
   }

   static final SimpleLocator[] resize(SimpleLocator[] var0, int var1) {
      SimpleLocator[] var2 = new SimpleLocator[var1];
      System.arraycopy(var0, 0, var2, 0, Math.min(var0.length, var1));
      return var2;
   }

   public synchronized void addDocument(Object var1, String var2) {
      if (this.fDocuments == null) {
         this.fDocuments = new Vector();
         this.fLocations = new Vector();
      }

      this.fDocuments.addElement(var1);
      this.fLocations.addElement(var2);
   }

   public synchronized void removeDocument(int var1) {
      if (this.fDocuments != null && var1 >= 0 && var1 < this.fDocuments.size()) {
         this.fDocuments.removeElementAt(var1);
         this.fLocations.removeElementAt(var1);
      }

   }

   public String getSchemaNamespace() {
      return this.fTargetNamespace;
   }

   synchronized DOMParser getDOMParser() {
      if (this.fDOMParser != null) {
         DOMParser var1 = (DOMParser)this.fDOMParser.get();
         if (var1 != null) {
            return var1;
         }
      }

      XML11Configuration var5 = new XML11Configuration(this.fSymbolTable);
      var5.setFeature("http://xml.org/sax/features/namespaces", true);
      var5.setFeature("http://xml.org/sax/features/validation", false);
      DOMParser var2 = new DOMParser(var5);

      try {
         var2.setFeature("http://apache.org/xml/features/dom/defer-node-expansion", false);
      } catch (SAXException var4) {
      }

      this.fDOMParser = new SoftReference(var2);
      return var2;
   }

   synchronized SAXParser getSAXParser() {
      if (this.fSAXParser != null) {
         SAXParser var1 = (SAXParser)this.fSAXParser.get();
         if (var1 != null) {
            return var1;
         }
      }

      XML11Configuration var3 = new XML11Configuration(this.fSymbolTable);
      var3.setFeature("http://xml.org/sax/features/namespaces", true);
      var3.setFeature("http://xml.org/sax/features/validation", false);
      SAXParser var2 = new SAXParser(var3);
      this.fSAXParser = new SoftReference(var2);
      return var2;
   }

   public synchronized XSNamedMap getComponents(short var1) {
      if (var1 > 0 && var1 <= 16 && GLOBAL_COMP[var1]) {
         if (this.fComponents == null) {
            this.fComponents = new XSNamedMap[17];
         }

         if (this.fComponents[var1] == null) {
            SymbolHash var2 = null;
            switch (var1) {
               case 1:
                  var2 = this.fGlobalAttrDecls;
                  break;
               case 2:
                  var2 = this.fGlobalElemDecls;
                  break;
               case 3:
               case 15:
               case 16:
                  var2 = this.fGlobalTypeDecls;
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
                  var2 = this.fGlobalAttrGrpDecls;
                  break;
               case 6:
                  var2 = this.fGlobalGroupDecls;
                  break;
               case 10:
                  var2 = this.fGlobalIDConstraintDecls;
                  break;
               case 11:
                  var2 = this.fGlobalNotationDecls;
            }

            if (var1 != 15 && var1 != 16) {
               this.fComponents[var1] = new XSNamedMapImpl(this.fTargetNamespace, var2);
            } else {
               this.fComponents[var1] = new XSNamedMap4Types(this.fTargetNamespace, var2, var1);
            }
         }

         return this.fComponents[var1];
      } else {
         return XSNamedMapImpl.EMPTY_MAP;
      }
   }

   public synchronized ObjectList getComponentsExt(short var1) {
      if (var1 > 0 && var1 <= 16 && GLOBAL_COMP[var1]) {
         if (this.fComponentsExt == null) {
            this.fComponentsExt = new ObjectList[17];
         }

         if (this.fComponentsExt[var1] == null) {
            SymbolHash var2 = null;
            switch (var1) {
               case 1:
                  var2 = this.fGlobalAttrDeclsExt;
                  break;
               case 2:
                  var2 = this.fGlobalElemDeclsExt;
                  break;
               case 3:
               case 15:
               case 16:
                  var2 = this.fGlobalTypeDeclsExt;
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
                  var2 = this.fGlobalAttrGrpDeclsExt;
                  break;
               case 6:
                  var2 = this.fGlobalGroupDeclsExt;
                  break;
               case 10:
                  var2 = this.fGlobalIDConstraintDeclsExt;
                  break;
               case 11:
                  var2 = this.fGlobalNotationDeclsExt;
            }

            Object[] var3 = var2.getEntries();
            this.fComponentsExt[var1] = new ObjectListImpl(var3, var3.length);
         }

         return this.fComponentsExt[var1];
      } else {
         return ObjectListImpl.EMPTY_LIST;
      }
   }

   public synchronized void resetComponents() {
      this.fComponents = null;
      this.fComponentsExt = null;
   }

   public XSTypeDefinition getTypeDefinition(String var1) {
      return this.getGlobalTypeDecl(var1);
   }

   public XSAttributeDeclaration getAttributeDeclaration(String var1) {
      return this.getGlobalAttributeDecl(var1);
   }

   public XSElementDeclaration getElementDeclaration(String var1) {
      return this.getGlobalElementDecl(var1);
   }

   public XSAttributeGroupDefinition getAttributeGroup(String var1) {
      return this.getGlobalAttributeGroupDecl(var1);
   }

   public XSModelGroupDefinition getModelGroupDefinition(String var1) {
      return this.getGlobalGroupDecl(var1);
   }

   public XSNotationDeclaration getNotationDeclaration(String var1) {
      return this.getGlobalNotationDecl(var1);
   }

   public XSIDCDefinition getIDCDefinition(String var1) {
      return this.getIDConstraintDecl(var1);
   }

   public StringList getDocumentLocations() {
      return new StringListImpl(this.fLocations);
   }

   public XSModel toXSModel() {
      return new XSModelImpl(new SchemaGrammar[]{this});
   }

   public XSModel toXSModel(XSGrammar[] var1) {
      if (var1 != null && var1.length != 0) {
         int var2 = var1.length;
         boolean var3 = false;

         for(int var4 = 0; var4 < var2; ++var4) {
            if (var1[var4] == this) {
               var3 = true;
               break;
            }
         }

         SchemaGrammar[] var5 = new SchemaGrammar[var3 ? var2 : var2 + 1];

         for(int var6 = 0; var6 < var2; ++var6) {
            var5[var6] = (SchemaGrammar)var1[var6];
         }

         if (!var3) {
            var5[var2] = this;
         }

         return new XSModelImpl(var5);
      } else {
         return this.toXSModel();
      }
   }

   public XSObjectList getAnnotations() {
      return this.fNumAnnotations == 0 ? XSObjectListImpl.EMPTY_LIST : new XSObjectListImpl(this.fAnnotations, this.fNumAnnotations);
   }

   public void addAnnotation(XSAnnotationImpl var1) {
      if (var1 != null) {
         if (this.fAnnotations == null) {
            this.fAnnotations = new XSAnnotationImpl[2];
         } else if (this.fNumAnnotations == this.fAnnotations.length) {
            XSAnnotationImpl[] var2 = new XSAnnotationImpl[this.fNumAnnotations << 1];
            System.arraycopy(this.fAnnotations, 0, var2, 0, this.fNumAnnotations);
            this.fAnnotations = var2;
         }

         this.fAnnotations[this.fNumAnnotations++] = var1;
      }
   }

   public void setImmutable(boolean var1) {
      this.fIsImmutable = var1;
   }

   public boolean isImmutable() {
      return this.fIsImmutable;
   }

   static {
      fAnySimpleType = (XSSimpleType)SG_SchemaNS.getGlobalTypeDecl("anySimpleType");
      SG_XSI = new BuiltinSchemaGrammar(2, (short)1);
      GLOBAL_COMP = new boolean[]{false, true, true, true, false, true, true, false, false, false, true, true, false, false, false, true, true};
   }

   private static class BuiltinAttrDecl extends XSAttributeDecl {
      public BuiltinAttrDecl(String var1, String var2, XSSimpleType var3, short var4) {
         this.fName = var1;
         super.fTargetNamespace = var2;
         this.fType = var3;
         this.fScope = var4;
      }

      public void setValues(String var1, String var2, XSSimpleType var3, short var4, short var5, ValidatedInfo var6, XSComplexTypeDecl var7) {
      }

      public void reset() {
      }

      public XSAnnotation getAnnotation() {
         return null;
      }

      public XSNamespaceItem getNamespaceItem() {
         return SchemaGrammar.SG_XSI;
      }
   }

   private static class XSAnyType extends XSComplexTypeDecl {
      public XSAnyType() {
         this.fName = "anyType";
         super.fTargetNamespace = SchemaSymbols.URI_SCHEMAFORSCHEMA;
         this.fBaseType = this;
         this.fDerivedBy = 2;
         this.fContentType = 3;
         this.fParticle = this.createParticle();
         this.fAttrGrp = this.createAttrGrp();
      }

      public void setValues(String var1, String var2, XSTypeDefinition var3, short var4, short var5, short var6, short var7, boolean var8, XSAttributeGroupDecl var9, XSSimpleType var10, XSParticleDecl var11) {
      }

      public void setName(String var1) {
      }

      public void setIsAbstractType() {
      }

      public void setContainsTypeID() {
      }

      public void setIsAnonymous() {
      }

      public void reset() {
      }

      public XSObjectList getAnnotations() {
         return XSObjectListImpl.EMPTY_LIST;
      }

      public XSNamespaceItem getNamespaceItem() {
         return SchemaGrammar.SG_SchemaNS;
      }

      private XSAttributeGroupDecl createAttrGrp() {
         XSWildcardDecl var1 = new XSWildcardDecl();
         var1.fProcessContents = 3;
         XSAttributeGroupDecl var2 = new XSAttributeGroupDecl();
         var2.fAttributeWC = var1;
         return var2;
      }

      private XSParticleDecl createParticle() {
         XSWildcardDecl var1 = new XSWildcardDecl();
         var1.fProcessContents = 3;
         XSParticleDecl var2 = new XSParticleDecl();
         var2.fMinOccurs = 0;
         var2.fMaxOccurs = -1;
         var2.fType = 2;
         var2.fValue = var1;
         XSModelGroupImpl var3 = new XSModelGroupImpl();
         var3.fCompositor = 102;
         var3.fParticleCount = 1;
         var3.fParticles = new XSParticleDecl[1];
         var3.fParticles[0] = var2;
         XSParticleDecl var4 = new XSParticleDecl();
         var4.fType = 3;
         var4.fValue = var3;
         return var4;
      }
   }

   public static final class Schema4Annotations extends SchemaGrammar {
      public static final Schema4Annotations INSTANCE = new Schema4Annotations();

      private Schema4Annotations() {
         this.fTargetNamespace = SchemaSymbols.URI_SCHEMAFORSCHEMA;
         this.fGrammarDescription = new XSDDescription();
         this.fGrammarDescription.fContextType = 3;
         this.fGrammarDescription.setNamespace(SchemaSymbols.URI_SCHEMAFORSCHEMA);
         this.fGlobalAttrDecls = new SymbolHash(1);
         this.fGlobalAttrGrpDecls = new SymbolHash(1);
         this.fGlobalElemDecls = new SymbolHash(6);
         this.fGlobalGroupDecls = new SymbolHash(1);
         this.fGlobalNotationDecls = new SymbolHash(1);
         this.fGlobalIDConstraintDecls = new SymbolHash(1);
         this.fGlobalAttrDeclsExt = new SymbolHash(1);
         this.fGlobalAttrGrpDeclsExt = new SymbolHash(1);
         this.fGlobalElemDeclsExt = new SymbolHash(6);
         this.fGlobalGroupDeclsExt = new SymbolHash(1);
         this.fGlobalNotationDeclsExt = new SymbolHash(1);
         this.fGlobalIDConstraintDeclsExt = new SymbolHash(1);
         this.fGlobalTypeDeclsExt = new SymbolHash(1);
         this.fAllGlobalElemDecls = new SymbolHash(6);
         this.fGlobalTypeDecls = SchemaGrammar.SG_SchemaNS.fGlobalTypeDecls;
         XSElementDecl var1 = this.createAnnotationElementDecl(SchemaSymbols.ELT_ANNOTATION);
         XSElementDecl var2 = this.createAnnotationElementDecl(SchemaSymbols.ELT_DOCUMENTATION);
         XSElementDecl var3 = this.createAnnotationElementDecl(SchemaSymbols.ELT_APPINFO);
         this.fGlobalElemDecls.put(var1.fName, var1);
         this.fGlobalElemDecls.put(var2.fName, var2);
         this.fGlobalElemDecls.put(var3.fName, var3);
         this.fGlobalElemDeclsExt.put("," + var1.fName, var1);
         this.fGlobalElemDeclsExt.put("," + var2.fName, var2);
         this.fGlobalElemDeclsExt.put("," + var3.fName, var3);
         this.fAllGlobalElemDecls.put(var1, var1);
         this.fAllGlobalElemDecls.put(var2, var2);
         this.fAllGlobalElemDecls.put(var3, var3);
         XSComplexTypeDecl var4 = new XSComplexTypeDecl();
         XSComplexTypeDecl var5 = new XSComplexTypeDecl();
         XSComplexTypeDecl var6 = new XSComplexTypeDecl();
         var1.fType = var4;
         var2.fType = var5;
         var3.fType = var6;
         XSAttributeGroupDecl var7 = new XSAttributeGroupDecl();
         XSAttributeGroupDecl var8 = new XSAttributeGroupDecl();
         XSAttributeGroupDecl var9 = new XSAttributeGroupDecl();
         XSAttributeUseImpl var10 = new XSAttributeUseImpl();
         var10.fAttrDecl = new XSAttributeDecl();
         var10.fAttrDecl.setValues(SchemaSymbols.ATT_ID, (String)null, (XSSimpleType)this.fGlobalTypeDecls.get("ID"), (short)0, (short)2, (ValidatedInfo)null, var4, (XSObjectList)null);
         var10.fUse = 0;
         var10.fConstraintType = 0;
         XSAttributeUseImpl var11 = new XSAttributeUseImpl();
         var11.fAttrDecl = new XSAttributeDecl();
         var11.fAttrDecl.setValues(SchemaSymbols.ATT_SOURCE, (String)null, (XSSimpleType)this.fGlobalTypeDecls.get("anyURI"), (short)0, (short)2, (ValidatedInfo)null, var5, (XSObjectList)null);
         var11.fUse = 0;
         var11.fConstraintType = 0;
         XSAttributeUseImpl var12 = new XSAttributeUseImpl();
         var12.fAttrDecl = new XSAttributeDecl();
         var12.fAttrDecl.setValues("lang".intern(), NamespaceContext.XML_URI, (XSSimpleType)this.fGlobalTypeDecls.get("language"), (short)0, (short)2, (ValidatedInfo)null, var5, (XSObjectList)null);
         var12.fUse = 0;
         var12.fConstraintType = 0;
         XSAttributeUseImpl var13 = new XSAttributeUseImpl();
         var13.fAttrDecl = new XSAttributeDecl();
         var13.fAttrDecl.setValues(SchemaSymbols.ATT_SOURCE, (String)null, (XSSimpleType)this.fGlobalTypeDecls.get("anyURI"), (short)0, (short)2, (ValidatedInfo)null, var6, (XSObjectList)null);
         var13.fUse = 0;
         var13.fConstraintType = 0;
         XSWildcardDecl var14 = new XSWildcardDecl();
         var14.fNamespaceList = new String[]{this.fTargetNamespace, null};
         var14.fType = 2;
         var14.fProcessContents = 3;
         var7.addAttributeUse(var10);
         var7.fAttributeWC = var14;
         var8.addAttributeUse(var11);
         var8.addAttributeUse(var12);
         var8.fAttributeWC = var14;
         var9.addAttributeUse(var13);
         var9.fAttributeWC = var14;
         XSParticleDecl var15 = this.createUnboundedModelGroupParticle();
         XSModelGroupImpl var16 = new XSModelGroupImpl();
         var16.fCompositor = 101;
         var16.fParticleCount = 2;
         var16.fParticles = new XSParticleDecl[2];
         var16.fParticles[0] = this.createChoiceElementParticle(var3);
         var16.fParticles[1] = this.createChoiceElementParticle(var2);
         var15.fValue = var16;
         XSParticleDecl var17 = this.createUnboundedAnyWildcardSequenceParticle();
         var4.setValues("#AnonType_" + SchemaSymbols.ELT_ANNOTATION, this.fTargetNamespace, SchemaGrammar.fAnyType, (short)2, (short)0, (short)3, (short)2, false, var7, (XSSimpleType)null, var15, XSObjectListImpl.EMPTY_LIST);
         var4.setName("#AnonType_" + SchemaSymbols.ELT_ANNOTATION);
         var4.setIsAnonymous();
         var5.setValues("#AnonType_" + SchemaSymbols.ELT_DOCUMENTATION, this.fTargetNamespace, SchemaGrammar.fAnyType, (short)2, (short)0, (short)3, (short)3, false, var8, (XSSimpleType)null, var17, XSObjectListImpl.EMPTY_LIST);
         var5.setName("#AnonType_" + SchemaSymbols.ELT_DOCUMENTATION);
         var5.setIsAnonymous();
         var6.setValues("#AnonType_" + SchemaSymbols.ELT_APPINFO, this.fTargetNamespace, SchemaGrammar.fAnyType, (short)2, (short)0, (short)3, (short)3, false, var9, (XSSimpleType)null, var17, XSObjectListImpl.EMPTY_LIST);
         var6.setName("#AnonType_" + SchemaSymbols.ELT_APPINFO);
         var6.setIsAnonymous();
      }

      public XMLGrammarDescription getGrammarDescription() {
         return this.fGrammarDescription.makeClone();
      }

      public void setImportedGrammars(Vector var1) {
      }

      public void addGlobalAttributeDecl(XSAttributeDecl var1) {
      }

      public void addGlobalAttributeDecl(XSAttributeGroupDecl var1, String var2) {
      }

      public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl var1) {
      }

      public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl var1, String var2) {
      }

      public void addGlobalElementDecl(XSElementDecl var1) {
      }

      public void addGlobalElementDecl(XSElementDecl var1, String var2) {
      }

      public void addGlobalElementDeclAll(XSElementDecl var1) {
      }

      public void addGlobalGroupDecl(XSGroupDecl var1) {
      }

      public void addGlobalGroupDecl(XSGroupDecl var1, String var2) {
      }

      public void addGlobalNotationDecl(XSNotationDecl var1) {
      }

      public void addGlobalNotationDecl(XSNotationDecl var1, String var2) {
      }

      public void addGlobalTypeDecl(XSTypeDefinition var1) {
      }

      public void addGlobalTypeDecl(XSTypeDefinition var1, String var2) {
      }

      public void addGlobalComplexTypeDecl(XSComplexTypeDecl var1) {
      }

      public void addGlobalComplexTypeDecl(XSComplexTypeDecl var1, String var2) {
      }

      public void addGlobalSimpleTypeDecl(XSSimpleType var1) {
      }

      public void addGlobalSimpleTypeDecl(XSSimpleType var1, String var2) {
      }

      public void addComplexTypeDecl(XSComplexTypeDecl var1, SimpleLocator var2) {
      }

      public void addRedefinedGroupDecl(XSGroupDecl var1, XSGroupDecl var2, SimpleLocator var3) {
      }

      public synchronized void addDocument(Object var1, String var2) {
      }

      synchronized DOMParser getDOMParser() {
         return null;
      }

      synchronized SAXParser getSAXParser() {
         return null;
      }

      private XSElementDecl createAnnotationElementDecl(String var1) {
         XSElementDecl var2 = new XSElementDecl();
         var2.fName = var1;
         var2.fTargetNamespace = this.fTargetNamespace;
         var2.setIsGlobal();
         var2.fBlock = 7;
         var2.setConstraintType((short)0);
         return var2;
      }

      private XSParticleDecl createUnboundedModelGroupParticle() {
         XSParticleDecl var1 = new XSParticleDecl();
         var1.fMinOccurs = 0;
         var1.fMaxOccurs = -1;
         var1.fType = 3;
         return var1;
      }

      private XSParticleDecl createChoiceElementParticle(XSElementDecl var1) {
         XSParticleDecl var2 = new XSParticleDecl();
         var2.fMinOccurs = 1;
         var2.fMaxOccurs = 1;
         var2.fType = 1;
         var2.fValue = var1;
         return var2;
      }

      private XSParticleDecl createUnboundedAnyWildcardSequenceParticle() {
         XSParticleDecl var1 = this.createUnboundedModelGroupParticle();
         XSModelGroupImpl var2 = new XSModelGroupImpl();
         var2.fCompositor = 102;
         var2.fParticleCount = 1;
         var2.fParticles = new XSParticleDecl[1];
         var2.fParticles[0] = this.createAnyLaxWildcardParticle();
         var1.fValue = var2;
         return var1;
      }

      private XSParticleDecl createAnyLaxWildcardParticle() {
         XSParticleDecl var1 = new XSParticleDecl();
         var1.fMinOccurs = 1;
         var1.fMaxOccurs = 1;
         var1.fType = 2;
         XSWildcardDecl var2 = new XSWildcardDecl();
         var2.fNamespaceList = null;
         var2.fType = 1;
         var2.fProcessContents = 3;
         var1.fValue = var2;
         return var1;
      }
   }

   public static class BuiltinSchemaGrammar extends SchemaGrammar {
      private static final String EXTENDED_SCHEMA_FACTORY_CLASS = "weblogic.apache.xerces.impl.dv.xs.ExtendedSchemaDVFactoryImpl";

      public BuiltinSchemaGrammar(int var1, short var2) {
         SchemaDVFactory var3;
         if (var2 == 1) {
            var3 = SchemaDVFactory.getInstance();
         } else {
            var3 = SchemaDVFactory.getInstance("weblogic.apache.xerces.impl.dv.xs.ExtendedSchemaDVFactoryImpl");
         }

         XSTypeDefinition[] var5;
         if (var1 == 1) {
            this.fTargetNamespace = SchemaSymbols.URI_SCHEMAFORSCHEMA;
            this.fGrammarDescription = new XSDDescription();
            this.fGrammarDescription.fContextType = 3;
            this.fGrammarDescription.setNamespace(SchemaSymbols.URI_SCHEMAFORSCHEMA);
            this.fGlobalAttrDecls = new SymbolHash(1);
            this.fGlobalAttrGrpDecls = new SymbolHash(1);
            this.fGlobalElemDecls = new SymbolHash(1);
            this.fGlobalGroupDecls = new SymbolHash(1);
            this.fGlobalNotationDecls = new SymbolHash(1);
            this.fGlobalIDConstraintDecls = new SymbolHash(1);
            this.fGlobalAttrDeclsExt = new SymbolHash(1);
            this.fGlobalAttrGrpDeclsExt = new SymbolHash(1);
            this.fGlobalElemDeclsExt = new SymbolHash(1);
            this.fGlobalGroupDeclsExt = new SymbolHash(1);
            this.fGlobalNotationDeclsExt = new SymbolHash(1);
            this.fGlobalIDConstraintDeclsExt = new SymbolHash(1);
            this.fGlobalTypeDeclsExt = new SymbolHash(1);
            this.fAllGlobalElemDecls = new SymbolHash(1);
            this.fGlobalTypeDecls = var3.getBuiltInTypes();
            int var4 = this.fGlobalTypeDecls.getLength();
            var5 = new XSTypeDefinition[var4];
            this.fGlobalTypeDecls.getValues(var5, 0);

            for(int var6 = 0; var6 < var4; ++var6) {
               XSTypeDefinition var7 = var5[var6];
               if (var7 instanceof XSSimpleTypeDecl) {
                  ((XSSimpleTypeDecl)var7).setNamespaceItem(this);
               }
            }

            this.fGlobalTypeDecls.put(SchemaGrammar.fAnyType.getName(), SchemaGrammar.fAnyType);
         } else if (var1 == 2) {
            this.fTargetNamespace = SchemaSymbols.URI_XSI;
            this.fGrammarDescription = new XSDDescription();
            this.fGrammarDescription.fContextType = 3;
            this.fGrammarDescription.setNamespace(SchemaSymbols.URI_XSI);
            this.fGlobalAttrGrpDecls = new SymbolHash(1);
            this.fGlobalElemDecls = new SymbolHash(1);
            this.fGlobalGroupDecls = new SymbolHash(1);
            this.fGlobalNotationDecls = new SymbolHash(1);
            this.fGlobalIDConstraintDecls = new SymbolHash(1);
            this.fGlobalTypeDecls = new SymbolHash(1);
            this.fGlobalAttrDeclsExt = new SymbolHash(1);
            this.fGlobalAttrGrpDeclsExt = new SymbolHash(1);
            this.fGlobalElemDeclsExt = new SymbolHash(1);
            this.fGlobalGroupDeclsExt = new SymbolHash(1);
            this.fGlobalNotationDeclsExt = new SymbolHash(1);
            this.fGlobalIDConstraintDeclsExt = new SymbolHash(1);
            this.fGlobalTypeDeclsExt = new SymbolHash(1);
            this.fAllGlobalElemDecls = new SymbolHash(1);
            this.fGlobalAttrDecls = new SymbolHash(8);
            String var9 = null;
            var5 = null;
            XSSimpleType var11 = null;
            byte var12 = 1;
            var9 = SchemaSymbols.XSI_TYPE;
            String var10 = SchemaSymbols.URI_XSI;
            var11 = var3.getBuiltInType("QName");
            this.fGlobalAttrDecls.put(var9, new BuiltinAttrDecl(var9, var10, var11, var12));
            var9 = SchemaSymbols.XSI_NIL;
            var10 = SchemaSymbols.URI_XSI;
            var11 = var3.getBuiltInType("boolean");
            this.fGlobalAttrDecls.put(var9, new BuiltinAttrDecl(var9, var10, var11, var12));
            XSSimpleType var8 = var3.getBuiltInType("anyURI");
            var9 = SchemaSymbols.XSI_SCHEMALOCATION;
            var10 = SchemaSymbols.URI_XSI;
            var11 = var3.createTypeList("#AnonType_schemaLocation", SchemaSymbols.URI_XSI, (short)0, var8, (XSObjectList)null);
            if (var11 instanceof XSSimpleTypeDecl) {
               ((XSSimpleTypeDecl)var11).setAnonymous(true);
            }

            this.fGlobalAttrDecls.put(var9, new BuiltinAttrDecl(var9, var10, var11, var12));
            var9 = SchemaSymbols.XSI_NONAMESPACESCHEMALOCATION;
            var10 = SchemaSymbols.URI_XSI;
            this.fGlobalAttrDecls.put(var9, new BuiltinAttrDecl(var9, var10, var8, var12));
         }

      }

      public XMLGrammarDescription getGrammarDescription() {
         return this.fGrammarDescription.makeClone();
      }

      public void setImportedGrammars(Vector var1) {
      }

      public void addGlobalAttributeDecl(XSAttributeDecl var1) {
      }

      public void addGlobalAttributeDecl(XSAttributeDecl var1, String var2) {
      }

      public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl var1) {
      }

      public void addGlobalAttributeGroupDecl(XSAttributeGroupDecl var1, String var2) {
      }

      public void addGlobalElementDecl(XSElementDecl var1) {
      }

      public void addGlobalElementDecl(XSElementDecl var1, String var2) {
      }

      public void addGlobalElementDeclAll(XSElementDecl var1) {
      }

      public void addGlobalGroupDecl(XSGroupDecl var1) {
      }

      public void addGlobalGroupDecl(XSGroupDecl var1, String var2) {
      }

      public void addGlobalNotationDecl(XSNotationDecl var1) {
      }

      public void addGlobalNotationDecl(XSNotationDecl var1, String var2) {
      }

      public void addGlobalTypeDecl(XSTypeDefinition var1) {
      }

      public void addGlobalTypeDecl(XSTypeDefinition var1, String var2) {
      }

      public void addGlobalComplexTypeDecl(XSComplexTypeDecl var1) {
      }

      public void addGlobalComplexTypeDecl(XSComplexTypeDecl var1, String var2) {
      }

      public void addGlobalSimpleTypeDecl(XSSimpleType var1) {
      }

      public void addGlobalSimpleTypeDecl(XSSimpleType var1, String var2) {
      }

      public void addComplexTypeDecl(XSComplexTypeDecl var1, SimpleLocator var2) {
      }

      public void addRedefinedGroupDecl(XSGroupDecl var1, XSGroupDecl var2, SimpleLocator var3) {
      }

      public synchronized void addDocument(Object var1, String var2) {
      }

      synchronized DOMParser getDOMParser() {
         return null;
      }

      synchronized SAXParser getSAXParser() {
         return null;
      }
   }
}
