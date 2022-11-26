package com.bea.common.ldap;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import kodo.kernel.jdoql.JDOQLParser;
import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPModification;
import netscape.ldap.LDAPModificationSet;
import netscape.ldap.LDAPSearchResults;
import netscape.ldap.util.DN;
import netscape.ldap.util.RDN;
import org.apache.openjpa.abstractstore.AbstractStoreManager;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.PCState;
import org.apache.openjpa.kernel.StoreQuery;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.rop.ResultObjectProvider;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.ObjectNotFoundException;
import org.apache.openjpa.util.StoreException;

public class LDAPStoreManager extends AbstractStoreManager {
   private static final String HEADER = "LDAPStoreManager: ";
   private static final Set knownBinaryAttrs = new HashSet();
   private static final Collection EMPTY_EXCEPTIONS;
   private ParserFactory pFac;
   private LDAPConfiguration conf;
   private Log log;

   public boolean initialize(OpenJPAStateManager sm, PCState state, FetchConfiguration fetch, Object context) {
      ObjectData data;
      if (context != null) {
         data = (ObjectData)context;
      } else {
         data = this.readEntry(sm.getMetaData(), sm.getObjectId(), fetch, (BitSet)null);
      }

      if (data == null) {
         return false;
      } else {
         sm.initialize(data.getMetaData().getDescribedType(), state);
         DataLoader dl = this.pFac.getDataLoader(data.getMetaData());
         dl.loadData(data, fetch, sm);
         return true;
      }
   }

   private ObjectData readEntry(ClassMetaData meta, Object oid, FetchConfiguration fetch, BitSet fields) {
      ObjectIdConverter oic = this.pFac.getObjectIdConverter(meta);
      if (oid != null && oic != null) {
         DistinguishedNameId dni = oic.convertObjectId(oid);
         if (dni != null) {
            try {
               LDAPConnection con = this.conf.getConnection();

               ObjectData var10;
               try {
                  DataLoader dl = this.pFac.getDataLoader(meta);
                  LDAPEntry entry = con.read(dni.getDN(), dl.createFetchList(fields, fetch));
                  if (entry == null) {
                     return null;
                  }

                  var10 = new ObjectData(oid, entry, meta);
               } finally {
                  this.conf.releaseConnection(con);
               }

               return var10;
            } catch (LDAPException var15) {
               if (32 != var15.getLDAPResultCode()) {
                  throw (new StoreException(var15)).setFatal(true);
               }
            }
         }
      }

      return null;
   }

   private LDAPEntry createEntry(OpenJPAStateManager sm) {
      LDAPEntry entry = null;
      ClassMetaData meta = sm.getMetaData();
      ObjectIdConverter oic = this.pFac.getObjectIdConverter(meta);
      StateParser sp = this.pFac.getStateParser(meta);
      Object oid = sm.getObjectId();
      if (oic != null && oid != null && sp != null) {
         DistinguishedNameId dni = oic.convertObjectId(oid);
         if (dni != null) {
            entry = new LDAPEntry(dni.getDN(), sp.create(sm));
         }
      }

      return entry;
   }

   private LDAPModificationSet createModification(OpenJPAStateManager sm) {
      LDAPModificationSet mods = null;
      ClassMetaData meta = sm.getMetaData();
      StateParser sp = this.pFac.getStateParser(meta);
      if (sp != null) {
         mods = sp.deltas(sm);
      }

      return mods;
   }

   protected OpenJPAConfiguration newConfiguration() {
      return new LDAPConfiguration();
   }

   protected void open() {
      this.conf = (LDAPConfiguration)this.ctx.getConfiguration();
      this.log = this.conf.getLog("openjpa.Runtime");
      this.pFac = new ParserFactory(this.log, this.conf.isVDETimestamp());
   }

   public Class getManagedType(Object oid) {
      return oid instanceof DistinguishedNameId ? ((DistinguishedNameId)oid).getManagedType() : null;
   }

   public boolean load(OpenJPAStateManager sm, BitSet fields, FetchConfiguration fetch, int lockLevel, Object context) {
      ObjectData data = context != null ? (ObjectData)context : this.readEntry(sm.getMetaData(), sm.getObjectId(), fetch, fields);
      if (data == null) {
         return false;
      } else {
         DataLoader dl = this.pFac.getDataLoader(data.getMetaData());
         dl.loadData(data, fields, fetch, sm);
         return true;
      }
   }

   public ResultObjectProvider executeExtent(ClassMetaData meta, boolean subs, FetchConfiguration fetch) {
      String loc = meta.getStringExtension("ldap-objectclass");
      String filter;
      if (loc != null) {
         int idx = loc.indexOf(44);
         filter = "(objectclass=" + (idx >= 0 ? loc.substring(0, idx) : loc) + ")";
      } else {
         filter = "(objectclass=" + meta.getClass().getSimpleName() + ")";
      }

      try {
         LDAPConnection con = this.conf.getConnection();

         LDAPSearchResultsObjectProvider var11;
         try {
            String base = "";
            DataLoader dl = this.pFac.getDataLoader(meta);
            String[] lfetch = dl.createFetchList((BitSet)null, fetch);
            LDAPSearchResults results = con.search(base, 2, filter, lfetch, false);
            var11 = new LDAPSearchResultsObjectProvider(meta, fetch, results);
         } finally {
            this.conf.releaseConnection(con);
         }

         return var11;
      } catch (LDAPException var16) {
         throw (new StoreException(var16)).setFatal(true);
      }
   }

   public LDAPConfiguration getConfiguration() {
      return this.conf;
   }

   public ResultObjectProvider getSearchResults(ClassMetaData meta, FetchConfiguration fetch, LDAPSearchResults results) {
      return new LDAPSearchResultsObjectProvider(meta, fetch, results);
   }

   protected Collection flush(Collection pNew, Collection pNewUpdated, Collection pNewFlushedDeleted, Collection pDirty, Collection pDeleted) {
      Collection exceps = EMPTY_EXCEPTIONS;

      try {
         LDAPConnection con = this.conf.getConnection();

         try {
            Iterator var8;
            OpenJPAStateManager sm;
            if (pNew != null) {
               var8 = pNew.iterator();

               while(var8.hasNext()) {
                  sm = (OpenJPAStateManager)var8.next();
                  LDAPEntry entry = this.createEntry(sm);
                  if (this.log.isTraceEnabled()) {
                     this.log.trace("LDAPStoreManager: " + this.print(entry));
                  }

                  while(true) {
                     boolean attempted = false;

                     try {
                        con.add(entry);
                     } catch (LDAPException var24) {
                        if (var24.getLDAPResultCode() == 68) {
                           break;
                        }

                        if (!attempted && var24.getLDAPResultCode() == 32) {
                           attempted = true;
                           DN dn = new DN(entry.getDN());
                           if (dn.countRDNs() > 0 && this.createHierarchyEntry(con, dn.getParent(), (Collection)exceps)) {
                              continue;
                           }
                        }

                        if (exceps == EMPTY_EXCEPTIONS) {
                           exceps = new ArrayList();
                        }

                        ((Collection)exceps).add((new StoreException(var24)).setFatal(true));
                     }
                     break;
                  }
               }
            }

            DistinguishedNameId dni;
            ObjectIdConverter oic;
            Object oid;
            if (pDirty != null) {
               var8 = pDirty.iterator();

               label281:
               while(true) {
                  do {
                     do {
                        do {
                           if (!var8.hasNext()) {
                              break label281;
                           }

                           sm = (OpenJPAStateManager)var8.next();
                           oic = this.pFac.getObjectIdConverter(sm.getMetaData());
                           oid = sm.getObjectId();
                        } while(oid == null);
                     } while(oic == null);

                     dni = oic.convertObjectId(oid);
                  } while(dni == null);

                  LDAPModificationSet mods = this.createModification(sm);
                  String dn = dni.getDN();
                  if (this.log.isTraceEnabled()) {
                     this.log.trace("LDAPStoreManager: " + this.print(dn, mods));
                  }

                  try {
                     con.modify(dn, mods);
                  } catch (LDAPException var23) {
                     if (exceps == EMPTY_EXCEPTIONS) {
                        exceps = new ArrayList();
                     }

                     ((Collection)exceps).add(((StoreException)(32 == var23.getLDAPResultCode() ? new ObjectNotFoundException(var23) : new StoreException(var23))).setFatal(true));
                  }
               }
            }

            if (pDeleted != null) {
               var8 = pDeleted.iterator();

               while(true) {
                  do {
                     do {
                        do {
                           if (!var8.hasNext()) {
                              return (Collection)exceps;
                           }

                           sm = (OpenJPAStateManager)var8.next();
                           oic = this.pFac.getObjectIdConverter(sm.getMetaData());
                           oid = sm.getObjectId();
                        } while(oid == null);
                     } while(oic == null);

                     dni = oic.convertObjectId(oid);
                  } while(dni == null);

                  try {
                     con.delete(dni.getDN());
                  } catch (LDAPException var22) {
                     if (32 != var22.getLDAPResultCode()) {
                        if (exceps == EMPTY_EXCEPTIONS) {
                           exceps = new ArrayList();
                        }

                        ((Collection)exceps).add((new StoreException(var22)).setFatal(true));
                     }
                  }
               }
            }
         } finally {
            this.conf.releaseConnection(con);
         }
      } catch (LDAPException var26) {
         if (exceps == EMPTY_EXCEPTIONS) {
            exceps = new ArrayList();
         }

         ((Collection)exceps).add((new StoreException(var26)).setFatal(true));
      }

      return (Collection)exceps;
   }

   private boolean createHierarchyEntry(LDAPConnection con, DN dn, Collection exceps) {
      LDAPAttributeSet attrs = new LDAPAttributeSet();
      if (dn.countRDNs() > 0) {
         RDN rdn = (RDN)dn.getRDNs().elementAt(0);
         String[] types = rdn.getTypes();
         if (types != null && types.length == 1) {
            String type = types[0];
            if ("ou".equalsIgnoreCase(type)) {
               attrs.add(new LDAPAttribute("objectclass", new String[]{"top", "organizationalUnit"}));
               attrs.add(new LDAPAttribute("ou", rdn.getValues()[0]));
            } else if ("dc".equalsIgnoreCase(type)) {
               attrs.add(new LDAPAttribute("objectclass", new String[]{"top", "domain"}));
               attrs.add(new LDAPAttribute("dc", rdn.getValues()[0]));
            }

            LDAPEntry entry = new LDAPEntry(dn.toString(), attrs);
            if (this.log.isTraceEnabled()) {
               this.log.trace("LDAPStoreManager: " + this.print(entry));
            }

            while(true) {
               boolean attempted = false;

               try {
                  con.add(entry);
                  return true;
               } catch (LDAPException var11) {
                  if (var11.getLDAPResultCode() == 68) {
                     return true;
                  }

                  if (attempted || var11.getLDAPResultCode() != 32) {
                     break;
                  }

                  attempted = true;
                  if (dn.countRDNs() <= 1 || !this.createHierarchyEntry(con, dn.getParent(), exceps)) {
                     break;
                  }
               }
            }

            exceps.add((new StoreException(var11)).setFatal(true));
         }
      }

      return false;
   }

   private String print(LDAPEntry entry) {
      StringBuilder sb = new StringBuilder();
      sb.append("dn: ");
      sb.append(entry.getDN());
      sb.append('\n');
      sb.append(this.print(entry.getAttributeSet()));
      return sb.toString();
   }

   private String print(LDAPAttributeSet attrs) {
      StringBuilder sb = new StringBuilder();
      if (attrs != null) {
         Enumeration e = attrs.getAttributes();

         while(e.hasMoreElements()) {
            sb.append(this.print((LDAPAttribute)e.nextElement()));
            if (e.hasMoreElements()) {
               sb.append('\n');
            }
         }
      }

      return sb.toString();
   }

   private String print(String dn, LDAPModificationSet mods) {
      StringBuilder sb = new StringBuilder();
      sb.append("dn: ");
      sb.append(dn);
      sb.append('\n');

      for(int i = 0; i < mods.size(); ++i) {
         sb.append(this.print(mods.elementAt(i)));
         if (i + 1 < mods.size()) {
            sb.append('\n');
         }
      }

      return sb.toString();
   }

   private String print(LDAPModification mod) {
      StringBuilder sb = new StringBuilder();
      switch (mod.getOp()) {
         case 0:
            sb.append("ADD: ");
            break;
         case 1:
            sb.append("DEL: ");
            break;
         case 2:
            sb.append("MOD: ");
            break;
         default:
            sb.append("UKN: ");
      }

      sb.append(this.print(mod.getAttribute()));
      return sb.toString();
   }

   private String print(LDAPAttribute attr) {
      StringBuilder sb = new StringBuilder();
      sb.append(attr.getBaseName());
      sb.append(": ");
      Enumeration v = attr.getByteValues();
      if (v == null) {
         sb.append("<none>");
      } else {
         while(v.hasMoreElements()) {
            byte[] val = (byte[])((byte[])v.nextElement());

            try {
               if (knownBinaryAttrs.contains(attr.getBaseName())) {
                  sb.append("<binary value, length:");
                  sb.append(val.length);
                  sb.append(">");
               } else {
                  String sval = new String(val, "UTF8");
                  if (sval.length() == 0 && val.length > 0) {
                     sb.append("<binary value, length:");
                     sb.append(val.length);
                     sb.append(">");
                  } else {
                     sb.append(sval);
                  }
               }
            } catch (Throwable var6) {
               if (val != null) {
                  sb.append("<binary value, length:");
                  sb.append(val.length);
                  sb.append(">");
               } else {
                  sb.append("null value");
               }
            }

            if (v.hasMoreElements()) {
               sb.append(", ");
            }
         }
      }

      return sb.toString();
   }

   public boolean exists(OpenJPAStateManager sm, Object oid) {
      return this.readEntry(sm.getMetaData(), oid, (FetchConfiguration)null, (BitSet)null) != null;
   }

   public boolean assignObjectId(OpenJPAStateManager sm, boolean preFlush) {
      ClassMetaData meta = sm.getMetaData();
      return meta.getIdentityType() == 2 ? ApplicationIds.assign(sm, this, preFlush) : this.assignDataStoreId(sm, preFlush);
   }

   protected boolean assignDataStoreId(OpenJPAStateManager sm, Object val) {
      throw new UnsupportedOperationException();
   }

   protected boolean assignDataStoreId(OpenJPAStateManager sm, boolean preFlush) {
      if (preFlush) {
         return false;
      } else {
         sm.setObjectId(this.pFac.getStateParser(sm.getMetaData()).parseState(sm));
         return true;
      }
   }

   protected Collection getUnsupportedOptions() {
      Collection c = super.getUnsupportedOptions();
      c.remove("openjpa.option.DatastoreIdentity");
      c.add("openjpa.option.EmbeddedRelation");
      c.add("openjpa.option.EmbeddedCollectionRelation");
      c.add("openjpa.option.EmbeddedMapRelation");
      return c;
   }

   public Object newDataStoreId(Object arg0, ClassMetaData arg1) {
      throw new UnsupportedOperationException();
   }

   public StoreQuery newQuery(String language) {
      return "javax.jdo.query.JDOQL".equals(language) ? new LDAPStoreQuery(new JDOQLParser(), this, this.log, this.pFac) : null;
   }

   public Object copyDataStoreId(Object oid, ClassMetaData meta) {
      DistinguishedNameId dni = (DistinguishedNameId)oid;
      return new DistinguishedNameId(meta, dni.getDN());
   }

   public Class getDataStoreIdType(ClassMetaData meta) {
      return DistinguishedNameId.class;
   }

   static {
      knownBinaryAttrs.add("xacmlDocument");
      knownBinaryAttrs.add("principalPassword");
      knownBinaryAttrs.add("userCertificate");
      knownBinaryAttrs.add("wlsXmlFragment");
      EMPTY_EXCEPTIONS = Collections.EMPTY_SET;
   }

   private class LDAPSearchResultsObjectProvider implements ResultObjectProvider {
      private ClassMetaData meta;
      private FetchConfiguration fetch;
      private LDAPSearchResults results;
      private LDAPEntry nextEntry;

      public LDAPSearchResultsObjectProvider(ClassMetaData meta, FetchConfiguration fetch, LDAPSearchResults results) {
         this.meta = meta;
         this.fetch = fetch;
         this.results = results;
      }

      public boolean supportsRandomAccess() {
         return false;
      }

      public void open() throws Exception {
      }

      public Object getResultObject() throws Exception {
         if (this.nextEntry != null) {
            String dn = this.nextEntry.getDN();
            if (LDAPStoreManager.this.log.isTraceEnabled()) {
               LDAPStoreManager.this.log.trace("LDAPStoreManager: result dn: " + dn);
               LDAPStoreManager.this.log.trace("LDAPStoreManager: " + LDAPStoreManager.this.print(this.nextEntry));
            }

            DistinguishedNameId dni = new DistinguishedNameId(this.meta, dn);
            ObjectIdConverter oic = LDAPStoreManager.this.pFac.getObjectIdConverter(this.meta);
            Object oid = oic != null ? oic.convertDistinguishedNameId(dni) : dni;
            if (oid != null) {
               return LDAPStoreManager.this.ctx.find(oid, this.fetch, (BitSet)null, new ObjectData(oid, this.nextEntry, this.meta), 0);
            }

            if (LDAPStoreManager.this.log.isTraceEnabled()) {
               LDAPStoreManager.this.log.trace("LDAPStoreManager: dn: " + dn + " was determined to not be of type: " + this.meta.getDescribedType().getName());
            }
         }

         return null;
      }

      public boolean next() throws Exception {
         if (this.results != null && this.results.hasMoreElements()) {
            this.nextEntry = this.results.next();
            return true;
         } else {
            return false;
         }
      }

      public boolean absolute(int pos) throws Exception {
         throw new UnsupportedOperationException();
      }

      public int size() throws Exception {
         return this.results != null ? this.results.getCount() : 0;
      }

      public void reset() throws Exception {
         throw new UnsupportedOperationException();
      }

      public void close() throws Exception {
      }

      public void handleCheckedException(Exception e) {
         throw new StoreException(e);
      }
   }
}
