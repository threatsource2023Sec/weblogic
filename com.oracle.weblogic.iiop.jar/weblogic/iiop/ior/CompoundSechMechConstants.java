package weblogic.iiop.ior;

public interface CompoundSechMechConstants {
   short IOPSEC_NOPROTECTION = 1;
   short IOPSEC_INTEGRITY = 2;
   short IOPSEC_CONFIDENTIALITY = 4;
   short IOPSEC_DETECTREPLAY = 8;
   short IOPSEC_DETECTMISORDERING = 16;
   short IOPSEC_ESTABLISHTRUSTINTARGET = 32;
   short IOPSEC_ESTABLISHTRUSTINCLIENT = 64;
   short IOPSEC_NODELEGATION = 128;
   short IOPSEC_SIMPLEDELEGATION = 256;
   short IOPSEC_COMPOSITEDELEGATION = 512;
   short IOPSEC_IDENTITYASSERTION = 1024;
   short IOPSEC_DELEGATIONBYCLIENT = 2048;
}
