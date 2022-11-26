package org.opensaml.saml.common.profile.logic;

import com.google.common.base.Predicate;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.ext.saml2mdrpi.RegistrationInfo;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;

public abstract class AbstractRegistrationInfoPredicate implements Predicate {
   private boolean matchIfMetadataSilent;

   public void setMatchIfMetadataSilent(boolean flag) {
      this.matchIfMetadataSilent = flag;
   }

   public boolean getMatchIfMetadataSilent() {
      return this.matchIfMetadataSilent;
   }

   @Nullable
   protected RegistrationInfo getRegistrationInfo(@Nullable EntityDescriptor entity) {
      if (null == entity) {
         return null;
      } else {
         Extensions extensions = entity.getExtensions();
         if (null != extensions) {
            Iterator var3 = extensions.getUnknownXMLObjects(RegistrationInfo.DEFAULT_ELEMENT_NAME).iterator();

            while(var3.hasNext()) {
               XMLObject object = (XMLObject)var3.next();
               if (object instanceof RegistrationInfo) {
                  return (RegistrationInfo)object;
               }
            }
         }

         for(EntitiesDescriptor group = (EntitiesDescriptor)entity.getParent(); null != group; group = (EntitiesDescriptor)group.getParent()) {
            extensions = group.getExtensions();
            if (null != extensions) {
               Iterator var7 = extensions.getUnknownXMLObjects(RegistrationInfo.DEFAULT_ELEMENT_NAME).iterator();

               while(var7.hasNext()) {
                  XMLObject object = (XMLObject)var7.next();
                  if (object instanceof RegistrationInfo) {
                     return (RegistrationInfo)object;
                  }
               }
            }
         }

         return null;
      }
   }

   public boolean apply(@Nullable EntityDescriptor input) {
      RegistrationInfo info = this.getRegistrationInfo(input);
      return info != null ? this.doApply(info) : this.matchIfMetadataSilent;
   }

   protected abstract boolean doApply(@Nonnull RegistrationInfo var1);
}
