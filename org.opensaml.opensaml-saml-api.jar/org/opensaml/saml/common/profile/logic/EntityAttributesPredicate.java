package org.opensaml.saml.common.profile.logic;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSAny;
import org.opensaml.core.xml.schema.XSBase64Binary;
import org.opensaml.core.xml.schema.XSBoolean;
import org.opensaml.core.xml.schema.XSDateTime;
import org.opensaml.core.xml.schema.XSInteger;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.core.xml.schema.XSURI;
import org.opensaml.saml.ext.saml2mdattr.EntityAttributes;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityAttributesPredicate implements Predicate {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(EntityAttributesPredicate.class);
   private final boolean trimTags;
   private final boolean matchAll;
   @Nonnull
   @NonnullElements
   private final Collection candidateSet;

   public EntityAttributesPredicate(@Nonnull @NonnullElements Collection candidates) {
      Constraint.isNotNull(candidates, "Attribute collection cannot be null");
      this.candidateSet = new ArrayList(Collections2.filter(candidates, Predicates.notNull()));
      this.trimTags = true;
      this.matchAll = false;
   }

   public EntityAttributesPredicate(@Nonnull @NonnullElements Collection candidates, boolean trim) {
      Constraint.isNotNull(candidates, "Attribute collection cannot be null");
      this.candidateSet = new ArrayList(Collections2.filter(candidates, Predicates.notNull()));
      this.trimTags = trim;
      this.matchAll = false;
   }

   public EntityAttributesPredicate(@Nonnull @NonnullElements Collection candidates, boolean trim, boolean all) {
      Constraint.isNotNull(candidates, "Attribute collection cannot be null");
      this.candidateSet = new ArrayList(Collections2.filter(candidates, Predicates.notNull()));
      this.trimTags = trim;
      this.matchAll = all;
   }

   public boolean getTrimTags() {
      return this.trimTags;
   }

   @Nonnull
   @NonnullElements
   @Unmodifiable
   @NotLive
   public Collection getCandidates() {
      return ImmutableList.copyOf(this.candidateSet);
   }

   public boolean apply(@Nullable EntityDescriptor input) {
      if (input == null) {
         return false;
      } else {
         Collection entityAttributes = null;
         Extensions exts = input.getExtensions();
         if (exts != null) {
            List children = exts.getUnknownXMLObjects(EntityAttributes.DEFAULT_ELEMENT_NAME);
            if (!children.isEmpty() && children.get(0) instanceof EntityAttributes) {
               if (entityAttributes == null) {
                  entityAttributes = new ArrayList();
               }

               entityAttributes.addAll(((EntityAttributes)children.get(0)).getAttributes());
            }
         }

         for(EntitiesDescriptor group = (EntitiesDescriptor)input.getParent(); group != null; group = (EntitiesDescriptor)group.getParent()) {
            exts = group.getExtensions();
            if (exts != null) {
               List children = exts.getUnknownXMLObjects(EntityAttributes.DEFAULT_ELEMENT_NAME);
               if (!children.isEmpty() && children.get(0) instanceof EntityAttributes) {
                  if (entityAttributes == null) {
                     entityAttributes = new ArrayList();
                  }

                  entityAttributes.addAll(((EntityAttributes)children.get(0)).getAttributes());
               }
            }
         }

         if (entityAttributes != null && !entityAttributes.isEmpty()) {
            EntityAttributesMatcher matcher = new EntityAttributesMatcher(entityAttributes);
            if (this.matchAll) {
               return Iterables.all(this.candidateSet, matcher);
            } else if (Iterables.tryFind(this.candidateSet, matcher).isPresent()) {
               return true;
            } else {
               return false;
            }
         } else {
            this.log.debug("no EntityAttributes extension found for {}", input.getEntityID());
            return false;
         }
      }
   }

   private class EntityAttributesMatcher implements Predicate {
      @Nonnull
      private final Logger log = LoggerFactory.getLogger(EntityAttributesPredicate.class);
      private final Collection attributes;

      public EntityAttributesMatcher(@Nonnull @NonnullElements Collection attrs) {
         this.attributes = (Collection)Constraint.isNotNull(attrs, "Extension attributes cannot be null");
      }

      public boolean apply(@Nonnull Candidate input) {
         List tagvals = input.values;
         List tagexps = input.regexps;
         boolean[] valflags = new boolean[tagvals.size()];
         boolean[] expflags = new boolean[tagexps.size()];
         Iterator var6 = this.attributes.iterator();

         while(true) {
            Attribute a;
            int tagindex;
            do {
               do {
                  do {
                     if (!var6.hasNext()) {
                        boolean[] var14 = valflags;
                        int var15 = valflags.length;

                        boolean flag;
                        for(tagindex = 0; tagindex < var15; ++tagindex) {
                           flag = var14[tagindex];
                           if (!flag) {
                              return false;
                           }
                        }

                        var14 = expflags;
                        var15 = expflags.length;

                        for(tagindex = 0; tagindex < var15; ++tagindex) {
                           flag = var14[tagindex];
                           if (!flag) {
                              return false;
                           }
                        }

                        return true;
                     }

                     a = (Attribute)var6.next();
                  } while(a.getName() == null);
               } while(!a.getName().equals(input.getName()));
            } while(input.getNameFormat() != null && !input.getNameFormat().equals(a.getNameFormat()));

            for(tagindex = 0; tagindex < tagvals.size(); ++tagindex) {
               String tagvalstr = (String)tagvals.get(tagindex);
               List cvalsx = a.getAttributeValues();
               Iterator var11 = cvalsx.iterator();

               while(var11.hasNext()) {
                  XMLObject cval = (XMLObject)var11.next();
                  String cvalstr = this.xmlObjectToString(cval);
                  if (tagvalstr != null && cvalstr != null) {
                     if (tagvalstr.equals(cvalstr)) {
                        valflags[tagindex] = true;
                        break;
                     }

                     if (EntityAttributesPredicate.this.trimTags && tagvalstr.equals(cvalstr.trim())) {
                        valflags[tagindex] = true;
                        break;
                     }
                  }
               }
            }

            for(tagindex = 0; tagindex < tagexps.size(); ++tagindex) {
               List cvals = a.getAttributeValues();
               Iterator var18 = cvals.iterator();

               while(var18.hasNext()) {
                  XMLObject cvalx = (XMLObject)var18.next();
                  String cvalstrx = this.xmlObjectToString(cvalx);
                  if (tagexps.get(tagindex) != null && cvalstrx != null && ((Pattern)tagexps.get(tagindex)).matcher(cvalstrx).matches()) {
                     expflags[tagindex] = true;
                     break;
                  }
               }
            }
         }
      }

      @Nullable
      private String xmlObjectToString(@Nonnull XMLObject object) {
         String toMatch = null;
         if (object instanceof XSString) {
            toMatch = ((XSString)object).getValue();
         } else if (object instanceof XSURI) {
            toMatch = ((XSURI)object).getValue();
         } else if (object instanceof XSBoolean) {
            toMatch = ((XSBoolean)object).getValue().getValue() ? "1" : "0";
         } else if (object instanceof XSInteger) {
            toMatch = ((XSInteger)object).getValue().toString();
         } else if (object instanceof XSDateTime) {
            DateTime dt = ((XSDateTime)object).getValue();
            if (dt != null) {
               toMatch = ((XSDateTime)object).getDateTimeFormatter().print(dt);
            }
         } else if (object instanceof XSBase64Binary) {
            toMatch = ((XSBase64Binary)object).getValue();
         } else if (object instanceof XSAny) {
            XSAny wc = (XSAny)object;
            if (wc.getUnknownAttributes().isEmpty() && wc.getUnknownXMLObjects().isEmpty()) {
               toMatch = wc.getTextContent();
            }
         }

         if (toMatch != null) {
            return toMatch;
         } else {
            this.log.warn("Unrecognized XMLObject type ({}), unable to convert to a string for comparison", object.getClass().getName());
            return null;
         }
      }
   }

   public static class Candidate {
      @Nonnull
      @NotEmpty
      private final String nam;
      @Nullable
      private final String nameFormat;
      @Nonnull
      @NonnullElements
      private List values;
      @Nonnull
      @NonnullElements
      private List regexps;

      public Candidate(@Nonnull @NotEmpty String name) {
         this.nam = (String)Constraint.isNotNull(StringSupport.trimOrNull(name), "Attribute Name cannot be null or empty");
         this.nameFormat = null;
         this.values = Collections.emptyList();
         this.regexps = Collections.emptyList();
      }

      public Candidate(@Nonnull @NotEmpty String name, @Nullable String format) {
         this.nam = (String)Constraint.isNotNull(StringSupport.trimOrNull(name), "Attribute Name cannot be null or empty");
         if ("urn:oasis:names:tc:SAML:2.0:attrname-format:unspecified".equals(format)) {
            this.nameFormat = null;
         } else {
            this.nameFormat = StringSupport.trimOrNull(format);
         }

         this.values = Collections.emptyList();
         this.regexps = Collections.emptyList();
      }

      @Nonnull
      @NotEmpty
      public String getName() {
         return this.nam;
      }

      @Nullable
      public String getNameFormat() {
         return this.nameFormat;
      }

      @Nonnull
      @NonnullElements
      @Unmodifiable
      @NotLive
      public List getValues() {
         return ImmutableList.copyOf(this.values);
      }

      public void setValues(@Nonnull @NonnullElements Collection vals) {
         Constraint.isNotNull(vals, "Values collection cannot be null");
         this.values = new ArrayList(vals.size());
         Iterator var2 = vals.iterator();

         while(var2.hasNext()) {
            String value = (String)var2.next();
            if (value != null) {
               this.values.add(value);
            }
         }

      }

      @Nonnull
      @NonnullElements
      @Unmodifiable
      @NotLive
      public List getRegexps() {
         return ImmutableList.copyOf(this.regexps);
      }

      public void setRegexps(@Nonnull @NonnullElements Collection exps) {
         Constraint.isNotNull(exps, "Regular expressions collection cannot be null");
         this.regexps = new ArrayList(Collections2.filter(exps, Predicates.notNull()));
      }
   }
}
