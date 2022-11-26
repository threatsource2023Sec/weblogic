package org.opensaml.saml.saml2.common;

import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;

public final class SAML2Support {
   private SAML2Support() {
   }

   public static boolean isValid(XMLObject xmlObject) {
      if (xmlObject instanceof TimeBoundSAMLObject) {
         TimeBoundSAMLObject timeBoundObject = (TimeBoundSAMLObject)xmlObject;
         if (!timeBoundObject.isValid()) {
            return false;
         }
      }

      XMLObject parent = xmlObject.getParent();
      return parent != null ? isValid(parent) : true;
   }

   @Nullable
   public static DateTime getEarliestExpiration(@Nullable XMLObject xmlObject) {
      DateTime now = new DateTime();
      return getEarliestExpiration(xmlObject, (DateTime)null, now);
   }

   @Nullable
   public static DateTime getEarliestExpiration(@Nullable XMLObject xmlObject, @Nullable DateTime candidateTime, @Nonnull DateTime now) {
      DateTime earliestExpiration = candidateTime;
      if (xmlObject instanceof CacheableSAMLObject) {
         earliestExpiration = getEarliestExpirationFromCacheable((CacheableSAMLObject)xmlObject, candidateTime, now);
      }

      if (xmlObject instanceof TimeBoundSAMLObject) {
         earliestExpiration = getEarliestExpirationFromTimeBound((TimeBoundSAMLObject)xmlObject, earliestExpiration);
      }

      if (xmlObject != null) {
         List children = xmlObject.getOrderedChildren();
         if (children != null) {
            Iterator var5 = xmlObject.getOrderedChildren().iterator();

            while(var5.hasNext()) {
               XMLObject child = (XMLObject)var5.next();
               if (child != null) {
                  earliestExpiration = getEarliestExpiration(child, earliestExpiration, now);
               }
            }
         }
      }

      return earliestExpiration;
   }

   @Nullable
   public static DateTime getEarliestExpirationFromCacheable(@Nonnull CacheableSAMLObject cacheableObject, @Nullable DateTime candidateTime, @Nonnull DateTime now) {
      DateTime earliestExpiration = candidateTime;
      if (cacheableObject.getCacheDuration() != null && cacheableObject.getCacheDuration() > 0L) {
         DateTime elementExpirationTime = now.plus(cacheableObject.getCacheDuration());
         if (candidateTime == null) {
            earliestExpiration = elementExpirationTime;
         } else if (elementExpirationTime != null && elementExpirationTime.isBefore(candidateTime)) {
            earliestExpiration = elementExpirationTime;
         }
      }

      return earliestExpiration;
   }

   @Nullable
   public static DateTime getEarliestExpirationFromTimeBound(@Nonnull TimeBoundSAMLObject timeBoundObject, @Nullable DateTime candidateTime) {
      DateTime earliestExpiration = candidateTime;
      DateTime elementExpirationTime = timeBoundObject.getValidUntil();
      if (candidateTime == null) {
         earliestExpiration = elementExpirationTime;
      } else if (elementExpirationTime != null && elementExpirationTime.isBefore(candidateTime)) {
         earliestExpiration = elementExpirationTime;
      }

      return earliestExpiration;
   }
}
