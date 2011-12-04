
require File.dirname(__FILE__) + '/notification.rb'

class Heartbeat
  include MongoMapper::Document
  key :notification, Notification
  key :state, String
  belongs_to :user

	def compute_state(user)
    #FIXME add magyck here!
    if @notification.content? and @notification.content.class == String
      if @notification.content.include? user.negative_keyword
        @state = "negative" 
      elsif @notification.content.include? user.positive_keyword
        @state = "positive"
      else
        @state = "neutral" 
      end
    else
      @state = "neutral" 
    end
	end

  def initialize(nf, user)
    @notification = nf
    @user_id=user.id
    compute_state(user)
  end
end
