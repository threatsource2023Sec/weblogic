package org.apache.openjpa.jdbc.conf;

import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.meta.MetaDataPlusMappingFactory;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.lib.log.Log;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.meta.MetaDataFactory;

public class MappingFactoryValue extends PluginValue {
   private static final Localizer _loc = Localizer.forPackage(MappingFactoryValue.class);
   private String[] _metaFactoryDefaults = null;
   private String[] _mappedMetaFactoryDefaults = null;

   public MappingFactoryValue(String prop) {
      super(prop, false);
   }

   public void setMetaDataFactoryDefault(String metaAlias, String mappingAlias) {
      this._metaFactoryDefaults = this.setAlias(metaAlias, mappingAlias, this._metaFactoryDefaults);
   }

   public void setMappedMetaDataFactoryDefault(String metaAlias, String mappingAlias) {
      this._mappedMetaFactoryDefaults = this.setAlias(metaAlias, mappingAlias, this._mappedMetaFactoryDefaults);
   }

   public MetaDataFactory instantiateMetaDataFactory(Configuration conf, PluginValue metaPlugin, String mapping) {
      return this.instantiateMetaDataFactory(conf, metaPlugin, mapping, true);
   }

   public MetaDataFactory instantiateMetaDataFactory(Configuration conf, PluginValue metaPlugin, String mapping, boolean fatal) {
      String clsName = this.getClassName();
      String props = this.getProperties();
      String metaClsName = metaPlugin.getClassName();
      String metaProps = metaPlugin.getProperties();
      if (StringUtils.isEmpty(clsName)) {
         String def;
         if (!StringUtils.isEmpty(mapping)) {
            def = this.unalias(metaPlugin.alias(metaClsName), this._mappedMetaFactoryDefaults, true);
            if (def != null) {
               clsName = this.unalias(def);
            }
         }

         if (StringUtils.isEmpty(clsName)) {
            def = this.unalias(metaPlugin.alias(metaClsName), this._metaFactoryDefaults, true);
            if (def != null) {
               clsName = this.unalias(def);
            }
         }
      }

      if (clsName != null && clsName.equals(metaClsName)) {
         if (props != null && metaProps == null) {
            metaProps = props;
         } else if (props != null) {
            metaProps = metaProps + "," + props;
         }

         clsName = null;
         props = null;
      }

      MetaDataFactory map = (MetaDataFactory)this.newInstance(clsName, MetaDataFactory.class, conf, fatal);
      MetaDataFactory meta;
      if (map != null && map.getClass().getName().indexOf("Deprecated") != -1) {
         meta = map;
         map = null;
      } else {
         meta = (MetaDataFactory)metaPlugin.newInstance(metaClsName, MetaDataFactory.class, conf, fatal);
      }

      if (map == null && props != null) {
         if (metaProps == null) {
            metaProps = props;
         } else {
            metaProps = metaProps + ", " + props;
         }
      }

      Configurations.configureInstance(map, conf, (String)props, fatal ? this.getProperty() : null);
      Configurations.configureInstance(meta, conf, (String)metaProps, fatal ? metaPlugin.getProperty() : null);
      Log log = conf.getLog("openjpa.MetaData");
      if (log.isTraceEnabled()) {
         log.trace(_loc.get("meta-factory", (Object)meta));
         if (map != null) {
            log.trace(_loc.get("map-factory", (Object)map));
         }
      }

      MetaDataFactory ret = map == null ? meta : new MetaDataPlusMappingFactory(meta, map);
      return (MetaDataFactory)ret;
   }
}
