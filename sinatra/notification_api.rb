#!/usr/bin/env ruby

require 'rubygems'
require 'sinatra'
require File.dirname(__FILE__) + '/heartbeat.rb'

MongoMapper.connection = Mongo::Connection.new
MongoMapper.database = "heartbeat"

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
	nf.timestamp = Time.now
  hb = Heartbeat.new(nf)
  hb.save
  nf.timestamp
end

