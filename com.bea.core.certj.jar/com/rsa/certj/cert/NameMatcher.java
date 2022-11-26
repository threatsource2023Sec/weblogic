package com.rsa.certj.cert;

import com.rsa.certj.cert.extensions.BuiltInDomainDefinedAttributes;
import com.rsa.certj.cert.extensions.BuiltInStandardAttributes;
import com.rsa.certj.cert.extensions.EDIPartyName;
import com.rsa.certj.cert.extensions.ExtendedNetworkAddress;
import com.rsa.certj.cert.extensions.ExtensionAttribute;
import com.rsa.certj.cert.extensions.ExtensionAttributes;
import com.rsa.certj.cert.extensions.GeneralName;
import com.rsa.certj.cert.extensions.ORAddress;
import com.rsa.certj.cert.extensions.ORName;
import com.rsa.certj.cert.extensions.OtherName;
import com.rsa.certj.cert.extensions.PDSParameter;
import com.rsa.certj.cert.extensions.PersonalName;
import com.rsa.certj.cert.extensions.UnformattedPostalAddress;
import java.util.ArrayList;
import java.util.Arrays;

/** @deprecated */
public final class NameMatcher {
   /** @deprecated */
   public static final int CMP_DIFFERENT = 0;
   /** @deprecated */
   public static final int CMP_SAME = 1;
   /** @deprecated */
   public static final int CMP_SMALLER = 2;
   /** @deprecated */
   public static final int CMP_LARGER = 3;

   private NameMatcher() {
   }

   /** @deprecated */
   public static boolean isNameIncluded(String var0, String var1) {
      return var1.toLowerCase().endsWith(var0.toLowerCase());
   }

   /** @deprecated */
   public static boolean matchDefinedAttributeLists(BuiltInDomainDefinedAttributes var0, BuiltInDomainDefinedAttributes var1) {
      if (var0 != null && var1 != null) {
         if (var0.getAttributeCount() > var1.getAttributeCount()) {
            return false;
         } else {
            try {
               for(int var2 = 0; var2 < var0.getAttributeCount(); ++var2) {
                  if (!Arrays.equals(var0.getAttribute(var2), var1.getAttribute(var2))) {
                     return false;
                  }
               }

               return true;
            } catch (NameException var3) {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static boolean matchDirectoryNames(X500Name var0, X500Name var1) {
      if (var0 != null && var1 != null) {
         int var2 = var0.getRDNCount();
         int var3 = var1.getRDNCount();
         if (var2 < var3) {
            return false;
         } else {
            try {
               for(int var4 = 0; var4 < var3; ++var4) {
                  if (!matchNameAttributeSets(var0.getRDN(var4), var1.getRDN(var4))) {
                     return false;
                  }
               }

               return true;
            } catch (NameException var5) {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static boolean matchDnsNames(String var0, String var1) {
      if (var0 != null && var1 != null) {
         if (isNameIncluded(var1, var0)) {
            if (var0.length() == var1.length()) {
               return true;
            }

            if (var0.charAt(var0.length() - 1 - var1.length()) == '.') {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static boolean matchEdiPartyNames(EDIPartyName var0, EDIPartyName var1) {
      return var0 != null && var1 != null && var0.equals(var1);
   }

   /** @deprecated */
   public static boolean matchExtensionAttributeLists(ExtensionAttributes var0, ExtensionAttributes var1) {
      if (var0 != null && var1 != null) {
         int var2 = var0.getAttributeCount();
         int var3 = var1.getAttributeCount();
         if (var2 == 0) {
            return true;
         } else if (var2 > var3) {
            return false;
         } else {
            try {
               for(int var5 = 0; var5 < var2; ++var5) {
                  boolean var4 = false;

                  for(int var6 = 0; var6 < var3; ++var6) {
                     if (matchExtensionAttributes(var0.getAttribute(var5), var1.getAttribute(var6))) {
                        var4 = true;
                        break;
                     }
                  }

                  if (!var4) {
                     return false;
                  }
               }

               return true;
            } catch (NameException var7) {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static boolean matchExtensionAttributes(ExtensionAttribute var0, ExtensionAttribute var1) {
      if (var0 != null && var1 != null) {
         int var2 = var0.getAttributeType();
         int var3 = var1.getAttributeType();
         if (var2 != var3) {
            return false;
         } else {
            switch (var2) {
               case 1:
               case 2:
               case 3:
               case 7:
                  String var4 = (String)var0.getAttribute();
                  String var5 = (String)var1.getAttribute();
                  if (var4 == null) {
                     return var5 == null;
                  }

                  return var4.equals(var5);
               case 4:
                  return matchPersonalNames((PersonalName)var0.getAttribute(), (PersonalName)var1.getAttribute());
               case 5:
                  return matchOrgUnitNameLists((String[])((String[])var0.getAttribute()), (String[])((String[])var1.getAttribute()));
               case 6:
                  return matchDefinedAttributeLists((BuiltInDomainDefinedAttributes)var0.getAttribute(), (BuiltInDomainDefinedAttributes)var1.getAttribute());
               case 8:
               case 9:
                  return matchOrNames((ORName)var0.getAttribute(), (ORName)var1.getAttribute());
               case 10:
               case 11:
               case 12:
               case 13:
               case 14:
               case 15:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
                  PDSParameter var6 = (PDSParameter)var0.getAttribute();
                  PDSParameter var7 = (PDSParameter)var1.getAttribute();
                  if (var6 == null) {
                     return var7 == null;
                  }

                  return var6.equals(var7);
               case 16:
                  UnformattedPostalAddress var8 = (UnformattedPostalAddress)var0.getAttribute();
                  UnformattedPostalAddress var9 = (UnformattedPostalAddress)var1.getAttribute();
                  if (var8 == null) {
                     return var9 == null;
                  }

                  return var8.equals(var9);
               case 22:
                  ExtendedNetworkAddress var10 = (ExtendedNetworkAddress)var0.getAttribute();
                  ExtendedNetworkAddress var11 = (ExtendedNetworkAddress)var1.getAttribute();
                  if (var10 == null) {
                     return var11 == null;
                  }

                  return var10.equals(var11);
               case 23:
                  return var0.getAttribute().equals(var1.getAttribute());
               default:
                  return false;
            }
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static boolean matchGeneralNames(GeneralName var0, GeneralName var1, int var2) {
      if (var0.getGeneralNameType() == var2 && var1.getGeneralNameType() == var2) {
         switch (var2) {
            case 1:
               return matchOtherNames((OtherName)var0.getGeneralName(), (OtherName)var1.getGeneralName());
            case 2:
               return matchRfc822Names((String)var0.getGeneralName(), (String)var1.getGeneralName());
            case 3:
               return matchDnsNames((String)var0.getGeneralName(), (String)var1.getGeneralName());
            case 4:
               return matchX400Addresses((ORAddress)var0.getGeneralName(), (ORAddress)var1.getGeneralName());
            case 5:
               return matchDirectoryNames((X500Name)var0.getGeneralName(), (X500Name)var1.getGeneralName());
            case 6:
               return matchEdiPartyNames((EDIPartyName)var0.getGeneralName(), (EDIPartyName)var1.getGeneralName());
            case 7:
               return matchResourceLocators((String)var0.getGeneralName(), (String)var1.getGeneralName());
            case 8:
               return matchIpAddresses((byte[])((byte[])var0.getGeneralName()), (byte[])((byte[])var1.getGeneralName()));
            case 9:
               return matchRegisteredIds((String)var0.getGeneralName(), (String)var1.getGeneralName());
            default:
               return false;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static boolean matchIpAddresses(byte[] var0, byte[] var1) {
      if (var0 != null && var1 != null) {
         int var9;
         byte[] var10;
         byte[] var11;
         int var12;
         int var14;
         int var8 = var0.length;
         var9 = var1.length;
         var10 = new byte[16];
         var11 = new byte[16];
         label62:
         switch (var8) {
            case 4:
            case 16:
               var12 = var8;
               var14 = 0;

               while(true) {
                  if (var14 >= var12) {
                     break label62;
                  }

                  var10[var14] = 0;
                  ++var14;
               }
            case 8:
            case 32:
               var12 = var8 / 2;
               var14 = 0;

               while(true) {
                  if (var14 >= var12) {
                     break label62;
                  }

                  var10[var14] = (byte)(~var0[var14 + var12]);
                  ++var14;
               }
            default:
               return false;
         }

         int var13;
         label50:
         switch (var9) {
            case 4:
            case 16:
               var13 = var9;
               var14 = 0;

               while(true) {
                  if (var14 >= var13) {
                     break label50;
                  }

                  var11[var14] = -1;
                  ++var14;
               }
            case 8:
            case 32:
               var13 = var9 / 2;
               System.arraycopy(var1, var13, var11, 0, var13);
               break;
            default:
               return false;
         }

         if (var12 != var13) {
            return false;
         } else {
            for(var14 = 0; var14 < var12; ++var14) {
               if ((var11[var14] & var0[var14]) != var1[var14] || (var11[var14] & (var0[var14] | var10[var14])) != var1[var14]) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static boolean matchNameAttributes(AttributeValueAssertion var0, AttributeValueAssertion var1) {
      if (var0 != null && var1 != null) {
         if (var0.getAttributeType() != var1.getAttributeType()) {
            return false;
         } else {
            String var2;
            String var3;
            try {
               var2 = var0.getStringAttribute();
               var3 = var1.getStringAttribute();
            } catch (NameException var5) {
               return false;
            }

            if (var0.getAttributeType() == 7) {
               return matchRfc822Names(var2, var3);
            } else if (var0.getAttributeType() == 19) {
               return var2.equalsIgnoreCase(var3);
            } else {
               switch (var0.getValueType()) {
                  case 3072:
                  case 5120:
                  case 7168:
                  case 7680:
                     return var2.equals(var3);
                  case 4864:
                     return printableStringsEqual(var2, var3);
                  default:
                     return false;
               }
            }
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static boolean matchNameAttributeSets(RDN var0, RDN var1) {
      if (var0 != null && var1 != null) {
         int var2 = var0.getAttributeCount();
         int var3 = var1.getAttributeCount();
         if (var2 > var3) {
            return false;
         } else {
            ArrayList var4 = new ArrayList();

            try {
               for(int var5 = 0; var5 < var3; ++var5) {
                  AttributeValueAssertion var6 = var1.getAttributeByIndex(var5);

                  for(int var7 = 0; var7 < var2; ++var7) {
                     AttributeValueAssertion var8 = var0.getAttributeByIndex(var7);
                     if (matchNameAttributes(var8, var6)) {
                        var4.add(var6);
                        break;
                     }
                  }
               }
            } catch (NameException var9) {
               return false;
            }

            return var2 == var4.size();
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static boolean matchOrgUnitNameLists(String[] var0, String[] var1) {
      if (var0 != null && var1 != null) {
         if (var0.length > var1.length) {
            return false;
         } else {
            for(int var2 = 0; var2 < var0.length; ++var2) {
               if (!var0[var2].equals(var1[var2])) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static boolean matchOrNames(ORName var0, ORName var1) {
      return var0 != null && var1 != null ? var0.equals(var1) : false;
   }

   /** @deprecated */
   public static boolean matchOtherNames(OtherName var0, OtherName var1) {
      return var0 != null && var1 != null ? var0.equals(var1) : false;
   }

   /** @deprecated */
   public static boolean matchPersonalNames(PersonalName var0, PersonalName var1) {
      return var0 != null && var1 != null ? var0.equals(var1) : false;
   }

   /** @deprecated */
   public static boolean matchRegisteredIds(String var0, String var1) {
      return var0 != null && var1 != null ? var0.equals(var1) : false;
   }

   /** @deprecated */
   public static boolean matchResourceLocators(String var0, String var1) {
      if (var0 != null && var1 != null) {
         String var2 = extractHostName(var0);
         String var3 = extractHostName(var1);
         if (var2 != null && var3 != null) {
            if (isNameIncluded(var3, var2)) {
               if (var2.length() == var3.length()) {
                  return true;
               }

               if (var3.length() > 0 && var3.charAt(0) == '.') {
                  return true;
               }
            }

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static boolean matchRfc822Names(String var0, String var1) {
      if (var0 != null && var1 != null) {
         if (isNameIncluded(var1, var0)) {
            if (var0.length() == var1.length()) {
               return true;
            }

            if (var1.length() > 0 && var1.charAt(0) == '.') {
               return true;
            }

            if (var0.charAt(var0.length() - 1 - var1.length()) == '@') {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static boolean matchStandardAttributeLists(BuiltInStandardAttributes var0, BuiltInStandardAttributes var1) {
      if (var0 != null && var1 != null) {
         if ((var0.getCountryName() != null || var0.getCountryName() != null) && !matchOrNames(var0.getCountryName(), var1.getCountryName())) {
            return false;
         } else if ((var0.getAdminDomainName() != null || var0.getAdminDomainName() != null) && !matchOrNames(var0.getAdminDomainName(), var1.getAdminDomainName())) {
            return false;
         } else if (var0.getNetworkAddress() != null && !var0.getNetworkAddress().equals(var1.getNetworkAddress())) {
            return false;
         } else if (var0.getTerminalIdentifier() != null && !var0.getTerminalIdentifier().equals(var1.getTerminalIdentifier())) {
            return false;
         } else if ((var0.getPrivateDomainName() != null || var0.getPrivateDomainName() != null) && !matchOrNames(var0.getPrivateDomainName(), var1.getPrivateDomainName())) {
            return false;
         } else if (var0.getOrganizationName() != null && !var0.getOrganizationName().equals(var1.getOrganizationName())) {
            return false;
         } else if (var0.getNumericUserIdentifier() != null && !var0.getNumericUserIdentifier().equals(var1.getNumericUserIdentifier())) {
            return false;
         } else if ((var0.getPersonalName() != null || var0.getPersonalName() != null) && !matchPersonalNames(var0.getPersonalName(), var1.getPersonalName())) {
            return false;
         } else {
            return var0.getOrganizationalUnitNames() == null || Arrays.equals(var0.getOrganizationalUnitNames(), var1.getOrganizationalUnitNames());
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static boolean matchUnformattedPostalAddrs(UnformattedPostalAddress var0, UnformattedPostalAddress var1) {
      if (var0 != null && var1 != null) {
         String[] var2 = var0.getPrintableAddress();
         String[] var3 = var1.getPrintableAddress();
         if (var2 == null) {
            if (var3 != null) {
               return false;
            }
         } else {
            if (var2.length > var3.length) {
               return false;
            }

            for(int var4 = 0; var4 < var2.length; ++var4) {
               if (var2[var4] != var3[var4] && (var2[var4] == null || !var2[var4].equals(var3[var4]))) {
                  return false;
               }
            }
         }

         String var6 = var0.getTeletexString();
         String var5 = var1.getTeletexString();
         if (var6 == null) {
            return var5 == null;
         } else {
            return var6.equals(var5);
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static boolean matchX400Addresses(ORAddress var0, ORAddress var1) {
      if (var0 != null && var1 != null) {
         BuiltInDomainDefinedAttributes var2 = var0.getBuiltInDomainDefinedAttributes();
         BuiltInDomainDefinedAttributes var3 = var1.getBuiltInDomainDefinedAttributes();
         BuiltInStandardAttributes var4 = var0.getBuiltInStandardAttributes();
         BuiltInStandardAttributes var5 = var1.getBuiltInStandardAttributes();
         ExtensionAttributes var6 = var0.getExtensionAttributes();
         ExtensionAttributes var7 = var1.getExtensionAttributes();
         if ((var4 != null || var5 != null) && !matchStandardAttributeLists(var4, var5)) {
            return false;
         } else {
            if (var2 != null || var3 != null) {
               if (var2 == null || var3 == null) {
                  return false;
               }

               if (!matchDefinedAttributeLists(var2, var3)) {
                  return false;
               }
            }

            if (var6 != null || var7 != null) {
               if (var6 == null || var7 == null) {
                  return false;
               }

               if (!matchExtensionAttributeLists(var6, var7)) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   public static int compareAltNames(GeneralName var0, GeneralName var1) {
      if (var0 != null && var1 != null) {
         boolean var2;
         boolean var3;
         switch (var0.getGeneralNameType()) {
            case 1:
               OtherName var4 = (OtherName)var0.getGeneralName();
               OtherName var5 = (OtherName)var1.getGeneralName();
               var2 = matchOtherNames(var4, var5);
               var3 = matchOtherNames(var5, var4);
               break;
            case 2:
               String var6 = (String)var0.getGeneralName();
               String var7 = (String)var1.getGeneralName();
               var2 = matchRfc822Names(var6, var7);
               var3 = matchRfc822Names(var7, var6);
               break;
            case 3:
               String var8 = (String)var0.getGeneralName();
               String var9 = (String)var1.getGeneralName();
               var2 = matchDnsNames(var8, var9);
               var3 = matchDnsNames(var9, var8);
               break;
            case 4:
               ORAddress var10 = (ORAddress)var0.getGeneralName();
               ORAddress var11 = (ORAddress)var1.getGeneralName();
               var2 = matchX400Addresses(var10, var11);
               var3 = matchX400Addresses(var11, var10);
               break;
            case 5:
               X500Name var12 = (X500Name)var0.getGeneralName();
               X500Name var13 = (X500Name)var1.getGeneralName();
               var2 = matchDirectoryNames(var12, var13);
               var3 = matchDirectoryNames(var13, var12);
               break;
            case 6:
               EDIPartyName var14 = (EDIPartyName)var0.getGeneralName();
               EDIPartyName var15 = (EDIPartyName)var1.getGeneralName();
               var2 = matchEdiPartyNames(var14, var15);
               var3 = matchEdiPartyNames(var15, var14);
               break;
            case 7:
               String var16 = (String)var0.getGeneralName();
               String var17 = (String)var1.getGeneralName();
               var2 = matchResourceLocators(var16, var17);
               var3 = matchResourceLocators(var17, var16);
               break;
            case 8:
               byte[] var18 = (byte[])((byte[])var0.getGeneralName());
               byte[] var19 = (byte[])((byte[])var1.getGeneralName());
               var2 = matchIpAddresses(var18, var19);
               var3 = matchIpAddresses(var19, var18);
               break;
            case 9:
               byte[] var20 = (byte[])((byte[])var0.getGeneralName());
               byte[] var21 = (byte[])((byte[])var1.getGeneralName());
               var2 = var3 = Arrays.equals(var20, var21);
               break;
            default:
               var2 = var3 = var0.equals(var1);
         }

         if (var2 && var3) {
            return 1;
         } else if (var2) {
            return 2;
         } else {
            return var3 ? 3 : 0;
         }
      } else {
         return 0;
      }
   }

   /** @deprecated */
   public static String compressWhiteSpaces(String var0) {
      StringBuffer var2 = new StringBuffer();
      boolean var3 = false;

      for(int var4 = 0; var4 < var0.length(); ++var4) {
         char var5 = var0.charAt(var4);
         if (" \t\n\f\r".indexOf(var5) >= 0) {
            if (!var3) {
               var2.append(" ");
               var3 = true;
            }
         } else {
            var2.append(var5);
            var3 = false;
         }
      }

      return var2.toString().trim();
   }

   private static boolean printableStringsEqual(String var0, String var1) {
      return convertToCanonicalString(var0).equals(convertToCanonicalString(var1));
   }

   private static String extractHostName(String var0) {
      if (var0 == null) {
         return null;
      } else {
         int var2 = var0.indexOf("://");
         if (var2 < 0) {
            var2 = 0;
         } else {
            var2 += "://".length();
         }

         int var3 = var0.indexOf(58, var2);
         int var4 = var0.indexOf(47, var2);
         int var5;
         if (var3 < 0 && var4 < 0) {
            var5 = var0.length();
         } else if (var3 < 0) {
            var5 = var4;
         } else if (var4 < 0) {
            var5 = var3;
         } else {
            var5 = Math.min(var3, var4);
         }

         return var0.substring(var2, var5);
      }
   }

   private static String convertToCanonicalString(String var0) {
      String var1 = var0;
      if (var0.startsWith("\\ ")) {
         var1 = var0.substring(2);
      }

      if (var1.endsWith("\\ ")) {
         var1 = var1.substring(0, var1.length() - 2);
      }

      return compressWhiteSpaces(var1).toUpperCase();
   }
}
