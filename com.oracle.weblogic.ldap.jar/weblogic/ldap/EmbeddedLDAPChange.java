package weblogic.ldap;

import com.octetstring.vde.Entry;
import com.octetstring.vde.EntryChanges;
import com.octetstring.vde.syntax.DirectoryString;

public class EmbeddedLDAPChange {
   public static final int CHANGE_ADD = 1;
   public static final int CHANGE_MODIFY = 2;
   public static final int CHANGE_DELETE = 3;
   public static final int CHANGE_RENAME = 4;
   private EntryChanges changes;

   public EmbeddedLDAPChange(EntryChanges changes) {
      this.changes = changes;
   }

   public String getEntryName() {
      DirectoryString name = this.changes.getName();
      if (name == null) {
         Entry entry = this.changes.getFullEntry();
         if (entry != null) {
            name = entry.getName();
         }
      }

      return name == null ? null : name.getDirectoryString();
   }

   public int getChangeType() {
      return this.changes.getChangeType();
   }
}
