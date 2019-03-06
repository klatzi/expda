--<verzio>20170117</verzio>
require 'hu.expanda.expda/LuaFunc'
require '.egyeb.functions'
local params = {...}
ui=params[1]
ean = params[2]:gsub("\n",""):gsub(':','')
kezelo = ui:getKezelo()
kulsoraktar = ui:getGlobal("kulsoraktar")
str = 'ean_check '..ean
t=luafunc.query_assoc(str,false)
cikknev=t[1]['CIKKNEV']
kod=t[1]['CIKK']
result=t[1]['RESULT']
cikkval=0;
if (result=='0') then
    ui:executeCommand('valueto','lcikknev',cikknev)
    ui:executeCommand('valuetohidden','lcikod',kod)
    ui:executeCommand('showobj','button_ujean;button_cikkklt','')
    ui:executeCommand('startlua','hkod/cikkklt.lua', kod)
elseif (result=='-1') then
 --ui:executeCommand('setfocus','eean','') 
 alert(ui,'Nem található termék ilyen ean kóddal:\n'..ean)
 ui:executeCommand('hideobj','button_cikkklt;lcikknev','')
 ui:executeCommand('showobj','button_ujean','')
 cikkval=1
elseif (result=='-2') then
 alert(ui,'Több termék is található termék ilyen ean kóddal:\n'..ean)
 ui:executeCommand('hideobj','button_cikkklt;lcikknev','')
 ui:executeCommand('showobj','button_ujean','')
 cikkval=2
end

if (cikkval>0) then
    if (cikkval==1) then
      ean='.'
    end
    ui:executeCommand("startlua","egyeb/cikkval_open.lua",ean)
end
