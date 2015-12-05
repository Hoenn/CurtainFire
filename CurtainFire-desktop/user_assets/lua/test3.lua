local bulletTimer = 0;
local changeangle = 0;
local changeangleopp = 0;
local multi = -1;

function tick(enemy, delta)
  if bulletTimer > 0.35 then
    enemy:shoot(8, changeangle, 50, 0); --radius, angle, speed, index
    enemy:shoot(8, changeangle + 180, 50, 0);
    --enemy:shoot(8, changeangleopp, 100, 1); --radius, angle, speed, index
    bulletTimer = 0;
    changeangle = changeangle + 15 * multi;
    --changeangleopp = changeangleopp + 15 * multi * -1;
  end
  bulletTimer = bulletTimer + delta;
  enemy:addBulletAngleLimit(3, 180, 0); --angle, limit, index
  enemy:addBulletSpeedLimit(1, 100, 0);
 -- enemy:addBulletAngleLimit(3, -180, 1);
end