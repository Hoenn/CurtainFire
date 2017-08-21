local bulletTimer = 0;
local num = 5;

local changeangle1 = 360 / num;
local angle1 = 0;
local count1 = 0;

local changeangle2 = 360 / num;
local angle2 = 0;
local count2 = 0;

function tick(enemy, delta, player)
	if bulletTimer > 0.10 then
		for i=1, num, 1 do
			enemy:shoot(10, angle1, 250, 0); --radius, angle, speed, index
			angle1 = angle1 + changeangle1;
		end
		count1 = count1 + 10;
		if count1 >= 360 then
			count1 = 0;
		end
		angle1 = count1;

		for i=1, num, 1 do
			enemy:shoot(10, angle2, 285, 0); --radius, angle, speed, index
			angle2 = angle2 - changeangle2;
		end
		count2 = count2 - 10;
		if count2 <= -360 then
			count2 = 0;
		end
		angle2 = count2;

		bulletTimer = 0;
	end
 bulletTimer = bulletTimer + delta;
 --enemy:addBulletSpeedLimitDecreasing(-1, 50, 0);
	-- if bulletTimer > 0.50 then
	-- 	local i = -num;
	-- 	while i <= num
	-- 	do
	-- 		enemy:shoot(i, 0, 10, 270, 300, 0);
	-- 		i = i + 50;
	-- 	end
	-- 	bulletTimer = 0;
	-- end
	-- bulletTimer = bulletTimer + delta;
end
