local bulletTimer = 0;
local changeangle = 0;

local tickTimer = 0;

function tick(enemy, delta)
  --shooting code
  if bulletTimer > 0.2 then
    enemy:shoot(8, changeangle, 100, 0); --radius, angle, speed, index
    bulletTimer = 0;
    changeangle = changeangle + 15;
  end
  bulletTimer = bulletTimer + delta;
  
  --enemy:addBulletSpeed(-0.003, 0);
end

function timer(time, delta)
  if (tickTimer >= time) then
    return true; -- return true when timer finished
  end
  tickTimer = tickTimer + delta;
  return false;
end