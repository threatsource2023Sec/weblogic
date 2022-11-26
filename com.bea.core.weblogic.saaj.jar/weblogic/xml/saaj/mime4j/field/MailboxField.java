package weblogic.xml.saaj.mime4j.field;

import weblogic.xml.saaj.mime4j.field.address.AddressList;
import weblogic.xml.saaj.mime4j.field.address.Mailbox;
import weblogic.xml.saaj.mime4j.field.address.MailboxList;
import weblogic.xml.saaj.mime4j.field.address.parser.ParseException;

public class MailboxField extends Field {
   private Mailbox mailbox;
   private ParseException parseException;

   public Mailbox getMailbox() {
      return this.mailbox;
   }

   public ParseException getParseException() {
      return this.parseException;
   }

   protected void parseBody(String body) {
      try {
         MailboxList mailboxList = AddressList.parse(body).flatten();
         if (mailboxList.size() > 0) {
            this.mailbox = mailboxList.get(0);
         }
      } catch (ParseException var3) {
         this.parseException = var3;
      }

   }
}
