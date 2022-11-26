package weblogic.wtc.tbridge;

import weblogic.wtc.jatmi.FldTbl;

public final class tBtest1flds32 implements FldTbl {
   public static final int ACCOUNT_ID = 33559542;
   public static final int ACCT_TYPE = 67113976;
   public static final int ADDRESS = 167777269;
   public static final int AMOUNT = 100668413;
   public static final int BALANCE = 100668401;
   public static final int BRANCH_ID = 33559536;
   public static final int FIRST_NAME = 167777274;
   public static final int LAST_ACCT = 33559538;
   public static final int LAST_NAME = 167777273;
   public static final int LAST_TELLER = 33559539;
   public static final int MID_INIT = 67113979;
   public static final int PHONE = 167777268;
   public static final int SSN = 167777271;
   public static final int TELLER_ID = 33559548;
   public static final int SBALANCE = 167777361;
   public static final int SAMOUNT = 167777362;
   public static final int XA_TYPE = 5203;
   public static final int CURS = 167777364;
   public static final int SVCHG = 167777365;
   public static final int VIEWNAME = 167777366;
   public static final int OPEN_CR = 67114071;
   public static final int TYPE_CR = 67114072;

   public String Fldid_to_name(int fldid) {
      switch (fldid) {
         case 5203:
            return new String("XA_TYPE");
         case 33559536:
            return new String("BRANCH_ID");
         case 33559538:
            return new String("LAST_ACCT");
         case 33559539:
            return new String("LAST_TELLER");
         case 33559542:
            return new String("ACCOUNT_ID");
         case 33559548:
            return new String("TELLER_ID");
         case 67113976:
            return new String("ACCT_TYPE");
         case 67113979:
            return new String("MID_INIT");
         case 67114071:
            return new String("OPEN_CR");
         case 67114072:
            return new String("TYPE_CR");
         case 100668401:
            return new String("BALANCE");
         case 100668413:
            return new String("AMOUNT");
         case 167777268:
            return new String("PHONE");
         case 167777269:
            return new String("ADDRESS");
         case 167777271:
            return new String("SSN");
         case 167777273:
            return new String("LAST_NAME");
         case 167777274:
            return new String("FIRST_NAME");
         case 167777361:
            return new String("SBALANCE");
         case 167777362:
            return new String("SAMOUNT");
         case 167777364:
            return new String("CURS");
         case 167777365:
            return new String("SVCHG");
         case 167777366:
            return new String("VIEWNAME");
         default:
            return null;
      }
   }

   public int name_to_Fldid(String name) {
      if (name.equals("ACCOUNT_ID")) {
         return 33559542;
      } else if (name.equals("ACCT_TYPE")) {
         return 67113976;
      } else if (name.equals("ADDRESS")) {
         return 167777269;
      } else if (name.equals("AMOUNT")) {
         return 100668413;
      } else if (name.equals("BALANCE")) {
         return 100668401;
      } else if (name.equals("BRANCH_ID")) {
         return 33559536;
      } else if (name.equals("FIRST_NAME")) {
         return 167777274;
      } else if (name.equals("LAST_ACCT")) {
         return 33559538;
      } else if (name.equals("LAST_NAME")) {
         return 167777273;
      } else if (name.equals("LAST_TELLER")) {
         return 33559539;
      } else if (name.equals("MID_INIT")) {
         return 67113979;
      } else if (name.equals("PHONE")) {
         return 167777268;
      } else if (name.equals("SSN")) {
         return 167777271;
      } else if (name.equals("TELLER_ID")) {
         return 33559548;
      } else if (name.equals("SBALANCE")) {
         return 167777361;
      } else if (name.equals("SAMOUNT")) {
         return 167777362;
      } else if (name.equals("XA_TYPE")) {
         return 5203;
      } else if (name.equals("CURS")) {
         return 167777364;
      } else if (name.equals("SVCHG")) {
         return 167777365;
      } else if (name.equals("VIEWNAME")) {
         return 167777366;
      } else if (name.equals("OPEN_CR")) {
         return 67114071;
      } else {
         return name.equals("TYPE_CR") ? 67114072 : -1;
      }
   }

   public String[] getFldNames() {
      String[] retval = new String[]{new String("ACCOUNT_ID"), new String("ACCT_TYPE"), new String("ADDRESS"), new String("AMOUNT"), new String("BALANCE"), new String("BRANCH_ID"), new String("FIRST_NAME"), new String("LAST_ACCT"), new String("LAST_NAME"), new String("LAST_TELLER"), new String("MID_INIT"), new String("PHONE"), new String("SSN"), new String("TELLER_ID"), new String("SBALANCE"), new String("SAMOUNT"), new String("XA_TYPE"), new String("CURS"), new String("SVCHG"), new String("VIEWNAME"), new String("OPEN_CR"), new String("TYPE_CR")};
      return retval;
   }
}
