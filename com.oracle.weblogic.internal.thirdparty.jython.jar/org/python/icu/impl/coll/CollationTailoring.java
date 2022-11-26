package org.python.icu.impl.coll;

import java.util.Map;
import org.python.icu.impl.Norm2AllModes;
import org.python.icu.impl.Normalizer2Impl;
import org.python.icu.impl.Trie2_32;
import org.python.icu.text.UnicodeSet;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;
import org.python.icu.util.VersionInfo;

public final class CollationTailoring {
   public CollationData data;
   public SharedObject.Reference settings;
   private String rules;
   private UResourceBundle rulesResource;
   public ULocale actualLocale;
   public int version;
   CollationData ownedData;
   Trie2_32 trie;
   UnicodeSet unsafeBackwardSet;
   public Map maxExpansions;

   CollationTailoring(SharedObject.Reference baseSettings) {
      this.actualLocale = ULocale.ROOT;
      this.version = 0;
      if (baseSettings != null) {
         assert ((CollationSettings)baseSettings.readOnly()).reorderCodes.length == 0;

         assert ((CollationSettings)baseSettings.readOnly()).reorderTable == null;

         assert ((CollationSettings)baseSettings.readOnly()).minHighNoReorder == 0L;

         this.settings = baseSettings.clone();
      } else {
         this.settings = new SharedObject.Reference(new CollationSettings());
      }

   }

   void ensureOwnedData() {
      if (this.ownedData == null) {
         Normalizer2Impl nfcImpl = Norm2AllModes.getNFCInstance().impl;
         this.ownedData = new CollationData(nfcImpl);
      }

      this.data = this.ownedData;
   }

   void setRules(String r) {
      assert this.rules == null && this.rulesResource == null;

      this.rules = r;
   }

   void setRulesResource(UResourceBundle res) {
      assert this.rules == null && this.rulesResource == null;

      this.rulesResource = res;
   }

   public String getRules() {
      if (this.rules != null) {
         return this.rules;
      } else {
         return this.rulesResource != null ? this.rulesResource.getString() : "";
      }
   }

   static VersionInfo makeBaseVersion(VersionInfo ucaVersion) {
      return VersionInfo.getInstance(VersionInfo.UCOL_BUILDER_VERSION.getMajor(), (ucaVersion.getMajor() << 3) + ucaVersion.getMinor(), ucaVersion.getMilli() << 6, 0);
   }

   void setVersion(int baseVersion, int rulesVersion) {
      int r = rulesVersion >> 16 & '\uff00';
      int s = rulesVersion >> 16 & 255;
      int t = rulesVersion >> 8 & 255;
      int q = rulesVersion & 255;
      this.version = VersionInfo.UCOL_BUILDER_VERSION.getMajor() << 24 | baseVersion & 16760832 | r + (r >> 6) & 16128 | (s << 3) + (s >> 5) + t + (q << 4) + (q >> 4) & 255;
   }

   int getUCAVersion() {
      return this.version >> 12 & 4080 | this.version >> 14 & 3;
   }
}
