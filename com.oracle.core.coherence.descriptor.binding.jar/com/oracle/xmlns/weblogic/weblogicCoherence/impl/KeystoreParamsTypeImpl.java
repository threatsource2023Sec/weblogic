package com.oracle.xmlns.weblogic.weblogicCoherence.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicCoherence.KeystoreParamsType;
import javax.xml.namespace.QName;

public class KeystoreParamsTypeImpl extends XmlComplexContentImpl implements KeystoreParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName COHERENCEIDENTITYALIAS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-identity-alias");
   private static final QName COHERENCEPRIVATEKEYPASSPHRASEENCRYPTED$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-coherence", "coherence-private-key-pass-phrase-encrypted");

   public KeystoreParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getCoherenceIdentityAlias() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COHERENCEIDENTITYALIAS$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCoherenceIdentityAlias() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COHERENCEIDENTITYALIAS$0, 0);
         return target;
      }
   }

   public boolean isNilCoherenceIdentityAlias() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COHERENCEIDENTITYALIAS$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCoherenceIdentityAlias() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCEIDENTITYALIAS$0) != 0;
      }
   }

   public void setCoherenceIdentityAlias(String coherenceIdentityAlias) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COHERENCEIDENTITYALIAS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(COHERENCEIDENTITYALIAS$0);
         }

         target.setStringValue(coherenceIdentityAlias);
      }
   }

   public void xsetCoherenceIdentityAlias(XmlString coherenceIdentityAlias) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COHERENCEIDENTITYALIAS$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(COHERENCEIDENTITYALIAS$0);
         }

         target.set(coherenceIdentityAlias);
      }
   }

   public void setNilCoherenceIdentityAlias() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COHERENCEIDENTITYALIAS$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(COHERENCEIDENTITYALIAS$0);
         }

         target.setNil();
      }
   }

   public void unsetCoherenceIdentityAlias() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCEIDENTITYALIAS$0, 0);
      }
   }

   public String getCoherencePrivateKeyPassPhraseEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COHERENCEPRIVATEKEYPASSPHRASEENCRYPTED$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetCoherencePrivateKeyPassPhraseEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COHERENCEPRIVATEKEYPASSPHRASEENCRYPTED$2, 0);
         return target;
      }
   }

   public boolean isNilCoherencePrivateKeyPassPhraseEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COHERENCEPRIVATEKEYPASSPHRASEENCRYPTED$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetCoherencePrivateKeyPassPhraseEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(COHERENCEPRIVATEKEYPASSPHRASEENCRYPTED$2) != 0;
      }
   }

   public void setCoherencePrivateKeyPassPhraseEncrypted(String coherencePrivateKeyPassPhraseEncrypted) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(COHERENCEPRIVATEKEYPASSPHRASEENCRYPTED$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(COHERENCEPRIVATEKEYPASSPHRASEENCRYPTED$2);
         }

         target.setStringValue(coherencePrivateKeyPassPhraseEncrypted);
      }
   }

   public void xsetCoherencePrivateKeyPassPhraseEncrypted(XmlString coherencePrivateKeyPassPhraseEncrypted) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COHERENCEPRIVATEKEYPASSPHRASEENCRYPTED$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(COHERENCEPRIVATEKEYPASSPHRASEENCRYPTED$2);
         }

         target.set(coherencePrivateKeyPassPhraseEncrypted);
      }
   }

   public void setNilCoherencePrivateKeyPassPhraseEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(COHERENCEPRIVATEKEYPASSPHRASEENCRYPTED$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(COHERENCEPRIVATEKEYPASSPHRASEENCRYPTED$2);
         }

         target.setNil();
      }
   }

   public void unsetCoherencePrivateKeyPassPhraseEncrypted() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(COHERENCEPRIVATEKEYPASSPHRASEENCRYPTED$2, 0);
      }
   }
}
