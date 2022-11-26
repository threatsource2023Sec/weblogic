package org.opensaml.xmlsec.keyinfo.impl;

import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.collection.LazySet;
import org.opensaml.xmlsec.signature.KeyInfo;

public class KeyInfoResolutionContext {
   private KeyInfo keyInfo;
   private final Set keyNames;
   private Key key;
   private final Collection resolvedCredentials;
   private final Map properties;

   public KeyInfoResolutionContext(@Nonnull Collection credentials) {
      this.resolvedCredentials = Collections.unmodifiableCollection(credentials);
      this.properties = new LazyMap();
      this.keyNames = new LazySet();
   }

   @Nullable
   public KeyInfo getKeyInfo() {
      return this.keyInfo;
   }

   public void setKeyInfo(@Nullable KeyInfo newKeyInfo) {
      this.keyInfo = newKeyInfo;
   }

   @Nonnull
   public Set getKeyNames() {
      return this.keyNames;
   }

   @Nullable
   public Key getKey() {
      return this.key;
   }

   public void setKey(@Nullable Key newKey) {
      this.key = newKey;
   }

   @Nonnull
   public Collection getResolvedCredentials() {
      return this.resolvedCredentials;
   }

   @Nonnull
   public Map getProperties() {
      return this.properties;
   }
}
