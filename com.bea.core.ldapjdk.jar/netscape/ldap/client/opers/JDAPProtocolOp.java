package netscape.ldap.client.opers;

import netscape.ldap.ber.stream.BERElement;

public interface JDAPProtocolOp {
   int BIND_REQUEST = 0;
   int BIND_RESPONSE = 1;
   int UNBIND_REQUEST = 2;
   int SEARCH_REQUEST = 3;
   int SEARCH_RESPONSE = 4;
   int SEARCH_RESULT = 5;
   int MODIFY_REQUEST = 6;
   int MODIFY_RESPONSE = 7;
   int ADD_REQUEST = 8;
   int ADD_RESPONSE = 9;
   int DEL_REQUEST = 10;
   int DEL_RESPONSE = 11;
   int MODIFY_RDN_REQUEST = 12;
   int MODIFY_RDN_RESPONSE = 13;
   int COMPARE_REQUEST = 14;
   int COMPARE_RESPONSE = 15;
   int ABANDON_REQUEST = 16;
   int SEARCH_RESULT_REFERENCE = 19;
   int EXTENDED_REQUEST = 23;
   int EXTENDED_RESPONSE = 24;

   int getType();

   BERElement getBERElement();

   String toString();
}
