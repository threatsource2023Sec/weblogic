package com.octetstring.vde.acl;

import com.octetstring.ldapv3.Filter;
import com.octetstring.nls.Messages;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.DNUtility;
import com.octetstring.vde.util.DirectoryException;
import com.octetstring.vde.util.InvalidDNException;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ParseFilter;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;

public class ACL {
   private boolean scopeSubtree = false;
   private boolean grant = false;
   private Vector permission = null;
   private Vector attr = null;
   private String authnLevel = null;
   private boolean authzDN = true;
   private byte subjectType = 0;
   private DirectoryString subject = null;
   private Filter targetFilter = null;
   public static final byte SUBJECT_INVALID = 0;
   public static final byte SUBJECT_PUBLIC = 2;
   public static final byte SUBJECT_SUBTREE = 4;
   public static final byte SUBJECT_ROLE = 8;
   public static final byte SUBJECT_GROUP = 16;
   public static final byte SUBJECT_THIS = 32;
   public static final byte SUBJECT_AUTHZID = 64;
   public static final byte SUBJECT_IPADDRESS = 96;

   public ACL() {
   }

   public ACL(String strACL) {
      StringTokenizer aclTok = new StringTokenizer(strACL, "#");
      String mytoken = aclTok.nextToken();
      if (mytoken.equalsIgnoreCase("subtree")) {
         this.setScopeSubtree(true);
         mytoken = null;
      } else if (mytoken.equalsIgnoreCase("entry")) {
         this.setScopeSubtree(false);
         mytoken = null;
      }

      if (mytoken == null) {
         mytoken = aclTok.nextToken();
      }

      StringTokenizer rightsTok = new StringTokenizer(mytoken, ":");
      if (rightsTok.nextToken().equalsIgnoreCase("grant")) {
         this.setGrant(true);
      } else {
         this.setGrant(false);
      }

      Vector attrVec;
      StringTokenizer attrTok;
      if (rightsTok.hasMoreTokens()) {
         attrVec = new Vector();
         attrTok = new StringTokenizer(rightsTok.nextToken(), ",");

         while(attrTok.hasMoreTokens()) {
            attrVec.addElement(new Character(attrTok.nextToken().charAt(0)));
         }

         this.setPermission(attrVec);
      }

      attrVec = new Vector();
      attrTok = new StringTokenizer(aclTok.nextToken(), ",");

      while(attrTok.hasMoreTokens()) {
         attrVec.addElement(new DirectoryString(attrTok.nextToken()));
      }

      this.setAttr(attrVec);
      StringTokenizer subjectTok = new StringTokenizer(aclTok.nextToken(), ":");
      String authn = subjectTok.nextToken();
      String subType;
      if (authn.equalsIgnoreCase("authnLevel")) {
         subType = subjectTok.nextToken();
         if (subType.equalsIgnoreCase("sasl")) {
            subType = subType.concat(":" + subjectTok.nextToken());
         }

         this.setAuthnLevel(subType);
      }

      subType = null;
      if (this.getAuthnLevel() == null) {
         subType = authn;
      } else {
         subType = subjectTok.nextToken();
      }

      if (subType.startsWith("authz")) {
         this.setSubjectType((byte)64);
         StringTokenizer subTypeTok = new StringTokenizer(subType, "-");
         subTypeTok.nextToken();
         if (subTypeTok.nextToken().equalsIgnoreCase("dn")) {
            this.setAuthzDN(true);
         } else {
            this.setAuthzDN(false);
         }

         try {
            this.setSubject(new DirectoryString(subjectTok.nextToken()));
         } catch (InvalidDNException var14) {
            Logger.getInstance().log(0, this, Messages.getString("Invalid_DN_Specified_in_ACL_15"));
         }
      } else if (subType.equalsIgnoreCase("public")) {
         this.setSubjectType((byte)2);
      } else if (subType.equalsIgnoreCase("this")) {
         this.setSubjectType((byte)32);
      } else {
         if (subType.equalsIgnoreCase("role")) {
            this.setSubjectType((byte)8);
         } else if (subType.equalsIgnoreCase("group")) {
            this.setSubjectType((byte)16);
         } else if (subType.equalsIgnoreCase("subtree")) {
            this.setSubjectType((byte)4);
         } else if (subType.equalsIgnoreCase("ipAddress")) {
            this.setSubjectType((byte)96);
         }

         try {
            this.setSubject(new DirectoryString(subjectTok.nextToken()));
         } catch (InvalidDNException var13) {
            Logger.getInstance().log(0, this, Messages.getString("Invalid_DN_Specified_in_ACL_22"));
         }
      }

      if (aclTok.hasMoreTokens()) {
         String tf = aclTok.nextToken();

         try {
            this.setTargetFilter(ParseFilter.parse(tf));
         } catch (DirectoryException var12) {
         }
      }

   }

   public Filter getTargetFilter() {
      return this.targetFilter;
   }

   public void setTargetFilter(Filter targetFilter) {
      this.targetFilter = targetFilter;
   }

   public Vector getAttr() {
      return this.attr;
   }

   public void setAttr(Vector newAttr) {
      this.attr = newAttr;
   }

   public String getAuthnLevel() {
      return this.authnLevel;
   }

   public void setAuthnLevel(String newAuthnLevel) {
      this.authnLevel = newAuthnLevel;
   }

   public boolean isAuthzDN() {
      return this.authzDN;
   }

   public void setAuthzDN(boolean newAuthzDN) {
      this.authzDN = newAuthzDN;
   }

   public boolean isGrant() {
      return this.grant;
   }

   public void setGrant(boolean newGrant) {
      this.grant = newGrant;
   }

   public Vector getPermission() {
      return this.permission;
   }

   public void setPermission(Vector newPermission) {
      this.permission = newPermission;
   }

   public boolean isScopeSubtree() {
      return this.scopeSubtree;
   }

   public void setScopeSubtree(boolean newScopeSubtree) {
      this.scopeSubtree = newScopeSubtree;
   }

   public DirectoryString getSubject() {
      return this.subject;
   }

   public void setSubject(DirectoryString newSubject) throws InvalidDNException {
      switch (this.getSubjectType()) {
         case 4:
         case 8:
         case 16:
            this.subject = DNUtility.getInstance().normalize(newSubject);
            break;
         case 64:
            if (this.isAuthzDN()) {
               this.subject = DNUtility.getInstance().normalize(newSubject);
            } else {
               this.subject = newSubject;
            }
            break;
         default:
            this.subject = newSubject;
      }

   }

   public byte getSubjectType() {
      return this.subjectType;
   }

   public void setSubjectType(byte newSubjectType) {
      this.subjectType = newSubjectType;
   }

   public String toString() {
      StringBuffer aclString = new StringBuffer();
      if (this.isGrant()) {
         aclString.append("grant:");
      } else {
         aclString.append("deny:");
      }

      Enumeration attrEnum;
      if (this.getPermission() != null) {
         attrEnum = this.getPermission().elements();

         while(attrEnum.hasMoreElements()) {
            Character permByte = (Character)attrEnum.nextElement();
            aclString.append(permByte);
            if (attrEnum.hasMoreElements()) {
               aclString.append(",");
            }
         }
      }

      aclString.append("#");
      if (this.getAttr() != null) {
         attrEnum = this.getAttr().elements();

         while(attrEnum.hasMoreElements()) {
            DirectoryString oneAttr = (DirectoryString)attrEnum.nextElement();
            aclString.append(oneAttr);
            if (attrEnum.hasMoreElements()) {
               aclString.append(",");
            }
         }
      }

      aclString.append("#");
      if (this.getAuthnLevel() != null) {
         aclString.append("authnLevel:");
         aclString.append(this.getAuthnLevel()).append(":");
      }

      switch (this.getSubjectType()) {
         case 2:
            aclString.append("public:");
            break;
         case 4:
            aclString.append("subtree:").append(this.getSubject());
            break;
         case 8:
            aclString.append("role:").append(this.getSubject());
            break;
         case 16:
            aclString.append("group:").append(this.getSubject());
            break;
         case 32:
            aclString.append("this:");
            break;
         case 64:
            aclString.append("authzID-");
            if (this.isAuthzDN()) {
               aclString.append("dn:");
            } else {
               aclString.append("u:");
            }

            aclString.append(this.getSubject());
            break;
         case 96:
            aclString.append("ipAddress:").append(this.getSubject());
      }

      return aclString.toString();
   }
}
