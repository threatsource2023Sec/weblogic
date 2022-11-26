package com.octetstring.vde.backend.jndi;

import com.asn1c.core.Int8;
import com.asn1c.core.OctetString;
import com.octetstring.ldapv3.Filter;
import com.octetstring.ldapv3.SubstringFilter_substrings_Seq;
import com.octetstring.nls.Messages;
import com.octetstring.vde.Entry;
import com.octetstring.vde.EntryChange;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.backend.Backend;
import com.octetstring.vde.backend.GenericEntrySet;
import com.octetstring.vde.operation.LDAPResult;
import com.octetstring.vde.schema.AttributeType;
import com.octetstring.vde.schema.SchemaChecker;
import com.octetstring.vde.syntax.BinarySyntax;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.DNUtility;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.Logger;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.naming.CommunicationException;
import javax.naming.ContextNotEmptyException;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NoPermissionException;
import javax.naming.directory.Attribute;
import javax.naming.directory.AttributeInUseException;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.InvalidAttributesException;
import javax.naming.directory.InvalidSearchFilterException;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SchemaViolationException;
import javax.naming.directory.SearchControls;

public class BackendJNDI implements Backend {
   private boolean failoveronly = false;
   private Vector ldaphosts = null;
   private String ldaphost = null;
   private int ldapport = 389;
   private int nextHost = 0;
   private DirectoryString ldapbase = null;
   private DirectoryString ldapuserdn = null;
   private String ldapuserpw = null;
   private boolean doBindVal = true;
   private String upperlocalbase = null;
   private boolean secureConnection = false;
   private String binaryAttributes = null;
   public Vector dnattrlist = null;
   private Hashtable RDNmap = null;
   private Hashtable forwardDNmap = null;
   private Hashtable reverseDNmap = null;
   private String[] rdnmapattrs = null;
   private Vector connpool = new Vector();
   private static final String CONFIG_ATTRMAP = "attrmap";
   private static final String CONFIG_DNATTRLIST = "dnattrlist";
   private static final String CONFIG_RDNMAP = "rdnmap";
   private static final String CONFIG_BINDDN = "binddn";
   private static final String CONFIG_BINDPW = "bindpw";
   private static final String CONFIG_REMOTEBASE = "remotebase";
   private static final String CONFIG_REMOTEHOST = "remotehost";
   private static final String CONFIG_REMOTEPORT = "remoteport";
   private static final String CONFIG_REMOTEHOSTS = "remotehosts";
   private static final String CONFIG_FAILOVERONLY = "failoveronly";
   private static final String CONFIG_PASSCREDENTIALS = "passcredentials";
   private static final String CONFIG_SECURE = "secure";
   private static final String CONFIG_SUFFIX = "suffix";
   private static final OctetString OC_PRESENT = new OctetString("objectclass".getBytes());
   private static final DirectoryString AT_OBJECTCLASS = new DirectoryString("objectclass");
   private DirectoryString ldaplocalbase = null;
   private Hashtable credentialCache = null;
   private Vector allServers = null;
   private Vector activeServers = null;
   private Vector activeRWServers = null;
   private RemoteServer[] serverSequence = null;
   private RemoteServer[] serverSequenceRW = null;
   private static final char[] hexbytes = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

   public BackendJNDI(Hashtable config) {
      this.ldaphost = (String)config.get("remotehost");
      String port = (String)config.get("remoteport");
      if (port != null) {
         this.ldapport = new Integer(port);
      }

      String sLDAPhosts = (String)config.get("remotehosts");
      String sec;
      RemoteServer tmpVal;
      if (sLDAPhosts != null) {
         this.allServers = new Vector();
         this.activeServers = new Vector();
         this.activeRWServers = new Vector();
         this.ldaphosts = new Vector();
         StringTokenizer st = new StringTokenizer(sLDAPhosts, ",");

         while(st.hasMoreTokens()) {
            sec = st.nextToken();
            this.ldaphosts.addElement(sec);
            tmpVal = new RemoteServer(sec);
            this.allServers.addElement(tmpVal);
            this.activeServers.addElement(tmpVal);
            if (!tmpVal.isReadOnly()) {
               this.activeRWServers.addElement(tmpVal);
            }
         }
      }

      String rmode = (String)config.get("failoveronly");
      if (rmode != null && rmode.equals("failover")) {
         this.failoveronly = true;
      }

      sec = (String)config.get("secure");
      if (sec != null && sec.equals("1")) {
         this.secureConnection = true;
      }

      tmpVal = null;
      String tmpVal = (String)config.get("remotebase");
      if (tmpVal != null) {
         try {
            this.ldapbase = DNUtility.getInstance().normalize(new DirectoryString(tmpVal));
         } catch (InvalidDNException var16) {
            Logger.getInstance().log(0, this, Messages.getString("Invalid_DN_for_remote_base_19"));
            this.ldapbase = new DirectoryString("");
         }
      } else {
         this.ldapbase = new DirectoryString("");
      }

      this.ldaplocalbase = (DirectoryString)config.get("suffix");
      if (this.ldaplocalbase == null) {
         this.ldaplocalbase = new DirectoryString("");
      } else {
         try {
            DirectoryString tmpbase = DNUtility.getInstance().normalize(this.ldaplocalbase);
            this.ldaplocalbase = tmpbase;
         } catch (InvalidDNException var15) {
            Logger.getInstance().log(0, this, Messages.getString("Invalid_DN_for_local_base_23"));
         }
      }

      this.upperlocalbase = this.ldaplocalbase.toString().toUpperCase();
      tmpVal = (String)config.get("binddn");
      if (tmpVal != null) {
         this.ldapuserdn = new DirectoryString(tmpVal);
      } else {
         this.ldapuserdn = new DirectoryString("");
      }

      this.ldapuserpw = (String)config.get("bindpw");
      if (this.ldapuserpw == null) {
         this.ldapuserpw = new String();
      }

      String passcreds = (String)config.get("passcredentials");
      if (passcreds != null && passcreds.equals("1")) {
         this.doBindVal = true;
      } else {
         this.doBindVal = false;
      }

      this.credentialCache = new Hashtable();
      this.reverseDNmap = new Hashtable();
      this.forwardDNmap = new Hashtable();
      this.RDNmap = new Hashtable();
      String rdnmap = (String)config.get("rdnmap");
      StringTokenizer dnat;
      Enumeration rma;
      if (rdnmap != null) {
         int size = 0;

         for(dnat = new StringTokenizer(rdnmap, ","); dnat.hasMoreTokens(); ++size) {
            StringTokenizer ott = new StringTokenizer(dnat.nextToken(), ":");
            DirectoryString oc = new DirectoryString(ott.nextToken());
            StringTokenizer mtt = new StringTokenizer(ott.nextToken(), "=");
            DirectoryString[] lr = new DirectoryString[]{new DirectoryString(mtt.nextToken()), new DirectoryString(mtt.nextToken())};
            this.RDNmap.put(oc, lr);
         }

         this.rdnmapattrs = new String[size + 1];
         this.rdnmapattrs[0] = "objectclass";
         int ct = 1;

         for(rma = this.RDNmap.keys(); rma.hasMoreElements(); ++ct) {
            DirectoryString[] oav = (DirectoryString[])((DirectoryString[])this.RDNmap.get((DirectoryString)rma.nextElement()));
            DirectoryString oa = oav[0];
            this.rdnmapattrs[ct] = oa.toString();
         }
      } else {
         this.rdnmapattrs = new String[0];
      }

      this.dnattrlist = new Vector();
      String dnalist = (String)config.get("dnattrlist");
      if (dnalist != null) {
         dnat = new StringTokenizer(dnalist, ",");

         while(dnat.hasMoreTokens()) {
            DirectoryString one = new DirectoryString(dnat.nextToken());
            this.dnattrlist.addElement(one);
         }
      }

      StringBuffer binattrs = new StringBuffer();
      boolean firstba = true;
      rma = SchemaChecker.getInstance().getAttributeTypes().elements();

      while(rma.hasMoreElements()) {
         AttributeType oneat = (AttributeType)rma.nextElement();
         if (oneat.getSyntaxInstance() instanceof BinarySyntax) {
            if (firstba) {
               firstba = false;
            } else {
               binattrs.append(" ");
            }

            binattrs.append(oneat.getName().toString());
         }
      }

      this.binaryAttributes = binattrs.toString();
   }

   private void rebuildServerSequence() {
      new Vector();
      Enumeration ase = this.activeServers.elements();
   }

   public Int8 add(DirectoryString binddn, Entry entry) {
      DirContext dc = null;
      boolean doOp = true;

      while(doOp) {
         doOp = false;
         if (this.doBind() && binddn != null) {
            dc = this.getConnection(binddn, (BinarySyntax)this.credentialCache.get(binddn));
         } else {
            dc = this.getConnection();
         }

         if (dc == null) {
            Logger.getInstance().log(0, this, Messages.getString("No_Remote_Hosts_Available_31"));
            return LDAPResult.UNWILLING_TO_PERFORM;
         }

         Attributes newAttrs = new BasicAttributes();
         Enumeration attrEnum = entry.keys();

         DirectoryString revConv;
         while(attrEnum.hasMoreElements()) {
            revConv = (DirectoryString)attrEnum.nextElement();
            Attribute oneAttr = new BasicAttribute(revConv.toString());
            Vector vals = entry.get(revConv);
            Enumeration valEnum = vals.elements();

            while(valEnum.hasMoreElements()) {
               oneAttr.add(((Syntax)valEnum.nextElement()).getValue());
            }

            newAttrs.put(oneAttr);
         }

         try {
            revConv = this.reverseConvertDN(entry.getName());
            DirectoryString convDN = this.convertBase(revConv);
            dc.createSubcontext(convDN.toString(), newAttrs);
         } catch (SchemaViolationException var11) {
            this.releaseConnection(dc);
            return LDAPResult.OBJECT_CLASS_VIOLATION;
         } catch (InvalidAttributesException var12) {
            this.releaseConnection(dc);
            return LDAPResult.OBJECT_CLASS_VIOLATION;
         } catch (NameAlreadyBoundException var13) {
            this.releaseConnection(dc);
            return LDAPResult.ENTRY_ALREADY_EXISTS;
         } catch (NoPermissionException var14) {
            this.releaseConnection(dc);
            return LDAPResult.INSUFFICIENT_ACCESS_RIGHTS;
         } catch (CommunicationException var15) {
            this.flushConnections();
            doOp = true;
         } catch (NamingException var16) {
            var16.printStackTrace();
            this.releaseConnection(dc);
            return LDAPResult.UNWILLING_TO_PERFORM;
         }
      }

      this.releaseConnection(dc);
      return LDAPResult.SUCCESS;
   }

   public boolean bind(DirectoryString dn, BinarySyntax password) {
      DirContext dc = this.getConnection(dn, password);
      if (dc != null) {
         this.credentialCache.put(dn, password);
         this.releaseConnection(dc);
         return true;
      } else {
         return false;
      }
   }

   private DirectoryString convertBase(DirectoryString base) {
      String myBase = base.toString();
      String tmpbase = myBase.toUpperCase();
      int loc = tmpbase.lastIndexOf(this.upperlocalbase);
      if (loc > 0) {
         myBase = myBase.substring(0, loc);
      } else if (loc == 0) {
         myBase = "";
      } else if (loc == -1) {
         return new DirectoryString("");
      }

      myBase = myBase + this.ldapbase;
      return new DirectoryString(myBase);
   }

   public DirectoryString convertDN(Entry entry, boolean toRemote) {
      DirectoryString newname = (DirectoryString)this.forwardDNmap.get(entry.getName());
      if (newname != null) {
         return newname;
      } else if (this.reverseDNmap.get(entry.getName()) != null) {
         return entry.getName();
      } else {
         Vector objClasses = entry.get(AT_OBJECTCLASS);
         if (objClasses == null) {
            return entry.getName();
         } else {
            Enumeration rme = this.RDNmap.keys();

            DirectoryString oneOC;
            do {
               if (!rme.hasMoreElements()) {
                  return entry.getName();
               }

               oneOC = (DirectoryString)rme.nextElement();
            } while(!objClasses.contains(oneOC));

            Vector rdns = DNUtility.getInstance().explodeDN(entry.getName());
            DirectoryString[] lr = (DirectoryString[])((DirectoryString[])this.RDNmap.get(oneOC));
            DirectoryString rdnattr = null;
            if (toRemote) {
               rdnattr = lr[1];
            } else {
               rdnattr = lr[0];
            }

            Vector vals = entry.get(rdnattr);
            if (vals == null) {
               Logger.getInstance().log(3, this, Messages.getString("Entry_with_DN____34") + entry.getName() + Messages.getString("___missing_RDN_attribute___35") + rdnattr);
               return entry.getName();
            } else {
               String newRDN = rdnattr + "=" + vals.firstElement();
               rdns.setElementAt(newRDN, 0);
               DirectoryString newDN = null;

               try {
                  newDN = DNUtility.getInstance().createDN(rdns);
               } catch (InvalidDNException var14) {
                  return entry.getName();
               }

               this.forwardDNmap.put(entry.getName(), newDN);
               this.reverseDNmap.put(newDN, entry.getName());
               return newDN;
            }
         }
      }
   }

   private String getFilterVal(byte[] binval) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < binval.length; ++i) {
         if ((binval[i] & 128) != 128 && binval[i] != 0 && binval[i] != 10 && binval[i] != 13 && binval[i] != 32 && binval[i] != 58 && binval[i] != 60 && binval[i] != 92 && binval[i] != 40 && binval[i] != 41 && binval[i] != 42) {
            sb.append((char)binval[i]);
         } else {
            sb.append("\\");
            sb.append(hexbytes[(binval[i] & 255) / 16]);
            sb.append(hexbytes[(binval[i] & 255) - (binval[i] & 255) / 16 * 16]);
         }
      }

      return sb.toString();
   }

   private String createFilterString(Filter currentFilter, DirectoryString base, int scope) {
      String filterString = null;
      DirectoryString matchType;
      String matchVal;
      Iterator substrEnum;
      String fs;
      switch (currentFilter.getSelector()) {
         case 0:
            filterString = new String("(&");
            substrEnum = currentFilter.getAnd().iterator();

            while(substrEnum.hasNext()) {
               fs = this.createFilterString((Filter)substrEnum.next(), base, scope);
               if (fs != null) {
                  if (fs.charAt(0) == '(') {
                     filterString = filterString.concat(fs);
                  } else {
                     filterString = filterString.concat("(" + fs + ")");
                  }
               }
            }

            filterString = filterString.concat(")");
            break;
         case 1:
            filterString = new String("(|");
            substrEnum = currentFilter.getOr().iterator();

            while(substrEnum.hasNext()) {
               fs = this.createFilterString((Filter)substrEnum.next(), base, scope);
               if (fs != null) {
                  if (fs.charAt(0) == '(') {
                     filterString = filterString.concat(fs);
                  } else {
                     filterString = filterString.concat("(" + fs + ")");
                  }
               }
            }

            filterString = filterString.concat(")");
            break;
         case 2:
            filterString = new String("(!");
            String nfs = this.createFilterString(currentFilter.getNot(), base, scope);
            if (nfs != null) {
               if (nfs.charAt(0) == '(') {
                  filterString = filterString.concat(nfs);
               } else {
                  filterString = filterString.concat("(" + nfs + ")");
               }
            }

            filterString = filterString.concat(")");
            break;
         case 3:
            matchType = new DirectoryString(currentFilter.getEqualityMatch().getAttributeDesc().toByteArray());
            matchVal = this.getFilterVal(currentFilter.getEqualityMatch().getAssertionValue().toByteArray());
            filterString = new String(matchType + "=" + matchVal);
            break;
         case 4:
            matchType = new DirectoryString(currentFilter.getSubstrings().getType().toByteArray());
            String subfilter = new String();
            substrEnum = currentFilter.getSubstrings().getSubstrings().iterator();

            while(substrEnum.hasNext()) {
               SubstringFilter_substrings_Seq oneSubFilter = (SubstringFilter_substrings_Seq)substrEnum.next();
               if (oneSubFilter.getSelector() == 0) {
                  subfilter = subfilter.concat(this.getFilterVal(oneSubFilter.getInitial().toByteArray()) + "*");
               } else if (oneSubFilter.getSelector() == 1) {
                  if (subfilter.length() == 0) {
                     subfilter = subfilter.concat("*");
                  }

                  subfilter = subfilter.concat(this.getFilterVal(oneSubFilter.getAny().toByteArray()) + "*");
               } else if (oneSubFilter.getSelector() == 2) {
                  if (subfilter.length() == 0) {
                     subfilter = subfilter.concat("*");
                  }

                  subfilter = subfilter.concat(this.getFilterVal(oneSubFilter.getFinal_().toByteArray()));
               }
            }

            filterString = new String(matchType + "=" + subfilter);
            break;
         case 5:
            matchType = new DirectoryString(currentFilter.getGreaterOrEqual().getAttributeDesc().toByteArray());
            matchVal = this.getFilterVal(currentFilter.getGreaterOrEqual().getAssertionValue().toByteArray());
            filterString = new String(matchType + ">=" + matchVal);
            break;
         case 6:
            matchType = new DirectoryString(currentFilter.getLessOrEqual().getAttributeDesc().toByteArray());
            matchVal = this.getFilterVal(currentFilter.getLessOrEqual().getAssertionValue().toByteArray());
            filterString = new String(matchType + "<=" + matchVal);
            break;
         case 7:
            matchType = new DirectoryString(currentFilter.getPresent().toByteArray());
            filterString = new String(matchType + "=*");
            break;
         case 8:
            matchType = new DirectoryString(currentFilter.getApproxMatch().getAttributeDesc().toByteArray());
            matchVal = this.getFilterVal(currentFilter.getApproxMatch().getAssertionValue().toByteArray());
            filterString = matchType + "~=" + matchVal;
            break;
         case 9:
            StringBuffer fsb = new StringBuffer();
            if (currentFilter.getExtensibleMatch().getType() != null) {
               fsb.append(new String(currentFilter.getExtensibleMatch().getType().toByteArray()));
               fsb.append(":");
            }

            if (currentFilter.getExtensibleMatch().getDnAttributes().booleanValue()) {
               fsb.append("dn:");
            }

            fsb.append(new String(currentFilter.getExtensibleMatch().getMatchingRule().toByteArray()));
            fsb.append(":=");
            fsb.append(this.getFilterVal(currentFilter.getExtensibleMatch().getMatchValue().toByteArray()));
            filterString = fsb.toString();
      }

      return filterString;
   }

   public Int8 delete(DirectoryString binddn, DirectoryString name) {
      DirContext dc = null;
      boolean doOp = true;

      while(doOp) {
         doOp = false;
         if (this.doBind() && binddn != null) {
            dc = this.getConnection(binddn, (BinarySyntax)this.credentialCache.get(binddn));
         } else {
            dc = this.getConnection();
         }

         if (dc == null) {
            Logger.getInstance().log(0, this, Messages.getString("No_Remote_Hosts_Available_62"));
            return LDAPResult.UNWILLING_TO_PERFORM;
         }

         try {
            dc.destroySubcontext(this.convertBase(this.reverseConvertDN(name)).toString());
         } catch (ContextNotEmptyException var6) {
            this.releaseConnection(dc);
            return LDAPResult.NOT_ALLOWED_ON_NON_LEAF;
         } catch (NameNotFoundException var7) {
            this.releaseConnection(dc);
            return LDAPResult.NO_SUCH_OBJECT;
         } catch (NoPermissionException var8) {
            this.releaseConnection(dc);
            return LDAPResult.INSUFFICIENT_ACCESS_RIGHTS;
         } catch (CommunicationException var9) {
            this.flushConnections();
            doOp = true;
         } catch (NamingException var10) {
            var10.printStackTrace();
            this.releaseConnection(dc);
            return LDAPResult.UNWILLING_TO_PERFORM;
         }
      }

      this.releaseConnection(dc);
      return LDAPResult.SUCCESS;
   }

   public boolean doBind() {
      return this.doBindVal;
   }

   public EntrySet get(DirectoryString binddn, DirectoryString base, int scope, Filter filter, boolean typesOnly, Vector attributes) throws DirectoryException {
      String filterString = this.createFilterString(filter, base, scope);
      String myBase = this.convertBase(this.reverseConvertDN(base)).toString();
      if (myBase.equals("")) {
         myBase = this.ldapbase.toString();
         if (scope == 1) {
            scope = 0;
         }
      }

      if (Logger.getInstance().isLogable(9)) {
         Logger.getInstance().log(9, this, Messages.getString("JNDI_Adapter_Search_using__n__BindDN____64") + binddn + "\n  Base:    " + myBase + "\n  Scope:   " + scope + "\n  Attribs: " + attributes + "\n  Filter:  " + filterString);
      }

      DirContext dc = null;
      NamingEnumeration ne = null;
      boolean doOp = true;

      while(doOp) {
         doOp = false;
         if (this.doBind() && binddn != null) {
            dc = this.getConnection(binddn, (BinarySyntax)this.credentialCache.get(binddn));
         } else {
            dc = this.getConnection();
         }

         if (dc == null) {
            Logger.getInstance().log(0, this, Messages.getString("No_Remote_Hosts_Available_69"));
            throw new DirectoryException(52, Messages.getString("No_Remote_Hosts_Available_70"));
         }

         SearchControls sc = new SearchControls();
         if (scope == 0) {
            sc.setSearchScope(0);
         } else if (scope == 2) {
            sc.setSearchScope(2);
         } else {
            sc.setSearchScope(1);
         }

         String[] at = null;
         if (attributes.size() > 0) {
            at = new String[attributes.size() + this.rdnmapattrs.length + 1];
         } else {
            at = new String[attributes.size() + this.rdnmapattrs.length];
         }

         System.arraycopy(this.rdnmapattrs, 0, at, attributes.size(), this.rdnmapattrs.length);
         Enumeration en = attributes.elements();

         int count;
         for(count = 0; en.hasMoreElements(); ++count) {
            String attr = ((DirectoryString)en.nextElement()).toString();
            at[count] = attr;
         }

         if (count > 0) {
            at[count] = "objectclass";
            sc.setReturningAttributes(at);
         }

         try {
            ne = dc.search(myBase, filterString, sc);
         } catch (InvalidSearchFilterException var17) {
            this.releaseConnection(dc);
            throw new DirectoryException(1, var17.getMessage());
         } catch (NameNotFoundException var18) {
            this.releaseConnection(dc);
            throw new DirectoryException(32, var18.getMessage());
         } catch (NoPermissionException var19) {
            this.releaseConnection(dc);
            throw new DirectoryException(50, var19.getMessage());
         } catch (CommunicationException var20) {
            this.flushConnections();
            doOp = true;
         } catch (NamingException var21) {
            var21.printStackTrace();
            this.releaseConnection(dc);
            return new GenericEntrySet();
         }

         if (base.length() < this.ldaplocalbase.length()) {
            base = this.ldaplocalbase;
         }
      }

      this.releaseConnection(dc);
      return new JNDIEntrySet(this, dc, base.toString(), ne);
   }

   public Entry getByDN(DirectoryString binddn, DirectoryString dn) throws DirectoryException {
      Filter sfilter = new Filter();
      sfilter.setPresent(OC_PRESENT);
      EntrySet myents = this.get(binddn, dn, 0, sfilter, false, new Vector());
      return !myents.hasMore() ? null : myents.getNext();
   }

   public Entry getByID(Integer id) {
      return null;
   }

   void releaseConnection(DirContext dc) {
      if (dc != null) {
         Hashtable env = null;

         try {
            env = dc.getEnvironment();
         } catch (NamingException var7) {
            dc = null;
            return;
         }

         if (env.containsKey("octetstring.discard")) {
            try {
               dc.close();
            } catch (NamingException var5) {
            }

            dc = null;
            return;
         }
      }

      synchronized(this.connpool) {
         this.connpool.addElement(dc);
      }
   }

   void flushConnections() {
      synchronized(this.connpool) {
         this.connpool = new Vector();
      }
   }

   DirContext getConnection() {
      int fhcount = 0;
      int hct = false;
      synchronized(this.connpool) {
         if (!this.connpool.isEmpty()) {
            DirContext dc = (DirContext)this.connpool.elementAt(0);
            this.connpool.removeElementAt(0);
            return dc;
         }
      }

      int hct;
      synchronized(this) {
         hct = this.nextHost++;
         if (this.ldaphosts != null && this.nextHost > this.ldaphosts.size()) {
            this.nextHost = 0;
         }
      }

      while(this.ldaphosts == null && fhcount < 1 || this.ldaphosts != null && fhcount < this.ldaphosts.size()) {
         Hashtable env = new Hashtable();
         env.put("java.naming.ldap.attributes.binary", this.binaryAttributes);
         env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
         env.put("java.naming.referral", "follow");
         if (this.ldaphosts == null) {
            env.put("java.naming.provider.url", "ldap://" + this.ldaphost + ":" + this.ldapport);
         } else if (this.failoveronly) {
            env.put("java.naming.provider.url", "ldap://" + this.ldaphosts.elementAt(fhcount));
            ++fhcount;
         } else {
            if (hct >= this.ldaphosts.size()) {
               hct = 0;
            }

            env.put("java.naming.provider.url", "ldap://" + this.ldaphosts.elementAt(hct));
            ++hct;
            ++fhcount;
         }

         if (this.secureConnection) {
            env.put("java.naming.security.protocol", "ssl");
         }

         env.put("java.naming.security.principal", this.ldapuserdn.toString());
         env.put("java.naming.security.credentials", this.ldapuserpw.toString());
         DirContext dc = null;

         try {
            dc = new InitialDirContext(env);
            return dc;
         } catch (NamingException var8) {
            Logger.getInstance().log(0, this, "Unable to connect to " + env.get("java.naming.provider.url") + " as " + this.ldapuserdn);
            Logger.getInstance().log(0, this, Messages.getString("Connection_Error___80") + var8.getRootCause().getMessage());
         }
      }

      return null;
   }

   DirContext getConnection(DirectoryString dn, BinarySyntax password) {
      String binddn = null;
      String bindpw = null;
      Hashtable env = new Hashtable();
      env.put("java.naming.ldap.attributes.binary", this.binaryAttributes);
      if (dn != null && dn.endsWith(this.ldaplocalbase)) {
         binddn = this.convertBase(this.reverseConvertDN(dn)).toString();
         if (password != null) {
            bindpw = new String(password.getValue());
         } else {
            bindpw = new String();
         }

         env.put("octetstring.discard", "true");
      } else {
         synchronized(this.connpool) {
            if (!this.connpool.isEmpty()) {
               DirContext dc = (DirContext)this.connpool.elementAt(0);
               this.connpool.removeElementAt(0);
               return dc;
            }
         }

         binddn = this.ldapuserdn.toString();
         bindpw = this.ldapuserpw.toString();
      }

      int fhcount = 0;
      int hct = false;
      int hct;
      synchronized(this) {
         hct = this.nextHost++;
         if (this.nextHost > this.ldaphosts.size()) {
            this.nextHost = 0;
         }
      }

      while(this.ldaphosts == null && fhcount < 1 || this.ldaphosts != null && fhcount < this.ldaphosts.size()) {
         env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
         if (this.ldaphosts == null) {
            env.put("java.naming.provider.url", "ldap://" + this.ldaphost + ":" + this.ldapport);
         } else if (this.failoveronly) {
            env.put("java.naming.provider.url", "ldap://" + this.ldaphosts.elementAt(fhcount));
            ++fhcount;
         } else {
            if (hct >= this.ldaphosts.size()) {
               hct = 0;
            }

            env.put("java.naming.provider.url", "ldap://" + this.ldaphosts.elementAt(hct));
            ++hct;
            ++fhcount;
         }

         env.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
         env.put("java.naming.referral", "follow");
         if (Logger.getInstance().isLogable(7)) {
            Logger.getInstance().log(7, this, Messages.getString("Getting_connection__binding_as__89") + binddn);
         }

         if (this.secureConnection) {
            env.put("java.naming.security.protocol", "ssl");
         }

         env.put("java.naming.security.principal", binddn);
         env.put("java.naming.security.credentials", bindpw);
         DirContext dc = null;

         try {
            dc = new InitialDirContext(env);
            return dc;
         } catch (NamingException var11) {
            Logger.getInstance().log(0, this, Messages.getString("Unable_to_connect_to__91") + env.get("java.naming.provider.url") + Messages.getString("_as__92") + binddn);
            Logger.getInstance().log(0, this, Messages.getString("Connection_Error___93") + var11.getRootCause().getMessage());
         }
      }

      return null;
   }

   public DirectoryString getLdapBase() {
      return this.ldapbase;
   }

   public DirectoryString getLdapLocalBase() {
      return this.ldaplocalbase;
   }

   public void modify(DirectoryString binddn, DirectoryString name, Vector changeEntries) throws DirectoryException {
      DirContext dc = null;
      boolean doOp = true;

      while(doOp) {
         doOp = false;
         if (this.doBind() && binddn != null) {
            dc = this.getConnection(binddn, (BinarySyntax)this.credentialCache.get(binddn));
         } else {
            dc = this.getConnection();
         }

         if (dc == null) {
            Logger.getInstance().log(0, this, Messages.getString("No_Remote_Hosts_Available_94"));
            throw new DirectoryException(53, Messages.getString("No_Remote_Hosts_Available_95"));
         }

         Enumeration ecenum = changeEntries.elements();
         ModificationItem[] mods = new ModificationItem[changeEntries.size()];

         for(int ce = 0; ecenum.hasMoreElements(); ++ce) {
            EntryChange oneEc = (EntryChange)ecenum.nextElement();
            int modType = oneEc.getModType();
            int modOp = -1;
            if (modType == 0) {
               modOp = 1;
            } else if (modType == 2) {
               modOp = 2;
            } else if (modType == 1) {
               modOp = 3;
            }

            Attribute oneAttr = new BasicAttribute(oneEc.getAttr().toString());
            Vector vals = oneEc.getValues();
            Enumeration valEnum = vals.elements();

            while(valEnum.hasMoreElements()) {
               Syntax synval = (Syntax)valEnum.nextElement();
               oneAttr.add(synval.getValue());
            }

            mods[ce] = new ModificationItem(modOp, oneAttr);
         }

         try {
            dc.modifyAttributes(this.convertBase(this.reverseConvertDN(name)).toString(), mods);
         } catch (AttributeInUseException var16) {
            this.releaseConnection(dc);
            throw new DirectoryException(20, var16.getMessage());
         } catch (InvalidAttributesException var17) {
            this.releaseConnection(dc);
            throw new DirectoryException(65, var17.getMessage());
         } catch (SchemaViolationException var18) {
            this.releaseConnection(dc);
            throw new DirectoryException(65, var18.getMessage());
         } catch (NameNotFoundException var19) {
            this.releaseConnection(dc);
            throw new DirectoryException(32, var19.getMessage());
         } catch (NoPermissionException var20) {
            this.releaseConnection(dc);
            throw new DirectoryException(50, var20.getMessage());
         } catch (CommunicationException var21) {
            this.flushConnections();
            doOp = true;
         } catch (NamingException var22) {
            this.releaseConnection(dc);
            var22.printStackTrace();
            throw new DirectoryException(53, Messages.getString("Unable_to_Modify_96"));
         }
      }

      this.releaseConnection(dc);
   }

   public Int8 rename(DirectoryString binddn, DirectoryString oldname, DirectoryString newname, DirectoryString newsuffix, boolean removeoldrdn) {
      DirContext dc = null;
      boolean doOp = true;

      while(doOp) {
         doOp = false;
         if (this.doBind() && binddn != null) {
            dc = this.getConnection(binddn, (BinarySyntax)this.credentialCache.get(binddn));
         } else {
            dc = this.getConnection();
         }

         if (dc == null) {
            Logger.getInstance().log(0, this, Messages.getString("No_Remote_Hosts_Available_97"));
            return LDAPResult.UNWILLING_TO_PERFORM;
         }

         String newrealname = null;
         DirectoryString oldrevConv;
         DirectoryString originalbase;
         DirectoryString newfullname;
         if (newsuffix != null) {
            oldrevConv = new DirectoryString(newname + "," + newsuffix);
            originalbase = this.reverseConvertDN(oldrevConv);
            newfullname = this.convertBase(originalbase);
            newrealname = newfullname.toString();
         } else {
            Vector basecomponents = DNUtility.getInstance().explodeDN(oldname);
            if (!basecomponents.isEmpty()) {
               basecomponents.removeElementAt(0);
            }

            originalbase = null;

            try {
               originalbase = DNUtility.getInstance().createDN(basecomponents);
               newfullname = new DirectoryString(newname + "," + originalbase);
               DirectoryString revConv = this.reverseConvertDN(newfullname);
               DirectoryString convDN = this.convertBase(revConv);
               newrealname = convDN.toString();
            } catch (InvalidDNException var19) {
               return LDAPResult.INVALID_DN_SYNTAX;
            }
         }

         oldrevConv = this.reverseConvertDN(oldname);
         originalbase = this.convertBase(oldrevConv);

         try {
            dc.rename(originalbase.toString(), newrealname);
         } catch (NameAlreadyBoundException var14) {
            this.releaseConnection(dc);
            return LDAPResult.ENTRY_ALREADY_EXISTS;
         } catch (SchemaViolationException var15) {
            this.releaseConnection(dc);
            return LDAPResult.OBJECT_CLASS_VIOLATION;
         } catch (NoPermissionException var16) {
            this.releaseConnection(dc);
            return LDAPResult.INSUFFICIENT_ACCESS_RIGHTS;
         } catch (CommunicationException var17) {
            this.flushConnections();
            doOp = true;
         } catch (NamingException var18) {
            this.releaseConnection(dc);
            var18.printStackTrace();
            return LDAPResult.OPERATIONS_ERROR;
         }
      }

      this.releaseConnection(dc);
      return LDAPResult.SUCCESS;
   }

   private DirectoryString reverseConvertDN(DirectoryString dn) {
      DirectoryString newDN = (DirectoryString)this.reverseDNmap.get(dn);
      return newDN == null ? dn : newDN;
   }
}
