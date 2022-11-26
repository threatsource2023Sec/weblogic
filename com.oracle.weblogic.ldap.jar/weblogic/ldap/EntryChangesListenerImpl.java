package weblogic.ldap;

import com.octetstring.vde.EntryChanges;
import com.octetstring.vde.EntryChangesListener;
import com.octetstring.vde.syntax.DirectoryString;

public class EntryChangesListenerImpl implements EntryChangesListener {
   EmbeddedLDAPChangeListener listener;
   DirectoryString eclBase;

   public EntryChangesListenerImpl(String changeBase, EmbeddedLDAPChangeListener listener) {
      this.listener = listener;
      this.eclBase = new DirectoryString(changeBase);
   }

   public DirectoryString getECLBase() {
      return this.eclBase;
   }

   public void receiveEntryChanges(EntryChanges entryChanges) {
      this.listener.entryChanged(new EmbeddedLDAPChange(entryChanges));
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.eclBase == null ? 0 : this.eclBase.hashCode());
      result = 31 * result + (this.listener == null ? 0 : this.listener.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         EntryChangesListenerImpl other = (EntryChangesListenerImpl)obj;
         if (this.eclBase == null) {
            if (other.eclBase != null) {
               return false;
            }
         } else if (!this.eclBase.equals(other.eclBase)) {
            return false;
         }

         if (this.listener == null) {
            if (other.listener != null) {
               return false;
            }
         } else if (this.listener != other.listener) {
            return false;
         }

         return true;
      }
   }
}
