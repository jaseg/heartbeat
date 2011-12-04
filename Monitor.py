import time
import datetime
from Configuration import THRESHOLDS_HEARTBEATS_MISSING, Configuration
from DbConnector import DbConnector
from LifeStatus import LifeStatus

class Monitor:

    def __init__(self):
        self.life_sign_counter = 0

    def init_database(self):
        self.db_connection = DbConnector()
        self.db_connection.init_database()

    def check_person_for_status(self):
        current_time = datetime.datetime.utcnow()
        last_hour = current_time - datetime.timedelta(hours=1)

        if self.db_connection.heartbeat_within_time(last_hour):
            # received: ok, store new status to db
            self.db_connection.store_status(LifeStatus.OK)
            self.life_sign_counter = 0
            print "ALL OK!"

            # not received: start incrementing monitor counter
            if self.life_sign_counter < THRESHOLDS_HEARTBEATS_MISSING:
                self.life_sign_counter += 1
                self.db_connection.store_status(LifeStatus.WARNING)
                print "MISSING HEARTBEAT"
            else:
                # Uh-oh! Person missing!
                self.db_connection.store_status(LifeStatus.MISSING)
                print "UH-OH! PERSON MISSING!"

def main():
    monitor = Monitor()
    monitor.init_database()

    while True:
        print "Checking status"
        monitor.check_person_for_status()

        print "Let's wait for 10 seconds"
        time.sleep(10)

#
# MAIN
#
if __name__ == "__main__":
    main()
  