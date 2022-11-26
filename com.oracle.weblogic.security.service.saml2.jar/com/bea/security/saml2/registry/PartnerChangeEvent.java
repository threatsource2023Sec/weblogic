package com.bea.security.saml2.registry;

import com.bea.common.security.store.data.PartnerId;

public interface PartnerChangeEvent {
   PartnerId getDeletedPartnerId();

   PartnerId getAddedPartnerId();

   PartnerId getUpdatedPartnerId();
}
