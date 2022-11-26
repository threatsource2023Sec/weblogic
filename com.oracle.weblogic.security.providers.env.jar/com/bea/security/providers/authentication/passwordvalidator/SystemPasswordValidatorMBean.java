package com.bea.security.providers.authentication.passwordvalidator;

import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.security.ProviderMBean;
import weblogic.management.security.authentication.PasswordValidatorMBean;

public interface SystemPasswordValidatorMBean extends StandardInterface, DescriptorBean, ProviderMBean, PasswordValidatorMBean {
   String getProviderClassName();

   String getDescription();

   String getVersion();

   boolean getRejectEqualOrContainUsername();

   void setRejectEqualOrContainUsername(boolean var1) throws InvalidAttributeValueException;

   boolean getRejectEqualOrContainReverseUsername();

   void setRejectEqualOrContainReverseUsername(boolean var1) throws InvalidAttributeValueException;

   int getMaxPasswordLength();

   void setMaxPasswordLength(int var1) throws InvalidAttributeValueException;

   int getMinPasswordLength();

   void setMinPasswordLength(int var1) throws InvalidAttributeValueException;

   int getMaxInstancesOfAnyCharacter();

   void setMaxInstancesOfAnyCharacter(int var1) throws InvalidAttributeValueException;

   int getMaxConsecutiveCharacters();

   void setMaxConsecutiveCharacters(int var1) throws InvalidAttributeValueException;

   int getMinAlphabeticCharacters();

   void setMinAlphabeticCharacters(int var1) throws InvalidAttributeValueException;

   int getMinNumericCharacters();

   void setMinNumericCharacters(int var1) throws InvalidAttributeValueException;

   int getMinLowercaseCharacters();

   void setMinLowercaseCharacters(int var1) throws InvalidAttributeValueException;

   int getMinUppercaseCharacters();

   void setMinUppercaseCharacters(int var1) throws InvalidAttributeValueException;

   int getMinNonAlphanumericCharacters();

   void setMinNonAlphanumericCharacters(int var1) throws InvalidAttributeValueException;

   int getMinNumericOrSpecialCharacters();

   void setMinNumericOrSpecialCharacters(int var1) throws InvalidAttributeValueException;

   String getName();
}
