local bulletTimer = 0;
local changeangle = 0;
local multi = -1;

function tick(enemy, delta)
  if bulletTimer > 0.1 then
    enemy:shoot(8, changeangle, 350, 0); --radius, angle, speed, index
    bulletTimer = 0;
    changeangle = changeangle + 7.5 * multi;
    if changeangle < -180 or changeangle > 0 then
      multi = multi * -1;
    end
  end
  bulletTimer = bulletTimer + delta;
  enemy:addBulletSpeedLimitDecreasing(-3.5, 50, 0);
end