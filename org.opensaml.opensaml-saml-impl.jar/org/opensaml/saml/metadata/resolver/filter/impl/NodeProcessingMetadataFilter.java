package org.opensaml.saml.metadata.resolver.filter.impl;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.Live;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.metadata.resolver.filter.FilterException;
import org.opensaml.saml.metadata.resolver.filter.MetadataFilter;
import org.opensaml.saml.metadata.resolver.filter.MetadataNodeProcessor;

public class NodeProcessingMetadataFilter extends AbstractInitializableComponent implements MetadataFilter {
   @Nonnull
   @NonnullElements
   private List processors = Collections.emptyList();

   @Nonnull
   @NonnullElements
   @Live
   public List getNodeProcessors() {
      return this.processors;
   }

   public void setNodeProcessors(@Nonnull @NonnullElements List newProcessors) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      Constraint.isNotNull(newProcessors, "MetadataNodeProcessor list cannot be null");
      this.processors = new ArrayList(Collections2.filter(newProcessors, Predicates.notNull()));
   }

   @Nullable
   public XMLObject filter(@Nullable XMLObject metadata) throws FilterException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      if (metadata == null) {
         return null;
      } else {
         this.processNode(metadata);
         return metadata;
      }
   }

   protected void doDestroy() {
      this.processors = null;
      super.doDestroy();
   }

   protected void processNode(XMLObject node) throws FilterException {
      Iterator var2 = this.getNodeProcessors().iterator();

      while(var2.hasNext()) {
         MetadataNodeProcessor processor = (MetadataNodeProcessor)var2.next();
         processor.process(node);
      }

      List children = node.getOrderedChildren();
      if (children != null) {
         Iterator var6 = node.getOrderedChildren().iterator();

         while(var6.hasNext()) {
            XMLObject child = (XMLObject)var6.next();
            if (child != null) {
               this.processNode(child);
            }
         }
      }

   }
}
