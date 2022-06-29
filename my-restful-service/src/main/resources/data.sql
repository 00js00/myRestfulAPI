insert into user(id,join_date,name,password,ssn) values(90001,now(),'User1','pwd111','701011-1111111');
insert into user(id,join_date,name,password,ssn) values(90002,now(),'User2','pwd222','801011-1111111');
insert into user(id,join_date,name,password,ssn) values(90003,now(),'User3','pwd333','901011-1111111');



insert into post(description, user_id, id) values('My first post',90001,10001);
insert into post(description, user_id, id) values('My second post',90001,10002);