package weblogic.ldap;

import java.util.Vector;
import netscape.ldap.LDAPControl;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPEntryComparator;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPSearchResults;

public class EmbeddedLDAPSearchResults extends LDAPSearchResults {
   private Vector entries = null;
   private boolean searchComplete = false;
   private boolean persistentSearch = false;
   private Vector exceptions;
   private boolean firstResult = false;

   public EmbeddedLDAPSearchResults() {
      this.entries = new Vector();
      this.searchComplete = true;
   }

   void add(EmbeddedLDAPSearchResult msg) {
      this.entries.addElement(msg.getEntry());
   }

   public LDAPControl[] getResponseControls() {
      return null;
   }

   public synchronized void sort(LDAPEntryComparator compare) {
   }

   public LDAPEntry next() throws LDAPException {
      Object o = this.nextElement();
      if (o instanceof LDAPException) {
         throw (LDAPException)o;
      } else {
         return o instanceof LDAPEntry ? (LDAPEntry)o : null;
      }
   }

   public Object nextElement() {
      Object obj;
      if (this.entries.size() > 0) {
         obj = this.entries.elementAt(0);
         this.entries.removeElementAt(0);
         return obj;
      } else if (this.exceptions != null && this.exceptions.size() > 0) {
         obj = this.exceptions.elementAt(0);
         this.exceptions.removeElementAt(0);
         return obj;
      } else {
         return null;
      }
   }

   public boolean hasMoreElements() {
      return this.entries.size() > 0 || this.exceptions != null && this.exceptions.size() > 0;
   }

   public int getCount() {
      int count = this.entries.size();
      if (this.exceptions != null) {
         count += this.exceptions.size();
      }

      return count;
   }
}
