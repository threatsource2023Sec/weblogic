package org.opensaml.core.xml.io;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.XMLObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarshallerFactory {
   private final Logger log = LoggerFactory.getLogger(MarshallerFactory.class);
   private final Map marshallers = new ConcurrentHashMap();

   @Nullable
   public Marshaller getMarshaller(@Nullable QName key) {
      return key == null ? null : (Marshaller)this.marshallers.get(key);
   }

   @Nullable
   public Marshaller getMarshaller(@Nonnull XMLObject xmlObject) {
      Marshaller marshaller = this.getMarshaller(xmlObject.getSchemaType());
      if (marshaller == null) {
         marshaller = this.getMarshaller(xmlObject.getElementQName());
      }

      return marshaller;
   }

   @Nonnull
   public Map getMarshallers() {
      return Collections.unmodifiableMap(this.marshallers);
   }

   public void registerMarshaller(@Nonnull QName key, @Nonnull Marshaller marshaller) {
      Constraint.isNotNull(key, "Marshaller key cannot be null");
      Constraint.isNotNull(marshaller, "Marshaller cannot be null");
      this.log.debug("Registering marshaller, {}, for object type {}", marshaller.getClass().getName(), key);
      this.marshallers.put(key, marshaller);
   }

   @Nullable
   public Marshaller deregisterMarshaller(@Nonnull QName key) {
      this.log.debug("Deregistering marshaller for object type {}", key);
      return key != null ? (Marshaller)this.marshallers.remove(key) : null;
   }
}
