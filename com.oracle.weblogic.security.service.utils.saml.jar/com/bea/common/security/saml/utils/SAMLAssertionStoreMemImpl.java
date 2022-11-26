package com.bea.common.security.saml.utils;

import com.bea.common.logger.spi.LoggerSpi;
import java.util.Properties;
import org.w3c.dom.Element;
import weblogic.security.providers.saml.SAMLAssertionStoreV2;

public class SAMLAssertionStoreMemImpl extends SAMLAbstractMemCache implements SAMLAssertionStoreV2 {
   private static final String NAME = "AssertionStore";
   private static final long EXPIRE_PERIOD = 60000L;

   public SAMLAssertionStoreMemImpl() {
      super((LoggerSpi)null, "AssertionStore", 60000L);
   }

   public boolean initStore(Properties props) {
      return this.init();
   }

   public void releaseStore() {
      this.release();
   }

   public void flushStore() {
      this.flush();
   }

   public boolean storeAssertion(String artifact, long expire, Element assertion) {
      return this.addEntry(artifact, expire, new SAMLAssertionStoreV2.AssertionInfo(artifact, (String)null, expire, assertion));
   }

   public Element retrieveAssertion(String artifact) {
      SAMLAssertionStoreV2.AssertionInfo aInfo = (SAMLAssertionStoreV2.AssertionInfo)this.removeEntry(artifact);
      return aInfo != null ? aInfo.getAssertion() : null;
   }

   public boolean storeAssertionInfo(String artifact, String partnerId, long expire, Element assertion) {
      return this.addEntry(artifact, expire, new SAMLAssertionStoreV2.AssertionInfo(artifact, partnerId, expire, assertion));
   }

   public SAMLAssertionStoreV2.AssertionInfo retrieveAssertionInfo(String artifact) {
      return (SAMLAssertionStoreV2.AssertionInfo)this.removeEntry(artifact);
   }
}
