package org.opensaml.core.xml;

import java.util.List;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.collection.LockableClassToInstanceMultiMap;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.IDIndex;
import org.w3c.dom.Element;

public interface XMLObject {
   void detach();

   @Nullable
   Element getDOM();

   @Nonnull
   QName getElementQName();

   @Nonnull
   IDIndex getIDIndex();

   @Nonnull
   NamespaceManager getNamespaceManager();

   @Nonnull
   Set getNamespaces();

   @Nullable
   String getNoNamespaceSchemaLocation();

   @Nullable
   List getOrderedChildren();

   @Nullable
   XMLObject getParent();

   @Nullable
   String getSchemaLocation();

   @Nullable
   QName getSchemaType();

   boolean hasChildren();

   boolean hasParent();

   void releaseChildrenDOM(boolean var1);

   void releaseDOM();

   void releaseParentDOM(boolean var1);

   @Nullable
   XMLObject resolveID(@Nonnull String var1);

   @Nullable
   XMLObject resolveIDFromRoot(@Nonnull String var1);

   void setDOM(@Nullable Element var1);

   void setNoNamespaceSchemaLocation(@Nullable String var1);

   void setParent(@Nullable XMLObject var1);

   void setSchemaLocation(@Nullable String var1);

   @Nullable
   Boolean isNil();

   @Nullable
   XSBooleanValue isNilXSBoolean();

   void setNil(@Nullable Boolean var1);

   void setNil(@Nullable XSBooleanValue var1);

   @Nonnull
   LockableClassToInstanceMultiMap getObjectMetadata();
}
