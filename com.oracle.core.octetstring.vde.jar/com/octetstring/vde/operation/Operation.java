package com.octetstring.vde.operation;

import com.octetstring.ldapv3.LDAPMessage;
import com.octetstring.vde.util.DirectoryException;

public interface Operation {
   LDAPMessage getResponse();

   void perform() throws DirectoryException;
}
