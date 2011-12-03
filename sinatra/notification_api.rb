#!/usr/bin/ruby -rubygems

require 'sinatra'
require 'notification'

post '/notification' do
	nf = Notification.new
	nf.content = params[:content]
	nf.coordinates = params[:coordinates]
	if params[:source]
		nf.source = params[:source]
	else
		nf.source = request.ip
	end
	nf.timestamp = Time.now.to_i
	DBWrapper.create_heartbeat(nf)
	nf.timestamp.to_s
end

