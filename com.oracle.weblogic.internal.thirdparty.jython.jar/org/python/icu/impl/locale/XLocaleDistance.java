package org.python.icu.impl.locale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.Row;
import org.python.icu.impl.Utility;
import org.python.icu.text.LocaleDisplayNames;
import org.python.icu.util.LocaleMatcher;
import org.python.icu.util.Output;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundleIterator;

public class XLocaleDistance {
   static final boolean PRINT_OVERRIDES = false;
   public static final int ABOVE_THRESHOLD = 100;
   /** @deprecated */
   @Deprecated
   public static final String ANY = "�";
   static final LocaleDisplayNames english;
   static final XCldrStub.Multimap CONTAINER_TO_CONTAINED;
   static final XCldrStub.Multimap CONTAINER_TO_CONTAINED_FINAL;
   private static final Set ALL_FINAL_REGIONS;
   private final DistanceTable languageDesired2Supported;
   private final RegionMapper regionMapper;
   private final int defaultLanguageDistance;
   private final int defaultScriptDistance;
   private final int defaultRegionDistance;
   private static final XLocaleDistance DEFAULT;

   private static String fixAny(String string) {
      return "*".equals(string) ? "�" : string;
   }

   private static List xGetLanguageMatcherData() {
      List distanceList = new ArrayList();
      ICUResourceBundle suppData = LocaleMatcher.getICUSupplementalData();
      ICUResourceBundle languageMatchingNew = suppData.findTopLevel("languageMatchingNew");
      ICUResourceBundle written = (ICUResourceBundle)languageMatchingNew.get("written");
      UResourceBundleIterator iter = written.getIterator();

      while(iter.hasNext()) {
         ICUResourceBundle item = (ICUResourceBundle)iter.next();
         boolean oneway = item.getSize() > 3 && "1".equals(item.getString(3));
         distanceList.add((Row.R4)Row.of(item.getString(0), item.getString(1), Integer.parseInt(item.getString(2)), oneway).freeze());
      }

      return Collections.unmodifiableList(distanceList);
   }

   private static Set xGetParadigmLocales() {
      ICUResourceBundle suppData = LocaleMatcher.getICUSupplementalData();
      ICUResourceBundle languageMatchingInfo = suppData.findTopLevel("languageMatchingInfo");
      ICUResourceBundle writtenParadigmLocales = (ICUResourceBundle)languageMatchingInfo.get("written").get("paradigmLocales");
      HashSet paradigmLocales = new HashSet(Arrays.asList(writtenParadigmLocales.getStringArray()));
      return Collections.unmodifiableSet(paradigmLocales);
   }

   private static Map xGetMatchVariables() {
      ICUResourceBundle suppData = LocaleMatcher.getICUSupplementalData();
      ICUResourceBundle languageMatchingInfo = suppData.findTopLevel("languageMatchingInfo");
      ICUResourceBundle writtenMatchVariables = (ICUResourceBundle)languageMatchingInfo.get("written").get("matchVariable");
      HashMap matchVariables = new HashMap();
      Enumeration enumer = writtenMatchVariables.getKeys();

      while(enumer.hasMoreElements()) {
         String key = (String)enumer.nextElement();
         matchVariables.put(key, writtenMatchVariables.getString(key));
      }

      return Collections.unmodifiableMap(matchVariables);
   }

   private static XCldrStub.Multimap xGetContainment() {
      XCldrStub.TreeMultimap containment = XCldrStub.TreeMultimap.create();
      containment.putAll("001", new String[]{"019", "002", "150", "142", "009"}).putAll((Object)"011", (Object[])("BF", "BJ", "CI", "CV", "GH", "GM", "GN", "GW", "LR", "ML", "MR", "NE", "NG", "SH", "SL", "SN", "TG")).putAll((Object)"013", (Object[])("BZ", "CR", "GT", "HN", "MX", "NI", "PA", "SV")).putAll((Object)"014", (Object[])("BI", "DJ", "ER", "ET", "KE", "KM", "MG", "MU", "MW", "MZ", "RE", "RW", "SC", "SO", "SS", "TZ", "UG", "YT", "ZM", "ZW")).putAll((Object)"142", (Object[])("145", "143", "030", "034", "035")).putAll((Object)"143", (Object[])("TM", "TJ", "KG", "KZ", "UZ")).putAll((Object)"145", (Object[])("AE", "AM", "AZ", "BH", "CY", "GE", "IL", "IQ", "JO", "KW", "LB", "OM", "PS", "QA", "SA", "SY", "TR", "YE", "NT", "YD")).putAll((Object)"015", (Object[])("DZ", "EG", "EH", "LY", "MA", "SD", "TN", "EA", "IC")).putAll((Object)"150", (Object[])("154", "155", "151", "039")).putAll((Object)"151", (Object[])("BG", "BY", "CZ", "HU", "MD", "PL", "RO", "RU", "SK", "UA", "SU")).putAll((Object)"154", (Object[])("GG", "IM", "JE", "AX", "DK", "EE", "FI", "FO", "GB", "IE", "IS", "LT", "LV", "NO", "SE", "SJ")).putAll((Object)"155", (Object[])("AT", "BE", "CH", "DE", "FR", "LI", "LU", "MC", "NL", "DD", "FX")).putAll((Object)"017", (Object[])("AO", "CD", "CF", "CG", "CM", "GA", "GQ", "ST", "TD", "ZR")).putAll((Object)"018", (Object[])("BW", "LS", "NA", "SZ", "ZA")).putAll((Object)"019", (Object[])("021", "013", "029", "005", "003", "419")).putAll((Object)"002", (Object[])("015", "011", "017", "014", "018")).putAll((Object)"021", (Object[])("BM", "CA", "GL", "PM", "US")).putAll((Object)"029", (Object[])("AG", "AI", "AW", "BB", "BL", "BQ", "BS", "CU", "CW", "DM", "DO", "GD", "GP", "HT", "JM", "KN", "KY", "LC", "MF", "MQ", "MS", "PR", "SX", "TC", "TT", "VC", "VG", "VI", "AN")).putAll((Object)"003", (Object[])("021", "013", "029")).putAll((Object)"030", (Object[])("CN", "HK", "JP", "KP", "KR", "MN", "MO", "TW")).putAll((Object)"035", (Object[])("BN", "ID", "KH", "LA", "MM", "MY", "PH", "SG", "TH", "TL", "VN", "BU", "TP")).putAll((Object)"039", (Object[])("AD", "AL", "BA", "ES", "GI", "GR", "HR", "IT", "ME", "MK", "MT", "RS", "PT", "SI", "SM", "VA", "XK", "CS", "YU")).putAll((Object)"419", (Object[])("013", "029", "005")).putAll((Object)"005", (Object[])("AR", "BO", "BR", "CL", "CO", "EC", "FK", "GF", "GY", "PE", "PY", "SR", "UY", "VE")).putAll((Object)"053", (Object[])("AU", "NF", "NZ")).putAll((Object)"054", (Object[])("FJ", "NC", "PG", "SB", "VU")).putAll((Object)"057", (Object[])("FM", "GU", "KI", "MH", "MP", "NR", "PW")).putAll((Object)"061", (Object[])("AS", "CK", "NU", "PF", "PN", "TK", "TO", "TV", "WF", "WS")).putAll((Object)"034", (Object[])("AF", "BD", "BT", "IN", "IR", "LK", "MV", "NP", "PK")).putAll((Object)"009", (Object[])("053", "054", "057", "061", "QO")).putAll((Object)"QO", (Object[])("AQ", "BV", "CC", "CX", "GS", "HM", "IO", "TF", "UM", "AC", "CP", "DG", "TA"));
      XCldrStub.TreeMultimap containmentResolved = XCldrStub.TreeMultimap.create();
      fill("001", containment, containmentResolved);
      return XCldrStub.ImmutableMultimap.copyOf(containmentResolved);
   }

   private static Set fill(String region, XCldrStub.TreeMultimap containment, XCldrStub.Multimap toAddTo) {
      Set contained = containment.get(region);
      if (contained == null) {
         return Collections.emptySet();
      } else {
         toAddTo.putAll((Object)region, (Collection)contained);
         Iterator var4 = contained.iterator();

         while(var4.hasNext()) {
            String subregion = (String)var4.next();
            toAddTo.putAll((Object)region, (Collection)fill(subregion, containment, toAddTo));
         }

         return toAddTo.get(region);
      }
   }

   public XLocaleDistance(DistanceTable datadistancetable2, RegionMapper regionMapper) {
      this.languageDesired2Supported = datadistancetable2;
      this.regionMapper = regionMapper;
      StringDistanceNode languageNode = (StringDistanceNode)((Map)((StringDistanceTable)this.languageDesired2Supported).subtables.get("�")).get("�");
      this.defaultLanguageDistance = languageNode.distance;
      StringDistanceNode scriptNode = (StringDistanceNode)((Map)((StringDistanceTable)languageNode.distanceTable).subtables.get("�")).get("�");
      this.defaultScriptDistance = scriptNode.distance;
      DistanceNode regionNode = (DistanceNode)((Map)((StringDistanceTable)scriptNode.distanceTable).subtables.get("�")).get("�");
      this.defaultRegionDistance = regionNode.distance;
   }

   private static Map newMap() {
      return new TreeMap();
   }

   public int distance(ULocale desired, ULocale supported, int threshold, DistanceOption distanceOption) {
      XLikelySubtags.LSR supportedLSR = XLikelySubtags.LSR.fromMaximalized(supported);
      XLikelySubtags.LSR desiredLSR = XLikelySubtags.LSR.fromMaximalized(desired);
      return this.distanceRaw(desiredLSR, supportedLSR, threshold, distanceOption);
   }

   public int distanceRaw(XLikelySubtags.LSR desired, XLikelySubtags.LSR supported, int threshold, DistanceOption distanceOption) {
      return this.distanceRaw(desired.language, supported.language, desired.script, supported.script, desired.region, supported.region, threshold, distanceOption);
   }

   public int distanceRaw(String desiredLang, String supportedlang, String desiredScript, String supportedScript, String desiredRegion, String supportedRegion, int threshold, DistanceOption distanceOption) {
      Output subtable = new Output();
      int distance = this.languageDesired2Supported.getDistance(desiredLang, supportedlang, subtable, true);
      boolean scriptFirst = distanceOption == XLocaleDistance.DistanceOption.SCRIPT_FIRST;
      if (scriptFirst) {
         distance >>= 2;
      }

      if (distance < 0) {
         distance = 0;
      } else if (distance >= threshold) {
         return 100;
      }

      int scriptDistance = ((DistanceTable)subtable.value).getDistance(desiredScript, supportedScript, subtable, true);
      if (scriptFirst) {
         scriptDistance >>= 1;
      }

      distance += scriptDistance;
      if (distance >= threshold) {
         return 100;
      } else if (desiredRegion.equals(supportedRegion)) {
         return distance;
      } else {
         String desiredPartition = this.regionMapper.toId(desiredRegion);
         String supportedPartition = this.regionMapper.toId(supportedRegion);
         Collection desiredPartitions = desiredPartition.isEmpty() ? this.regionMapper.macroToPartitions.get(desiredRegion) : null;
         Collection supportedPartitions = supportedPartition.isEmpty() ? this.regionMapper.macroToPartitions.get(supportedRegion) : null;
         int subdistance;
         if (desiredPartitions == null && supportedPartitions == null) {
            subdistance = ((DistanceTable)subtable.value).getDistance(desiredPartition, supportedPartition, (Output)null, false);
         } else {
            subdistance = 0;
            if (desiredPartitions == null) {
               desiredPartitions = Collections.singleton(desiredPartition);
            }

            if (supportedPartitions == null) {
               supportedPartitions = Collections.singleton(supportedPartition);
            }

            Iterator var18 = desiredPartitions.iterator();

            while(var18.hasNext()) {
               String desiredPartition2 = (String)var18.next();
               Iterator var20 = supportedPartitions.iterator();

               while(var20.hasNext()) {
                  String supportedPartition2 = (String)var20.next();
                  int tempSubdistance = ((DistanceTable)subtable.value).getDistance(desiredPartition2, supportedPartition2, (Output)null, false);
                  if (subdistance < tempSubdistance) {
                     subdistance = tempSubdistance;
                  }
               }
            }
         }

         distance += subdistance;
         return distance >= threshold ? 100 : distance;
      }
   }

   public static XLocaleDistance getDefault() {
      return DEFAULT;
   }

   private static void printMatchXml(List desired, List supported, Integer distance, Boolean oneway) {
   }

   private static String fixedName(List match) {
      List alt = new ArrayList(match);
      int size = alt.size();

      assert size >= 1 && size <= 3;

      StringBuilder result = new StringBuilder();
      String language;
      if (size >= 3) {
         language = (String)alt.get(2);
         if (!language.equals("*") && !language.startsWith("$")) {
            result.append(english.regionDisplayName(language));
         } else {
            result.append(language);
         }
      }

      if (size >= 2) {
         language = (String)alt.get(1);
         if (language.equals("*")) {
            result.insert(0, language);
         } else {
            result.insert(0, english.scriptDisplayName(language));
         }
      }

      if (size >= 1) {
         language = (String)alt.get(0);
         if (language.equals("*")) {
            result.insert(0, language);
         } else {
            result.insert(0, english.languageDisplayName(language));
         }
      }

      return XCldrStub.CollectionUtilities.join(alt, "; ");
   }

   public static void add(StringDistanceTable languageDesired2Supported, List desired, List supported, int percentage) {
      int size = desired.size();
      if (size == supported.size() && size >= 1 && size <= 3) {
         String desiredLang = fixAny((String)desired.get(0));
         String supportedLang = fixAny((String)supported.get(0));
         if (size == 1) {
            languageDesired2Supported.addSubtable(desiredLang, supportedLang, percentage);
         } else {
            String desiredScript = fixAny((String)desired.get(1));
            String supportedScript = fixAny((String)supported.get(1));
            if (size == 2) {
               languageDesired2Supported.addSubtables(desiredLang, supportedLang, desiredScript, supportedScript, percentage);
            } else {
               String desiredRegion = fixAny((String)desired.get(2));
               String supportedRegion = fixAny((String)supported.get(2));
               languageDesired2Supported.addSubtables(desiredLang, supportedLang, desiredScript, supportedScript, desiredRegion, supportedRegion, percentage);
            }
         }

      } else {
         throw new IllegalArgumentException();
      }
   }

   public String toString() {
      return this.toString(false);
   }

   public String toString(boolean abbreviate) {
      return this.regionMapper + "\n" + this.languageDesired2Supported.toString(abbreviate);
   }

   static Set getContainingMacrosFor(Collection input, Set output) {
      output.clear();
      Iterator var2 = CONTAINER_TO_CONTAINED.asMap().entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         if (input.containsAll((Collection)entry.getValue())) {
            output.add(entry.getKey());
         }
      }

      return output;
   }

   public static XCldrStub.Multimap invertMap(Map map) {
      return XCldrStub.Multimaps.invertFrom((Map)XCldrStub.Multimaps.forMap(map), XCldrStub.LinkedHashMultimap.create());
   }

   public Set getParadigms() {
      return this.regionMapper.paradigms;
   }

   public int getDefaultLanguageDistance() {
      return this.defaultLanguageDistance;
   }

   public int getDefaultScriptDistance() {
      return this.defaultScriptDistance;
   }

   public int getDefaultRegionDistance() {
      return this.defaultRegionDistance;
   }

   /** @deprecated */
   @Deprecated
   public StringDistanceTable internalGetDistanceTable() {
      return (StringDistanceTable)this.languageDesired2Supported;
   }

   public static void main(String[] args) {
      DistanceTable table = getDefault().languageDesired2Supported;
      DistanceTable compactedTable = table.compact();
      if (!table.equals(compactedTable)) {
         throw new IllegalArgumentException("Compaction isn't equal");
      }
   }

   static {
      english = LocaleDisplayNames.getInstance(ULocale.ENGLISH);
      CONTAINER_TO_CONTAINED = xGetContainment();
      XCldrStub.Multimap containerToFinalContainedBuilder = XCldrStub.TreeMultimap.create();
      Iterator var1 = CONTAINER_TO_CONTAINED.asMap().entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry entry = (Map.Entry)var1.next();
         String container = (String)entry.getKey();
         Iterator var4 = ((Set)entry.getValue()).iterator();

         while(var4.hasNext()) {
            String contained = (String)var4.next();
            if (CONTAINER_TO_CONTAINED.get(contained) == null) {
               containerToFinalContainedBuilder.put(container, contained);
            }
         }
      }

      CONTAINER_TO_CONTAINED_FINAL = XCldrStub.ImmutableMultimap.copyOf(containerToFinalContainedBuilder);
      ALL_FINAL_REGIONS = XCldrStub.ImmutableSet.copyOf(CONTAINER_TO_CONTAINED_FINAL.get("001"));
      String[][] variableOverrides = new String[][]{{"$enUS", "AS+GU+MH+MP+PR+UM+US+VI"}, {"$cnsar", "HK+MO"}, {"$americas", "019"}, {"$maghreb", "MA+DZ+TN+LY+MR+EH"}};
      String[] paradigmRegions = new String[]{"en", "en-GB", "es", "es-419", "pt-BR", "pt-PT"};
      String[][] regionRuleOverrides = new String[][]{{"ar_*_$maghreb", "ar_*_$maghreb", "96"}, {"ar_*_$!maghreb", "ar_*_$!maghreb", "96"}, {"ar_*_*", "ar_*_*", "95"}, {"en_*_$enUS", "en_*_$enUS", "96"}, {"en_*_$!enUS", "en_*_$!enUS", "96"}, {"en_*_*", "en_*_*", "95"}, {"es_*_$americas", "es_*_$americas", "96"}, {"es_*_$!americas", "es_*_$!americas", "96"}, {"es_*_*", "es_*_*", "95"}, {"pt_*_$americas", "pt_*_$americas", "96"}, {"pt_*_$!americas", "pt_*_$!americas", "96"}, {"pt_*_*", "pt_*_*", "95"}, {"zh_Hant_$cnsar", "zh_Hant_$cnsar", "96"}, {"zh_Hant_$!cnsar", "zh_Hant_$!cnsar", "96"}, {"zh_Hant_*", "zh_Hant_*", "95"}, {"*_*_*", "*_*_*", "96"}};
      RegionMapper.Builder rmb = (new RegionMapper.Builder()).addParadigms(paradigmRegions);
      String[][] var25 = variableOverrides;
      int var27 = variableOverrides.length;

      for(int var6 = 0; var6 < var27; ++var6) {
         String[] variableRule = var25[var6];
         rmb.add(variableRule[0], variableRule[1]);
      }

      StringDistanceTable defaultDistanceTable = new StringDistanceTable();
      RegionMapper defaultRegionMapper = rmb.build();
      XCldrStub.Splitter bar = XCldrStub.Splitter.on('_');
      List[] sorted = new ArrayList[]{new ArrayList(), new ArrayList(), new ArrayList()};
      Iterator var8 = xGetLanguageMatcherData().iterator();

      while(var8.hasNext()) {
         Row.R4 info = (Row.R4)var8.next();
         String desiredRaw = (String)info.get0();
         String supportedRaw = (String)info.get1();
         List desired = bar.splitToList(desiredRaw);
         List supported = bar.splitToList(supportedRaw);
         Boolean oneway = (Boolean)info.get3();
         int distance = desiredRaw.equals("*_*") ? 50 : (Integer)info.get2();
         int size = desired.size();
         if (size != 3) {
            sorted[size - 1].add(Row.of(desired, supported, distance, oneway));
         }
      }

      ArrayList[] var31 = sorted;
      int var33 = sorted.length;

      int var34;
      for(var34 = 0; var34 < var33; ++var34) {
         List item1 = var31[var34];

         Boolean oneway;
         List desired;
         List supported;
         Integer distance;
         for(Iterator var37 = item1.iterator(); var37.hasNext(); printMatchXml(desired, supported, distance, oneway)) {
            Row.R4 item2 = (Row.R4)var37.next();
            desired = (List)item2.get0();
            supported = (List)item2.get1();
            distance = (Integer)item2.get2();
            oneway = (Boolean)item2.get3();
            add(defaultDistanceTable, desired, supported, distance);
            if (oneway != Boolean.TRUE && !desired.equals(supported)) {
               add(defaultDistanceTable, supported, desired, distance);
            }
         }
      }

      String[][] var32 = regionRuleOverrides;
      var33 = regionRuleOverrides.length;

      for(var34 = 0; var34 < var33; ++var34) {
         String[] rule = var32[var34];
         List desiredBase = new ArrayList(bar.splitToList(rule[0]));
         List supportedBase = new ArrayList(bar.splitToList(rule[1]));
         Integer distance = 100 - Integer.parseInt(rule[2]);
         printMatchXml(desiredBase, supportedBase, distance, false);
         Collection desiredRegions = defaultRegionMapper.getIdsFromVariable((String)desiredBase.get(2));
         if (desiredRegions.isEmpty()) {
            throw new IllegalArgumentException("Bad region variable: " + (String)desiredBase.get(2));
         }

         Collection supportedRegions = defaultRegionMapper.getIdsFromVariable((String)supportedBase.get(2));
         if (supportedRegions.isEmpty()) {
            throw new IllegalArgumentException("Bad region variable: " + (String)supportedBase.get(2));
         }

         Iterator var47 = desiredRegions.iterator();

         while(var47.hasNext()) {
            String desiredRegion2 = (String)var47.next();
            desiredBase.set(2, desiredRegion2.toString());
            Iterator var19 = supportedRegions.iterator();

            while(var19.hasNext()) {
               String supportedRegion2 = (String)var19.next();
               supportedBase.set(2, supportedRegion2.toString());
               add(defaultDistanceTable, desiredBase, supportedBase, distance);
               add(defaultDistanceTable, supportedBase, desiredBase, distance);
            }
         }
      }

      DEFAULT = new XLocaleDistance(defaultDistanceTable.compact(), defaultRegionMapper);
   }

   static class CompactAndImmutablizer extends IdMakerFull {
      StringDistanceTable compact(StringDistanceTable item) {
         return this.toId(item) != null ? (StringDistanceTable)this.intern(item) : new StringDistanceTable(this.compact(item.subtables, 0));
      }

      Map compact(Map item, int level) {
         if (this.toId(item) != null) {
            return (Map)this.intern(item);
         } else {
            Map copy = new LinkedHashMap();
            Iterator var4 = item.entrySet().iterator();

            while(var4.hasNext()) {
               Map.Entry entry = (Map.Entry)var4.next();
               Object value = entry.getValue();
               if (value instanceof Map) {
                  copy.put(entry.getKey(), this.compact((Map)value, level + 1));
               } else {
                  copy.put(entry.getKey(), this.compact((DistanceNode)value));
               }
            }

            return XCldrStub.ImmutableMap.copyOf(copy);
         }
      }

      DistanceNode compact(DistanceNode item) {
         if (this.toId(item) != null) {
            return (DistanceNode)this.intern(item);
         } else {
            DistanceTable distanceTable = item.getDistanceTable();
            return (DistanceNode)(distanceTable != null && !distanceTable.isEmpty() ? new StringDistanceNode(item.distance, this.compact((StringDistanceTable)((StringDistanceNode)item).distanceTable)) : new DistanceNode(item.distance));
         }
      }
   }

   private static class RegionSet {
      private final Set tempRegions;
      private Operation operation;

      private RegionSet() {
         this.tempRegions = new TreeSet();
         this.operation = null;
      }

      private Set parseSet(String barString) {
         this.operation = XLocaleDistance.RegionSet.Operation.add;
         int last = 0;
         this.tempRegions.clear();

         int i;
         for(i = 0; i < barString.length(); ++i) {
            char c = barString.charAt(i);
            switch (c) {
               case '+':
                  this.add(barString, last, i);
                  last = i + 1;
                  this.operation = XLocaleDistance.RegionSet.Operation.add;
                  break;
               case '-':
                  this.add(barString, last, i);
                  last = i + 1;
                  this.operation = XLocaleDistance.RegionSet.Operation.remove;
            }
         }

         this.add(barString, last, i);
         return this.tempRegions;
      }

      private Set inverse() {
         TreeSet result = new TreeSet(XLocaleDistance.ALL_FINAL_REGIONS);
         result.removeAll(this.tempRegions);
         return result;
      }

      private void add(String barString, int last, int i) {
         if (i > last) {
            String region = barString.substring(last, i);
            this.changeSet(this.operation, region);
         }

      }

      private void changeSet(Operation operation, String region) {
         Collection contained = XLocaleDistance.CONTAINER_TO_CONTAINED_FINAL.get(region);
         if (contained != null && !contained.isEmpty()) {
            if (XLocaleDistance.RegionSet.Operation.add == operation) {
               this.tempRegions.addAll(contained);
            } else {
               this.tempRegions.removeAll(contained);
            }
         } else if (XLocaleDistance.RegionSet.Operation.add == operation) {
            this.tempRegions.add(region);
         } else {
            this.tempRegions.remove(region);
         }

      }

      // $FF: synthetic method
      RegionSet(Object x0) {
         this();
      }

      private static enum Operation {
         add,
         remove;
      }
   }

   static class RegionMapper implements IdMapper {
      final XCldrStub.Multimap variableToPartition;
      final Map regionToPartition;
      final XCldrStub.Multimap macroToPartitions;
      final Set paradigms;

      private RegionMapper(XCldrStub.Multimap variableToPartitionIn, Map regionToPartitionIn, XCldrStub.Multimap macroToPartitionsIn, Set paradigmsIn) {
         this.variableToPartition = XCldrStub.ImmutableMultimap.copyOf(variableToPartitionIn);
         this.regionToPartition = XCldrStub.ImmutableMap.copyOf(regionToPartitionIn);
         this.macroToPartitions = XCldrStub.ImmutableMultimap.copyOf(macroToPartitionsIn);
         this.paradigms = XCldrStub.ImmutableSet.copyOf(paradigmsIn);
      }

      public String toId(String region) {
         String result = (String)this.regionToPartition.get(region);
         return result == null ? "" : result;
      }

      public Collection getIdsFromVariable(String variable) {
         if (variable.equals("*")) {
            return Collections.singleton("*");
         } else {
            Collection result = this.variableToPartition.get(variable);
            if (result != null && !result.isEmpty()) {
               return result;
            } else {
               throw new IllegalArgumentException("Variable not defined: " + variable);
            }
         }
      }

      public Set regions() {
         return this.regionToPartition.keySet();
      }

      public Set variables() {
         return this.variableToPartition.keySet();
      }

      public String toString() {
         XCldrStub.TreeMultimap partitionToVariables = (XCldrStub.TreeMultimap)XCldrStub.Multimaps.invertFrom((XCldrStub.Multimap)this.variableToPartition, XCldrStub.TreeMultimap.create());
         XCldrStub.TreeMultimap partitionToRegions = XCldrStub.TreeMultimap.create();
         Iterator var3 = this.regionToPartition.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry e = (Map.Entry)var3.next();
            partitionToRegions.put(e.getValue(), e.getKey());
         }

         StringBuilder buffer = new StringBuilder();
         buffer.append("Partition ➠ Variables ➠ Regions (final)");
         Iterator var7 = partitionToVariables.asMap().entrySet().iterator();

         Map.Entry e;
         while(var7.hasNext()) {
            e = (Map.Entry)var7.next();
            buffer.append('\n');
            buffer.append((String)e.getKey() + "\t" + e.getValue() + "\t" + partitionToRegions.get(e.getKey()));
         }

         buffer.append("\nMacro ➠ Partitions");
         var7 = this.macroToPartitions.asMap().entrySet().iterator();

         while(var7.hasNext()) {
            e = (Map.Entry)var7.next();
            buffer.append('\n');
            buffer.append((String)e.getKey() + "\t" + e.getValue());
         }

         return buffer.toString();
      }

      // $FF: synthetic method
      RegionMapper(XCldrStub.Multimap x0, Map x1, XCldrStub.Multimap x2, Set x3, Object x4) {
         this(x0, x1, x2, x3);
      }

      static class Builder {
         private final XCldrStub.Multimap regionToRawPartition = XCldrStub.TreeMultimap.create();
         private final RegionSet regionSet = new RegionSet();
         private final Set paradigms = new LinkedHashSet();

         void add(String variable, String barString) {
            Set tempRegions = this.regionSet.parseSet(barString);
            Iterator var4 = tempRegions.iterator();

            String inverseVariable;
            while(var4.hasNext()) {
               inverseVariable = (String)var4.next();
               this.regionToRawPartition.put(inverseVariable, variable);
            }

            Set inverse = this.regionSet.inverse();
            inverseVariable = "$!" + variable.substring(1);
            Iterator var6 = inverse.iterator();

            while(var6.hasNext()) {
               String region = (String)var6.next();
               this.regionToRawPartition.put(region, inverseVariable);
            }

         }

         public Builder addParadigms(String... paradigmRegions) {
            String[] var2 = paradigmRegions;
            int var3 = paradigmRegions.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               String paradigm = var2[var4];
               this.paradigms.add(new ULocale(paradigm));
            }

            return this;
         }

         RegionMapper build() {
            IdMakerFull id = new IdMakerFull("partition");
            XCldrStub.Multimap variableToPartitions = XCldrStub.TreeMultimap.create();
            Map regionToPartition = new TreeMap();
            XCldrStub.Multimap partitionToRegions = XCldrStub.TreeMultimap.create();
            Iterator var5 = this.regionToRawPartition.asMap().entrySet().iterator();

            String partition;
            while(var5.hasNext()) {
               Map.Entry e = (Map.Entry)var5.next();
               String region = (String)e.getKey();
               Collection rawPartition = (Collection)e.getValue();
               String partition = String.valueOf((char)(945 + id.add(rawPartition)));
               regionToPartition.put(region, partition);
               partitionToRegions.put(partition, region);
               Iterator var10 = rawPartition.iterator();

               while(var10.hasNext()) {
                  partition = (String)var10.next();
                  variableToPartitions.put(partition, partition);
               }
            }

            XCldrStub.Multimap macroToPartitions = XCldrStub.TreeMultimap.create();
            Iterator var13 = XLocaleDistance.CONTAINER_TO_CONTAINED.asMap().entrySet().iterator();

            while(var13.hasNext()) {
               Map.Entry e = (Map.Entry)var13.next();
               String macro = (String)e.getKey();
               Iterator var16 = partitionToRegions.asMap().entrySet().iterator();

               while(var16.hasNext()) {
                  Map.Entry e2 = (Map.Entry)var16.next();
                  partition = (String)e2.getKey();
                  if (!Collections.disjoint((Collection)e.getValue(), (Collection)e2.getValue())) {
                     macroToPartitions.put(macro, partition);
                  }
               }
            }

            return new RegionMapper(variableToPartitions, regionToPartition, macroToPartitions, this.paradigms);
         }
      }
   }

   public static enum DistanceOption {
      NORMAL,
      SCRIPT_FIRST;
   }

   static class AddSub implements XCldrStub.Predicate {
      private final String desiredSub;
      private final String supportedSub;
      private final CopyIfEmpty r;

      AddSub(String desiredSub, String supportedSub, StringDistanceTable distanceTableToCopy) {
         this.r = new CopyIfEmpty(distanceTableToCopy);
         this.desiredSub = desiredSub;
         this.supportedSub = supportedSub;
      }

      public boolean test(DistanceNode node) {
         if (node == null) {
            throw new IllegalArgumentException("bad structure");
         } else {
            ((StringDistanceNode)node).addSubtables(this.desiredSub, this.supportedSub, this.r);
            return true;
         }
      }
   }

   static class CopyIfEmpty implements XCldrStub.Predicate {
      private final StringDistanceTable toCopy;

      CopyIfEmpty(StringDistanceTable resetIfNotNull) {
         this.toCopy = resetIfNotNull;
      }

      public boolean test(DistanceNode node) {
         StringDistanceTable subtables = (StringDistanceTable)node.getDistanceTable();
         if (subtables.subtables.isEmpty()) {
            subtables.copy(this.toCopy);
         }

         return true;
      }
   }

   /** @deprecated */
   @Deprecated
   public static class StringDistanceTable extends DistanceTable {
      final Map subtables;

      StringDistanceTable(Map tables) {
         this.subtables = tables;
      }

      StringDistanceTable() {
         this(XLocaleDistance.newMap());
      }

      public boolean isEmpty() {
         return this.subtables.isEmpty();
      }

      public boolean equals(Object obj) {
         return this == obj || obj != null && obj.getClass() == this.getClass() && this.subtables.equals(((StringDistanceTable)obj).subtables);
      }

      public int hashCode() {
         return this.subtables.hashCode();
      }

      public int getDistance(String desired, String supported, Output distanceTable, boolean starEquals) {
         boolean star = false;
         Map sub2 = (Map)this.subtables.get(desired);
         if (sub2 == null) {
            sub2 = (Map)this.subtables.get("�");
            star = true;
         }

         DistanceNode value = (DistanceNode)sub2.get(supported);
         if (value == null) {
            value = (DistanceNode)sub2.get("�");
            if (value == null && !star) {
               sub2 = (Map)this.subtables.get("�");
               value = (DistanceNode)sub2.get(supported);
               if (value == null) {
                  value = (DistanceNode)sub2.get("�");
               }
            }

            star = true;
         }

         if (distanceTable != null) {
            distanceTable.value = ((StringDistanceNode)value).distanceTable;
         }

         return starEquals && star && desired.equals(supported) ? 0 : value.distance;
      }

      public void copy(StringDistanceTable other) {
         Iterator var2 = other.subtables.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry e1 = (Map.Entry)var2.next();
            Iterator var4 = ((Map)e1.getValue()).entrySet().iterator();

            while(var4.hasNext()) {
               Map.Entry e2 = (Map.Entry)var4.next();
               DistanceNode value = (DistanceNode)e2.getValue();
               this.addSubtable((String)e1.getKey(), (String)e2.getKey(), value.distance);
            }
         }

      }

      DistanceNode addSubtable(String desired, String supported, int distance) {
         Map sub2 = (Map)this.subtables.get(desired);
         if (sub2 == null) {
            this.subtables.put(desired, sub2 = XLocaleDistance.newMap());
         }

         DistanceNode oldNode = (DistanceNode)sub2.get(supported);
         if (oldNode != null) {
            return oldNode;
         } else {
            StringDistanceNode newNode = new StringDistanceNode(distance);
            sub2.put(supported, newNode);
            return newNode;
         }
      }

      private DistanceNode getNode(String desired, String supported) {
         Map sub2 = (Map)this.subtables.get(desired);
         return sub2 == null ? null : (DistanceNode)sub2.get(supported);
      }

      public void addSubtables(String desired, String supported, XCldrStub.Predicate action) {
         DistanceNode node = this.getNode(desired, supported);
         if (node == null) {
            Output node2 = new Output();
            int distance = this.getDistance(desired, supported, node2, true);
            node = this.addSubtable(desired, supported, distance);
            if (node2.value != null) {
               ((StringDistanceNode)node).copyTables((StringDistanceTable)((StringDistanceTable)node2.value));
            }
         }

         action.test(node);
      }

      public void addSubtables(String desiredLang, String supportedLang, String desiredScript, String supportedScript, int percentage) {
         boolean haveKeys = false;
         Iterator var7 = this.subtables.entrySet().iterator();

         label44:
         while(true) {
            Map.Entry e1;
            boolean desiredIsKey;
            do {
               if (!var7.hasNext()) {
                  StringDistanceTable dt = new StringDistanceTable();
                  dt.addSubtable(desiredScript, supportedScript, percentage);
                  CopyIfEmpty r = new CopyIfEmpty(dt);
                  this.addSubtables(desiredLang, supportedLang, r);
                  return;
               }

               e1 = (Map.Entry)var7.next();
               String key1 = (String)e1.getKey();
               desiredIsKey = desiredLang.equals(key1);
            } while(!desiredIsKey && !desiredLang.equals("�"));

            Iterator var11 = ((Map)e1.getValue()).entrySet().iterator();

            while(true) {
               Map.Entry e2;
               boolean supportedIsKey;
               do {
                  if (!var11.hasNext()) {
                     continue label44;
                  }

                  e2 = (Map.Entry)var11.next();
                  String key2 = (String)e2.getKey();
                  supportedIsKey = supportedLang.equals(key2);
                  haveKeys |= desiredIsKey && supportedIsKey;
               } while(!supportedIsKey && !supportedLang.equals("�"));

               DistanceNode value = (DistanceNode)e2.getValue();
               ((StringDistanceTable)value.getDistanceTable()).addSubtable(desiredScript, supportedScript, percentage);
            }
         }
      }

      public void addSubtables(String desiredLang, String supportedLang, String desiredScript, String supportedScript, String desiredRegion, String supportedRegion, int percentage) {
         boolean haveKeys = false;
         Iterator var9 = this.subtables.entrySet().iterator();

         label44:
         while(true) {
            Map.Entry e1;
            boolean desiredIsKey;
            do {
               if (!var9.hasNext()) {
                  StringDistanceTable dt = new StringDistanceTable();
                  dt.addSubtable(desiredRegion, supportedRegion, percentage);
                  AddSub r = new AddSub(desiredScript, supportedScript, dt);
                  this.addSubtables(desiredLang, supportedLang, r);
                  return;
               }

               e1 = (Map.Entry)var9.next();
               String key1 = (String)e1.getKey();
               desiredIsKey = desiredLang.equals(key1);
            } while(!desiredIsKey && !desiredLang.equals("�"));

            Iterator var13 = ((Map)e1.getValue()).entrySet().iterator();

            while(true) {
               Map.Entry e2;
               boolean supportedIsKey;
               do {
                  if (!var13.hasNext()) {
                     continue label44;
                  }

                  e2 = (Map.Entry)var13.next();
                  String key2 = (String)e2.getKey();
                  supportedIsKey = supportedLang.equals(key2);
                  haveKeys |= desiredIsKey && supportedIsKey;
               } while(!supportedIsKey && !supportedLang.equals("�"));

               StringDistanceNode value = (StringDistanceNode)e2.getValue();
               ((StringDistanceTable)value.distanceTable).addSubtables(desiredScript, supportedScript, desiredRegion, supportedRegion, percentage);
            }
         }
      }

      public String toString() {
         return this.toString(false);
      }

      public String toString(boolean abbreviate) {
         return this.toString(abbreviate, "", new IdMakerFull("interner"), new StringBuilder()).toString();
      }

      public StringBuilder toString(boolean abbreviate, String indent, IdMakerFull intern, StringBuilder buffer) {
         String indent2 = indent.isEmpty() ? "" : "\t";
         Integer id = abbreviate ? intern.getOldAndAdd(this.subtables) : null;
         if (id != null) {
            buffer.append(indent2).append('#').append(id).append('\n');
         } else {
            for(Iterator var7 = this.subtables.entrySet().iterator(); var7.hasNext(); indent2 = indent) {
               Map.Entry e1 = (Map.Entry)var7.next();
               Map subsubtable = (Map)e1.getValue();
               buffer.append(indent2).append((String)e1.getKey());
               String indent3 = "\t";
               id = abbreviate ? intern.getOldAndAdd(subsubtable) : null;
               if (id != null) {
                  buffer.append(indent3).append('#').append(id).append('\n');
               } else {
                  for(Iterator var11 = subsubtable.entrySet().iterator(); var11.hasNext(); indent3 = indent + '\t') {
                     Map.Entry e2 = (Map.Entry)var11.next();
                     DistanceNode value = (DistanceNode)e2.getValue();
                     buffer.append(indent3).append((String)e2.getKey());
                     id = abbreviate ? intern.getOldAndAdd(value) : null;
                     if (id != null) {
                        buffer.append('\t').append('#').append(id).append('\n');
                     } else {
                        buffer.append('\t').append(value.distance);
                        DistanceTable distanceTable = value.getDistanceTable();
                        if (distanceTable != null) {
                           id = abbreviate ? intern.getOldAndAdd(distanceTable) : null;
                           if (id != null) {
                              buffer.append('\t').append('#').append(id).append('\n');
                           } else {
                              ((StringDistanceTable)distanceTable).toString(abbreviate, indent + "\t\t\t", intern, buffer);
                           }
                        } else {
                           buffer.append('\n');
                        }
                     }
                  }
               }
            }
         }

         return buffer;
      }

      public StringDistanceTable compact() {
         return (new CompactAndImmutablizer()).compact(this);
      }

      public Set getCloser(int threshold) {
         Set result = new HashSet();
         Iterator var3 = this.subtables.entrySet().iterator();

         while(true) {
            while(var3.hasNext()) {
               Map.Entry e1 = (Map.Entry)var3.next();
               String desired = (String)e1.getKey();
               Iterator var6 = ((Map)e1.getValue()).entrySet().iterator();

               while(var6.hasNext()) {
                  Map.Entry e2 = (Map.Entry)var6.next();
                  if (((DistanceNode)e2.getValue()).distance < threshold) {
                     result.add(desired);
                     break;
                  }
               }
            }

            return result;
         }
      }

      public Integer getInternalDistance(String a, String b) {
         Map subsub = (Map)this.subtables.get(a);
         if (subsub == null) {
            return null;
         } else {
            DistanceNode dnode = (DistanceNode)subsub.get(b);
            return dnode == null ? null : dnode.distance;
         }
      }

      public DistanceNode getInternalNode(String a, String b) {
         Map subsub = (Map)this.subtables.get(a);
         return subsub == null ? null : (DistanceNode)subsub.get(b);
      }

      public Map getInternalMatches() {
         Map result = new LinkedHashMap();
         Iterator var2 = this.subtables.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            result.put(entry.getKey(), new LinkedHashSet(((Map)entry.getValue()).keySet()));
         }

         return result;
      }
   }

   static class StringDistanceNode extends DistanceNode {
      final DistanceTable distanceTable;

      public StringDistanceNode(int distance, DistanceTable distanceTable) {
         super(distance);
         this.distanceTable = distanceTable;
      }

      public boolean equals(Object obj) {
         StringDistanceNode other;
         return this == obj || obj != null && obj.getClass() == this.getClass() && this.distance == (other = (StringDistanceNode)obj).distance && Utility.equals(this.distanceTable, other.distanceTable) && super.equals(other);
      }

      public int hashCode() {
         return this.distance ^ Utility.hashCode(this.distanceTable);
      }

      StringDistanceNode(int distance) {
         this(distance, new StringDistanceTable());
      }

      public void addSubtables(String desiredSub, String supportedSub, CopyIfEmpty r) {
         ((StringDistanceTable)this.distanceTable).addSubtables(desiredSub, supportedSub, r);
      }

      public String toString() {
         return "distance: " + this.distance + "\n" + this.distanceTable;
      }

      public void copyTables(StringDistanceTable value) {
         if (value != null) {
            ((StringDistanceTable)this.distanceTable).copy(value);
         }

      }

      public DistanceTable getDistanceTable() {
         return this.distanceTable;
      }
   }

   static class IdMakerFull implements IdMapper {
      private final Map objectToInt;
      private final List intToObject;
      final String name;

      IdMakerFull(String name) {
         this.objectToInt = new HashMap();
         this.intToObject = new ArrayList();
         this.name = name;
      }

      IdMakerFull() {
         this("unnamed");
      }

      IdMakerFull(String name, Object zeroValue) {
         this(name);
         this.add(zeroValue);
      }

      public Integer add(Object source) {
         Integer result = (Integer)this.objectToInt.get(source);
         if (result == null) {
            Integer newResult = this.intToObject.size();
            this.objectToInt.put(source, newResult);
            this.intToObject.add(source);
            return newResult;
         } else {
            return result;
         }
      }

      public Integer toId(Object source) {
         return (Integer)this.objectToInt.get(source);
      }

      public Object fromId(int id) {
         return this.intToObject.get(id);
      }

      public Object intern(Object source) {
         return this.fromId(this.add(source));
      }

      public int size() {
         return this.intToObject.size();
      }

      public Integer getOldAndAdd(Object source) {
         Integer result = (Integer)this.objectToInt.get(source);
         if (result == null) {
            Integer newResult = this.intToObject.size();
            this.objectToInt.put(source, newResult);
            this.intToObject.add(source);
         }

         return result;
      }

      public String toString() {
         return this.size() + ": " + this.intToObject;
      }

      public boolean equals(Object obj) {
         return this == obj || obj != null && obj.getClass() == this.getClass() && this.intToObject.equals(((IdMakerFull)obj).intToObject);
      }

      public int hashCode() {
         return this.intToObject.hashCode();
      }
   }

   private interface IdMapper {
      Object toId(Object var1);
   }

   /** @deprecated */
   @Deprecated
   public static class DistanceNode {
      final int distance;

      public DistanceNode(int distance) {
         this.distance = distance;
      }

      public DistanceTable getDistanceTable() {
         return null;
      }

      public boolean equals(Object obj) {
         return this == obj || obj != null && obj.getClass() == this.getClass() && this.distance == ((DistanceNode)obj).distance;
      }

      public int hashCode() {
         return this.distance;
      }

      public String toString() {
         return "\ndistance: " + this.distance;
      }
   }

   /** @deprecated */
   @Deprecated
   public abstract static class DistanceTable {
      abstract int getDistance(String var1, String var2, Output var3, boolean var4);

      abstract Set getCloser(int var1);

      abstract String toString(boolean var1);

      public DistanceTable compact() {
         return this;
      }

      public DistanceNode getInternalNode(String any, String any2) {
         return null;
      }

      public Map getInternalMatches() {
         return null;
      }

      public boolean isEmpty() {
         return true;
      }
   }
}
