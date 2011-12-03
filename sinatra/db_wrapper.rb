
require 'heartbeat'
require 'mongo'

class DbWrapper
  attr_accessor :name
  
  @conn = Mongo::Connection.new
  @db = @conn['heartbeat']
  @notifications = @db['heartbeats']

  def self.insert_heartbeat(heartbeat)
    doc = {
      "state" => heartbeat.state,
      "notification" => {
        "content" => heartbeat.notification.content,
        "coordinates" => {
          "longitude" => heartbeat.notification.coordinates.longitude,
          "latitude" => heartbeat.notification.coordinates.latitude
        },
        "timestamp" => heartbeat.notification.timestamp,
        "source" => heartbeat.notification.source
      }
    }
    puts @notifications.insert(doc).to_s
  end

  def self.create_heartbeat(notification)
	  hb = Heartbeat.new(notification)
    insert_heartbeat(hb)
  end
end

