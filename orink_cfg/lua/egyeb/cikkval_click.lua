--<verzio>20161123</verzio>
require 'hu.expanda.expda/LuaFunc'
local params = {...}
ui=params[1]
row = ui:findObject('cikkval_table'):getSelectedRow()  
t = luafunc.rowtotable(row)
kod= t['KOD']
nev= t['NEV']
ui:executeCommand('toast','Választott cikk:\n[' .. kod  .. '] ' .. nev)
ui:executeCommand('hide','cikkvalpanel','')
ui:executeCommand('valueto','lcikknev',nev)
ui:executeCommand('valuetohidden','lcikod',kod)
aktmodul = tostring(ui:findObject('lmodulstat'):getText())
if (aktmodul == 'Beérkezés') then
  ui:executeCommand('showobj','cap_drb;ldrb;cap_drb2;edrb2;button_ujean;pfooter','')
  ui:executeCommand('valueto','ldrb','0') 
  ui:executeCommand('valueto','edrb2','') 
  ui:executeCommand('setfocus','edrb2','') 
elseif (aktmodul=='Leltár') then
  ui:executeCommand('showobj','cap_drb;edrb;button_ujean','')
  ui:executeCommand('valueto','edrb','') 
  ui:executeCommand('setfocus','edrb','') 
elseif (aktmodul=='Hkód rendezés') then
  ui:executeCommand('showobj','cap_drb;edrb;button_ujean;button_cikkklt;pfooter','')
  ui:executeCommand('valueto','edrb','') 

    szorzo = tostring(ui:findObject('lszorzo'):getText())
    hkod = tostring(ui:findObject('ehkod'):getText())
    str = 'hkod_cikkhkklt '..hkod..' '..kod
    t2=luafunc.query_assoc(str,false)
    maxkidrb=t2[1]['MAXKIDRB']

    if (szorzo=='-1') then
      ui:executeCommand('valueto','lmaxdrb',maxkidrb)
      ui:executeCommand('showobj','cap_maxdrb','')
    else
      ui:executeCommand('valuetohidden','cap_maxdrb',maxkidrb)
      ui:executeCommand('hideobj','cap_maxdrb','')
    end

  ui:executeCommand('setfocus','edrb','') 
end
