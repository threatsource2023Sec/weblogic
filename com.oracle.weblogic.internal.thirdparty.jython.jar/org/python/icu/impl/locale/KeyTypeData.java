package org.python.icu.impl.locale;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import java.util.regex.Pattern;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.util.Output;
import org.python.icu.util.UResourceBundle;
import org.python.icu.util.UResourceBundleIterator;

public class KeyTypeData {
   static Set DEPRECATED_KEYS = Collections.emptySet();
   static Map VALUE_TYPES = Collections.emptyMap();
   static Map DEPRECATED_KEY_TYPES = Collections.emptyMap();
   private static final Object[][] KEY_DATA = new Object[0][];
   private static final Map KEYMAP = new HashMap();
   private static Map BCP47_KEYS;

   public static String toBcpKey(String key) {
      key = AsciiUtil.toLowerString(key);
      KeyData keyData = (KeyData)KEYMAP.get(key);
      return keyData != null ? keyData.bcpId : null;
   }

   public static String toLegacyKey(String key) {
      key = AsciiUtil.toLowerString(key);
      KeyData keyData = (KeyData)KEYMAP.get(key);
      return keyData != null ? keyData.legacyId : null;
   }

   public static String toBcpType(String key, String type, Output isKnownKey, Output isSpecialType) {
      if (isKnownKey != null) {
         isKnownKey.value = false;
      }

      if (isSpecialType != null) {
         isSpecialType.value = false;
      }

      key = AsciiUtil.toLowerString(key);
      type = AsciiUtil.toLowerString(type);
      KeyData keyData = (KeyData)KEYMAP.get(key);
      if (keyData != null) {
         if (isKnownKey != null) {
            isKnownKey.value = Boolean.TRUE;
         }

         Type t = (Type)keyData.typeMap.get(type);
         if (t != null) {
            return t.bcpId;
         }

         if (keyData.specialTypes != null) {
            Iterator var6 = keyData.specialTypes.iterator();

            while(var6.hasNext()) {
               SpecialType st = (SpecialType)var6.next();
               if (st.handler.isWellFormed(type)) {
                  if (isSpecialType != null) {
                     isSpecialType.value = true;
                  }

                  return st.handler.canonicalize(type);
               }
            }
         }
      }

      return null;
   }

   public static String toLegacyType(String key, String type, Output isKnownKey, Output isSpecialType) {
      if (isKnownKey != null) {
         isKnownKey.value = false;
      }

      if (isSpecialType != null) {
         isSpecialType.value = false;
      }

      key = AsciiUtil.toLowerString(key);
      type = AsciiUtil.toLowerString(type);
      KeyData keyData = (KeyData)KEYMAP.get(key);
      if (keyData != null) {
         if (isKnownKey != null) {
            isKnownKey.value = Boolean.TRUE;
         }

         Type t = (Type)keyData.typeMap.get(type);
         if (t != null) {
            return t.legacyId;
         }

         if (keyData.specialTypes != null) {
            Iterator var6 = keyData.specialTypes.iterator();

            while(var6.hasNext()) {
               SpecialType st = (SpecialType)var6.next();
               if (st.handler.isWellFormed(type)) {
                  if (isSpecialType != null) {
                     isSpecialType.value = true;
                  }

                  return st.handler.canonicalize(type);
               }
            }
         }
      }

      return null;
   }

   private static void initFromResourceBundle() {
      UResourceBundle keyTypeDataRes = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "keyTypeData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
      getKeyInfo(keyTypeDataRes.get("keyInfo"));
      getTypeInfo(keyTypeDataRes.get("typeInfo"));
      UResourceBundle keyMapRes = keyTypeDataRes.get("keyMap");
      UResourceBundle typeMapRes = keyTypeDataRes.get("typeMap");
      UResourceBundle typeAliasRes = null;
      UResourceBundle bcpTypeAliasRes = null;

      try {
         typeAliasRes = keyTypeDataRes.get("typeAlias");
      } catch (MissingResourceException var32) {
      }

      try {
         bcpTypeAliasRes = keyTypeDataRes.get("bcpTypeAlias");
      } catch (MissingResourceException var31) {
      }

      UResourceBundleIterator keyMapItr = keyMapRes.getIterator();
      Map _Bcp47Keys = new LinkedHashMap();

      while(keyMapItr.hasNext()) {
         UResourceBundle keyMapEntry = keyMapItr.next();
         String legacyKeyId = keyMapEntry.getKey();
         String bcpKeyId = keyMapEntry.getString();
         boolean hasSameKey = false;
         if (bcpKeyId.length() == 0) {
            bcpKeyId = legacyKeyId;
            hasSameKey = true;
         }

         LinkedHashSet _bcp47Types = new LinkedHashSet();
         _Bcp47Keys.put(bcpKeyId, Collections.unmodifiableSet(_bcp47Types));
         boolean isTZ = legacyKeyId.equals("timezone");
         Map typeAliasMap = null;
         String from;
         if (typeAliasRes != null) {
            UResourceBundle typeAliasResByKey = null;

            try {
               typeAliasResByKey = typeAliasRes.get(legacyKeyId);
            } catch (MissingResourceException var29) {
            }

            if (typeAliasResByKey != null) {
               typeAliasMap = new HashMap();

               String from;
               Object aliasSet;
               for(UResourceBundleIterator typeAliasResItr = typeAliasResByKey.getIterator(); typeAliasResItr.hasNext(); ((Set)aliasSet).add(from)) {
                  UResourceBundle typeAliasDataEntry = typeAliasResItr.next();
                  from = typeAliasDataEntry.getKey();
                  from = typeAliasDataEntry.getString();
                  if (isTZ) {
                     from = from.replace(':', '/');
                  }

                  aliasSet = (Set)typeAliasMap.get(from);
                  if (aliasSet == null) {
                     aliasSet = new HashSet();
                     typeAliasMap.put(from, aliasSet);
                  }
               }
            }
         }

         Map bcpTypeAliasMap = null;
         UResourceBundle bcpTypeAliasDataEntry;
         if (bcpTypeAliasRes != null) {
            UResourceBundle bcpTypeAliasResByKey = null;

            try {
               bcpTypeAliasResByKey = bcpTypeAliasRes.get(bcpKeyId);
            } catch (MissingResourceException var30) {
            }

            if (bcpTypeAliasResByKey != null) {
               bcpTypeAliasMap = new HashMap();

               Object aliasSet;
               for(UResourceBundleIterator bcpTypeAliasResItr = bcpTypeAliasResByKey.getIterator(); bcpTypeAliasResItr.hasNext(); ((Set)aliasSet).add(from)) {
                  bcpTypeAliasDataEntry = bcpTypeAliasResItr.next();
                  from = bcpTypeAliasDataEntry.getKey();
                  String to = bcpTypeAliasDataEntry.getString();
                  aliasSet = (Set)bcpTypeAliasMap.get(to);
                  if (aliasSet == null) {
                     aliasSet = new HashSet();
                     bcpTypeAliasMap.put(to, aliasSet);
                  }
               }
            }
         }

         Map typeDataMap = new HashMap();
         EnumSet specialTypeSet = null;
         bcpTypeAliasDataEntry = null;

         try {
            bcpTypeAliasDataEntry = typeMapRes.get(legacyKeyId);
         } catch (MissingResourceException var33) {
            assert false;
         }

         if (bcpTypeAliasDataEntry != null) {
            UResourceBundleIterator typeMapResByKeyItr = bcpTypeAliasDataEntry.getIterator();

            label171:
            while(true) {
               while(true) {
                  if (!typeMapResByKeyItr.hasNext()) {
                     break label171;
                  }

                  UResourceBundle typeMapEntry = typeMapResByKeyItr.next();
                  String legacyTypeId = typeMapEntry.getKey();
                  String bcpTypeId = typeMapEntry.getString();
                  char first = legacyTypeId.charAt(0);
                  boolean isSpecialType = '9' < first && first < 'a' && bcpTypeId.length() == 0;
                  if (isSpecialType) {
                     if (specialTypeSet == null) {
                        specialTypeSet = EnumSet.noneOf(SpecialType.class);
                     }

                     specialTypeSet.add(KeyTypeData.SpecialType.valueOf(legacyTypeId));
                     _bcp47Types.add(legacyTypeId);
                  } else {
                     if (isTZ) {
                        legacyTypeId = legacyTypeId.replace(':', '/');
                     }

                     boolean hasSameType = false;
                     if (bcpTypeId.length() == 0) {
                        bcpTypeId = legacyTypeId;
                        hasSameType = true;
                     }

                     _bcp47Types.add(bcpTypeId);
                     Type t = new Type(legacyTypeId, bcpTypeId);
                     typeDataMap.put(AsciiUtil.toLowerString(legacyTypeId), t);
                     if (!hasSameType) {
                        typeDataMap.put(AsciiUtil.toLowerString(bcpTypeId), t);
                     }

                     Set bcpTypeAliasSet;
                     Iterator var27;
                     String alias;
                     if (typeAliasMap != null) {
                        bcpTypeAliasSet = (Set)typeAliasMap.get(legacyTypeId);
                        if (bcpTypeAliasSet != null) {
                           var27 = bcpTypeAliasSet.iterator();

                           while(var27.hasNext()) {
                              alias = (String)var27.next();
                              typeDataMap.put(AsciiUtil.toLowerString(alias), t);
                           }
                        }
                     }

                     if (bcpTypeAliasMap != null) {
                        bcpTypeAliasSet = (Set)bcpTypeAliasMap.get(bcpTypeId);
                        if (bcpTypeAliasSet != null) {
                           var27 = bcpTypeAliasSet.iterator();

                           while(var27.hasNext()) {
                              alias = (String)var27.next();
                              typeDataMap.put(AsciiUtil.toLowerString(alias), t);
                           }
                        }
                     }
                  }
               }
            }
         }

         KeyData keyData = new KeyData(legacyKeyId, bcpKeyId, typeDataMap, specialTypeSet);
         KEYMAP.put(AsciiUtil.toLowerString(legacyKeyId), keyData);
         if (!hasSameKey) {
            KEYMAP.put(AsciiUtil.toLowerString(bcpKeyId), keyData);
         }
      }

      BCP47_KEYS = Collections.unmodifiableMap(_Bcp47Keys);
   }

   private static void getKeyInfo(UResourceBundle keyInfoRes) {
      Set _deprecatedKeys = new LinkedHashSet();
      Map _valueTypes = new LinkedHashMap();
      UResourceBundleIterator keyInfoIt = keyInfoRes.getIterator();

      while(keyInfoIt.hasNext()) {
         UResourceBundle keyInfoEntry = keyInfoIt.next();
         String key = keyInfoEntry.getKey();
         KeyInfoType keyInfo = KeyTypeData.KeyInfoType.valueOf(key);
         UResourceBundleIterator keyInfoIt2 = keyInfoEntry.getIterator();

         while(keyInfoIt2.hasNext()) {
            UResourceBundle keyInfoEntry2 = keyInfoIt2.next();
            String key2 = keyInfoEntry2.getKey();
            String value2 = keyInfoEntry2.getString();
            switch (keyInfo) {
               case deprecated:
                  _deprecatedKeys.add(key2);
                  break;
               case valueType:
                  _valueTypes.put(key2, KeyTypeData.ValueType.valueOf(value2));
            }
         }
      }

      DEPRECATED_KEYS = Collections.unmodifiableSet(_deprecatedKeys);
      VALUE_TYPES = Collections.unmodifiableMap(_valueTypes);
   }

   private static void getTypeInfo(UResourceBundle typeInfoRes) {
      Map _deprecatedKeyTypes = new LinkedHashMap();
      UResourceBundleIterator keyInfoIt = typeInfoRes.getIterator();

      while(keyInfoIt.hasNext()) {
         UResourceBundle keyInfoEntry = keyInfoIt.next();
         String key = keyInfoEntry.getKey();
         TypeInfoType typeInfo = KeyTypeData.TypeInfoType.valueOf(key);
         UResourceBundleIterator keyInfoIt2 = keyInfoEntry.getIterator();

         while(keyInfoIt2.hasNext()) {
            UResourceBundle keyInfoEntry2 = keyInfoIt2.next();
            String key2 = keyInfoEntry2.getKey();
            Set _deprecatedTypes = new LinkedHashSet();
            UResourceBundleIterator keyInfoIt3 = keyInfoEntry2.getIterator();

            while(keyInfoIt3.hasNext()) {
               UResourceBundle keyInfoEntry3 = keyInfoIt3.next();
               String key3 = keyInfoEntry3.getKey();
               switch (typeInfo) {
                  case deprecated:
                     _deprecatedTypes.add(key3);
               }
            }

            _deprecatedKeyTypes.put(key2, Collections.unmodifiableSet(_deprecatedTypes));
         }
      }

      DEPRECATED_KEY_TYPES = Collections.unmodifiableMap(_deprecatedKeyTypes);
   }

   private static void initFromTables() {
      Object[][] var0 = KEY_DATA;
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         Object[] keyDataEntry = var0[var2];
         String legacyKeyId = (String)keyDataEntry[0];
         String bcpKeyId = (String)keyDataEntry[1];
         String[][] typeData = (String[][])((String[][])keyDataEntry[2]);
         String[][] typeAliasData = (String[][])((String[][])keyDataEntry[3]);
         String[][] bcpTypeAliasData = (String[][])((String[][])keyDataEntry[4]);
         boolean hasSameKey = false;
         if (bcpKeyId == null) {
            bcpKeyId = legacyKeyId;
            hasSameKey = true;
         }

         Map typeAliasMap = null;
         int var13;
         String from;
         if (typeAliasData != null) {
            typeAliasMap = new HashMap();
            String[][] var11 = typeAliasData;
            int var12 = typeAliasData.length;

            for(var13 = 0; var13 < var12; ++var13) {
               String[] typeAliasDataEntry = var11[var13];
               String from = typeAliasDataEntry[0];
               from = typeAliasDataEntry[1];
               Set aliasSet = (Set)typeAliasMap.get(from);
               if (aliasSet == null) {
                  aliasSet = new HashSet();
                  typeAliasMap.put(from, aliasSet);
               }

               ((Set)aliasSet).add(from);
            }
         }

         Map bcpTypeAliasMap = null;
         if (bcpTypeAliasData != null) {
            bcpTypeAliasMap = new HashMap();
            String[][] var28 = bcpTypeAliasData;
            var13 = bcpTypeAliasData.length;

            for(int var31 = 0; var31 < var13; ++var31) {
               String[] bcpTypeAliasDataEntry = var28[var31];
               from = bcpTypeAliasDataEntry[0];
               String to = bcpTypeAliasDataEntry[1];
               Set aliasSet = (Set)bcpTypeAliasMap.get(to);
               if (aliasSet == null) {
                  aliasSet = new HashSet();
                  bcpTypeAliasMap.put(to, aliasSet);
               }

               ((Set)aliasSet).add(from);
            }
         }

         assert typeData != null;

         Map typeDataMap = new HashMap();
         Set specialTypeSet = null;
         String[][] var32 = typeData;
         int var35 = typeData.length;

         for(int var37 = 0; var37 < var35; ++var37) {
            String[] typeDataEntry = var32[var37];
            String legacyTypeId = typeDataEntry[0];
            String bcpTypeId = typeDataEntry[1];
            boolean isSpecialType = false;
            SpecialType[] var21 = KeyTypeData.SpecialType.values();
            int var22 = var21.length;

            for(int var23 = 0; var23 < var22; ++var23) {
               SpecialType st = var21[var23];
               if (legacyTypeId.equals(st.toString())) {
                  isSpecialType = true;
                  if (specialTypeSet == null) {
                     specialTypeSet = new HashSet();
                  }

                  specialTypeSet.add(st);
                  break;
               }
            }

            if (!isSpecialType) {
               boolean hasSameType = false;
               if (bcpTypeId == null) {
                  bcpTypeId = legacyTypeId;
                  hasSameType = true;
               }

               Type t = new Type(legacyTypeId, bcpTypeId);
               typeDataMap.put(AsciiUtil.toLowerString(legacyTypeId), t);
               if (!hasSameType) {
                  typeDataMap.put(AsciiUtil.toLowerString(bcpTypeId), t);
               }

               Set typeAliasSet = (Set)typeAliasMap.get(legacyTypeId);
               if (typeAliasSet != null) {
                  Iterator var44 = typeAliasSet.iterator();

                  while(var44.hasNext()) {
                     String alias = (String)var44.next();
                     typeDataMap.put(AsciiUtil.toLowerString(alias), t);
                  }
               }

               Set bcpTypeAliasSet = (Set)bcpTypeAliasMap.get(bcpTypeId);
               if (bcpTypeAliasSet != null) {
                  Iterator var46 = bcpTypeAliasSet.iterator();

                  while(var46.hasNext()) {
                     String alias = (String)var46.next();
                     typeDataMap.put(AsciiUtil.toLowerString(alias), t);
                  }
               }
            }
         }

         EnumSet specialTypes = null;
         if (specialTypeSet != null) {
            specialTypes = EnumSet.copyOf(specialTypeSet);
         }

         KeyData keyData = new KeyData(legacyKeyId, bcpKeyId, typeDataMap, specialTypes);
         KEYMAP.put(AsciiUtil.toLowerString(legacyKeyId), keyData);
         if (!hasSameKey) {
            KEYMAP.put(AsciiUtil.toLowerString(bcpKeyId), keyData);
         }
      }

   }

   public static Set getBcp47Keys() {
      return BCP47_KEYS.keySet();
   }

   public static Set getBcp47KeyTypes(String key) {
      return (Set)BCP47_KEYS.get(key);
   }

   public static boolean isDeprecated(String key) {
      return DEPRECATED_KEYS.contains(key);
   }

   public static boolean isDeprecated(String key, String type) {
      Set deprecatedTypes = (Set)DEPRECATED_KEY_TYPES.get(key);
      return deprecatedTypes == null ? false : deprecatedTypes.contains(type);
   }

   public static ValueType getValueType(String key) {
      ValueType type = (ValueType)VALUE_TYPES.get(key);
      return type == null ? KeyTypeData.ValueType.single : type;
   }

   static {
      initFromResourceBundle();
   }

   private static enum TypeInfoType {
      deprecated;
   }

   private static enum KeyInfoType {
      deprecated,
      valueType;
   }

   private static class Type {
      String legacyId;
      String bcpId;

      Type(String legacyId, String bcpId) {
         this.legacyId = legacyId;
         this.bcpId = bcpId;
      }
   }

   private static class KeyData {
      String legacyId;
      String bcpId;
      Map typeMap;
      EnumSet specialTypes;

      KeyData(String legacyId, String bcpId, Map typeMap, EnumSet specialTypes) {
         this.legacyId = legacyId;
         this.bcpId = bcpId;
         this.typeMap = typeMap;
         this.specialTypes = specialTypes;
      }
   }

   private static enum SpecialType {
      CODEPOINTS(new CodepointsTypeHandler()),
      REORDER_CODE(new ReorderCodeTypeHandler()),
      RG_KEY_VALUE(new RgKeyValueTypeHandler()),
      SUBDIVISION_CODE(new SubdivisionKeyValueTypeHandler()),
      PRIVATE_USE(new PrivateUseKeyValueTypeHandler());

      SpecialTypeHandler handler;

      private SpecialType(SpecialTypeHandler handler) {
         this.handler = handler;
      }
   }

   private static class PrivateUseKeyValueTypeHandler extends SpecialTypeHandler {
      private static final Pattern pat = Pattern.compile("[a-zA-Z0-9]{3,8}(-[a-zA-Z0-9]{3,8})*");

      private PrivateUseKeyValueTypeHandler() {
         super(null);
      }

      boolean isWellFormed(String value) {
         return pat.matcher(value).matches();
      }

      // $FF: synthetic method
      PrivateUseKeyValueTypeHandler(Object x0) {
         this();
      }
   }

   private static class SubdivisionKeyValueTypeHandler extends SpecialTypeHandler {
      private static final Pattern pat = Pattern.compile("([a-zA-Z]{2}|[0-9]{3})");

      private SubdivisionKeyValueTypeHandler() {
         super(null);
      }

      boolean isWellFormed(String value) {
         return pat.matcher(value).matches();
      }

      // $FF: synthetic method
      SubdivisionKeyValueTypeHandler(Object x0) {
         this();
      }
   }

   private static class RgKeyValueTypeHandler extends SpecialTypeHandler {
      private static final Pattern pat = Pattern.compile("([a-zA-Z]{2}|[0-9]{3})[zZ]{4}");

      private RgKeyValueTypeHandler() {
         super(null);
      }

      boolean isWellFormed(String value) {
         return pat.matcher(value).matches();
      }

      // $FF: synthetic method
      RgKeyValueTypeHandler(Object x0) {
         this();
      }
   }

   private static class ReorderCodeTypeHandler extends SpecialTypeHandler {
      private static final Pattern pat = Pattern.compile("[a-zA-Z]{3,8}(-[a-zA-Z]{3,8})*");

      private ReorderCodeTypeHandler() {
         super(null);
      }

      boolean isWellFormed(String value) {
         return pat.matcher(value).matches();
      }

      // $FF: synthetic method
      ReorderCodeTypeHandler(Object x0) {
         this();
      }
   }

   private static class CodepointsTypeHandler extends SpecialTypeHandler {
      private static final Pattern pat = Pattern.compile("[0-9a-fA-F]{4,6}(-[0-9a-fA-F]{4,6})*");

      private CodepointsTypeHandler() {
         super(null);
      }

      boolean isWellFormed(String value) {
         return pat.matcher(value).matches();
      }

      // $FF: synthetic method
      CodepointsTypeHandler(Object x0) {
         this();
      }
   }

   private abstract static class SpecialTypeHandler {
      private SpecialTypeHandler() {
      }

      abstract boolean isWellFormed(String var1);

      String canonicalize(String value) {
         return AsciiUtil.toLowerString(value);
      }

      // $FF: synthetic method
      SpecialTypeHandler(Object x0) {
         this();
      }
   }

   public static enum ValueType {
      single,
      multiple,
      incremental,
      any;
   }
}
