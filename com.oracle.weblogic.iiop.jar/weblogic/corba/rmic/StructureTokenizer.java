package weblogic.corba.rmic;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import weblogic.rmi.utils.Utilities;

final class StructureTokenizer extends StreamTokenizer {
   boolean eofOK;
   String currFile;
   int nestingLevel;
   final boolean verbose;
   int errors;

   StructureTokenizer(Reader br) {
      super(br);
      this.eofOK = false;
      this.currFile = "[none]";
      this.nestingLevel = 0;
      this.verbose = false;
      this.errors = 0;
      this.init();
   }

   StructureTokenizer(Reader br, String currFile) {
      this(br);
      this.currFile = currFile;
   }

   void init() {
      this.resetSyntax();
      this.whitespaceChars(0, 32);
      this.wordChars(33, 126);
      this.eolIsSignificant(false);
      this.commentChar(59);
      this.ordinaryChar(40);
      this.ordinaryChar(41);
      this.ordinaryChar(91);
      this.ordinaryChar(93);
      this.quoteChar(34);
   }

   void aargh(String msg) throws Exception {
      ++this.errors;
      System.err.println(this.currFile + ": " + msg);
      if (this.errors > 20) {
         throw new ParseException("Exceeded 20 errors. Aborted.");
      }
   }

   Structure parseStructure() throws Exception {
      this.match('(');
      this.eofOK = false;
      ++this.nestingLevel;
      Structure ret = new Structure();
      ret.name = this.nextWord();
      ret.elements = this.parseElements();
      this.match(')');
      --this.nestingLevel;
      if (this.nestingLevel == 0) {
         this.eofOK = true;
      }

      return ret;
   }

   public int nextToken() throws IOException {
      int ttype = super.nextToken();
      if (ttype == 34) {
         ttype = -3;
      }

      if (ttype == -1 && !this.eofOK) {
         System.err.println("Unexpected end of file in '" + this.currFile + "'");
         Runtime.getRuntime().exit(-1);
      }

      return ttype;
   }

   Hashtable parseElements() throws Exception {
      Hashtable el = new Hashtable();

      while(true) {
         int t = this.nextToken();
         char c = (char)t;
         if (c == ')') {
            this.putBack();
            return el;
         }

         String name;
         Object value;
         if (c == '(') {
            this.putBack();
            Structure str = this.parseStructure();
            name = str.name;
            value = str.elements;
         } else {
            name = this.sval;
            value = this.parseValue();
         }

         el.put(name, value);
      }
   }

   Object parseValue() throws Exception {
      int t = this.nextToken();
      char c = (char)t;
      if (c == '[') {
         this.putBack();
         return this.parseStringVector();
      } else if (t == -3) {
         return this.sval;
      } else {
         this.fatalError("Expected word or '[' words ']'. Got " + this.interpret());
         return this.sval;
      }
   }

   Vector parseStringVector() throws Exception {
      Vector v = new Vector();
      this.match('[');

      while(true) {
         int t = this.nextToken();
         char c = (char)t;
         if (c == ']') {
            return v;
         }

         if (t == -3) {
            v.addElement(this.sval);
         } else {
            this.fatalError("Expected string or ']'. Got " + this.interpret());
         }
      }
   }

   void match(char ch) throws Exception {
      int ttype = this.nextToken();
      if ((char)ttype != ch) {
         this.fatalError("Expected '" + ch + "'. Got " + this.interpret());
      }
   }

   void fatalError(String msg) throws ParseException {
      throw new ParseException(this.lineno(), msg);
   }

   String nextWord() throws Exception {
      int ttype = this.nextToken();
      if (ttype == -3) {
         return this.sval;
      } else {
         this.fatalError("Expected a word. Got " + this.interpret());
         return null;
      }
   }

   String interpret() {
      switch (this.ttype) {
         case -3:
         case 34:
            return "word (" + this.sval + ")";
         case -2:
            return "number (" + this.nval + ")";
         case 10:
            return "end of line";
         default:
            return "character '" + (char)this.ttype + "'";
      }
   }

   void putBack() throws Exception {
      this.pushBack();
   }

   static Class[] getParameterTypes(String pTypes) throws ClassNotFoundException {
      if (pTypes == null) {
         return new Class[0];
      } else if (pTypes.equals("")) {
         return new Class[0];
      } else {
         int comma = pTypes.indexOf(44);
         if (comma == -1) {
            return new Class[]{getParameterType(pTypes)};
         } else {
            StringTokenizer st = new StringTokenizer(pTypes, ",");
            int tCount = st.countTokens();
            Class[] parameterTypes = new Class[tCount];

            for(int i = 0; i < tCount; ++i) {
               parameterTypes[i] = getParameterType(st.nextToken());
            }

            return parameterTypes;
         }
      }
   }

   private static Class getParameterType(String type) throws ClassNotFoundException {
      if (type.charAt(type.length() - 1) == ']') {
         int last = type.lastIndexOf(91);
         int first = type.indexOf(91);
         int[] sizes = new int[(last - first) / 2 + 1];
         type = type.substring(0, first);
         return Array.newInstance(getParameterType(type), sizes).getClass();
      } else if (type.equals("boolean")) {
         return Boolean.TYPE;
      } else if (type.equals("int")) {
         return Integer.TYPE;
      } else if (type.equals("short")) {
         return Short.TYPE;
      } else if (type.equals("long")) {
         return Long.TYPE;
      } else if (type.equals("double")) {
         return Double.TYPE;
      } else if (type.equals("float")) {
         return Float.TYPE;
      } else if (type.equals("char")) {
         return Character.TYPE;
      } else {
         return type.equals("byte") ? Byte.TYPE : Utilities.classForName(type);
      }
   }

   private static Method getMethod(Class c, String methodName, Class[] parameterTypes) throws NoSuchMethodException {
      StringBuffer sb = new StringBuffer();
      int i = 0;

      for(int len = methodName.length(); i < len; ++i) {
         char achar = methodName.charAt(i);
         if (!Character.isWhitespace(achar)) {
            sb.append(achar);
         }
      }

      methodName = sb.toString();

      try {
         return c.getMethod(methodName, parameterTypes);
      } catch (NoSuchMethodException var7) {
         System.err.println("Method " + methodName + " in " + c + " not found. Skipping.");
         throw var7;
      }
   }

   static Method getMethod(Class c, String methodName, String pTypes) throws NoSuchMethodException, ClassNotFoundException {
      return getMethod(c, methodName, getParameterTypes(pTypes));
   }
}
