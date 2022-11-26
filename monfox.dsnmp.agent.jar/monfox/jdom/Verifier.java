package monfox.jdom;

import monfox.toolkit.snmp.SnmpException;

public final class Verifier {
   private Verifier() {
   }

   public static final String checkElementName(String var0) {
      String var1;
      if ((var1 = a(var0)) != null) {
         return var1;
      } else {
         return var0.indexOf(":") != -1 ? b("\u0000\u007f=\u001f\f+gx\u001c\b(v+R\n$}6\u001d\u001dep7\u001c\u001d$z6R\n*\u007f7\u001c\u001a") : null;
      }
   }

   public static final String checkAttributeName(String var0) {
      String var1;
      if ((var1 = a(var0)) != null) {
         return var1;
      } else if (!var0.equals(b("=~4H\u001a5r;\u0017")) && !var0.equals(b("=~4H\u0005$}?"))) {
         if (var0.indexOf(":") != -1) {
            return b("\u0004g,\u0000\u0000'f,\u0017I+r5\u0017\u001aep9\u001c\u0007*gx\u0011\u0006+g9\u001b\u0007ep7\u001e\u0006+`");
         } else {
            return var0.equals(b("=~4\u001c\u001a")) ? b("\u0004}x3\u001d1a1\u0010\u001c1vx\u001c\b(vx\u001f\b<36\u001d\u001deq=RK=~4\u001c\u001ag(x\u0007\u001a 3,\u001a\fe]9\u001f\f6c9\u0011\fep4\u0013\u001a63,\u001dI(r6\u0013\u000e 36\u0013\u0004 `(\u0013\n `") : null;
         }
      } else {
         return null;
      }
   }

   public static final String checkCharacterData(String var0) {
      if (var0 == null) {
         return b("\u000436\u0007\u0005)31\u0001I+|,R\be\u007f=\u0015\b)3\u0000?%ee9\u001e\u001c ");
      } else {
         int var1 = 0;
         int var2 = var0.length();

         while(var1 < var2) {
            if (!isXMLCharacter(var0.charAt(var1))) {
               return b("uk") + Integer.toHexString(var0.charAt(var1)) + b("ez+R\u0007*gx\u0013I)v?\u0013\u0005eK\u0015>I&{9\u0000\b&g=\u0000");
            }

            ++var1;
            if (Element.b) {
               break;
            }
         }

         return null;
      }
   }

   public static final String checkCDATASection(String var0) {
      String var1 = null;
      if ((var1 = checkCharacterData(var0)) != null) {
         return var1;
      } else {
         return var0.indexOf(b("\u0018Nf")) != -1 ? b("\u0006W\u0019&(ep9\u001c\u0007*gx\u001b\u00071v*\u001c\b)\u007f!R\n*},\u0013\u0000+39R*\u0001R\f3I }<\u001b\u0007\"3<\u0017\u0005,~1\u0006\f73p/4{:") : null;
      }
   }

   public static final String checkNamespacePrefix(String var0) {
      boolean var5 = Element.b;
      if (var0 != null && !var0.equals("")) {
         char var1 = var0.charAt(0);
         if (isXMLDigit(var1)) {
            return b("\u000br5\u0017\u001a5r;\u0017I5a=\u0014\u0000=v+R\n$}6\u001d\u001deq=\u0015\u0000+3/\u001b\u001d-39R\u00070~:\u0017\u001b");
         } else if (var1 == '$') {
            return b("\u000br5\u0017\u001a5r;\u0017I5a=\u0014\u0000=v+R\n$}6\u001d\u001deq=\u0015\u0000+3/\u001b\u001d-39R\r*\u007f4\u0013\u001be`1\u0015\u0007e;|[");
         } else if (var1 == '-') {
            return b("\u000br5\u0017\u001a5r;\u0017I5a=\u0014\u0000=v+R\n$}6\u001d\u001deq=\u0015\u0000+3/\u001b\u001d-39R\u0001<c0\u0017\u0007e;u[");
         } else if (var1 == '.') {
            return b("\u000br5\u0017\u001a5r;\u0017I5a=\u0014\u0000=v+R\n$}6\u001d\u001deq=\u0015\u0000+3/\u001b\u001d-39R\u0019 a1\u001d\re;v[");
         } else if (var0.toLowerCase().startsWith(b("=~4"))) {
            return b("\u000br5\u0017\u001a5r;\u0017I5a=\u0014\u0000=v+R\n$}6\u001d\u001deq=\u0015\u0000+3/\u001b\u001d-3z\n\u0004)1x\u001b\u0007er6\u000bI&|5\u0010\u0000+r,\u001b\u0006+37\u0014I&r+\u0017");
         } else {
            int var2 = 0;
            int var3 = var0.length();

            int var10000;
            while(true) {
               if (var2 < var3) {
                  char var4 = var0.charAt(var2);
                  var10000 = isXMLNameCharacter(var4);
                  if (var5) {
                     break;
                  }

                  if (var10000 == 0) {
                     return b("\u000br5\u0017\u001a5r;\u0017I5a=\u0014\u0000=v+R\n$}6\u001d\u001dep7\u001c\u001d$z6R\u001d-vx\u0011\u0001$a9\u0011\u001d axP") + var4 + "\"";
                  }

                  ++var2;
                  if (!var5) {
                     continue;
                  }
               }

               var10000 = var0.indexOf(":");
               break;
            }

            return var10000 != -1 ? b("\u000br5\u0017\u001a5r;\u0017I5a=\u0014\u0000=v+R\n$}6\u001d\u001dep7\u001c\u001d$z6R\n*\u007f7\u001c\u001a") : null;
         }
      } else {
         return null;
      }
   }

   public static final String checkNamespaceURI(String var0) {
      if (var0 != null && !var0.equals("")) {
         char var1 = var0.charAt(0);
         if (Character.isDigit(var1)) {
            return b("\u000br5\u0017\u001a5r;\u0017I\u0010A\u0011\u0001I&r6\u001c\u000613:\u0017\u000e,}x\u0005\u00001{x\u0013I+f5\u0010\f7");
         } else if (var1 == '$') {
            return b("\u000br5\u0017\u001a5r;\u0017I\u0010A\u0011\u0001I&r6\u001c\u000613:\u0017\u000e,}x\u0005\u00001{x\u0013I!|4\u001e\b73+\u001b\u000e+3pV@");
         } else {
            return var1 == '-' ? b("\u000br5\u0017\u001a5r;\u0017I\u0010A\u0011\u0001I&r6\u001c\u000613:\u0017\u000e,}x\u0005\u00001{x\u0013I-j(\u001a\f+3p_@") : null;
         }
      } else {
         return null;
      }
   }

   public static final String checkProcessingInstructionTarget(String var0) {
      String var1;
      if ((var1 = a(var0)) != null) {
         return var1;
      } else if (var0.indexOf(":") != -1) {
         return b("\u0015a7\u0011\f6`1\u001c\u000eez6\u0001\u001d7f;\u0006\u0000*}x\u0006\b7t=\u0006\u001aep9\u001c\u0007*gx\u0011\u0006+g9\u001b\u0007ep7\u001e\u0006+`");
      } else {
         return var0.equalsIgnoreCase(b("=~4")) ? b("\u0015a7\u0011\f6`1\u001c\u000eez6\u0001\u001d7f;\u0006\u0000*}+R\n$}6\u001d\u001de{9\u0004\ferx\u0006\b7t=\u0006I*uxP\u0011(\u007fzR\u0000+39\u001c\u0010ep7\u001f\u000b,}9\u0006\u0000*}x\u001d\u000fep9\u0001\f") : null;
      }
   }

   public static final String checkCommentData(String var0) {
      String var1 = null;
      if ((var1 = checkCharacterData(var0)) != null) {
         return var1;
      } else {
         return var0.indexOf(b("h>")) != -1 ? b("\u0006|5\u001f\f+g+R\n$}6\u001d\u001dep7\u001c\u001d$z6R\r*f:\u001e\fe{!\u0002\u0001 }+RAh>q") : null;
      }
   }

   private static String a(String var0) {
      if (var0 != null && var0.length() != 0 && !var0.trim().equals("")) {
         char var1 = var0.charAt(0);
         if (!isXMLNameStartCharacter(var1)) {
            return b("\u001d^\u0014R\u0007$~=\u0001I&r6\u001c\u000613:\u0017\u000e,}x\u0005\u00001{x\u0006\u0001 3;\u001a\b7r;\u0006\f73z") + var1 + "\"";
         } else {
            int var2 = 0;
            int var3 = var0.length();

            while(var2 < var3) {
               char var4 = var0.charAt(var2);
               if (!isXMLNameCharacter(var4)) {
                  return b("\u001d^\u0014R\u0007$~=\u0001I&r6\u001c\u000613;\u001d\u00071r1\u001cI1{=R\n-r*\u0013\n1v*RK") + var4 + "\"";
               }

               ++var2;
               if (Element.b) {
                  break;
               }
            }

            return null;
         }
      } else {
         return b("\u001d^\u0014R\u0007$~=\u0001I&r6\u001c\u000613:\u0017I+f4\u001eI*ax\u0017\u00045g!");
      }
   }

   public static boolean isXMLCharacter(char var0) {
      if (var0 == '\n') {
         return true;
      } else if (var0 == '\r') {
         return true;
      } else if (var0 == '\t') {
         return true;
      } else if (var0 < ' ') {
         return false;
      } else if (var0 <= '\ud7ff') {
         return true;
      } else if (var0 < '\ue000') {
         return false;
      } else if (var0 <= '�') {
         return true;
      } else if (var0 < 65536) {
         return false;
      } else {
         return var0 <= 1114111;
      }
   }

   public static boolean isXMLNameCharacter(char var0) {
      return isXMLLetter(var0) || isXMLDigit(var0) || var0 == '.' || var0 == '-' || var0 == '_' || var0 == ':' || isXMLCombiningChar(var0) || isXMLExtender(var0);
   }

   public static boolean isXMLNameStartCharacter(char var0) {
      return isXMLLetter(var0) || var0 == '_' || var0 == ':';
   }

   public static boolean isXMLLetterOrDigit(char var0) {
      return isXMLLetter(var0) || isXMLDigit(var0);
   }

   public static boolean isXMLLetter(char var0) {
      if (var0 < 'A') {
         return false;
      } else if (var0 <= 'Z') {
         return true;
      } else if (var0 < 'a') {
         return false;
      } else if (var0 <= 'z') {
         return true;
      } else if (var0 < 192) {
         return false;
      } else if (var0 <= 214) {
         return true;
      } else if (var0 < 216) {
         return false;
      } else if (var0 <= 246) {
         return true;
      } else if (var0 < 248) {
         return false;
      } else if (var0 <= 255) {
         return true;
      } else if (var0 < 256) {
         return false;
      } else if (var0 <= 305) {
         return true;
      } else if (var0 < 308) {
         return false;
      } else if (var0 <= 318) {
         return true;
      } else if (var0 < 321) {
         return false;
      } else if (var0 <= 328) {
         return true;
      } else if (var0 < 330) {
         return false;
      } else if (var0 <= 382) {
         return true;
      } else if (var0 < 384) {
         return false;
      } else if (var0 <= 451) {
         return true;
      } else if (var0 < 461) {
         return false;
      } else if (var0 <= 496) {
         return true;
      } else if (var0 < 500) {
         return false;
      } else if (var0 <= 501) {
         return true;
      } else if (var0 < 506) {
         return false;
      } else if (var0 <= 535) {
         return true;
      } else if (var0 < 592) {
         return false;
      } else if (var0 <= 680) {
         return true;
      } else if (var0 < 699) {
         return false;
      } else if (var0 <= 705) {
         return true;
      } else if (var0 == 902) {
         return true;
      } else if (var0 < 904) {
         return false;
      } else if (var0 <= 906) {
         return true;
      } else if (var0 == 908) {
         return true;
      } else if (var0 < 910) {
         return false;
      } else if (var0 <= 929) {
         return true;
      } else if (var0 < 931) {
         return false;
      } else if (var0 <= 974) {
         return true;
      } else if (var0 < 976) {
         return false;
      } else if (var0 <= 982) {
         return true;
      } else if (var0 == 986) {
         return true;
      } else if (var0 == 988) {
         return true;
      } else if (var0 == 990) {
         return true;
      } else if (var0 == 992) {
         return true;
      } else if (var0 < 994) {
         return false;
      } else if (var0 <= 1011) {
         return true;
      } else if (var0 < 1025) {
         return false;
      } else if (var0 <= 1036) {
         return true;
      } else if (var0 < 1038) {
         return false;
      } else if (var0 <= 1103) {
         return true;
      } else if (var0 < 1105) {
         return false;
      } else if (var0 <= 1116) {
         return true;
      } else if (var0 < 1118) {
         return false;
      } else if (var0 <= 1153) {
         return true;
      } else if (var0 < 1168) {
         return false;
      } else if (var0 <= 1220) {
         return true;
      } else if (var0 < 1223) {
         return false;
      } else if (var0 <= 1224) {
         return true;
      } else if (var0 < 1227) {
         return false;
      } else if (var0 <= 1228) {
         return true;
      } else if (var0 < 1232) {
         return false;
      } else if (var0 <= 1259) {
         return true;
      } else if (var0 < 1262) {
         return false;
      } else if (var0 <= 1269) {
         return true;
      } else if (var0 < 1272) {
         return false;
      } else if (var0 <= 1273) {
         return true;
      } else if (var0 < 1329) {
         return false;
      } else if (var0 <= 1366) {
         return true;
      } else if (var0 == 1369) {
         return true;
      } else if (var0 < 1377) {
         return false;
      } else if (var0 <= 1414) {
         return true;
      } else if (var0 < 1488) {
         return false;
      } else if (var0 <= 1514) {
         return true;
      } else if (var0 < 1520) {
         return false;
      } else if (var0 <= 1522) {
         return true;
      } else if (var0 < 1569) {
         return false;
      } else if (var0 <= 1594) {
         return true;
      } else if (var0 < 1601) {
         return false;
      } else if (var0 <= 1610) {
         return true;
      } else if (var0 < 1649) {
         return false;
      } else if (var0 <= 1719) {
         return true;
      } else if (var0 < 1722) {
         return false;
      } else if (var0 <= 1726) {
         return true;
      } else if (var0 < 1728) {
         return false;
      } else if (var0 <= 1742) {
         return true;
      } else if (var0 < 1744) {
         return false;
      } else if (var0 <= 1747) {
         return true;
      } else if (var0 == 1749) {
         return true;
      } else if (var0 < 1765) {
         return false;
      } else if (var0 <= 1766) {
         return true;
      } else if (var0 < 2309) {
         return false;
      } else if (var0 <= 2361) {
         return true;
      } else if (var0 == 2365) {
         return true;
      } else if (var0 < 2392) {
         return false;
      } else if (var0 <= 2401) {
         return true;
      } else if (var0 < 2437) {
         return false;
      } else if (var0 <= 2444) {
         return true;
      } else if (var0 < 2447) {
         return false;
      } else if (var0 <= 2448) {
         return true;
      } else if (var0 < 2451) {
         return false;
      } else if (var0 <= 2472) {
         return true;
      } else if (var0 < 2474) {
         return false;
      } else if (var0 <= 2480) {
         return true;
      } else if (var0 == 2482) {
         return true;
      } else if (var0 < 2486) {
         return false;
      } else if (var0 <= 2489) {
         return true;
      } else if (var0 < 2524) {
         return false;
      } else if (var0 <= 2525) {
         return true;
      } else if (var0 < 2527) {
         return false;
      } else if (var0 <= 2529) {
         return true;
      } else if (var0 < 2544) {
         return false;
      } else if (var0 <= 2545) {
         return true;
      } else if (var0 < 2565) {
         return false;
      } else if (var0 <= 2570) {
         return true;
      } else if (var0 < 2575) {
         return false;
      } else if (var0 <= 2576) {
         return true;
      } else if (var0 < 2579) {
         return false;
      } else if (var0 <= 2600) {
         return true;
      } else if (var0 < 2602) {
         return false;
      } else if (var0 <= 2608) {
         return true;
      } else if (var0 < 2610) {
         return false;
      } else if (var0 <= 2611) {
         return true;
      } else if (var0 < 2613) {
         return false;
      } else if (var0 <= 2614) {
         return true;
      } else if (var0 < 2616) {
         return false;
      } else if (var0 <= 2617) {
         return true;
      } else if (var0 < 2649) {
         return false;
      } else if (var0 <= 2652) {
         return true;
      } else if (var0 == 2654) {
         return true;
      } else if (var0 < 2674) {
         return false;
      } else if (var0 <= 2676) {
         return true;
      } else if (var0 < 2693) {
         return false;
      } else if (var0 <= 2699) {
         return true;
      } else if (var0 == 2701) {
         return true;
      } else if (var0 < 2703) {
         return false;
      } else if (var0 <= 2705) {
         return true;
      } else if (var0 < 2707) {
         return false;
      } else if (var0 <= 2728) {
         return true;
      } else if (var0 < 2730) {
         return false;
      } else if (var0 <= 2736) {
         return true;
      } else if (var0 < 2738) {
         return false;
      } else if (var0 <= 2739) {
         return true;
      } else if (var0 < 2741) {
         return false;
      } else if (var0 <= 2745) {
         return true;
      } else if (var0 == 2749) {
         return true;
      } else if (var0 == 2784) {
         return true;
      } else if (var0 < 2821) {
         return false;
      } else if (var0 <= 2828) {
         return true;
      } else if (var0 < 2831) {
         return false;
      } else if (var0 <= 2832) {
         return true;
      } else if (var0 < 2835) {
         return false;
      } else if (var0 <= 2856) {
         return true;
      } else if (var0 < 2858) {
         return false;
      } else if (var0 <= 2864) {
         return true;
      } else if (var0 < 2866) {
         return false;
      } else if (var0 <= 2867) {
         return true;
      } else if (var0 < 2870) {
         return false;
      } else if (var0 <= 2873) {
         return true;
      } else if (var0 == 2877) {
         return true;
      } else if (var0 < 2908) {
         return false;
      } else if (var0 <= 2909) {
         return true;
      } else if (var0 < 2911) {
         return false;
      } else if (var0 <= 2913) {
         return true;
      } else if (var0 < 2949) {
         return false;
      } else if (var0 <= 2954) {
         return true;
      } else if (var0 < 2958) {
         return false;
      } else if (var0 <= 2960) {
         return true;
      } else if (var0 < 2962) {
         return false;
      } else if (var0 <= 2965) {
         return true;
      } else if (var0 < 2969) {
         return false;
      } else if (var0 <= 2970) {
         return true;
      } else if (var0 == 2972) {
         return true;
      } else if (var0 < 2974) {
         return false;
      } else if (var0 <= 2975) {
         return true;
      } else if (var0 < 2979) {
         return false;
      } else if (var0 <= 2980) {
         return true;
      } else if (var0 < 2984) {
         return false;
      } else if (var0 <= 2986) {
         return true;
      } else if (var0 < 2990) {
         return false;
      } else if (var0 <= 2997) {
         return true;
      } else if (var0 < 2999) {
         return false;
      } else if (var0 <= 3001) {
         return true;
      } else if (var0 < 3077) {
         return false;
      } else if (var0 <= 3084) {
         return true;
      } else if (var0 < 3086) {
         return false;
      } else if (var0 <= 3088) {
         return true;
      } else if (var0 < 3090) {
         return false;
      } else if (var0 <= 3112) {
         return true;
      } else if (var0 < 3114) {
         return false;
      } else if (var0 <= 3123) {
         return true;
      } else if (var0 < 3125) {
         return false;
      } else if (var0 <= 3129) {
         return true;
      } else if (var0 < 3168) {
         return false;
      } else if (var0 <= 3169) {
         return true;
      } else if (var0 < 3205) {
         return false;
      } else if (var0 <= 3212) {
         return true;
      } else if (var0 < 3214) {
         return false;
      } else if (var0 <= 3216) {
         return true;
      } else if (var0 < 3218) {
         return false;
      } else if (var0 <= 3240) {
         return true;
      } else if (var0 < 3242) {
         return false;
      } else if (var0 <= 3251) {
         return true;
      } else if (var0 < 3253) {
         return false;
      } else if (var0 <= 3257) {
         return true;
      } else if (var0 == 3294) {
         return true;
      } else if (var0 < 3296) {
         return false;
      } else if (var0 <= 3297) {
         return true;
      } else if (var0 < 3333) {
         return false;
      } else if (var0 <= 3340) {
         return true;
      } else if (var0 < 3342) {
         return false;
      } else if (var0 <= 3344) {
         return true;
      } else if (var0 < 3346) {
         return false;
      } else if (var0 <= 3368) {
         return true;
      } else if (var0 < 3370) {
         return false;
      } else if (var0 <= 3385) {
         return true;
      } else if (var0 < 3424) {
         return false;
      } else if (var0 <= 3425) {
         return true;
      } else if (var0 < 3585) {
         return false;
      } else if (var0 <= 3630) {
         return true;
      } else if (var0 == 3632) {
         return true;
      } else if (var0 < 3634) {
         return false;
      } else if (var0 <= 3635) {
         return true;
      } else if (var0 < 3648) {
         return false;
      } else if (var0 <= 3653) {
         return true;
      } else if (var0 < 3713) {
         return false;
      } else if (var0 <= 3714) {
         return true;
      } else if (var0 == 3716) {
         return true;
      } else if (var0 < 3719) {
         return false;
      } else if (var0 <= 3720) {
         return true;
      } else if (var0 == 3722) {
         return true;
      } else if (var0 == 3725) {
         return true;
      } else if (var0 < 3732) {
         return false;
      } else if (var0 <= 3735) {
         return true;
      } else if (var0 < 3737) {
         return false;
      } else if (var0 <= 3743) {
         return true;
      } else if (var0 < 3745) {
         return false;
      } else if (var0 <= 3747) {
         return true;
      } else if (var0 == 3749) {
         return true;
      } else if (var0 == 3751) {
         return true;
      } else if (var0 < 3754) {
         return false;
      } else if (var0 <= 3755) {
         return true;
      } else if (var0 < 3757) {
         return false;
      } else if (var0 <= 3758) {
         return true;
      } else if (var0 == 3760) {
         return true;
      } else if (var0 < 3762) {
         return false;
      } else if (var0 <= 3763) {
         return true;
      } else if (var0 == 3773) {
         return true;
      } else if (var0 < 3776) {
         return false;
      } else if (var0 <= 3780) {
         return true;
      } else if (var0 < 3904) {
         return false;
      } else if (var0 <= 3911) {
         return true;
      } else if (var0 < 3913) {
         return false;
      } else if (var0 <= 3945) {
         return true;
      } else if (var0 < 4256) {
         return false;
      } else if (var0 <= 4293) {
         return true;
      } else if (var0 < 4304) {
         return false;
      } else if (var0 <= 4342) {
         return true;
      } else if (var0 == 4352) {
         return true;
      } else if (var0 < 4354) {
         return false;
      } else if (var0 <= 4355) {
         return true;
      } else if (var0 < 4357) {
         return false;
      } else if (var0 <= 4359) {
         return true;
      } else if (var0 == 4361) {
         return true;
      } else if (var0 < 4363) {
         return false;
      } else if (var0 <= 4364) {
         return true;
      } else if (var0 < 4366) {
         return false;
      } else if (var0 <= 4370) {
         return true;
      } else if (var0 == 4412) {
         return true;
      } else if (var0 == 4414) {
         return true;
      } else if (var0 == 4416) {
         return true;
      } else if (var0 == 4428) {
         return true;
      } else if (var0 == 4430) {
         return true;
      } else if (var0 == 4432) {
         return true;
      } else if (var0 < 4436) {
         return false;
      } else if (var0 <= 4437) {
         return true;
      } else if (var0 == 4441) {
         return true;
      } else if (var0 < 4447) {
         return false;
      } else if (var0 <= 4449) {
         return true;
      } else if (var0 == 4451) {
         return true;
      } else if (var0 == 4453) {
         return true;
      } else if (var0 == 4455) {
         return true;
      } else if (var0 == 4457) {
         return true;
      } else if (var0 < 4461) {
         return false;
      } else if (var0 <= 4462) {
         return true;
      } else if (var0 < 4466) {
         return false;
      } else if (var0 <= 4467) {
         return true;
      } else if (var0 == 4469) {
         return true;
      } else if (var0 == 4510) {
         return true;
      } else if (var0 == 4520) {
         return true;
      } else if (var0 == 4523) {
         return true;
      } else if (var0 < 4526) {
         return false;
      } else if (var0 <= 4527) {
         return true;
      } else if (var0 < 4535) {
         return false;
      } else if (var0 <= 4536) {
         return true;
      } else if (var0 == 4538) {
         return true;
      } else if (var0 < 4540) {
         return false;
      } else if (var0 <= 4546) {
         return true;
      } else if (var0 == 4587) {
         return true;
      } else if (var0 == 4592) {
         return true;
      } else if (var0 == 4601) {
         return true;
      } else if (var0 < 7680) {
         return false;
      } else if (var0 <= 7835) {
         return true;
      } else if (var0 < 7840) {
         return false;
      } else if (var0 <= 7929) {
         return true;
      } else if (var0 < 7936) {
         return false;
      } else if (var0 <= 7957) {
         return true;
      } else if (var0 < 7960) {
         return false;
      } else if (var0 <= 7965) {
         return true;
      } else if (var0 < 7968) {
         return false;
      } else if (var0 <= 8005) {
         return true;
      } else if (var0 < 8008) {
         return false;
      } else if (var0 <= 8013) {
         return true;
      } else if (var0 < 8016) {
         return false;
      } else if (var0 <= 8023) {
         return true;
      } else if (var0 == 8025) {
         return true;
      } else if (var0 == 8027) {
         return true;
      } else if (var0 == 8029) {
         return true;
      } else if (var0 < 8031) {
         return false;
      } else if (var0 <= 8061) {
         return true;
      } else if (var0 < 8064) {
         return false;
      } else if (var0 <= 8116) {
         return true;
      } else if (var0 < 8118) {
         return false;
      } else if (var0 <= 8124) {
         return true;
      } else if (var0 == 8126) {
         return true;
      } else if (var0 < 8130) {
         return false;
      } else if (var0 <= 8132) {
         return true;
      } else if (var0 < 8134) {
         return false;
      } else if (var0 <= 8140) {
         return true;
      } else if (var0 < 8144) {
         return false;
      } else if (var0 <= 8147) {
         return true;
      } else if (var0 < 8150) {
         return false;
      } else if (var0 <= 8155) {
         return true;
      } else if (var0 < 8160) {
         return false;
      } else if (var0 <= 8172) {
         return true;
      } else if (var0 < 8178) {
         return false;
      } else if (var0 <= 8180) {
         return true;
      } else if (var0 < 8182) {
         return false;
      } else if (var0 <= 8188) {
         return true;
      } else if (var0 == 8486) {
         return true;
      } else if (var0 < 8490) {
         return false;
      } else if (var0 <= 8491) {
         return true;
      } else if (var0 == 8494) {
         return true;
      } else if (var0 < 8576) {
         return false;
      } else if (var0 <= 8578) {
         return true;
      } else if (var0 == 12295) {
         return true;
      } else if (var0 < 12321) {
         return false;
      } else if (var0 <= 12329) {
         return true;
      } else if (var0 < 12353) {
         return false;
      } else if (var0 <= 12436) {
         return true;
      } else if (var0 < 12449) {
         return false;
      } else if (var0 <= 12538) {
         return true;
      } else if (var0 < 12549) {
         return false;
      } else if (var0 <= 12588) {
         return true;
      } else if (var0 < 19968) {
         return false;
      } else if (var0 <= '龥') {
         return true;
      } else if (var0 < '가') {
         return false;
      } else {
         return var0 <= '힣';
      }
   }

   public static boolean isXMLCombiningChar(char var0) {
      if (var0 < 768) {
         return false;
      } else if (var0 <= 837) {
         return true;
      } else if (var0 < 864) {
         return false;
      } else if (var0 <= 865) {
         return true;
      } else if (var0 < 1155) {
         return false;
      } else if (var0 <= 1158) {
         return true;
      } else if (var0 < 1425) {
         return false;
      } else if (var0 <= 1441) {
         return true;
      } else if (var0 < 1443) {
         return false;
      } else if (var0 <= 1465) {
         return true;
      } else if (var0 < 1467) {
         return false;
      } else if (var0 <= 1469) {
         return true;
      } else if (var0 == 1471) {
         return true;
      } else if (var0 < 1473) {
         return false;
      } else if (var0 <= 1474) {
         return true;
      } else if (var0 == 1476) {
         return true;
      } else if (var0 < 1611) {
         return false;
      } else if (var0 <= 1618) {
         return true;
      } else if (var0 == 1648) {
         return true;
      } else if (var0 < 1750) {
         return false;
      } else if (var0 <= 1756) {
         return true;
      } else if (var0 < 1757) {
         return false;
      } else if (var0 <= 1759) {
         return true;
      } else if (var0 < 1760) {
         return false;
      } else if (var0 <= 1764) {
         return true;
      } else if (var0 < 1767) {
         return false;
      } else if (var0 <= 1768) {
         return true;
      } else if (var0 < 1770) {
         return false;
      } else if (var0 <= 1773) {
         return true;
      } else if (var0 < 2305) {
         return false;
      } else if (var0 <= 2307) {
         return true;
      } else if (var0 == 2364) {
         return true;
      } else if (var0 < 2366) {
         return false;
      } else if (var0 <= 2380) {
         return true;
      } else if (var0 == 2381) {
         return true;
      } else if (var0 < 2385) {
         return false;
      } else if (var0 <= 2388) {
         return true;
      } else if (var0 < 2402) {
         return false;
      } else if (var0 <= 2403) {
         return true;
      } else if (var0 < 2433) {
         return false;
      } else if (var0 <= 2435) {
         return true;
      } else if (var0 == 2492) {
         return true;
      } else if (var0 == 2494) {
         return true;
      } else if (var0 == 2495) {
         return true;
      } else if (var0 < 2496) {
         return false;
      } else if (var0 <= 2500) {
         return true;
      } else if (var0 < 2503) {
         return false;
      } else if (var0 <= 2504) {
         return true;
      } else if (var0 < 2507) {
         return false;
      } else if (var0 <= 2509) {
         return true;
      } else if (var0 == 2519) {
         return true;
      } else if (var0 < 2530) {
         return false;
      } else if (var0 <= 2531) {
         return true;
      } else if (var0 == 2562) {
         return true;
      } else if (var0 == 2620) {
         return true;
      } else if (var0 == 2622) {
         return true;
      } else if (var0 == 2623) {
         return true;
      } else if (var0 < 2624) {
         return false;
      } else if (var0 <= 2626) {
         return true;
      } else if (var0 < 2631) {
         return false;
      } else if (var0 <= 2632) {
         return true;
      } else if (var0 < 2635) {
         return false;
      } else if (var0 <= 2637) {
         return true;
      } else if (var0 < 2672) {
         return false;
      } else if (var0 <= 2673) {
         return true;
      } else if (var0 < 2689) {
         return false;
      } else if (var0 <= 2691) {
         return true;
      } else if (var0 == 2748) {
         return true;
      } else if (var0 < 2750) {
         return false;
      } else if (var0 <= 2757) {
         return true;
      } else if (var0 < 2759) {
         return false;
      } else if (var0 <= 2761) {
         return true;
      } else if (var0 < 2763) {
         return false;
      } else if (var0 <= 2765) {
         return true;
      } else if (var0 < 2817) {
         return false;
      } else if (var0 <= 2819) {
         return true;
      } else if (var0 == 2876) {
         return true;
      } else if (var0 < 2878) {
         return false;
      } else if (var0 <= 2883) {
         return true;
      } else if (var0 < 2887) {
         return false;
      } else if (var0 <= 2888) {
         return true;
      } else if (var0 < 2891) {
         return false;
      } else if (var0 <= 2893) {
         return true;
      } else if (var0 < 2902) {
         return false;
      } else if (var0 <= 2903) {
         return true;
      } else if (var0 < 2946) {
         return false;
      } else if (var0 <= 2947) {
         return true;
      } else if (var0 < 3006) {
         return false;
      } else if (var0 <= 3010) {
         return true;
      } else if (var0 < 3014) {
         return false;
      } else if (var0 <= 3016) {
         return true;
      } else if (var0 < 3018) {
         return false;
      } else if (var0 <= 3021) {
         return true;
      } else if (var0 == 3031) {
         return true;
      } else if (var0 < 3073) {
         return false;
      } else if (var0 <= 3075) {
         return true;
      } else if (var0 < 3134) {
         return false;
      } else if (var0 <= 3140) {
         return true;
      } else if (var0 < 3142) {
         return false;
      } else if (var0 <= 3144) {
         return true;
      } else if (var0 < 3146) {
         return false;
      } else if (var0 <= 3149) {
         return true;
      } else if (var0 < 3157) {
         return false;
      } else if (var0 <= 3158) {
         return true;
      } else if (var0 < 3202) {
         return false;
      } else if (var0 <= 3203) {
         return true;
      } else if (var0 < 3262) {
         return false;
      } else if (var0 <= 3268) {
         return true;
      } else if (var0 < 3270) {
         return false;
      } else if (var0 <= 3272) {
         return true;
      } else if (var0 < 3274) {
         return false;
      } else if (var0 <= 3277) {
         return true;
      } else if (var0 < 3285) {
         return false;
      } else if (var0 <= 3286) {
         return true;
      } else if (var0 < 3330) {
         return false;
      } else if (var0 <= 3331) {
         return true;
      } else if (var0 < 3390) {
         return false;
      } else if (var0 <= 3395) {
         return true;
      } else if (var0 < 3398) {
         return false;
      } else if (var0 <= 3400) {
         return true;
      } else if (var0 < 3402) {
         return false;
      } else if (var0 <= 3405) {
         return true;
      } else if (var0 == 3415) {
         return true;
      } else if (var0 == 3633) {
         return true;
      } else if (var0 < 3636) {
         return false;
      } else if (var0 <= 3642) {
         return true;
      } else if (var0 < 3655) {
         return false;
      } else if (var0 <= 3662) {
         return true;
      } else if (var0 == 3761) {
         return true;
      } else if (var0 < 3764) {
         return false;
      } else if (var0 <= 3769) {
         return true;
      } else if (var0 < 3771) {
         return false;
      } else if (var0 <= 3772) {
         return true;
      } else if (var0 < 3784) {
         return false;
      } else if (var0 <= 3789) {
         return true;
      } else if (var0 < 3864) {
         return false;
      } else if (var0 <= 3865) {
         return true;
      } else if (var0 == 3893) {
         return true;
      } else if (var0 == 3895) {
         return true;
      } else if (var0 == 3897) {
         return true;
      } else if (var0 == 3902) {
         return true;
      } else if (var0 == 3903) {
         return true;
      } else if (var0 < 3953) {
         return false;
      } else if (var0 <= 3972) {
         return true;
      } else if (var0 < 3974) {
         return false;
      } else if (var0 <= 3979) {
         return true;
      } else if (var0 < 3984) {
         return false;
      } else if (var0 <= 3989) {
         return true;
      } else if (var0 == 3991) {
         return true;
      } else if (var0 < 3993) {
         return false;
      } else if (var0 <= 4013) {
         return true;
      } else if (var0 < 4017) {
         return false;
      } else if (var0 <= 4023) {
         return true;
      } else if (var0 == 4025) {
         return true;
      } else if (var0 < 8400) {
         return false;
      } else if (var0 <= 8412) {
         return true;
      } else if (var0 == 8417) {
         return true;
      } else if (var0 < 12330) {
         return false;
      } else if (var0 <= 12335) {
         return true;
      } else if (var0 == 12441) {
         return true;
      } else {
         return var0 == 12442;
      }
   }

   public static boolean isXMLExtender(char var0) {
      if (var0 < 182) {
         return false;
      } else if (var0 == 183) {
         return true;
      } else if (var0 == 720) {
         return true;
      } else if (var0 == 721) {
         return true;
      } else if (var0 == 903) {
         return true;
      } else if (var0 == 1600) {
         return true;
      } else if (var0 == 3654) {
         return true;
      } else if (var0 == 3782) {
         return true;
      } else if (var0 == 12293) {
         return true;
      } else if (var0 < 12337) {
         return false;
      } else if (var0 <= 12341) {
         return true;
      } else if (var0 < 12445) {
         return false;
      } else if (var0 <= 12446) {
         return true;
      } else if (var0 < 12540) {
         return false;
      } else {
         return var0 <= 12542;
      }
   }

   public static boolean isXMLDigit(char var0) {
      if (var0 < '0') {
         return false;
      } else if (var0 <= '9') {
         return true;
      } else if (var0 < 1632) {
         return false;
      } else if (var0 <= 1641) {
         return true;
      } else if (var0 < 1776) {
         return false;
      } else if (var0 <= 1785) {
         return true;
      } else if (var0 < 2406) {
         return false;
      } else if (var0 <= 2415) {
         return true;
      } else if (var0 < 2534) {
         return false;
      } else if (var0 <= 2543) {
         return true;
      } else if (var0 < 2662) {
         return false;
      } else if (var0 <= 2671) {
         return true;
      } else if (var0 < 2790) {
         return false;
      } else if (var0 <= 2799) {
         return true;
      } else if (var0 < 2918) {
         return false;
      } else if (var0 <= 2927) {
         return true;
      } else if (var0 < 3047) {
         return false;
      } else if (var0 <= 3055) {
         return true;
      } else if (var0 < 3174) {
         return false;
      } else if (var0 <= 3183) {
         return true;
      } else if (var0 < 3302) {
         return false;
      } else if (var0 <= 3311) {
         return true;
      } else if (var0 < 3430) {
         return false;
      } else if (var0 <= 3439) {
         return true;
      } else if (var0 < 3664) {
         return false;
      } else if (var0 <= 3673) {
         return true;
      } else if (var0 < 3792) {
         return false;
      } else if (var0 <= 3801) {
         return true;
      } else if (var0 < 3872) {
         return false;
      } else {
         return var0 <= 3881;
      }
   }

   private static boolean a(char var0) {
      if (var0 >= 'A' && var0 <= 'Z') {
         return true;
      } else if (var0 >= 'a' && var0 <= 'z') {
         return true;
      } else if (var0 >= 192 && var0 <= 214) {
         return true;
      } else if (var0 >= 216 && var0 <= 246) {
         return true;
      } else if (var0 >= 248 && var0 <= 255) {
         return true;
      } else if (var0 >= 256 && var0 <= 305) {
         return true;
      } else if (var0 >= 308 && var0 <= 318) {
         return true;
      } else if (var0 >= 321 && var0 <= 328) {
         return true;
      } else if (var0 >= 330 && var0 <= 382) {
         return true;
      } else if (var0 >= 384 && var0 <= 451) {
         return true;
      } else if (var0 >= 461 && var0 <= 496) {
         return true;
      } else if (var0 >= 500 && var0 <= 501) {
         return true;
      } else if (var0 >= 506 && var0 <= 535) {
         return true;
      } else if (var0 >= 592 && var0 <= 680) {
         return true;
      } else if (var0 >= 699 && var0 <= 705) {
         return true;
      } else if (var0 >= 904 && var0 <= 906) {
         return true;
      } else if (var0 == 902) {
         return true;
      } else if (var0 == 908) {
         return true;
      } else if (var0 >= 910 && var0 <= 929) {
         return true;
      } else if (var0 >= 931 && var0 <= 974) {
         return true;
      } else if (var0 >= 976 && var0 <= 982) {
         return true;
      } else if (var0 == 986) {
         return true;
      } else if (var0 == 988) {
         return true;
      } else if (var0 == 990) {
         return true;
      } else if (var0 == 992) {
         return true;
      } else if (var0 >= 994 && var0 <= 1011) {
         return true;
      } else if (var0 >= 1025 && var0 <= 1036) {
         return true;
      } else if (var0 >= 1038 && var0 <= 1103) {
         return true;
      } else if (var0 >= 1105 && var0 <= 1116) {
         return true;
      } else if (var0 >= 1118 && var0 <= 1153) {
         return true;
      } else if (var0 >= 1168 && var0 <= 1220) {
         return true;
      } else if (var0 >= 1223 && var0 <= 1224) {
         return true;
      } else if (var0 >= 1227 && var0 <= 1228) {
         return true;
      } else if (var0 >= 1232 && var0 <= 1259) {
         return true;
      } else if (var0 >= 1262 && var0 <= 1269) {
         return true;
      } else if (var0 >= 1272 && var0 <= 1273) {
         return true;
      } else if (var0 >= 1329 && var0 <= 1366) {
         return true;
      } else if (var0 == 1369) {
         return true;
      } else if (var0 >= 1377 && var0 <= 1414) {
         return true;
      } else if (var0 >= 1488 && var0 <= 1514) {
         return true;
      } else if (var0 >= 1520 && var0 <= 1522) {
         return true;
      } else if (var0 >= 1569 && var0 <= 1594) {
         return true;
      } else if (var0 >= 1601 && var0 <= 1610) {
         return true;
      } else if (var0 >= 1649 && var0 <= 1719) {
         return true;
      } else if (var0 >= 1722 && var0 <= 1726) {
         return true;
      } else if (var0 >= 1728 && var0 <= 1742) {
         return true;
      } else if (var0 >= 1744 && var0 <= 1747) {
         return true;
      } else if (var0 == 1749) {
         return true;
      } else if (var0 >= 1765 && var0 <= 1766) {
         return true;
      } else if (var0 >= 2309 && var0 <= 2361) {
         return true;
      } else if (var0 == 2365) {
         return true;
      } else if (var0 >= 2392 && var0 <= 2401) {
         return true;
      } else if (var0 >= 2437 && var0 <= 2444) {
         return true;
      } else if (var0 >= 2447 && var0 <= 2448) {
         return true;
      } else if (var0 >= 2451 && var0 <= 2472) {
         return true;
      } else if (var0 >= 2474 && var0 <= 2480) {
         return true;
      } else if (var0 == 2482) {
         return true;
      } else if (var0 >= 2486 && var0 <= 2489) {
         return true;
      } else if (var0 >= 2524 && var0 <= 2525) {
         return true;
      } else if (var0 >= 2527 && var0 <= 2529) {
         return true;
      } else if (var0 >= 2544 && var0 <= 2545) {
         return true;
      } else if (var0 >= 2565 && var0 <= 2570) {
         return true;
      } else if (var0 >= 2575 && var0 <= 2576) {
         return true;
      } else if (var0 >= 2579 && var0 <= 2600) {
         return true;
      } else if (var0 >= 2602 && var0 <= 2608) {
         return true;
      } else if (var0 >= 2610 && var0 <= 2611) {
         return true;
      } else if (var0 >= 2613 && var0 <= 2614) {
         return true;
      } else if (var0 >= 2616 && var0 <= 2617) {
         return true;
      } else if (var0 >= 2649 && var0 <= 2652) {
         return true;
      } else if (var0 == 2654) {
         return true;
      } else if (var0 >= 2674 && var0 <= 2676) {
         return true;
      } else if (var0 >= 2693 && var0 <= 2699) {
         return true;
      } else if (var0 == 2701) {
         return true;
      } else if (var0 >= 2703 && var0 <= 2705) {
         return true;
      } else if (var0 >= 2707 && var0 <= 2728) {
         return true;
      } else if (var0 >= 2730 && var0 <= 2736) {
         return true;
      } else if (var0 >= 2738 && var0 <= 2739) {
         return true;
      } else if (var0 >= 2741 && var0 <= 2745) {
         return true;
      } else if (var0 == 2749) {
         return true;
      } else if (var0 == 2784) {
         return true;
      } else if (var0 >= 2821 && var0 <= 2828) {
         return true;
      } else if (var0 >= 2831 && var0 <= 2832) {
         return true;
      } else if (var0 >= 2835 && var0 <= 2856) {
         return true;
      } else if (var0 >= 2858 && var0 <= 2864) {
         return true;
      } else if (var0 >= 2866 && var0 <= 2867) {
         return true;
      } else if (var0 >= 2870 && var0 <= 2873) {
         return true;
      } else if (var0 == 2877) {
         return true;
      } else if (var0 >= 2908 && var0 <= 2909) {
         return true;
      } else if (var0 >= 2911 && var0 <= 2913) {
         return true;
      } else if (var0 >= 2949 && var0 <= 2954) {
         return true;
      } else if (var0 >= 2958 && var0 <= 2960) {
         return true;
      } else if (var0 >= 2962 && var0 <= 2965) {
         return true;
      } else if (var0 >= 2969 && var0 <= 2970) {
         return true;
      } else if (var0 == 2972) {
         return true;
      } else if (var0 >= 2974 && var0 <= 2975) {
         return true;
      } else if (var0 >= 2979 && var0 <= 2980) {
         return true;
      } else if (var0 >= 2984 && var0 <= 2986) {
         return true;
      } else if (var0 >= 2990 && var0 <= 2997) {
         return true;
      } else if (var0 >= 2999 && var0 <= 3001) {
         return true;
      } else if (var0 >= 3077 && var0 <= 3084) {
         return true;
      } else if (var0 >= 3086 && var0 <= 3088) {
         return true;
      } else if (var0 >= 3090 && var0 <= 3112) {
         return true;
      } else if (var0 >= 3114 && var0 <= 3123) {
         return true;
      } else if (var0 >= 3125 && var0 <= 3129) {
         return true;
      } else if (var0 >= 3168 && var0 <= 3169) {
         return true;
      } else if (var0 >= 3205 && var0 <= 3212) {
         return true;
      } else if (var0 >= 3214 && var0 <= 3216) {
         return true;
      } else if (var0 >= 3218 && var0 <= 3240) {
         return true;
      } else if (var0 >= 3242 && var0 <= 3251) {
         return true;
      } else if (var0 >= 3253 && var0 <= 3257) {
         return true;
      } else if (var0 == 3294) {
         return true;
      } else if (var0 >= 3296 && var0 <= 3297) {
         return true;
      } else if (var0 >= 3333 && var0 <= 3340) {
         return true;
      } else if (var0 >= 3342 && var0 <= 3344) {
         return true;
      } else if (var0 >= 3346 && var0 <= 3368) {
         return true;
      } else if (var0 >= 3370 && var0 <= 3385) {
         return true;
      } else if (var0 >= 3424 && var0 <= 3425) {
         return true;
      } else if (var0 >= 3585 && var0 <= 3630) {
         return true;
      } else if (var0 == 3632) {
         return true;
      } else if (var0 >= 3634 && var0 <= 3635) {
         return true;
      } else if (var0 >= 3648 && var0 <= 3653) {
         return true;
      } else if (var0 >= 3713 && var0 <= 3714) {
         return true;
      } else if (var0 == 3716) {
         return true;
      } else if (var0 >= 3719 && var0 <= 3720) {
         return true;
      } else if (var0 == 3722) {
         return true;
      } else if (var0 == 3725) {
         return true;
      } else if (var0 >= 3732 && var0 <= 3735) {
         return true;
      } else if (var0 >= 3737 && var0 <= 3743) {
         return true;
      } else if (var0 >= 3745 && var0 <= 3747) {
         return true;
      } else if (var0 == 3749) {
         return true;
      } else if (var0 == 3751) {
         return true;
      } else if (var0 >= 3754 && var0 <= 3755) {
         return true;
      } else if (var0 >= 3757 && var0 <= 3758) {
         return true;
      } else if (var0 == 3760) {
         return true;
      } else if (var0 >= 3762 && var0 <= 3763) {
         return true;
      } else if (var0 == 3773) {
         return true;
      } else if (var0 >= 3776 && var0 <= 3780) {
         return true;
      } else if (var0 >= 3904 && var0 <= 3911) {
         return true;
      } else if (var0 >= 3913 && var0 <= 3945) {
         return true;
      } else if (var0 >= 4256 && var0 <= 4293) {
         return true;
      } else if (var0 >= 4304 && var0 <= 4342) {
         return true;
      } else if (var0 == 4352) {
         return true;
      } else if (var0 >= 4354 && var0 <= 4355) {
         return true;
      } else if (var0 >= 4357 && var0 <= 4359) {
         return true;
      } else if (var0 == 4361) {
         return true;
      } else if (var0 >= 4363 && var0 <= 4364) {
         return true;
      } else if (var0 >= 4366 && var0 <= 4370) {
         return true;
      } else if (var0 == 4412) {
         return true;
      } else if (var0 == 4414) {
         return true;
      } else if (var0 == 4416) {
         return true;
      } else if (var0 == 4428) {
         return true;
      } else if (var0 == 4430) {
         return true;
      } else if (var0 == 4432) {
         return true;
      } else if (var0 >= 4436 && var0 <= 4437) {
         return true;
      } else if (var0 == 4441) {
         return true;
      } else if (var0 >= 4447 && var0 <= 4449) {
         return true;
      } else if (var0 == 4451) {
         return true;
      } else if (var0 == 4453) {
         return true;
      } else if (var0 == 4455) {
         return true;
      } else if (var0 == 4457) {
         return true;
      } else if (var0 >= 4461 && var0 <= 4462) {
         return true;
      } else if (var0 >= 4466 && var0 <= 4467) {
         return true;
      } else if (var0 == 4469) {
         return true;
      } else if (var0 == 4510) {
         return true;
      } else if (var0 == 4520) {
         return true;
      } else if (var0 == 4523) {
         return true;
      } else if (var0 >= 4526 && var0 <= 4527) {
         return true;
      } else if (var0 >= 4535 && var0 <= 4536) {
         return true;
      } else if (var0 == 4538) {
         return true;
      } else if (var0 >= 4540 && var0 <= 4546) {
         return true;
      } else if (var0 == 4587) {
         return true;
      } else if (var0 == 4592) {
         return true;
      } else if (var0 == 4601) {
         return true;
      } else if (var0 >= 7680 && var0 <= 7835) {
         return true;
      } else if (var0 >= 7840 && var0 <= 7929) {
         return true;
      } else if (var0 >= 7936 && var0 <= 7957) {
         return true;
      } else if (var0 >= 7960 && var0 <= 7965) {
         return true;
      } else if (var0 >= 7968 && var0 <= 8005) {
         return true;
      } else if (var0 >= 8008 && var0 <= 8013) {
         return true;
      } else if (var0 >= 8016 && var0 <= 8023) {
         return true;
      } else if (var0 == 8025) {
         return true;
      } else if (var0 == 8027) {
         return true;
      } else if (var0 == 8029) {
         return true;
      } else if (var0 >= 8031 && var0 <= 8061) {
         return true;
      } else if (var0 >= 8064 && var0 <= 8116) {
         return true;
      } else if (var0 >= 8118 && var0 <= 8124) {
         return true;
      } else if (var0 == 8126) {
         return true;
      } else if (var0 >= 8130 && var0 <= 8132) {
         return true;
      } else if (var0 >= 8134 && var0 <= 8140) {
         return true;
      } else if (var0 >= 8144 && var0 <= 8147) {
         return true;
      } else if (var0 >= 8150 && var0 <= 8155) {
         return true;
      } else if (var0 >= 8160 && var0 <= 8172) {
         return true;
      } else if (var0 >= 8178 && var0 <= 8180) {
         return true;
      } else if (var0 >= 8182 && var0 <= 8188) {
         return true;
      } else if (var0 == 8486) {
         return true;
      } else if (var0 >= 8490 && var0 <= 8491) {
         return true;
      } else if (var0 == 8494) {
         return true;
      } else if (var0 >= 8576 && var0 <= 8578) {
         return true;
      } else if (var0 >= 12353 && var0 <= 12436) {
         return true;
      } else if (var0 >= 12449 && var0 <= 12538) {
         return true;
      } else if (var0 >= 12549 && var0 <= 12588) {
         return true;
      } else if (var0 >= '가' && var0 <= '힣') {
         return true;
      } else if (var0 >= 19968 && var0 <= '龥') {
         return true;
      } else if (var0 == 12295) {
         return true;
      } else {
         return var0 >= 12321 && var0 <= 12329;
      }
   }

   private static boolean b(char var0) {
      if (var0 >= '0' && var0 <= '9') {
         return true;
      } else if (var0 >= 1632 && var0 <= 1641) {
         return true;
      } else if (var0 >= 1776 && var0 <= 1785) {
         return true;
      } else if (var0 >= 2406 && var0 <= 2415) {
         return true;
      } else if (var0 >= 2534 && var0 <= 2543) {
         return true;
      } else if (var0 >= 2662 && var0 <= 2671) {
         return true;
      } else if (var0 >= 2790 && var0 <= 2799) {
         return true;
      } else if (var0 >= 2918 && var0 <= 2927) {
         return true;
      } else if (var0 >= 3047 && var0 <= 3055) {
         return true;
      } else if (var0 >= 3174 && var0 <= 3183) {
         return true;
      } else if (var0 >= 3302 && var0 <= 3311) {
         return true;
      } else if (var0 >= 3430 && var0 <= 3439) {
         return true;
      } else if (var0 >= 3664 && var0 <= 3673) {
         return true;
      } else if (var0 >= 3792 && var0 <= 3801) {
         return true;
      } else {
         return var0 >= 3872 && var0 <= 3881;
      }
   }

   private static boolean c(char var0) {
      if (var0 >= 768 && var0 <= 837) {
         return true;
      } else if (var0 >= 864 && var0 <= 865) {
         return true;
      } else if (var0 >= 1155 && var0 <= 1158) {
         return true;
      } else if (var0 >= 1425 && var0 <= 1441) {
         return true;
      } else if (var0 >= 1443 && var0 <= 1465) {
         return true;
      } else if (var0 >= 1467 && var0 <= 1469) {
         return true;
      } else if (var0 == 1471) {
         return true;
      } else if (var0 >= 1473 && var0 <= 1474) {
         return true;
      } else if (var0 == 1476) {
         return true;
      } else if (var0 >= 1611 && var0 <= 1618) {
         return true;
      } else if (var0 == 1648) {
         return true;
      } else if (var0 >= 1750 && var0 <= 1756) {
         return true;
      } else if (var0 >= 1757 && var0 <= 1759) {
         return true;
      } else if (var0 >= 1760 && var0 <= 1764) {
         return true;
      } else if (var0 >= 1767 && var0 <= 1768) {
         return true;
      } else if (var0 >= 1770 && var0 <= 1773) {
         return true;
      } else if (var0 >= 2305 && var0 <= 2307) {
         return true;
      } else if (var0 == 2364) {
         return true;
      } else if (var0 >= 2366 && var0 <= 2380) {
         return true;
      } else if (var0 == 2381) {
         return true;
      } else if (var0 >= 2385 && var0 <= 2388) {
         return true;
      } else if (var0 >= 2402 && var0 <= 2403) {
         return true;
      } else if (var0 >= 2433 && var0 <= 2435) {
         return true;
      } else if (var0 == 2492) {
         return true;
      } else if (var0 == 2494) {
         return true;
      } else if (var0 == 2495) {
         return true;
      } else if (var0 >= 2496 && var0 <= 2500) {
         return true;
      } else if (var0 >= 2503 && var0 <= 2504) {
         return true;
      } else if (var0 >= 2507 && var0 <= 2509) {
         return true;
      } else if (var0 == 2519) {
         return true;
      } else if (var0 >= 2530 && var0 <= 2531) {
         return true;
      } else if (var0 == 2562) {
         return true;
      } else if (var0 == 2620) {
         return true;
      } else if (var0 == 2622) {
         return true;
      } else if (var0 == 2623) {
         return true;
      } else if (var0 >= 2624 && var0 <= 2626) {
         return true;
      } else if (var0 >= 2631 && var0 <= 2632) {
         return true;
      } else if (var0 >= 2635 && var0 <= 2637) {
         return true;
      } else if (var0 >= 2672 && var0 <= 2673) {
         return true;
      } else if (var0 >= 2689 && var0 <= 2691) {
         return true;
      } else if (var0 == 2748) {
         return true;
      } else if (var0 >= 2750 && var0 <= 2757) {
         return true;
      } else if (var0 >= 2759 && var0 <= 2761) {
         return true;
      } else if (var0 >= 2763 && var0 <= 2765) {
         return true;
      } else if (var0 >= 2817 && var0 <= 2819) {
         return true;
      } else if (var0 == 2876) {
         return true;
      } else if (var0 >= 2878 && var0 <= 2883) {
         return true;
      } else if (var0 >= 2887 && var0 <= 2888) {
         return true;
      } else if (var0 >= 2891 && var0 <= 2893) {
         return true;
      } else if (var0 >= 2902 && var0 <= 2903) {
         return true;
      } else if (var0 >= 2946 && var0 <= 2947) {
         return true;
      } else if (var0 >= 3006 && var0 <= 3010) {
         return true;
      } else if (var0 >= 3014 && var0 <= 3016) {
         return true;
      } else if (var0 >= 3018 && var0 <= 3021) {
         return true;
      } else if (var0 == 3031) {
         return true;
      } else if (var0 >= 3073 && var0 <= 3075) {
         return true;
      } else if (var0 >= 3134 && var0 <= 3140) {
         return true;
      } else if (var0 >= 3142 && var0 <= 3144) {
         return true;
      } else if (var0 >= 3146 && var0 <= 3149) {
         return true;
      } else if (var0 >= 3157 && var0 <= 3158) {
         return true;
      } else if (var0 >= 3202 && var0 <= 3203) {
         return true;
      } else if (var0 >= 3262 && var0 <= 3268) {
         return true;
      } else if (var0 >= 3270 && var0 <= 3272) {
         return true;
      } else if (var0 >= 3274 && var0 <= 3277) {
         return true;
      } else if (var0 >= 3285 && var0 <= 3286) {
         return true;
      } else if (var0 >= 3330 && var0 <= 3331) {
         return true;
      } else if (var0 >= 3390 && var0 <= 3395) {
         return true;
      } else if (var0 >= 3398 && var0 <= 3400) {
         return true;
      } else if (var0 >= 3402 && var0 <= 3405) {
         return true;
      } else if (var0 == 3415) {
         return true;
      } else if (var0 == 3633) {
         return true;
      } else if (var0 >= 3636 && var0 <= 3642) {
         return true;
      } else if (var0 >= 3655 && var0 <= 3662) {
         return true;
      } else if (var0 == 3761) {
         return true;
      } else if (var0 >= 3764 && var0 <= 3769) {
         return true;
      } else if (var0 >= 3771 && var0 <= 3772) {
         return true;
      } else if (var0 >= 3784 && var0 <= 3789) {
         return true;
      } else if (var0 >= 3864 && var0 <= 3865) {
         return true;
      } else if (var0 == 3893) {
         return true;
      } else if (var0 == 3895) {
         return true;
      } else if (var0 == 3897) {
         return true;
      } else if (var0 == 3902) {
         return true;
      } else if (var0 == 3903) {
         return true;
      } else if (var0 >= 3953 && var0 <= 3972) {
         return true;
      } else if (var0 >= 3974 && var0 <= 3979) {
         return true;
      } else if (var0 >= 3984 && var0 <= 3989) {
         return true;
      } else if (var0 == 3991) {
         return true;
      } else if (var0 >= 3993 && var0 <= 4013) {
         return true;
      } else if (var0 >= 4017 && var0 <= 4023) {
         return true;
      } else if (var0 == 4025) {
         return true;
      } else if (var0 >= 8400 && var0 <= 8412) {
         return true;
      } else if (var0 == 8417) {
         return true;
      } else if (var0 >= 12330 && var0 <= 12335) {
         return true;
      } else if (var0 == 12441) {
         return true;
      } else {
         return var0 == 12442;
      }
   }

   private static boolean d(char var0) {
      if (var0 == 183) {
         return true;
      } else if (var0 == 720) {
         return true;
      } else if (var0 == 721) {
         return true;
      } else if (var0 == 903) {
         return true;
      } else if (var0 == 1600) {
         return true;
      } else if (var0 == 3654) {
         return true;
      } else if (var0 == 3782) {
         return true;
      } else if (var0 == 12293) {
         return true;
      } else if (var0 >= 12337 && var0 <= 12341) {
         return true;
      } else if (var0 >= 12445 && var0 <= 12446) {
         return true;
      } else {
         return var0 >= 12540 && var0 <= 12542;
      }
   }

   private static boolean e(char var0) {
      if (var0 >= ' ' && var0 <= '\ud7ff') {
         return true;
      } else if (var0 >= '\ue000' && var0 <= '�') {
         return true;
      } else if (var0 >= 65536 && var0 <= 1114111) {
         return true;
      } else if (var0 == '\n') {
         return true;
      } else if (var0 == '\r') {
         return true;
      } else {
         return var0 == '\t';
      }
   }

   public static void main(String[] var0) {
      boolean var2 = Element.b;
      int var1 = 0;

      int var10000;
      int var10001;
      while(true) {
         if (var1 < 65536) {
            var10000 = isXMLLetter((char)var1);
            var10001 = a((char)var1);
            if (var2) {
               break;
            }

            if (var10000 != var10001) {
               System.out.println(b(",`\u0000?%\tv,\u0006\f735\u001b\u001a(r,\u0011\u0001\u007f3") + var1 + b("e{=\nSe") + Integer.toHexString(var1));
            }

            ++var1;
            if (!var2) {
               continue;
            }

            SnmpException.b = !SnmpException.b;
         }

         var1 = 0;
         var10000 = var1;
         var10001 = 65536;
         break;
      }

      while(true) {
         label101: {
            if (var10000 < var10001) {
               var10000 = isXMLDigit((char)var1);
               var10001 = b((char)var1);
               if (var2) {
                  break label101;
               }

               if (var10000 != var10001) {
                  System.out.println(b(",`\u0000?%\u0001z?\u001b\u001de~1\u0001\u0004$g;\u001aSe") + var1 + b("e{=\nSe") + Integer.toHexString(var1));
               }

               ++var1;
               if (!var2) {
                  var10000 = var1;
                  var10001 = 65536;
                  continue;
               }

               var1 = 0;
            } else {
               var1 = 0;
            }

            var10000 = var1;
            var10001 = 65536;
         }

         while(true) {
            label121: {
               if (var10000 < var10001) {
                  var10000 = isXMLCombiningChar((char)var1);
                  var10001 = c((char)var1);
                  if (var2) {
                     break label121;
                  }

                  if (var10000 != var10001) {
                     System.out.println(b(",`\u0000?%\u0006|5\u0010\u0000+z6\u0015*-r*R\u0004,`5\u0013\u001d&{bR") + var1 + b("e{=\nSe") + Integer.toHexString(var1));
                  }

                  ++var1;
                  if (!var2) {
                     var10000 = var1;
                     var10001 = 65536;
                     continue;
                  }
               }

               var1 = 0;
               var10000 = var1;
               var10001 = 65536;
            }

            while(true) {
               label71: {
                  if (var10000 < var10001) {
                     var10000 = isXMLExtender((char)var1);
                     var10001 = d((char)var1);
                     if (var2) {
                        break label71;
                     }

                     if (var10000 != var10001) {
                        System.out.println(b(",`\u0000?%\u0000k,\u0017\u0007!v*R\u0004,`5\u0013\u001d&{bR") + var1 + b("e{=\nSe") + Integer.toHexString(var1));
                     }

                     ++var1;
                     if (!var2) {
                        var10000 = var1;
                        var10001 = 65536;
                        continue;
                     }

                     var1 = 0;
                  } else {
                     var1 = 0;
                  }

                  var10000 = var1;
               }

               for(var10001 = 65536; var10000 < var10001; var10001 = 65536) {
                  if (isXMLCharacter((char)var1) != e((char)var1)) {
                     System.out.println(b(",`\u0000?%\u0006{9\u0000\b&g=\u0000I(z+\u001f\b1p0HI") + var1 + b("e{=\nSe") + Integer.toHexString(var1));
                  }

                  ++var1;
                  if (var2) {
                     return;
                  }

                  var10000 = var1;
               }

               return;
            }
         }
      }
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 69;
               break;
            case 1:
               var10003 = 19;
               break;
            case 2:
               var10003 = 88;
               break;
            case 3:
               var10003 = 114;
               break;
            default:
               var10003 = 105;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
