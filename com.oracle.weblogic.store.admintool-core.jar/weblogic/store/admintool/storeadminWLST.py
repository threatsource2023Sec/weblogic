# Caution: This file is part of the command scripting implementation. 
# Do not edit or move this file because this may cause commands and scripts to fail. 
# Do not try to reuse the logic in this file or keep copies of this file because this 
# could cause your scripts to fail when you upgrade to a different version.
# Copyright (c) 2011 by Oracle and/or its affiliates.  All rights reserved.


import sys
import types
import java 

from weblogic.store.admintool import StoreAdminWLSTPlugin as plugin

from weblogic.store.admintool import CommandDefs
from weblogic.store.admintool.CommandDefs import CommandType
from weblogic.store.admintool.CommandDefs.CommandType import *
from weblogic.store.admintool.CommandDefs import CommandParam
from weblogic.store.admintool.CommandDefs.CommandParam import *


def defaultHelp() :
    """
    The storeadminWLST module contains the following WebLogic
    PersistentStore-related offline administrative commands:

    openfilestore(store, dir='.', create='false') 
    openjdbcstore(store,driver='null',url='null',propfile='null',
          user='null',password='null',ddl='null',tableNamePrefix='null') 
    closestore(store) 
    liststore(store='null',dir='null') 
    dumpstore(store, outfile, conn='null', deep='false') 
    compactstore(dir,tempdir='null')
    getopenstores() 
    getstoreconns(store) 
    helpstore(command='defaultHelp')

    Use helpstore(command) or helpstore('command') to get 
    command-specific help. 

    Note: Command parameters shown above with "=" are optional.
       For example, 'compactstore' command can be invoked as either 
         compactstore(store='storename', tempdir='/tmp') 
       or
         compactstore(store='storename') #tempdir takes default value
       Default values for optional parameters are listed in the
       command-specific help.

    The open* methods must be used to open/create a store first before
    most of the other storeadmin functions can be called on them.
    Operations on stores can be in terms of the store itself or in
    terms of store connections. Store connections are logical groups
    of records in a PersistentStore (e.g.: JMS and JTA subsytems
    could use different connections in the same PersistentStore).
    
    """

def openfilestore(store, dir='.', create='false') :
    """
    opens a FileStore for further offline storeadmin operations
    
    Usage: openfilestore(store, dir='.', create='false') 

    @param store [mandatory] name of store to be opened
    @param dir [optional (default .)] name of file system directory 
               containing store files
    @param create [optional (default false)] will create non-existing 
                  filestore if set to true

    @return 1 on success, 0 on failure

    """
    args=plugin.getCommand(OPENFILE)
    args+= plugin.getParam(STORE,store)
    args+= plugin.getParam(DIR,dir)
    if (create=='true') :
       args+= plugin.getParam(CREATE,create)
    return plugin.runCommand(args)
    
def openjdbcstore(store,driver='null',url='null',propfile='null',user='null',password='null',ddl='null',tableNamePrefix='null') :
    """
    opens a JDBCStore for further offline storeadmin operations

    Usage: openjdbcstore(store,driver='null',url='null',propfile='null',
             user='null',password='null',ddl='null',tableNamePrefix='null') 

    @param store [mandatory] name of store to be opened
    @param driver [optional] name of JDBC driver class
    @param url [optional] URL to connect to the database
    @param propfile [optional] JDBC properties file
    @param user [optional] userid for accessing database
    @param password [optional] password for accessing database
    @param ddl [optional] name of DDL file defining database table format
    @param tableNamePrefix [optional] prefix for naming database table

    Note: driver and url must be specified either while invoking this function
          or in the propfile. If specified in both, the invocation
          values will over-ride the propfile values.
 
          All the other optional attributes can each be specified on
          invocation, in the propfile, in neither, or in both. If
          specified in both, the invocation values take precedence.

    @return 1 on success, 0 on failure

    """
    args=plugin.getCommand(OPENJDBC)
    args+= plugin.getParam(STORE,store)
    if (driver != 'null') :
        args+=plugin.getParam(DRIVER,driver)
    if (url != 'null') :
        args+=plugin.getParam(URL,url)
    if (propfile != 'null') :
        args+=plugin.getParam(PROPFILE,propfile)
    if (user != 'null') :
        args+=plugin.getParam(USER,user)
    if (password != 'null') :
        args+=plugin.getParam(PASSWORD,password)
    if (tableNamePrefix != 'null') :
        args+=plugin.getParam(PREFIX,tableNamePrefix)
    if (ddl != 'null') :
        args+=plugin.getParam(DDL,ddl)
    return plugin.runCommand(args)
    

def closestore(store) :
    """
    closes a prevoiusly opened FileStore or JDBCStore

    Usage: closestore(store) 

    @param store [mandatory] a previously opened JDBC or File store's name.

    @return 1 on success, 0 on failure

    """
    args=plugin.getCommand(CLOSE)
    args+=plugin.getParam(STORE,store)
    return plugin.runCommand(args)

def getopenstores() :
    """
    returns list of opened stores (for script access)

    Usage: getopenstores()

    @return String[] of store names

    """
    return plugin.getStoreNames()

def getstoreconns(store) :
    """
    returns list of connections in the specified store (for script access)

    Usage: getstoreconns(store)

    @param store [mandatory] a previously opened JDBC or File store's name.

    @return String[] of store connection names

    """
    return plugin.getConnectionNames(store)

def liststore(store='null',dir='null') :
    """
    lists storenames, opened stores, or connections (for interactive access)
    Parameters store and dir cannot both be specified concurrently.

    Usage: liststore(store='null',dir='null') 

    @param store [optional] a previously opened JDBC or File store's name.
                 If store is specified, all connections in the store are
                 listed.
    @param dir [optional] directory for which to list available store names
                 If dir is specified, all store names in the directory are listed.
    If neither store nor dir are specified, all open store names are listed.

    @return 1 on success, 0 on failure

    """
    args=plugin.getCommand(LIST)
    if (store != 'null') : 
        args+=plugin.getParam(STORE,store)
    if (dir != 'null') : 
        args+=plugin.getParam(DIR,dir)
    return plugin.runCommand(args)

def dumpstore(store, outfile, conn='null', deep='false') :
    """
    dumps store contents in human-readable format to an XML file

    Usage: dumpstore(store, outfile, conn='null', deep='false') 

    @param store [mandatory] a previously opened JDBC or File store's name.
    @param outfile [mandatory] name of xml file to dump the output to
                   (with or without .xml suffix)
    @param conn [optional default all connections] store connection name
                the dump should be restricted to.
    @param deep [optional default false] for "deep dump" - if true, adds
                store record data contents as hexdump to the dump output.

    @return 1 on success, 0 on failure

    """
    args=plugin.getCommand(DUMP)
    args+=plugin.getParam(STORE,store)
    args+=plugin.getParam(OUTFILE,outfile)
    if (conn != 'null') :
        args+=plugin.getParam(CONNECTION,conn)
    if (deep == 'true') :
        args+=plugin.getParam(DEEP,deep)
    return plugin.runCommand(args)

def copystore(fromstore,tostore,conn='null',overwrite='false') :
    """
    copies all or specified connections from one store to another
    Note: advanced mode only

    Usage: copystore(fromstore,tostore,conn='null',overwrite='false') 

    @param fromstore [mandatory] a previously opened JDBC or File store's name.
    @param tostore [mandatory] a previously opened JDBC or File store's name.
    @param conn [optional default all connections] store connection name
                the copy should be restricted to.
    @param overwrite [optional default false] if true, overwrites tostore
                connection contents in case of connection name collisions.

    fromstore and tostore cannot be the same.

    @return 1 on success, 0 on failure

    """
    args=plugin.getCommand(COPY)
    args+=plugin.getParam(FROM,fromstore)
    args+=plugin.getParam(TO,tostore)
    if (conn != 'null') :
        args+=plugin.getParam(CONNECTION,conn)
    if (overwrite == 'true') :
        args+=plugin.getParam(OVERWRITE,overwrite)
    return plugin.runCommand(args)

def deletestore(store,conn) :
    """
    deletes all or specified connections in a store
    Note: advanced mode only

    Usage: deletestore(store,conn) 

    @param store [mandatory] a previously opened JDBC or File store's name.
    @param conn [mandatory] store connection name the delete should 
                be restricted to. Special value 'all' deletes all
                store connections.

    @return 1 on success, 0 on failure

    """
    args=plugin.getCommand(DELETE)
    args+=plugin.getParam(STORE,store)
    if (conn == 'all') :
        args+=plugin.getParam(ALL,conn)
    else :
        args+=plugin.getParam(CONNECTION,conn)
    return plugin.runCommand(args)

def compactstore(dir,tempdir='null'):
    """
    compact/defragment the space occupied by the files of a filestore

    Usage: compactstore(dir,tempdir='null')

    @param dir [mandatory] directory containing filestore contents
    @param tempdir [optional default system tempdir] directory to
               be used during compacting. Should not be under dir,
               and should have extra space sufficient for the
               filestore.

    Compacted files of the filestore will be left in dir.
    Original uncompacted store files will not be deleted. They will
    be left in a uniquely named directory under tempdir.

    @return 1 on success, 0 on failure

    """
    args=plugin.getCommand(COMPACT)
    args+=plugin.getParam(DIR,dir)
    if (tempdir != 'null') :
        args+=plugin.getParam(TEMPDIR,tempdir)
    return plugin.runCommand(args)
    
def advancedstore(mode='false'):
    """
    """
    args=plugin.getCommand(ADVANCED)
    if (mode=='true') :
       args+= plugin.getParam(ON,mode)
    else :
       args+=plugin.getParam(OFF,mode)
    return plugin.runCommand(args)


functionMap = { "openfilestore": openfilestore,
                "openjdbcstore": openjdbcstore,
                "closestore": closestore,
                "liststore": liststore,
                "dumpstore": dumpstore,
                "copystore": copystore,
                "deletestore": deletestore,
                "compactstore": compactstore,
                "getopenstores": getopenstores,
                "getstoreconns": getstoreconns,
                "defaultHelp": defaultHelp,
              }


def helpstore(command=defaultHelp): 
    #if (command != 'null'):
    #    args += " "+command
    #return plugin.runCommand(args)
    if (type(command) == types.StringType) :
        try: 
            f=functionMap[command]
        except KeyError:
            print "ERROR: Unknown command: "+ command
            print ""
            f=defaultHelp
    else :
        f=command
    print f.__doc__
   
