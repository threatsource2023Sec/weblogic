package weblogic.servlet.utils;

import weblogic.servlet.internal.HTTPDebugLogger;

public class SimpleApacheURLMatchMap extends URLMatchMap {
   static String[][] mappings = new String[][]{{"/foo/*", "FooServlet"}, {"/foo/bar/*", "FooBarServlet"}, {"/baz/*", "BazServlet"}, {"*.html", "FileServlet"}, {"*.jsp", "JSPServlet"}, {"*.class", "ClasspathServlet"}, {"/", "DefaultServlet"}, {"foo2/*", "FooServlet2"}, {"foo2/bar2/*", "FooBarServlet2"}, {"baz2/*", "BazServlet2"}, {"boom/*", "BoomServlet"}, {"/common/*", "Common1"}, {"/commonxyz/pdf/gy/*", "Common1"}, {"/common/kjh/*", "Common2"}, {"/commonjkhjk/*", "Common3"}, {"/commo/*", "Common4"}, {"/a/*", "a"}, {"/aa/*", "aa"}, {"/aaa/*", "aaa"}, {"*.jws", "JWSServlet"}};
   static String[][] tests = new String[][]{{"/foo/xxx", "FooServlet"}, {"/baz/xxx", "BazServlet"}, {"/foo/bar/xxxx", "FooBarServlet"}, {"/foo/xxx.html", "FooServlet"}, {"/qqq/yyy/ttt.html", "FileServlet"}, {"/qqq/yyy/ttt", "DefaultServlet"}, {"/qqq/jjj.jsp", "JSPServlet"}, {"/qqq/jjj.class", "ClasspathServlet"}, {"/foo2/xxx", "FooServlet2"}, {"/foo2/bar2/xxxx", "FooBarServlet2"}, {"/baz2/xxx", "BazServlet2"}, {"foo2/xxx", "FooServlet2"}, {"foo2/bar2/xxxx", "FooBarServlet2"}, {"baz2/xxx", "BazServlet2"}, {"/commonxyz/pdf/gy/*", "Common1"}, {"/common/kjh/*", "Common2"}, {"/commonjkhjk/*", "Common3"}, {"/commo/*", "Common4"}, {"/a", "a"}, {"/a/", "a"}, {"/a/x", "a"}, {"/a/xx", "a"}, {"/a/xxx", "a"}, {"/a/xxxx", "a"}, {"/a/xxxxx", "a"}, {"/aa", "aa"}, {"/aa/", "aa"}, {"/aa/x", "aa"}, {"/aa/xx", "aa"}, {"/aa/xxx", "aa"}, {"/aa/xxxx", "aa"}, {"/aa/xxxxx", "aa"}, {"/aaa", "aaa"}, {"/aaa/", "aaa"}, {"/aaa/x", "aaa"}, {"/aaa/xx", "aaa"}, {"/aaa/xxx", "aaa"}, {"/aaa/xxxx", "aaa"}, {"/aaa/xxxxx", "aaa"}, {"boom", "BoomServlet"}, {"boom/", "BoomServlet"}, {"boom/x", "BoomServlet"}, {"boom/xx", "BoomServlet"}, {"boom/xxx", "BoomServlet"}, {"boom/xxxx", "BoomServlet"}, {"/foo", "FooServlet"}, {"/foo1.jws", "JWSServlet"}, {"/foo2.jws/blah/somemore", "JWSServlet"}};
   static String[] removes = new String[]{"/foo/bar/*", "foo2/*", "boom/*", "*.jsp", "*.class"};
   static String[][] tests1 = new String[][]{{"/foo/xxx", "FooServlet"}, {"/baz/xxx", "BazServlet"}, {"/foo/bar/xxxx", "FooServlet"}, {"/foo/xxx.html", "FooServlet"}, {"/qqq/yyy/ttt.html", "FileServlet"}, {"/qqq/yyy/ttt", "DefaultServlet"}, {"/qqq/jjj.jsp", "DefaultServlet"}, {"/qqq/jjj.class", "DefaultServlet"}, {"/foo2/xxx", "DefaultServlet"}, {"/foo2/bar2/xxxx", "FooBarServlet2"}, {"/baz2/xxx", "BazServlet2"}, {"foo2/xxx", "DefaultServlet"}, {"foo2/bar2/xxxx", "FooBarServlet2"}, {"baz2/xxx", "BazServlet2"}, {"boom/xxx", "DefaultServlet"}, {"/commonxyz/pdf/gy/*", "Common1"}, {"/common/kjh/*", "Common2"}, {"/commonjkhjk/*", "Common3"}, {"/commo/*", "Common4"}, {"/foo", "FooServlet"}};

   public void put(String name, Object obj) {
      super.put(name, obj);
   }

   public Object get(String name) {
      Object obj = super.get(name);
      if (!name.equals("/") && (obj == null || obj.equals(this.defaultObject))) {
         int pos = false;
         String servletPath = name;
         int pos;
         if ((pos = name.indexOf(".")) != -1) {
            String begin = name.substring(0, pos + 1);
            String extn = name.substring(pos + 1);
            if ((pos = extn.indexOf("/")) != -1) {
               extn = extn.substring(0, pos);
               servletPath = begin + extn;
            }

            return super.get(servletPath);
         }
      }

      return obj;
   }

   public Object clone() {
      SimpleApacheURLMatchMap newMap = new SimpleApacheURLMatchMap();
      newMap.defaultObject = this.defaultObject;
      newMap.setCaseInsensitive(this.isCaseInsensitive());
      newMap.setExtensionCaseInsensitive(this.isExtensionCaseInsensitive());
      newMap.jspObject = this.jspObject;
      newMap.nodes = new URLMatchMap.URLMatchNode[257];

      int i;
      for(i = 0; i < 257; ++i) {
         newMap.nodes[i] = this.nodes[i];
      }

      int size = this.exts.length;
      newMap.exts = new URLMatchMap.URLExtensionNode[size];

      for(i = 0; i < size; ++i) {
         newMap.exts[i] = this.exts[i];
      }

      return newMap;
   }

   public static void main(String[] a) {
      SimpleApacheURLMatchMap mmap = new SimpleApacheURLMatchMap();
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
         if (!tests1[i][1].equals(o)) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("FAILED: expected '" + tests1[i][0] + "'->'" + tests1[i][1] + "' got '" + o + "'");
            }
         } else if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("SUCCESS: '" + tests1[i][0] + "'->'" + tests1[i][1] + "'");
         }
      }

   }
}
