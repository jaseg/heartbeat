import ConfigParser

#
# NOT CHANGEABLE
#
TIME_FORMAT = '%Y-%m-%dT%H:%M:%S.%fZ'
DB_HEARTBEAT_COLLECTION = 'heartbeats'
DB_LIFE_STATUS_COLLECTION = 'lifestatus'
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
THRESHOLDS_TIME_INTERVAL_IN_SECONDS = 60

class Configuration:

    db_host = 'localhost'
    db_port = 27017
    db_name = 'heartbeat'

    heartbeats_missing = THRESHOLDS_HEARTBEATS_MISSING
    time_intervals = THRESHOLDS_TIME_INTERVAL_IN_SECONDS

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
        self.time_intervals = self.config.get(THRESHOLDS_SECTION, THRESHOLDS_TIME_INTERVAL_IN_SECONDS)




