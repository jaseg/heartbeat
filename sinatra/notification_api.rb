#!/usr/bin/ruby -rubygems

require 'sinatra'
require 'heartbeat'
MongoMapper.connection = Mongo::Connection.new
MongoMapper.database = "heartbeat"
require 'digest/sha1'
#require 'haml'
require 'sinatra-authentication'
use Rack::Session::Cookie, :secret => 'PWUaNDEMp684wCppWY96XBACpWpuoRIG'

set :public_folder, File.dirname(__FILE__) + '/../layout'
set :sinatra_authentication_view_path, File.dirname(__FILE__) + "/authentication/lib/views/"
module Sinatra
  module Helpers
    def use_layout?
      false
    end
  end
end

get '/' do
  redirect to '/index.html'
end

post '/notification' do
  login_required
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

