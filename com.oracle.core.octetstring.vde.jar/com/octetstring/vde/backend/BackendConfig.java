package com.octetstring.vde.backend;

import com.octetstring.ldapv3.Filter;
import com.octetstring.vde.Entry;
import com.octetstring.vde.EntryChange;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.syntax.BinarySyntax;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class BackendConfig extends BaseBackend {
   private DirectoryString suffix = null;

   public BackendConfig(Hashtable config) {
      super(config);
      this.suffix = (DirectoryString)config.get("suffix");
   }

   public boolean bind(DirectoryString dn, BinarySyntax password) {
      return false;
   }

   public boolean doBind() {
      return false;
   }

   public EntrySet get(DirectoryString binddn, DirectoryString base, int scope, Filter filter, boolean attrsOnly, Vector attrs) throws DirectoryException {
      if (scope == 0 && base.equals(this.suffix)) {
         Vector entries = new Vector();
         entries.addElement(new Integer(1));
         return new GenericEntrySet(this, entries);
      } else {
         return new GenericEntrySet(this, new Vector());
      }
   }

   public Entry getByDN(DirectoryString binddn, DirectoryString dn) {
      return dn.equals(this.suffix) ? this.getByID(new Integer(1)) : null;
   }

   public Entry getByID(Integer id) {
      Entry configEntry = null;
      if (id != 1) {
         return null;
      } else {
         try {
            configEntry = new Entry(this.suffix);
         } catch (InvalidDNException var10) {
         }

         ServerConfig sc = ServerConfig.getInstance();
         String[] opts = sc.getOptionNames();

         for(int i = 0; i < opts.length; ++i) {
            Vector vals = new Vector();
            DirectoryString type = new DirectoryString(opts[i]);
            String valstr = (String)sc.get(opts[i]);
            if (valstr != null) {
               DirectoryString val = new DirectoryString(valstr);
               vals.addElement(val);
               configEntry.put(type, vals);
            }
         }

         return configEntry;
      }
   }

   public void modify(DirectoryString binddn, DirectoryString name, Vector changeEntries) throws DirectoryException {
      Enumeration ecenum = changeEntries.elements();
      int ce = false;

      while(ecenum.hasMoreElements()) {
         EntryChange oneEc = (EntryChange)ecenum.nextElement();
         int modType = oneEc.getModType();
         int modOp = true;
         if (modType != 0) {
            if (modType == 2) {
               DirectoryString attr = oneEc.getAttr();
               Vector vals = oneEc.getValues();
               if (!vals.isEmpty()) {
                  Syntax val = (Syntax)vals.elementAt(0);
                  String strval = val.toString();
                  ServerConfig.getInstance().put(attr.toString(), strval);
                  if (attr.equals(new DirectoryString("vde.debug"))) {
                     Logger.getInstance().setLogLevel(new Integer(strval));
                  }
               }
            } else if (modType == 1) {
            }
         }
      }

   }
}
