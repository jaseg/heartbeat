
require 'digest'

GLOBAL_SALT = "ylLUonFlk53i5fQ08e0495PFdPFFZBJn"

class User
  include MongoMapper::Document
  
  key :username, String
  key :pwhash, String
  key :positive_keyword, String
  key :negative_keyword, String
  key :contact_ids, Array
  many :users, :in => :contact_ids
  
  def self.hash(text, salt)
    hash = Digest::SHA256.new
    hash << text
    hash << salt
    return hash.digest 
  end

  def authenticate(pwhash, temp_salt)
    return User.hash(@pwhash, temp_salt) == User.hash(pwhash, temp_salt)
  end

  def self.find_by_name(username)
    first(:username => username)
  end

end

