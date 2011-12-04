# -*- encoding : utf-8 -*-
class HeartbeatsController < ApplicationController
  def index
    @heartbeats = Heartbeat.new
    @heartbeats.status = 0
    @heartbeats.timestamp = "2011-12-04T11:32:31.267Z"
    @heartbeats.source = "mobile"
  end
end
