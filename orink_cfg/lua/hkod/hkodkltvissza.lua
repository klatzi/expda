--<verzio>20161203</verzio>
require 'hu.expanda.expda/LuaFunc'
local params = {...}
ui = params[1]
ui:executeCommand("hideobj","hkodkltpanel;cikkkltpanel;cikkvalpanel")
ui:executeCommand("showobj","pfooter")


