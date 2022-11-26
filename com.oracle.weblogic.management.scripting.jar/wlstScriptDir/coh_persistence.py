#
# Name:    coh_persistence.py
# Author:  (Oracle)
# Date:    Nov-2014
# Purpose: This WLST script contains functions for issuing Coherence
#          Persistence commands when connected to an Admin Server 
#          domain runtime mbean server.  This script is automatically
#          exec'd on WLST startup and the commands are made available.
#
# Copyright (c) 2015, 2019, Oracle and/or its affiliates. All rights reserved.

import os
from java.io import File
from com.oracle.datagrid.persistence import PersistenceTools;
from com.oracle.datagrid.persistence import PersistenceStatistics;
from com.oracle.datagrid.persistence import OfflinePersistenceInfo;
from com.tangosol.persistence        import CachePersistenceHelper;

# Exception to identify specific errors
class PersistenceException(RuntimeError):
   def __init__(self, arg):
      self.args = arg

#####################
# Helpers
#####################

# Purpose: Print the value of a boolean
#
# Params:  value - value to print
def __printBool(value):
   if value == True:
      return 'True'
   else:
      return 'False'

# Purpose:  Ensure that the user is in domainRuntime() tree
#
# Throws:   PersistenceException if we are not in domainRuntime tree
def __ensureDomainRuntime():
   loc=pwd()
   if not loc.startswith('domainRuntime'):
      raise PersistenceException('You must be in domain runtime tree to using Recoverable Caching commands')

# Purpose: Return the fully qualified name for an object
#
# Params:  query - query to get fully qualified name from
#
# Return:  the fully qualified name for an object
#
def __coh_getFullyQualifiedName(query):
   __ensureDomainRuntime()
   beans = list(mbs.queryMBeans(ObjectName(query),None))
   if len(beans) == 0:
      raise RuntimeException('No results found')
   for bean in beans:
      return bean.getObjectName()

# Purpose: Return the MBean name of the PersistenceCoordinator for a service
#
# Params:  serviceName - service name to get persistence coordinator for
#
# Return:  MBean name of the PersistenceCoordinator for a service
#
# Throws:  PersistenceException is the service is not valid
#
def __coh_getPersistenceCoordinator(serviceName):
   try:
      return __coh_getFullyQualifiedName('Coherence:type=Persistence,service=' + serviceName + ',responsibility=PersistenceCoordinator,*')
   except Exception, e:
      raise PersistenceException('Service %s does not exist or is not configured for recoverable caching. (%s)' % (serviceName, e))

# Purpose: Return the current operation status for a PersistenceCoordinator
#
# Params:  serviceName - service name to check
# Params:  serviceName - service name to check
#
# Return:  the current status of the snapshot manager
#
def __coh_getCurrentStatus(serviceName):
   manager = __coh_getPersistenceCoordinator(serviceName)
   return mbs.getAttribute(manager, 'OperationStatus')

# Purpose: Return the list of snapshots for a PersistenceCoordinator
#
# Params:  serviceName - service name to list snapshots for
#
# Return:  the current list of snapshots
#
def __coh_getSnapshots(serviceName):
   manager = __coh_getPersistenceCoordinator(serviceName)
   return mbs.getAttribute(manager, 'Snapshots')

# Purpose: Return the list of archived snapshots for a PersistenceCoordinator
#
# Params:  serviceName - service name to list archived snapshots for
#
# Return:  the current list of snapshots
#
def __coh_getArchivedSnapshots(serviceName):
   manager = __coh_getPersistenceCoordinator(serviceName)
   try:
      return mbs.invoke(manager, "listArchivedSnapshots", None, None)
   except Exception, e:
      raise PersistenceException('Unable to list archived snapshots for service ' + serviceName +
                                        ' : ' + str(e.getMessage()) + ' ' + str(e.getCause()))

# Purpose: Return True if a service exists and can be used for recoverable
#          caching commands
#
# Params:  serviceName - service name to check for existance
#
def __coh_serviceExists(serviceName):
   try:
      manager = __coh_getPersistenceCoordinator(serviceName)
      return True
   except Exception, e:
      return False

# Purpose: Return True if a snapshot exists for a given service
#
# Params: snapshotName - snapshot name to check
#         serviceName  - service name to check
#
# Return: True if a snapshot exists
#
def __coh_snapshotExists(snapshotName, serviceName):
   snapshots = __coh_getSnapshots(serviceName)
   for snapshot in snapshots:
      if snapshot == snapshotName:
         return True
   return False

# Purpose: Return True if an archived snapshot exists for a given service
#
# Params: snapshotName - snapshot name to check
#         serviceName  - service name to check
#
# Return: True if an archived snapshot exists
#
def __coh_archivedSnapshotExists(snapshotName, serviceName):
    snapshots = __coh_getArchivedSnapshots(serviceName)
    for snapshot in snapshots:
        if snapshot == snapshotName:
            return True
    return False

# Purpose: Return True if an archived snapshot exists for a given service
#
# Params: snapshotName - snapshot name to check
#         serviceName  - service name to check
#
# Return: True if a snapshot exists
#
def __coh_archivedSnapshotExists(snapshotName, serviceName):
    snapshots = __coh_getArchivedSnapshots(serviceName)
    for snapshot in snapshots:
        if snapshot == snapshotName:
            return True
    return False

# Purpose: Call a persistence operations against a service
#
# Params:  operation    - the operation to execute
#          snapshotName - snapshot name to operate on
#          serviceName  - service name to operate on
#          timeout      - the number of seconds to wait for the operation to complete
#
# Throws:  PersistenceException if any errors
#
def __coh_callPersistenceOperation(operation, snapshotName, serviceName, timeout = 300):
   __ensureDomainRuntime()
   totalSleepTime = 0
   sleepTime      = 1
   try:
      name   = __coh_getPersistenceCoordinator(serviceName)
      params = [snapshotName]
      sign   = ['java.lang.String']

      mbs.invoke(name, operation, params, sign)
      status = __coh_getCurrentStatus(serviceName)
      while status != 'Idle':
          java.lang.Thread.sleep(sleepTime * 1000L)
          totalSleepTime += sleepTime
          if totalSleepTime >= timeout:
             raise RuntimeError('Waited for ' + str(timeout) + ' seconds for completion of ' + operation + ' on service ' + serviceName + ' for snapshot ' + snapshotName)
          status = __coh_getCurrentStatus(serviceName)
          print 'Current status: Service ' + serviceName + ' is ' + status
   except Exception, e:
      raise PersistenceException('Unable to call operation ' + operation + ' on service ' + serviceName +
              ' and snapshot ' + snapshotName + ': ' + str(e.getMessage()) + ' ' + str(e.getCause()))

# Purpose: Print out the information obtained from the tools
# 
# Params:  tools - an instance of PersistenceTools
#
def __coh_printToolsDetails(tools):
   stats = tools.getStatistics();
   info  = tools.getPersistenceInfo(); 

   print 'Storage Version:              %s' % (str(info.getStorageVersion()))
   print 'Implementation Version:       %s' % (str(info.getImplVersion()))
   print 'Number of partitions:         %s' % (str(info.getPartitionCount()))
   print 'Number of partitions present: %s' % (str(len(info.getGUIDs())))
   print 'Is Complete?:                 %s' % (__printBool(info.isComplete()))
   print 'Is Archived?:                 %s' % (__printBool(info.isArchived()))
   print
   print 'Cache Statistics'

   if stats != None:
      for cacheName in stats:
         print 'cacheName=%s, size=%s, bytes=%s, indexes=%s, triggers=%s, listeners=%s, locks=%s' %     \
                (cacheName, str(stats.getCacheSize(cacheName)),  str(stats.getCacheBytes(cacheName)),   \
                            str(stats.getIndexCount(cacheName)), str(stats.getTriggerCount(cacheName)), \
                            str(stats.getListenerCount(cacheName)), str(stats.getLockCount(cacheName))  \
                )

    
#####################
# Public functions
#####################

# Purpose: List the snapshots for a given service
#
# Params:  serviceName - service name to list snapshots for
#
def coh_listSnapshots(serviceName):
   print 'Snapshots for service %s' % (serviceName)
   snapshots = __coh_getSnapshots(serviceName)
   for snapshot in snapshots:
      print '   ' + snapshot

# Purpose: List the archived snapshots for a given service
#
# Params:  serviceName - service name to list archived snapshots for
#
def coh_listArchivedSnapshots(serviceName):
    print 'Archived snapshots for service %s' % (serviceName)
    snapshots = __coh_getArchivedSnapshots(serviceName)
    for snapshot in snapshots:
        print '   ' + snapshot

# Purpose: Create a snapshot for a given service.
#
# Params:  snapshotName - name of the snapshot to create
#          serviceName  - service name to create the snapshot for
#
def coh_createSnapshot(snapshotName, serviceName):
  if (__coh_snapshotExists(snapshotName, serviceName)):
     raise PersistenceException('A snapshot named %s already exists for service %s' % (snapshotName, serviceName))
  print 'Creating snapshot %s for service %s' % (snapshotName, serviceName)
  __coh_callPersistenceOperation('createSnapshot', snapshotName, serviceName)

# Purpose: Remove a snapshot for a given service.
#
# Params:  snapshotName - name of the snapshot to remove
#          serviceName  - service name to remove the snapshot for
#
def coh_removeSnapshot(snapshotName, serviceName):
  if (not __coh_snapshotExists(snapshotName, serviceName)):
     raise PersistenceException('A snapshot named %s does not exist for service %s' % (snapshotName, serviceName))
  print 'Removing snapshot %s for service %s' % (snapshotName, serviceName)
  __coh_callPersistenceOperation('removeSnapshot', snapshotName, serviceName)

# Purpose: Recover a snapshot for a given service.
#
# Params:  snapshotName - name of the snapshot to recover
#          serviceName  - service name to recover the snapshot for
#
def coh_recoverSnapshot(snapshotName, serviceName):
    if (not __coh_snapshotExists(snapshotName, serviceName)):
        raise PersistenceException('A snapshot named %s does not exist for service %s' % (snapshotName, serviceName))
    print 'Recovering snapshot %s for service %s' % (snapshotName, serviceName)
    __coh_callPersistenceOperation('recoverSnapshot', snapshotName, serviceName)

# Purpose: Archive a snapshot for a given service.
#
# Params:  snapshotName - name of the snapshot to archive
#          serviceName  - service name to archive the snapshot for
#
def coh_archiveSnapshot(snapshotName, serviceName):
    if (not __coh_snapshotExists(snapshotName, serviceName)):
        raise PersistenceException('A snapshot named %s does not exist for service %s' % (snapshotName, serviceName))
    print 'Archiving snapshot %s for service %s' % (snapshotName, serviceName)
    __coh_callPersistenceOperation('archiveSnapshot', snapshotName, serviceName)

# Purpose: Retrieve an archived snapshot for a given service.
#
# Params:  snapshotName - name of the snapshot to retrieve
#          serviceName  - service name to retrieve the snapshot for
#
def coh_retrieveArchivedSnapshot(snapshotName, serviceName):
    if (__coh_snapshotExists(snapshotName, serviceName)):
        raise PersistenceException('A snapshot named %s already exists for service %s. Please remove if before you retrieve the snapshot' % (snapshotName, serviceName))
    if (not __coh_archivedSnapshotExists(snapshotName, serviceName)):
        raise PersistenceException('An archived snapshot named %s does not exist for service %s' % (snapshotName, serviceName))
    print 'Retrieving snapshot %s for service %s' % (snapshotName, serviceName)
    __coh_callPersistenceOperation('retrieveArchivedSnapshot', snapshotName, serviceName)

# Purpose: Remove an archived snapshot for a given service.
#
# Params:  snapshotName - name of the snapshot to remove
#          serviceName  - service name to remove the snapshot for
#
def coh_removeArchivedSnapshot(snapshotName, serviceName):
    if (not __coh_archivedSnapshotExists(snapshotName, serviceName)):
        raise PersistenceException('An archived snapshot named %s does not exist for service %s' % (snapshotName, serviceName))
    print 'Removing snapshot %s for service %s' % (snapshotName, serviceName)
    __coh_callPersistenceOperation('removeArchivedSnapshot', snapshotName, serviceName)

# Purpose: Validate a snapshot on disk
#
# Params:  snapshotDir - snapshot directory to validate
#          verbose     - indicates if verbose output is required
#
def coh_validateSnapshot(snapshotDir, verbose=False):
   if os.path.isdir(snapshotDir) == False:
      raise PersistenceException('The directory ' + snapshotDir + ' does not exist');
   try:
      print
      print 'Validating snapshot directory %s' % (snapshotDir)
      tools = CachePersistenceHelper.getSnapshotPersistenceTools(File(snapshotDir));
      if verbose:
         __coh_printToolsDetails(tools)

      print 
      print 'Validation complete'

   except Exception, e:
      raise PersistenceException('Unable to validate snapshot directory ' + snapshotDir + 
              ' : ' + str(e.getMessage()) + ' ' + str(e.getCause()))

# Purpose: Validate an archived snapshot using the archiver.
#          Note: the operational config containing the archiver should
#          be available on the classpath.
#
# Params:  snapshotName - name of the archived snapshot to validate
#          clusterName  - name of the cluster the archived snapshot belongs to
#          serviceName  - name of the service the archived snapshot belongs to
#          archiverName - name of the archiver to use for retrieving snapshot
#          verbose      - indicates if verbose output is required
#
def coh_validateArchivedSnapshot(snapshotName, clusterName, serviceName, archiverName, verbose=False):
   try:
      print
      print 'Validating archived snapshot %s belonging to cluster %s and service %s using archiver %s' % (snapshotName, clusterName, serviceName, archiverName)
      tools = CachePersistenceHelper.getArchiverPersistenceTools(snapshotName, clusterName, serviceName, archiverName);
      if verbose:
         __coh_printToolsDetails(tools)

      print 
      print 'Validation complete'

   except Exception, e:
      raise PersistenceException('Unable to validate archived snapshot ' + snapshotName + 
              ' : ' + str(e.getMessage()) + ' ' + str(e.getCause()))
