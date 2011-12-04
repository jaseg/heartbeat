
require File.dirname(__FILE__) + '/user.rb'

class Session
  attr_accessor :user, :created

  def self.initialize
    @created = Time.now
  end

  def self.generateID
    (0...24).map{ ('a'..'z').to_a[rand(26)] }.join
  end
end

