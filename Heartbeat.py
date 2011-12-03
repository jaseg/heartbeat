class Heartbeat:

    content = None
    time_stamp = None
    coordinates = None
    source = None

    def __init__(self, content, time_stamp, coordinates, source):
        self.content     = content
        self.time_stamp  = time_stamp
        self.coordinates = coordinates
        self.source      = source

