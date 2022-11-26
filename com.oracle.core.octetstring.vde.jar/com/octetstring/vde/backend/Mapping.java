package com.octetstring.vde.backend;

import com.asn1c.core.OctetString;
import com.octetstring.ldapv3.Filter;
import com.octetstring.ldapv3.Filter_and;
import com.octetstring.ldapv3.Filter_or;
import com.octetstring.nls.Messages;
import com.octetstring.vde.Entry;
import com.octetstring.vde.schema.SchemaChecker;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.DNUtility;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.Logger;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.oro.text.perl.Perl5Util;
import org.apache.oro.text.regex.MatchResult;

public class Mapping {
   private static int MAPPING_NONE = 0;
   private static int MAPPING_RENAME = 1;
   private static int MAPPING_COPY = 2;
   private static int MAPPING_RAPPEND = 3;
   private static int MAPPING_CAPPEND = 4;
   private static int MAPPING_ADDVALUE = 5;
   private static int MAPPING_DELVALUE = 6;
   private static int MAPPING_REPVALUE = 7;
   private static int MAPPING_SETDN = 8;
   private static int MAPPING_SPLITVALS = 9;
   private static int MAPPING_HIDEALL = 10;
   private static final DirectoryString ATTR_DN = new DirectoryString("dn");
   private static OctetString UNMATCHABLE_TYPE = new OctetString("_unmatchable".getBytes());
   boolean reverse = false;
   int type;
   DirectoryString attr;
   DirectoryString origAttr;
   DirectoryString dnendswith;
   String setdn;
   Vector values;
   DirectoryString haveattr;
   Syntax haveattrval;
   String separator;

   public Mapping(String mappingString) throws MappingException {
      this.type = MAPPING_NONE;
      this.attr = null;
      this.origAttr = null;
      this.dnendswith = null;
      this.setdn = null;
      this.values = null;
      this.haveattr = null;
      this.haveattrval = null;
      this.separator = null;
      StringTokenizer st = new StringTokenizer(mappingString, " ");

      while(true) {
         while(st.hasMoreTokens()) {
            String tok = st.nextToken();
            if (tok.equalsIgnoreCase("haveattr")) {
               if (!st.hasMoreTokens()) {
                  throw new MappingException("haveattr requires attribute type as argument");
               }

               this.haveattr = new DirectoryString(st.nextToken());
            } else {
               StringTokenizer rst;
               if (tok.equalsIgnoreCase("haveattrval")) {
                  rst = new StringTokenizer(st.nextToken(), "=");
                  if (rst.countTokens() < 2) {
                     throw new MappingException("haveattrval requires an argument specifying attribute=value");
                  }

                  this.haveattr = new DirectoryString(rst.nextToken());
                  this.haveattrval = SchemaChecker.getInstance().getAttributeType(this.haveattr).getSyntaxInstance();

                  try {
                     this.haveattrval.setValue(rst.nextToken().getBytes("UTF8"));
                  } catch (UnsupportedEncodingException var7) {
                     this.haveattrval.setValue(rst.nextToken().getBytes());
                  }
               } else if (tok.equalsIgnoreCase("matchdn")) {
                  if (!st.hasMoreTokens()) {
                     throw new MappingException("matchdn requires a distinguished name argument");
                  }

                  this.dnendswith = new DirectoryString(st.nextToken());
               } else if (tok.equalsIgnoreCase("reverse")) {
                  this.reverse = true;
               } else {
                  String attrval;
                  StringTokenizer rst;
                  if (tok.equalsIgnoreCase("rename")) {
                     this.type = MAPPING_RENAME;
                     if (!st.hasMoreTokens()) {
                        throw new MappingException("rename requires newattr=oldattr as argument");
                     }

                     attrval = st.nextToken();
                     rst = new StringTokenizer(attrval, "=");
                     if (rst.countTokens() < 2) {
                        throw new MappingException("rename requires newattr=oldattr as argument");
                     }

                     this.attr = new DirectoryString(rst.nextToken());
                     this.origAttr = new DirectoryString(rst.nextToken());
                  } else if (tok.equalsIgnoreCase("copy")) {
                     this.type = MAPPING_COPY;
                     if (!st.hasMoreTokens()) {
                        throw new MappingException("copy requires newattr=oldattr as argument");
                     }

                     attrval = st.nextToken();
                     rst = new StringTokenizer(attrval, "=");
                     if (rst.countTokens() < 2) {
                        throw new MappingException("copy requires newattr=oldattr as argument");
                     }

                     this.attr = new DirectoryString(rst.nextToken());
                     this.origAttr = new DirectoryString(rst.nextToken());
                  } else if (tok.equalsIgnoreCase("cappend")) {
                     this.type = MAPPING_CAPPEND;
                     if (!st.hasMoreTokens()) {
                        throw new MappingException("cappend requires newattr=oldattr as argument");
                     }

                     attrval = st.nextToken();
                     rst = new StringTokenizer(attrval, "=");
                     if (rst.countTokens() < 2) {
                        throw new MappingException("cappend requires newattr=oldattr as argument");
                     }

                     this.attr = new DirectoryString(rst.nextToken());
                     this.origAttr = new DirectoryString(rst.nextToken());
                  } else if (tok.equalsIgnoreCase("rappend")) {
                     this.type = MAPPING_RAPPEND;
                     if (!st.hasMoreTokens()) {
                        throw new MappingException("rappend requires newattr=oldattr as argument");
                     }

                     attrval = st.nextToken();
                     rst = new StringTokenizer(attrval, "=");
                     if (rst.countTokens() < 2) {
                        throw new MappingException("rappend requires newattr=oldattr as argument");
                     }

                     this.attr = new DirectoryString(rst.nextToken());
                     this.origAttr = new DirectoryString(rst.nextToken());
                  } else {
                     StringBuffer sb;
                     if (tok.equalsIgnoreCase("addvalue")) {
                        this.type = MAPPING_ADDVALUE;
                        if (!st.hasMoreTokens()) {
                           throw new MappingException("addvalue requires attribute=value as argument");
                        }

                        attrval = st.nextToken();
                        rst = new StringTokenizer(attrval, "=");
                        if (rst.countTokens() < 2) {
                           throw new MappingException("addvalue requires attribute=value as argument");
                        }

                        this.attr = new DirectoryString(rst.nextToken());
                        this.values = new Vector();
                        sb = new StringBuffer();
                        sb.append(rst.nextToken());

                        while(st.hasMoreTokens()) {
                           sb.append(" ").append(st.nextToken());
                        }

                        this.values.addElement(sb.toString());
                     } else if (tok.equalsIgnoreCase("deletevalue")) {
                        this.type = MAPPING_DELVALUE;
                        if (!st.hasMoreTokens()) {
                           throw new MappingException("deletevalue requires attribute=value as argument");
                        }

                        attrval = st.nextToken();
                        rst = new StringTokenizer(attrval, "=");
                        if (rst.countTokens() < 2) {
                           throw new MappingException("deletevalue requires attribute=value as argument");
                        }

                        this.attr = new DirectoryString(rst.nextToken());
                        this.values = new Vector();
                        sb = new StringBuffer();
                        sb.append(rst.nextToken());

                        while(st.hasMoreTokens()) {
                           sb.append(" ").append(st.nextToken());
                        }

                        this.values.addElement(sb.toString());
                     } else if (tok.equalsIgnoreCase("replacevalue")) {
                        this.type = MAPPING_REPVALUE;
                        if (!st.hasMoreTokens()) {
                           throw new MappingException("replacevalue requires attribute=value as argument");
                        }

                        attrval = st.nextToken();
                        rst = new StringTokenizer(attrval, "=");
                        if (rst.countTokens() < 2) {
                           throw new MappingException("replacevalue requires attribute=value as argument");
                        }

                        this.attr = new DirectoryString(rst.nextToken());
                        this.values = new Vector();
                        sb = new StringBuffer();
                        sb.append(rst.nextToken());

                        while(st.hasMoreTokens()) {
                           sb.append(" ").append(st.nextToken());
                        }

                        this.values.addElement(sb.toString());
                     } else if (!tok.equalsIgnoreCase("setdn")) {
                        if (tok.equalsIgnoreCase("splitvals")) {
                           this.type = MAPPING_SPLITVALS;
                           if (!st.hasMoreTokens()) {
                              throw new MappingException("splitvals requires attribute=delimeter as argument");
                           }

                           if (st.countTokens() >= 2) {
                              this.attr = new DirectoryString(st.nextToken());
                              this.separator = st.nextToken();
                           } else {
                              rst = new StringTokenizer(st.nextToken(), "=", true);
                              if (rst.countTokens() < 3) {
                                 throw new MappingException("splitvals requires attribute=delimeter as argument");
                              }

                              this.attr = new DirectoryString(rst.nextToken());
                              String junk = rst.nextToken();
                              this.separator = rst.nextToken();
                           }

                           if (this.separator.equals("\\s")) {
                              this.separator = new String(" ");
                           }
                        } else {
                           if (!tok.equalsIgnoreCase("hideall")) {
                              throw new MappingException("unknown operation - " + tok);
                           }

                           this.type = MAPPING_HIDEALL;
                        }
                     } else {
                        this.type = MAPPING_SETDN;
                        if (!st.hasMoreTokens()) {
                           throw new MappingException("setdn requires a distinguished name constructor as an argument");
                        }

                        StringBuffer sb = new StringBuffer();
                        sb.append(st.nextToken());

                        while(st.hasMoreTokens()) {
                           sb.append(" ").append(st.nextToken());
                        }

                        this.setdn = sb.toString();
                     }
                  }
               }
            }
         }

         return;
      }
   }

   public Entry transform(Entry entry) {
      return this.transform(entry, false);
   }

   public Entry transform(Entry entry, boolean reversed) {
      if (this.reverse != reversed) {
         return entry;
      } else if (this.haveattr != null && entry.get(this.haveattr) == null) {
         return entry;
      } else {
         Vector curdncomps;
         if (this.haveattrval != null) {
            curdncomps = entry.get(this.haveattr);
            if (curdncomps == null) {
               return entry;
            }

            if (!curdncomps.contains(this.haveattrval)) {
               return entry;
            }
         }

         if (this.dnendswith != null && !entry.getName().endsWith(this.dnendswith)) {
            return entry;
         } else if (this.type == MAPPING_HIDEALL) {
            return null;
         } else if (this.type == MAPPING_RENAME) {
            curdncomps = entry.get(this.origAttr);
            if (curdncomps != null) {
               entry.remove(this.origAttr);
               entry.put(this.attr, curdncomps);
            }

            return entry;
         } else if (this.type == MAPPING_COPY) {
            curdncomps = entry.get(this.origAttr);
            if (curdncomps != null) {
               entry.put(this.attr, curdncomps);
            }

            return entry;
         } else {
            Vector vars;
            if (this.type == MAPPING_RAPPEND) {
               curdncomps = entry.get(this.origAttr);
               vars = entry.get(this.attr);
               if (curdncomps != null) {
                  if (vars == null) {
                     entry.put(this.attr, curdncomps);
                  } else {
                     vars.addAll(curdncomps);
                  }

                  entry.remove(this.origAttr);
               }

               return entry;
            } else if (this.type == MAPPING_CAPPEND) {
               curdncomps = entry.get(this.origAttr);
               vars = entry.get(this.attr);
               if (curdncomps != null) {
                  if (vars == null) {
                     entry.put(this.attr, curdncomps);
                  } else {
                     vars.addAll(curdncomps);
                  }
               }

               return entry;
            } else {
               Vector tmpvv;
               String regex;
               MatchResult mr;
               Enumeration vals;
               String val;
               Vector newvals;
               Perl5Util p5u;
               Enumeration ve;
               boolean invar;
               StringBuffer tmpstring;
               String one;
               Vector tmpvv;
               String regex;
               if (this.type == MAPPING_ADDVALUE) {
                  curdncomps = entry.get(this.attr);
                  if (curdncomps == null) {
                     curdncomps = new Vector();
                  }

                  vals = this.values.elements();

                  while(vals.hasMoreElements()) {
                     val = (String)vals.nextElement();
                     newvals = new Vector();
                     p5u = new Perl5Util();
                     p5u.split(newvals, "/(%)/", val);
                     ve = newvals.elements();
                     invar = false;
                     tmpstring = new StringBuffer();

                     while(ve.hasMoreElements()) {
                        one = (String)ve.nextElement();
                        if (one.equals("%")) {
                           invar = !invar;
                        } else if (!invar) {
                           tmpstring.append(one);
                        } else {
                           tmpvv = new Vector();
                           p5u.split(tmpvv, "/:/", one);
                           tmpvv = entry.get(new DirectoryString((String)tmpvv.elementAt(0)));
                           if (tmpvv != null && !tmpvv.isEmpty()) {
                              regex = new String(((Syntax)tmpvv.elementAt(0)).getValue());
                              if (tmpvv.size() > 1) {
                                 regex = (String)tmpvv.elementAt(1);
                                 if (p5u.match(regex, regex)) {
                                    mr = p5u.getMatch();
                                    tmpstring.append(mr.toString());
                                 }
                              } else {
                                 tmpstring.append(regex);
                              }
                           }
                        }
                     }

                     if (tmpstring.length() != 0) {
                        curdncomps.addElement(new DirectoryString(tmpstring.toString()));
                     }
                  }

                  if (!curdncomps.isEmpty()) {
                     entry.put(this.attr, curdncomps);
                  }

                  return entry;
               } else if (this.type == MAPPING_DELVALUE) {
                  curdncomps = entry.get(this.attr);
                  if (curdncomps == null) {
                     return entry;
                  } else {
                     vals = this.values.elements();

                     while(vals.hasMoreElements()) {
                        val = (String)vals.nextElement();
                        newvals = new Vector();
                        p5u = new Perl5Util();
                        p5u.split(newvals, "/(%)/", val);
                        ve = newvals.elements();
                        invar = false;
                        tmpstring = new StringBuffer();

                        while(ve.hasMoreElements()) {
                           one = (String)ve.nextElement();
                           if (one.equals("%")) {
                              invar = !invar;
                           } else if (!invar) {
                              tmpstring.append(one);
                           } else {
                              tmpvv = new Vector();
                              p5u.split(tmpvv, "/:/", one);
                              tmpvv = entry.get(new DirectoryString((String)tmpvv.elementAt(0)));
                              if (tmpvv != null && !tmpvv.isEmpty()) {
                                 regex = new String(((Syntax)tmpvv.elementAt(0)).getValue());
                                 if (tmpvv.size() > 1) {
                                    regex = (String)tmpvv.elementAt(1);
                                    if (p5u.match(regex, regex)) {
                                       mr = p5u.getMatch();
                                       tmpstring.append(mr.toString());
                                    }
                                 } else {
                                    tmpstring.append(regex);
                                 }
                              }
                           }
                        }

                        curdncomps.removeElement(new DirectoryString(tmpstring.toString()));
                     }

                     return entry;
                  }
               } else if (this.type == MAPPING_REPVALUE) {
                  curdncomps = new Vector();
                  vals = this.values.elements();

                  while(vals.hasMoreElements()) {
                     val = (String)vals.nextElement();
                     newvals = new Vector();
                     p5u = new Perl5Util();
                     p5u.split(newvals, "/(%)/", val);
                     ve = newvals.elements();
                     invar = false;
                     tmpstring = new StringBuffer();

                     while(ve.hasMoreElements()) {
                        one = (String)ve.nextElement();
                        if (one.equals("%")) {
                           invar = !invar;
                        } else if (!invar) {
                           tmpstring.append(one);
                        } else {
                           tmpvv = new Vector();
                           p5u.split(tmpvv, "/:/", one);
                           tmpvv = entry.get(new DirectoryString((String)tmpvv.elementAt(0)));
                           if (tmpvv != null && !tmpvv.isEmpty()) {
                              regex = new String(((Syntax)tmpvv.elementAt(0)).getValue());
                              if (tmpvv.size() > 1) {
                                 regex = (String)tmpvv.elementAt(1);
                                 if (p5u.match(regex, regex)) {
                                    mr = p5u.getMatch();
                                    tmpstring.append(mr.toString());
                                 }
                              } else {
                                 tmpstring.append(regex);
                              }
                           }
                        }
                     }

                     curdncomps.addElement(new DirectoryString(tmpstring.toString()));
                  }

                  entry.put(this.attr, curdncomps);
                  return entry;
               } else {
                  Perl5Util p5u;
                  String one;
                  if (this.type == MAPPING_SPLITVALS) {
                     curdncomps = entry.get(this.attr);
                     if (curdncomps == null) {
                        return entry;
                     } else {
                        Syntax val = (Syntax)curdncomps.elementAt(0);
                        p5u = new Perl5Util();
                        newvals = new Vector();
                        p5u.split(newvals, "/" + this.separator + "/", val.toString());
                        Vector realvals = new Vector();
                        ve = newvals.elements();

                        while(ve.hasMoreElements()) {
                           one = (String)ve.nextElement();
                           if (one != null && one.length() != 0) {
                              realvals.addElement(new DirectoryString(one));
                           }
                        }

                        entry.put(this.attr, realvals);
                        return entry;
                     }
                  } else if (this.type != MAPPING_SETDN) {
                     return entry;
                  } else {
                     curdncomps = DNUtility.getInstance().explodeDN(entry.getName());
                     vars = new Vector();
                     p5u = new Perl5Util();
                     p5u.split(vars, "/(%)/", this.setdn);
                     Enumeration varenum = vars.elements();
                     boolean invar = false;
                     StringBuffer tmpstring = new StringBuffer();

                     while(varenum.hasMoreElements()) {
                        one = (String)varenum.nextElement();
                        if (one.equals("%")) {
                           invar = !invar;
                        } else if (!invar) {
                           tmpstring.append(one);
                        } else {
                           Vector components = new Vector();
                           p5u.split(components, "/:/", one);
                           DirectoryString myattr = new DirectoryString((String)components.elementAt(0));
                           if (myattr.equals(ATTR_DN)) {
                              int numcom = curdncomps.size() - 1;
                              if (components.size() > 1) {
                                 numcom = Integer.parseInt((String)components.elementAt(1));
                                 if (numcom > curdncomps.size()) {
                                    numcom = curdncomps.size() - 1;
                                 }
                              }

                              for(int ct = curdncomps.size() - numcom; ct < curdncomps.size(); ++ct) {
                                 tmpstring.append(curdncomps.elementAt(ct));
                                 if (ct < curdncomps.size() - 1) {
                                    tmpstring.append(",");
                                 }
                              }
                           } else {
                              tmpvv = entry.get(myattr);
                              if (tmpvv != null && !tmpvv.isEmpty()) {
                                 String tmpval = new String(((Syntax)tmpvv.elementAt(0)).getValue());
                                 if (components.size() > 1) {
                                    regex = (String)components.elementAt(1);
                                    if (p5u.match(regex, tmpval)) {
                                       MatchResult mr = p5u.getMatch();
                                       tmpstring.append(mr.toString());
                                    }
                                 } else {
                                    tmpstring.append(tmpval);
                                 }
                              }
                           }
                        }

                        try {
                           entry.setName(new DirectoryString(tmpstring.toString()));
                        } catch (InvalidDNException var17) {
                           Logger.getInstance().log(0, this, Messages.getString("Invalid_DN_created_by_mapping_rules___44") + tmpstring);
                        }
                     }

                     return entry;
                  }
               }
            }
         }
      }
   }

   public Filter updateFilter(Filter currentFilter) {
      if (currentFilter == null) {
         return currentFilter;
      } else {
         DirectoryString matchType;
         Filter tmpFilter;
         Filter_or tmpor;
         Filter sFilter;
         switch (currentFilter.getSelector()) {
            case 0:
               Filter_and newand = new Filter_and();
               Iterator andEnum = currentFilter.getAnd().iterator();

               while(andEnum.hasNext()) {
                  sFilter = new Filter((Filter)andEnum.next());
                  sFilter = this.updateFilter(sFilter);
                  if (sFilter != null) {
                     newand.addElement(sFilter);
                  }
               }

               currentFilter.setAnd(newand);
               break;
            case 1:
               tmpor = new Filter_or();
               Iterator orEnum = currentFilter.getOr().iterator();

               while(orEnum.hasNext()) {
                  Filter oneor = new Filter((Filter)orEnum.next());
                  oneor = this.updateFilter(oneor);
                  if (oneor != null) {
                     tmpor.addElement(oneor);
                  }
               }

               currentFilter.setOr(tmpor);
            case 2:
            default:
               break;
            case 3:
               matchType = new DirectoryString(currentFilter.getEqualityMatch().getAttributeDesc().toByteArray());
               if (this.attr != null && this.attr.equals(matchType)) {
                  if (this.type == MAPPING_RENAME || this.type == MAPPING_COPY) {
                     currentFilter.getEqualityMatch().setAttributeDesc(new OctetString(this.origAttr.getBytes()));
                  }

                  if (this.type == MAPPING_RAPPEND || this.type == MAPPING_CAPPEND) {
                     tmpFilter = new Filter();
                     tmpor = new Filter_or();
                     tmpFilter.setOr(tmpor);
                     tmpor.addElement(currentFilter);
                     sFilter = new Filter(currentFilter);
                     sFilter.getEqualityMatch().setAttributeDesc(new OctetString(this.origAttr.getBytes()));
                     tmpor.addElement(sFilter);
                     currentFilter = tmpFilter;
                  }
               } else if (this.origAttr != null && this.origAttr.equals(matchType) && (this.type == MAPPING_RENAME || this.type == MAPPING_RAPPEND)) {
                  currentFilter.getEqualityMatch().setAttributeDesc(UNMATCHABLE_TYPE);
               }
               break;
            case 4:
               matchType = new DirectoryString(currentFilter.getSubstrings().getType().toByteArray());
               if (this.attr != null && this.attr.equals(matchType)) {
                  if (this.type == MAPPING_RENAME || this.type == MAPPING_COPY) {
                     currentFilter.getSubstrings().setType(new OctetString(this.origAttr.getBytes()));
                  }

                  if (this.type == MAPPING_RAPPEND || this.type == MAPPING_CAPPEND) {
                     tmpFilter = new Filter();
                     tmpor = new Filter_or();
                     tmpFilter.setOr(tmpor);
                     tmpor.addElement(currentFilter);
                     sFilter = new Filter(currentFilter);
                     sFilter.getSubstrings().setType(new OctetString(this.origAttr.getBytes()));
                     tmpor.addElement(sFilter);
                     currentFilter = tmpFilter;
                  }
               } else if (this.origAttr != null && this.origAttr.equals(matchType) && (this.type == MAPPING_RENAME || this.type == MAPPING_RAPPEND)) {
                  currentFilter.getSubstrings().setType(UNMATCHABLE_TYPE);
               }
               break;
            case 5:
               matchType = new DirectoryString(currentFilter.getGreaterOrEqual().getAttributeDesc().toByteArray());
               if (this.attr != null && this.attr.equals(matchType)) {
                  if (this.type == MAPPING_RENAME || this.type == MAPPING_COPY) {
                     currentFilter.getGreaterOrEqual().setAttributeDesc(new OctetString(this.origAttr.getBytes()));
                  }

                  if (this.type == MAPPING_RAPPEND || this.type == MAPPING_CAPPEND) {
                     tmpFilter = new Filter();
                     tmpor = new Filter_or();
                     tmpFilter.setOr(tmpor);
                     tmpor.addElement(currentFilter);
                     sFilter = new Filter(currentFilter);
                     sFilter.getGreaterOrEqual().setAttributeDesc(new OctetString(this.origAttr.getBytes()));
                     tmpor.addElement(sFilter);
                     currentFilter = tmpFilter;
                  }
               } else if (this.origAttr != null && this.origAttr.equals(matchType) && (this.type == MAPPING_RENAME || this.type == MAPPING_RAPPEND)) {
                  currentFilter.getGreaterOrEqual().setAttributeDesc(UNMATCHABLE_TYPE);
               }
               break;
            case 6:
               matchType = new DirectoryString(currentFilter.getLessOrEqual().getAttributeDesc().toByteArray());
               if (this.attr != null && this.attr.equals(matchType)) {
                  if (this.type == MAPPING_RENAME || this.type == MAPPING_COPY) {
                     currentFilter.getLessOrEqual().setAttributeDesc(new OctetString(this.origAttr.getBytes()));
                  }

                  if (this.type == MAPPING_RAPPEND || this.type == MAPPING_CAPPEND) {
                     tmpFilter = new Filter();
                     tmpor = new Filter_or();
                     tmpFilter.setOr(tmpor);
                     tmpor.addElement(currentFilter);
                     sFilter = new Filter(currentFilter);
                     sFilter.getLessOrEqual().setAttributeDesc(new OctetString(this.origAttr.getBytes()));
                     tmpor.addElement(sFilter);
                     currentFilter = tmpFilter;
                  }
               } else if (this.origAttr != null && this.origAttr.equals(matchType) && (this.type == MAPPING_RENAME || this.type == MAPPING_RAPPEND)) {
                  currentFilter.getLessOrEqual().setAttributeDesc(UNMATCHABLE_TYPE);
               }
               break;
            case 7:
               matchType = new DirectoryString(currentFilter.getPresent().toByteArray());
               if (this.attr != null && this.attr.equals(matchType)) {
                  if (this.type == MAPPING_RENAME || this.type == MAPPING_COPY) {
                     currentFilter.setPresent(new OctetString(this.origAttr.getBytes()));
                  }

                  if (this.type == MAPPING_RAPPEND || this.type == MAPPING_CAPPEND) {
                     tmpFilter = new Filter();
                     tmpor = new Filter_or();
                     tmpFilter.setOr(tmpor);
                     tmpor.addElement(currentFilter);
                     sFilter = new Filter(currentFilter);
                     sFilter.setPresent(new OctetString(this.origAttr.getBytes()));
                     tmpor.addElement(sFilter);
                     currentFilter = tmpFilter;
                  }
               } else if (this.origAttr != null && this.origAttr.equals(matchType) && (this.type == MAPPING_RENAME || this.type == MAPPING_RAPPEND)) {
                  currentFilter.setPresent(UNMATCHABLE_TYPE);
               }
         }

         return currentFilter;
      }
   }
}
