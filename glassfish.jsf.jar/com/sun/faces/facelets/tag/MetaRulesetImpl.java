package com.sun.faces.facelets.tag;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.beans.IntrospectionException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.MetaRule;
import javax.faces.view.facelets.MetaRuleset;
import javax.faces.view.facelets.Metadata;
import javax.faces.view.facelets.MetadataTarget;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagException;

public class MetaRulesetImpl extends MetaRuleset {
   private static final Logger LOGGER;
   private static final Map metadata;
   private final Tag tag;
   private final Class type;
   private final Map attributes;
   private final List mappers;
   private final List rules;
   private static final Metadata NONE;

   public MetaRulesetImpl(Tag tag, Class type) {
      this.tag = tag;
      this.type = type;
      this.attributes = new HashMap();
      this.mappers = new ArrayList();
      this.rules = new ArrayList();
      TagAttribute[] attrs = this.tag.getAttributes().getAll();

      for(int i = 0; i < attrs.length; ++i) {
         if (attrs[i].getLocalName().equals("class")) {
            this.attributes.put("styleClass", attrs[i]);
         } else {
            this.attributes.put(attrs[i].getLocalName(), attrs[i]);
         }
      }

      this.rules.add(BeanPropertyTagRule.Instance);
   }

   public MetaRuleset ignore(String attribute) {
      Util.notNull("attribute", attribute);
      this.attributes.remove(attribute);
      return this;
   }

   public MetaRuleset alias(String attribute, String property) {
      Util.notNull("attribute", attribute);
      Util.notNull("property", property);
      TagAttribute attr = (TagAttribute)this.attributes.remove(attribute);
      if (attr != null) {
         this.attributes.put(property, attr);
      }

      return this;
   }

   public MetaRuleset add(Metadata mapper) {
      Util.notNull("mapper", mapper);
      if (!this.mappers.contains(mapper)) {
         this.mappers.add(mapper);
      }

      return this;
   }

   public MetaRuleset addRule(MetaRule rule) {
      Util.notNull("rule", rule);
      this.rules.add(rule);
      return this;
   }

   public Metadata finish() {
      if (!this.attributes.isEmpty()) {
         if (this.rules.isEmpty()) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               Iterator itr = this.attributes.values().iterator();

               while(itr.hasNext()) {
                  LOGGER.severe(itr.next() + " Unhandled by MetaTagHandler for type " + this.type.getName());
               }
            }
         } else {
            MetadataTarget target = this.getMetadataTarget();
            int ruleEnd = this.rules.size() - 1;
            Iterator var3 = this.attributes.entrySet().iterator();

            while(var3.hasNext()) {
               Map.Entry entry = (Map.Entry)var3.next();
               Metadata data = null;

               for(int i = ruleEnd; data == null && i >= 0; --i) {
                  MetaRule rule = (MetaRule)this.rules.get(i);
                  data = rule.applyRule((String)entry.getKey(), (TagAttribute)entry.getValue(), target);
               }

               if (data == null) {
                  if (LOGGER.isLoggable(Level.SEVERE)) {
                     LOGGER.severe(entry.getValue() + " Unhandled by MetaTagHandler for type " + this.type.getName());
                  }
               } else {
                  this.mappers.add(data);
               }
            }
         }
      }

      return (Metadata)(this.mappers.isEmpty() ? NONE : new MetadataImpl((Metadata[])this.mappers.toArray(new Metadata[this.mappers.size()])));
   }

   public MetaRuleset ignoreAll() {
      this.attributes.clear();
      return this;
   }

   protected MetadataTarget getMetadataTarget() {
      WeakReference metaRef = (WeakReference)metadata.get(this.type);
      MetadataTarget meta = metaRef == null ? null : (MetadataTarget)metaRef.get();
      if (meta == null) {
         try {
            meta = new MetadataTargetImpl(this.type);
         } catch (IntrospectionException var4) {
            throw new TagException(this.tag, "Error Creating TargetMetadata", var4);
         }

         metadata.put(this.type, new WeakReference(meta));
      }

      return (MetadataTarget)meta;
   }

   static {
      LOGGER = FacesLogger.FACELETS_META.getLogger();
      metadata = Collections.synchronizedMap(new WeakHashMap());
      NONE = new Metadata() {
         public void applyMetadata(FaceletContext ctx, Object instance) {
         }
      };
   }
}
