require File.dirname(__FILE__) + '/notification.rb'

class Heartbeat
  include MongoMapper::Document
  key :notification, Notification
  key :state, String
#  def self.to_mongo(value)
#    [{"notification" => notification, "state" => state()}]
#  end
#
#  def self.from_mongo(value)
#    Heartbeat.new(Notification.from_mongo(value["notification"]));
#  end

	def compute_state
    #FIXME add magyck here!
		@state = "neutral"
	end

  def initialize(nf)
    @notification = nf
    compute_state
  end
end
