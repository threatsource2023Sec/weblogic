package org.python.icu.text;

final class RBNFChinesePostProcessor implements RBNFPostProcessor {
   private boolean longForm;
   private int format;
   private static final String[] rulesetNames = new String[]{"%traditional", "%simplified", "%accounting", "%time"};

   public void init(RuleBasedNumberFormat formatter, String rules) {
   }

   public void process(StringBuilder buf, NFRuleSet ruleSet) {
      String name = ruleSet.getName();

      int i;
      for(i = 0; i < rulesetNames.length; ++i) {
         if (rulesetNames[i].equals(name)) {
            this.format = i;
            this.longForm = i == 1 || i == 3;
            break;
         }
      }

      if (this.longForm) {
         for(i = buf.indexOf("*"); i != -1; i = buf.indexOf("*", i)) {
            buf.delete(i, i + 1);
         }

      } else {
         String DIAN = "點";
         String[][] markers = new String[][]{{"萬", "億", "兆", "〇"}, {"万", "亿", "兆", "〇"}, {"萬", "億", "兆", "零"}};
         String[] m = markers[this.format];

         int s;
         int n;
         for(s = 0; s < m.length - 1; ++s) {
            n = buf.indexOf(m[s]);
            if (n != -1) {
               buf.insert(n + m[s].length(), '|');
            }
         }

         int x = buf.indexOf("點");
         if (x == -1) {
            x = buf.length();
         }

         s = 0;
         n = -1;
         String ling = markers[this.format][3];

         int i;
         while(x >= 0) {
            i = buf.lastIndexOf("|", x);
            int nn = buf.lastIndexOf(ling, x);
            int ns = 0;
            if (nn > i) {
               ns = nn > 0 && buf.charAt(nn - 1) != '*' ? 2 : 1;
            }

            x = i - 1;
            switch (s * 3 + ns) {
               case 0:
                  s = ns;
                  n = -1;
                  break;
               case 1:
                  s = ns;
                  n = nn;
                  break;
               case 2:
                  s = ns;
                  n = -1;
                  break;
               case 3:
                  s = ns;
                  n = -1;
                  break;
               case 4:
                  buf.delete(nn - 1, nn + ling.length());
                  s = 0;
                  n = -1;
                  break;
               case 5:
                  buf.delete(n - 1, n + ling.length());
                  s = ns;
                  n = -1;
                  break;
               case 6:
                  s = ns;
                  n = -1;
                  break;
               case 7:
                  buf.delete(nn - 1, nn + ling.length());
                  s = 0;
                  n = -1;
                  break;
               case 8:
                  s = ns;
                  n = -1;
                  break;
               default:
                  throw new IllegalStateException();
            }
         }

         i = buf.length();

         while(true) {
            char c;
            do {
               --i;
               if (i < 0) {
                  return;
               }

               c = buf.charAt(i);
            } while(c != '*' && c != '|');

            buf.delete(i, i + 1);
         }
      }
   }
}
