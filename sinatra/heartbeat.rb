
require 'notification'

class Heartbeat
  include MongoMapper::Document
  key :notification, Notification
  key :state, String
  belongs_to :user

	def compute_state
    #FIXME add magyck here!
		@state = "neutral"
	end

  def initialize(nf, user)
    @notification = nf
    @user_id=user.id
    compute_state
  end
end
