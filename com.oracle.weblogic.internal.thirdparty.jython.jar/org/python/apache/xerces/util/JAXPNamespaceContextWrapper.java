package org.python.apache.xerces.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;
import org.python.apache.xerces.xni.NamespaceContext;

public final class JAXPNamespaceContextWrapper implements NamespaceContext {
   private javax.xml.namespace.NamespaceContext fNamespaceContext;
   private SymbolTable fSymbolTable;
   private List fPrefixes;
   private final Vector fAllPrefixes = new Vector();
   private int[] fContext = new int[8];
   private int fCurrentContext;

   public JAXPNamespaceContextWrapper(SymbolTable var1) {
      this.setSymbolTable(var1);
   }

   public void setNamespaceContext(javax.xml.namespace.NamespaceContext var1) {
      this.fNamespaceContext = var1;
   }

   public javax.xml.namespace.NamespaceContext getNamespaceContext() {
      return this.fNamespaceContext;
   }

   public void setSymbolTable(SymbolTable var1) {
      this.fSymbolTable = var1;
   }

   public SymbolTable getSymbolTable() {
      return this.fSymbolTable;
   }

   public void setDeclaredPrefixes(List var1) {
      this.fPrefixes = var1;
   }

   public List getDeclaredPrefixes() {
      return this.fPrefixes;
   }

   public String getURI(String var1) {
      if (this.fNamespaceContext != null) {
         String var2 = this.fNamespaceContext.getNamespaceURI(var1);
         if (var2 != null && !"".equals(var2)) {
            return this.fSymbolTable != null ? this.fSymbolTable.addSymbol(var2) : var2.intern();
         }
      }

      return null;
   }

   public String getPrefix(String var1) {
      if (this.fNamespaceContext != null) {
         if (var1 == null) {
            var1 = "";
         }

         String var2 = this.fNamespaceContext.getPrefix(var1);
         if (var2 == null) {
            var2 = "";
         }

         return this.fSymbolTable != null ? this.fSymbolTable.addSymbol(var2) : var2.intern();
      } else {
         return null;
      }
   }

   public Enumeration getAllPrefixes() {
      return Collections.enumeration(new TreeSet(this.fAllPrefixes));
   }

   public void pushContext() {
      if (this.fCurrentContext + 1 == this.fContext.length) {
         int[] var1 = new int[this.fContext.length * 2];
         System.arraycopy(this.fContext, 0, var1, 0, this.fContext.length);
         this.fContext = var1;
      }

      this.fContext[++this.fCurrentContext] = this.fAllPrefixes.size();
      if (this.fPrefixes != null) {
         this.fAllPrefixes.addAll(this.fPrefixes);
      }

   }

   public void popContext() {
      this.fAllPrefixes.setSize(this.fContext[this.fCurrentContext--]);
   }

   public boolean declarePrefix(String var1, String var2) {
      return true;
   }

   public int getDeclaredPrefixCount() {
      return this.fPrefixes != null ? this.fPrefixes.size() : 0;
   }

   public String getDeclaredPrefixAt(int var1) {
      return (String)this.fPrefixes.get(var1);
   }

   public void reset() {
      this.fCurrentContext = 0;
      this.fContext[this.fCurrentContext] = 0;
      this.fAllPrefixes.clear();
   }
}
