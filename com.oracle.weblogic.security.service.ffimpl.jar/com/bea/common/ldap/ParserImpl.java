package com.bea.common.ldap;

import com.bea.common.ldap.escaping.Escaping;
import com.bea.common.ldap.escaping.EscapingFactory;
import com.bea.common.ldap.exps.Const;
import com.bea.common.security.internal.service.ServiceLogger;
import com.bea.common.security.utils.Pair;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TimeZone;
import netscape.ldap.LDAPAttribute;
import netscape.ldap.LDAPAttributeSet;
import netscape.ldap.LDAPEntry;
import netscape.ldap.LDAPModificationSet;
import netscape.ldap.util.DN;
import netscape.ldap.util.RDN;
import org.apache.openjpa.event.OrphanedKeyAction;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;
import org.apache.openjpa.kernel.StoreContext;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;
import org.apache.openjpa.util.ApplicationIds;
import org.apache.openjpa.util.Proxy;
import org.apache.openjpa.util.StoreException;
import org.apache.openjpa.util.UnsupportedException;

public class ParserImpl implements StateParser, DataLoader, ObjectIdConverter {
   private static final String HEADER = "LDAPParser: ";
   private ClassMetaData meta;
   private ParserFactory factory;
   private Log log;
   private boolean isVDETimestamp;
   private Map fieldBuilders = new HashMap();
   private List dnBuilders = new ArrayList();
   private List attrBuilders = new ArrayList();
   private Map insertConstantRDNMap;

   public ParserImpl(ClassMetaData meta, ParserFactory factory, boolean isVDETimestamp) {
      this.meta = meta;
      this.factory = factory;
      this.log = factory.getLog();
      this.isVDETimestamp = isVDETimestamp;
      this.insertConstantRDNMap = new HashMap();
      String crdn = meta.getStringExtension("com.bea.common.security", "ldap-insert-constant-rdn");
      int k;
      String rdnc;
      if (crdn != null) {
         StringTokenizer st = new StringTokenizer(crdn, ";");

         while(st.hasMoreTokens()) {
            String val = st.nextToken();
            k = val.indexOf(32);
            if (k > 0) {
               rdnc = val.substring(0, k);
               String value = val.substring(k + 1);
               this.insertConstantRDNMap.put(rdnc, value);
            }
         }
      }

      this.attrBuilders.add(new LDAPObjectclassAttributeBuilder(meta));
      FieldMetaData[] pkfs = meta.getPrimaryKeyFields();
      if (pkfs != null) {
         int i;
         FieldMetaData f;
         for(i = pkfs.length - 1; i >= 0; --i) {
            f = pkfs[i];
            rdnc = (String)this.insertConstantRDNMap.get(f.getName());
            if (rdnc != null) {
               this.dnBuilders.add(new ConstantDNBuilder(rdnc));
            }

            DNBuilder b = new AttributeDNBuilder(f);
            if (this.dnBuilders.size() > 0) {
               String rdnc = pkfs[i + 1].getStringExtension("com.bea.common.security", "ldap-rdn-cont");
               if (rdnc != null && Boolean.valueOf(rdnc)) {
                  b = new MultiAttributeDNBuilder((DNBuilder)b, (DNBuilder)this.dnBuilders.remove(this.dnBuilders.size() - 1));
               }
            }

            this.dnBuilders.add(b);
         }

         for(i = pkfs.length - 1; i >= 0; --i) {
            f = pkfs[i];
            this.attrBuilders.add(new LDAPSingleAttributeBuilder(f));
            rdnc = f.getStringExtension("com.bea.common.security", "ldap-rdn-cont");
            if (rdnc == null || !Boolean.valueOf(rdnc)) {
               break;
            }
         }
      }

      FieldMetaData[] fs = meta.getFields();
      if (fs != null) {
         k = 0;

         for(int j = 0; j < fs.length; ++j) {
            FieldMetaData f = fs[j];
            LDAPAttributeBuilder lab = null;
            Object fb;
            if (f.isPrimaryKey()) {
               fb = new DNFieldBuilder(f, k++);
            } else {
               label63:
               switch (f.getTypeCode()) {
                  case 11:
                     ValueMetaData av = f.getElement();
                     if (av.getTypeCode() == 1) {
                        fb = new BinaryAttributeFieldBuilder(f);
                        lab = new LDAPBinaryAttributeBuilder(f);
                        break;
                     }
                  case 12:
                     ValueMetaData v = f.getElement();
                     switch (v.getTypeCode()) {
                        case 11:
                        default:
                           fb = new MultiValueAttributeFieldBuilder(f);
                           lab = new LDAPMultiAttributeBuilder(f);
                           break label63;
                     }
                  default:
                     fb = new AttributeFieldBuilder(f);
                     lab = new LDAPSingleAttributeBuilder(f);
               }
            }

            this.fieldBuilders.put(f, fb);
            if (lab != null) {
               this.attrBuilders.add(lab);
            }

            String crdnVal = (String)this.insertConstantRDNMap.get(f.getName());
            if (crdnVal != null) {
               if (fb instanceof DNFieldBuilder) {
                  ConstantDNFieldBuilder cdnfb = new ConstantDNFieldBuilder((DNFieldBuilder)fb, crdnVal);
                  this.fieldBuilders.put(f, cdnfb);
                  k += cdnfb.size() - 1;
               }

               ++k;
            }
         }
      }

   }

   public DistinguishedNameId parseState(OpenJPAStateManager sm) {
      String dn = this.buildDN(sm);
      if (this.log.isTraceEnabled()) {
         this.log.trace("LDAPParser: built dn from state: " + dn);
      }

      return new DistinguishedNameId(this.meta, dn);
   }

   public void loadData(ObjectData data, FetchConfiguration fetch, OpenJPAStateManager sm) {
      this.loadData(data, (BitSet)null, fetch, sm);
   }

   public void loadData(ObjectData data, BitSet fields, FetchConfiguration fetch, OpenJPAStateManager sm) {
      LDAPEntry entry = data.getEntry();
      LDAPAttributeSet attrs = entry.getAttributeSet();
      List comps = new ArrayList();
      DN dn = new DN(entry.getDN());
      List rdns = dn.getRDNs();
      ListIterator it = rdns.listIterator(rdns.size());

      while(true) {
         String[] dnComponents;
         do {
            if (!it.hasPrevious()) {
               dnComponents = (String[])comps.toArray(new String[comps.size()]);
               FieldMetaData[] fmds = this.meta.getFields();

               for(int i = 0; i < fmds.length; ++i) {
                  FieldMetaData f = fmds[i];
                  if (this.isOnFetchList(f, fields, fetch)) {
                     FieldBuilder fb = (FieldBuilder)this.fieldBuilders.get(f);
                     if (fb != null) {
                        fb.load(dnComponents, attrs, sm, fetch);
                     }
                  }
               }

               return;
            }

            dnComponents = ((RDN)it.previous()).getValues();
         } while(dnComponents == null);

         for(int i = 0; i < dnComponents.length; ++i) {
            comps.add(dnComponents[i]);
         }
      }
   }

   public String[] createFetchList(BitSet fields, FetchConfiguration fetch) {
      List fetchList = new ArrayList();
      FieldMetaData[] fmds = this.meta.getFields();

      for(int i = 0; i < fmds.length; ++i) {
         FieldMetaData f = fmds[i];
         if (this.isOnFetchList(f, fields, fetch)) {
            String attr = f.getStringExtension("com.bea.common.security", "ldap-attr");
            fetchList.add(attr != null ? attr : f.getName());
         }
      }

      String[] list = fetchList.isEmpty() ? null : (String[])fetchList.toArray(new String[fetchList.size()]);
      return list;
   }

   private boolean isOnFetchList(FieldMetaData f, BitSet fields, FetchConfiguration fetch) {
      return fields != null ? fields.get(f.getIndex()) : f.getManagement() == 3 && (f.isInDefaultFetchGroup() || fetch.hasFetchGroup(f.getLoadFetchGroup()) || fetch.hasField(f.getFullName()));
   }

   public String createSearchBase(ClassMetaData meta, Map scopeMap, Object[] parameters) {
      FieldMetaData[] pks = meta.getPrimaryKeyFields();
      StringBuilder searchBase = new StringBuilder();
      boolean foundScoping = false;
      boolean foundBaseStart = false;

      for(int i = pks.length - 2; i >= 0; --i) {
         FieldMetaData f = pks[i];
         if (!foundScoping) {
            String rdnCont = f.getStringExtension("com.bea.common.security", "ldap-rdn-cont");
            if (rdnCont != null && Boolean.valueOf(rdnCont)) {
               continue;
            }

            foundScoping = true;
         }

         Const c = (Const)scopeMap.get(f);
         if (foundBaseStart && c == null) {
            throw new StoreException(ServiceLogger.getIllegalQuery());
         }

         if (c != null) {
            foundBaseStart = true;
            String crdnVal;
            if (searchBase.length() > 0) {
               crdnVal = f.getStringExtension("com.bea.common.security", "ldap-rdn-cont");
               searchBase.append((char)(crdnVal != null && Boolean.valueOf(crdnVal) ? '+' : ','));
            }

            crdnVal = (String)this.insertConstantRDNMap.get(f.getName());
            if (crdnVal != null) {
               searchBase.append(crdnVal);
               searchBase.append(',');
            }

            String attr = f.getStringExtension("com.bea.common.security", "ldap-attr");
            searchBase.append(attr != null ? attr : f.getName());
            searchBase.append('=');
            searchBase.append(c.getValue(parameters).toString());
         }
      }

      boolean holeFound = false;

      for(int i = 0; i < pks.length; ++i) {
         FieldMetaData f = pks[i];
         Const c = (Const)scopeMap.get(f);
         if (holeFound && c != null) {
            throw new StoreException("");
         }
      }

      return searchBase.toString();
   }

   private String buildDN(OpenJPAStateManager sm) {
      StringBuilder dn = new StringBuilder();
      Iterator it = this.dnBuilders.iterator();

      while(it.hasNext()) {
         ((DNBuilder)it.next()).consume(sm, dn);
         if (it.hasNext()) {
            dn.append(',');
         }
      }

      return dn.toString();
   }

   private Object toLoadable(OpenJPAStateManager sm, FieldMetaData fmd, Object val, FetchConfiguration fetch) {
      if (val == null) {
         return null;
      } else {
         switch (fmd.getTypeCode()) {
            case 12:
               Collection c = (Collection)val;
               Collection c2 = (Collection)sm.newFieldProxy(fmd.getIndex());
               Iterator itr = c.iterator();

               while(itr.hasNext()) {
                  c2.add(this.toNestedLoadable(sm, fmd.getElement(), itr.next(), fetch));
               }

               return c2;
            default:
               return this.toNestedLoadable(sm, fmd, val, fetch);
         }
      }
   }

   private Object toNestedLoadable(OpenJPAStateManager sm, ValueMetaData vmd, Object val, FetchConfiguration fetch) {
      if (val == null) {
         return null;
      } else {
         switch (vmd.getTypeCode()) {
            case 0:
            case 16:
               if (val instanceof Boolean) {
                  return val;
               }

               return "TRUE".equalsIgnoreCase(val.toString()) ? Boolean.TRUE : Boolean.FALSE;
            case 1:
            case 2:
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 17:
            case 18:
            case 19:
            case 20:
            case 23:
            case 24:
            case 25:
            case 26:
            default:
               return val;
            case 5:
            case 21:
               if (val instanceof Integer) {
                  return val;
               }

               return new Integer(Integer.parseInt(val.toString()));
            case 6:
            case 22:
               if (val instanceof Long) {
                  return val;
               }

               return new Long(Long.parseLong(val.toString()));
            case 14:
               if (val instanceof Date) {
                  return ((Date)val).clone();
               } else {
                  String value = val.toString();
                  if (this.isVDETimestamp) {
                     if (value.length() != 13) {
                        throw new StoreException(ServiceLogger.getLDAPIllegalTimestampLength(value));
                     }
                  } else if (value.length() < 15) {
                     throw new StoreException(ServiceLogger.getLDAPIllegalTimestampLength(value));
                  }

                  int z = this.isVDETimestamp ? 12 : 14;

                  int year;
                  for(year = 0; year < z; ++year) {
                     if (!Character.isDigit(value.charAt(year))) {
                        throw new StoreException(ServiceLogger.getLDAPIllegalTimestampDigit(value));
                     }
                  }

                  year = Integer.parseInt(value.substring(0, 4));
                  int month = Integer.parseInt(value.substring(4, 6));
                  int day = Integer.parseInt(value.substring(6, 8));
                  int hour = Integer.parseInt(value.substring(8, 10));
                  int minute = Integer.parseInt(value.substring(10, 12));
                  int second = 0;
                  int millisecond = 0;
                  TimeZone tz = null;
                  if (this.isVDETimestamp) {
                     if (value.charAt(12) != 'Z') {
                        throw new StoreException(ServiceLogger.getLDAPIllegalTimestampDesignator(value));
                     }

                     tz = TimeZone.getTimeZone("GMT");
                  } else {
                     second = Integer.parseInt(value.substring(12, 14));
                     int idx = 14;
                     char nextTok = value.charAt(idx);
                     if (nextTok == '.') {
                        do {
                           ++idx;
                        } while(idx < value.length() && Character.isDigit(value.charAt(idx)));

                        String milliS = value.substring(15, idx);
                        switch (milliS.length()) {
                           case 0:
                              milliS = "000";
                              break;
                           case 1:
                              milliS = milliS + "00";
                              break;
                           case 2:
                              milliS = milliS + "0";
                           case 3:
                              break;
                           default:
                              milliS = milliS.substring(0, 3);
                        }

                        millisecond = Integer.parseInt(milliS);
                     }

                     if (idx >= value.length()) {
                        throw new StoreException(ServiceLogger.getLDAPMissingTimestampDesignator(value));
                     }

                     nextTok = value.charAt(idx);
                     switch (nextTok) {
                        case '+':
                        case '-':
                           if (value.length() != idx + 5) {
                              throw new StoreException(ServiceLogger.getLDAPMissingTimestampDesignator(value));
                           }

                           for(int i = idx; i < idx + 5; ++i) {
                              if (!Character.isDigit(value.charAt(i))) {
                                 throw new StoreException(ServiceLogger.getLDAPIllegalTimestampDigit(value));
                              }
                           }

                           tz = TimeZone.getTimeZone("GMT" + value.substring(idx, idx + 2) + ":" + value.substring(idx + 2, idx + 4));
                           break;
                        case 'Z':
                           tz = TimeZone.getTimeZone("GMT");
                           ++idx;
                           break;
                        default:
                           throw new StoreException(ServiceLogger.getLDAPIllegalTimestampDesignator(value));
                     }

                     --month;
                  }

                  Calendar cal = Calendar.getInstance(tz);
                  cal.set(1, year);
                  if (cal.getActualMinimum(2) <= month && cal.getActualMaximum(2) >= month) {
                     cal.set(2, month);
                     if (cal.getActualMinimum(5) <= day && cal.getActualMaximum(5) >= day) {
                        cal.set(5, day);
                        cal.set(11, hour);
                        cal.set(12, minute);
                        cal.set(13, second);
                        cal.set(14, millisecond);
                        return cal.getTime();
                     } else {
                        throw new StoreException(ServiceLogger.getLDAPOutOfAgeDay(value));
                     }
                  } else {
                     throw new StoreException(ServiceLogger.getLDAPOutOfAgeMonth(value));
                  }
               }
            case 15:
            case 27:
               StoreContext ctx = sm.getContext();
               Object pc = null;
               ClassMetaData vmdc = vmd.getDeclaredTypeMetaData();
               ObjectIdConverter oic = this.factory.getObjectIdConverter(vmdc);
               if (oic != null) {
                  pc = ctx.find(oic.convertDistinguishedNameId(new DistinguishedNameId(vmdc, val.toString())), fetch, (BitSet)null, (Object)null, 0);
               }

               if (pc != null) {
                  return pc;
               } else {
                  OrphanedKeyAction action = ctx.getConfiguration().getOrphanedKeyActionInstance();
                  return action.orphan(val, sm, vmd);
               }
         }
      }
   }

   private Object toStorable(FieldMetaData fmd, Object val, StoreContext ctx) {
      if (val == null) {
         return null;
      } else {
         switch (fmd.getTypeCode()) {
            case 11:
               ValueMetaData e = fmd.getElement();
               if (e.getTypeCode() == 1) {
                  return val;
               } else {
                  Collection c = new ArrayList();
                  int i = 0;

                  for(int len = Array.getLength(val); i < len; ++i) {
                     Object o = this.toNestedStorable(e, Array.get(val, i), ctx);
                     if (o != null) {
                        c.add(o);
                     }
                  }

                  if (c.isEmpty()) {
                     return null;
                  }

                  return c.toArray(new String[c.size()]);
               }
            case 12:
               Collection c = (Collection)val;
               if (c.isEmpty()) {
                  return null;
               } else {
                  Collection c2 = new ArrayList();
                  Iterator itr = c.iterator();

                  while(itr.hasNext()) {
                     Object o = this.toNestedStorable(fmd.getElement(), itr.next(), ctx);
                     if (o != null) {
                        c2.add(o);
                     }
                  }

                  if (c2.isEmpty()) {
                     return null;
                  }

                  return c2.toArray(new String[c2.size()]);
               }
            default:
               return this.toNestedStorable(fmd, val, ctx);
         }
      }
   }

   private Object toNestedStorable(ValueMetaData vmd, Object val, StoreContext ctx) {
      if (val == null) {
         return null;
      } else {
         switch (vmd.getTypeCode()) {
            case 0:
            case 16:
               if (val instanceof Boolean) {
                  return (Boolean)val ? "TRUE" : "FALSE";
               }

               return val.toString();
            case 11:
            case 12:
               throw new UnsupportedException(ServiceLogger.getStoreUnsupportedNestedContainer());
            case 14:
               if (val instanceof Proxy) {
                  return ((Proxy)val).copy(val);
               }

               return DateUtil.toString((Date)val, this.isVDETimestamp);
            case 15:
            case 27:
               Object oid = ctx.getObjectId(val);
               ObjectIdConverter oic = this.factory.getObjectIdConverter(vmd.getDeclaredTypeMetaData());
               return oid != null && oic != null ? oic.convertObjectId(oid).getDN() : null;
            default:
               return val.toString();
         }
      }
   }

   public DistinguishedNameId convertObjectId(Object oid) {
      if (oid instanceof DistinguishedNameId) {
         return (DistinguishedNameId)oid;
      } else {
         if (this.log.isTraceEnabled()) {
            this.log.trace("LDAPParser: oid: " + oid.toString());
         }

         Object[] pks = ApplicationIds.toPKValues(oid, this.meta);
         if (this.log.isTraceEnabled()) {
            this.log.trace("LDAPParser: converting dn from oid keys: " + this.printArray(pks));
         }

         StringBuilder dn = new StringBuilder();
         Iterator it = this.dnBuilders.iterator();

         while(it.hasNext()) {
            ((DNBuilder)it.next()).consume(pks, dn);
            if (it.hasNext()) {
               dn.append(',');
            }
         }

         if (this.log.isTraceEnabled()) {
            this.log.trace("LDAPParser: generated dn: " + dn.toString());
         }

         return new DistinguishedNameId(this.meta, dn.toString());
      }
   }

   public Object convertDistinguishedNameId(DistinguishedNameId dni) {
      if (dni == null) {
         return null;
      } else if (this.meta.getObjectIdType().isAssignableFrom(dni.getClass())) {
         return dni;
      } else {
         if (this.log.isTraceEnabled()) {
            this.log.trace("LDAPParser: converting dni to oid: " + dni.getDN());
         }

         FieldMetaData[] pfmd = this.meta.getPrimaryKeyFields();
         if (pfmd == null) {
            return null;
         } else {
            Object[] vals = new Object[pfmd.length];
            List comps = new ArrayList();
            DN dn = new DN(dni.getDN());
            List rdns = dn.getRDNs();
            ListIterator it = rdns.listIterator(rdns.size());

            while(true) {
               String[] rattrs;
               String[] rvals;
               do {
                  do {
                     if (!it.hasPrevious()) {
                        for(int i = 0; i < pfmd.length; ++i) {
                           FieldBuilder fb = (FieldBuilder)this.fieldBuilders.get(pfmd[i]);
                           if (fb != null && !fb.parse(comps, vals)) {
                              return null;
                           }
                        }

                        Object oid = ApplicationIds.fromPKValues(vals, this.meta);
                        if (this.log.isTraceEnabled()) {
                           this.log.trace("LDAPParser: converted oid: " + oid.toString());
                        }

                        return oid;
                     }

                     RDN r = (RDN)it.previous();
                     rattrs = r.getTypes();
                     rvals = r.getValues();
                  } while(rattrs == null);
               } while(rvals == null);

               for(int i = 0; i < rvals.length; ++i) {
                  comps.add(new Pair(rattrs[i], rvals[i]));
               }
            }
         }
      }
   }

   public LDAPAttributeSet create(OpenJPAStateManager sm) {
      LDAPAttributeSet ls = new LDAPAttributeSet();
      Iterator var3 = this.attrBuilders.iterator();

      while(var3.hasNext()) {
         LDAPAttributeBuilder b = (LDAPAttributeBuilder)var3.next();
         b.build(sm, ls);
      }

      return ls;
   }

   public LDAPModificationSet deltas(OpenJPAStateManager sm) {
      LDAPModificationSet ls = new LDAPModificationSet();
      Iterator var3 = this.attrBuilders.iterator();

      while(var3.hasNext()) {
         LDAPAttributeBuilder b = (LDAPAttributeBuilder)var3.next();
         b.build(sm, ls);
      }

      return ls;
   }

   private String printArray(Object[] arg) {
      if (arg != null) {
         StringBuilder sb = new StringBuilder();
         sb.append('[');

         for(int i = 0; i < arg.length; ++i) {
            if (i > 0) {
               sb.append(',');
            }

            sb.append(arg[i]);
         }

         sb.append(']');
         return sb.toString();
      } else {
         return "null";
      }
   }

   private class LDAPBinaryMultiAttributeBuilder extends LDAPSingleAttributeBuilder {
      public LDAPBinaryMultiAttributeBuilder(FieldMetaData f) {
         super(f);
      }

      public void build(OpenJPAStateManager sm, LDAPAttributeSet attrs) {
         Object o = sm.fetch(this.getField().getIndex());
         if (o != null) {
            Collection vals = (Collection)ParserImpl.this.toStorable(this.getField(), o, sm.getContext());
            if (vals != null) {
               LDAPAttribute a = new LDAPAttribute(this.getAttribute());
               Iterator var6 = vals.iterator();

               while(var6.hasNext()) {
                  byte[] v = (byte[])var6.next();
                  a.addValue(v);
               }

               attrs.add(a);
            }
         }

      }

      public void build(OpenJPAStateManager sm, LDAPModificationSet mods) {
         if (sm.isDirty()) {
            BitSet dirtySet = sm.getDirty();
            FieldMetaData f = this.getField();
            if (dirtySet.get(f.getIndex())) {
               Object o = sm.fetch(f.getIndex());
               if (o != null) {
                  mods.add(2, new LDAPAttribute(this.getAttribute(), (byte[])((byte[])ParserImpl.this.toStorable(this.getField(), o, sm.getContext()))));
               } else {
                  mods.add(2, new LDAPAttribute(this.getAttribute()));
               }
            }
         }

      }
   }

   private class LDAPBinaryAttributeBuilder extends LDAPSingleAttributeBuilder {
      public LDAPBinaryAttributeBuilder(FieldMetaData f) {
         super(f);
      }

      public void build(OpenJPAStateManager sm, LDAPAttributeSet attrs) {
         Object o = sm.fetch(this.getField().getIndex());
         if (o != null) {
            byte[] ba = (byte[])((byte[])ParserImpl.this.toStorable(this.getField(), o, sm.getContext()));
            if (ba != null) {
               attrs.add(new LDAPAttribute(this.getAttribute(), ba));
            }
         }

      }

      public void build(OpenJPAStateManager sm, LDAPModificationSet mods) {
         if (sm.isDirty()) {
            BitSet dirtySet = sm.getDirty();
            FieldMetaData f = this.getField();
            if (dirtySet.get(f.getIndex())) {
               Object o = sm.fetch(f.getIndex());
               if (o != null) {
                  byte[] ba = (byte[])((byte[])ParserImpl.this.toStorable(this.getField(), o, sm.getContext()));
                  mods.add(2, new LDAPAttribute(this.getAttribute(), ba));
               } else {
                  mods.add(2, new LDAPAttribute(this.getAttribute()));
               }
            }
         }

      }
   }

   private class LDAPMultiAttributeBuilder extends LDAPSingleAttributeBuilder {
      public LDAPMultiAttributeBuilder(FieldMetaData f) {
         super(f);
      }

      public void build(OpenJPAStateManager sm, LDAPAttributeSet attrs) {
         Object o = sm.fetch(this.getField().getIndex());
         if (o != null) {
            String[] val = (String[])((String[])ParserImpl.this.toStorable(this.getField(), o, sm.getContext()));
            if (val != null) {
               attrs.add(new LDAPAttribute(this.getAttribute(), this.escaper != null ? this.escape(val) : val));
            }
         }

      }

      protected String[] escape(String[] data) {
         String[] escaped = null;
         if (data != null) {
            Escaping e = this.escaper.getEscaping();
            escaped = new String[data.length];

            for(int i = 0; i < data.length; ++i) {
               escaped[i] = e.escapeString(data[i]);
            }
         }

         return escaped;
      }

      public void build(OpenJPAStateManager sm, LDAPModificationSet mods) {
         if (sm.isDirty()) {
            BitSet dirtySet = sm.getDirty();
            FieldMetaData f = this.getField();
            if (dirtySet.get(f.getIndex())) {
               Object o = sm.fetch(f.getIndex());
               if (o != null) {
                  String[] val = (String[])((String[])ParserImpl.this.toStorable(this.getField(), o, sm.getContext()));
                  mods.add(2, new LDAPAttribute(this.getAttribute(), val != null && this.escaper != null ? this.escape(val) : val));
               } else {
                  mods.add(2, new LDAPAttribute(this.getAttribute()));
               }
            }
         }

      }
   }

   private class LDAPSingleAttributeBuilder extends BaseLDAPAttributeBuilder {
      private String attr;
      protected EscapingFactory escaper;

      public LDAPSingleAttributeBuilder(FieldMetaData f) {
         super(f);
         this.attr = f.getStringExtension("com.bea.common.security", "ldap-attr");
         if (this.attr == null) {
            this.attr = f.getName();
         }

         String escaperClass = f.getStringExtension("com.bea.common.security", "ldap-escaping");
         if (escaperClass != null) {
            this.escaper = this.getEscapingFactory(escaperClass);
         }

      }

      protected EscapingFactory getEscapingFactory(String escaperClass) {
         try {
            return (EscapingFactory)Class.forName(escaperClass).newInstance();
         } catch (Throwable var3) {
            throw new StoreException(var3);
         }
      }

      public String getAttribute() {
         return this.attr;
      }

      public void build(OpenJPAStateManager sm, LDAPAttributeSet attrs) {
         Object o = sm.fetch(this.getField().getIndex());
         if (o != null) {
            String val = (String)ParserImpl.this.toStorable(this.getField(), o, sm.getContext());
            if (val != null) {
               attrs.add(new LDAPAttribute(this.getAttribute(), this.escaper != null ? this.escaper.getEscaping().escapeString(val) : val));
            }
         }

      }

      public void build(OpenJPAStateManager sm, LDAPModificationSet mods) {
         if (sm.isDirty()) {
            BitSet dirtySet = sm.getDirty();
            FieldMetaData f = this.getField();
            if (dirtySet.get(f.getIndex())) {
               Object o = sm.fetch(f.getIndex());
               if (o != null) {
                  String val = (String)ParserImpl.this.toStorable(this.getField(), o, sm.getContext());
                  mods.add(2, new LDAPAttribute(this.getAttribute(), val != null && this.escaper != null ? this.escaper.getEscaping().escapeString(val) : val));
               } else {
                  mods.add(2, new LDAPAttribute(this.getAttribute()));
               }
            }
         }

      }
   }

   private abstract class BaseLDAPAttributeBuilder implements LDAPAttributeBuilder {
      private FieldMetaData f;

      public BaseLDAPAttributeBuilder(FieldMetaData f) {
         this.f = f;
      }

      public FieldMetaData getField() {
         return this.f;
      }
   }

   private class LDAPObjectclassAttributeBuilder implements LDAPAttributeBuilder {
      private List ocs = new ArrayList();

      public LDAPObjectclassAttributeBuilder(ClassMetaData meta) {
         this.ocs.add("top");
         String loc = meta.getStringExtension("com.bea.common.security", "ldap-objectclass");
         if (loc != null) {
            StringTokenizer st = new StringTokenizer(loc, ",");

            while(st.hasMoreTokens()) {
               this.ocs.add(st.nextToken());
            }
         } else {
            this.ocs.add(meta.getDescribedType().getSimpleName());
         }

      }

      public void build(OpenJPAStateManager sm, LDAPAttributeSet attrs) {
         Set x = new HashSet(this.ocs);
         FieldMetaData[] fmds = ParserImpl.this.meta.getFields();

         for(int i = 0; i < fmds.length; ++i) {
            FieldMetaData fmd = fmds[i];
            String aux = fmd.getStringExtension("com.bea.common.security", "ldap-auxiliary");
            if (aux != null && sm.fetch(fmd.getIndex()) != null) {
               x.add(aux);
            }
         }

         attrs.add(new LDAPAttribute("objectclass", (String[])x.toArray(new String[x.size()])));
      }

      public void build(OpenJPAStateManager sm, LDAPModificationSet mods) {
         if (sm.isDirty()) {
            Set x = new HashSet();
            BitSet dirtySet = sm.getDirty();
            FieldMetaData[] fmds = ParserImpl.this.meta.getFields();

            for(int i = 0; i < fmds.length; ++i) {
               if (dirtySet.get(i)) {
                  FieldMetaData fmd = fmds[i];
                  String aux = fmd.getStringExtension("com.bea.common.security", "ldap-auxiliary");
                  if (aux != null && sm.fetch(fmd.getIndex()) != null) {
                     x.add(aux);
                  }
               }
            }

            if (!x.isEmpty()) {
               mods.add(0, new LDAPAttribute("objectclass", (String[])x.toArray(new String[x.size()])));
            }
         }

      }
   }

   private interface LDAPAttributeBuilder {
      void build(OpenJPAStateManager var1, LDAPAttributeSet var2);

      void build(OpenJPAStateManager var1, LDAPModificationSet var2);
   }

   private class MultiAttributeDNBuilder implements DNBuilder {
      private DNBuilder previous;
      private DNBuilder next;

      public MultiAttributeDNBuilder(DNBuilder previous, DNBuilder next) {
         this.previous = previous;
         this.next = next;
      }

      public void consume(OpenJPAStateManager sm, StringBuilder dn) {
         this.previous.consume(sm, dn);
         dn.append('+');
         this.next.consume(sm, dn);
      }

      public void consume(Object[] publicKeys, StringBuilder dn) {
         this.previous.consume(publicKeys, dn);
         dn.append('+');
         this.next.consume(publicKeys, dn);
      }
   }

   private class AttributeDNBuilder implements DNBuilder {
      private FieldMetaData fmd;
      private String rdna;
      private int index;
      private int pIndex;
      private EscapingFactory escaper;

      public AttributeDNBuilder(FieldMetaData fmd) {
         this.fmd = fmd;
         this.rdna = fmd.getStringExtension("com.bea.common.security", "ldap-attr");
         if (this.rdna == null) {
            this.rdna = fmd.getName();
         }

         this.index = fmd.getIndex();
         this.pIndex = fmd.getPrimaryKeyIndex();
         String escaperClass = fmd.getStringExtension("com.bea.common.security", "ldap-escaping");
         if (escaperClass != null) {
            this.escaper = this.getEscapingFactory(escaperClass);
         }

      }

      protected EscapingFactory getEscapingFactory(String escaperClass) {
         try {
            return (EscapingFactory)Class.forName(escaperClass).newInstance();
         } catch (Throwable var3) {
            throw new StoreException(var3);
         }
      }

      public void consume(OpenJPAStateManager sm, StringBuilder dn) {
         dn.append(this.rdna);
         dn.append('=');
         Object o = sm.fetch(this.index);
         if (o == null) {
            throw new StoreException(ServiceLogger.getNullOrEmptyPrimaryKey(this.fmd.getName()));
         } else {
            String val = o.toString();
            dn.append(this.escaper != null ? this.escaper.getEscaping().escapeString(val) : val);
         }
      }

      public void consume(Object[] publicKeys, StringBuilder dn) {
         dn.append(this.rdna);
         dn.append('=');
         Object o = publicKeys[this.pIndex];
         if (o == null) {
            throw new StoreException(ServiceLogger.getNullOrEmptyPrimaryKey(this.fmd.getName()));
         } else {
            String val = o.toString();
            dn.append(this.escaper != null ? this.escaper.getEscaping().escapeString(val) : val);
         }
      }
   }

   private class ConstantDNBuilder implements DNBuilder {
      private String constant;

      public ConstantDNBuilder(String constant) {
         this.constant = constant;
      }

      public void consume(OpenJPAStateManager sm, StringBuilder dn) {
         dn.append(this.constant);
      }

      public void consume(Object[] publicKeys, StringBuilder dn) {
         dn.append(this.constant);
      }
   }

   private interface DNBuilder {
      void consume(OpenJPAStateManager var1, StringBuilder var2);

      void consume(Object[] var1, StringBuilder var2);
   }

   private class MultiValueBinaryAttributeFieldBuilder extends AttributeFieldBuilder {
      public MultiValueBinaryAttributeFieldBuilder(FieldMetaData f) {
         super(f);
      }

      public void load(String[] dnComponents, LDAPAttributeSet attributes, OpenJPAStateManager sm, FetchConfiguration fetch) {
         LDAPAttribute a = attributes.getAttribute(this.getAttribute());
         if (a != null) {
            Enumeration vals = a.getByteValues();
            if (vals != null) {
               Collection c = new ArrayList();

               while(vals.hasMoreElements()) {
                  c.add((String)vals.nextElement());
               }

               sm.store(this.getField().getIndex(), ParserImpl.this.toLoadable(sm, this.getField(), c, fetch));
            }
         }

      }
   }

   private class BinaryAttributeFieldBuilder extends AttributeFieldBuilder {
      public BinaryAttributeFieldBuilder(FieldMetaData f) {
         super(f);
      }

      public void load(String[] dnComponents, LDAPAttributeSet attributes, OpenJPAStateManager sm, FetchConfiguration fetch) {
         LDAPAttribute a = attributes.getAttribute(this.getAttribute());
         if (a != null) {
            Enumeration vals = a.getByteValues();
            if (vals != null && vals.hasMoreElements()) {
               byte[] n = (byte[])((byte[])vals.nextElement());
               Object o = ParserImpl.this.toLoadable(sm, this.getField(), n, fetch);
               sm.store(this.getField().getIndex(), o);
            }
         }

      }
   }

   private class MultiValueAttributeFieldBuilder extends AttributeFieldBuilder {
      public MultiValueAttributeFieldBuilder(FieldMetaData f) {
         super(f);
      }

      protected Collection unescape(Collection data) {
         Collection unescaped = null;
         if (data != null) {
            Escaping e = this.escaper.getEscaping();
            unescaped = new ArrayList();
            Iterator it = data.iterator();

            while(it.hasNext()) {
               unescaped.add(e.unescapeString((String)it.next()));
            }
         }

         return unescaped;
      }

      public void load(String[] dnComponents, LDAPAttributeSet attributes, OpenJPAStateManager sm, FetchConfiguration fetch) {
         LDAPAttribute a = attributes.getAttribute(this.getAttribute());
         if (a != null) {
            Enumeration vals = a.getStringValues();
            if (vals != null) {
               Collection c = new ArrayList();

               while(vals.hasMoreElements()) {
                  c.add((String)vals.nextElement());
               }

               sm.store(this.getField().getIndex(), ParserImpl.this.toLoadable(sm, this.getField(), this.escaper != null ? this.unescape(c) : c, fetch));
            }
         }

      }
   }

   private class ConstantDNFieldBuilder implements FieldBuilder {
      private DNFieldBuilder inner;
      private List constant;

      public ConstantDNFieldBuilder(DNFieldBuilder inner, String constant) {
         this.inner = inner;
         this.constant = new ArrayList();
         if (constant != null) {
            StringTokenizer st = new StringTokenizer(constant, ",");

            while(st.hasMoreTokens()) {
               String rdn = st.nextToken();
               int idx = rdn.indexOf(61);
               this.constant.add(idx >= 0 ? new Pair(rdn.substring(0, idx), rdn.substring(idx + 1)) : new Pair(rdn, ""));
            }
         }

      }

      public int size() {
         return this.constant.size();
      }

      public void load(String[] dnComponents, LDAPAttributeSet attributes, OpenJPAStateManager sm, FetchConfiguration fetch) {
         this.inner.load(dnComponents, attributes, sm, fetch);
      }

      public boolean parse(List dnComponents, Object[] pks) {
         if (dnComponents != null) {
            int dnIndex = this.inner.getDNIndex();
            if (dnComponents.size() > dnIndex + this.constant.size()) {
               for(int i = this.constant.size() - 1; i >= 0; --i) {
                  ++dnIndex;
                  Pair rdn = (Pair)dnComponents.get(dnIndex);
                  Pair cdn = (Pair)this.constant.get(i);
                  if (ParserImpl.this.log.isTraceEnabled()) {
                     ParserImpl.this.log.trace("LDAPParser: comparing rdn: " + (String)rdn.getLeft() + "=" + (String)rdn.getRight());
                     ParserImpl.this.log.trace("LDAPParser:        to cdn: " + (String)cdn.getLeft() + "=" + (String)cdn.getRight());
                  }

                  if (!((String)cdn.getLeft()).equals(rdn.getLeft())) {
                     if (ParserImpl.this.log.isTraceEnabled()) {
                        ParserImpl.this.log.trace("LDAPParser: parse error because RDN attribute: " + (String)rdn.getLeft() + " does not match expected constant: " + (String)cdn.getLeft());
                     }

                     return false;
                  }

                  if (!((String)cdn.getRight()).equals(rdn.getRight())) {
                     if (ParserImpl.this.log.isTraceEnabled()) {
                        ParserImpl.this.log.trace("LDAPParser: parse error because RDN value: " + (String)rdn.getRight() + " does not match expected constant: " + (String)cdn.getRight());
                     }

                     return false;
                  }
               }

               return this.inner.parse(dnComponents, pks);
            }
         }

         if (ParserImpl.this.log.isTraceEnabled()) {
            ParserImpl.this.log.trace("LDAPParser: parse failure because DN has no components or expected index is longer than DN");
         }

         return false;
      }
   }

   private class DNFieldBuilder extends AttributeFieldBuilder {
      private int dnIndex;

      public DNFieldBuilder(FieldMetaData f, int dnIndex) {
         super(f);
         this.dnIndex = dnIndex;
      }

      public int getDNIndex() {
         return this.dnIndex;
      }

      public void load(String[] dnComponents, LDAPAttributeSet attributes, OpenJPAStateManager sm, FetchConfiguration fetch) {
         if (dnComponents != null && dnComponents.length > this.dnIndex) {
            String comp = dnComponents[this.dnIndex];
            if (comp != null) {
               int idx = comp.indexOf(61);
               if (idx >= 0) {
                  String val = comp.substring(idx + 1);
                  if (this.getAttribute().equalsIgnoreCase(comp.substring(0, idx))) {
                     sm.store(this.getField().getIndex(), this.escaper != null ? this.escaper.getEscaping().unescapeString(val) : val);
                  }
               } else {
                  sm.store(this.getField().getIndex(), this.escaper != null ? this.escaper.getEscaping().unescapeString(comp) : comp);
               }
            }
         }

      }

      public boolean parse(List dnComponents, Object[] pks) {
         if (dnComponents != null && dnComponents.size() > this.dnIndex) {
            Pair rdn = (Pair)dnComponents.get(this.dnIndex);
            if (!this.getAttribute().equals(rdn.getLeft())) {
               if (ParserImpl.this.log.isTraceEnabled()) {
                  ParserImpl.this.log.trace("LDAPParser: parse error because RDN attribute: " + this.getAttribute() + " does not match expected: " + (String)rdn.getLeft());
               }

               return false;
            } else {
               String comp = (String)rdn.getRight();
               if (comp == null) {
                  if (ParserImpl.this.log.isTraceEnabled()) {
                     ParserImpl.this.log.trace("LDAPParser: parse error because value is null for RDN attribute: " + this.getAttribute());
                  }

                  return false;
               } else {
                  pks[this.getField().getPrimaryKeyIndex()] = this.escaper != null ? this.escaper.getEscaping().unescapeString(comp) : comp;
                  return true;
               }
            }
         } else {
            if (ParserImpl.this.log.isTraceEnabled()) {
               ParserImpl.this.log.trace("LDAPParser: parse failure because DN has no components or expected index is longer than DN");
            }

            return false;
         }
      }
   }

   private class AttributeFieldBuilder extends BaseFieldBuilder {
      private String attr;
      protected EscapingFactory escaper;

      public AttributeFieldBuilder(FieldMetaData f) {
         super(f);
         this.attr = f.getStringExtension("com.bea.common.security", "ldap-attr");
         if (this.attr == null) {
            this.attr = f.getName();
         }

         String escaperClass = f.getStringExtension("com.bea.common.security", "ldap-escaping");
         if (escaperClass != null) {
            this.escaper = this.getEscapingFactory(escaperClass);
         }

      }

      protected EscapingFactory getEscapingFactory(String escaperClass) {
         try {
            return (EscapingFactory)Class.forName(escaperClass).newInstance();
         } catch (Throwable var3) {
            throw new StoreException(var3);
         }
      }

      public String getAttribute() {
         return this.attr;
      }

      public void load(String[] dnComponents, LDAPAttributeSet attributes, OpenJPAStateManager sm, FetchConfiguration fetch) {
         LDAPAttribute a = attributes.getAttribute(this.getAttribute());
         if (a != null) {
            Enumeration vals = a.getStringValues();
            if (vals != null && vals.hasMoreElements()) {
               String val = (String)vals.nextElement();
               sm.store(this.getField().getIndex(), ParserImpl.this.toLoadable(sm, this.getField(), this.escaper != null ? this.escaper.getEscaping().unescapeString(val) : val, fetch));
            }
         }

      }
   }

   private abstract class BaseFieldBuilder implements FieldBuilder {
      private FieldMetaData f;

      public BaseFieldBuilder(FieldMetaData f) {
         this.f = f;
      }

      public FieldMetaData getField() {
         return this.f;
      }

      public boolean parse(List dnComponents, Object[] pks) {
         return true;
      }
   }

   private interface FieldBuilder {
      void load(String[] var1, LDAPAttributeSet var2, OpenJPAStateManager var3, FetchConfiguration var4);

      boolean parse(List var1, Object[] var2);
   }
}
