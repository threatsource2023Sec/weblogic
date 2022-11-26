package weblogic.xml.saaj.mime4j.field.address;

import java.util.ArrayList;

public class Group extends Address {
   private String name;
   private MailboxList mailboxList;

   public Group(String name, MailboxList mailboxes) {
      this.name = name;
      this.mailboxList = mailboxes;
   }

   public String getName() {
      return this.name;
   }

   public MailboxList getMailboxes() {
      return this.mailboxList;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.name);
      buf.append(":");

      for(int i = 0; i < this.mailboxList.size(); ++i) {
         buf.append(this.mailboxList.get(i).toString());
         if (i + 1 < this.mailboxList.size()) {
            buf.append(",");
         }
      }

      buf.append(";");
      return buf.toString();
   }

   protected void doAddMailboxesTo(ArrayList results) {
      for(int i = 0; i < this.mailboxList.size(); ++i) {
         results.add(this.mailboxList.get(i));
      }

   }
}
