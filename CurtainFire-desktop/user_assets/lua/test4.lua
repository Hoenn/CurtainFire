local bulletTimer = 0;
local num = 10;
local changeangle = 360 / num;
local angle = 0;
local count = 0;

function tick(enemy, delta)
  if bulletTimer > 0.15 then
    for i=1, num, 1 do
      enemy:shoot(20, angle, 250, 0); --radius, angle, speed, index
      angle = angle + changeangle;
    end
    count = count + 10;
    if count >= 360 then
      count = 0;
    end
    angle = count;
    bulletTimer = 0;
  end
  bulletTimer = bulletTimer + delta;
  enemy:addBulletSpeedLimitDecreasing(-1, 50, 0);
end