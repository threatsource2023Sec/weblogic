package weblogic.servlet.logging;

public interface LogField {
   int UNKNOWN_ID = 0;
   int TIME_ID = 1;
   int TIME_TAKEN_ID = 2;
   int DATE_ID = 3;
   int BYTES_ID = 4;
   int IP_ID = 5;
   int DNS_ID = 6;
   int STATUS_ID = 7;
   int METHOD_ID = 8;
   int URI_ID = 9;
   int URI_STEM_ID = 10;
   int URI_QUERY_ID = 11;
   int COMMENT_ID = 12;
   int ECID_ID = 13;
   int RID_ID = 14;
   int AUTHUSER_ID = 15;
   int UNKNOWN_PFX = 0;
   int CLIENT_PFX = 1;
   int SERVER_PFX = 2;
   int CLIENT_SERVER_PFX = 3;
   int SERVER_CLIENT_PFX = 4;
   int APPLICATION_PFX = 5;

   void logField(HttpAccountingInfo var1, FormatStringBuffer var2);
}
