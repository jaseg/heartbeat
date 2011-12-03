
require 'notification'

class Heartbeat
	attr_accessor :notification

  def initialize(nf)
    @notification = nf
  end

	def state
    #FIXME add magyck here!
		return "neutral"
	end
end
