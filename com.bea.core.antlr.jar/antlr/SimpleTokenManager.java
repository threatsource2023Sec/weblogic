package antlr;

import antlr.collections.impl.Vector;
import java.util.Enumeration;
import java.util.Hashtable;

class SimpleTokenManager implements TokenManager, Cloneable {
   protected int maxToken = 4;
   protected Vector vocabulary;
   private Hashtable table;
   protected Tool antlrTool;
   protected String name;
   protected boolean readOnly = false;

   SimpleTokenManager(String var1, Tool var2) {
      this.antlrTool = var2;
      this.name = var1;
      this.vocabulary = new Vector(1);
      this.table = new Hashtable();
      TokenSymbol var3 = new TokenSymbol("EOF");
      var3.setTokenType(1);
      this.define(var3);
      this.vocabulary.ensureCapacity(3);
      this.vocabulary.setElementAt("NULL_TREE_LOOKAHEAD", 3);
   }

   public Object clone() {
      try {
         SimpleTokenManager var1 = (SimpleTokenManager)super.clone();
         var1.vocabulary = (Vector)this.vocabulary.clone();
         var1.table = (Hashtable)this.table.clone();
         var1.maxToken = this.maxToken;
         var1.antlrTool = this.antlrTool;
         var1.name = this.name;
         return var1;
      } catch (CloneNotSupportedException var3) {
         this.antlrTool.panic("cannot clone token manager");
         return null;
      }
   }

   public void define(TokenSymbol var1) {
      this.vocabulary.ensureCapacity(var1.getTokenType());
      this.vocabulary.setElementAt(var1.getId(), var1.getTokenType());
      this.mapToTokenSymbol(var1.getId(), var1);
   }

   public String getName() {
      return this.name;
   }

   public String getTokenStringAt(int var1) {
      return (String)this.vocabulary.elementAt(var1);
   }

   public TokenSymbol getTokenSymbol(String var1) {
      return (TokenSymbol)this.table.get(var1);
   }

   public TokenSymbol getTokenSymbolAt(int var1) {
      return this.getTokenSymbol(this.getTokenStringAt(var1));
   }

   public Enumeration getTokenSymbolElements() {
      return this.table.elements();
   }

   public Enumeration getTokenSymbolKeys() {
      return this.table.keys();
   }

   public Vector getVocabulary() {
      return this.vocabulary;
   }

   public boolean isReadOnly() {
      return false;
   }

   public void mapToTokenSymbol(String var1, TokenSymbol var2) {
      this.table.put(var1, var2);
   }

   public int maxTokenType() {
      return this.maxToken - 1;
   }

   public int nextTokenType() {
      return this.maxToken++;
   }

   public void setName(String var1) {
      this.name = var1;
   }

   public void setReadOnly(boolean var1) {
      this.readOnly = var1;
   }

   public boolean tokenDefined(String var1) {
      return this.table.containsKey(var1);
   }
}
