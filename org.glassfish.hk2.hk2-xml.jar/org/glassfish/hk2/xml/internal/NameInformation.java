package org.glassfish.hk2.xml.internal;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class NameInformation {
   private final Map nameMapping;
   private final Set noXmlElement;
   private final Map addMethodToVariableName;
   private final Map removeMethodToVariableName;
   private final Map lookupMethodToVariableName;
   private final Set referenceSet;
   private final Map aliases;
   private final XmlElementData valueData;

   public NameInformation(Map nameMapping, Set unmappedNames, Map addMethodToVariableName, Map removeMethodToVariableName, Map lookupMethodToVariableName, Set referenceSet, Map aliases, XmlElementData valueData) {
      this.nameMapping = nameMapping;
      this.noXmlElement = unmappedNames;
      this.addMethodToVariableName = addMethodToVariableName;
      this.removeMethodToVariableName = removeMethodToVariableName;
      this.lookupMethodToVariableName = lookupMethodToVariableName;
      this.referenceSet = referenceSet;
      this.aliases = aliases;
      this.valueData = valueData;
   }

   public String getNamespaceMap(String mapMe) {
      if (mapMe == null) {
         return null;
      } else {
         return !this.nameMapping.containsKey(mapMe) ? mapMe : ((XmlElementData)this.nameMapping.get(mapMe)).getNamespace();
      }
   }

   public String getNameMap(String mapMe) {
      if (mapMe == null) {
         return null;
      } else {
         return !this.nameMapping.containsKey(mapMe) ? mapMe : ((XmlElementData)this.nameMapping.get(mapMe)).getName();
      }
   }

   public List getAliases(String variableName) {
      return (List)this.aliases.get(variableName);
   }

   public String getDefaultNameMap(String mapMe) {
      if (mapMe == null) {
         return "\u0000";
      } else {
         return !this.nameMapping.containsKey(mapMe) ? "\u0000" : ((XmlElementData)this.nameMapping.get(mapMe)).getDefaultValue();
      }
   }

   public String getXmlWrapperTag(String mapMe) {
      if (mapMe == null) {
         return null;
      } else {
         return !this.nameMapping.containsKey(mapMe) ? null : ((XmlElementData)this.nameMapping.get(mapMe)).getXmlWrapperTag();
      }
   }

   public boolean hasNoXmlElement(String variableName) {
      return variableName == null ? true : this.noXmlElement.contains(variableName);
   }

   public boolean isReference(String variableName) {
      return variableName == null ? false : this.referenceSet.contains(variableName);
   }

   public boolean isRequired(String variableName) {
      if (variableName == null) {
         return false;
      } else {
         return !this.nameMapping.containsKey(variableName) ? false : ((XmlElementData)this.nameMapping.get(variableName)).isRequired();
      }
   }

   public String getOriginalMethodName(String variableName) {
      if (variableName == null) {
         return null;
      } else {
         return !this.nameMapping.containsKey(variableName) ? null : ((XmlElementData)this.nameMapping.get(variableName)).getOriginalMethodName();
      }
   }

   public Format getFormat(String variableName) {
      if (variableName == null) {
         return Format.ATTRIBUTE;
      } else if (this.valueData != null && this.valueData.getName().equals(variableName)) {
         return Format.VALUE;
      } else {
         return !this.nameMapping.containsKey(variableName) ? Format.ATTRIBUTE : ((XmlElementData)this.nameMapping.get(variableName)).getFormat();
      }
   }

   public String getAddVariableName(String methodName) {
      return (String)this.addMethodToVariableName.get(methodName);
   }

   public String getRemoveVariableName(String methodName) {
      return (String)this.removeMethodToVariableName.get(methodName);
   }

   public String getLookupVariableName(String methodName) {
      return (String)this.lookupMethodToVariableName.get(methodName);
   }

   public String toString() {
      return "NameInformation(nameMapping=" + this.nameMapping + ",noXmlElement=" + this.noXmlElement + ",addMethodToVariableName=" + this.addMethodToVariableName + ",removeMethodToVariableName=" + this.removeMethodToVariableName + ",lookupMethodToVariableName=" + this.lookupMethodToVariableName + ",referenceSet=" + this.referenceSet + ",aliases=" + this.aliases + ",valueData=" + this.valueData + "," + System.identityHashCode(this) + ")";
   }
}
