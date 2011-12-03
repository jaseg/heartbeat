#!/usr/bin/ruby -rubygems

require 'sinatra'
require 'heartbeat'
require 'session'
MongoMapper.connection = Mongo::Connection.new
MongoMapper.database = "heartbeat"

set :public_folder, File.dirname(__FILE__) + '/../layout'

sessions = Hash.new

get '/' do
  redirect to '/index.html'
end

post '/notification' do
  id = params[:id]
  return '{\"error\":"invalid session"}' unless id and sessions[id]
  user = sessions[id].user
  return '{\"error\": "not authenticated"}' unless user
	nf = Notification.new
	nf.content = params[:content]
	nf.coordinates = Coordinates.new
	nf.coordinates.longitude = params[:longitude].to_f
	nf.coordinates.latitude = params[:latitude].to_f
	nf.source = params[:source] ? params[:source] : "extern"
  nf.ip = request.ip
	nf.timestamp = Time.now
  hb = Heartbeat.new(nf, user)
  hb.save
  "{\"timestamp\":\"#{nf.timestamp}\"}"
end

get '/session' do
  session = Session.new
  id = Session.generateID
  sessions[id] = session
  "{\"id\":\"#{id}\",\"salt\":\"#{GLOBAL_SALT}\"}"
end

post '/login' do
  id = params[:id]
  username = params[:username]
  pwhash = params[:pwhash]
  return '{\"error\":"invalid session"}' unless id and sessions[id]
  if username and pwhash 
    user = User.find_by_name(username)
    return '{"error":"already authenticated"}' if sessions[id].user
    if user and user.authenticate(pwhash, id)
      sessions[id].user = user
      return '{\"success\":\"true\"}'
    else
      sessions.delete id
      return '{\"error\":"wrong something"}'
    end
  else
    return '{\"error\":"request invalid"}'
  end
end

post '/logout' do
  id = params[:id]
  sessions.delete id if id
end

post '/register' do
  puts 'register called'
  username = params[:username]
  pwhash = params[:pwhash]
  id = params[:id]
  return '{\"error\":"invalid session"}' unless id and sessions[id]
  if username and pwhash
    user = User.new
    user.username = username
    user.pwhash = pwhash
    unless User.find_by_name(username)
      user.save
      sessions[id].user = user
      return '{\"success\":\"true\"}'
    else
      return '{\"error\":"username taken"}'
    end
  else
    return '{\"error\":"request invalid"}'
  end
end

def session_cleanup
  #Timeout after two hours FIXME this should be configurable
  deadline = Time.now - 3600*2
  sessions.each do |id,session|
    if (session.created <=> deadline) == -1
      sessions.delete id
    end
  end
end

