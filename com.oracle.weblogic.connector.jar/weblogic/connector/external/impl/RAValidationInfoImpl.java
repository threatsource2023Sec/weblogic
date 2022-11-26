package weblogic.connector.external.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import weblogic.connector.external.PropSetterTable;
import weblogic.connector.external.RAValidationInfo;
import weblogic.connector.utils.PropertyNameNormalizer;

public class RAValidationInfoImpl implements RAValidationInfo {
   private boolean hasRAxml = false;
   private boolean isLinkRef = false;
   private String linkRef = null;
   private boolean isCompliant = true;
   private List warnings;
   private PropSetterTable raPropSetterTable;
   private final Map adminPropSetterTables = new HashMap();
   private final Map connectionFactoryPropSetterTables = new HashMap();
   private final Map activationSpecPropSetterTables = new HashMap();
   private String moduleName = "";
   private PropertyNameNormalizer propertyNameNormalizer = null;

   public boolean isCompliant() {
      return this.isCompliant;
   }

   public List getWarnings() {
      return this.warnings;
   }

   public void setWarnings(List warnings) {
      this.warnings = warnings;
   }

   public boolean hasRAxml() {
      return this.hasRAxml;
   }

   public boolean isLinkRef() {
      return this.isLinkRef;
   }

   public String getLinkRef() {
      return this.linkRef;
   }

   public PropSetterTable getRAPropSetterTable() {
      if (this.raPropSetterTable == null) {
         this.raPropSetterTable = new PropSetterTableImpl(this.propertyNameNormalizer);
      }

      return this.raPropSetterTable;
   }

   public PropSetterTable getAdminPropSetterTable(String adminInterface, String adminClass) {
      return this.getOrCreate(adminInterface + adminClass, this.adminPropSetterTables);
   }

   public PropSetterTable getConnectionFactoryPropSetterTable(String connectionFactoryInterface) {
      return this.getOrCreate(connectionFactoryInterface, this.connectionFactoryPropSetterTables);
   }

   public PropSetterTable getActivationSpecPropSetterTable(String activationSpecClass) {
      return this.getOrCreate(activationSpecClass, this.activationSpecPropSetterTables);
   }

   PropSetterTable getOrCreate(String key, Map tableMap) {
      PropSetterTable result = (PropSetterTable)tableMap.get(key);
      if (result == null) {
         result = new PropSetterTableImpl(this.propertyNameNormalizer);
         tableMap.put(key, result);
      }

      return (PropSetterTable)result;
   }

   public String getModuleName() {
      return this.moduleName;
   }

   void setLinkRef(String lRef) {
      this.linkRef = lRef;
      this.isLinkRef = this.linkRef != null;
   }

   void setModuleName(String moduleName) {
      this.moduleName = moduleName;
   }

   void setCompliant(boolean compliant) {
      this.isCompliant = compliant;
   }

   void setHasRAxml(boolean hasRAxml) {
      this.hasRAxml = hasRAxml;
   }

   public void setPropertyNameNormalizer(PropertyNameNormalizer propertyNameNormalizer) {
      this.propertyNameNormalizer = propertyNameNormalizer;
   }
}
