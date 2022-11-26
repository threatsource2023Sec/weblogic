package com.bea.xml.stream.util;

import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;

public class NamespaceContextImpl implements NamespaceContext {
   SymbolTable prefixTable = new SymbolTable();
   SymbolTable uriTable = new SymbolTable();
   NamespaceContext rootContext;

   public NamespaceContextImpl() {
      this.init();
   }

   public NamespaceContextImpl(NamespaceContext rootContext) {
      this.rootContext = null;
      this.init();
   }

   public void init() {
      this.bindNamespace("xml", "http://www.w3.org/XML/1998/namespace");
      this.bindNamespace("xmlns", "http://www.w3.org/XML/1998/namespace");
   }

   public void openScope() {
      this.prefixTable.openScope();
      this.uriTable.openScope();
   }

   public void closeScope() {
      this.prefixTable.closeScope();
      this.uriTable.closeScope();
   }

   public void bindNamespace(String prefix, String uri) {
      this.prefixTable.put(prefix, uri);
      this.uriTable.put(uri, prefix);
   }

   public int getDepth() {
      return this.prefixTable.getDepth();
   }

   public String getNamespaceURI(String prefix) {
      String value = this.prefixTable.get(prefix);
      return value == null && this.rootContext != null ? this.rootContext.getNamespaceURI(prefix) : value;
   }

   public String getPrefix(String uri) {
      String value = this.uriTable.get(uri);
      return value == null && this.rootContext != null ? this.rootContext.getPrefix(uri) : value;
   }

   public void bindDefaultNameSpace(String uri) {
      this.bindNamespace("", uri);
   }

   public void unbindDefaultNameSpace() {
      this.bindNamespace("", (String)null);
   }

   public void unbindNamespace(String prefix, String uri) {
      this.prefixTable.put(prefix, (String)null);
      this.prefixTable.put(uri, (String)null);
   }

   public String getDefaultNameSpace() {
      return this.getNamespaceURI("");
   }

   public Iterator getPrefixes(String uri) {
      return this.uriTable.getAll(uri).iterator();
   }

   public static void main(String[] args) throws Exception {
      NamespaceContextImpl nci = new NamespaceContextImpl();
      nci.openScope();
      nci.bindNamespace("a", "uri");
      nci.bindNamespace("b", "uri");
      System.out.println("a=" + nci.getNamespaceURI("a"));
      System.out.println("uri=" + nci.getPrefix("uri"));
      Iterator vals = nci.getPrefixes("uri");

      while(vals.hasNext()) {
         System.out.println("1 uri->" + vals.next());
      }

      nci.openScope();
      nci.bindNamespace("a", "uri2");
      vals = nci.getPrefixes("uri");

      while(vals.hasNext()) {
         System.out.println("2 uri->" + vals.next());
      }

      nci.closeScope();
      nci.closeScope();
   }
}
