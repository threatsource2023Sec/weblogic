package org.opensaml.core.xml;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.LockableClassToInstanceMultiMap;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import net.shibboleth.utilities.java.support.xml.XMLConstants;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.IDIndex;
import org.opensaml.core.xml.util.XMLObjectSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public abstract class AbstractXMLObject implements XMLObject {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AbstractXMLObject.class);
   private XMLObject parent;
   @Nonnull
   private QName elementQname;
   private String schemaLocation;
   private String noNamespaceSchemaLocation;
   private QName typeQname;
   private Element dom;
   private XSBooleanValue nil;
   private NamespaceManager nsManager = new NamespaceManager(this);
   @Nonnull
   private final LockableClassToInstanceMultiMap objectMetadata;
   private final IDIndex idIndex = new IDIndex(this);

   protected AbstractXMLObject(@Nullable String namespaceURI, @Nonnull @NotEmpty String elementLocalName, @Nullable String namespacePrefix) {
      this.elementQname = QNameSupport.constructQName(namespaceURI, elementLocalName, namespacePrefix);
      if (namespaceURI != null) {
         this.setElementNamespacePrefix(namespacePrefix);
      }

      this.objectMetadata = new LockableClassToInstanceMultiMap(true);
   }

   public void detach() {
      this.releaseParentDOM(true);
      this.parent = null;
   }

   @Nullable
   public Element getDOM() {
      return this.dom;
   }

   @Nonnull
   public QName getElementQName() {
      return this.elementQname;
   }

   @Nonnull
   public IDIndex getIDIndex() {
      return this.idIndex;
   }

   @Nonnull
   public NamespaceManager getNamespaceManager() {
      return this.nsManager;
   }

   @Nonnull
   public Set getNamespaces() {
      return this.getNamespaceManager().getNamespaces();
   }

   @Nullable
   public String getNoNamespaceSchemaLocation() {
      return this.noNamespaceSchemaLocation;
   }

   @Nullable
   public XMLObject getParent() {
      return this.parent;
   }

   @Nullable
   public String getSchemaLocation() {
      return this.schemaLocation;
   }

   @Nullable
   public QName getSchemaType() {
      return this.typeQname;
   }

   public boolean hasChildren() {
      List children = this.getOrderedChildren();
      return children != null && children.size() > 0;
   }

   public boolean hasParent() {
      return this.getParent() != null;
   }

   protected void manageQualifiedAttributeNamespace(@Nonnull QName attributeName, boolean hasValue) {
      if (hasValue) {
         this.getNamespaceManager().registerAttributeName(attributeName);
      } else {
         this.getNamespaceManager().deregisterAttributeName(attributeName);
      }

   }

   @Nullable
   protected QName prepareElementContentForAssignment(@Nullable QName oldValue, @Nullable QName newValue) {
      if (oldValue == null) {
         if (newValue != null) {
            this.getNamespaceManager().registerContentValue(newValue);
            this.releaseThisandParentDOM();
            return newValue;
         } else {
            return null;
         }
      } else {
         this.getNamespaceManager().deregisterContentValue();
         if (!oldValue.equals(newValue)) {
            if (newValue != null) {
               this.getNamespaceManager().registerContentValue(newValue);
            }

            this.releaseThisandParentDOM();
         }

         return newValue;
      }
   }

   @Nullable
   protected QName prepareAttributeValueForAssignment(@Nonnull String attributeID, @Nullable QName oldValue, @Nullable QName newValue) {
      if (oldValue == null) {
         if (newValue != null) {
            this.getNamespaceManager().registerAttributeValue(attributeID, newValue);
            this.releaseThisandParentDOM();
            return newValue;
         } else {
            return null;
         }
      } else {
         this.getNamespaceManager().deregisterAttributeValue(attributeID);
         if (!oldValue.equals(newValue)) {
            if (newValue != null) {
               this.getNamespaceManager().registerAttributeValue(attributeID, newValue);
            }

            this.releaseThisandParentDOM();
         }

         return newValue;
      }
   }

   @Nullable
   protected String prepareForAssignment(@Nullable String oldValue, @Nullable String newValue) {
      return this.prepareForAssignment(oldValue, newValue, true);
   }

   @Nullable
   protected String prepareForAssignment(@Nullable String oldValue, @Nullable String newValue, boolean normalize) {
      String newString = newValue;
      if (normalize) {
         newString = StringSupport.trimOrNull(newValue);
      }

      if (!Objects.equals(oldValue, newString)) {
         this.releaseThisandParentDOM();
      }

      return newString;
   }

   @Nullable
   protected Object prepareForAssignment(@Nullable Object oldValue, @Nullable Object newValue) {
      if (oldValue == null) {
         if (newValue != null) {
            this.releaseThisandParentDOM();
            return newValue;
         } else {
            return null;
         }
      } else {
         if (!oldValue.equals(newValue)) {
            this.releaseThisandParentDOM();
         }

         return newValue;
      }
   }

   @Nullable
   protected XMLObject prepareForAssignment(@Nullable XMLObject oldValue, @Nullable XMLObject newValue) {
      if (newValue != null && newValue.hasParent()) {
         throw new IllegalArgumentException(newValue.getClass().getName() + " cannot be added - it is already the child of another XML Object");
      } else if (oldValue == null) {
         if (newValue != null) {
            this.releaseThisandParentDOM();
            newValue.setParent(this);
            this.idIndex.registerIDMappings(newValue.getIDIndex());
            return newValue;
         } else {
            return null;
         }
      } else {
         if (!oldValue.equals(newValue)) {
            oldValue.setParent((XMLObject)null);
            this.releaseThisandParentDOM();
            this.idIndex.deregisterIDMappings(oldValue.getIDIndex());
            if (newValue != null) {
               newValue.setParent(this);
               this.idIndex.registerIDMappings(newValue.getIDIndex());
            }
         }

         return newValue;
      }
   }

   protected void registerOwnID(@Nullable String oldID, @Nullable String newID) {
      String newString = StringSupport.trimOrNull(newID);
      if (!Objects.equals(oldID, newString)) {
         if (oldID != null) {
            this.idIndex.deregisterIDMapping(oldID);
         }

         if (newString != null) {
            this.idIndex.registerIDMapping(newString, this);
         }
      }

   }

   public void releaseChildrenDOM(boolean propagateRelease) {
      this.log.trace("Releasing cached DOM reprsentation for children of {} with propagation set to {}", this.getElementQName(), propagateRelease);
      if (this.getOrderedChildren() != null) {
         Iterator var2 = this.getOrderedChildren().iterator();

         while(var2.hasNext()) {
            XMLObject child = (XMLObject)var2.next();
            if (child != null) {
               child.releaseDOM();
               if (propagateRelease) {
                  child.releaseChildrenDOM(propagateRelease);
               }
            }
         }
      }

   }

   public void releaseDOM() {
      this.log.trace("Releasing cached DOM reprsentation for {}", this.getElementQName());
      this.setDOM((Element)null);
      if (this.getObjectMetadata().containsKey(XMLObjectSource.class)) {
         this.log.trace("Releasing cached XMLObjectSource for {}", this.getElementQName());
         this.getObjectMetadata().remove(XMLObjectSource.class);
      }

   }

   public void releaseParentDOM(boolean propagateRelease) {
      this.log.trace("Releasing cached DOM reprsentation for parent of {} with propagation set to {}", this.getElementQName(), propagateRelease);
      XMLObject parentElement = this.getParent();
      if (parentElement != null) {
         this.parent.releaseDOM();
         if (propagateRelease) {
            this.parent.releaseParentDOM(propagateRelease);
         }
      }

   }

   public void releaseThisAndChildrenDOM() {
      if (this.getDOM() != null) {
         this.releaseDOM();
         this.releaseChildrenDOM(true);
      }

   }

   public void releaseThisandParentDOM() {
      if (this.getDOM() != null) {
         this.releaseDOM();
         this.releaseParentDOM(true);
      }

   }

   @Nullable
   public XMLObject resolveID(@Nonnull @NotEmpty String id) {
      return this.idIndex.lookup(id);
   }

   @Nullable
   public XMLObject resolveIDFromRoot(@Nonnull @NotEmpty String id) {
      Object root;
      for(root = this; ((XMLObject)root).hasParent(); root = ((XMLObject)root).getParent()) {
      }

      return ((XMLObject)root).resolveID(id);
   }

   public void setDOM(@Nullable Element newDom) {
      this.dom = newDom;
   }

   public void setElementNamespacePrefix(@Nullable String prefix) {
      if (prefix == null) {
         this.elementQname = new QName(this.elementQname.getNamespaceURI(), this.elementQname.getLocalPart());
      } else {
         this.elementQname = new QName(this.elementQname.getNamespaceURI(), this.elementQname.getLocalPart(), prefix);
      }

      this.getNamespaceManager().registerElementName(this.elementQname);
   }

   protected void setElementQName(@Nonnull QName name) {
      Constraint.isNotNull(name, "Element QName cannot be null");
      this.elementQname = QNameSupport.constructQName(name.getNamespaceURI(), name.getLocalPart(), name.getPrefix());
      this.getNamespaceManager().registerElementName(this.elementQname);
   }

   public void setNoNamespaceSchemaLocation(@Nullable String location) {
      this.noNamespaceSchemaLocation = StringSupport.trimOrNull(location);
      this.manageQualifiedAttributeNamespace(XMLConstants.XSI_NO_NAMESPACE_SCHEMA_LOCATION_ATTRIB_NAME, this.noNamespaceSchemaLocation != null);
   }

   public void setParent(@Nullable XMLObject newParent) {
      this.parent = newParent;
   }

   public void setSchemaLocation(@Nullable String location) {
      this.schemaLocation = StringSupport.trimOrNull(location);
      this.manageQualifiedAttributeNamespace(XMLConstants.XSI_SCHEMA_LOCATION_ATTRIB_NAME, this.schemaLocation != null);
   }

   protected void setSchemaType(@Nullable QName type) {
      this.typeQname = type;
      this.getNamespaceManager().registerElementType(this.typeQname);
      this.manageQualifiedAttributeNamespace(XMLConstants.XSI_TYPE_ATTRIB_NAME, this.typeQname != null);
   }

   public Boolean isNil() {
      return this.nil != null ? this.nil.getValue() : Boolean.FALSE;
   }

   public XSBooleanValue isNilXSBoolean() {
      return this.nil;
   }

   public void setNil(@Nullable Boolean newNil) {
      if (newNil != null) {
         this.nil = (XSBooleanValue)this.prepareForAssignment((Object)this.nil, (Object)(new XSBooleanValue(newNil, false)));
      } else {
         this.nil = (XSBooleanValue)this.prepareForAssignment((Object)this.nil, (Object)null);
      }

      this.manageQualifiedAttributeNamespace(XMLConstants.XSI_NIL_ATTRIB_NAME, this.nil != null);
   }

   public void setNil(@Nullable XSBooleanValue newNil) {
      this.nil = (XSBooleanValue)this.prepareForAssignment((Object)this.nil, (Object)newNil);
      this.manageQualifiedAttributeNamespace(XMLConstants.XSI_NIL_ATTRIB_NAME, this.nil != null);
   }

   @Nonnull
   public LockableClassToInstanceMultiMap getObjectMetadata() {
      return this.objectMetadata;
   }
}
