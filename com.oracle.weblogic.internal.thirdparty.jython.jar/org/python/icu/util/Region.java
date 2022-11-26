package org.python.icu.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.python.icu.impl.ICUResourceBundle;

public class Region implements Comparable {
   private String id;
   private int code;
   private RegionType type;
   private Region containingRegion = null;
   private Set containedRegions = new TreeSet();
   private List preferredValues = null;
   private static boolean regionDataIsLoaded = false;
   private static Map regionIDMap = null;
   private static Map numericCodeMap = null;
   private static Map regionAliases = null;
   private static ArrayList regions = null;
   private static ArrayList availableRegions = null;
   private static final String UNKNOWN_REGION_ID = "ZZ";
   private static final String OUTLYING_OCEANIA_REGION_ID = "QO";
   private static final String WORLD_ID = "001";

   private Region() {
   }

   private static synchronized void loadRegionData() {
      if (!regionDataIsLoaded) {
         regionAliases = new HashMap();
         regionIDMap = new HashMap();
         numericCodeMap = new HashMap();
         availableRegions = new ArrayList(Region.RegionType.values().length);
         UResourceBundle metadataAlias = null;
         UResourceBundle territoryAlias = null;
         UResourceBundle codeMappings = null;
         UResourceBundle idValidity = null;
         UResourceBundle regionList = null;
         UResourceBundle regionRegular = null;
         UResourceBundle regionMacro = null;
         UResourceBundle regionUnknown = null;
         UResourceBundle worldContainment = null;
         UResourceBundle territoryContainment = null;
         UResourceBundle groupingContainment = null;
         UResourceBundle metadata = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "metadata", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
         metadataAlias = metadata.get("alias");
         territoryAlias = metadataAlias.get("territory");
         UResourceBundle supplementalData = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
         codeMappings = supplementalData.get("codeMappings");
         idValidity = supplementalData.get("idValidity");
         regionList = idValidity.get("region");
         regionRegular = regionList.get("regular");
         regionMacro = regionList.get("macroregion");
         regionUnknown = regionList.get("unknown");
         territoryContainment = supplementalData.get("territoryContainment");
         worldContainment = territoryContainment.get("001");
         groupingContainment = territoryContainment.get("grouping");
         String[] continentsArr = worldContainment.getStringArray();
         List continents = Arrays.asList(continentsArr);
         String[] groupingArr = groupingContainment.getStringArray();
         List groupings = Arrays.asList(groupingArr);
         List regionCodes = new ArrayList();
         List allRegions = new ArrayList();
         allRegions.addAll(Arrays.asList(regionRegular.getStringArray()));
         allRegions.addAll(Arrays.asList(regionMacro.getStringArray()));
         allRegions.add(regionUnknown.getString());
         Iterator var19 = allRegions.iterator();

         while(true) {
            String id;
            int j;
            String child;
            while(var19.hasNext()) {
               id = (String)var19.next();
               int rangeMarkerLocation = id.indexOf("~");
               if (rangeMarkerLocation > 0) {
                  StringBuilder regionName = new StringBuilder(id);
                  char endRange = regionName.charAt(rangeMarkerLocation + 1);
                  regionName.setLength(rangeMarkerLocation);
                  j = regionName.charAt(rangeMarkerLocation - 1);

                  while(j <= endRange) {
                     child = regionName.toString();
                     regionCodes.add(child);
                     j = (char)(j + 1);
                     regionName.setCharAt(rangeMarkerLocation - 1, (char)j);
                  }
               } else {
                  regionCodes.add(id);
               }
            }

            regions = new ArrayList(regionCodes.size());

            Region ar;
            for(var19 = regionCodes.iterator(); var19.hasNext(); regions.add(ar)) {
               id = (String)var19.next();
               ar = new Region();
               ar.id = id;
               ar.type = Region.RegionType.TERRITORY;
               regionIDMap.put(id, ar);
               if (id.matches("[0-9]{3}")) {
                  ar.code = Integer.valueOf(id);
                  numericCodeMap.put(ar.code, ar);
                  ar.type = Region.RegionType.SUBCONTINENT;
               } else {
                  ar.code = -1;
               }
            }

            int i;
            UResourceBundle mapping;
            String grouping;
            String parent;
            Region parentRegion;
            for(i = 0; i < territoryAlias.getSize(); ++i) {
               mapping = territoryAlias.get(i);
               grouping = mapping.getKey();
               parent = mapping.get("replacement").getString();
               if (regionIDMap.containsKey(parent) && !regionIDMap.containsKey(grouping)) {
                  regionAliases.put(grouping, regionIDMap.get(parent));
               } else {
                  if (regionIDMap.containsKey(grouping)) {
                     parentRegion = (Region)regionIDMap.get(grouping);
                  } else {
                     parentRegion = new Region();
                     parentRegion.id = grouping;
                     regionIDMap.put(grouping, parentRegion);
                     if (grouping.matches("[0-9]{3}")) {
                        parentRegion.code = Integer.valueOf(grouping);
                        numericCodeMap.put(parentRegion.code, parentRegion);
                     } else {
                        parentRegion.code = -1;
                     }

                     regions.add(parentRegion);
                  }

                  parentRegion.type = Region.RegionType.DEPRECATED;
                  List aliasToRegionStrings = Arrays.asList(parent.split(" "));
                  parentRegion.preferredValues = new ArrayList();
                  Iterator var42 = aliasToRegionStrings.iterator();

                  while(var42.hasNext()) {
                     String s = (String)var42.next();
                     if (regionIDMap.containsKey(s)) {
                        parentRegion.preferredValues.add(regionIDMap.get(s));
                     }
                  }
               }
            }

            for(i = 0; i < codeMappings.getSize(); ++i) {
               mapping = codeMappings.get(i);
               if (mapping.getType() == 8) {
                  String[] codeMappingStrings = mapping.getStringArray();
                  parent = codeMappingStrings[0];
                  Integer codeMappingNumber = Integer.valueOf(codeMappingStrings[1]);
                  String codeMapping3Letter = codeMappingStrings[2];
                  if (regionIDMap.containsKey(parent)) {
                     Region r = (Region)regionIDMap.get(parent);
                     r.code = codeMappingNumber;
                     numericCodeMap.put(r.code, r);
                     regionAliases.put(codeMapping3Letter, r);
                  }
               }
            }

            Region r;
            if (regionIDMap.containsKey("001")) {
               r = (Region)regionIDMap.get("001");
               r.type = Region.RegionType.WORLD;
            }

            if (regionIDMap.containsKey("ZZ")) {
               r = (Region)regionIDMap.get("ZZ");
               r.type = Region.RegionType.UNKNOWN;
            }

            Iterator var31 = continents.iterator();

            while(var31.hasNext()) {
               grouping = (String)var31.next();
               if (regionIDMap.containsKey(grouping)) {
                  r = (Region)regionIDMap.get(grouping);
                  r.type = Region.RegionType.CONTINENT;
               }
            }

            var31 = groupings.iterator();

            while(var31.hasNext()) {
               grouping = (String)var31.next();
               if (regionIDMap.containsKey(grouping)) {
                  r = (Region)regionIDMap.get(grouping);
                  r.type = Region.RegionType.GROUPING;
               }
            }

            if (regionIDMap.containsKey("QO")) {
               r = (Region)regionIDMap.get("QO");
               r.type = Region.RegionType.SUBCONTINENT;
            }

            int i;
            for(i = 0; i < territoryContainment.getSize(); ++i) {
               UResourceBundle mapping = territoryContainment.get(i);
               parent = mapping.getKey();
               if (!parent.equals("containedGroupings") && !parent.equals("deprecated")) {
                  parentRegion = (Region)regionIDMap.get(parent);

                  for(j = 0; j < mapping.getSize(); ++j) {
                     child = mapping.getString(j);
                     Region childRegion = (Region)regionIDMap.get(child);
                     if (parentRegion != null && childRegion != null) {
                        parentRegion.containedRegions.add(childRegion);
                        if (parentRegion.getType() != Region.RegionType.GROUPING) {
                           childRegion.containingRegion = parentRegion;
                        }
                     }
                  }
               }
            }

            for(i = 0; i < Region.RegionType.values().length; ++i) {
               availableRegions.add(new TreeSet());
            }

            var31 = regions.iterator();

            while(var31.hasNext()) {
               ar = (Region)var31.next();
               Set currentSet = (Set)availableRegions.get(ar.type.ordinal());
               currentSet.add(ar);
               availableRegions.set(ar.type.ordinal(), currentSet);
            }

            regionDataIsLoaded = true;
            return;
         }
      }
   }

   public static Region getInstance(String id) {
      if (id == null) {
         throw new NullPointerException();
      } else {
         loadRegionData();
         Region r = (Region)regionIDMap.get(id);
         if (r == null) {
            r = (Region)regionAliases.get(id);
         }

         if (r == null) {
            throw new IllegalArgumentException("Unknown region id: " + id);
         } else {
            if (r.type == Region.RegionType.DEPRECATED && r.preferredValues.size() == 1) {
               r = (Region)r.preferredValues.get(0);
            }

            return r;
         }
      }
   }

   public static Region getInstance(int code) {
      loadRegionData();
      Region r = (Region)numericCodeMap.get(code);
      if (r == null) {
         String pad = "";
         if (code < 10) {
            pad = "00";
         } else if (code < 100) {
            pad = "0";
         }

         String id = pad + Integer.toString(code);
         r = (Region)regionAliases.get(id);
      }

      if (r == null) {
         throw new IllegalArgumentException("Unknown region code: " + code);
      } else {
         if (r.type == Region.RegionType.DEPRECATED && r.preferredValues.size() == 1) {
            r = (Region)r.preferredValues.get(0);
         }

         return r;
      }
   }

   public static Set getAvailable(RegionType type) {
      loadRegionData();
      return Collections.unmodifiableSet((Set)availableRegions.get(type.ordinal()));
   }

   public Region getContainingRegion() {
      loadRegionData();
      return this.containingRegion;
   }

   public Region getContainingRegion(RegionType type) {
      loadRegionData();
      if (this.containingRegion == null) {
         return null;
      } else {
         return this.containingRegion.type.equals(type) ? this.containingRegion : this.containingRegion.getContainingRegion(type);
      }
   }

   public Set getContainedRegions() {
      loadRegionData();
      return Collections.unmodifiableSet(this.containedRegions);
   }

   public Set getContainedRegions(RegionType type) {
      loadRegionData();
      Set result = new TreeSet();
      Set cr = this.getContainedRegions();
      Iterator var4 = cr.iterator();

      while(var4.hasNext()) {
         Region r = (Region)var4.next();
         if (r.getType() == type) {
            result.add(r);
         } else {
            result.addAll(r.getContainedRegions(type));
         }
      }

      return Collections.unmodifiableSet(result);
   }

   public List getPreferredValues() {
      loadRegionData();
      return this.type == Region.RegionType.DEPRECATED ? Collections.unmodifiableList(this.preferredValues) : null;
   }

   public boolean contains(Region other) {
      loadRegionData();
      if (this.containedRegions.contains(other)) {
         return true;
      } else {
         Iterator var2 = this.containedRegions.iterator();

         Region cr;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            cr = (Region)var2.next();
         } while(!cr.contains(other));

         return true;
      }
   }

   public String toString() {
      return this.id;
   }

   public int getNumericCode() {
      return this.code;
   }

   public RegionType getType() {
      return this.type;
   }

   public int compareTo(Region other) {
      return this.id.compareTo(other.id);
   }

   public static enum RegionType {
      UNKNOWN,
      TERRITORY,
      WORLD,
      CONTINENT,
      SUBCONTINENT,
      GROUPING,
      DEPRECATED;
   }
}
