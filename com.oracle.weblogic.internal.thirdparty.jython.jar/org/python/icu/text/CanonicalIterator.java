package org.python.icu.text;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.python.icu.impl.Norm2AllModes;
import org.python.icu.impl.Normalizer2Impl;
import org.python.icu.impl.Utility;
import org.python.icu.lang.UCharacter;

public final class CanonicalIterator {
   private static boolean PROGRESS = false;
   private static boolean SKIP_ZEROS = true;
   private final Normalizer2 nfd;
   private final Normalizer2Impl nfcImpl;
   private String source;
   private boolean done;
   private String[][] pieces;
   private int[] current;
   private transient StringBuilder buffer = new StringBuilder();
   private static final Set SET_WITH_NULL_STRING = new HashSet();

   public CanonicalIterator(String source) {
      Norm2AllModes allModes = Norm2AllModes.getNFCInstance();
      this.nfd = allModes.decomp;
      this.nfcImpl = allModes.impl.ensureCanonIterData();
      this.setSource(source);
   }

   public String getSource() {
      return this.source;
   }

   public void reset() {
      this.done = false;

      for(int i = 0; i < this.current.length; ++i) {
         this.current[i] = 0;
      }

   }

   public String next() {
      if (this.done) {
         return null;
      } else {
         this.buffer.setLength(0);

         for(int i = 0; i < this.pieces.length; ++i) {
            this.buffer.append(this.pieces[i][this.current[i]]);
         }

         String result = this.buffer.toString();
         int i = this.current.length - 1;

         while(true) {
            if (i < 0) {
               this.done = true;
               break;
            }

            int var10002 = this.current[i]++;
            if (this.current[i] < this.pieces[i].length) {
               break;
            }

            this.current[i] = 0;
            --i;
         }

         return result;
      }
   }

   public void setSource(String newSource) {
      this.source = this.nfd.normalize(newSource);
      this.done = false;
      if (newSource.length() == 0) {
         this.pieces = new String[1][];
         this.current = new int[1];
         this.pieces[0] = new String[]{""};
      } else {
         List segmentList = new ArrayList();
         int start = 0;

         int i;
         int cp;
         for(i = UTF16.findOffsetFromCodePoint((String)this.source, 1); i < this.source.length(); i += Character.charCount(cp)) {
            cp = this.source.codePointAt(i);
            if (this.nfcImpl.isCanonSegmentStarter(cp)) {
               segmentList.add(this.source.substring(start, i));
               start = i;
            }
         }

         segmentList.add(this.source.substring(start, i));
         this.pieces = new String[segmentList.size()][];
         this.current = new int[segmentList.size()];

         for(i = 0; i < this.pieces.length; ++i) {
            if (PROGRESS) {
               System.out.println("SEGMENT");
            }

            this.pieces[i] = this.getEquivalents((String)segmentList.get(i));
         }

      }
   }

   /** @deprecated */
   @Deprecated
   public static void permute(String source, boolean skipZeros, Set output) {
      if (source.length() <= 2 && UTF16.countCodePoint(source) <= 1) {
         output.add(source);
      } else {
         Set subpermute = new HashSet();

         int cp;
         for(int i = 0; i < source.length(); i += UTF16.getCharCount(cp)) {
            cp = UTF16.charAt(source, i);
            if (!skipZeros || i == 0 || UCharacter.getCombiningClass(cp) != 0) {
               subpermute.clear();
               permute(source.substring(0, i) + source.substring(i + UTF16.getCharCount(cp)), skipZeros, subpermute);
               String chStr = UTF16.valueOf(source, i);
               Iterator var7 = subpermute.iterator();

               while(var7.hasNext()) {
                  String s = (String)var7.next();
                  String piece = chStr + s;
                  output.add(piece);
               }
            }
         }

      }
   }

   private String[] getEquivalents(String segment) {
      Set result = new HashSet();
      Set basic = this.getEquivalents2(segment);
      Set permutations = new HashSet();
      Iterator it = basic.iterator();

      while(it.hasNext()) {
         String item = (String)it.next();
         permutations.clear();
         permute(item, SKIP_ZEROS, permutations);
         Iterator it2 = permutations.iterator();

         while(it2.hasNext()) {
            String possible = (String)it2.next();
            if (Normalizer.compare((String)possible, (String)segment, 0) == 0) {
               if (PROGRESS) {
                  System.out.println("Adding Permutation: " + Utility.hex(possible));
               }

               result.add(possible);
            } else if (PROGRESS) {
               System.out.println("-Skipping Permutation: " + Utility.hex(possible));
            }
         }
      }

      String[] finalResult = new String[result.size()];
      result.toArray(finalResult);
      return finalResult;
   }

   private Set getEquivalents2(String segment) {
      Set result = new HashSet();
      if (PROGRESS) {
         System.out.println("Adding: " + Utility.hex(segment));
      }

      result.add(segment);
      StringBuffer workingBuffer = new StringBuffer();
      UnicodeSet starts = new UnicodeSet();

      int cp;
      label41:
      for(int i = 0; i < segment.length(); i += Character.charCount(cp)) {
         cp = segment.codePointAt(i);
         if (this.nfcImpl.getCanonStartSet(cp, starts)) {
            UnicodeSetIterator iter = new UnicodeSetIterator(starts);

            while(true) {
               int cp2;
               Set remainder;
               do {
                  if (!iter.next()) {
                     continue label41;
                  }

                  cp2 = iter.codepoint;
                  remainder = this.extract(cp2, segment, i, workingBuffer);
               } while(remainder == null);

               String prefix = segment.substring(0, i);
               prefix = prefix + UTF16.valueOf(cp2);
               Iterator var11 = remainder.iterator();

               while(var11.hasNext()) {
                  String item = (String)var11.next();
                  result.add(prefix + item);
               }
            }
         }
      }

      return result;
   }

   private Set extract(int comp, String segment, int segmentPos, StringBuffer buf) {
      if (PROGRESS) {
         System.out.println(" extract: " + Utility.hex(UTF16.valueOf(comp)) + ", " + Utility.hex(segment.substring(segmentPos)));
      }

      String decomp = this.nfcImpl.getDecomposition(comp);
      if (decomp == null) {
         decomp = UTF16.valueOf(comp);
      }

      boolean ok = false;
      int decompPos = 0;
      int decompCp = UTF16.charAt((String)decomp, 0);
      decompPos += UTF16.getCharCount(decompCp);
      buf.setLength(0);

      int cp;
      for(int i = segmentPos; i < segment.length(); i += UTF16.getCharCount(cp)) {
         cp = UTF16.charAt(segment, i);
         if (cp == decompCp) {
            if (PROGRESS) {
               System.out.println("  matches: " + Utility.hex(UTF16.valueOf(cp)));
            }

            if (decompPos == decomp.length()) {
               buf.append(segment.substring(i + UTF16.getCharCount(cp)));
               ok = true;
               break;
            }

            decompCp = UTF16.charAt(decomp, decompPos);
            decompPos += UTF16.getCharCount(decompCp);
         } else {
            if (PROGRESS) {
               System.out.println("  buffer: " + Utility.hex(UTF16.valueOf(cp)));
            }

            UTF16.append(buf, cp);
         }
      }

      if (!ok) {
         return null;
      } else {
         if (PROGRESS) {
            System.out.println("Matches");
         }

         if (buf.length() == 0) {
            return SET_WITH_NULL_STRING;
         } else {
            String remainder = buf.toString();
            return 0 != Normalizer.compare((String)(UTF16.valueOf(comp) + remainder), (String)segment.substring(segmentPos), 0) ? null : this.getEquivalents2(remainder);
         }
      }
   }

   static {
      SET_WITH_NULL_STRING.add("");
   }
}
