--<verzio>20171211</verzio>
require 'hu.expanda.expda/LuaFunc'
require '.egyeb.functions'
local params = {...}
ui = params[1]
kezelo = tostring(ui:findObject('elogin'):getText()):gsub("n",""):gsub(':','')
str = 'login_check '..kezelo
t=luafunc.query_assoc(str,false)
if (t[1]['RESULTTEXT']=='OK') then
    ui:executeCommand("valueto","elogin","")
    ui:executeCommand('openxml','bluetooth.xml',kezelo)
    --ui:executeCommand('openxml','mainmenu.xml',kezelo)

    
else
    alert(ui,"Nem megfelelő bejelentkezési adatok!")
    ui:executeCommand("valueto","elogin","")
    ui:executeCommand("setfocus","elogin","")
end