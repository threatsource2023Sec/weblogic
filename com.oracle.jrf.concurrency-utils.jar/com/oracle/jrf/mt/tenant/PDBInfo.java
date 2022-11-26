package com.oracle.jrf.mt.tenant;

import java.util.Date;

public interface PDBInfo {
   String getPDBId();

   String getPDBName();

   Date getCreationDate();
}
