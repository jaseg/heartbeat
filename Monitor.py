import time
import datetime
from Configuration import THRESHOLDS_HEARTBEATS_MISSING, Configuration
from DbConnector import DbConnector
from LifeStatus import LifeStatus

class Monitor:

    def __init__(self):
        self.life_sign_counter = 0

    def init_database(self):
        self.db_connector = DbConnector()
        self.db_connector.init_database()

    def check_person_for_status(self, heartbeats_missing):
        current_time = datetime.datetime.utcnow()
        last_hour = current_time - datetime.timedelta(hours=1)

        if self.db_connector.heartbeat_within_time(last_hour):
            # received: ok, store new status to db
            self.db_connector.store_status(LifeStatus.OK)
            self.life_sign_counter = 0
            print "ALL OK!"
        else:
            # not received: start incrementing monitor counter
            if self.life_sign_counter < heartbeats_missing:
                self.life_sign_counter += 1
                self.db_connector.store_status(LifeStatus.WARNING)
                print "MISSING HEARTBEAT"
            else:
                # Uh-oh! Person missing!
                self.db_connector.store_status(LifeStatus.MISSING)
                print "UH-OH! PERSON MISSING!"

def main():
    configuration = Configuration()
    monitor = Monitor()
    monitor.init_database()

    while True:
        print "."
        time.sleep(configuration.time_intervals)

        monitor.check_person_for_status(configuration.heartbeats_missing)


#
# MAIN
#
if __name__ == "__main__":
    main()
  