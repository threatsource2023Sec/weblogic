package org.opensaml.core.xml;

import com.google.common.base.Strings;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class NamespaceManager {
   public static final String DEFAULT_NS_TOKEN = "#default";
   private static final Namespace XML_NAMESPACE = new Namespace("http://www.w3.org/XML/1998/namespace", "xml");
   private static final Namespace XSI_NAMESPACE = new Namespace("http://www.w3.org/2001/XMLSchema-instance", "xsi");
   @Nonnull
   private final XMLObject owner;
   private Namespace elementName;
   private Namespace elementType;
   @Nonnull
   private final Set decls;
   @Nonnull
   private final Set attrNames;
   @Nonnull
   private final Map attrValues;
   private Namespace contentValue;

   public NamespaceManager(@Nonnull XMLObject owningObject) {
      this.owner = (XMLObject)Constraint.isNotNull(owningObject, "Owner XMLObject cannot be null");
      this.decls = new LazySet();
      this.attrNames = new LazySet();
      this.attrValues = new LazyMap();
   }

   @Nonnull
   @NotEmpty
   public static String generateAttributeID(@Nonnull QName name) {
      return name.toString();
   }

   @Nonnull
   public XMLObject getOwner() {
      return this.owner;
   }

   @Nonnull
   public Set getNamespaces() {
      Set namespaces = this.mergeNamespaceCollections(this.decls, this.attrNames, this.attrValues.values());
      this.addNamespace(namespaces, this.getElementNameNamespace());
      this.addNamespace(namespaces, this.getElementTypeNamespace());
      this.addNamespace(namespaces, this.contentValue);
      return Collections.unmodifiableSet(namespaces);
   }

   public void registerNamespaceDeclaration(@Nonnull Namespace namespace) {
      this.addNamespace(this.decls, namespace);
   }

   public void deregisterNamespaceDeclaration(@Nonnull Namespace namespace) {
      this.removeNamespace(this.decls, namespace);
   }

   @Nonnull
   public Set getNamespaceDeclarations() {
      return Collections.unmodifiableSet(this.decls);
   }

   public void registerAttributeName(@Nonnull QName attributeName) {
      if (this.checkQName(attributeName)) {
         this.addNamespace(this.attrNames, this.buildNamespace(attributeName));
      }

   }

   public void deregisterAttributeName(@Nonnull QName attributeName) {
      if (this.checkQName(attributeName)) {
         this.removeNamespace(this.attrNames, this.buildNamespace(attributeName));
      }

   }

   public void registerAttributeValue(@Nonnull String attributeID, @Nonnull QName attributeValue) {
      if (this.checkQName(attributeValue)) {
         this.attrValues.put(attributeID, this.buildNamespace(attributeValue));
      }

   }

   public void deregisterAttributeValue(@Nonnull String attributeID) {
      this.attrValues.remove(attributeID);
   }

   public void registerContentValue(@Nonnull QName content) {
      if (this.checkQName(content)) {
         this.contentValue = this.buildNamespace(content);
      }

   }

   public void deregisterContentValue() {
      this.contentValue = null;
   }

   @Nonnull
   public Set getNonVisibleNamespacePrefixes() {
      LazySet prefixes = new LazySet();
      this.addPrefixes(prefixes, this.getNonVisibleNamespaces());
      return prefixes;
   }

   @Nonnull
   public Set getNonVisibleNamespaces() {
      LazySet nonVisibleCandidates = new LazySet();
      List children = this.getOwner().getOrderedChildren();
      if (children != null) {
         Iterator var3 = children.iterator();

         while(var3.hasNext()) {
            XMLObject child = (XMLObject)var3.next();
            if (child != null) {
               Set childNonVisibleNamespaces = child.getNamespaceManager().getNonVisibleNamespaces();
               if (!childNonVisibleNamespaces.isEmpty()) {
                  nonVisibleCandidates.addAll(childNonVisibleNamespaces);
               }
            }
         }
      }

      nonVisibleCandidates.addAll(this.getNonVisibleNamespaceCandidates());
      nonVisibleCandidates.removeAll(this.getVisibleNamespaces());
      nonVisibleCandidates.remove(XML_NAMESPACE);
      return nonVisibleCandidates;
   }

   @Nonnull
   public Set getAllNamespacesInSubtreeScope() {
      LazySet namespaces = new LazySet();
      List children = this.getOwner().getOrderedChildren();
      Iterator var3;
      if (children != null) {
         var3 = children.iterator();

         while(var3.hasNext()) {
            XMLObject child = (XMLObject)var3.next();
            if (child != null) {
               Set childNamespaces = child.getNamespaceManager().getAllNamespacesInSubtreeScope();
               if (!childNamespaces.isEmpty()) {
                  namespaces.addAll(childNamespaces);
               }
            }
         }
      }

      var3 = this.getNamespaces().iterator();

      while(var3.hasNext()) {
         Namespace myNS = (Namespace)var3.next();
         namespaces.add(myNS);
      }

      return namespaces;
   }

   public void registerElementName(@Nonnull QName name) {
      if (this.checkQName(name)) {
         this.elementName = this.buildNamespace(name);
      }

   }

   public void registerElementType(@Nullable QName type) {
      if (type != null) {
         if (this.checkQName(type)) {
            this.elementType = this.buildNamespace(type);
         }
      } else {
         this.elementType = null;
      }

   }

   @Nullable
   private Namespace getElementNameNamespace() {
      if (this.elementName == null && this.checkQName(this.owner.getElementQName())) {
         this.elementName = this.buildNamespace(this.owner.getElementQName());
      }

      return this.elementName;
   }

   @Nullable
   private Namespace getElementTypeNamespace() {
      if (this.elementType == null) {
         QName type = this.owner.getSchemaType();
         if (type != null && this.checkQName(type)) {
            this.elementType = this.buildNamespace(type);
         }
      }

      return this.elementType;
   }

   @Nonnull
   private Namespace buildNamespace(@Nonnull QName name) {
      Constraint.isNotNull(name, "QName cannot be null");
      String uri = (String)Constraint.isNotNull(StringSupport.trimOrNull(name.getNamespaceURI()), "Namespace URI of QName cannot be null");
      String prefix = StringSupport.trimOrNull(name.getPrefix());
      return new Namespace(uri, prefix);
   }

   private void addNamespace(@Nonnull Set namespaces, @Nullable Namespace newNamespace) {
      if (newNamespace != null) {
         namespaces.add(newNamespace);
      }
   }

   private void removeNamespace(@Nonnull Set namespaces, @Nullable Namespace oldNamespace) {
      if (oldNamespace != null) {
         namespaces.remove(oldNamespace);
      }
   }

   @Nonnull
   private Set mergeNamespaceCollections(Collection... namespaces) {
      LazySet newNamespaces = new LazySet();
      Collection[] var3 = namespaces;
      int var4 = namespaces.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Collection nsCollection = var3[var5];
         Iterator var7 = nsCollection.iterator();

         while(var7.hasNext()) {
            Namespace ns = (Namespace)var7.next();
            if (ns != null) {
               this.addNamespace(newNamespaces, ns);
            }
         }
      }

      return newNamespaces;
   }

   @Nonnull
   private Set getVisibleNamespaces() {
      LazySet namespaces = new LazySet();
      if (this.getElementNameNamespace() != null) {
         namespaces.add(this.getElementNameNamespace());
      }

      if (this.getElementTypeNamespace() != null) {
         namespaces.add(XSI_NAMESPACE);
      }

      Iterator var2 = this.attrNames.iterator();

      while(var2.hasNext()) {
         Namespace attribName = (Namespace)var2.next();
         if (attribName != null) {
            namespaces.add(attribName);
         }
      }

      return namespaces;
   }

   @Nonnull
   private Set getNonVisibleNamespaceCandidates() {
      LazySet namespaces = new LazySet();
      if (this.getElementTypeNamespace() != null) {
         namespaces.add(this.getElementTypeNamespace());
      }

      Iterator var2 = this.attrValues.values().iterator();

      while(var2.hasNext()) {
         Namespace attribValue = (Namespace)var2.next();
         if (attribValue != null) {
            namespaces.add(attribValue);
         }
      }

      if (this.contentValue != null) {
         namespaces.add(this.contentValue);
      }

      return namespaces;
   }

   private void addPrefixes(@Nonnull Set prefixes, @Nonnull Collection namespaces) {
      String prefix;
      for(Iterator var3 = namespaces.iterator(); var3.hasNext(); prefixes.add(prefix)) {
         Namespace ns = (Namespace)var3.next();
         prefix = StringSupport.trimOrNull(ns.getNamespacePrefix());
         if (prefix == null) {
            prefix = "#default";
         }
      }

   }

   private boolean checkQName(@Nullable QName name) {
      if (name != null) {
         return !Strings.isNullOrEmpty(name.getNamespaceURI());
      } else {
         return false;
      }
   }
}
