package kodo.jdbc.meta;

import java.util.HashMap;
import java.util.Map;
import kodo.jdbc.meta.strats.LockGroupNumberVersionStrategy;
import kodo.jdbc.meta.strats.LockGroupStateComparisonVersionStrategy;
import kodo.jdbc.meta.strats.LockGroupTimestampVersionStrategy;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.MappingRepository;
import org.apache.openjpa.jdbc.meta.Version;
import org.apache.openjpa.jdbc.meta.VersionMappingInfo;
import org.apache.openjpa.jdbc.meta.VersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.NumberVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.StateComparisonVersionStrategy;
import org.apache.openjpa.jdbc.meta.strats.TimestampVersionStrategy;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.meta.ClassMetaData;
import org.apache.openjpa.meta.FieldMetaData;
import org.apache.openjpa.meta.ValueMetaData;

public class KodoMappingRepository extends MappingRepository {
   private final Map _lgs = new HashMap();

   protected ClassMetaData newClassMetaData(Class type) {
      return new KodoClassMapping(type, this);
   }

   protected ClassMetaData newEmbeddedClassMetaData(ValueMetaData owner) {
      return new KodoClassMapping(owner);
   }

   protected FieldMetaData newFieldMetaData(String name, Class type, ClassMetaData owner) {
      return new KodoFieldMapping(name, type, (ClassMapping)owner);
   }

   public LockGroup getLockGroup(String name) {
      if (name == null) {
         throw new NullPointerException("name == null");
      } else {
         synchronized(this._lgs) {
            LockGroup lg = (LockGroup)this._lgs.get(name);
            if (lg == null) {
               lg = new LockGroup(name);
               this._lgs.put(name, lg);
            }

            return lg;
         }
      }
   }

   public synchronized ClassMetaData getMetaData(Object oid, ClassLoader envLoader, boolean mustExist) {
      return super.getMetaData(oid, envLoader, mustExist);
   }

   public synchronized void clear() {
      super.clear();
      this._lgs.clear();
   }

   protected VersionMappingInfo newMappingInfo(Version version) {
      return new LockGroupVersionMappingInfo();
   }

   protected VersionStrategy instantiateVersionStrategy(String name, Version version) {
      String props = Configurations.getProperties(name);
      name = Configurations.getClassName(name);
      if ("version-number".equals(name)) {
         return this.instantiateVersionStrategy(LockGroupNumberVersionStrategy.class, version, props);
      } else if ("timestamp".equals(name)) {
         return this.instantiateVersionStrategy(LockGroupTimestampVersionStrategy.class, version, props);
      } else {
         return "state-comparison".equals(name) ? this.instantiateVersionStrategy(LockGroupStateComparisonVersionStrategy.class, version, props) : super.instantiateVersionStrategy(name, version);
      }
   }

   protected VersionStrategy defaultStrategy(Version version, boolean adapt) {
      VersionStrategy vstrat = super.defaultStrategy(version, adapt);
      if (vstrat == null) {
         return null;
      } else if (vstrat.getClass() == NumberVersionStrategy.class) {
         return new LockGroupNumberVersionStrategy();
      } else if (vstrat.getClass() == TimestampVersionStrategy.class) {
         return new LockGroupTimestampVersionStrategy();
      } else {
         return (VersionStrategy)(vstrat.getClass() == StateComparisonVersionStrategy.class ? new LockGroupStateComparisonVersionStrategy() : vstrat);
      }
   }

   protected VersionStrategy defaultStrategy(Version vers, FieldMapping vfield) {
      switch (vfield.getTypeCode()) {
         case 1:
         case 5:
         case 6:
         case 7:
         case 10:
         case 17:
         case 21:
         case 22:
         case 23:
            return new LockGroupNumberVersionStrategy();
         case 2:
         case 3:
         case 4:
         case 8:
         case 9:
         case 11:
         case 12:
         case 13:
         case 15:
         case 16:
         case 18:
         case 19:
         case 20:
         case 24:
         case 25:
         case 26:
         case 27:
         default:
            return super.defaultStrategy(vers, vfield);
         case 14:
         case 28:
            return new LockGroupTimestampVersionStrategy();
      }
   }
}
