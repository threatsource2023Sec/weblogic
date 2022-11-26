package weblogic.security;

public interface WSUserPasswordDigest {
   String getUsername();

   byte[] getDecodedPasswordDigest();

   byte[] getDecodedNonce();

   String getCreatedString();

   long getCreatedTimeInMillis();
}
