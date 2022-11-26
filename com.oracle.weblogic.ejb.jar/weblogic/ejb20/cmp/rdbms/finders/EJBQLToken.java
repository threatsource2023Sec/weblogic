package weblogic.ejb20.cmp.rdbms.finders;

import java.io.Serializable;

public class EJBQLToken implements Serializable {
   String tokenText;
   Boolean hadException;

   public EJBQLToken() {
      this("");
   }

   public EJBQLToken(String s) {
      this(s, false);
   }

   public EJBQLToken(String s, boolean b) {
      this.tokenText = null;
      this.tokenText = s;
      this.hadException = new Boolean(b);
   }

   public boolean getHadException() {
      return this.hadException;
   }

   public void setHadException(boolean b) {
      this.hadException = new Boolean(b);
   }

   public String getTokenText() {
      return this.tokenText;
   }

   public void setTokenText(String s) {
      this.tokenText = s;
   }

   public void prependTokenText(String s) {
      if (this.tokenText == null) {
         this.tokenText = s;
      } else {
         this.tokenText = s + this.tokenText;
      }
   }

   public void appendTokenText(String s) {
      if (this.tokenText == null) {
         this.tokenText = s;
      } else {
         this.tokenText = this.tokenText + s;
      }
   }
}
