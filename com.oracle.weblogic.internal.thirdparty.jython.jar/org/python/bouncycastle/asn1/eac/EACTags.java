package org.python.bouncycastle.asn1.eac;

import org.python.bouncycastle.asn1.ASN1ApplicationSpecific;

public class EACTags {
   public static final int COUNTRY_CODE_NATIONAL_DATA = 1;
   public static final int ISSUER_IDENTIFICATION_NUMBER = 2;
   public static final int CARD_SERVICE_DATA = 3;
   public static final int INITIAL_ACCESS_DATA = 4;
   public static final int CARD_ISSUER_DATA = 5;
   public static final int PRE_ISSUING_DATA = 6;
   public static final int CARD_CAPABILITIES = 7;
   public static final int STATUS_INFORMATION = 8;
   public static final int EXTENDED_HEADER_LIST = 13;
   public static final int APPLICATION_IDENTIFIER = 15;
   public static final int APPLICATION_LABEL = 16;
   public static final int FILE_REFERENCE = 17;
   public static final int COMMAND_TO_PERFORM = 18;
   public static final int DISCRETIONARY_DATA = 19;
   public static final int OFFSET_DATA_OBJECT = 20;
   public static final int TRACK1_APPLICATION = 22;
   public static final int TRACK2_APPLICATION = 23;
   public static final int TRACK3_APPLICATION = 24;
   public static final int CARD_EXPIRATION_DATA = 25;
   public static final int PRIMARY_ACCOUNT_NUMBER = 26;
   public static final int NAME = 27;
   public static final int TAG_LIST = 28;
   public static final int HEADER_LIST = 29;
   public static final int LOGIN_DATA = 30;
   public static final int CARDHOLDER_NAME = 32;
   public static final int TRACK1_CARD = 33;
   public static final int TRACK2_CARD = 34;
   public static final int TRACK3_CARD = 35;
   public static final int APPLICATION_EXPIRATION_DATE = 36;
   public static final int APPLICATION_EFFECTIVE_DATE = 37;
   public static final int CARD_EFFECTIVE_DATE = 38;
   public static final int INTERCHANGE_CONTROL = 39;
   public static final int COUNTRY_CODE = 40;
   public static final int INTERCHANGE_PROFILE = 41;
   public static final int CURRENCY_CODE = 42;
   public static final int DATE_OF_BIRTH = 43;
   public static final int CARDHOLDER_NATIONALITY = 44;
   public static final int LANGUAGE_PREFERENCES = 45;
   public static final int CARDHOLDER_BIOMETRIC_DATA = 46;
   public static final int PIN_USAGE_POLICY = 47;
   public static final int SERVICE_CODE = 48;
   public static final int TRANSACTION_COUNTER = 50;
   public static final int TRANSACTION_DATE = 51;
   public static final int CARD_SEQUENCE_NUMBER = 52;
   public static final int SEX = 53;
   public static final int CURRENCY_EXPONENT = 54;
   public static final int STATIC_INTERNAL_AUTHENTIFICATION_ONE_STEP = 55;
   public static final int SIGNATURE = 55;
   public static final int STATIC_INTERNAL_AUTHENTIFICATION_FIRST_DATA = 56;
   public static final int STATIC_INTERNAL_AUTHENTIFICATION_SECOND_DATA = 57;
   public static final int DYNAMIC_INTERNAL_AUTHENTIFICATION = 58;
   public static final int DYNAMIC_EXTERNAL_AUTHENTIFICATION = 59;
   public static final int DYNAMIC_MUTUAL_AUTHENTIFICATION = 60;
   public static final int CARDHOLDER_PORTRAIT_IMAGE = 64;
   public static final int ELEMENT_LIST = 65;
   public static final int ADDRESS = 66;
   public static final int CARDHOLDER_HANDWRITTEN_SIGNATURE = 67;
   public static final int APPLICATION_IMAGE = 68;
   public static final int DISPLAY_IMAGE = 69;
   public static final int TIMER = 70;
   public static final int MESSAGE_REFERENCE = 71;
   public static final int CARDHOLDER_PRIVATE_KEY = 72;
   public static final int CARDHOLDER_PUBLIC_KEY = 73;
   public static final int CERTIFICATION_AUTHORITY_PUBLIC_KEY = 74;
   public static final int DEPRECATED = 75;
   public static final int CERTIFICATE_HOLDER_AUTHORIZATION = 76;
   public static final int INTEGRATED_CIRCUIT_MANUFACTURER_ID = 77;
   public static final int CERTIFICATE_CONTENT = 78;
   public static final int UNIFORM_RESOURCE_LOCATOR = 80;
   public static final int ANSWER_TO_RESET = 81;
   public static final int HISTORICAL_BYTES = 82;
   public static final int DIGITAL_SIGNATURE = 61;
   public static final int APPLICATION_TEMPLATE = 1;
   public static final int FCP_TEMPLATE = 2;
   public static final int WRAPPER = 3;
   public static final int FMD_TEMPLATE = 4;
   public static final int CARDHOLDER_RELATIVE_DATA = 5;
   public static final int CARD_DATA = 6;
   public static final int AUTHENTIFICATION_DATA = 7;
   public static final int SPECIAL_USER_REQUIREMENTS = 8;
   public static final int LOGIN_TEMPLATE = 10;
   public static final int QUALIFIED_NAME = 11;
   public static final int CARDHOLDER_IMAGE_TEMPLATE = 12;
   public static final int APPLICATION_IMAGE_TEMPLATE = 13;
   public static final int APPLICATION_RELATED_DATA = 14;
   public static final int FCI_TEMPLATE = 15;
   public static final int DISCRETIONARY_DATA_OBJECTS = 19;
   public static final int COMPATIBLE_TAG_ALLOCATION_AUTHORITY = 24;
   public static final int COEXISTANT_TAG_ALLOCATION_AUTHORITY = 25;
   public static final int SECURITY_SUPPORT_TEMPLATE = 26;
   public static final int SECURITY_ENVIRONMENT_TEMPLATE = 27;
   public static final int DYNAMIC_AUTHENTIFICATION_TEMPLATE = 28;
   public static final int SECURE_MESSAGING_TEMPLATE = 29;
   public static final int NON_INTERINDUSTRY_DATA_OBJECT_NESTING_TEMPLATE = 30;
   public static final int DISPLAY_CONTROL = 32;
   public static final int CARDHOLDER_CERTIFICATE = 33;
   public static final int CV_CERTIFICATE = 33;
   public static final int CARDHOLER_REQUIREMENTS_INCLUDED_FEATURES = 34;
   public static final int CARDHOLER_REQUIREMENTS_EXCLUDED_FEATURES = 35;
   public static final int BIOMETRIC_DATA_TEMPLATE = 46;
   public static final int DIGITAL_SIGNATURE_BLOCK = 61;
   public static final int CARDHOLDER_PRIVATE_KEY_TEMPLATE = 72;
   public static final int CARDHOLDER_PUBLIC_KEY_TEMPLATE = 73;
   public static final int CERTIFICATE_HOLDER_AUTHORIZATION_TEMPLATE = 76;
   public static final int CERTIFICATE_CONTENT_TEMPLATE = 78;
   public static final int CERTIFICATE_BODY = 78;
   public static final int BIOMETRIC_INFORMATION_TEMPLATE = 0;
   public static final int BIOMETRIC_INFORMATION_GROUP_TEMPLATE = 1;

   public static int getTag(int var0) {
      return decodeTag(var0);
   }

   public static int getTagNo(int var0) {
      for(int var1 = 24; var1 >= 0; var1 -= 8) {
         if ((255 << var1 & var0) != 0) {
            return ~(255 << var1) & var0;
         }
      }

      return 0;
   }

   public static int encodeTag(ASN1ApplicationSpecific var0) {
      int var1 = 64;
      boolean var2 = var0.isConstructed();
      if (var2) {
         var1 |= 32;
      }

      int var3 = var0.getApplicationTag();
      if (var3 > 31) {
         var1 |= 31;
         var1 <<= 8;
         int var4 = var3 & 127;
         var1 |= var4;

         for(var3 >>= 7; var3 > 0; var3 >>= 7) {
            var1 |= 128;
            var1 <<= 8;
            var4 = var3 & 127;
         }
      } else {
         var1 |= var3;
      }

      return var1;
   }

   public static int decodeTag(int var0) {
      int var1 = 0;
      boolean var2 = false;

      for(int var3 = 24; var3 >= 0; var3 -= 8) {
         int var4 = var0 >> var3 & 255;
         if (var4 != 0) {
            if (var2) {
               var1 <<= 7;
               var1 |= var4 & 127;
            } else {
               if ((var4 & 31) != 31) {
                  return var4 & 31;
               }

               var2 = true;
            }
         }
      }

      return var1;
   }
}
