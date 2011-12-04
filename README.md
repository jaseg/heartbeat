Heartbeat Monitor
=================

This application is part of the "Heartbeat" Solution started on the [RHok](http://www.rhok.org/) (Random Hacks of Kindness) Berlin on Dec. 3rd 2011.

General objective
------------------
"Heartbeat" is a proof of concept. The goal is to develop a system that provides people who are in danger of oppression due to political views with the possibility to be "tracked" in daily life. This tracking makes sure that the person is seemingly still active in his own private environment (and not locked away). As soon the tracking cannot spot the person anymore (no heartbeats have been received) the system starts informing family members, defined contacts, Amnesty International, etc. immediately to report a missing person.

Tracking can be accomplished by:

* using a mobile app (sending out automatic background heartbeats or manually sending out an alarm by the user)
* manually using a web interface
* monitoring the person's Twitter Tweets
* receiving SMS
* etc.

Heartbeat Monitor's objective
------------------------------
Heartbeats are stored to a database (here: MongoDB). These heartbeats are monitored in defined time intervals. As soon as heartbeats are missing (e.g. no heartbeats within the last 8 hours), the system sets a Warning status. As soon as a threshold (e.g. 3 missing hearbeats) is reached, the system switches to an Alarm status and starts contacting defined family members, contacts, etc. Further actions still need to be defined (e.g. inform Amnesty International if family has confirmed the missing of the particular person).

Requirements
-------------
A working Python 2.6 and higher should be installed.

Installation
-------------
Just copy this to a desired location (will change in future).

Running the monitor
-------------------
Simply start by running the main:

	$ python Monitor.py
