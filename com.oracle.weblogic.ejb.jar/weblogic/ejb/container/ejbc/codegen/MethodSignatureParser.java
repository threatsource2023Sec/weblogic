package weblogic.ejb.container.ejbc.codegen;

import java.util.ArrayList;
import java.util.List;
import weblogic.utils.RecursiveDescentParser;

class MethodSignatureParser extends RecursiveDescentParser {
   private static final boolean verbose = false;

   public MethodSignatureParser(String s) {
      this.setSkipWhiteSpace(true);
      this.buf = s.toCharArray();
   }

   public boolean matchSignature(MethodSignature sig) {
      this.startMatch();
      if (this.matchModifiers()) {
         sig.setModifiers((Integer)this.oval);
      }

      if (this.matchType()) {
         sig.setReturnType((Class)this.oval);
      }

      if (!this.matchID()) {
         return this.failure();
      } else {
         sig.setName(this.sval);
         if (!this.match('(')) {
            return this.failure();
         } else {
            List types = new ArrayList();
            List names = new ArrayList();

            do {
               String name = null;
               if (!this.matchType()) {
                  break;
               }

               Class type = (Class)this.oval;
               if (!this.matchID()) {
                  return this.failure();
               }

               name = this.sval;
               types.add(type);
               names.add(name);
            } while(this.match(','));

            int size = types.size();
            sig.setParameterTypes((Class[])((Class[])types.toArray(new Class[size])));
            sig.setParameterNames((String[])((String[])names.toArray(new String[size])));
            if (!this.match(')')) {
               return this.failure();
            } else {
               if (this.matchThrows()) {
                  sig.setExceptionTypes((Class[])((Class[])this.oval));
               }

               return this.success();
            }
         }
      }
   }

   public boolean matchID() {
      this.startMatch();
      int start = this.peek;
      if (Character.isJavaIdentifierStart(this.buf[this.peek])) {
         ++this.peek;

         for(int len = this.buf.length; this.peek < len && Character.isJavaIdentifierPart(this.buf[this.peek]); ++this.peek) {
         }
      }

      return this.peek > start ? this.success() : this.failure();
   }

   public boolean matchType() {
      this.startMatch();
      StringBuffer sb = new StringBuffer();
      if (this.matchPrimitiveType()) {
         return this.success();
      } else {
         if (this.matchID()) {
            sb.append(this.sval);
         }

         while(true) {
            this.startMatch();
            if (!this.match('.') || !this.matchID()) {
               this.failure();
               if (sb.length() == 0) {
                  return this.failure();
               } else {
                  int dim;
                  for(dim = 0; this.match("[]"); ++dim) {
                  }

                  Class type = this.getType(sb.toString(), dim);
                  if (type != null) {
                     this.oval = type;
                     return this.success();
                  } else {
                     return this.failure();
                  }
               }
            }

            this.success();
            sb.append(this.sval);
         }
      }
   }

   private Class getType(String name, int dim) {
      String prefix = "";
      String postfix = "";

      try {
         if (dim > 0) {
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < dim; ++i) {
               sb.append("[");
            }

            sb.append("L");
            prefix = sb.toString();
            postfix = ";";
         }

         try {
            return Class.forName(prefix + name + postfix);
         } catch (ClassNotFoundException var7) {
            return Class.forName(prefix + "java.lang." + name + postfix);
         }
      } catch (ClassNotFoundException var8) {
         return null;
      }
   }

   public boolean matchPrimitiveType() {
      this.startMatch();
      if (this.match("boolean")) {
         this.oval = Boolean.TYPE;
      } else if (this.match("byte")) {
         this.oval = Byte.TYPE;
      } else if (this.match("char")) {
         this.oval = Character.TYPE;
      } else if (this.match("double")) {
         this.oval = Double.TYPE;
      } else if (this.match("float")) {
         this.oval = Float.TYPE;
      } else if (this.match("int")) {
         this.oval = Integer.TYPE;
      } else if (this.match("long")) {
         this.oval = Long.TYPE;
      } else if (this.match("short")) {
         this.oval = Short.TYPE;
      } else {
         if (!this.match("void")) {
            return this.failure();
         }

         this.oval = Void.TYPE;
      }

      int dim;
      for(dim = 0; this.match("[]"); ++dim) {
      }

      if (dim > 0) {
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < dim; ++i) {
            sb.append("[");
         }

         if (this.oval == Boolean.TYPE) {
            sb.append("Z");
         } else if (this.oval == Byte.TYPE) {
            sb.append("B");
         } else if (this.oval == Character.TYPE) {
            sb.append("C");
         } else if (this.oval == Double.TYPE) {
            sb.append("D");
         } else if (this.oval == Float.TYPE) {
            sb.append("F");
         } else if (this.oval == Integer.TYPE) {
            sb.append("I");
         } else if (this.oval == Long.TYPE) {
            sb.append("J");
         } else if (this.oval == Short.TYPE) {
            sb.append("S");
         }

         try {
            this.oval = Class.forName(sb.toString());
         } catch (ClassNotFoundException var4) {
            return this.failure();
         }
      }

      return this.success();
   }

   public boolean matchModifiers() {
      this.startMatch();
      int mods = 0;
      int start = this.peek;

      while(true) {
         while(!this.match("public")) {
            if (this.match("protected")) {
               mods ^= 4;
            } else if (this.match("private")) {
               mods ^= 2;
            } else if (this.match("abstract")) {
               mods ^= 1024;
            } else if (this.match("static")) {
               mods ^= 8;
            } else if (this.match("final")) {
               mods ^= 16;
            } else if (this.match("synchronized")) {
               mods ^= 32;
            } else {
               if (!this.match("native")) {
                  if (this.peek > start) {
                     this.oval = new Integer(mods);
                     return this.success();
                  }

                  return this.failure();
               }

               mods ^= 256;
            }
         }

         mods ^= 1;
      }
   }

   public boolean matchThrows() {
      this.startMatch();
      if (this.match("throws")) {
         List types = new ArrayList();

         while(this.matchType()) {
            types.add(this.oval);
            if (!this.match(',')) {
               break;
            }
         }

         int size = types.size();
         if (size > 0) {
            this.oval = types.toArray(new Class[size]);
            return this.success();
         }
      }

      return this.failure();
   }
}
