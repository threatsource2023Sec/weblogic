package weblogic.servlet.utils;

import weblogic.servlet.internal.HTTPDebugLogger;

public class URLMatchMap implements URLMapping {
   protected static final int ARRAY_SIZE = 257;
   protected URLMatchNode[] nodes;
   protected URLExtensionNode[] exts;
   protected Object defaultObject;
   protected Object jspObject;
   private boolean caseInsensitive;
   private boolean extnCaseInsensitive;
   static String[][] mappings = new String[][]{{"/foo/*", "FooServlet"}, {"/foo/bar/*", "FooBarServlet"}, {"/baz/*", "BazServlet"}, {"*.html", "FileServlet"}, {"*.jsp", "JSPServlet"}, {"*.class", "ClasspathServlet"}, {"*.x", "SingleCharExtn"}, {"*.yz", "TwoCharExtn"}, {"/", "DefaultServlet"}, {"foo2/*", "FooServlet2"}, {"foo2/bar2/*", "FooBarServlet2"}, {"baz2/*", "BazServlet2"}, {"boom/*", "BoomServlet"}, {"/common/*", "Common1"}, {"/commonxyz/pdf/gy/*", "Common1"}, {"/common/kjh/*", "Common2"}, {"/commonjkhjk/*", "Common3"}, {"/commo/*", "Common4"}, {"/a/*", "a"}, {"/aa/*", "aa"}, {"/aaa/*", "aaa"}, {"/specviol/*", "SpecWildCard"}, {"/specviol", "SpecExact"}};
   static String[][] tests = new String[][]{{"/foo/xxx", "FooServlet"}, {"/baz/xxx", "BazServlet"}, {"/foo/bar/xxxx", "FooBarServlet"}, {"/foo/xxx.html", "FooServlet"}, {"/qqq/yyy/ttt.html", "FileServlet"}, {"/qqq/yyy/ttt", "DefaultServlet"}, {"/qqq/jjj.jsp", "JSPServlet"}, {"/qqq/jjj.class", "ClasspathServlet"}, {"/foo2/xxx", "FooServlet2"}, {"/foo2/bar2/xxxx", "FooBarServlet2"}, {"/baz2/xxx", "BazServlet2"}, {"foo2/xxx", "FooServlet2"}, {"foo2/bar2/xxxx", "FooBarServlet2"}, {"baz2/xxx", "BazServlet2"}, {"/commonxyz/pdf/gy/*", "Common1"}, {"/common/kjh/*", "Common2"}, {"/commonjkhjk/*", "Common3"}, {"/commo/*", "Common4"}, {"/blah.yz", "TwoCharExtn"}, {"/blah.x", "SingleCharExtn"}, {"/a", "a"}, {"/a/", "a"}, {"/a/x", "a"}, {"/a/xx", "a"}, {"/a/xxx", "a"}, {"/a/xxxx", "a"}, {"/a/xxxxx", "a"}, {"/aa", "aa"}, {"/aa/", "aa"}, {"/aa/x", "aa"}, {"/aa/xx", "aa"}, {"/aa/xxx", "aa"}, {"/aa/xxxx", "aa"}, {"/aa/xxxxx", "aa"}, {"/aaa", "aaa"}, {"/aaa/", "aaa"}, {"/aaa/x", "aaa"}, {"/aaa/xx", "aaa"}, {"/aaa/xxx", "aaa"}, {"/aaa/xxxx", "aaa"}, {"/aaa/xxxxx", "aaa"}, {"boom", "BoomServlet"}, {"boom/", "BoomServlet"}, {"boom/x", "BoomServlet"}, {"boom/xx", "BoomServlet"}, {"boom/xxx", "BoomServlet"}, {"boom/xxxx", "BoomServlet"}, {"/home", "DefaultServlet"}, {"/foo", "FooServlet"}, {"/specviol/foo", "SpecWildCard"}, {"/specviol/", "SpecWildCard"}, {"/specviol", "SpecExact"}};
   static String[] removes = new String[]{"/foo/bar/*", "foo2/*", "boom/*", "*.jsp", "*.class", "/"};
   static String[][] tests1 = new String[][]{{"/foo/xxx", "FooServlet"}, {"/baz/xxx", "BazServlet"}, {"/foo/bar/xxxx", "FooServlet"}, {"/foo/xxx.html", "FooServlet"}, {"/qqq/yyy/ttt.html", "FileServlet"}, {"/qqq/yyy/ttt", "null"}, {"/qqq/jjj.jsp", "null"}, {"/qqq/jjj.class", "null"}, {"/foo2/xxx", "null"}, {"/foo2/bar2/xxxx", "FooBarServlet2"}, {"/baz2/xxx", "BazServlet2"}, {"foo2/xxx", "null"}, {"foo2/bar2/xxxx", "FooBarServlet2"}, {"baz2/xxx", "BazServlet2"}, {"boom/xxx", "null"}, {"/commonxyz/pdf/gy/*", "Common1"}, {"/common/kjh/*", "Common2"}, {"/commonjkhjk/*", "Common3"}, {"/commo/*", "Common4"}, {"/home", "null"}, {"/foo", "FooServlet"}};

   public URLMatchMap() {
      this((Object)null, false, false);
   }

   public URLMatchMap(Object defaultObject, boolean ci) {
      this(defaultObject, ci, ci);
   }

   public URLMatchMap(Object defaultObject, boolean ci, boolean ciExtn) {
      this.nodes = null;
      this.exts = null;
      this.caseInsensitive = false;
      this.extnCaseInsensitive = false;
      this.defaultObject = defaultObject;
      this.caseInsensitive = ci;
      this.extnCaseInsensitive = ciExtn;
      this.nodes = new URLMatchNode[257];
      this.exts = new URLExtensionNode[0];
   }

   public void setDefault(Object defaultObject) {
      this.defaultObject = defaultObject;
   }

   public Object getDefault() {
      return this.defaultObject;
   }

   public boolean isCaseInsensitive() {
      return this.caseInsensitive;
   }

   public void setCaseInsensitive(boolean ci) {
      this.caseInsensitive = ci;
   }

   public boolean isExtensionCaseInsensitive() {
      return this.extnCaseInsensitive;
   }

   public void setExtensionCaseInsensitive(boolean ci) {
      this.extnCaseInsensitive = ci;
   }

   public void put(String pat, Object val) {
      if (pat == null) {
         throw new NullPointerException("null pattern put");
      } else if (val == null) {
         throw new NullPointerException("null object put");
      } else {
         if (pat.length() < 1 || pat.equals("/")) {
            pat = "/*";
         }

         if (pat.startsWith("*.")) {
            this.putByExtension(pat, val);
         } else {
            if (pat.charAt(0) != '/') {
               pat = "/" + pat;
            }

            if (!pat.equals("/") && !pat.equals("/*") && !pat.equals("*")) {
               if (pat.length() != 0 && pat.charAt(0) == '/') {
                  if (this.caseInsensitive) {
                     pat = pat.toLowerCase();
                  }

                  int hash = hashPath(pat);
                  URLMatchNode newnode = new URLMatchNode(pat, val, hash);
                  int mod = (hash & Integer.MAX_VALUE) % 257;
                  URLMatchNode iter = this.nodes[mod];
                  if (iter == null) {
                     this.nodes[mod] = newnode;
                  } else if (newnode.len >= iter.len) {
                     if (newnode.len == iter.len && newnode.exact == iter.exact && newnode.pattern.equals(iter.pattern)) {
                        iter.val = val;
                     } else {
                        this.nodes[mod] = newnode;
                        newnode.next = iter;
                     }
                  } else {
                     while(iter.next != null) {
                        if (newnode.len >= iter.next.len) {
                           if (newnode.len == iter.next.len && newnode.exact == iter.next.exact && newnode.pattern.equals(iter.next.pattern)) {
                              iter.next.val = val;
                              return;
                           }

                           newnode.next = iter.next;
                           iter.next = newnode;
                           return;
                        }

                        iter = iter.next;
                     }

                     iter.next = newnode;
                     newnode.next = null;
                  }
               } else {
                  throw new IllegalArgumentException("bad URLMatchMap path: '" + pat + "'");
               }
            } else {
               this.defaultObject = val;
            }
         }
      }
   }

   private void putByExtension(String pat, Object val) {
      if (pat.equals(".jsp")) {
         this.jspObject = val;
      }

      if (pat.startsWith("*.") && pat.length() >= 3) {
         pat = pat.substring(2);
         if (this.caseInsensitive) {
            pat = pat.toLowerCase();
         }

         int len = this.exts.length;

         for(int i = 0; i < len; ++i) {
            if (pat.equals(this.exts[i].ext)) {
               this.exts[i] = new URLExtensionNode(pat, this.caseInsensitive || this.extnCaseInsensitive, val);
               return;
            }
         }

         URLExtensionNode[] newexts = new URLExtensionNode[len + 1];
         System.arraycopy(this.exts, 0, newexts, 0, len);
         this.exts = newexts;
         this.exts[len] = new URLExtensionNode(pat, this.caseInsensitive || this.extnCaseInsensitive, val);
      } else {
         throw new IllegalArgumentException("bad URLMath extension pattern: '" + pat + "'");
      }
   }

   public Object get(String uri) {
      if (uri != null && uri.length() != 0 && !uri.equals("/")) {
         if (uri.charAt(0) != '/') {
            StringBuffer sb = new StringBuffer(uri.length() + 1);
            sb.append('/').append(uri);
            uri = sb.toString();
         }

         if (this.caseInsensitive) {
            uri = uri.toLowerCase();
         }

         Object ret = this.getByPath(uri);
         if (ret != null) {
            return ret;
         } else if (uri.endsWith(".jsp") && this.jspObject != null) {
            return this.jspObject;
         } else {
            ret = this.getByExtension(uri);
            return ret != null ? ret : this.defaultObject;
         }
      } else {
         return this.defaultObject;
      }
   }

   private Object getByPath(String uri) {
      int hash = hashPath(uri);
      int mod = (hash & Integer.MAX_VALUE) % 257;
      int urilen = uri.length();
      URLMatchNode umn = this.nodes[mod];
      return umn != null ? umn.match(uri, urilen, hash) : null;
   }

   private Object getByExtension(String uri) {
      int dot = uri.lastIndexOf(46);
      if (dot < 0) {
         return null;
      } else {
         int urilen = uri.length();
         if (dot == urilen - 1) {
            return null;
         } else {
            int off = dot + 1;
            int lenOfExtension = urilen - off;
            int len = this.exts.length;

            for(int i = 0; i < len; ++i) {
               if (this.exts[i].match(uri, off, lenOfExtension)) {
                  return this.exts[i].val;
               }
            }

            return null;
         }
      }
   }

   public void remove(String pat) {
      if (pat == null) {
         throw new NullPointerException("null pattern put");
      } else if (pat.length() >= 1 && !pat.equals("/")) {
         if (pat.startsWith("*.")) {
            this.removeByExtension(pat);
         } else {
            if (pat.charAt(0) != '/') {
               pat = "/" + pat;
            }

            if (pat.length() != 0 && pat.charAt(0) == '/') {
               if (this.caseInsensitive) {
                  pat = pat.toLowerCase();
               }

               int hash = hashPath(pat);
               int mod = (hash & Integer.MAX_VALUE) % 257;
               URLMatchNode iter = this.nodes[mod];
               if (iter != null) {
                  if (pat.endsWith("*")) {
                     pat = pat.substring(0, pat.length() - 1);
                  }

                  if (pat.equals(iter.pattern)) {
                     this.nodes[mod] = iter.next;
                  } else {
                     while(iter.next != null) {
                        if (pat.equals(iter.next.pattern)) {
                           iter.next = iter.next.next;
                           return;
                        }

                        iter = iter.next;
                     }

                  }
               }
            } else {
               throw new IllegalArgumentException("bad URLMatchMap path: '" + pat + "'");
            }
         }
      } else {
         this.defaultObject = null;
      }
   }

   private void removeByExtension(String pat) {
      if (pat.equals(".jsp")) {
         this.jspObject = null;
      }

      if (pat.startsWith("*.") && pat.length() >= 3) {
         pat = pat.substring(2);
         if (this.caseInsensitive) {
            pat = pat.toLowerCase();
         }

         int len = this.exts.length;

         for(int i = 0; i < len; ++i) {
            if (pat.equals(this.exts[i].ext)) {
               int newlen = len - 1;

               for(int j = i; j < newlen; ++j) {
                  this.exts[j] = this.exts[j + 1];
               }

               URLExtensionNode[] newexts = new URLExtensionNode[newlen];
               System.arraycopy(this.exts, 0, newexts, 0, newlen);
               this.exts = newexts;
               return;
            }
         }

      } else {
         throw new IllegalArgumentException("bad URLMath extension pattern: '" + pat + "'");
      }
   }

   private static int hashPath(String path) {
      int nextSlash = path.indexOf(47, 1);
      if (nextSlash != -1 && nextSlash < 4) {
         path = path.substring(0, nextSlash);
      }

      int len = path.length();
      int c1 = 0;
      int c2 = 0;
      int c3 = 0;
      if (len > 1) {
         c1 = path.charAt(1);
         if (len > 2) {
            c2 = path.charAt(2);
            if (len > 3) {
               c3 = path.charAt(3);
            }
         }
      }

      int x = c1 + c2 + c3 + c1 * c2;
      return x;
   }

   public Object clone() {
      URLMatchMap newMap = new URLMatchMap(this.defaultObject, this.caseInsensitive, this.extnCaseInsensitive);
      newMap.jspObject = this.jspObject;
      newMap.nodes = new URLMatchNode[257];

      int i;
      for(i = 0; i < 257; ++i) {
         newMap.nodes[i] = this.nodes[i];
      }

      int size = this.exts.length;
      newMap.exts = new URLExtensionNode[size];

      for(i = 0; i < size; ++i) {
         newMap.exts[i] = this.exts[i];
      }

      return newMap;
   }

   public int size() {
      int sz = this.exts.length;

      for(int i = 0; i < 257; ++i) {
         URLMatchNode node = this.nodes[i];
         if (node != null) {
            ++sz;

            while(node.next != null) {
               node = node.next;
               ++sz;
            }
         }
      }

      if (this.defaultObject != null) {
         ++sz;
      }

      return sz;
   }

   public Object[] values() {
      int sz = this.size();
      int i = 0;
      Object[] allValues = new Object[sz];
      if (this.defaultObject != null) {
         allValues[i++] = this.defaultObject;
      }

      int nod_i;
      for(nod_i = 0; nod_i < this.exts.length; ++nod_i) {
         allValues[i] = this.exts[nod_i].val;
         ++i;
      }

      for(nod_i = 0; nod_i < 257; ++nod_i) {
         URLMatchNode node = this.nodes[nod_i];
         if (node != null) {
            allValues[i] = node.val;
            ++i;

            while(node.next != null) {
               node = node.next;
               allValues[i] = node.val;
               ++i;
            }
         }
      }

      return allValues;
   }

   public String[] keys() {
      int sz = this.size();
      String[] allKeys = new String[sz];
      int i = 0;
      if (this.defaultObject != null) {
         allKeys[i++] = "";
      }

      int nod_i;
      for(nod_i = 0; nod_i < this.exts.length; ++nod_i) {
         allKeys[i] = "*." + this.exts[nod_i].ext;
         ++i;
      }

      for(nod_i = 0; nod_i < 257; ++nod_i) {
         URLMatchNode node = this.nodes[nod_i];
         if (node != null) {
            allKeys[i] = node.pattern;
            ++i;

            while(node.next != null) {
               node = node.next;
               allKeys[i] = node.pattern;
               ++i;
            }
         }
      }

      return allKeys;
   }

   public static void main(String[] a) {
      URLMatchMap mmap = new URLMatchMap((Object)null, false);
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("### RUN 1 ###");
      }

      int i;
      for(i = 0; i < mappings.length; ++i) {
         mmap.put(mappings[i][0], mappings[i][1]);
      }

      Object o;
      for(i = 0; i < tests.length; ++i) {
         o = mmap.get(tests[i][0]);
         if (!tests[i][1].equals(o)) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("FAILED: expected '" + tests[i][0] + "'->'" + tests[i][1] + "' got '" + o + "'");
            }
         } else if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("SUCCESS: '" + tests[i][0] + "'->'" + tests[i][1] + "'");
         }
      }

      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("### RUN 2 ###");
      }

      for(i = 0; i < removes.length; ++i) {
         mmap.remove(removes[i]);
      }

      for(i = 0; i < tests1.length; ++i) {
         o = mmap.get(tests1[i][0]);
         if (!tests1[i][1].equals("" + o)) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("FAILED: expected '" + tests1[i][0] + "'->'" + tests1[i][1] + "' got '" + o + "'");
            }
         } else if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("SUCCESS: '" + tests1[i][0] + "'->'" + tests1[i][1] + "'");
         }
      }

   }

   public class URLMatchNode {
      String pattern;
      int len;
      int hash;
      boolean exact;
      Object val;
      URLMatchNode next;

      URLMatchNode(String pat, Object val, int hash) {
         this.val = val;
         this.hash = hash;
         if (pat.length() != 0 && pat.charAt(0) == '/' && !pat.equals("/") && !pat.equals("/*")) {
            if (pat.endsWith("/*")) {
               this.exact = false;
               pat = pat.substring(0, pat.length() - 1);
            } else {
               this.exact = true;
            }

            this.pattern = pat;
            this.len = this.pattern.length();
         } else {
            throw new IllegalArgumentException("bad URLMatchMap path: '" + pat + "'");
         }
      }

      public Object match(String uri, int urilen, int urihash) {
         Object ret = this.exactMatch(uri, urilen, urihash);
         return ret != null ? ret : this.wildCardMatch(uri, urilen, urihash);
      }

      Object exactMatch(String uri, int urilen, int urihash) {
         URLMatchNode temp = this;

         do {
            if (temp.exact && temp.hash == urihash && urilen == temp.len && uri.equals(temp.pattern)) {
               return temp.val;
            }

            temp = temp.next;
         } while(temp != null);

         return null;
      }

      Object wildCardMatch(String uri, int urilen, int urihash) {
         URLMatchNode temp = this;

         do {
            if (!temp.exact && temp.hash == urihash) {
               if (urilen >= temp.len) {
                  if (uri.startsWith(temp.pattern)) {
                     return temp.val;
                  }
               } else {
                  if (urilen == temp.len - 1 && temp.pattern.endsWith("/") && temp.pattern.startsWith(uri)) {
                     return temp.val;
                  }

                  if (urilen == temp.len && uri.equals(temp.pattern)) {
                     return temp.val;
                  }
               }
            }

            temp = temp.next;
         } while(temp != null);

         return null;
      }
   }

   public class URLExtensionNode {
      String ext;
      char[] extChars;
      int len;
      boolean caseInsensitive;
      Object val;

      URLExtensionNode(String ex, boolean ci, Object val) {
         this.caseInsensitive = ci;
         this.val = val;
         if (this.caseInsensitive) {
            ex = ex.toLowerCase();
         }

         this.ext = ex;
         this.extChars = this.ext.toCharArray();
         this.len = this.extChars.length;
      }

      boolean match(String uri, int off, int offlen) {
         if (this.len != offlen) {
            return false;
         } else {
            int i;
            int j;
            if (!this.caseInsensitive) {
               i = 0;

               for(j = off; i < this.len; ++j) {
                  if (this.extChars[i] != uri.charAt(j)) {
                     return false;
                  }

                  ++i;
               }
            } else {
               i = 0;

               for(j = off; i < this.len; ++j) {
                  if (Character.toLowerCase(this.extChars[i]) != Character.toLowerCase(uri.charAt(j))) {
                     return false;
                  }

                  ++i;
               }
            }

            return true;
         }
      }
   }
}
