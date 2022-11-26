package org.python.icu.impl;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.python.icu.impl.locale.AsciiUtil;
import org.python.icu.util.UResourceBundle;
import org.python.icu.util.UResourceBundleIterator;

public class ValidIdentifiers {
   public static Map getData() {
      return ValidIdentifiers.ValidityData.data;
   }

   public static Datasubtype isValid(Datatype datatype, Set datasubtypes, String code) {
      Map subtable = (Map)ValidIdentifiers.ValidityData.data.get(datatype);
      if (subtable != null) {
         Iterator var4 = datasubtypes.iterator();

         while(var4.hasNext()) {
            Datasubtype datasubtype = (Datasubtype)var4.next();
            ValiditySet validitySet = (ValiditySet)subtable.get(datasubtype);
            if (validitySet != null && validitySet.contains(AsciiUtil.toLowerString(code))) {
               return datasubtype;
            }
         }
      }

      return null;
   }

   public static Datasubtype isValid(Datatype datatype, Set datasubtypes, String code, String value) {
      Map subtable = (Map)ValidIdentifiers.ValidityData.data.get(datatype);
      if (subtable != null) {
         code = AsciiUtil.toLowerString(code);
         value = AsciiUtil.toLowerString(value);
         Iterator var5 = datasubtypes.iterator();

         while(var5.hasNext()) {
            Datasubtype datasubtype = (Datasubtype)var5.next();
            ValiditySet validitySet = (ValiditySet)subtable.get(datasubtype);
            if (validitySet != null && validitySet.contains(code, value)) {
               return datasubtype;
            }
         }
      }

      return null;
   }

   private static class ValidityData {
      static final Map data;

      private static void addRange(String string, Set subvalues) {
         string = AsciiUtil.toLowerString(string);
         int pos = string.indexOf(126);
         if (pos < 0) {
            subvalues.add(string);
         } else {
            StringRange.expand(string.substring(0, pos), string.substring(pos + 1), false, subvalues);
         }

      }

      static {
         Map _data = new EnumMap(Datatype.class);
         UResourceBundle suppData = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
         UResourceBundle validityInfo = suppData.get("idValidity");
         UResourceBundleIterator datatypeIterator = validityInfo.getIterator();

         while(datatypeIterator.hasNext()) {
            UResourceBundle datatype = datatypeIterator.next();
            String rawKey = datatype.getKey();
            Datatype key = ValidIdentifiers.Datatype.valueOf(rawKey);
            Map values = new EnumMap(Datasubtype.class);

            Datasubtype subkey;
            HashSet subvalues;
            for(UResourceBundleIterator datasubtypeIterator = datatype.getIterator(); datasubtypeIterator.hasNext(); values.put(subkey, new ValiditySet(subvalues, key == ValidIdentifiers.Datatype.subdivision))) {
               UResourceBundle datasubtype = datasubtypeIterator.next();
               String rawsubkey = datasubtype.getKey();
               subkey = ValidIdentifiers.Datasubtype.valueOf(rawsubkey);
               subvalues = new HashSet();
               if (datasubtype.getType() == 0) {
                  addRange(datasubtype.getString(), subvalues);
               } else {
                  String[] var13 = datasubtype.getStringArray();
                  int var14 = var13.length;

                  for(int var15 = 0; var15 < var14; ++var15) {
                     String string = var13[var15];
                     addRange(string, subvalues);
                  }
               }
            }

            _data.put(key, Collections.unmodifiableMap(values));
         }

         data = Collections.unmodifiableMap(_data);
      }
   }

   public static class ValiditySet {
      public final Set regularData;
      public final Map subdivisionData;

      public ValiditySet(Set plainData, boolean makeMap) {
         if (makeMap) {
            HashMap _subdivisionData = new HashMap();

            String subdivision;
            Object oldSet;
            for(Iterator var4 = plainData.iterator(); var4.hasNext(); ((Set)oldSet).add(subdivision)) {
               String s = (String)var4.next();
               int pos = s.indexOf(45);
               int pos2 = pos + 1;
               if (pos < 0) {
                  pos2 = pos = s.charAt(0) < 'A' ? 3 : 2;
               }

               String key = s.substring(0, pos);
               subdivision = s.substring(pos2);
               oldSet = (Set)_subdivisionData.get(key);
               if (oldSet == null) {
                  _subdivisionData.put(key, oldSet = new HashSet());
               }
            }

            this.regularData = null;
            HashMap _subdivisionData2 = new HashMap();
            Iterator var12 = _subdivisionData.entrySet().iterator();

            while(var12.hasNext()) {
               Map.Entry e = (Map.Entry)var12.next();
               Set value = (Set)e.getValue();
               Set set = value.size() == 1 ? Collections.singleton(value.iterator().next()) : Collections.unmodifiableSet(value);
               _subdivisionData2.put(e.getKey(), set);
            }

            this.subdivisionData = Collections.unmodifiableMap(_subdivisionData2);
         } else {
            this.regularData = Collections.unmodifiableSet(plainData);
            this.subdivisionData = null;
         }

      }

      public boolean contains(String code) {
         if (this.regularData != null) {
            return this.regularData.contains(code);
         } else {
            int pos = code.indexOf(45);
            String key = code.substring(0, pos);
            String value = code.substring(pos + 1);
            return this.contains(key, value);
         }
      }

      public boolean contains(String key, String value) {
         Set oldSet = (Set)this.subdivisionData.get(key);
         return oldSet != null && oldSet.contains(value);
      }

      public String toString() {
         return this.regularData != null ? this.regularData.toString() : this.subdivisionData.toString();
      }
   }

   public static enum Datasubtype {
      deprecated,
      private_use,
      regular,
      special,
      unknown,
      macroregion;
   }

   public static enum Datatype {
      currency,
      language,
      region,
      script,
      subdivision,
      unit,
      variant,
      u,
      t,
      x,
      illegal;
   }
}
