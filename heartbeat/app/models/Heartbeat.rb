class Heartbeat
  include MongoMapper::Document
  
  key :status,    Integer
  key :timestamp, DateTime
  key :source,    String
  
end
