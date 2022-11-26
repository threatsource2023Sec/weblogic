package org.python.icu.text;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CharsetDetector {
   private static final int kBufSize = 8000;
   byte[] fInputBytes = new byte[8000];
   int fInputLen;
   short[] fByteStats = new short[256];
   boolean fC1Bytes = false;
   String fDeclaredEncoding;
   byte[] fRawInput;
   int fRawLength;
   InputStream fInputStream;
   private boolean fStripTags = false;
   private boolean[] fEnabledRecognizers;
   private static final List ALL_CS_RECOGNIZERS;

   public CharsetDetector setDeclaredEncoding(String encoding) {
      this.fDeclaredEncoding = encoding;
      return this;
   }

   public CharsetDetector setText(byte[] in) {
      this.fRawInput = in;
      this.fRawLength = in.length;
      return this;
   }

   public CharsetDetector setText(InputStream in) throws IOException {
      this.fInputStream = in;
      this.fInputStream.mark(8000);
      this.fRawInput = new byte[8000];
      this.fRawLength = 0;

      int bytesRead;
      for(int remainingLength = 8000; remainingLength > 0; remainingLength -= bytesRead) {
         bytesRead = this.fInputStream.read(this.fRawInput, this.fRawLength, remainingLength);
         if (bytesRead <= 0) {
            break;
         }

         this.fRawLength += bytesRead;
      }

      this.fInputStream.reset();
      return this;
   }

   public CharsetMatch detect() {
      CharsetMatch[] matches = this.detectAll();
      return matches != null && matches.length != 0 ? matches[0] : null;
   }

   public CharsetMatch[] detectAll() {
      ArrayList matches = new ArrayList();
      this.MungeInput();

      for(int i = 0; i < ALL_CS_RECOGNIZERS.size(); ++i) {
         CSRecognizerInfo rcinfo = (CSRecognizerInfo)ALL_CS_RECOGNIZERS.get(i);
         boolean active = this.fEnabledRecognizers != null ? this.fEnabledRecognizers[i] : rcinfo.isDefaultEnabled;
         if (active) {
            CharsetMatch m = rcinfo.recognizer.match(this);
            if (m != null) {
               matches.add(m);
            }
         }
      }

      Collections.sort(matches);
      Collections.reverse(matches);
      CharsetMatch[] resultArray = new CharsetMatch[matches.size()];
      resultArray = (CharsetMatch[])matches.toArray(resultArray);
      return resultArray;
   }

   public Reader getReader(InputStream in, String declaredEncoding) {
      this.fDeclaredEncoding = declaredEncoding;

      try {
         this.setText(in);
         CharsetMatch match = this.detect();
         return match == null ? null : match.getReader();
      } catch (IOException var4) {
         return null;
      }
   }

   public String getString(byte[] in, String declaredEncoding) {
      this.fDeclaredEncoding = declaredEncoding;

      try {
         this.setText(in);
         CharsetMatch match = this.detect();
         return match == null ? null : match.getString(-1);
      } catch (IOException var4) {
         return null;
      }
   }

   public static String[] getAllDetectableCharsets() {
      String[] allCharsetNames = new String[ALL_CS_RECOGNIZERS.size()];

      for(int i = 0; i < allCharsetNames.length; ++i) {
         allCharsetNames[i] = ((CSRecognizerInfo)ALL_CS_RECOGNIZERS.get(i)).recognizer.getName();
      }

      return allCharsetNames;
   }

   public boolean inputFilterEnabled() {
      return this.fStripTags;
   }

   public boolean enableInputFilter(boolean filter) {
      boolean previous = this.fStripTags;
      this.fStripTags = filter;
      return previous;
   }

   private void MungeInput() {
      int srci = false;
      int dsti = 0;
      boolean inMarkup = false;
      int openTags = 0;
      int badTags = 0;
      int srci;
      if (this.fStripTags) {
         for(srci = 0; srci < this.fRawLength && dsti < this.fInputBytes.length; ++srci) {
            byte b = this.fRawInput[srci];
            if (b == 60) {
               if (inMarkup) {
                  ++badTags;
               }

               inMarkup = true;
               ++openTags;
            }

            if (!inMarkup) {
               this.fInputBytes[dsti++] = b;
            }

            if (b == 62) {
               inMarkup = false;
            }
         }

         this.fInputLen = dsti;
      }

      int i;
      if (openTags < 5 || openTags / 5 < badTags || this.fInputLen < 100 && this.fRawLength > 600) {
         i = this.fRawLength;
         if (i > 8000) {
            i = 8000;
         }

         for(srci = 0; srci < i; ++srci) {
            this.fInputBytes[srci] = this.fRawInput[srci];
         }

         this.fInputLen = srci;
      }

      Arrays.fill(this.fByteStats, (short)0);

      for(srci = 0; srci < this.fInputLen; ++srci) {
         i = this.fInputBytes[srci] & 255;
         ++this.fByteStats[i];
      }

      this.fC1Bytes = false;

      for(i = 128; i <= 159; ++i) {
         if (this.fByteStats[i] != 0) {
            this.fC1Bytes = true;
            break;
         }
      }

   }

   /** @deprecated */
   @Deprecated
   public String[] getDetectableCharsets() {
      List csnames = new ArrayList(ALL_CS_RECOGNIZERS.size());

      for(int i = 0; i < ALL_CS_RECOGNIZERS.size(); ++i) {
         CSRecognizerInfo rcinfo = (CSRecognizerInfo)ALL_CS_RECOGNIZERS.get(i);
         boolean active = this.fEnabledRecognizers == null ? rcinfo.isDefaultEnabled : this.fEnabledRecognizers[i];
         if (active) {
            csnames.add(rcinfo.recognizer.getName());
         }
      }

      return (String[])csnames.toArray(new String[csnames.size()]);
   }

   /** @deprecated */
   @Deprecated
   public CharsetDetector setDetectableCharset(String encoding, boolean enabled) {
      int modIdx = -1;
      boolean isDefaultVal = false;

      int i;
      for(i = 0; i < ALL_CS_RECOGNIZERS.size(); ++i) {
         CSRecognizerInfo csrinfo = (CSRecognizerInfo)ALL_CS_RECOGNIZERS.get(i);
         if (csrinfo.recognizer.getName().equals(encoding)) {
            modIdx = i;
            isDefaultVal = csrinfo.isDefaultEnabled == enabled;
            break;
         }
      }

      if (modIdx < 0) {
         throw new IllegalArgumentException("Invalid encoding: \"" + encoding + "\"");
      } else {
         if (this.fEnabledRecognizers == null && !isDefaultVal) {
            this.fEnabledRecognizers = new boolean[ALL_CS_RECOGNIZERS.size()];

            for(i = 0; i < ALL_CS_RECOGNIZERS.size(); ++i) {
               this.fEnabledRecognizers[i] = ((CSRecognizerInfo)ALL_CS_RECOGNIZERS.get(i)).isDefaultEnabled;
            }
         }

         if (this.fEnabledRecognizers != null) {
            this.fEnabledRecognizers[modIdx] = enabled;
         }

         return this;
      }
   }

   static {
      List list = new ArrayList();
      list.add(new CSRecognizerInfo(new CharsetRecog_UTF8(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_16_BE(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_16_LE(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_32_BE(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_32_LE(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_sjis(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_2022.CharsetRecog_2022JP(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_2022.CharsetRecog_2022CN(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_2022.CharsetRecog_2022KR(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_gb_18030(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_euc.CharsetRecog_euc_jp(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_euc.CharsetRecog_euc_kr(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_big5(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_1(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_2(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_5_ru(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_6_ar(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_7_el(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_8_I_he(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_8_he(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_windows_1251(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_windows_1256(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_KOI8_R(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_9_tr(), true));
      list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM424_he_rtl(), false));
      list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM424_he_ltr(), false));
      list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM420_ar_rtl(), false));
      list.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM420_ar_ltr(), false));
      ALL_CS_RECOGNIZERS = Collections.unmodifiableList(list);
   }

   private static class CSRecognizerInfo {
      CharsetRecognizer recognizer;
      boolean isDefaultEnabled;

      CSRecognizerInfo(CharsetRecognizer recognizer, boolean isDefaultEnabled) {
         this.recognizer = recognizer;
         this.isDefaultEnabled = isDefaultEnabled;
      }
   }
}
