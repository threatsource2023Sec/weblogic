package weblogic.security.net;

import java.text.ParseException;

public interface ConnectionFilterRulesListener extends ConnectionFilter {
   void checkRules(String[] var1) throws ParseException;

   void setRules(String[] var1) throws ParseException;
}
