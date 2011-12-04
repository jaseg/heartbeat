require 'mongo_mapper'

class Coordinates
  include MongoMapper::EmbeddedDocument
  key :longitude, Float
  key :latitude, Float
end

