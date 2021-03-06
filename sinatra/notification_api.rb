#!/usr/bin/env ruby

require 'rubygems'
require 'sinatra'
require File.dirname(__FILE__) + '/heartbeat.rb'
require File.dirname(__FILE__) + '/session.rb'

MongoMapper.connection = Mongo::Connection.new
MongoMapper.database = "heartbeat"

set :public_folder, File.dirname(__FILE__) + '/../layout'

sessions = Hash.new
sessions["default"] = Session.new
sessions["default"].user = User.find_by_name("jaseg")
puts sessions["default"].user
puts sessions["default"].user.username

get '/' do
  redirect to '/index.html'
end

post '/notification' do
  id = params[:id]
  #FIXME testing code
  id = "default" if id.nil?
  return '{"error":"invalid session"}' unless id and sessions[id]
  user = sessions[id].user
  return '{"error": "not authenticated"}' unless user
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

#direct version
get '/notifications/by-source/:source' do
  id = params[:id]
  return '{"error":"invalid session"}' unless id and sessions[id]
  #page = params[:page] ? params[:page] : 1
  return Heartbeat.all(:conditions => {"user_id" => sessions[id].user.id, "notification.source" => params[:source]}).to_json()
end

get '/sources' do
  id = params[:id]
  return '{"error":"invalid session"}' unless id and sessions[id]
  return Heartbeat.all(:conditions => {"user_id" => sessions[id].user.id}).map {|hb|
    hb.notification.source
  }.uniq.to_json()
end

#contact version
get "/user/:username/notifications/by-source/:source" do
  id = params[:id]
  return '{"error":"invalid session"}' unless id and sessions[id]
  contact = sessions[id].user
  return '{"error":"user not found"}' unless params[:username]
  user = User.find_by_name(params[:username])
  #FIXME
  return '{"error":"user not found"}' unless user and user.contact_ids.includes? contact.id
  #page = params[:page] ? params[:page] : 1
  return Heartbeat.all(:conditions => {"user_id" => user.id, "notification.source" => params[:source]}).to_json()
end

get '/contacts' do
  id = params[:id]
  return '{"error":"invalid session"}' unless id and sessions[id]
  user = sessions[id].user
  return '{"error": "not authenticated"}' unless user
  user.contact_ids.map {|contact_id|
    User.find_by_id(contact_id).username
  }.to_json
end

delete '/contacts/:name' do
  id = params[:id]
  return '{"error":"invalid session"}' unless id and sessions[id]
  user = sessions[id].user
  return '{"error": "not authenticated"}' unless user
  user.contact_ids.delete User.find_by_name(params[:name]).id
end

post '/contacts' do
  id = params[:id]
  return '{"error":"invalid session"}' unless id and sessions[id]
  user = sessions[id].user
  return '{"error":"not authenticated"}' unless user
  contact_name = params[:contact_name]
  return '{"error":"invalid arguments"}' unless contact_name
  new_id = User.find_by_name(contact_name).id
  #return '{"error":"already added"}' unless user.contact_ids.include? new_id
  user.contact_ids << new_id
  #FIXME ugly.
  user.contact_ids.uniq!
  user.save
  '{"success":"true"}'
end

get '/session' do
  session = Session.new
  id = Session.generateID
  sessions[id] = session
  "{\"id\":\"#{id}\",\"salt\":\"#{GLOBAL_SALT}\"}"
end

post '/update_user' do
  id = params[:id]
  return '{"error":"invalid session"}' unless id and sessions[id]
  user = sessions[id].user
  return '{"error": "not authenticated"}' unless user
  user.positive_keyword = params[:positive_keyword] if params[:positive_keyword]
  user.negative_keyword = params[:negative_keyword] if params[:negative_keyword]
  user.save
end

post '/login' do
  id = params[:id]
  username = params[:username]
  pwhash = params[:pwhash]
  return '{"error":"invalid session"}' unless id and sessions[id]
  if username and pwhash 
    user = User.find_by_name(username)
    return '{"error":"already authenticated"}' if sessions[id].user
    if user and user.authenticate(pwhash, id)
      sessions[id].user = user
      return '{"success":"true"}'
    else
      sessions.delete id
      return '{"error":"wrong something"}'
    end
  else
    return '{"error":"request invalid"}'
  end
end

post '/logout' do
  id = params[:id]
  sessions.delete id if id
  return '{"success":"true"}'
end

post '/register' do
  puts 'register called'
  username = params[:username]
  pwhash = params[:pwhash]
  id = params[:id]
  return '{"error":"invalid session"}' unless id and sessions[id]
  if username and pwhash
    user = User.new
    user.username = username
    user.pwhash = pwhash
    user.positive_keyword = ''
    user.negative_keyword = ''
    unless User.find_by_name(username)
      user.save
      sessions[id].user = user
      return '{"success":"true"}'
    else
      return '{"error":"username taken"}'
    end
  else
    return '{"error":"request invalid"}'
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

