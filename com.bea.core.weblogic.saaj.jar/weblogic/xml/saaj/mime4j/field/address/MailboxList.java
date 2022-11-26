package weblogic.xml.saaj.mime4j.field.address;

import java.util.ArrayList;

public class MailboxList {
   private ArrayList mailboxes;

   public MailboxList(ArrayList mailboxes, boolean dontCopy) {
      if (mailboxes != null) {
         this.mailboxes = dontCopy ? mailboxes : (ArrayList)mailboxes.clone();
      } else {
         this.mailboxes = new ArrayList(0);
      }

   }

   public int size() {
      return this.mailboxes.size();
   }

   public Mailbox get(int index) {
      if (0 <= index && this.size() > index) {
         return (Mailbox)this.mailboxes.get(index);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public void print() {
      for(int i = 0; i < this.size(); ++i) {
         Mailbox mailbox = this.get(i);
         System.out.println(mailbox.toString());
      }

   }
}
