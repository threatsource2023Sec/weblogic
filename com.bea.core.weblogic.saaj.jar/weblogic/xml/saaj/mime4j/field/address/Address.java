package weblogic.xml.saaj.mime4j.field.address;

import java.util.ArrayList;

public abstract class Address {
   final void addMailboxesTo(ArrayList results) {
      this.doAddMailboxesTo(results);
   }

   protected abstract void doAddMailboxesTo(ArrayList var1);
}
