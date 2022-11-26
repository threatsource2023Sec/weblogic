package com.octetstring.vde.backend.standard;

import com.octetstring.vde.Entry;
import com.octetstring.vde.EntryChange;
import com.octetstring.vde.backend.Backend;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.syntax.IntegerSyntax;
import com.octetstring.vde.syntax.Syntax;
import com.octetstring.vde.util.Logger;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;
import org.apache.oro.text.perl.Perl5Util;

public class Index {
   private Map itPres = null;
   private Map itExact = null;
   private Map itOrder = null;
   private Map itSub = null;
   private Vector presenceIndices = null;
   private Vector exactIndices = null;
   private Vector orderingIndices = null;
   private Vector substringIndices = null;
   private Backend backEnd = null;
   public static final int PRESENCE = 0;
   public static final int EXACT = 1;
   public static final int ORDER_LESS = 2;
   public static final int ORDER_GREATER = 3;
   public static final int SUBSTR_INITIAL = 4;
   public static final int SUBSTR_ANY = 5;
   public static final int SUBSTR_FINAL = 6;
   private static final KeyPtr ALL_STRING = new KeyPtr("*".getBytes());
   private static final String NOENCODING = "";

   public Index(Backend backEnd, Vector presenceIndices, Vector exactIndices, Vector orderingIndices, Vector substringIndices) {
      this.itPres = Collections.synchronizedMap(new HashMap());
      this.itExact = Collections.synchronizedMap(new HashMap());
      this.itOrder = Collections.synchronizedMap(new HashMap());
      this.itSub = Collections.synchronizedMap(new HashMap());
      this.presenceIndices = presenceIndices;
      this.exactIndices = exactIndices;
      this.orderingIndices = orderingIndices;
      this.substringIndices = substringIndices;
      this.backEnd = backEnd;
      Enumeration presMatchEnum = presenceIndices.elements();

      while(presMatchEnum.hasMoreElements()) {
         this.itPres.put((DirectoryString)presMatchEnum.nextElement(), new KeyEidList(ALL_STRING));
      }

      Enumeration equalsMatchEnum = exactIndices.elements();

      while(equalsMatchEnum.hasMoreElements()) {
         this.itExact.put((DirectoryString)equalsMatchEnum.nextElement(), Collections.synchronizedMap(new HashMap()));
      }

      Enumeration orderMatchEnum = orderingIndices.elements();

      while(orderMatchEnum.hasMoreElements()) {
         this.itOrder.put((DirectoryString)orderMatchEnum.nextElement(), Collections.synchronizedSortedMap(new TreeMap()));
      }

      Enumeration subMatchEnum = substringIndices.elements();

      while(subMatchEnum.hasMoreElements()) {
         this.itSub.put((DirectoryString)subMatchEnum.nextElement(), Collections.synchronizedSortedMap(new TreeMap()));
      }

   }

   private int[] concatEids(int[] orig, int[] neweids) {
      int[] full = null;
      int[] full;
      if (orig == null) {
         full = new int[neweids.length];
         System.arraycopy(neweids, 0, full, 0, neweids.length);
         return full;
      } else {
         full = new int[orig.length + neweids.length];
         System.arraycopy(orig, 0, full, 0, orig.length);
         System.arraycopy(neweids, 0, full, orig.length, neweids.length);
         return full;
      }
   }

   private int[] concatEids(int[] orig, int neweid) {
      int[] tmp = new int[]{neweid};
      return this.concatEids(orig, tmp);
   }

   public void delete(Entry entry) {
      int entryId = entry.getID();
      Enumeration keys = entry.keys();

      while(keys.hasMoreElements()) {
         DirectoryString attr = (DirectoryString)keys.nextElement();
         String at = attr.normalize();
         int index = at.indexOf(59);
         if (index >= 0) {
            attr = new DirectoryString(at.substring(0, index));
         }

         Map exactAI = (Map)this.itExact.get(attr);
         Map orderAI = (Map)this.itOrder.get(attr);
         Map substrAI = (Map)this.itSub.get(attr);
         Vector vals = entry.get(attr);
         Enumeration valEnum = vals.elements();

         while(valEnum.hasMoreElements()) {
            Syntax aVal = (Syntax)valEnum.nextElement();
            String normVal = aVal.normalize();
            IntegerSyntax valHash = new IntegerSyntax(normVal.hashCode());
            KeyEidList kel;
            if (exactAI != null) {
               kel = (KeyEidList)exactAI.get(new KeyEidList(new KeyPtr(valHash.toString().getBytes())));
               if (kel != null) {
                  kel.removeEid(entryId);
                  if (kel.size() == 0) {
                     exactAI.remove(kel);
                  }
               }
            }

            if (orderAI != null) {
               kel = (KeyEidList)orderAI.get(new KeyEidList(new KeyPtr(this.getUTFBytes(normVal))));
               if (kel != null) {
                  kel.removeEid(entryId);
                  if (kel.size() == 0) {
                     orderAI.remove(kel);
                  }
               }
            }

            if (substrAI != null) {
               kel = (KeyEidList)substrAI.get(new KeyEidList(new KeyPtr(this.getUTFBytes(aVal.reverse().normalize()))));
               if (kel != null) {
                  kel.removeEid(entryId);
                  if (kel.size() == 0) {
                     substrAI.remove(kel);
                  }
               }
            }
         }

         KeyEidList kel = (KeyEidList)this.itPres.get(attr);
         if (kel != null) {
            kel.removeEid(entryId);
         }
      }

   }

   public int[] getCandidates(int type, DirectoryString cisAttr, Syntax synValue) {
      int[] results = null;
      String val;
      SortedMap eids;
      SortedMap valHash;
      if (type != 3 && type != 2) {
         if (type == 0) {
            if (this.itPres.containsKey(cisAttr)) {
               KeyEidList kel = (KeyEidList)this.itPres.get(cisAttr);
               return kel.getEids();
            } else {
               return new int[0];
            }
         } else {
            if (type == 1) {
               val = synValue.normalize();
               if (type == 1 && this.itOrder.containsKey(cisAttr)) {
                  Map attrIndex = (Map)this.itOrder.get(cisAttr);
                  KeyEidList kel = (KeyEidList)attrIndex.get(new KeyEidList(new KeyPtr(this.getUTFBytes(val))));
                  if (kel == null) {
                     return results;
                  }

                  return kel.getEids();
               }

               if (this.itExact.containsKey(cisAttr)) {
                  eids = null;
                  valHash = null;
                  KeyEidList kel = null;
                  if (type == 1 && eids == null) {
                     Map attrIndex = (Map)this.itExact.get(cisAttr);
                     IntegerSyntax valHash = new IntegerSyntax(val.hashCode());
                     kel = (KeyEidList)attrIndex.get(valHash.toString());
                     if (kel == null) {
                        return results;
                     }
                  }

                  int[] eids = kel.getEids();

                  for(int i = 0; i < eids.length; ++i) {
                     int currentEid = eids[i];
                     Entry current = this.backEnd.getByID(new Integer(currentEid));
                     if (current.get(cisAttr).contains(synValue)) {
                        results = this.concatEids(results, currentEid);
                     }
                  }
               }
            }

            return results != null ? results : new int[0];
         }
      } else {
         if (this.itOrder.containsKey(cisAttr)) {
            val = synValue.normalize();
            eids = (SortedMap)this.itOrder.get(cisAttr);
            valHash = null;
            if (type == 3) {
               valHash = eids.tailMap(new KeyEidList(new KeyPtr(this.getUTFBytes(val))));
            } else {
               valHash = eids.headMap(new KeyEidList(new KeyPtr(this.getUTFBytes(val))));
            }

            Collection matchEnts = valHash.keySet();
            int[] eids = null;
            boolean doit = true;

            while(doit) {
               try {
                  KeyEidList kel;
                  for(Iterator enumEnts = matchEnts.iterator(); enumEnts.hasNext(); eids = this.concatEids(eids, kel.getEids())) {
                     kel = (KeyEidList)enumEnts.next();
                  }

                  doit = false;
               } catch (ConcurrentModificationException var13) {
               }
            }

            results = eids;
         }

         return results != null ? results : new int[0];
      }
   }

   public int[] getCandidates(int type, DirectoryString cisAttr, Syntax synValue, String regex) {
      int[] results = null;
      if (type == 4 || type == 5 || type == 6) {
         Perl5Util p5u = null;
         if (regex != null) {
            p5u = new Perl5Util();
         }

         SortedMap forwardIndex = null;
         SortedMap reverseIndex = null;
         forwardIndex = (SortedMap)this.itOrder.get(cisAttr);
         reverseIndex = (SortedMap)this.itSub.get(cisAttr);
         int[] tmpMatches;
         byte[] valmatch;
         SortedMap tailMap;
         Set tail;
         boolean doit;
         Iterator iter;
         KeyEidList kel;
         int[] eids;
         if (type == 4 && forwardIndex != null) {
            tmpMatches = null;
            valmatch = this.getUTFBytes(synValue.normalize());
            tailMap = forwardIndex.tailMap(new KeyEidList(new KeyPtr(valmatch)));
            tail = tailMap.keySet();
            doit = true;

            while(doit) {
               try {
                  iter = tail.iterator();

                  while(iter.hasNext()) {
                     kel = (KeyEidList)iter.next();
                     if (!kel.getKeyptr().startsWith(valmatch)) {
                        break;
                     }

                     if (regex != null) {
                        if (p5u.match(regex, kel.getKeyptr().toString())) {
                           eids = kel.getEids();
                           tmpMatches = this.concatEids(tmpMatches, eids);
                        }
                     } else {
                        eids = kel.getEids();
                        tmpMatches = this.concatEids(tmpMatches, eids);
                     }
                  }

                  results = tmpMatches;
                  doit = false;
               } catch (ConcurrentModificationException var20) {
                  tmpMatches = null;
               }
            }
         } else if (type == 6 && reverseIndex != null) {
            tmpMatches = null;
            valmatch = this.getUTFBytes(synValue.reverse().normalize());
            tailMap = reverseIndex.tailMap(new KeyEidList(new KeyPtr(valmatch)));
            tail = tailMap.keySet();
            doit = true;

            while(doit) {
               try {
                  iter = tail.iterator();

                  while(iter.hasNext()) {
                     kel = (KeyEidList)iter.next();
                     if (!kel.getKeyptr().startsWith(valmatch)) {
                        break;
                     }

                     if (regex != null) {
                        StringBuffer tmpsb = new StringBuffer(kel.getKeyptr().toString());
                        if (p5u.match(regex, tmpsb.reverse().toString())) {
                           int[] eids = kel.getEids();
                           tmpMatches = this.concatEids(tmpMatches, eids);
                        }
                     } else {
                        eids = kel.getEids();
                        tmpMatches = this.concatEids(tmpMatches, eids);
                     }
                  }

                  results = tmpMatches;
                  doit = false;
               } catch (ConcurrentModificationException var19) {
                  tmpMatches = null;
               }
            }
         } else if ((type == 5 && forwardIndex != null || type == 6 && forwardIndex != null && reverseIndex == null) && regex != null) {
            tmpMatches = null;
            Set tail = forwardIndex.keySet();
            boolean doit = true;

            while(doit) {
               try {
                  Iterator iter = tail.iterator();

                  while(iter.hasNext()) {
                     KeyEidList kel = (KeyEidList)iter.next();
                     if (p5u.match(regex, kel.getKeyptr().toString())) {
                        int[] eids = kel.getEids();
                        tmpMatches = this.concatEids(tmpMatches, eids);
                     }
                  }

                  results = tmpMatches;
                  doit = false;
               } catch (ConcurrentModificationException var18) {
                  tmpMatches = null;
               }
            }
         }
      }

      return results;
   }

   public void index(Entry entry) {
      int entryId = entry.getID();
      boolean dumpLog = Logger.getInstance().isLogable(11);
      Enumeration indexes = this.presenceIndices.elements();

      DirectoryString indexKey;
      Vector attrValVec;
      while(indexes.hasMoreElements()) {
         indexKey = (DirectoryString)indexes.nextElement();
         attrValVec = entry.get(indexKey);
         if (attrValVec != null) {
            if (dumpLog) {
               Logger.getInstance().log(11, this, "presIndex:  " + entry.getName() + " | " + indexKey);
            }

            KeyEidList kel = (KeyEidList)this.itPres.get(indexKey);
            kel.addEid(entryId);
         }
      }

      indexes = this.exactIndices.elements();

      while(true) {
         Enumeration attrValEnum;
         Syntax val;
         KeyEidList eids;
         Map attrIndex;
         do {
            do {
               if (!indexes.hasMoreElements()) {
                  indexes = this.orderingIndices.elements();

                  while(true) {
                     KeyEidList kel;
                     KeyEidList getKel;
                     do {
                        do {
                           if (!indexes.hasMoreElements()) {
                              indexes = this.substringIndices.elements();

                              while(true) {
                                 do {
                                    do {
                                       if (!indexes.hasMoreElements()) {
                                          return;
                                       }

                                       indexKey = (DirectoryString)indexes.nextElement();
                                       attrValVec = entry.get(indexKey);
                                    } while(attrValVec == null);
                                 } while(attrValVec.size() <= 0);

                                 attrIndex = (Map)this.itSub.get(indexKey);

                                 for(attrValEnum = attrValVec.elements(); attrValEnum.hasMoreElements(); getKel.addEid(entryId)) {
                                    val = (Syntax)attrValEnum.nextElement();
                                    if (dumpLog) {
                                       Logger.getInstance().log(11, this, "subStringIndex:  " + entry.getName() + "\n    " + indexKey + "=" + val);
                                    }

                                    kel = new KeyEidList(KeyPool.getInstance().get(this.getUTFBytes(val.reverse().normalize())));
                                    getKel = (KeyEidList)attrIndex.get(kel);
                                    eids = null;
                                    if (getKel == null) {
                                       getKel = kel;
                                       attrIndex.put(kel, kel);
                                    }
                                 }
                              }
                           }

                           indexKey = (DirectoryString)indexes.nextElement();
                           attrValVec = entry.get(indexKey);
                        } while(attrValVec == null);
                     } while(attrValVec.size() <= 0);

                     attrIndex = (Map)this.itOrder.get(indexKey);

                     for(attrValEnum = attrValVec.elements(); attrValEnum.hasMoreElements(); getKel.addEid(entryId)) {
                        val = (Syntax)attrValEnum.nextElement();
                        if (dumpLog) {
                           Logger.getInstance().log(11, this, "orderIndex:  " + entry.getName() + "\n    " + indexKey + "=" + val);
                        }

                        kel = new KeyEidList(KeyPool.getInstance().get(this.getUTFBytes(val.normalize())));
                        getKel = (KeyEidList)attrIndex.get(kel);
                        eids = null;
                        if (getKel == null) {
                           getKel = kel;
                           attrIndex.put(kel, kel);
                        }
                     }
                  }
               }

               indexKey = (DirectoryString)indexes.nextElement();
               attrValVec = entry.get(indexKey);
            } while(attrValVec == null);
         } while(attrValVec.size() <= 0);

         attrIndex = (Map)this.itExact.get(indexKey);

         for(attrValEnum = attrValVec.elements(); attrValEnum.hasMoreElements(); eids.addEid(entryId)) {
            val = (Syntax)attrValEnum.nextElement();
            String normVal = val.normalize();
            if (dumpLog) {
               Logger.getInstance().log(11, this, "exactIndex:  " + entry.getName() + "\n    " + indexKey + "=" + normVal);
            }

            String valHash = (new IntegerSyntax(normVal.hashCode())).toString();
            eids = (KeyEidList)attrIndex.get(valHash);
            if (eids == null) {
               eids = new KeyEidList(new KeyPtr(valHash.getBytes()));
               attrIndex.put(eids, eids);
            }
         }
      }
   }

   public void index(Entry entry, Vector changes) {
      int entryId = entry.getID();
      Enumeration changeEnum = changes.elements();

      while(true) {
         DirectoryString attr;
         Vector oldvals;
         KeyEidList kel;
         do {
            int changeType;
            Vector vals;
            Map exactAI;
            Map orderAI;
            Map substrAI;
            Syntax aVal;
            String normVal;
            KeyEidList kel;
            KeyEidList kel;
            Enumeration cvals;
            IntegerSyntax valHash;
            label171:
            do {
               label167:
               while(changeEnum.hasMoreElements()) {
                  EntryChange change = (EntryChange)changeEnum.nextElement();
                  changeType = change.getModType();
                  attr = change.getAttr();
                  vals = change.getValues();
                  oldvals = entry.get(attr);
                  exactAI = (Map)this.itExact.get(attr);
                  orderAI = (Map)this.itOrder.get(attr);
                  substrAI = (Map)this.itSub.get(attr);
                  if (changeType != 0 && changeType != 2) {
                     continue label171;
                  }

                  if (this.presenceIndices.contains(attr)) {
                     kel = (KeyEidList)this.itPres.get(attr);
                     kel.addEid(entry.getID());
                  }

                  if (changeType == 0) {
                     cvals = vals.elements();

                     while(cvals.hasMoreElements()) {
                        aVal = (Syntax)cvals.nextElement();
                        normVal = aVal.normalize();
                        if (orderAI != null) {
                           kel = (KeyEidList)orderAI.get(new KeyEidList(new KeyPtr(this.getUTFBytes(normVal))));
                           if (kel == null) {
                              kel = new KeyEidList(KeyPool.getInstance().get(this.getUTFBytes(normVal)));
                              orderAI.put(kel, kel);
                           }

                           kel.addEid(entry.getID());
                        }
                     }
                  }

                  if (changeType == 2) {
                     if (oldvals != null) {
                        cvals = oldvals.elements();

                        while(cvals.hasMoreElements()) {
                           aVal = (Syntax)cvals.nextElement();
                           normVal = null;
                           if (exactAI != null) {
                              normVal = aVal.normalize();
                              valHash = new IntegerSyntax(normVal.hashCode());
                              kel = (KeyEidList)exactAI.get(new KeyEidList(new KeyPtr(valHash.toString().getBytes())));
                              if (kel != null) {
                                 kel.removeEid(entryId);
                                 if (kel.size() == 0) {
                                    exactAI.remove(kel);
                                 }
                              }
                           }

                           if (orderAI != null) {
                              if (normVal == null) {
                                 normVal = aVal.normalize();
                              }

                              kel = (KeyEidList)orderAI.get(new KeyEidList(new KeyPtr(this.getUTFBytes(normVal))));
                              if (kel != null) {
                                 kel.removeEid(entryId);
                                 if (kel.size() == 0) {
                                    orderAI.remove(kel);
                                 }
                              }
                           }

                           if (substrAI != null) {
                              kel = (KeyEidList)substrAI.get(new KeyEidList(new KeyPtr(this.getUTFBytes(aVal.reverse().normalize()))));
                              if (kel != null) {
                                 kel.removeEid(entryId);
                                 if (kel.size() == 0) {
                                    substrAI.remove(kel);
                                 }
                              }
                           }
                        }
                     }

                     cvals = vals.elements();

                     while(true) {
                        do {
                           if (!cvals.hasMoreElements()) {
                              continue label167;
                           }

                           aVal = (Syntax)cvals.nextElement();
                        } while(changeType != 2 && oldvals != null && oldvals.contains(aVal));

                        normVal = null;
                        if (exactAI != null) {
                           normVal = aVal.normalize();
                           valHash = new IntegerSyntax(normVal.hashCode());
                           kel = (KeyEidList)exactAI.get(new KeyEidList(new KeyPtr(valHash.toString().getBytes())));
                           if (kel == null) {
                              kel = new KeyEidList(new KeyPtr(valHash.toString().getBytes()));
                              exactAI.put(kel, kel);
                           }

                           kel.addEid(entryId);
                        }

                        if (orderAI != null) {
                           if (normVal == null) {
                              normVal = aVal.normalize();
                           }

                           kel = (KeyEidList)orderAI.get(new KeyEidList(new KeyPtr(this.getUTFBytes(normVal))));
                           if (kel == null) {
                              kel = new KeyEidList(KeyPool.getInstance().get(this.getUTFBytes(aVal.normalize())));
                              orderAI.put(kel, kel);
                           }

                           kel.addEid(entryId);
                        }

                        if (substrAI != null) {
                           byte[] revVal = this.getUTFBytes(aVal.reverse().normalize());
                           kel = (KeyEidList)substrAI.get(new KeyEidList(new KeyPtr(revVal)));
                           if (kel == null) {
                              kel = new KeyEidList(KeyPool.getInstance().get(revVal));
                              substrAI.put(kel, kel);
                           }

                           kel.addEid(entryId);
                        }
                     }
                  }
               }

               return;
            } while(changeType != 1);

            cvals = vals.elements();

            while(cvals.hasMoreElements()) {
               aVal = (Syntax)cvals.nextElement();
               if (oldvals != null && oldvals.contains(aVal)) {
                  normVal = null;
                  if (exactAI != null) {
                     normVal = aVal.normalize();
                     valHash = new IntegerSyntax(normVal.hashCode());
                     kel = (KeyEidList)exactAI.get(new KeyEidList(new KeyPtr(valHash.toString().getBytes())));
                     if (kel != null) {
                        kel.removeEid(entryId);
                        if (kel.size() == 0) {
                           exactAI.remove(kel);
                        }
                     }
                  }

                  if (orderAI != null) {
                     if (normVal == null) {
                        normVal = aVal.normalize();
                     }

                     kel = (KeyEidList)orderAI.get(new KeyEidList(new KeyPtr(this.getUTFBytes(normVal))));
                     if (kel != null) {
                        kel.removeEid(entryId);
                        if (kel.size() == 0) {
                           orderAI.remove(kel);
                        }
                     }
                  }

                  if (substrAI != null) {
                     kel = (KeyEidList)substrAI.get(new KeyEidList(new KeyPtr(this.getUTFBytes(aVal.reverse().normalize()))));
                     if (kel != null) {
                        kel.removeEid(entryId);
                        if (kel.size() == 0) {
                           substrAI.remove(kel);
                        }
                     }
                  }

                  oldvals.removeElement(aVal);
               }
            }
         } while(oldvals != null && oldvals.size() != 0);

         kel = (KeyEidList)this.itPres.get(attr);
         if (kel != null) {
            kel.removeEid(entryId);
         }
      }
   }

   private byte[] getUTFBytes(String str) {
      try {
         return str.getBytes("UTF8");
      } catch (UnsupportedEncodingException var3) {
         return str.getBytes();
      }
   }
}
