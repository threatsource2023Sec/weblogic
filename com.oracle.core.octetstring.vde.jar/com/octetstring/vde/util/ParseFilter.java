package com.octetstring.vde.util;

import com.asn1c.core.OctetString;
import com.octetstring.ldapv3.AttributeValueAssertion;
import com.octetstring.ldapv3.Filter;
import com.octetstring.ldapv3.Filter_and;
import com.octetstring.ldapv3.Filter_or;
import com.octetstring.ldapv3.SubstringFilter;
import com.octetstring.ldapv3.SubstringFilter_substrings;
import com.octetstring.ldapv3.SubstringFilter_substrings_Seq;
import com.octetstring.nls.Messages;
import com.octetstring.vde.syntax.DirectoryString;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;

public class ParseFilter {
   public static Filter parse(String filter) throws DirectoryException {
      Filter fil = null;
      Filter cfil = null;
      int settype = -1;
      Vector branch = new Vector();
      int parencount = 0;
      int level = 0;
      StringTokenizer st = new StringTokenizer(filter, "(", true);
      new Filter();

      while(true) {
         while(st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.equals("(")) {
               ++parencount;
            } else {
               Filter nfilt;
               if (token.equals("&")) {
                  nfilt = new Filter();
                  Filter_and myand = new Filter_and();
                  nfilt.setAnd(myand);
                  if (settype == -1) {
                     fil = nfilt;
                  } else if (settype == 0) {
                     cfil.getAnd().addElement(nfilt);
                  } else if (settype == 1) {
                     cfil.getOr().addElement(nfilt);
                  } else if (settype == 2) {
                     if (cfil.getNot() != null) {
                        throw new DirectoryException(2, Messages.getString("Bad_LDAP_Filter_14"));
                     }

                     cfil.setNot(nfilt);
                  }

                  branch.addElement(nfilt);
                  settype = 0;
                  cfil = nfilt;
                  ++level;
               } else if (token.equals("|")) {
                  nfilt = new Filter();
                  nfilt.setOr(new Filter_or());
                  if (settype == -1) {
                     fil = nfilt;
                  } else if (settype == 0) {
                     cfil.getAnd().addElement(nfilt);
                  } else if (settype == 1) {
                     cfil.getOr().addElement(nfilt);
                  } else if (settype == 2) {
                     if (cfil.getNot() != null) {
                        throw new DirectoryException(2, Messages.getString("Bad_LDAP_Filter_14"));
                     }

                     cfil.setNot(nfilt);
                  }

                  branch.addElement(nfilt);
                  settype = 1;
                  cfil = nfilt;
                  ++level;
               } else if (token.equals("!")) {
                  nfilt = new Filter();
                  nfilt.setNot((Filter)null);
                  if (settype == -1) {
                     fil = nfilt;
                  } else if (settype == 0) {
                     cfil.getAnd().addElement(nfilt);
                  } else if (settype == 1) {
                     cfil.getOr().addElement(nfilt);
                  } else if (settype == 2) {
                     if (cfil.getNot() != null) {
                        throw new DirectoryException(2, Messages.getString("Bad_LDAP_Filter_14"));
                     }

                     cfil.setNot(nfilt);
                  }

                  branch.addElement(nfilt);
                  settype = 2;
                  cfil = nfilt;
                  ++level;
               } else {
                  nfilt = null;
                  StringTokenizer nst = new StringTokenizer(token, ")", true);
                  int nup = 0;

                  while(nst.hasMoreTokens()) {
                     String ntok = nst.nextToken();
                     if (ntok.equals(")")) {
                        ++nup;
                     } else {
                        int loc = false;
                        String attr;
                        String val;
                        AttributeValueAssertion em;
                        int loc;
                        if ((loc = ntok.indexOf(">=")) != -1) {
                           attr = ntok.substring(0, loc);
                           val = ntok.substring(loc + 2);
                           val = unescapeValue(val);
                           nfilt = new Filter();
                           em = new AttributeValueAssertion();
                           nfilt.setGreaterOrEqual(em);
                           em.setAttributeDesc(new OctetString(attr.getBytes()));

                           try {
                              em.setAssertionValue(new OctetString(val.getBytes("UTF8")));
                           } catch (UnsupportedEncodingException var31) {
                              em.setAssertionValue(new OctetString(val.getBytes()));
                           }
                        } else if ((loc = ntok.indexOf("<=")) != -1) {
                           attr = ntok.substring(0, loc);
                           val = ntok.substring(loc + 2);
                           val = unescapeValue(val);
                           nfilt = new Filter();
                           em = new AttributeValueAssertion();
                           nfilt.setLessOrEqual(em);
                           em.setAttributeDesc(new OctetString(attr.getBytes()));

                           try {
                              em.setAssertionValue(new OctetString(val.getBytes("UTF8")));
                           } catch (UnsupportedEncodingException var30) {
                              em.setAssertionValue(new OctetString(val.getBytes()));
                           }
                        } else if ((loc = ntok.indexOf("=")) != -1) {
                           attr = ntok.substring(0, loc);
                           val = ntok.substring(loc + 1);
                           if (val.equals("*")) {
                              nfilt = new Filter();
                              nfilt.setPresent(new OctetString(attr.getBytes()));
                           } else if (val.indexOf("*") < 0) {
                              nfilt = new Filter();
                              em = new AttributeValueAssertion();
                              nfilt.setEqualityMatch(em);
                              em.setAttributeDesc(new OctetString(attr.getBytes()));
                              val = unescapeValue(val);

                              try {
                                 em.setAssertionValue(new OctetString(val.getBytes("UTF8")));
                              } catch (UnsupportedEncodingException var29) {
                                 em.setAssertionValue(new OctetString(val.getBytes()));
                              }
                           } else {
                              StringTokenizer vst = new StringTokenizer(val, "*", true);
                              String initialsub = null;
                              String anysub = null;
                              String finalsub = null;
                              String firstTok = vst.nextToken();
                              String nextTok;
                              if (!firstTok.equals("*")) {
                                 initialsub = firstTok;
                                 nextTok = vst.nextToken();
                              }

                              if (vst.hasMoreTokens()) {
                                 nextTok = vst.nextToken();
                                 if (!vst.hasMoreTokens()) {
                                    finalsub = nextTok;
                                 } else {
                                    anysub = nextTok;
                                    String skipnext = vst.nextToken();
                                    if (vst.hasMoreTokens()) {
                                       finalsub = vst.nextToken();
                                    }
                                 }
                              }

                              nfilt = new Filter();
                              SubstringFilter sf = new SubstringFilter();
                              nfilt.setSubstrings(sf);
                              sf.setType(new OctetString(attr.getBytes()));
                              SubstringFilter_substrings ss = new SubstringFilter_substrings();
                              SubstringFilter_substrings_Seq ssc;
                              if (initialsub != null) {
                                 ssc = new SubstringFilter_substrings_Seq();
                                 initialsub = unescapeValue(initialsub);

                                 try {
                                    ssc.setInitial(new OctetString(initialsub.getBytes("UTF8")));
                                 } catch (UnsupportedEncodingException var28) {
                                    ssc.setInitial(new OctetString(initialsub.getBytes()));
                                 }

                                 ss.addElement(ssc);
                              }

                              if (anysub != null) {
                                 ssc = new SubstringFilter_substrings_Seq();
                                 anysub = unescapeValue(anysub);

                                 try {
                                    ssc.setAny(new OctetString(anysub.getBytes("UTF8")));
                                 } catch (UnsupportedEncodingException var27) {
                                    ssc.setAny(new OctetString(anysub.getBytes()));
                                 }

                                 ss.addElement(ssc);
                              }

                              if (finalsub != null) {
                                 ssc = new SubstringFilter_substrings_Seq();
                                 finalsub = unescapeValue(finalsub);

                                 try {
                                    ssc.setFinal_(new OctetString(finalsub.getBytes("UTF8")));
                                 } catch (UnsupportedEncodingException var26) {
                                    ssc.setFinal_(new OctetString(finalsub.getBytes()));
                                 }

                                 ss.addElement(ssc);
                              }

                              sf.setSubstrings(ss);
                           }
                        }

                        if (nfilt == null) {
                           throw new DirectoryException(2, Messages.getString("Bad_LDAP_Filter_14"));
                        }

                        if (settype == -1) {
                           fil = nfilt;
                        } else if (settype == 0) {
                           cfil.getAnd().addElement(nfilt);
                        } else if (settype == 1) {
                           cfil.getOr().addElement(nfilt);
                        } else if (settype == 2) {
                           if (cfil.getNot() != null) {
                              throw new DirectoryException(2, Messages.getString("Bad_LDAP_Filter_14"));
                           }

                           cfil.setNot(nfilt);
                        }
                     }
                  }

                  if (nup > 1) {
                     level = level - nup + 1;
                     parencount -= nup;
                     if (level < 1) {
                        return fil;
                     }

                     cfil = (Filter)branch.elementAt(level - 1);
                     settype = cfil.getSelector();
                     branch.setSize(level);
                  }
               }
            }
         }

         return fil;
      }
   }

   public static String filterToString(Filter currentFilter) {
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
               fs = filterToString((Filter)substrEnum.next());
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
               fs = filterToString((Filter)substrEnum.next());
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
            String nfs = filterToString(currentFilter.getNot());
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
            matchVal = new String(currentFilter.getEqualityMatch().getAssertionValue().toByteArray());
            filterString = new String(matchType + "=" + matchVal);
            break;
         case 4:
            matchType = new DirectoryString(currentFilter.getSubstrings().getType().toByteArray());
            String subfilter = new String();
            substrEnum = currentFilter.getSubstrings().getSubstrings().iterator();

            while(substrEnum.hasNext()) {
               SubstringFilter_substrings_Seq oneSubFilter = (SubstringFilter_substrings_Seq)substrEnum.next();
               if (oneSubFilter.getSelector() == 0) {
                  subfilter = subfilter.concat(new String(oneSubFilter.getInitial().toByteArray()) + "*");
               } else if (oneSubFilter.getSelector() == 1) {
                  if (subfilter.length() == 0) {
                     subfilter = subfilter.concat("*");
                  }

                  subfilter = subfilter.concat(new String(oneSubFilter.getAny().toByteArray()) + "*");
               } else if (oneSubFilter.getSelector() == 2) {
                  if (subfilter.length() == 0) {
                     subfilter = subfilter.concat("*");
                  }

                  subfilter = subfilter.concat(new String(oneSubFilter.getFinal_().toByteArray()));
               }
            }

            filterString = new String(matchType + "=" + subfilter);
            break;
         case 5:
            matchType = new DirectoryString(currentFilter.getGreaterOrEqual().getAttributeDesc().toByteArray());
            matchVal = new String(currentFilter.getGreaterOrEqual().getAssertionValue().toByteArray());
            filterString = new String(matchType + ">=" + matchVal);
            break;
         case 6:
            matchType = new DirectoryString(currentFilter.getLessOrEqual().getAttributeDesc().toByteArray());
            matchVal = new String(currentFilter.getLessOrEqual().getAssertionValue().toByteArray());
            filterString = new String(matchType + "<=" + matchVal);
            break;
         case 7:
            matchType = new DirectoryString(currentFilter.getPresent().toByteArray());
            filterString = new String(matchType + "=*");
            break;
         case 8:
            matchType = new DirectoryString(currentFilter.getApproxMatch().getAttributeDesc().toByteArray());
            matchVal = new String(currentFilter.getApproxMatch().getAssertionValue().toByteArray());
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
            fsb.append(new String(currentFilter.getExtensibleMatch().getMatchValue().toByteArray()));
            filterString = fsb.toString();
      }

      return filterString;
   }

   public static String unescapeValue(String value) {
      if (value == null) {
         return value;
      } else {
         int idx = value.indexOf(92);
         if (idx < 0) {
            return value;
         } else {
            int len = value.length();
            StringBuffer buf = new StringBuffer(len);

            for(int i = 0; i < len; ++i) {
               char ch = value.charAt(i);
               if (ch == '\\' && i < len - 2) {
                  char ch2 = value.charAt(i + 1);
                  char ch3 = value.charAt(i + 2);
                  i += 2;
                  if (ch2 == '2' && (ch3 == 'a' || ch3 == 'A')) {
                     buf.append('*');
                  } else if (ch2 == '2' && ch3 == '8') {
                     buf.append('(');
                  } else if (ch2 == '2' && ch3 == '9') {
                     buf.append(')');
                  } else if (ch2 != '5' || ch3 != 'c' && ch3 != 'C') {
                     if (ch2 == '0' && ch3 == '0') {
                        buf.append('\u0000');
                     } else {
                        buf.append(ch);
                        buf.append(ch2);
                        buf.append(ch3);
                     }
                  } else {
                     buf.append('\\');
                  }
               } else {
                  buf.append(ch);
               }
            }

            return buf.toString();
         }
      }
   }
}
