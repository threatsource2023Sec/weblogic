package com.bea.common.security.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class URLMatchMap {
   private boolean caseInsensitive;
   private List wildcardList;
   private Map patternMap;
   private static final char WILDCARD_PATH = '*';
   private static final String WILDCARD_EXTENSION = "*.";
   private static final char CHAR_SLASH = '/';
   private static final char CHAR_POINT = '.';
   static String[][] mappings = new String[][]{{"/myapp/aa/*", "myidp"}, {"/baz/*", "BazServlet"}, {"*.jsp", "JSPServlet"}, {"/*.jsp", "JSPServlet"}, {"/*/*.jsp", "JSPServlet1"}, {"/specviol/*/ddd/*.xcv", "SpecExact"}, {"/*/abc/*.jsx", "JSPServlet2"}, {"/abc/*/*.jsp", "JSPServlet3"}, {"/*/test/abc.js", "JSPServlet4"}, {"/*/*/foo/foo/abc.js", "JSPServlet8"}, {"/*/dot.in.path/test/abc.js", "JSPServlet5"}, {"/foo/*/abcd/*", "JSPServlet6"}, {"/foo/*/ab.cd/*", "JSPServlet7"}, {"/", "JSPServlet9"}, {"/*", "DefaultServlet"}};
   static String[][] tests = new String[][]{{"/myapp/aa/welcome.jsp", "myidp"}, {"/fowo/sdf.jsp", "JSPServlet1"}, {"/sdf.jsp", "JSPServlet"}, {"/abc/dd.jsp", "JSPServlet3"}, {"cc/abc/dd.jsx", "JSPServlet2"}, {"/cc/abc/dd.jsx", "JSPServlet2"}, {"/baz/dd.jsp", "BazServlet"}, {"/baz/dd", "BazServlet"}, {"/dfds*/dd", "DefaultServlet"}, {"/test/cde/test/abc.js", "JSPServlet4"}, {"/foo/foo/abc.js", "JSPServlet8"}, {"/a/dot.in.path/abc.js", "JSPServlet5"}, {"/specviol/cc/ddd/dd.xcv", "SpecExact"}, {"/foo/abcd", "JSPServlet6"}, {"/foo/ab.cd", "JSPServlet7"}, {"/", "JSPServlet9"}};

   public URLMatchMap() {
      this(false);
   }

   public URLMatchMap(boolean ci) {
      this.caseInsensitive = false;
      this.wildcardList = new ArrayList(12);
      this.patternMap = new HashMap(24);
      this.caseInsensitive = ci;
   }

   public boolean isCaseInsensitive() {
      return this.caseInsensitive;
   }

   public void setCaseInsensitive(boolean ci) {
      this.caseInsensitive = ci;
   }

   public void put(String pat, Object val) {
      if (pat != null && pat.length() >= 1) {
         if (pat.charAt(0) != '/') {
            pat = '/' + pat;
         }

         this.patternMap.put(pat, val);
         if (pat.indexOf(42) >= 0) {
            this.wildcardList.add(new URLPattern(pat));
         }

      }
   }

   public Object get(String url) {
      Object exact = this.patternMap.get(url);
      if (exact != null) {
         return exact;
      } else {
         try {
            Iterator ite = this.wildcardList.iterator();
            int maxExactMatchedLength = -1;
            String matchedPattern = null;

            while(ite.hasNext()) {
               URLPattern pattern = (URLPattern)ite.next();
               MatchResult result = pattern.match(url);
               if (result.isMatch() && result.getExactMatchLength() > maxExactMatchedLength) {
                  maxExactMatchedLength = result.getExactMatchLength();
                  matchedPattern = pattern.getPattern();
               }
            }

            if (matchedPattern != null) {
               return this.patternMap.get(matchedPattern);
            } else {
               return null;
            }
         } catch (Exception var8) {
            return null;
         }
      }
   }

   public void clear() {
      this.patternMap.clear();
      this.wildcardList.clear();
   }

   public static void main(String[] a) {
      URLMatchMap mmap = new URLMatchMap(false);
      System.err.println("### RUN 1 ###");

      int i;
      for(i = 0; i < mappings.length; ++i) {
         mmap.put(mappings[i][0], mappings[i][1]);
      }

      for(i = 0; i < tests.length; ++i) {
         Object o = mmap.get(tests[i][0]);
         if (!tests[i][1].equals(o)) {
            System.err.println("FAILED: expected '" + tests[i][0] + "'->'" + tests[i][1] + "' got '" + o + "'");
         } else {
            System.err.println("SUCCESS: '" + tests[i][0] + "'->'" + tests[i][1] + "'");
         }
      }

   }

   private class URLMatchNode {
      private URLMatchNode next;
      private String path;
      private String ext;
      private boolean wildcardSupport;
      private boolean extension;
      private boolean wildcardPath;
      private boolean wildcardExtension;

      private URLMatchNode(String item, boolean wildcard) {
         this.ext = "";
         this.wildcardSupport = false;
         this.extension = false;
         this.wildcardPath = false;
         this.wildcardExtension = false;
         this.path = item;
         this.wildcardSupport = wildcard;
         if (this.wildcardSupport) {
            if (this.path.startsWith("*.")) {
               this.extension = true;
               this.wildcardExtension = true;
            } else if (this.path.length() == 1 && this.path.charAt(0) == '*') {
               this.wildcardPath = true;
            } else if (this.path.indexOf(46) > 0) {
               this.extension = true;
            }

            if (this.extension) {
               int s1 = this.path.indexOf(46);
               if (s1 < this.path.length() - 1) {
                  this.ext = this.path.substring(s1 + 1);
               }
            }
         }

      }

      private boolean isExtension() {
         return this.extension;
      }

      private boolean isWildcardPath() {
         return this.wildcardPath;
      }

      private boolean pathMatch(String src, String target) {
         return URLMatchMap.this.caseInsensitive ? src.equalsIgnoreCase(target) : src.equals(target);
      }

      private boolean match(String pt) {
         if (!this.wildcardSupport) {
            return this.pathMatch(this.path, pt);
         } else if (this.extension) {
            if (this.wildcardExtension) {
               int s1 = pt.indexOf(46);
               if (s1 < 0) {
                  return false;
               } else {
                  return s1 < pt.length() - 1 ? this.pathMatch(this.ext, pt.substring(s1 + 1)) : this.pathMatch(this.ext, "");
               }
            } else {
               return this.pathMatch(this.path, pt);
            }
         } else {
            return this.wildcardPath ? true : this.pathMatch(this.path, pt);
         }
      }

      // $FF: synthetic method
      URLMatchNode(String x1, boolean x2, Object x3) {
         this(x1, x2);
      }
   }

   private class URLPattern {
      private String pattern;
      private URLMatchNode first;

      private URLPattern(String pat) {
         this.pattern = pat;
         this.first = this.compile(this.pattern, true);
      }

      private String getPattern() {
         return this.pattern;
      }

      private URLMatchNode compile(String pattern, boolean wildcardSupport) {
         int i = false;
         int start = 0;
         URLMatchNode head = null;
         URLMatchNode current = null;
         if (pattern.charAt(0) == '/') {
            start = 1;
         }

         String last;
         URLMatchNode mapItem;
         int ix;
         while((ix = pattern.indexOf(47, start)) >= 0) {
            last = pattern.substring(start, ix);
            if (last != null && last.length() > 0) {
               mapItem = URLMatchMap.this.new URLMatchNode(last, wildcardSupport);
               if (head == null) {
                  head = mapItem;
                  current = mapItem;
               } else {
                  current.next = mapItem;
                  current = current.next;
               }
            }

            start = ix + 1;
            ++ix;
         }

         ix = pattern.length();
         last = pattern.substring(start, ix);
         if (last != null && last.length() > 0) {
            mapItem = URLMatchMap.this.new URLMatchNode(last, wildcardSupport);
            if (head == null) {
               head = mapItem;
            } else {
               current.next = mapItem;
            }
         }

         return head;
      }

      private MatchResult match(URLMatchNode source, URLMatchNode target, int mathchLength, boolean firstIn) {
         int exactMatchLength = mathchLength;

         boolean matched;
         for(matched = true; target != null && source != null; target = target.next) {
            if (source.isWildcardPath()) {
               while(source.next != null && source.next.isWildcardPath()) {
                  source = source.next;
               }

               if (source.next != null) {
                  for(source = source.next; target != null; target = target.next) {
                     MatchResult result = this.match(source, target, exactMatchLength, false);
                     if (result.isMatch()) {
                        return result;
                     }
                  }

                  return URLMatchMap.MatchResult.NOT_FOUND;
               }

               while(target.next != null) {
                  target = target.next;
               }

               if (source.match(target.path)) {
                  return new MatchResult(true, exactMatchLength);
               }

               return URLMatchMap.MatchResult.NOT_FOUND;
            }

            if (source.isExtension()) {
               if (source.match(target.path)) {
                  return new MatchResult(true, exactMatchLength);
               }

               return URLMatchMap.MatchResult.NOT_FOUND;
            }

            if (!source.match(target.path)) {
               return URLMatchMap.MatchResult.NOT_FOUND;
            }

            if (firstIn) {
               ++exactMatchLength;
            }

            source = source.next;
         }

         if (source != null && !source.isWildcardPath() || target != null) {
            matched = false;
         }

         return matched ? new MatchResult(true, exactMatchLength) : URLMatchMap.MatchResult.NOT_FOUND;
      }

      private MatchResult match(String url) {
         URLMatchNode target = this.compile(url, false);
         URLMatchNode source = this.first;
         return this.match(source, target, 0, true);
      }

      // $FF: synthetic method
      URLPattern(String x1, Object x2) {
         this(x1);
      }
   }

   private static class MatchResult {
      private boolean match;
      private int exactMatchedLength;
      private static final MatchResult NOT_FOUND = new MatchResult(false, 0);

      private MatchResult(boolean result, int exactMatchedCount) {
         this.match = false;
         this.exactMatchedLength = 0;
         this.match = result;
         this.exactMatchedLength = exactMatchedCount;
      }

      private boolean isMatch() {
         return this.match;
      }

      private int getExactMatchLength() {
         return this.exactMatchedLength;
      }

      // $FF: synthetic method
      MatchResult(boolean x0, int x1, Object x2) {
         this(x0, x1);
      }
   }
}
