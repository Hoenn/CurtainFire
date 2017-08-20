local doOnce = true;
local twoOnce = true;
local bulletTimer = 0;
local num = 25;
local angle = 0;
local changeAngle = 360 / num;

function tick(enemy, delta)
    --Spawn n bullets on top of eachother    
    if doOnce then
        for i = 1, num, 1 do
            enemy:shoot(20, 270, 150, i);
        end
        doOnce = false;
    end

    if bulletTimer > 1.5 and twoOnce then
        for i = 1, num, 1 do
            enemy:changeBulletAngle(angle, i);
            enemy:changeBulletSpeed(250, i);
            angle = changeAngle + angle;
        end
        twoOnce = false;
    end
    bulletTimer = bulletTimer + delta;
end