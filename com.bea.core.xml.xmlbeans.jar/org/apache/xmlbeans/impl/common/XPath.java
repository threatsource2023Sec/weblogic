package org.apache.xmlbeans.impl.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlException;

public class XPath {
   public static final String _NS_BOUNDARY = "$xmlbeans!ns_boundary";
   public static final String _DEFAULT_ELT_NS = "$xmlbeans!default_uri";
   private final Selector _selector;
   private final boolean _sawDeepDot;

   public static XPath compileXPath(String xpath) throws XPathCompileException {
      return compileXPath(xpath, "$this", (Map)null);
   }

   public static XPath compileXPath(String xpath, String currentNodeVar) throws XPathCompileException {
      return compileXPath(xpath, currentNodeVar, (Map)null);
   }

   public static XPath compileXPath(String xpath, Map namespaces) throws XPathCompileException {
      return compileXPath(xpath, "$this", namespaces);
   }

   public static XPath compileXPath(String xpath, String currentNodeVar, Map namespaces) throws XPathCompileException {
      return (new CompilationContext(namespaces, currentNodeVar)).compile(xpath);
   }

   private XPath(Selector selector, boolean sawDeepDot) {
      this._selector = selector;
      this._sawDeepDot = sawDeepDot;
   }

   public boolean sawDeepDot() {
      return this._sawDeepDot;
   }

   // $FF: synthetic method
   XPath(Selector x0, boolean x1, Object x2) {
      this(x0, x1);
   }

   private static final class Selector {
      final Step[] _paths;

      Selector(Step[] paths) {
         this._paths = paths;
      }
   }

   private static final class Step {
      final boolean _attr;
      final boolean _deep;
      int _flags;
      final QName _name;
      Step _next;
      Step _prev;
      boolean _hasBacktrack;
      Step _backtrack;

      Step(boolean deep, boolean attr, QName name) {
         this._name = name;
         this._deep = deep;
         this._attr = attr;
         int flags = 0;
         if (this._deep || !this._attr) {
            flags |= 2;
         }

         if (this._attr) {
            flags |= 4;
         }

         this._flags = flags;
      }

      boolean isWild() {
         return this._name.getLocalPart().length() == 0;
      }

      boolean match(QName name) {
         String local = this._name.getLocalPart();
         String nameLocal = name.getLocalPart();
         int localLength = local.length();
         String uri;
         if (localLength == 0) {
            uri = this._name.getNamespaceURI();
            int uriLength = uri.length();
            return uriLength == 0 ? true : uri.equals(name.getNamespaceURI());
         } else if (localLength != nameLocal.length()) {
            return false;
         } else {
            uri = this._name.getNamespaceURI();
            String nameUri = name.getNamespaceURI();
            if (uri.length() != nameUri.length()) {
               return false;
            } else {
               return local.equals(nameLocal) && uri.equals(nameUri);
            }
         }
      }
   }

   private static class CompilationContext {
      private String _expr;
      private boolean _sawDeepDot;
      private boolean _lastDeepDot;
      private String _currentNodeVar;
      protected Map _namespaces;
      private Map _externalNamespaces;
      private int _offset;
      private int _line;
      private int _column;

      CompilationContext(Map namespaces, String currentNodeVar) {
         assert this._currentNodeVar == null || this._currentNodeVar.startsWith("$");

         if (currentNodeVar == null) {
            this._currentNodeVar = "$this";
         } else {
            this._currentNodeVar = currentNodeVar;
         }

         this._namespaces = new HashMap();
         this._externalNamespaces = (Map)(namespaces == null ? new HashMap() : namespaces);
      }

      XPath compile(String expr) throws XPathCompileException {
         this._offset = 0;
         this._line = 1;
         this._column = 1;
         this._expr = expr;
         return this.tokenizeXPath();
      }

      int currChar() {
         return this.currChar(0);
      }

      int currChar(int offset) {
         return this._offset + offset >= this._expr.length() ? -1 : this._expr.charAt(this._offset + offset);
      }

      void advance() {
         if (this._offset < this._expr.length()) {
            char ch = this._expr.charAt(this._offset);
            ++this._offset;
            ++this._column;
            if (ch == '\r' || ch == '\n') {
               ++this._line;
               this._column = 1;
               if (this._offset + 1 < this._expr.length()) {
                  char nextCh = this._expr.charAt(this._offset + 1);
                  if ((nextCh == '\r' || nextCh == '\n') && ch != nextCh) {
                     ++this._offset;
                  }
               }
            }
         }

      }

      void advance(int count) {
         assert count >= 0;

         while(count-- > 0) {
            this.advance();
         }

      }

      boolean isWhitespace() {
         return this.isWhitespace(0);
      }

      boolean isWhitespace(int offset) {
         int ch = this.currChar(offset);
         return ch == 32 || ch == 9 || ch == 10 || ch == 13;
      }

      boolean isNCNameStart() {
         return this.currChar() == -1 ? false : XMLChar.isNCNameStart(this.currChar());
      }

      boolean isNCName() {
         return this.currChar() == -1 ? false : XMLChar.isNCName(this.currChar());
      }

      boolean startsWith(String s) {
         return this.startsWith(s, 0);
      }

      boolean startsWith(String s, int offset) {
         return this._offset + offset >= this._expr.length() ? false : this._expr.startsWith(s, this._offset + offset);
      }

      private XPathCompileException newError(String msg) {
         XmlError err = XmlError.forLocation(msg, 0, (String)null, this._line, this._column, this._offset);
         return new XPathCompileException(err);
      }

      String lookupPrefix(String prefix) throws XPathCompileException {
         if (this._namespaces.containsKey(prefix)) {
            return (String)this._namespaces.get(prefix);
         } else if (this._externalNamespaces.containsKey(prefix)) {
            return (String)this._externalNamespaces.get(prefix);
         } else if (prefix.equals("xml")) {
            return "http://www.w3.org/XML/1998/namespace";
         } else if (prefix.equals("xs")) {
            return "http://www.w3.org/2001/XMLSchema";
         } else if (prefix.equals("xsi")) {
            return "http://www.w3.org/2001/XMLSchema-instance";
         } else if (prefix.equals("fn")) {
            return "http://www.w3.org/2002/11/xquery-functions";
         } else if (prefix.equals("xdt")) {
            return "http://www.w3.org/2003/11/xpath-datatypes";
         } else if (prefix.equals("local")) {
            return "http://www.w3.org/2003/11/xquery-local-functions";
         } else {
            throw this.newError("Undefined prefix: " + prefix);
         }
      }

      private boolean parseWhitespace() throws XPathCompileException {
         boolean sawSpace;
         for(sawSpace = false; this.isWhitespace(); sawSpace = true) {
            this.advance();
         }

         return sawSpace;
      }

      private boolean tokenize(String s) {
         assert s.length() > 0;

         int offset;
         for(offset = 0; this.isWhitespace(offset); ++offset) {
         }

         if (!this.startsWith(s, offset)) {
            return false;
         } else {
            offset += s.length();
            this.advance(offset);
            return true;
         }
      }

      private boolean tokenize(String s1, String s2) {
         assert s1.length() > 0;

         assert s2.length() > 0;

         int offset;
         for(offset = 0; this.isWhitespace(offset); ++offset) {
         }

         if (!this.startsWith(s1, offset)) {
            return false;
         } else {
            for(offset += s1.length(); this.isWhitespace(offset); ++offset) {
            }

            if (!this.startsWith(s2, offset)) {
               return false;
            } else {
               offset += s2.length();
               this.advance(offset);
               return true;
            }
         }
      }

      private boolean tokenize(String s1, String s2, String s3) {
         assert s1.length() > 0;

         assert s2.length() > 0;

         assert s3.length() > 0;

         int offset;
         for(offset = 0; this.isWhitespace(offset); ++offset) {
         }

         if (!this.startsWith(s1, offset)) {
            return false;
         } else {
            for(offset += s1.length(); this.isWhitespace(offset); ++offset) {
            }

            if (!this.startsWith(s2, offset)) {
               return false;
            } else {
               for(offset += s2.length(); this.isWhitespace(offset); ++offset) {
               }

               if (!this.startsWith(s3, offset)) {
                  return false;
               } else {
                  for(offset += s3.length(); this.isWhitespace(offset); ++offset) {
                  }

                  this.advance(offset);
                  return true;
               }
            }
         }
      }

      private boolean tokenize(String s1, String s2, String s3, String s4) {
         assert s1.length() > 0;

         assert s2.length() > 0;

         assert s3.length() > 0;

         assert s4.length() > 0;

         int offset;
         for(offset = 0; this.isWhitespace(offset); ++offset) {
         }

         if (!this.startsWith(s1, offset)) {
            return false;
         } else {
            for(offset += s1.length(); this.isWhitespace(offset); ++offset) {
            }

            if (!this.startsWith(s2, offset)) {
               return false;
            } else {
               for(offset += s2.length(); this.isWhitespace(offset); ++offset) {
               }

               if (!this.startsWith(s3, offset)) {
                  return false;
               } else {
                  for(offset += s3.length(); this.isWhitespace(offset); ++offset) {
                  }

                  if (!this.startsWith(s4, offset)) {
                     return false;
                  } else {
                     offset += s4.length();
                     this.advance(offset);
                     return true;
                  }
               }
            }
         }
      }

      private String tokenizeNCName() throws XPathCompileException {
         this.parseWhitespace();
         if (!this.isNCNameStart()) {
            throw this.newError("Expected non-colonized name");
         } else {
            StringBuffer sb = new StringBuffer();
            sb.append((char)this.currChar());
            this.advance();

            while(this.isNCName()) {
               sb.append((char)this.currChar());
               this.advance();
            }

            return sb.toString();
         }
      }

      private QName getAnyQName() {
         return new QName("", "");
      }

      private QName tokenizeQName() throws XPathCompileException {
         if (this.tokenize("*")) {
            return this.getAnyQName();
         } else {
            String ncName = this.tokenizeNCName();
            return !this.tokenize(":") ? new QName(this.lookupPrefix(""), ncName) : new QName(this.lookupPrefix(ncName), this.tokenize("*") ? "" : this.tokenizeNCName());
         }
      }

      private String tokenizeQuotedUri() throws XPathCompileException {
         byte quote;
         if (this.tokenize("\"")) {
            quote = 34;
         } else {
            if (!this.tokenize("'")) {
               throw this.newError("Expected quote (\" or ')");
            }

            quote = 39;
         }

         StringBuffer sb = new StringBuffer();

         while(this.currChar() != -1) {
            if (this.currChar() == quote) {
               this.advance();
               if (this.currChar() != quote) {
                  return sb.toString();
               }
            }

            sb.append((char)this.currChar());
            this.advance();
         }

         throw this.newError("Path terminated in URI literal");
      }

      private Step addStep(boolean deep, boolean attr, QName name, Step steps) {
         Step step = new Step(deep, attr, name);
         if (steps == null) {
            return step;
         } else {
            Step s;
            for(s = steps; steps._next != null; steps = steps._next) {
            }

            steps._next = step;
            step._prev = steps;
            return s;
         }
      }

      private Step tokenizeSteps() throws XPathCompileException {
         if (this.tokenize("/")) {
            throw this.newError("Absolute paths unsupported");
         } else {
            boolean deep;
            if (!this.tokenize("$", this._currentNodeVar, "//") && !this.tokenize(".", "//")) {
               if (!this.tokenize("$", this._currentNodeVar, "/") && !this.tokenize(".", "/")) {
                  if (this.tokenize("$", this._currentNodeVar) || this.tokenize(".")) {
                     return this.addStep(false, false, (QName)null, (Step)null);
                  }

                  deep = false;
               } else {
                  deep = false;
               }
            } else {
               deep = true;
            }

            Step steps = null;
            boolean deepDot = false;

            while(true) {
               if (this.tokenize("attribute", "::") || this.tokenize("@")) {
                  steps = this.addStep(deep, true, this.tokenizeQName(), steps);
                  break;
               }

               if (this.tokenize(".")) {
                  deepDot = deepDot || deep;
               } else {
                  this.tokenize("child", "::");
                  QName name;
                  if ((name = this.tokenizeQName()) != null) {
                     steps = this.addStep(deep, false, name, steps);
                     deep = false;
                  }
               }

               if (this.tokenize("//")) {
                  deep = true;
                  deepDot = false;
               } else {
                  if (!this.tokenize("/")) {
                     break;
                  }

                  if (deepDot) {
                     deep = true;
                  }
               }
            }

            if (this._lastDeepDot = deepDot) {
               this._lastDeepDot = true;
               steps = this.addStep(true, false, this.getAnyQName(), steps);
            }

            return this.addStep(false, false, (QName)null, steps);
         }
      }

      private void computeBacktrack(Step steps) throws XPathCompileException {
         Step t;
         for(Step s = steps; s != null; s = t) {
            for(t = s._next; t != null && !t._deep; t = t._next) {
            }

            if (!s._deep) {
               for(Step u = s; u != t; u = u._next) {
                  u._hasBacktrack = true;
               }
            } else {
               int n = 0;

               Step u;
               for(u = s; u != t && u._name != null && !u.isWild() && !u._attr; u = u._next) {
                  ++n;
               }

               QName[] pattern = new QName[n + 1];
               int[] kmp = new int[n + 1];
               Step v = s;

               int i;
               for(i = 0; i < n; ++i) {
                  pattern[i] = v._name;
                  v = v._next;
               }

               pattern[n] = this.getAnyQName();
               i = 0;
               int j = kmp[0] = -1;

               while(i < n) {
                  while(j > -1 && !pattern[i].equals(pattern[j])) {
                     j = kmp[j];
                  }

                  ++i;
                  QName var10000 = pattern[i];
                  ++j;
                  if (var10000.equals(pattern[j])) {
                     kmp[i] = kmp[j];
                  } else {
                     kmp[i] = j;
                  }
               }

               i = 0;

               for(v = s; v != u; v = v._next) {
                  v._hasBacktrack = true;
                  v._backtrack = s;

                  for(j = kmp[i]; j > 0; --j) {
                     v._backtrack = v._backtrack._next;
                  }

                  ++i;
               }

               v = s;
               if (n > 1) {
                  for(j = kmp[n - 1]; j > 0; --j) {
                     v = v._next;
                  }
               }

               if (u != t && u._attr) {
                  u._hasBacktrack = true;
                  u._backtrack = v;
                  u = u._next;
               }

               if (u != t && u._name == null) {
                  u._hasBacktrack = true;
                  u._backtrack = v;
               }

               assert s._deep;

               s._hasBacktrack = true;
               s._backtrack = s;
            }
         }

      }

      private void tokenizePath(ArrayList paths) throws XPathCompileException {
         this._lastDeepDot = false;
         Step steps = this.tokenizeSteps();
         this.computeBacktrack(steps);
         paths.add(steps);
         if (this._lastDeepDot) {
            this._sawDeepDot = true;
            Step s = null;

            for(Step t = steps; t != null; t = t._next) {
               if (t._next != null && t._next._next == null) {
                  s = this.addStep(t._deep, true, t._name, s);
               } else {
                  s = this.addStep(t._deep, t._attr, t._name, s);
               }
            }

            this.computeBacktrack(s);
            paths.add(s);
         }

      }

      private Selector tokenizeSelector() throws XPathCompileException {
         ArrayList paths = new ArrayList();
         this.tokenizePath(paths);

         while(this.tokenize("|")) {
            this.tokenizePath(paths);
         }

         return new Selector((Step[])((Step[])paths.toArray(new Step[0])));
      }

      private XPath tokenizeXPath() throws XPathCompileException {
         while(true) {
            String prefix;
            if (this.tokenize("declare", "namespace")) {
               if (!this.parseWhitespace()) {
                  throw this.newError("Expected prefix after 'declare namespace'");
               }

               prefix = this.tokenizeNCName();
               if (!this.tokenize("=")) {
                  throw this.newError("Expected '='");
               }

               String uri = this.tokenizeQuotedUri();
               if (this._namespaces.containsKey(prefix)) {
                  throw this.newError("Redefinition of namespace prefix: " + prefix);
               }

               this._namespaces.put(prefix, uri);
               if (this._externalNamespaces.containsKey(prefix)) {
                  throw this.newError("Redefinition of namespace prefix: " + prefix);
               }

               this._externalNamespaces.put(prefix, uri);
               if (!this.tokenize(";")) {
               }

               this._externalNamespaces.put("$xmlbeans!ns_boundary", new Integer(this._offset));
            } else {
               if (!this.tokenize("declare", "default", "element", "namespace")) {
                  if (!this._namespaces.containsKey("")) {
                     this._namespaces.put("", "");
                  }

                  Selector selector = this.tokenizeSelector();
                  this.parseWhitespace();
                  if (this.currChar() != -1) {
                     throw this.newError("Unexpected char '" + (char)this.currChar() + "'");
                  }

                  return new XPath(selector, this._sawDeepDot);
               }

               prefix = this.tokenizeQuotedUri();
               if (this._namespaces.containsKey("")) {
                  throw this.newError("Redefinition of default element namespace");
               }

               this._namespaces.put("", prefix);
               if (this._externalNamespaces.containsKey("$xmlbeans!default_uri")) {
                  throw this.newError("Redefinition of default element namespace : ");
               }

               this._externalNamespaces.put("$xmlbeans!default_uri", prefix);
               if (!this.tokenize(";")) {
                  throw this.newError("Default Namespace declaration must end with ;");
               }

               this._externalNamespaces.put("$xmlbeans!ns_boundary", new Integer(this._offset));
            }
         }
      }

      private void processNonXpathDecls() {
      }
   }

   public static class ExecutionContext {
      public static final int HIT = 1;
      public static final int DESCEND = 2;
      public static final int ATTRS = 4;
      private XPath _xpath;
      private ArrayList _stack = new ArrayList();
      private PathContext[] _paths;

      public final void init(XPath xpath) {
         int i;
         if (this._xpath != xpath) {
            this._xpath = xpath;
            this._paths = new PathContext[xpath._selector._paths.length];

            for(i = 0; i < this._paths.length; ++i) {
               this._paths[i] = new PathContext();
            }
         }

         this._stack.clear();

         for(i = 0; i < this._paths.length; ++i) {
            this._paths[i].init(xpath._selector._paths[i]);
         }

      }

      public final int start() {
         int result = 0;

         for(int i = 0; i < this._paths.length; ++i) {
            result |= this._paths[i].start();
         }

         return result;
      }

      public final int element(QName name) {
         assert name != null;

         this._stack.add(name);
         int result = 0;

         for(int i = 0; i < this._paths.length; ++i) {
            result |= this._paths[i].element(name);
         }

         return result;
      }

      public final boolean attr(QName name) {
         boolean hit = false;

         for(int i = 0; i < this._paths.length; ++i) {
            hit |= this._paths[i].attr(name);
         }

         return hit;
      }

      public final void end() {
         this._stack.remove(this._stack.size() - 1);

         for(int i = 0; i < this._paths.length; ++i) {
            this._paths[i].end();
         }

      }

      private final class PathContext {
         private Step _curr;
         private List _prev = new ArrayList();

         PathContext() {
         }

         void init(Step steps) {
            this._curr = steps;
            this._prev.clear();
         }

         private QName top(int i) {
            return (QName)ExecutionContext.this._stack.get(ExecutionContext.this._stack.size() - 1 - i);
         }

         private void backtrack() {
            assert this._curr != null;

            if (this._curr._hasBacktrack) {
               this._curr = this._curr._backtrack;
            } else {
               assert !this._curr._deep;

               this._curr = this._curr._prev;

               label39:
               while(!this._curr._deep) {
                  int t = 0;

                  for(Step s = this._curr; !s._deep; s = s._prev) {
                     if (!s.match(this.top(t++))) {
                        this._curr = this._curr._prev;
                        continue label39;
                     }
                  }

                  return;
               }

            }
         }

         int start() {
            assert this._curr != null;

            assert this._curr._prev == null;

            if (this._curr._name != null) {
               return this._curr._flags;
            } else {
               this._curr = null;
               return 1;
            }
         }

         int element(QName name) {
            this._prev.add(this._curr);
            if (this._curr == null) {
               return 0;
            } else {
               assert this._curr._name != null;

               if (!this._curr._attr && this._curr.match(name)) {
                  if ((this._curr = this._curr._next)._name != null) {
                     return this._curr._flags;
                  } else {
                     this.backtrack();
                     return this._curr == null ? 1 : 1 | this._curr._flags;
                  }
               } else {
                  do {
                     this.backtrack();
                     if (this._curr == null) {
                        return 0;
                     }

                     if (this._curr.match(name)) {
                        this._curr = this._curr._next;
                        break;
                     }
                  } while(!this._curr._deep);

                  return this._curr._flags;
               }
            }
         }

         boolean attr(QName name) {
            return this._curr != null && this._curr._attr && this._curr.match(name);
         }

         void end() {
            this._curr = (Step)this._prev.remove(this._prev.size() - 1);
         }
      }
   }

   public static class XPathCompileException extends XmlException {
      XPathCompileException(XmlError err) {
         super(err.toString(), (Throwable)null, (XmlError)err);
      }
   }
}
