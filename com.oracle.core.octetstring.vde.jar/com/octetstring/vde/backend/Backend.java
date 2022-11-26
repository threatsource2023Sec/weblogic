package com.octetstring.vde.backend;

import com.asn1c.core.Int8;
import com.octetstring.ldapv3.Filter;
import com.octetstring.vde.Entry;
import com.octetstring.vde.EntrySet;
import com.octetstring.vde.syntax.BinarySyntax;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.DirectoryException;
import java.util.Vector;

public interface Backend {
   Int8 add(DirectoryString var1, Entry var2);

   boolean bind(DirectoryString var1, BinarySyntax var2);

   Int8 delete(DirectoryString var1, DirectoryString var2);

   boolean doBind();

   EntrySet get(DirectoryString var1, DirectoryString var2, int var3, Filter var4, boolean var5, Vector var6) throws DirectoryException;

   Entry getByDN(DirectoryString var1, DirectoryString var2) throws DirectoryException;

   Entry getByID(Integer var1);

   void modify(DirectoryString var1, DirectoryString var2, Vector var3) throws DirectoryException;

   Int8 rename(DirectoryString var1, DirectoryString var2, DirectoryString var3, DirectoryString var4, boolean var5);
}
