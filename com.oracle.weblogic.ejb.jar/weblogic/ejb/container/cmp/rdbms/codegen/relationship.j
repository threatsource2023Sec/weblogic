@start rule: simpleLoadCheckForField
      if (!(@isLoadedVarForField @perhapsOrTermForIsModified)) {
        @callLoadMethodForField
      }
@end rule: simpleLoadCheckForField



@start rule: cmpGetMethod
  public @fieldClassForCmpField @getMethodNameForField()
  {
    try {
      @simpleLoadCheckForField
      return @fieldNameForField;
    @standardCatch
  }
@end rule: cmpGetMethod


@start rule: cmpGetMethodJavaClassInternal
  @java_class_internal_method_signature
  {
    try {
      @simpleLoadCheckForField
      return super.@getMethodNameForField();
    @standardCatch
  }
@end rule: cmpGetMethodJavaClassInternal



@start rule: cmpSetMethod
  public void @setMethodNameForField(
    @fieldClassForCmpField @fieldNameForField)
  {
    @cmpSetMethodGuard
  }
@end rule: cmpSetMethod


@start rule: cmpSetMethodBody
    @trimStringTypes
    @cmpSetMethodCheck
    this.@fieldNameForField = @fieldNameForField;
    @isModifiedVarForField = true;
    @beanIsModifiedVar = true;
    @perhapsSetTableModifiedVarForCmpField
    @registerModifiedBean
@end rule: cmpSetMethodBody

@start rule: cmpSetMethodBodyJavaClass
    @trimStringTypes
    @cmpSetMethodCheck
    super.@setMethodNameForField(arg0);
    @isModifiedVarForField = true;
    @beanIsModifiedVar = true;
    @perhapsSetTableModifiedVarForCmpField
    @registerModifiedBean
@end rule: cmpSetMethodBodyJavaClass



@start rule: cmpSetMethodBodyForOptimistic
    if(! __WL_beanIsLoaded()) {
	try {
          @callLoadMethodForField
	@standardCatch
    }

    @cmpSetMethodBody
@end rule: cmpSetMethodBodyForOptimistic

@start rule: cmpSetMethodBodyForOptimisticJavaClass
    if(! __WL_beanIsLoaded()) {
	try {
          @callLoadMethodForField
	@standardCatch
    }

    @cmpSetMethodBodyJavaClass
@end rule: cmpSetMethodBodyForOptimisticJavaClass



@start rule: cmpSetMethodJavaClassInternal
  @java_class_internal_method_signature
  {
    @cmpSetMethodGuard
  }
@end rule: cmpSetMethodJavaClassInternal




@start rule: oneToOneGetterBody_fkOwner
    try {
      @loadCheckForCmrField

      if (@fieldVarForField==null) {
        if (!@fkVarForFieldIsNull) {
          @perhapsDeclareFkVar
          @perhapsAllocateFkVar
          @perhapsAssignFkVar
          Transaction orgTx =
            TransactionHelper.getTransactionHelper().getTransaction();
          try {
             @readOnlyOneToOneGetterBody_fkOwner
           }finally {
              @readOnlyResumeTx
           } 
        }
      }


      return @fieldVarForField;
    @standardCatch
   
@end rule: oneToOneGetterBody_fkOwner


@start rule: oneToOneGetterBody
    try {
      if (!@isCmrLoadedVarNameForField) {
         if (@debugEnabled) {
            @debugSay("calling @finderVarForField with value of " +
              @ctxVar.getPrimaryKey());
         }
         Transaction orgTx =
           TransactionHelper.getTransactionHelper().getTransaction();
         try {
           @readOnlyFinderOneToOneGetterBody
          }
         catch (InternalException ie) {
           if (ie.detail!=null && ie.detail instanceof ObjectNotFoundException)
             @assignFieldVarForFieldWithNull
           else
             throw ie;
         }
         finally {
           @readOnlyResumeTx
         } 
         @isCmrLoadedVarNameForField = true;
         if (@debugEnabled) {
           if (@fieldVarForField == null)
             @debugSay("bean not found.");
           else
             @debugSay("bean was found.");
         }
      }
      return @fieldVarForField;
    @standardCatch
          
@end rule: oneToOneGetterBody


@start rule: oneToOneSetNullBody_fkOwner
    try {
      @loadCheckForCmrField

      if (!@fkVarForFieldIsNull) {
        @perhapsDeclareFkVar
        @perhapsAllocateFkVar
        @perhapsAssignFkVar

        @relInterfaceNameForField @beanVar =
           (@relInterfaceNameForField)@bmVarForField.lookup(@fkVarForField);
        int @oldStateVar = @beanVar.__WL_getMethodState();
        try {
          @beanVar.__WL_setMethodState(WLEnterpriseBean.STATE_BUSINESS_METHOD);
          @beanVar.@relatedDoSetForField(null);
        } finally {
          @beanVar.__WL_setMethodState(@oldStateVar);
        }
        if (ejbStore && !@pmVar.getOrderDatabaseOperations()) {
          @beanVarEjbStoreForField
        }
      }
    @standardCatch
@end rule: oneToOneSetNullBody_fkOwner


@start rule: oneToOneSetNullBody
    try {
      @getMethodNameForField();
      if (@fieldVarForField !=null) {
        @relInterfaceNameForField @beanVar =
        (@relInterfaceNameForField)@bmVarForField.lookup(
          @fieldVarGetPrimaryKey);

        int @oldStateVar = @beanVar.__WL_getMethodState();
        try {
          @beanVar.__WL_setMethodState(WLEnterpriseBean.STATE_BUSINESS_METHOD);
          @beanVar.@relatedDoSetForField(null);
        }
        finally {
          @beanVar.__WL_setMethodState(@oldStateVar);
        }
        if (ejbStore && !@pmVar.getOrderDatabaseOperations()) {
          @beanVarEjbStoreForField
        }
      }
    @standardCatch
@end rule: oneToOneSetNullBody


@start rule: oneToManySetBody_local_fkOwner
    try {
      @oneToManySetBody_fkOwner_preSetCheck

      @doSetMethodNameForField(@fieldNameForField);

      @perhapsCallPostSetMethodForField

      if (ejbStore && !@pmVar.getOrderDatabaseOperations()) {
        ejbStore();    
      }
    @standardCatch
@end rule: oneToManySetBody_local_fkOwner


@start rule: oneToManySetBody_remote_fkOwner
    if (__WL_method_state==STATE_EJB_CREATE) {
      Loggable l = EJBLogger.logsetCheckForCmrFieldDuringEjbCreateLoggable();
      throw new IllegalStateException(l.getMessage());
    }

    try {
      @doSetMethodNameForField(@fieldNameForField);
    @standardCatch
@end rule: oneToManySetBody_remote_fkOwner


@start rule: oneToManySetBody_fkOwner_preSetCheck
    @loadCheckForCmrField

    if (!@fkVarForFieldIsNull) {
      @perhapsDeclareFkVar
      @perhapsAllocateFkVar
      @perhapsAssignFkVar

      @relInterfaceNameForField @beanVar =
        (@relInterfaceNameForField)@bmVarForField.lookup(@fkVarForField);
      int @oldStateVar = @beanVar.__WL_getMethodState();
      try {
        @beanVar.__WL_setMethodState(WLEnterpriseBean.STATE_BUSINESS_METHOD);
        RDBMSSet @colVar = (RDBMSSet)
          @beanVar.@getRelatedMethodNameForField();
        @colVar.doRemove(@ctxVar.@getEJBObject(), remove);

        // register relation change on relation's non-FK holder
        ((CMPBean)@beanVar).__WL_setNonFKHolderRelationChange(true);

      } finally {
        @beanVar.__WL_setMethodState(@oldStateVar);
      }
    }
@end rule: oneToManySetBody_fkOwner_preSetCheck


@start rule: oneToOnePostSetBody_fkOwner
    if (!@fkVarForFieldIsNull) {
      @perhapsDeclareFkVar
      @perhapsAllocateFkVar
      @perhapsAssignFkVar
      @relInterfaceNameForField @beanVar = (@relInterfaceNameForField)
        @bmVarForField.lookup(@fkVarForField);

      int @oldStateVar = @beanVar.__WL_getMethodState();
      if (@oldStateVar == WLEnterpriseBean.STATE_EJB_POSTCREATE) {
        @beanVar.@relatedIsCmrLoadedVarNameForField(true);
      }
      try {
        @beanVar.__WL_setMethodState(WLEnterpriseBean.STATE_BUSINESS_METHOD);

        @beanVar.@relatedSetRestForField((@componentInterfaceForBean)
          @ctxVar.@getEJBObject(), this.__WL_getMethodState());
      } finally {
        @beanVar.__WL_setMethodState(@oldStateVar);
      }
    }
@end rule: oneToOnePostSetBody_fkOwner


@start rule: oneToOnePostSetBody
    if (@fieldVarForField !=null) {
      @relInterfaceNameForField @beanVar = (@relInterfaceNameForField)
      @bmVarForField.lookup(@fieldVarGetPrimaryKey);

      int @oldStateVar = @beanVar.__WL_getMethodState();
      try {
        @beanVar.__WL_setMethodState(WLEnterpriseBean.STATE_BUSINESS_METHOD);

        @beanVar.@relatedSetRestForField((@componentInterfaceForBean)
          @ctxVar.@getEJBObject(), this.__WL_getMethodState());
      } finally {
        @beanVar.__WL_setMethodState(@oldStateVar);
      }
    }
@end rule: oneToOnePostSetBody


@start rule: checkIsRemovedBody
    if ((@fieldNameForField != null) && (@fieldNameGetPrimaryKey != null)) {
      @relInterfaceNameForField @beanVar = (@relInterfaceNameForField)
      @bmVarForField.lookup(@fieldNameGetPrimaryKey);
      return @beanVar.__WL_getIsRemoved();
    }
    return false;
@end rule: checkIsRemovedBody


@start rule: checkRelatedExistsOneMany
    if (!@beanVar.__WL_beanIsLoaded() && !@beanVar.__WL_exists(@fkVarForField)) {
      Loggable l = EJBLogger.logillegalAttemptToAssignRemovedBeanToCMRFieldLoggable(
        @fkVarForField.toString());
      throw new IllegalArgumentException(l.getMessage());
    }
@end rule: checkRelatedExistsOneMany


@start rule: oneToManyPostSetBody
    if (!@fkVarForFieldIsNull) {
      @perhapsDeclareFkVar
      @perhapsAllocateFkVar
      @perhapsAssignFkVar
      @relInterfaceNameForField @beanVar = (@relInterfaceNameForField)
        @bmVarForField.lookup(@fkVarForField);

      int @oldStateVar = @beanVar.__WL_getMethodState();
      try {
        @beanVar.__WL_setMethodState(WLEnterpriseBean.STATE_BUSINESS_METHOD);
	      
        @perhapsCheckRelatedExistsOneMany

        RDBMSSet @colVar = (RDBMSSet)
          @beanVar.@getRelatedMethodNameForField();
        @colVar.doAdd(@ctxVar.@getEJBObject());

        // register relation change on relation's non-FK holder
        ((CMPBean)@beanVar).__WL_setNonFKHolderRelationChange(true);

      } finally {
        @beanVar.__WL_setMethodState(@oldStateVar);
      }
    }
@end rule: oneToManyPostSetBody


@start rule: oneToManySetBody
    if (@fieldNameForField==null) {
      Loggable l = EJBLogger.lognullAssignedToCmrFieldLoggable();
      throw new IllegalArgumentException(l.getMessage());
   
    }

    try {
      @getMethodNameForField();

      if (@fieldNameForField==@fieldVarForField)
        return;

      @fieldVarForField.clear();
      @fieldVarForField.addAll(@fieldNameForField);
    @standardCatch
@end rule: oneToManySetBody


@start rule: oneToOneDoSetBody_fkOwner
    try {
      @loadCheckForCmrField
      if (@debugEnabled) {
        @debugSay("[" + @ctxVar.getPrimaryKey() +
                  "]called @doSetMethodNameForField...");
      }

      @assignFieldVarForFieldWithFieldNameForField

      if (@fieldVarForField == null) {
        if (@debugEnabled) {
          @debugSay("setting field variable to null.");
        }
        @assignFkVarsNull_forField
      }
      else {
        if (@debugEnabled) {
          @debugSay("setting field variable to new value.");
        }
        @assignFkVarsFkField_forField
      }
      @registerModifiedBean
    @standardCatch
@end rule: oneToOneDoSetBody_fkOwner


@start rule: oneToManyGetterBody_fkOwner
    try {
      @loadCheckForCmrField

      if (@fieldVarForField==null) {
        @perhapsDeclareFkVar
        @perhapsAllocateFkVar
        @perhapsAssignFkVar
        if (!@fkVarForFieldIsNull) {
          Transaction orgTx = 
            TransactionHelper.getTransactionHelper().getTransaction();
          try {
            @readOnlyOneToManyGetterBody_fkOwner
          } finally {
            @readOnlyResumeTx
          }       
        }
      }

      return @fieldVarForField;
    @standardCatch
@end rule: oneToManyGetterBody_fkOwner


@start rule: oneToManyGetterBody
    try {
      if (@fieldVarForField==null) {
         if (@debugEnabled) {
            @debugSay("\t@fieldVarForField was null.");
         }
         @assignFieldVarForFieldWithAllocatedOneToManySet
         return @fieldVarForField;
      }
      Transaction currentTx =
        TransactionHelper.getTransactionHelper().getTransaction();
      if(currentTx != null && ((RDBMSSet)@fieldVarForField)
                         .checkIfCurrentTxEqualsCreateTx(currentTx)) {
        return @fieldVarForField;
      } else {
        @assignFieldVarForFieldWithOneToManySetClone

        ((RDBMSSet)@fieldVarForField).setTransaction(currentTx);
	((RDBMSSet)@fieldVarForField).setIsCreatorBeanInvalidated(false);
        return @fieldVarForField;
      }
       
    @standardCatch
@end rule: oneToManyGetterBody

@start rule: ManyToManyGetterBody
    try {
      if (@fieldVarForField==null) {
        if (@debugEnabled) {
          @debugSay("\t@fieldVarForField was null.");
        }

        @assignFieldVarForFieldWithAllocatedManyToManySet
        return @fieldVarForField;
      }
      Transaction currentTx = 
        TransactionHelper.getTransactionHelper().getTransaction();
      if(currentTx != null && ((RDBMSSet)@fieldVarForField)
                         .checkIfCurrentTxEqualsCreateTx(currentTx)) {
        return @fieldVarForField;
      } else {
        @assignFieldVarForFieldWithOneToManySetClone

        ((RDBMSSet)@fieldVarForField).setTransaction(currentTx);
        return @fieldVarForField;
      }
      
    @standardCatch
@end rule: ManyToManyGetterBody


@start rule: manyToManyGetSQL
  if (cmrf.equals("@fieldNameForField")) {
    @getMethodNameForField();
    if (operation == DDConstants.INSERT) {
      String @queryVar = ((RDBMSM2NSet) @fieldVarForField).getAddJoinTableSQL();
      if (@debugEnabled) {
        @debugSay("__WL_getM2NSQL() produced sqlString: " + @queryVar);
      }
      return @queryVar;
    }
    else {
      throw new AssertionError("Unknown request for Many To Many Get SQL: "+
	operation);
    }
  }
@end rule: manyToManyGetSQL


@start rule: getCmrBeansForCmrField
  if (cmrf.equals("@fieldNameForField")) {
    return @getMethodNameForField();
  }
@end rule: getCmrBeansForCmrField


@start rule: oneToOneSetRestBody_fkOwner
    try {
      @loadCheckForCmrField

      if (@debugEnabled) {
        @debugSay("[" + @ctxVar.getPrimaryKey() +
                  "]called @setRestMethodNameForField...");
      }
      if (!(@fkVarForFieldIsNull)) {
        if (@debugEnabled) { @debugSay("\tvariable for field is not null."); }
        @perhapsDeclareFkVar
        @perhapsAllocateFkVar
        @perhapsAssignFkVar

        @relInterfaceNameForField @beanVar =
         (@relInterfaceNameForField) @bmVarForField.lookup(@fkVarForField);
        int @oldStateVar = @beanVar.__WL_getMethodState();
        try {
          @beanVar.__WL_setMethodState(WLEnterpriseBean.STATE_BUSINESS_METHOD);
          @beanVar.@relatedDoSetForField(null);
        } finally {
          @beanVar.__WL_setMethodState(@oldStateVar);
        }
      }
      @doSetMethodNameForField(@fieldNameForField);
    @standardCatch
@end rule: oneToOneSetRestBody_fkOwner


@start rule: checkExistsOneOne
    if (!@beanIsLoadedVar && !__WL_exists(@ctxVar.getPrimaryKey())) {
      Loggable l = EJBLogger.logillegalAttemptToAssignRemovedBeanToCMRFieldLoggable(
        @ctxVar.getPrimaryKey().toString());
      throw new IllegalArgumentException(l.getMessage());
    }
@end rule: checkExistsOneOne


@start rule: oneToOneSetRestBody
    try {
      @perhapsCheckExistsOneOne

      @perhapsOptimizeOneToOne

      if (@fieldVarForField !=null) {
        @relInterfaceNameForField @beanVar = (@relInterfaceNameForField)
          @bmVarForField.lookup(@fieldVarGetPrimaryKey);

        int @oldStateVar = @beanVar.__WL_getMethodState();
        try {
          @beanVar.__WL_setMethodState(WLEnterpriseBean.STATE_BUSINESS_METHOD);
          @beanVar.@relatedDoSetForField(null);
        } finally {
          @beanVar.__WL_setMethodState(@oldStateVar);
        }
      }

      @doSetMethodNameForField(@fieldNameForField);
    @standardCatch
@end rule: oneToOneSetRestBody
