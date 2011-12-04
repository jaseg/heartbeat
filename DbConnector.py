import datetime

from pymongo import Connection
from Configuration import Configuration, DB_HEARTBEAT_COLLECTION, DB_LIFE_STATUS_COLLECTION

class DbConnector:

    def __init__(self):
        self.config = Configuration()

    def init_database(self):
        self.host = self.config.db_host
        self.port = self.config.db_port
        self.connection = Connection(self.host, self.port)
        self.db = self.connection[self.config.db_name]
        self.heartbeat = self.db[DB_HEARTBEAT_COLLECTION]
        self.lifestatus = self.db[DB_LIFE_STATUS_COLLECTION]

    def heartbeat_within_time(self, time_stamp):
        """
        Example record from MongoDB:
        {
            "_id" : ObjectId("4edb4dd55edba2e777000008"),
            "notification" : {
                "_id" : ObjectId("4edb4dd55edba2e777000009"),
                "coordinates" : {
                    "_id" : ObjectId("4edb4dd55edba2e777000007"),
                    "longitude" : 0,
                    "latitude" : 0
                },
                "timestamp" : ISODate("2011-12-04T10:39:17Z"),
                "source" : "127.0.0.1"
            },
            "state" : "neutral"
        }
        """
        status = True
        records = self.heartbeat.find({"notification.timestamp": {"$gt": time_stamp}})
        if records.count() == 0:
            print "No records found"
            status = False
        else:
            print "Found {0} records!".format(records.count())

        return status


    def store_status(self, life_status):
        self.lifestatus.insert({"status" : life_status, "timestamp" : datetime.datetime.utcnow()})
        return True
        