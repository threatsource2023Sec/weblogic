package com.bea.staxb.runtime.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.xml.namespace.NamespaceContext;

final class ScopedNamespaceContext implements NamespaceContext {
   private final Stack scopeStack;
   private LLNamespaceContext current;
   private String currentDefaultNamespace;
   private static final List XML_NS_PREFIX_LIST = Collections.singletonList("xml");
   private static final List XMLNS_ATTRIBUTE_LIST = Collections.singletonList("xmlns");

   public ScopedNamespaceContext() {
      this((LLNamespaceContext)null, new Stack());
   }

   private ScopedNamespaceContext(LLNamespaceContext head, Stack scopeStack) {
      this.current = head;
      this.scopeStack = scopeStack;
   }

   public ScopedNamespaceContext(NamespaceContext root_nsctx) {
      this((LLNamespaceContext)null, new Stack());
   }

   public void clear() {
      this.scopeStack.clear();
      this.current = null;
   }

   public String getNamespaceURI(String prefix) {
      if (prefix == null) {
         throw new IllegalArgumentException("null prefix");
      } else if ("xml".equals(prefix)) {
         return "http://www.w3.org/XML/1998/namespace";
      } else if ("xmlns".equals(prefix)) {
         return "http://www.w3.org/2000/xmlns/";
      } else {
         return this.current == null ? null : this.current.getNamespaceURI(prefix);
      }
   }

   public String getPrefix(String namespaceURI) {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("null namespaceURI");
      } else if ("http://www.w3.org/XML/1998/namespace".equals(namespaceURI)) {
         return "xml";
      } else if ("http://www.w3.org/2000/xmlns/".equals(namespaceURI)) {
         return "xmlns";
      } else if (this.current == null) {
         return null;
      } else {
         return namespaceURI.equals(this.currentDefaultNamespace) ? "" : this.current.getPrefix(namespaceURI);
      }
   }

   public Iterator getPrefixes(String namespaceURI) {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("null namespaceURI");
      } else if ("".equals(namespaceURI)) {
         throw new IllegalArgumentException("zero length namespaceURI");
      } else if ("http://www.w3.org/XML/1998/namespace".equals(namespaceURI)) {
         return XML_NS_PREFIX_LIST.iterator();
      } else {
         return "http://www.w3.org/2000/xmlns/".equals(namespaceURI) ? XMLNS_ATTRIBUTE_LIST.iterator() : this.current.getPrefixes(namespaceURI);
      }
   }

   public boolean scopeOpened() {
      return !this.scopeStack.isEmpty();
   }

   public void openScope() {
      this.scopeStack.push(this.current);
   }

   public void closeScope() {
      if (!this.scopeOpened()) {
         throw new IllegalStateException("no scope currently open");
      } else {
         LLNamespaceContext previous = (LLNamespaceContext)this.scopeStack.pop();
         if (previous == null) {
            if (!this.scopeOpened()) {
               this.currentDefaultNamespace = null;
            }
         } else {
            this.updateDefaultNamespace(previous.getPrefix(), previous.getNamespace());
         }

         this.current = previous;
      }
   }

   private void updateDefaultNamespace(String prefix, String namespace) {
      if ("".equals(prefix)) {
         if ("".equals(namespace)) {
            this.currentDefaultNamespace = null;
         } else {
            this.currentDefaultNamespace = namespace;
         }
      }

   }

   public void bindNamespace(String prefix, String namespace) {
      if (namespace == null) {
         throw new IllegalArgumentException("null namespace not allowed");
      } else if (prefix == null) {
         throw new IllegalArgumentException("null prefix");
      } else if ("".equals(namespace) && !"".equals(prefix)) {
         throw new IllegalArgumentException("cannot bind prefix" + prefix + " to empty namespace");
      } else if (!this.scopeOpened()) {
         throw new IllegalStateException("no scope currently open");
      } else {
         this.updateDefaultNamespace(prefix, namespace);
         this.current = new LLNamespaceContext(prefix, namespace, this.current);
      }
   }

   public int getDepth() {
      return this.scopeStack.size();
   }

   public Map getNamespaceMap() {
      return getNamespaceMap(this.current);
   }

   public int getCurrentScopeNamespaceCount() {
      if (this.current == null) {
         return 0;
      } else {
         LLNamespaceContext top = (LLNamespaceContext)this.scopeStack.peek();
         int cnt = 0;

         for(LLNamespaceContext cur = this.current; cur != top; cur = cur.getPredecessor()) {
            ++cnt;
         }

         return cnt;
      }
   }

   public String getCurrentScopeNamespacePrefix(int i) {
      return this.getCurrentScopeNamespace(i).getPrefix();
   }

   public String getCurrentScopeNamespaceURI(int i) {
      return this.getCurrentScopeNamespace(i).getNamespace();
   }

   private LLNamespaceContext getCurrentScopeNamespace(int i) {
      if (i < 0) {
         throw new IllegalArgumentException("negative index: " + i);
      } else {
         LLNamespaceContext top = (LLNamespaceContext)this.scopeStack.peek();
         int cnt = 0;

         for(LLNamespaceContext cur = this.current; cur != top; cur = cur.getPredecessor()) {
            if (cnt == i) {
               return cur;
            }

            ++cnt;
         }

         throw new IndexOutOfBoundsException("index of out range: " + i);
      }
   }

   private static final Map getNamespaceMap(LLNamespaceContext ctx) {
      if (ctx == null) {
         return new HashMap();
      } else {
         Map result = getNamespaceMap(ctx.predecessor);
         result.put(ctx.prefix, ctx.namespace);
         return result;
      }
   }

   private static final class LLNamespaceContext {
      private final String prefix;
      private final String namespace;
      private final LLNamespaceContext predecessor;

      LLNamespaceContext(String prefix, String uri, LLNamespaceContext predecessor) {
         this.prefix = prefix;
         this.namespace = uri;
         this.predecessor = predecessor;
      }

      final String getNamespace() {
         return this.namespace;
      }

      final String getPrefix() {
         return this.prefix;
      }

      final LLNamespaceContext getPredecessor() {
         return this.predecessor;
      }

      String getNamespaceURI(String prefix) {
         for(LLNamespaceContext entry = this; entry != null; entry = entry.predecessor) {
            if (prefix.equals(entry.prefix)) {
               return entry.namespace;
            }
         }

         return null;
      }

      String getPrefix(String namespaceURI) {
         for(LLNamespaceContext entry = this; entry != null; entry = entry.predecessor) {
            if (namespaceURI.equals(entry.namespace)) {
               return entry.prefix;
            }
         }

         return null;
      }

      Iterator getPrefixes(String namespaceURI) {
         Set result = getPrefixSet(namespaceURI, this);
         return result.iterator();
      }

      static final Set getPrefixSet(String namespace, LLNamespaceContext context) {
         Stack reversed = new Stack();

         LLNamespaceContext current;
         for(current = context; current != null; current = current.predecessor) {
            reversed.push(current);
         }

         Set result = new HashSet(4);

         while(!reversed.isEmpty()) {
            current = (LLNamespaceContext)reversed.pop();
            if (namespace.equals(current.namespace)) {
               result.add(current.prefix);
            } else {
               result.remove(current.prefix);
            }
         }

         return result;
      }
   }
}
