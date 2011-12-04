# -*- encoding : utf-8 -*-
require 'test_helper'

class HeartbeatsControllerTest < ActionController::TestCase
  def test_index
    get :index
    assert_template 'index'
  end
end
