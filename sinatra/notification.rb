
require File.dirname(__FILE__) + '/coordinates.rb'

class Notification
  include  MongoMapper::EmbeddedDocument

  key :content, String
  key :coordinates, Coordinates
  key :timestamp, Time
  key :ip, String
  key :source, String
end

