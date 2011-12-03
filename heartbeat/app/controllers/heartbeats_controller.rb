class HeartbeatsController < ApplicationController
  def index
    #@heartbeats = Heartbeat.all
    @heartbeats = ['Auto', 'Motorrad']
  end
end
