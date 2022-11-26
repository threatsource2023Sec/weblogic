package org.python.icu.text;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.CharacterIterator;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.python.icu.impl.ClassLoaderUtil;
import org.python.icu.impl.Normalizer2Impl;
import org.python.icu.impl.Utility;
import org.python.icu.impl.coll.BOCSU;
import org.python.icu.impl.coll.CollationCompare;
import org.python.icu.impl.coll.CollationData;
import org.python.icu.impl.coll.CollationFastLatin;
import org.python.icu.impl.coll.CollationIterator;
import org.python.icu.impl.coll.CollationKeys;
import org.python.icu.impl.coll.CollationLoader;
import org.python.icu.impl.coll.CollationRoot;
import org.python.icu.impl.coll.CollationSettings;
import org.python.icu.impl.coll.CollationTailoring;
import org.python.icu.impl.coll.ContractionsAndExpansions;
import org.python.icu.impl.coll.FCDUTF16CollationIterator;
import org.python.icu.impl.coll.SharedObject;
import org.python.icu.impl.coll.TailoredSet;
import org.python.icu.impl.coll.UTF16CollationIterator;
import org.python.icu.util.ULocale;
import org.python.icu.util.VersionInfo;

public final class RuleBasedCollator extends Collator {
   private Lock frozenLock;
   private CollationBuffer collationBuffer;
   CollationData data;
   SharedObject.Reference settings;
   CollationTailoring tailoring;
   private ULocale validLocale;
   private boolean actualLocaleIsSameAsValid;

   public RuleBasedCollator(String rules) throws Exception {
      if (rules == null) {
         throw new IllegalArgumentException("Collation rules can not be null");
      } else {
         this.validLocale = ULocale.ROOT;
         this.internalBuildTailoring(rules);
      }
   }

   private final void internalBuildTailoring(String rules) throws Exception {
      CollationTailoring base = CollationRoot.getRoot();
      ClassLoader classLoader = ClassLoaderUtil.getClassLoader(this.getClass());

      CollationTailoring t;
      try {
         Class builderClass = classLoader.loadClass("org.python.icu.impl.coll.CollationBuilder");
         Object builder = builderClass.getConstructor(CollationTailoring.class).newInstance(base);
         Method parseAndBuild = builderClass.getMethod("parseAndBuild", String.class);
         t = (CollationTailoring)parseAndBuild.invoke(builder, rules);
      } catch (InvocationTargetException var8) {
         throw (Exception)var8.getTargetException();
      }

      t.actualLocale = null;
      this.adoptTailoring(t);
   }

   public Object clone() throws CloneNotSupportedException {
      return this.isFrozen() ? this : this.cloneAsThawed();
   }

   private final void initMaxExpansions() {
      synchronized(this.tailoring) {
         if (this.tailoring.maxExpansions == null) {
            this.tailoring.maxExpansions = CollationElementIterator.computeMaxExpansions(this.tailoring.data);
         }

      }
   }

   public CollationElementIterator getCollationElementIterator(String source) {
      this.initMaxExpansions();
      return new CollationElementIterator(source, this);
   }

   public CollationElementIterator getCollationElementIterator(CharacterIterator source) {
      this.initMaxExpansions();
      CharacterIterator newsource = (CharacterIterator)source.clone();
      return new CollationElementIterator(newsource, this);
   }

   public CollationElementIterator getCollationElementIterator(UCharacterIterator source) {
      this.initMaxExpansions();
      return new CollationElementIterator(source, this);
   }

   public boolean isFrozen() {
      return this.frozenLock != null;
   }

   public Collator freeze() {
      if (!this.isFrozen()) {
         this.frozenLock = new ReentrantLock();
         if (this.collationBuffer == null) {
            this.collationBuffer = new CollationBuffer(this.data);
         }
      }

      return this;
   }

   public RuleBasedCollator cloneAsThawed() {
      try {
         RuleBasedCollator result = (RuleBasedCollator)super.clone();
         result.settings = this.settings.clone();
         result.collationBuffer = null;
         result.frozenLock = null;
         return result;
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   private void checkNotFrozen() {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify frozen RuleBasedCollator");
      }
   }

   private final CollationSettings getOwnedSettings() {
      return (CollationSettings)this.settings.copyOnWrite();
   }

   private final CollationSettings getDefaultSettings() {
      return (CollationSettings)this.tailoring.settings.readOnly();
   }

   /** @deprecated */
   @Deprecated
   public void setHiraganaQuaternary(boolean flag) {
      this.checkNotFrozen();
   }

   /** @deprecated */
   @Deprecated
   public void setHiraganaQuaternaryDefault() {
      this.checkNotFrozen();
   }

   public void setUpperCaseFirst(boolean upperfirst) {
      this.checkNotFrozen();
      if (upperfirst != this.isUpperCaseFirst()) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setCaseFirst(upperfirst ? 768 : 0);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setLowerCaseFirst(boolean lowerfirst) {
      this.checkNotFrozen();
      if (lowerfirst != this.isLowerCaseFirst()) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setCaseFirst(lowerfirst ? 512 : 0);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public final void setCaseFirstDefault() {
      this.checkNotFrozen();
      CollationSettings defaultSettings = this.getDefaultSettings();
      if (this.settings.readOnly() != defaultSettings) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setCaseFirstDefault(defaultSettings.options);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setAlternateHandlingDefault() {
      this.checkNotFrozen();
      CollationSettings defaultSettings = this.getDefaultSettings();
      if (this.settings.readOnly() != defaultSettings) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setAlternateHandlingDefault(defaultSettings.options);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setCaseLevelDefault() {
      this.checkNotFrozen();
      CollationSettings defaultSettings = this.getDefaultSettings();
      if (this.settings.readOnly() != defaultSettings) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlagDefault(1024, defaultSettings.options);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setDecompositionDefault() {
      this.checkNotFrozen();
      CollationSettings defaultSettings = this.getDefaultSettings();
      if (this.settings.readOnly() != defaultSettings) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlagDefault(1, defaultSettings.options);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setFrenchCollationDefault() {
      this.checkNotFrozen();
      CollationSettings defaultSettings = this.getDefaultSettings();
      if (this.settings.readOnly() != defaultSettings) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlagDefault(2048, defaultSettings.options);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setStrengthDefault() {
      this.checkNotFrozen();
      CollationSettings defaultSettings = this.getDefaultSettings();
      if (this.settings.readOnly() != defaultSettings) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setStrengthDefault(defaultSettings.options);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setNumericCollationDefault() {
      this.checkNotFrozen();
      CollationSettings defaultSettings = this.getDefaultSettings();
      if (this.settings.readOnly() != defaultSettings) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlagDefault(2, defaultSettings.options);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setFrenchCollation(boolean flag) {
      this.checkNotFrozen();
      if (flag != this.isFrenchCollation()) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlag(2048, flag);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setAlternateHandlingShifted(boolean shifted) {
      this.checkNotFrozen();
      if (shifted != this.isAlternateHandlingShifted()) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setAlternateHandlingShifted(shifted);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setCaseLevel(boolean flag) {
      this.checkNotFrozen();
      if (flag != this.isCaseLevel()) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlag(1024, flag);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setDecomposition(int decomposition) {
      this.checkNotFrozen();
      boolean flag;
      switch (decomposition) {
         case 16:
            flag = false;
            break;
         case 17:
            flag = true;
            break;
         default:
            throw new IllegalArgumentException("Wrong decomposition mode.");
      }

      if (flag != ((CollationSettings)this.settings.readOnly()).getFlag(1)) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlag(1, flag);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setStrength(int newStrength) {
      this.checkNotFrozen();
      if (newStrength != this.getStrength()) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setStrength(newStrength);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public RuleBasedCollator setMaxVariable(int group) {
      int value;
      if (group == -1) {
         value = -1;
      } else {
         if (4096 > group || group > 4099) {
            throw new IllegalArgumentException("illegal max variable group " + group);
         }

         value = group - 4096;
      }

      int oldValue = ((CollationSettings)this.settings.readOnly()).getMaxVariable();
      if (value == oldValue) {
         return this;
      } else {
         CollationSettings defaultSettings = this.getDefaultSettings();
         if (this.settings.readOnly() == defaultSettings && value < 0) {
            return this;
         } else {
            CollationSettings ownedSettings = this.getOwnedSettings();
            if (group == -1) {
               group = 4096 + defaultSettings.getMaxVariable();
            }

            long varTop = this.data.getLastPrimaryForGroup(group);

            assert varTop != 0L;

            ownedSettings.setMaxVariable(value, defaultSettings.options);
            ownedSettings.variableTop = varTop;
            this.setFastLatinOptions(ownedSettings);
            return this;
         }
      }
   }

   public int getMaxVariable() {
      return 4096 + ((CollationSettings)this.settings.readOnly()).getMaxVariable();
   }

   /** @deprecated */
   @Deprecated
   public int setVariableTop(String varTop) {
      this.checkNotFrozen();
      if (varTop != null && varTop.length() != 0) {
         boolean numeric = ((CollationSettings)this.settings.readOnly()).isNumeric();
         long ce1;
         long ce2;
         if (((CollationSettings)this.settings.readOnly()).dontCheckFCD()) {
            UTF16CollationIterator ci = new UTF16CollationIterator(this.data, numeric, varTop, 0);
            ce1 = ci.nextCE();
            ce2 = ci.nextCE();
         } else {
            FCDUTF16CollationIterator ci = new FCDUTF16CollationIterator(this.data, numeric, varTop, 0);
            ce1 = ci.nextCE();
            ce2 = ci.nextCE();
         }

         if (ce1 != 4311744768L && ce2 == 4311744768L) {
            this.internalSetVariableTop(ce1 >>> 32);
            return (int)((CollationSettings)this.settings.readOnly()).variableTop;
         } else {
            throw new IllegalArgumentException("Variable top argument string must map to exactly one collation element");
         }
      } else {
         throw new IllegalArgumentException("Variable top argument string can not be null or zero in length.");
      }
   }

   /** @deprecated */
   @Deprecated
   public void setVariableTop(int varTop) {
      this.checkNotFrozen();
      this.internalSetVariableTop((long)varTop & 4294967295L);
   }

   private void internalSetVariableTop(long varTop) {
      if (varTop != ((CollationSettings)this.settings.readOnly()).variableTop) {
         int group = this.data.getGroupForPrimary(varTop);
         if (group < 4096 || 4099 < group) {
            throw new IllegalArgumentException("The variable top must be a primary weight in the space/punctuation/symbols/currency symbols range");
         }

         long v = this.data.getLastPrimaryForGroup(group);

         assert v != 0L && v >= varTop;

         if (v != ((CollationSettings)this.settings.readOnly()).variableTop) {
            CollationSettings ownedSettings = this.getOwnedSettings();
            ownedSettings.setMaxVariable(group - 4096, this.getDefaultSettings().options);
            ownedSettings.variableTop = v;
            this.setFastLatinOptions(ownedSettings);
         }
      }

   }

   public void setNumericCollation(boolean flag) {
      this.checkNotFrozen();
      if (flag != this.getNumericCollation()) {
         CollationSettings ownedSettings = this.getOwnedSettings();
         ownedSettings.setFlag(2, flag);
         this.setFastLatinOptions(ownedSettings);
      }
   }

   public void setReorderCodes(int... order) {
      this.checkNotFrozen();
      int length = order != null ? order.length : 0;
      if (length == 1 && order[0] == 103) {
         length = 0;
      }

      if (length == 0) {
         if (((CollationSettings)this.settings.readOnly()).reorderCodes.length == 0) {
            return;
         }
      } else if (Arrays.equals(order, ((CollationSettings)this.settings.readOnly()).reorderCodes)) {
         return;
      }

      CollationSettings defaultSettings = this.getDefaultSettings();
      CollationSettings ownedSettings;
      if (length == 1 && order[0] == -1) {
         if (this.settings.readOnly() != defaultSettings) {
            ownedSettings = this.getOwnedSettings();
            ownedSettings.copyReorderingFrom(defaultSettings);
            this.setFastLatinOptions(ownedSettings);
         }

      } else {
         ownedSettings = this.getOwnedSettings();
         if (length == 0) {
            ownedSettings.resetReordering();
         } else {
            ownedSettings.setReordering(this.data, (int[])order.clone());
         }

         this.setFastLatinOptions(ownedSettings);
      }
   }

   private void setFastLatinOptions(CollationSettings ownedSettings) {
      ownedSettings.fastLatinOptions = CollationFastLatin.getOptions(this.data, ownedSettings, ownedSettings.fastLatinPrimaries);
   }

   public String getRules() {
      return this.tailoring.getRules();
   }

   public String getRules(boolean fullrules) {
      return !fullrules ? this.tailoring.getRules() : CollationLoader.getRootRules() + this.tailoring.getRules();
   }

   public UnicodeSet getTailoredSet() {
      UnicodeSet tailored = new UnicodeSet();
      if (this.data.base != null) {
         (new TailoredSet(tailored)).forData(this.data);
      }

      return tailored;
   }

   public void getContractionsAndExpansions(UnicodeSet contractions, UnicodeSet expansions, boolean addPrefixes) throws Exception {
      if (contractions != null) {
         contractions.clear();
      }

      if (expansions != null) {
         expansions.clear();
      }

      (new ContractionsAndExpansions(contractions, expansions, (ContractionsAndExpansions.CESink)null, addPrefixes)).forData(this.data);
   }

   /** @deprecated */
   void internalAddContractions(int c, UnicodeSet set) {
      (new ContractionsAndExpansions(set, (UnicodeSet)null, (ContractionsAndExpansions.CESink)null, false)).forCodePoint(this.data, c);
   }

   public CollationKey getCollationKey(String source) {
      if (source == null) {
         return null;
      } else {
         CollationBuffer buffer = null;

         CollationKey var3;
         try {
            buffer = this.getCollationBuffer();
            var3 = this.getCollationKey(source, buffer);
         } finally {
            this.releaseCollationBuffer(buffer);
         }

         return var3;
      }
   }

   private CollationKey getCollationKey(String source, CollationBuffer buffer) {
      buffer.rawCollationKey = this.getRawCollationKey(source, buffer.rawCollationKey, buffer);
      return new CollationKey(source, buffer.rawCollationKey);
   }

   public RawCollationKey getRawCollationKey(String source, RawCollationKey key) {
      if (source == null) {
         return null;
      } else {
         CollationBuffer buffer = null;

         RawCollationKey var4;
         try {
            buffer = this.getCollationBuffer();
            var4 = this.getRawCollationKey(source, key, buffer);
         } finally {
            this.releaseCollationBuffer(buffer);
         }

         return var4;
      }
   }

   private RawCollationKey getRawCollationKey(CharSequence source, RawCollationKey key, CollationBuffer buffer) {
      if (key == null) {
         key = new RawCollationKey(this.simpleKeyLengthEstimate(source));
      } else if (key.bytes == null) {
         key.bytes = new byte[this.simpleKeyLengthEstimate(source)];
      }

      CollationKeyByteSink sink = new CollationKeyByteSink(key);
      this.writeSortKey(source, sink, buffer);
      key.size = sink.NumberOfBytesAppended();
      return key;
   }

   private int simpleKeyLengthEstimate(CharSequence source) {
      return 2 * source.length() + 10;
   }

   private void writeSortKey(CharSequence s, CollationKeyByteSink sink, CollationBuffer buffer) {
      boolean numeric = ((CollationSettings)this.settings.readOnly()).isNumeric();
      if (((CollationSettings)this.settings.readOnly()).dontCheckFCD()) {
         buffer.leftUTF16CollIter.setText(numeric, s, 0);
         CollationKeys.writeSortKeyUpToQuaternary(buffer.leftUTF16CollIter, this.data.compressibleBytes, (CollationSettings)this.settings.readOnly(), sink, 1, CollationKeys.SIMPLE_LEVEL_FALLBACK, true);
      } else {
         buffer.leftFCDUTF16Iter.setText(numeric, s, 0);
         CollationKeys.writeSortKeyUpToQuaternary(buffer.leftFCDUTF16Iter, this.data.compressibleBytes, (CollationSettings)this.settings.readOnly(), sink, 1, CollationKeys.SIMPLE_LEVEL_FALLBACK, true);
      }

      if (((CollationSettings)this.settings.readOnly()).getStrength() == 15) {
         this.writeIdenticalLevel(s, sink);
      }

      sink.Append(0);
   }

   private void writeIdenticalLevel(CharSequence s, CollationKeyByteSink sink) {
      int nfdQCYesLimit = this.data.nfcImpl.decompose(s, 0, s.length(), (Normalizer2Impl.ReorderingBuffer)null);
      sink.Append(1);
      sink.key_.size = sink.NumberOfBytesAppended();
      int prev = 0;
      if (nfdQCYesLimit != 0) {
         prev = BOCSU.writeIdenticalLevelRun(prev, s, 0, nfdQCYesLimit, sink.key_);
      }

      if (nfdQCYesLimit < s.length()) {
         int destLengthEstimate = s.length() - nfdQCYesLimit;
         StringBuilder nfd = new StringBuilder();
         this.data.nfcImpl.decompose(s, nfdQCYesLimit, s.length(), nfd, destLengthEstimate);
         BOCSU.writeIdenticalLevelRun(prev, nfd, 0, nfd.length(), sink.key_);
      }

      sink.setBufferAndAppended(sink.key_.bytes, sink.key_.size);
   }

   /** @deprecated */
   @Deprecated
   public long[] internalGetCEs(CharSequence str) {
      CollationBuffer buffer = null;

      long[] var7;
      try {
         buffer = this.getCollationBuffer();
         boolean numeric = ((CollationSettings)this.settings.readOnly()).isNumeric();
         Object iter;
         if (((CollationSettings)this.settings.readOnly()).dontCheckFCD()) {
            buffer.leftUTF16CollIter.setText(numeric, str, 0);
            iter = buffer.leftUTF16CollIter;
         } else {
            buffer.leftFCDUTF16Iter.setText(numeric, str, 0);
            iter = buffer.leftFCDUTF16Iter;
         }

         int length = ((CollationIterator)iter).fetchCEs() - 1;

         assert length >= 0 && ((CollationIterator)iter).getCE(length) == 4311744768L;

         long[] ces = new long[length];
         System.arraycopy(((CollationIterator)iter).getCEs(), 0, ces, 0, length);
         var7 = ces;
      } finally {
         this.releaseCollationBuffer(buffer);
      }

      return var7;
   }

   public int getStrength() {
      return ((CollationSettings)this.settings.readOnly()).getStrength();
   }

   public int getDecomposition() {
      return (((CollationSettings)this.settings.readOnly()).options & 1) != 0 ? 17 : 16;
   }

   public boolean isUpperCaseFirst() {
      return ((CollationSettings)this.settings.readOnly()).getCaseFirst() == 768;
   }

   public boolean isLowerCaseFirst() {
      return ((CollationSettings)this.settings.readOnly()).getCaseFirst() == 512;
   }

   public boolean isAlternateHandlingShifted() {
      return ((CollationSettings)this.settings.readOnly()).getAlternateHandling();
   }

   public boolean isCaseLevel() {
      return (((CollationSettings)this.settings.readOnly()).options & 1024) != 0;
   }

   public boolean isFrenchCollation() {
      return (((CollationSettings)this.settings.readOnly()).options & 2048) != 0;
   }

   /** @deprecated */
   @Deprecated
   public boolean isHiraganaQuaternary() {
      return false;
   }

   public int getVariableTop() {
      return (int)((CollationSettings)this.settings.readOnly()).variableTop;
   }

   public boolean getNumericCollation() {
      return (((CollationSettings)this.settings.readOnly()).options & 2) != 0;
   }

   public int[] getReorderCodes() {
      return (int[])((CollationSettings)this.settings.readOnly()).reorderCodes.clone();
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!super.equals(obj)) {
         return false;
      } else {
         RuleBasedCollator o = (RuleBasedCollator)obj;
         if (!((CollationSettings)this.settings.readOnly()).equals(o.settings.readOnly())) {
            return false;
         } else if (this.data == o.data) {
            return true;
         } else {
            boolean thisIsRoot = this.data.base == null;
            boolean otherIsRoot = o.data.base == null;

            assert !thisIsRoot || !otherIsRoot;

            if (thisIsRoot != otherIsRoot) {
               return false;
            } else {
               String theseRules = this.tailoring.getRules();
               String otherRules = o.tailoring.getRules();
               if ((thisIsRoot || theseRules.length() != 0) && (otherIsRoot || otherRules.length() != 0) && theseRules.equals(otherRules)) {
                  return true;
               } else {
                  UnicodeSet thisTailored = this.getTailoredSet();
                  UnicodeSet otherTailored = o.getTailoredSet();
                  return thisTailored.equals(otherTailored);
               }
            }
         }
      }
   }

   public int hashCode() {
      int h = ((CollationSettings)this.settings.readOnly()).hashCode();
      if (this.data.base == null) {
         return h;
      } else {
         UnicodeSet set = this.getTailoredSet();

         for(UnicodeSetIterator iter = new UnicodeSetIterator(set); iter.next() && iter.codepoint != UnicodeSetIterator.IS_STRING; h ^= this.data.getCE32(iter.codepoint)) {
         }

         return h;
      }
   }

   public int compare(String source, String target) {
      return this.doCompare(source, target);
   }

   private static final int compareNFDIter(Normalizer2Impl nfcImpl, NFDIterator left, NFDIterator right) {
      while(true) {
         int leftCp = left.nextCodePoint();
         int rightCp = right.nextCodePoint();
         if (leftCp == rightCp) {
            if (leftCp < 0) {
               return 0;
            }
         } else {
            if (leftCp < 0) {
               leftCp = -2;
            } else if (leftCp == 65534) {
               leftCp = -1;
            } else {
               leftCp = left.nextDecomposedCodePoint(nfcImpl, leftCp);
            }

            if (rightCp < 0) {
               rightCp = -2;
            } else if (rightCp == 65534) {
               rightCp = -1;
            } else {
               rightCp = right.nextDecomposedCodePoint(nfcImpl, rightCp);
            }

            if (leftCp < rightCp) {
               return -1;
            }

            if (leftCp > rightCp) {
               return 1;
            }
         }
      }
   }

   /** @deprecated */
   @Deprecated
   protected int doCompare(CharSequence left, CharSequence right) {
      if (left == right) {
         return 0;
      } else {
         int equalPrefixLength = 0;

         while(true) {
            if (equalPrefixLength == left.length()) {
               if (equalPrefixLength == right.length()) {
                  return 0;
               }
               break;
            }

            if (equalPrefixLength == right.length() || left.charAt(equalPrefixLength) != right.charAt(equalPrefixLength)) {
               break;
            }

            ++equalPrefixLength;
         }

         CollationSettings roSettings = (CollationSettings)this.settings.readOnly();
         boolean numeric = roSettings.isNumeric();
         if (equalPrefixLength > 0 && (equalPrefixLength != left.length() && this.data.isUnsafeBackward(left.charAt(equalPrefixLength), numeric) || equalPrefixLength != right.length() && this.data.isUnsafeBackward(right.charAt(equalPrefixLength), numeric))) {
            do {
               --equalPrefixLength;
            } while(equalPrefixLength > 0 && this.data.isUnsafeBackward(left.charAt(equalPrefixLength), numeric));
         }

         int fastLatinOptions = roSettings.fastLatinOptions;
         int result;
         if (fastLatinOptions < 0 || equalPrefixLength != left.length() && left.charAt(equalPrefixLength) > 383 || equalPrefixLength != right.length() && right.charAt(equalPrefixLength) > 383) {
            result = -2;
         } else {
            result = CollationFastLatin.compareUTF16(this.data.fastLatinTable, roSettings.fastLatinPrimaries, fastLatinOptions, left, right, equalPrefixLength);
         }

         CollationBuffer buffer;
         if (result == -2) {
            buffer = null;

            try {
               buffer = this.getCollationBuffer();
               if (roSettings.dontCheckFCD()) {
                  buffer.leftUTF16CollIter.setText(numeric, left, equalPrefixLength);
                  buffer.rightUTF16CollIter.setText(numeric, right, equalPrefixLength);
                  result = CollationCompare.compareUpToQuaternary(buffer.leftUTF16CollIter, buffer.rightUTF16CollIter, roSettings);
               } else {
                  buffer.leftFCDUTF16Iter.setText(numeric, left, equalPrefixLength);
                  buffer.rightFCDUTF16Iter.setText(numeric, right, equalPrefixLength);
                  result = CollationCompare.compareUpToQuaternary(buffer.leftFCDUTF16Iter, buffer.rightFCDUTF16Iter, roSettings);
               }
            } finally {
               this.releaseCollationBuffer(buffer);
            }
         }

         if (result == 0 && roSettings.getStrength() >= 15) {
            buffer = null;

            int var10;
            try {
               buffer = this.getCollationBuffer();
               Normalizer2Impl nfcImpl = this.data.nfcImpl;
               if (!roSettings.dontCheckFCD()) {
                  buffer.leftFCDUTF16NFDIter.setText(nfcImpl, left, equalPrefixLength);
                  buffer.rightFCDUTF16NFDIter.setText(nfcImpl, right, equalPrefixLength);
                  var10 = compareNFDIter(nfcImpl, buffer.leftFCDUTF16NFDIter, buffer.rightFCDUTF16NFDIter);
                  return var10;
               }

               buffer.leftUTF16NFDIter.setText(left, equalPrefixLength);
               buffer.rightUTF16NFDIter.setText(right, equalPrefixLength);
               var10 = compareNFDIter(nfcImpl, buffer.leftUTF16NFDIter, buffer.rightUTF16NFDIter);
            } finally {
               this.releaseCollationBuffer(buffer);
            }

            return var10;
         } else {
            return result;
         }
      }
   }

   RuleBasedCollator(CollationTailoring t, ULocale vl) {
      this.data = t.data;
      this.settings = t.settings.clone();
      this.tailoring = t;
      this.validLocale = vl;
      this.actualLocaleIsSameAsValid = false;
   }

   private void adoptTailoring(CollationTailoring t) {
      assert this.settings == null && this.data == null && this.tailoring == null;

      this.data = t.data;
      this.settings = t.settings.clone();
      this.tailoring = t;
      this.validLocale = t.actualLocale;
      this.actualLocaleIsSameAsValid = false;
   }

   final boolean isUnsafe(int c) {
      return this.data.isUnsafeBackward(c, ((CollationSettings)this.settings.readOnly()).isNumeric());
   }

   public VersionInfo getVersion() {
      int version = this.tailoring.version;
      int rtVersion = VersionInfo.UCOL_RUNTIME_VERSION.getMajor();
      return VersionInfo.getInstance((version >>> 24) + (rtVersion << 4) + (rtVersion >> 4), version >> 16 & 255, version >> 8 & 255, version & 255);
   }

   public VersionInfo getUCAVersion() {
      VersionInfo v = this.getVersion();
      return VersionInfo.getInstance(v.getMinor() >> 3, v.getMinor() & 7, v.getMilli() >> 6, 0);
   }

   private final CollationBuffer getCollationBuffer() {
      if (this.isFrozen()) {
         this.frozenLock.lock();
      } else if (this.collationBuffer == null) {
         this.collationBuffer = new CollationBuffer(this.data);
      }

      return this.collationBuffer;
   }

   private final void releaseCollationBuffer(CollationBuffer buffer) {
      if (this.isFrozen()) {
         this.frozenLock.unlock();
      }

   }

   public ULocale getLocale(ULocale.Type type) {
      if (type == ULocale.ACTUAL_LOCALE) {
         return this.actualLocaleIsSameAsValid ? this.validLocale : this.tailoring.actualLocale;
      } else if (type == ULocale.VALID_LOCALE) {
         return this.validLocale;
      } else {
         throw new IllegalArgumentException("unknown ULocale.Type " + type);
      }
   }

   void setLocale(ULocale valid, ULocale actual) {
      assert valid == null == (actual == null);

      if (Utility.objectEquals(actual, this.tailoring.actualLocale)) {
         this.actualLocaleIsSameAsValid = false;
      } else {
         assert Utility.objectEquals(actual, valid);

         this.actualLocaleIsSameAsValid = true;
      }

      this.validLocale = valid;
   }

   private static final class CollationBuffer {
      UTF16CollationIterator leftUTF16CollIter;
      UTF16CollationIterator rightUTF16CollIter;
      FCDUTF16CollationIterator leftFCDUTF16Iter;
      FCDUTF16CollationIterator rightFCDUTF16Iter;
      UTF16NFDIterator leftUTF16NFDIter;
      UTF16NFDIterator rightUTF16NFDIter;
      FCDUTF16NFDIterator leftFCDUTF16NFDIter;
      FCDUTF16NFDIterator rightFCDUTF16NFDIter;
      RawCollationKey rawCollationKey;

      private CollationBuffer(CollationData data) {
         this.leftUTF16CollIter = new UTF16CollationIterator(data);
         this.rightUTF16CollIter = new UTF16CollationIterator(data);
         this.leftFCDUTF16Iter = new FCDUTF16CollationIterator(data);
         this.rightFCDUTF16Iter = new FCDUTF16CollationIterator(data);
         this.leftUTF16NFDIter = new UTF16NFDIterator();
         this.rightUTF16NFDIter = new UTF16NFDIterator();
         this.leftFCDUTF16NFDIter = new FCDUTF16NFDIterator();
         this.rightFCDUTF16NFDIter = new FCDUTF16NFDIterator();
      }

      // $FF: synthetic method
      CollationBuffer(CollationData x0, Object x1) {
         this(x0);
      }
   }

   private static final class FCDUTF16NFDIterator extends UTF16NFDIterator {
      private StringBuilder str;

      FCDUTF16NFDIterator() {
      }

      void setText(Normalizer2Impl nfcImpl, CharSequence seq, int start) {
         this.reset();
         int spanLimit = nfcImpl.makeFCD(seq, start, seq.length(), (Normalizer2Impl.ReorderingBuffer)null);
         if (spanLimit == seq.length()) {
            this.s = seq;
            this.pos = start;
         } else {
            if (this.str == null) {
               this.str = new StringBuilder();
            } else {
               this.str.setLength(0);
            }

            this.str.append(seq, start, spanLimit);
            Normalizer2Impl.ReorderingBuffer buffer = new Normalizer2Impl.ReorderingBuffer(nfcImpl, this.str, seq.length() - start);
            nfcImpl.makeFCD(seq, spanLimit, seq.length(), buffer);
            this.s = this.str;
            this.pos = 0;
         }

      }
   }

   private static class UTF16NFDIterator extends NFDIterator {
      protected CharSequence s;
      protected int pos;

      UTF16NFDIterator() {
      }

      void setText(CharSequence seq, int start) {
         this.reset();
         this.s = seq;
         this.pos = start;
      }

      protected int nextRawCodePoint() {
         if (this.pos == this.s.length()) {
            return -1;
         } else {
            int c = Character.codePointAt(this.s, this.pos);
            this.pos += Character.charCount(c);
            return c;
         }
      }
   }

   private abstract static class NFDIterator {
      private String decomp;
      private int index;

      NFDIterator() {
      }

      final void reset() {
         this.index = -1;
      }

      final int nextCodePoint() {
         if (this.index >= 0) {
            if (this.index != this.decomp.length()) {
               int c = Character.codePointAt(this.decomp, this.index);
               this.index += Character.charCount(c);
               return c;
            }

            this.index = -1;
         }

         return this.nextRawCodePoint();
      }

      final int nextDecomposedCodePoint(Normalizer2Impl nfcImpl, int c) {
         if (this.index >= 0) {
            return c;
         } else {
            this.decomp = nfcImpl.getDecomposition(c);
            if (this.decomp == null) {
               return c;
            } else {
               c = Character.codePointAt(this.decomp, 0);
               this.index = Character.charCount(c);
               return c;
            }
         }
      }

      protected abstract int nextRawCodePoint();
   }

   private static final class CollationKeyByteSink extends CollationKeys.SortKeyByteSink {
      private RawCollationKey key_;

      CollationKeyByteSink(RawCollationKey key) {
         super(key.bytes);
         this.key_ = key;
      }

      protected void AppendBeyondCapacity(byte[] bytes, int start, int n, int length) {
         if (this.Resize(n, length)) {
            System.arraycopy(bytes, start, this.buffer_, length, n);
         }

      }

      protected boolean Resize(int appendCapacity, int length) {
         int newCapacity = 2 * this.buffer_.length;
         int altCapacity = length + 2 * appendCapacity;
         if (newCapacity < altCapacity) {
            newCapacity = altCapacity;
         }

         if (newCapacity < 200) {
            newCapacity = 200;
         }

         byte[] newBytes = new byte[newCapacity];
         System.arraycopy(this.buffer_, 0, newBytes, 0, length);
         this.buffer_ = this.key_.bytes = newBytes;
         return true;
      }
   }
}
