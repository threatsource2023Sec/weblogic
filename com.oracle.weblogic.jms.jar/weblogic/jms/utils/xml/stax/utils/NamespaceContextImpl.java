package weblogic.jms.utils.xml.stax.utils;

import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.NamespaceContext;
import weblogic.jms.utils.xml.babel.baseparser.SymbolTable;

public class NamespaceContextImpl implements NamespaceContext {
   SymbolTable prefixTable = new SymbolTable();
   SymbolTable uriTable = new SymbolTable();
   NamespaceContext rootContext;

   public NamespaceContextImpl() {
      this.init();
   }

   public NamespaceContextImpl(NamespaceContext rootContext) {
      this.rootContext = rootContext;
      this.init();
   }

   public void init() {
      this.bindNamespace("xml", "http://www.w3.org/XML/1998/namespace");
      this.bindNamespace("xmlns", "http://www.w3.org/2000/xmlns/");
      this.bindNamespace("xmlns", "http://www.w3.org/2000/xmlns/");
   }

   public void openScope() {
      this.prefixTable.openScope();
      this.uriTable.openScope();
   }

   public final List closeScope(boolean collect_outofscope_entries) {
      this.uriTable.closeScope(false);
      return this.prefixTable.closeScope(collect_outofscope_entries);
   }

   public final List closeScope() {
      return this.closeScope(true);
   }

   public void bindNamespace(String prefix, String uri) {
      this.prefixTable.put(prefix, uri);
      this.uriTable.put(uri, prefix);
   }

   public int getDepth() {
      return this.prefixTable.getDepth();
   }

   public String getNamespaceURI(String prefix) {
      if (prefix == null) {
         throw new IllegalArgumentException("The prefix may not be null");
      } else {
         String value = this.prefixTable.get(prefix);
         return value == null && this.rootContext != null ? this.rootContext.getNamespaceURI(prefix) : value;
      }
   }

   public String getPrefix(String uri) {
      if (uri == null) {
         throw new IllegalArgumentException("The uri may not be null.");
      } else {
         String value = null;
         value = this.uriTable.get(uri);
         return value == null && this.rootContext != null ? this.rootContext.getPrefix(uri) : value;
      }
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
