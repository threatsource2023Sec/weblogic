package com.octetstring.vde.backend.jndi;

import com.octetstring.nls.Messages;
import com.octetstring.vde.Entry;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.Logger;
import java.util.Vector;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchResult;

public class JNDIEntrySet implements EntrySet {
   private BackendJNDI myBackend = null;
   private boolean hasMore = false;
   private String base = null;
   private DirContext dc = null;
   private NamingEnumeration ne = null;

   public JNDIEntrySet() {
   }

   public JNDIEntrySet(BackendJNDI myBackend, DirContext dc, String base, NamingEnumeration ne) {
      this.myBackend = myBackend;
      this.dc = dc;
      this.ne = ne;
      this.base = base;
   }

   private String convertDN(String dn) {
      String tmpdn = dn.toLowerCase();
      int index = tmpdn.indexOf(this.myBackend.getLdapBase().toString().toLowerCase());
      String mydn = null;
      if (index > 0) {
         mydn = dn.substring(0, index) + this.myBackend.getLdapLocalBase();
      } else if (index == 0) {
         mydn = this.myBackend.getLdapLocalBase().toString();
      } else {
         mydn = dn;
      }

      return mydn;
   }

   protected void finalize() throws Throwable {
      super.finalize();
      this.dc = null;
      this.ne = null;
   }

   public Entry getNext() {
      if (!this.hasMore()) {
         return null;
      } else {
         Entry entry = new Entry();

         try {
            SearchResult sr = (SearchResult)this.ne.next();
            String name = sr.getName();

            try {
               if (this.base != null && !this.base.equals("")) {
                  entry.setName(new DirectoryString(name + "," + this.base));
               } else {
                  entry.setName(new DirectoryString(name));
               }
            } catch (InvalidDNException var12) {
               System.out.println(Messages.getString("Attempted_to_uses_name___3") + name);
               var12.printStackTrace();
               return null;
            }

            Attributes at = sr.getAttributes();
            NamingEnumeration ane = at.getAll();

            while(ane.hasMore()) {
               Attribute attr = (Attribute)ane.next();
               DirectoryString attrType = new DirectoryString(attr.getID());
               NamingEnumeration values = attr.getAll();
               Vector vals = new Vector();

               while(values.hasMore()) {
                  Object oneVal = values.nextElement();
                  if (oneVal instanceof String) {
                     if (this.myBackend.dnattrlist.contains(attrType)) {
                        oneVal = this.convertDN((String)oneVal);
                     }

                     vals.addElement(new DirectoryString((String)oneVal));
                  } else {
                     vals.addElement(new DirectoryString((byte[])((byte[])oneVal)));
                  }
               }

               entry.put(attrType, vals);
            }
         } catch (NamingException var13) {
            var13.printStackTrace();
            return null;
         } catch (NullPointerException var14) {
            Logger.getInstance().log(3, this, "LDAP Adapter Returned a NULL Entry.");
            return null;
         }

         try {
            entry.setName(this.myBackend.convertDN(entry, false));
         } catch (InvalidDNException var11) {
            var11.printStackTrace();
         }

         return entry;
      }
   }

   public boolean hasMore() {
      try {
         return this.ne != null ? this.ne.hasMore() : false;
      } catch (NamingException var2) {
         return false;
      } catch (NullPointerException var3) {
         return false;
      }
   }
}
