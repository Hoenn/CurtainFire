local bulletTimer = 0;

local numBullets = 14;
local dAngle = 360/numBullets;
local cAngle = 0;
local sAngle = 0;
local scAngle = 3;



function tick(enemy, delta)

    if bulletTimer> 0.10 then
        for i=1, numBullets, 1 do
            if i % 3 == 0 then 
                enemy:setBulletColor(120, 0, 190);
                enemy:shoot(35, cAngle + sAngle, 200, 0); --radius, angle, speed, index
			else
                enemy:setBulletColor(170, 0, 215);
                enemy:shoot(15, cAngle + sAngle, 230, 0); --radius, angle, speed, index
            end
			cAngle = cAngle + dAngle;
            sAngle = sAngle + scAngle;
        end
        bulletTimer = 0;
        if cAngle >= 360 then
            cAngle = 0;
        end
        if sAngle >= 360 then
            sAngle = 0;
        end
    end
    bulletTimer = bulletTimer + delta;
    enemy:addBulletAngleLimit(0.2, 360, 0);
end