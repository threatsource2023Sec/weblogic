# Copyright (c) 2015, 2016, Oracle and/or its affiliates. All rights reserved.
#
# Migration sub-script: this is the sample component export handler
#

from java.io import File

import handler


# this is the sample component handler for all components to follow
# only need to implement in the handle() method
class sampleCompHandler(handler.BaseComponentHandler):
      # parameters:
      #   configFile : the File object of the migration-config.xml
      #   theCompDir : the File object to the '/component/{name}' directory
      def handle(self, configFile, theCompDir):
          # do component specific export handler processing here ...
          # e.q.:
          print self.name + " handler : " + configFile.getCanonicalPath() + " , " + theCompDir.getCanonicalPath()
          return

