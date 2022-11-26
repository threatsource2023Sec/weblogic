package com.octetstring.vde.operation;

import com.asn1c.core.Int8;

public class LDAPResult {
   public static final Int8 SUCCESS = new Int8((byte)0);
   public static final Int8 OPERATIONS_ERROR = new Int8((byte)1);
   public static final Int8 PROTOCOL_ERROR = new Int8((byte)2);
   public static final Int8 TIME_LIMIT_EXCEEDED = new Int8((byte)3);
   public static final Int8 SIZE_LIMIT_EXCEEDED = new Int8((byte)4);
   public static final Int8 COMPARE_FALSE = new Int8((byte)5);
   public static final Int8 COMPARE_TRUE = new Int8((byte)6);
   public static final Int8 AUTH_METHOD_NOT_SUPPORTED = new Int8((byte)7);
   public static final Int8 STRONG_AUTH_REQUIRED = new Int8((byte)8);
   public static final Int8 REFERRAL = new Int8((byte)10);
   public static final Int8 ADMIN_LIMIT_EXCEEDED = new Int8((byte)11);
   public static final Int8 UNAVAILABLE_CRITICAL_EXTENSION = new Int8((byte)12);
   public static final Int8 CONFIDENTIALITY_REQUIRED = new Int8((byte)13);
   public static final Int8 SASL_BIND_IN_PROGRESS = new Int8((byte)14);
   public static final Int8 NO_SUCH_ATTRIBUTE = new Int8((byte)16);
   public static final Int8 UNDEFINED_ATTRIBUTE_TYPE = new Int8((byte)17);
   public static final Int8 INAPPROPRIATE_MATCHING = new Int8((byte)18);
   public static final Int8 CONSTRAINT_VIOLATION = new Int8((byte)19);
   public static final Int8 ATTRIBUTE_OR_VALUE_EXISTS = new Int8((byte)20);
   public static final Int8 INVALID_ATTRIBUTE_SYNTAX = new Int8((byte)21);
   public static final Int8 NO_SUCH_OBJECT = new Int8((byte)32);
   public static final Int8 ALIAS_PROBLEM = new Int8((byte)33);
   public static final Int8 INVALID_DN_SYNTAX = new Int8((byte)34);
   public static final Int8 ALIAS_DEREFERENCING_PROBLEM = new Int8((byte)36);
   public static final Int8 INAPPROPRIATE_AUTHENTICATION = new Int8((byte)48);
   public static final Int8 INVALID_CREDENTIALS = new Int8((byte)49);
   public static final Int8 INSUFFICIENT_ACCESS_RIGHTS = new Int8((byte)50);
   public static final Int8 BUSY = new Int8((byte)51);
   public static final Int8 UNAVAILABLE = new Int8((byte)52);
   public static final Int8 UNWILLING_TO_PERFORM = new Int8((byte)53);
   public static final Int8 LOOP_DETECT = new Int8((byte)54);
   public static final Int8 NAMING_VIOLATION = new Int8((byte)64);
   public static final Int8 OBJECT_CLASS_VIOLATION = new Int8((byte)65);
   public static final Int8 NOT_ALLOWED_ON_NON_LEAF = new Int8((byte)66);
   public static final Int8 NOT_ALLOWED_ON_RDN = new Int8((byte)67);
   public static final Int8 ENTRY_ALREADY_EXISTS = new Int8((byte)68);
   public static final Int8 OBJECT_CLASS_MODS_PROHIBITED = new Int8((byte)69);
   public static final Int8 AFFECTS_MULTIPLE_DSAS = new Int8((byte)71);
   public static final Int8 OTHER = new Int8((byte)80);
}
