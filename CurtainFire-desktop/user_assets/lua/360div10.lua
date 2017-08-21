local bulletTimer = 0;

local num = 10;
local changeangle1 = 360 / num;
local angle1 = 60;

function tick(enemy, delta, player)
	if bulletTimer > 0.20 then
		for i=1, num, 1 do
			enemy:shoot(10, angle1, 250, 1); --radius, angle, speed, index
			angle1 = angle1 + changeangle1;
		end
		angle1 = 60;
		bulletTimer = 0
	end
 	bulletTimer = bulletTimer + delta;
	--enemy:addBulletAngle(50, 1);
end