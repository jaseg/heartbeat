
require 'coordinates'

class Notification
  include  MongoMapper::EmbeddedDocument

  key :content, String
  key :coordinates, Coordinates
  key :timestamp, Time
  key :ip, String
  key :source, String
end

