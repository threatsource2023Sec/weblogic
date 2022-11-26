package com.octetstring.vde.backend;

import com.asn1c.core.Int8;
import com.octetstring.ldapv3.Filter;
import com.octetstring.vde.Entry;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.operation.LDAPResult;
import com.octetstring.vde.syntax.BinarySyntax;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.DirectoryException;
import java.util.Hashtable;
import java.util.Vector;

public class BaseBackend implements Backend {
   public BaseBackend(Hashtable config) {
   }

   public Int8 add(DirectoryString binddn, Entry entry) {
      return LDAPResult.UNWILLING_TO_PERFORM;
   }

   public boolean bind(DirectoryString dn, BinarySyntax password) {
      return false;
   }

   public Int8 delete(DirectoryString binddn, DirectoryString name) {
      return LDAPResult.UNWILLING_TO_PERFORM;
   }

   public boolean doBind() {
      return false;
   }

   public EntrySet get(DirectoryString binddn, DirectoryString base, int scope, Filter filter, boolean typesOnly, Vector attributes) throws DirectoryException {
      return new GenericEntrySet(this, new Vector());
   }

   public Entry getByDN(DirectoryString binddn, DirectoryString dn) throws DirectoryException {
      throw new DirectoryException(32);
   }

   public Entry getByID(Integer id) {
      return new Entry();
   }

   public void modify(DirectoryString binddn, DirectoryString name, Vector changeEntries) throws DirectoryException {
      throw new DirectoryException(53);
   }

   public Int8 rename(DirectoryString binddn, DirectoryString oldname, DirectoryString newname, DirectoryString newsuffix, boolean removeoldrdn) {
      return LDAPResult.UNWILLING_TO_PERFORM;
   }
}
