package net.shibboleth.utilities.java.support.security;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.net.InetAddresses;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.ServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.AbstractIdentifiableInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.IPRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IPRangeAccessControl extends AbstractIdentifiableInitializableComponent implements AccessControl {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(IPRangeAccessControl.class);
   @Nonnull
   @NonnullElements
   private Collection allowedRanges = Collections.emptyList();

   public void setAllowedRanges(@Nonnull @NonnullElements Collection ranges) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      Constraint.isNotNull(ranges, "IPRange collection cannot be null");
      this.allowedRanges = new ArrayList(Collections2.filter(ranges, Predicates.notNull()));
   }

   public boolean checkAccess(@Nonnull ServletRequest request, @Nullable String operation, @Nullable String resource) {
      Constraint.isNotNull(request, "ServletRequest cannot be null");
      String addr = request.getRemoteAddr();
      if (addr != null) {
         this.log.debug("{} Evaluating client address '{}'", this.getLogPrefix(), addr);

         try {
            byte[] resolvedAddress = InetAddresses.forString(addr).getAddress();
            Iterator i$ = this.allowedRanges.iterator();

            while(i$.hasNext()) {
               IPRange range = (IPRange)i$.next();
               if (range.contains(resolvedAddress)) {
                  this.log.debug("{} Granted access to client address '{}' (Operation: {}, Resource: {})", new Object[]{this.getLogPrefix(), addr, operation, resource});
                  return true;
               }
            }
         } catch (IllegalArgumentException var8) {
            this.log.warn("{} Error translating client address", this.getLogPrefix(), var8);
         }

         this.log.warn("{} Denied request from client address '{}' (Operation: {}, Resource: {})", new Object[]{this.getLogPrefix(), addr, operation, resource});
      } else {
         this.log.warn("{} Denied request from client address 'unknown' (Operation: {}, Resource: {})", new Object[]{this.getLogPrefix(), operation, resource});
      }

      return false;
   }

   @Nonnull
   private String getLogPrefix() {
      return "Policy " + this.getId() + ":";
   }
}
