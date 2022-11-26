package com.octetstring.vde.backend;

import com.octetstring.ldapv3.Filter;
import com.octetstring.nls.Messages;
import com.octetstring.vde.Entry;
import com.octetstring.vde.EntryChange;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.LDAPServer;
import com.octetstring.vde.syntax.BinarySyntax;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.Logger;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

public class BackendRoot extends BaseBackend {
   public BackendRoot(Hashtable config) {
      super(config);
   }

   public boolean bind(DirectoryString dn, BinarySyntax password) {
      return false;
   }

   public boolean doBind() {
      return false;
   }

   public EntrySet get(DirectoryString binddn, DirectoryString base, int scope, Filter filter, boolean attrsOnly, Vector attrs) throws DirectoryException {
      if (scope == 0 && base.equals(new DirectoryString(""))) {
         Vector entries = new Vector();
         entries.addElement(new Integer(1));
         return new GenericEntrySet(this, entries);
      } else {
         return new GenericEntrySet(this, new Vector());
      }
   }

   public Entry getByDN(DirectoryString binddn, DirectoryString dn) {
      return this.getByID(new Integer(1));
   }

   public Entry getByID(Integer id) {
      Entry rootEntry = null;

      try {
         rootEntry = new Entry(new DirectoryString(""));
      } catch (InvalidDNException var10) {
      }

      Vector namingContexts = new Vector();
      Vector objectClass = new Vector();
      Vector subschemaEntry = new Vector();
      Vector supportedLDAPVersion = new Vector();
      Vector supportedSASLMechanisms = new Vector();
      Enumeration ncEnum = BackendHandler.getInstance().getHandlerTable().keys();

      while(ncEnum.hasMoreElements()) {
         DirectoryString nc = (DirectoryString)ncEnum.nextElement();
         if (!nc.equals(new DirectoryString("")) && !nc.equals(new DirectoryString("cn=schema"))) {
            namingContexts.addElement(nc);
         }
      }

      objectClass.addElement(new DirectoryString("top"));
      subschemaEntry.addElement(new DirectoryString("cn=schema"));
      supportedLDAPVersion.addElement(new DirectoryString("2"));
      supportedLDAPVersion.addElement(new DirectoryString("3"));
      supportedSASLMechanisms.addElement(new DirectoryString("EXTERNAL"));
      supportedSASLMechanisms.addElement(new DirectoryString("CRAM-MD5"));
      rootEntry.put(new DirectoryString("namingContexts"), namingContexts);
      rootEntry.put(new DirectoryString("objectClass"), objectClass);
      rootEntry.put(new DirectoryString("subschemaSubEntry"), subschemaEntry);
      rootEntry.put(new DirectoryString("supportedSASLMechanisms"), supportedSASLMechanisms);
      rootEntry.put(new DirectoryString("supportedLDAPVersion"), supportedLDAPVersion);
      Vector supportedExtension = new Vector();
      supportedExtension.addElement(new DirectoryString("1.3.6.1.4.1.1466.20037"));
      rootEntry.put(new DirectoryString("supportedExtension"), supportedExtension);
      return rootEntry;
   }

   public void modify(DirectoryString binddn, DirectoryString name, Vector changeEntries) throws DirectoryException {
      Enumeration ecenum = changeEntries.elements();

      while(true) {
         EntryChange oneEc;
         int modType;
         do {
            if (!ecenum.hasMoreElements()) {
               return;
            }

            oneEc = (EntryChange)ecenum.nextElement();
            modType = oneEc.getModType();
            int modOp = true;
         } while(modType != 2);

         if (!oneEc.getAttr().equals(new DirectoryString("vdecontrol"))) {
            throw new DirectoryException(53, Messages.getString("Cannot_Modify_Root_DSE_23"));
         }

         Vector vals = oneEc.getValues();
         Enumeration ve = vals.elements();

         while(ve.hasMoreElements()) {
            String val = ((Syntax)ve.nextElement()).toString();
            if (val.equalsIgnoreCase("stop")) {
               Logger.getInstance().log(5, this, Messages.getString("Server_Shutdown_via_LDAP_18"));
               Logger.getInstance().flush();
            }

            if (val.equalsIgnoreCase("reload replication")) {
               Logger.getInstance().log(5, this, Messages.getString("Reloading_Replication_20"));
               LDAPServer.getReplication().reload();
            }

            if (val.startsWith("replicainit")) {
               StringTokenizer st = new StringTokenizer(val);
               String nt = st.nextToken();
               String agreement = st.nextToken();
               Logger.getInstance().log(5, this, Messages.getString("Initializing_Replication_Agreement___22") + agreement);
               LDAPServer.getReplication().setupAgreement(agreement);
            }
         }
      }
   }
}
