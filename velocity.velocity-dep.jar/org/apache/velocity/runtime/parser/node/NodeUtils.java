package org.apache.velocity.runtime.parser.node;

import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.parser.Token;

public class NodeUtils {
   public static String specialText(Token t) {
      String specialText = "";
      if (t.specialToken != null && !t.specialToken.image.startsWith("##")) {
         Token tmp_t;
         for(tmp_t = t.specialToken; tmp_t.specialToken != null; tmp_t = tmp_t.specialToken) {
         }

         while(tmp_t != null) {
            String st = tmp_t.image;
            StringBuffer sb = new StringBuffer();

            for(int i = 0; i < st.length(); ++i) {
               char c = st.charAt(i);
               if (c == '#' || c == '$') {
                  sb.append(c);
               }

               if (c == '\\') {
                  boolean ok = true;
                  boolean term = false;
                  int j = i;

                  for(ok = true; ok && j < st.length(); ++j) {
                     char cc = st.charAt(j);
                     if (cc != '\\') {
                        if (cc == '$') {
                           term = true;
                           ok = false;
                        } else {
                           ok = false;
                        }
                     }
                  }

                  if (term) {
                     String foo = st.substring(i, j);
                     sb.append(foo);
                     i = j;
                  }
               }
            }

            specialText = specialText + sb.toString();
            tmp_t = tmp_t.next;
         }

         return specialText;
      } else {
         return specialText;
      }
   }

   public static String tokenLiteral(Token t) {
      return specialText(t) + t.image;
   }

   public static String interpolate(String argStr, Context vars) {
      StringBuffer argBuf = new StringBuffer();
      int cIdx = 0;

      while(true) {
         while(cIdx < argStr.length()) {
            char ch = argStr.charAt(cIdx);
            switch (ch) {
               case '$':
                  StringBuffer nameBuf = new StringBuffer();
                  ++cIdx;

                  for(; cIdx < argStr.length(); ++cIdx) {
                     ch = argStr.charAt(cIdx);
                     if (ch != '_' && ch != '-' && !Character.isLetterOrDigit(ch)) {
                        if (ch != '{' && ch != '}') {
                           break;
                        }
                     } else {
                        nameBuf.append(ch);
                     }
                  }

                  if (nameBuf.length() > 0) {
                     Object value = vars.get(nameBuf.toString());
                     if (value == null) {
                        argBuf.append("$").append(nameBuf.toString());
                     } else {
                        argBuf.append(value.toString());
                     }
                  }
                  break;
               default:
                  argBuf.append(ch);
                  ++cIdx;
            }
         }

         return argBuf.toString();
      }
   }
}
