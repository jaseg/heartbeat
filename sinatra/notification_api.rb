#!/usr/bin/ruby -rubygems

require 'sinatra'
require 'notification'
require 'db_wrapper'

set :public_folder, File.dirname(__FILE__) + '/../layout'

get '/' do
  redirect to '/index.html'
end

post '/notification' do
	nf = Notification.new
	nf.content = params[:content]
	nf.coordinates = Coordinates.new
	nf.coordinates.longitude = params[:longitude].to_f
	nf.coordinates.latitude = params[:latitude].to_f
	if params[:source]
		nf.source = params[:source]
	else
		nf.source = request.ip
	end
	nf.timestamp = Time.now.to_i
	DbWrapper.create_heartbeat(nf)
end

