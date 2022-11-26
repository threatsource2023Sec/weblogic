package com.bea.security.xacml.cache.resource;

import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.StringAttribute;
import com.bea.common.security.xacml.attr.StringAttributeBag;
import com.bea.common.security.xacml.policy.AttributeValue;
import com.bea.common.security.xacml.policy.ResourceAttributeDesignator;
import com.bea.common.security.xacml.policy.ResourceMatch;
import com.bea.common.security.xacml.policy.Resources;
import com.bea.common.security.xacml.policy.Target;
import com.bea.security.xacml.target.KnownResourceMatch;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import weblogic.security.spi.Resource;

public class ResourceMatchUtil {
   public static final String RESOURCE_ID = "urn:oasis:names:tc:xacml:1.0:resource:resource-id";
   public static final String RESOURCE_PARENT = "urn:oasis:names:tc:xacml:2.0:resource:resource-parent";
   public static final String RESOURCE_ANCESTOR = "urn:oasis:names:tc:xacml:2.0:resource:resource-ancestor";
   public static final String RESOURCE_ANCESTOR_OR_SELF = "urn:oasis:names:tc:xacml:2.0:resource:resource-ancestor-or-self";
   public static final String STRING_EQUAL = "urn:oasis:names:tc:xacml:1.0:function:string-equal";
   public static final String STRING_TYPE = "http://www.w3.org/2001/XMLSchema#string";
   private final URI resourceId;
   private final URI resourceParent;
   private final URI resourceAncestor;
   private final URI resourceAncestorOrSelf;
   private final URI stringEqual;
   private final URI stringType;

   public ResourceMatchUtil() throws URISyntaxException {
      try {
         this.resourceId = new URI("urn:oasis:names:tc:xacml:1.0:resource:resource-id");
         this.resourceParent = new URI("urn:oasis:names:tc:xacml:2.0:resource:resource-parent");
         this.resourceAncestor = new URI("urn:oasis:names:tc:xacml:2.0:resource:resource-ancestor");
         this.resourceAncestorOrSelf = new URI("urn:oasis:names:tc:xacml:2.0:resource:resource-ancestor-or-self");
         this.stringEqual = new URI("urn:oasis:names:tc:xacml:1.0:function:string-equal");
         this.stringType = new URI("http://www.w3.org/2001/XMLSchema#string");
      } catch (java.net.URISyntaxException var2) {
         throw new URISyntaxException(var2);
      }
   }

   public static List generateResourceMatches(Resource resource) throws URISyntaxException {
      return generateResourceMatches(resource.toString());
   }

   public static List generateResourceMatches(String resource) throws URISyntaxException {
      try {
         List matchList = new ArrayList();
         if (resource != null && resource.length() > 0) {
            matchList.add(new ResourceMatch(new URI("urn:oasis:names:tc:xacml:1.0:function:string-equal"), new AttributeValue(new StringAttribute(resource)), new ResourceAttributeDesignator(new URI("urn:oasis:names:tc:xacml:2.0:resource:resource-ancestor-or-self"), new URI("http://www.w3.org/2001/XMLSchema#string"), true)));
         }

         return !matchList.isEmpty() ? matchList : null;
      } catch (java.net.URISyntaxException var2) {
         throw new URISyntaxException(var2);
      }
   }

   public Collection generateKnownMatch(Resource r) {
      Collection col = new ArrayList();
      if (r != null) {
         StringAttribute self = new StringAttribute(r.toString());
         col.add(new KnownResourceMatch(this.stringEqual, this.resourceId, this.stringType, self, true));
         Resource p = r.getParentResource();
         if (p != null) {
            StringAttribute parent = new StringAttribute(p.toString());
            col.add(new KnownResourceMatch(this.stringEqual, this.resourceParent, this.stringType, parent, true));
            Collection ancestors = new ArrayList();
            Collection ancestorsOrSelf = new ArrayList();
            ancestors.add(parent);
            ancestorsOrSelf.add(self);
            ancestorsOrSelf.add(parent);

            for(Resource grandParent = p.getParentResource(); grandParent != null; grandParent = grandParent.getParentResource()) {
               StringAttribute ancestor = new StringAttribute(grandParent.toString());
               ancestors.add(ancestor);
               ancestorsOrSelf.add(ancestor);
            }

            col.add(new KnownResourceMatch(this.stringEqual, this.resourceAncestor, this.stringType, new StringAttributeBag(ancestors), true));
            col.add(new KnownResourceMatch(this.stringEqual, this.resourceAncestorOrSelf, this.stringType, new StringAttributeBag(ancestorsOrSelf), true));
         } else {
            col.add(new KnownResourceMatch(this.stringEqual, this.resourceAncestorOrSelf, this.stringType, self, true));
         }
      }

      return !col.isEmpty() ? col : null;
   }

   public Target updateTarget(Target t, String sourceRes, String destRes) {
      if (t == null) {
         return null;
      } else {
         List lr = new ArrayList();
         Resources tr = t.getResources();
         if (tr != null) {
            List trl = tr.getResources();
            if (trl != null) {
               Iterator var7 = trl.iterator();

               while(var7.hasNext()) {
                  com.bea.common.security.xacml.policy.Resource i = (com.bea.common.security.xacml.policy.Resource)var7.next();
                  List rm = new ArrayList();
                  List irm = i.getMatches();
                  if (irm != null) {
                     Iterator var11 = irm.iterator();

                     label56:
                     while(true) {
                        while(true) {
                           if (!var11.hasNext()) {
                              break label56;
                           }

                           ResourceMatch im = (ResourceMatch)var11.next();
                           if (this.stringEqual.equals(im.getMatchId())) {
                              ResourceAttributeDesignator rad = im.getDesignator();
                              if (rad != null && this.stringType.equals(rad.getDataType())) {
                                 URI attributeId = rad.getAttributeId();
                                 if ((this.resourceId.equals(attributeId) || this.resourceParent.equals(attributeId) || this.resourceAncestor.equals(attributeId) || this.resourceAncestorOrSelf.equals(attributeId)) && sourceRes.equals(((StringAttribute)im.getAttributeValue().getValue()).getValue())) {
                                    rm.add(new ResourceMatch(this.stringEqual, new StringAttribute(destRes), rad));
                                    continue;
                                 }
                              }
                           }

                           rm.add(im);
                        }
                     }
                  }

                  if (!rm.isEmpty()) {
                     lr.add(new com.bea.common.security.xacml.policy.Resource(rm));
                  }
               }
            }
         }

         Resources r = lr.isEmpty() ? null : new Resources(lr);
         return new Target(t.getSubjects(), r, t.getActions(), t.getEnvironments());
      }
   }

   public String getTargetResource(Target t) throws MultipleResourceTargetException {
      String res = null;
      if (t != null) {
         Resources rs = t.getResources();
         if (rs != null) {
            List rl = rs.getResources();
            if (rl.size() > 1) {
               throw new MultipleResourceTargetException("Multiple resources present in target; use alternate inspection method");
            }

            Iterator var5 = rl.iterator();

            label64:
            while(true) {
               com.bea.common.security.xacml.policy.Resource r;
               do {
                  if (!var5.hasNext()) {
                     return res;
                  }

                  r = (com.bea.common.security.xacml.policy.Resource)var5.next();
               } while(r == null);

               List rml = r.getMatches();
               Iterator var8 = rml.iterator();

               while(true) {
                  ResourceMatch rm;
                  URI attributeId;
                  do {
                     ResourceAttributeDesignator rad;
                     do {
                        do {
                           do {
                              if (!var8.hasNext()) {
                                 continue label64;
                              }

                              rm = (ResourceMatch)var8.next();
                           } while(!this.stringEqual.equals(rm.getMatchId()));

                           rad = rm.getDesignator();
                        } while(rad == null);
                     } while(!this.stringType.equals(rad.getDataType()));

                     attributeId = rad.getAttributeId();
                  } while(!this.resourceId.equals(attributeId) && !this.resourceParent.equals(attributeId) && !this.resourceAncestor.equals(attributeId) && !this.resourceAncestorOrSelf.equals(attributeId));

                  if (res != null) {
                     throw new MultipleResourceTargetException("Multiple resource matches present in target; use alternate inspection method");
                  }

                  res = ((StringAttribute)rm.getAttributeValue().getValue()).getValue();
               }
            }
         }
      }

      return res;
   }
}
