--<verzio>20161101</verzio>
require 'hu.expanda.expda/LuaFunc'
local params = {...}
ui = params[1]
kezelo = ui:getKezelo()
--atnezo panel
mibiz = tostring(ui:findObject('lmibiz'):getText())
str = 'bevet_cikklist '..kezelo..' '..mibiz
list=luafunc.query_assoc_to_str(str,false)
luafunc.refreshtable_fromstring('atnezo_table',list)
str = 'kiadas_review_sum '.. kezelo ..' '..mibiz
sum=luafunc.query_assoc(str,false)
drb = sum[1]['DRB']
drb2 = sum[1]['DRB2']
ui:executeCommand("valueto","losszesen",'Összesen ' .. drb .. ' drb')
ui:executeCommand("valueto","lkiszedve",'Bevéve ' ..drb2 .. ' drb')

ui:executeCommand("showobj","reviewpanel")


