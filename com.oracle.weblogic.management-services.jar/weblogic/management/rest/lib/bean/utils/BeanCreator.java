package weblogic.management.rest.lib.bean.utils;

import weblogic.management.rest.lib.utils.ConfigurationTransaction;

public interface BeanCreator extends ConfigurationTransaction.TransactionContents {
   Object getNewBean();
}
