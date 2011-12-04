import ConfigParser

#
# NOT CHANGEABLE
#
TIME_FORMAT = '%Y-%m-%dT%H:%M:%S.%fZ'
DB_HEARTBEAT_COLLECTION = 'heartbeat'
DB_LIFE_STATUS_COLLECTION = 'life_status'
CONFIG_FILE="heartbeat_monitor.cfg"

#
# KEYS FROM CONFIGURATION FILE
#
DB_SECTION = "DB"
DB_HOST = 'DB_HOST'
DB_PORT = 'DB_PORT'
DB_NAME = 'DB_NAME'
THRESHOLDS_SECTION = "THRESHOLDS"
THRESHOLDS_HEARTBEATS_MISSING = 3

class Configuration:

    db_host = 'localhost'
    db_port = 27017
    db_name = 'heartbeat'

    heartbeats_missing = 3

    def __init__(self):
        self.config = ConfigParser.RawConfigParser()

    def read_configuration_file(self):
        self.config.read(CONFIG_FILE)

        # Read "DB" section
        self.db_host = self.config.get(DB_SECTION, DB_HOST)
        self.db_port = self.config.get(DB_SECTION, DB_PORT)
        self.db_name = self.config.get(DB_SECTION, DB_NAME)

        # Read "Thresholds" section
        self.heartbeats_missing = self.config.get(THRESHOLDS_SECTION, THRESHOLDS_HEARTBEATS_MISSING)




