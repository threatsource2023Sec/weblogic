package weblogic.xml.saaj.mime4j.field;

import weblogic.xml.saaj.mime4j.field.address.AddressList;
import weblogic.xml.saaj.mime4j.field.address.MailboxList;
import weblogic.xml.saaj.mime4j.field.address.parser.ParseException;

public class MailboxListField extends Field {
   private MailboxList mailboxList;
   private ParseException parseException;

   public MailboxList getMailboxList() {
      return this.mailboxList;
   }

   public ParseException getParseException() {
      return this.parseException;
   }

   protected void parseBody(String body) {
      try {
         this.mailboxList = AddressList.parse(body).flatten();
      } catch (ParseException var3) {
         this.parseException = var3;
      }

   }
}
