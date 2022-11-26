package com.octetstring.vde;

import com.octetstring.vde.syntax.DirectoryString;

public interface EntryChangesListener {
   DirectoryString getECLBase();

   void receiveEntryChanges(EntryChanges var1);
}
