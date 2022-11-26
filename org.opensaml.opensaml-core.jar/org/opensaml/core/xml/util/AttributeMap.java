package org.opensaml.core.xml.util;

import com.google.common.base.Strings;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.opensaml.core.xml.NamespaceManager;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NotThreadSafe
public class AttributeMap implements Map {
   private final Logger log = LoggerFactory.getLogger(AttributeMap.class);
   private final XMLObject attributeOwner;
   private Map attributes;
   private Set idAttribNames;
   private Set qnameAttribNames;
   private boolean inferQNameValues;

   public AttributeMap(@Nonnull XMLObject newOwner) {
      Constraint.isNotNull(newOwner, "Attribute owner XMLObject cannot be null");
      this.attributeOwner = newOwner;
      this.attributes = new LazyMap();
      this.idAttribNames = new LazySet();
      this.qnameAttribNames = new LazySet();
   }

   public String put(QName attributeName, String value) {
      Constraint.isNotNull(attributeName, "Attribute name cannot be null");
      String oldValue = this.get(attributeName);
      if (!Objects.equals(value, oldValue)) {
         this.releaseDOM();
         this.attributes.put(attributeName, value);
         if (this.isIDAttribute(attributeName) || XMLObjectProviderRegistrySupport.isIDAttribute(attributeName)) {
            this.attributeOwner.getIDIndex().deregisterIDMapping(oldValue);
            this.attributeOwner.getIDIndex().registerIDMapping(value, this.attributeOwner);
         }

         if (!Strings.isNullOrEmpty(attributeName.getNamespaceURI())) {
            if (value == null) {
               this.attributeOwner.getNamespaceManager().deregisterAttributeName(attributeName);
            } else {
               this.attributeOwner.getNamespaceManager().registerAttributeName(attributeName);
            }
         }

         this.checkAndDeregisterQNameValue(attributeName, oldValue);
         this.checkAndRegisterQNameValue(attributeName, value);
      }

      return oldValue;
   }

   public QName put(QName attributeName, QName value) {
      Constraint.isNotNull(attributeName, "Attribute name cannot be null");
      String oldValueString = this.get(attributeName);
      QName oldValue = null;
      if (!Strings.isNullOrEmpty(oldValueString)) {
         oldValue = this.resolveQName(oldValueString, true);
      }

      if (!Objects.equals(oldValue, value)) {
         this.releaseDOM();
         if (value != null) {
            String newStringValue = this.constructAttributeValue(value);
            this.attributes.put(attributeName, newStringValue);
            this.registerQNameValue(attributeName, value);
            this.attributeOwner.getNamespaceManager().registerAttributeName(attributeName);
         } else {
            this.deregisterQNameValue(attributeName);
            this.attributeOwner.getNamespaceManager().deregisterAttributeName(attributeName);
         }
      }

      return oldValue;
   }

   public void clear() {
      LazySet keys = new LazySet();
      keys.addAll(this.attributes.keySet());
      Iterator var2 = keys.iterator();

      while(var2.hasNext()) {
         QName attributeName = (QName)var2.next();
         this.remove(attributeName);
      }

   }

   public Set keySet() {
      return Collections.unmodifiableSet(this.attributes.keySet());
   }

   public int size() {
      return this.attributes.size();
   }

   public boolean isEmpty() {
      return this.attributes.isEmpty();
   }

   public boolean containsKey(Object key) {
      return this.attributes.containsKey(key);
   }

   public boolean containsValue(Object value) {
      return this.attributes.containsValue(value);
   }

   public String get(Object key) {
      return (String)this.attributes.get(key);
   }

   public String remove(Object key) {
      String removedValue = (String)this.attributes.remove(key);
      if (removedValue != null) {
         this.releaseDOM();
         QName attributeName = (QName)key;
         if (this.isIDAttribute(attributeName) || XMLObjectProviderRegistrySupport.isIDAttribute(attributeName)) {
            this.attributeOwner.getIDIndex().deregisterIDMapping(removedValue);
         }

         this.attributeOwner.getNamespaceManager().deregisterAttributeName(attributeName);
         this.checkAndDeregisterQNameValue(attributeName, removedValue);
      }

      return removedValue;
   }

   public void putAll(Map t) {
      if (t != null && t.size() > 0) {
         Iterator var2 = t.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            this.put((QName)entry.getKey(), (String)entry.getValue());
         }
      }

   }

   public Collection values() {
      return Collections.unmodifiableCollection(this.attributes.values());
   }

   public Set entrySet() {
      return Collections.unmodifiableSet(this.attributes.entrySet());
   }

   public void registerID(QName attributeName) {
      if (!this.idAttribNames.contains(attributeName)) {
         this.idAttribNames.add(attributeName);
      }

      if (this.containsKey(attributeName)) {
         this.attributeOwner.getIDIndex().registerIDMapping(this.get(attributeName), this.attributeOwner);
      }

   }

   public void deregisterID(QName attributeName) {
      if (this.idAttribNames.contains(attributeName)) {
         this.idAttribNames.remove(attributeName);
      }

      if (this.containsKey(attributeName)) {
         this.attributeOwner.getIDIndex().deregisterIDMapping(this.get(attributeName));
      }

   }

   public boolean isIDAttribute(QName attributeName) {
      return this.idAttribNames.contains(attributeName);
   }

   public void registerQNameAttribute(QName attributeName) {
      this.qnameAttribNames.add(attributeName);
   }

   public void deregisterQNameAttribute(QName attributeName) {
      this.qnameAttribNames.remove(attributeName);
   }

   public boolean isQNameAttribute(QName attributeName) {
      return this.qnameAttribNames.contains(attributeName);
   }

   public boolean isInferQNameValues() {
      return this.inferQNameValues;
   }

   public void setInferQNameValues(boolean flag) {
      this.inferQNameValues = flag;
   }

   private void releaseDOM() {
      this.attributeOwner.releaseDOM();
      this.attributeOwner.releaseParentDOM(true);
   }

   private void checkAndRegisterQNameValue(QName attributeName, String attributeValue) {
      if (attributeValue != null) {
         QName qnameValue = this.checkQName(attributeName, attributeValue);
         if (qnameValue != null) {
            this.log.trace("Attribute '{}' with value '{}' was evaluated to be QName type", attributeName, attributeValue);
            this.registerQNameValue(attributeName, qnameValue);
         } else {
            this.log.trace("Attribute '{}' with value '{}' was not evaluated to be QName type", attributeName, attributeValue);
         }

      }
   }

   private void registerQNameValue(QName attributeName, QName attributeValue) {
      if (attributeValue != null) {
         String attributeID = NamespaceManager.generateAttributeID(attributeName);
         this.log.trace("Registering QName attribute value '{}' under attibute ID '{}'", attributeValue, attributeID);
         this.attributeOwner.getNamespaceManager().registerAttributeValue(attributeID, attributeValue);
      }
   }

   private void checkAndDeregisterQNameValue(QName attributeName, String attributeValue) {
      if (attributeValue != null) {
         QName qnameValue = this.checkQName(attributeName, attributeValue);
         if (qnameValue != null) {
            this.log.trace("Attribute '{}' with value '{}' was evaluated to be QName type", attributeName, attributeValue);
            this.deregisterQNameValue(attributeName);
         } else {
            this.log.trace("Attribute '{}' with value '{}' was not evaluated to be QName type", attributeName, attributeValue);
         }

      }
   }

   private void deregisterQNameValue(QName attributeName) {
      String attributeID = NamespaceManager.generateAttributeID(attributeName);
      this.log.trace("Deregistering QName attribute with attibute ID '{}'", attributeID);
      this.attributeOwner.getNamespaceManager().deregisterAttributeValue(attributeID);
   }

   private QName checkQName(QName attributeName, String attributeValue) {
      this.log.trace("Checking whether attribute '{}' with value {} is a QName type", attributeName, attributeValue);
      if (attributeValue == null) {
         this.log.trace("Attribute value was null, returning null");
         return null;
      } else {
         QName valueName;
         if (this.isQNameAttribute(attributeName)) {
            this.log.trace("Configuration indicates attribute with name '{}' is a QName type, resolving value QName", attributeName);
            valueName = this.resolveQName(attributeValue, true);
            if (valueName != null) {
               this.log.trace("Successfully resolved attribute value to QName: {}", valueName);
            } else {
               this.log.trace("Could not resolve attribute value to QName, returning null");
            }

            return valueName;
         } else if (this.isInferQNameValues()) {
            this.log.trace("Attempting to infer whether attribute value is a QName");
            valueName = this.resolveQName(attributeValue, false);
            if (valueName != null) {
               this.log.trace("Resolved attribute as a QName: '{}'", valueName);
            } else {
               this.log.trace("Attribute value was not resolveable to a QName, returning null");
            }

            return valueName;
         } else {
            this.log.trace("Attribute was not registered in configuration as a QName type and QName inference is disabled");
            return null;
         }
      }
   }

   private QName resolveQName(String attributeValue, boolean isDefaultNSOK) {
      if (attributeValue == null) {
         return null;
      } else {
         this.log.trace("Attemtping to resolve QName from attribute value '{}'", attributeValue);
         String candidatePrefix = null;
         String localPart = null;
         int ci = attributeValue.indexOf(58);
         if (ci > -1) {
            candidatePrefix = attributeValue.substring(0, ci);
            this.log.trace("Evaluating candiate namespace prefix '{}'", candidatePrefix);
            localPart = attributeValue.substring(ci + 1);
         } else {
            if (!isDefaultNSOK) {
               this.log.trace("Value did not contain a colon, default namespace is disallowed, returning null");
               return null;
            }

            candidatePrefix = null;
            this.log.trace("Value did not contain a colon, evaluating as default namespace");
            localPart = attributeValue;
         }

         this.log.trace("Evaluated QName local part as '{}'", localPart);
         String nsURI = XMLObjectSupport.lookupNamespaceURI(this.attributeOwner, candidatePrefix);
         this.log.trace("Resolved namespace URI '{}'", nsURI);
         if (nsURI != null) {
            QName name = QNameSupport.constructQName(nsURI, localPart, candidatePrefix);
            this.log.trace("Resolved QName '{}'", name);
            return name;
         } else {
            this.log.trace("Namespace URI for candidate prefix '{}' could not be resolved", candidatePrefix);
            this.log.trace("Value was either not a QName, or namespace URI could not be resolved");
            return null;
         }
      }
   }

   private String constructAttributeValue(QName attributeValue) {
      String trimmedLocalName = StringSupport.trimOrNull(attributeValue.getLocalPart());
      if (trimmedLocalName == null) {
         throw new IllegalArgumentException("Local name may not be null or empty");
      } else {
         String trimmedPrefix = StringSupport.trimOrNull(attributeValue.getPrefix());
         String qualifiedName;
         if (trimmedPrefix != null) {
            qualifiedName = trimmedPrefix + ":" + StringSupport.trimOrNull(trimmedLocalName);
         } else {
            qualifiedName = StringSupport.trimOrNull(trimmedLocalName);
         }

         return qualifiedName;
      }
   }
}
