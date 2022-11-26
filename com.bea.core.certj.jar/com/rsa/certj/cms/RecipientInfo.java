package com.rsa.certj.cms;

import com.rsa.jsafe.provider.JsafeJCE;

/** @deprecated */
public abstract class RecipientInfo {
   RecipientInfo() {
   }

   abstract com.rsa.jsafe.cms.RecipientInfo a(JsafeJCE var1) throws CMSException;
}
