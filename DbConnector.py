import datetime

from pymongo import Connection
from Configuration import Configuration, TIME_FORMAT, DB_HEARTBEAT_COLLECTION, DB_LIFE_STATUS_COLLECTION

class DbConnector:

    def __init__(self):
        self.config = Configuration()

    def init_database(self):
        self.host = self.config.db_host
        self.port = self.config.db_port
        self.connection = Connection(self.host, self.port)
        self.db = self.connection[self.config.db_name]
        self.heartbeat = self.db[DB_HEARTBEAT_COLLECTION]
        self.life_status = self.db[DB_LIFE_STATUS_COLLECTION]

    def heartbeat_within_time(self, time_stamp):
        time_stamp_to_search = time_stamp.strftime(TIME_FORMAT)

        status = True
        if not self.heartbeat.find({"time_stamp": {"$gt": time_stamp_to_search}}).count():
            status = False

        return status


    def store_status(self, life_status):
        current_time = datetime.datetime.utcnow().strftime(TIME_FORMAT)
        print self.life_status.insert({"status" : life_status, "time_stamp" : current_time})
        return True